package com.example.doan2.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import com.example.doan2.DAO.DSChitiethoadon;
import com.example.doan2.DAO.DSNhanvien;
import com.example.doan2.DAO.DSThucdon;
import com.example.doan2.DTO.Nhanvien;
import com.example.doan2.DTO.Thucdon;
import com.example.doan2.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ThucdonAdapter extends BaseAdapter {
    private DSThucdon context;
    private int layout;
    private List<Thucdon> thucdonList;


    public ThucdonAdapter(DSThucdon context, int layout, List<Thucdon> thucdonList) {
        this.context = context;
        this.layout = layout;
        this.thucdonList = thucdonList;
    }


    @Override
    public int getCount() {
        return thucdonList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void filterList(ArrayList<Thucdon> filteredList) {
        thucdonList = filteredList;
        notifyDataSetChanged();
    }

    private class ViewHolder{
        TextView tvten,tvloai,tvtt,tvgia;
        ImageView imgmonan;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.imgmonan= (ImageView) view.findViewById(R.id.imgthucdon);
            holder.tvten= (TextView) view.findViewById(R.id.tendma);
            holder.tvloai = (TextView) view.findViewById(R.id.loaidma);
            holder.tvtt = (TextView) view.findViewById(R.id.tinhtrangdma);
            holder.tvgia = (TextView) view.findViewById(R.id.giadma);
            view.setTag(holder);

        }else{
            holder= (ViewHolder) view.getTag();
        }
        Thucdon td = thucdonList.get(i);
        holder.tvten.setText(td.getTenhh());
        holder.tvloai.setText(td.getLoaihh());
        holder.tvtt.setText(td.getTinhtrang());
        String n = String.valueOf(td.getGia());
        holder.tvgia.setText(n);
        byte[] hinh = td.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray( hinh,0,hinh.length);
        holder.imgmonan.setImageBitmap(bitmap);
        return view;
    }

}


