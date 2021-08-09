package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.api.Api;
import com.salononline.salonbusiness.Data.BookingData;
import com.salononline.salonbusiness.Data.BookingDetailAdapter;
import com.salononline.salonbusiness.Parse.BookingPersons;
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

public class BookingDetails extends AppCompatActivity {
    Toolbar mToolbar;
    int from;
    Button bookingAcceptButton,bookingDeclineButton;
    LinearLayout acceptBookingLayout;
    List<BookingPersons> bookingPersonsList;
    String token,id,created,shopUuid;
    RecyclerView bookingDetailsRecyclerView;
    BookingDetailAdapter bookingDetailAdapter;
    TextView bookingDetailOffer,bookingDetailHygiene,bookingDetailAmount,bookingIdDetail,bookingDateDetail;
    Double hygieneCharge,subTotal,totalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        mToolbar=findViewById(R.id.bookingDetailsToolbar);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("");
        bookingPersonsList=new ArrayList<>();
        bookingPersonsList=(ArrayList<BookingPersons>) getIntent().getSerializableExtra("bookingList");
        from=getIntent().getIntExtra("from",0);
        token=getIntent().getStringExtra("token");
        shopUuid=getIntent().getStringExtra("shopUuid");
        id=getIntent().getStringExtra("id");
        created=getIntent().getStringExtra("created");
        hygieneCharge=getIntent().getDoubleExtra("hygiene",0.00);
        subTotal=getIntent().getDoubleExtra("offer",0.00);
        totalCost=getIntent().getDoubleExtra("amount",0.00);

        bookingDetailOffer=findViewById(R.id.bookingDetailsOfferDiscount);
        bookingDetailHygiene=findViewById(R.id.bookingDetailsHygieneValue);
        bookingDetailAmount=findViewById(R.id.bookingDetailsAmount);

        bookingDetailHygiene.setText("₹"+String.valueOf(hygieneCharge));

        bookingDetailAmount.setText("₹"+String.valueOf(totalCost));


        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view=layoutInflater.inflate(R.layout.custom_bar_top,null);
        actionBar.setCustomView(action_bar_view);

        acceptBookingLayout=findViewById(R.id.acceptBookingLayout);
        bookingAcceptButton=findViewById(R.id.bookingAcceptButton);
        bookingDeclineButton=findViewById(R.id.bookingDeclineButton);

        if(from==1){
            acceptBookingLayout.setVisibility(View.VISIBLE);
        }

        bookingDateDetail=findViewById(R.id.bookingDateDetail);
        bookingIdDetail=findViewById(R.id.bookingIdDetail);

        bookingIdDetail.setText(id);
        String[] arr=created.split("T",2);
        bookingDateDetail.setText(ValidateUser.convertTextDate(arr[0]));

        bookingDetailsRecyclerView=findViewById(R.id.bookingDetailsRecyclerView);
        bookingDetailAdapter=new BookingDetailAdapter(getApplicationContext(),bookingPersonsList);
        bookingDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(BookingDetails.this));
        bookingDetailsRecyclerView.setAdapter(bookingDetailAdapter);
        bookingDetailAdapter.notifyDataSetChanged();

        bookingAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                BookingData bookingData=new BookingData("accept");
                retrofit2.Call<json> acceptBookingCall=apiInterface.acceptBooking("Bearer "+token,id,bookingData);
                acceptBookingCall.enqueue(new Callback<json>() {
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

        bookingDeclineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                BookingData bookingData=new BookingData("Decline from salon as barber not available");
                retrofit2.Call<json> declineBookingCall=apiInterface.declineBooking("Bearer "+token,id,bookingData);
                declineBookingCall.enqueue(new Callback<json>() {
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
