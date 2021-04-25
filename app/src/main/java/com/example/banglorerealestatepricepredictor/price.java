package com.example.banglorerealestatepricepredictor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class price extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        Bundle extras = getIntent().getExtras();
        String area = extras.getString("area") +" SqFt";
        String BHKS = extras.getString("bhk")+" BHK";
        String bathr = extras.getString("bath")+" BATHROOMS";
        String locationn = extras.getString("loc").toUpperCase();
        String price ="Rs. "+extras.getString("price")+" LAKH";

        TextView locat=findViewById(R.id.location);
        TextView size=findViewById(R.id.size);
        TextView bhks=findViewById(R.id.BHKS);
        TextView bath=findViewById(R.id.bathr);
        TextView predprice=findViewById(R.id.predprice);

        locat.setText(locationn);
        size.setText(area);
        bhks.setText(BHKS);
        bath.setText(bathr);
        predprice.setText(price);

        Button back1 = findViewById(R.id.back1);
        back1.setOnClickListener(v -> finish());



    }
}