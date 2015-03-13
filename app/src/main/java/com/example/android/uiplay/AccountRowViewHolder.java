package com.example.android.uiplay;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.uiplay.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountRowViewHolder extends RecyclerView.ViewHolder {
    public Account account;
    public View accountRowItem;
    public ViewGroup parent;

    public Button pressMeBehindButton;
    public List<Button> frontButtons = new ArrayList<>();
    public View frontLayout;
    public ViewGroup frontButtonsContainer;
    public View initialVisibleLayout;
    public View behindLayout;
    public View divider;

    public TextView accountNumber;
    public TextView balanceDescription;
    public TextView balance;
    public TextView accountName;

    public AccountRowViewHolder(View accountRowItem) {
        super(accountRowItem);
        this.accountRowItem = accountRowItem;
        pressMeBehindButton = (Button) accountRowItem.findViewById(R.id.button_below);
        frontButtons.add((Button) accountRowItem.findViewById(R.id.button_front_1));
        frontButtons.add((Button) accountRowItem.findViewById(R.id.button_front_2));
        frontButtons.add((Button) accountRowItem.findViewById(R.id.button_front_3));
        frontLayout = accountRowItem.findViewById(R.id.front_layout);
        frontButtonsContainer = (ViewGroup) accountRowItem.findViewById(R.id.front_buttons_container);
        initialVisibleLayout = accountRowItem.findViewById(R.id.account_details_container);
        behindLayout = accountRowItem.findViewById(R.id.behind_layout);
        divider = accountRowItem.findViewById(R.id.account_list_divider);

        accountNumber = (TextView) accountRowItem.findViewById(R.id.account_number);
        balanceDescription = (TextView) accountRowItem.findViewById(R.id.balance_description);
        balance = (TextView) accountRowItem.findViewById(R.id.balance);
        accountName = (TextView) accountRowItem.findViewById(R.id.account_name);
    }
}
