package com.example.android.uiplay.expandablelist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.android.uiplay.R;
import com.example.android.uiplay.databinding.FragmentExspandableListBinding;
import com.example.android.uiplay.databinding.ItemRowBinding;

public class ItemFragment extends Fragment {
    private String[] fruit = {"apple", "orange", "bannana", "peach"};
    private String[] animals = {"lion", "tiger", "elephant", "bear", "kangaro", "rhinoserous"};

    private FragmentExspandableListBinding binding;

    public static ItemFragment newInstance() {
        return new ItemFragment();
    }

    public ItemFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exspandable_list, container, false);
        binding = DataBindingUtil.bind(view);
        binding.setController(new ListsController(binding));

        binding.listView1.setAdapter(new ItemArrayAdapter(getActivity(), 0, fruit));
        binding.listView2.setAdapter(new ItemArrayAdapter(getActivity(), 0, animals));

        binding.listView1.post(new Runnable() {
            @Override
            public void run() {
                binding.getController().setListView1FullHeight(binding.listView1.getMeasuredHeight());
            }
        });

        binding.listView2.post(new Runnable() {
            @Override
            public void run() {
                binding.getController().setListView2FullHeight(binding.listView2.getMeasuredHeight());
            }
        });

        return view;
    }


    private class ItemArrayAdapter extends ArrayAdapter<String> {
        private LayoutInflater layoutInflater;
        public ItemArrayAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String itemString = getItem(position);

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_row, null);
            }

            ItemRowBinding itemRowBinding = DataBindingUtil.bind(convertView);

            itemRowBinding.rowNumber.setText(String.valueOf(position + 1));
            itemRowBinding.rowText.setText(itemString);

            return convertView;
        }
    }
}
