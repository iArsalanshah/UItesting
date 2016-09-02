package com.hyenoon.uigesturetest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hyenoon.uigesturetest.sticker_views.StickerImageView;

public class MainActivity extends AppCompatActivity {
    ImageView img1, img2, finalImage, backImage;
    Button share, save, takePic;
    private FrameLayout layout;
    private StickerImageView stickerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        clickEvents();
        layout.addView(stickerImageView);
    }

    private void init() {
        layout = (FrameLayout) findViewById(R.id.bg);
        finalImage = (ImageView) findViewById(R.id.fullScreenFinalImage);
        backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setImageResource(R.drawable.capa_1);
        stickerImageView = new StickerImageView(this);
        stickerImageView.setImageDrawable(getResources().getDrawable(R.drawable.capa_2));
        save = (Button) findViewById(R.id.btnSave);
    }

    private void clickEvents() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalImage.setVisibility(View.VISIBLE);
//                stickerImageView.setControlsVisibility(false);

                //combine two bitmaps
                backImage.buildDrawingCache();
                finalImage.setImageBitmap(overlay(backImage.getDrawingCache(), stickerImageView.getImageBitmap()));
            }
        });
    }

    public Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        bmp2 = getResizedBitmap(bmp2, stickerImageView.getHeight(), stickerImageView.getWidth());
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, 0f, 0f, null);
        canvas.drawBitmap(bmp2, 0f, 0f, null);
        return bmOverlay;
    }

    @Override
    public void onBackPressed() {
        if (finalImage.getVisibility() == View.VISIBLE) {
            stickerImageView.setControlsVisibility(true);
            finalImage.setVisibility(View.GONE);
        } else
            super.onBackPressed();
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
}