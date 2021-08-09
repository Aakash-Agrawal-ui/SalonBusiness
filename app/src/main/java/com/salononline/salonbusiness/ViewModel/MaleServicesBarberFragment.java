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


import com.salononline.salonbusiness.Data.BarberItem;
import com.salononline.salonbusiness.Data.GetBarberAdapter;
import com.salononline.salonbusiness.Parse.ParseGetAllBarberResult;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaleServicesBarberFragment extends Fragment {
    View view;
    private RecyclerView maleServicesBarberRecyclerView;
    private List<BarberItem> barberItems;
    private String token,shopUuid,salonType;
    GetBarberAdapter getBarberAdapter;

    public MaleServicesBarberFragment(String token,String shopUuid,String salonType) {
        this.token=token;
        this.shopUuid=shopUuid;
        this.salonType=salonType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.male_services_barber_layout,container,false);
        maleServicesBarberRecyclerView=view.findViewById(R.id.maleServicesBarberRecyclerView);
        getBarberAdapter=new GetBarberAdapter(getContext(),barberItems,token,shopUuid,salonType);
        maleServicesBarberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        maleServicesBarberRecyclerView.setAdapter(getBarberAdapter);
        getBarberAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barberItems=new ArrayList<>();
        barberItems=getAllBarbersList();

    }
    private List<BarberItem> getAllBarbersList() {
        final List<BarberItem> barberItemsTemp=new ArrayList<>();
        ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<ParseGetAllBarberResult> getAllBarberResultCall=apiInterface.getAllBarbers("Bearer "+token,shopUuid,"MALE");
        getAllBarberResultCall.enqueue(new Callback<ParseGetAllBarberResult>() {
            @Override
            public void onResponse(Call<ParseGetAllBarberResult> call, Response<ParseGetAllBarberResult> response) {
                    if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            BarberItem barberItem = new BarberItem();
                            barberItem.setBarberName(response.body().getData().get(i).getBarberName());
                            barberItem.setBarberUuid(response.body().getData().get(i).getBarberUuid());
                            barberItem.setGender(response.body().getData().get(i).getGender());
                            barberItem.setBarberState(response.body().getData().get(i).getBarberState());
                            barberItem.setShopUuid(response.body().getData().get(i).getShopUuid());
                            barberItemsTemp.add(barberItem);
                            getBarberAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ParseGetAllBarberResult> call, Throwable t) {
                Toast.makeText(getContext(),"Internet Required",Toast.LENGTH_SHORT).show();
            }
        });
        return barberItemsTemp;
    }
}
