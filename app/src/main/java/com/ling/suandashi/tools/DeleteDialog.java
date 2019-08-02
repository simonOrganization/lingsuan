package com.ling.suandashi.tools;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ling.suandashi.R;

import androidx.annotation.NonNull;

/**
 * @author Imxu
 * @time 2019/7/24 15:53
 * @des ${TODO}
 */
public class DeleteDialog extends Dialog {

    ImageView delete;
    TextView cancel;
    TextView confirm;

    public DeleteDialog(@NonNull Context context) {
        super(context, R.style.delete_dialog_style);

        setCancelable(false);

        //加载布局文件
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.user_delete_dialog, null);

        delete = view.findViewById(R.id.user_delete_delete_iv);
        cancel = view.findViewById(R.id.user_delete_cancel_tv);
        confirm = view.findViewById(R.id.user_delete_confirm_tv);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.cancel();
                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.confirm();
                }
            }
        });
        setContentView(view); //dialog添加视图
    }

    public interface DeleteDialogListener{
        void  cancel();
        void  confirm();
    }

    DeleteDialogListener mListener;

    public void setDeleteDialogListener(DeleteDialogListener listener) {
        mListener = listener;
    }
}
