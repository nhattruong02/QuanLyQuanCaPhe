package com.example.doan2.DTO;

public class Chitiethoadon {
    String ten;
    double gia;
    int soluong;
    int mahd;
    int mahh;
    int mahdct;
    public Chitiethoadon(){

    }
    public Chitiethoadon(int mahdct ,int mahd, int mahh,String ten, double gia, int soluong) {
        this.mahdct = mahdct;
        this.mahd = mahd;
        this.mahh = mahh;
        this.ten = ten;
        this.gia = gia;
        this.soluong = soluong;
    }

    public Chitiethoadon(String ten, double gia, int soluong) {
        this.ten = ten;
        this.gia = gia;
        this.soluong = soluong;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
    public int getMahd() {
        return mahd;
    }

    public void setMahd(int mahd) {
        this.mahd = mahd;
    }

    public int getMahh() {
        return mahh;
    }

    public void setMahh(int mahh) {
        this.mahh = mahh;
    }

    public int getMahdct() {
        return mahdct;
    }

    public void setMahdct(int mahdct) {
        this.mahdct = mahdct;
    }
}
