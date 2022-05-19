package com.pyrojewel.ui.home;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.common.base.app.BaseFragment;
import com.example.common.bean.Schedule;
import com.example.common.listener.OnTaskFinishedListener;
import com.example.diary.schedule.ScheduleRecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.pyrojewel.adapter.ScheduleAdapter;
import com.pyrojewel.schedule.LoadScheduleTask;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Pyrojewel
 */
public class HomeFragment extends BaseFragment implements OnTaskFinishedListener<List<Schedule>> {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RelativeLayout rLHomeNoTask;
    private ScheduleRecyclerView rvScheduleList;
    private ScheduleAdapter mScheduleAdapter;
    private Button refreshCtx;

    private RelativeLayout relativeLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Nullable
    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void bindView() {
        rLHomeNoTask = getActivity().findViewById(R.id.rlHomeNoTask);
        rvScheduleList = getActivity().findViewById(R.id.rvHomeScheduleList);
        refreshCtx = getActivity().findViewById(R.id.refreshCtx);

        rvScheduleList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mScheduleAdapter = new ScheduleAdapter(getActivity(), this);
        rvScheduleList.setAdapter(mScheduleAdapter);
        refreshCtx.setOnClickListener(this::onClick);

    }

    @SuppressLint("SetTextI18n")
    public void onClick(View view) {
        LocalDateTime locTs = LocalDateTime.now();
        refreshCtx.setText("刷新 "+locTs.getYear() + "-" + locTs.getMonthValue() + "-" + locTs.getDayOfMonth());
        new LoadScheduleTask(mActivity, this,
                locTs.getYear(), locTs.getMonth().getValue()-1, locTs.getDayOfMonth()
        ).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onTaskFinished(List<Schedule> data) {
        mScheduleAdapter.changeAllData(data);
        rLHomeNoTask.setVisibility(data.size() == 0 ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

