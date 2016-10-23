package com.qf.mediaplayerdemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, SeekBar.OnSeekBarChangeListener {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;


    private SeekBar seekBar;

    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);

        /*
        1、raw
         */
//        mediaPlayer = MediaPlayer.create(this, R.raw.test);

        /*
        2、播放拓展卡的资源
         */
//        mediaPlayer = new MediaPlayer();
//        File file = new File(Environment.getExternalStorageDirectory(), "/Movies/test.3gp");
//        try {
//            mediaPlayer.setDataSource(this, Uri.fromFile(file));
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        /*
        3、播放网络资源
         */
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, Uri.parse("http://10.20.153.219:8080/AndroidServer/file/test.3gp"));
            mediaPlayer.prepare();
//            mediaPlayer.getCurrentPosition();
//            mediaPlayer.getDuration();
//            mediaPlayer.prepareAsync();
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//
//                }
//            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnclick(View view){
        mediaPlayer.start();

        new MyThread().start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mediaPlayer != null){
            mediaPlayer.reset();
            mediaPlayer.release();
        }

        flag = false;
    }

    /**
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        mediaPlayer.seekTo(progress);
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            while(flag){
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(mediaPlayer.getCurrentPosition());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
