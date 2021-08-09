package com.salononline.salonbusiness.ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.salononline.salonbusiness.Data.AreaAdapter;
import com.salononline.salonbusiness.Data.AreaItem;
import com.salononline.salonbusiness.Data.CityAdapter;
import com.salononline.salonbusiness.Data.CityItem;
import com.salononline.salonbusiness.Data.CountryAdapter;
import com.salononline.salonbusiness.Data.CountryItem;
import com.salononline.salonbusiness.Data.StateAdapter;
import com.salononline.salonbusiness.Data.StateItem;
import com.salononline.salonbusiness.Data.TokenDecode;
import com.salononline.salonbusiness.Parse.AddSalonData;
import com.salononline.salonbusiness.Parse.AddressDet;
import com.salononline.salonbusiness.Parse.FileParse;
import com.salononline.salonbusiness.Parse.ParseAreas;
import com.salononline.salonbusiness.Parse.ParseCities;
import com.salononline.salonbusiness.Parse.ParseCountries;
import com.salononline.salonbusiness.Parse.ParseSalonAdd;
import com.salononline.salonbusiness.Parse.ParseStates;
import com.salononline.salonbusiness.Parse.ShopFacility;
import com.salononline.salonbusiness.Parse.ShopTimings;
import com.salononline.salonbusiness.Parse.jsonverify;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.Prefs;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.Validations.ValidateUser;

import java.io.File;

