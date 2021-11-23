package com.example.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.order.constant.Constant;
import com.example.order.entity.ItemSubType;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.stream.Collectors;

public class TabActivity extends AppCompatActivity {
    private List<ItemSubType> subTypes;

    private TabLayout mTabTl;
    private ViewPager mContentVp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        Intent intent = getIntent();
        int typeId = intent.getIntExtra(Constant.ITEM_TYPE_KEY, 0);
        subTypes = Constant.itemSubTypeList.parallelStream().filter(x -> {
            if (typeId == 0) {
                return true;
            } else {
                return x.getItemType() == typeId;
            }
        }).collect(Collectors.toList());
        mTabTl =  findViewById(R.id.tl_tab);
        mContentVp = findViewById(R.id.vp_content);
        initTab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initTab(){
        mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabTl.setTabTextColors(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.white));
        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white));
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(mContentVp);
        subTypes.forEach(st ->{
            TabLayout.Tab item = mTabTl.newTab();
            item.setText(st.getName());
            mTabTl.addTab(item);
        });
    }



}