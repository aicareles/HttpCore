package com.example.admin.myhttplib.impl;

import com.example.admin.myhttplib.callback.SimpleNetCallback;

import java.util.Map;

/**
 * Created by LiuLei on 2017/9/1.
 */

public interface RequestTypeLisenter {

    void doGet(int tag, String url, SimpleNetCallback meNetCallback);

    void doPost(int tag, String url, Map<String, String> params, SimpleNetCallback callback);

    void cancelRequest(int tag);
}
