package com.example.lyricsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText edtArtistName;
    EditText edtSongName;
    Button btnGetLyrics;
    TextView txtlyrics;
    RequestQueue requestqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtArtistName = (EditText)findViewById(R.id.edtArtistName);
        edtSongName = (EditText)findViewById(R.id.edtSongName);
        btnGetLyrics = (Button)findViewById(R.id.btnGetLyrics);
        txtlyrics = (TextView) findViewById(R.id.txtlyrics);

        btnGetLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url="https://api.lyrics.ovh/v1/"+edtArtistName.getText().toString()+"/"+edtSongName.getText().toString();
                url.replaceAll(" ","20%");

                 requestqueue= Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.toString());
                            txtlyrics.setText(jsonObject.getString("lyrics"));

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),"worng entry",Toast.LENGTH_SHORT).show();

                            }
                        });

                requestqueue.add(jsonObjectRequest);


            }
        });
    }
}
