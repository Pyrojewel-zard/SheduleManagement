package com.pyrojewel.Course;

import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Pyrojewel
 */
public class Csv {
    /**针对具体表格的可用数据的开始与结束*/
    final static int DATA_START =3;
    final static int DATA_END =16;
    final static int WEEK =5;
    /**读取Excel*/
    public static Workbook readExcel(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        String xls=".xls";
        String xlsx=".xlsx";
        try {
            is = new FileInputStream(filePath);
            if (xls.equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (xlsx.equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int weeks(String str){
        str=str.replace("[周]","");
        String[]arr;
        String key=",";
        if(str.contains(key)){
            arr=str.split(",");
        }else{
            arr=str.split("-");
        }
        return Integer.valueOf(arr[0]);
    }
    public static int weekLength(String str){
        str=str.replace("[周]","");
        String[]arr;
        String key=",";
        if(str.contains(key)){
            arr=str.split(",");
        }else{
            arr=str.split("-");
        }
        if(arr.length==1) {
            return 1;
        } else {
            return Integer.valueOf(arr[arr.length-1])-Integer.valueOf(arr[0]);
        }
    }
    public static int days(String str){
        str=str.replace("[","");
        str=str.replace("节","");
        str=str.replace("[","");
        String[]arr;
        arr=str.split("-");
        return Integer.valueOf(arr[0]);
    }
    public static int dayLength(String str){
        str=str.replace("[","");
        str=str.replace("节","");
        str=str.replace("]","");
        String[]arr;
        arr=str.split("-");
        int a=Integer.valueOf(arr[arr.length-1])-Integer.valueOf(arr[0]);
        return a;
    }
    /**如何解析？？！！！如何构建？？！！*/
    public static String change(String string){
        String str=string.replace(")\n(",")(").trim();
        str=str.replace("）\n（",")(");
        str=str.replace("）\n(",")(");
        str=str.replace(")\n（",")(");
        return str;
    }
    public static ArrayList<CourseModel> course(String path){
        Workbook workbook = null;
        workbook = readExcel(path);
        if(workbook!=null){
        Sheet sht0 = workbook.getSheetAt(0);
        ArrayList<CourseModel> courseModels=new ArrayList<>();
        for (int j = 1; j <= WEEK; j++) {
            //表示列从星期一到星期五
            for (int i = DATA_START; i <= DATA_END; i++) {
                //表示行，下标从0开始，需要减一
                Row r = sht0.getRow(i);
                String[] arr;
                //对体育专项突然六行的特殊数据处理……
                arr = change(r.getCell(j).toString()).split("\n");
                if(arr.length==1) {
                    continue;
                }
                int len=0;
                while(len<arr.length) {
                    CourseModel courseModel = new CourseModel();
                    courseModel.setName(arr[len++]);

                    courseModel.setTeacher(arr[len++]);
                    courseModel.setWeekString((arr[len++]));
                    courseModel.setDayOfWeek(j);
                    courseModel.setPlace(arr[len++]);
                    courseModel.setTimeStart(days(arr[len]));
                    courseModel.setTimeLength(dayLength(arr[len++]));
                    if(!courseModels.contains(courseModel)) {
                        courseModels.add(courseModel);
                    }
                }
            }
        }
            return courseModels;
        }else
        {
            Log.d("false","cann't open");
            return null;
        }
    }

    public static void main(String[] args) {
        String path="C:\\Users\\Pyrojewel\\Desktop\\hello.xls";
        course(path);
    }
}