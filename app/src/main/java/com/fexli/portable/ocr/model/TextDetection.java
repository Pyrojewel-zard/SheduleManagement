package com.fexli.portable.ocr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tencentcloudapi.common.AbstractModel;

import java.util.HashMap;

public class TextDetection extends AbstractModel {

    /**
     * 识别出的文本行内容
     */
    @SerializedName("DetectedText")
    @Expose
    private String DetectedText;

    /**
     * 置信度 0 ~100
     */
    @SerializedName("Confidence")
    @Expose
    private Integer Confidence;

    /**
     * 文本行坐标，以四个顶点坐标表示
     * 注意：此字段可能返回 null，表示取不到有效值。
     */
    @SerializedName("Polygon")
    @Expose
    private Coord[] Polygon;

    /**
     * 此字段为扩展字段。
     * GeneralBasicOcr接口返回段落信息Parag，包含ParagNo。
     */
    @SerializedName("AdvancedInfo")
    @Expose
    private String AdvancedInfo;

    /**
     * 获取识别出的文本行内容
     *
     * @return DetectedText 识别出的文本行内容
     */
    public String getDetectedText() {
        return this.DetectedText;
    }

    /**
     * 设置识别出的文本行内容
     *
     * @param DetectedText 识别出的文本行内容
     */
    public void setDetectedText(String DetectedText) {
        this.DetectedText = DetectedText;
    }

    /**
     * 获取置信度 0 ~100
     *
     * @return Confidence 置信度 0 ~100
     */
    public Integer getConfidence() {
        return this.Confidence;
    }

    /**
     * 设置置信度 0 ~100
     *
     * @param Confidence 置信度 0 ~100
     */
    public void setConfidence(Integer Confidence) {
        this.Confidence = Confidence;
    }

    /**
     * 获取文本行坐标，以四个顶点坐标表示
     * 注意：此字段可能返回 null，表示取不到有效值。
     *
     * @return Polygon 文本行坐标，以四个顶点坐标表示
     * 注意：此字段可能返回 null，表示取不到有效值。
     */
    public Coord[] getPolygon() {
        return this.Polygon;
    }

    /**
     * 设置文本行坐标，以四个顶点坐标表示
     * 注意：此字段可能返回 null，表示取不到有效值。
     *
     * @param Polygon 文本行坐标，以四个顶点坐标表示
     *                注意：此字段可能返回 null，表示取不到有效值。
     */
    public void setPolygon(Coord[] Polygon) {
        this.Polygon = Polygon;
    }

    /**
     * 获取此字段为扩展字段。
     * GeneralBasicOcr接口返回段落信息Parag，包含ParagNo。
     *
     * @return AdvancedInfo 此字段为扩展字段。
     * GeneralBasicOcr接口返回段落信息Parag，包含ParagNo。
     */
    public String getAdvancedInfo() {
        return this.AdvancedInfo;
    }

    /**
     * 设置此字段为扩展字段。
     * GeneralBasicOcr接口返回段落信息Parag，包含ParagNo。
     *
     * @param AdvancedInfo 此字段为扩展字段。
     *                     GeneralBasicOcr接口返回段落信息Parag，包含ParagNo。
     */
    public void setAdvancedInfo(String AdvancedInfo) {
        this.AdvancedInfo = AdvancedInfo;
    }

    /**
     * 内部实现，用户禁止调用
     */
    @Override
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "DetectedText", this.DetectedText);
        this.setParamSimple(map, prefix + "Confidence", this.Confidence);
        this.setParamArrayObj(map, prefix + "Polygon.", this.Polygon);
        this.setParamSimple(map, prefix + "AdvancedInfo", this.AdvancedInfo);

    }
}

