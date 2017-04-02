package com.example.loadingpage.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.loadingpage.ActivityManager;
import com.example.loadingpage.User;
import com.loopj.android.http.AsyncHttpClient;
import butterknife.ButterKnife;

/**
 * Created by majinqiang on 11/17/2016.
 * 通用的Activity的使用的基类
 */

public abstract class BaseActivity extends FragmentActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提供加载布局的方法
        setContentView(getLayoutId());
        //抽取activity一定要得的绑定View
        ButterKnife.bind(this);
        //将当前的Activity添加到ActivityManager中
        ActivityManager.getInstance().addActivity(this);
        //提供初始化title
        initTitle();
        //提供初始化数据的方法
        initData();
    }
    //初始化内容数据
    protected abstract void initData();

    //初始化title
    protected abstract void initTitle();
    //加载布局
    public abstract int getLayoutId();

    //销毁当前的Activity
    public void removeCurrentActivity(){
        ActivityManager.getInstance().deleteCurrent();
    }

    //销毁当前所有的activity
    public  void removeAll(){
        ActivityManager.getInstance().deleteAll();
    }
    //启动新的activity
    public void goToActivity(Class activity,Bundle bundle){
        Intent intent = new Intent(this,activity);
        //携带数据
        if(bundle != null && bundle.size() != 0) {
            intent.putExtra("data",bundle);
        }
        startActivity(intent);
    }

    //使用AsyncHttpClient，实现联网的声明
    public AsyncHttpClient mClient = new AsyncHttpClient();

    //保存用户信息的操作:使用sp存储
    public void saveData(User user) {
        //保存到本地
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("UF_ACC",user.UF_ACC);
        editor.putString("UF_AVATAR_URL",user.UF_AVATAR_URL);
        editor.putString("UF_IS_CERT",user.UF_IS_CERT);
        editor.putString("UF_PHONE",user.UF_PHONE);
        editor.commit();//只有提交以后，才可以创建此文件，并保存数据
    }

    //获取用户信息
    public User readUser(){
        User user = new User();
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        user.UF_ACC = sp.getString("UF_ACC","");
        user.UF_AVATAR_URL = sp.getString("UF_AVATAR_URL","");
        user.UF_IS_CERT = sp.getString("UF_IS_CERT","");
        user.UF_PHONE = sp.getString("UF_PHONE","");

        return user;
    }


}
