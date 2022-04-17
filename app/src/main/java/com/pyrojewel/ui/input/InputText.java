package com.pyrojewel.ui.input;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.common.bean.Schedule;
import com.example.common.data.ScheduleDao;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentInputTextBinding;
import com.pyrojewel.MainActivity;

import java.util.Calendar;


/**
 * @author Pyrojewel
 */
public class InputText extends Fragment {
    private SeekBar sbNormal;
    private SeekBar exTime;
    private TextView txtDiff,txtTime;
    private EditText mContext;
    private EditText nameEt;
    private EditText DDLDate;
    private TextView cancel;
    private TextView confirm;

    private String mName;
    private int mYear;
    private int mMonth;
    private int mDay;

    private FragmentInputTextBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInputTextBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameEt= getActivity().findViewById(R.id.name);
        txtDiff =getActivity().findViewById(R.id.difftxt);
        DDLDate = getActivity().findViewById(R.id.editDDLDate);
        sbNormal = getActivity().findViewById(R.id.sbNormal);
        exTime=getActivity().findViewById(R.id.exTime);
        txtTime=getActivity().findViewById(R.id.time_txt);
        cancel= getActivity().findViewById(R.id.tvCancel);
        confirm= getActivity().findViewById(R.id.tvConfirm);
        mContext=getActivity().findViewById(R.id.mContext_text);
        cancel.setOnClickListener(this::onClick);
        confirm.setOnClickListener(this::onClick);
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
            Intent intent = getActivity().getIntent();
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

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

    public void onClick(View view){
        Intent intent=new Intent(getActivity(), MainActivity.class);
        switch(view.getId()){
            case R.id.tvCancel:

                startActivity(intent);
                break;
            case R.id.tvConfirm:
                if(addSchedule()){
                startActivity(intent);
        }
        }
    }
    private boolean addSchedule(){
        if(nameEt.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "未输入项目名称", Toast.LENGTH_LONG).show();
            return false;
        } else if(DDLDate.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "未输入DDL截止日期", Toast.LENGTH_LONG).show();
            return false;
        } else if(sbNormal.getProgress()==0) {
            Toast.makeText(getActivity(), "未输入难度评级", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(exTime.getProgress()==0) {
            Toast.makeText(getActivity(), "未输入完成预期时间", Toast.LENGTH_LONG).show();
            return false;
        } else {
            Schedule schedule=new Schedule();
            schedule.setName(nameEt.getText().toString());
            schedule.setYear(mYear);
            schedule.setMonth(mMonth);
            schedule.setDay(mDay);
            schedule.setFinish(0);
            schedule.setDiffLevel((int)sbNormal.getProgress());
            schedule.setFinishtime((int)exTime.getProgress());
            ScheduleDao scheduleDao=new ScheduleDao(getActivity());
            long id=scheduleDao.addSchedule(schedule);
            System.out.println(id);
            return true;
        }
    }

}