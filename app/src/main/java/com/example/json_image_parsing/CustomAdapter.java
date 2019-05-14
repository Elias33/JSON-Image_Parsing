package com.example.json_image_parsing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context applicationContext;
    int sample;
    List<Model> s;
    LayoutInflater inflater;


    public CustomAdapter(Context applicationContext, int sample, List<Model> s) {

            this.applicationContext= applicationContext;
            this.sample= sample;
            this.s=s;

    }

    @Override
    public int getCount() {
        return s.size();
    }

    @Override
    public Object getItem(int position) {
        return s.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            inflater= (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=  inflater.inflate(R.layout.sample,parent,false);




        }

        TextView t1;
        ImageView img;
        t1= convertView.findViewById(R.id.t1);
        img= convertView.findViewById(R.id.img);
        t1.setText(s.get(position).getName());
        ImageLoader.getInstance().displayImage(s.get(position).getImage(),img);



        return convertView;
    }
}
