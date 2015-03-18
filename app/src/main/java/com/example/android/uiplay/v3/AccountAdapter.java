package com.example.android.uiplay.v3;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.uiplay.AccountRowBuilder;
import com.example.android.uiplay.AccountRowViewHolder;
import com.example.android.uiplay.R;
import com.example.android.uiplay.model.Account;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountRowViewHolder> {
    private static final String TAG = "AccountAdapter";

    @IntDef({VIEW_TYPE_BUTTONS_1, VIEW_TYPE_BUTTONS_2, VIEW_TYPE_BUTTONS_3})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NumButtons {}

    public static final int VIEW_TYPE_BUTTONS_1 = 1;
    public static final int VIEW_TYPE_BUTTONS_2 = 2;
    public static final int VIEW_TYPE_BUTTONS_3 = 3;

    private ArrayList<Account> accounts = new ArrayList<>();

    @Nullable
    @Override
    public AccountRowViewHolder onCreateViewHolder(ViewGroup parent, @NumButtons int viewType) {
        Log.d(TAG, "onCreateViewHolder()");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_row, parent, false);

        AccountRowViewHolder accountRowViewHolder = new AccountRowViewHolder(view);
        accountRowViewHolder.parent = parent;

        view.setTag(accountRowViewHolder);

        return accountRowViewHolder;
    }

    @Override
    public void onBindViewHolder(AccountRowViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder()");
        holder.account = accounts.get(position);
        AccountRowBuilder.setupGestureDetector(holder);
        AccountRowBuilder.setupButtons(holder);
        AccountRowBuilder.setupViewWidths(holder);
        AccountRowBuilder.populateData(holder);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount()");
        return accounts.size();
    }

    public void add(Account account) {
        Log.d(TAG, "add()");
        accounts.add(account);
        notifyItemInserted(accounts.size());
    }
}
