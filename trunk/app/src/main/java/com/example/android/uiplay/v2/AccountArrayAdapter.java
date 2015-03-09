package com.example.android.uiplay.v2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.android.uiplay.AccountRowBuilder;
import com.example.android.uiplay.AccountRowViewHolder;
import com.example.android.uiplay.R;
import com.example.android.uiplay.model.Account;

import java.util.List;

public class AccountArrayAdapter extends ArrayAdapter<Account> {
    private static final String TAG = "AccountArrayAdapter";
    Context context;

    public AccountArrayAdapter(Context context, int resource, List<Account> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    /**
     * Here we generate our account row.  In order to get our off screen buttons to work, we have to
     * calculate and adjust various views here based on the device width at runtime.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View accountRowItem;
        AccountRowViewHolder viewHolder = null;
        Account account = getItem(position);

        if (convertView != null) {
            Log.d(TAG, "reusing view: position " + position);
            accountRowItem = convertView;
            viewHolder = (AccountRowViewHolder) accountRowItem.getTag();
        } else {
            Log.d(TAG, "creating new view: position " + position);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            accountRowItem = layoutInflater.inflate(R.layout.account_row, parent, false);
        }

        return AccountRowBuilder.setupAccountRow(accountRowItem, account, viewHolder, parent);
    }
}
