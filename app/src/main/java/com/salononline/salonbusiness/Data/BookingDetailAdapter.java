package com.salononline.salonbusiness.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.salononline.salonbusiness.Parse.BookingPersons;
import com.salononline.salonbusiness.Parse.BookingServices;
import com.salononline.salonbusiness.R;

import java.util.ArrayList;
import java.util.List;

public class BookingDetailAdapter extends RecyclerView.Adapter<BookingDetailAdapter.ViewHolder> {
    private Context context;
    private List<BookingPersons> bookingPersonsItemList;
    private BookingDetailServiceAdapter bookingDetailServiceAdapter;
    public BookingDetailAdapter(Context context, List<BookingPersons> bookingPersonsItemList) {
        this.context=context;
        this.bookingPersonsItemList=bookingPersonsItemList;

    }

    @NonNull
    @Override
    public BookingDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_detail,parent,false);
        return new BookingDetailAdapter.ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingDetailAdapter.ViewHolder holder, int position) {
        BookingPersons bookingPersons=bookingPersonsItemList.get(position);
        holder.bookingCustomerName.setText(bookingPersons.getUserName());
        if(bookingPersons.getGender().equals("MALE")) {
            holder.bookingCustomerGender.setText(" (M)");
        }
        else{
            holder.bookingCustomerGender.setText(" (F)");
        }
        holder.bookingCustomerQueue.setText(bookingPersons.getQueueNumber());
        holder.bookingCustomerServingTime.setText(bookingPersons.getServingTime());
        holder.bookingCustomerExpert.setText(bookingPersons.getBarberName());
        List<BookingServices> bookingServicesList=new ArrayList<>();
        bookingServicesList=bookingPersons.getBookingServices();
        bookingDetailServiceAdapter=new BookingDetailServiceAdapter(context,bookingServicesList);
        holder.bookingCustomerServiceView.setLayoutManager(new LinearLayoutManager(context));
        holder.bookingCustomerServiceView.setAdapter(bookingDetailServiceAdapter);
        bookingDetailServiceAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return Math.max(bookingPersonsItemList.size(),0);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView bookingCustomerName,bookingCustomerGender,bookingCustomerQueue,bookingCustomerExpert,bookingCustomerServingTime;
        RecyclerView bookingCustomerServiceView;



        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;
            bookingCustomerName=itemView.findViewById(R.id.bookingDetailsCustomerName);
            bookingCustomerGender=itemView.findViewById(R.id.bookingDetailsCustomerGender);
            bookingCustomerQueue=itemView.findViewById(R.id.bookingDetailsCustomerQueue);
            bookingCustomerExpert=itemView.findViewById(R.id.bookingDetailsServingExpertName);
            bookingCustomerServingTime=itemView.findViewById(R.id.bookingDetailsServingTimeValue);
            bookingCustomerServiceView=itemView.findViewById(R.id.bookingDetailsServicesRecyclerView);

        }
    }
}
