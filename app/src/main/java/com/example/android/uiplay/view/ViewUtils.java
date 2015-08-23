package com.example.android.uiplay.view;

import android.util.DisplayMetrics;

import com.example.android.uiplay.App;

@SuppressWarnings("unused")
public class ViewUtils {
    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = App.context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    public static int pxToDp(int px) {
        DisplayMetrics displayMetrics = App.context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
