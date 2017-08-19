package com.example.admin.myhttplib;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.util.List;

/**
 * Created by LiuLei on 2017/8/19.
 */

public class LLListResponse<T> extends LLResponse{

    private List<T> mList;

    public List<T> getList() {
        return mList;
    }

    public void parseList(Class<T> cls) {
        if(TextUtils.isEmpty(getData())){
            return;
        }
        try {
            mList = JSON.parseArray(getData(), cls);
        } catch (JSONException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }
}
