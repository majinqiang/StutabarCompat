package com.example.lenovo.stutabarcompat.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lenovo.stutabarcompat.R;
import com.example.lenovo.stutabarcompat.fragment.CareMainFragment;
import com.example.lenovo.stutabarcompat.fragment.NewsMainFragment;
import com.example.lenovo.stutabarcompat.fragment.PhotosMainFragment;
import com.example.lenovo.stutabarcompat.fragment.VideoMainFragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private RadioGroup rg_main;
    /**
     * 各个Fragemnt页面的集合
     */
    private ArrayList<BaseFragment> fragments;

    /**
     * 列表中对应的Fragment的位置
     */
    private int position;

    /**
     * 上一个页面的Fragment
     */
    private Fragment content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);

        initFragment();
        //设置RadioGroup状态改变的监听
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rg_main.check(R.id.rb_local_video);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                case R.id.rb_local_video:
                    position = 0;
                    break;
                case R.id.rb_local_audio:
                    position = 1;
                    break;
                case R.id.rb_net_video:
                    position = 2;
                    break;
                case R.id.rb_net_audio:
                    position = 3;
                    break;
            }

            Fragment toFragment = getFragment(position);
            switchFragment(content,toFragment);
        }
    }

    /**
     *
     * @param fromFragment 在点击这个时刻正在显示
     * @param toFragment 在点击后这个时刻马上要显示
     */
    private void switchFragment(Fragment fromFragment,Fragment toFragment) {
        if(toFragment !=content ){//才去显示
            if(toFragment != null){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(!toFragment.isAdded()){
                    //隐藏之前显示 fromFragment
                    if(fromFragment != null){
                        transaction.hide(fromFragment);
                    }
                    //添加toFragment
                    transaction.add(R.id.fl_main_container,toFragment).commit();
//                    transaction.commit();
                }else{
                    // //隐藏之前显示 fromFragment
                    if(fromFragment != null){
                        transaction.hide(fromFragment);
                    }
                    ///显示toFragment
                    transaction.show(toFragment).commit();

                }

                content = toFragment;//videoFragment

            }

        }
//        Fragment fragment = getFragment(0);
//        //1.得到FragmentManger
//        FragmentManager supportFragmentManager = getSupportFragmentManager();
//        //2.开启事务
//        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
//        //3.替换
//        transaction.replace(R.id.fl_main_container, toFragment);
//        //4.提交事务
//        transaction.commit();

    }

    /**
     * 得到Fragment
     * @param position
     * @return
     */
    private Fragment getFragment(int position) {
        return fragments.get(position);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new NewsMainFragment());//本地视频
        fragments.add(new PhotosMainFragment());//本地音频
        fragments.add(new VideoMainFragment());//网络视频
        fragments.add(new CareMainFragment());//网络音频

    }
    private boolean isExit = false;
    private Handler handler = new Handler();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK){
            if(0!= position){
                //把主页选中
                position = 0;
                rg_main.check(R.id.rb_local_video);
                return true;
            }else if(!isExit){
                isExit = true;
                Toast.makeText(MainActivity.this, "再点击一次推出软件", Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                },2000);

                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
