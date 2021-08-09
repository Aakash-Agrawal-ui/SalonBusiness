package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.salononline.salonbusiness.Parse.ParseUpdateImage;
import com.salononline.salonbusiness.Parse.QueryData;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.Validations.ValidateUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputLayout enterFeedback;
    private Button sendFeedback;
    ProgressDialog loadingBar;
    private String shopUuid,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        toolbar=findViewById(R.id.feedbackToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        loadingBar=new ProgressDialog(this);

        shopUuid=getIntent().getStringExtra("shopUuid");
        token=getIntent().getStringExtra("token");

        enterFeedback=findViewById(R.id.enterFeedback);
        sendFeedback=findViewById(R.id.sendFeedback);

        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback=enterFeedback.getEditText().getText().toString();
                String feedback_error= ValidateUser.validateFeedback(feedback);
                if(!feedback_error.equals("")){
                    enterFeedback.setError(feedback_error);
                }
                else {
                    ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    QueryData queryData=new QueryData(shopUuid,feedback);
                    retrofit2.Call<ParseUpdateImage> postQueryCall=apiInterface.postQuery("Bearer "+token,queryData);
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.setTitle("Please wait ...");
                    loadingBar.show();
                    postQueryCall.enqueue(new Callback<ParseUpdateImage>() {
                        @Override
                        public void onResponse(Call<ParseUpdateImage> call, Response<ParseUpdateImage> response) {
                            if(!response.isSuccessful()){
                                loadingBar.dismiss();
                                Toast.makeText(getApplicationContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(response.body().getCode()==200){
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
                                    enterFeedback.getEditText().setText("");
                                }
                                else if(response.body().getCode()==500){
                                    loadingBar.dismiss();
                                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ParseUpdateImage> call, Throwable t) {
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(),"Internet Required",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}
