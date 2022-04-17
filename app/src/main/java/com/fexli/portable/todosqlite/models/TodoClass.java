package com.fexli.portable.todosqlite.models;

import java.util.HashMap;

public class TodoClass {
    public static final HashMap<String, Long> DIFF_CHART = new HashMap<String, Long>() {{
        put("高等数学A(下)", 95L);
        put("高等数学A(上)", 95L);
        put("通信原理I", 95L);
        put("大学物理E(上)", 80L);
        put("信号与系统", 90L);
        put("数字电路与逻辑设计", 85L);
        put("线性代数", 85L);
        put("电磁场与电磁波", 85L);
        put("电子电路基础", 90L);
        put("概率论与随机过程", 80L);
        put("微波工程基础", 75L);
        put("毛泽东思想和中国特色社会主义理论体系概论", 70L);
        put("数据结构与算法", 80L);
        put("数学物理方法", 90L);
        put("电路分析基础", 80L);
        put("大学物理E（下）", 80L);
        put("专业实验I", 85L);
        put("微电子器件基础", 65L);
        put("综合英语（B）", 60L);
        put("复变函数", 75L);
        put("综合英语（A）", 50L);
        put("马克思主义基本原理概论", 70L);
        put("电子电路创新设计", 70L);
        put("创新设计与工程实践", 70L);
        put("固体物理", 70L);
        put("高频电子线路", 80L);
        put("模拟集成电路设计", 80L);
        put("数字电路与逻辑设计实验（下）", 80L);
        put("电子测量与电子电路实验II", 75L);
        put("电路仿真与PCB设计", 65L);
        put("量子力学", 70L);
        put("情景英语视听说", 50L);
        put("网络信息系统基础", 40L);
        put("计算机基础与C语言", 65L);
        put("计算机网络", 60L);
        put("电子工艺实习", 50L);
        put("电子测量与电子电路实验III", 80L);
        put("思想道德修养与法律基础", 20L);
        put("电子测量与电子电路实验Ⅰ", 65L);
        put("体育专项(上)", 50L);
        put("全息3D技术与创业项目简介（双创）", 20L);
        put("体育基础(下)", 50L);
        put("体育基础(上)", 50L);
        put("电路辅助设计与仿真", 50L);
        put("经济管理", 10L);
        put("中国近现代史纲要", 20L);
        put("生命科学导论", 20L);
        put("物理实验B", 30L);
        put("专利分析与申请", 10L);
        put("数字电路与逻辑设计实验（上）", 30L);
        put("军训", 50L);
        put("物联网安全", 10L);
        put("中国古建筑文化与鉴赏（在线课程）", 10L);
        put("军事理论", 10L);
        put("中国近现代史纲要（实践环节）", 20L);
        put("电子信息类专业导论", 10L);
        put("信号与系统实验", 20L);
        put("毛泽东思想和中国特色社会主义理论体系概论（实践环节）", 10L);
        put("项目管理与商业决策", 10L);
        put("马克思主义基本原理概论（实践环节）", 10L);
        put("大学生心理健康", 10L);
        put("形势与政策5", 10L);
        put("形势与政策4", 10L);
        put("形势与政策3", 10L);
        put("形势与政策2", 10L);
        put("形势与政策1", 10L);
        put("安全教育", 10L);
    }};
    public int startTs, endTs, weekday;
    //课程开始时间，结束时间，星期。
    // 其中开始/结束时间：单位为秒，范围为0-86400
    // 星期为1-7 = 周一 ~ 周日
    public String name;
    // 课程名称，根据课程名称计算难度


    public TodoClass(int startTs, int endTs, int weekday, String name) {
        this.startTs = startTs;
        this.endTs = endTs;
        this.weekday = weekday;
        this.name = name;
    }

    public long getDIfficulty() {
        Long diff = DIFF_CHART.get(this.name);
        return diff != null ? diff : 0;
    }
}
