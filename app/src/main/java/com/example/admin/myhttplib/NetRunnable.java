package com.example.admin.myhttplib;

import com.example.admin.myhttplib.callback.SimpleNetCallback;
import com.example.admin.myhttplib.impl.RequestImpl;
import com.example.admin.myhttplib.impl.RequestProxy;
import com.example.admin.myhttplib.impl.RequestTypeLisenter;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * Created by liulei on 2017/3/23.
 */

public class NetRunnable implements Runnable{

    private RequestTypeLisenter mRequest;
    private RequestType method = RequestType.POST;
    private SimpleNetCallback meNetCallback;
    private String mUrl;
    private int mTag = -1;//默认为-1
    private ArrayList<Integer> mTags = new ArrayList<>();//请求tag集合

    private Map<String,String>mParams;

    public enum RequestType {
        GET,
        POST
    }

    public NetRunnable(RequestType method, int tag, String url, Map<String,String>params, SimpleNetCallback callback) {
        this.method = method;
        this.meNetCallback = callback;
        mUrl = url;
        mParams = params;
        mTag = tag;
        //设置动态代理
        mRequest = (RequestTypeLisenter) RequestProxy
                .getInstance()
                .bindProxy(RequestImpl.getInstance());
    }

    public NetRunnable(RequestType method, SimpleNetCallback callback) {
        this.method = method;
        this.meNetCallback = callback;
    }

    @Override
    public void run() {
        if (method.equals(RequestType.POST)) {
            mRequest.doPost(mTag,mUrl,mParams,meNetCallback);
        } else if (method.equals(RequestType.GET)) {
            mRequest.doGet(mTag, mUrl, meNetCallback);
        }
    }

    public void cancelRequest(int tag){
        if(mRequest != null){
            mRequest.cancelRequest(tag);
        }
    }

}
