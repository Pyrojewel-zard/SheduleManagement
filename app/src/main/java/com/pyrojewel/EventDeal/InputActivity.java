package com.pyrojewel.EventDeal;



import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.R;
import com.pyrojewel.MainActivity;
import com.pyrojewel.Model.DBOpenHelper;
import com.pyrojewel.Model.DatabaseModel;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class InputActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText nameEt;
    private EditText mDDLDate;
    private RatingBar diffLevel;
    private EditText downTime;
    private EditText upTime;
    private TextView cancel;
    private TextView confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_input);
        nameEt= findViewById(R.id.name);
        mDDLDate = findViewById(R.id.editDDLDate);
        diffLevel= findViewById(R.id.diffLevel);
        downTime= findViewById(R.id.downTime);
        upTime= findViewById(R.id.Uptime);
        cancel= findViewById(R.id.tvCancel);
        confirm= findViewById(R.id.tvConfirm);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        mDDLDate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        mDDLDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });
    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(InputActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                InputActivity.this.mDDLDate.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.tvCancel:
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.tvConfirm:
                if(nameEt.getText().toString().equals(""))
                    Toast.makeText(this, "未输入项目名称", Toast.LENGTH_LONG).show();
                else if(mDDLDate.getText().toString().equals(""))
                    Toast.makeText(this, "未输入DDL截止日期", Toast.LENGTH_LONG).show();
                else if(diffLevel.getRating()==0)
                    Toast.makeText(this, "未输入难度评级", Toast.LENGTH_LONG).show();
                    //Toast.makeText(this, valueOf((int)diffLevel.getRating()), Toast.LENGTH_LONG).show();
                else if(downTime.getText().toString()=="")
                    Toast.makeText(this, "未输入完成预期下限时间", Toast.LENGTH_LONG).show();
                else if(upTime.getText().toString()=="")
                    Toast.makeText(this, "未输入完成预期上限时间", Toast.LENGTH_LONG).show();
                else {
                    DBOpenHelper dbHelper = new DBOpenHelper(this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values=new ContentValues();
                    values.put(DatabaseModel.COLUMN_NAME,nameEt.getText().toString());
                    values.put(DatabaseModel.COLUMN_DDL,mDDLDate.getText().toString());
                    values.put(DatabaseModel.COLUMN_LEVEL,(int)diffLevel.getRating());
                    values.put(DatabaseModel.COLUMN_FINISH,false);
                    values.put(DatabaseModel.COLUMN_DURATIOND,Integer.parseInt(downTime.getText().toString()));
                    values.put(DatabaseModel.COLUMN_DURATIONU,Integer.parseInt(upTime.getText().toString()));
                    long newRowId = db.insert(DatabaseModel.TABLE_NAME, null, values);
                    System.out.println(newRowId);
                }
        }
    }
}
