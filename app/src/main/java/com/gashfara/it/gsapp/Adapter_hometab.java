package com.gashfara.it.gsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class Adapter_hometab extends FragmentPagerAdapter {

    private String[] pageTitle = {"ホーム", "書庫", "ストック"};//タブのタイトル

    public Adapter_hometab(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return TextFragment.newInstance(1);
            case 1:
                return new Fragment_library();
            case 2:
                return new Fragment_stock();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageTitle.length;//タブ数
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitle[position]; //タブのタイトル
    }

    public Fragment findFragmentByPosition(ViewPager viewPager,
                                           int position) {
        return (Fragment) instantiateItem(viewPager, position);
    }
}
