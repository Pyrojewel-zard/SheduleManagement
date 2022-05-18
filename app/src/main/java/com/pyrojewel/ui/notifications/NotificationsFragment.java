package com.pyrojewel.ui.notifications;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.common.base.app.BaseFragment;
import com.example.common.bean.Schedule;
import com.example.common.listener.OnTaskFinishedListener;
import com.example.diary.OnCalendarClickListener;
import com.example.diary.schedule.ScheduleLayout;
import com.example.diary.schedule.ScheduleRecyclerView;
import com.example.myapplication.R;
import com.pyrojewel.adapter.ScheduleAdapter;
import com.pyrojewel.schedule.LoadScheduleTask;

import java.util.Calendar;
import java.util.List;

/**
 * @author Pyrojewel
 */
public class NotificationsFragment extends BaseFragment implements OnCalendarClickListener, View.OnClickListener,
            OnTaskFinishedListener<List<Schedule>>{

        private ScheduleLayout slSchedule;
        private ScheduleRecyclerView rvScheduleList;
        private EditText etInputContent;
        private RelativeLayout rLNoTask;
        private LinearLayout llTitleDate;
        private TextView tvTitleMonth, tvTitleDay;
        private ScheduleAdapter mScheduleAdapter;
        private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;
        private long mTime;
        private String[] mMonthText;
        private String[] mDayText;
        public static NotificationsFragment getInstance() {
            return new NotificationsFragment();
        }

        @Nullable
        @Override
        protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
            return inflater.inflate(R.layout.fragment_notifications, container, false);
        }

        @Override
        protected void bindView() {
            slSchedule = searchViewById(R.id.slSchedule);
            rvScheduleList =searchViewById(R.id.rvScheduleList);
            llTitleDate = getActivity().findViewById(R.id.llTitleDate);
            tvTitleMonth = getActivity().findViewById(R.id.tvTitleMonth);
            tvTitleDay = getActivity().findViewById(R.id.tvTitleDay);
            slSchedule=getActivity().findViewById(R.id.slSchedule);
            mMonthText = getResources().getStringArray(R.array.calendar_month);
            mDayText = getResources().getStringArray(R.array.calendar_day);
            rLNoTask = searchViewById(R.id.rlNoTask);
            slSchedule.setOnCalendarClickListener(this);
            //显示当前日月title
            llTitleDate.setVisibility(View.VISIBLE);
            tvTitleMonth.setText(mMonthText[Calendar.getInstance().get(Calendar.MONTH)]);
            tvTitleDay.setText(getString(R.string.calendar_today));
            initScheduleList();
        }

        @Override
        protected void initData() {
            super.initData();
        }

        @Override
        protected void bindData() {
            super.bindData();
            resetScheduleList();
        }

        public void resetScheduleList() {
            new LoadScheduleTask(mActivity, this,
                    mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }



        @Override
        public void onClickDate(int year, int month, int day) {
            setCurrentSelectDate(year, month, day);
            resetScheduleList();
        }


        private void initScheduleList() {
            rvScheduleList = slSchedule.getSchedulerRecyclerView();
            LinearLayoutManager manager = new LinearLayoutManager(mActivity);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rvScheduleList.setLayoutManager(manager);
            DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setSupportsChangeAnimations(false);
            rvScheduleList.setItemAnimator(itemAnimator);
            mScheduleAdapter = new ScheduleAdapter(mActivity, this);
            rvScheduleList.setAdapter(mScheduleAdapter);
        }

        private void setCurrentSelectDate(int year, int month, int day) {
            mCurrentSelectYear = year;
            mCurrentSelectMonth = month;
            mCurrentSelectDay = day;
            tvTitleMonth.setText(mMonthText[mCurrentSelectMonth]);
            tvTitleDay.setText(mDayText[mCurrentSelectDay]);

        }


        @Override
        public void onTaskFinished(List<Schedule> data) {
            mScheduleAdapter.changeAllData(data);
            rLNoTask.setVisibility(data.size() == 0 ? View.VISIBLE : View.GONE);
            updateTaskHintUi(data.size());
        }

        private void updateTaskHintUi(int size) {
            if (size == 0) {
                slSchedule.removeTaskHint(mCurrentSelectDay);
            } else {
                slSchedule.addTaskHint(mCurrentSelectDay);
            }
        }


        public int getCurrentCalendarPosition() {
            return slSchedule.getMonthCalendar().getCurrentItem();
        }

    @Override
    public void onClick(View v) {

    }
}
