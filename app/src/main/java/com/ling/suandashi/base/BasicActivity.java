package com.ling.suandashi.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ling.suandashi.LSApplication;
import com.ling.suandashi.view.ZProgressDialog;

import java.util.ArrayList;

public class BasicActivity extends AppCompatActivity implements IBaseView{

    public static LayoutInflater inflater;
    public static boolean isActive; //全局变量
    ZProgressDialog zProgressDialog;
    private ArrayList<BaseFragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(getApplicationContext());
    }

    @Override
    protected void onStop() {
        if (!LSApplication.isAppOnForeground()) {
            //app 进入后台
            isActive = false;//记录当前已经进入后台
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isActive) {
            //app 从后台唤醒，进入前台
            isActive = true;
        }
    }

    //是否需要关闭fragment
    protected void doFragmentBack() {
        if (mFragmentList != null && mFragmentList.size() > 0) {
            for (int i = mFragmentList.size() - 1; i > 0; i--) {
                BaseFragment fragment = mFragmentList.get(i);
                if (fragment != null) {//取最后非空
                    if (!fragment.onBackPressed()) {//子类不处理
                        fragment.finish();
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        doFragmentBack();
        super.onBackPressed();
    }


    @Override
    public void showLoading() {
        try {
            if (zProgressDialog == null || !zProgressDialog.isShowing()) {
                zProgressDialog = new ZProgressDialog(this);
                zProgressDialog.setCancelable(false);
                zProgressDialog.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void hideLoading() {
        try{
            if (zProgressDialog != null && zProgressDialog.isShowing()) {
                zProgressDialog.dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @return void    返回类型
     * @Title exitApp
     * @Description 退出程序
     */
    public void exitApp() {
        finish();
        System.exit(0);
    }

    public synchronized void addFragment(BaseFragment fragment) {
        mFragmentList.add(fragment);
    }

    public synchronized void removeFragment(BaseFragment fragment) {
        mFragmentList.remove(fragment);
    }

    public ArrayList<BaseFragment> getFragmentList() {
        return mFragmentList;
    }

    public void showSoftInputMethod(EditText inputText) {
        inputText.setFocusable(true);
        inputText.setFocusableInTouchMode(true);
        inputText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(inputText, InputMethodManager.SHOW_IMPLICIT);
    }

    public void collapseSoftInputMethod(View view) {
        try{
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
