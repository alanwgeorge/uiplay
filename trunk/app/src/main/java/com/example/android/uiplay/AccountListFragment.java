package com.example.android.uiplay;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class AccountListFragment extends Fragment {
    private static final String TAG = "AccountListFragment";

    private ViewGroup fragmentContainer;
//    private View touchedView;

    public AccountListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add_item:
                Log.d(TAG, "add item menu selected");
                addAccountRow(fragmentContainer);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        fragmentContainer = (ViewGroup) rootView.findViewById(R.id.fragment_container);

        return rootView;
    }

    private void addAccountRow(ViewGroup container) {
        View fragmentContent = getActivity().getLayoutInflater().inflate(R.layout.item_row, container, false);
        Button pressMeBehindButton = (Button) fragmentContent.findViewById(R.id.button_below);
        Button pressMeFrontButton = (Button) fragmentContent.findViewById(R.id.button_front);
        View frontLayout = fragmentContent.findViewById(R.id.front_layout);
        ViewGroup frontButtonsContainer = (ViewGroup) fragmentContent.findViewById(R.id.front_buttons_container);
        View visibleLayout = fragmentContent.findViewById(R.id.account_details_container);
        View behindLayout = fragmentContent.findViewById(R.id.behind_layout);
        View divider = fragmentContent.findViewById(R.id.account_list_divider);

        pressMeBehindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Behind Press Me pressed", Toast.LENGTH_LONG).show();
            }
        });

        pressMeFrontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Front Press Me pressed", Toast.LENGTH_LONG).show();
            }
        });
        // hide the divider for the first item in list
        if (container.getChildCount() == 0) {
            fragmentContent.findViewById(R.id.account_list_divider).setVisibility(View.GONE);
        }

        // Here we set the width of our scrollable area, its container and the divider to be the
        // screen width plus the size of any left scrollable buttons
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int displayWidth = size.x;

        // calculate the scrollable size
        int fragmentContentWidth =
                displayWidth +
                        ((int) getResources().getDimension(R.dimen.account_button_width) * frontButtonsContainer.getChildCount());

        ViewGroup.LayoutParams params = fragmentContent.getLayoutParams();
        params.width = fragmentContentWidth;
        fragmentContent.setLayoutParams(params);

        params = frontLayout.getLayoutParams();
        params.width = fragmentContentWidth;
        frontLayout.setLayoutParams(params);

        params = divider.getLayoutParams();
        params.width = fragmentContentWidth;
        divider.setLayoutParams(params);

        // Here we adjust the width of the initially visible part of our layout to
        // be the width of the display area.
        params = behindLayout.getLayoutParams();
        params.width = displayWidth;
        behindLayout.setLayoutParams(params);

        params = visibleLayout.getLayoutParams();
        params.width = displayWidth;
        visibleLayout.setLayoutParams(params);

        // Add our new row to the it's container
        container.addView(fragmentContent);
    }
}
