package com.example.android.uiplay.expandabletoolbar;

import android.support.v7.widget.RecyclerView;

import timber.log.Timber;

public class DetailAdapterDataObserver extends RecyclerView.AdapterDataObserver {
    @Override
    public void onChanged() {
        Timber.d("onChanged()");
        super.onChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        Timber.d("onItemRangeChanged()");
        super.onItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        Timber.d("onItemRangeInserted()");
        super.onItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        Timber.d("onItemRangeRemoved()");
        super.onItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        Timber.d("onItemRangeMoved()");
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
    }
}
