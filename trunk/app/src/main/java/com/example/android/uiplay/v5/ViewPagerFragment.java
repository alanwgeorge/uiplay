package com.example.android.uiplay.v5;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.uiplay.MainActivity;
import com.example.android.uiplay.R;

import java.util.ArrayList;

public class ViewPagerFragment extends Fragment {

    public ViewPagerFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(new ViewPageAdapter(getChildFragmentManager()));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));
    }

    private static class ViewPageAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments = new ArrayList<>();

        public ViewPageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

            fragments.add(new OfferFragment());
            fragments.add(new OfferFragment());
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

    public static class OfferFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_view_page, container, false);
        }

    }
}
