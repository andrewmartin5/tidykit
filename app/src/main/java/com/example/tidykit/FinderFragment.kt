package com.example.tidykit

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class FinderFragment : Fragment(), View.OnClickListener {
    var nextClick = false;
    var enabled = false;
    var mp: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.finder_fragment, container, false)
        val finderButton: Button = view.findViewById<Button>(R.id.finderButton)
        finderButton.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View) {
        when (v.getId()) {
            R.id.finderButton -> {
                if (!enabled) {
                    if (!nextClick) {
                        var toast: Toast =
                            Toast.makeText(
                                activity,
                                "Please Press the Button Again: WARNING: VERY LOUD",
                                Toast.LENGTH_SHORT
                            )
                        toast.show()
                        nextClick = true
                    } else {
                        mp = MediaPlayer.create(context, R.raw.white_noise_ringtone)
                        mp!!.start()
                        mp!!.isLooping = true
                        var toast: Toast =
                            Toast.makeText(
                                activity,
                                "Headphone Finder On. To Disable, Click Again",
                                Toast.LENGTH_SHORT
                            )
                        toast.show()
                        nextClick = false
                        enabled = true
                    }
                } else {
                    enabled = false
                    if (mp != null) {
                        var toast: Toast =
                            Toast.makeText(activity, "Headphone Finder Disabled", Toast.LENGTH_SHORT)
                        toast.show()
                        mp!!.pause()
                        mp = null
                    }
                }
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