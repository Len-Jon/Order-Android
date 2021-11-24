package com.example.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.order.constant.Constant;
import com.example.order.entity.ItemSubType;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.stream.Collectors;

public class TabActivity extends AppCompatActivity {
    private int typeId;
    private List<ItemSubType> subTypes;
    private List<Integer> subTypeIds;

    private TabLayout tabLayout;
    private ViewPager2 viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        init();
        initTab();
        initViewPager();


        new TabLayoutMediator(tabLayout, viewPager, true, (tab, position) -> tab.setText(subTypes.get(position).getName())).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void init() {
        tabLayout = findViewById(R.id.tl_tab);
        viewPager = findViewById(R.id.vp_content);

        Intent intent = getIntent();
        typeId = intent.getIntExtra(Constant.ITEM_TYPE_ID_KEY, 0);
        subTypes = Constant.itemSubTypeList.stream().filter(x -> x.getItemType() == typeId).collect(Collectors.toList());
        ItemSubType all = new ItemSubType();
        all.setId(0);
        all.setName("全部");
        all.setItemType(typeId);
        subTypes.add(0, all);
        subTypeIds = subTypes.stream().map(ItemSubType::getId).collect(Collectors.toList());
    }

    private void initTab() {
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.white));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white));
        ViewCompat.setElevation(tabLayout, 10);
    }

    private void initViewPager() {
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return TabFragment.newInstance(typeId, subTypeIds.get(position));
            }

            @Override
            public int getItemCount() {
                return subTypeIds.size();
            }
        });
    }
}