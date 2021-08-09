package com.salononline.salonbusiness.ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.salononline.salonbusiness.Data.FacilityItem;
import com.salononline.salonbusiness.Data.TokenDecode;
import com.salononline.salonbusiness.Parse.FileParse;
import com.salononline.salonbusiness.Parse.GetShopResult;
import com.salononline.salonbusiness.Parse.ParseUpdateImage;
import com.salononline.salonbusiness.Parse.UpdateImageData;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.Prefs;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.UpdateShopInfo;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalonProfile extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE =100 ;
    private static final int GALLERY_PICK =1 ;
    private Toolbar mToolbar;
    private BottomNavigationView navigationView;
    private String token,shopUuid,ownerName,salonEmail,salonMobile;
    boolean acAvailable = false, carParking = false, bikeParking = false, onlinePayment = false, homeService = false, cardPayment = false
            ,hotWater=false,musicAvailable=false;
    JSONObject jsonObject;
    ArrayList<FacilityItem> facilitiesList;
    ImageView salonProfileImage;
    Button updateSalonDetailsLink;
    private ProgressDialog loadingBar;
    private String url="https://www.salonqs.com/",salonImagePath;
    private TextView bookingLink,logOutLink,salonProfileId,salonProfileAddress,salonProfileNumber,salonProfileEmail,editImageLink,salonSendFeedbackLink,
                updateLoginNumber,salonProfileFacilities,salonProfileOwnerName,salonProfileOffers,salonRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_profile);
        mToolbar=findViewById(R.id.salonProfileToolbar);
        loadingBar=new ProgressDialog(SalonProfile.this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Salon Profile");
        
        try {
            String payload = TokenDecode.decodeJWT(Prefs.getInstance(SalonProfile.this).getString("token"));
            jsonObject= new JSONObject(payload);
            JSONObject salonDetailsObject=jsonObject.getJSONObject("salonDetails");
            token = Prefs.getInstance(SalonProfile.this).getString("token");
            shopUuid =salonDetailsObject.getString("shopUuid");
        } catch (Exception e) {
            e.printStackTrace();
        }

        salonProfileOwnerName=findViewById(R.id.salonProfileOwnerName);
        editImageLink=findViewById(R.id.editImageLink);
        updateSalonDetailsLink=findViewById(R.id.updateSalonDetailsLink);
        salonProfileImage=findViewById(R.id.salonProfilePic);
        salonProfileFacilities=findViewById(R.id.salonProfileFacilities);
        salonProfileId=findViewById(R.id.salonProfileId);
        salonProfileAddress=findViewById(R.id.salonProfileAddress);
        salonProfileNumber=findViewById(R.id.salonProfileNumber);
        salonProfileEmail=findViewById(R.id.salonProfileEmail);
        bookingLink=findViewById(R.id.salonProfileBooking);
        salonProfileOffers=findViewById(R.id.salonProfileOffers);
        logOutLink=findViewById(R.id.salonLOgOutLink);
        salonSendFeedbackLink=findViewById(R.id.salonSendFeedbackLink);
        salonRating=findViewById(R.id.salonRatingPlayStore);
        updateLoginNumber=findViewById(R.id.updateLoginNumber);
        navigationView=findViewById(R.id.navigation_view_profile);
        navigationView.setSelectedItemId(R.id.profile);

        salonRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.salonqs.businessapp");
//                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
//                startActivity(intent);
                Toast.makeText(getApplicationContext(),"We are not on play store we will update you once we are live",Toast.LENGTH_LONG).show();
            }
        });

        updateLoginNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateNumberIntent=new Intent(getApplicationContext(),UpdateNumber.class);
                updateNumberIntent.putExtra("token",token);
                startActivity(updateNumberIntent);
            }
        });

        salonSendFeedbackLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedbackIntent=new Intent(getApplicationContext(),FeedbackActivity.class);
                feedbackIntent.putExtra("shopUuid",shopUuid);
                feedbackIntent.putExtra("token",token);
                startActivity(feedbackIntent);
            }
        });
        
        editImageLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23){
                    if(checkPermission()){
                        UpdateImage();
                    }
                    else {
                        requestPermission();
                    }

                }
                else{
                   UpdateImage();
                }
            }
        });
        
        bookingLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookingProfileIntent=new Intent(getApplicationContext(),Booking.class);
                startActivity(bookingProfileIntent);
                overridePendingTransition(0,0);
            }
        });
        salonProfileOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offersProfileIntent=new Intent(getApplicationContext(),Offers.class);
                startActivity(offersProfileIntent);
                overridePendingTransition(0,0);
            }
        });
        logOutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater= LayoutInflater.from(v.getContext());
                View view=inflater.inflate(R.layout.confirmation_dialog,null);
                Button noButton=(Button) view.findViewById(R.id.noButton);
                Button yesButton=(Button) view.findViewById(R.id.yesButton);
                dialogBuilder.setView(view);
                final AlertDialog dialog=dialogBuilder.create();
                dialog.show();
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Prefs.getInstance(SalonProfile.this).putString("token","");
                        Prefs.getInstance(SalonProfile.this).putString("status","");
                        Intent mainIntent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                        dialog.dismiss();
                    }
                });

            }
        });


        getSalonData();


        salonProfileFacilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SalonFacilities.class);
                intent.putExtra("list",facilitiesList);
                startActivity(intent);
            }
        });


        updateSalonDetailsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent=new Intent(getApplicationContext(), UpdateShopInfo.class);
                updateIntent.putExtra("token",token);
                updateIntent.putExtra("shopUuid",shopUuid);
                updateIntent.putExtra("name",ownerName);
                updateIntent.putExtra("email",salonEmail);
                updateIntent.putExtra("mobile",salonMobile);
                updateIntent.putExtra("AC",acAvailable);
                updateIntent.putExtra("Water",hotWater);
                updateIntent.putExtra("BPark",bikeParking);
                updateIntent.putExtra("CPark",carParking);
                updateIntent.putExtra("OP",onlinePayment);
                updateIntent.putExtra("HS",homeService);
                updateIntent.putExtra("music",musicAvailable);
                updateIntent.putExtra("CP",cardPayment);

                startActivity(updateIntent);
            }
        });




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
                        Intent barberIntent=new Intent(getApplicationContext(),Barber.class);
                        startActivity(barberIntent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });
    }

    private void UpdateImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryIntent,"Select Picture"), GALLERY_PICK);


    }
    private void uploadImage(String path, String fileGroup) {
        if(path!=null) {
            File file=new File(path);
            RequestBody group=RequestBody.create(MultipartBody.FORM,fileGroup);
            ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
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
                            uploadImageToDb(salonImagePath);

                        }
                        if (response.body().getCode() == 400) {
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

    private void uploadImageToDb(final String salonImagePath) {
        ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        UpdateImageData updateImageData = new UpdateImageData(salonImagePath);
        retrofit2.Call<ParseUpdateImage> getImageCall = apiInterface.updateLogo("Bearer " + token, shopUuid, updateImageData);
        getImageCall.enqueue(new Callback<ParseUpdateImage>() {
            @Override
            public void onResponse(Call<ParseUpdateImage> call, Response<ParseUpdateImage> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        Picasso.with(getApplicationContext()).load(url + salonImagePath).into(salonProfileImage);

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ParseUpdateImage> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getSalonData() {
        facilitiesList=new ArrayList<>();

        ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<GetShopResult> getShopResultCall=apiInterface.getShopData("Bearer "+token,shopUuid);
        getShopResultCall.enqueue(new Callback<GetShopResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetShopResult> call, Response<GetShopResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        editImageLink.setEnabled(true);
                        updateSalonDetailsLink.setEnabled(true);
                        Picasso.with(getApplicationContext()).load(url + response.body().getData().getLogoImage()).into(salonProfileImage);

                        ownerName = response.body().getData().getOwnerName();
                        salonEmail = response.body().getData().getShopEmail();
                        salonMobile = response.body().getData().getShopMobile();

                        salonProfileOwnerName.setText(ownerName);
                        salonProfileId.setText(response.body().getData().getShopUuid());
                        salonProfileEmail.setText("   "+response.body().getData().getShopEmail());
                        salonProfileNumber.setText("   "+response.body().getData().getShopMobile());
                        salonProfileAddress.setText("   "+response.body().getData().getAddress().getLocality() + ", " +
                                response.body().getData().getAddress().getCityName() + ", " + response.body().getData().getAddress().getStateName() + ", " +
                                response.body().getData().getAddress().getCountryName());
                        if (response.body().getData().getShopFacility().isAcAvailable()) {
                            acAvailable = true;
                            facilitiesList.add(new FacilityItem("AC"));
                        }
                        if (response.body().getData().getShopFacility().isHotWater()) {
                            hotWater = true;
                            facilitiesList.add(new FacilityItem("Hot Water"));
                        }
                        if (response.body().getData().getShopFacility().isMusic()) {
                            musicAvailable = true;
                            facilitiesList.add(new FacilityItem("Music"));
                        }
                        if (response.body().getData().getShopFacility().isBikeParking()) {
                            bikeParking = true;
                            facilitiesList.add(new FacilityItem("Bike Parking"));
                        }
                        if (response.body().getData().getShopFacility().isCardPayment()) {
                            cardPayment = true;
                            facilitiesList.add(new FacilityItem("Card Payment"));
                        }
                        if (response.body().getData().getShopFacility().isCarParking()) {
                            carParking = true;
                            facilitiesList.add(new FacilityItem("Car Parking"));
                        }
                        if (response.body().getData().getShopFacility().isHomeService()) {
                            homeService = true;
                            facilitiesList.add(new FacilityItem("Home Service"));
                        }
                        if (response.body().getData().getShopFacility().isOnlinePayment()) {
                            onlinePayment = true;
                            facilitiesList.add(new FacilityItem("Online Payment"));
                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<GetShopResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(SalonProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(getApplicationContext(),"Please Give Permission To Upload File",Toast.LENGTH_SHORT).show();
        }
        else {
            ActivityCompat.requestPermissions(SalonProfile.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(SalonProfile.this,Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_container));
                TextView textView = (TextView) layout.findViewById(R.id.textToast);
                textView.setText("Permission Successful");
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 135);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            } else {
                Toast.makeText(SalonProfile.this, "Permission Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null) {
            Uri salonImageUri = data.getData();
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
}
