package com.ling.suandashi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ling.suandashi.R;
import com.ling.suandashi.adapter.UserManagerAdapter;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.base.DefaultListener;
import com.ling.suandashi.data.UserSession;
import com.ling.suandashi.data.entity.User;
import com.ling.suandashi.data.request.ContactListRequest;
import com.ling.suandashi.data.request.DeleteUserRequest;
import com.ling.suandashi.data.request.RequestData;
import com.ling.suandashi.data.request.tools.APIException;
import com.ling.suandashi.data.request.tools.ErrorTools;
import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.data.request.tools.ResponseListener;
import com.ling.suandashi.net.HttpRequestUtils;
import com.ling.suandashi.tools.DeleteDialog;
import com.ling.suandashi.view.RecycleViewDivider;
import com.ling.suandashi.view.SlideRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserManagerActivity extends BasicActivity{

    @BindView(R.id.base_title_title_tv)
    TextView title;
    @BindView(R.id.user_manager_root)
    RelativeLayout root;//用户管理界面
    @BindView(R.id.user_manager_nouser_rl)
    RelativeLayout no_user_rl;//暂无用户页
    @BindView(R.id.user_manager_rv)
    SlideRecyclerView mRecyclerView;//用户管理

    DeleteDialog mDeleteDialog;
    UserManagerAdapter adapter;
    RecycleViewDivider mDivider;
    List<User> mUserList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);
        ButterKnife.bind(this);
        title.setText("用户管理");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mDivider = new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL
                ,1, Color.parseColor("#20894009"));
        mRecyclerView.addItemDecoration(mDivider);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lodaData();
    }

    private void lodaData() {
        ContactListRequest request = new ContactListRequest(UserSession.getInstances().getValue(UserSession.USER_ID,""));
        HttpRequestUtils requestUtils = new HttpRequestUtils(this, request, new ResponseListener<List<User>>(){
            @Override
            public void onResponse(List<User> list) {
                if(list.size() > 0){
                    mRecyclerView.setVisibility(View.VISIBLE);
                    no_user_rl.setVisibility(View.GONE);
                    mUserList = list;
                    for(User user : mUserList){
                        if(user.getId() == UserSession.getInstances().getValue(UserSession.USER_SUB_ID,0)){//当前用户设置被选中
                            user.setSelect(true);
                        }
                    }
                    fillAdapter(mUserList);
                }else {
                    mRecyclerView.setVisibility(View.GONE);
                    no_user_rl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(RequestData params, RequestResult result) {
                ErrorTools.doError(UserManagerActivity.this,params,result.getMessage(),result.getStatus());
            }
            @Override
            public void onNetworkError(APIException error) {
                ErrorTools.doNetError(UserManagerActivity.this,error);
            }
        });
        requestUtils.execute();
    }

    private void fillAdapter(List<User> list) {
        adapter = new UserManagerAdapter(UserManagerActivity.this,list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new UserManagerAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View v, int positon, User data) {

                switch (v.getId()){
                    case R.id.user_manager_adapter_delete:
                        showDeleteDialog(positon,data);
                        break;
                    default:
                        UserSession.getInstances().saveUserUsuallyInfo(data);
                        finish();
                        break;
                }
            }
        });
    }

    private void showDeleteDialog(int positon, User data) {
        mDeleteDialog = new DeleteDialog(UserManagerActivity.this);
        mDeleteDialog.setDeleteDialogListener(new DeleteDialog.DeleteDialogListener() {
            @Override
            public void cancel() {
                mDeleteDialog.dismiss();
            }

            @Override
            public void confirm() {
                DeleteUserRequest request = new DeleteUserRequest(data.getId()+"");
                HttpRequestUtils requestUtils = new HttpRequestUtils(UserManagerActivity.this, request, new DefaultListener(UserManagerActivity.this) {
                    @Override
                    public void onResponse(Object obj) {
                        mDeleteDialog.dismiss();
                        mUserList.remove(positon);
                        if(mUserList.size() == 0){
                            mRecyclerView.setVisibility(View.GONE);
                            no_user_rl.setVisibility(View.VISIBLE);
                            UserSession.getInstances().deleteUserInfo();
                        }else {
                            //当删除了一个用户时候要重新刷新列表，并把选中状态当放第一个数据中
                            mUserList.get(0).setSelect(true);
                            UserSession.getInstances().saveUserUsuallyInfo(mUserList.get(0));
                        }

                        adapter.notifyDataSetChanged();
                        mRecyclerView.closeMenu();
                    }
                });
                requestUtils.execute();
            }
        });
        mDeleteDialog.show();
    }

    @OnClick({R.id.base_title_back ,R.id.user_manager_new_tv})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.user_manager_new_tv://新增用户
                Intent intent = new Intent(this, AddUserActivity.class);
                startActivity(intent);
                if(mUserList!= null && mUserList.size() == 0){//当用户没有的时候去添加新用户，返回的时候直接回用户页
                    finish();
                }
                break;
        }
    }


}
