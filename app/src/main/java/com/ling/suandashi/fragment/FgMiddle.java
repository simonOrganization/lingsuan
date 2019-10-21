package com.ling.suandashi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ling.suandashi.R;
import com.ling.suandashi.activity.WebActivity;
import com.ling.suandashi.adapter.MiddleModuleAdapter;
import com.ling.suandashi.base.BaseFragment;
import com.ling.suandashi.data.entity.ModuleBean;
import com.ling.suandashi.data.request.ModuleListRequest;
import com.ling.suandashi.data.request.RequestData;
import com.ling.suandashi.data.request.tools.APIException;
import com.ling.suandashi.data.request.tools.ErrorTools;
import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.data.request.tools.ResponseListener;
import com.ling.suandashi.net.HttpRequestUtils;
import com.ling.suandashi.tools.CommonUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Imxu
 * @time 2019/6/23 19:18
 * @des ${TODO}
 */
public class FgMiddle extends BaseFragment {

    @BindView(R.id.middle_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.middle_title_rl)
    RelativeLayout title_rl;//标题展示，滑动后出现
    @BindView(R.id.middle_scrollview)
    NestedScrollView mScrollView;

    MiddleModuleAdapter mAdapter;
    private float alpha = 0;
    Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_middle,container,false);
        mUnbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                alpha =(float)scrollY/ CommonUtils.dip2px(30);
                title_rl.setAlpha(alpha);
            }
        });
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void onVisible() {
        super.onVisible();
        if (!isResumed()) {
            return;
        }
        loadData();
    }

    private void loadData() {
        ModuleListRequest request = new ModuleListRequest();
        HttpRequestUtils requestUtils = new HttpRequestUtils(getContext(), request, new ResponseListener<List<ModuleBean>>() {
            @Override
            public void onResponse(List<ModuleBean> list) {
                mAdapter = new MiddleModuleAdapter(getContext(),list);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new MiddleModuleAdapter.MyItemClickListener() {
                    @Override
                    public void onItemClick(View v, int positon, ModuleBean.Module data) {
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra(WebActivity.URL_KEY,data.modelUrl);
                        intent.putExtra(WebActivity.KEY_TITLE,data.modelName);
                        getActivity().startActivity(intent);
                    }
                });
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
}
