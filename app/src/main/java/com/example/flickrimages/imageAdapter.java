package com.example.flickrimages;

import android.content.Context;
import android.util.Log;
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


    //adapterin constructorit, ottaa mm. kuvien urlit ja näytön koon vastaan
    public imageAdapter(Context context,int layout,ArrayList<String> imageURLSin,int width,int height){
        super(context, layout, imageURLSin);
        this.layout = layout;
        this.context = context;
        this.imageURLS = imageURLSin;
        this.screenWidth = width;
        this.screenHeight = height;

        Log.d("applikaatio","got new urls " + imageURLS.toString());
    }

    //adapterin viewin päivitys
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        //jos viewiä ei ole olemassa tehdään uusi
       if(convertView == null){
           convertView=LayoutInflater.from(context).inflate(R.layout.image_view_layout,null);
           holder = new ViewHolder();
           holder.imageView = convertView.findViewById(R.id.imageLayoutImage);
           convertView.setTag(holder);

           //jos on haetaan vanha tagilla
       }else {
           holder =(ViewHolder)convertView.getTag();
       }

       //haetaan kuvan url
       String url = imageURLS.get(position);

       //jos url ei ole tyhjä
       if(!url.equals("")){

           //ladataan kuva
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


    //holderi listan imageviewien tilojen tallentamiseen
    private class ViewHolder{
        ImageView imageView;
    }


}
