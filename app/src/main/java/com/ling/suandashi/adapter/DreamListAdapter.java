package com.ling.suandashi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ling.suandashi.R;
import com.ling.suandashi.data.entity.ArticleBean;
import com.ling.suandashi.data.entity.User;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Imxu
 * @time 2019/7/21 19:18
 * @des ${TODO}
 */

public class DreamListAdapter extends RecyclerView.Adapter<DreamListAdapter.MyHolder> {
    private Context mContext;
    private MyItemClickListener mItemClickListener = null;
    private List<ArticleBean> mDatas;

    public interface MyItemClickListener {
        void onItemClick(View v, int positon, ArticleBean data);
    }

    public DreamListAdapter(Context context, List<ArticleBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dream_list_adapter, parent, false);
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

    public void addDatas(List<ArticleBean> data) {
        mDatas.addAll(data);
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        TextView title;
        TextView type;
        ImageView image;//选择图片

        public MyHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            title = itemView.findViewById(R.id.dreamlist_title);
            type = itemView.findViewById(R.id.dreamlist_type);
            image = itemView.findViewById(R.id.dreamlist_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition(), mDatas.get(getAdapterPosition()));
            }
        }

        public void setRefreshData(ArticleBean bean, int position) {
            title.setText(bean.title);
            type.setText(bean.type);
            Glide.with(mContext).load(bean.thumb).into(image);
        }
    }
}
