package com.salononline.salonbusiness.ViewModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.salononline.salonbusiness.Parse.GetQueueNameResult;
import com.salononline.salonbusiness.R;
import com.salononline.salonbusiness.Repositry.ApiInterface;
import com.salononline.salonbusiness.Repositry.RetrofitClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaleQueueFragment extends Fragment {
    View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String token,shopUuid,salonType;
    MaleQueueAdapter maleQueueAdapter;

    public MaleQueueFragment(String token,String shopUuid,String salonType) {
        this.token=token;
        this.shopUuid=shopUuid;
        this.salonType=salonType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.male_queue,container,false);
        tabLayout=view.findViewById(R.id.male_queue_tab_layout);
        viewPager=view.findViewById(R.id.maleQueueViewPager);
        assert getFragmentManager() != null;
        maleQueueAdapter=new MaleQueueAdapter(getFragmentManager());
        getAllBarbersList();
        viewPager.setAdapter(maleQueueAdapter);
        tabLayout.setupWithViewPager(viewPager);



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void getAllBarbersList() {
        ApiInterface apiInterface= RetrofitClient.getRetofitInstance().create(ApiInterface.class);
        retrofit2.Call<GetQueueNameResult> getAllBarberResultCall=apiInterface.queueName("Bearer "+token,shopUuid,"MALE");
        getAllBarberResultCall.enqueue(new Callback<GetQueueNameResult>() {
            @Override
            public void onResponse(Call<GetQueueNameResult> call, Response<GetQueueNameResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"Unknown Error Occurred",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.body().getCode() == 200) {
                        for (int i = 0; i < response.body().getData().getMaleServiceQueue().size(); i++) {
                            String barberUuid=response.body().getData().getMaleServiceQueue().get(i).getBarberUuid();
                            maleQueueAdapter.AddFragment(new MaleQueueManagementFragment(token,shopUuid,barberUuid),response.body().getData().getMaleServiceQueue().get(i).getBarberName());
                        }
                        maleQueueAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetQueueNameResult> call, Throwable t) {
                Toast.makeText(getContext(),"Internet Required",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
