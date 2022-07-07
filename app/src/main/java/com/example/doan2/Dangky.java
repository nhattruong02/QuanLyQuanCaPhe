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

public class Dangky extends AppCompatActivity {
    DbHelper database;
    Button btndk1,btnql;
    EditText edthoten,edtemail,edttkdk,edtmkdk;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky);
        btndk1=(Button) findViewById(R.id.btndangky1);
        btnql=(Button) findViewById(R.id.btnquaylai);
        edttkdk=(EditText) findViewById(R.id.edttkdk);
        edtmkdk=(EditText) findViewById(R.id.edtmkdk);
        edthoten=(EditText) findViewById(R.id.edthoten);
        edtemail=(EditText) findViewById(R.id.edtemail);
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
        Intent intent = new Intent(Dangky.this,Dangnhap.class);
        startActivity(intent);
    }
    public void btnQuaylai(){
        Intent intent = new Intent(Dangky.this,Dangnhap.class);
        startActivity(intent);
    }
    public Boolean ktdangky(){
        String tk =edttkdk.getText().toString();
        Cursor kttk = database.Getdata("SELECT * FROM Taikhoan WHERE tk='"+tk+"'");
        if(kttk.getCount()!=0) {
            return true;
        }
        else{
            return false;
        }
    }
    public void onClickDangky(View view){
        String tk =edttkdk.getText().toString();
        String mk= edtmkdk.getText().toString();
        String hoten =edthoten.getText().toString();
        String email =edtemail.getText().toString();
        boolean kttk =ktdangky();
        if(kttk) {
            Toast.makeText(this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
        }
        else if(tk.equals("") || mk.equals("")|| hoten.equals("")||email.equals("")){
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
        else{
            database.QueryData("INSERT INTO Taikhoan VALUES ('"+tk+"','"+mk+"','"+hoten+"','"+email+"')");
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            btnDangky();
        }
    }
}
