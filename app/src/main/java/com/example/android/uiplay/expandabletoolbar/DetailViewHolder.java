package com.example.android.uiplay.expandabletoolbar;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.uiplay.databinding.DetailRowBinding;

public class DetailViewHolder extends RecyclerView.ViewHolder {
    private DetailRowBinding binding;

    public DetailViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public DetailRowBinding getBinding() {
        return binding;
    }
}
