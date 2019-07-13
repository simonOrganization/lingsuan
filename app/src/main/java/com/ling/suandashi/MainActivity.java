package com.ling.suandashi;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.fragment.FgHome;
import com.ling.suandashi.fragment.FgMiddle;
import com.ling.suandashi.fragment.FgMy;
import com.ling.suandashi.tools.CommonUtils;
import com.ling.suandashi.view.NoScrollViewPager;

public class MainActivity extends BasicActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.container)
    NoScrollViewPager mViewPager;

    @BindView(R.id.tab_yunshi_tv)
    TextView tab_yunshi_tv;
    @BindView(R.id.tab_cesuan_tv)
    TextView tab_cesuan_tv;
    @BindView(R.id.tab_mine_tv)
    TextView tab_mine_tv;

    @BindView(R.id.tab_iv_yunshi_big)
    ImageView yunshi_big;
    @BindView(R.id.tab_iv_yunshi_s)
    ImageView yunshi_s;
    @BindView(R.id.tab_iv_cesuan_big)
    ImageView cesuan_big;
    @BindView(R.id.tab_iv_cesuan_s)
    ImageView cesuan_s;
    @BindView(R.id.tab_iv_mine_big)
    ImageView mine_big;
    @BindView(R.id.tab_iv_mine_s)
    ImageView mine_s;

    private FgHome mFgHome;
    private FgMiddle mMiddle;
    private FgMy mMy;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAdapterContent();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setScrollble(false);
        bottomSelect(0);
    }

    private void initAdapterContent() {
        mFgHome = new FgHome();
        mMiddle = new FgMiddle();
        mMy = new FgMy();
        addFragment(mFgHome);
        addFragment(mMiddle);
        addFragment(mMy);
    }

    private long exitTime;

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() != 0) {
            doFragmentBack();
            mViewPager.setCurrentItem(0);
        } else {
            long times = System.currentTimeMillis();
            if ((times - exitTime) > 2000) {
                CommonUtils.showToast("再次点击退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomSelect(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void bottomSelect(int position) {
        resetBottomView();
        if(position == 0){
            tab_yunshi_tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            yunshi_big.setVisibility(View.VISIBLE);
            yunshi_s.setVisibility(View.GONE);
        }else if(position == 1){
            tab_cesuan_tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            cesuan_big.setVisibility(View.VISIBLE);
            cesuan_s.setVisibility(View.GONE);
        }else if(position == 2){
            tab_mine_tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            mine_big.setVisibility(View.VISIBLE);
            mine_s.setVisibility(View.GONE);
        }
    }

    private void resetBottomView() {
        tab_yunshi_tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tab_cesuan_tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tab_mine_tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        yunshi_big.setVisibility(View.GONE);
        cesuan_big.setVisibility(View.GONE);
        mine_big.setVisibility(View.GONE);

        yunshi_s.setVisibility(View.VISIBLE);
        cesuan_s.setVisibility(View.VISIBLE);
        mine_s.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.tab_yunshi_rl,R.id.tab_cesuan_rl,R.id.tab_mine_rl})
    void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_yunshi_rl:
                bottomSelect(0);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tab_cesuan_rl:
                bottomSelect(1);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tab_mine_rl:
                bottomSelect(2);
                mViewPager.setCurrentItem(2);
                break;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return mFgHome;
                }
                case 1: {
                    return mMiddle;
                }
                case 2: {
                    return mMy;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "发现";
                case 1:
                    return "目的地";
                case 2:
                    return "我的";
            }
            return null;
        }
    }
}
