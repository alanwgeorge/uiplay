package com.example.android.uiplay.expandabletoolbar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.uiplay.R;

import java.util.ArrayList;


public class DetailAdapter extends RecyclerView.Adapter<DetailViewHolder> {
    private ArrayList<Detail> details = new ArrayList<>();

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_row, parent, false));
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        holder.getBinding().setController(new DetailController(details.get(position)));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public void add(Detail detail) {
        details.add(detail);
        notifyItemInserted(details.size());
    }
}
