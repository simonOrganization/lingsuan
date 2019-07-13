package com.ling.suandashi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.cclx.mobile.permission.OnConsumerPermissionListener;
import com.cclx.mobile.permission.OnDenyPermissionListener;
import com.cclx.mobile.permission.PermissionUtils;
import com.ling.suandashi.LSApplication;
import com.ling.suandashi.R;
import com.ling.suandashi.activity.AgreementActivity;
import com.ling.suandashi.activity.SecretActivity;
import com.ling.suandashi.activity.VersionActivity;
import com.ling.suandashi.base.BaseFragment;

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
}
