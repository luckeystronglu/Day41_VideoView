package com.qf.listviewmedia;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken on 2016/10/23.14:31
 */
public class MediaAdapter extends BaseAdapter implements View.OnClickListener, MediaPlayer.OnPreparedListener {


    private Context context;
    private List<VideoEntity> datas;
    private int playIndex = -1;//需要播放的视频item下标

    private MediaPlayer mediaplayer;

    public MediaAdapter(Context context){
        this.context = context;
        this.datas = new ArrayList<>();
        this.mediaplayer = new MediaPlayer();
        this.mediaplayer.setOnPreparedListener(this);
    }

    public void setDatas(List<VideoEntity> datas){
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if(convertView != null){
            viewHodler = (ViewHodler) convertView.getTag();
        } else {
            viewHodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
            viewHodler.iv = (ImageView) convertView.findViewById(R.id.iv);
            viewHodler.tv = (TextView) convertView.findViewById(R.id.tv_title);
            viewHodler.ttv = (TextureView) convertView.findViewById(R.id.ttv);
            viewHodler.ttv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //暂停position
                    if(mediaplayer != null && mediaplayer.isPlaying()){
                        mediaplayer.pause();
                    } else {
                        mediaplayer.start();
                    }
                }
            });
            //设置图片的点击事件
            viewHodler.iv.setOnClickListener(this);
            convertView.setTag(viewHodler);
        }

        //设置标题
        viewHodler.tv.setText(datas.get(position).getTitle());

        //设置图片
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(datas.get(position).getWidth(), datas.get(position).getHeight());
        layoutParams.gravity = Gravity.CENTER;
        viewHodler.iv.setLayoutParams(layoutParams);
        //设置tag
        viewHodler.iv.setTag(R.id.position, position);

        Glide.with(context).load(datas.get(position).getImgUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(datas.get(position).getWidth(), datas.get(position).getHeight())
                .thumbnail(0.1f)
                .into(viewHodler.iv);


        //判断Item是否已经重用
        if(viewHodler.ttv.getTag() != null){
            if((int)viewHodler.ttv.getTag() != position && (int)viewHodler.ttv.getTag() == playIndex){
                //说明Item已经被重用
                if(mediaplayer != null && mediaplayer.isPlaying()){
                    mediaplayer.stop();
                    mediaplayer.reset();
                }

                playIndex = -1;
            }
        }


        //设置TextureView
        viewHodler.ttv.setTag(position);
        viewHodler.ttv.setLayoutParams(layoutParams);

        //开始播放视频
        if(position == playIndex){
            //该item需要播放视频
            //隐藏图片
            viewHodler.iv.setVisibility(View.GONE);
            try {
                mediaplayer.setSurface(new Surface(viewHodler.ttv.getSurfaceTexture()));
                mediaplayer.setDataSource(datas.get(position).getMediaUrl());
                mediaplayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //显示图片
            viewHodler.iv.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    /**
     * 点击图片开始播放
     * @param v
     */
    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.position);
        this.playIndex = position;

        //重置Mediaplayer
        if(mediaplayer != null && mediaplayer.isPlaying()){
            mediaplayer.reset();
        }
        this.notifyDataSetChanged();
    }

    /**
     * 视频准备完成
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaplayer.start();
    }

    class ViewHodler{
        ImageView iv;
        TextureView ttv;
        TextView tv;


    }
}
