package com.salononline.salonbusiness.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.salononline.salonbusiness.Parse.BookingServices;
import com.salononline.salonbusiness.R;

import java.util.List;

public class BookingDetailServiceAdapter extends RecyclerView.Adapter<BookingDetailServiceAdapter.ViewHolder> {
    private Context context;
    private List<BookingServices> bookingServicesList;
    public BookingDetailServiceAdapter(Context context, List<BookingServices> bookingServicesList) {
        this.context=context;
        this.bookingServicesList=bookingServicesList;

    }
    @NonNull
    @Override
    public BookingDetailServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_customer_service_layout,parent,false);
        return new BookingDetailServiceAdapter.ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingDetailServiceAdapter.ViewHolder holder, int position) {
        BookingServices bookingServices=bookingServicesList.get(position);
        holder.bookingDetailsServiceName.setText(bookingServices.getServiceName());
        holder.bookingDetailsServicePrice.setText("₹"+bookingServices.getPrice());

    }

    @Override
    public int getItemCount() {
        return Math.max(bookingServicesList.size(),0);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView bookingDetailsServiceName,bookingDetailsServicePrice;

        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;
            bookingDetailsServiceName=itemView.findViewById(R.id.bookingDetailsCustomerServiceName);
            bookingDetailsServicePrice=itemView.findViewById(R.id.bookingDetailsCustomerServicePrice);
        }
    }
}
