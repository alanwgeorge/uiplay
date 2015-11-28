package com.example.android.uiplay.expandabletoolbar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.uiplay.R;

import java.util.ArrayList;

import timber.log.Timber;

public class DetailAdapter extends RecyclerView.Adapter<DetailViewHolder> {
    private ArrayList<Detail> details = new ArrayList<>();

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Timber.d("onCreateViewHolder([parent, viewType])");
        return new DetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_row, parent, false));
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        Timber.d("onBindViewHolder([holder, position])");
        if (position == 0) {
            holder.getBinding().divider.setVisibility(View.INVISIBLE);
        }
        holder.getBinding().setController(new DetailController(details.get(position)));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        Timber.d("getItemCount(%d)", details.size());
        return details.size();
    }

    public void add(Detail detail) {
        Timber.d("add(%s)", detail);
        details.add(detail);
        notifyItemInserted(details.size());
    }
}
