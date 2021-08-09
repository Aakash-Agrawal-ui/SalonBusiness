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

public class CityAdapter extends ArrayAdapter<CityItem> {
    public CityAdapter(@NonNull Context context, ArrayList<CityItem> cityItems) {
        super(context,0,cityItems);

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
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.city_spinner,parent,false);
        }
        TextView city_name=convertView.findViewById(R.id.city);
        TextView city_code=convertView.findViewById(R.id.cityCode);

        CityItem currentItem=getItem(position);
        if(currentItem!=null) {
            city_name.setText(currentItem.getCity_name());
            city_code.setText(currentItem.getCity_code());
        }

        return convertView;

    }
}


