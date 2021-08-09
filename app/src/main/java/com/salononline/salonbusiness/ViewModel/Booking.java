package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.salononline.salonbusiness.Data.TokenDecode;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.Prefs;

import org.json.JSONObject;

public class Booking extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String token,shopUuid;
    JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        try {
            String payload = TokenDecode.decodeJWT(Prefs.getInstance(Booking.this).getString("token"));
            jsonObject= new JSONObject(payload);
            JSONObject salonDetailsObject=jsonObject.getJSONObject("salonDetails");
            token = Prefs.getInstance(Booking.this).getString("token");
            shopUuid =salonDetailsObject.getString("shopUuid");
        } catch (Exception e) {
            e.printStackTrace();
        }


        tabLayout=findViewById(R.id.profile_booking_tab_layout);
        viewPager=findViewById(R.id.profileBookingViewPager);
        ProfileBookingViewAdapter profileBookingViewAdapter=new ProfileBookingViewAdapter(getSupportFragmentManager());
        profileBookingViewAdapter.AddFragment(new BookingAcceptedFragment(token,shopUuid),"Booking Accepted");
        profileBookingViewAdapter.AddFragment(new BookingDeclinedFragment(token,shopUuid),"Booking Declined");

        viewPager.setAdapter(profileBookingViewAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
