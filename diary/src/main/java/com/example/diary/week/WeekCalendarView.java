package com.example.diary.week;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;

import androidx.viewpager.widget.ViewPager;

import com.example.diary.OnCalendarClickListener;
import com.example.diary.R;

public class WeekCalendarView extends ViewPager implements OnWeekClickListener{
    private OnCalendarClickListener mOnCalendarClickListener;
    private WeekAdapter mWeekAdapter;
    public WeekCalendarView(Context context) {
        this(context, null);
    }
    public WeekCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        addOnPageChangeListener(mOnPageChangeListener);
    }
    private void initAttrs(Context context, AttributeSet attrs) {
        initWeekAdapter(context, context.obtainStyledAttributes(attrs, R.styleable.WeekCalendarView));
    }

    private void initWeekAdapter(Context context, TypedArray array) {
        mWeekAdapter = new WeekAdapter(context, array, this);
        setAdapter(mWeekAdapter);
        setCurrentItem(mWeekAdapter.getWeekCount() / 2, false);
    }
    @Override
    public void onClickDate(int year, int month, int day) {
        if (mOnCalendarClickListener != null) {
            mOnCalendarClickListener.onClickDate(year, month, day);
            System.out.println(year+"-"+month+"-"+"day");
        }
    }

    private final OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(final int position) {
            WeekView weekView = mWeekAdapter.getViews().get(position);
            if (weekView != null) {

                weekView.clickThisWeek(weekView.getSelectYear(), weekView.getSelectMonth(), weekView.getSelectDay());
            } else {
                WeekCalendarView.this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onPageSelected(position);
                    }
                }, 50);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * ????????????????????????
     * @param onCalendarClickListener
     */
    public void setOnCalendarClickListener(OnCalendarClickListener onCalendarClickListener) {
        mOnCalendarClickListener = onCalendarClickListener;
    }

    public SparseArray<WeekView> getWeekViews() {
        return mWeekAdapter.getViews();
    }

    public WeekAdapter getWeekAdapter() {
        return mWeekAdapter;
    }

    public WeekView getCurrentWeekView() {
        return getWeekViews().get(getCurrentItem());
    }

}
