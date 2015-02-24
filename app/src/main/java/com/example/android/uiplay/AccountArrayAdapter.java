package com.example.android.uiplay;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.uiplay.model.Account;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

class AccountArrayAdapter extends ArrayAdapter<Account> {
    private static final String TAG = "AccountArrayAdapter";
    Context context;

    AccountArrayAdapter(Context context, int resource, List<Account> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    /**
     * Here we generate our account row.  In order to get our off screen buttons to work we have to
     * calculate and adjust various views here based on the device width at runtime.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View fragmentContent;
        AccountListFragment.ViewHolder viewHolder;
        Account account = getItem(position);

//        if (convertView != null && AccountListFragment.countVisibleFrontButtons(convertView) == account.getNumberOfButtons()) {
//            Log.d(TAG, "reusing view: position " + position);
//            fragmentContent = convertView;
//            viewHolder = (AccountListFragment.ViewHolder) fragmentContent.getTag();
//        } else {
            Log.d(TAG, "creating new view: position " + position);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            fragmentContent = layoutInflater.inflate(R.layout.item_row, parent, false);
            viewHolder = new AccountListFragment.ViewHolder();

            viewHolder.root = fragmentContent;
            viewHolder.parent = parent; // ListView

            viewHolder.pressMeBehindButton = (Button) fragmentContent.findViewById(R.id.button_below);
            viewHolder.frontButtons.add((Button) fragmentContent.findViewById(R.id.button_front_1));
            viewHolder.frontButtons.add((Button) fragmentContent.findViewById(R.id.button_front_2));
            viewHolder.frontButtons.add((Button) fragmentContent.findViewById(R.id.button_front_3));
            viewHolder.frontLayout = fragmentContent.findViewById(R.id.front_layout);
            viewHolder.frontButtonsContainer = (ViewGroup) fragmentContent.findViewById(R.id.front_buttons_container);
            viewHolder.initialVisibleLayout = fragmentContent.findViewById(R.id.account_details_container);
            viewHolder.behindLayout = fragmentContent.findViewById(R.id.behind_layout);

            viewHolder.accountNumber = (TextView) fragmentContent.findViewById(R.id.account_number);
            viewHolder.balanceDescription = (TextView) fragmentContent.findViewById(R.id.balance_description);
            viewHolder.balance = (TextView) fragmentContent.findViewById(R.id.balance);
            viewHolder.accountName = (TextView) fragmentContent.findViewById(R.id.account_name);
//        }

        setupGestureDetector(viewHolder);
        setupButtons(position, viewHolder);
        setupViewWidths(position,viewHolder);
        populateData(position, viewHolder);

        fragmentContent.setTag(viewHolder);

        return fragmentContent;
    }

    private void setupGestureDetector(AccountListFragment.ViewHolder viewHolder) {
        AccountListFragment.AccountRowGestureListener gestureListener
                = new AccountListFragment.AccountRowGestureListener(
                context,
                viewHolder.frontLayout,
                viewHolder.parent);

        final GestureDetector gestureDetector = new GestureDetector(context, gestureListener);
        viewHolder.frontLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    private void populateData(int position, AccountListFragment.ViewHolder viewHolder) {
        Account account = getItem(position);

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

        viewHolder.accountNumber.setText(account.getMaskedAccountNumber());
        viewHolder.balanceDescription.setText(account.getBalanceDescription());
        viewHolder.balance.setText(numberFormat.format(account.getBalance() / 100));
        viewHolder.accountName.setText(account.getName());
    }

    private void setupViewWidths(int position, AccountListFragment.ViewHolder viewHolder) {
        Account account = getItem(position);

        // Here we set the width of our scrollable area, its container and the divider to be the
        // screen width plus the size of any left scrollable buttons
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int displayWidth = size.x;

        // calculate the scrollable size
        int fragmentContentWidth = displayWidth + ((int) context.getResources().getDimension(R.dimen.account_button_width) * account.getNumberOfButtons());

        // set the "front" views and their containers width to accommodate for the off screen buttons
        ViewGroup.LayoutParams params = viewHolder.parent.getLayoutParams();
        params.width = fragmentContentWidth;
        viewHolder.parent.setLayoutParams(params);

        params = viewHolder.root.getLayoutParams();
        params.width = fragmentContentWidth;
        viewHolder.root.setLayoutParams(params);

        params = viewHolder.frontLayout.getLayoutParams();
        params.width = fragmentContentWidth;
        viewHolder.frontLayout.setLayoutParams(params);

        // Here we adjust the width of the initially visible part of our layout and the behind/hidden part to
        // be the width of the display area.
        params = viewHolder.initialVisibleLayout.getLayoutParams();
        params.width = displayWidth;
        viewHolder.initialVisibleLayout.setLayoutParams(params);

        params = viewHolder.behindLayout.getLayoutParams();
        params.width = displayWidth;
        viewHolder.behindLayout.setLayoutParams(params);
    }

    private void setupButtons(int position, AccountListFragment.ViewHolder viewHolder) {
        Account account = getItem(position);

        final View frontLayoutFinal = viewHolder.frontLayout;

        for (Button button : viewHolder.frontButtons) {
            button.setVisibility(View.GONE);
        }

        for (int i = 0; i < account.getNumberOfButtons(); i++) {
            viewHolder.frontButtons.get(i).setVisibility(View.VISIBLE);
        }

        if (viewHolder.pressMeBehindButton != null) {
            viewHolder.pressMeBehindButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frontLayoutFinal.animate().x(0);
//                    frontLayoutFinal.requestLayout();
                    Toast.makeText(context, "Behind Press Me pressed", Toast.LENGTH_SHORT).show();
                }
            });
        }

        for (Button button : viewHolder.frontButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frontLayoutFinal.animate().x(0);
//                    frontLayoutFinal.requestLayout();
                    Toast.makeText(context, "Front Press Me pressed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
