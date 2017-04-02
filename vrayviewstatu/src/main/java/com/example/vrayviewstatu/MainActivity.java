package com.example.vrayviewstatu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.example.vrayviewstatu.varyview.VaryViewHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    VaryViewHelper mVaryViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//放数据的父布局
        mVaryViewHelper = new VaryViewHelper.Builder()
                .setDataView(findViewById(R.id.vary_content))//放数据的父布局，逻辑处理在该Activity中处理
                .setLoadingView(LayoutInflater.from(this).inflate(R.layout.layout_loadingview, null))//加载页，无实际逻辑处理
                .setEmptyView(LayoutInflater.from(this).inflate(R.layout.layout_emptyview, null))//空页面，无实际逻辑处理
                .setErrorView(LayoutInflater.from(this).inflate(R.layout.layout_errorview, null))//错误页面
                .setRefreshListener(new ErrorClickListener())//错误页点击刷新实现
                .build();
        findViewById(R.id.data).setOnClickListener(this);
        findViewById(R.id.empty).setOnClickListener(this);
        findViewById(R.id.error).setOnClickListener(this);
        findViewById(R.id.loading).setOnClickListener(this);
    }

    public class ErrorClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mVaryViewHelper.showLoadingView();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.data:
                mVaryViewHelper.showDataView();
                break;
            case R.id.empty:
                mVaryViewHelper.showEmptyView();
                break;
            case R.id.error:
                mVaryViewHelper.showErrorView();
                break;
            case R.id.loading:
                mVaryViewHelper.showLoadingView();
                break;
        }
    }
}
