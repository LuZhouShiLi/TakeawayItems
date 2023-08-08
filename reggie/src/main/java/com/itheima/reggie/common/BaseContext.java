package com.itheima.reggie.common;

/**
 * 基于ThreadLocal封装的工具类  用户保存和获取当前登录用户Id  在用户登录之后就保存id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);// 设置id
    }

    public static Long getCurrentId(){
        return threadLocal.get();// 获取id
    }

}
