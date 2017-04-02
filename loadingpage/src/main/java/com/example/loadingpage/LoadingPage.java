package com.example.loadingpage;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.loadingpage.net.NetClient;
import com.example.loadingpage.net.NetUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * Created by majinqiang on 11/14/2016.
 */

public abstract class LoadingPage extends FrameLayout {
    //提供联网操作的4种状态
    //1.提供联网操作的4种状态
    private final int STATE_LOADING = 1;//加载状态
    private final int STATE_ERROR = 2;//联网失败的状态
    private final int STATE_EMPTY = 3;//联网成功，但是返回数据为空的状态
    private final int STATE_SUCCESS = 4;//联网成功，且正确返回数据的状态

    //默认一开始状态是加载状态
    private int state_current = STATE_LOADING;

    //2.提供4个不同的页面
    private View view_loading;
    private View view_error;
    private View view_empty;
    private View view_success;

    Context mContext;
    public LoadingPage(Context context) {
        this(context,null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
         //在创建LoadingPage对象的时候，传入了依赖的Activity的对象
        this.mContext = context;
        //初始化四种View的情况
        initView();
    }
    
    //初始化四种View的情况
    private void initView() {
        //指明视图显示宽高的参数
        //参数全部充满
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        if(view_loading == null) {
            view_loading = View.inflate(mContext,R.layout.page_loading,null);
            //把视图添加到FrameLayout
            addView(view_loading,layoutParams);
        }

        if(view_empty == null) {
            view_empty = View.inflate(mContext,R.layout.page_empty,null);
            //添加到帧布局中
            addView(view_empty,layoutParams);
        }

        if(view_error == null) {
            view_error = View.inflate(mContext,R.layout.page_error,null);
            addView(view_error,layoutParams);
        }

        //根据state_current的值，决定显示具体的那个View
        showSafePage();
    }

    //更新界面的操作需要在主线程中执行
    private void showSafePage() {
        //更新界面的操作需要在主线程中执行
        UIUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //加载显示具体的View
                showPage();
            }
        });
    }

     //在主线程中更新UI
    private void showPage() {
        //如果当前的状态是正确的，就显示，否则隐藏
        view_loading.setVisibility(state_current == STATE_LOADING ? View.VISIBLE : View.GONE);
        view_empty.setVisibility(state_current == STATE_EMPTY ? View.VISIBLE : View.GONE);
        view_error.setVisibility(state_current == STATE_ERROR ? View.VISIBLE : View.GONE);

        if(view_success == null) {
            view_success = View.inflate(mContext,layoutId(),null);
            addView(view_success);
        }
        view_success.setVisibility(state_current == STATE_SUCCESS ? View.VISIBLE : View.GONE);
    }

    //各个子Fragment的layout布局id
    public abstract  int layoutId();
    //枚举类，封装请求的状态
    private ResultState resultState;

    //执行联网操作
    public void showFromNet(){
        final String url = url();
        //判断url是不是为空，假如有的页面不需要网络链接，不需要去网络请求数据
        if(TextUtils.isEmpty(url)) {
            resultState = ResultState.SUCCESS;
            resultState.content = "";
            //加载网页
            loadPage();
            //直接跳出方法
            return;
        }

        //如果需要联网获取数据
         //联网操作是一个耗时的操作，使用延时模拟联网操作
       UIUtils.getHandler().postDelayed(new Runnable() {
           @Override
           public void run() {
               NetUtils.get(url,params(), new NetClient() {
                   @Override
                   public void sucess(String massage) {
                       if(TextUtils.isEmpty(massage)) {
//                           state_current = STATE_EMPTY;
                           //设置当前的状态
                           resultState = ResultState.EMPTY;
                           //如果没数据，设置当亲的内容为空
                           resultState.setContent("");
                       }else {
//                           state_current = STATE_SUCCESS;
                           //设置当前的状态
                           resultState = ResultState.SUCCESS;
                           //设置获取的内容
                           resultState.setContent(massage);
                       }
                       //加载数据
                       loadPage();
                   }

                   @Override
                   public void error(String json) {
                       resultState =ResultState.ERROR;
                       resultState.setContent("");
                       //加载页面，传递数据
                       loadPage();
                   }
               });
           }
       }, 2000);
    }

    //创建设置参数的方法
    protected abstract RequestParams params();

    private void loadPage(){
        switch (resultState) {
            case ERROR:
                state_current = STATE_ERROR;// 失败
                break;
            case EMPTY:
                state_current = STATE_EMPTY;// " "
                break;
            case SUCCESS:
                state_current = STATE_SUCCESS;// 成功
                break;
        }

        //第二次主动联网  联网获取数据为空  联网失败  联网成功
        // 更新界面的操作需要在主线程中执行
        showSafePage();

        //如果当前是联网成功的状态
        if(state_current == STATE_SUCCESS) {
            onSuccess(resultState,view_success);
        }
    }

    //如果当前是联网成功的状态
    protected abstract void onSuccess(ResultState state, View view_success);

    protected abstract String url();

    //提供一个枚举类:将当前联网以后的状态以及可能返回的数据，封装在枚举类中、
    public enum ResultState{

        ERROR(2), EMPTY(3), SUCCESS(4);
        //网络当前的状态
        int state;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        //网络获取的内容
        private String content;

        ResultState(int state) {
            this.state = state;
        }
    }

}
