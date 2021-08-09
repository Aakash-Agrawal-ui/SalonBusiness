package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.chaos.view.PinView;
import com.salononline.salonbusiness.Parse.SendOtpClass;
import com.salononline.salonbusiness.Parse.UpdateNumberData;
import com.salononline.salonbusiness.Parse.json;
import com.salononline.salonbusiness.Parse.jsonverify;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.Prefs;
import com.salononline.salonbusiness.Repositry.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyNumber extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView resendOtp,verifyNewNum;
    private PinView enteredOtp;
    private Button verifyUser;
    private AlertDialog.Builder dialogBuilder;
    private String otpEvent,username,messageTitle,password,token;
    private int otp;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_number);

        otpEvent=getIntent().getStringExtra("otpEvent");
        username=getIntent().getStringExtra("username");
        password=getIntent().getStringExtra("password");
        token=getIntent().getStringExtra("token");
        loadingBar=new ProgressDialog(this);


        enteredOtp=(PinView) findViewById(R.id.enteredOtpUpdate);
        verifyUser=findViewById(R.id.verifyOtpButtonUpdate);
        verifyNewNum=findViewById(R.id.verifyNewNumber);
        verifyNewNum.setText("+91"+username);

        mToolbar=(Toolbar) findViewById(R.id.otp_toolbar_update);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("OTP Verification");
        resendOtp=(TextView) findViewById(R.id.resendOtpUpdate);

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
            public void onClick(View v) {
                if (enteredOtp.getText().toString().length()!=6){
                    Toast.makeText(getApplicationContext(),"Please enter OTP",Toast.LENGTH_SHORT).show();
                }
                else{
                    verifyUser.setEnabled(false);
                    otp = Integer.parseInt(enteredOtp.getText().toString());
                    ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    UpdateNumberData updateNumberData=new UpdateNumberData(username,otpEvent,password,otp);
                    retrofit2.Call<jsonverify> updateNumberCallResult=apiInterface.updateNumber("Bearer "+token,updateNumberData);
                    updateNumberCallResult.enqueue(new Callback<jsonverify>() {
                        @Override
                        public void onResponse(Call<jsonverify> call, Response<jsonverify> response) {
                            if(!response.isSuccessful()){
                                verifyUser.setEnabled(true);
                                Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (response.body().getCode() == 200) {
                                    Prefs.getInstance(VerifyNumber.this).putString("token", response.body().getData().getToken());
                                    LayoutInflater inflater=getLayoutInflater();
                                    View layout=inflater.inflate(R.layout.customtoastlayout,(ViewGroup) findViewById(R.id.custom_toast_container));
                                    TextView textView=(TextView) layout.findViewById(R.id.textToast);
                                    textView.setText("Number Updated Successfully");
                                    Toast toast=new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.BOTTOM,0,135);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();
                                    startActivity(new Intent(getApplicationContext(), SalonProfile.class));
                                    finish();
                                } else if (response.body().getCode() == 102) {
                                    verifyUser.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else if (response.body().getCode() == 104) {
                                    verifyUser.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else if (response.body().getCode() == 100) {
                                    verifyUser.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else if (response.body().getCode() == 110) {
                                    verifyUser.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    verifyUser.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<jsonverify> call, Throwable t) {
                            verifyUser.setEnabled(true);
                            Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private String resendOtpFn() {
        ApiInterface apiInterfaceClient= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
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
