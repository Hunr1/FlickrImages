package com.example.flickrimages;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //tehdään 1. slider page
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle("Flickr image app");
        sliderPage.setDescription("App to help you browse images from flickr");
        sliderPage.setBgColor(getColor(R.color.IntroBackground1));

        //tehdään 2. slider page
        SliderPage sliderPage2= new SliderPage();
        sliderPage2.setTitle("Just another slider page");
        sliderPage2.setDescription("and it works!");
        sliderPage2.setBgColor(getColor(R.color.IntroBackground2));

        //tehdään 3. slider page
        SliderPage sliderPage3= new SliderPage();
        sliderPage3.setTitle("This app will use internet");
        sliderPage3.setDescription("hopefully");
        sliderPage3.setImageDrawable(R.drawable.ic_thumb_up_24px);
        sliderPage3.setBgColor(getColor(R.color.IntroBackground3));

        //lisätään slider paget app introon
        addSlide(AppIntroFragment.newInstance(sliderPage));
        addSlide(AppIntroFragment.newInstance(sliderPage2));
        addSlide(AppIntroFragment.newInstance(sliderPage3));

        //asetetaan animaatio
        setFlowAnimation();

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
