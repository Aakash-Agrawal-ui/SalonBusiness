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

public class Queue extends AppCompatActivity {
    String token,shopUuid,salonType;
    private BottomNavigationView navigationView;
    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
        mToolbar=findViewById(R.id.queueToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Queue");
        try {
            String payload = TokenDecode.decodeJWT(Prefs.getInstance(Queue.this).getString("token"));
            jsonObject= new JSONObject(payload);
            JSONObject salonDetailsObject=jsonObject.getJSONObject("salonDetails");
            token = Prefs.getInstance(Queue.this).getString("token");
            shopUuid =salonDetailsObject.getString("shopUuid");
            salonType=salonDetailsObject.getString("salonType");
        } catch (Exception e) {
            e.printStackTrace();
        }

        tabLayout=findViewById(R.id.queue_tab_layout);
        viewPager=findViewById(R.id.queueViewPager);
        QueueViewAdapter queueViewAdapter=new QueueViewAdapter(getSupportFragmentManager());
        if(salonType.equals("MALE")) {
            queueViewAdapter.AddFragment(new MaleQueueFragment(token,shopUuid,salonType), "Male Queue");
        }
        else if(salonType.equals("FEMALE")) {
            queueViewAdapter.AddFragment(new FemaleQueueFragment(token,shopUuid,salonType), "Female Queue");
        }
        else
        {
            queueViewAdapter.AddFragment(new MaleQueueFragment(token,shopUuid,salonType), "Male Queue");
            queueViewAdapter.AddFragment(new FemaleQueueFragment(token,shopUuid,salonType), "Female Queue");
        }

        viewPager.setAdapter(queueViewAdapter);
        tabLayout.setupWithViewPager(viewPager);


        navigationView=findViewById(R.id.navigation_view_queue);
        navigationView.setSelectedItemId(R.id.queue);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.booking:
                        Intent bookingIntent=new Intent(getApplicationContext(),Homepage.class);
                        startActivity(bookingIntent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.queue:
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
