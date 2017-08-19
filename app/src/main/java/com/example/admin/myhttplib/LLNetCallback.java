package com.example.admin.myhttplib;

/**
 *
 * Created by liulei on 2017/3/23.
 */

public interface LLNetCallback {

    void onSuccess(int tag,String entity);

    void onFailure(int tag,String msg,int code);
}
