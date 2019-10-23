package com.jack.shot.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import com.blankj.utilcode.util.ImageUtils;
import com.jack.shot.utils.ImgUtils;

import java.io.File;
import java.io.IOException;

import static com.jack.shot.share.UriUtils.forceGetFileUri;
import static com.jack.shot.share.UriUtils.getFileUri;

public class ShareUtils {

    /**
     * 文字分享框
     * @param mContext
     * @param txt
     */
    public static void openShareTxt(Context mContext, String txt) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, txt);
        //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
        shareIntent = Intent.createChooser(shareIntent, "文字分享框");
        mContext.startActivity(shareIntent);
    }

    /**
     * 图片分享
     * @param context
     */
    public static void openSharePic(Context context) {
        ShareView shareView = new ShareView(context);
        shareView.setTxt("优秀的程序员应该具备两方面能力：\n" +
                "\n" +
                "良好的 程序设计 能力：\n" +
                "掌握常用的数据结构和算法（例如链表，栈，堆，队列，排序和散列）；\n" +
                "理解计算机科学的核心概念（例如计算机系统结构、操作系统、编译原理和计算机网络）；\n" +
                "熟悉至少两门以上编程语言（例如 C++，Java，C#，和 Python）；\n" +
                "专业的 软件开发 素养：\n" +
                "具备良好的编程实践，能够编写可测试（Testable），可扩展（Extensible），可维护（Maintainable）的代码；\n" +
                "把握客户需求，按时交付客户所需要的软件产品；\n" +
                "理解现代软件开发过程中的核心概念（例如面向对象程序设计，测试驱动开发，持续集成，和持续交付等等）");
        Bitmap bitmap = shareView.createImage();
        String filePath = Environment.getExternalStorageDirectory() + "/aaaaaa/aaa";
        try {
            ImgUtils.buildFile(filePath, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        filePath += "/" + ImgUtils.getTimeStampFileName(0);
        ImageUtils.save(bitmap, filePath, Bitmap.CompressFormat.JPEG);

        Intent share = new Intent(Intent.ACTION_SEND);
        File file = new File(filePath);
        Uri contentUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            contentUri = getFileUri(context, ShareContentType.IMAGE,file);
            share.putExtra(Intent.EXTRA_STREAM, contentUri);
            share.setType("image/*");
        } else {
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            share.setType("image/*");
        }
        try{
            context.startActivity(Intent.createChooser(share, "Share"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片分享
     * @param mContext
     */
    public static void openShareLongPic(Context mContext, String filepath) {
        try {
            ShareView shareView = new ShareView(mContext);
            Bitmap showBit = ImageUtils.getBitmap(filepath);
            shareView.setImg(showBit);

            Bitmap bitmap = shareView.createImage();
            String imagePath = Environment.getExternalStorageDirectory() + "/Ace";
            ImgUtils.buildFile(imagePath, true);
            imagePath += "/" + ImgUtils.getTimeStampFileName(0);
            ImageUtils.save(bitmap, imagePath, Bitmap.CompressFormat.JPEG);

            Uri imgUri;
            File file = new File(imagePath);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imgUri = forceGetFileUri(file);
            } else {
                imgUri = Uri.fromFile(file);
            }

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            //其中fileUri为文件的标识符
            shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
            shareIntent.setType("image/*");
            shareIntent = Intent.createChooser(shareIntent, "图片分享框");
            mContext.startActivity(shareIntent);
            bitmap.recycle();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
