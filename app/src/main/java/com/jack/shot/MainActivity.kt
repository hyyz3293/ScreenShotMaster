package com.jack.shot

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.PermissionUtils
import com.jack.shot.share.ScreenShotListenManager
import com.jack.shot.share.ShareUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var screenManager: ScreenShotListenManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenManager = ScreenShotListenManager.newInstance(this)
        screenManager!!.setListener {imagePath ->
            ShareUtils.openShareLongPic(this, imagePath)
        }

        initPermission()
        initLister()
    }


    override fun onStart() {
        super.onStart()
        screenManager!!.startListen()
    }

    override fun onStop() {
        super.onStop()
        screenManager!!.stopListen()
    }



    @SuppressLint("WrongConstant")
    fun initPermission() {
        PermissionUtils.permission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {

                }

                override fun onDenied(
                    permissionsDeniedForever: List<String>,
                    permissionsDenied: List<String>) {
                }
            }).request()
    }

    private fun initLister() {
        OnClickLongPic.setOnClickListener {
            ShareUtils.openSharePic(this)
        }

        OnClickLongText.setOnClickListener {
            ShareUtils.openShareTxt(this, "分享个什么文字好呢")
        }


    }
}
