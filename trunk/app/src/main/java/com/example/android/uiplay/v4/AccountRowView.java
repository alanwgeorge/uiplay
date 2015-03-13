package com.example.android.uiplay.v4;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class AccountRowView extends View {
    private static final String TAG = "AccountRowLayout";

    public AccountRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout(" + changed + ", " + l + ", " + t + ", " + r + ", " + b + ")");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure(" + widthMeasureSpec + ", " + heightMeasureSpec + ")");
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(200, 200);
    }


}
