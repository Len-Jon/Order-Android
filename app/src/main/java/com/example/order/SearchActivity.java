package com.example.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.order.adapter.ChosenItemListAdapter;
import com.example.order.adapter.ItemListAdapter;
import com.example.order.constant.Constant;
import com.example.order.entity.Item;

import java.util.List;
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity {
    private EditText itemNameTextView;

    private ItemListAdapter itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.itemNameTextView = findViewById(R.id.search_item_name);
        this.itemListAdapter = new ItemListAdapter(Constant.itemList);
        RecyclerView recyclerView = findViewById(R.id.search_list_recycler_view);
        recyclerView.setAdapter(this.itemListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        search(findViewById(R.id.search_button));
    }

    public void search(View view) {
        CharSequence name = this.itemNameTextView.getText();
        if (name == null) {
            this.itemListAdapter.setItemList(Constant.itemList);
        } else {
            this.itemListAdapter.setItemList(Constant.itemList.stream().filter(x -> x.getName().contains(name)).collect(Collectors.toList()));
        }
        this.itemListAdapter.notifyDataSetChanged();
    }
}