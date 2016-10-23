package com.qf.day41_videoview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * 第一种播放视频的方式：VideoView
 * 第二种播放视频的方式：MediaPlayer + SurfaceView
 */
public class MainActivity extends AppCompatActivity {

    private VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vv = (VideoView) findViewById(R.id.vv);

        /*
        1、raw下的视频文件
         */
//        vv.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.test);

        /*
        2、手机的SD卡
         */
//        File file = new File(Environment.getExternalStorageDirectory(), "/Movies/test.3gp");
//        Log.d("print", "--------->" + file.exists());
//        vv.setVideoURI(Uri.fromFile(file));

        /*
        3、网络上的视频资源 点播Http 直播rtsp rtmp
        rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp
         */
//        vv.setVideoURI(Uri.parse("rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp"));
        vv.setVideoPath("http://10.20.153.219:8080/AndroidServer/file/test.3gp");

        //设置一个控制器
        vv.setMediaController(new MediaController(this));

    }

    public void btnclick(View view){
        vv.start();
    }
}
