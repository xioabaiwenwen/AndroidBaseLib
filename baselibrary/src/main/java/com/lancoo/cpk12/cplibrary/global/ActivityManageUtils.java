package com.lancoo.cpk12.cplibrary.global;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by hzy on 2019/1/10
 * Activity的统一管理类
 *
 * @author Administrator
 */
public class ActivityManageUtils {
    /**
     * Activity栈
     */
    private static Stack<Activity> activityStack;
    /**
     * 单例模式
     */
    private static ActivityManageUtils instance;

    private ActivityManageUtils() {

    }

    /**
     * 单一实例
     */
    public static ActivityManageUtils getInstance() {
        if (instance == null) {
            instance = new ActivityManageUtils();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 当栈的个数为1的时候，判断cls是否在栈内
     */
    public boolean currentActivity(Class<?> cls) {
        if (activityStack.size() != 1) {
            return true;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 当栈的个数为1的时候，判断cls是否在栈内
     */
    public boolean haveActivity(String className) {
        if (activityStack.size() != 1) {
            return true;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().getName().equalsIgnoreCase(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 退出应用程序
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}