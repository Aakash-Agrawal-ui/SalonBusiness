package com.salononline.salonbusiness.Data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.salononline.salonbusiness.Parse.BookingPersons;
import com.salononline.salonbusiness.Parse.BookingSingleItem;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Validations.ValidateUser;
import com.salononline.salonbusiness.ViewModel.BookingDetails;

import java.util.ArrayList;
import java.util.List;

public class GetBookingListAdapter extends RecyclerView.Adapter<GetBookingListAdapter.ViewHolder> {
    private Context context;
    private List<BookingSingleItem> bookingSingleItemList;
    private String token,shopUuid;

    public GetBookingListAdapter(Context context, List<BookingSingleItem> bookingSingleItemList, String token, String shopUuid) {
        this.context = context;
        this.bookingSingleItemList = bookingSingleItemList;
        this.token = token;
        this.shopUuid = shopUuid;
    }

    @NonNull
    @Override
    public GetBookingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_layout,parent,false);
        return new GetBookingListAdapter.ViewHolder(view,context,token,shopUuid);

    }

    @Override
    public void onBindViewHolder(@NonNull GetBookingListAdapter.ViewHolder holder, int position) {
        BookingSingleItem bookingSingleItem=bookingSingleItemList.get(position);
        holder.bookingId.setText(bookingSingleItem.getBookingUuid());
        holder.servicesTotal.setText(String.valueOf(bookingSingleItem.getTotalServices()));
        holder.noOfPersons.setText(String.valueOf(bookingSingleItem.getNoOfPersons()));
        String[] arr=bookingSingleItem.getCreatedAt().split("T",2);
        holder.time.setText(ValidateUser.convertTextTime(arr[1].substring(0,8)));
        holder.date.setText(ValidateUser.convertTextDate(arr[0]));

    }

    @Override
    public int getItemCount() {
        return Math.max(bookingSingleItemList.size(), 0);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookingId,servicesTotal,noOfPersons,date,time;
        public ViewHolder(@NonNull View itemView,Context ctx,String tkn,String sUid) {
            super(itemView);
            context=ctx;
            token=tkn;
            shopUuid=sUid;

            bookingId=itemView.findViewById(R.id.bookingIdText);
            servicesTotal=itemView.findViewById(R.id.totalServicesText);
            noOfPersons=itemView.findViewById(R.id.noOfPersons);
            date=itemView.findViewById(R.id.bookingDate);
            time=itemView.findViewById(R.id.bookingTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, BookingDetails.class);
                    int position=getAdapterPosition();
                    intent.putExtra("bookingList", (ArrayList<BookingPersons>) bookingSingleItemList.get(position).getBookingPersons());
                    intent.putExtra("token",token);
                    intent.putExtra("shopUuid",shopUuid);
                    intent.putExtra("id",bookingSingleItemList.get(position).getBookingUuid());
                    intent.putExtra("from",1);
                    intent.putExtra("created",bookingSingleItemList.get(position).getCreatedAt());
                    intent.putExtra("hygiene",bookingSingleItemList.get(position).getPriceDetails().getHygieneCharges());
                    intent.putExtra("offer",bookingSingleItemList.get(position).getPriceDetails().getTotalTax());
                    intent.putExtra("amount",bookingSingleItemList.get(position).getPriceDetails().getTotalCost());
                    context.startActivity(intent);

                }
            });
        }

    }
}
