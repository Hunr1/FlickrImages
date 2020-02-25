package com.example.flickrimages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //API:n url
    private String url =  "https://api.flickr.com/services/feeds/photos_public.gne?nojsoncallback=?&format=json&tags=";
    private String tag = "";

    EditText tagInput;
    RequestQueue queue;

    //JSON tiedoston tagit
    private static final String TAG_ITEMS = "items";
    private static final String TAG_MEDIA = "media";
    private static final String TAG_IMAGE = "m";

    JSONArray items = null;
    JSONObject media = null;
    //Array list johon talletetaan haetut URL:t
    ArrayList<String> imageURLS;

    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagInput=findViewById(R.id.editText_tag);
        //uusi volley request queue
        queue = Volley.newRequestQueue(this);

        //haetaan ruudun koko kuvien skaalaamista varten
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height= size.y;
    }


    //napin painalluksen käsittelijä
    public void onLoadClick(View v){
       tag = tagInput.getText().toString();
       String searchURL = "";
       searchURL=url+tag;

       Log.d("applikaatio",searchURL);

        //uusi volley request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, searchURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("applikaatio","saatiin data " + response.toString());

                        //lähetetään vastaus JSON parsettavaksi
                        JsonParseResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("applikaatio","Volley error " + error.toString());
                        error.printStackTrace();
                    }
                });
        queue.add(jsonObjectRequest);
    }

    //JSON parseri
    public void JsonParseResponse(JSONObject response){
        try{
            imageURLS = new ArrayList<>();

            //items json array
            items = response.getJSONArray(TAG_ITEMS);
            Log.d("applikaatio","parsetaan" + items.toString());

            //käydään kaikki itemit (kuvat) läpi
            for(int i = 0; i < items.length();i++){

                //haetaan item json objekti
                JSONObject item = items.getJSONObject(i);

                //haetaan media json objekti
                media = item.getJSONObject(TAG_MEDIA);

                //haetaan image url
                String imageURL = media.getString(TAG_IMAGE);

                //lisätään url array listiin
                imageURLS.add(imageURL);
                Log.d("applikaatio",imageURL);
            }

            Log.d("applikaatio","saatiin urlit luodaan adapteri");
            ListView imagesListView = findViewById(R.id.imageListView);

            //tehdään listviewille adapteri
            ListAdapter adapter = new imageAdapter(MainActivity.this,
                    R.layout.image_view_layout,
                    imageURLS,
                    width,
                    height);
            imagesListView.setAdapter(adapter);
            imagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override//adapterille click listener
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i("Applikaatio","clicked " + i);

                  //käynnistetään yksittäinen kuva activity, lisätään extrana klickatun kuvan url
                    Intent intent = new Intent(getApplicationContext(),SingleImage.class);
                    intent.putExtra("url",imageURLS.get(i));
                    startActivity(intent);
                }
            });

        }catch (JSONException e){
            Log.d("applikaatio",e.toString());
            e.printStackTrace();
        }
    }


}

