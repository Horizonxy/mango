package com.mango.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mango.ui.widget.UploadPictureView;

import java.util.List;

public class UploadPictureAdapter extends BaseAdapter {

    List data;

    public UploadPictureAdapter(List data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 1 : data.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = new UploadPictureView(parent.getContext());

        }
        return convertView;
    }
}
