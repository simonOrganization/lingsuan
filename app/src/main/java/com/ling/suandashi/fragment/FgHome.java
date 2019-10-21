package com.ling.suandashi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ling.suandashi.R;
import com.ling.suandashi.activity.AddUserActivity;
import com.ling.suandashi.activity.DreamActivity;
import com.ling.suandashi.activity.PersonYunShiActivity;
import com.ling.suandashi.activity.UserManagerActivity;
import com.ling.suandashi.activity.WebActivity;
import com.ling.suandashi.activity.XingZuoActivity;
import com.ling.suandashi.adapter.HomeViewPagerListAdapter;
import com.ling.suandashi.base.BaseFragment;
import com.ling.suandashi.base.DefaultListener;
import com.ling.suandashi.data.UserSession;
import com.ling.suandashi.data.entity.HomePageBean;
import com.ling.suandashi.data.entity.User;
import com.ling.suandashi.data.request.ContactListRequest;
import com.ling.suandashi.data.request.HomePageRequest;
import com.ling.suandashi.data.request.RequestData;
import com.ling.suandashi.data.request.tools.APIException;
import com.ling.suandashi.data.request.tools.ErrorTools;
import com.ling.suandashi.data.request.tools.RequestResult;
import com.ling.suandashi.data.request.tools.ResponseListener;
import com.ling.suandashi.net.HttpRequestUtils;
import com.ling.suandashi.tools.CommonUtils;
import com.ling.suandashi.tools.LSLog;
import com.ling.suandashi.view.RatingBar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Imxu
 * @time 2019/6/23 19:18
 * @des ${TODO}
 */
public class FgHome extends BaseFragment {

    @BindView(R.id.home_scrollview)
    NestedScrollView mScrollView;
    @BindView(R.id.home_title_rl)
    RelativeLayout title_rl;//标题展示，滑动后出现

    @BindView(R.id.home_no_internet_rl)
    RelativeLayout no_internet_rl;//没有网络时显示

    @BindView(R.id.home_login_rl)
    RelativeLayout no_login_root;//没有登录时布局
    @BindView(R.id.home_login_iv)
    ImageView no_login_iv;//没有登录时图片

    @BindView(R.id.home_data_root)
    LinearLayout home_root;//首页数据显示

    @BindView(R.id.home_login_login_root)
    LinearLayout login_root;//登录后个人信息布局
    @BindView(R.id.home_login_login_name)
    TextView userName;//用户姓名
    @BindView(R.id.home_login_login_birthday)
    TextView userBirthday;//用户生日

    @BindView(R.id.home_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.home_viewpager_point_ll)
    LinearLayout viewpager_ll;//viewpager的滑动小点容器

    @BindView(R.id.home_ai_img)
    ImageView ai_img;//公告图片

    @BindView(R.id.home_date_day)
    TextView date_today;
    @BindView(R.id.home_date_year)
    TextView date_year;
    @BindView(R.id.home_date_week)
    TextView date_week;
    @BindView(R.id.home_date_nongli)
    TextView date_nongli;
    @BindView(R.id.home_yi_tv)
    TextView yi_data;
    @BindView(R.id.home_ji_tv)
    TextView ji_data;

    @BindView(R.id.home_xingzuo_tv)
    TextView xingzuo_name;

    @BindView(R.id.home_yunshi_start_zh)
    RatingBar ratingBar_zh;//综合指数
    @BindView(R.id.home_yunshi_start_gz)
    RatingBar ratingBar_gz;//工作指数
    @BindView(R.id.home_yunshi_start_aq)
    RatingBar ratingBar_aq;//爱情指数
    @BindView(R.id.home_yunshi_start_lc)
    RatingBar ratingBar_lc;//理财指数

    @BindView(R.id.home_yunshi_tv_xz)
    TextView yunshi_xz;//速配星座
    @BindView(R.id.home_yunshi_tv_ys)
    TextView yunshi_ys;//幸运颜色
    @BindView(R.id.home_yunshi_tv_sz)
    TextView yunshi_sz;//幸运数字
    @BindView(R.id.home_yunshi_tv_all)
    TextView yunshi_all;//整体运势

