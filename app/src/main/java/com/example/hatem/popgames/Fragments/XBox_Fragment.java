package com.example.hatem.popgames.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hatem.popgames.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class XBox_Fragment extends Fragment {


    public XBox_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_xbox_, container, false);
    }

}
