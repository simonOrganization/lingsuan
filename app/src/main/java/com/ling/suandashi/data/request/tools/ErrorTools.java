package com.ling.suandashi.data.request.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ling.suandashi.LSApplication;
import com.ling.suandashi.LsService;
import com.ling.suandashi.R;
import com.ling.suandashi.data.request.RequestData;
import com.ling.suandashi.tools.DialogUtils;
import com.ling.suandashi.tools.LSLog;

import androidx.appcompat.app.AlertDialog;

/**
 * @author Imxu
 * @time 2019/7/27 11:53
 * @des ${TODO}
 */
public class ErrorTools {

    public static void doNetError(Context context,APIException error){
        int code = error.getException().getCode();
        String errorCode = error.getException().getErrorCode();
        String errorMsg = error.getException().getMessage();
        LSLog.d("NetError:==> " + error.getErrorCode() + "-" + code + "-" + errorCode + "-" + errorMsg);
        if (TextUtils.isEmpty(errorMsg)) {
            errorMsg = "请求接口失败 code-" + code+"  api code-" + errorCode;
        }
        if (context == null) {
            return;
        }
        String toastMsg = String.format(context.getString(R.string.server_internal_exception1), error.getErrorCode() + "- api code:" + error.getApiCode());
        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
    }

    public static void doError(Context context, RequestData params, String message, int error){
        String errorCode = "";
        if (params != null) {
            errorCode = params.getApiCode();
        }

        if (TextUtils.isEmpty(message)) {
            return;
        }
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(LsService.topDialogContext);
        errorDialog.setCancelable(false);
        errorDialog.setTitle("提示");
        if (params != null) {
            errorDialog.setMessage("灵算："+message + "(" + params.getApiCode() + ")");
        } else {
            errorDialog.setMessage("灵算："+message);
        }

        errorDialog.setNegativeButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Do nothing
            }
        });
        DialogUtils.getInstances().showDialog(errorDialog.create());
    }
}
