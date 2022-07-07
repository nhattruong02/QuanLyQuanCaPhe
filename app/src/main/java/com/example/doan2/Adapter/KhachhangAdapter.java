package com.example.doan2.Adapter;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.doan2.DAO.DSKhachhang;
import com.example.doan2.DTO.Khachhang;
import com.example.doan2.R;

import java.util.ArrayList;
import java.util.List;

public class KhachhangAdapter extends BaseAdapter {
    private DSKhachhang context;
    private int layout;
    private List<Khachhang> khachhangList;


    public KhachhangAdapter(DSKhachhang context, int layout, List<Khachhang> khachhangList) {
        this.context = context;
        this.layout = layout;
        this.khachhangList= khachhangList;
    }

    @Override
    public int getCount() {
        return khachhangList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void filterList(ArrayList<Khachhang> filteredList) {
        khachhangList = filteredList;
        notifyDataSetChanged();
    }

    private class ViewHolder{
        TextView tvhoten,tvgt,tvns,tvsdt;
        ImageView imgkhachhang;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.imgkhachhang= (ImageView) view.findViewById(R.id.imgkhachhang);
            holder.tvhoten= (TextView) view.findViewById(R.id.hotendkh);
            holder.tvgt = (TextView) view.findViewById(R.id.gtdkh);
            holder.tvns = (TextView) view.findViewById(R.id.nsdkh);
            holder.tvsdt = (TextView) view.findViewById(R.id.sdtdkh);
            view.setTag(holder);

        }else{
            holder= (ViewHolder) view.getTag();
        }
        Khachhang kh = khachhangList.get(i);
        holder.tvhoten.setText(kh.getHoten());
        holder.tvgt.setText(kh.getGt());
        holder.tvns.setText(kh.getNs());
        holder.tvsdt.setText(kh.getSdt());

        return view;
    }

}
