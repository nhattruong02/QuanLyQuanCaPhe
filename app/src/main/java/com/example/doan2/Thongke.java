package com.example.doan2;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan2.DAO.DSChitiethoadon;
import com.example.doan2.DAO.DSHoadon;
import com.example.doan2.DAO.DSThucdon;
import com.example.doan2.DTO.Hoadon;
import com.example.doan2.DTO.Thucdon;
import com.example.doan2.Database.DbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Thongke extends AppCompatActivity {
    DbHelper database;
    EditText edttungay,edtdenngay;
    TextView tvtongtien;
    Button btnthongke;
    DSHoadon dsHoadon;
    ArrayList<Hoadon> hd = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongke);
        edttungay = findViewById(R.id.tungay);
        btnthongke = findViewById(R.id.btnthongke);
        tvtongtien =findViewById(R.id.tvtongtien);
        database = new DbHelper(this,"qlqcaphe.sqlite",null,1);
        edttungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tungay();
            }
        });
        btnthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ngay = edttungay.getText().toString();
                tvtongtien.setText(String.valueOf(thongke(ngay) +"Đ"));
            }
        });
    }
    public void tungay(){
        Calendar calendar =Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edttungay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
    public double thongke(String ngay){
        dsHoadon = new DSHoadon();
        dsHoadon.DSHoadon(Thongke.this);
        hd = dsHoadon.getAll();
        double tongtien=0;
        for (int i = 0; i < hd.size(); i++) {
            if (hd.get(i).getNgayxuat().equals(ngay)) {
                double tongtienhd =hd.get(i).getTongtien();
                tongtien = tongtien + tongtienhd;
            }
        }
        return tongtien;
    }
}
