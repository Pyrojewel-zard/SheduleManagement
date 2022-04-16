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
import com.pyrojewel.EventDeal.InputActivity;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

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
        int currentapiVersion=android.os.Build.VERSION.SDK_INT;    //获取当前系统版本
        if (currentapiVersion> 9) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads().detectDiskWrites().detectNetwork()
                    .penaltyLog().build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
                    .build());}
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
                File file = uriToFileApiQ(uri,this);
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
                    OcrClient client = new OcrClient(cred, "ap-beijing",clientProfile);
                    GeneralBasicOCRRequest req = new GeneralBasicOCRRequest();
                    //                req.setImageBase64("iVBORw0KGgoAAAANSUhEUgAAAPwAAAFLCAIAAAB9cXZJAAAgAElEQVR4Ae1dvXIiPbMWp06dm9hg3wA22OIK4ArsTRyRkkFokjdz6MwJhJA5JXJiuAJzBS4HOxPsVn2X8VVxWtL8aP5/0CBpeGa38IykabUe9TSaptXN/vOf3/T/fD7/+fOHPlnxEbWhExzFCBwW+RguDsX3BDUev3eyOHiVLdHgAgT+R07Q379/v3//Tp9ESkp/9lO2yZ9QlMYI3G3z52N7F7cpOBvSvd6MPY8Gy2NBExRfjsCA1DxR+fZtFMm9lP6Sz8t7BQUgYBCBtKZXZf2ff/7JPgkGeUXXQEALAoHQq7IenVMH0Xkk/Vp6BREgYBCBMk1PbEWyHkl/B7z6m+lgMN34HZAGSSCQRcACTX98WbHJ5LR/h9Rn5wclHSAQvMj+97//F+nySLvTmp5sOKly7Twcl4O3h/PD2+CeHc41LBzaGQDBm0MgZadXLZWERa4FM98i167UW08ma26WphNWw5LdrhfcBQQUBAyv6f33PZv9GtLjNfw1m+yesbC/Oa1rYsBGNT1X74lDan3locQpENCOgFFNf3zZj9Xf5g+L0+ol/ilSWHXw22RCK+BCAwImrTfHt934Qf1t/u5hwXZvsdRrGB9IAIEMAuatNxmWlAKy7Dz/9D4e+aIfBxDQhIBJTV81BH/z/Ll+gsRX4YT6hgjYrekbDgbNgUAdBGzW9HX4Rxsg0BgBo9abxtziBiCgAQGzmp5eVJVjip0TGmYUJCoRMK/pQ98Db83wi2zlfKGBBgTManplAL73dWLjHzBOKpjgtBsEzGv63b1Y4Yye2cGDk2U3swyqCQTMa3q5vPFoP/Q99kMn5gYXHSFgXtPLgQ2Hj1uKfwEnhI7mGWQVBMxr+ogZ//dndC5O4HCWxANXmhAwr+mDNf1gMNqP11jVa5pXkClDwKg/fZWnNC144GFfBRLqmyLwv/KBiPbFqjtiqSpbXvYAaa6TDmdbWDE143rz5MoinJEpkZ4ho3J/8/MDADpAwPyavoNBgSQQKEPAIutNGZtxne8fN8spNhHGiOCsKQKmNb1/XE4pvpk8yOOsLOCTT9I+fXn5/fV5ajpMtAcCCgImrTciGEIcjt3zDl6dyOw8iHvoptb0xR3tgcDZpPWG4vmdSHgjh5vhUN0lrjyYOAUCOhEwuKanYAhs8nOkczSgBQRqIGB6TV+DRTQBAnoRMKjpeZQbxCrWO52gVgcBk5r+7t/15LSaLzd+YLPx6ajDdEEbOKgVAIPiJAIGNT0FbX388A7jz/1oJE2W8/l7krvklRTqweB+x1jgpoZUDkmIcFULAWmyVCN0R+d0f3ROhi553r3Fi1sk844aVko4qHU/PT3ogZm002vGT8S6VwPCaqYPcj1BABHO8r5UUNZrBIyu6XuNLAZnLQImrTfWggLG+o2AaU1PPpOxw9lgWuFy1u+5wOiuhIBJ3xvG/M38fj/zzh9ydxS5DXsMG6WuNPW3241pTZ9AnjzO7iDzCUhw0QECZtf0w8enBVvRT1O0rtmU+tJ3MHSQvFkErLDTkyf9ejGhTIOIfdATU7jVw7DJTk9uBiNa4SPF1M1q4CsN3Oya3t/Qoib0McvELYYD2ZWE4Na6MbumZ+zreR54mw0Qt/jWhM/YeK1Y0xetAOFAVoQMyi9AwKydvvxRR4SzcnxQ2xIBRDhrCRxucxcB02t6d5ED584iYNZ64yxsYNxlBExr+iYRzlzGGbxbhIBRTc9/jbr/HD8FYc2814coCg4P4Vcz2p9FaIIVNxAwuEe2ODyfrAlD/Hm1gv1dYMLCrbeFgEFNXxLhbPRzQpmU34Nfa4dDuF66oUEd4dL0mj4fJooNcj6//njjv9ZiY0k+Rihtj4DBX2T5IqbSrdJb12l1W1/PGO1lCJjU9IURzvzjMfZD+0EP9PgHFjjt9RruTCNgUNPzx9U7CD96ydUk0PvJwsU6fKO97PnG3UBAImCTP336ecQ1EOgEAYPWm07GA6JAoBIBk2v6SubQAAh0gYBZTX9ckkmyPLtaF4MGzdtGwKymv9uevSf2PEKGzNuWwmuP3rD1RrxO8yyDlQZ7mB6AgCYEglDd2Tj0VELPX7ZcU78pMlk3HF6CzJkpmHCpBQGza3ou1+Lg+ad2b8fwEn+BQIcI2LJHlruYfanjpOX+easW4BwIaELAEk2vaTQgAwRqIGDWehMz6H2d4gucAYEuEbBE03Pf+sXDnTJSbsIfwJSpIIJTXQhYsaanADc7MlmqMq9rfKADBLIImLbTc4/KfNOkSBYIB0stRjoQUREwq+lpDXP/uTh452wuBkpSsp89BSlKss8qSoBAawQQ4aw1dLjRVQRssd64ih/4dhABS6w3DiIHlp1FAJre2akD420RgKZvixzucxYBaHpnpw6Mt0UAmr4tcrjPWQQq7PTkCaAOjSz86iXOgYCLCJRp+uwOkr9//7o4SPAMBFQEytb0379/JylPfao34xwIuIhAmabPSjw0vYtzDJ5TCEDTpwDBZf8RgKbv/xxjhCkEoOlTgOCy/wjYoemPjEyjG7//cGOENiAATW/DLICHqyJgh6a/Y/Sr1+Owk5EvB2ypJ6AOJUOk0Jv4Qupkmq5KVG4XzP4OVVSibru6/Hw9oZ94g/8HhdyCnReH82ERVE0WZ7lvUJYv5F2Tc5SuQZZLAt76zBb8NLo96oJotj8o5tpkkok/iEhs7RE1dWcQ1o+6L5LybLl+Xg9cuFWBJCGmkkBGvXMk07x8cqb9hXRwmZ4kHgbJWCT08jK6V162/iTpFs9hakMvhL41osZutHdNvziwrYyPMGQU/iw6Fk9M7qi9+5dNTuz9Oq+/FLDhc/3vHeN5shLxB3kkNorFhkgO0QTZf2LHmt56nPz3PZv94i8dw1+zye4ZC3vrp6yEQXs1fQnTQZXHKCpaNu+gl4iJWU2mRguS+dNpRUlt6RitTuy0v9IXTA3e0KQxAu5p+t0bkyuazTMtr5lcV9D65/M3H7y/Yc+7BApRVaK00cXxZT9W3zgOi9PqJbAIIRJbIyitaOyepqe49fMB/zFrP2ZeGNf48YmxFS+cM/a6TiAbVVHtdJOoqnlBMQfHiZCDCCxeEzlbm5VEOMvabWSJsbfuc2zGMchDumtEYksjYvt12c6plCe99DS29eE1xRcisZlCvn2/gdDnyneuPz21bN9bD+8cPn589HBYvR5SWcbw3CehEzTI4eyerb2uPBE64RlEnUXAPeuNs1CDcVsQsMN6Y7vDmbRLCjM9fVC6Z1umD3y0QsCs9cYRhzPuYBM6q6WS3sL3xnZbTZY/w5r+8YM7FZ8POc/r7p69PYhaj413LPwxiO2e2cMrLz+M2Woe/FCVcz9jd1veTMiroHMOnXlyW9cs9Hl2rHH2d+Cat6OZBQjYu6a3y+GMsd29WN6MntnBU/zL4HBmgRQ3ZMGwpm/IrcnmcnnjeTN2P0ICOJMzcXHf9mr66qFdz+Es5mU4fNzSMj7hXRzX4swJBNzT9AYczpIz6f/+VArgcKaA4cipe5r++g5nciqDNT35Fu/Ha3VV78hMg80YAbN7ZLPmpPISXXv/yntpVguHs2Z4mW/tnqaPn1crzqTDWUeRHKwYYf+YgJflhXMKh7MLATRwux2avnaEs62WH5gM4IwuLULAPeuNReCVseL7x81yCoN+GUam6uzQ9LY7nNHe2+NySvHN5EEeZ2WBR3yS9unLy++vz5OpaUW/pQiYtd644XDGfczYZCFjTJ3PnnfwZMC1ckOE6qYWtYSDWgSFsRNEOKuGPld4q2+jEGyxb2bUHEIfQWHsxN41vTUOZxQMgU1+jkq/L+tXwkGtPlZdtbRjTd/V6EAXCOQgYK+mz2E2VXQlhzMe5QYhzVLYO33pnqa/vsMZD9p6Ws2XGz+w2fh0tJ11OKi1RU7ffe5pegMOZ/Srq3cYf+5HMprlYD5/L5sBkbyBjJv3FF8wcFNDKocywK5eZ3aPbNMXeJsczqQdJjth4WbaorHBQa0ImWuVw/cmK7U1S7gdJoylWfMWaoaIaPWx6qolIpx1hWwBXTioFQBzxWI7IpxdccDoCgi4Z73BnAGBCxFwz3qjK0VmMzrkMxk7nA2mFS5nF04Kbu8YAePWm/XiTBkzKUvmJEwbSC/xqpUmyhZYkiJTtu8s1WYqqhl5nIXOZ8LBJsfF5lqGCPTTAgHDmn4zZfuf7GPLth/slUeUYSVhIssjll0x8tlweCcTHHaskEC+GwSMrul9tj9R0r5gZMNHHoLvrUTqSyHoMtXm8PFpwXiiNVrXbJK+9HAgK50VKyuNavoC5xkbgbrbftD3qPf69PPreU7Cj5yaNs5STZ6MavoRo80Zv6vcWBqnyCx4lhrTyUJI65rH7ccrueIgp2YWHWdKjGr6IZtN2D70Y6FsmOSsItP4RXkw66fI7NIRzd/QoiZ8OJNxi+FA5oysx4zaab05H84TdmbsPFmfI+tN8J4eVslaWcitN2QFkrcszvFmvrBxTToFpgBvvZjQl1JwxBsHqTl2QhVgZnFxsF2wKHtmttzOsagmzmtzCAeyayN+aX9G1/Sh6nT5LyKcuTd78LK8cM7gQHYhgAZuDxzOvn0bFWWNzZYbYBNdAgF9CBi13ugbBigBgfoIuLemb+YoVoxEMzpNIpwV94kaOxAwG+GM3sMdcDgriXDGjZnhRMKUealZ5Ur3G45wRmH9yIIuD26Pp+ya4kI1Qabs9GpVBBIVstBJkztjTgJTvdq4Dp2IoHqSG6lMNJA14a8CnhrsD/Z7FUK7zo2u6d1wOCuJcDaiX453z+/Br7XD4TBU+jyJLZ9oJfdmXIUzwwgYXdMXOMkYhqRB92SvPJ9ff7zNpQNm0v+yAR00vSoCRjW9Gw5nVRHOhsID05uNd/dzOF9eVXpbdmZU0zvicFYY4cw/HmM/tB80A+Mf0QIHjmgtJfIat9lpvbHM4Yw86Q+Ky9lkshYvr8nChSwM3tnwImvXy6vKjWHrjcrKJeeqleYSOjrvhSOaTjR10oLvTUdfp4hk1hGwGsgGQv/9+/esj01RiYZu+08Cjmj2zjEinNk7N+CsIwSMWm86GhPIAoFSBIza6Us5QyUQ6AgBaPqOgAVZexGAprd3bsBZRwhA03cELMjaiwA0vb1zA846QgCaviNgQdZeBKDp7Z0bcNYRAtD0HQELsvYiAE1v79yAs44QgKbvCFiQtRcBaHp75wacdYSA3Zr+yAYDhi14Hc39zZKFpu9o6n3KR7icDpZtswnpZcuPcyNSAqEw0r7ePhpRo2SNg+AwkKnReHx6nVtiGtAqDmbTgEh+UxHNfrEQYaAWMo5PfkO1tEN+qBueDlGG5xGBq2pw1S0/Z4oRFPDDOwq2X6p4dHluMj49RXpKo+/xxJqEBlVR4Cf5XxUbuS2QgqLJKnVXKsVykkkZoqhPRbhxmJOHykYcsyyKWEY3TNZChnkR7ZYNp0lKhigggpPkLlmRr0GlbJof2T+X+nIhuxY+ufzIzuvAVgRndbnJPbIUikyGN4tjkh3ObKEwTZdhzDNZyiOZsfNaPAf0YERCH0RHk9u1KVJaGOFMoZU6lfKaKqSsJyTQJNi8PJYOMRFUKmq5wFCBkBtRETwb9DCEz0JAlRfUnr38xlr5kWzldxSwHP/Jb6adH+9AiKsg8X4bwBYz3ODMpKaP4uxR0mP6T5LMtXUY5Y8PIk/oVYSigarhAembglS++v0QNVNOcic1lnPekl+J3qitkPFMQZJI8m6ZmSeXW4WN6DRJKihOUsx0nylIEkneLUjmFEUcJE+SpDrgh3fAj+j7NNl/l1cmrTfDH4x9Mv/IPmfsYcwzyFICwCitrISk5ufXiZ1W3NTD/4/YqUbSwjzKnqBD4crEMVqd2Gdl8sM8OrrK9PJDb7Pz/cz7eIyC8zTlUyc/Mu7h2XuilNlXzlBq1HpDEc5O7P03xUhidw8kX+z3J4vDJTWZEYopOVmz8zn+32pmeWzK5Hq3iYRwkVDCPTXhv6CtRn5I4kf72WuT8WSZ0shPSHwoQml9eeH1Nf6a1PRsyMaMrVYijeaIsT39Y/S3xfFrxjX9JrIP+qzKLMfnL6PGh79mk9NqHicF96NEmoVM7d5kCEt/87xjC5kRtLCxqCD5o2+SlDGzW36Oy9GKrV/zNYEBflgErH98WZ0mP6Npv0ZkOKOanrLG0sJuwe5IFijEH2OnMf1tcwwfmbdm+/tghTOdM69C6qPM93wlE329UuAObz3e3wcrnOn8pYoOsf9GKcQHg9F+vPZElGIpRIPB/Y6x3T2vizsoHFyH/DDGAy+TVgjXbTXYYZ3yw/z3l4CZ0fPn4lDwNBaCdWnFrdrptbwo5b7t1aBM9yVXUTXuqdOkF/zQy3Yn4MQAIsLZpVqj+f20EPpcP23bfac1767yDqv4uUZkuEDoEeGsUjT0NbAt+JlV/FyDGUQ40yfMoOQIAkatN45gBDZ7hoBh603P0MRwnEAAmt6JaQKTOhGApteJJmg5gQA0vRPTBCZ1IgBNrxNN0HICAWh6J6YJTOpEAJpeJ5qg5QQC0PROTBOY1IkANL1ONEHLCQSg6Z2YJjCpEwFoep1ogpYTCPRP0/s8xlJ6X5ITc1GfSb69yECMpPoMWt5SbiL58+cP+djX+Yxd8S0845v9r7y93qMgFhQIp3bUA12oyfA7V+9WF/tG6ZiMe6N74LnxLdpuJqrBXKtIZkRXPppRzJ6Snjjz8UHBMpJtcwecbIKrHARMxr0hdih4UxTohofBUYI0URizyYLHw6GYZzL6kmxPM58b4SwbUCwpMlx4VKm5fiSzAH5iazJJR13ImRoqUp/YXAlXG0gSctDqQPNJ33KpaU0vAjPxKRInkfZTgzcF0cvELBVFOKPK7PyLO/KLhbI1EsmM80njzWcrLYlKKxFALSvLSgt5My9IPt1porg2rOlpAqRMkzqPVH7qAaA2MoSlepKdOZrtvP3EGangdya1Jr8S4hSSyBQkiSTvLnzcsizKniWXUR+5zYJC3m945EcCq0WmrItbrDNvvaHoHfQieDqxp8dwfj0eoqxd1KeQRPlfnZG6yntK1frve4rhxreE8xA7u+caUbOldve8GUUC67lRKgVWZ5fm7fT+hlFQFlJp98twlBT5rFVcvlPdQFkdROoKeS/9SzJ/CsPP8KCBp/17RXiekNxw+LglvU+RpcIS+Zc/vjgaImBa0x8Zhd46fLCtxyY7FkT9osBPE7Z/D4Yin4rK0GF3FDgqIxMUMc2iSGbHl/1YNcAcFqfVSyzFuZHG4vn0Kehh+uBRnJJR1a4RISzNhWvXhjX98p5W00GEs9c1xQNjUgYeP9jsi02XbDll8z07eKJNObg8KGJ2wdBhpK6mkcxIQseJZ7fgOU0OM4iRpoZQCxvwSIKT9b88QByOJgj0KsKZjBEfWGU0vqIlX2TrE6b78l6uCwk0aF/84xS92zbqtJCb3laYt97ohlba37PGvUv6aSf0QvrU5UwFC/Xbc37yrTncMDWRSSsqervhatN2ejegbyf0boztBrmEpr+FSS/5ZriF4afH2C9NT9/thd/76ZEbuu7aQa2IfvE7gCEgDHbbJ03PRT79CscTedGDEByTgoWwMgEdrmTaOKhxWY34r+C+kn4eQMrQb+bUsKbv1OEs7W5AyUspnWrx1HJ5Tx7q67ABB7XUFxexHyRfLR4Dryl5brNVctDqQMuJ96HWtKbv1uGshWrLigWfZiF+13ZQy2elWurK7svU8YJbc1AzrOmFPPHUsF05nAWJjWldsy5T8rEkZaSCVyUfHn4lVCO1FcupTEGSSPLuUk0c8yGbpVdran3hebL3ZLOI1WTxbV0Z/kWWlEy3Dmd32w+aUO/16efXM6WGipJLJZcxVVfGHNSqGEN9GwRM+94wdg2Hs+Hw7nH78bqe1PfwSoBpxEGNOym05DfBfOICDmoEh2lN363Dmb+h7JihH6PPJ1zN85rr4GWRg5rIsLqaLzfhECgPZTiYhCTXv4CDmsDKrO9NtDuEr0Fou6CS3r5ku2DBCjS9duYkFYNf5od73j7nHS54DeDoqEvq2HpD5dJ4WL6mX9A2AUFkEWwIk/2JsvBD7UC+Lcu3hWiESZNlonnUJjypop96NZG38ReAHBBCmr38a9p6oxdUEpFGP06FUquXi9qvqplua/EjxTR8bOK/5WbH4h+n6Fkpf5gybLpeYN56oxtBqZHLJUD2KWa7gUNYfU65XNbhIEmxW34KftnqrtPk4Gy6QnbBWFXqO6OdHPfscBb5w/VRBSVNCARC/+3b6O/fvzWzyWrqGmSAgBkETFtvzIwavd40Aubt9DcNPwZvAgFoehOoo0+jCEDTG4UfnZtAAJreBOro0ygC0PRG4UfnJhCApjeBOvo0igA0vVH40bkJBKDpTaCOPo0iAE1vFH50bgIBaHoTqKNPowhA0xuFH52bQMBlTe+z6SCIcqxAdwspNZXhWnHqWopPszunLvKyFuFDEv7w5BzeaBPJRd3zm7k3eiB2FG+hJKbOxT3VJVAU4azu/VXtiugXb1Kponj9epd3TqWFnot8Zg9Qu/0cdSeCR4+Soi6etxobRzrkR2yOXCx4WtvaW1iS2xHLg5xX0s+dgLpQXrGd6Z1THk+aSVtj+f9M6szDIqii3JqRFo3yaS7W54mypzYb2Cu7r04VynjPa7SniG6YrIXM8CKxV1Y+RFJSw82z+Tq9esavxY/ktoYQiQc1Gj0PAHcbEdRMa3ovzhGrptGUqTMDGaUHg53lOX8Mwlyz8pGIljcFU51fHOhl8STF0iqkksRdCgOtVqhASL2oCKSDHobkjvFAunix+lAVCF1+M6385HeR5aduu/SdZfdl6nhBLWDSvXR4bdp6M2R3PNceP36M2elLnvJPSssTbLcbMsobJQ+eYumJyTvu/uX52NRj8nOkXhaf84RnPG+NIMTz/EUp2qLSxdNjyJiks3jaivZDEZkjlSGNooncf669tvsDtfNTPPS4hocDqY1YfFv5GY+gkjjutlx62wKTIKXtwrz1ZrNkgwH/f7+rGpXPKNVYXcEuI6Y3YhlJ/Hw/8z5ST0kZA6k6vfykiOMyjYBhTb+ZshVj3pmdz0x+EaYZbHId6euqmzRGLCOJH+1nrxdIPDGrkZ+qocf1txtBzbCmp5hjk59iueKznIyR8QyJM5Fqc/XCeJgvny3nPMdydBSk6us2YtlxSRlB16/5Oh4R1GhybEzxaVjTbw+MVD2tbaZz9uOJp5IdRCmUI3FWTh5f2eKTjWg5NGcP1F6pYldPqUkzSstiFmZDHtSKD2tRik+KnfvhHcaf+9GIeKdjPg9z96q4RufyIR4M+Do0SPSZDIjrTIpP+ePUnz9/6HWjzmeHL9WXk5aWlXJrc5teMjaJmkTovswPBzVvLW3WKT9Fy8xy05S08Oa1IctUJyCUIlRaadpOX8pcq8r6Ec7qk28nZGK2I5Nq/d6qW9rIT2zvT/DfHQiJbhpdmLbTN2LWWON2QtYdu7bx091IO6FseE0frRfbnFzP4Ywbm+0yNTP2+Xa8MG53G8z7cY/Da/q0743w/7quw9mZkheGclDw/d6JrqKhCqeIvDV0Rx32iKzLa/q00PPf8jOvTB2vBDw6hDgUeSfkyArnM8toTkPhThQ+U+JpTjbKHXCyCa5yEDC9pr8lh7MAfnoMJ+SQnHk8c2ZHfWJzJVxtIO/nJbb5uuSMzGiRaU3fF4cznqS5jr+ZSNhAi5KstOaJgdJKfJNkVzNKCwh9HoR5ZaY1vcIT95pcBNeRW6W8JgdMOd+J8uTyhqY/T3lmpIJTTGpNfiXIhyQyBUkiybt5HT/qLenpXsll1Ecw4tw/Ie1i+rXI5NK+4ULz1hvXHc6kG+HZe2L3o+QPlOJZSH6QOyWb/eLum9y3c/e8qTbAyKfd82ZEf7A8Junhqg0Chn1v3Hc4C0GXHsdfXnid+5e7EIdeC6PVqUHGzOHwcUt6f/eWknqkyMwFurzQsKZ33eGMRUku/ePL6pR0T884nB1f9mP1F9rD4rR6iaU40z45dX6ORx5SZCYxqndlWNM77nDG/PeXwFlr9Py5OBS4WwZTQRI6frhT5qXAMVRpQaeBa9dgMNqPU9tUnHHwSo7I/JXD0RCyr2LSWO6uwxktYPJexrMDLftxKnpXzrsNZYSARdYbTfPhrsOZkFZ1+VOICLfqFFiL6hMppN77CtN2ejcA5kKWtZG7wTu4zCDgrqbvQqNn4LnRgpJvkj4g4qam5z/JFH2/Wz0rfPERvMflR8+5NveFDnN9dmhzUdNzkU+/7/E5CsXposeh25WMVRHR+ANW6jCXB/S1n8ou+jOv6SliGQUwozhnk0yEsyiYWSJKZFYsU4pfjdQVxzGr/mrghJOHuo6PKUWvkHRDHyKiSbnKlfAs1hIkFZguxLJbmoY1vRrVzFvzIH7SeiEjnK3FBbVRhT5/HvJnQbYNIwKGXsCliGbJ8+bisQpMobF0CAFwPyKaGGCBw1wGDjFmx9/qjWp64TGmCnTkTxadZAWUUE+ubfg0JEuim+RXwLpeiEZ5V2aWhUgkFlScqnjIQlYyBUki8VOSYKyA56jNlfjhvPIj+v5KMBANLVHq+IXRX2Q9HrjmRzJ6npwBTZ8U4+J8fv3xNqefTafTZev9dXojkJG7gUUR0Ro5zGmaF8NkjPrejHjgmt/VjoZpjJKRzKoidQ3vtiT63my8u5/X8GpMd8avNUYgI4m3MiJagcNcLx3ajGp6EbFsHwYY8jdsx1jCOSVPALMOKyKi6mq+3PjB80NeYOLMPx6PYRn7QcTGia+VXAevG4uIVuYwR4D106HNqKZn7PGDzb7YdMmWUzbfs4PHVIesPJlnOZHMiiN1vT3zlQ0/Rs8s5a6VT73DCGS2RUQjAMod5nrr0OZkNARdjmXhm6juF7Pki2x96hbxU/zjFL3b1nsHrz/uK7c0ak51KvsAAAO1SURBVL25aKyXuyGI2avl4NWU0XZCbw8/nP98a47IsiVNyU1Bsae9YTu9PUBo5aSd0GtlIUHMNn4SzF3/YkDLG1rbffs2+vv37/fv3+t85i+GUQoEHEHAqPXGEYzAZs8QMGy96RmaGI4TCEDTOzFNYFInAtD0OtEELScQgKZ3YprApE4EoOl1oglaTiAATe/ENIFJnQhA0+tEE7ScQACa3olpApM6EYCm14kmaDmBADS9E9MEJnUiAE2vE03QcgIBaHonpglM6kQAml4nmqDlBALQ9E5ME5jUiQA0vU40QcsJBKDpnZgmMKkTAWh6nWiClhMIQNM7MU1gUicC0PQ60QQtJxCApndimsCkTgSg6XWiCVpOIABN78Q0gUmdCEDT60QTtJxAAJreiWkCkzoRMKHpeYjs5fHyUVTTkcG4ZdjiacvY9JfzCQqWIdBvTS8ykVCsRJ5EBgcQCBAwoekBPhAwikB7Te9vltNg4RAlczouB7Rw8Y/ZGhY1ny7D1COMmk83ggzPByXuihYhvP10uaSPZLKoPDqcULxgql71CMQjQoMLklEZnTt03hYBmZSBlgB//vyp+RmuFzIpJs88JHQc2ZwiyLMg+aIIeC7zCCrR/kXzvJSUfDkSBf4Xa5MgZWY+HdFvlFSTt48uiNeA3YheWECNRLLNRGeiOT76jUA7Te+/70+T9b93IjHg8NdsoqQ+Wxw+tmHFz2ApLVIXPT3K5nf/zuIVdkRlEVTzp0aQn/0K0g6KhDi7N/7mW0yH31b/KOO/PhW0dBSBdmv6hikm/d+fbPJzVBsiTj6ZE03e2pROYYcN+S+kgwonEWin6S9LMVkJVG6Gv8q7wgY1skB2zH/ICf7aiUA7TS9WNKv5JspGHGVmzB2lbP4iWtP76nx1ym0WFYr2+/cwQebmeccWPNVmIZ34KaG3WGpddTTkv4oc6t1CoJ2mZzyJ5Xq8vw/yVU7nL15ZDuTh4+t68Slaz98eniqt5pz87GsurTfz/fjgbUWqzSI6cR7MOXt9rSRPj08z/t2aU3BbgQByTlUAhOr+IdBS0/cPCIzodhBot6a/HXww0h4iAE3fw0nFkMoRgKYvxwe1PUQAmr6Hk4ohlSMATV+OD2p7iAA0fQ8nFUMqRwCavhwf1PYQAWj6Hk4qhlSOADR9OT6o7SEC0PQ9nFQMqRwBaPpyfFDbQwSg6Xs4qRhSOQLQ9OX4oLaHCEDT93BSMaRyBKDpy/FBbQ8RgKbv4aRiSOUIQNOX44PaHiLw//IP0x0oKEKzAAAAAElFTkSuQmCC");
                    req.setImageBase64(s);

                    req.setLanguageType("zh_rare");
                    try {
                        GeneralBasicOCRResponse resp = client.GeneralBasicOCR(req);
                        System.out.println(GeneralBasicOCRResponse.toJsonString(resp));
                    } catch (TencentCloudSDKException e) {
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static File uriToFileApiQ(Uri uri, Context context) {
        File file = null;
        if(uri == null) {
            return null;
        }
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = context.getContentResolver();
            String displayName = System.currentTimeMillis()+ Math.round((Math.random() + 1) * 1000)
                    +"."+ MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));

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
}
