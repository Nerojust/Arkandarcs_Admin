package com.nerojust.arkandarcsadmin.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.adapters.PageViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadyToShipFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ReadyToShipFragment() {
        // Required empty public constructor
    }

    public static ShippedFragment newInstance(int index) {
        ShippedFragment fragment = new ShippedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageViewModel pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ready_to_ship, container, false);
    }

}
