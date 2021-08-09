package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.salononline.salonbusiness.Data.TokenDecode;
import com.salononline.salonbusiness.Parse.VerifyShopStatus;
import com.salononline.salonbusiness.Parse.jsonverify;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.Prefs;
import com.salononline.salonbusiness.Repositry.RetrofitClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationNo extends AppCompatActivity {
    private  Toolbar mToolbar;
    private String token,shopUuid;
    private TextView applicationNo;
    private Button helpButton;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_no);
        mToolbar = (Toolbar) findViewById(R.id.application_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Application Number");
        try {
            String payload = TokenDecode.decodeJWT(Prefs.getInstance(ApplicationNo.this).getString("token"));
            jsonObject= new JSONObject(payload);
            JSONObject salonDetailsObject=jsonObject.getJSONObject("salonDetails");
            token = Prefs.getInstance(ApplicationNo.this).getString("token");
            shopUuid =salonDetailsObject.getString("shopUuid");
        } catch (Exception e) {
            e.printStackTrace();
        }
        applicationNo=findViewById(R.id.applicationNo);

        helpButton=findViewById(R.id.applicationHelpButtonLink);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedbackIntent=new Intent(getApplicationContext(),FeedbackActivity.class);
                feedbackIntent.putExtra("shopUuid",shopUuid);
                feedbackIntent.putExtra("token",token);
                startActivity(feedbackIntent);
            }
        });


        ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<VerifyShopStatus> verifyCall=apiInterface.verifyShop("Bearer "+token,shopUuid);
        verifyCall.enqueue(new Callback<VerifyShopStatus>() {
            @Override
            public void onResponse(Call<VerifyShopStatus> call, Response<VerifyShopStatus> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.code() == 200) {
                        if (response.body().getCode() == 200) {
                            if (response.body().getData().isDigitalVerified()) {
                                refresh(token);
                                Intent homepage = new Intent(getApplicationContext(), Homepage.class);
                                startActivity(homepage);
                                finish();
                            } else {
                                applicationNo.setText(response.body().getData().getShopApplicationNo());
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"Unknown Error",Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<VerifyShopStatus> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void refresh(String token_param) {
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
                        Prefs.getInstance(ApplicationNo.this).putString("token", response.body().getData().getToken());
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
