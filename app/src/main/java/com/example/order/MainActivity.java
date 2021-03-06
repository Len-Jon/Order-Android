package com.example.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.alibaba.fastjson.JSONObject;
import com.example.order.constant.Constant;
import com.example.order.entity.Item;
import com.example.order.entity.ItemSubType;
import com.example.order.entity.ItemType;
import com.example.order.receiver.NetworkChangeReceiver;
import com.example.order.util.HttpUtils;
import com.example.order.util.ImgUtil;
import com.example.order.util.MenuUtil;
import com.google.android.material.navigation.NavigationView;

//import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private NetworkChangeReceiver networkChangeReceiver;
    private static final String API_URI = "https://lenjon.top/order";
    private static final String ITEM_TYPE_URL = API_URI + "/item-type";
    private static final String ITEM_SUB_TYPE_URL = API_URI + "/item-sub-type";
    private static final String ITEM_URL = API_URI + "/item";


    private DrawerLayout drawer;
    private Toolbar toolbar;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataInit();
        drawInit();

        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        //?????????????????????????????????????????????wifi???????????????????????????
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(networkChangeReceiver, filter);
    }

    /**
     * ????????????????????????
     */
    private void drawInit() {
        drawer = findViewById(R.id.main_draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_main:
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        break;
                    case R.id.nav_setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                    case R.id.nav_feedback:
                        startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * ???????????????
     */
    private void dataInit() {
        Handler handler = new MyHandler(Looper.myLooper(), MainActivity.this);
        new Thread(() -> {
            JSONObject res = JSONObject.parseObject(HttpUtils.doGet(ITEM_TYPE_URL));
            Constant.itemTypeList = res.getJSONArray("data").toJavaList(ItemType.class);
        }).start();
        new Thread(() -> {
            JSONObject res = JSONObject.parseObject(HttpUtils.doGet(ITEM_SUB_TYPE_URL));
            Constant.itemSubTypeList = res.getJSONArray("data").toJavaList(ItemSubType.class);
        }).start();
        new Thread(() -> {
            JSONObject res = JSONObject.parseObject(HttpUtils.doGet(ITEM_URL));
            Constant.itemList = res.getJSONArray("data").toJavaList(Item.class);
        }).start();
        for (int i = 0; i < 10; i++) {
            if (Constant.itemTypeList != null && Constant.itemSubTypeList != null && Constant.itemList != null) {
                Constant.initMap();
                handler.sendEmptyMessage(0);
                return;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                break;
            }
        }
        Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    /**
     * ????????????UI???Handler
     */
    static class MyHandler extends Handler {
        WeakReference<MainActivity> weakReference;

        public MyHandler(@NonNull Looper looper, MainActivity activity) {
            super(looper);
            weakReference = new WeakReference<>(activity);
        }

        @SuppressLint({"SetTextI18n"})
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = weakReference.get();
            if (mainActivity == null)
                return;
            switch (msg.what) {
                case 0:
                    //????????????
                    RelativeLayout mainRelativeLayout = mainActivity.findViewById(R.id.main_relative_layout);
                    int typeSize = Constant.itemTypeList.size();
                    TextView[] textViews = new TextView[typeSize];
                    ImageView[] imageViews = new ImageView[typeSize];
                    for (int i = 0; i < typeSize; i++) {
                        imageViews[i] = new ImageView(mainActivity);
                        ItemType itemType = Constant.itemTypeList.get(i);
                        imageViews[i].setImageBitmap(ImgUtil.getBitMap(itemType.getPic()));
                        imageViews[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        imageViews[i].setOnClickListener(view -> {
                            Intent intent = new Intent(view.getContext(), TabActivity.class).putExtra(Constant.ITEM_TYPE_ID_KEY, itemType.getId());
                            view.getContext().startActivity(intent);
                        });
                        textViews[i] = new TextView(mainActivity);
                        textViews[i].setText(itemType.getName());
                        textViews[i].setGravity(Gravity.CENTER);
                    }
                    textViews[0].setId(R.id.main_center_text_view);
                    textViews[1].setId(R.id.main_left_text_view);
                    textViews[2].setId(R.id.main_right_text_view);
                    //??????6???RelativeLayout??????
                    RelativeLayout.LayoutParams lpc = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams lpl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams lpr = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams lplu = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams lpcu = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams lpru = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    //??????????????????
                    lpc.addRule(RelativeLayout.CENTER_IN_PARENT);
                    lpl.addRule(RelativeLayout.LEFT_OF, R.id.main_center_text_view);
                    lpr.addRule(RelativeLayout.RIGHT_OF, R.id.main_center_text_view);
                    lplu.addRule(RelativeLayout.ABOVE, R.id.main_left_text_view);
                    lpcu.addRule(RelativeLayout.ABOVE, R.id.main_center_text_view);
                    lpru.addRule(RelativeLayout.ABOVE, R.id.main_right_text_view);
                    lplu.addRule(RelativeLayout.LEFT_OF, R.id.main_center_text_view);
                    lpru.addRule(RelativeLayout.RIGHT_OF, R.id.main_center_text_view);
                    //????????????????????????
                    lpl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    lpr.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    lpcu.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    lplu.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    lpru.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    //????????????????????????
                    lpl.addRule(RelativeLayout.CENTER_VERTICAL);
                    lpr.addRule(RelativeLayout.CENTER_VERTICAL);
                    //????????????
                    mainRelativeLayout.addView(textViews[0], lpc);
                    mainRelativeLayout.addView(textViews[1], lpl);
                    mainRelativeLayout.addView(textViews[2], lpr);
                    mainRelativeLayout.addView(imageViews[0], lpcu);
                    mainRelativeLayout.addView(imageViews[1], lplu);
                    mainRelativeLayout.addView(imageViews[2], lpru);
                    break;
                default:
                    break;
            }
        }
    }
}