package com.example.android.uiplay.expandablelist;


import android.animation.ValueAnimator;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.widget.SeekBar;

import com.example.android.uiplay.BR;

import java.util.ArrayList;
import java.util.List;

public class MenuController extends BaseObservable {

    private List<MenuGroupController> menuGroupControllers = new ArrayList<>();
    private long animationDuration = 300;

    @Bindable
    public String getAnimationDuration() {
        return String.valueOf(animationDuration) + " Milliseconds";
    }

    public void observe(@NonNull MenuGroupController controller) {
        menuGroupControllers.add(controller);
    }

    public void onMenuGroupClick(@NonNull final MenuGroupController menuGroupController) {
        final boolean isTargetExpanding =! menuGroupController.isExpanded();
        final List<MenuGroupController> collapsibleList = new ArrayList<>();

        for (MenuGroupController groupController : menuGroupControllers) {
            if (groupController.isExpanded()) {
                collapsibleList.add(groupController);
            }
        }

        ValueAnimator animator = ValueAnimator.ofInt(0, 100).setDuration(animationDuration);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (Integer) animation.getAnimatedValue();

                if (isTargetExpanding) {
                    menuGroupController.setProgress(progress);
                    for (MenuGroupController collapsing : collapsibleList) {
                        collapsing.setProgress(100 - progress);
                    }
                } else {
                    menuGroupController.setProgress(100 - progress);
                }
            }
        });

        animator.start();
    }

    @SuppressWarnings("unused")
    public SeekBar.OnSeekBarChangeListener getSeekBarListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                animationDuration = progress + 100;
                notifyPropertyChanged(BR.animationDuration);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        };
    }

}
