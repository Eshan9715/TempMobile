package com.example.hivemqmobile;

import static com.example.hivemqmobile.Settings.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Temp extends Fragment {
    View v;
    TextView temCel, temFra;
    ImageView tempGiff;
    String tem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_temp, container, false);

        temCel = v.findViewById(R.id.room_cel);
        temFra = v.findViewById(R.id.room_fah);
        tempGiff = v.findViewById(R.id.tempGif);

        Bundle bundle = getArguments();
        if (bundle != null) {
            tem = bundle.getString("m0h");
        }

//        int qos = 1;
//        try{
//            client.subscribe(tem, qos);
//        }catch (MqttException e){
//            e.printStackTrace();
//        }
//        client.setCallback(new MqttCallback() {
//            @Override
//            public void connectionLost(Throwable cause) {
//
//            }
//            @Override
//            public void messageArrived(String topic, MqttMessage message) throws Exception {
//                String msg = new String(message.getPayload());
//                String setMsg = msg + " C";
//                temCel.setText(setMsg);
//                double temp = Double.parseDouble(msg);
//                double temp_fah = (temp * 1.8) +32;
//                double roundDbl = Math.round(temp_fah*100.0)/100.0;
//                String fran = String.valueOf(roundDbl);
//                temFra.setText(fran + " F");
//                Toast.makeText(Temp.this.getContext(), "Message received", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken token) {
//
//            }
//        });

        Glide.with(this).load(R.drawable.temp).into(tempGiff);

        return  v;
    }
}