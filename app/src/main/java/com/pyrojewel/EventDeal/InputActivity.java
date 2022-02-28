package com.pyrojewel.EventDeal;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.base.app.BaseActivity;
import com.example.common.bean.EventSet;
import com.example.common.bean.Schedule;
import com.example.common.listener.OnTaskFinishedListener;
import com.example.common.util.ToastUtils;
import com.example.myapplication.R;
import com.pyrojewel.MainActivity;
import com.pyrojewel.dialog.InputLocationDialog;
import com.pyrojewel.dialog.SelectDateDialog;
import com.pyrojewel.dialog.SelectEventSetDialog;
import com.pyrojewel.task.eventset.LoadEventSetMapTask;
import com.pyrojewel.task.schedule.UpdateScheduleTask;
import com.pyrojewel.utils.DateUtils;
import com.pyrojewel.utils.JeekUtils;

import java.util.HashMap;
import java.util.Map;

public class InputActivity extends BaseActivity implements View.OnClickListener {

    public static int UPDATE_SCHEDULE_CANCEL = 1;
    public static int UPDATE_SCHEDULE_FINISH = 2;
    public static String SCHEDULE_OBJ = "schedule.obj";
    public static String CALENDAR_POSITION = "calendar.position";

    private View vScheduleColor;
    private EditText etScheduleTitle, etScheduleDesc;
    private ImageView ivScheduleEventSetIcon;
    private TextView tvScheduleEventSet, tvScheduleTime, tvScheduleLocation;
    private SelectEventSetDialog mSelectEventSetDialog;
    private SelectDateDialog mSelectDateDialog;
    private InputLocationDialog mInputLocationDialog;

    private Map<Integer, EventSet> mEventSetsMap;
    private Schedule mSchedule;
    private int mPosition = -1;

    private int mColor = 0;

    @Override
    protected void bindView() {

        setContentView(R.layout.layout_input);
        TextView tvTitle = searchViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.schedule_event_detail_setting));
        searchViewById(R.id.tvCancel).setOnClickListener(this);
        searchViewById(R.id.tvFinish).setOnClickListener(this);
        searchViewById(R.id.llScheduleEventSet).setOnClickListener(this);
        searchViewById(R.id.llScheduleTime).setOnClickListener(this);
        searchViewById(R.id.llScheduleLocation).setOnClickListener(this);
        vScheduleColor = searchViewById(R.id.vScheduleColor);
        ivScheduleEventSetIcon = searchViewById(R.id.ivScheduleEventSetIcon);
        etScheduleTitle = searchViewById(R.id.etScheduleTitle);
        etScheduleDesc = searchViewById(R.id.etScheduleDesc);
        tvScheduleEventSet = searchViewById(R.id.tvScheduleEventSet);
        tvScheduleTime = searchViewById(R.id.tvScheduleTime);
        tvScheduleLocation = searchViewById(R.id.tvScheduleLocation);    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                setResult(UPDATE_SCHEDULE_CANCEL);
                finish();
                break;
            case R.id.tvFinish:
                confirm();
                break;
        }
    }
    private void confirm() {
        if (etScheduleTitle.getText().length() != 0) {
            mSchedule.setTitle(etScheduleTitle.getText().toString());
            mSchedule.setDesc(etScheduleDesc.getText().toString());
            new UpdateScheduleTask(this, new OnTaskFinishedListener<Boolean>() {
                @Override
                public void onTaskFinished(Boolean data) {
                    setResult(UPDATE_SCHEDULE_FINISH);
                    finish();
                }
            }, mSchedule).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            ToastUtils.showShortToast(this, R.string.schedule_input_content_is_no_null);
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    private void showSelectEventSetDialog() {
        if (mSelectEventSetDialog == null) {
            mSelectEventSetDialog = new SelectEventSetDialog(this, (SelectEventSetDialog.OnSelectEventSetListener) this, mSchedule.getEventSetId());
        }
        mSelectEventSetDialog.show();
    }
    private void showSelectDateDialog() {
        if (mSelectDateDialog == null) {
            mSelectDateDialog = new SelectDateDialog(this, (SelectDateDialog.OnSelectDateListener) this, mSchedule.getYear(), mSchedule.getMonth(), mSchedule.getDay(), mPosition);
        }
        mSelectDateDialog.show();
    }

    private void showInputLocationDialog() {
        if (mInputLocationDialog == null) {
            mInputLocationDialog = new InputLocationDialog(this, (InputLocationDialog.OnLocationBackListener) this);
        }
        mInputLocationDialog.show();
    }




}
