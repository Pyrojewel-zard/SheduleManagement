package com.fexli.portable.ocr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tencentcloudapi.common.AbstractModel;

import java.util.HashMap;

public class GeneralFastOCRRequest extends AbstractModel {

    /**
     * 图片的 Base64 值。
     * 支持的图片格式：PNG、JPG、JPEG，暂不支持 GIF 格式。
     * 支持的图片大小：所下载图片经Base64编码后不超过 3M。图片下载时间不超过 3 秒。
     * 图片的 ImageUrl、ImageBase64 必须提供一个，如果都提供，只使用 ImageUrl。
     */
    @SerializedName("ImageBase64")
    @Expose
    private String ImageBase64;

    /**
     * 图片的 Url 地址。
     * 支持的图片格式：PNG、JPG、JPEG，暂不支持 GIF 格式。
     * 支持的图片大小：所下载图片经 Base64 编码后不超过 3M。图片下载时间不超过 3 秒。
     * 图片存储于腾讯云的 Url 可保障更高的下载速度和稳定性，建议图片存储于腾讯云。
     * 非腾讯云存储的 Url 速度和稳定性可能受一定影响。
     */
    @SerializedName("ImageUrl")
    @Expose
    private String ImageUrl;

    /**
     * 获取图片的 Base64 值。
     * 支持的图片格式：PNG、JPG、JPEG，暂不支持 GIF 格式。
     * 支持的图片大小：所下载图片经Base64编码后不超过 3M。图片下载时间不超过 3 秒。
     * 图片的 ImageUrl、ImageBase64 必须提供一个，如果都提供，只使用 ImageUrl。
     *
     * @return ImageBase64 图片的 Base64 值。
     * 支持的图片格式：PNG、JPG、JPEG，暂不支持 GIF 格式。
     * 支持的图片大小：所下载图片经Base64编码后不超过 3M。图片下载时间不超过 3 秒。
     * 图片的 ImageUrl、ImageBase64 必须提供一个，如果都提供，只使用 ImageUrl。
     */
    public String getImageBase64() {
        return this.ImageBase64;
    }

    /**
     * 设置图片的 Base64 值。
     * 支持的图片格式：PNG、JPG、JPEG，暂不支持 GIF 格式。
     * 支持的图片大小：所下载图片经Base64编码后不超过 3M。图片下载时间不超过 3 秒。
     * 图片的 ImageUrl、ImageBase64 必须提供一个，如果都提供，只使用 ImageUrl。
     *
     * @param ImageBase64 图片的 Base64 值。
     *                    支持的图片格式：PNG、JPG、JPEG，暂不支持 GIF 格式。
     *                    支持的图片大小：所下载图片经Base64编码后不超过 3M。图片下载时间不超过 3 秒。
     *                    图片的 ImageUrl、ImageBase64 必须提供一个，如果都提供，只使用 ImageUrl。
     */
    public void setImageBase64(String ImageBase64) {
        this.ImageBase64 = ImageBase64;
    }

    /**
     * 获取图片的 Url 地址。
     * 支持的图片格式：PNG、JPG、JPEG，暂不支持 GIF 格式。
     * 支持的图片大小：所下载图片经 Base64 编码后不超过 3M。图片下载时间不超过 3 秒。
     * 图片存储于腾讯云的 Url 可保障更高的下载速度和稳定性，建议图片存储于腾讯云。
     * 非腾讯云存储的 Url 速度和稳定性可能受一定影响。
     *
     * @return ImageUrl 图片的 Url 地址。
     * 支持的图片格式：PNG、JPG、JPEG，暂不支持 GIF 格式。
     * 支持的图片大小：所下载图片经 Base64 编码后不超过 3M。图片下载时间不超过 3 秒。
     * 图片存储于腾讯云的 Url 可保障更高的下载速度和稳定性，建议图片存储于腾讯云。
     * 非腾讯云存储的 Url 速度和稳定性可能受一定影响。
     */
    public String getImageUrl() {
        return this.ImageUrl;
    }

    /**
     * 设置图片的 Url 地址。
     * 支持的图片格式：PNG、JPG、JPEG，暂不支持 GIF 格式。
     * 支持的图片大小：所下载图片经 Base64 编码后不超过 3M。图片下载时间不超过 3 秒。
     * 图片存储于腾讯云的 Url 可保障更高的下载速度和稳定性，建议图片存储于腾讯云。
     * 非腾讯云存储的 Url 速度和稳定性可能受一定影响。
     *
     * @param ImageUrl 图片的 Url 地址。
     *                 支持的图片格式：PNG、JPG、JPEG，暂不支持 GIF 格式。
     *                 支持的图片大小：所下载图片经 Base64 编码后不超过 3M。图片下载时间不超过 3 秒。
     *                 图片存储于腾讯云的 Url 可保障更高的下载速度和稳定性，建议图片存储于腾讯云。
     *                 非腾讯云存储的 Url 速度和稳定性可能受一定影响。
     */
    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    /**
     * 内部实现，用户禁止调用
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "ImageBase64", this.ImageBase64);
        this.setParamSimple(map, prefix + "ImageUrl", this.ImageUrl);

    }
}