    private User mUser;
    private boolean isFirstShow = true;
    private HomePageBean homePageBean;
    private float alpha = 0;

    Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        mUnbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showUserInfo();
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                alpha =(float)scrollY/CommonUtils.dip2px(120);
                title_rl.setAlpha(alpha);
            }
        });

        loadUserData();
        loadHomeData(isFirstShow);
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void onVisible() {
        super.onVisible();
        if (!isResumed()) {
            return;
        }
        loadUserData();
        loadHomeData(isFirstShow);
    }

    private void loadHomeData(boolean isFirst) {
        HomePageRequest request = new HomePageRequest(UserSession.getInstances().getValue(UserSession.USER_SUB_BIRTHDAY,""));
        HttpRequestUtils requestUtils = new HttpRequestUtils(getContext(), request, new DefaultListener(getContext()) {
            @Override
            public void onResponse(Object obj) {
                no_internet_rl.setVisibility(View.GONE);
                isFirstShow = false;
                if(obj instanceof HomePageBean){
                    homePageBean = (HomePageBean) obj;
                    if(homePageBean != null && homePageBean.module != null && homePageBean.module.size() > 0){
                        List<List<HomePageBean.HomeModule>> moduleList = new ArrayList<>();
                        List<HomePageBean.HomeModule> first = new ArrayList<>();
                        List<HomePageBean.HomeModule> second = new ArrayList<>();
                            for(int i = 0 ; i< homePageBean.module.size(); i++){
                                if(i < 8){
                                    first.add(homePageBean.module.get(i));
                                }else if(i > 7 && i < 16){
                                    second.add(homePageBean.module.get(i));
                                }
                            }
                        if(first.size() > 0){
                            moduleList.add(first);
                        }
                        if(second.size() > 0){
                            moduleList.add(second);
                        }
                        ListRecycleAdapter listRecycleAdapter = new ListRecycleAdapter(moduleList, new ItemClickListener() {
                            @Override
                            public void onItemClick(HomePageBean.HomeModule module) {
                                Intent intent = new Intent(getContext(), WebActivity.class);
                                intent.putExtra(WebActivity.KEY_TITLE,module.modelName);
                                intent.putExtra(WebActivity.URL_KEY,module.modelUrl);
                                getActivity().startActivity(intent);
                            }
                        });

                        mViewPager.setAdapter(listRecycleAdapter);

                        viewpager_ll.removeAllViews();
                        for(int i = 0; i < moduleList.size(); i++){
                            View view = new View(getContext());
                            view.setBackgroundResource(R.drawable.home_viewpager_list_noselect_bg);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(CommonUtils.dip2px(6),CommonUtils.dip2px(6));
                            params.leftMargin = CommonUtils.dip2px(5);
                            viewpager_ll.addView(view,params);
                            if(i == 0){
                                view.setBackgroundResource(R.drawable.home_viewpager_list_select_bg);
                            }
                        }

                        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }
                            @Override
                            public void onPageSelected(int position) {
                                for (int i = 0; i<moduleList.size(); i++){
                                    viewpager_ll.getChildAt(i).setBackgroundResource(R.drawable.home_viewpager_list_noselect_bg);
                                    if(i == position){
                                        viewpager_ll.getChildAt(i).setBackgroundResource(R.drawable.home_viewpager_list_select_bg);
                                    }
                                }
                            }
                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                    }
                    if(homePageBean != null && !TextUtils.isEmpty(homePageBean.aiImg)){
                        Glide.with(getContext()).load(homePageBean.aiImg).into(ai_img);
                    }

                    if(homePageBean != null && homePageBean.huangli != null){
                        date_year.setText(CommonUtils.timeToYearMonth(homePageBean.huangli.GregorianDateTime));
                        date_today.setText(CommonUtils.timeToToday(homePageBean.huangli.GregorianDateTime));
                        date_week.setText(CommonUtils.timeToWeek(homePageBean.huangli.GregorianDateTime));
                        date_nongli.setText(homePageBean.huangli.LMonth+homePageBean.huangli.LDay);
                        yi_data.setText(homePageBean.huangli.Yi.replace("."," "));
                        ji_data.setText(homePageBean.huangli.Ji.replace("."," "));
                    }
                    if(homePageBean != null && homePageBean.xingzuo != null){
                        dealXingzuoTitle(homePageBean.xingzuo.name);
                    }
                    if(homePageBean != null && homePageBean.xingzuo != null && homePageBean.xingzuo.day != null){
                        ratingBar_zh.setStar(homePageBean.xingzuo.day.summary_star);
                        ratingBar_aq.setStar(homePageBean.xingzuo.day.love_star);
                        ratingBar_gz.setStar(homePageBean.xingzuo.day.work_star);
                        ratingBar_lc.setStar(homePageBean.xingzuo.day.money_star);

                        yunshi_xz.setText(homePageBean.xingzuo.day.grxz);
                        yunshi_ys.setText(homePageBean.xingzuo.day.lucky_color);
                        yunshi_sz.setText(homePageBean.xingzuo.day.lucky_num+"");
                        yunshi_all.setText(homePageBean.xingzuo.day.general_txt);
                    }
                }
                home_root.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(RequestData params, RequestResult result) {
                super.onFailure(params, result);
                no_internet_rl.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNetworkError(APIException error) {
                super.onNetworkError(error);
                no_internet_rl.setVisibility(View.VISIBLE);
            }
        });
        requestUtils.setShowLoader(isFirst);
        requestUtils.execute();
    }

    private void dealXingzuoTitle(String name) {
        switch (name){
            case "白羊座":
                xingzuo_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_sheep,0,0,0);
                break;
            case "金牛座":
                xingzuo_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_jinniu,0,0,0);
                break;
            case "双子座":
                xingzuo_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_shuangzi,0,0,0);
                break;
            case "巨蟹座":
                xingzuo_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_juxie,0,0,0);
                break;
            case "狮子座":
                xingzuo_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_shizi,0,0,0);
                break;
            case "处女座":
                xingzuo_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_chunv,0,0,0);
                break;
            case "天秤座":
                xingzuo_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_tiancheng,0,0,0);
                break;
            case "天蝎座":
                xingzuo_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_tianxie,0,0,0);
                break;
            case "射手座":
                xingzuo_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_sheshou,0,0,0);
                break;
            case "摩羯座":
                xingzuo_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_moxie,0,0,0);
                break;
            case "水瓶座":
                xingzuo_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_shuiping,0,0,0);
                break;
            case "双鱼座":
                xingzuo_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_shuangyu,0,0,0);
                break;
        }
        xingzuo_name.setText(name);
    }

    /**
     * 用户展示信息
     */
    private void showUserInfo() {
        if(!TextUtils.isEmpty(UserSession.getInstances().getValue(UserSession.USER_SUB_NAME,""))){
            userName.setText(UserSession.getInstances().getValue(UserSession.USER_SUB_NAME,""));

            userBirthday.setText("阳历："+ CommonUtils.birthdayToDay(UserSession.getInstances().getValue(UserSession.USER_SUB_BIRTHDAY,""))
                    +" "+UserSession.getInstances().getValue(UserSession.USER_SUB_HOUR,"")+"时");
            no_login_root.setVisibility(View.GONE);
            no_login_iv.setVisibility(View.GONE);
            login_root.setVisibility(View.VISIBLE);
        }else {
            login_root.setVisibility(View.GONE);
            no_login_root.setVisibility(View.VISIBLE);
            no_login_iv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 用户展示信息
     */
    private void showUserInfo(User user){
        userName.setText(user.getName());
        userBirthday.setText("阳历："+CommonUtils.birthdayToDay(user.getBrithday())+" "+user.getHour()+"时");
        no_login_root.setVisibility(View.GONE);
        no_login_iv.setVisibility(View.GONE);
        login_root.setVisibility(View.VISIBLE);
    }

    private void loadUserData() {
        ContactListRequest request = new ContactListRequest(UserSession.getInstances().getValue(UserSession.USER_ID,""));
        HttpRequestUtils requestUtils = new HttpRequestUtils(getContext(), request, new ResponseListener<List<User>>(){
            @Override
            public void onResponse(List<User> list) {
                if(list.size() > 0){//有用户则显示
                    if(UserSession.getInstances().getValue(UserSession.USER_SUB_ID,0) > 0){//显示保存的用户信息
                        for(User user : list){
                            if(user.getId() == UserSession.getInstances().getValue(UserSession.USER_SUB_ID,0)){
                                mUser = user;
                                showUserInfo(user);
                                UserSession.getInstances().saveUserUsuallyInfo(mUser);
                            }
                        }
                    }else {
                        mUser = list.get(0);
                        UserSession.getInstances().saveUserUsuallyInfo(mUser);
                        showUserInfo(mUser);
                    }
                }else {
                    login_root.setVisibility(View.GONE);
                    no_login_root.setVisibility(View.VISIBLE);
                    no_login_iv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(RequestData params, RequestResult result) {
                ErrorTools.doError(getContext(),params,result.getMessage(),result.getStatus());
            }
            @Override
            public void onNetworkError(APIException error) {
                ErrorTools.doNetError(getContext(),error);
            }
        });
        requestUtils.setShowLoader(false);
        requestUtils.execute();
    }

    @OnClick({R.id.home_login_rl,R.id.home_login_iv,R.id.home_login_login_qiehuan,R.id.home_ai_img
                ,R.id.home_no_internet_iv,R.id.home_login_yunshi,R.id.home_login_mingpan,R.id.home_xingzuo_more})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.home_login_rl:
            case R.id.home_login_iv:
                intent = new Intent(getContext(), AddUserActivity.class);
                startActivity(intent);
                break;
            case R.id.home_login_login_qiehuan:
                intent = new Intent(getContext(), UserManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.home_login_yunshi://个人运势
                intent = new Intent(getContext(), PersonYunShiActivity.class);
                intent.putExtra(PersonYunShiActivity.usrName,mUser.getName());
                intent.putExtra(PersonYunShiActivity.usrBirthday,"阳历："+CommonUtils.birthdayToDay(mUser.getBrithday())+" "+mUser.getHour()+"时");
                intent.putExtra(PersonYunShiActivity.dataBirthday,mUser.getBrithday());
                startActivity(intent);
                break;
            case R.id.home_login_mingpan://周公解梦
                intent = new Intent(getContext(), DreamActivity.class);
                startActivity(intent);
                break;
            case R.id.home_xingzuo_more://星座更多
                intent = new Intent(getContext(), XingZuoActivity.class);
                intent.putExtra("xingzuo",homePageBean.xingzuo.name);
                startActivity(intent);
                break;
            case R.id.home_ai_img://AI全面预测跳转
                intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra(WebActivity.URL_KEY,homePageBean.ai);
                getActivity().startActivity(intent);
                break;
            case R.id.home_no_internet_iv://没有网络
                loadUserData();
                loadHomeData(isFirstShow);
                break;
        }
    }

    class ListRecycleAdapter extends PagerAdapter{
        List<List<HomePageBean.HomeModule>> datas;
        ItemClickListener mListener;
        public ListRecycleAdapter(List<List<HomePageBean.HomeModule>> moduleList , ItemClickListener listener) {
            datas = moduleList;
            mListener = listener;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = View.inflate(getContext(),R.layout.home_viewpager_view,null);
            RecyclerView recyclerView = view.findViewById(R.id.home_viewpager_view_rv);
            HomeViewPagerListAdapter adapter = new HomeViewPagerListAdapter(getContext(),datas.get(position));
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(),4);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new HomeViewPagerListAdapter.MyItemClickListener() {
                @Override
                public void onItemClick(View v, int positon, HomePageBean.HomeModule data) {
                    if(mListener != null){
                        mListener.onItemClick(data);
                    }
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public interface ItemClickListener{
        void onItemClick(HomePageBean.HomeModule module);
    }
}
