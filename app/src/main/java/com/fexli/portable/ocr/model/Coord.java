package com.fexli.portable.ocr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tencentcloudapi.common.AbstractModel;

import java.util.HashMap;

public class Coord  extends AbstractModel {

    /**
     * 横坐标
     */
    @SerializedName("X")
    @Expose
    private Integer X;

    /**
     * 纵坐标
     */
    @SerializedName("Y")
    @Expose
    private Integer Y;

    /**
     * 获取横坐标
     * @return X 横坐标
     */
    public Integer getX() {
        return this.X;
    }

    /**
     * 设置横坐标
     * @param X 横坐标
     */
    public void setX(Integer X) {
        this.X = X;
    }

    /**
     * 获取纵坐标
     * @return Y 纵坐标
     */
    public Integer getY() {
        return this.Y;
    }

    /**
     * 设置纵坐标
     * @param Y 纵坐标
     */
    public void setY(Integer Y) {
        this.Y = Y;
    }

    /**
     * 内部实现，用户禁止调用
     */
    @Override
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "X", this.X);
        this.setParamSimple(map, prefix + "Y", this.Y);

    }
}