package com.example.doan2.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor Getdata(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
    public void themthucdon(String tenhh, String loaihh, String tinhtrang, double gia, byte[] hinh){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Thucdon VALUES (null,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,tenhh);
        statement.bindString(2,loaihh);
        statement.bindString(3,tinhtrang);
        statement.bindDouble(4,gia);
        statement.bindBlob(5,hinh);
        statement.executeInsert();

    }
    public void suathucdon(String tenhh, String loaihh, String tinhtrang, double gia, byte[] hinh ,int mahh1) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE Thucdon SET ten = ?, loai=?,tinhtrang =?,gia =?,hinhanh=? WHERE mahh = '"+mahh1+"'";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, tenhh);
        statement.bindString(2, loaihh);
        statement.bindString(3, tinhtrang);
        statement.bindDouble(4, gia);
        statement.bindBlob(5, hinh);
        statement.executeUpdateDelete();
    }
}
