package com.example.order.util;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.order.OrderListActivity;
import com.example.order.R;

public class MenuUtil {
    public static boolean onOptionsItemSelected(Context context, @NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order:
                context.startActivity(new Intent(context, OrderListActivity.class));
                break;
            default:
                break;
        }
        return true;
    }
}
