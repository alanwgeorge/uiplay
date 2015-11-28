package com.example.android.uiplay;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.uiplay.model.Account;

import java.text.NumberFormat;
import java.util.Locale;

import timber.log.Timber;

public class AccountRowBuilder {
    public static View setupAccountRow(@NonNull View accountRowItem,
                                @NonNull Account account,
                                @Nullable AccountRowViewHolder viewHolder,
                                @NonNull ViewGroup listView) {

        if (viewHolder == null) {
            viewHolder = new AccountRowViewHolder(accountRowItem);

            viewHolder.accountRowItem = accountRowItem;
            viewHolder.parent = listView;

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

            if (viewHolder.divider != null && listView instanceof ListView) { // ListView has builtin divider
                viewHolder.divider.setVisibility(View.GONE);
            }
        }

        viewHolder.account = account;

        setupGestureDetector(viewHolder);
        setupButtons(viewHolder);
        setupViewWidths(viewHolder);
        populateData(viewHolder);

        accountRowItem.setTag(viewHolder);

        return accountRowItem;
    }

    // TODO make number of buttons more dynamic
    public static int countVisibleFrontButtons(View frontLayout) {
        int visibleButtons = 0;

        if (frontLayout.findViewById(R.id.button_front_1) != null &&
                frontLayout.findViewById(R.id.button_front_1).getVisibility() == View.VISIBLE) {
            visibleButtons++;
        }

        if (frontLayout.findViewById(R.id.button_front_2) != null &&
                frontLayout.findViewById(R.id.button_front_2).getVisibility() == View.VISIBLE) {
            visibleButtons++;
        }

        if (frontLayout.findViewById(R.id.button_front_3) != null &&
                frontLayout.findViewById(R.id.button_front_3).getVisibility() == View.VISIBLE) {
            visibleButtons++;
        }

        return visibleButtons;
    }

    public static void setupGestureDetector(AccountRowViewHolder viewHolder) {
        AccountRowGestureListener gestureListener
                = new AccountRowGestureListener(
                App.context,
                viewHolder.frontLayout,
                viewHolder.parent);

        final GestureDetector gestureDetector = new GestureDetector(App.context, gestureListener);
        viewHolder.frontLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    public static void populateData(AccountRowViewHolder viewHolder) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

        viewHolder.accountNumber.setText(viewHolder.account.getMaskedAccountNumber());
        viewHolder.balanceDescription.setText(viewHolder.account.getBalanceDescription());
        viewHolder.balance.setText(numberFormat.format(viewHolder.account.getBalance() / 100));
        viewHolder.accountName.setText(viewHolder.account.getName());
    }

    public static void setupViewWidths(AccountRowViewHolder viewHolder) {
        // Here we set the width of our scrollable area, its container and the divider to be the
        // screen width plus the size of any left scrollable buttons
        if (App.context == null) {
            Timber.d("App.context is null");
        }

        WindowManager windowManager = (WindowManager) App.context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int displayWidth = size.x;

        // calculate the scrollable size
        int frontLayoutWidth = displayWidth + ((int) App.context.getResources().getDimension(R.dimen.account_button_width) * AccountRowBuilder.countVisibleFrontButtons(viewHolder.frontLayout));

        // set the "front" views and their containers width to accommodate for the off screen buttons
        ViewGroup.LayoutParams params;

        params = viewHolder.frontLayout.getLayoutParams();
        params.width = frontLayoutWidth;
        viewHolder.frontLayout.setLayoutParams(params);

        if (viewHolder.divider != null) {
            // hide the divider for the first item in list
            if (viewHolder.parent.getChildCount() == 0) {
                viewHolder.divider.setVisibility(View.GONE);
            }
        }

        // Here we adjust the width of the initially visible part of our layout and the behind/hidden part to
        // be the width of the display area.
        params = viewHolder.initialVisibleLayout.getLayoutParams();
        params.width = displayWidth;
        viewHolder.initialVisibleLayout.setLayoutParams(params);
    }

    public static void setupButtons(final AccountRowViewHolder viewHolder) {

        for (Button button : viewHolder.frontButtons) {
            if (button != null) {
                button.setVisibility(View.GONE);
            }
        }

        for (int i = 0; i < viewHolder.account.getNumberOfButtons(); i++) {
            if (viewHolder.frontButtons.get(i) != null) {
                viewHolder.frontButtons.get(i).setVisibility(View.VISIBLE);
            }
        }

        if (viewHolder.pressMeBehindButton != null) {
            viewHolder.pressMeBehindButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.frontLayout.animate().x(getUnscrolledX(viewHolder));
                    Toast.makeText(App.context, "Behind Press Me pressed", Toast.LENGTH_SHORT).show();
                }
            });
        }

        for (Button button : viewHolder.frontButtons) {
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.frontLayout.animate().x(getUnscrolledX(viewHolder));
                        Toast.makeText(App.context, "Front Press Me pressed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public static int getUnscrolledX(AccountRowViewHolder viewHolder) {
        return -((int) App.context.getResources().getDimension(R.dimen.account_button_width) * AccountRowBuilder.countVisibleFrontButtons(viewHolder.frontLayout));
    }
}
