package com.example.tong.billmanagement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener
{
    //控件
    private EditText et_year;
    private EditText et_month;
    private EditText et_day;
    private EditText et_coast;
    private Spinner spin_type;
    private EditText et_content;
    private Button btn_go_main;
    private Button btn_insert;

    private ArrayAdapter spin_adapter;

    //存入数据库的各个列
    private String year;
    private String month;
    private String day;
    private String coast;
    private String type;
    private String content;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        et_year = (EditText) findViewById(R.id.et_year);
        et_month = (EditText) findViewById(R.id.et_month);
        et_day = (EditText) findViewById(R.id.et_day);
        et_coast = (EditText) findViewById(R.id.et_coast);
        spin_type = (Spinner) findViewById(R.id.spin_type);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_go_main = (Button) findViewById(R.id.btn_go_main);
        btn_insert = (Button) findViewById(R.id.btn_insert);

        spin_adapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        spin_type.setAdapter(spin_adapter);

        btn_go_main.setOnClickListener(this);
        btn_insert.setOnClickListener(this);


    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_go_main:
            {
                Intent intent_go_main = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent_go_main);
                break;
            }
            case R.id.btn_insert:
            {
                insertData();
                clearInput();
                Toast toast = Toast.makeText(InsertActivity.this, "插入成功！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
            }
            default:
            {
                break;
            }
        }
    }

    private void insertData()
    {
        year = et_year.getText().toString();
        month = et_month.getText().toString();
        day = et_day.getText().toString();
        coast = et_coast.getText().toString();
        type = spin_type.getSelectedItem().toString();
        content = et_content.getText().toString();
        DBHelper dbHelper;
        dbHelper = new DBHelper(this, "BillManagement", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("year", year);
        values.put("month", month);
        values.put("day", day);
        values.put("coast", coast);
        values.put("type", type);
        values.put("content", content);
        db.insert("Bill", null, values);
        //db.execSQL("insert into bill(year, month, day, coast, content, type) values(year, month, day, coast, content, type)");
    }

    private void clearInput()
    {
        et_year.setText("");
        et_month.setText("");
        et_day.setText("");
        et_coast.setText("");
        et_content.setText("");
    }
}
