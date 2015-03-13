package com.example.android.uiplay.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.android.uiplay.R;

public class AccountRowFrontRelativeLayout extends RelativeLayout implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final String TAG = "AccountRowFrntRltivLyut";

    private GestureDetectorCompat gestureDetector;
    private float onDownFrontViewX;

    public AccountRowFrontRelativeLayout(Context context) {
        this(context, null);
    }

    public AccountRowFrontRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AccountRowFrontRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gestureDetector = new GestureDetectorCompat(context, this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        Log.d(TAG, "onLayout(" + changed + ", " + l + ", " + t + ", " + r + ", " + b + ")");

        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
//        int action = MotionEventCompat.getActionMasked(event);
//        switch(action) {
//            case (MotionEvent.ACTION_DOWN) :
//                Log.d(TAG, "onInterceptTouchEvent: Action was DOWN");
//                break;
//            case (MotionEvent.ACTION_MOVE) :
//                Log.d(TAG,"onInterceptTouchEvent: Action was MOVE");
//                break;
//            case (MotionEvent.ACTION_UP) :
//                Log.d(TAG,"onInterceptTouchEvent: Action was UP");
//                break;
//            case (MotionEvent.ACTION_CANCEL) :
//                Log.d(TAG,"onInterceptTouchEvent: Action was CANCEL");
//                break;
//            case (MotionEvent.ACTION_OUTSIDE) :
//                Log.d(TAG,"onInterceptTouchEvent: Movement occurred outside bounds " +
//                        "of current screen element");
//                break;
//            default :
//                Log.d(TAG, "onInterceptTouchEvent: Action was " + action);
//        }

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
//        int action = MotionEventCompat.getActionMasked(event);
//        switch(action) {
//            case (MotionEvent.ACTION_DOWN) :
//                Log.d(TAG,"onTouchEvent: Action was DOWN");
//                break;
//            case (MotionEvent.ACTION_MOVE) :
//                Log.d(TAG,"onTouchEvent: Action was MOVE");
//                break;
//            case (MotionEvent.ACTION_UP) :
//                Log.d(TAG,"onTouchEvent: Action was UP");
//                break;
//            case (MotionEvent.ACTION_CANCEL) :
//                Log.d(TAG,"onTouchEvent: Action was CANCEL");
//                break;
//            case (MotionEvent.ACTION_OUTSIDE) :
//                Log.d(TAG,"onTouchEvent: Movement occurred outside bounds " +
//                        "of current screen element");
//                break;
//            default :
//                Log.d(TAG, "onTouchEvent: Action was " + action);
//        }
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        onDownFrontViewX = getX();
        Log.d(TAG, "onDown(" + e + ") x = " + onDownFrontViewX);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG, "onShowPress(" + e + ")");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp(" + e + ")");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent1, MotionEvent motionEvent2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll(" + motionEvent1 + ", " + motionEvent2 + ", " + distanceX + ", " + distanceY + ")");

        Log.d(TAG, "getX() = " + getX());

        int frontButtonWidth = (int) getContext().getResources().getDimension(R.dimen.account_button_width);
        int belowButtonWidth = (int) getContext().getResources().getDimension(R.dimen.button_below_width);
        int belowButtonPadding = (int) getContext().getResources().getDimension(R.dimen.button_below_padding);

        if ((motionEvent2.getRawX() - motionEvent1.getRawX()) > 0) { // scrolling right
            if (onDownFrontViewX == 0) {  // un-scrolled position
                animate().x(frontButtonWidth); // reveal front button
            } else if (onDownFrontViewX == -(belowButtonWidth + (belowButtonPadding * 2))) { // scrolled left below button visible
                animate().x(0); // return to un-scrolled position
            }
        } else { // scrolling left
            if (onDownFrontViewX == 0) { // un-scrolled position
                animate().x(-(belowButtonWidth + (belowButtonPadding * 2))); // reveal bellow button
            } else if (onDownFrontViewX == frontButtonWidth) { // scrolled right front button visible
                animate().x(0); // return to un-scrolled position
            }
        }

        requestLayout();

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG, "onLongPress(" + e + ")");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling(" + e1 + ", " + e2 + ", " + velocityX + ", " + velocityY + ")");

        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d(TAG, "onSingleTapConfirmed(" + e + ")");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap(" + e + ")");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(TAG, "onDoubleTapEvent(" + e + ")");
        return true;
    }
}
