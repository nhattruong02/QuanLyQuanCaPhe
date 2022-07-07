package com.example.doan2.DTO;

public class Khachhang {
    int makh;
    String hoten,gt,ns,sdt;

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Khachhang(int makh, String hoten, String gt, String ns, String sdt) {
        this.makh = makh;
        this.hoten = hoten;
        this.gt = gt;
        this.ns = ns;
        this.sdt = sdt;
    }
}

