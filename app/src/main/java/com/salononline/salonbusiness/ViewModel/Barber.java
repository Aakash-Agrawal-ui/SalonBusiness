package com.salononline.salonbusiness.ViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.salononline.salonbusiness.Data.TokenDecode;
import com.salononline.salonbusiness.Parse.ParseShopStatus;
import com.salononline.salonbusiness.Parse.ParseUpdateImage;
import com.salononline.salonbusiness.Parse.ShopStateData;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.Prefs;
import com.salononline.salonbusiness.Repositry.RetrofitClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Barber extends AppCompatActivity {
    private Toolbar mToolbar;
    private BottomNavigationView navigationView;
    private Button addBarberButtonLink;
    private String token,shopUuid,shopStatus,salonType;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SwitchCompat shopSettingStatus;

    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber);

        mToolbar=findViewById(R.id.barberToolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Settings");

        try {
            String payload = TokenDecode.decodeJWT(Prefs.getInstance(Barber.this).getString("token"));
            jsonObject= new JSONObject(payload);
            JSONObject salonDetailsObject=jsonObject.getJSONObject("salonDetails");
            token = Prefs.getInstance(Barber.this).getString("token");
            shopUuid =salonDetailsObject.getString("shopUuid");
            salonType=salonDetailsObject.getString("salonType");
        } catch (Exception e) {
            e.printStackTrace();
        }

        tabLayout=findViewById(R.id.barber_tab_layout);
        viewPager=findViewById(R.id.barberViewPager);
        BarberViewAdapter barberViewAdapter=new BarberViewAdapter(getSupportFragmentManager());
        if(salonType.equals("MALE")) {
            barberViewAdapter.AddFragment(new MaleServicesBarberFragment(token,shopUuid,salonType), "Male Experts");
        }
        else if(salonType.equals("FEMALE")){
            barberViewAdapter.AddFragment(new FemaleServicesBarberFragment(token,shopUuid,salonType),"Female Experts");
        }
        else {
            barberViewAdapter.AddFragment(new MaleServicesBarberFragment(token,shopUuid,salonType), "Male Experts");
            barberViewAdapter.AddFragment(new FemaleServicesBarberFragment(token,shopUuid,salonType), "Female Experts");
        }

        viewPager.setAdapter(barberViewAdapter);
        tabLayout.setupWithViewPager(viewPager);

        addBarberButtonLink=findViewById(R.id.addBarberButtonLink);
        shopSettingStatus=findViewById(R.id.shopSettingsStatus);


        if(!Prefs.getInstance(Barber.this).getString("status").equals("")){
            if(Prefs.getInstance(Barber.this).getString("status").equals("OPEN")) {
                shopSettingStatus.setChecked(true);
            }
            else{
                shopSettingStatus.setChecked(false);
            }
        }
        else {
            getShopStatusInfo();
        }
        shopSettingStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopSettingStatus.isChecked()){
                    shopStatus="OPEN";
                }else {
                    shopStatus="CLOSED";
                }
                ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                ShopStateData shopStateData=new ShopStateData(shopStatus);
                retrofit2.Call<ParseUpdateImage> updateSalonStateCall=apiInterface.updateSalonState("Bearer "+token,shopUuid,shopStateData);
                updateSalonStateCall.enqueue(new Callback<ParseUpdateImage>() {
                    @Override
                    public void onResponse(Call<ParseUpdateImage> call, Response<ParseUpdateImage> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (response.body().getCode() == 200) {
                                Prefs.getInstance(Barber.this).putString("status", shopStatus);
                                LayoutInflater inflater=getLayoutInflater();
                                View layout=inflater.inflate(R.layout.customtoastlayout,(ViewGroup) findViewById(R.id.custom_toast_container));
                                TextView textView=(TextView) layout.findViewById(R.id.textToast);
                                textView.setText(response.body().getMessage());
                                Toast toast=new Toast(getApplicationContext());
                                toast.setGravity(Gravity.BOTTOM,0,135);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ParseUpdateImage> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        addBarberButtonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addBarberIntent=new Intent(getApplicationContext(),AddBarber.class);
                addBarberIntent.putExtra("token",token);
                addBarberIntent.putExtra("shopUuid",shopUuid);
                addBarberIntent.putExtra("SalonType",salonType);
                startActivity(addBarberIntent);
            }
        });

        navigationView=findViewById(R.id.navigation_view_barber);
        navigationView.setSelectedItemId(R.id.barber);

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
                        Intent serviceIntent=new Intent(getApplicationContext(),Service.class);
                        startActivity(serviceIntent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.barber:
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

    private void getShopStatusInfo() {
        ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<ParseShopStatus> parseShopStatusCall = apiInterface.getShopStatusResult("Bearer " + token, shopUuid);
        parseShopStatusCall.enqueue(new Callback<ParseShopStatus>() {
            @Override
            public void onResponse(Call<ParseShopStatus> call, Response<ParseShopStatus> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        Prefs.getInstance(Barber.this).putString("status", response.body().getData().getShopStatus());
                        if (response.body().getData().getShopStatus().equals("OPEN")) {
                            shopSettingStatus.setChecked(true);

                        } else {
                            shopSettingStatus.setChecked(false);
                        }
                    } else if (response.body().getCode() == 404) {
                        Toast.makeText(getApplicationContext(), "Shop Not Found", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getApplicationContext(), "Unknown Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ParseShopStatus> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
