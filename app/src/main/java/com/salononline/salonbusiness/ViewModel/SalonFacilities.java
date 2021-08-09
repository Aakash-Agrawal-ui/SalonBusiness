package com.salononline.salonbusiness.ViewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;


import com.salononline.salonbusiness.Data.FacilitiesAdapter;
import com.salononline.salonbusiness.Data.FacilityItem;
import com.salononline.salonbusiness.R;

import java.util.ArrayList;
import java.util.List;

public class SalonFacilities extends AppCompatActivity {
    List<FacilityItem> facilitiesList;
    ListView shopFacilities;
    private Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_facilities);
        mToolbar=findViewById(R.id.salonFacilitiesToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Salon Facilities");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        facilitiesList=new ArrayList<>();
        facilitiesList=(ArrayList<FacilityItem>) getIntent().getSerializableExtra("list");
        shopFacilities=findViewById(R.id.salonProfileFacilitiesList);
        FacilitiesAdapter adapter=new FacilitiesAdapter(getApplicationContext(), (ArrayList<FacilityItem>) facilitiesList);
        shopFacilities.setAdapter(adapter);
    }
}
