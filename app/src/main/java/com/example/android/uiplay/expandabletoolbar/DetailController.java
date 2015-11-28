package com.example.android.uiplay.expandabletoolbar;

import java.text.DateFormat;
import java.text.NumberFormat;

public class DetailController {
    private Detail detail;
    public static final DateFormat dateFormat = DateFormat.getDateInstance();
    public static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    public DetailController(Detail detail) {
        this.detail = detail;
    }

    public String getDate() {
        return dateFormat.format(detail.getDate());
    }

    public String getDescription() {
        return detail.getDescription();
    }

    public String getAmount() {
        return currencyFormat.format(detail.getAmount() / 100d);
    }
}
