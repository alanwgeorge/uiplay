package com.example.android.uiplay.expandablelist;


import android.view.ViewGroup;
import android.widget.SeekBar;
import com.example.android.uiplay.databinding.FragmentExspandableListBinding;

public class ListsController {
    public FragmentExspandableListBinding binding;
    private int listView1FullHeight = -1;
    private int listView2FullHeight = -1;

    public ListsController(FragmentExspandableListBinding binding) {
        this.binding = binding;
    }

    public void setListView2FullHeight(int listView2FullHeight) {
        this.listView2FullHeight = listView2FullHeight;
    }

    public void setListView1FullHeight(int listView1FullHeight) {
        this.listView1FullHeight = listView1FullHeight;
    }


    public SeekBar.OnSeekBarChangeListener getSeekBarListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.progressText.setText(String.valueOf(progress));

                ViewGroup.LayoutParams params1 = binding.listView1.getLayoutParams();
                params1.height = (int) ((progress / 100f) * listView1FullHeight);
                binding.listView1.setLayoutParams(params1);

                ViewGroup.LayoutParams params2 = binding.listView2.getLayoutParams();
                params2.height = (int) (((100 - progress) / 100f) * listView2FullHeight);
                binding.listView2.setLayoutParams(params2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        };
    }
}
