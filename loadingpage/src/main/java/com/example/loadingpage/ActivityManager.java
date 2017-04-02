package com.example.loadingpage;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by shkstart on 2016/11/12 0012.
 * 提供当前应用中所有创建的Activity的管理器 （单例：饿汉式 ， 懒汉式）
 * 涉及到activity的添加、删除指定、删除当前、删除所有、返回栈大小的方法
 */

public class ActivityManager {
    //使用饿汉式创建单例模式
    public static ActivityManager instance = new ActivityManager();

    private  ActivityManager(){

    }
    public static ActivityManager getInstance() {
        return instance;
    }

    //提供操作activity的容器：Stack
    private Stack<Activity> mStack = new Stack<>();

    //Activity的添加
    //Pushes the specified object onto the top of the stack
    public  void addActivity(Activity activity){
        if(activity != null) {
            // Adds the specified object at the end of this vector.
            // activityStack.add(activity);
            mStack.push(activity);
        }

    }

    //删除指定的Activity
    public void deleteActivity(Activity activity) {
        //        for(int i = 0; i < mStack.size(); i++) {
//            if(activity != null && activity.getClass().equals(activityStack.get(i).getClass())){
//                activity.finish();//销毁当前的activity对象
//                activityStack.remove(i);//将指定的activity对象从栈空间移除
//            }
//        }
        //栈空间从上而下，只有一个出口，所以必须从倒序删除，防止漏掉其中要删除的activity
        for(int i = mStack.size()-1; i >= 0 ; i--) {
           if(activity != null && activity.getClass().equals(mStack.get(i).getClass())){
               //销毁当前的activity对象
               activity.finish();
               //将指定的activity对象从栈空间移除
               mStack.remove(i);
           }
        }
    }
    //删除当前的activity(栈顶的activity)
    public  void deleteCurrent(){
        //方式一
//        Activity activity = mStack.get(mStack.size() -1);
//        activity.finish();
//        mStack.remove(mStack.size() -1);
        //方式二
        mStack.lastElement().finish();
        mStack.remove(mStack.lastElement());

        //        activityStack.pop().finish();
    }

    //删除所有的activity
    public  void deleteAll(){
        for(int i = mStack.size()-1; i >=0 ; i--) {
              //销毁所有的activity
              mStack.get(i).finish();
             //从栈里移除所有的activity
              mStack.remove(i);
        }
    }
    //返回栈的大小
    public int getCount(){
        return mStack.size();
    }
}
