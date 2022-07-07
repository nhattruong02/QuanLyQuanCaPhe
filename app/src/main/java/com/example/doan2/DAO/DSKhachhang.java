package com.example.doan2.DAO;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.example.doan2.Adapter.KhachhangAdapter;
import com.example.doan2.DTO.Hoadon;
import com.example.doan2.DTO.Khachhang;
import com.example.doan2.DTO.Nhanvien;
import com.example.doan2.Database.DbHelper;
import com.example.doan2.R;

import java.util.ArrayList;

public class DSKhachhang extends AppCompatActivity {
    DbHelper database;
    Button btnthem, btnhuy, btnsua, btnhuysua;
    EditText edthoten, edtgt, edtns, edtsdt,timkiemkh;
    ListView lvkhachhang;
    KhachhangAdapter adapter;
    ArrayList<Khachhang> arrkhachhang;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khachhang);
        lvkhachhang = (ListView) findViewById(R.id.lvkhachhang);
        arrkhachhang = new ArrayList<>();
        adapter = new KhachhangAdapter(this, R.layout.dong_khach_hang, arrkhachhang);
        lvkhachhang.setAdapter(adapter);
        database = new DbHelper(this, "qlqcaphe.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Khachhang(makh INTEGER PRIMARY KEY AUTOINCREMENT ,hoten NVARCHAR(30),gioitinh NVARCHAR(3),Namsinh DATETIME,SDT CHAR(11))");
        getDatakhachhang();
        registerForContextMenu(lvkhachhang);
        timkiemkh = findViewById(R.id.timkiemkh);
        timkiemkh.addTextChangedListener(new TextWatcher() {
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
        ArrayList<Khachhang> filteredList= new ArrayList<>();
        for (Khachhang kh : arrkhachhang){
            if(kh.getHoten().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(kh);
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
                Khachhang khsua = arrkhachhang.get(index);
                String hotensua = khsua.getHoten();
                String gtsua = khsua.getGt();
                String nssua = khsua.getNs();
                String sdtsua = khsua.getSdt();
                int manvsua = khsua.getMakh();
                DialogSua(hotensua, gtsua, nssua, sdtsua, manvsua);
                return true;
            case R.id.xoa:
                Khachhang khxoa = arrkhachhang.get(index);
                String hotenxoa = khxoa.getHoten();
                int makhxoa = khxoa.getMakh();
                DialogXoa(hotenxoa, makhxoa);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void getDatakhachhang() {
        Cursor dlkhachhang = database.Getdata("SELECT * FROM Khachhang");
        arrkhachhang.clear();
        while (dlkhachhang.moveToNext()) {
            int manv = dlkhachhang.getInt(0);
            String hoten = dlkhachhang.getString(1);
            String gt = dlkhachhang.getString(2);
            String ns = dlkhachhang.getString(3);
            String sdt = dlkhachhang.getString(4);
            arrkhachhang.add(new Khachhang(manv, hoten, gt, ns, sdt));
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

    public void DialogXoa(String hotenkh, int makh) {
        AlertDialog.Builder dialogxoa = new AlertDialog.Builder(this);
        dialogxoa.setMessage("Bạn có muốn xoá khách hàng " + hotenkh + " này không?");
        dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM khachhang WHERE makh ='" + makh + "'");
                Toast.makeText(DSKhachhang.this, "Đã xoá khách hàng " + hotenkh, Toast.LENGTH_SHORT).show();
                getDatakhachhang();
            }
        });
        dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogxoa.show();
    }

    public void DialogSua(String hoten, String gt, String ns, String sdt, int makh) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.suakhachhang);
        btnsua = (Button) dialog.findViewById(R.id.suakh);
        btnhuysua = (Button) dialog.findViewById(R.id.huysuakh);
        edthoten = (EditText) dialog.findViewById(R.id.hotenkhsua);
        edtgt = (EditText) dialog.findViewById(R.id.gioitinhkhsua);
        edtns = (EditText) dialog.findViewById(R.id.namsinhkhsua);
        edtsdt = (EditText) dialog.findViewById(R.id.sdtkhsua);
        edthoten.setText(hoten);
        edtgt.setText(gt);
        edtns.setText(ns);
        edtsdt.setText(sdt);
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hotenmoi = edthoten.getText().toString();
                String gtmoi = edtgt.getText().toString();
                String nsmoi = edtns.getText().toString();
                String sdtmoi = edtsdt.getText().toString();
                if (hotenmoi.equals("") || gtmoi.equals("") || nsmoi.equals("") || sdtmoi .equals("")) {
                    Toast.makeText(DSKhachhang.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("UPDATE Khachhang SET hoten ='" + hotenmoi + "',gioitinh ='" + gtmoi + "',Namsinh ='" + nsmoi + "',SDT='" + sdtmoi + "' WHERE makh = '" + makh + "'");
                    Toast.makeText(DSKhachhang.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDatakhachhang();
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
        dialog.setContentView(R.layout.themkhachhang);
        btnthem = (Button) dialog.findViewById(R.id.themkh);
        btnhuy = (Button) dialog.findViewById(R.id.huykh);
        edthoten = (EditText) dialog.findViewById(R.id.hotenkh);
        edtgt = (EditText) dialog.findViewById(R.id.gioitinhkh);
        edtns = (EditText) dialog.findViewById(R.id.namsinhkh);
        edtsdt = (EditText) dialog.findViewById(R.id.sdtkh);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hotenkh = edthoten.getText().toString();
                String gtkh = edtgt.getText().toString();
                String nskh = edtns.getText().toString();
                String sdtkh = edtsdt.getText().toString();
                if (hotenkh.equals("") || gtkh.equals("") || nskh.equals("") || sdtkh.equals("")) {
                    Toast.makeText(DSKhachhang.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("INSERT INTO Khachhang VALUES(null,'" + hotenkh + "','" + gtkh + "','" + nskh + "','" + sdtkh + "')");
                    Toast.makeText(DSKhachhang.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDatakhachhang();
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


