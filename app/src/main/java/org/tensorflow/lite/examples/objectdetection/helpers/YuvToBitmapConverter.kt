package org.tensorflow.lite.examples.objectdetection.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.media.Image
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicYuvToRGB
import android.renderscript.Type

class YuvToBitmapConverter(context: Context) {
    private val rs = RenderScript.create(context)
    private val yuvToRgbIntrinsic = ScriptIntrinsicYuvToRGB.create(rs, Element.U8_4(rs))

    fun convert(image: Image): Bitmap {
        // ARCore frames usually have 3 planes: Y, U, and V
        val yBuffer = image.planes[0].buffer
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        // Copy Y, then V, then U (NV21 format)
        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        // Create the RenderScript allocations
        val yuvType = Type.Builder(rs, Element.U8(rs)).setX(image.width).setY(image.height).setYuvFormat(ImageFormat.NV21)
        val inAllocation = Allocation.createTyped(rs, yuvType.create(), Allocation.USAGE_SCRIPT)

        val rgbaType = Type.Builder(rs, Element.U8_4(rs)).setX(image.width).setY(image.height)
        val outAllocation = Allocation.createTyped(rs, rgbaType.create(), Allocation.USAGE_SCRIPT)

        inAllocation.copyFrom(nv21)

        // Execute the conversion
        yuvToRgbIntrinsic.setInput(inAllocation)
        yuvToRgbIntrinsic.forEach(outAllocation)

        // Create the final Bitmap
        val bitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
        outAllocation.copyTo(bitmap)

        // Adjust for phone orientation (usually 90 or 270 degrees)
        return rotateBitmap(bitmap, 90f)
    }

    private fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }
}