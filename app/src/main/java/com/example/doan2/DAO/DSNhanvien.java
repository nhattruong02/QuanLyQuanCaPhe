package com.example.doan2.DAO;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan2.Adapter.NhanvienAdapter;
import com.example.doan2.DTO.Nhanvien;

import com.example.doan2.DTO.Thucdon;
import com.example.doan2.Database.DbHelper;
import com.example.doan2.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DSNhanvien extends AppCompatActivity {
    DbHelper database;
    Button btnthem, btnhuy,btnsua,btnhuysua;
    EditText edthoten, edtgt, edtns, edtqq, edtcccd, edtsdt,timkiemnv;
    ListView lvnhanvien;
    NhanvienAdapter adapter;
    ArrayList<Nhanvien> arrnhanvien;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhanvien);
        timkiemnv= findViewById(R.id.timkiemnv);
        lvnhanvien = (ListView) findViewById(R.id.lvnhanvien);
        arrnhanvien = new ArrayList<>();
        adapter = new NhanvienAdapter(this, R.layout.dong_nhan_vien, arrnhanvien);
        lvnhanvien.setAdapter(adapter);
        database = new DbHelper(this, "qlqcaphe.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Nhanvien(manv INTEGER PRIMARY KEY AUTOINCREMENT ,hoten NVARCHAR(30),gioitinh NVARCHAR(3),Namsinh DATETIME,Quequan NVARCHAR(30),CCCD CHAR(12),SDT CHAR(11))");
        getDatanhanvien();
        registerForContextMenu(lvnhanvien);
        timkiemnv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }
    private void filter(String text){
        ArrayList<Nhanvien> filteredList= new ArrayList<>();
        for (Nhanvien nv : arrnhanvien){
            if(nv.getHoten().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(nv);
            }
        }
        adapter.filterList(filteredList);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()){
            case R.id.sua:
                Nhanvien nvsua = arrnhanvien.get(index);
                String tennvsua =nvsua.getHoten();
                String gtsua=nvsua.getGioitinh();
                String nssua=nvsua.getNamsinh();
                String qqsua =nvsua.getQuequan();
                String cccdsua =nvsua.getCccd();
                String sdtsua = nvsua.getSdt();
                int manvsua =nvsua.getManv();
                DialogSua(tennvsua,gtsua,nssua,qqsua,cccdsua,sdtsua,manvsua);
                return true;
            case R.id.xoa:
                Nhanvien nvxoa = arrnhanvien.get(index);
                String tennv =nvxoa.getHoten();
                int manv =nvxoa.getManv();
                DialogXoa(tennv,manv);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void getDatanhanvien() {
        Cursor dlnhanvien = database.Getdata("SELECT * FROM Nhanvien");
        arrnhanvien.clear();
        while (dlnhanvien.moveToNext()) {
            int manv = dlnhanvien.getInt(0);
            String hoten = dlnhanvien.getString(1);
            String gt = dlnhanvien.getString(2);
            String ns = dlnhanvien.getString(3);
            String qq = dlnhanvien.getString(4);
            String cccd = dlnhanvien.getString(5);
            String sdt = dlnhanvien.getString(6);
            arrnhanvien.add(new Nhanvien(manv, hoten, gt, ns, qq, cccd, sdt));
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.them) {
            Dialogthem();
        }
        return super.onOptionsItemSelected(item);
    }
    public void DialogXoa(String hotennv,int manv){
        AlertDialog.Builder dialogxoa = new AlertDialog.Builder(this);
        dialogxoa.setMessage("Bạn có muốn xoá nhân viên "+hotennv+" này không?");
        dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM Nhanvien WHERE manv ='"+manv+"'");
                Toast.makeText(DSNhanvien.this, "Đã xoá nhân viên "+hotennv, Toast.LENGTH_SHORT).show();
                getDatanhanvien();
            }
        });
        dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogxoa.show();
    }
    public void DialogSua(String hoten,String gt,String ns,String qq,String cccd,String sdt,int manv){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.suanhanvien);
        btnsua = (Button) dialog.findViewById(R.id.suanv);
        btnhuysua = (Button) dialog.findViewById(R.id.huysua);
        edthoten = (EditText) dialog.findViewById(R.id.hotennvsua);
        edtgt = (EditText) dialog.findViewById(R.id.gioitinhnvsua);
        edtns = (EditText) dialog.findViewById(R.id.namsinhnvsua);
        edtqq = (EditText) dialog.findViewById(R.id.quequannvsua);
        edtcccd = (EditText) dialog.findViewById(R.id.cccdnvsua);
        edtsdt = (EditText) dialog.findViewById(R.id.sdtnvsua);
        edthoten.setText(hoten);
        edtgt.setText(gt);
        edtns.setText(ns);
        edtqq.setText(qq);
        edtcccd.setText(cccd);
        edtsdt.setText(sdt);
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hotenmoi = edthoten.getText().toString().trim();
                String gtmoi = edtgt.getText().toString().trim();
                String nsmoi = edtns.getText().toString().trim();
                String qqmoi = edtqq.getText().toString().trim();
                String cccdmoi = edtcccd.getText().toString().trim();
                String sdtmoi = edtsdt.getText().toString().trim();
                if(hotenmoi.equals("") || gtmoi.equals("") || nsmoi.equals("") || qqmoi.equals("") || cccdmoi.equals("") || sdtmoi.equals(""))
                    Toast.makeText(DSNhanvien.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                else {
                    database.QueryData("UPDATE Nhanvien SET hoten ='" + hotenmoi + "',gioitinh ='" + gtmoi + "',Namsinh ='" + nsmoi + "',Quequan ='" + qqmoi + "',CCCD ='" + cccdmoi + "',SDT='" + sdtmoi + "' WHERE manv = '" + manv + "'");
                    Toast.makeText(DSNhanvien.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDatanhanvien();
                }
            }
        });
        btnhuysua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void Dialogthem() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.themnhanvien);
        btnthem = (Button) dialog.findViewById(R.id.themnv);
        btnhuy = (Button) dialog.findViewById(R.id.huynv);
        edthoten = (EditText) dialog.findViewById(R.id.hotennv);
        edtgt = (EditText) dialog.findViewById(R.id.gioitinhnv);
        edtns = (EditText) dialog.findViewById(R.id.namsinhnv);
        edtqq = (EditText) dialog.findViewById(R.id.quequannv);
        edtcccd = (EditText) dialog.findViewById(R.id.cccdnv);
        edtsdt = (EditText) dialog.findViewById(R.id.sdtnv);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hotennv= edthoten.getText().toString().trim();
                String gtnv= edtgt.getText().toString().trim();
                String nsnv= edtns.getText().toString().trim();
                String qqnv= edtqq.getText().toString().trim();
                String cccdnv= edtcccd.getText().toString().trim();
                String sdtnv= edtsdt.getText().toString().trim();
                if(hotennv.equals("") || gtnv.equals("") || nsnv.equals("") || qqnv.equals("") || cccdnv.equals("") || sdtnv.equals(""))
                    Toast.makeText(DSNhanvien.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                else {
                    database.QueryData("INSERT INTO Nhanvien VALUES(null,'" + hotennv + "','" + gtnv + "','" + nsnv + "','" + qqnv + "','" + cccdnv + "','" + sdtnv + "')");
                    Toast.makeText(DSNhanvien.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDatanhanvien();
                }
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}