package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.salononline.salonbusiness.Parse.AddBarberData;
import com.salononline.salonbusiness.Parse.GetBarberResult;
import com.salononline.salonbusiness.Parse.ParseUpdateImage;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.Validations.ValidateUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBarber extends AppCompatActivity {
    private Toolbar mToolbar;
    private String token,barberUuid,salonType,gender="";
    private TextInputLayout updateBarberName;
    private RadioButton updateBarberMale,updateBarberFemale;
    private RadioGroup updateBarberRadioGroup;
    private TextView updateBarberGenderText;
    private Button updateBarberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_barber);
        token=getIntent().getStringExtra("token");
        barberUuid=getIntent().getStringExtra("barberUuid");
        salonType=getIntent().getStringExtra("salon_type");

        mToolbar=findViewById(R.id.updateBarberToolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Update Expert");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        updateBarberRadioGroup=findViewById(R.id.updateRadioGroupBarber);
        updateBarberGenderText=findViewById(R.id.updateBarberTextGender);
        updateBarberName=findViewById(R.id.updateBarberName);
        updateBarberMale=findViewById(R.id.updateBarberMale);
        updateBarberFemale=findViewById(R.id.updateBarberFemale);
        updateBarberButton=findViewById(R.id.updateBarberButton);

        if(salonType.equals("MALE") || salonType.equals("FEMALE")){
            gender=salonType;
            updateBarberMale.setEnabled(false);
            updateBarberFemale.setEnabled(false);
            if(salonType.equals("MALE")) {
                updateBarberMale.setChecked(true);
            }
            else{
                updateBarberFemale.setChecked(true);
            }
            updateBarberRadioGroup.setEnabled(false);

        }

        getBarber();
        updateBarberMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender="MALE";

            }
        });
        updateBarberFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender="FEMALE";
            }
        });
        updateBarberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barber_name=updateBarberName.getEditText().getText().toString();
                String barber_name_error= ValidateUser.validateFullName(barber_name);

                if(!barber_name_error.equals("")){
                    updateBarberName.setError(barber_name_error);
                }
                else {
                    updateBarberName.setError(null);
                }
                if(gender.equals("")){
                    Toast.makeText(getApplicationContext(),"Please Select Gender",Toast.LENGTH_SHORT).show();
                }
                if(barber_name_error.equals("") && !gender.equals("")){
                    ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    AddBarberData addBarberData=new AddBarberData(barber_name,gender);
                    retrofit2.Call<ParseUpdateImage> updateBarberResultCall=apiInterface.updateBarber("Bearer "+token,barberUuid,addBarberData);
                    updateBarberResultCall.enqueue(new Callback<ParseUpdateImage>() {
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
                                    startActivity(new Intent(getApplicationContext(), Barber.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_SHORT).show();
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

    private void getBarber() {
        ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<GetBarberResult> getBarberResultCall=apiInterface.getBarberCall("Bearer "+token,barberUuid);
        getBarberResultCall.enqueue(new Callback<GetBarberResult>() {
            @Override
            public void onResponse(Call<GetBarberResult> call, Response<GetBarberResult> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.body().getCode() == 200) {
                        barberUuid = response.body().getData().getBarberUuid();
                        updateBarberName.getEditText().setText(response.body().getData().getBarberName());
                        gender = response.body().getData().getGender();
                        if (gender.equals("MALE")) {
                            updateBarberMale.setChecked(true);
                        } else if (gender.equals("FEMALE")) {
                            updateBarberFemale.setChecked(true);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetBarberResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