import okhttp3.MediaType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSalon extends AppCompatActivity {
    private static final int GPS_CODE = 8000;
    private Toolbar mToolbar;
    private String token, shopUuid = null;
    private ProgressDialog loadingBar;
    private TextView latitude, longitude;
    private final int REQUEST_CODE = 2;
    private ArrayList<CountryItem> mCountryItem;
    private ArrayList<StateItem> mStateItems;
    private ArrayList<CityItem> mCityItems;
    private ArrayList<AreaItem> mAreaItems;
    private CountryAdapter countryAdapter;
    private StateAdapter stateAdapter;
    private CityAdapter cityAdapter;
    private AreaAdapter areaAdapter;
    private Spinner countriesName, statesName, citiesName, seats,salonLocality;
    private boolean accept=false;

    private Button openMondayTime, openTuesdayTime, openWednesdayTime, openThursdayTime, openFridayTime, openSaturdayTime, openSundayTime;
    private Button closeMondayTime, closeTuesdayTime, closeWednesdayTime, closeThursdayTime, closeFridayTime, closeSaturdayTime, closeSundayTime;

    private Button addSalonLoc, submitForm, uploadRegCert, uploadMSMeCert, helpButton;
    private TextView UploadRegPath, UploadMSmePath, uploadSalonPath,termsAndConditionsText;
    private TextInputLayout salonName, ownerName, salonMobile, salonEmail, salonStreet, salonPinCode;

    private CheckBox ac_Available, car_Parking, bike_Parking, online_Payment, home_Service, card_Payment, hot_Water, music, mondayClosed, tuesdayClosed, wednesdayClosed,
            thursdayClosed, fridayClosed, saturdayClosed, sundayClosed,acceptCheck;

    private RadioButton maleGender, femaleGender, unisexGender;

    private Button salonImage;

    private Uri salonImageUri;

    private String country = "", state = "", city = "",area="", street = null, salon_name, owner_name, salon_mobile, salon_email,
            pincode, latitude_value = "", longitude_value = "", gender = "", monday_open = "09:00:00", monday_closed = "19:00:00",
            tuesday_open = "09:00:00", tuesday_closed = "19:00:00", wednesday_open = "09:00:00", wednesday_closed = "19:00:00", thursday_open = "09:00:00",
            thursday_closed = "19:00:00", friday_open = "09:00:00", friday_closed = "19:00:00", saturday_open = "09:00:00",
            saturday_closed = "19:00:00", sunday_open = "09:00:00", sunday_closed = "19:00:00",country_code,state_code,city_code,area_code;

    private int seats_no = 0;
    boolean acAvailable = false, carParking = false, bikeParking = false, onlinePayment = false, homeService = false, cardPayment = false, hotWater = false, musicAvailable = false, monday_status = false, tuesday_status = false, wednesday_status = false, thursday_status = false,
            friday_status = false, saturday_status = false, sunday_status = false;

    String salonImagePath = "", regCertPath = "", msMePath = "";
    private static final int REQUEST_PHONE_CALL=6;
    final static int GALLERY_PICK = 1, FILE_PICK = 4, MSME_PICK = 5;
    private final int PERMISSION_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_salon);


        if (!checkInternetConnection()) {
            Toast.makeText(getApplicationContext(), "Requires Internet Connection", Toast.LENGTH_LONG).show();

        }
        mToolbar = (Toolbar) findViewById(R.id.add_salon_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Salon");


        token = Prefs.getInstance(AddSalon.this).getString("token");
        loadingBar = new ProgressDialog(AddSalon.this);

        //files
        salonImage = findViewById(R.id.salonImage);
        uploadRegCert = findViewById(R.id.uploadRegCert);
        uploadMSMeCert = findViewById(R.id.uploadMsMeCert);

        UploadRegPath = findViewById(R.id.uploadRegPath);
        UploadMSmePath = findViewById(R.id.uploadMsMePath);
        uploadSalonPath = findViewById(R.id.uploadSalonPath);
        helpButton = findViewById(R.id.addSalonHelpButtonLink);
        termsAndConditionsText=findViewById(R.id.termsAndConditionsText);
        acceptCheck=findViewById(R.id.acceptCheck);

        termsAndConditionsText.setPaintFlags(termsAndConditionsText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        acceptCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept= acceptCheck.isChecked();
            }
        });

        termsAndConditionsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddSalonView.class);
                startActivity(intent);

            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });



        salonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23){
                    if(checkPermission()){
                        openGallery();
                    }
                    else {
                        requestPermission();
                    }

                }
                else{
                    openGallery();
                }

            }
        });

        uploadRegCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23){
                    if(checkPermission()){
                        filePicker();
                    }
                    else {
                        requestPermission();
                    }

                }
                else{
                    filePicker();
                }

            }
        });

        uploadMSMeCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23){
                    if(checkPermission()){
                        msMePicker();
                    }
                    else {
                        requestPermission();
                    }

                }
                else{
                    msMePicker();
                }

            }
        });

        //TextInputReference
        salonName = (TextInputLayout) findViewById(R.id.salonName);
        ownerName = findViewById(R.id.ownerName);
        salonMobile = findViewById(R.id.salonMobile);
        salonEmail = findViewById(R.id.salonEmail);
        salonStreet = findViewById(R.id.salonStreet);
        salonPinCode = findViewById(R.id.salonPinCode);


        //CheckboxReference
        ac_Available = findViewById(R.id.acAvailableCheck);
        car_Parking = findViewById(R.id.carParkingCheck);
        bike_Parking = findViewById(R.id.bikeParkingCheck);
        home_Service = findViewById(R.id.homeServiceCheck);
        online_Payment = findViewById(R.id.onlinePaymentCheck);
        card_Payment = findViewById(R.id.cardPaymentCheck);
        hot_Water=findViewById(R.id.hotWaterCheck);
        music=findViewById(R.id.musicCheck);


        mondayClosed = findViewById(R.id.mondayClosed);
        tuesdayClosed = findViewById(R.id.tuesdayClosed);
        wednesdayClosed = findViewById(R.id.wednesdayClosed);
        thursdayClosed = findViewById(R.id.thursdayClosed);
        fridayClosed = findViewById(R.id.fridayClosed);
        saturdayClosed = findViewById(R.id.saturdayClosed);
        sundayClosed = findViewById(R.id.sundayClosed);

        //spinners
        seats = findViewById(R.id.seats);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.seatNumber, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seats.setAdapter(arrayAdapter);



        initializeTimeButton();


        initList();
        seats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((String) parent.getItemAtPosition(position)).equals("Select seats")) {
                    seats_no = Integer.parseInt((String) parent.getItemAtPosition(position));
                } else {
                    seats_no = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        countriesName = findViewById(R.id.countriesName);
        countryAdapter = new CountryAdapter(this, mCountryItem);

        countriesName.setAdapter(countryAdapter);

        statesName = findViewById(R.id.statesName);
        citiesName = findViewById(R.id.citiesName);
        salonLocality = findViewById(R.id.salonLocality);

        statesName.setEnabled(false);
        citiesName.setEnabled(false);
        salonLocality.setEnabled(false);

        mStateItems = new ArrayList<>();
        mStateItems.add(new StateItem("Select State"));
        stateAdapter = new StateAdapter(this, mStateItems);
        statesName.setAdapter(stateAdapter);


        mCityItems = new ArrayList<>();
        mCityItems.add(new CityItem("Select City"));

        cityAdapter = new CityAdapter(this, mCityItems);

        citiesName.setAdapter(cityAdapter);

        mAreaItems=new ArrayList<>();
        mAreaItems.add(new AreaItem("Select Area"));
        areaAdapter=new AreaAdapter(this,mAreaItems);
        salonLocality.setAdapter(areaAdapter);



        submitForm = findViewById(R.id.submitFormButton);

        //radio Reference
        maleGender = findViewById(R.id.maleGender);
        femaleGender = findViewById(R.id.femaleGender);
        unisexGender = findViewById(R.id.unisexGender);

        maleGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "MALE";
            }
        });
        femaleGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "FEMALE";

            }
        });

        unisexGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "UNISEX";
            }
        });


        //shop Facilities Check

        ac_Available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acAvailable = ac_Available.isChecked();
            }
        });

        car_Parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carParking = car_Parking.isChecked();
            }
        });
        bike_Parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bikeParking = bike_Parking.isChecked();
            }
        });
        online_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlinePayment = online_Payment.isChecked();
            }
        });
        home_Service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeService = home_Service.isChecked();
            }
        });
        card_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardPayment = card_Payment.isChecked();
            }
        });

        hot_Water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotWater= hot_Water.isChecked();
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicAvailable= music.isChecked();
            }
        });

        //timing check
        mondayClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mondayClosed.isChecked()) {
                    monday_status = true;
                    monday_open = null;
                    monday_closed = null;
                    openMondayTime.setEnabled(false);
                    closeMondayTime.setEnabled(false);
                } else {
                    monday_status = false;
                    openMondayTime.setEnabled(true);
                    closeMondayTime.setEnabled(true);
                }
            }
        });

        tuesdayClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tuesdayClosed.isChecked()) {
                    tuesday_status = true;
                    tuesday_open = null;
                    tuesday_closed = null;
                    openTuesdayTime.setEnabled(false);
                    closeTuesdayTime.setEnabled(false);
                } else {
                    tuesday_status = false;
                    openTuesdayTime.setEnabled(true);
                    closeTuesdayTime.setEnabled(true);
                }
            }
        });
        wednesdayClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wednesdayClosed.isChecked()) {
                    wednesday_status = true;
                    wednesday_open = null;
                    wednesday_closed = null;
                    openWednesdayTime.setEnabled(false);
                    closeWednesdayTime.setEnabled(false);
                } else {
                    wednesday_status = false;
                    openWednesdayTime.setEnabled(true);
                    closeWednesdayTime.setEnabled(true);
                }
            }
        });

        thursdayClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thursdayClosed.isChecked()) {
                    thursday_status = true;
                    thursday_open = null;
                    thursday_closed = null;
                    openThursdayTime.setEnabled(false);
                    closeThursdayTime.setEnabled(false);
                } else {
                    thursday_status = false;
                    openThursdayTime.setEnabled(true);
                    closeThursdayTime.setEnabled(true);
                }
            }
        });

        fridayClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fridayClosed.isChecked()) {
                    friday_status = true;
                    friday_open = null;
                    friday_closed = null;
                    openFridayTime.setEnabled(false);
                    closeFridayTime.setEnabled(false);
                } else {
                    friday_status = false;
                    openFridayTime.setEnabled(true);
                    closeFridayTime.setEnabled(true);
                }
            }
        });

        saturdayClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saturdayClosed.isChecked()) {
                    saturday_status = true;
                    saturday_open = null;
                    saturday_closed = null;
                    openSaturdayTime.setEnabled(false);
                    closeSaturdayTime.setEnabled(false);
                } else {
                    saturday_status = false;
                    openSaturdayTime.setEnabled(true);
                    closeSaturdayTime.setEnabled(true);
                }
            }
        });

        sundayClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sundayClosed.isChecked()) {
                    sunday_status = true;
                    sunday_open = null;
                    sunday_closed = null;
                    openSundayTime.setEnabled(false);
                    closeSundayTime.setEnabled(false);
                } else {
                    sunday_status = false;
                    openSundayTime.setEnabled(true);
                    closeSundayTime.setEnabled(true);
                }
            }
        });


        //Address Check
        countriesName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryItem clickedItem = (CountryItem) parent.getItemAtPosition(position);
                if (!clickedItem.getCountry_name().equals("Select Country")) {

                    String clickedCountryCode = clickedItem.getCountry_code();
                    country_code=clickedItem.getCountry_code();
                    country = clickedItem.getCountry_name();
                    getStates(clickedCountryCode);

                } else {
                    statesName.setEnabled(false);
                    country = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        statesName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StateItem clickedItem = (StateItem) parent.getItemAtPosition(position);
                if (!clickedItem.getState_name().equals("Select State")) {
                    state = clickedItem.getState_name();
                    state_code=clickedItem.getState_code();
                    getCities(clickedItem.getState_code());
                } else {
                    citiesName.setEnabled(false);
                    state = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citiesName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CityItem clickedItem = (CityItem) parent.getItemAtPosition(position);
                if (!clickedItem.getCity_name().equals("Select City")) {
                    city = clickedItem.getCity_name();
                    city_code=clickedItem.getCity_code();
                    getAreas(city_code);
                } else {
                    city = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        salonLocality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AreaItem clickedItem=(AreaItem) parent.getItemAtPosition(position);
                if(!clickedItem.getArea_name().equals("Select Area")){
                    area=clickedItem.getArea_name();
                    area_code=clickedItem.getArea_code();
                }else{
                    area="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addSalonLoc = findViewById(R.id.addLocation);
        addSalonLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent mapIntent = new Intent(getApplicationContext(), MapDesignActivity.class);
                    startActivityForResult(mapIntent, REQUEST_CODE);
            }
        });
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);


        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp;

                if(!monday_status) {
                    temp= openMondayTime.getText().toString();
                    monday_open= ValidateUser.convertToDate(temp);

                    temp = closeMondayTime.getText().toString();
                    monday_closed=ValidateUser.convertToDate(temp);
                }

                if(!tuesday_status){
                    temp = openTuesdayTime.getText().toString();
                    tuesday_open=ValidateUser.convertToDate(temp);

                    temp = closeTuesdayTime.getText().toString();
                    thursday_closed=ValidateUser.convertToDate(temp);
                }

                if(!wednesday_status){
                    temp = openWednesdayTime.getText().toString();
                    wednesday_open = ValidateUser.convertToDate(temp);

                    temp = closeWednesdayTime.getText().toString();
                    wednesday_closed=ValidateUser.convertToDate(temp);
                }

                if(!thursday_status){
                    temp = openThursdayTime.getText().toString();
                    thursday_open = ValidateUser.convertToDate(temp);

                    temp = closeThursdayTime.getText().toString();
                    thursday_closed= ValidateUser.convertToDate(temp);
                }

                if(!friday_status){
                    temp = openFridayTime.getText().toString();
                    friday_open = ValidateUser.convertToDate(temp);

                    temp = closeFridayTime.getText().toString();
                    friday_closed= ValidateUser.convertToDate(temp);
                }

                if(!saturday_status){
                   temp = openSaturdayTime.getText().toString();
                    saturday_open = ValidateUser.convertToDate(temp);

                    temp = closeSaturdayTime.getText().toString();
                    saturday_closed= ValidateUser.convertToDate(temp);
                }
                if(!sunday_status){
                    temp = openSundayTime.getText().toString();
                    sunday_open = ValidateUser.convertToDate(temp);

                   temp = closeSundayTime.getText().toString();
                    sunday_closed= ValidateUser.convertToDate(temp);
                }
                salon_name = salonName.getEditText().getText().toString();
                String salon_name_error = ValidateUser.validateSalonName(salon_name);

                owner_name = ownerName.getEditText().getText().toString();
                String owner_name_error = ValidateUser.validateFullName(owner_name);

                salon_mobile = salonMobile.getEditText().getText().toString();
                String salon_mobile_error = ValidateUser.validatePhone(salon_mobile);

                salon_email = salonEmail.getEditText().getText().toString();
                String salon_email_error = ValidateUser.validateEmail(salon_email);

                street = salonStreet.getEditText().getText().toString();
                String street_error = ValidateUser.validateStreet(street);


                pincode = salonPinCode.getEditText().getText().toString();
                String pincode_error = ValidateUser.validatePinCode(pincode);

                if (!salon_name_error.equals("")) {
                    salonName.setError(salon_name_error);
                } else {
                    salonName.setError(null);
                }
                if (!owner_name_error.equals("")) {
                    ownerName.setError(owner_name_error);
                } else {
                    ownerName.setError(null);
                }
                if (!salon_mobile_error.equals("")) {
                    salonMobile.setError(salon_mobile_error);
                } else {
                    salonMobile.setError(null);
                }
                if (!salon_email_error.equals("")) {
                    salonEmail.setError(salon_email_error);
                } else {
                    salonEmail.setError(null);
                }
                if (!pincode_error.equals("")) {
                    salonPinCode.setError(pincode_error);
                } else {
                    salonPinCode.setError(null);
                }
                if(salonImagePath.equals("")){
                    uploadSalonPath.setText("Not Uploaded");
                    uploadSalonPath.setTextColor(Color.RED);
                }
                if(regCertPath.equals("")){
                   UploadRegPath.setText("Not Uploaded");
                   UploadRegPath.setTextColor(Color.RED);
                }
                if(msMePath.equals("")){
                    UploadMSmePath.setText("Not Uploaded");
                    UploadMSmePath.setTextColor(Color.RED);
                }
                if (!country.equals("")) {
                    if (!state.equals("")) {
                        if (!city.equals("")) {
                            if(area.equals("")) {
                                Toast.makeText(getApplicationContext(), "Select Area", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Select City", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"Select State",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Select Country",Toast.LENGTH_SHORT).show();
                }
                if (seats_no == 0) {
                    Toast.makeText(getApplicationContext(),"Select no. of seats",Toast.LENGTH_SHORT).show();
                }

                else if (latitude_value.equals("")) {
                    Toast.makeText(getApplicationContext(),"Please select location from map",Toast.LENGTH_SHORT).show();

                }
                else if (gender.equals("")) {
                    Toast.makeText(getApplicationContext(),"Select Gender Preferences",Toast.LENGTH_SHORT).show();
                }
                else if(!accept){
                    Toast.makeText(getApplicationContext(),"Please accept terms and conditions to continue",Toast.LENGTH_SHORT).show();
                }
                if (accept && !salonImagePath.equals("") && !regCertPath.equals("") && !msMePath.equals("") && salon_name_error.equals("") &&
                        owner_name_error.equals("") && salon_mobile_error.equals("") && salon_email_error.equals("")  && !area.equals("")
                        && pincode_error.equals("") && !country.equals("") && !state.equals("") && !city.equals("") && (seats_no!=0)
                        && !latitude_value.equals("") && !longitude_value.equals("")) {

                    submitForm.setEnabled(false);
                    ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    AddressDet addressDet=new AddressDet(latitude_value,longitude_value,street,area,pincode,city,state,country,area_code,city_code,state_code,country_code);

                    ShopFacility shopFacility=new ShopFacility(acAvailable,carParking,bikeParking,onlinePayment,homeService,cardPayment,hotWater,musicAvailable);

                    List<ShopTimings> shopTimingsList=new ArrayList<>();
                        ShopTimings shopTimingsMon=new ShopTimings("MONDAY",monday_open,monday_closed,monday_status);
                        ShopTimings shopTimingsTues=new ShopTimings("TUESDAY",tuesday_open,tuesday_closed,tuesday_status);
                        ShopTimings shopTimingsWed=new ShopTimings("WEDNESDAY",wednesday_open,wednesday_closed,wednesday_status);
                        ShopTimings shopTimingsThurs=new ShopTimings("THURSDAY",thursday_open,thursday_closed,thursday_status);
                        ShopTimings shopTimingsFri=new ShopTimings("FRIDAY",friday_open,friday_closed,friday_status);
                        ShopTimings shopTimingsSat=new ShopTimings("SATURDAY",saturday_open,saturday_closed,saturday_status);
                        ShopTimings shopTimingsSun=new ShopTimings("SUNDAY",sunday_open,sunday_closed,sunday_status);
                    shopTimingsList.add(shopTimingsMon);
                    shopTimingsList.add(shopTimingsTues);
                    shopTimingsList.add(shopTimingsWed);
                    shopTimingsList.add(shopTimingsThurs);
                    shopTimingsList.add(shopTimingsFri);
                    shopTimingsList.add(shopTimingsSat);
                    shopTimingsList.add(shopTimingsSun);

                    AddSalonData addSalonData=new AddSalonData(owner_name,salon_name,salon_mobile,salon_email,seats_no,salonImagePath,regCertPath
                            ,msMePath,gender.toUpperCase(),addressDet,shopFacility,shopTimingsList );

                    loadingBar.setTitle("Adding Salon");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    retrofit2.Call<ParseSalonAdd> addSalon=apiInterface.addSalonForm("Bearer "+token,addSalonData);

                    addSalon.enqueue(new Callback<ParseSalonAdd>() {
                        @Override
                        public void onResponse(Call<ParseSalonAdd> call, Response<ParseSalonAdd> response) {

                            if(!response.isSuccessful()){
                                submitForm.setEnabled(true);
                                loadingBar.dismiss();
                                Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (response.body().getCode() == 201) {
                                    loadingBar.dismiss();
                                    refresh(token);
                                } else if (response.body().getCode() == 400) {
                                    submitForm.setEnabled(true);
                                    loadingBar.dismiss();
                                    Toast.makeText(getApplicationContext(), "Fields are missing", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ParseSalonAdd> call, Throwable t) {
                            loadingBar.dismiss();
                            submitForm.setEnabled(true);
                            Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });
    }



    private void makePhoneCall() {
        if(ContextCompat.checkSelfPermission(AddSalon.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddSalon.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:+918482961714"));
            startActivity(intent);
        }
    }


    private void refresh(String token_param) {
        ApiInterface apiInterface=RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<jsonverify> refreshCall=apiInterface.refreshToken(token_param);
        refreshCall.enqueue(new Callback<jsonverify>() {
            @Override
            public void onResponse(Call<jsonverify> call, Response<jsonverify> response) {
                if(!response.isSuccessful()){
                    submitForm.setEnabled(true);
                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        LayoutInflater inflater=getLayoutInflater();
                        View layout=inflater.inflate(R.layout.customtoastlayout,(ViewGroup) findViewById(R.id.custom_toast_container));
                        TextView textView=(TextView) layout.findViewById(R.id.textToast);
                        textView.setText("Form Submitted Successfully");
                        Toast toast=new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM,0,135);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();

                        Prefs.getInstance(AddSalon.this).putString("token", response.body().getData().getToken());
                        try {
                            String payload = TokenDecode.decodeJWT(Prefs.getInstance(AddSalon.this).getString("token"));
                            JSONObject jsonObject = new JSONObject(payload);
                            JSONObject salonDetailsObject=jsonObject.getJSONObject("salonDetails");
                            if (salonDetailsObject.has("shopUuid")) {
                                Intent shopApplication = new Intent(getApplicationContext(), ApplicationNo.class);
                                startActivity(shopApplication);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (response.body().getCode() == 400) {
                        submitForm.setEnabled(true);
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getCode() == 121) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<jsonverify> call, Throwable t) {
                submitForm.setEnabled(true);
                Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void filePicker() {
        new MaterialFilePicker()
                .withActivity(AddSalon.this)
                .withRequestCode(FILE_PICK)
                .withHiddenFiles(true)
                .withTitle("Select file")
                .start();

    }
    private void msMePicker() {
        new MaterialFilePicker()
                .withActivity(AddSalon.this)
                .withRequestCode(MSME_PICK)
                .withHiddenFiles(true)
                .withTitle("Select file")
                .start();

    }


    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryIntent,"Select Picture"), GALLERY_PICK);
        

    }

    private void uploadImage(String path,String fileGroup) {
        if(path!=null) {
            File file=new File(path);
            RequestBody group=RequestBody.create(MultipartBody.FORM,fileGroup);
            ApiInterface apiInterface=RetrofitClient.getRetofitInstance().create(ApiInterface.class);
            loadingBar.setTitle("Uploading File");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            RequestBody requestFile= RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part requestData=MultipartBody.Part.createFormData("file",file.getName(),requestFile);

           retrofit2.Call<FileParse> uploadContent=apiInterface.upload("Bearer "+token,requestData,group);
            uploadContent.enqueue(new Callback<FileParse>() {
                @Override
                public void onResponse(Call<FileParse> call, Response<FileParse> response) {
                    if(!response.isSuccessful()){
                        loadingBar.dismiss();
                        Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (response.body().getCode() == 201) {
                            loadingBar.dismiss();
                            salonImagePath = response.body().getData().getFileLocation();
                            uploadSalonPath.setText("Uploaded");
                            uploadSalonPath.setTextColor(getResources().getColor(R.color.colorRed));

                        }
                        else if (response.body().getCode() == 400) {
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<FileParse> call, Throwable t) {
                    loadingBar.dismiss();
                    Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();

                }
            });


        }
        else{
            Toast.makeText(getApplicationContext(),"Please Select File",Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadReg(String path,String fileGroup) {
        if(path!=null) {
            if(path.endsWith("jpg") || path.endsWith("jpeg") || path.endsWith("png") || path.endsWith("pdf") || path.endsWith("doc")
                    ||path.endsWith("docx")) {
                File file = new File(path);
                RequestBody group = RequestBody.create(MultipartBody.FORM, fileGroup);
                ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                loadingBar.setTitle("Uploading File");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                RequestBody requestFile=null;
                if(path.endsWith("jpg") || path.endsWith("jpeg") || path.endsWith("png")) {
                     requestFile= RequestBody.create(MediaType.parse("image/*"), file);
                }
                else if(path.endsWith("pdf")){
                    requestFile= RequestBody.create(MediaType.parse("application/pdf"), file);
                }
                else {
                    requestFile= RequestBody.create(MediaType.parse("application/msword"), file);
                }

                MultipartBody.Part requestData = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                retrofit2.Call<FileParse> uploadContent = apiInterface.upload("Bearer " + token, requestData, group);
                uploadContent.enqueue(new Callback<FileParse>() {
                    @Override
                    public void onResponse(Call<FileParse> call, Response<FileParse> response) {
                        if (!response.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                        } else {
                            if (response.body().getCode() == 201) {
                                loadingBar.dismiss();
                                regCertPath = response.body().getData().getFileLocation();
                                UploadRegPath.setText("Uploaded");
                                UploadRegPath.setTextColor(getResources().getColor(R.color.colorRed));
                            } else if (response.body().getCode() == 400) {
                                loadingBar.dismiss();
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FileParse> call, Throwable t) {
                        loadingBar.dismiss();
                        Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(),"Supported file formats are jpeg, png, pdf, doc and docX",Toast.LENGTH_SHORT).show();
            }


        }
        else{
            Toast.makeText(getApplicationContext(),"Please Select File",Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadMSME(String path,String fileGroup) {
        if(path!=null) {
            if(path.endsWith("jpg") || path.endsWith("jpeg") || path.endsWith("png") || path.endsWith("pdf") || path.endsWith("doc")
                    ||path.endsWith("docx")) {
                File file = new File(path);
                RequestBody group = RequestBody.create(MultipartBody.FORM, fileGroup);
                ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                loadingBar.setTitle("Uploading File");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                RequestBody requestFile=null;
                if(path.endsWith("jpg") || path.endsWith("jpeg") || path.endsWith("png")) {
                    requestFile= RequestBody.create(MediaType.parse("image/*"), file);
                }
                else if(path.endsWith("pdf")){
                    requestFile= RequestBody.create(MediaType.parse("application/pdf"), file);
                }
                else {
                    requestFile= RequestBody.create(MediaType.parse("application/msword"), file);
                }

                MultipartBody.Part requestData = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                retrofit2.Call<FileParse> uploadContent = apiInterface.upload("Bearer " + token, requestData, group);
                uploadContent.enqueue(new Callback<FileParse>() {
                    @Override
                    public void onResponse(Call<FileParse> call, Response<FileParse> response) {
                        if (!response.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                        } else {
                            if (response.body().getCode() == 201) {
                                loadingBar.dismiss();
                                msMePath = response.body().getData().getFileLocation();
                                UploadMSmePath.setText("Uploaded");
                                UploadMSmePath.setTextColor(getResources().getColor(R.color.colorRed));
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<FileParse> call, Throwable t) {
                        loadingBar.dismiss();
                        Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(),"Supported file formats are jpeg, png, pdf, doc and docX",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Please Select File",Toast.LENGTH_SHORT).show();
        }
    }


    private void initializeTimeButton() {
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        openMondayTime = findViewById(R.id.mondayOpenTime);
        openTuesdayTime = findViewById(R.id.tuesdayOpenTime);
        openWednesdayTime = findViewById(R.id.wednesdayOpenTime);
        openThursdayTime = findViewById(R.id.thursdayOpenTime);
        openFridayTime = findViewById(R.id.fridayOpenTime);
        openSaturdayTime = findViewById(R.id.saturdayOpenTime);
        openSundayTime = findViewById(R.id.sundayOpenTime);

        closeMondayTime = findViewById(R.id.mondayCloseTime);
        closeTuesdayTime = findViewById(R.id.tuesdayCloseTime);
        closeWednesdayTime = findViewById(R.id.wednesdayCloseTime);
        closeThursdayTime = findViewById(R.id.thursdayCloseTime);
        closeFridayTime = findViewById(R.id.fridayCloseTime);
        closeSaturdayTime = findViewById(R.id.saturdayCloseTime);
        closeSundayTime = findViewById(R.id.sundayCloseTime);


        openMondayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            openMondayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            openMondayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });

        openTuesdayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            openTuesdayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            openTuesdayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });

        openWednesdayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            openWednesdayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            openWednesdayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });

        openThursdayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            openThursdayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            openThursdayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });

        openFridayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            openFridayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            openFridayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });

        openSaturdayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            openSaturdayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            openSaturdayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });

        openSundayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            openSundayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            openSundayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });

        closeMondayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            closeMondayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            closeMondayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });

        closeTuesdayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            closeTuesdayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            closeTuesdayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });

        closeWednesdayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            closeWednesdayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            closeWednesdayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });

        closeThursdayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            closeThursdayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            closeThursdayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });

        closeFridayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            closeFridayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            closeFridayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });
        closeSaturdayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            closeSaturdayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            closeSaturdayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();
            }
        });
        closeSundayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min=String.valueOf(minute);
                        if(min.length()==1) {
                            min="0"+min;
                        }
                        if (hourOfDay > 12) {
                            closeSundayTime.setText(hourOfDay % 12 + ":" + min + " PM");
                        } else {
                            closeSundayTime.setText(hourOfDay + ":" + min + " AM");
                        }
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();


            }
        });
    }
    private void getAreas(String city_code) {
        salonLocality.setEnabled(true);
        ApiInterface apiInterface=RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<ParseAreas> getAreasList=apiInterface.getAreas(city_code);
        getAreasList.enqueue(new Callback<ParseAreas>() {
            @Override
            public void onResponse(Call<ParseAreas> call, Response<ParseAreas> response) {
                mAreaItems.clear();
                mAreaItems.add(new AreaItem("Select Area"));
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            mAreaItems.add(new AreaItem(response.body().getData().get(i).getAreaName(), response.body().getData().get(i).getAreaCode()));
                        }
                        areaAdapter.notifyDataSetChanged();

                    }

                }
            }

            @Override
            public void onFailure(Call<ParseAreas> call, Throwable t) {

            }
        });
    }


    private void getCities(String state_code) {
        citiesName.setEnabled(true);
        ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<ParseCities> getStateList = apiInterface.getCities(state_code);

        getStateList.enqueue(new Callback<ParseCities>() {
            @Override
            public void onResponse(Call<ParseCities> call, Response<ParseCities> response) {
                mCityItems.clear();
                mCityItems.add(new CityItem("Select City"));
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            mCityItems.add(new CityItem(response.body().getData().get(i).getCityName(), response.body().getData().get(i).getCityCode()));
                        }
                        cityAdapter.notifyDataSetChanged();

                    }
                }

            }

            @Override
            public void onFailure(Call<ParseCities> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void getStates(String clickedCountryCode) {
        statesName.setEnabled(true);
        ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<ParseStates> getStateList = apiInterface.getStates(clickedCountryCode);

        getStateList.enqueue(new Callback<ParseStates>() {
            @Override
            public void onResponse(Call<ParseStates> call, Response<ParseStates> response) {
                mStateItems.clear();
                mStateItems.add(new StateItem("Select State"));
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            mStateItems.add(new StateItem(response.body().getData().get(i).getStateName(), response.body().getData().get(i).getStateCode()));
                        }
                        stateAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<ParseStates> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_LONG).show();

            }
        });


    }

    private void initList() {
        mCountryItem = new ArrayList<>();
        mCountryItem.add(new CountryItem("Select Country"));

        ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<ParseCountries> getCountryList = apiInterface.getCountries();

        getCountryList.enqueue(new Callback<ParseCountries>() {
            @Override
            public void onResponse(Call<ParseCountries> call, Response<ParseCountries> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            mCountryItem.add(new CountryItem(response.body().getData().get(i).getCountryName(), response.body().getData().get(i).getCountryCode()));
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ParseCountries> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                latitude.setText(data.getStringExtra("latitude"));
                longitude.setText(data.getStringExtra("longitude"));

                latitude_value = latitude.getText().toString();
                longitude_value = longitude.getText().toString();

            }
        }

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null) {
            salonImageUri = data.getData();
            String path = convert(salonImageUri);
            uploadImage(path, "LOGO_IMAGE");


//            Uri imageUri = data.getData();
//            CropImage.activity()
//                    .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1)
//                    .start(this);

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                salonImageUri = result.getUri();
//                salonImage.setImageURI(salonImageUri);
//            }
//        }
        }
        if(requestCode==FILE_PICK && resultCode==RESULT_OK && data!=null){
            String pdfPath=data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            uploadReg(pdfPath,"SHOP_DOCUMENT");

        }
        if(requestCode==MSME_PICK && resultCode==RESULT_OK && data!=null){
            String pdfPath=data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            uploadMSME(pdfPath,"SHOP_DOCUMENT");

        }
        else if(resultCode == Activity.RESULT_OK && requestCode == GPS_CODE){
            if(isGpsEnabled()){
                Toast.makeText(getApplicationContext(),"GPS is enabled",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"GPS is not enabled ,Unable to show location",Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(AddSalon.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(getApplicationContext(),"Please Give Permission To Upload File",Toast.LENGTH_SHORT).show();
        }
        else {
            ActivityCompat.requestPermissions(AddSalon.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(AddSalon.this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    LayoutInflater inflater=getLayoutInflater();
                    View layout=inflater.inflate(R.layout.customtoastlayout,(ViewGroup) findViewById(R.id.custom_toast_container));
                    TextView textView=(TextView) layout.findViewById(R.id.textToast);
                    textView.setText("Permission Successful");
                    Toast toast=new Toast(getApplicationContext());
                    toast.setGravity(Gravity.BOTTOM,0,135);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
                else {
                    Toast.makeText(AddSalon.this,"Permission Failed",Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_PHONE_CALL:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    makePhoneCall();
                }
                else {
                    Toast.makeText(AddSalon.this,"Permission Failed",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
    private String convert(Uri uri){
        Cursor cursor=getContentResolver().query(uri,null,null,null,null);
        cursor.moveToFirst();
        String document_id=cursor.getString(0);
        document_id=document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor=getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,MediaStore.Images.Media._ID+" = ? ",new String[]{document_id},null);
        cursor.moveToFirst();
        String path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }
    private boolean checkInternetConnection(){
        ConnectivityManager cn=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork=cn.getActiveNetworkInfo();

        return activeNetwork!=null;

    }
    private boolean isGpsEnabled(){
        LocationManager locationManager= (LocationManager) this.getSystemService(LOCATION_SERVICE);
        boolean providerEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(providerEnabled){
            return true;
        }else {
            AlertDialog alertDialog=new AlertDialog.Builder(this)
                    .setTitle("GPS Permissions")
                    .setMessage("GPS is required, Please enable GPS services")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent,GPS_CODE);


                        }
                    })
                    .setCancelable(false)
                    .show();
            return true;

        }
    }


}
