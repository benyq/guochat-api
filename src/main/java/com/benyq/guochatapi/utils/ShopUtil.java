package com.benyq.guochatapi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ShopUtil {

    public static String generateOrderId(String shopId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
        return sdf.format(new Date()) + shopId;
    }

}
