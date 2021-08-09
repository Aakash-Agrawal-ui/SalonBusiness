package com.salononline.salonbusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.salononline.salonbusiness.Parse.ParseUpdateImage;
import com.salononline.salonbusiness.Parse.ShopFacility;
import com.salononline.salonbusiness.Parse.UpdateSalonDetailsData;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.Validations.ValidateUser;
import com.google.android.material.textfield.TextInputLayout;
import com.salononline.salonbusiness.ViewModel.SalonProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateShopInfo extends AppCompatActivity {
    private Toolbar mToolbar;
    private String token,shopUuid,ownerName,salonEmail,salonNum;

    private TextInputLayout updateOwnerName,updateSalonMobile,updateSalonEmail;

    private Button updateShopDetailsButton;
    private CheckBox ac_Available_update, car_Parking_update, bike_Parking_update, online_Payment_update, home_Service_update,
            card_Payment_update,hot_Water_update,music_update;
    boolean acAvailable = false, carParking = false, bikeParking = false, onlinePayment = false, homeService = false, cardPayment = false
            ,hotWater=false,musicAvailable=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shop_info);
        mToolbar=findViewById(R.id.update_salon_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Update Salon Details");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        token=getIntent().getStringExtra("token");
        shopUuid=getIntent().getStringExtra("shopUuid");
        ownerName=getIntent().getStringExtra("name");
        salonNum=getIntent().getStringExtra("mobile");
        salonEmail=getIntent().getStringExtra("email");
        acAvailable=getIntent().getBooleanExtra("AC",false);
        cardPayment=getIntent().getBooleanExtra("CP",false);
        onlinePayment=getIntent().getBooleanExtra("OP",false);
        musicAvailable=getIntent().getBooleanExtra("music",false);
        carParking=getIntent().getBooleanExtra("CPark",false);
        bikeParking=getIntent().getBooleanExtra("BPark",false);
        hotWater=getIntent().getBooleanExtra("Water",false);
        homeService=getIntent().getBooleanExtra("HS",false);

        updateShopDetailsButton=findViewById(R.id.updateShopDetailsButton);

        updateOwnerName=findViewById(R.id.updateOwnerName);
        updateOwnerName.getEditText().setText(ownerName);

        updateSalonEmail=findViewById(R.id.updateSalonEmail);
        updateSalonEmail.getEditText().setText(salonEmail);

        updateSalonMobile=findViewById(R.id.updateSalonMobile);
        updateSalonMobile.getEditText().setText(salonNum);



        //checkbox reference
        ac_Available_update = findViewById(R.id.acAvailableCheckUpdate);
        ac_Available_update.setChecked(acAvailable);
        car_Parking_update = findViewById(R.id.carParkingCheckUpdate);
        car_Parking_update.setChecked(carParking);
        bike_Parking_update = findViewById(R.id.bikeParkingCheckUpdate);
        bike_Parking_update.setChecked(bikeParking);
        home_Service_update = findViewById(R.id.homeServiceCheckUpdate);
        home_Service_update.setChecked(homeService);
        online_Payment_update = findViewById(R.id.onlinePaymentCheckUpdate);
        online_Payment_update.setChecked(onlinePayment);
        card_Payment_update = findViewById(R.id.cardPaymentCheckUpdate);
        card_Payment_update.setChecked(cardPayment);
        hot_Water_update=findViewById(R.id.hotWaterCheckUpdate);
        hot_Water_update.setChecked(hotWater);
        music_update=findViewById(R.id.musicCheckUpdate);
        music_update.setChecked(musicAvailable);

        ac_Available_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acAvailable = ac_Available_update.isChecked();
            }
        });

        car_Parking_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carParking = car_Parking_update.isChecked();
            }
        });
        bike_Parking_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bikeParking = bike_Parking_update.isChecked();
            }
        });
        online_Payment_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlinePayment = online_Payment_update.isChecked();
            }
        });
        home_Service_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeService = home_Service_update.isChecked();
            }
        });
        card_Payment_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardPayment = card_Payment_update.isChecked();
            }
        });

        hot_Water_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotWater= hot_Water_update.isChecked();
            }
        });

        music_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicAvailable= music_update.isChecked();
            }
        });

        updateShopDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owner_name=updateOwnerName.getEditText().getText().toString();
                String owner_name_error= ValidateUser.validateFullName(owner_name);

                String salon_mobile=updateSalonMobile.getEditText().getText().toString();
                String salon_mobile_error= ValidateUser.validatePhone(salon_mobile);

                String salon_email=updateSalonEmail.getEditText().getText().toString();
                String salon_email_error=ValidateUser.validateEmail(salon_email);

                if(!owner_name_error.equals("")){
                    updateOwnerName.setError(owner_name_error);
                }else{
                    updateOwnerName.setError(null);
                }
                if(!salon_mobile_error.equals("")){
                    updateSalonMobile.setError(salon_mobile_error);
                }else{
                    updateSalonMobile.setError(null);
                }
                if(!salon_email_error.equals("")){
                    updateSalonEmail.setError(salon_email_error);
                }else{
                    updateSalonEmail.setError(null);
                }
                if(owner_name_error.equals("") && salon_email_error.equals("") && salon_mobile_error.equals("")){
                    ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    ShopFacility shopFacility=new ShopFacility(acAvailable,carParking,bikeParking,onlinePayment,homeService,cardPayment,hotWater,musicAvailable);
                    UpdateSalonDetailsData updateSalonDetailsData=new UpdateSalonDetailsData(owner_name,salon_mobile,salon_email,shopFacility);

                    retrofit2.Call<ParseUpdateImage> updateSalonDetCall=apiInterface.updateSalonDet("Bearer "+token,shopUuid,updateSalonDetailsData);
                    updateSalonDetCall.enqueue(new Callback<ParseUpdateImage>() {
                        @Override
                        public void onResponse(Call<ParseUpdateImage> call, Response<ParseUpdateImage> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (response.body().getCode() == 200) {
                                    LayoutInflater inflater=getLayoutInflater();
                                    View layout=inflater.inflate(R.layout.customtoastlayout,(ViewGroup) findViewById(R.id.custom_toast_container));
                                    TextView textView=(TextView) layout.findViewById(R.id.textToast);
                                    textView.setText(response.body().getMessage());
                                    Toast toast=new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.BOTTOM,0,135);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();
                                    startActivity(new Intent(getApplicationContext(), SalonProfile.class));
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ParseUpdateImage> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
