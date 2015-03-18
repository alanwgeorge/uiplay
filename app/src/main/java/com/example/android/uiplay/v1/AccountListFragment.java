package com.example.android.uiplay.v1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.android.uiplay.AccountRowBuilder;
import com.example.android.uiplay.MainActivity;
import com.example.android.uiplay.R;
import com.example.android.uiplay.model.Account;

import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class AccountListFragment extends Fragment {
    private static final String TAG = "AccountListFragment";

    private ViewGroup fragmentContainer;
    private ScrollView scrollView;

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
                addAccountRow(fragmentContainer);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_account_list, container, false);
        fragmentContainer = (ViewGroup) rootView.findViewById(R.id.fragment_container);
        scrollView = (ScrollView) rootView.findViewById(R.id.base_scrollview);

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
        View fragmentContent = getActivity().getLayoutInflater().inflate(R.layout.account_row, container, false);

        Random random = new Random();
        Account account = new Account(
                "My Account" + container.getChildCount(),
                random.nextInt(100000000),
                "Available Balance", "..." + random.nextInt(10000),
                random.nextInt(3) + 1);

        container.addView(AccountRowBuilder.setupAccountRow(fragmentContent, account, null, container));
        container.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}
