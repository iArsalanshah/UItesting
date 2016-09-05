package com.hyenoon.uigesturetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hyenoon.uigesturetest.sticker_views.StickerImageView;

public class MainActivity extends AppCompatActivity {
    ImageView img1, img2, backImage;
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
                stickerImageView.setControlsVisibility(false);
                layout.setDrawingCacheEnabled(true);
                backImage.setImageBitmap(layout.getDrawingCache());
                stickerImageView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (stickerImageView.getVisibility() == View.GONE) {
            stickerImageView.setVisibility(View.VISIBLE);
            backImage.setImageResource(R.drawable.capa_1);
            stickerImageView.setControlsVisibility(true);
        } else
            super.onBackPressed();
    }
}