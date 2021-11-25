package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order.constant.Constant;

public class SettingActivity extends AppCompatActivity {
    private TextView addressText;
    private Spinner payMethodSpinner;
    private String[] methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        this.addressText = findViewById(R.id.setting_addr_text);
        this.payMethodSpinner = findViewById(R.id.setting_pay_method_spinner);
        Resources res = getResources();
        this.methods = res.getStringArray(R.array.pay_method_array_list);
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

    public void confirm(View view) {
        Constant.ADDRESS = this.addressText.getText().toString();
        String chosenMethod = this.payMethodSpinner.getSelectedItem().toString();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].equals(chosenMethod)) {
                Constant.PAY_METHOD_INDEX = i;
                break;
            }
        }
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }
}