package com.example.doan2.DTO;

import java.io.Serializable;

public class Thucdon implements Serializable {
    int mahh;
    String tenhh, loaihh, tinhtrang;
    double gia;
    byte[] hinh;

    public Thucdon() {

    }

    public Thucdon(int mahh, String tenhh, double gia) {
        this.mahh = mahh;
        this.tenhh = tenhh;
        this.gia = gia;
    }

    public Thucdon(int mahh, String tenhh, String loaihh, String tinhtrang, double gia, byte[] hinh) {
        this.mahh = mahh;
        this.tenhh = tenhh;
        this.loaihh = loaihh;
        this.tinhtrang = tinhtrang;
        this.gia = gia;
        this.hinh = hinh;
    }

    @Override
    public String toString() {
        return "Tên món:" + tenhh + '-' + "Giá:" + gia;
    }

    public int getMahh() {
        return mahh;
    }

    public void setMahh(int mahh) {
        this.mahh = mahh;
    }

    public String getTenhh() {
        return tenhh;
    }

    public void setTenhh(String tenhh) {
        this.tenhh = tenhh;
    }

    public String getLoaihh() {
        return loaihh;
    }

    public void setLoaihh(String loaihh) {
        this.loaihh = loaihh;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }
}
