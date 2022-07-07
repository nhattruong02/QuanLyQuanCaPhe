package com.example.doan2.DTO;

public class Ban {
    public String tenban;
    public int maban;
    public int suchua;

    public Ban(int maban,String tenban, int suchua) {
        this.tenban = tenban;
        this.maban = maban;
        this.suchua = suchua;
    }

    public String getTenban() {
        return tenban;
    }

    public void setTenban(String tenban) {
        this.tenban = tenban;
    }

    public int getMaban() {
        return maban;
    }

    public void setMaban(int maban) {
        this.maban = maban;
    }

    public int getSuchua() {
        return suchua;
    }

    public void setSuchua(int suchua) {
        this.suchua = suchua;
    }
}
