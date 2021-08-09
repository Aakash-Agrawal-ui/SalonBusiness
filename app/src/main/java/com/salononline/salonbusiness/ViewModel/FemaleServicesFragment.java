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


import com.salononline.salonbusiness.Data.GetServicesAdapter;
import com.salononline.salonbusiness.Data.ServiceItem;
import com.salononline.salonbusiness.Parse.ParseGetAllServiceResult;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FemaleServicesFragment extends Fragment {
    View view;
    private RecyclerView femaleServicesRecyclerView;
    private String token,shopUuid,salonType;
    private List<ServiceItem> serviceItems;
    GetServicesAdapter getServicesAdapter;

    public FemaleServicesFragment(String token,String shopUuid,String salonType) {
        this.token=token;
        this.shopUuid=shopUuid;
        this.salonType=salonType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.female_services_layout,container,false);

        femaleServicesRecyclerView=view.findViewById(R.id.femaleServicesRecyclerView);

        getServicesAdapter=new GetServicesAdapter(getContext(),serviceItems,token,shopUuid,salonType);
        femaleServicesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        femaleServicesRecyclerView.setAdapter(getServicesAdapter);
        getServicesAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceItems=new ArrayList<>();
        serviceItems=getAllServicesList();

    }

    private List<ServiceItem> getAllServicesList() {
        final List<ServiceItem> serviceItemsTemp=new ArrayList<>();
        ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<ParseGetAllServiceResult> getAllServiceResultCall=apiInterface.getAllService("Bearer "+token,shopUuid,"FEMALE");
        getAllServiceResultCall.enqueue(new Callback<ParseGetAllServiceResult>() {
            @Override
            public void onResponse(Call<ParseGetAllServiceResult> call, Response<ParseGetAllServiceResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            ServiceItem serviceItem = new ServiceItem();
                            serviceItem.setServiceName(response.body().getData().get(i).getServiceName());
                            serviceItem.setServiceUuid(response.body().getData().get(i).getServiceUuid());
                            serviceItem.setShopUuid(response.body().getData().get(i).getShopUuid());
                            serviceItem.setServiceType(response.body().getData().get(i).getServiceType());
                            serviceItem.setPrice(response.body().getData().get(i).getPrice());
                            serviceItem.setDiscountedPrice(response.body().getData().get(i).getDiscountedPrice());
                            serviceItem.setDiscount(response.body().getData().get(i).getDiscount());
                            serviceItem.setMinServiceTime(response.body().getData().get(i).getMinServiceTime());
                            serviceItem.setServiceState(response.body().getData().get(i).getServiceState());
                            serviceItem.setShortDescription(response.body().getData().get(i).getShortDescription());
                            serviceItemsTemp.add(serviceItem);
                            getServicesAdapter.notifyDataSetChanged();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ParseGetAllServiceResult> call, Throwable t) {
                Toast.makeText(getContext(),"Internet Connection Required",Toast.LENGTH_SHORT).show();

            }
        });
        return serviceItemsTemp;
    }
}
