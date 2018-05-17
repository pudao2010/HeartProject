package com.zyyoona7.heartview;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyyoona7.heartlib.HeartView;
import com.zyyoona7.heartview.viewpager3d.ClipView;
import com.zyyoona7.heartview.viewpager3d.FlowTransformer;

import java.util.HashMap;
import java.util.Random;


/**
 * 播放 despacito
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private HeartView mHeartView;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UI:
                    tvContent.append(len);
                    break;

                default:
                    break;
            }
        }
    };
    private TextView tvContent;

    protected static final int UI = 100;
    private String TextDemo = "我是一段测试文字我是一段测试文字我是一段测试文字我是一段测试文字我是一段测试文字我是一段测试文字我是一段测试文字";
    private char[] charArrays;
    private String len = "";

    int[] imgIds = {
            R.drawable.img_001,
            R.drawable.img_002,
            R.drawable.img_003,
            R.drawable.img_004,
            R.drawable.img_005,
            R.drawable.img_006,
            R.drawable.img_007,
            R.drawable.img_008,
            R.drawable.grouphead,
    };

    ViewPager mViewPager;

    HashMap<Integer, ClipView> imageViewList = new HashMap<>();
    private MediaPlayer mMediaPlayer;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mHeartView.addHeart(new Random().nextInt(6));
            mHeartView.addHeart(new Random().nextInt(6));
            mHeartView.addHeart(new Random().nextInt(6));
            mHeartView.addHeart(new Random().nextInt(6));
            mHandler.postDelayed(this, 500);
        }
    };
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);
        mHeartView = (HeartView) findViewById(R.id.heart_view);
        ImageView bgImage = (ImageView) findViewById(R.id.iv_bg);
        mHeartView.setOnClickListener(this);
        bgImage.setOnClickListener(this);

        mViewPager = (ViewPager) findViewById(R.id.fvp_pagers);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setPageTransformer(true, new FlowTransformer(mViewPager));
        mViewPager.setOffscreenPageLimit(imgIds.length);
        initMediaPlayer();
        mHandler.postDelayed(runnable, 2000);
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawTextOneByOne();
            }
        }, 2500);

    }

    private void drawTextOneByOne() {
        new Thread() {
            public void run() {
                try {
                    charArrays = TextDemo.toCharArray();
                    for (int i = 0; i < charArrays.length; i++) {
                        sleep(800);
                        len = charArrays[i] + "";
                        mHandler.sendEmptyMessage(UI);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initMediaPlayer() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.anzhan);
//        mMediaPlayer.start();

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//获取最大音量值
        int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//获取当前音量
        Log.e("TAG", "initMediaPlayer: streamMaxVolume=" + streamMaxVolume + ", streamVolume=" + streamVolume);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                streamMaxVolume / 2, AudioManager.FLAG_ALLOW_RINGER_MODES);//给音量设置值，第二个参数值，第三个参数是设置一下标识

    }

    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return imgIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            /*ClipView clipView;
            if (imageViewList.containsKey(position)) {
                clipView = imageViewList.get(position);
            } else {

                //xml的方式使用ReflectView
                clipView = (ClipView) View.inflate(MainActivity.this, R.layout.item_image, null);
                ImageView imageView = (ImageView) clipView.findViewById(R.id.iv_img);
                imageView.setImageResource(imgIds[position]);

                //纯java方式使用ReflectView
//                ImageView imageView = new ImageView(container.getContext());
//                imageView.setImageResource(imgIds[position]);
//                imageView.setAdjustViewBounds(false);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                ReflectView reflectView = new ReflectView(container.getContext());
//                reflectView.setContentView(imageView, 0.5f);
//                clipView = new ClipView(container.getContext());
//                clipView.setId(position + 1);
//                clipView.addView(reflectView);


                imageViewList.put(position, clipView);

            }
            container.addView(clipView);
            clipView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "v.getId " + v.getId(), Toast.LENGTH_LONG).show();
                    mViewPager.setCurrentItem(position, true);
                }
            });
            return clipView;*/
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(imgIds[position]);
            container.addView(imageView);
            return imageView;
        }
    };

    @Override
    public void onClick(View v) {
        mHeartView.addHeart(new Random().nextInt(6));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mHandler.removeCallbacks(runnable);
    }
}
