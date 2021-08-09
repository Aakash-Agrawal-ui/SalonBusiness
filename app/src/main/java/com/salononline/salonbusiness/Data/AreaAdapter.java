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

public class AreaAdapter extends ArrayAdapter<AreaItem> {
    public AreaAdapter(@NonNull Context context, ArrayList<AreaItem> areaItems) {
        super(context,0,areaItems);
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
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.area_spinner,parent,false);
        }
        TextView area_name=convertView.findViewById(R.id.area);
        TextView area_code=convertView.findViewById(R.id.areaCode);

        AreaItem currentItem=getItem(position);
        if(currentItem!=null) {
            area_name.setText(currentItem.getArea_name());
            area_code.setText(currentItem.getArea_code());
        }

        return convertView;

    }
}
