package com.example.flickrimages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Adapter;
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

    private String url =  "https://api.flickr.com/services/feeds/photos_public.gne?nojsoncallback=?&format=json&tags=";
    private String tag = "";
    EditText tagInput;
    RequestQueue queue;
    private static final String TAG_ITEMS = "items";
    private static final String TAG_MEDIA = "media";
    private static final String TAG_IMAGE = "m";

    JSONArray items = null;
    JSONObject media = null;
    ArrayList<String> imageURLS;
    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagInput=findViewById(R.id.editText_tag);
        queue = Volley.newRequestQueue(this);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height= size.y;
    }


    public void onLoadClick(View v){
       tag = tagInput.getText().toString();
       String searchURL = "";
       searchURL=url+tag;

       Log.d("applikaatio",searchURL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, searchURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("applikaatio","saatiin data " + response.toString());
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

    public void JsonParseResponse(JSONObject response){
        try{
            imageURLS = new ArrayList<>();
            items = response.getJSONArray(TAG_ITEMS);
            Log.d("applikaatio","parsetaan" + items.toString());
            for(int i = 0; i < items.length();i++){
                JSONObject obj = items.getJSONObject(i);

                media = obj.getJSONObject(TAG_MEDIA);

                String imageURL = media.getString(TAG_IMAGE);
                imageURLS.add(imageURL);
                Log.d("applikaatio",imageURL);
            }

            Log.d("applikaatio","saatiin urlit luodaan adapteri");
            ListView imagesListView = findViewById(R.id.imageListView);

            ListAdapter adapter = new imageAdapter(MainActivity.this,
                    R.layout.image_view_layout,
                    imageURLS,
                    width,
                    height);
            imagesListView.setAdapter(adapter);
            imagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i("Applikaatio","clicked " + i);
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

