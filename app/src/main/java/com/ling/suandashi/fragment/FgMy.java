package com.ling.suandashi.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.bumptech.glide.Glide;
import com.cclx.mobile.permission.OnConsumerPermissionListener;
import com.cclx.mobile.permission.OnDenyPermissionListener;
import com.cclx.mobile.permission.PermissionUtils;
import com.ling.suandashi.LSApplication;
import com.ling.suandashi.R;
import com.ling.suandashi.activity.AddUserActivity;
import com.ling.suandashi.activity.AgreementActivity;
import com.ling.suandashi.activity.LoginActivity;
import com.ling.suandashi.activity.SecretActivity;
import com.ling.suandashi.activity.UserManagerActivity;
import com.ling.suandashi.activity.VersionActivity;
import com.ling.suandashi.base.BaseFragment;
import com.ling.suandashi.base.DefaultListener;
import com.ling.suandashi.data.UserSession;
import com.ling.suandashi.data.entity.User;
import com.ling.suandashi.data.request.ContactListRequest;
import com.ling.suandashi.data.request.RequestData;
import com.ling.suandashi.data.request.tools.APIException;
import com.ling.suandashi.data.request.tools.ErrorTools;
import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.data.request.tools.ResponseListener;
import com.ling.suandashi.net.HttpRequestUtils;
import com.ling.suandashi.tools.CommonUtils;
import com.ling.suandashi.tools.DialogUtils;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Imxu
 * @time 2019/6/23 19:18
 * @des ${TODO}
 */
public class FgMy extends BaseFragment {


    @BindView(R.id.mine_order_rl)
    RelativeLayout order_rl;//点击进入我的订单
    @BindView(R.id.mine_login_rl)
    RelativeLayout nologin_root;//没有登录时用户信息
    @BindView(R.id.mine_login_login_root)
    RelativeLayout login_root;//登陆后用户信息
    @BindView(R.id.mine_login_login_name)
    TextView userName;//用户姓名
    @BindView(R.id.mine_login_login_birthday)
    TextView userBirthday;//用户生日
    
    User mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showUserInfo();
    }

    private void showUserInfo() {
        if(!TextUtils.isEmpty(UserSession.getInstances().getValue(UserSession.USER_SUB_NAME,""))){
            userName.setText(UserSession.getInstances().getValue(UserSession.USER_SUB_NAME,""));

            userBirthday.setText("阳历："+CommonUtils.birthdayToDay(UserSession.getInstances().getValue(UserSession.USER_SUB_BIRTHDAY,""))
                    +" "+UserSession.getInstances().getValue(UserSession.USER_SUB_HOUR,"")+"时");
            nologin_root.setVisibility(View.GONE);
            login_root.setVisibility(View.VISIBLE);
        }else {
            login_root.setVisibility(View.GONE);
            nologin_root.setVisibility(View.VISIBLE);
        }
    }

    private void showUserInfo(User user){
        userName.setText(user.getName());
        userBirthday.setText("阳历："+CommonUtils.birthdayToDay(user.getBrithday())+" "+user.getHour()+"时");
        nologin_root.setVisibility(View.GONE);
        login_root.setVisibility(View.VISIBLE);
    }
    @Override
    public void onResume() {
        super.onResume();
        loadUserData();
    }

    private void loadUserData() {
        ContactListRequest request = new ContactListRequest(UserSession.getInstances().getValue(UserSession.USER_ID,""));
        HttpRequestUtils requestUtils = new HttpRequestUtils(getContext(), request, new ResponseListener<List<User>>(){
            @Override
            public void onResponse(List<User> list) {
                if(list.size() > 0){//有用户则显示
                    if(UserSession.getInstances().getValue(UserSession.USER_SUB_ID,0) > 0){//显示保存的用户信息
                        for(User user : list){
                            if(user.getId() == UserSession.getInstances().getValue(UserSession.USER_SUB_ID,0)){
                                mUser = user;
                                showUserInfo(user);
                            }
                        }
                    }else {
                        mUser = list.get(0);
                        UserSession.getInstances().saveUserUsuallyInfo(mUser);
                        showUserInfo(mUser);
                    }
                }else {
                    login_root.setVisibility(View.GONE);
                    nologin_root.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(RequestData params, RequestResult result) {
                ErrorTools.doError(getContext(),params,result.getMessage(),result.getStatus());
            }
            @Override
            public void onNetworkError(APIException error) {
                ErrorTools.doNetError(getContext(),error);
            }
        });
        requestUtils.setShowLoader(false);
        requestUtils.execute();
    }

    @OnClick({R.id.version_cache_rl,R.id.version_version_rl,R.id.version_kefu_rl,R.id.version_evaluate_rl,R.id.mine_login_rl,
                R.id.mine_login_login_qiehuan})
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.version_cache_rl:
                DialogUtils.getInstances().show("清除缓存", "将删除"+ CommonUtils.getCacheSize()+"缓存的图片和系统预填信息", "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommonUtils.deleteCache();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.mine_login_login_qiehuan://切换用户
                intent = new Intent(getContext(), UserManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.version_version_rl://版本说明
                intent = new Intent(getContext(), VersionActivity.class);
                startActivity(intent);
                break;
            case R.id.version_kefu_rl://意见反馈
                grantCamera();
                break;
            case R.id.version_evaluate_rl://评价应用
                intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_login_rl://添加用户信息
                intent = new Intent(getContext(), AddUserActivity.class);
                startActivity(intent);
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
