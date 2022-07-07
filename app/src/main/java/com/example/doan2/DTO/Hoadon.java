package com.example.doan2.DTO;

public class Hoadon {
    int mahd;
    String tennv;
    String ngayxuat;
    String tenban;
    String tinhtrang;
    double tongtien;
    public Hoadon(){

    }
    public Hoadon(int mahd, String tennv, String ngayxuat, String tenban, String tinhtrang, double tongtien) {
        this.mahd = mahd;
        this.tennv = tennv;
        this.ngayxuat = ngayxuat;
        this.tenban = tenban;
        this.tinhtrang = tinhtrang;
        this.tongtien = tongtien;
    }
    public int getMahd() {
        return mahd;
    }

    public void setMahd(int mahd) {
        this.mahd = mahd;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getNgayxuat() {
        return ngayxuat;
    }

    public void setNgayxuat(String ngayxuat) {
        this.ngayxuat = ngayxuat;
    }

    public String getTenban() {
        return tenban;
    }

    public void setTenban(String tenban) {
        this.tenban = tenban;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

}
