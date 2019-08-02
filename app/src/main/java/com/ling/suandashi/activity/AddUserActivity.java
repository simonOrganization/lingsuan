package com.ling.suandashi.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.ling.suandashi.R;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.base.DefaultListener;
import com.ling.suandashi.data.UserSession;
import com.ling.suandashi.data.request.AddAndEditUserRequest;
import com.ling.suandashi.net.HttpRequestUtils;
import com.ling.suandashi.tools.LSLog;
import com.ling.suandashi.view.LastInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddUserActivity extends BasicActivity{

    @BindView(R.id.base_title_title_tv)
    TextView title;
    @BindView(R.id.add_user_name_et)
    LastInputEditText name_et;
    @BindView(R.id.add_user_birthday_day)
    TextView birthday;
    @BindView(R.id.add_user_name_male_x)
    ImageView male;
    @BindView(R.id.add_user_name_male_o)
    ImageView female;

    private boolean isMale = true;
    private String user_hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);
        title.setText("新增用户");
    }

    @OnClick({R.id.base_title_back,R.id.add_user_name_rl,R.id.add_user_birthday_rl,R.id.add_user_tv,R.id.add_user_name_male_rl})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_title_back:
                name_et.clearFocus();
                collapseSoftInputMethod(view);
                finish();
                break;
            case R.id.add_user_name_rl://姓名
                showSoftInputMethod(name_et);
                break;
            case R.id.add_user_name_male_rl://性别
                name_et.clearFocus();
                collapseSoftInputMethod(view);
                if(isMale){
                    male.setImageResource(R.mipmap.sex_checkbox_admin);
                    female.setImageResource(R.mipmap.sex_checkbox_select);
                }else {
                    female.setImageResource(R.mipmap.sex_checkbox_admin);
                    male.setImageResource(R.mipmap.sex_checkbox_select);
                }
                isMale = !isMale;
                break;
            case R.id.add_user_birthday_rl://生日
                name_et.clearFocus();
                collapseSoftInputMethod(view);
                chooseTime();
                break;
            case R.id.add_user_tv://保存信息
                name_et.clearFocus();
                collapseSoftInputMethod(view);
                savaInfo();
                break;
        }

    }

    private void savaInfo() {
        if(TextUtils.isEmpty(name_et.getText().toString())){
            Toast.makeText(this, "请输入您的真实姓名以便测算", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(birthday.getText().toString())){
            Toast.makeText(this, "请输入您的生辰以便测算", Toast.LENGTH_SHORT).show();
        }

        AddAndEditUserRequest request = new AddAndEditUserRequest(UserSession.getInstances().getValue(UserSession.USER_ID,""),name_et.getText().toString()
            ,birthday.getText().toString(),user_hour,isMale,"");
        HttpRequestUtils requestUtils = new HttpRequestUtils(this, request, new DefaultListener(this) {
            @Override
            public void onResponse(Object obj) {
                finish();
            }
        });
        requestUtils.execute();
    }

    private void chooseTime() {
        DatePickDialog dialog = new DatePickDialog(this);
        dialog.setTitle("选择生辰");
        dialog.setType(DateType.TYPE_YMDH);
        dialog.setYearLimt(90);
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format2 = new SimpleDateFormat("HH");
                birthday.setText(format.format(date));
                user_hour = format2.format(date)+"时";
            }
        });
        dialog.show();
    }


    public void showSoftInputMethod(EditText inputText) {
        inputText.setFocusable(true);
        inputText.setFocusableInTouchMode(true);
        inputText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(inputText, InputMethodManager.SHOW_IMPLICIT);
    }

    public void collapseSoftInputMethod(View view) {
        try{
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
