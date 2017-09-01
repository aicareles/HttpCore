package com.example.admin.myhttplib.callback;

/**
 *
 * Created by liulei on 2017/3/23.
 */

public interface SimpleNetCallback {

    void onSuccess(int tag,String entity);

    void onFailure(int tag,String msg,int code);
}
