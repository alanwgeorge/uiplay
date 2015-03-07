package com.example.android.uiplay;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.uiplay.model.Account;

import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class AccountListFragment2 extends Fragment {
    private static final String TAG = "AccountListFragment2";

    private ViewGroup fragmentContainer;

    public AccountListFragment2() { }

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
                addAccountRow(fragmentContainer);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main2, container, false);
        fragmentContainer = (ViewGroup) rootView.findViewById(R.id.fragment_container);

        addAccountRow(fragmentContainer);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));
    }

    private void addAccountRow(ViewGroup container) {
        View fragmentContent = getActivity().getLayoutInflater().inflate(R.layout.item_row2, container, false);

        Random random = new Random();
        Account account = new Account(
                "My Account" + container.getChildCount(),
                random.nextInt(100000000),
                "Available Balance", "..." + random.nextInt(10000),
                random.nextInt(3) + 1);

        container.addView(AccountArrayAdapter.setupAccountRow(fragmentContent, account, null, container));
    }
}
