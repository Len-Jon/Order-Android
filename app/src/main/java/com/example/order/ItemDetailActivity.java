package com.example.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.order.constant.Constant;
import com.example.order.entity.Item;
import com.example.order.util.ImgUtil;
import com.example.order.util.MenuUtil;

import java.util.Optional;

public class ItemDetailActivity extends AppCompatActivity {
    public static final String FROM_ORDER_LIST_KEY = "FROM_ORDER_LIST_KEY";
    private Item item;
    private TextView cntTextView;
    private static final String CNT_TEXT = "已选数量: %d";

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();
        boolean fromOrderList = intent.getBooleanExtra(ItemDetailActivity.FROM_ORDER_LIST_KEY, false);
        if (fromOrderList && actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        Optional<Item> itemOptional = Constant.itemList.stream().filter(x -> x.getId() == intent.getIntExtra(Constant.ITEM_ID_KEY, 0)).findAny();
        if (itemOptional.isPresent()) {
            this.item = itemOptional.get();
            TextView itemDetailNameTextView = findViewById(R.id.item_detail_name);
            itemDetailNameTextView.setText(this.item.getName());
            TextView itemDetailDescTextView = findViewById(R.id.item_detail_desc);
            itemDetailDescTextView.setText(this.item.getDescription());
            if(this.item.getPic() != null && !this.item.getPic().isEmpty()){
                ImageView itemImgView = findViewById(R.id.item_detail_img);
                itemImgView.setImageBitmap(ImgUtil.getBitMap(this.item.getPic()));
            }
            this.cntTextView = findViewById(R.id.item_cnt_text);
            this.cntTextView.setText(String.format(CNT_TEXT, Constant.itemCntMap.getOrDefault(item.getId(), 0)));
        }

    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onResume() {
        super.onResume();
        this.cntTextView.setText(String.format(CNT_TEXT, Constant.getItemCnt(item.getId())));
    }

    @SuppressLint("DefaultLocale")
    public void addCnt(View view) {
        int cnt = Constant.getItemCnt(item.getId());
        cnt++;
        Constant.itemCntMap.put(item.getId(), cnt);
        cntTextView.setText(String.format(CNT_TEXT, cnt));
    }

    @SuppressLint("DefaultLocale")
    public void minusCnt(View view) {
        int cnt = Constant.getItemCnt(item.getId());
        cnt--;
        if (cnt < 0) {
            Toast.makeText(this, "数量不能小于0", Toast.LENGTH_SHORT).show();
        } else {
            Constant.itemCntMap.put(item.getId(), cnt);
            cntTextView.setText(String.format(CNT_TEXT, cnt));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return MenuUtil.onOptionsItemSelected(this, item);
    }
}