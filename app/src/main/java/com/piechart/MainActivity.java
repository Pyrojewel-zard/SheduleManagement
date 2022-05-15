package com.piechart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CakeSurfaceView pieChart;
    private PieChartView pieChartNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //pieChart = (CakeSurfaceView)findViewById(R.id.assets_pie_chart);
        //pieChartNow = (PieChartView) findViewById(R.id.assets_pie_chart_now);
        initData();
    }

    private void initData() {
        List<CakeValue> itemBeanList = new ArrayList<>();
        itemBeanList.add(new CakeValue("可用余额",200,"#FABD3B"));
        itemBeanList.add(new CakeValue("待收总额",300,"#F9943C"));
        itemBeanList.add(new CakeValue("投资冻结",100,"#FFD822"));
        itemBeanList.add(new CakeValue("提现冻结",250,"#F7602B"));
        pieChart.setData(itemBeanList);

        List<CakeValue> itemBeanLists = new ArrayList<>();
        itemBeanLists.add(new CakeValue("可用余额",100,"#FABD3B"));
        itemBeanLists.add(new CakeValue("待收总额",500,"#F9943C"));
        itemBeanLists.add(new CakeValue("投资冻结",200,"#FFD822"));
        itemBeanLists.add(new CakeValue("提现冻结",300,"#F7602B"));
        pieChartNow.setData(itemBeanLists);
    }
}
