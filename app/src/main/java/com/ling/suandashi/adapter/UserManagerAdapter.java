package com.ling.suandashi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ling.suandashi.R;
import com.ling.suandashi.data.entity.User;

import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Imxu
 * @time 2019/7/21 19:18
 * @des ${TODO}
 */

public class UserManagerAdapter extends RecyclerView.Adapter<UserManagerAdapter.MyHolder> {
    private Context mContext;
    private MyItemClickListener mItemClickListener = null;
    private List<User> mDatas;

    public interface MyItemClickListener {
        void onItemClick(View v, int positon, User data);
    }

    public UserManagerAdapter(Context context, List<User> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_manager_adapter, parent, false);
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

    public void addDatas(List<User> data) {
        mDatas.addAll(data);
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        TextView name;
        TextView birthday;
        ImageView image;//选择图片
        ImageView delete;

        public MyHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            name = itemView.findViewById(R.id.user_manager_adapter_name);
            birthday = itemView.findViewById(R.id.user_manager_adapter_time);
            image = itemView.findViewById(R.id.user_manager_adapter_view);
            delete = itemView.findViewById(R.id.user_manager_adapter_delete);
            delete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition(), mDatas.get(getAdapterPosition()));
            }
        }

        public void setRefreshData(User bean, int position) {
            name.setText(bean.getName());
            birthday.setText(bean.getBrithday());
            if(bean.isSelect()){
                image.setVisibility(View.VISIBLE);
            }else {
                image.setVisibility(View.INVISIBLE);
            }
        }
    }
}
