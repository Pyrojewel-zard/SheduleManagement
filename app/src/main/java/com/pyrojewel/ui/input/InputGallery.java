package com.pyrojewel.ui.input;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentInputGalleryBinding;
import com.pyrojewel.EventDeal.InputActivity;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRResponse;
import com.time.nlp.TimeNormalizer;
import com.time.nlp.TimeUnit;
import com.time.nlp.stringPreHandlingModule;
import com.time.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * @author Pyrojewel
 */
public class InputGallery extends Fragment {
    private static final int SELECT_PIC = 100;
    private FragmentInputGalleryBinding binding;
    private ImageButton imageButton;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInputGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        //获取当前系统版本
        if (currentApiVersion > 9) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads().detectDiskWrites().detectNetwork()
                    .penaltyLog().build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
                    .build());
        }
        imageButton=(ImageButton) getActivity().findViewById(R.id.imageInput);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent();
                I.setAction(Intent.ACTION_PICK);
                I.setType("image/*");
                startActivityForResult(I, SELECT_PIC);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PIC) {
                Uri uri = data.getData();
                File file = uriToFileApiQ(uri, getActivity());
                //获取图片存到沙盒里面，大概率就是这个问题
                try {
                    InputStream in = new FileInputStream(new File(file.getPath()));
                    String s = ImageToBase64ByStream(in);
                    System.out.println(s);

                    Credential cred = new Credential("AKIDJSLzMRFn56qJVQ4RaS3MPBgtpVv3uVps", "nKaZGgphIcgTzEExPaZHislPlqeCTHn9");
                    ClientProfile clientProfile = new ClientProfile();
                    HttpProfile httpProfile = new HttpProfile();
                    httpProfile.setEndpoint("ocr.tencentcloudapi.com");
                    clientProfile.setHttpProfile(httpProfile);
                    OcrClient client = new OcrClient(cred, "ap-beijing", clientProfile);
                    GeneralBasicOCRRequest req = new GeneralBasicOCRRequest();
                    req.setImageBase64(s);
                    req.setLanguageType("zh_rare");
                    try {
                        GeneralBasicOCRResponse resp = client.GeneralBasicOCR(req);
                        String rr=GeneralBasicOCRResponse.toJsonString(resp);
                        String detectText=parseJSONWithJSONObject(rr);
                        System.out.println(detectText);
                        String[]task=deal(detectText);
                        if(task!=null){
                        System.out.println(task[0]+task[1]);
                        Intent intent=new Intent(getActivity(), InputActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("time",task[0]);
                        bundle.putString("task",task[1]);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        }
                    } catch (TencentCloudSDKException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static File uriToFileApiQ(Uri uri, Context context) {
        File file = null;
        if (uri == null) {
            return null;
        }
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = context.getContentResolver();
            String displayName = System.currentTimeMillis() + Math.round((Math.random() + 1) * 1000)
                    + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));

            try {
                InputStream is = contentResolver.openInputStream(uri);
                File cache = new File(context.getCacheDir().getAbsolutePath(), displayName);
                FileOutputStream fos = new FileOutputStream(cache);
                FileUtils.copy(is, fos);
                file = cache;
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 文件流生成base64
     *
     * @param
     * @return
     */
    public static String ImageToBase64ByStream(InputStream in) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            data = new byte[in.available()];
            in.read(data);
            in.close();
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(data);
            // 返回Base64编码过的字节数组字符串
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        return null;
    }

    private String parseJSONWithJSONObject(String JsonData) {
        try {

            JSONObject object = new JSONObject(JsonData);
            JSONArray textDetections = (JSONArray) object.get("TextDetections");
            StringBuffer mBuffer = new StringBuffer("");
            for (int i = 0; i < textDetections.length(); i++) {
                JSONObject jsonObject = textDetections.getJSONObject(i);
                String detectedText = jsonObject.getString("DetectedText");
                mBuffer.append(detectedText);
            }
            return mBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String[] deal(String str){
        File file=new File("libs/TimeExp.m");
        TimeNormalizer normalizer = new TimeNormalizer(file.toURI().toString());
        normalizer.parse(str);
        // 抽取时间
        TimeUnit[] unit = normalizer.getTimeUnit();
        String a=str;
        String[] re=new String[2];
        if(unit.length!=0) {
            a = stringPreHandlingModule.numberTranslator(a).replace(unit[0].Time_Expression, "");
            //删除掉原本内容中的时间部分，余下的作为task
            re[0]=(DateUtil.formatDateDefault(unit[0].getTime())) ;
            re[1]=a;
            return re;
            //方便显示我就又加在一起了，只要其中一项，或者两项分开，改一下就好
        }else{
            Toast t = Toast.makeText(getActivity(), "没有识别到时间关键词", Toast.LENGTH_LONG);
            t.show();
            return null;
        }
    }
}