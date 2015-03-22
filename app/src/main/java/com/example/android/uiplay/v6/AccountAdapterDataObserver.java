package com.example.android.uiplay.v6;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class AccountAdapterDataObserver extends RecyclerView.AdapterDataObserver {
    private static final String TAG = "AccountAdapterDatObsrvr";

    @Override
    public void onChanged() {
        Log.d(TAG, "onChanged()");
        super.onChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        Log.d(TAG, "onItemRangeChanged()");
        super.onItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        Log.d(TAG, "onItemRangeInserted()");
        super.onItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        Log.d(TAG, "onItemRangeRemoved()");
        super.onItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        Log.d(TAG, "onItemRangeMoved()");
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
    }
}
