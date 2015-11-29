package com.example.android.uiplay.expandabletoolbar;

import android.view.View;

import java.util.Date;

@SuppressWarnings("unused")
public class DetailsController {
    private DetailAdapter detailAdapter;

    public DetailsController(DetailAdapter detailAdapter) {
        this.detailAdapter = detailAdapter;
    }

    public void addDetail() {
        detailAdapter.add(new Detail("Description of account detail", 12345, new Date()));
    }

    public View.OnClickListener getClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDetail();
            }
        };
    }
}
