package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.salononline.salonbusiness.Data.CategoryAdapter;
import com.salononline.salonbusiness.Data.CategoryItem;
import com.salononline.salonbusiness.Parse.GetServiceResult;
import com.salononline.salonbusiness.Parse.ParseCategory;
import com.salononline.salonbusiness.Parse.ParseUpdateImage;
import com.salononline.salonbusiness.Parse.UpdateServiceData;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.Validations.ValidateUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateService extends AppCompatActivity {
    private Toolbar mToolbar;
    private ProgressDialog loadingBar;
    private String serviceUuid,token,salonType;
    private TextInputLayout updateServiceName,updateServicePrice,updateDiscountPercent,updateShortDescription;
    private RadioGroup radioGroup;
    private RadioButton updateServiceMale,updateServiceFemale;
    private TextView updateTextServiceType;
    private String service_name,service_price,service_type="",categoryUuid="",hoursDuration="00",minutesDuration="00",serviceTime="",service_description;
    private Spinner hours,minutes,categoriesSpinner;
    private Button updateServiceButton;
    private CategoryAdapter categoryAdapter;
    Integer discount_percent;
    private ArrayList<CategoryItem> mCategoryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service);
        loadingBar=new ProgressDialog(UpdateService.this);
        mToolbar=findViewById(R.id.updateServiceToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Update Salon Service");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        token=getIntent().getStringExtra("token");
        serviceUuid=getIntent().getStringExtra("serviceUuid");
        salonType=getIntent().getStringExtra("salon_type");

        radioGroup=findViewById(R.id.updateServiceRadioGroup);
        updateTextServiceType=findViewById(R.id.updateTextServiceType);
        updateServiceButton=findViewById(R.id.updateServiceButton);
        updateServiceMale=findViewById(R.id.updateServiceMale);
        updateServiceFemale=findViewById(R.id.updateServiceFemale);
        updateShortDescription=findViewById(R.id.updateShortDescription);

        if(salonType.equals("MALE") || salonType.equals("FEMALE")){
            service_type=salonType;
            updateServiceMale.setEnabled(false);
            updateServiceFemale.setEnabled(false);
            service_type=salonType;
            if(salonType.equals("MALE")) {
                updateServiceMale.setChecked(true);
            }
            else{
                updateServiceFemale.setChecked(true);
            }
            radioGroup.setEnabled(false);

        }

        updateDiscountPercent=findViewById(R.id.updateDiscountPercent);
        updateServiceName=findViewById(R.id.updateServiceName);
        updateServicePrice=findViewById(R.id.updateServicePrice);

        hours = findViewById(R.id.updateHoursDuration);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.hours_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hours.setAdapter(arrayAdapter);

        minutes = findViewById(R.id.updateMinutesDuration);
        ArrayAdapter<CharSequence> arrayAdapterMinutes = ArrayAdapter.createFromResource(this, R.array.minutes_array, android.R.layout.simple_spinner_item);
        arrayAdapterMinutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutes.setAdapter(arrayAdapterMinutes);

        getCategoryList();

        categoriesSpinner=findViewById(R.id.updateCategories);
        categoryAdapter=new CategoryAdapter(this,mCategoryItems);
        categoriesSpinner.setAdapter(categoryAdapter);

        getService();


        hours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!parent.getItemAtPosition(position).equals("Hours")) {
                    hoursDuration = String.valueOf(parent.getItemAtPosition(position));
                    if(hoursDuration.length()==1){
                        hoursDuration="0"+hoursDuration;
                    }
                }
                else {
                    hoursDuration="00";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        minutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!parent.getItemAtPosition(position).equals("Minutes")) {
                    minutesDuration =String.valueOf(parent.getItemAtPosition(position));
                    if(minutesDuration.length()==1){
                        minutesDuration="0"+minutesDuration;
                    }
                }
                else {
                    hoursDuration="00";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryItem clickedItem=(CategoryItem) parent.getItemAtPosition(position);
                if(!clickedItem.getCategory_name().equals("Category"))
                {
                    categoryUuid=clickedItem.getCategory_uid();

                }
                else {
                    categoryUuid="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        updateServiceMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service_type="MALE";

            }
        });

        updateServiceFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service_type = "FEMALE";

            }
        });
        updateServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceTime=hoursDuration+":"+minutesDuration+":"+"00";
                service_name=updateServiceName.getEditText().getText().toString();
                String service_name_error= ValidateUser.validateServiceName(service_name);

                service_price=updateServicePrice.getEditText().getText().toString();
                String service_price_error=ValidateUser.validateSalonName(service_price);
                service_description=updateShortDescription.getEditText().getText().toString();
                String description_error=ValidateUser.validateServiceDescription(service_description);
                if(categoryUuid.equals("")){
                    Toast.makeText(getApplicationContext(),"Select Category",Toast.LENGTH_SHORT).show();
                }
                if(!description_error.equals("")){
                    updateShortDescription.setError(description_error);
                }
                else{
                    updateShortDescription.setError(null);
                }

                if(!updateDiscountPercent.getEditText().getText().toString().equals("")) {
                    discount_percent = Integer.parseInt(updateDiscountPercent.getEditText().getText().toString());
                    if(discount_percent==0){
                        updateDiscountPercent.setError("Can't be Zero");
                    }
                    else{
                        updateDiscountPercent.setError(null);
                    }
                }
                else{
                    discount_percent=null;
                }
                if(!service_name_error.equals("")){
                    updateServiceName.setError(service_name_error);
                }
                else{
                    updateServiceName.setError(null);
                }
                if(!service_price_error.equals("")){
                    updateServicePrice.setError(service_price_error);
                }
                else
                {
                    updateServicePrice.setError(null);
                }
                if(!service_price.equals("") && Integer.parseInt(service_price)==0){
                    updateServicePrice.setError("Can't be Zero");
                }

                if(service_type.equals("")){
                    Toast.makeText(getApplicationContext(),"Select Gender",Toast.LENGTH_SHORT).show();
                }
                if(hoursDuration.equals("00")){
                    if(minutesDuration.equals("00")){
                        Toast.makeText(UpdateService.this, "Set Service Duration Time", Toast.LENGTH_SHORT).show();
                    }
                }
                if(description_error.equals("") && !categoryUuid.equals("") && (discount_percent == null || discount_percent != 0) && service_price_error.equals("") && service_name_error.equals("") &&
                        !service_type.equals("") && !(Integer.parseInt(service_price) == 0) && !(hoursDuration.equals("00")
                        && minutesDuration.equals("00"))){
                    loadingBar.setTitle("Updating Service");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    UpdateServiceData updateServiceData=new UpdateServiceData(service_name,service_type, Integer.parseInt(service_price),null,discount_percent
                            ,serviceTime,service_description);

                    retrofit2.Call<ParseUpdateImage> updateServiceResultCall=apiInterface.updateService("Bearer "+token,serviceUuid,categoryUuid,
                            updateServiceData);
                    updateServiceResultCall.enqueue(new Callback<ParseUpdateImage>() {
                        @Override
                        public void onResponse(Call<ParseUpdateImage> call, Response<ParseUpdateImage> response) {
                            if(!response.isSuccessful()){
                                loadingBar.dismiss();
                                Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (response.body().getCode() == 200) {
                                    loadingBar.dismiss();
                                    LayoutInflater inflater=getLayoutInflater();
                                    View layout=inflater.inflate(R.layout.customtoastlayout,(ViewGroup) findViewById(R.id.custom_toast_container));
                                    TextView textView=(TextView) layout.findViewById(R.id.textToast);
                                    textView.setText("Service Updated Successfully");
                                    Toast toast=new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.BOTTOM,0,135);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();
                                    startActivity(new Intent(getApplicationContext(), Service.class));
                                    finish();
                                } else {
                                    loadingBar.dismiss();
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ParseUpdateImage> call, Throwable t) {
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }

    private void getService() {
        ApiInterface apiInterface=RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<GetServiceResult> getServiceResultCall=apiInterface.getServiceCall("Bearer "+token,serviceUuid);
        getServiceResultCall.enqueue(new Callback<GetServiceResult>() {
            @Override
            public void onResponse(Call<GetServiceResult> call, Response<GetServiceResult> response) {
                if(!response.isSuccessful()){
                    loadingBar.dismiss();
                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        serviceUuid = response.body().getData().getServiceUuid();
                        updateServiceName.getEditText().setText(response.body().getData().getServiceName());
                        updateDiscountPercent.getEditText().setText(String.valueOf(response.body().getData().getDiscount()));
                        updateServicePrice.getEditText().setText(String.valueOf(response.body().getData().getPrice()));
                        updateShortDescription.getEditText().setText(response.body().getData().getShortDescription());
                        service_type = response.body().getData().getServiceType();
                        if (service_type.equals("MALE")) {
                            updateServiceMale.setChecked(true);
                        } else if (service_type.equals("FEMALE")) {
                            updateServiceFemale.setChecked(true);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetServiceResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCategoryList() {
        mCategoryItems=new ArrayList<>();
        mCategoryItems.add(new CategoryItem("Category"));
        ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<ParseCategory> getAllCategory=apiInterface.getCategory("Bearer "+token);
        getAllCategory.enqueue(new Callback<ParseCategory>() {
            @Override
            public void onResponse(Call<ParseCategory> call, Response<ParseCategory> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            mCategoryItems.add(new CategoryItem(response.body().getData().get(i).getCategoryUuid(), response.body().getData().get(i).getCategoryName()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ParseCategory> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}
