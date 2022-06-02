package com.example.hivemqmobile;

import static com.example.hivemqmobile.Settings.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;


public class LED extends Fragment {
    View v;
    MaterialCardView onB, offB;
    ImageView bulbON, bulbOFF,ledGif;
    String let;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_l_e_d, container, false);

        onB = v.findViewById(R.id.l1);
        offB = v.findViewById(R.id.l2);
        bulbON = v.findViewById(R.id.ledOn);
        bulbOFF = v.findViewById(R.id.ledOff);
        ledGif = v.findViewById(R.id.ledGif);

        bulbON.setVisibility(View.INVISIBLE);
        bulbOFF.setVisibility(View.INVISIBLE);

        Bundle bundle1 = getArguments();
        if (bundle1 != null) {
            let = bundle1.getString("m1h");
        }

        Glide.with(this).load(R.drawable.bulb).into(ledGif);

        onB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bulbOFF.setVisibility(View.INVISIBLE);
                bulbON.setVisibility(View.VISIBLE);
                ledGif.setVisibility(View.INVISIBLE);

                String topic = let;
                String payload = "on";
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
                Toast.makeText(LED.this.getContext(),"LED on!", Toast.LENGTH_SHORT).show();

            }
        });

        offB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bulbON.setVisibility(View.INVISIBLE);
                bulbOFF.setVisibility(View.VISIBLE);
                ledGif.setVisibility(View.INVISIBLE);

                String topic = let;
                String payload = "off";
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
                Toast.makeText(LED.this.getContext(),"LED off!", Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }
}