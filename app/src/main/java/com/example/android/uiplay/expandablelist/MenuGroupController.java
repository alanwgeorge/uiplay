package com.example.android.uiplay.expandablelist;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.uiplay.App;
import com.example.android.uiplay.R;
import com.example.android.uiplay.databinding.GroupMenuRowBinding;

public class MenuGroupController {
    private GroupMenuRowBinding binding;
    private MenuController menuController;
    private String label;
    private int fullHeight = -1;
    private int progress = 0;

    public MenuGroupController(@NonNull GroupMenuRowBinding binding, @NonNull MenuController menuController, @NonNull String label, int childHeightPx) {
        this.binding = binding;
        this.menuController = menuController;
        this.label = label;

        fullHeight = childHeightPx * binding.childListView.getChildCount();
    }

    @SuppressWarnings("unused")
    public View.OnClickListener getGroupMenuListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuController.onMenuGroupClick(MenuGroupController.this);
            }
        };
    }

    public boolean isExpanded() {
        return progress == 100;
    }

    public boolean isCollapsed() {
        return progress == 0;
    }

    @SuppressWarnings("unused")
    public String getLabel() {
        return label;
    }

    @SuppressWarnings("deprecation")
    public void setProgress(int progress) {
        if (progress > this.progress && isCollapsed()) {
            binding.rowText.setBackgroundColor(App.context.getResources().getColor(R.color.secondary_1_3));
        } else if (progress < this.progress && isExpanded()){
            binding.rowText.setBackgroundColor(App.context.getResources().getColor(R.color.secondary_1_2));
        }
        this.progress = progress;
        ViewGroup.LayoutParams params1 = binding.childListView.getLayoutParams();
        params1.height = (int) ((progress / 100f) * fullHeight);
        binding.childListView.setLayoutParams(params1);
    }
}
