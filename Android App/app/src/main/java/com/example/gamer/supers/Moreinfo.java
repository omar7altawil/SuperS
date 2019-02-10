package com.example.gamer.supers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.macroyau.thingspeakandroid.ThingSpeakChannel;
import com.macroyau.thingspeakandroid.model.ChannelFeed;

import java.util.ArrayList;
import java.util.Date;

public class Moreinfo extends AppCompatActivity {
    private static User requestedObj ;
    TextView LivingRoomT;
    TextView BedRoomT;
    TextView KitchenT;
    TextView BathroomT;
    ImageView LivingRoomI;
    ImageView BedRoomI;
    ImageView KitchenI;
    ImageView BathroomI;
    TextView name;
    ImageView pic;
    User user;
    ArrayList <String> rooms=new ArrayList <String>();

    // Fire base code
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRoot = database.getReference("users");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moreinfo);
        name = (TextView) findViewById(R.id.name);
        pic = (ImageView) findViewById(R.id.pic);

        LivingRoomT = (TextView) findViewById(R.id.textView3);
        LivingRoomI = (ImageView) findViewById(R.id.livingroom);
        BedRoomT = (TextView) findViewById(R.id.BedRoom);
        BedRoomI = (ImageView) findViewById(R.id.bedroom);
        KitchenT= (TextView) findViewById(R.id.textView);
        KitchenI = (ImageView) findViewById(R.id.kitchen);
        BathroomT =(TextView) findViewById(R.id.textView9);
        BathroomI = (ImageView) findViewById(R.id.bath);
        userRoot.child("omar7altawil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rooms.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String room = data.getKey();
                    if (room.length() == 11) {
                        LivingRoomT.setVisibility(View.VISIBLE);
                        LivingRoomI.setVisibility(View.VISIBLE);
                    } else if (room.length() == 8 ) {
                        BedRoomT.setVisibility(View.VISIBLE);
                        BedRoomI.setVisibility(View.VISIBLE);
                    } else if (room.length() == 7) {
                        KitchenT.setVisibility(View.VISIBLE);
                        KitchenI.setVisibility(View.VISIBLE);
                    } else if (room.length() == 9) {
                        BathroomT.setVisibility(View.VISIBLE);
                        BathroomI.setVisibility(View.VISIBLE);
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



        setUpPage();
    }




    public static void setRequestedObj(User obj){
        requestedObj = obj;
    }
    public void setUpPage() { // Researches database
        user = requestedObj;

        name.setText(user.getName());

        Glide.with(getApplicationContext()).load(user.getImage()).into(pic);
    }// End List down




    public void LivingRoom(View view){
        MainActivity.setRequestedObj("Living Room");
        startActivity(new Intent(Moreinfo.this, MainActivity.class));
    }
    public void Bathroom(View view){
        MainActivity.setRequestedObj("Bath Room");
        startActivity(new Intent(Moreinfo.this, MainActivity.class));
    }
    public void Kitchen(View view){
        MainActivity.setRequestedObj("Kitchen");
        startActivity(new Intent(Moreinfo.this, MainActivity.class));
    }
    public void BedRoom(View view){
        MainActivity.setRequestedObj("Bed Room");
        startActivity(new Intent(Moreinfo.this, MainActivity.class));
    }

}