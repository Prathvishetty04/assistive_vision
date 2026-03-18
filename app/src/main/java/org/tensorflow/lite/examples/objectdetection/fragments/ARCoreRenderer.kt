package org.tensorflow.lite.examples.objectdetection.fragments

import android.opengl.GLSurfaceView
import com.google.ar.core.Frame
import com.google.ar.core.Session
import com.google.ar.core.exceptions.CameraNotAvailableException
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

// This class handles the rendering of the AR background and any 3D objects.
class ARCoreRenderer(private val session: Session) : GLSurfaceView.Renderer {

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // No setup needed for this basic renderer
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        session.setDisplayGeometry(0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        try {
            session.setCameraTextureName(0)
            val frame: Frame = session.update()
            // We will draw objects based on the frame later
        } catch (e: CameraNotAvailableException) {
            // Handle camera exception
        }
    }
}