package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.order.adapter.CustomAdapter;
import com.example.order.constant.Constant;
import com.example.order.entity.Item;

import java.util.Optional;

public class ItemDetailActivity extends AppCompatActivity {
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent intent = getIntent();
        Optional<Item> itemOptional = Constant.itemList.stream().filter(x -> x.getId() == intent.getIntExtra(CustomAdapter.ViewHolder.ITEM_ID_KEY, 0)).findAny();
        if(itemOptional.isPresent()){
            this.item = itemOptional.get();
           TextView itemDetailNameTextView = findViewById(R.id.item_detail_name);
           itemDetailNameTextView.setText(this.item.getName());
        }


    }
}