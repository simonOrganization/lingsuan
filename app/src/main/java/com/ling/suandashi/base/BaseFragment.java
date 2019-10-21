package com.ling.suandashi.base;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import androidx.fragment.app.Fragment;

/**
 * @author Imxu
 * @time 2019/6/23 18:57
 * @des ${TODO}
 */
public class BaseFragment extends Fragment {

    protected BaseFragment mSourceFragment;

    @Override
    public void onStop() {
        super.onStop();
        collapseSoftInputMethod();
    }

    /**
     * 点击 back键的时候触发该方法，如果返回false，返回事件交给上层处理。如果返回true，则表示自己处理事件
     * @return
     */
    public boolean onBackPressed() {
        return false;
    }

    /**
     * 将fragment从后台堆栈中弹出,  (模拟用户按下BACK 命令).
     */
    public void finish() {
        collapseSoftInputMethod();
        //将fragment从后台堆栈中弹出,  (模拟用户按下BACK 命令).
        if (getFragmentManager() == null) return;
        getFragmentManager().popBackStack();
        Fragment fragment = this.geSourceFragment();
        if (fragment != null && fragment instanceof BaseFragment) {
            fragment.onResume();
        }
        ((BasicActivity) getActivity()).removeFragment(this);
    }

    public BaseFragment geSourceFragment() {
        return mSourceFragment;
    }

    /**
     * 展示软键盘
     */
    public void showSoftInputMethod(EditText inputText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(inputText, InputMethodManager.SHOW_IMPLICIT);
    }

    protected void hideInputMethod(View view) {
        InputMethodManager m = (InputMethodManager) this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        m.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 收起软键盘
     */
    public void collapseSoftInputMethod() {
        if (getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null && getView() != null)
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }

    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
            onVisible();
        }else{
            isVisible = false;
            onInVisible();
        }
    }

    public void onVisible(){

    }

    public void onInVisible(){

    }
}
