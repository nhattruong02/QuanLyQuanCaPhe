package com.example.doan2.DAO;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan2.Adapter.BanAdapter;
import com.example.doan2.Adapter.ThucdonAdapter;
import com.example.doan2.DTO.Ban;
import com.example.doan2.DTO.Khachhang;
import com.example.doan2.DTO.Nhanvien;
import com.example.doan2.DTO.Thucdon;
import com.example.doan2.Database.DbHelper;
import com.example.doan2.MainActivity;
import com.example.doan2.R;

import java.util.ArrayList;

public class DSBan extends AppCompatActivity {
    DbHelper database;
    Button btnthem, btnhuy, btnsua, btnhuysua;
    EditText edttenban, edtsucchua,timkiemban;
    GridView gvban;
    BanAdapter adapter;
    ArrayList<Ban> arrban;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ban);
        gvban = (GridView) findViewById(R.id.gvban);
        arrban = new ArrayList<>();
        adapter = new BanAdapter(this, R.layout.dong_ban, arrban);
        gvban.setAdapter(adapter);
        database = new DbHelper(this, "qlqcaphe.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Ban(maban INTEGER PRIMARY KEY AUTOINCREMENT ,tenban NVARCHAR(30),succhua INT)");
        getDataban();
        registerForContextMenu(gvban);
        timkiemban = findViewById(R.id.timkiemban);
        timkiemban.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }
    private void filter(String text){
        ArrayList<Ban> filteredList= new ArrayList<>();
        for (Ban b : arrban){
            if(b.getTenban().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(b);
            }
        }
        adapter.filterList(filteredList);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
            case R.id.sua:
                Ban bsua = arrban.get(index);
                String tenbsua = bsua.getTenban();
                int mabsua = bsua.getMaban();
                DialogSua(tenbsua, mabsua);
                return true;
            case R.id.xoa:
                Ban bxoa = arrban.get(index);
                String tenbxua = bxoa.getTenban();
                int mabxoa = bxoa.getMaban();
                DialogXoa(tenbxua, mabxoa);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void getDataban() {
        Cursor dlban = database.Getdata("SELECT * FROM Ban");
        arrban.clear();
        while (dlban.moveToNext()) {
            int maban = dlban.getInt(0);
            String tenban = dlban.getString(1);
            int succhua = dlban.getInt(2);
            arrban.add(new Ban(maban, tenban, succhua));
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.them) {
            Dialogthem();
        }
        return super.onOptionsItemSelected(item);
    }

    public void DialogXoa(String tenban, int maban) {
        AlertDialog.Builder dialogxoa = new AlertDialog.Builder(this);
        dialogxoa.setMessage("Bạn có muốn xoá bàn " + tenban + " này không?");
        dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM Ban WHERE maban ='" + maban + "'");
                Toast.makeText(DSBan.this, "Đã xoá bàn " + tenban, Toast.LENGTH_SHORT).show();
                getDataban();
            }
        });
        dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogxoa.show();
    }

    public void DialogSua(String tenban, int maban) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.suaban);
        btnsua = (Button) dialog.findViewById(R.id.suaban);
        btnhuysua = (Button) dialog.findViewById(R.id.huysuaban);
        edttenban = (EditText) dialog.findViewById(R.id.tenbansua);
        edttenban.setText(tenban);
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenbanmoi = edttenban.getText().toString();
                if (tenban.equals("")) {
                    Toast.makeText(DSBan.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("UPDATE Ban SET tenban ='" + tenbanmoi + "' WHERE maban = '" + maban + "'");
                    Toast.makeText(DSBan.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataban();
                }
            }
        });
        btnhuysua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void Dialogthem() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.themban);
        btnthem = (Button) dialog.findViewById(R.id.themban);
        btnhuy = (Button) dialog.findViewById(R.id.huyban);
        edttenban = (EditText) dialog.findViewById(R.id.tenban);
        edtsucchua = (EditText) dialog.findViewById(R.id.succhuaban);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tenban = edttenban.getText().toString();
                if (tenban.equals("")) {
                    Toast.makeText(DSBan.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                try {
                    int succhuaban = Integer.parseInt(edtsucchua.getText().toString());
                    if (succhuaban < 0) {
                        Toast.makeText(DSBan.this, "Sức chứa phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    } else {
                        database.QueryData("INSERT INTO Ban VALUES(null,'" + tenban + "'," + succhuaban + ")");
                        Toast.makeText(DSBan.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        getDataban();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(DSBan.this, "Hãy nhập sức chứa là số!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}

