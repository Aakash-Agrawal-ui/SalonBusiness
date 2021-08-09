package com.salononline.salonbusiness.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.salononline.salonbusiness.R;

import java.util.ArrayList;

public class FacilitiesAdapter extends ArrayAdapter<FacilityItem> {
    public FacilitiesAdapter(@NonNull Context context, ArrayList<FacilityItem> facilitiesListDetails) {
        super(context, 0,facilitiesListDetails);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    private View initView(int position,View convertView,ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.facility_layout,parent,false);
        }
        TextView facility_name=convertView.findViewById(R.id.facilities_text);


        FacilityItem currentItem=getItem(position);
        if(currentItem!=null) {
            facility_name.setText(currentItem.getFacility_name());
        }

        return convertView;

    }
}
