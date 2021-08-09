package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.salononline.salonbusiness.Data.BookingDetailServiceAdapter;
import com.salononline.salonbusiness.Parse.BookingServices;
import com.salononline.salonbusiness.Parse.QueueData;
import com.salononline.salonbusiness.Parse.json;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.Validations.ValidateUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingQueueDetails extends AppCompatActivity {
    Toolbar mToolbar;
    private String token,shopUuid,personUuid,id,created,queueNum,serveAt,mobile,name,gender,status;
    private List<BookingServices> bookingServicesList;
    private Button startButton,endButton;
    TextView bookingIdDetail,bookingDateDetail,queueDetailUserName,queueDetailUserGender,queueDetailUserMobile,queueDetailServingTime
            ,queueDetailQueueNumber;
    private RecyclerView bookingQueueDetailsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_queue_details);
        mToolbar=findViewById(R.id.bookingQueueDetailsToolbar);
        setSupportActionBar(mToolbar);

        token=getIntent().getStringExtra("token");
        shopUuid=getIntent().getStringExtra("shopUuid");
        id=getIntent().getStringExtra("id");
        created=getIntent().getStringExtra("created");
        queueNum=getIntent().getStringExtra("queueNum");
        serveAt=getIntent().getStringExtra("serve");
        personUuid=getIntent().getStringExtra("pid");
        name=getIntent().getStringExtra("name");
        gender=getIntent().getStringExtra("gender");
        mobile=getIntent().getStringExtra("mob");
        status=getIntent().getStringExtra("status");
        bookingServicesList=new ArrayList<>();
        bookingServicesList=(ArrayList<BookingServices>) getIntent().getSerializableExtra("bookingServicesList");


        getSupportActionBar().setTitle("");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view=layoutInflater.inflate(R.layout.custom_bar_top,null);
        actionBar.setCustomView(action_bar_view);
        startButton=findViewById(R.id.bookingStartButton);
        endButton=findViewById(R.id.bookingEndButton);

        bookingDateDetail=findViewById(R.id.bookingDateDetail);
        bookingIdDetail=findViewById(R.id.bookingIdDetail);

        if(status.equals("PENDING")){
            endButton.setVisibility(View.GONE);
        }

        queueDetailUserName=findViewById(R.id.queueDetailUserName);
        queueDetailUserGender=findViewById(R.id.queueDetailUserGender);
        queueDetailUserMobile=findViewById(R.id.queueDetailUserMobile);
        queueDetailQueueNumber=findViewById(R.id.queueDetailQueueNumber);
        queueDetailServingTime=findViewById(R.id.queueDetailServingTime);

        bookingIdDetail.setText(id);
        String[] arr=created.split("T",2);
        bookingDateDetail.setText(ValidateUser.convertTextDate(arr[0]));

        bookingQueueDetailsRecyclerView=findViewById(R.id.bookingQueueDetailsRecyclerView);
        BookingDetailServiceAdapter bookingDetailServiceAdapter=new BookingDetailServiceAdapter(getApplicationContext(),bookingServicesList);
        bookingQueueDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        bookingQueueDetailsRecyclerView.setAdapter(bookingDetailServiceAdapter);
        bookingDetailServiceAdapter.notifyDataSetChanged();

        queueDetailServingTime.setText(serveAt);
        queueDetailQueueNumber.setText(queueNum);
        queueDetailUserMobile.setText(mobile);
        queueDetailUserName.setText(name);
        if(gender.equals("MALE")) {
            queueDetailUserGender.setText(" (M) ");
        }
        else{
            queueDetailUserGender.setText(" (F) ");
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                QueueData queueData=new QueueData(personUuid);
                retrofit2.Call<json> startBookingCall=apiInterface.startBooking("Bearer "+token,id,queueData);
                startBookingCall.enqueue(new Callback<json>() {
                    @Override
                    public void onResponse(Call<json> call, Response<json> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                        } else {
                            if (response.body().getCode() == 200){
                                LayoutInflater inflater=getLayoutInflater();
                                View layout=inflater.inflate(R.layout.customtoastlayout,(ViewGroup) findViewById(R.id.custom_toast_container));
                                TextView textView=(TextView) layout.findViewById(R.id.textToast);
                                textView.setText(response.body().getMessage());
                                Toast toast=new Toast(getApplicationContext());
                                toast.setGravity(Gravity.BOTTOM,0,135);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                startButton.setVisibility(View.GONE);
                                endButton.setVisibility(View.VISIBLE);
                            }
                            else if (response.body().getCode()==404){
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else if (response.body().getCode()==400){
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<json> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                QueueData queueData=new QueueData(personUuid);
                retrofit2.Call<json> completeBookingCall=apiInterface.completeBooking("Bearer "+token,id,queueData);
                completeBookingCall.enqueue(new Callback<json>() {
                    @Override
                    public void onResponse(Call<json> call, Response<json> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                        } else {
                            if (response.body().getCode() == 200){
                                LayoutInflater inflater=getLayoutInflater();
                                View layout=inflater.inflate(R.layout.customtoastlayout,(ViewGroup) findViewById(R.id.custom_toast_container));
                                TextView textView=(TextView) layout.findViewById(R.id.textToast);
                                textView.setText(response.body().getMessage());
                                Toast toast=new Toast(getApplicationContext());
                                toast.setGravity(Gravity.BOTTOM,0,135);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                            }
                            else if (response.body().getCode()==404){
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else if (response.body().getCode()==400){
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<json> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Internet Connection Required", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}
