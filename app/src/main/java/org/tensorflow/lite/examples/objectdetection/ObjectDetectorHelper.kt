package org.tensorflow.lite.examples.objectdetection

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.os.SystemClock
import android.speech.tts.TextToSpeech
import android.util.Log
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.Rot90Op
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.Locale

class ObjectDetectorHelper(
    var threshold: Float = 0.5f,
    var numThreads: Int = 2,
    var maxResults: Int = 5,
    val context: Context,
    val objectDetectorListener: DetectorListener?
) : TextToSpeech.OnInitListener {

    private var interpreter: Interpreter? = null
    private var labels = listOf<String>()
    private var tts: TextToSpeech? = null

    private var tensorWidth = 0
    private var tensorHeight = 0
    private var numChannel = 0
    private var numElements = 0

    init {
        setupObjectDetector()
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
                objectDetectorListener?.onError("TTS Language not supported")
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
            objectDetectorListener?.onError("TTS Initialization Failed")
        }
    }

    private fun setupObjectDetector() {
        val modelName = "yolov5m-fp16.tflite"
        try {
            val modelBuffer = FileUtil.loadMappedFile(context, modelName)
            val options = Interpreter.Options()
            options.numThreads = numThreads
            interpreter = Interpreter(modelBuffer, options)

            val inputTensor = interpreter?.getInputTensor(0)
            val inputShape = inputTensor?.shape()
            tensorWidth = inputShape?.get(2) ?: 0
            tensorHeight = inputShape?.get(1) ?: 0

            val outputTensor = interpreter?.getOutputTensor(0)
            val outputShape = outputTensor?.shape()
            numChannel = outputShape?.get(1) ?: 0
            numElements = outputShape?.get(2) ?: 0

            labels = FileUtil.loadLabels(context, "coco_labels.txt")

        } catch (e: Exception) {
            objectDetectorListener?.onError("TFLite failed to load model with error: " + e.message)
            Log.e("ObjectDetectorHelper", "Error setting up interpreter", e)
        }
    }

    fun detect(image: Bitmap, imageRotation: Int) {
        if (interpreter == null) {
            return
        }

        var inferenceTime = SystemClock.uptimeMillis()

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(tensorHeight, tensorWidth, ResizeOp.ResizeMethod.BILINEAR))
            .add(Rot90Op(-imageRotation / 90))
            .build()

        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(image))
        val resizedBitmap = tensorImage.bitmap

        val byteBuffer = convertBitmapToByteBuffer(resizedBitmap)
        val output = Array(1) { Array(numChannel) { FloatArray(numElements) } }
        interpreter?.run(byteBuffer, output)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime

        val results = processOutput(output[0])

        objectDetectorListener?.onResults(
            results,
            inferenceTime,
            tensorImage.height,
            tensorImage.width
        )
    }

    private fun processOutput(modelOutput: Array<FloatArray>): List<Detection> {
        val detections = mutableListOf<Detection>()
        for (i in 0 until numChannel) {
            val score = modelOutput[i][4]
            if (score >= threshold) {
                val x = modelOutput[i][0]
                val y = modelOutput[i][1]
                val w = modelOutput[i][2]
                val h = modelOutput[i][3]

                var maxScore = 0f
                var label = ""
                for (j in 5 until numElements) {
                    if (modelOutput[i][j] > maxScore) {
                        maxScore = modelOutput[i][j]
                        label = labels.getOrElse(j - 5) { "Unknown" }
                    }
                }

                val boundingBox = RectF((x - w / 2), (y - h / 2), (x + w / 2), (y + h / 2))
                detections.add(Detection(boundingBox, listOf(label), listOf(score * maxScore)))
            }
        }
        return detections.sortedByDescending { it.scores[0] }.take(maxResults)
    }

    // THIS IS THE CORRECTED FUNCTION
    fun speak(message: String) {
        tts?.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(1 * tensorWidth * tensorHeight * 3 * 4)
        byteBuffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(tensorWidth * tensorHeight)
        bitmap.getPixels(pixels, 0, tensorWidth, 0, 0, tensorWidth, tensorHeight)

        for (pixelValue in pixels) {
            val r = (pixelValue shr 16 and 0xFF) / 255.0f
            val g = (pixelValue shr 8 and 0xFF) / 255.0f
            val b = (pixelValue and 0xFF) / 255.0f
            byteBuffer.putFloat(r)
            byteBuffer.putFloat(g)
            byteBuffer.putFloat(b)
        }
        return byteBuffer
    }

    data class Detection(
        val boundingBox: RectF,
        val categories: List<String>,
        val scores: List<Float>
    )

    interface DetectorListener {
        fun onError(error: String)
        fun onResults(
            results: List<Detection>?,
            inferenceTime: Long,
            imageHeight: Int,
            imageWidth: Int
        )
    }
}