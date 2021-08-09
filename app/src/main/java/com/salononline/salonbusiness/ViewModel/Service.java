package com.salononline.salonbusiness.ViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.salononline.salonbusiness.Data.TokenDecode;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.Prefs;

import org.json.JSONObject;

public class Service extends AppCompatActivity {
    private Toolbar mToolbar;
    private BottomNavigationView navigationView;
    private String token,shopUuid,salonType;
    JSONObject jsonObject;
    private Button addServiceButtonLink;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mToolbar=findViewById(R.id.serviceToolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Services");


        try {
            String payload = TokenDecode.decodeJWT(Prefs.getInstance(Service.this).getString("token"));
            jsonObject= new JSONObject(payload);
            JSONObject salonDetailsObject=jsonObject.getJSONObject("salonDetails");
            token = Prefs.getInstance(Service.this).getString("token");
            shopUuid =salonDetailsObject.getString("shopUuid");
            salonType=salonDetailsObject.getString("salonType");
        } catch (Exception e) {
            e.printStackTrace();
        }

        tabLayout=findViewById(R.id.service_tab_layout);
        viewPager=findViewById(R.id.serviceViewPager);
        ServiceViewAdapter serviceViewAdapter=new ServiceViewAdapter(getSupportFragmentManager());
        if(salonType.equals("MALE")) {
            serviceViewAdapter.AddFragment(new MaleServicesFragment(token, shopUuid, salonType), "Male Services");
        }
        else if(salonType.equals("FEMALE")) {
            serviceViewAdapter.AddFragment(new FemaleServicesFragment(token, shopUuid, salonType), "Female Services");
        }
        else{
            serviceViewAdapter.AddFragment(new MaleServicesFragment(token, shopUuid, salonType), "Male Services");
            serviceViewAdapter.AddFragment(new FemaleServicesFragment(token, shopUuid, salonType), "Female Services");

        }

        viewPager.setAdapter(serviceViewAdapter);
        tabLayout.setupWithViewPager(viewPager);

        addServiceButtonLink=findViewById(R.id.addServiceButtonLink);

        addServiceButtonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addServiceIntent=new Intent(getApplicationContext(),AddService.class);
                addServiceIntent.putExtra("token",token);
                addServiceIntent.putExtra("shopUuid",shopUuid);
                addServiceIntent.putExtra("SalonType",salonType);
                startActivity(addServiceIntent);
            }
        });

        navigationView=findViewById(R.id.navigation_view_service);
        navigationView.setSelectedItemId(R.id.services);


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
                        Intent queueIntent=new Intent(getApplicationContext(),Queue.class);
                        startActivity(queueIntent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;


                    case R.id.services:
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
