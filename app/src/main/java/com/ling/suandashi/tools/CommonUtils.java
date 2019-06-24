package com.ling.suandashi.tools;

import android.widget.Toast;

import com.ling.suandashi.LSApplication;

/**
 * @author Imxu
 * @time 2019/6/23 19:41
 * @des ${TODO}
 */
public class CommonUtils {

    public static void showToast(String msg) {
        Toast.makeText(LSApplication.GlobalContext,msg,Toast.LENGTH_SHORT).show();
    }

}
