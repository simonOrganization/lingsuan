package com.ling.suandashi;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.multidex.MultiDexApplication;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.ling.suandashi.data.Constants;
import com.ling.suandashi.tools.DialogUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Imxu
 * @time 2019/6/22 16:15
 * @des ${TODO}
 */
public class LSApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks{

    public static LSApplication GlobalContext;
    /**
     * 所有activity管理
     */
    private final static List<Activity> activityList = new LinkedList<>();
    /**
     * 正在运行的activity
     */
    private final List<Activity> runningActivity = new LinkedList<>();

    /**
     * 单例模式中获取唯一的app实例
     */
    public static LSApplication getInstance() {
        return GlobalContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalContext = this;

        //初始化全局弹窗
        initCommonDialog();

        FeedbackAPI.init(this, Constants.ALIAS_BAICHUAN_KEY, Constants.ALIPAY_APP_SECRET); //初始化意见反馈sdk
        FeedbackAPI.setBackIcon(R.mipmap.nav_back);
    }

    /**
     * 只回收activity
     */
    public void quite() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
    /**
     * 遍历所有Activity并finish
     * Created by ZHZEPHI at 2015年1月9日 下午12:26:32
     */
    public void exit() {
        quite();
        System.exit(0);
    }

    private void initCommonDialog(){
        try {
            Intent dialogservice = new Intent(this, LsService.class);
            startService(dialogservice);
        } catch (Exception e) {
        }
    }

    /**
     * APP是否处于前台唤醒状态
     *
     * @return
     */
    public static boolean isAppOnForeground() {
        try{
            ActivityManager activityManager = (ActivityManager) LSApplication.GlobalContext.getSystemService(Context.ACTIVITY_SERVICE);
            String packageName =  LSApplication.GlobalContext.getPackageName();
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                    .getRunningAppProcesses();
            if (appProcesses == null)
                return false;

            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(packageName)
                        && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                }
            }
        }catch (Exception e){
        }
        return false;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        activityList.add(activity);
        if (activity.getParent() != null) {
            LsService.topDialogContext = activity.getParent();
        } else {
            LsService.topDialogContext = activity;
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        runningActivity.add(activity);
        if (activity.getParent() != null) {
            LsService.topDialogContext = activity.getParent();
        } else {
            LsService.topDialogContext = activity;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity.getParent() != null) {
            LsService.topDialogContext = activity.getParent();
        } else {
            LsService.topDialogContext = activity;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        DialogUtils.getInstances().cancel();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        runningActivity.remove(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 获取应用当前状态信息
     *
     * @return 0：后台 1：主界面 2：二级界面
     */
    public Integer getForgetInfo() {
        if (runningActivity.size() <= 0) {
            //没有activity说明在后台
            return 0;
        }
        if (runningActivity.get(0) instanceof MainActivity) {
            //如果activity是MainActivity说明是在主界面
            return 1;
        }
        //其他情况是在二级界面
        return 2;
    }

    public Activity getTopActivity(){
        if (runningActivity != null && runningActivity.size() > 0) {
            return runningActivity.get(runningActivity.size()-1);
        }
        return null;
    }

    /**
     * 是否还有正在运行的activity
     * @return
     */
    public static boolean hasAliveActivity(){
        if(activityList==null || activityList.size()==0){
            return false;
        }
        return true;
    }
}
