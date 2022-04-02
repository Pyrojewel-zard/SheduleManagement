package com.fexli.portable.ocr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tencentcloudapi.common.AbstractModel;

import java.util.HashMap;

public class GeneralAccurateOCRResponse extends AbstractModel {

    /**
     * 检测到的文本信息，具体内容请点击左侧链接。
     */
    @SerializedName("TextDetections")
    @Expose
    private TextDetection[] TextDetections;

    /**
     * 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    @SerializedName("RequestId")
    @Expose
    private String RequestId;

    /**
     * 获取检测到的文本信息，具体内容请点击左侧链接。
     *
     * @return TextDetections 检测到的文本信息，具体内容请点击左侧链接。
     */
    public TextDetection[] getTextDetections() {
        return this.TextDetections;
    }

    /**
     * 设置检测到的文本信息，具体内容请点击左侧链接。
     *
     * @param TextDetections 检测到的文本信息，具体内容请点击左侧链接。
     */
    public void setTextDetections(TextDetection[] TextDetections) {
        this.TextDetections = TextDetections;
    }

    /**
     * 获取唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     *
     * @return RequestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    public String getRequestId() {
        return this.RequestId;
    }

    /**
     * 设置唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     *
     * @param RequestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    /**
     * 内部实现，用户禁止调用
     */
    @Override
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamArrayObj(map, prefix + "TextDetections.", this.TextDetections);
        this.setParamSimple(map, prefix + "RequestId", this.RequestId);

    }
}