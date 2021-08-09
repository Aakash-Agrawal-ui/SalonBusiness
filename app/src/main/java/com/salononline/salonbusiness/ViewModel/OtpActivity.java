package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.chaos.view.PinView;
import com.salononline.salonbusiness.Data.TokenDecode;
import com.salononline.salonbusiness.Parse.SendOtpClass;
import com.salononline.salonbusiness.Parse.SignUpVeify;
import com.salononline.salonbusiness.Parse.json;
import com.salononline.salonbusiness.Parse.jsonverify;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.Prefs;
import com.salononline.salonbusiness.Repositry.RetrofitClient;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView resendOtp,verify;
    private PinView enteredOtp;
    private Button verifyUser;
    private AlertDialog.Builder dialogBuilder;
    private String otpEvent,username,messageTitle;
    private int otp;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        otpEvent=getIntent().getStringExtra("otpEvent");
        username=getIntent().getStringExtra("username");
        loadingBar=new ProgressDialog(this);


        enteredOtp=(PinView) findViewById(R.id.enteredOtp);
        verifyUser=findViewById(R.id.verifyOtpButton);
        verify=findViewById(R.id.verify);
        verify.setText("+91"+username);

        mToolbar=(Toolbar) findViewById(R.id.otp_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Enter Verification Code");
        resendOtp=(TextView) findViewById(R.id.resendOtp);

        resendOtp.setPaintFlags(resendOtp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp.setEnabled(false);

                String dialogMessage="OTP send successfully";
                dialogMessage=resendOtpFn();
                if(dialogMessage==null){
                    dialogMessage="OTP send successfully";
                }

                resendOtp.setEnabled(true);
                dialogBuilder=new AlertDialog.Builder(v.getContext());

                dialogBuilder.setTitle(dialogMessage);
                dialogBuilder.setCancelable(true);
                dialogBuilder.setIcon(R.drawable.messageicon);
                dialogBuilder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                AlertDialog dialog=dialogBuilder.create();
                dialog.show();
                resendOtp.setEnabled(true);


            }
        });

        verifyUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (enteredOtp.getText().toString().length()!=6){
                    Toast.makeText(getApplicationContext(),"Please enter OTP",Toast.LENGTH_SHORT).show();
                }
                else {
                    verifyUser.setEnabled(false);
                    otp = Integer.parseInt(enteredOtp.getText().toString());
                    ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    SignUpVeify signUpVeify = new SignUpVeify(username, otpEvent, otp);


                    if (otpEvent.equals("SIGNUP")) {
                        loadingBar.setTitle("Creating New User");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                        retrofit2.Call<jsonverify> newCall = apiInterface.VerifyData(signUpVeify);
                        newCall.enqueue(new Callback<jsonverify>() {
                            @Override
                            public void onResponse(Call<jsonverify> call, Response<jsonverify> response) {
                                if(!response.isSuccessful()){
                                    loadingBar.dismiss();
                                    verifyUser.setEnabled(true);
                                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    if (response.body().getCode() == 200) {
                                        loadingBar.dismiss();
                                        verifyUser.setEnabled(true);
                                        LayoutInflater inflater=getLayoutInflater();
                                        View layout=inflater.inflate(R.layout.customtoastlayout,(ViewGroup) findViewById(R.id.custom_toast_container));
                                        TextView textView=(TextView) layout.findViewById(R.id.textToast);
                                        textView.setText("User Created Successfully");
                                        Toast toast=new Toast(getApplicationContext());
                                        toast.setGravity(Gravity.BOTTOM,0,135);
                                        toast.setDuration(Toast.LENGTH_SHORT);
                                        toast.setView(layout);
                                        toast.show();
                                        Prefs.getInstance(OtpActivity.this).putString("token", response.body().getData().getToken());
                                        Intent addSalon = new Intent(getApplicationContext(), AddSalon.class);
                                        startActivity(addSalon);
                                        finish();


                                    } else if (response.body().getCode() == 102) {
                                        loadingBar.dismiss();
                                        verifyUser.setEnabled(true);
                                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    } else if (response.body().getCode() == 100) {
                                        loadingBar.dismiss();
                                        verifyUser.setEnabled(true);
                                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    } else if (response.body().getCode() == 104) {
                                        loadingBar.dismiss();
                                        verifyUser.setEnabled(true);
                                        Toast.makeText(getApplicationContext(), response.body().getMessage() + " Try after 24 Hours", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<jsonverify> call, Throwable t) {
                                loadingBar.dismiss();
                                verifyUser.setEnabled(true);
                                Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_SHORT).show();

                            }
                        });

                }
                else if (otpEvent.equals("LOGIN")) {
                        loadingBar.setTitle("Logging in ");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();

                        retrofit2.Call<jsonverify> newCall = apiInterface.VerifyLoginData(signUpVeify);
                        newCall.enqueue(new Callback<jsonverify>() {
                            @Override
                            public void onResponse(Call<jsonverify> call, Response<jsonverify> response) {
                                if(!response.isSuccessful()){
                                    loadingBar.dismiss();
                                    verifyUser.setEnabled(true);
                                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    if (response.body().getCode() == 200) {
                                        loadingBar.dismiss();
                                        verifyUser.setEnabled(true);

                                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        Prefs.getInstance(OtpActivity.this).putString("token", response.body().getData().getToken());
                                        try {
                                            String payload = TokenDecode.decodeJWT(Prefs.getInstance(OtpActivity.this).getString("token"));
                                            JSONObject jsonObject = new JSONObject(payload);
                                            JSONObject salonDetailsObject=jsonObject.getJSONObject("salonDetails");
                                            if (salonDetailsObject.has("verified")) {
                                                if (salonDetailsObject.getBoolean("verified")) {
                                                    Intent homepage = new Intent(getApplicationContext(), Homepage.class);
                                                    startActivity(homepage);
                                                    finish();

                                                } else {
                                                    if (salonDetailsObject.has("shopUuid")) {
                                                        Intent shopApplication = new Intent(getApplicationContext(), ApplicationNo.class);
                                                        startActivity(shopApplication);
                                                        finish();
                                                    } else {
                                                        Intent addSalon = new Intent(getApplicationContext(), AddSalon.class);
                                                        startActivity(addSalon);
                                                        finish();
                                                    }
                                                }
                                            } else {
                                                if (salonDetailsObject.has("shopUuid")) {
                                                    Intent shopApplication = new Intent(getApplicationContext(), ApplicationNo.class);
                                                    startActivity(shopApplication);
                                                    finish();
                                                } else {
                                                    Intent addSalon = new Intent(getApplicationContext(), AddSalon.class);
                                                    startActivity(addSalon);
                                                    finish();

                                                }
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (response.body().getCode() == 102) {
                                        loadingBar.dismiss();
                                        verifyUser.setEnabled(true);
                                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    } else if (response.body().getCode() == 100) {
                                        loadingBar.dismiss();
                                        verifyUser.setEnabled(true);
                                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    } else if (response.body().getCode() == 104) {
                                        loadingBar.dismiss();
                                        verifyUser.setEnabled(true);
                                        Toast.makeText(getApplicationContext(), response.body().getMessage() + " Try after 24 Hours", Toast.LENGTH_SHORT).show();

                                    } else if (response.body().getCode() == 404) {
                                        loadingBar.dismiss();
                                        verifyUser.setEnabled(true);
                                        Toast.makeText(getApplicationContext(), response.body().getMessage() + " Sign up to continue", Toast.LENGTH_SHORT).show();

                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<jsonverify> call, Throwable t) {
                                loadingBar.dismiss();
                                verifyUser.setEnabled(true);
                                Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                }
            }
        });
    }

    private String resendOtpFn() {
        ApiInterface apiInterfaceClient=RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        SendOtpClass sendOtpClass=new SendOtpClass(username,otpEvent);
        retrofit2.Call<json> resendCall=apiInterfaceClient.ResendData(sendOtpClass);
        resendCall.enqueue(new Callback<json>() {
            @Override
            public void onResponse(Call<json> call, Response<json> response) {
                if(!response.isSuccessful()){
                    messageTitle="Service Unavailable, Try after Some Time";
                }
                else {
                    if (response.body().getCode() == 200) {
                        messageTitle = response.body().getMessage();
                    } else if (response.body().getCode() == 103) {
                        messageTitle = response.body().getMessage();
                    }
                }

            }
            @Override
            public void onFailure(Call<json> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();

            }
        });
        return messageTitle;

    }
}
