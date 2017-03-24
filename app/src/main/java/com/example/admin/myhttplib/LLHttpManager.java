package com.example.admin.myhttplib;

import java.util.Map;

/**
 *
 * Created by admin on 2017/3/24.
 */

class LLHttpManager {

     static void doNetGet(int tag,String url,final LLNetCallback callback){
        LLExecutor.THREAD_POOL_EXECUTOR.execute(new LLNetRunnable(LLNetRunnable.RequestType.GET, tag, url,null, new LLNetCallback() {
            @Override
            public void onSuccess(final int tag, final String entity) {
                LLExecutor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(tag,entity);
                    }
                });
            }

            @Override
            public void onFailure(final int tag, final String msg, final int code) {
                LLExecutor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(tag,msg,code);
                    }
                });
            }
        }));
    }

    static void doNetPost(int tag, String url, Map<String,String> params, final LLNetCallback callback){
        LLExecutor.THREAD_POOL_EXECUTOR.execute(new LLNetRunnable(LLNetRunnable.RequestType.POST, tag, url,params, new LLNetCallback() {
            @Override
            public void onSuccess(final int tag, final String entity) {
                LLExecutor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(tag,entity);
                    }
                });
            }

            @Override
            public void onFailure(final int tag, final String msg, final int code) {
                LLExecutor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(tag,msg,code);
                    }
                });
            }
        }));
    }

    static void cancelHttp(int tag){
        LLNetRunnable.cancelHttp(tag);
    }
}
