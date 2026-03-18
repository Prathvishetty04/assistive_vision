package org.tensorflow.lite.examples.objectdetection.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.ar.core.Session
import com.google.ar.core.exceptions.*
import org.tensorflow.lite.examples.objectdetection.R
import org.tensorflow.lite.examples.objectdetection.helpers.DisplayRotationHelper
import org.tensorflow.lite.examples.objectdetection.helpers.TapHelper

class ARFragment : Fragment() {

    private val CAMERA_PERMISSION_CODE = 0
    private val CAMERA_PERMISSION = Manifest.permission.CAMERA

    private var arSession: Session? = null
    private lateinit var surfaceView: GLSurfaceView
    private lateinit var displayRotationHelper: DisplayRotationHelper
    lateinit var tapHelper: TapHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_ar, container, false)
        val surfaceViewContainer = rootView.findViewById<FrameLayout>(R.id.ar_surface_view_container)

        displayRotationHelper = DisplayRotationHelper(requireContext())
        surfaceView = GLSurfaceView(requireContext())
        surfaceViewContainer.addView(surfaceView)

        tapHelper = TapHelper(requireContext())
        surfaceView.setOnTouchListener(tapHelper)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        if (!hasCameraPermission()) {
            requestCameraPermission()
            return
        }

        try {
            if (arSession == null) {
                when (com.google.ar.core.ArCoreApk.getInstance().requestInstall(requireActivity(), true)) {
                    com.google.ar.core.ArCoreApk.InstallStatus.INSTALLED -> {
                        arSession = Session(requireContext())
                    }
                    com.google.ar.core.ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        return
                    }
                }
            }
            arSession?.resume()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override fun onPause() {
        super.onPause()
        if (arSession != null) {
            displayRotationHelper.onPause()
            surfaceView.onPause()
            arSession?.pause()
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(CAMERA_PERMISSION), CAMERA_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, results: IntArray) {
        if (!hasCameraPermission()) {
            Toast.makeText(context, "Camera permission is needed to run this application", Toast.LENGTH_LONG).show()
            activity?.finish()
        }
    }

    private fun handleException(e: Exception) {
        val message = when (e) {
            is UnavailableUserDeclinedInstallationException -> "Please install Google Play Services for AR"
            is UnavailableApkTooOldException -> "Please update ARCore"
            is UnavailableSdkTooOldException -> "Please update this app"
            is UnavailableDeviceNotCompatibleException -> "This device does not support AR"
            is CameraNotAvailableException -> "Camera not available. Try restarting the app."
            else -> "Failed to create AR session: $e"
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}