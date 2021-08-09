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
import com.salononline.salonbusiness.Parse.ParseUpdateImage;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.Validations.ValidateUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBarber extends AppCompatActivity {
    private Toolbar mToolbar;
   private String token,shopUuid,salonType,gender="";
    private TextInputLayout barberName;
    private RadioButton barberMale,barberFemale;
    private RadioGroup barberRadioGroup;
    private TextView genderText;
    private Button addBarberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barber);

        token=getIntent().getStringExtra("token");
        shopUuid=getIntent().getStringExtra("shopUuid");
        salonType=getIntent().getStringExtra("SalonType");

        mToolbar=findViewById(R.id.addBarberToolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Expert");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        barberName=findViewById(R.id.barberName);
        genderText=findViewById(R.id.textGender);
        barberRadioGroup=findViewById(R.id.radioGroupBarber);
        barberMale=findViewById(R.id.barberMale);
        barberFemale=findViewById(R.id.barberFemale);
        addBarberButton=findViewById(R.id.addBarberButton);

        if(salonType.equals("MALE") || salonType.equals("FEMALE")){
            gender=salonType;
            barberMale.setEnabled(false);
            barberFemale.setEnabled(false);
            if(salonType.equals("MALE")) {
                barberMale.setChecked(true);
            }
            else{
               barberFemale.setChecked(true);
            }
            barberRadioGroup.setEnabled(false);
        }

        barberMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    gender="MALE";

            }
        });
        barberFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender="FEMALE";
            }
        });

        addBarberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barber_name=barberName.getEditText().getText().toString();
                String barber_name_error= ValidateUser.validateFullName(barber_name);
                addBarberButton.setEnabled(false);

                if(!barber_name_error.equals("")){
                    barberName.setError(barber_name_error);
                }
                else {
                    barberName.setError(null);
                }
                if(gender.equals("")){
                    Toast.makeText(getApplicationContext(),"Please Select Gender",Toast.LENGTH_SHORT).show();
                }
                if(barber_name_error.equals("") && !gender.equals("")){
                    ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    AddBarberData addBarberData=new AddBarberData(barber_name,gender);
                    retrofit2.Call<ParseUpdateImage> addBarberCall=apiInterface.addNewBarber("Bearer "+token,shopUuid,addBarberData);
                    addBarberCall.enqueue(new Callback<ParseUpdateImage>() {
                        @Override
                        public void onResponse(Call<ParseUpdateImage> call, Response<ParseUpdateImage> response) {
                            if(!response.isSuccessful()){
                                addBarberButton.setEnabled(true);
                                Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (response.body().getCode() == 201) {
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
                                } else if (response.body().getCode() == 151) {
                                    addBarberButton.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    addBarberButton.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ParseUpdateImage> call, Throwable t) {
                            addBarberButton.setEnabled(true);
                            Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
