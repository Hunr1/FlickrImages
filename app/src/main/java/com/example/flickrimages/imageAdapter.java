package com.example.flickrimages;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class imageAdapter extends ArrayAdapter<String> {

    Context context;
    int layout;
    ArrayList<String> imageURLS;
    int screenWidth;
    int screenHeight;


    public imageAdapter(Context context,int layout,ArrayList<String> imageURLSin,int width,int height){
        super(context, layout, imageURLSin);
        this.layout = layout;
        this.context = context;
        this.imageURLS = imageURLSin;
        this.screenWidth = width;
        this.screenHeight = height;

        Log.d("applikaatio","got new urls " + imageURLS.toString());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

       if(convertView == null){
           convertView=LayoutInflater.from(context).inflate(R.layout.image_view_layout,null);
           holder = new ViewHolder();
           holder.imageView = convertView.findViewById(R.id.imageLayoutImage);
           convertView.setTag(holder);
       }else {
           holder =(ViewHolder)convertView.getTag();
       }

       String url = imageURLS.get(position);
       if(!url.equals("")){
           Picasso.get()
                   .load(url)
                   .resize(screenWidth,screenHeight/2)
                   .centerCrop()
                   .into(holder.imageView);
       }else{
            Log.d("applikaatio","image url empty");
       }

       return convertView;
    }

    private class ViewHolder{
        ImageView imageView;
    }


}
