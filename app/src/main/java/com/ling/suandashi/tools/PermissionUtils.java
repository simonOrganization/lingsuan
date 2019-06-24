package com.ling.suandashi.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;

import com.ling.suandashi.R;


/**
 * 授权弹框
 */

public class PermissionUtils {

    private static PermissionUtils permissionUtils;

    private PermissionUtils() {
    }

    public static PermissionUtils getIns() {
        if (permissionUtils == null) {
            synchronized (PermissionUtils.class) {
                if (permissionUtils == null) {
                    permissionUtils = new PermissionUtils();
                }
            }
        }
        return permissionUtils;
    }


    public void showSettingDialog(final Activity activity, String permission, final CallBackReListener callBackReListener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.grant_fail_title);
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            dialog.setMessage(R.string.grant_tip_message2);
            dialog.setPositiveButton(R.string.grant_btn_setting, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                    intent.setData(uri);
                    activity.startActivity(intent);
                }
            });
        } else {
            dialog.setMessage(R.string.grant_tip_message);
            dialog.setPositiveButton(R.string.grant_fail_btn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callBackReListener != null) {
                        callBackReListener.onReCall();
                    }
                }
            });
        }
        DialogUtils.getInstances().showDialog(dialog.create());
    }

    public interface CallBackReListener {
        void onReCall();
    }

    public interface CallbackListener {
        void onRequest();

        void onCnacel();
    }


    public static boolean isGranted(String permission,Context context) {
        return !isMarshmallow() || isGranted_(permission,context);
    }

    private static boolean isGranted_(String permission,Context context) {
        int checkSelfPermission = ActivityCompat.checkSelfPermission(context, permission);
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }

    private static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
