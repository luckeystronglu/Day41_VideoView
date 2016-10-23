package com.qf.textrueview;

import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import java.io.File;
import java.io.IOException;

/**
 * Mediaplay + TextrueView(4.0)
 */
public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    private TextureView ttr;
    private MediaPlayer mediaPlayer;
    private Surface surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ttr = (TextureView) findViewById(R.id.ttr);
        ttr.setSurfaceTextureListener(this);


        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "/Movies/test.3gp")));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnclick(View view){
        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        this.surface = new Surface(surface);
        mediaPlayer.setSurface(this.surface);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {}

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
