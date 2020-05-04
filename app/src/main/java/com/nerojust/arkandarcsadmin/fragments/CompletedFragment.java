package com.nerojust.arkandarcsadmin.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nerojust.arkandarcsadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";
    public CompletedFragment() {
        // Required empty public constructor
    }

    public static CompletedFragment newInstance(int index) {
        CompletedFragment fragment = new CompletedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed, container, false);
    }

}
