package com.fexli.portable.schedule;

import com.fexli.portable.todosqlite.models.ToDoTask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

class TimeFinder {
    int day;
    long time;

    TimeFinder(int day, long time) {
        this.day = day;
        this.time = time;
    }
}

public class TimeScheduler {
    public HashMap<Integer, ArrayList<Long>> TimeTable; // 当前规划的时间表，日期1-7为周一-周日
    public ArrayList<ToDoTask> Scheduled; //已经规划的任务列表

    public TimeScheduler() {
        this.TimeTable = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            this.TimeTable.put(i + 1, new ArrayList<>());
        }
        this.Scheduled = new ArrayList<>();
    }

    public TimeScheduler(HashMap<Integer, ArrayList<Long>> table) {
        this.TimeTable = table;
        this.Scheduled = new ArrayList<>();
    }

    public TimeScheduler(HashMap<Integer, ArrayList<Long>> table, ArrayList<ToDoTask> scheduled) {
        this.TimeTable = table;
        this.Scheduled = scheduled;
    }

    public void ClearScheduled() {
        this.TimeTable.clear();
        for (int i = 0; i < 7; i++) {
            this.TimeTable.put(i + 1, new ArrayList<>());
        }
        this.Scheduled = new ArrayList<>();
    }

    public ParsedTime ParseTime(Long ts) {
        return new ParsedTime((int) (ts % 100000), (int) (ts / 100000));
    }

    public long PackTime(int begin, int cost) {
        return ((long) (begin) * 100000) + ((long) cost);
    }

    // StartScheduleDefault 开启常规任务规划
    public boolean StartScheduleDefault(ToDoTask[] tasks) {
        for (ToDoTask task : tasks) {
            int timeCost = task.Arrange();
            TimeFinder emptyTs = this.FindEmpty(timeCost);
            if (emptyTs == null) {
                return false;
            }
            task.taskDay = emptyTs.day;
            task.taskAt = emptyTs.time;
            this.Scheduled.add(task);
        }
        return true;
    }

    // QueryTime 从日期table中寻找剩余的时间cost
    public long QueryTime(int timecost, ArrayList<Long> table) {
        // check empty
        if (table.size() == 0) {
            long packed = this.PackTime(0, timecost);
            table.add(packed);
            return packed;
        }
        // query first
        if (this.ParseTime(table.get(0)).begin > timecost) {
            long packed = this.PackTime(0, timecost);
            table.add(packed);
            return packed;
        }
        // query inner
        ParsedTime prev = null;
        for (Long seed : table) {
            ParsedTime parsed = this.ParseTime(seed);
            if (prev == null) {
                prev = parsed;
                continue;
            }
            if ((parsed.begin - prev.end) > timecost) {
                int relax = Math.min((parsed.begin - prev.end) - timecost, 300);
                long packed = this.PackTime(prev.end + relax, timecost);
                table.add(packed);
                return packed;
            }
        }
        // query last
        if (this.ParseTime(table.get(table.size() - 1)).end < 86400 - timecost) {
            long packed = this.PackTime(86400 - timecost, timecost);
            table.add(packed);
            return packed;
        }
        return 0;
    }

    /** FindEmpty 尝试从日程安排中寻找一个空闲时间。*/
    public TimeFinder FindEmpty(int timecost) {
        for (int i = 0; i < 7; i++) {
            ArrayList<Long> table = this.TimeTable.get(i + 1);
            if (table == null) {
                continue;
            }
            long timed = this.QueryTime(timecost, table);
            if (timed != 0) {
                table.sort(new Comparator<Long>() {
                    @Override
                    public int compare(Long aLong, Long t1) {
                        return aLong.compareTo(t1);
                    }
                });
                return new TimeFinder(i + 1, timed);
            }
        }
        return null;
    }

    public static class ParsedTime {
        public int begin;
        public int end;

        public ParsedTime(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }
    }
}
