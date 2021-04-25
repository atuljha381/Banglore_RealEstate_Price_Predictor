package com.example.banglorerealestatepricepredictor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RequestQueue mQueue;
    ArrayList<String> locations= new ArrayList<>();
    String url;
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText serverurl= findViewById(R.id.serverurl);

        mQueue = Volley.newRequestQueue( this);

        Button next = findViewById(R.id.next);

        Button submit = findViewById(R.id.urlsubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = serverurl.getText().toString();
                getlocations();
            }
            private void getlocations() {
                System.out.println(url);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, response -> {
                    try {
                        flag=true;
                        JSONArray jsonArray = response.getJSONArray("data_columns");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if(i>2)
                              locations.add(jsonArray.getString(i));
                        }
                    } catch (JSONException e) {
                        flag=false;
                        Toast.makeText(getApplicationContext(),"Json Error: Bad Server",Toast.LENGTH_SHORT).show();
                    }
                }, error ->{
                    flag=false;
                    Toast.makeText(getApplicationContext()," Url Error: Bad Server", Toast.LENGTH_SHORT).show();
                });

                mQueue.add(request);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(locations);
                if(flag) {
                    gettingvalueactivity();
                }
            }

            private void gettingvalueactivity() {
                Intent intent = new Intent( MainActivity.this , gettingvalues.class);
                intent.putExtra("locations",locations);
                intent.putExtra("serverurl",url);
                startActivity(intent);
            }

        });

    }
}