package com.example.android.uiplay;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class AccountArrayAdapter extends ArrayAdapter<Account> {
    private static final String TAG = "AccountArrayAdapter";
    Context context;

    AccountArrayAdapter(Context context, int resource, List<Account> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    // TODO make number of buttons more dynamic
    public static int countVisibleFrontButtons(View frontLayout) {
        int visibleButtons = 0;

        if (frontLayout.findViewById(R.id.button_front_1).getVisibility() == View.VISIBLE) {
            visibleButtons++;
        }

        if (frontLayout.findViewById(R.id.button_front_2).getVisibility() == View.VISIBLE) {
            visibleButtons++;
        }

        if (frontLayout.findViewById(R.id.button_front_3).getVisibility() == View.VISIBLE) {
            visibleButtons++;
        }

        return visibleButtons;
    }

    /**
     * Here we generate our account row.  In order to get our off screen buttons to work, we have to
     * calculate and adjust various views here based on the device width at runtime.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View accountRowItem;
        ViewHolder viewHolder = null;
        Account account = getItem(position);

        if (convertView != null) {
            Log.d(TAG, "reusing view: position " + position);
            accountRowItem = convertView;
            viewHolder = (ViewHolder) accountRowItem.getTag();
        } else {
            Log.d(TAG, "creating new view: position " + position);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            accountRowItem = layoutInflater.inflate(R.layout.item_row2, parent, false);
        }

        return setupAccountRow(accountRowItem, account, viewHolder, parent);
    }

    public static View setupAccountRow(@NonNull View accountRowItem,
                                @NonNull Account account,
                                @Nullable ViewHolder viewHolder,
                                @NonNull ViewGroup parent) {

        if (viewHolder == null) {
            viewHolder = new ViewHolder();

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
            viewHolder.divider = accountRowItem.findViewById(R.id.account_list_divider);


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

    private static void setupGestureDetector(ViewHolder viewHolder) {
        AccountRowGestureListener gestureListener
                = new AccountRowGestureListener(
                App.context,
                viewHolder.frontLayout,
                viewHolder.parentListView);

        final GestureDetector gestureDetector = new GestureDetector(App.context, gestureListener);
        viewHolder.frontLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    private static void populateData(ViewHolder viewHolder) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

        viewHolder.accountNumber.setText(viewHolder.account.getMaskedAccountNumber());
        viewHolder.balanceDescription.setText(viewHolder.account.getBalanceDescription());
        viewHolder.balance.setText(numberFormat.format(viewHolder.account.getBalance() / 100));
        viewHolder.accountName.setText(viewHolder.account.getName());
    }

    private static void setupViewWidths(ViewHolder viewHolder) {
        // Here we set the width of our scrollable area, its container and the divider to be the
        // screen width plus the size of any left scrollable buttons
        WindowManager windowManager = (WindowManager) App.context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int displayWidth = size.x;

        // calculate the scrollable size
        int fragmentContentWidth = displayWidth + ((int) App.context.getResources().getDimension(R.dimen.account_button_width) * viewHolder.account.getNumberOfButtons());

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

        if (viewHolder.divider != null) {
            // hide the divider for the first item in list
            if (viewHolder.parentListView.getChildCount() == 0) {
                viewHolder.divider.setVisibility(View.GONE);
            }
            params = viewHolder.divider.getLayoutParams();
            params.width = fragmentContentWidth;
            viewHolder.divider.setLayoutParams(params);
        }

        // Here we adjust the width of the initially visible part of our layout and the behind/hidden part to
        // be the width of the display area.
        params = viewHolder.initialVisibleLayout.getLayoutParams();
        params.width = displayWidth;
        viewHolder.initialVisibleLayout.setLayoutParams(params);

        params = viewHolder.behindLayout.getLayoutParams();
        params.width = displayWidth;
        viewHolder.behindLayout.setLayoutParams(params);
    }

    private static void setupButtons(ViewHolder viewHolder) {
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
                    Toast.makeText(App.context, "Behind Press Me pressed", Toast.LENGTH_SHORT).show();
                }
            });
        }

        for (Button button : viewHolder.frontButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frontLayoutFinal.animate().x(0);
                    Toast.makeText(App.context, "Front Press Me pressed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static class ViewHolder {
        Account account;
        View accountRowItem;
        ViewGroup parentListView;

        Button pressMeBehindButton;
        List<Button> frontButtons = new ArrayList<>();
        View frontLayout;
        ViewGroup frontButtonsContainer;
        View initialVisibleLayout;
        View behindLayout;
        View divider;

        TextView accountNumber;
        TextView balanceDescription;
        TextView balance;
        TextView accountName;
    }
}
