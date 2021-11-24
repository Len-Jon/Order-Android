package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order.adapter.CustomAdapter;
import com.example.order.constant.Constant;
import com.example.order.entity.Item;

import java.util.Optional;

public class ItemDetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ITEM_DETAIL";
    private Item item;
    private Toast mToast;
    private TextView cntTextView;
    private static final String CNT_TEXT = "已选数量: %d";

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent intent = getIntent();
        Optional<Item> itemOptional = Constant.itemList.stream().filter(x -> x.getId() == intent.getIntExtra(CustomAdapter.ViewHolder.ITEM_ID_KEY, 0)).findAny();
        if (itemOptional.isPresent()) {
            this.item = itemOptional.get();
            TextView itemDetailNameTextView = findViewById(R.id.item_detail_name);
            itemDetailNameTextView.setText(this.item.getName());
            TextView itemDetailDescTextView = findViewById(R.id.item_detail_desc);
            itemDetailDescTextView.setText(this.item.getDescription());
            this.cntTextView = findViewById(R.id.item_cnt_text);
            this.cntTextView.setText(String.format(CNT_TEXT, 0));
        }

    }

    @SuppressLint("DefaultLocale")
    public void addCnt(View view) {
        int cnt = Constant.itemCntMap.getOrDefault(item.getId(), 0);
        cnt++;
        Constant.itemCntMap.put(item.getId(), cnt);
        cntTextView.setText(String.format(CNT_TEXT, cnt));
    }

    @SuppressLint("DefaultLocale")
    public void minusCnt(View view) {
        int cnt = Constant.itemCntMap.getOrDefault(item.getId(), 0);
        cnt--;
        if (cnt < 0) {
            Toast.makeText(this, "数量不能小于0", Toast.LENGTH_SHORT).show();
        } else {
            Constant.itemCntMap.put(item.getId(), cnt);
            cntTextView.setText(String.format(CNT_TEXT, cnt));
        }
    }
}