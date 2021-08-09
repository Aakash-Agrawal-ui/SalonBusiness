package com.salononline.salonbusiness.ViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.salononline.salonbusiness.Data.TokenDecode;
import com.salononline.salonbusiness.Parse.SendOtpClass;
import com.salononline.salonbusiness.Parse.SignUpData;
import com.salononline.salonbusiness.Parse.json;
import com.salononline.salonbusiness.Parse.jsonverify;
import com.salononline.salonbusiness.Parse.jsonverifylogin;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.Prefs;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.Validations.ValidateUser;

import org.json.JSONObject;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView businessSignUpLink,forgetPasswordLink,policiesText;
    private Button sendOtp;
    private Button loginBusinessuser;
    private TextInputLayout phoneNumber,passwordEdit;
    String policy="Terms of Services, Privacy Policy and Content Policy";
    private Toolbar mToolbar;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Prefs.getInstance(MainActivity.this).getString("token").equals("")) {
            try {
                String payload = TokenDecode.decodeJWT(Prefs.getInstance(MainActivity.this).getString("token"));
                JSONObject jsonObject = new JSONObject(payload);
                if (jsonObject.getLong("exp") <= TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
                    refreshToken(Prefs.getInstance(MainActivity.this).getString("token"));
                }
                else{
                    try {
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        phoneNumber = (TextInputLayout) findViewById(R.id.businessUserPhoneNum);
        businessSignUpLink = findViewById(R.id.signUpLink);
        sendOtp = findViewById(R.id.businessSendOtp);
        loadingBar = new ProgressDialog(this);
        forgetPasswordLink = findViewById(R.id.businessForgetPassword);
        loginBusinessuser = findViewById(R.id.businessUserLogin);
        policiesText = findViewById(R.id.policiesTextAnother);
        passwordEdit = (TextInputLayout) findViewById(R.id.businessUserPassword);


        SpannableString ss = new SpannableString(policy);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(getApplicationContext(), PdfView.class);
                startActivity(intent);

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(getApplicationContext(), PolicyView.class);
                startActivity(intent);

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);

            }
        };
        ss.setSpan(clickableSpan1, 0, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2, 19, 52, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        policiesText.setText(ss);
        policiesText.setMovementMethod(LinkMovementMethod.getInstance());


        forgetPasswordLink.setPaintFlags(forgetPasswordLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        businessSignUpLink.setPaintFlags(businessSignUpLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        businessSignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BusinessUserSignUpActivity.class));
            }
        });

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNum = phoneNumber.getEditText().getText().toString();
                String phoneError = ValidateUser.validatePhone(phoneNum);
                if (!phoneError.equals("")) {
                    phoneNumber.setError(phoneError);
                } else {
                    phoneNumber.setError(null);
                    sendOtp.setEnabled(false);
                    ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    SendOtpClass sendOtpClass = new SendOtpClass(phoneNum, "LOGIN");
                    loadingBar.setTitle("Sending OTP to " + phoneNum);
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();


                    final retrofit2.Call<json> loginWithOtp = apiInterface.ResendData(sendOtpClass);
                    loginWithOtp.enqueue(new Callback<json>() {
                        @Override
                        public void onResponse(Call<json> call, Response<json> response) {
                            if (!response.isSuccessful()) {
                                loadingBar.dismiss();
                                sendOtp.setEnabled(true);
                                Toast.makeText(getApplicationContext(), "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.body().getCode() == 200) {
                                    loadingBar.dismiss();
                                    sendOtp.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent otpIntent = new Intent(getApplicationContext(), OtpActivity.class);
                                    otpIntent.putExtra("username", phoneNum);
                                    otpIntent.putExtra("otpEvent", "LOGIN");
                                    otpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(otpIntent);
                                } else if (response.body().getCode() == 103) {
                                    loadingBar.dismiss();
                                    sendOtp.setEnabled(true);

                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else if (response.body().getCode() == 404) {
                                    loadingBar.dismiss();
                                    sendOtp.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage() + " Sign up to continue", Toast.LENGTH_SHORT).show();

                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<json> call, Throwable t) {
                            sendOtp.setEnabled(true);
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_LONG).show();

                        }
                    });

                }

            }
        });

        forgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNum = phoneNumber.getEditText().getText().toString();
                String phoneError = ValidateUser.validatePhone(phoneNum);
                if (!phoneError.equals("")) {
                    phoneNumber.setError(phoneError);
                } else {
                    phoneNumber.setError(null);
                    forgetPasswordLink.setEnabled(false);
                    ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    SendOtpClass sendOtpClass = new SendOtpClass(phoneNum, "FORGOT_PASSWORD");
                    loadingBar.setTitle("Sending OTP to " + phoneNum);
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();


                    final retrofit2.Call<json> forgetPasswordOtp = apiInterface.ResendData(sendOtpClass);
                    forgetPasswordOtp.enqueue(new Callback<json>() {
                        @Override
                        public void onResponse(Call<json> call, Response<json> response) {
                            if (!response.isSuccessful()) {
                                loadingBar.dismiss();
                                forgetPasswordLink.setEnabled(true);
                                Toast.makeText(getApplicationContext(), "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.body().getCode() == 200) {
                                    loadingBar.dismiss();
                                    forgetPasswordLink.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent otpIntent = new Intent(getApplicationContext(), ForgetPassword.class);
                                    otpIntent.putExtra("username", phoneNum);
                                    otpIntent.putExtra("otpEvent", "FORGOT_PASSWORD");
                                    startActivity(otpIntent);
                                } else if (response.body().getCode() == 103) {
                                    loadingBar.dismiss();
                                    forgetPasswordLink.setEnabled(true);

                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else if (response.body().getCode() == 404) {
                                    loadingBar.dismiss();
                                    forgetPasswordLink.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), response.body().getMessage() + " Sign up to continue", Toast.LENGTH_SHORT).show();

                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<json> call, Throwable t) {
                            forgetPasswordLink.setEnabled(true);
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_LONG).show();

                        }
                    });

                }

            }
        });

        loginBusinessuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNum = phoneNumber.getEditText().getText().toString();
                String phoneError = ValidateUser.validatePhone(phoneNum);
                final String passwordVal = passwordEdit.getEditText().getText().toString();
                String passwordError = ValidateUser.validatePassword(passwordVal);
                if (!phoneError.equals("")) {
                    phoneNumber.setError(phoneError);
                } else {
                    phoneNumber.setError(null);
                }
                if (!passwordError.equals("")) {
                    passwordEdit.setError(passwordError);
                } else {
                    passwordEdit.setError(null);
                }
                if (passwordError.equals("") && phoneError.equals("")) {
                    loginBusinessuser.setEnabled(false);
                    ApiInterface apiInterface = RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    SignUpData signUpData = new SignUpData(phoneNum, passwordVal);
                    loadingBar.setTitle("Logging In");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    retrofit2.Call<jsonverifylogin> loginWithPassword = apiInterface.VerifyLoginWithPassword(signUpData);
                    loginWithPassword.enqueue(new Callback<jsonverifylogin>() {
                        @Override
                        public void onResponse(Call<jsonverifylogin> call, Response<jsonverifylogin> response) {
                            if (response.code() == 502 || response.code() == 503 || response.code() == 400) {
                                loadingBar.dismiss();
                                loginBusinessuser.setEnabled(true);
                                Toast.makeText(getApplicationContext(), "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.code() == 200 || response.code() == 404) {
                                    if (response.body().getCode() == 200) {
                                        loadingBar.dismiss();
                                        loginBusinessuser.setEnabled(true);
                                        Prefs.getInstance(MainActivity.this).putString("token", response.body().getData().getToken());
                                        LayoutInflater inflater = getLayoutInflater();
                                        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_container));
                                        TextView textView = (TextView) layout.findViewById(R.id.textToast);
                                        textView.setText("Logged In Successfully");
                                        Toast toast = new Toast(getApplicationContext());
                                        toast.setGravity(Gravity.BOTTOM, 0, 135);
                                        toast.setDuration(Toast.LENGTH_SHORT);
                                        toast.setView(layout);
                                        toast.show();
                                        try {
                                            String payload = TokenDecode.decodeJWT(Prefs.getInstance(MainActivity.this).getString("token"));
                                            JSONObject jsonObject = new JSONObject(payload);
                                            JSONObject salonDetailsObject = jsonObject.getJSONObject("salonDetails");
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
                                    } else if (response.body().getCode() == 404) {
                                        loadingBar.dismiss();
                                        loginBusinessuser.setEnabled(true);
                                        phoneNumber.setError(response.body().getMessage());

                                    }
                                } else if (response.code() == 401) {
                                    loadingBar.dismiss();
                                    loginBusinessuser.setEnabled(true);
                                    phoneNumber.setError("Mobile Number or Password is incorrect");

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<jsonverifylogin> call, Throwable t) {
                            loginBusinessuser.setEnabled(true);
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Internet Required", Toast.LENGTH_LONG).show();

                        }
                    });

                }

            }
        });

    }

    private void refreshToken(String token_param) {
        ApiInterface apiInterface=RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<jsonverify> refreshCall=apiInterface.refreshToken(token_param);
        refreshCall.enqueue(new Callback<jsonverify>() {
            @Override
            public void onResponse(Call<jsonverify> call, Response<jsonverify> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        Prefs.getInstance(MainActivity.this).putString("token", response.body().getData().getToken());
                        try {
                            String payload = TokenDecode.decodeJWT(Prefs.getInstance(MainActivity.this).getString("token"));
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

                    } else if (response.body().getCode() == 400) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getCode() == 121) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<jsonverify> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
