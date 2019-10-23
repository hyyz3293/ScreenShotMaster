package com.jack.shot.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ScreenUtils;
import com.jack.shot.R;


public class ShareView extends FrameLayout {

    private int IMAGE_WIDTH = 720;
    private int IMAGE_HEIGHT = 1280;

    private ScrollView scrollView;
    private View layoutView;

    private TextView textView;
    private ImageView imgView;

    public ShareView(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        layoutView = View.inflate(getContext(), R.layout.item_long_view, this);
        scrollView = layoutView.findViewById(R.id.scrolls);
        textView = layoutView.findViewById(R.id.aaaa);
        imgView = layoutView.findViewById(R.id.img);
    }

    public void setImg(Bitmap bitmap) {
        if (imgView != null && bitmap != null)
            imgView.setImageBitmap(bitmap);
    }


    public void setTxt(String string) {
        if (textView != null)
            textView.setText(string);
    }


    /**
     * 生成图片 ---- 长图用这种方法生成
     *
     * @return
     */
    public Bitmap createImage() {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);

        layoutView.measure(width, height);

        IMAGE_WIDTH = ScreenUtils.getScreenWidth(); // 获取宽度
        IMAGE_HEIGHT = layoutView.getMeasuredHeight(); // 获取高度

        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(IMAGE_WIDTH, MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(IMAGE_HEIGHT, MeasureSpec.EXACTLY);

        measure(widthMeasureSpec, heightMeasureSpec);
        layout(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        scrollView.draw(canvas);
        return bitmap;

    }

    /**
     * 生成图片
     *
     * @return
     */
    public Bitmap createImage2() {
        //由于直接new出来的view是不会走测量、布局、绘制的方法的，所以需要我们手动去调这些方法，不然生成的图片就是黑色的。
        //IMAGE_HEIGHT = layoutView.get();
        //IMAGE_WIDTH = layoutView.getMeasuredWidth();
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        layoutView.measure(width, height);
        IMAGE_WIDTH = ScreenUtils.getScreenWidth(); // 获取宽度
        IMAGE_HEIGHT = layoutView.getMeasuredHeight(); // 获取高度

        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(IMAGE_WIDTH, MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(IMAGE_HEIGHT, MeasureSpec.EXACTLY);

        measure(widthMeasureSpec, heightMeasureSpec);
        layout(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        Bitmap bitmap = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        draw(canvas);

        return bitmap;
    }

}