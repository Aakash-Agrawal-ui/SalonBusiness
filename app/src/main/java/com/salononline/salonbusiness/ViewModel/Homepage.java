package com.salononline.salonbusiness.ViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.salononline.salonbusiness.Data.TokenDecode;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.Prefs;

import org.json.JSONObject;

public class Homepage extends AppCompatActivity {
    String token,shopUuid;
    private BottomNavigationView navigationView;
    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        try {
            String payload = TokenDecode.decodeJWT(Prefs.getInstance(Homepage.this).getString("token"));
            jsonObject= new JSONObject(payload);
            JSONObject salonDetailsObject=jsonObject.getJSONObject("salonDetails");
            token = Prefs.getInstance(Homepage.this).getString("token");
            shopUuid =salonDetailsObject.getString("shopUuid");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mToolbar=findViewById(R.id.bookingToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Booking");

        tabLayout=findViewById(R.id.booking_tab_layout);
        viewPager=findViewById(R.id.homepageViewPager);
        HomepageViewAdapter homepageViewAdapter=new HomepageViewAdapter(getSupportFragmentManager());
        homepageViewAdapter.AddFragment(new BookingListFragment(token,shopUuid),"Booking List");
        homepageViewAdapter.AddFragment(new BookingHistoryFragment(token,shopUuid),"Booking History");

        viewPager.setAdapter(homepageViewAdapter);
        tabLayout.setupWithViewPager(viewPager);



        navigationView=findViewById(R.id.navigation_view);
        navigationView.setSelectedItemId(R.id.booking);



        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.booking:
                        return true;

                    case R.id.queue:
                        Intent queueIntent=new Intent(getApplicationContext(),Queue.class);
                        startActivity(queueIntent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;


                    case R.id.services:
                        Intent serviceIntent=new Intent(getApplicationContext(),Service.class);
                        startActivity(serviceIntent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.barber:
                        Intent barberIntent=new Intent(getApplicationContext(),Barber.class);
                        startActivity(barberIntent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.profile:
                        Intent profileIntent=new Intent(getApplicationContext(),SalonProfile.class);
                        startActivity(profileIntent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
}
