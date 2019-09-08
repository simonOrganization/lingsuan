package com.ling.suandashi.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ling.suandashi.R;
import com.ling.suandashi.adapter.XingZuoAdapter;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.base.DefaultListener;
import com.ling.suandashi.data.entity.XingZuoBean;
import com.ling.suandashi.data.request.RequestData;
import com.ling.suandashi.data.request.XingZuoRequest;
import com.ling.suandashi.data.request.tools.APIException;
import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.net.HttpRequestUtils;
import com.ling.suandashi.tools.LSLog;
import com.ling.suandashi.view.RatingBar;
import com.mengpeng.recyclerviewgallery.CarouselLayoutManager;
import com.mengpeng.recyclerviewgallery.CarouselZoomPostLayoutListener;
import com.mengpeng.recyclerviewgallery.CenterScrollListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XingZuoActivity extends BasicActivity{

    @BindView(R.id.base_title_title_tv)
    TextView title;

    @BindView(R.id.xingzuo_speed_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.xingzuo_yunshi_start_zh)
    RatingBar ratingBar_zh;//综合指数
    @BindView(R.id.xingzuo_yunshi_start_gz)
    RatingBar ratingBar_gz;//工作指数
    @BindView(R.id.xingzuo_yunshi_start_aq)
    RatingBar ratingBar_aq;//爱情指数
    @BindView(R.id.xingzuo_yunshi_start_lc)
    RatingBar ratingBar_lc;//理财指数

    @BindView(R.id.xingzuo_yunshi_tv_all)
    TextView yunshi_all;//整体运势
    @BindView(R.id.xingzuo_yunshi_tv_caiyun)
    TextView yunshi_caiyun;//财运分析
    @BindView(R.id.xingzuo_yunshi_caiyun_title)
    TextView caiyun_title;//财运标题

    @BindView(R.id.xingzuo_yunshi_today)
    TextView today_tv;
    @BindView(R.id.xingzuo_yunshi_tomorrow)
    TextView tomorrow_tv;
    @BindView(R.id.xingzuo_yunshi_week)
    TextView week_tv;
    @BindView(R.id.xingzuo_yunshi_month)
    TextView month_tv;
    @BindView(R.id.xingzuo_yunshi_year)
    TextView year_tv;

    private List<String> mList = new ArrayList<>();
    private String xingzuoName = "白羊座";
    private XingZuoBean mZuoBean;

    private int selectPos = 0;
    private boolean isStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xing_zuo);
        ButterKnife.bind(this);

        xingzuoName = getIntent().getStringExtra("xingzuo");
        title.setText("星座运势");

        //设置12星座假数据
        mList.add("白羊座");
        mList.add("金牛座");
        mList.add("双子座");
        mList.add("巨蟹座");
        mList.add("狮子座");
        mList.add("处女座");
        mList.add("天秤座");
        mList.add("天蝎座");
        mList.add("射手座");
        mList.add("摩羯座");
        mList.add("水瓶座");
        mList.add("双鱼座");

        /**
         * https://gitee.com/mengpeng920223/RecyclerView-Gallery
         */
        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new CenterScrollListener());

        XingZuoAdapter adapter = new XingZuoAdapter(this,mList);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    CarouselLayoutManager manager = (CarouselLayoutManager) recyclerView.getLayoutManager();
                    if(!isStop){
                        if(selectPos != manager.getCenterItemPosition()){
                            isStop = true;
                            loadData(mList.get(manager.getCenterItemPosition()));
                            selectPos = manager.getCenterItemPosition();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        if(!TextUtils.isEmpty(xingzuoName)){
            mRecyclerView.scrollToPosition(dealXingzuoPos(xingzuoName));
        }

        loadData(xingzuoName);
    }

    private void loadData(String name) {
        XingZuoRequest request = new XingZuoRequest(name);
        HttpRequestUtils requestUtils = new HttpRequestUtils(this, request, new DefaultListener(this) {
            @Override
            public void onResponse(Object obj) {
                if(obj instanceof XingZuoBean){
                    isStop = false;
                    mZuoBean = (XingZuoBean) obj;

                    selectPos = dealXingzuoPos(mZuoBean.name);

                    resetUI();
                    today_tv.setBackgroundResource(R.mipmap.xingzuo_button_select);
                    ratingBar_zh.setStar(mZuoBean.day.summary_star);
                    ratingBar_gz.setStar(mZuoBean.day.work_star);
                    ratingBar_aq.setStar(mZuoBean.day.love_star);
                    ratingBar_lc.setStar(mZuoBean.day.money_star);
                    yunshi_all.setText(mZuoBean.day.general_txt);
                    yunshi_caiyun.setText(mZuoBean.day.money_txt);
                }
            }
            @Override
            public void onFailure(RequestData params, RequestResult result) {
                super.onFailure(params, result);
                isStop = false;
            }

            @Override
            public void onNetworkError(APIException error) {
                super.onNetworkError(error);
                isStop = false;
            }
        });
        requestUtils.execute();
    }


    @OnClick({R.id.base_title_back,R.id.xingzuo_yunshi_today,R.id.xingzuo_yunshi_tomorrow,R.id.xingzuo_yunshi_week,R.id.xingzuo_yunshi_month
            ,R.id.xingzuo_yunshi_year})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_title_back:
                finish();
                break;
            case R.id.xingzuo_yunshi_today:
                resetUI();
                today_tv.setBackgroundResource(R.mipmap.xingzuo_button_select);
                ratingBar_zh.setStar(mZuoBean.day.summary_star);
                ratingBar_gz.setStar(mZuoBean.day.work_star);
                ratingBar_aq.setStar(mZuoBean.day.love_star);
                ratingBar_lc.setStar(mZuoBean.day.money_star);
                yunshi_all.setText(mZuoBean.day.general_txt);
                if(TextUtils.isEmpty(mZuoBean.day.money_txt)){
                    yunshi_caiyun.setVisibility(View.GONE);
                    caiyun_title.setVisibility(View.INVISIBLE);
                }else {
                    yunshi_caiyun.setVisibility(View.VISIBLE);
                    caiyun_title.setVisibility(View.VISIBLE);
                    yunshi_caiyun.setText(mZuoBean.day.money_txt);
                }
                break;
            case R.id.xingzuo_yunshi_tomorrow:
                resetUI();
                tomorrow_tv.setBackgroundResource(R.mipmap.xingzuo_button_select);
                ratingBar_zh.setStar(mZuoBean.tomorrow.summary_star);
                ratingBar_gz.setStar(mZuoBean.tomorrow.work_star);
                ratingBar_aq.setStar(mZuoBean.tomorrow.love_star);
                ratingBar_lc.setStar(mZuoBean.tomorrow.money_star);
                yunshi_all.setText(mZuoBean.tomorrow.general_txt);
                if(TextUtils.isEmpty(mZuoBean.tomorrow.money_txt)){
                    yunshi_caiyun.setVisibility(View.GONE);
                    caiyun_title.setVisibility(View.INVISIBLE);
                }else {
                    yunshi_caiyun.setVisibility(View.VISIBLE);
                    caiyun_title.setVisibility(View.VISIBLE);
                    yunshi_caiyun.setText(mZuoBean.tomorrow.money_txt);
                }
                break;
            case R.id.xingzuo_yunshi_week:
                resetUI();
                week_tv.setBackgroundResource(R.mipmap.xingzuo_button_select);
                ratingBar_zh.setStar(mZuoBean.week.summary_star);
                ratingBar_gz.setStar(mZuoBean.week.work_star);
                ratingBar_aq.setStar(mZuoBean.week.love_star);
                ratingBar_lc.setStar(mZuoBean.week.money_star);
                yunshi_all.setText(mZuoBean.week.general_txt);
                if(TextUtils.isEmpty(mZuoBean.week.money_txt)){
                    yunshi_caiyun.setVisibility(View.GONE);
                    caiyun_title.setVisibility(View.INVISIBLE);
                }else {
                    yunshi_caiyun.setVisibility(View.VISIBLE);
                    caiyun_title.setVisibility(View.VISIBLE);
                    yunshi_caiyun.setText(mZuoBean.week.money_txt);
                }
                break;
            case R.id.xingzuo_yunshi_month:
                resetUI();
                month_tv.setBackgroundResource(R.mipmap.xingzuo_button_select);
                ratingBar_zh.setStar(mZuoBean.month.summary_star);
                ratingBar_gz.setStar(mZuoBean.month.work_star);
                ratingBar_aq.setStar(mZuoBean.month.love_star);
                ratingBar_lc.setStar(mZuoBean.month.money_star);
                yunshi_all.setText(mZuoBean.month.general_txt);
                if(TextUtils.isEmpty(mZuoBean.month.money_txt)){
                    yunshi_caiyun.setVisibility(View.GONE);
                    caiyun_title.setVisibility(View.INVISIBLE);
                }else {
                    yunshi_caiyun.setVisibility(View.VISIBLE);
                    caiyun_title.setVisibility(View.VISIBLE);
                    yunshi_caiyun.setText(mZuoBean.month.money_txt);
                }
                break;
            case R.id.xingzuo_yunshi_year:
                resetUI();
                year_tv.setBackgroundResource(R.mipmap.xingzuo_button_select);
                ratingBar_zh.setStar(checkStar(mZuoBean.year.general_index));
                ratingBar_gz.setStar(checkStar(mZuoBean.year.work_index));
                ratingBar_aq.setStar(checkStar(mZuoBean.year.love_index));
                ratingBar_lc.setStar(checkStar(mZuoBean.year.money_index));

                yunshi_all.setText(mZuoBean.year.general_txt);
                if(TextUtils.isEmpty(mZuoBean.year.money_txt)){
                    yunshi_caiyun.setVisibility(View.GONE);
                    caiyun_title.setVisibility(View.INVISIBLE);
                }else {
                    yunshi_caiyun.setVisibility(View.VISIBLE);
                    caiyun_title.setVisibility(View.VISIBLE);
                    yunshi_caiyun.setText(mZuoBean.year.money_txt);
                }
                break;
        }
    }

    private float checkStar(String content) {
        if(content.contains("分")){
            try {
                String number = content.replace("分","");
                return Integer.parseInt(number)/20;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    private void resetUI() {
        today_tv.setBackgroundResource(R.mipmap.xingzuo_button_admin);
        tomorrow_tv.setBackgroundResource(R.mipmap.xingzuo_button_admin);
        week_tv.setBackgroundResource(R.mipmap.xingzuo_button_admin);
        month_tv.setBackgroundResource(R.mipmap.xingzuo_button_admin);
        year_tv.setBackgroundResource(R.mipmap.xingzuo_button_admin);
    }

    private int dealXingzuoPos(String name) {
        switch (name){
            case "白羊座":
                return 0;
            case "金牛座":
                return 1;
            case "双子座":
                return 2;
            case "巨蟹座":
                return 3;
            case "狮子座":
                return 4;
            case "处女座":
                return 5;
            case "天秤座":
                return 6;
            case "天蝎座":
                return 7;
            case "射手座":
                return 8;
            case "摩羯座":
                return 9;
            case "水瓶座":
                return 10;
            case "双鱼座":
                return 11;
        }
        return 0;
    }

}
