package com.jack.shot.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;

public class PhototUtils {

    private static final int REQUEST_FILE_SELECT_CODE = 100;
    /**
     * 打开系统相机进行拍照
     */
    private void openSystemCamera(Activity mContext) {
        // 调用系统相机进行拍照与上面通过文件选择器获得文件 uri 的方式类似
        // 在 onActivityResult 进行回调处理，此时 Uri 是你 FileProvider 中指定的，注意与文件选择器获取的 Uri 的区别。
        //调用系统相机
        Intent takePhotoIntent = new Intent();
        takePhotoIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePhotoIntent.resolveActivity(mContext.getPackageManager()) == null) {
            Toast.makeText(mContext, "当前系统没有可用的相机应用", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = "TEMP_" + System.currentTimeMillis() + ".jpg";
        File photoFile = new File(Environment.getExternalStorageDirectory(), fileName);

        Uri currentTakePhotoUri;
        // 7.0和以上版本的系统要通过 FileProvider 创建一个 content 类型的 Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currentTakePhotoUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileProvider", photoFile);
            takePhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            currentTakePhotoUri = Uri.fromFile(photoFile);
        }

        //将拍照结果保存至 outputFile 的Uri中，不保留在相册中
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentTakePhotoUri);
        mContext.startActivityForResult(takePhotoIntent, REQUEST_FILE_SELECT_CODE);
    }



}
