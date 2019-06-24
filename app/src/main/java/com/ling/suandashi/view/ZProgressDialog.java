/*
 * ZProgressDialog.java [V1.0.0]
 * classes:com.zongfi.zfutil.widget.ZProgressDialog
 * ZHZEPHI Create at 2015年2月4日 上午11:37:20
 */
package com.ling.suandashi.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ling.suandashi.R;


/**
 *
 */
public class ZProgressDialog extends Dialog {

    private final Context context;
    private final ImageView img;
    private final TextView txt;

    private AnimationDrawable mLoadingAinm;

    /**
     * @param context
     */
    public ZProgressDialog(Context context) {
        super(context, R.style.Z_progress_dialog);
        this.context = context;

        setCancelable(false); //dialog模态

        //加载布局文件
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.z_progress_dialog, null);
        img = (ImageView) view.findViewById(R.id.z_progress_dialog_img);
        txt = (TextView) view.findViewById(R.id.z_progress_dialog_txt);

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.progressbar);  //给图片添加动态效果
        if (img != null) {
            img.setAnimation(anim);
        }
        if (txt != null) {
            txt.setText("拼命加载中……");
        }
        setContentView(view); //dialog添加视图
    }

    /**
     * 设置动画帧图片的进度条
     *
     * @param context
     * @param resId
     */
    public ZProgressDialog(Context context, int resId) {
        super(context, R.style.Z_progress_dialog);
        this.context = context;

        setCancelable(false); //dialog模态

        //加载布局文件
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.z_progress_dialog, null);
        img = (ImageView) view.findViewById(R.id.z_progress_dialog_img);
        txt = (TextView) view.findViewById(R.id.z_progress_dialog_txt);

        img.setImageResource(resId);
        mLoadingAinm = (AnimationDrawable) img.getDrawable();
        img.post(new Runnable() {

            @Override
            public void run() {
                mLoadingAinm.start();
            }
        });

        txt.setText("拼命加载中...");
        setContentView(view); //dialog添加视图
    }

    public void setMessage(String msg) {
        txt.setText(msg);
    }

}
