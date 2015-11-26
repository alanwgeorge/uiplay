package com.example.android.uiplay.expandablelist;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.android.uiplay.R;
import com.example.android.uiplay.databinding.ChildMenuRowBinding;
import com.example.android.uiplay.databinding.FragmentExpandableListBinding;
import com.example.android.uiplay.databinding.GroupMenuRowBinding;

import java.util.HashMap;
import java.util.Map;

public class ItemFragment extends Fragment {
    private Map<String, String[]> menuGroups;

    public static ItemFragment newInstance() {
        return new ItemFragment();
    }

    public ItemFragment() {
        menuGroups = new HashMap<>();
        String[] fruits = {"apple", "orange", "banana", "peach"};
        menuGroups.put("fruits", fruits);
        String[] singles = {"just me"};
        menuGroups.put("singles", singles);
        String[] elements = {"carbon", "helium", "uranium", "plutonium", "neon", "gold", "lead", "silver", "copper", "lead"};
        menuGroups.put("elements", elements);
        String[] animals = {"lion", "tiger", "elephant", "bear", "kangaroo", "rhinoceros"};
        menuGroups.put("animals", animals);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expandable_list, container, false);
        FragmentExpandableListBinding binding = DataBindingUtil.bind(view);
        MenuController menuController = new MenuController();
        binding.setController(menuController);

        for (String groupMenuString : menuGroups.keySet()) {
            LinearLayout menuGroupView = (LinearLayout) inflater.inflate(R.layout.group_menu_row, binding.menuGroupLayout, false);
            GroupMenuRowBinding groupMenuRowBinding = DataBindingUtil.bind(menuGroupView);

            binding.menuGroupLayout.addView(menuGroupView);

            int childViewIndex = 0;
            for (String childMenuString : menuGroups.get(groupMenuString)) {
                LinearLayout menuChildView = (LinearLayout) inflater.inflate(R.layout.child_menu_row, binding.menuGroupLayout, false);
                ChildMenuRowBinding childMenuRowBinding = DataBindingUtil.bind(menuChildView);

                childMenuRowBinding.rowText.setText(childMenuString);
                childMenuRowBinding.rowNumber.setText(String.valueOf(++childViewIndex));

                groupMenuRowBinding.childListView.addView(menuChildView);

            }
            ViewGroup.LayoutParams params1 = groupMenuRowBinding.childListView.getLayoutParams();
            params1.height = 0;
            groupMenuRowBinding.childListView.setLayoutParams(params1);

            MenuGroupController menuGroupController = new MenuGroupController(
                    groupMenuRowBinding,
                    menuController,
                    groupMenuString,
                    (int) getResources().getDimension(R.dimen.child_menu_height)
            );

            groupMenuRowBinding.setController(menuGroupController);
            menuController.observe(menuGroupController);
        }

        return view;
    }
}
