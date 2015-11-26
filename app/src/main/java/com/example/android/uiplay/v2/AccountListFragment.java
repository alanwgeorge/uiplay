package com.example.android.uiplay.v2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.uiplay.R;
import com.example.android.uiplay.model.Account;

import java.util.ArrayList;
import java.util.Random;

public class AccountListFragment extends ListFragment {
    private static final String TAG = "AccountListFragment";

    private AccountArrayAdapter accountArrayAdapter;

    public AccountListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add_item:
                addAccountRow();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_account_list_listview, container, false);

        accountArrayAdapter = new AccountArrayAdapter(getActivity(), 0, new ArrayList<Account>());
        setListAdapter(accountArrayAdapter);
        accountArrayAdapter.setNotifyOnChange(true);
        addAccountRow();

        return rootView;
    }

    private void addAccountRow() {
        Random random = new Random();

        int numberOfButtons = random.nextInt(3) + 1;
        Log.d(TAG, "numberOfButtons: "+ numberOfButtons);
        accountArrayAdapter.add(new Account(
                "My Account" + getListAdapter().getCount(),
                random.nextInt(100000000),
                "Available Balance", "..." + random.nextInt(10000),
                numberOfButtons));
    }

}
