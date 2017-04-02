package com.example.loadingpage;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by majinqiang on 11/13/2016.
 */

public class MyApplication  extends Application{
    //全局的上下文
    public static  Context mContext;
    public static Handler handler;
    public static Thread mainThread;
    public static int mainThreadId;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();//当前用于初始化Application的线程，即为主线程
        mainThreadId = android.os.Process.myTid();//获取当前主线程的Id

        //设置出现未捕获异常时的处理类
//        CrashHandler.getInstance().init();


        //配置OkhttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }

}
