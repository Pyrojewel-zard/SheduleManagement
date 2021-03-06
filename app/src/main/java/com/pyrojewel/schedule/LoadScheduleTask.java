package com.pyrojewel.schedule;

import android.content.Context;

import com.example.common.base.task.BaseAsyncTask;
import com.example.common.bean.Schedule;
import com.example.common.data.ScheduleDao;
import com.example.common.listener.OnTaskFinishedListener;

import java.util.List;


/**
 * Created by Jimmy on 2016/10/11 0011.
 */
public class LoadScheduleTask extends BaseAsyncTask<List<Schedule>> {

    private int mYear;
    private int mMonth;
    private int mDay;

    public LoadScheduleTask(Context context, OnTaskFinishedListener<List<Schedule>> onTaskFinishedListener, int year, int month, int day) {
        super(context, onTaskFinishedListener);
        mYear = year;
        mMonth = month;
        mDay = day;
    }

    @Override
    protected List<Schedule> doInBackground(Void... params) {
        ScheduleDao dao = ScheduleDao.getInstance(mContext);
        return dao.getScheduleByDate(mYear, mMonth,mDay);
    }
}
