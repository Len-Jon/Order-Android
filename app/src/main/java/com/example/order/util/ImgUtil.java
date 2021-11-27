package com.example.order.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ImgUtil {
    public static Bitmap getBitMap(String base64) {
        byte[] decodedStr = Base64.decode(base64.substring(22), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedStr, 0, decodedStr.length);
    }
}
