package com.example.doan2.Adapter;


import android.content.Context;
import android.telecom.PhoneAccount;
import android.view.ContextMenu;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.doan2.DAO.DSNhanvien;
import com.example.doan2.DTO.Nhanvien;
import com.example.doan2.R;

import java.util.ArrayList;
import java.util.List;

public class NhanvienAdapter extends BaseAdapter {
    private DSNhanvien context;
    private int layout;
    private List<Nhanvien> nhanvienList;


    public NhanvienAdapter(DSNhanvien context, int layout,List<Nhanvien> nhanvienList) {
        this.context = context;
        this.layout = layout;
        this.nhanvienList = nhanvienList;
    }

    @Override
    public int getCount() {
        return nhanvienList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void filterList(ArrayList<Nhanvien> filterlist) {
        nhanvienList = filterlist;
        notifyDataSetChanged();
    }

    private class ViewHolder{
        TextView tvhoten,tvgt,tvns,tvqq,tvcccd,tvsdt;
        ImageView imgnhanvien;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.imgnhanvien= (ImageView) view.findViewById(R.id.imgnhanvien);
            holder.tvhoten= (TextView) view.findViewById(R.id.hotendnv);
            holder.tvgt = (TextView) view.findViewById(R.id.gtdnv);
            holder.tvns = (TextView) view.findViewById(R.id.nsdnv);
            holder.tvqq = (TextView) view.findViewById(R.id.qqdnv);
            holder.tvcccd = (TextView) view.findViewById(R.id.cccddnv);
            holder.tvsdt = (TextView) view.findViewById(R.id.sdtdnv);
            view.setTag(holder);

        }else{
            holder= (ViewHolder) view.getTag();
        }
        Nhanvien nv = nhanvienList.get(i);
        holder.tvhoten.setText(nv.getHoten());
        holder.tvgt.setText(nv.getGioitinh());
        holder.tvns.setText(nv.getNamsinh());
        holder.tvqq.setText(nv.getQuequan());
        holder.tvcccd.setText(nv.getCccd());
        holder.tvsdt.setText(nv.getSdt());
        return view;
    }

}

