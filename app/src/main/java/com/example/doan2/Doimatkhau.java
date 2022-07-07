package com.example.doan2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan2.Database.DbHelper;

public class Doimatkhau extends AppCompatActivity {
    DbHelper database;
    Button btnthaydoi,btnql;
    EditText edttk,edtmk,edtmkmoi,edtxacnhanmk;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doimatkhau);
        btnthaydoi = (Button) findViewById(R.id.btnthaydoi);
        btnql = (Button) findViewById(R.id.btnquaylai);
        edttk = (EditText) findViewById(R.id.edttk);
        edtmk = (EditText)findViewById(R.id.edtmk);
        edtmkmoi = (EditText)findViewById(R.id.edtmkmoi);
        edtxacnhanmk = (EditText)findViewById(R.id.edtxacnhanmk);
        database = new DbHelper(this,"qlqcaphe.sqlite",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Taikhoan(tk CHAR(20) PRIMARY KEY,mk CHAR(20),hoten NVARCHAR(30),Email CHAR(30) )");
        btnql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnQuaylai();
            }
        });
    }
    public void btnDangky(){
        Intent intent = new Intent(Doimatkhau.this,Dangnhap.class);
        startActivity(intent);
    }
    public void btnQuaylai(){
        Intent intent = new Intent(Doimatkhau.this,Dangnhap.class);
        startActivity(intent);
    }
    public Boolean ktdangky(){
        String tk =edttk.getText().toString();
        String mk= edtmk.getText().toString();
        Cursor kttk = database.Getdata("SELECT * FROM Taikhoan WHERE tk='"+tk+"' AND mk='"+mk+"'");
        if(kttk.getCount()==0) {
            return true;
        }
        else{
            return false;
        }
    }
    public void onClickThaydoi(View view){
        String tk =edttk.getText().toString();
        String mk= edtmk.getText().toString();
        String mkmoi =edtmkmoi.getText().toString();
        String xacnhanmk =edtxacnhanmk.getText().toString();
        boolean kttk =ktdangky();
        if(kttk) {
            Toast.makeText(this, "Tài khoản hoặc mật khẩu chưa tồn tại!", Toast.LENGTH_SHORT).show();
        }
        else if(tk.equals("") || mk.equals("")|| mkmoi.equals("")||xacnhanmk.equals("")) {
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
        else if(!mkmoi.equals(xacnhanmk)){
            Toast.makeText(this, "Mật khẩu xác nhận sai!", Toast.LENGTH_SHORT).show();
        }
        else{
            database.QueryData("UPDATE Taikhoan SET mk ='"+mkmoi+"'");
            Toast.makeText(this, "Đổi mật khẩu thành công thành công!", Toast.LENGTH_SHORT).show();
            btnDangky();
        }
    }

}
