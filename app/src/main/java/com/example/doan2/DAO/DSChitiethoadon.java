package com.example.doan2.DAO;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan2.Adapter.HoadonAdapter;
import com.example.doan2.Adapter.HoadonchitietAdapter;
import com.example.doan2.DTO.Ban;
import com.example.doan2.DTO.Chitiethoadon;
import com.example.doan2.DTO.Hoadon;
import com.example.doan2.DTO.Nhanvien;
import com.example.doan2.DTO.Thucdon;
import com.example.doan2.Database.DbHelper;
import com.example.doan2.R;

import java.util.ArrayList;

public class DSChitiethoadon extends AppCompatActivity {
    DbHelper database;
    ListView lvcthd;
    TextView madon, tenhh, giahh, tongtien;
    EditText edtsoluong,edtslsua;
    int mahd,soluongcong;
    DSThucdon dsThucdon;
    ArrayList<Thucdon> td = new ArrayList<>();
    Spinner spn;
    Button btndongy,btnsua,btnhuysua;
    HoadonchitietAdapter adapter;
    ArrayList<Chitiethoadon> arrcthd;
    int mahh;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiethoadon);
        btndongy = findViewById(R.id.btndongy);
        tongtien = findViewById(R.id.tongtiencthd);
        Intent intent = getIntent();
        mahd = intent.getIntExtra("Mahoadon", 0);
        String mahd1 = String.valueOf(mahd);
        madon = findViewById(R.id.tvmadon);
        madon.setText(mahd1);
        tenhh = findViewById(R.id.tendcthd);
        edtsoluong = findViewById(R.id.soluongcthd);
        giahh = findViewById(R.id.giadcthd);
        lvcthd = findViewById(R.id.lvcthd);
        spn = findViewById(R.id.spnthucdon);
        dsThucdon = new DSThucdon();
        dsThucdon.DSThucdon(DSChitiethoadon.this);
        td = dsThucdon.getAll();
        ArrayAdapter adapterspn = new ArrayAdapter(this, android.R.layout.simple_spinner_item, td);
        spn.setAdapter(adapterspn);
        database = new DbHelper(this, "qlqcaphe.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Chitiethoadon(macthd INTEGER PRIMARY KEY AUTOINCREMENT ,mahd NVARCHAR(10) REFERENCES Hoadon(mahd),mahh NVARCHAR(10) REFERENCES Thucdon(mahh),ten INTEGER,gia DOUBLE,soluong INTEGER)");
        arrcthd = new ArrayList<>();
        adapter = new HoadonchitietAdapter(DSChitiethoadon.this, R.layout.dong_chi_tiet_hoa_don, arrcthd);
        lvcthd.setAdapter(adapter);
        btndongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int soluong = Integer.parseInt(edtsoluong.getText().toString());
                    if(soluong < 0){
                        Toast.makeText(DSChitiethoadon.this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    }
                    Thucdon td = (Thucdon) spn.getSelectedItem();
                    mahh = td.getMahh();
                    String ten = td.getTenhh();
                    double gia = td.getGia();
                    int i;
                    for (i=0;i<arrcthd.size();i++){
                        if(arrcthd.get(i).getMahh() == mahh){
                            soluong = soluong + arrcthd.get(i).getSoluong();
                            arrcthd.get(i).setSoluong(soluong);
                            double tong = tongtien();
                            tongtien.setText(tong+"Đ");
                            suacthd(mahd,mahh,soluong);
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                    if(i>=arrcthd.size()) {
                        themcthd(mahd, mahh, ten, gia, soluong);
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(DSChitiethoadon.this, "Hãy nhập vào số!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getDatacthd(mahd);
        registerForContextMenu(lvcthd);
    }
    public double tongtien() {
        double tongtien = 0;
        for (int i = 0; i < arrcthd.size(); i++) {
            double gia = arrcthd.get(i).getGia();
            int soluong = arrcthd.get(i).getSoluong();
            tongtien = tongtien + (gia * soluong);
        }
        return tongtien;
    }

    public void getDatacthd(int mahdlv) {
        Cursor dlcthd = database.Getdata("SELECT * FROM Chitiethoadon WHERE mahd = '"+mahdlv+"'");
        arrcthd.clear();
        while (dlcthd.moveToNext()) {
            int mahdct = dlcthd.getInt(0);
            int mahd = dlcthd.getInt(1);
            int mahh = dlcthd.getInt(2);
            String ten = dlcthd.getString(3);
            double gia = dlcthd.getDouble(4);
            int soluong = dlcthd.getInt(5);
            arrcthd.add(new Chitiethoadon(mahdct, mahd, mahh, ten, gia, soluong));
        }
        double tong = tongtien();
        tongtien.setText(tong+"Đ");
        database.QueryData("UPDATE Hoadon SET tongtien = '" + tong + "' WHERE mahd = '" + mahd + "'");
        adapter.notifyDataSetChanged();
    }
    public void suacthd(int mahd,int mahh,int soluong){
        database.QueryData("UPDATE Chitiethoadon SET soluong ='" + soluong + "' WHERE mahh = '" + mahh + "' AND mahd ='"+mahd+"'");
    }
    public void themcthd(int mahd, int mahh, String ten, double gia, int soluong) {
        database.QueryData("INSERT INTO Chitiethoadon VALUES(null,'" + mahd + "','" + mahh + "','" + ten + "','" + gia + "','" + soluong + "')");
        Toast.makeText(DSChitiethoadon.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
        getDatacthd(mahd);
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
        switch (item.getItemId()) {
            case R.id.sua:
                Chitiethoadon cthdsua = arrcthd.get(index);
                int slsua = cthdsua.getSoluong();
                int mahdctsua = cthdsua.getMahdct();
                DialogSua(slsua,mahdctsua);
                return true;
            case R.id.xoa:
                Chitiethoadon cthdxoa = arrcthd.get(index);
                String ten = cthdxoa.getTen();
                int ma = cthdxoa.getMahdct();
                DialogXoa(ten, ma);
                return true;
        }
        return super.onContextItemSelected(item);
    }
    public void DialogXoa(String ten,int ma){
        AlertDialog.Builder dialogxoa = new AlertDialog.Builder(this);
        dialogxoa.setMessage("Bạn có muốn xoá món "+ten+" này không?");
        dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM Chitiethoadon WHERE macthd ='"+ma+"'");
                Toast.makeText(DSChitiethoadon.this, "Đã xoá món "+ten, Toast.LENGTH_SHORT).show();
                getDatacthd(mahd);
            }
        });
        dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogxoa.show();
    }
    public void DialogSua(int soluong,int ma){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.suachitietthucdon);
        edtslsua = (EditText) dialog.findViewById(R.id.soluongsua);
        btnsua = (Button) dialog.findViewById(R.id.suacthd);
        btnhuysua = (Button) dialog.findViewById(R.id.huysuacthd);
        String n = String.valueOf(soluong);
        edtslsua.setText(n);
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int slmoi = Integer.parseInt(edtslsua.getText().toString());
                    if(slmoi < 0){
                        Toast.makeText(DSChitiethoadon.this, "Số lượng phải lớn hơn 0 ", Toast.LENGTH_SHORT).show();
                    }else {
                        database.QueryData("UPDATE Chitiethoadon SET soluong ='" + slmoi + "' WHERE macthd = '" + ma + "'");
                        Toast.makeText(DSChitiethoadon.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        getDatacthd(mahd);
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(DSChitiethoadon.this, "Hãy nhập vào số!", Toast.LENGTH_SHORT).show();
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
    public void DSChitiethoadon(Context context){
        database = new DbHelper(context,"qlqcaphe.sqlite", null, 1);
    }
    public ArrayList<Chitiethoadon> getAll1(){
        ArrayList<Chitiethoadon> dscthd =new ArrayList<>();
        Cursor dlcthd = database.Getdata("SELECT * FROM Chitiethoadon");
        while (dlcthd.moveToNext()) {
            Chitiethoadon cthd = new Chitiethoadon();
            cthd.setMahd(dlcthd.getInt(1));
            cthd.setGia( dlcthd.getDouble(4));
            cthd.setSoluong( dlcthd.getInt(5));
            dscthd.add(cthd);
        }
        return dscthd;
    }
}


