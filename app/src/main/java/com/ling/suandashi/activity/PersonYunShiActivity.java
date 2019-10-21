package com.ling.suandashi.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ling.suandashi.R;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.base.DefaultListener;
import com.ling.suandashi.data.entity.PersonFortuneBean;
import com.ling.suandashi.data.request.PersonYunShiRequest;
import com.ling.suandashi.net.HttpRequestUtils;
import com.ling.suandashi.view.CBProgressBar;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonYunShiActivity extends BasicActivity{

    @BindView(R.id.base_title_title_tv)
    TextView title;
    @BindView(R.id.person_yunshi_cb_circle)
    CBProgressBar summary_star;

    @BindView(R.id.person_yunshi_today)
    TextView person_day;
    @BindView(R.id.person_yunshi_month)
    TextView person_month;

    @BindView(R.id.person_yunshi_name)
    TextView name;
    @BindView(R.id.person_yunshi_birthday)
    TextView birthday;
    @BindView(R.id.person_yunshi_summary_tv)
    TextView summary_tv;

    @BindView(R.id.person_yunshi_day_ll)
    LinearLayout day_ll;

    @BindView(R.id.person_yunshi_cb_caiyun)
    CBProgressBar caiyun_start;
    @BindView(R.id.person_yunshi_caiyun_tv)
    TextView caiyun_tv;
    @BindView(R.id.person_yunshi_cb_work)
    CBProgressBar work_start;
    @BindView(R.id.person_yunshi_work_tv)
    TextView work_tv;
    @BindView(R.id.person_yunshi_cb_love)
    CBProgressBar love_start;
    @BindView(R.id.person_yunshi_love_tv)
    TextView love_tv;

    @BindView(R.id.person_yunshi_tv_all)
    TextView yunshi_summary_tv;

    @BindView(R.id.person_yunshi_month_ll)
    LinearLayout month_ll;
    @BindView(R.id.person_yunshi_month_summary_title)
    TextView month_summary_title;
    @BindView(R.id.person_yunshi_month_summary_tv)
    TextView month_summary_tv;
    @BindView(R.id.person_yunshi_month_work_title)
    TextView month_work_title;
    @BindView(R.id.person_yunshi_month_work_tv)
    TextView month_work_tv;
    @BindView(R.id.person_yunshi_month_love_title)
    TextView month_love_title;
    @BindView(R.id.person_yunshi_month_love_tv)
    TextView month_love_tv;

    @BindView(R.id.person_yunshi_img)
    ImageView img;

    public static final String usrName = "USRNAME";
    public static final String usrBirthday = "USRBIRTHDAY";
    public static final String dataBirthday = "DATABIRTHDAY";
    private PersonFortuneBean mBean;

    private SensorManager mSensorManager;
    private SensorEventListener mSensorEventListener;
    private float val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_yunshi);
        ButterKnife.bind(this);
        title.setText("个人运势");

        name.setText(getIntent().getStringExtra(usrName));
        birthday.setText(getIntent().getStringExtra(usrBirthday));

        loadData(getIntent().getStringExtra(dataBirthday));

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                RotateAnimation animation = new RotateAnimation
                        (val, -event.values[0], Animation.RELATIVE_TO_SELF, 0.5f, Animation.
                                RELATIVE_TO_SELF, 0.5f);
                animation.setFillAfter(true);
                img.startAnimation(animation);
                //让动画停留在在结束的位置
                //记录上一次的角度
                val = -event.values[0];
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(mSensorEventListener,mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    private void loadData(String dataBirthday) {

        PersonYunShiRequest request = new PersonYunShiRequest(dataBirthday);
        HttpRequestUtils requestUtils = new HttpRequestUtils(this, request, new DefaultListener(this) {
            @Override
            public void onResponse(Object obj) {
                if(obj instanceof PersonFortuneBean){
                    mBean = (PersonFortuneBean) obj;
                    day_ll.setVisibility(View.VISIBLE);
                    summary_star.setProgress(mBean.xingzuo.day.summary_star * 20);
                    summary_tv.setText(mBean.xingzuo.day.summary_star * 20 + "分");

                    if(mBean.xingzuo != null && mBean.xingzuo.day != null){

                        caiyun_start.setProgress(mBean.xingzuo.day.money_star * 20);
                        caiyun_tv.setText(mBean.xingzuo.day.money_star * 20 + "分");
                        work_start.setProgress(mBean.xingzuo.day.work_star * 20);
                        work_tv.setText(mBean.xingzuo.day.work_star * 20 + "分");
                        love_start.setProgress(mBean.xingzuo.day.love_star * 20);
                        love_tv.setText(mBean.xingzuo.day.love_star * 20 + "分");

                        yunshi_summary_tv.setText(mBean.xingzuo.day.general_txt);
                    }

                    if(mBean.xingzuo != null && mBean.xingzuo.month != null){
                        if(TextUtils.isEmpty(mBean.xingzuo.month.general_txt)){
                            month_summary_title.setVisibility(View.GONE);
                            month_summary_tv.setVisibility(View.GONE);
                        }else {
                            month_summary_title.setText(getMonth(dataBirthday)+"月总体运势");
                            month_summary_tv.setText(mBean.xingzuo.month.general_txt);
                        }
                        if(TextUtils.isEmpty(mBean.xingzuo.month.work_txt)){
                            month_work_title.setVisibility(View.GONE);
                            month_work_tv.setVisibility(View.GONE);
                        }else {
                            month_work_title.setText(getMonth(dataBirthday)+"月工作分析");
                            month_work_tv.setText(mBean.xingzuo.month.work_txt);
                        }
                        if(TextUtils.isEmpty(mBean.xingzuo.month.love_txt)){
                            month_love_title.setVisibility(View.GONE);
                            month_love_tv.setVisibility(View.GONE);
                        }else {
                            month_love_title.setText(getMonth(dataBirthday)+"月感情分析");
                            month_love_tv.setText(mBean.xingzuo.month.love_txt);
                        }
                    }


                }
            }
        });
        requestUtils.setShowLoader(false);
        requestUtils.execute();
    }

    private String getMonth(String dataBirthday){
        String[] mSplit = dataBirthday.split("-");
        String month = mSplit[1];
        if(month.contains("0")){
            return month.replace("0","");
        }
        return month;
    }


    @OnClick({R.id.base_title_back,R.id.person_yunshi_qiehuan,R.id.person_yunshi_year,R.id.person_yunshi_today
                ,R.id.person_yunshi_month})
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.base_title_back:
                finish();
                break;
            case R.id.person_yunshi_year:
                intent = new Intent(this, WebActivity.class);
                intent.putExtra(WebActivity.KEY_TITLE,mBean.jinnian.title);
                intent.putExtra(WebActivity.URL_KEY,mBean.jinnian.url);
                startActivity(intent);
                break;
            case R.id.person_yunshi_qiehuan:
                intent = new Intent(this, UserManagerActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.person_yunshi_today:
                day_ll.setVisibility(View.VISIBLE);
                month_ll.setVisibility(View.GONE);
                person_day.setBackgroundResource(R.mipmap.yunhsi_button_select);
                person_month.setBackgroundResource(R.mipmap.yunhsi_button_admin);
                break;
            case R.id.person_yunshi_month:
                day_ll.setVisibility(View.GONE);
                month_ll.setVisibility(View.VISIBLE);
                person_day.setBackgroundResource(R.mipmap.yunhsi_button_admin);
                person_month.setBackgroundResource(R.mipmap.yunhsi_button_select);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mSensorEventListener);
    }
}
