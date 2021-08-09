package com.salononline.salonbusiness.ViewModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
;

import com.salononline.salonbusiness.Data.GetBookingListAdapter;
import com.salononline.salonbusiness.Parse.BookingSingleItem;
import com.salononline.salonbusiness.Parse.ParseBookingListResult;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingListFragment extends Fragment {
    View view;
    private String token,shopUuid;
    private TextView refresh;
    private List<BookingSingleItem> bookingSingleItems;
    private RecyclerView bookingListRecyclerView;

    GetBookingListAdapter getBookingListAdapter;


    public BookingListFragment(String token,String shopUuid) {
        this.token=token;
        this.shopUuid=shopUuid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.booking_list,container,false);

        bookingListRecyclerView=view.findViewById(R.id.bookingListRecyclerView);



        getBookingListAdapter=new GetBookingListAdapter(getContext(),bookingSingleItems,token,shopUuid);

        bookingListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookingListRecyclerView.setAdapter(getBookingListAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookingSingleItems=new ArrayList<>();
        bookingSingleItems=getAllBookingList();

    }

    private List<BookingSingleItem> getAllBookingList() {
        final List<BookingSingleItem> bookingListTemp=new ArrayList<>();
        ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<ParseBookingListResult> getBookingCall=apiInterface.bookingListData("Bearer "+token,shopUuid);
        getBookingCall.enqueue(new Callback<ParseBookingListResult>() {
            @Override
            public void onResponse(Call<ParseBookingListResult> call, Response<ParseBookingListResult> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.body().getCode() == 200) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            BookingSingleItem bookingSingleItem = new BookingSingleItem();
                            bookingSingleItem.setUserUuid(response.body().getData().get(i).getUserUuid());
                            bookingSingleItem.setShopUuid(response.body().getData().get(i).getShopUuid());
                            bookingSingleItem.setBookingUuid(response.body().getData().get(i).getBookingUuid());
                            bookingSingleItem.setBookingLatitude(response.body().getData().get(i).getBookingLatitude());
                            bookingSingleItem.setBookingLongitude(response.body().getData().get(i).getBookingLongitude());
                            bookingSingleItem.setTotalServices(response.body().getData().get(i).getTotalServices());
                            bookingSingleItem.setNoOfPersons(response.body().getData().get(i).getNoOfPersons());
                            bookingSingleItem.setCreatedAt(response.body().getData().get(i).getCreatedAt());
                            bookingSingleItem.setBookingStatus(response.body().getData().get(i).getBookingStatus());
                            bookingSingleItem.setBookingPersons(response.body().getData().get(i).getBookingPersons());
                            bookingSingleItem.setBookingStages(response.body().getData().get(i).getBookingStages());
                            bookingSingleItem.setPriceDetails(response.body().getData().get(i).getPriceDetails());
                            bookingListTemp.add(bookingSingleItem);
                            getBookingListAdapter.notifyDataSetChanged();
                        }
                    }
                    else if(response.body().getCode()==404){
                        Toast.makeText(getContext(), "No Bookings Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ParseBookingListResult> call, Throwable t) {
                Toast.makeText(getContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();
            }
        });
        return bookingListTemp;
    }
}
