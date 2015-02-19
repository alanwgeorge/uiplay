package com.example.android.uiplay;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.uiplay.model.Account;

import java.util.List;

class AccountArrayAdapter extends ArrayAdapter<Account> {
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
        ViewHolder viewHolder;

        if (convertView != null) {
            fragmentContent = convertView;
            viewHolder = (ViewHolder) fragmentContent.getTag();
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            fragmentContent = layoutInflater.inflate(R.layout.item_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.pressMeBehindButton = (Button) fragmentContent.findViewById(R.id.button_below);
            viewHolder.pressMeFrontButton = (Button) fragmentContent.findViewById(R.id.button_front);
            viewHolder.frontLayout = fragmentContent.findViewById(R.id.front_layout);
            viewHolder.frontButtonsContainer = (ViewGroup) fragmentContent.findViewById(R.id.front_buttons_container);
            viewHolder.initialVisibleLayout = fragmentContent.findViewById(R.id.account_details_container);
            viewHolder.behindLayout = fragmentContent.findViewById(R.id.behind_layout);

            fragmentContent.setTag(viewHolder);
        }

        final View frontLayoutFinal = viewHolder.frontLayout;

        viewHolder.pressMeBehindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontLayoutFinal.animate().x(0);
                frontLayoutFinal.requestLayout();
                Toast.makeText(context, "Behind Press Me pressed", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.pressMeFrontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontLayoutFinal.animate().x(0);
                frontLayoutFinal.requestLayout();
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
        int fragmentContentWidth = displayWidth + ((int) context.getResources().getDimension(R.dimen.account_button_width) * viewHolder.frontButtonsContainer.getChildCount());

        // set the "front" views and their containers width to accommodate for the off screen buttons
        ViewGroup.LayoutParams params = parent.getLayoutParams();
        params.width = fragmentContentWidth;
        parent.setLayoutParams(params);

        params = fragmentContent.getLayoutParams();
        params.width = fragmentContentWidth;
        fragmentContent.setLayoutParams(params);

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

        return fragmentContent;
    }

    private static class ViewHolder {
        Button pressMeBehindButton;
        Button pressMeFrontButton;
        View frontLayout;
        ViewGroup frontButtonsContainer;
        View initialVisibleLayout;
        View behindLayout;

    }
}
