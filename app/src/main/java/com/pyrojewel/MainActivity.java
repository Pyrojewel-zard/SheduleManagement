package com.pyrojewel;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.StrictMode;
import android.view.View;
import android.webkit.MimeTypeMap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pyrojewel.EventDeal.InputActivity;
import com.tencentcloudapi.common.AbstractModel;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Map;

/**
 * @author Pyrojewel
 */
public class MainActivity extends AppCompatActivity {
    private static final int SELECT_PIC = 100;
    private FloatingActionButton ftb;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;    //获取当前系统版本
        if (currentapiVersion > 9) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads().detectDiskWrites().detectNetwork()
                    .penaltyLog().build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
                    .build());
        }
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        ftb = (FloatingActionButton) findViewById(R.id.ftbInput);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        ftb.setOnClickListener(new View.OnClickListener() {
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
                File file = uriToFileApiQ(uri, this);
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
}