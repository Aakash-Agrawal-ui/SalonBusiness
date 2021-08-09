package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.salononline.salonbusiness.R;

public class Offers extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        tabLayout=findViewById(R.id.profile_offers_tab_layout);
        viewPager=findViewById(R.id.profileOffersViewPager);
        ProfileOffersAdapter profileOfferViewAdapter=new ProfileOffersAdapter(getSupportFragmentManager());
        profileOfferViewAdapter.AddFragment(new CreateEvent(),"Create Event");
        profileOfferViewAdapter.AddFragment(new SubmittedEvent(),"Submitted Event");

        viewPager.setAdapter(profileOfferViewAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
