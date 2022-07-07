package com.example.doan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.doan2.DAO.DSBan;
import com.example.doan2.DAO.DSHoadon;
import com.example.doan2.DAO.DSKhachhang;
import com.example.doan2.DAO.DSNhanvien;
import com.example.doan2.DAO.DSThucdon;
import com.example.doan2.DTO.Chitiethoadon;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void ban() {
        Intent intent = new Intent(MainActivity.this, DSBan.class);
        startActivity(intent);
    }

    public void onClickban(View view) {
        ban();
    }
    public void nhanvien() {
        Intent intent = new Intent(MainActivity.this, DSNhanvien.class);
        startActivity(intent);
    }

    public void onClicknhanvien(View view) {
        nhanvien();
    }

    public void khachhang() {
        Intent intent = new Intent(MainActivity.this, DSKhachhang.class);
        startActivity(intent);
    }

    public void onClickkhachhang(View view) {
        khachhang();
    }

    public void thucdon() {
        Intent intent = new Intent(MainActivity.this, DSThucdon.class);
        startActivity(intent);
    }

    public void onClickthucdon(View view) {
        thucdon();
    }
    public void hoadon() {
        Intent intent = new Intent(MainActivity.this, DSHoadon.class);
        startActivity(intent);
    }

    public void onClickhoadon(View view) {
        hoadon();
    }
    public void thongke() {
        Intent intent = new Intent(MainActivity.this, Thongke.class);
        startActivity(intent);
    }

    public void onClickthongke(View view) {
        thongke();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuactivity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.thoat:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}