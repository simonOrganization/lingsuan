package com.ling.suandashi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ling.suandashi.R;
import com.ling.suandashi.data.entity.HomePageBean;
import com.ling.suandashi.data.entity.ModuleBean;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Imxu
 * @time 2019/7/21 19:18
 * @des ${TODO}
 */

public class MiddleModuleAdapter extends RecyclerView.Adapter<MiddleModuleAdapter.MyHolder> {
    private Context mContext;
    private MyItemClickListener mItemClickListener = null;
    private List<ModuleBean> mDatas;

    public interface MyItemClickListener {
        void onItemClick(View v, int positon, ModuleBean.Module data);
    }

    public MiddleModuleAdapter(Context context, List<ModuleBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.middle_module_adapter, parent, false);
        MyHolder myHolder = new MyHolder(view, mItemClickListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.setRefreshData(mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addDatas(List<ModuleBean> data) {
        mDatas.addAll(data);
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        ImageView mImageView;
        RecyclerView mRecyclerView;//选择图片

        public MyHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            mImageView = itemView.findViewById(R.id.middle_adapter_iv);
            mRecyclerView = itemView.findViewById(R.id.middle_adapter_rv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        public void setRefreshData(ModuleBean bean, int position) {
            if(bean.cat_id == 1){
                mImageView.setImageResource(R.mipmap.test_bazi);
            }else if(bean.cat_id == 2){
                mImageView.setImageResource(R.mipmap.test_hunlian);
            }else if(bean.cat_id == 3){
                mImageView.setImageResource(R.mipmap.test_caiyun);
            }else if(bean.cat_id == 4){
                mImageView.setImageResource(R.mipmap.test_xuanji);
            }else {
                mImageView.setImageResource(R.mipmap.test_jixiong);
            }
            MiddleModuleListAdapter adapter = new MiddleModuleListAdapter(mContext,bean.modelList);
            GridLayoutManager layoutManager = new GridLayoutManager(mContext,3);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new MiddleModuleListAdapter.MyItemClickListener() {
                @Override
                public void onItemClick(View v, int positon, ModuleBean.Module data) {
                    if (mListener != null) {
                        mListener.onItemClick(v, getAdapterPosition(), data);
                    }
                }
            });
        }
    }
}
