package com.example.android.uiplay;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.uiplay.model.Account;

/**
 * A placeholder fragment containing a simple view.
 */
public class AccountListFragment extends ListFragment {
    private static final String TAG = "AccountListFragment";

    private ViewGroup fragmentContainer;
    AccountArrayAdapter accountArrayAdapter;

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
//                addAccountRow();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        fragmentContainer = (ViewGroup) rootView.findViewById(R.id.fragment_container);
        accountArrayAdapter = new AccountArrayAdapter(getActivity(), R.layout.item_row, new Account[]{new Account("My Account", 100000, "Available Balance", "...5463")});
        setListAdapter(accountArrayAdapter);
        accountArrayAdapter.setNotifyOnChange(true);

        return rootView;
    }

    @SuppressWarnings("UnusedDeclaration")
    private void addAccountRow() {
        accountArrayAdapter.add(new Account("My Account", 100000, "Available Balance", "...5463"));
    }

    /**
     * Here we add our account row.  In order to get our off screen buttons to work we have to
     * calculate and adjust various view here based on the device width at runtime.
     *
     * @param container container
     */
    private void addAccountRow(ViewGroup container) {
        View fragmentContent = getActivity().getLayoutInflater().inflate(R.layout.item_row, container, false);
        Button pressMeBehindButton = (Button) fragmentContent.findViewById(R.id.button_below);
        Button pressMeFrontButton = (Button) fragmentContent.findViewById(R.id.button_front);
        View frontLayout = fragmentContent.findViewById(R.id.front_layout);
        ViewGroup frontButtonsContainer = (ViewGroup) fragmentContent.findViewById(R.id.front_buttons_container);
        View initialVisibleLayout = fragmentContent.findViewById(R.id.account_details_container);
        View behindLayout = fragmentContent.findViewById(R.id.behind_layout);
        View divider = fragmentContent.findViewById(R.id.account_list_divider);

        pressMeBehindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Behind Press Me pressed", Toast.LENGTH_SHORT).show();
            }
        });

        pressMeFrontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Front Press Me pressed", Toast.LENGTH_SHORT).show();
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
        int fragmentContentWidth = displayWidth + ((int) getResources().getDimension(R.dimen.account_button_width) * frontButtonsContainer.getChildCount());

        // set the "front" views width to accommodate for the off screen buttons
        ViewGroup.LayoutParams params = fragmentContent.getLayoutParams();
        params.width = fragmentContentWidth;
        fragmentContent.setLayoutParams(params);

        params = frontLayout.getLayoutParams();
        params.width = fragmentContentWidth;
        frontLayout.setLayoutParams(params);

        params = divider.getLayoutParams();
        params.width = fragmentContentWidth;
        divider.setLayoutParams(params);

        // Here we adjust the width of the initially visible part of our layout and the behind/hidden part to
        // be the width of the display area.
        params = initialVisibleLayout.getLayoutParams();
        params.width = displayWidth;
        initialVisibleLayout.setLayoutParams(params);

        params = behindLayout.getLayoutParams();
        params.width = displayWidth;
        behindLayout.setLayoutParams(params);

        // Add our new row to the it's container
        container.addView(fragmentContent);
    }

    private static class AccountArrayAdapter extends ArrayAdapter<Account> {
        Context context;

        public AccountArrayAdapter(Context context, int resource, Account[] objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView != null) {
                view = convertView;
            } else {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                view = layoutInflater.inflate(R.layout.item_row, parent, false);
            }
            Button pressMeBehindButton = (Button) view.findViewById(R.id.button_below);
            Button pressMeFrontButton = (Button) view.findViewById(R.id.button_front);
            View frontLayout = view.findViewById(R.id.front_layout);
            ViewGroup frontButtonsContainer = (ViewGroup) view.findViewById(R.id.front_buttons_container);
            View initialVisibleLayout = view.findViewById(R.id.account_details_container);
            View behindLayout = view.findViewById(R.id.behind_layout);
            View divider = view.findViewById(R.id.account_list_divider);

            pressMeBehindButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Behind Press Me pressed", Toast.LENGTH_SHORT).show();
                }
            });

            pressMeFrontButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Front Press Me pressed", Toast.LENGTH_SHORT).show();
                }
            });

            // Here we set the width of our scrollable area, its container and the divider to be the
            // screen width plus the size of any left scrollable buttons
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int displayWidth = size.x;

            // calculate the scrollable size
            int viewWidth = displayWidth + ((int) context.getResources().getDimension(R.dimen.account_button_width) * frontButtonsContainer.getChildCount());

            // set the "front" views width to accommodate for the off screen buttons
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = viewWidth;
            view.setLayoutParams(params);

            params = frontLayout.getLayoutParams();
            params.width = viewWidth;
            frontLayout.setLayoutParams(params);

            params = divider.getLayoutParams();
            params.width = viewWidth;
            divider.setLayoutParams(params);

            // Here we adjust the width of the initially visible part of our layout and the behind/hidden part to
            // be the width of the display area.
            params = initialVisibleLayout.getLayoutParams();
            params.width = displayWidth;
            initialVisibleLayout.setLayoutParams(params);

            params = behindLayout.getLayoutParams();
            params.width = displayWidth;
            behindLayout.setLayoutParams(params);

            return view;
        }
    }
}
