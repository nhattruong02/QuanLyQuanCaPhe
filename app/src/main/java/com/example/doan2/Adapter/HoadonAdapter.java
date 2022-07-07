package com.example.doan2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan2.DAO.DSBan;
import com.example.doan2.DAO.DSHoadon;
import com.example.doan2.DTO.Ban;
import com.example.doan2.DTO.Hoadon;
import com.example.doan2.R;

import java.util.ArrayList;
import java.util.List;

public class HoadonAdapter extends BaseAdapter{
        private DSHoadon context;
        private int layout;
        private List<Hoadon> hoadonList;

        public HoadonAdapter(DSHoadon context, int layout, List<Hoadon> hoadonList) {
            this.context = context;
            this.layout = layout;
            this.hoadonList= hoadonList;
        }


        @Override
        public int getCount() {
            return hoadonList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

    public void filterList(ArrayList<Hoadon> filteredList) {
            hoadonList = filteredList;
            notifyDataSetChanged();
    }

    private class ViewHolder{
            TextView tvmahd,tvhoten,tvngayxuat,tvtenban,tvtinhtrang,tvtongtien;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HoadonAdapter.ViewHolder holder;
            if(view==null){
                holder = new HoadonAdapter.ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(layout,null);
                holder.tvmahd= (TextView) view.findViewById(R.id.mahddhd);
                holder.tvhoten= (TextView) view.findViewById(R.id.tennvdhd);
                holder.tvngayxuat= (TextView) view.findViewById(R.id.ngayxuatdhd);
                holder.tvtenban= (TextView) view.findViewById(R.id.tenbandhd);
                holder.tvtinhtrang= (TextView) view.findViewById(R.id.tinhtrangdhd);
                holder.tvtongtien= (TextView) view.findViewById(R.id.tongtiendhd);
                view.setTag(holder);

            }else{
                holder= (HoadonAdapter.ViewHolder) view.getTag();
            }
            Hoadon hd = hoadonList.get(i);
            String n1 = String.valueOf(hd.getMahd());
            holder.tvmahd.setText(n1);
            holder.tvhoten.setText(hd.getTennv());
            holder.tvngayxuat.setText(hd.getNgayxuat());
            holder.tvtenban.setText(hd.getTenban());
            holder.tvtinhtrang.setText(hd.getTinhtrang());
            String n = String.valueOf(hd.getTongtien());
            holder.tvtongtien.setText(n);
            return view;
        }

    }
