package com.example.android.uiplay.v6;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.uiplay.R;
import com.example.android.uiplay.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountRowViewHolderV2 extends RecyclerView.ViewHolder {
    public Account account;
    public View accountRowItem;
    public ViewGroup parent;

    public List<Button> frontButtons = new ArrayList<>();
    public View frontLayout;
    public ViewGroup frontButtonsContainer;
    public View initialVisibleLayout;
    public View divider;

    public TextView accountNumber;
    public TextView balanceDescription;
    public TextView balance;
    public TextView accountName;

    public AccountRowViewHolderV2(View accountRowItem) {
        super(accountRowItem);
        this.accountRowItem = accountRowItem;
        frontButtons.add((Button) accountRowItem.findViewById(R.id.button_front_1));
        frontButtons.add((Button) accountRowItem.findViewById(R.id.button_front_2));
        frontButtons.add((Button) accountRowItem.findViewById(R.id.button_front_3));
        frontButtons.add((Button) accountRowItem.findViewById(R.id.button_front_4));
        frontButtons.add((Button) accountRowItem.findViewById(R.id.button_front_5));
        frontButtons.add((Button) accountRowItem.findViewById(R.id.button_front_6));
        frontLayout = accountRowItem.findViewById(R.id.front_layout);
        frontButtonsContainer = (ViewGroup) accountRowItem.findViewById(R.id.front_buttons_container);
        initialVisibleLayout = accountRowItem.findViewById(R.id.account_details_container);
        divider = accountRowItem.findViewById(R.id.account_list_divider);

        accountNumber = (TextView) accountRowItem.findViewById(R.id.account_number);
        balanceDescription = (TextView) accountRowItem.findViewById(R.id.balance_description);
        balance = (TextView) accountRowItem.findViewById(R.id.balance);
        accountName = (TextView) accountRowItem.findViewById(R.id.account_name);
    }
}
