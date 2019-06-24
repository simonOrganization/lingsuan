package com.ling.suandashi;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;


import com.ling.suandashi.tools.CommonDialogListener;
import com.ling.suandashi.tools.DialogUtils;

import androidx.annotation.Nullable;

/**
 * Created by SPW on 2018/2/27.
 */

public class LsService extends Service implements CommonDialogListener {

    private static Dialog commonDialog;
    public static Context topDialogContext;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DialogUtils.getInstances().setListener(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            if(commonDialog!=null&&commonDialog.isShowing()){
                commonDialog.cancel();
                commonDialog=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Dialog getCommonDialog(){
        return commonDialog;
    }


    private void showDialog(Dialog dialog){
        try{
            if(commonDialog!=null && commonDialog.isShowing()){
                return;
            }
            if(topDialogContext!=null){
                commonDialog = dialog;
                commonDialog.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void show(Dialog dialog) {
        showDialog(dialog);
    }

    @Override
    public void dismiss() {
        try{
            if(commonDialog!=null){
                commonDialog.dismiss();
                commonDialog=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
