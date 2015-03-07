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
     * Here we generate our account row.  In order to get our off screen buttons to work, we have to
     * calculate and adjust various views here based on the device width at runtime.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View accountRowItem;
        AccountListFragment.ViewHolder viewHolder;
        Account account = getItem(position);

        if (convertView != null) {
            Log.d(TAG, "reusing view: position " + position);
            accountRowItem = convertView;
            viewHolder = (AccountListFragment.ViewHolder) accountRowItem.getTag();
        } else {
            Log.d(TAG, "creating new view: position " + position);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            accountRowItem = layoutInflater.inflate(R.layout.item_row, parent, false);
            viewHolder = new AccountListFragment.ViewHolder();

            viewHolder.accountRowItem = accountRowItem;
            viewHolder.parentListView = parent; // the ListView

            viewHolder.pressMeBehindButton = (Button) accountRowItem.findViewById(R.id.button_below);
            viewHolder.frontButtons.add((Button) accountRowItem.findViewById(R.id.button_front_1));
            viewHolder.frontButtons.add((Button) accountRowItem.findViewById(R.id.button_front_2));
            viewHolder.frontButtons.add((Button) accountRowItem.findViewById(R.id.button_front_3));
            viewHolder.frontLayout = accountRowItem.findViewById(R.id.front_layout);
            viewHolder.frontButtonsContainer = (ViewGroup) accountRowItem.findViewById(R.id.front_buttons_container);
            viewHolder.initialVisibleLayout = accountRowItem.findViewById(R.id.account_details_container);
            viewHolder.behindLayout = accountRowItem.findViewById(R.id.behind_layout);

            viewHolder.accountNumber = (TextView) accountRowItem.findViewById(R.id.account_number);
            viewHolder.balanceDescription = (TextView) accountRowItem.findViewById(R.id.balance_description);
            viewHolder.balance = (TextView) accountRowItem.findViewById(R.id.balance);
            viewHolder.accountName = (TextView) accountRowItem.findViewById(R.id.account_name);
        }

        viewHolder.account = account;

        setupGestureDetector(viewHolder);
        setupButtons(viewHolder);
        setupViewWidths(viewHolder);
        populateData(viewHolder);

        accountRowItem.setTag(viewHolder);

        return accountRowItem;
    }

    private void setupGestureDetector(AccountListFragment.ViewHolder viewHolder) {
        AccountListFragment.AccountRowGestureListener gestureListener
                = new AccountListFragment.AccountRowGestureListener(
                context,
                viewHolder.frontLayout,
                viewHolder.parentListView);

        final GestureDetector gestureDetector = new GestureDetector(context, gestureListener);
        viewHolder.frontLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    private void populateData(AccountListFragment.ViewHolder viewHolder) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

        viewHolder.accountNumber.setText(viewHolder.account.getMaskedAccountNumber());
        viewHolder.balanceDescription.setText(viewHolder.account.getBalanceDescription());
        viewHolder.balance.setText(numberFormat.format(viewHolder.account.getBalance() / 100));
        viewHolder.accountName.setText(viewHolder.account.getName());
    }

    private void setupViewWidths(AccountListFragment.ViewHolder viewHolder) {
        // Here we set the width of our scrollable area, its container and the divider to be the
        // screen width plus the size of any left scrollable buttons
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int displayWidth = size.x;

        // calculate the scrollable size
        int fragmentContentWidth = displayWidth + ((int) context.getResources().getDimension(R.dimen.account_button_width) * viewHolder.account.getNumberOfButtons());

        // set the "front" views and their containers width to accommodate for the off screen buttons
        ViewGroup.LayoutParams params = viewHolder.parentListView.getLayoutParams();
        params.width = fragmentContentWidth;
        viewHolder.parentListView.setLayoutParams(params);

        params = viewHolder.accountRowItem.getLayoutParams();
        params.width = fragmentContentWidth;
        viewHolder.accountRowItem.setLayoutParams(params);

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

    private void setupButtons(AccountListFragment.ViewHolder viewHolder) {
        final View frontLayoutFinal = viewHolder.frontLayout;

        for (Button button : viewHolder.frontButtons) {
            button.setVisibility(View.GONE);
        }

        for (int i = 0; i < viewHolder.account.getNumberOfButtons(); i++) {
            viewHolder.frontButtons.get(i).setVisibility(View.VISIBLE);
        }

        if (viewHolder.pressMeBehindButton != null) {
            viewHolder.pressMeBehindButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frontLayoutFinal.animate().x(0);
                    Toast.makeText(context, "Behind Press Me pressed", Toast.LENGTH_SHORT).show();
                }
            });
        }

        for (Button button : viewHolder.frontButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frontLayoutFinal.animate().x(0);
                    Toast.makeText(context, "Front Press Me pressed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
