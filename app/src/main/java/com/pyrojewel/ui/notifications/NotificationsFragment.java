package com.pyrojewel.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.common.bean.Schedule;
import com.example.common.data.ScheduleDao;
import com.example.diary.OnCalendarClickListener;
import com.example.diary.schedule.ScheduleLayout;
import com.example.diary.schedule.ScheduleRecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNotificationsBinding;
import com.pyrojewel.adapter.ScheduleAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Pyrojewel
 */
public class NotificationsFragment extends Fragment {

    private ScheduleLayout slSchedule;
    private ScheduleRecyclerView rvScheduleList;
    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private LinearLayout llTitleDate;
    private TextView tvTitleMonth, tvTitleDay;
    private ScheduleAdapter mAdapter;
    private ArrayList<Schedule> mSchedules;
    private String[] mMonthText;
    private String[] mDayText;
    private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi();
        rvScheduleList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvScheduleList.setAdapter(mAdapter);
        slSchedule.setOnCalendarClickListener(new OnCalendarClickListener() {
            @Override
            public void onClickDate(int year, int month, int day) {
                mCurrentSelectYear=year;
                mCurrentSelectMonth=month;
                mCurrentSelectDay=day-1;
                tvTitleMonth.setText(mMonthText[mCurrentSelectMonth]);
                tvTitleDay.setText(mDayText[mCurrentSelectDay]);
                taskShow();
            }
        });
    }
    public void initUi(){
        slSchedule = getActivity().findViewById(R.id.slSchedule);
        rvScheduleList =getActivity().findViewById(R.id.rvScheduleList);
        llTitleDate = getActivity().findViewById(R.id.llTitleDate);
        tvTitleMonth = getActivity().findViewById(R.id.tvTitleMonth);
        tvTitleDay = getActivity().findViewById(R.id.tvTitleDay);
        slSchedule=getActivity().findViewById(R.id.slSchedule);
        mMonthText = getResources().getStringArray(R.array.calendar_month);
        mDayText = getResources().getStringArray(R.array.calendar_day);
        llTitleDate.setVisibility(View.VISIBLE);
        llTitleDate.setVisibility(View.VISIBLE);
        tvTitleMonth.setText(mMonthText[Calendar.getInstance().get(Calendar.MONTH)]);
        tvTitleDay.setText(getString(R.string.calendar_today));
        mSchedules = (ArrayList<Schedule>) ScheduleDao.getInstance(getActivity()).getScheduleByDate(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        mAdapter = new ScheduleAdapter(getActivity(), mSchedules);
    }
/**
* 进行数据库的交互，获得对应的Month和day的任务，再进行id排序进行显示
* 这就涉及到了游标是吧，其实还有一个中文输入的问题没有解决
* */
    public void taskShow(){
        mSchedules = (ArrayList<Schedule>) ScheduleDao.getInstance(getActivity()).getScheduleByDate(mCurrentSelectYear,mCurrentSelectMonth,mCurrentSelectDay+1);
        mAdapter = new ScheduleAdapter(getActivity(), mSchedules);
        rvScheduleList.setAdapter(mAdapter);
    }
    /**获取数据库的相关内容*/
       @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
