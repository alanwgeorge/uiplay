package com.example.android.uiplay.v6;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.uiplay.MainActivity;
import com.example.android.uiplay.R;
import com.example.android.uiplay.model.Account;

import java.util.Random;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class AccountListFragment extends Fragment {
    public static final String TAG = "AccountListFragment";
    private RecyclerView recyclerView;
    private AccountAdapter accountAdapter;

    public AccountListFragment() {
        // Required empty public constructor
    }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_account_list_recyclerview, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        accountAdapter = new AccountAdapter();
        accountAdapter.registerAdapterDataObserver(new AccountAdapterDataObserver());
        recyclerView.setAdapter(accountAdapter);

        addAccountRow();

        return rootView;
    }

    private void addAccountRow() {
        Random random = new Random();
        Account account = new Account(
                "My Account" + accountAdapter.getItemCount(),
                random.nextInt(100000000),
                "Available Balance", "..." + random.nextInt(10000),
                random.nextInt(6) + 1);

        accountAdapter.add(account);
        recyclerView.scrollToPosition(accountAdapter.getItemCount() - 1);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));
    }
}
