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

public class StateAdapter extends ArrayAdapter<StateItem> {
    public StateAdapter(@NonNull Context context, ArrayList<StateItem> stateItems) {
        super(context,0,stateItems);

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
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.state_spinner,parent,false);
        }
        TextView state_name=convertView.findViewById(R.id.state);
        TextView state_code=convertView.findViewById(R.id.stateCode);

        StateItem currentItem=getItem(position);
        if(currentItem!=null) {
            state_name.setText(currentItem.getState_name());
            state_code.setText(currentItem.getState_code());
        }

        return convertView;

    }
}


