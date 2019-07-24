package com.ling.suandashi.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.ling.suandashi.LsService;


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

    public void show(String title, String message, String positive, String negative, DialogInterface.OnClickListener okClick, DialogInterface.OnClickListener cancleClick){
        AlertDialog.Builder dialog = new AlertDialog.Builder(LsService.topDialogContext);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton(positive, okClick);
        dialog.setNegativeButton(negative, cancleClick);
        showDialog(dialog.create());
    }

}
