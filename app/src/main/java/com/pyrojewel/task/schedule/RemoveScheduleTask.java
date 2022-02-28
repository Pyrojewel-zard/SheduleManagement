package com.pyrojewel.task.schedule;

import android.content.Context;

import com.example.common.base.task.BaseAsyncTask;
import com.example.common.data.ScheduleDao;
import com.example.common.listener.OnTaskFinishedListener;

/**
 * Created by example on 2016/10/11 0011.
 */
public class RemoveScheduleTask extends BaseAsyncTask<Boolean> {

    private long mId;

    public RemoveScheduleTask(Context context, OnTaskFinishedListener<Boolean> onTaskFinishedListener, long id) {
        super(context, onTaskFinishedListener);
        mId = id;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ScheduleDao dao = ScheduleDao.getInstance(mContext);
        return dao.removeSchedule(mId);
    }
}
