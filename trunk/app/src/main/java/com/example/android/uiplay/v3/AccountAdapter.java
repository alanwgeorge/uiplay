package com.example.android.uiplay.v3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.uiplay.AccountRowBuilder;
import com.example.android.uiplay.AccountRowViewHolder;
import com.example.android.uiplay.R;
import com.example.android.uiplay.model.Account;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountRowViewHolder> {
//    private static final String TAG = "AccountAdapter";
    private ArrayList<Account> accounts = new ArrayList<>();

    @Override
    public AccountRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_row, parent, false);
        AccountRowViewHolder accountRowViewHolder = new AccountRowViewHolder(view);
        accountRowViewHolder.parent = parent;

        return accountRowViewHolder;
    }

    @Override
    public void onBindViewHolder(AccountRowViewHolder holder, int position) {
        holder.account = accounts.get(position);
        AccountRowBuilder.setupGestureDetector(holder);
        AccountRowBuilder.setupButtons(holder);
        AccountRowBuilder.setupViewWidths(holder);
        AccountRowBuilder.populateData(holder);
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public void add(Account account) {
        accounts.add(account);
        notifyItemInserted(accounts.size());
    }
}
