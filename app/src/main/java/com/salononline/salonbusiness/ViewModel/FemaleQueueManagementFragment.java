package com.salononline.salonbusiness.ViewModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.salononline.salonbusiness.Data.QueueBarberManageAdapter;
import com.salononline.salonbusiness.Parse.BarberBookingItem;
import com.salononline.salonbusiness.Parse.BookingQueuePersons;
import com.salononline.salonbusiness.Parse.ParseGetQueueBarber;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FemaleQueueManagementFragment extends Fragment {
    View view;
    String token,shopUuid,barberUuid;
    private RecyclerView femaleQueueRecyclerView;
    private BarberBookingItem barberBookingItem;
    private List<BookingQueuePersons> bookingQueuePersonsList;
    QueueBarberManageAdapter queueBarberManageAdapter;

    public FemaleQueueManagementFragment(String token,String shopUuid,String barberUuid) {
        this.token=token;
        this.shopUuid=shopUuid;
        this.barberUuid=barberUuid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.female_queue_management,container,false);
        femaleQueueRecyclerView=view.findViewById(R.id.femaleBarberQueueRecyclerView);
        queueBarberManageAdapter=new QueueBarberManageAdapter(getContext(),bookingQueuePersonsList,token,barberUuid,shopUuid);
        femaleQueueRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        femaleQueueRecyclerView.setAdapter(queueBarberManageAdapter);
        queueBarberManageAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barberBookingItem=new BarberBookingItem();
        bookingQueuePersonsList=new ArrayList<>();
        bookingQueuePersonsList=getQueueBarber();

        //bookingQueuePersonsList=barberBookingItem.getBookingPersons();
    }

    private List<BookingQueuePersons> getQueueBarber() {
        List<BookingQueuePersons> bookingQueuePersonsTemp=new ArrayList<>();
        ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<ParseGetQueueBarber> getQueueBarberCall=apiInterface.getQueueBarber("Bearer "+token,barberUuid);
        getQueueBarberCall.enqueue(new Callback<ParseGetQueueBarber>() {
            @Override
            public void onResponse(Call<ParseGetQueueBarber> call, Response<ParseGetQueueBarber> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                } else {
                    if(response.body().getCode()==200){
                        for(int i=0;i<response.body().getData().getBookingPersons().size();i++){
                            BookingQueuePersons bookingQueuePersons=new BookingQueuePersons();
                            bookingQueuePersons.setUserName(response.body().getData().getBookingPersons().get(i).getUserName());
                            bookingQueuePersons.setUserMobile(response.body().getData().getBookingPersons().get(i).getUserMobile());
                            bookingQueuePersons.setBookingServices(response.body().getData().getBookingPersons().get(i).getBookingServices());
                            bookingQueuePersons.setBooking(response.body().getData().getBookingPersons().get(i).getBooking());
                            bookingQueuePersons.setGender(response.body().getData().getBookingPersons().get(i).getGender());
                            bookingQueuePersons.setBarberName(response.body().getData().getBookingPersons().get(i).getBarberName());
                            bookingQueuePersons.setBarberUuid(response.body().getData().getBookingPersons().get(i).getBarberUuid());
                            bookingQueuePersons.setSubTotal(response.body().getData().getBookingPersons().get(i).getSubTotal());
                            bookingQueuePersons.setTotalServiceTime(response.body().getData().getBookingPersons().get(i).getTotalServiceTime());
                            bookingQueuePersons.setNoOfServices(response.body().getData().getBookingPersons().get(i).getNoOfServices());
                            bookingQueuePersons.setQueueNumber(response.body().getData().getBookingPersons().get(i).getQueueNumber());
                            bookingQueuePersons.setQueueStatus(response.body().getData().getBookingPersons().get(i).getQueueStatus());
                            bookingQueuePersons.setPersonUuid(response.body().getData().getBookingPersons().get(i).getPersonUuid());
                            bookingQueuePersons.setServingTime(response.body().getData().getBookingPersons().get(i).getServingTime());
                            bookingQueuePersonsTemp.add(bookingQueuePersons);
                            queueBarberManageAdapter.notifyDataSetChanged();
                        }
                    }
                    else if(response.body().getCode()==404){
                        Toast.makeText(getContext(), "No Bookings Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ParseGetQueueBarber> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Connection Required", Toast.LENGTH_SHORT).show();
            }
        });
        return bookingQueuePersonsTemp;
    }
}
