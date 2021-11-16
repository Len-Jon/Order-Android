package com.example.order;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.order.entity.ItemType;

//import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<ItemType> itemTypeList = new ArrayList<>();

    private static final String API_URI = "https://lenjon.top/test";
    private static final String ITEM_TYPE_URL = API_URI + "/item-type";
    private Handler handler;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(myToolbar);
        handler = new MyHandler(Looper.myLooper(), MainActivity.this);
        new Thread(() -> {
            try {
                URL url = new URL(ITEM_TYPE_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(5000);
                conn.connect();
                InputStream inputStream = null;
                BufferedReader reader = null;
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = conn.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String result = reader.readLine();
                    JSONObject res = JSONObject.parseObject(result);
                    JSONArray jsonArray = res.getJSONArray("data");
                    itemTypeList = jsonArray.toJavaList(ItemType.class);
                    handler.sendEmptyMessage(0);
                }
                if (reader != null)
                    reader.close();
                if (inputStream != null)
                    inputStream.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 异步更新UI的Handler
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
                    //数据写入
                    RelativeLayout mainRelativeLayout = mainActivity.findViewById(R.id.main_relative_layout);
                    int typeSize = mainActivity.itemTypeList.size();
                    TextView[] textViews = new TextView[typeSize];
                    ImageView[] imageViews = new ImageView[typeSize];
                    for (int i = 0; i < typeSize; i++) {
                        imageViews[i] = new ImageView(mainActivity);
                        ItemType itemType = mainActivity.itemTypeList.get(i);
                        byte[] decodedStr = Base64.decode(itemType.getPic().substring(22), Base64.DEFAULT);
                        imageViews[i].setImageBitmap(BitmapFactory.decodeByteArray(decodedStr, 0, decodedStr.length));
                        imageViews[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        textViews[i] = new TextView(mainActivity);
                        textViews[i].setText(itemType.getName());
                        textViews[i].setGravity(Gravity.CENTER);
                    }
                    textViews[0].setId(R.id.main_center_text_view);
                    textViews[1].setId(R.id.main_left_text_view);
                    textViews[2].setId(R.id.main_right_text_view);
                    //定义6个RelativeLayout组件
                    RelativeLayout.LayoutParams lpc = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams lpl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams lpr = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams lplu = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams lpcu = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams lpru = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    //相对位置调成
                    lpc.addRule(RelativeLayout.CENTER_IN_PARENT);
                    lpl.addRule(RelativeLayout.LEFT_OF, R.id.main_center_text_view);
                    lpr.addRule(RelativeLayout.RIGHT_OF, R.id.main_center_text_view);
                    lplu.addRule(RelativeLayout.ABOVE, R.id.main_left_text_view);
                    lpcu.addRule(RelativeLayout.ABOVE, R.id.main_center_text_view);
                    lpru.addRule(RelativeLayout.ABOVE, R.id.main_right_text_view);
                    lplu.addRule(RelativeLayout.LEFT_OF, R.id.main_center_text_view);
                    lpru.addRule(RelativeLayout.RIGHT_OF, R.id.main_center_text_view);
                    //水平方向位置调整
                    lpl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    lpr.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    lpcu.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    lplu.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    lpru.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    //垂直方向位置调整
                    lpl.addRule(RelativeLayout.CENTER_VERTICAL);
                    lpr.addRule(RelativeLayout.CENTER_VERTICAL);
                    //添加组件
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