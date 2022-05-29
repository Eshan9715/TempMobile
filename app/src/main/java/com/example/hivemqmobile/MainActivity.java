package com.example.hivemqmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText tem_cel , tem_fran, publish01, publish02,publish03, subs01;
    static String mqtt_host = "tcp://broker.mqttdashboard.com:1883";
    static String userName = "tem_data";
    static String password = "QWer12@@21reWQ";

    MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        tem_cel = findViewById(R.id.txt_cel);
        tem_fran = findViewById(R.id.txt_fran);
        publish01 = findViewById(R.id.txt_pubTop1);
        publish02 = findViewById(R.id.txt_pubTop2);
        publish03 = findViewById(R.id.txt_pubTop3);
        subs01 = findViewById(R.id.txt_tem);

        Button btn_connect =(Button)findViewById(R.id.connect);

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientId = MqttClient.generateClientId();
                client = new MqttAndroidClient(MainActivity.this,mqtt_host, clientId);

                MqttConnectOptions options = new MqttConnectOptions();
                options.setUserName(userName);
                options.setPassword(password.toCharArray());

                try {
                    IMqttToken token = client.connect(options);
                    token.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            Toast.makeText(MainActivity.this,"Connected!", Toast.LENGTH_SHORT).show();

                            // We are connected
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            Toast.makeText(MainActivity.this,"Connection failed!", Toast.LENGTH_SHORT).show();

                            // Something went wrong e.g. connection timeout or firewall problems

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }


            }
        });

        Button btn_disconnect =(Button)findViewById(R.id.disconnect);

        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    IMqttToken disconToken = client.disconnect();
                    disconToken.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            // we are now successfully disconnected
                            Toast.makeText(MainActivity.this,"Disconnected!", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken,
                                              Throwable exception) {
                            // something went wrong, but probably we are disconnected anyway
                            Toast.makeText(MainActivity.this,"Not Disconnected!", Toast.LENGTH_SHORT).show();

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }
        });
        Button btn_on =(Button)findViewById(R.id.on);

        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pub3topic = publish03.getText().toString();
                String topic = pub3topic;

                String payload = "on";
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this,"LED on!", Toast.LENGTH_SHORT).show();

            }
        });
        Button btn_off =(Button)findViewById(R.id.off);

        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pub3topic2 = publish03.getText().toString();
                String topic = pub3topic2;

                String payload = "off";
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this,"LED off!", Toast.LENGTH_SHORT).show();

            }
        });
        Button btn_pub1 =(Button)findViewById(R.id.publish1);

        btn_pub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pub1topic = publish01.getText().toString();
                String topic = pub1topic;
                String pub1topic2 = publish02.getText().toString();

                String payload = pub1topic2;
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this,"Publish Done!", Toast.LENGTH_SHORT).show();

            }
        });

        Button btn_sub1 =(Button)findViewById(R.id.Read);

        btn_sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub1topic = subs01.getText().toString();
                int qos = 1;
                try{
                    client.subscribe(sub1topic, qos);
                }catch (MqttException e){
                    e.printStackTrace();
                }
                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {

                    }
                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        String msg = new String(message.getPayload());
                        String setMsg = msg + " C";
                        tem_cel.setText(setMsg);
                        double temp = Double.parseDouble(msg);
                        double temp_fah = (temp * 1.8) +32;
                        double roundDbl = Math.round(temp_fah*100.0)/100.0;
                        String fran = String.valueOf(roundDbl);
                        tem_fran.setText(fran + " F");
                        Toast.makeText(MainActivity.this, "Message received", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {

                    }
                });
            }
        });
    }
}