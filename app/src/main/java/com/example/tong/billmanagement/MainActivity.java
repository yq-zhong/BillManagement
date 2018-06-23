package com.example.tong.billmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    //控件
    private EditText et_year;
    private EditText et_month;
    private EditText et_day;
    private Spinner spin_type;
    private Button btn_select_date;
    private Button btn_select_type;
    private Button btn_select;
    private RecyclerView rv_bill;
    private Button btn_go_insert;

    private ArrayAdapter spin_adapter;
    private BillAdapter billAdapter;

    //对数据库进行操作的工具
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    //存放查询结果
    private List<Bill_Item> billList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_year = (EditText) findViewById(R.id.et_year);
        et_month = (EditText) findViewById(R.id.et_month);
        et_day = (EditText) findViewById(R.id.et_day);
        spin_type = (Spinner) findViewById(R.id.spin_type);
        btn_select_date = (Button) findViewById(R.id.btn_select_date);
        btn_select_type = (Button) findViewById(R.id.btn_select_type);
        btn_select = (Button) findViewById(R.id.btn_select);
        rv_bill = (RecyclerView) findViewById(R.id.rv_bill);
        btn_go_insert = (Button) findViewById(R.id.btn_go_insert);

        spin_adapter = ArrayAdapter.createFromResource(this, R.array.types_select, android.R.layout.simple_spinner_item);
        spin_type.setAdapter(spin_adapter);

        btn_select_date.setOnClickListener(this);
        btn_select_type.setOnClickListener(this);
        btn_select.setOnClickListener(this);
        btn_go_insert.setOnClickListener(this);

        selectAll();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_select_date:
            {
                selectByDate();
                break;
            }
            case R.id.btn_select_type:
            {
                selectByType();
                break;
            }
            case R.id.btn_select:
            {
                selectAll();
                break;
            }
            case R.id.btn_go_insert:
            {
                Intent intent_go_insert = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent_go_insert);
                break;
            }
            default:
            {
                break;
            }
        }
    }

    private void selectByDate()
    {
        String year = et_year.getText().toString();
        String month = et_month.getText().toString();
        String day = et_day.getText().toString();
        dbHelper = new DBHelper(this, "BillManagement", null, 1);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        if(year.equals(""))  //查询所有数据
        {
            cursor = db.query("bill", null, null, null, null, null, null);
        }
        else if(month.equals(""))  //按年查
        {
            cursor = db.query("bill", null,
                    "year = ?",
                     new String[] {year},
                    null, null, null);
        }
        else if(day.equals("")) //按月查
        {
            cursor = db.query("bill", null,
                    "year = ? and month = ?",
                     new String[] {year, month},
                    null, null, null);
        }
        else //按日查
        {
            cursor = db.query("bill", null,
                    "year = ? and month = ? and day = ?",
                     new String[] {year, month, day},
                    null, null, null);
        }
        initBill(cursor);
        showBill();
        db.close();
    }

    private void selectByType()
    {
        String type = spin_type.getSelectedItem().toString();
        dbHelper = new DBHelper(this, "BillManagement", null, 1);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        if(type.equals("全部"))
        {
            cursor = db.query("bill", null, null, null, null, null, null);
        }
        else
        {
            cursor = db.query("bill", null, "type = ?", new String[] {type}, null, null, null);
        }
        initBill(cursor);
        showBill();
        db.close();
    }

    private void selectAll()
    {
        dbHelper = new DBHelper(this, "BillManagement", null, 1);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Bill", null, null, null, null, null, null);
        initBill(cursor);
        showBill();
        db.close();
    }

    private void initBill(Cursor cursor)
    {
        String id;
        String year;
        String month;
        String day;
        String coast;
        String type;
        String content;
        Bill_Item bill_item;
        billList.clear();
        if(cursor.moveToFirst())
        {
            do
            {
                id = cursor.getString(cursor.getColumnIndex("id"));
                year = cursor.getString(cursor.getColumnIndex("year"));
                month = cursor.getString(cursor.getColumnIndex("month"));
                day = cursor.getString(cursor.getColumnIndex("day"));
                coast = cursor.getString(cursor.getColumnIndex("coast"));
                type = cursor.getString(cursor.getColumnIndex("type"));
                content = cursor.getString(cursor.getColumnIndex("content"));
                bill_item = new Bill_Item(id, year, month, day, coast, type, content);
                billList.add(bill_item);
            }while (cursor.moveToNext());
        }
        else
        {
            Toast toast = Toast.makeText(MainActivity.this, "没有符合条件的记录!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        cursor.close();
    }

    private void showBill()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_bill.setLayoutManager(layoutManager);
        billAdapter = new BillAdapter(billList, this);
        billAdapter.setCallback(adapterCallback);
        rv_bill.setAdapter(billAdapter);
    }

    //****************适配器回掉函数**********************
    BillAdapter.AdapterCallback adapterCallback = new BillAdapter.AdapterCallback()
    {
        @Override
        public void callBack(View view, int position)
        {
            switch (view.getId())
            {
                case R.id.btn_delete:
                {
                    Bill_Item bill_item = billList.get(position);
                    String id = bill_item.getId();
                    deleteItem(id);
                    Toast toast = Toast.makeText(MainActivity.this, "删除成功，请重新查询以刷新页面", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                }
                case R.id.btn_update:
                {
                    Bill_Item bill_item = billList.get(position);
                    Intent intent_go_update = new Intent(MainActivity.this, UpdateActivity.class);
                    intent_go_update.putExtra("bill_item", bill_item);
                    startActivity(intent_go_update);
                    break;
                }
                default:
                {
                    break;
                }
            }
        }
    };

    private void deleteItem(String id)
    {
        DBHelper dbHelper;
        dbHelper = new DBHelper(this, "BillManagement", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("bill", "id = ?", new String[]{id});
    }
}
