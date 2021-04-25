package com.example.banglorerealestatepricepredictor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class gettingvalues extends AppCompatActivity {


    String ans=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gettingvalues);

        Bundle extras = getIntent().getExtras();
        ArrayList<String> loca = extras.getStringArrayList("locations");
        String url = extras.getString("serverurl");
        System.out.println(loca);
        System.out.println(url);
        RequestQueue mQueue;
        mQueue = Volley.newRequestQueue( this);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());

        ArrayList<String> spinnerarray = new ArrayList<>();
        spinnerarray.add("One");
        spinnerarray.add("Two");
        spinnerarray.add("Three");
        spinnerarray.add("Four");
        spinnerarray.add("Five");

        Spinner locspinner = findViewById(R.id.locspinner);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loca);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locspinner.setAdapter(arrayAdapter1);

        Spinner bhkspinner = findViewById(R.id.bhkspinner);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerarray);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bhkspinner.setAdapter(arrayAdapter2);

        Spinner bathspinner = findViewById(R.id.bathspinner);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerarray);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bathspinner.setAdapter(arrayAdapter3);


        Button price = findViewById(R.id.price);
        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText sqft= findViewById(R.id.totalsqft);

                String tsqft = sqft.getText().toString();
                String location = locspinner.getSelectedItem().toString();
                String bhk = getvalue(bhkspinner.getSelectedItem().toString());
                String bath = getvalue(bathspinner.getSelectedItem().toString());

                String predictedprice= getprice(tsqft,location,bhk,bath);

                if(predictedprice != null)
                    predictpriceactivity(tsqft,location,bhk,bath,predictedprice);

            }
            private void predictpriceactivity(String total_sqft,String location,String bhk,String bath,String predictedprice){

                Intent i = new Intent( gettingvalues.this , price.class);
                i.putExtra("area",total_sqft);
                i.putExtra("loc",location);
                i.putExtra("bhk",bhk);
                i.putExtra("bath",bath);
                i.putExtra("price",predictedprice);
                startActivity(i);

            }


            private String getvalue(String s){
                switch (s) {
                    case "One":
                        return "1";
                    case "Two":
                        return "2";
                    case "Three":
                        return "3";
                    case "Four":
                        return "4";
                    default:
                        return "5";
                }
            }

            private String getprice(String total_sqft,String location,String bhk,String bath){

                StringRequest request = new StringRequest(Request.Method.POST, url+"/predict_home_price", response -> ans=response, error -> Toast.makeText(gettingvalues.this, "Error:server did not respond", Toast.LENGTH_LONG).show()){
                    @Override
                    protected Map<String, String> getParams() {

                        final HashMap<String, String> postParams = new HashMap<>();
                        postParams.put("total_sqft", total_sqft);
                        postParams.put("location", location);
                        postParams.put("bhk", bhk);
                        postParams.put("bath", bath);

                        return postParams;
                    }
                };
                mQueue.add(request);
                return ans;
            }
        });

    }
}