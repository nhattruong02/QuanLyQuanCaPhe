package com.example.doan2.DAO;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.doan2.Adapter.HoadonAdapter;
import com.example.doan2.DTO.Chitiethoadon;
import com.example.doan2.DTO.Hoadon;
import com.example.doan2.DTO.Khachhang;
import com.example.doan2.DTO.Nhanvien;
import com.example.doan2.DTO.Thucdon;
import com.example.doan2.Database.DbHelper;
import com.example.doan2.MainActivity;
import com.example.doan2.R;
import com.example.doan2.Thongke;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DSHoadon extends AppCompatActivity {
    DbHelper database;
    Button btnthem, btnhuy, btnsua, btnhuysua;
    EditText edthotennv, edtngayxuat, edttenban, edttinhtrang, edttongtien,timkiemhd;
    ListView lvhoadon;
    HoadonAdapter adapter;
    ArrayList<Hoadon> arrhoadon;
    ArrayList<Chitiethoadon> cthd = new ArrayList<>();
    DSChitiethoadon dsChitiethoadon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoadon);
        lvhoadon = (ListView) findViewById(R.id.lvhoadon);
        arrhoadon = new ArrayList<>();
        adapter = new HoadonAdapter(this, R.layout.dong_hoa_don, arrhoadon);
        lvhoadon.setAdapter(adapter);
        database = new DbHelper(this, "qlqcaphe.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Hoadon(mahd INTEGER PRIMARY KEY AUTOINCREMENT ,hotennv NVARCHAR(30),ngayxuat DATETIME,tenban NVARCHAR(30),tinhtrang NVARCHAR(30),tongtien DOUBLE)");
        getDatahoadon();
        lvhoadon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DSHoadon.this, DSChitiethoadon.class);
                intent.putExtra("Mahoadon", arrhoadon.get(i).getMahd());
                startActivity(intent);
            }
        });
        registerForContextMenu(lvhoadon);
        timkiemhd =  findViewById(R.id.timkiemhoadon);
        timkiemhd.addTextChangedListener(new TextWatcher() {
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
        ArrayList<Hoadon> filteredList= new ArrayList<>();
        for (Hoadon hd : arrhoadon){
            if(hd.getTenban().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(hd);
            }
        }
        adapter.filterList(filteredList);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
            case R.id.sua:
                Hoadon hdsua = arrhoadon.get(index);
                String htnvsua = hdsua.getTennv();
                String nxsua = hdsua.getNgayxuat();
                String tenbansua = hdsua.getTenban();
                String ttsua = hdsua.getTinhtrang();
                double tongtiensua = hdsua.getTongtien();
                int mahdsua = hdsua.getMahd();
                DialogSua(htnvsua, nxsua, tenbansua, ttsua, tongtiensua, mahdsua);
                return true;
            case R.id.xoa:
                Hoadon hdxoa = arrhoadon.get(index);
                int mahdxoa = hdxoa.getMahd();
                DialogXoa(mahdxoa);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void getDatahoadon() {
        Cursor dlhoadon = database.Getdata("SELECT * FROM Hoadon");
        arrhoadon.clear();
        while (dlhoadon.moveToNext()) {
            int mahd = dlhoadon.getInt(0);
            String hotennv = dlhoadon.getString(1);
            String ngayxuat = dlhoadon.getString(2);
            String tenban = dlhoadon.getString(3);
            String tinhtrang = dlhoadon.getString(4);
            arrhoadon.add(new Hoadon(mahd, hotennv, ngayxuat, tenban, tinhtrang, tongtien(mahd)));
            database.QueryData("UPDATE Hoadon SET tongtien = '" + tongtien(mahd) + "' WHERE mahd = '" + mahd + "'");
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

    public void DialogXoa(int mahd) {
        AlertDialog.Builder dialogxoa = new AlertDialog.Builder(this);
        dialogxoa.setMessage("Bạn có muốn xoá hoá đơn " + mahd + " này không?");
        dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM Hoadon WHERE mahd ='" + mahd + "'");
                Toast.makeText(DSHoadon.this, "Đã xoá hoá đơn " + mahd, Toast.LENGTH_SHORT).show();
                getDatahoadon();
            }
        });
        dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogxoa.show();
    }

    public void DialogSua(String tennv, String ngayxuat, String tenban, String tinhtrang, double tongtien, int mahd) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.suahoadon);
        btnsua = (Button) dialog.findViewById(R.id.suahd);
        btnhuysua = (Button) dialog.findViewById(R.id.huyhd);
        edthotennv = (EditText) dialog.findViewById(R.id.hotenhdsua);
        edtngayxuat = (EditText) dialog.findViewById(R.id.ngayxuathdsua);
        edttenban = (EditText) dialog.findViewById(R.id.tenbanhdsua);
        edttinhtrang = (EditText) dialog.findViewById(R.id.tinhtranghdsua);
        edthotennv.setText(tennv);
        edtngayxuat.setText(ngayxuat);
        edttenban.setText(tenban);
        edttinhtrang.setText(tinhtrang);
        String n = String.valueOf(tongtien);
        edttongtien.setText(n);
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hotennvmoi = edthotennv.getText().toString();
                String ngayxuatmoi = edtngayxuat.getText().toString();
                String tenbanmoi = edttenban.getText().toString();
                String tinhtrangmoi = edttinhtrang.getText().toString();
                if (hotennvmoi.equals("") || ngayxuatmoi.equals("") || tenbanmoi.equals("") || tinhtrangmoi.equals("")) {
                    Toast.makeText(DSHoadon.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("UPDATE Hoadon SET hotennv ='" + hotennvmoi + "',ngayxuat ='" + ngayxuatmoi + "',tenban ='" + tenbanmoi + "',tinhtrang='" + tinhtrangmoi + "' WHERE mahd = '" + mahd + "'");
                    Toast.makeText(DSHoadon.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDatahoadon();
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
        dialog.setContentView(R.layout.themhoadon);
        btnthem = (Button) dialog.findViewById(R.id.themhd);
        btnhuy = (Button) dialog.findViewById(R.id.huyhd);
        edthotennv = (EditText) dialog.findViewById(R.id.hotenhd);
        edtngayxuat = (EditText) dialog.findViewById(R.id.ngayxuathd);
        edttenban = (EditText) dialog.findViewById(R.id.tenbanhd);
        edttinhtrang = (EditText) dialog.findViewById(R.id.tinhtranghd);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hotennv = edthotennv.getText().toString();
                String ngayxuathd = edtngayxuat.getText().toString();
                String tenbanhd = edttenban.getText().toString();
                String tinhtranghd = edttinhtrang.getText().toString();
                int tong = 0;
                if (hotennv.equals("") || ngayxuathd.equals("") || tenbanhd.equals("") || tinhtranghd.equals("")) {
                    Toast.makeText(DSHoadon.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("INSERT INTO Hoadon VALUES(null,'" + hotennv + "','" + ngayxuathd + "','" + tenbanhd + "','" + tinhtranghd + "','" + tong + "')");
                    Toast.makeText(DSHoadon.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDatahoadon();
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

    public double tongtien(int mahd) {
        double tongtien = 0;
        dsChitiethoadon = new DSChitiethoadon();
        dsChitiethoadon.DSChitiethoadon(DSHoadon.this);
        cthd = dsChitiethoadon.getAll1();
        for (int i = 0; i < cthd.size(); i++) {
            if (cthd.get(i).getMahd() == mahd) {
                double gia = cthd.get(i).getGia();
                int soluong = cthd.get(i).getSoluong();
                tongtien = tongtien + (gia * soluong);
            }
        }
        return tongtien;
    }
    public void DSHoadon(Context context){
        database = new DbHelper(context,"qlqcaphe.sqlite", null, 1);
    }
    public ArrayList<Hoadon> getAll(){
        ArrayList<Hoadon> dshd =new ArrayList<>();
        Cursor dlhd = database.Getdata("SELECT * FROM Hoadon");
        while (dlhd .moveToNext()) {
            Hoadon hd  = new Hoadon();
            hd.setNgayxuat(dlhd.getString(2));
            hd.setTongtien( dlhd.getDouble(5));
            dshd.add(hd);
        }
        return dshd;
    }

}

