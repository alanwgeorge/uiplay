package com.example.android.uiplay.expandablelist;

import android.view.View;
import android.view.ViewGroup;

import com.example.android.uiplay.App;
import com.example.android.uiplay.R;
import com.example.android.uiplay.databinding.GroupMenuRowBinding;

import org.antlr.v4.runtime.misc.NotNull;

public class MenuGroupController {
    private GroupMenuRowBinding binding;
    private MenuController menuController;
    private String label;
    private int fullHeight = -1;
    private int progress = 0;

    public MenuGroupController(@NotNull GroupMenuRowBinding binding, @NotNull MenuController menuController, @NotNull String label, int childHeightPx) {
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
            binding.rowText.setSelected(true);
        } else if (progress < this.progress && isExpanded()){
            binding.rowText.setSelected(false);
        }
        this.progress = progress;
        ViewGroup.LayoutParams params1 = binding.childListView.getLayoutParams();
        params1.height = (int) ((progress / 100f) * fullHeight);
        binding.childListView.setLayoutParams(params1);
    }
}
