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

public class CategoryAdapter extends ArrayAdapter<CategoryItem> {
    public CategoryAdapter(@NonNull Context context, ArrayList<CategoryItem> categoryItems) {
        super(context, 0,categoryItems);
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
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.category_spinner,parent,false);
        }
        TextView category_name=convertView.findViewById(R.id.category);
        TextView category_uid=convertView.findViewById(R.id.categoryUid);

        CategoryItem currentItem=getItem(position);
        if(currentItem!=null) {
            category_name.setText(currentItem.getCategory_name());
            category_uid.setText(currentItem.getCategory_uid());
        }

        return convertView;

    }
}
