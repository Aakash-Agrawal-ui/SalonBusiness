package com.salononline.salonbusiness.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.salononline.salonbusiness.Parse.ParseUpdateImage;
import com.salononline.salonbusiness.Parse.ShopServiceStateData;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;
import com.salononline.salonbusiness.ViewModel.UpdateService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetServicesAdapter extends RecyclerView.Adapter<GetServicesAdapter.ViewHolder> {
    private Context context;
    private String salonType;
    private List<ServiceItem> serviceItemList;
    private LayoutInflater inflater;
    private String token,shopUuid;

    public GetServicesAdapter(Context context, List<ServiceItem> serviceItemList,String token,String shopUuid,String salonType) {
        this.context = context;
        this.serviceItemList=serviceItemList;
        this.token=token;
        this.shopUuid=shopUuid;
        this.salonType=salonType;
    }
    @NonNull
    @Override
    public GetServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.service_layout,parent,false);
        return new ViewHolder(view,context,token,shopUuid,salonType);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GetServicesAdapter.ViewHolder holder, int position) {
        ServiceItem serviceItem=serviceItemList.get(position);
        holder.serviceUuid=serviceItem.getServiceUuid();
        holder.serviceNameDet.setText(serviceItem.getServiceName().substring(0, 1).toUpperCase() + serviceItem.getServiceName().substring(1));
        holder.servicePriceDet.setText("₹"+String.valueOf(serviceItem.getPrice()));
        if(serviceItem.getDiscount()!=null) {
            holder.serviceDiscountDet.setText(String.valueOf(serviceItem.getDiscount()) + "% off");
        }
        else{
            holder.serviceDiscountDet.setText("No Discount");
        }
        holder.serviceStatus.setChecked(serviceItem.getServiceState().equals("ACTIVE"));
        String[] arr=serviceItem.getMinServiceTime().split(":",3);
        if(!arr[0].equals("00")) {
            holder.serviceDuration.setText(arr[0] + "hr " + arr[1] + "min");
        }
        else{
            holder.serviceDuration.setText(arr[1] + "min");
        }
        holder.shortDescription.setText(serviceItem.getShortDescription());


    }

    @Override
    public int getItemCount() {
        if(serviceItemList.size()!=0)
            return serviceItemList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView serviceNameDet,servicePriceDet,serviceDiscountDet,shortDescription,serviceDuration;
        Button updateService;
        Switch serviceStatus;
        String serviceUuid;

        public ViewHolder(@NonNull View itemView,Context ctx,String tkn,String sUid,String type) {
            super(itemView);
            context=ctx;
            token=tkn;
            shopUuid=sUid;
            salonType=type;

            serviceNameDet=itemView.findViewById(R.id.serviceNameDet);
            servicePriceDet=itemView.findViewById(R.id.servicePriceDet);
            serviceDiscountDet=itemView.findViewById(R.id.serviceDiscountDet);
            updateService=itemView.findViewById(R.id.updateService);
            serviceStatus=itemView.findViewById(R.id.serviceStatus);
            serviceDuration=itemView.findViewById(R.id.serviceDuration);
            shortDescription=itemView.findViewById(R.id.shortDescription);

            updateService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent updateIntent=new Intent(context, UpdateService.class);
                    updateIntent.putExtra("serviceUuid",serviceUuid);
                    updateIntent.putExtra("token",token);
                    updateIntent.putExtra("salon_type",salonType);
                    context.startActivity(updateIntent);
                }
            });



            serviceStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String shopStatus;
                    if(serviceStatus.isChecked()){
                        shopStatus="ACTIVE";
                    }
                    else{
                        shopStatus="DEACTIVE";
                    }
                    ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    ShopServiceStateData shopServiceStateData=new ShopServiceStateData(shopStatus);
                    retrofit2.Call<ParseUpdateImage> updateServiceStateCall=apiInterface.updateSalonServiceState("Bearer "+token,serviceUuid,shopServiceStateData);
                    updateServiceStateCall.enqueue(new Callback<ParseUpdateImage>() {
                        @Override
                        public void onResponse(Call<ParseUpdateImage> call, Response<ParseUpdateImage> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(context,"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (response.body().getCode() == 200) {
                                    LayoutInflater inflater=LayoutInflater.from(context);
                                    View layout=inflater.inflate(R.layout.customtoastlayout,null);
                                    TextView textView=(TextView) layout.findViewById(R.id.textToast);
                                    textView.setText("Service status updated successfully");
                                    Toast toast=new Toast(context);
                                    toast.setGravity(Gravity.BOTTOM,0,135);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();
                                } else {
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ParseUpdateImage> call, Throwable t) {
                            Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}
