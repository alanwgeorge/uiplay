package com.example.android.uiplay;

import android.animation.LayoutTransition;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        LayoutTransition layoutTransition = new LayoutTransition();
        listView.setLayoutTransition(layoutTransition);

        accountArrayAdapter = new AccountArrayAdapter(getActivity(), 0, new ArrayList<Account>());
        setListAdapter(accountArrayAdapter);
        accountArrayAdapter.setNotifyOnChange(true);
        addAccountRow();

        return rootView;
    }

    private void addAccountRow() {
        Random random = new Random();
        accountArrayAdapter.add(new Account("My Account" + getListAdapter().getCount(), random.nextInt(100000000), "Available Balance", "..." + random.nextInt(10000)));
    }

    public static class AccountRowGestureListener implements GestureDetector.OnGestureListener {
//        private static final String TAG = "AccountRowGestureListener";
        private float onDownFrontViewX;
        private View view;
        private ViewGroup listView;
        private Context context;

        public AccountRowGestureListener(Context context, View view, ViewGroup listView) {
            this.view = view;
            this.context = context;
            this.listView = listView;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            onDownFrontViewX = view.getX();
//        Log.d(TAG, "onDown(" + e + ") x = " + onDownFrontViewX);
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
//        Log.d(TAG, "onShowPress(" + e + ")");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "onSingleTapUp(" + e + ")");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent1, MotionEvent motionEvent2, float distanceX, float distanceY) {
//        Log.d(TAG, "onScroll(" + motionEvent1 + ", " + motionEvent2 + ", " + distanceX + ", " + distanceY + ")");
//        Log.d(TAG, "getX() = " + getX());

            resetOtherAccountRows();

            int frontButtonWidth = (int) context.getResources().getDimension(R.dimen.account_button_width);
            int belowButtonWidth = (int) context.getResources().getDimension(R.dimen.button_below_width);
            int belowButtonPadding = (int) context.getResources().getDimension(R.dimen.button_below_padding);

            if ((motionEvent2.getRawX() - motionEvent1.getRawX()) > 0) { // scrolling right
                if (onDownFrontViewX == 0) {  // un-scrolled position
                    view.animate().x(frontButtonWidth); // reveal front button
                } else if (onDownFrontViewX == -(belowButtonWidth + (belowButtonPadding * 2))) { // scrolled left below button visible
                    view.animate().x(0); // return to un-scrolled position
                }
            } else { // scrolling left
                if (onDownFrontViewX == 0) { // un-scrolled position
                    view.animate().x(-(belowButtonWidth + (belowButtonPadding * 2))); // reveal bellow button
                } else if (onDownFrontViewX == frontButtonWidth) { // scrolled right front button visible
                    view.animate().x(0); // return to un-scrolled position
                }
            }

            view.requestLayout();

            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
//        Log.d(TAG, "onLongPress(" + e + ")");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        Log.d(TAG, "onFling(" + e1 + ", " + e2 + ", " + velocityX + ", " + velocityY + ")");
            return false;
        }

        private void resetOtherAccountRows() {
            for (int i = 0; i < listView.getChildCount(); i++) {
                ViewGroup itemView = (ViewGroup) listView.getChildAt(i);
                View frontView = itemView.findViewById(R.id.front_layout);
                if (frontView != view && frontView.getX() != 0) {
                    frontView.animate().x(0);
                }
            }
        }

    }
}
