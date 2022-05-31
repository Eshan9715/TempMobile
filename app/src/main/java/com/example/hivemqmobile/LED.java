package com.example.hivemqmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.card.MaterialCardView;


public class LED extends Fragment {
    View v;
    MaterialCardView onB, offB;
    ImageView bulbON, bulbOFF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_l_e_d, container, false);

        onB = v.findViewById(R.id.l1);
        offB = v.findViewById(R.id.l2);
        bulbON = v.findViewById(R.id.ledOn);
        bulbOFF = v.findViewById(R.id.ledOff);

        bulbON.setVisibility(View.INVISIBLE);

        onB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bulbOFF.setVisibility(View.INVISIBLE);
                bulbON.setVisibility(View.VISIBLE);


            }
        });

        offB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bulbON.setVisibility(View.INVISIBLE);
                bulbOFF.setVisibility(View.VISIBLE);
            }
        });





        return v;
    }
}