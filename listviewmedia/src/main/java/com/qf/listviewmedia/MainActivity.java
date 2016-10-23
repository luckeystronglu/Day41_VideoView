package com.qf.listviewmedia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<VideoEntity> datas;
    private MediaAdapter mediaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv);
        mediaAdapter = new MediaAdapter(this);
        listView.setAdapter(mediaAdapter);

        loadDatas();
    }

    /**
     * 加载数据
     */
    private void loadDatas() {
        String json = null;
        try {
            InputStream in = getAssets().open("video1.json");
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            int len = 0;
            byte[] buffer = new byte[1024];

            while((len = in.read(buffer)) != -1){
                out.write(buffer, 0, len);
            }

            json = new String(out.toByteArray(), "utf-8");
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(json != null){
            datas = new ArrayList<>();

            //解析json
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("data");

                for(int i = 0; i < jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    int type = jsonObject.getInt("type");
                    if(type == 1){
                        //创建一个实体类
                        VideoEntity videoEntity = new VideoEntity();
                        //获得视频播放地址
                        String mediaurl = jsonObject.getJSONObject("group").getJSONObject("360p_video").getJSONArray("url_list").getJSONObject(0).getString("url");
                        videoEntity.setMediaUrl(mediaurl);

                        //获得宽高
                        int width = jsonObject.getJSONObject("group").getJSONObject("360p_video").getInt("width");
                        int height = jsonObject.getJSONObject("group").getJSONObject("360p_video").getInt("height");
                        videoEntity.setWidth(width);
                        videoEntity.setHeight(height);

                        //解析标题
                        String title = jsonObject.getJSONObject("group").getString("text");
                        videoEntity.setTitle(title);


                        //解析封面图片
                        String imgUrl = jsonObject.getJSONObject("group").getJSONObject("medium_cover").getJSONArray("url_list").getJSONObject(0).getString("url");
                        videoEntity.setImgUrl(imgUrl);

                        datas.add(videoEntity);
                    }
                }

                mediaAdapter.setDatas(datas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
