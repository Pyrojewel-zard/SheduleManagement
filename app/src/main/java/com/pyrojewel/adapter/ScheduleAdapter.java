package com.pyrojewel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.common.bean.Schedule;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
这里的改写，咱们只需要显示，删除可有可无，无需显示是否完成，纯粹的显示
 * @author 28956
 */
public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    /**使用这个保存读取到的列表*/
    private List<Schedule> mSchedules;


    public ScheduleAdapter(Context context,ArrayList<Schedule>schedules) {
        mContext = context;
        mSchedules =schedules;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ScheduleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_schedule, parent, false));
    }
    /**重用数据时调用的方法*/
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final Schedule schedule = mSchedules.get(position);
            final ScheduleViewHolder viewHolder = (ScheduleViewHolder) holder;
            viewHolder.tvScheduleTitle.setText(schedule.getTitle());
            //这个可以有，修改数据界面
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //放一个弹出界面，然后修改数据可好
                    //UpdateDialog dialog = new UpdateDialog(mContext,schedule);
                    //dialog.show();
                }
            });
        }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mSchedules.size();
    }

/**相当于是描述要显示的界面，就是需要增加的ViewHolder*/
    protected class ScheduleViewHolder extends RecyclerView.ViewHolder {

        protected View vScheduleHintBlock;
        protected TextView tvScheduleTitle;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            vScheduleHintBlock = itemView.findViewById(R.id.vScheduleHintBlock);
            tvScheduleTitle = (TextView) itemView.findViewById(R.id.tvScheduleTitle);
        }
    }
}
