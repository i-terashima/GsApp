package com.gashfara.it.gsapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnSampleListChangeListener {
    FragmentAdapter adapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        //pager.setOffscreenPageLimit(3);//タブ３つは破棄せず再利用
        adapter = new FragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager); //スワイプするとタブのステータスが変更
    }

    @Override
    public void onListSelectedChanged(String s) {
        TextFragment fragment = (TextFragment) adapter.findFragmentByPosition(pager, 1);
        fragment.setText(s);
        pager.setCurrentItem(1);

    }
}