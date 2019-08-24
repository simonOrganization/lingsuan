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
import com.ling.suandashi.data.entity.User;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Imxu
 * @time 2019/7/21 19:18
 * @des ${TODO}
 */

public class HomeViewPagerListAdapter extends RecyclerView.Adapter<HomeViewPagerListAdapter.MyHolder> {
    private Context mContext;
    private MyItemClickListener mItemClickListener = null;
    private List<HomePageBean.HomeModule> mDatas;

    public interface MyItemClickListener {
        void onItemClick(View v, int positon, HomePageBean.HomeModule data);
    }

    public HomeViewPagerListAdapter(Context context, List<HomePageBean.HomeModule> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_viewpager_list_adapter, parent, false);
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

    public void addDatas(List<HomePageBean.HomeModule> data) {
        mDatas.addAll(data);
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        TextView name;
        ImageView image;//选择图片

        public MyHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            name = itemView.findViewById(R.id.home_viewpager_list_adapter_tv);
            image = itemView.findViewById(R.id.home_viewpager_list_adapter_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition(), mDatas.get(getAdapterPosition()));
            }
        }

        public void setRefreshData(HomePageBean.HomeModule bean, int position) {
            name.setText(bean.modelName);
            Glide.with(mContext).load(bean.modelPic).into(image);
        }
    }
}
