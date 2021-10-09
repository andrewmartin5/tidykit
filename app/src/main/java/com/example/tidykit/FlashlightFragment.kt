package com.example.tidykit

import android.content.Context.CAMERA_SERVICE
import android.content.pm.PackageManager
import android.graphics.Camera
import android.hardware.camera2.CameraManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tidykit.R

class FlashlightFragment : Fragment(), View.OnClickListener {
    private var isFlashlightAvailable = false
    private var cameraManager: CameraManager? = null
    private var flashLightIsOn = false
    private var cameraID: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.flashlight_fragment, container, false)
        val lightButton: Button = view.findViewById<Button>(R.id.flashlightButton)
        lightButton.setOnClickListener(this)
        flashlightSetUp()
        return view
    }

    var torchCallback: CameraManager.TorchCallback = object : CameraManager.TorchCallback() {
        override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
            super.onTorchModeChanged(cameraId, enabled)
            flashLightIsOn = enabled
        }
    }

    private fun flashlightSetUp() {
        isFlashlightAvailable =
            requireActivity().applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        cameraManager =
            requireActivity().getSystemService(CAMERA_SERVICE) as CameraManager
        cameraManager!!.registerTorchCallback(torchCallback, null)
        if (cameraManager != null) {
            cameraID = cameraManager!!.cameraIdList[0]
        }
    }

    override fun onClick(v: View) {
        when (v.getId()) {
            R.id.flashlightButton -> {
                var toast: Toast =
                    Toast.makeText(activity, "No Flashlight Found", Toast.LENGTH_SHORT)
                if (isFlashlightAvailable) {
                    cameraID?.let { cameraManager?.setTorchMode(it, !flashLightIsOn) }
                    if (flashLightIsOn) {
                        toast = Toast.makeText(activity, "Flashlight Off", Toast.LENGTH_SHORT)
                    } else {
                        toast = Toast.makeText(activity, "Flashlight On", Toast.LENGTH_SHORT)
                    }
                }
                toast.show()
            }
            else -> {
                val toast: Toast = Toast.makeText(
                    activity,
                    "You Probably Shouldn't Have Clicked That",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }
}