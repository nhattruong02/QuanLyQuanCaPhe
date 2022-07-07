package com.example.doan2.DAO;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.doan2.Adapter.ThucdonAdapter;


import com.example.doan2.DTO.Chitiethoadon;
import com.example.doan2.DTO.Nhanvien;
import com.example.doan2.DTO.Thucdon;
import com.example.doan2.Database.DbHelper;
import com.example.doan2.R;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class DSThucdon extends AppCompatActivity {
    DbHelper database;
    Button btnthem, btnhuy,btnsua,btnhuysua;
    EditText edtten, edtloai, edttt, edtgia,timkiemtd;
    ListView lvthucdon;
    ImageView imgthucdon;
    ThucdonAdapter adapter;
    ArrayList<Thucdon> arrthucdon;
    int REQUEST_CODE_FOLDER=1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thucdon);
        lvthucdon = (ListView) findViewById(R.id.lvthucdon);
        arrthucdon = new ArrayList<>();
        adapter = new ThucdonAdapter(this, R.layout.dong_thuc_don, arrthucdon);
        lvthucdon.setAdapter(adapter);
        database = new DbHelper(DSThucdon.this, "qlqcaphe.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Thucdon(mahh INTEGER PRIMARY KEY AUTOINCREMENT ,ten NVARCHAR(30),loai NVARCHAR(30),tinhtrang NVARCHAR(30),gia DOUBLE,hinhanh BLOB)");
        getDatathucdon();
        registerForContextMenu(lvthucdon);
        timkiemtd = findViewById(R.id.timkiemthucdon);
        timkiemtd.addTextChangedListener(new TextWatcher() {
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
        ArrayList<Thucdon> filteredList= new ArrayList<>();
        for (Thucdon td : arrthucdon){
            if(td.getTenhh().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(td);
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
                Thucdon tdsua = arrthucdon.get(index);
                String tentdsua =tdsua.getTenhh();
                String loaitdsua=tdsua.getLoaihh();
                String ttsua=tdsua.getTinhtrang();
                double giasua =tdsua.getGia();
                byte[] hinhsua =tdsua.getHinh();
                int matdsua =tdsua.getMahh();
                DialogSua(tentdsua, loaitdsua,ttsua,giasua,hinhsua,matdsua);
                return true;
            case R.id.xoa:
                Thucdon tdxoa = arrthucdon.get(index);
                String tentdxoa =tdxoa.getTenhh();
                int matdxua=tdxoa.getMahh();
                DialogXoa(tentdxoa,matdxua);
                return true;
        }
        return super.onContextItemSelected(item);
    }
    public void getDatathucdon() {
        Cursor dlthucdon = database.Getdata("SELECT * FROM Thucdon");
        arrthucdon.clear();
        while (dlthucdon.moveToNext()) {
            int matd = dlthucdon.getInt(0);
            String ten = dlthucdon.getString(1);
            String loai = dlthucdon.getString(2);
            String tt = dlthucdon.getString(3);
            Double gia = dlthucdon.getDouble(4);
            byte[] hinh =dlthucdon.getBlob(5);
            arrthucdon.add(new Thucdon(matd, ten, loai, tt, gia,hinh));
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
    public void DialogXoa(String tenhh,int mahh){
        AlertDialog.Builder dialogxoa = new AlertDialog.Builder(this);
        dialogxoa.setMessage("Bạn có muốn xoá món "+tenhh+" này không?");
        dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM Thucdon WHERE mahh ='"+mahh+"'");
                Toast.makeText(DSThucdon.this, "Đã xoá món "+tenhh, Toast.LENGTH_SHORT).show();
                getDatathucdon();
            }
        });
        dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogxoa.show();
    }
    public void DialogSua(String tenhh,String loai,String tt,Double gia,byte[] hinhmoi,int mahh){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.suathucdon);
        btnsua = (Button) dialog.findViewById(R.id.suahh);
        btnhuysua = (Button) dialog.findViewById(R.id.huyhh);
        edtten = (EditText) dialog.findViewById(R.id.tenhhsua);
        edtloai = (EditText) dialog.findViewById(R.id.loaihhsua);
        edttt = (EditText) dialog.findViewById(R.id.tthhsua);
        edtgia = (EditText) dialog.findViewById(R.id.giahhsua);
        imgthucdon = (ImageView) dialog.findViewById(R.id.imgsuatd);
        edtten.setText(tenhh);
        edtloai.setText(loai);
        edttt.setText(tt);
        String n = String.valueOf(gia);
        edtgia.setText(n);
        Bitmap bitmap = BitmapFactory.decodeByteArray( hinhmoi,0,hinhmoi.length);
        imgthucdon.setImageBitmap(bitmap);
        imgthucdon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtten.equals("")||edtloai.equals("")||edttt.equals("")||edtgia.equals("")||imgthucdon.equals("")){
                    Toast.makeText(DSThucdon.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imgthucdon.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                    byte[] hinhmoi = byteArray.toByteArray();
                    database.suathucdon(
                            edtten.getText().toString(),
                            edtloai.getText().toString(),
                            edttt.getText().toString(),
                            Double.parseDouble(edtgia.getText().toString()),
                            hinhmoi,
                            mahh
                    );
                    Toast.makeText(DSThucdon.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDatathucdon();
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
        dialog.setContentView(R.layout.themthucdon);
        btnthem = (Button) dialog.findViewById(R.id.themhh);
        btnhuy = (Button) dialog.findViewById(R.id.huyhh);
        edtten = (EditText) dialog.findViewById(R.id.tenhh);
        edtloai = (EditText) dialog.findViewById(R.id.loaihh);
        edttt = (EditText) dialog.findViewById(R.id.tthh);
        edtgia = (EditText) dialog.findViewById(R.id.giahh);
        imgthucdon = (ImageView) dialog.findViewById(R.id.imgthemtd);
        imgthucdon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtten.equals("") || edtloai.equals("") || edttt.equals("") || edtgia.equals("") || imgthucdon.equals("")){
                    Toast.makeText(DSThucdon.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgthucdon.getDrawable();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                        byte[] hinh = byteArray.toByteArray();
                        database.themthucdon(
                                edtten.getText().toString(),
                                edtloai.getText().toString(),
                                edttt.getText().toString(),
                                Double.parseDouble(edtgia.getText().toString()),
                                hinh
                        );
                        Toast.makeText(DSThucdon.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        getDatathucdon();
                    }catch (NullPointerException e){
                        Toast.makeText(DSThucdon.this, "Hãy thêm hình cho món!", Toast.LENGTH_SHORT).show();
                    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data!=null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = (Bitmap) BitmapFactory.decodeStream(inputStream);
                imgthucdon.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void DSThucdon(Context context){
        database = new DbHelper(context,"qlqcaphe.sqlite", null, 1);
    }
    public ArrayList<Thucdon> getAll(){
        ArrayList<Thucdon> dstd =new ArrayList<>();
        Cursor dlthucdon = database.Getdata("SELECT * FROM Thucdon");
        while (dlthucdon.moveToNext()) {
            Thucdon td = new Thucdon();
            td.setMahh( dlthucdon.getInt(0));
            td.setTenhh(dlthucdon.getString(1));
            td.setGia( dlthucdon.getDouble(4));
            dstd.add(td);
        }
        return dstd;
    }
}

