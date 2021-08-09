package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.chaos.view.PinView;

import com.google.android.material.textfield.TextInputLayout;
import com.salononline.salonbusiness.Parse.ForgetData;
import com.salononline.salonbusiness.Parse.SendOtpClass;
import com.salononline.salonbusiness.Parse.json;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.Validations.ValidateUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView resendOtpLink,forget;
    private PinView enteredOtp;
    private Button forgetPasswordButton;
    private TextInputLayout newPassword,confirmPassword;
    private AlertDialog.Builder dialogBuilder;
    private ProgressDialog loadingBar;
    private String username,otpEvent,messageTitle;
    private int otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mToolbar=(Toolbar) findViewById(R.id.forget_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingBar=new ProgressDialog(this);

        otpEvent=getIntent().getStringExtra("otpEvent");
        username=getIntent().getStringExtra("username");
        enteredOtp=(PinView) findViewById(R.id.enteredOtpForget);
        forget=findViewById(R.id.forget);

        forget.setText("+91"+username);


        resendOtpLink=findViewById(R.id.resendOtpLink);
        resendOtpLink.setPaintFlags(resendOtpLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        newPassword=findViewById(R.id.newPassword);
        confirmPassword=findViewById(R.id.confirmPassword);
        forgetPasswordButton=(Button) findViewById(R.id.forgetPasswordButton);

        resendOtpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dialogTitle="OTP send successfully";
                resendOtpLink.setEnabled(false);
                dialogBuilder=new AlertDialog.Builder(v.getContext());
                dialogTitle=resendOtpFn();
                if(dialogTitle==null){
                    dialogTitle="OTP send successfully";
                }
                dialogBuilder.setTitle(dialogTitle);
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
                resendOtpLink.setEnabled(true);

            }
        });

        forgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String password = newPassword.getEditText().getText().toString().trim();
                final String confirmPass = confirmPassword.getEditText().getText().toString().trim();
                String passwordError = ValidateUser.validatePassword(password);
                String confirmError = ValidateUser.validateConfirm(confirmPass, password);


                if (enteredOtp.getText().toString().length()!=6){
                    Toast.makeText(getApplicationContext(),"Please enter OTP",Toast.LENGTH_SHORT).show();
                }
                if (!passwordError.equals("")) {
                    newPassword.setError(passwordError);
                } else {
                    newPassword.setError(null);
                }
                if (!confirmError.equals("")) {
                    confirmPassword.setError(confirmError);
                } else {
                    confirmPassword.setError(null);
                }
                if (enteredOtp.getText().toString().length()!=6){
                    Toast.makeText(getApplicationContext(),"Please enter OTP",Toast.LENGTH_SHORT).show();
                }
                if (enteredOtp.getText().toString().length()==6 && passwordError.equals("") && confirmError.equals("")){
                    forgetPasswordButton.setEnabled(false);
                    otp = Integer.parseInt(enteredOtp.getText().toString());
                    ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    ForgetData forgetData = new ForgetData(username,otpEvent,password,confirmPass,otp);

                    loadingBar.setTitle("Resetting Password");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    retrofit2.Call<json> resetPasswordCall = apiInterface.ResetPassword(forgetData);
                    resetPasswordCall.enqueue(new Callback<json>() {
                        @Override
                        public void onResponse(Call<json> call, Response<json> response) {
                            if(!response.isSuccessful()){
                                loadingBar.dismiss();
                                forgetPasswordButton.setEnabled(true);
                                Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (response.body().getCode() == 200) {
                                    loadingBar.dismiss();
                                    forgetPasswordButton.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                } else if (response.body().getCode() == 102) {
                                    loadingBar.dismiss();
                                    forgetPasswordButton.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                } else if (response.body().getCode() == 100) {
                                    loadingBar.dismiss();
                                    forgetPasswordButton.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                } else if (response.body().getCode() == 104) {
                                    loadingBar.dismiss();
                                    forgetPasswordButton.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage() + " Try after 24 Hours", Toast.LENGTH_SHORT).show();

                                } else if (response.body().getCode() == 404) {
                                    loadingBar.dismiss();
                                    forgetPasswordButton.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                } else if (response.body().getCode() == 303) {
                                    loadingBar.dismiss();
                                    forgetPasswordButton.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }


                        }

                        @Override
                        public void onFailure(Call<json> call, Throwable t) {
                            loadingBar.dismiss();
                            forgetPasswordButton.setEnabled(true);
                            Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_SHORT).show();

                        }
                    });
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
