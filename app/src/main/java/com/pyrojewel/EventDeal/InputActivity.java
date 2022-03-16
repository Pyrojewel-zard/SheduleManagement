package com.pyrojewel.EventDeal;



import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.bean.Schedule;
import com.example.common.data.ScheduleDao;
import com.example.common.listener.OnTaskFinishedListener;
import com.example.myapplication.R;
import com.pyrojewel.MainActivity;
import com.pyrojewel.schedule.AddScheduleTask;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class InputActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText nameEt;
    private EditText DDLDate;
    private RatingBar diffLevel;
    private EditText downTime;
    private EditText upTime;
    private TextView cancel;
    private TextView confirm;

    private String mName;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mLevel;
    private int mFinished;
    private int mDurationUp;
    private int mDurationDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_input);
        nameEt= findViewById(R.id.name);
        DDLDate = findViewById(R.id.editDDLDate);
        diffLevel= findViewById(R.id.diffLevel);
        downTime= findViewById(R.id.downTime);
        upTime= findViewById(R.id.Uptime);
        cancel= findViewById(R.id.tvCancel);
        confirm= findViewById(R.id.tvConfirm);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        DDLDate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        DDLDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

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
                InputActivity.this.DDLDate.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                mYear=year;
                mMonth=monthOfYear;
                mDay=dayOfMonth;
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    @Override
    public void onClick(View view){
        Intent intent=new Intent(this, MainActivity.class);
        switch(view.getId()){
            case R.id.tvCancel:

                startActivity(intent);
                break;
            case R.id.tvConfirm:
                addSchedule();
                    startActivity(intent);
                }
        }
    private void addSchedule(){
        if(nameEt.getText().toString().equals(""))
            Toast.makeText(this, "未输入项目名称", Toast.LENGTH_LONG).show();
        else if(DDLDate.getText().toString().equals(""))
            Toast.makeText(this, "未输入DDL截止日期", Toast.LENGTH_LONG).show();
        else if(diffLevel.getRating()==0)
            Toast.makeText(this, "未输入难度评级", Toast.LENGTH_LONG).show();
            //Toast.makeText(this, valueOf((int)diffLevel.getRating()), Toast.LENGTH_LONG).show();
        else if(downTime.getText().toString()=="")
            Toast.makeText(this, "未输入完成预期下限时间", Toast.LENGTH_LONG).show();
        else if(upTime.getText().toString()=="")
            Toast.makeText(this, "未输入完成预期上限时间", Toast.LENGTH_LONG).show();
        else {
            Schedule schedule=new Schedule();
            Toast.makeText(this, mYear+"-"+mMonth, Toast.LENGTH_LONG).show();
            schedule.setName(nameEt.getText().toString());
            schedule.setYear(mYear);
            schedule.setMonth(mMonth);
            schedule.setDay(mDay);
            schedule.setFinish(0);
            schedule.setDiffLevel((int)diffLevel.getRating());
            schedule.setFinishDurationUp(Integer.parseInt(upTime.getText().toString()));
            schedule.setFinishDurationDown(Integer.parseInt(downTime.getText().toString()));

            ScheduleDao scheduleDao=new ScheduleDao(this);
            long id=scheduleDao.addSchedule(schedule);
            System.out.println(id);
        }

    }
}
