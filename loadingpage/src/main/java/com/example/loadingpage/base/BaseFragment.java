package com.example.loadingpage.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loadingpage.LoadingPage;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;

/**
 * Created by majinqiang on 11/11/2016.
 */
public abstract class BaseFragment extends Fragment {

    public LoadingPage mLoadingPage;

    //执行联网操作
    public void showNetFromData(){
//       此处连网操作并不好，所以用一个方法执行
        mLoadingPage.showFromNet();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

                mLoadingPage = new LoadingPage(getActivity()) {
                    @Override
                    public int layoutId(){
                        return getLayoutId();
                    }

                    @Override
                    protected RequestParams params(){
                        return getParams();
                    }

                    @Override
                    protected void onSuccess(ResultState state,View view_success) {
                        //绑定View
                        ButterKnife.bind(BaseFragment.this, view_success);//别忘了绑定布局
                        //初始化视图组件
                        initTitle();
                        //初始化页面数据
                        initData(state.getContent());
                    }

                    @Override
                    protected String url() {
                        return getUrl();
                    }
                };
        return mLoadingPage;

    }

    //初始化页面数据
    protected abstract void initData(String content);
    //初始化视图组件
    protected abstract void initTitle();
    //设置联网请求的参数
    protected abstract RequestParams getParams();
    //返回子视图的layout的id
    protected abstract int getLayoutId();
    //请求参数的路径
    protected abstract String getUrl();


    /**
     * 当Activity创建成功的时候
     * 得到Fragment的视图，对视图进行数据的设置，联网请求
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         //执行连网操作
         showNetFromData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //解绑防止内存泄漏
        ButterKnife.unbind(this);
    }
}
