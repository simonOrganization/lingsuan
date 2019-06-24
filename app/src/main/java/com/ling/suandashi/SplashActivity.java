package com.ling.suandashi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.data.UserSession;
import com.ling.suandashi.tools.MatcherUtils;
import com.ling.suandashi.tools.PermissionRes;
import com.ling.suandashi.tools.PermissionUtils;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SplashActivity extends BasicActivity {

    //引导图片资源
    private static final int[] pics = {R.mipmap.hug_01, R.mipmap.hug_02, R.mipmap.hug_03};
    ViewPager viewPager;
    float  actionDownX;
    float  actionUpX;

    /**
     * 进入登录界面
     */
    private final Handler finishHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //记录首次运行标
            String versionInfo = BuildConfig.VERSION_NAME;
            UserSession.getInstances(SplashActivity.this).saveValue("version", versionInfo);
            gotoLoginNext();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
    }

    private void initViews(){
        viewPager = findViewById(R.id.loading_viewpage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init(); //初始化加载
    }
    /**
     * 初始化加载
     */
    private void init() {
        viewPager.setVisibility(View.GONE);
        grantStorage();
    }
    /**
     * 授权获取手机信息权限
     */
    private void grantPhone() {
        MPermissions.requestPermissions(SplashActivity.this, PermissionRes.READ_PHONE_STATE, android.Manifest.permission.READ_PHONE_STATE);
    }

    @PermissionGrant(PermissionRes.READ_PHONE_STATE)
    public void requestPhoneSuccess() {
        grantStorage();
    }

    @PermissionDenied(PermissionRes.READ_PHONE_STATE)
    public void requestPhoneFailed() {
        PermissionUtils.getIns().showSettingDialog(this, android.Manifest.permission.READ_PHONE_STATE, new PermissionUtils.CallBackReListener() {
            @Override
            public void onReCall() {
                grantPhone();
            }
        });
    }

    /**
     * 授权读取存储空间权限
     */
    private void grantStorage() {
        MPermissions.requestPermissions(SplashActivity.this, PermissionRes.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @PermissionGrant(PermissionRes.WRITE_EXTERNAL_STORAGE)
    public void requestSdcardSuccess() {
        gotoSetp();
    }

    @PermissionDenied(PermissionRes.WRITE_EXTERNAL_STORAGE)
    public void requestSdcardFailed() {
        PermissionUtils.getIns().showSettingDialog(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionUtils.CallBackReListener() {
            @Override
            public void onReCall() {
                grantStorage();
            }
        });
    }

    private void gotoSetp() {
        /*
         * 此处进行首次加载判断
         * 1. 如果是第一次加载则显示引导页
         * 2. 如果不是首次进入，则直接进入登录界面
         */
        if (iSFirst() || isNewVersion()) {
            viewPager.setVisibility(View.VISIBLE);
            UserSession.getInstances(this).saveValue("isFirst", "1");

            final List<View> views = new ArrayList<>();
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            for (int i = 0; i < pics.length; i++) {
                ImageView iv = new ImageView(this);
                iv.setLayoutParams(mParams);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setBackgroundResource(pics[i]);
                views.add(iv);
            }
            views.get(views.size() - 1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //最后一张图片点击直接进入系统
                    finishHandler.sendEmptyMessage(0);
                }
            });
            //设置adapters
            AdPageAdapter aAdapter = new AdPageAdapter(views);
            viewPager.setAdapter(aAdapter);
        } else {
            // 是否登录，进入登录或者主界面
            finishHandler.sendEmptyMessage(0);
        }

        viewPager.setOnTouchListener((View v, MotionEvent event)->{
            if(viewPager.getCurrentItem()==viewPager.getAdapter().getCount()-1){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        actionDownX =  event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        actionUpX = event.getX();
                        if(actionDownX-actionUpX>150){
                            finishHandler.sendEmptyMessage(0);
                        }
                        break;
                }
            }
            return false;
        });
    }

    /**
     * 进入下一个操作，进入登录界面
     */
    private void gotoLoginNext() {
        //检查用户注册状态
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finishHandler.removeCallbacksAndMessages(null);
        finish();
    }

    /**
     * 判断是否第一次运行
     * Created by ZHZEPHI at 2015年2月3日 下午4:24:39
     * @return
     */
    private boolean iSFirst() {
        String isFirString = UserSession.getInstances(SplashActivity.this).getValue("isFirst", "");
        return MatcherUtils.isNull(isFirString);
    }
    /**
     * 判断是否是新版本
     * Created by ZHZEPHI at 2015年4月3日 下午3:45:04
     * @return
     */
    private boolean isNewVersion() {
        String versionInfo = BuildConfig.VERSION_NAME;
        return !UserSession.getInstances(SplashActivity.this).getValue("version", "").equals(versionInfo);
    }

    /**
     * 引导页
     */
    public class AdPageAdapter extends PagerAdapter {
        private final List<View> views;
        public AdPageAdapter(List<View> views) {
            this.views = views;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }
    }
}
