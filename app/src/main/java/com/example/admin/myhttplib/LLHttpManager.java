package com.example.admin.myhttplib;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by liulei on 2017/3/24.
 */
class LLHttpManager<T> {

    private static LLHttpManager ins;

    public static LLHttpManager getInstance() {
        if(ins == null){
            ins = new LLHttpManager();
        }
        return ins;
    }

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

    void doBeanNetPost(int tag, String url, Map<String,String> params, final LLBeanNetCallback<T> callback){
        LLExecutor.THREAD_POOL_EXECUTOR.execute(new LLNetRunnable(LLNetRunnable.RequestType.POST, tag, url,params, new LLNetCallback() {
            @Override
            public void onSuccess(final int tag, final String entity) {
                LLExecutor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        //解析
                        LLResponse parsed = JSON.parseObject(entity,LLResponse.class);
                        JSONObject dataObject = JSON.parseObject(entity);
                        parsed.setData(dataObject.getString(LLResponse.DATA));
                        parsed.setStatus(dataObject.getInteger(LLResponse.STATUS));
                        parsed.setMsg(dataObject.getString(LLResponse.MSG));
                        if (callback.getType() instanceof Class) {
                            parsed.parseVo((Class<T>) callback.getType());
                        } else if (callback.getType() instanceof ParameterizedType) {
                            Type[] types = ((ParameterizedType) callback.getType()).getActualTypeArguments();
                            if (types != null && types.length > 0 && types[0] instanceof Class) {
                                parsed.parseVo((Class<T>) types[0]);
                            }
                        }
//                        T data = (T) jsonObject.getString(LLResponse.DATA);
                        callback.onSuccess(tag, parsed);
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

    void doListNetPost(int tag, String url, Map<String,String> params, final LLListNetCallback<T> callback){
        LLExecutor.THREAD_POOL_EXECUTOR.execute(new LLNetRunnable(LLNetRunnable.RequestType.POST, tag, url,params, new LLNetCallback() {
            @Override
            public void onSuccess(final int tag, final String entity) {
                LLExecutor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        LLListResponse parsedList = JSON.parseObject(entity,LLListResponse.class);
                        JSONObject dataObject = JSON.parseObject(entity);
                        parsedList.setData(dataObject.getString(LLResponse.DATA));
                        parsedList.setStatus(dataObject.getInteger(LLResponse.STATUS));
                        parsedList.setMsg(dataObject.getString(LLResponse.MSG));
                        if (callback.getType() instanceof Class) {
                            parsedList.parseList((Class<T>) callback.getType());
                        } else if (callback.getType() instanceof ParameterizedType) {
                            Type[] types = ((ParameterizedType) callback.getType()).getActualTypeArguments();
                            if (types != null && types.length > 0 && types[0] instanceof Class) {
                                parsedList.parseList((Class<T>) types[0]);
                            }
                        }
//                        T data = (T) jsonObject.getString(LLResponse.DATA);
                        callback.onSuccess(tag, parsedList);
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


    void doSimpleNetPost(int tag, String url, Map<String,String> params, final LLNetCallback callback){
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
