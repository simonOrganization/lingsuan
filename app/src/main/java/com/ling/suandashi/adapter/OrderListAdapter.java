package com.ling.suandashi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ling.suandashi.R;
import com.ling.suandashi.data.entity.ModuleBean;
import com.ling.suandashi.data.entity.OrderBean;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Imxu
 * @time 2019/7/21 19:18
 * @des ${TODO}
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyHolder> {
    private Context mContext;
    private MyItemClickListener mItemClickListener = null;
    private List<OrderBean.OrderList> mDatas;

    public interface MyItemClickListener {
        void onItemClick(View v, int positon, OrderBean.OrderList data);
    }

    public OrderListAdapter(Context context, List<OrderBean.OrderList> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_adapter, parent, false);
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

    public void addDatas(List<OrderBean.OrderList> data) {
        mDatas.addAll(data);
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        ImageView mImageView;
        TextView name;
        TextView money;
        TextView state;
        View mView;

        public MyHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            mImageView = itemView.findViewById(R.id.order_list_img);
            name = itemView.findViewById(R.id.order_list_name);
            money = itemView.findViewById(R.id.order_list_money);
            state = itemView.findViewById(R.id.order_list_state);
            mView = itemView.findViewById(R.id.order_list_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition(), mDatas.get(getAdapterPosition()));
            }
        }

        public void setRefreshData(OrderBean.OrderList bean, int position) {
            if(position == mDatas.size()-1){
               mView.setVisibility(View.GONE);
            }else {
                mView.setVisibility(View.VISIBLE);
            }
            name.setText(bean.title);
            money.setText("￥"+bean.sub);
            if(2 == bean.status){
                state.setText("已完成");
                state.setTextColor(Color.parseColor("#999999"));
            }else {
                state.setText("未完成");
                state.setTextColor(Color.parseColor("#E02D2D"));
            }
            Glide.with(mContext).load(bean.img).into(mImageView);
        }
    }
}
