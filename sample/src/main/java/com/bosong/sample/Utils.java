package com.bosong.sample;

import android.content.Context;

/**
 * Created by boson on 2017/3/10.
 */

public class Utils {
    private Utils(){}

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
