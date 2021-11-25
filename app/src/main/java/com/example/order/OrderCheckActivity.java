package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.order.constant.Constant;

public class OrderCheckActivity extends AppCompatActivity {
    private TextView addressText;
    private Spinner payMethodSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_check);

        this.addressText = findViewById(R.id.user_addr_text);
        this.payMethodSpinner = findViewById(R.id.user_pay_method_spinner);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constant.ADDRESS != null) {
            addressText.setText(Constant.ADDRESS);
        }
        if (Constant.PAY_METHOD_INDEX != null) {
            payMethodSpinner.setSelection(Constant.PAY_METHOD_INDEX);
        }
    }
}