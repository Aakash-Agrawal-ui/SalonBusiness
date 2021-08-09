package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AppCompatActivity;

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

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.salononline.salonbusiness.Data.CategoryAdapter;
import com.salononline.salonbusiness.Data.CategoryItem;
import com.salononline.salonbusiness.Parse.ParseCategory;
import com.salononline.salonbusiness.Parse.ParseNewService;
import com.salononline.salonbusiness.Parse.json;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.Validations.ValidateUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddService extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextInputLayout serviceName,servicePrice,discountPercent;
    private TextInputLayout description;
    private Button addServiceButton;
    private RadioGroup radioGroup;
    private TextView textServiceType;
    private RadioButton serviceMale,serviceFemale;
    private Spinner hours,minutes,categoriesSpinner;
    private ArrayList<CategoryItem> mCategoryItems;
    private CategoryAdapter categoryAdapter;
    Integer discount_percent;
    private ProgressDialog loadingBar;
    String service_name,service_description,service_price,service_type="",categoryUuid="",token,shopUuid,salonType,
            hoursDuration="00",minutesDuration="00",serviceTime="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        token=getIntent().getStringExtra("token");
        shopUuid=getIntent().getStringExtra("shopUuid");
        salonType=getIntent().getStringExtra("SalonType");
        loadingBar=new ProgressDialog(AddService.this);

        textServiceType=findViewById(R.id.textServiceType);
        radioGroup=findViewById(R.id.radioGroup);
        serviceMale=findViewById(R.id.serviceMale);
        serviceFemale=findViewById(R.id.serviceFemale);

        if(salonType.equals("MALE") || salonType.equals("FEMALE")){
            serviceMale.setEnabled(false);
            serviceFemale.setEnabled(false);
            service_type=salonType;
            if(salonType.equals("MALE")) {
                serviceMale.setChecked(true);
            }
            else{
                serviceFemale.setChecked(true);
            }
            radioGroup.setEnabled(false);

        }

        mToolbar=findViewById(R.id.addServiceToolbar);
        serviceName=findViewById(R.id.serviceName);
        description=findViewById(R.id.shortDescription);
        addServiceButton=findViewById(R.id.addServiceButton);
        servicePrice=findViewById(R.id.servicePrice);
        discountPercent=findViewById(R.id.discountPercent);


        hours = findViewById(R.id.hoursDuration);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.hours_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hours.setAdapter(arrayAdapter);

        minutes = findViewById(R.id.minutesDuration);
        ArrayAdapter<CharSequence> arrayAdapterMinutes = ArrayAdapter.createFromResource(this, R.array.minutes_array, android.R.layout.simple_spinner_item);
        arrayAdapterMinutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutes.setAdapter(arrayAdapterMinutes);

        getCategoryList();

        categoriesSpinner=findViewById(R.id.categories);
        categoryAdapter=new CategoryAdapter(this,mCategoryItems);
        categoriesSpinner.setAdapter(categoryAdapter);

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



        serviceMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    service_type="MALE";

            }
        });

        serviceFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    service_type="FEMALE";

            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Salon Service");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceTime=hoursDuration+":"+minutesDuration+":"+"00";
                service_name=serviceName.getEditText().getText().toString();
                String service_name_error= ValidateUser.validateServiceName(service_name);


                service_price=servicePrice.getEditText().getText().toString();
                String service_price_error=ValidateUser.validateSalonName(service_price);

                service_description=description.getEditText().getText().toString();
                String description_error=ValidateUser.validateServiceDescription(service_description);
                if(categoryUuid.equals("")){
                    Toast.makeText(getApplicationContext(),"Select Category",Toast.LENGTH_SHORT).show();
                }
                if(!description_error.equals("")){
                    description.setError(description_error);
                }
                else{
                    description.setError(null);
                }
                if(!discountPercent.getEditText().getText().toString().equals("")) {
                    discount_percent = Integer.parseInt(discountPercent.getEditText().getText().toString());
                    if(discount_percent==0){
                        discountPercent.setError("Can't be Zero");
                    }
                    else{
                        discountPercent.setError(null);
                    }
                }
                else{
                    discount_percent=null;
                }
                if(!service_name_error.equals("")){
                    serviceName.setError(service_name_error);
                }
                else{
                    serviceName.setError(null);
                }
                if(!service_price_error.equals("")){
                    servicePrice.setError(service_price_error);
                }
                else
                {
                    servicePrice.setError(null);
                }
                if(!servicePrice.getEditText().getText().toString().equals("") && Integer.parseInt(service_price)==0){
                    servicePrice.setError("Can't be Zero");
                }

                if(service_type.equals("")){
                    Toast.makeText(getApplicationContext(),"Select Gender",Toast.LENGTH_SHORT).show();
                }
                if(hoursDuration.equals("00")){
                    if(minutesDuration.equals("00")){
                        Toast.makeText(AddService.this, "Set Service Duration Time", Toast.LENGTH_SHORT).show();
                    }
                }
                 if(description_error.equals("") && !categoryUuid.equals("") && (discount_percent == null || discount_percent != 0)
                         && service_price_error.equals("") && service_name_error.equals("") && !service_type.equals("")
                         && !(Integer.parseInt(service_price) == 0) && !(hoursDuration.equals("00") && minutesDuration.equals("00"))){
                    loadingBar.setTitle("Adding Service");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    ParseNewService parseNewService = new ParseNewService(service_name, service_type, Integer.parseInt(service_price), discount_percent,
                                serviceTime, null, service_description);

                    retrofit2.Call<json> addServiceCall=apiInterface.addNewService("Bearer "+token,shopUuid,categoryUuid,parseNewService);
                    addServiceCall.enqueue(new Callback<json>() {
                        @Override
                        public void onResponse(Call<json> call, Response<json> response) {
                            if(!response.isSuccessful()){
                                loadingBar.dismiss();
                                Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (response.body().getCode() == 201) {
                                    loadingBar.dismiss();
                                    LayoutInflater inflater=getLayoutInflater();
                                    View layout=inflater.inflate(R.layout.customtoastlayout,(ViewGroup) findViewById(R.id.custom_toast_container));
                                    TextView textView=(TextView) layout.findViewById(R.id.textToast);
                                    textView.setText(response.body().getMessage());
                                    Toast toast=new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.BOTTOM,0,135);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();
                                    startActivity(new Intent(getApplicationContext(), Service.class));
                                    finish();
                                }
                                else if(response.body().getCode()==150){
                                    loadingBar.dismiss();
                                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                else if (response.body().getCode() == 400) {
                                    loadingBar.dismiss();
                                    Toast.makeText(getApplicationContext(), response.body().getValidationErrors().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Unknown Error",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<json> call, Throwable t) {
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
                Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_LONG).show();
            }
        });
    }
}
