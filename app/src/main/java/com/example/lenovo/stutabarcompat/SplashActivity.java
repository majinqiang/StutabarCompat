package com.example.lenovo.stutabarcompat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lenovo.stutabarcompat.activity.GuideActivity;
import com.example.lenovo.stutabarcompat.activity.WelcomeActivity;
//转载:http://blog.csdn.net/yanzhenjie1003/article/details/52201896

public class SplashActivity extends AppCompatActivity {
    /**
     * 静态常量
     */
    public static final String START_MAIN = "start_main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, GuideActivity.class));
        finish();
    }
}
