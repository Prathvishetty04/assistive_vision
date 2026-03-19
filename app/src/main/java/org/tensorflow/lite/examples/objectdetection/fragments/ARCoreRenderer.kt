package org.tensorflow.lite.examples.objectdetection.fragments

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import com.google.ar.core.Frame
import com.google.ar.core.Session
import org.tensorflow.lite.examples.objectdetection.helpers.YuvToBitmapConverter
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ARCoreRenderer(
    private val context: Context,
    private val session: Session,
    private val fragment: ARFragment
) : GLSurfaceView.Renderer {

    private val converter = YuvToBitmapConverter(context)
    private var textureId = -1

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // 1. Set the background clear color to black (not white)
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f)

        // 2. Generate a texture ID for the camera feed
        val textures = IntArray(1)
        GLES20.glGenTextures(1, textures, 0)
        textureId = textures[0]

        // Bind the texture to ARCore
        session.setCameraTextureName(textureId)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        session.setDisplayGeometry(0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        // 3. Clear the screen every frame
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        try {
            // Use the textureId we generated in onSurfaceCreated
            session.setCameraTextureName(textureId)

            val frame: Frame = session.update()

            // 4. Handle the camera image for AI processing
            val image = try {
                frame.acquireCameraImage()
            } catch (e: Exception) {
                null
            } ?: return

            val bitmap = converter.convert(image)

            // Send back to fragment for YOLO processing
            fragment.onFrameProcessed(bitmap, frame)

            image.close()

        } catch (e: Exception) {
            Log.e("AR_RENDERER", "Draw frame failed: ${e.message}")
        }
    }
}