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

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText et_year;
    private EditText et_month;
    private EditText et_day;
    private EditText et_coast;
    private Spinner spin_type;
    private EditText et_content;
    private Button btn_go_main;
    private Button btn_update;

    private ArrayAdapter spin_adapter;

    //存入数据库的各个列
    private String id;
    private String year;
    private String month;
    private String day;
    private String coast;
    private String type;
    private String content;

    //Intent intent_go_main = new Intent(UpdateActivity.this, MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        et_year = (EditText) findViewById(R.id.et_year);
        et_month = (EditText) findViewById(R.id.et_month);
        et_day = (EditText) findViewById(R.id.et_day);
        et_coast = (EditText) findViewById(R.id.et_coast);
        spin_type = (Spinner) findViewById(R.id.spin_type);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_go_main = (Button) findViewById(R.id.btn_go_main);
        btn_update = (Button) findViewById(R.id.btn_update);

        spin_adapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        spin_type.setAdapter(spin_adapter);

        btn_go_main.setOnClickListener(this);
        btn_update.setOnClickListener(this);

        Intent intent_from_main = getIntent();
        Bill_Item bill_item = (Bill_Item)intent_from_main.getSerializableExtra("bill_item");
        setValues(bill_item);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_go_main:
            {
                Intent intent_go_main = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent_go_main);
                break;
            }
            case R.id.btn_update:
            {
                updateData();
                Toast toast = Toast.makeText(UpdateActivity.this, "修改成功! 请重新查询以刷新", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent_go_main = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent_go_main);
                break;
            }
            default:
            {
                break;
            }
        }
    }

    private void setValues(Bill_Item bill_item)
    {
        id = bill_item.getId();
        et_year.setText(bill_item.getYear());
        et_month.setText(bill_item.getMonth());
        et_day.setText(bill_item.getDay());
        et_coast.setText(bill_item.getCoast());
        et_content.setText(bill_item.getContent());
        int k = spin_adapter.getCount();
        for (int i = 0; i < k; i++)
        {
            if(bill_item.getType().equals(spin_adapter.getItem(i).toString()))
            {
                spin_type.setSelection(i);
                break;
            }
        }
    }

    private void updateData()
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
        db.update("bill", values, "id = ?", new String[]{id});
    }
}
