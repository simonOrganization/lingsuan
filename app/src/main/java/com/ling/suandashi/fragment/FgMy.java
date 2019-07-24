package com.ling.suandashi.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.bumptech.glide.Glide;
import com.cclx.mobile.permission.OnConsumerPermissionListener;
import com.cclx.mobile.permission.OnDenyPermissionListener;
import com.cclx.mobile.permission.PermissionUtils;
import com.ling.suandashi.LSApplication;
import com.ling.suandashi.R;
import com.ling.suandashi.activity.AgreementActivity;
import com.ling.suandashi.activity.LoginActivity;
import com.ling.suandashi.activity.SecretActivity;
import com.ling.suandashi.activity.UserManagerActivity;
import com.ling.suandashi.activity.VersionActivity;
import com.ling.suandashi.base.BaseFragment;
import com.ling.suandashi.tools.DialogUtils;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Imxu
 * @time 2019/6/23 19:18
 * @des ${TODO}
 */
public class FgMy extends BaseFragment {

    Long cacheSize;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        ButterKnife.bind(this,view);
        return view;
    }


    @OnClick({R.id.version_cache_rl,R.id.version_version_rl,R.id.version_kefu_rl,R.id.version_evaluate_rl})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.version_cache_rl:
                DialogUtils.getInstances().show("清除缓存", "将删除"+getCacheSize()+"缓存的图片和系统预填信息", "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCache();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.version_version_rl:
                Intent intent = new Intent(getContext(), VersionActivity.class);
                startActivity(intent);
                break;
            case R.id.version_kefu_rl:
                //意见反馈
                grantCamera();
                break;
            case R.id.version_evaluate_rl:
                Intent intent1 = new Intent(getContext(), LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void grantCamera() {
        PermissionUtils.getPermissions((FragmentActivity) getContext(), new OnConsumerPermissionListener() {
            @Override
            public void onAllowed(String permissionName, String permissionDesc) {
                //意见反馈
                try {
                    FeedbackAPI.openFeedbackActivity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new OnDenyPermissionListener() {
            @Override
            public void onDeny(String permissionName, String permissionDesc) {
                PermissionUtils.showPromptDialog(LSApplication.GlobalContext,permissionDesc);
            }
        },android.Manifest.permission.CAMERA);
    }

    private long calculateCacheFileSize() {
        long length = 0L;
        try {
            String cachePath = Glide.getPhotoCacheDir(LSApplication.GlobalContext).getPath();
            File cacheDir1 = new File(cachePath);
            if (cacheDir1 != null) {
                length += getFileOrDirSize(cacheDir1);
            }
        } catch (Exception e) {

        }
        return length;
    }

    public static long getFileOrDirSize(File file) {
        if (!file.exists()) return 0;
        if (!file.isDirectory()) return file.length();
        long length = 0;
        File[] list = file.listFiles();
        if (list != null) { // 文件夹被删除时, 子文件正在被写入, 文件属性异常返回null.
            for (File item : list) {
                length += getFileOrDirSize(item);
            }
        }

        return length;
    }

    private String getCacheSize() {
        String result = "";
        cacheSize = calculateCacheFileSize();
        long oneKB = 1024;
        long oneMB = 1024 * 1024;
        long oneGB = 1024 * 1024 * 1024;
        if (cacheSize == 0) {
            return "";
        } else if (cacheSize > 0 && cacheSize < oneKB) {
            return "1K";
        } else if (cacheSize > oneKB && cacheSize < oneMB) {
            long num = cacheSize / oneKB;
            return num + "K";
        } else if (cacheSize > oneMB && cacheSize < oneGB) {
            long num = cacheSize / oneMB;
            return num + "M";
        } else if (cacheSize > oneGB) {
            long num = cacheSize / oneGB;
            return num + "G";
        }
        return result;
    }
    public static void deleteCache() {
        try {
            String cachePath = Glide.getPhotoCacheDir(LSApplication.GlobalContext).getPath();
            File cacheDir1 = new File(cachePath);
            if (cacheDir1.exists()) {
                delete(cacheDir1);
            }
        } catch (Exception e) {

        }
    }
    public static void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File tmpFile = files[i];
                if (tmpFile.isDirectory()) {
                    delete(tmpFile);
                } else {
                    tmpFile.delete();
                }
            }
        }
    }
}
