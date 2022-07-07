package com.example.doan2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.doan2.DAO.DSBan;
import com.example.doan2.DTO.Ban;
import com.example.doan2.R;


import java.util.ArrayList;
import java.util.List;

public class BanAdapter extends BaseAdapter {
    private DSBan context;
    private int layout;
    private List<Ban> banList;


    public BanAdapter(DSBan context, int layout, List<Ban> banList) {
        this.context = context;
        this.layout = layout;
        this.banList= banList;
    }


    @Override
    public int getCount() {
        return banList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void filterList(ArrayList<Ban> filteredList) {
        banList = filteredList;
        notifyDataSetChanged();
    }

    private class ViewHolder{
        TextView tvtenban;
        ImageView imgban;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BanAdapter.ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.imgban= (ImageView) view.findViewById(R.id.imgban);
            holder.tvtenban= (TextView) view.findViewById(R.id.tendongban);
            view.setTag(holder);

        }else{
            holder= (BanAdapter.ViewHolder) view.getTag();
        }
        Ban b = banList.get(i);
        holder.tvtenban.setText(b.tenban);
        return view;
    }

}