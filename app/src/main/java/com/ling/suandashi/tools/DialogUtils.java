package com.ling.suandashi.tools;

import android.app.Dialog;

/**
 * Created by SPW on 2017/1/22.
 */
public class DialogUtils {

    private static DialogUtils instances;
    private static CommonDialogListener mListener;

    private DialogUtils() {

    }

    public void setListener(CommonDialogListener listener) {
        mListener = listener;
    }

    public static DialogUtils getInstances() {
        if (instances == null) {
            synchronized (DialogUtils.class) {
                if (instances == null) {
                    instances = new DialogUtils();
                }
            }
        }
        return instances;
    }


    public void showDialog(Dialog dialog) {
        if (mListener != null) {
            mListener.show(dialog);
        }
    }

    public void cancel() {
        if (mListener != null) {
            mListener.dismiss();
        }
    }

}
