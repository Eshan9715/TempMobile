package com.example.hivemqmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Temp extends Fragment {
    View v;
    TextView temCel, temFra;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_temp, container, false);

        temCel = v.findViewById(R.id.room_cel);
        temFra = v.findViewById(R.id.room_fah);


        return  v;
    }
}