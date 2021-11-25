package com.example.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.order.adapter.ChosenItemListAdapter;
import com.example.order.constant.Constant;
import com.example.order.entity.Item;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OrderListActivity extends AppCompatActivity {
    private List<Item> chosenList;
    private ChosenItemListAdapter chosenItemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        RecyclerView recyclerView = findViewById(R.id.order_list_recycler_view);
        this.chosenList = Constant.getChosenList();
        this.chosenItemListAdapter = new ChosenItemListAdapter(this.chosenList);
        recyclerView.setAdapter(this.chosenItemListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        this.chosenList = Constant.getChosenList();
        this.chosenItemListAdapter.setItemList(this.chosenList);
        this.chosenItemListAdapter.notifyDataSetChanged();
    }

    public void check(View view) {
        startActivity(new Intent(this, OrderCheckActivity.class));
    }
}