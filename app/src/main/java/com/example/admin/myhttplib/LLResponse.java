package com.example.admin.myhttplib;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

/**
 * Created by LiuLei on 2017/8/19.
 */

public class LLResponse<T> implements ErrorStatus{

    public static final String DATA = "data";//服务器的静态常量  真实数据
    public static final String STATUS = "status";//服务器的静态常量  状态码
    public static final String MSG = "msg";//服务器的静态常量  返回结果信息

    public static String data   = "";
    private   int    status = UNKNOWN_ERROR;
    private   String msg    = "";

    private T mVo;

    public T getVo() {
        return mVo;
    }

    public void parseVo(Class<T> cls) {
        if(TextUtils.isEmpty(getData())){
            return;
        }
        try {
            mVo = JSON.parseObject(getData(), cls);
        } catch (JSONException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
