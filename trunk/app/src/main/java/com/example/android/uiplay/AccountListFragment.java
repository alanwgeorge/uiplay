package com.example.android.uiplay;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.uiplay.model.Account;

import java.util.ArrayList;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
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
                Log.d(TAG, "add item menu selected");
                addAccountRow();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        accountArrayAdapter = new AccountArrayAdapter(getActivity(), 0, new ArrayList<Account>());
        setListAdapter(accountArrayAdapter);
        accountArrayAdapter.setNotifyOnChange(true);
        accountArrayAdapter.add(new Account("My Account", 100000, "Available Balance", "...5463"));

        return rootView;
    }

    private void addAccountRow() {
        Random random = new Random();
        accountArrayAdapter.add(new Account("My Account" + getListAdapter().getCount(), random.nextInt(100000000), "Available Balance", "...5463"));
    }

}
