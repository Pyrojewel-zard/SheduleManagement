package com.fexli.portable.schedule;

import com.fexli.portable.todosqlite.models.ToDoTask;
import com.fexli.portable.todosqlite.models.TodoClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    public ParseResult StartScheduleManaged(ToDoTask[] tasks, TodoClass[] classes, int totalDayOfWeek) {
        // 现在的目标是把所有任务全部获取
        // 然后优先进行计算，通过平均的方法进行安排
        // 然后在对每一个任务进行填入

        List<MissionTimeTable> allTask = new ArrayList<>();

        // 获取难度总和
        long totalDiff = 0L;

        ArrayList<Long> weekDiff = new ArrayList<Long>() {{
            for (int i = 0; i < totalDayOfWeek; i++) {
                add(0L);
            }
        }};

        ArrayList<Integer> weekTimeCost = new ArrayList<Integer>() {{
            for (int i = 0; i < totalDayOfWeek; i++) {
                add(0);
            }
        }};

        ArrayList<ArrayList<MissionTimeTable>> weekTask = new ArrayList<ArrayList<MissionTimeTable>>() {{
            for (int i = 0; i < totalDayOfWeek; i++) {
                add(new ArrayList<>());
            }
        }};


        // 获取全部任务，转换为时间排序对象
        for (ToDoTask task : tasks) {
            // 获取当前任务的需求时间
            allTask.add(new MissionTimeTable(task));
            totalDiff += (long) (task.difficulty);
        }

        // 获取全部课程，转换为时间排序对象
        for (TodoClass todoClass : classes) {
            long diff = todoClass.getDIfficulty();
            totalDiff += diff;
            weekDiff.set(todoClass.weekday - 1, weekDiff.get(todoClass.weekday - 1) + diff);
            weekTimeCost.set(todoClass.weekday - 1, weekTimeCost.get(todoClass.weekday - 1) + todoClass.endTs - todoClass.startTs);
            Objects.requireNonNull(this.TimeTable.get(todoClass.weekday)).add(this.PackTime(todoClass.startTs, todoClass.endTs - todoClass.startTs));
        }

//        for (int i = 1; i <= 7; i++) { // 已存在时间表中的项目，未知难度，忽略
//            ArrayList<Long> table = this.TimeTable.get(i);
//            if (table == null) {
//                continue;
//            }
//            for (Long time : table) {
//                allTask.add(new MissionTimeTable(this.ParseTime(time), i));
//                totalDiff += 0;
//            }
//        }

        // 进行规划排序，选择出一个最优的平均解
        // 先把所有class填入，然后根据平均值计算最相近的结果填入

        // 计算平均难度
        Long avgDiff = totalDiff / totalDayOfWeek;

        allTask.sort(new Comparator<MissionTimeTable>() {
            @Override
            public int compare(MissionTimeTable aLong, MissionTimeTable t1) {
                return -aLong.compareTo(t1); // 逆序排列
            }
        });

        // 依次填入Task，可否复用？
        for (MissionTimeTable task : allTask) {
            long lowDiff = 0L;
            int lowDay = 1;
            for (int i = 0; i < totalDayOfWeek; i++) {
                if (weekDiff.get(i) < lowDiff) {
                    lowDiff = weekDiff.get(i);
                    lowDay = i;
                }
            }
            task.weekday = task.ownTask.taskDay = lowDay + 1;
            weekDiff.set(lowDay, weekDiff.get(lowDay) + task.ownTask.difficulty);
            weekTask.get(lowDay).add(task);
            weekTimeCost.set(lowDay, weekTimeCost.get(lowDay) + task.timeCost);
        }
        // 先计算剩余时间，根据剩余时间安排休息时间
        for (int i = 0; i < totalDayOfWeek; i++) {
            int timeCost = weekTimeCost.get(i);//当前日的占用时间
            if (timeCost > 41400) {
                return new ParseResult(false, "使用时间超过限定时间(9:30-21:00)[星期" + Integer.valueOf(i + 1).toString() + "]");
            }
//            int breakTime = 41400 - timeCost;
            for (MissionTimeTable table : weekTask.get(i)) {
                ArrayList<Long> _timeTable = this.TimeTable.get(i + 1);
                if (_timeTable == null) {
                    continue;
                }
                long timed = this.QueryTime2(table.timeCost, _timeTable);
                if (timed != 0) {
                    _timeTable.sort(new Comparator<Long>() {
                        @Override
                        public int compare(Long aLong, Long t1) {
                            return aLong.compareTo(t1);
                        }
                    });
                    table.ownTask.taskAt = timed;
                    this.Scheduled.add(table.ownTask);
                }
            }
        }

        return new ParseResult(true, "完成");
    }

    public long QueryTime2(int timecost, ArrayList<Long> table) {
        // check empty
        if (table.size() == 0) {
            long packed = this.PackTime(34200, timecost);
            table.add(packed);
            return packed;
        }
        // query first
        if (this.ParseTime(table.get(0)).begin > timecost + 34200) {
            long packed = this.PackTime(34200, timecost);
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
                int relax = Math.min((parsed.begin - prev.end) - timecost, timecost / 6);
                long packed = this.PackTime(prev.end + relax, timecost);
                table.add(packed);
                return packed;
            }
        }
        // query last
        if (this.ParseTime(table.get(table.size() - 1)).end < 75600 - timecost) {
            long packed = this.PackTime(75600 - timecost, timecost);
            table.add(packed);
            return packed;
        }
        return 0;
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

    /**
     * FindEmpty 尝试从日程安排中寻找一个空闲时间。
     */
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

    public static class MissionTimeTable implements Comparable<MissionTimeTable> {
        public ToDoTask ownTask;
        public int timeCost;
        public ParsedTime arrange;
        public boolean isClass;
        public int weekday;

        public MissionTimeTable(ToDoTask task) {
            this.ownTask = task;
            this.timeCost = task.Arrange();
            this.isClass = false;
            this.arrange = null;
        }

        public MissionTimeTable(int startTs, int endTs, int weekday) {
            this.isClass = true;
            this.ownTask = null;
            this.arrange = new ParsedTime(startTs, endTs);
            this.timeCost = endTs - startTs;
            this.weekday = weekday;
        }

        public MissionTimeTable(ParsedTime ts, int weekday) {
            this.isClass = true;
            this.ownTask = null;
            this.arrange = ts;
            this.timeCost = ts.end - ts.begin;
            this.weekday = weekday;
        }

        @Override
        public int compareTo(MissionTimeTable other) {
            return other.timeCost - this.timeCost;
        }
    }

    public static class ParseResult {
        public boolean status;
        public String result;

        public ParseResult(boolean ok, String errInfo) {
            this.status = ok;
            this.result = errInfo;
        }
    }
}
