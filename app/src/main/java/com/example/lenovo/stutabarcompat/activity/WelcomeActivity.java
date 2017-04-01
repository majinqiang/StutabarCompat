package com.example.lenovo.stutabarcompat.activity;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lenovo.stutabarcompat.R;


public class WelcomeActivity extends AppCompatActivity {
     Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        //全屏
//        if (Build.VERSION.SDK_INT >= 21){
//            View decroView = getWindow().getDecorView();
//            int options= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE ;
//            decroView.setSystemUiVisibility(options);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        //设置状态栏的颜色
//        StatusBarCompat.compat(this, getResources().getColor(R.color.status_bar_color));

    }
}
