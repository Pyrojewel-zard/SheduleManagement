package com.fexli.portable.ocr;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tencentcloudapi.common.AbstractClient;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.JsonResponseModel;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.ocr.v20181119.models.GeneralAccurateOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralAccurateOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.GeneralFastOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralFastOCRResponse;

import java.lang.reflect.Type;


public class OcrClient extends AbstractClient {
    private static final String endpoint = "ocr.tencentcloudapi.com";
    private static final String version = "2021-12-29";

    /**
     * 构造client
     *
     * @param credential 认证信息实例
     * @param region     产品地域
     */
    public OcrClient(Credential credential, String region) {
        this(credential, region, new ClientProfile());
    }

    /**
     * 构造client
     *
     * @param credential 认证信息实例
     * @param region     产品地域
     * @param profile    配置实例
     */
    public OcrClient(Credential credential, String region, ClientProfile profile) {
        super(OcrClient.endpoint, OcrClient.version, credential, region, profile);
    }


    /**
     * 接口支持图像整体文字的检测和识别，返回文字框位置与文字内容。相比通用印刷体识别接口，准确率和召回率更高。
     *
     * @param req GeneralAccurateOCRRequest
     * @return GeneralAccurateOCRResponse
     * @throws TencentCloudSDKException
     */
    public GeneralAccurateOCRResponse GeneralAccurateOCR(GeneralAccurateOCRRequest req) throws TencentCloudSDKException {
        JsonResponseModel<GeneralAccurateOCRResponse> rsp = null;
        try {
            Type type = new TypeToken<JsonResponseModel<GeneralAccurateOCRResponse>>() {
            }.getType();
            rsp = gson.fromJson(this.internalRequest(req, "GeneralAccurateOCR"), type);
        } catch (JsonSyntaxException e) {
            throw new TencentCloudSDKException(e.getMessage());
        }
        return rsp.response;
    }

    /**
     * 接口支持多场景、任意版面下整图文字的识别。
     * 支持自动识别语言类型，同时支持自选语言种类（推荐），
     * 除中英文外，支持日语、韩语、西班牙语、法语、德语、葡萄牙语、越南语、马来语、俄语、意大利语、荷兰语、瑞典语、
     * 芬兰语、丹麦语、挪威语、匈牙利语、泰语等多种语言。应用场景包括：印刷文档识别、网络图片识别、广告图文字识别、
     * 街景店招识别、菜单识别、视频标题识别、头像文字识别等。
     *
     * @param req GeneralBasicOCRRequest
     * @return GeneralBasicOCRResponse
     * @throws TencentCloudSDKException
     */
    public GeneralBasicOCRResponse GeneralBasicOCR(GeneralBasicOCRRequest req) throws TencentCloudSDKException {
        JsonResponseModel<GeneralBasicOCRResponse> rsp = null;
        try {
            Type type = new TypeToken<JsonResponseModel<GeneralBasicOCRResponse>>() {
            }.getType();
            rsp = gson.fromJson(this.internalRequest(req, "GeneralBasicOCR"), type);
        } catch (JsonSyntaxException e) {
            throw new TencentCloudSDKException(e.getMessage());
        }
        return rsp.response;
    }

    /**
     * 本接口支持图片中整体文字的检测和识别，返回文字框位置与文字内容。相比通用印刷体识别接口，识别速度更快、支持的 QPS 更高。
     *
     * @param req GeneralFastOCRRequest
     * @return GeneralFastOCRResponse
     * @throws TencentCloudSDKException
     */
    public GeneralFastOCRResponse GeneralFastOCR(GeneralFastOCRRequest req) throws TencentCloudSDKException {
        JsonResponseModel<GeneralFastOCRResponse> rsp = null;
        try {
            Type type = new TypeToken<JsonResponseModel<GeneralFastOCRResponse>>() {
            }.getType();
            rsp = gson.fromJson(this.internalRequest(req, "GeneralFastOCR"), type);
        } catch (JsonSyntaxException e) {
            throw new TencentCloudSDKException(e.getMessage());
        }
        return rsp.response;
    }
}