package com.ling.suandashi;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

import android.os.Bundle;

import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.fragment.FgHome;
import com.ling.suandashi.fragment.FgMiddle;
import com.ling.suandashi.fragment.FgMy;
import com.ling.suandashi.tools.CommonUtils;
import com.ling.suandashi.view.NoScrollViewPager;

public class MainActivity extends BasicActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.container)
    NoScrollViewPager mViewPager;

    private FgHome mFgHome;
    private FgMiddle mMiddle;
    private FgMy mMy;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAdapterContent();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setScrollble(false);
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

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
