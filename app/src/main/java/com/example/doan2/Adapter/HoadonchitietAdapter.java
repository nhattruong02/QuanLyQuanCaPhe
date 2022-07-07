package com.example.doan2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan2.DAO.DSChitiethoadon;

import com.example.doan2.DTO.Chitiethoadon;
import com.example.doan2.R;

import java.util.List;

public class HoadonchitietAdapter extends BaseAdapter {
    private DSChitiethoadon context;
    private int layout;
    private List<Chitiethoadon> listcthd;


    public HoadonchitietAdapter(DSChitiethoadon context, int layout, List<Chitiethoadon> listcthd) {
        this.context = context;
        this.layout = layout;
        this.listcthd= listcthd;
    }

    @Override
    public int getCount() {
        return listcthd.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView tvtenhh,giahh,soluonghh;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.tvtenhh= (TextView) view.findViewById(R.id.tendcthd);
            holder.giahh = (TextView) view.findViewById(R.id.giadcthd);
            holder.soluonghh = (TextView) view.findViewById(R.id.soluongdcthd);
            view.setTag(holder);

        }else{
            holder= (ViewHolder) view.getTag();
        }
        Chitiethoadon cthd = listcthd.get(i);
        holder.tvtenhh.setText(cthd.getTen());
        String n = String.valueOf(cthd.getGia());
        holder.giahh.setText(n);
        String n2 = String.valueOf(cthd.getSoluong());
        holder.soluonghh.setText(n2);
        return view;
    }

}
