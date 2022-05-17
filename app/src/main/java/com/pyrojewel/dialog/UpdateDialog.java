package com.pyrojewel.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.common.bean.Schedule;
import com.example.common.data.ScheduleDao;
import com.example.myapplication.R;

import java.util.Calendar;

public class UpdateDialog extends Dialog {
    /**弹框需要展示的信息*/
    public Schedule mSchedule;
    private SeekBar sbNormal;
    private SeekBar exTime;
    private TextView txtDiff,txtTime;
    private EditText nameEt;
    private EditText DDLDate;
    private Button mButtonCancel;
    private Button mButtonUpdate;
    private Button mButtonDelete;

    private String mName;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Context mContext;

    public UpdateDialog(@NonNull Context context,Schedule schedule) {
        super(context);
        this.mSchedule = schedule;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_dialog_layout);

        initView();

               //删除按钮操作
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleDao.getInstance(mContext).removeSchedule(mSchedule);
                dismiss();
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEt.getText().toString();

                Schedule schedule = new Schedule();
                ScheduleDao.getInstance(mContext).updateSchedule(schedule);
                dismiss();
            }
        });

        setCanceledOnTouchOutside(false);
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
        try {
            Intent intent = getOwnerActivity().getIntent();
            Bundle bundle=intent.getExtras();
            String time=bundle.getString("time");
            String task=bundle.getString("task");
            nameEt.setText(task);
            DDLDate.setText(time);
        }catch (Exception e){
            System.out.println(e);
        }
        sbNormal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtDiff.setText("任务难度:" + progress + " 星");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        exTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtTime.setText("预计完成时间:" + progress + " （h）");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                DDLDate.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                mYear=year;
                mMonth=monthOfYear;
                mDay=dayOfMonth;
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    //初始化界面
    private void initView(){
        nameEt= findViewById(R.id.updatename);
        txtDiff =findViewById(R.id.updatedifftxt);
        DDLDate = findViewById(R.id.updateeditDDLDate);
        sbNormal = findViewById(R.id.updatesbNormal);
        exTime=findViewById(R.id.updateexTime);
        txtTime=findViewById(R.id.updatetime_txt);
        mButtonCancel= findViewById(R.id.CancelOption);
        mButtonUpdate = findViewById(R.id.tvUpate);
        mButtonDelete = findViewById(R.id.tvDelete);
    }
}

