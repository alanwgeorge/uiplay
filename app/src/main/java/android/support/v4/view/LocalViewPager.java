package android.support.v4.view;

import android.content.Context;
import android.util.AttributeSet;

public class LocalViewPager extends ViewPager {
//    private static final String TAG = "LocalViewPager";

    public LocalViewPager(Context context) {
        super(context);
    }

    public LocalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
        if (smoothScroll) {
            super.setCurrentItemInternal(item, smoothScroll, always, 1);
        } else {
            super.setCurrentItemInternal(item, smoothScroll, always, 0);
        }
    }
}
