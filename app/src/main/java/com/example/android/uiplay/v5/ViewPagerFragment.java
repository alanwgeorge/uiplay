package com.example.android.uiplay.v5;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.android.uiplay.App;
import com.example.android.uiplay.MainActivity;
import com.example.android.uiplay.R;

import java.util.ArrayList;

public class ViewPagerFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private static final String TAG = "ViewPagerFragment";

    private ViewPager viewPager;
    private RadioButton page1Indicator;
    private RadioButton page2Indicator;
    private Toast offerToast;

    public ViewPagerFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);

        // test
        page1Indicator = (RadioButton) rootView.findViewById(R.id.pager_page_indicator1);
        page2Indicator = (RadioButton) rootView.findViewById(R.id.pager_page_indicator2);

        View leftTapTarget = rootView.findViewById(R.id.offer_nav_left_tap_target);
        View rightTapTarget = rootView.findViewById(R.id.offer_nav_right_tap_target);

        leftTapTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() > 0) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                }
            }
        });

        rightTapTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
            }
        });


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewPager.setAdapter(new ViewPageAdapter(getChildFragmentManager()));
        viewPager.setOnPageChangeListener(this);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.d(TAG, "onTouch(" + v + ", " +  event + ")");
                return false;
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.d(TAG, "onPageScrolled()");
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected(" + position + ")");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                Log.d(TAG, "onPageScrollStateChanged(SCROLL_STATE_IDLE)");
                if (page1Indicator != null && page2Indicator != null) {
                    if (offerToast != null) offerToast.cancel();
                    String message = null;
                    int currentItem = viewPager.getCurrentItem();
                    Log.d(TAG, "currentItem = " + currentItem);
                    switch (currentItem) {
                        case 0:
                            page1Indicator.setChecked(true);
                            page2Indicator.setChecked(false);
                            message = "offer 1 presented";
                            break;
                        case 1:
                            page2Indicator.setChecked(true);
                            page1Indicator.setChecked(false);
                            message = "offer 2 presented";
                    }

                    offerToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                    offerToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, App.dpToPx(100));

                    offerToast.show();
                }
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                Log.d(TAG, "onPageScrollStateChanged(SCROLL_STATE_DRAGGING)");
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                Log.d(TAG, "onPageScrollStateChanged(SCROLL_STATE_SETTLING)");
                break;
        }
    }

    private static class ViewPageAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments = new ArrayList<>();

        public ViewPageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

            fragments.add(OfferFragment.newInstance("Get Yours Today! \nRonco Veg-O-Matic", "It slices! It dices!"));
            fragments.add(OfferFragment.newInstance("Get Yours Today! \nRonco Inside-The-Shell Egg Scrambler","Gets rid of those slimy egg whites in your scrambled eggs."));
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
