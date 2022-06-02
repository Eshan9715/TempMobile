package com.example.hivemqmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Settings extends AppCompatActivity {
    SwitchCompat  switch_connect;
    static MqttAndroidClient client;
    ImageView iot, backWelcome;
    Button cont;
    boolean hiveConnect = false;
    TextInputEditText tempTopic, ledTopic;

    static String mqtt_host = "tcp://broker.mqttdashboard.com:1883";
    String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        switch_connect = findViewById(R.id.switch_con);
        iot = findViewById(R.id.iot);
        tempTopic = findViewById(R.id.text_tem);
        ledTopic = findViewById(R.id.text_led);
        backWelcome = findViewById(R.id.back_sett);


        cont = findViewById(R.id.btn_sett);

        backWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, welcome.class));
            }
        });

        switch_connect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    Connect();
                } else {
                    Disconnect();
                }
            }
        });

        // Adding the gif here using glide library
        Glide.with(this).load(R.drawable.iot).into(iot);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (hiveConnect) {
//                    String TT = tempTopic.getText().toString();
//                    String LT = ledTopic.getText().toString();
//
//                    if (TT.isEmpty()) {
//                        tempTopic.setError("Enter temperature topic!");
//                        tempTopic.requestFocus();
//                    }else if (LT.isEmpty()) {
//                        ledTopic.setError("Enter led topic!");
//                        ledTopic.requestFocus();
//
//                    } else {
//
//                        LED lamp = new LED();
//                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//                        Bundle bundle1 = new Bundle();
//                        bundle1.putString("m1h",LT);
//
//                        lamp.setArguments(bundle1);
//                        fragmentTransaction.replace(R.id.frameLayout,lamp).commit();
//
//                        Temp fan = new Temp();
//                        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
//
//                        Bundle bundle = new Bundle();
//                        bundle.putString("mOh",TT);
//
//
//                        fan.setArguments(bundle);
//                        fragmentTransaction2.replace(R.id.frameLayout,fan).commit();

                        Intent homeAct = new Intent(Settings.this, Home.class);
                        startActivity(homeAct);
                        Toast.makeText(Settings.this, "Connected Successfully!", Toast.LENGTH_SHORT).show();
//                    }
//
//                }else {
//                    Toast.makeText(Settings.this, "Check your connection & settings!", Toast.LENGTH_SHORT).show();
//
//                }
            }
        });


    }
    private void Connect() {

        clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), mqtt_host, clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    hiveConnect = true;
                    Toast.makeText(getApplicationContext(), "connected!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    hiveConnect = false;
                    Toast.makeText(getApplicationContext(), "connected failed!", Toast.LENGTH_LONG).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void Disconnect() {
        try {
            IMqttToken disconToken = client.disconnect();
            disconToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // we are now successfully disconnected
                    hiveConnect = false;
                    Toast.makeText(Settings.this,"Disconnected!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // something went wrong, but probably we are disconnected anyway
                    hiveConnect = true;
                    Toast.makeText(Settings.this,"Not Disconnected!", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}