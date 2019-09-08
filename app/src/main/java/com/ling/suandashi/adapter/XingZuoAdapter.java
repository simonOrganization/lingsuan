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

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Imxu
 * @time 2019/7/21 19:18
 * @des ${TODO}
 */

public class XingZuoAdapter extends RecyclerView.Adapter<XingZuoAdapter.MyHolder> {
    private Context mContext;
    private MyItemClickListener mItemClickListener = null;
    private List<String> mDatas;

    public interface MyItemClickListener {
        void onItemClick(View v, int positon, String data);
    }

    public XingZuoAdapter(Context context, List<String> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
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

    public void addDatas(List<String> data) {
        mDatas.addAll(data);
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        TextView name;
        TextView time;
        ImageView image;//选择图片

        public MyHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            name = itemView.findViewById(R.id.xingzuo_adapter_name);
            time = itemView.findViewById(R.id.xingzuo_adapter_time);
            image = itemView.findViewById(R.id.xingzuo_adapter_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition(), mDatas.get(getAdapterPosition()));
            }
        }

        public void setRefreshData(String bean, int position) {
            if(position == 0){
                image.setImageResource(R.mipmap.xingzuo_baiyang);
                time.setText("3.21-4.19");
            }else if(position == 1){
                image.setImageResource(R.mipmap.xingzuo_jinniu);
                time.setText("4.20-5.20");
            }else if(position == 2){
                image.setImageResource(R.mipmap.xingzuo_shuangzi);
                time.setText("5.21-6.21");
            }else if(position == 3){
                image.setImageResource(R.mipmap.xingzuo_juxie);
                time.setText("6.22-7.22");
            }else if(position == 4){
                image.setImageResource(R.mipmap.xingzuo_shizi);
                time.setText("7.23-8.22");
            }else if(position == 5){
                image.setImageResource(R.mipmap.xingzuo_chunv);
                time.setText("8.23-9.22");
            }else if(position == 6){
                image.setImageResource(R.mipmap.xingzuo_tiancheng);
                time.setText("9.23-10.23");
            }else if(position == 7){
                image.setImageResource(R.mipmap.xingzuo_tianxie);
                time.setText("10.24-11.22");
            }else if(position == 8){
                image.setImageResource(R.mipmap.xingzuo_sheshou);
                time.setText("11.23-12.21");
            }else if(position == 9){
                image.setImageResource(R.mipmap.xingzuo_moxie);
                time.setText("12.22-1.19");
            }else if(position == 10){
                image.setImageResource(R.mipmap.xingzuo_shuiping);
                time.setText("1.20-2.18");
            }else{
                image.setImageResource(R.mipmap.xingzuo_shuangyu);
                time.setText("2.19-3.20");
            }
            name.setText(bean);
        }
    }
}
