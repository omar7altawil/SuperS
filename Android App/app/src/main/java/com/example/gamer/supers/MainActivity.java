package com.example.gamer.supers;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.macroyau.thingspeakandroid.ThingSpeakChannel;
import com.macroyau.thingspeakandroid.ThingSpeakLineChart;
import com.macroyau.thingspeakandroid.model.ChannelFeed;
import com.macroyau.thingspeakandroid.model.Feed;

import java.util.ArrayList;
import java.util.Date;

import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends AppCompatActivity {

     private static String requestedObj ;
    // Fire base code
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRoot = database.getReference("users");
    String roomname;
    ArrayList<Sensors> livingroom_Sensors=new ArrayList<Sensors>();
    ArrayList<Sensors> bedroomsen_Sors=new ArrayList<Sensors>();
    ArrayList<Sensors> bathroom_Sensors=new ArrayList<Sensors>();
    ArrayList<Sensors> kitchin_Sensors=new ArrayList<Sensors>();


    TextView tempt;
    TextView humdt;
    TextView prat;
    TextView atlt;
    ImageView flame;
    ImageView light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempt = (TextView) findViewById(R.id.tempt);
        humdt = (TextView) findViewById(R.id.humdt);
        prat= (TextView) findViewById(R.id.prat);
        atlt =(TextView) findViewById(R.id.atlt);
        flame = (ImageView) findViewById(R.id.flame);
        light = (ImageView) findViewById(R.id.light);
        setUpPage();
    }
    public static void setRequestedObj(String room){
        requestedObj = room;
    }
    public void setUpPage() { // Researches database
            roomname=requestedObj;
        userRoot.child("omar7altawil").child(roomname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Sensors sensors = dataSnapshot.getValue(Sensors.class);
                tempt.setText((String)sensors.getTempC()+" C");
                humdt.setText((String)sensors.getHumidity()+" %");
                atlt.setText((String)sensors.getAltitude()+" K");
                prat.setText((String)sensors.getPressure()+" PA");
                if(Integer.parseInt(sensors.getLight())>3)
                    light.setImageResource(R.drawable.lighton);
                else
                    light.setImageResource(R.drawable.lightoff);
                if(Integer.parseInt(sensors.getFlame())>3)
                    flame.setVisibility(View.VISIBLE);
                else
                    flame.setVisibility(View.INVISIBLE);

                if(roomname=="Living Room"){
                    livingroom_Sensors.add(sensors);
                }
                else if(roomname=="Bed Room"){
                    bedroomsen_Sors.add(sensors);
                }
                else if(roomname=="Kitchen"){
                    kitchin_Sensors.add(sensors);
                }
                else if(roomname=="Bath Room"){
                    bathroom_Sensors.add(sensors);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }
}


