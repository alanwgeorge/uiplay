package com.example.android.uiplay;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.TextView;

import com.example.android.uiplay.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountListFragment extends ListFragment {
    private static final String TAG = "AccountListFragment";

    private AccountArrayAdapter accountArrayAdapter;

    public AccountListFragment() { }

    // TODO make number of buttons more dynamic
    public static int countVisibleFrontButtons(View frontLayout) {
        int visibleButtons = 0;

        if (frontLayout.findViewById(R.id.button_front_1).getVisibility() == View.VISIBLE) {
            visibleButtons++;
        }

        if (frontLayout.findViewById(R.id.button_front_2).getVisibility() == View.VISIBLE) {
            visibleButtons++;
        }

        if (frontLayout.findViewById(R.id.button_front_3).getVisibility() == View.VISIBLE) {
            visibleButtons++;
        }

        return visibleButtons;
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
        addAccountRow();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));
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

    public static class AccountRowGestureListener implements GestureDetector.OnGestureListener {
//        private static final String TAG = "AccountRowGestureListener";
        private float onDownFrontViewX;
        private View frontLayout;
        private ViewGroup listView;
        private Context context;

        public AccountRowGestureListener(Context context, View frontLayout, ViewGroup listView) {
            this.frontLayout = frontLayout;
            this.context = context;
            this.listView = listView;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            onDownFrontViewX = frontLayout.getX();
//            Log.d(TAG, hashCode() + ":onDown(" + e + ") x = " + onDownFrontViewX);
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

            int frontButtonsWidth = (int) context.getResources().getDimension(R.dimen.account_button_width) * countVisibleFrontButtons(frontLayout);
            int belowButtonWidth = (int) context.getResources().getDimension(R.dimen.button_below_width);
            int belowButtonPadding = (int) context.getResources().getDimension(R.dimen.button_below_padding);

            if ((motionEvent2.getRawX() - motionEvent1.getRawX()) > 0) { // scrolling right
                if (onDownFrontViewX == 0) {  // un-scrolled position
                    frontLayout.animate().x(frontButtonsWidth); // reveal front buttons
                } else if (onDownFrontViewX == -(belowButtonWidth + (belowButtonPadding * 2))) { // scrolled left below button is visible
                    frontLayout.animate().x(0); // return to un-scrolled position
                }
            } else { // scrolling left
                if (onDownFrontViewX == 0) { // un-scrolled position
                    frontLayout.animate().x(-(belowButtonWidth + (belowButtonPadding * 2))); // reveal bellow button
                } else if (onDownFrontViewX == frontButtonsWidth) { // scrolled right front button visible
                    frontLayout.animate().x(0); // return to un-scrolled position
                }
            }

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
                ViewGroup accountRow = (ViewGroup) listView.getChildAt(i);
                View frontLayout = accountRow.findViewById(R.id.front_layout);
                if (frontLayout != this.frontLayout && frontLayout.getX() != 0) {
                    frontLayout.animate().x(0);
                }
            }
        }

    }

    public static class ViewHolder {
        Account account;
        View accountRowItem;
        ViewGroup parentListView;

        Button pressMeBehindButton;
        List<Button> frontButtons = new ArrayList<>();
        View frontLayout;
        ViewGroup frontButtonsContainer;
        View initialVisibleLayout;
        View behindLayout;

        TextView accountNumber;
        TextView balanceDescription;
        TextView balance;
        TextView accountName;
    }
}
