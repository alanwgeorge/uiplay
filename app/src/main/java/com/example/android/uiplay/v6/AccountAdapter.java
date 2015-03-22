package com.example.android.uiplay.v6;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.uiplay.R;
import com.example.android.uiplay.model.Account;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountRowViewHolderV2> {
    private static final String TAG = "AccountAdapter";

    @IntDef({
            VIEW_TYPE_BUTTONS_1,
            VIEW_TYPE_BUTTONS_2,
            VIEW_TYPE_BUTTONS_3,
            VIEW_TYPE_BUTTONS_4,
            VIEW_TYPE_BUTTONS_5,
            VIEW_TYPE_BUTTONS_6
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface NumButtons {}

    public static final int VIEW_TYPE_BUTTONS_1 = 1;
    public static final int VIEW_TYPE_BUTTONS_2 = 2;
    public static final int VIEW_TYPE_BUTTONS_3 = 3;
    public static final int VIEW_TYPE_BUTTONS_4 = 4;
    public static final int VIEW_TYPE_BUTTONS_5 = 5;
    public static final int VIEW_TYPE_BUTTONS_6 = 6;

    private ArrayList<Account> accounts = new ArrayList<>();

    @Nullable
    @Override
    public AccountRowViewHolderV2 onCreateViewHolder(ViewGroup parent, @NumButtons int viewType) {
        Log.d(TAG, "onCreateViewHolder()");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_row_v2, parent, false);

        AccountRowViewHolderV2 accountRowViewHolder = new AccountRowViewHolderV2(view);
        accountRowViewHolder.parent = parent;

        view.setTag(accountRowViewHolder);

        return accountRowViewHolder;
    }

    @Override
    public void onBindViewHolder(AccountRowViewHolderV2 holder, int position) {
        Log.d(TAG, "onBindViewHolder()");
        holder.account = accounts.get(position);
        AccountRowBuilderV2.setupGestureDetector(holder);
        AccountRowBuilderV2.setupViewWidths(holder);
        AccountRowBuilderV2.setupButtons(holder);
        AccountRowBuilderV2.populateData(holder);
    }

    @Override
    public int getItemViewType(int position) {
        return accounts.get(position).getNumberOfButtons();
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
