package com.example.doan2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan2.DAO.DSKhachhang;
import com.example.doan2.Database.DbHelper;

public class Dangnhap extends AppCompatActivity implements View.OnClickListener{
    DbHelper database;
    Button btndn,btndk;
    EditText edttk,edtmk;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);
        btndn = (Button) findViewById(R.id.btndangnhap);
        btndk = (Button) findViewById(R.id.btndangky);
        edttk=(EditText) findViewById(R.id.edttk);
        edtmk=(EditText) findViewById(R.id.edtmk);
        btndn.setOnClickListener(this);
        btndk.setOnClickListener(this);
        database = new DbHelper(this,"qlqcaphe.sqlite",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Taikhoan(tk CHAR(20) PRIMARY KEY,mk CHAR(20),hoten NVARCHAR(30),Email CHAR(30) )");
    }
    public void btnDangnhap(){
        Intent intent = new Intent(Dangnhap.this,MainActivity.class);
        startActivity(intent);
    }
    public boolean ktdangnhap(){
        String tk =edttk.getText().toString();
        String mk =edtmk.getText().toString();
        Cursor tkdn = database.Getdata("SELECT * FROM Taikhoan WHERE tk='"+tk+"' AND mk='"+mk+"'");
        if(tkdn.getCount() != 0) {
            return true;
        }
        else{
            return false;
        }
    }
    public void btnDangky(){
        Intent intent = new Intent(Dangnhap.this, Dangky.class);
        startActivity(intent);
    }
    public void doimk() {
        Intent intent = new Intent(Dangnhap.this, Doimatkhau.class);
        startActivity(intent);
    }

    public void onClickdoimk(View view) {
        doimk();
    }
    @Override
    public void onClick(View view) {
        int isCheck = view.getId();
        boolean kt =ktdangnhap();
        switch (isCheck) {
            case (R.id.btndangnhap): {
                if (kt) {
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    btnDangnhap();
                } else {
                    Toast.makeText(this, "Tài khoản hoặc mật khẩu không tồn tại!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case (R.id.btndangky):
                btnDangky();
                break;

        }
    }
}
