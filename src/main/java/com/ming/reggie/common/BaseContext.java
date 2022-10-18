package com.ming.reggie.common;

/**
 * 基于ThreadLocal的封装工具类，用来保存和获取登录用户的id
 */
public class BaseContext {

    private  static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
