package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.salononline.salonbusiness.Parse.SignUpData;
import com.salononline.salonbusiness.Parse.json;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.Validations.ValidateUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessUserSignUpActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextInputLayout businessUserName,businessUserMobile,businessUserPassword,businessUserConfirmPass;
    private Button registerBusinessUserButton;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_user_sign_up);
        mToolbar=(Toolbar) findViewById(R.id.sign_up_toolbar);
        loadingBar=new ProgressDialog(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        businessUserName=(TextInputLayout) findViewById(R.id.businessUserFullName);
        businessUserMobile=(TextInputLayout) findViewById(R.id.businessUserMobile);
        businessUserPassword=(TextInputLayout) findViewById(R.id.businessUserPasswordEdit);
        businessUserConfirmPass=(TextInputLayout) findViewById(R.id.businessUserConfirmPassword);
        registerBusinessUserButton=(Button) findViewById(R.id.registerBusinessUser);

        registerBusinessUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fullName = businessUserName.getEditText().getText().toString().trim();
                final String mobile = businessUserMobile.getEditText().getText().toString().trim();
                final String password = businessUserPassword.getEditText().getText().toString().trim();
                final String confirmPass = businessUserConfirmPass.getEditText().getText().toString().trim();

                String nameError = ValidateUser.validateFullName(fullName);
                String phoneError = ValidateUser.validatePhone(mobile);
                String passwordError = ValidateUser.validatePassword(password);
                String confirmError = ValidateUser.validateConfirm(confirmPass, password);
                if (!nameError.equals("")) {
                    businessUserName.setError(nameError);
                } else {
                    businessUserName.setError(null);
                }
                if (!phoneError.equals("")) {
                    businessUserMobile.setError(phoneError);
                } else {
                    businessUserMobile.setError(null);
                }
                if (!passwordError.equals("")) {
                    businessUserPassword.setError(passwordError);
                } else {
                    businessUserPassword.setError(null);
                }
                if (!confirmError.equals("")) {
                    businessUserConfirmPass.setError(confirmError);
                } else {

                    businessUserConfirmPass.setError(null);
                }
                if (nameError.equals("") && passwordError.equals("") && phoneError.equals("") && confirmError.equals("")) {
                    registerBusinessUserButton.setEnabled(false);
                    ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    SignUpData signUpData=new SignUpData(fullName,mobile,password,confirmPass);
                    loadingBar.setTitle("Sending OTP to "+mobile);
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    retrofit2.Call<json> call=apiInterface.PostData(signUpData);
                    call.enqueue(new Callback<json>() {
                        @Override
                        public void onResponse(Call<json> call, Response<json> response) {
                            if(!response.isSuccessful()){
                                loadingBar.dismiss();
                                registerBusinessUserButton.setEnabled(true);
                                Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (response.body().getCode() == 201) {
                                    loadingBar.dismiss();
                                    registerBusinessUserButton.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent otpIntent = new Intent(getApplicationContext(), OtpActivity.class);
                                    otpIntent.putExtra("username", mobile);
                                    otpIntent.putExtra("otpEvent", "SIGNUP");
                                    otpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(otpIntent);
                                } else if (response.body().getCode() == 110) {
                                    loadingBar.dismiss();
                                    registerBusinessUserButton.setEnabled(true);
                                    businessUserMobile.setError(response.body().getMessage());
                                } else if (response.body().getCode() == 400) {
                                    loadingBar.dismiss();
                                    registerBusinessUserButton.setEnabled(true);
                                    businessUserMobile.setError(response.body().getValidationErrors().get(0).getMessage());
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<json> call, Throwable t) {
                            loadingBar.dismiss();
                            registerBusinessUserButton.setEnabled(true);
                            Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });

    }

}
