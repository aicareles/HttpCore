package com.example.admin.myhttplib.callback;

import com.example.admin.myhttplib.response.LLResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by LiuLei on 2017/8/19.
 */

public abstract class LLBeanNetCallback<T>{

    private final Type type;

    public LLBeanNetCallback() {
        Type superClass = this.getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) superClass).getActualTypeArguments();
        this.type = types[0];
    }

    public Type getType() {
        return type;
    }
    public void onSuccess(int tag, LLResponse<T> data){};

    public void onFailure(int tag,String msg,int code){};
}
