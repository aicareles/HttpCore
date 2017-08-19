//package com.example.admin.myhttplib;
//
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//
///**
// * Created by LiuLei on 2017/8/19.
// */
//
//public abstract class LLResponseListenser<T> implements LLBeanNetCallback<T>{
//
//    private final Type type;
//
//    public LLResponseListenser() {
//        Type superClass = this.getClass().getGenericSuperclass();
//        Type[] types = ((ParameterizedType) superClass).getActualTypeArguments();
//        this.type = types[0];
//    }
//
//    public Type getType() {
//        return type;
//    }
//
//}
