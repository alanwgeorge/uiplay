package com.example.android.uiplay;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class AccountRowGestureListener implements GestureDetector.OnGestureListener {
    private static final String TAG = "AccountRowGestureLstnr";
    private float onDownFrontViewX;
    private View frontLayout;
    private ViewGroup listView;
    private Context context;

    public AccountRowGestureListener(Context context, View frontLayout, ViewGroup listView) {
        this.frontLayout = frontLayout;
        this.context = context;
        this.listView = listView;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        onDownFrontViewX = frontLayout.getX();
//            Log.d(TAG, hashCode() + ":onDown(" + e + ") x = " + onDownFrontViewX);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
//        Log.d(TAG, "onShowPress(" + e + ")");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp(" + e + ")");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent1, MotionEvent motionEvent2, float distanceX, float distanceY) {
        resetOtherAccountRows();

        int frontButtonsWidth = (int) context.getResources().getDimension(R.dimen.account_button_width) * AccountRowBuilder.countVisibleFrontButtons(frontLayout);
        int belowButtonWidth = (int) context.getResources().getDimension(R.dimen.button_below_width);
        int belowButtonPadding = (int) context.getResources().getDimension(R.dimen.button_below_padding);

        if ((motionEvent2.getRawX() - motionEvent1.getRawX()) > 0) { // scrolling right
            if (onDownFrontViewX == -frontButtonsWidth) {  // un-scrolled position
                frontLayout.animate().x(0); // reveal front buttons
            } else if (onDownFrontViewX == -(frontButtonsWidth + belowButtonWidth + (belowButtonPadding * 2))) { // scrolled left below button is visible
                frontLayout.animate().x(-frontButtonsWidth); // return to un-scrolled position
            }
        } else { // scrolling left
            if (onDownFrontViewX == -frontButtonsWidth) { // un-scrolled position
                frontLayout.animate().x(-(frontButtonsWidth + belowButtonWidth + (belowButtonPadding * 2))); // reveal bellow button
            } else if (onDownFrontViewX == 0) { // scrolled right front button visible
                frontLayout.animate().x(-frontButtonsWidth); // return to un-scrolled position
            }
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
//        Log.d(TAG, "onLongPress(" + e + ")");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        Log.d(TAG, "onFling(" + e1 + ", " + e2 + ", " + velocityX + ", " + velocityY + ")");
        return false;
    }

    private void resetOtherAccountRows() {
        for (int i = 0; i < listView.getChildCount(); i++) {
            ViewGroup accountRow = (ViewGroup) listView.getChildAt(i);
            View frontLayout = accountRow.findViewById(R.id.front_layout);
            if (frontLayout != this.frontLayout) {
//                frontLayout.animate().x(-((int) context.getResources().getDimension(R.dimen.account_button_width) * AccountRowBuilder.countVisibleFrontButtons(frontLayout)));
                frontLayout.animate().x(AccountRowBuilder.getUnscrolledX((AccountRowViewHolder) accountRow.getTag()));
            }
        }
    }

}
