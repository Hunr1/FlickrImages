package com.example.flickrimages;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class SingleImage extends AppCompatActivity {

    ImageView imgView;
    String url;
    private ScaleGestureDetector mScaleGestureDetector;

    //zoomauksen nopeus
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image_view);
        //haetaan kuvan URL extroista
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            url = bundle.getString("url");
        }

        imgView = findViewById(R.id.singleImage);
        //ladataan kuva imageViewiin
        Picasso.get().load(url).into(imgView);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
            return mScaleGestureDetector.onTouchEvent(event);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        // Source https://stackoverflow.com/questions/10630373/android-image-view-pinch-zooming
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            imgView.setScaleX(mScaleFactor);
            imgView.setScaleY(mScaleFactor);
            return true;
        }
    }
}
