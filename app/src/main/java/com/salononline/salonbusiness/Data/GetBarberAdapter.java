package com.salononline.salonbusiness.Data;

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
import com.salononline.salonbusiness.ViewModel.UpdateBarber;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBarberAdapter extends RecyclerView.Adapter<GetBarberAdapter.ViewHolder> {
    private Context context;
    private String salonType;
    private List<BarberItem> barberItemList;
    private LayoutInflater inflater;
    private String token,shopUuid;

    public GetBarberAdapter(Context context, List<BarberItem> barberItemList, String token, String shopUuid, String salonType) {
        this.context = context;
        this.barberItemList = barberItemList;
        this.token = token;
        this.shopUuid = shopUuid;
        this.salonType = salonType;
    }

    @NonNull
    @Override
    public GetBarberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.barber_layout,parent,false);
        return new GetBarberAdapter.ViewHolder(view,context,token,shopUuid,salonType);
    }

    @Override
    public void onBindViewHolder(@NonNull GetBarberAdapter.ViewHolder holder, int position) {
        BarberItem barberItem=barberItemList.get(position);
        holder.barberUuid=barberItem.getBarberUuid();
        holder.barberNameDet.setText(barberItem.getBarberName());
        holder.barberGenderDet.setText(barberItem.getGender());
        holder.barberStatus.setChecked(barberItem.getBarberState().equals("ACTIVE"));

    }

    @Override
    public int getItemCount() {
        return barberItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView barberNameDet,barberGenderDet;
        Button updateBarber;
        Switch barberStatus;
        String barberUuid;

        public ViewHolder(@NonNull View itemView, Context ctx, String tkn, String sUid, String type) {
            super(itemView);
            context=ctx;
            token=tkn;
            shopUuid=sUid;
            salonType=type;
            barberNameDet=itemView.findViewById(R.id.barberNameDet);
            barberGenderDet=itemView.findViewById(R.id.barberGenderDet);
            updateBarber=itemView.findViewById(R.id.updateServiceBarberButton);
            barberStatus=itemView.findViewById(R.id.serviceBarberStatus);

            updateBarber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent updateIntent=new Intent(context, UpdateBarber.class);
                    updateIntent.putExtra("barberUuid",barberUuid);
                    updateIntent.putExtra("token",token);
                    updateIntent.putExtra("salon_type",salonType);
                    context.startActivity(updateIntent);

                }
            });

            barberStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String barber_status;
                    if(barberStatus.isChecked()){
                        barber_status="ACTIVE";
                    }
                    else{
                        barber_status="DEACTIVE";
                    }
                    ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
                    ShopServiceStateData shopServiceStateData=new ShopServiceStateData(barber_status);
                    retrofit2.Call<ParseUpdateImage> updateBarberServiceCall=apiInterface.updateSalonBarberState("Bearer "+token,barberUuid,shopServiceStateData);
                    updateBarberServiceCall.enqueue(new Callback<ParseUpdateImage>() {
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
                                    textView.setText("Expert status updated successfully");
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
