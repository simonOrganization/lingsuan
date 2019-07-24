package com.ling.suandashi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ling.suandashi.R;
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
    private List<String> mDatas;

    public interface MyItemClickListener {
        void onItemClick(View v, int positon, String data);
    }

    public UserManagerAdapter(Context context, List<String> datas) {
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

    public void addDatas(List<String> data) {
        mDatas.addAll(data);
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        TextView title;
        TextView delete;

        public MyHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            title = itemView.findViewById(R.id.tv_quantity);
            delete = itemView.findViewById(R.id.tv_delete);
            delete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition(), mDatas.get(getPosition()));
            }
        }

        public void setRefreshData(String bean, int position) {
            title.setText(bean);
        }
    }
}
