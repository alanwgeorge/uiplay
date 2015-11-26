package com.example.android.uiplay.expandablelist;

import android.view.View;

@SuppressWarnings("unused")
public class MenuItemController {
    public View.OnClickListener getMenuItemClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);
            }
        };
    }
}
