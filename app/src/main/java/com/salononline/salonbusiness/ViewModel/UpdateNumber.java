package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.salononline.salonbusiness.Parse.GetUserProfileResult;
import com.salononline.salonbusiness.Parse.SendOtpClass;
import com.salononline.salonbusiness.Parse.json;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.Validations.ValidateUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateNumber extends AppCompatActivity {
    private String token;
    private Toolbar mToolbar;
    private TextInputLayout loginMobileNumber,loginPassword;
    private Button updateNumberButton;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_number);

        token=getIntent().getStringExtra("token");
        mToolbar=findViewById(R.id.update_number_toolbar);
        loadingBar=new ProgressDialog(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Update Number");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loginMobileNumber=findViewById(R.id.updateLoginMobileNumber);
        loginPassword=findViewById(R.id.updateLoginUserPasswordProfile);
        updateNumberButton=findViewById(R.id.updateNumberButtonUser);

        updateNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobile_number = loginMobileNumber.getEditText().getText().toString();
                String mobile_number_error = ValidateUser.validatePhone(mobile_number);

                final String password = loginPassword.getEditText().getText().toString();
                String password_error = ValidateUser.validatePassword(password);
                if (!mobile_number_error.equals("")) {
                    loginMobileNumber.setError(mobile_number_error);
                } else {
                    loginMobileNumber.setError(null);
                }
                if (!password_error.equals("")) {
                    loginPassword.setError(password_error);
                } else {
                    loginPassword.setError(null);
                }
                if (password_error.equals("") && mobile_number_error.equals("")) {
                    ApiInterface apiInterfaceClient = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    SendOtpClass sendOtpClass = new SendOtpClass(mobile_number,"ADD_UPDATE_MOBILE");
                    loadingBar.setTitle("Sending OTP to "+mobile_number);
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    retrofit2.Call<json> resendCall = apiInterfaceClient.ResendData(sendOtpClass);
                    resendCall.enqueue(new Callback<json>() {
                        @Override
                        public void onResponse(Call<json> call, Response<json> response) {
                            if(!response.isSuccessful()){
                                loadingBar.dismiss();
                                Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (response.body().getCode() == 200) {
                                    loadingBar.dismiss();
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent otpIntent = new Intent(getApplicationContext(), VerifyNumber.class);
                                    otpIntent.putExtra("username", mobile_number);
                                    otpIntent.putExtra("otpEvent", "ADD_UPDATE_MOBILE");
                                    otpIntent.putExtra("password", password);
                                    otpIntent.putExtra("token", token);
                                    otpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(otpIntent);
                                } else if (response.body().getCode() == 103) {
                                    loadingBar.dismiss();

                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else if (response.body().getCode() == 404) {
                                    loadingBar.dismiss();
                                    Toast.makeText(getApplicationContext(), response.body().getMessage() + " Sign up to continue", Toast.LENGTH_SHORT).show();

                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<json> call, Throwable t) {
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }

    private void getUserInfo() {
        ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<GetUserProfileResult> userProfileResultCall=apiInterface.userProfileResult("Bearer "+token);
        userProfileResultCall.enqueue(new Callback<GetUserProfileResult>() {
            @Override
            public void onResponse(Call<GetUserProfileResult> call, Response<GetUserProfileResult> response) {
                if(response.body().getCode()==200){
                    loginMobileNumber.getEditText().setText(response.body().getData().getUserMobile());
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserProfileResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
