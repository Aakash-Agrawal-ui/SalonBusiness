package com.salononline.salonbusiness.Data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.salononline.salonbusiness.Parse.BookingQueuePersons;
import com.salononline.salonbusiness.Parse.BookingServices;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Validations.ValidateUser;
import com.salononline.salonbusiness.ViewModel.BookingQueueDetails;

import java.util.ArrayList;
import java.util.List;

public class QueueBarberManageAdapter extends RecyclerView.Adapter<QueueBarberManageAdapter.ViewHolder> {
    private Context context;
    private List<BookingQueuePersons> bookingQueuePersonsList;
    private String token,shopUuid,barberUuid;

    public QueueBarberManageAdapter(Context context,List<BookingQueuePersons> bookingQueuePersonsList,String token,String shopUuid,String barberUuid) {
        this.context = context;
        this.bookingQueuePersonsList=bookingQueuePersonsList;
        this.token=token;
        this.shopUuid=shopUuid;
        this.barberUuid=barberUuid;
    }

    @NonNull
    @Override
    public QueueBarberManageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_queue_layout,parent,false);
        return new QueueBarberManageAdapter.ViewHolder(view,context,token,shopUuid,barberUuid);
    }

    @Override
    public void onBindViewHolder(@NonNull QueueBarberManageAdapter.ViewHolder holder, int position) {
        BookingQueuePersons bookingQueuePersons=bookingQueuePersonsList.get(position);
        holder.queueBookingUserName.setText(bookingQueuePersons.getUserName());
        holder.queueBookingUserGender.setText(bookingQueuePersons.getGender());
        holder.queueBookingTotalServices.setText(String.valueOf(bookingQueuePersons.getNoOfServices()));
        holder.queueBookingServingTime.setText(bookingQueuePersons.getServingTime());
        String[] arr=bookingQueuePersons.getBooking().getCreatedAt().split("T",2);
        holder.queueBookingTime.setText(ValidateUser.convertTextTime(arr[1].substring(0,8)));
        holder.queueBookingDate.setText(ValidateUser.convertTextDate(arr[0]));



    }

    @Override
    public int getItemCount() {
        return Math.max(bookingQueuePersonsList.size(),0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView queueBookingUserName,queueBookingUserGender,queueBookingTotalServices,queueBookingServingTime,queueBookingDate,queueBookingTime;

        public ViewHolder(@NonNull View itemView,Context ctx,String tkn,String sUid,String bUid) {
            super(itemView);
            context=ctx;
            token=tkn;
            shopUuid=sUid;
            barberUuid=bUid;
            queueBookingDate=itemView.findViewById(R.id.queueBookingDate);
            queueBookingServingTime=itemView.findViewById(R.id.queueServingTime);
            queueBookingTime=itemView.findViewById(R.id.queueBookingTime);
            queueBookingTotalServices=itemView.findViewById(R.id.totalQueueServicesText);
            queueBookingUserGender=itemView.findViewById(R.id.bookingQueueUserGender);
            queueBookingUserName=itemView.findViewById(R.id.bookingQueueUserName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, BookingQueueDetails.class);
                    int position=getAdapterPosition();
                    intent.putExtra("bookingServicesList", (ArrayList<BookingServices>) bookingQueuePersonsList.get(position).getBookingServices());
                    intent.putExtra("token",token);
                    intent.putExtra("shopUuid",shopUuid);
                    intent.putExtra("id",bookingQueuePersonsList.get(position).getBooking().getBookingUuid());
                    intent.putExtra("pid",bookingQueuePersonsList.get(position).getPersonUuid());
                    intent.putExtra("created",bookingQueuePersonsList.get(position).getBooking().getCreatedAt());
                    intent.putExtra("queueNum",bookingQueuePersonsList.get(position).getQueueNumber());
                    intent.putExtra("serve",bookingQueuePersonsList.get(position).getServingTime());
                    intent.putExtra("mob",bookingQueuePersonsList.get(position).getUserMobile());
                    intent.putExtra("name",bookingQueuePersonsList.get(position).getUserName());
                    intent.putExtra("gender",bookingQueuePersonsList.get(position).getGender());
                    intent.putExtra("status",bookingQueuePersonsList.get(position).getQueueStatus());

                    context.startActivity(intent);
                }
            });
        }
    }
}
