package com.example.android.uiplay.v6;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.uiplay.App;
import com.example.android.uiplay.R;

import java.text.NumberFormat;
import java.util.Locale;

public class AccountRowBuilderV2 {
//    private static final String TAG = "AccountRowBuilder";

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

        if (frontLayout.findViewById(R.id.button_front_4) != null &&
                frontLayout.findViewById(R.id.button_front_4).getVisibility() == View.VISIBLE) {
            visibleButtons++;
        }

        if (frontLayout.findViewById(R.id.button_front_5) != null &&
                frontLayout.findViewById(R.id.button_front_5).getVisibility() == View.VISIBLE) {
            visibleButtons++;
        }

        if (frontLayout.findViewById(R.id.button_front_6) != null &&
                frontLayout.findViewById(R.id.button_front_6).getVisibility() == View.VISIBLE) {
            visibleButtons++;
        }

        return visibleButtons;
    }

    public static void setupGestureDetector(AccountRowViewHolderV2 viewHolder) {
        AccountRowGestureListenerV2 gestureListener
                = new AccountRowGestureListenerV2(
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

    public static void populateData(AccountRowViewHolderV2 viewHolder) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

        viewHolder.accountNumber.setText(viewHolder.account.getMaskedAccountNumber());
        viewHolder.balanceDescription.setText(viewHolder.account.getBalanceDescription());
        viewHolder.balance.setText(numberFormat.format(viewHolder.account.getBalance() / 100));
        viewHolder.accountName.setText(viewHolder.account.getName());
    }

    public static void setupViewWidths(AccountRowViewHolderV2 viewHolder) {
        // Here we set the width of our scrollable area, its container and the divider to be the
        // screen width plus the size of any left scrollable buttons
        WindowManager windowManager = (WindowManager) App.context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int displayWidth = size.x;

        // calculate the scrollable size
        int frontLayoutWidth = displayWidth + ((int) App.context.getResources().getDimension(R.dimen.account_button_width) * viewHolder.account.getNumberOfButtons());

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

    public static void setupButtons(final AccountRowViewHolderV2 viewHolder) {

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

        for (Button button : viewHolder.frontButtons) {
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.frontLayout.animate().x(0);
                        Toast.makeText(App.context, "Front Press Me pressed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
