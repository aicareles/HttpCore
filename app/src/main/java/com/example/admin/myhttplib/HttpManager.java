package com.example.admin.myhttplib;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.admin.myhttplib.callback.BeanNetCallback;
import com.example.admin.myhttplib.callback.ListNetCallback;
import com.example.admin.myhttplib.callback.SimpleNetCallback;
import com.example.admin.myhttplib.response.ListResponse;
import com.example.admin.myhttplib.response.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 *
 * Created by liulei on 2017/3/24.
 */
class HttpManager<T> {

    private static HttpManager ins;

    public static HttpManager getInstance() {
        if(ins == null){
            ins = new HttpManager();
        }
        return ins;
    }

    /**
     * 执行get请求
     * @param tag
     * @param url
     * @param callback
     */
     static void doNetGet(int tag,String url,final SimpleNetCallback callback){
        Executor.THREAD_POOL_EXECUTOR.execute(new NetRunnable(NetRunnable.RequestType.GET, tag, url,null, new SimpleNetCallback() {
            @Override
            public void onSuccess(final int tag, final String entity) {
                Executor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(tag,entity);
                    }
                });
            }

            @Override
            public void onFailure(final int tag, final String msg, final int code) {
                Executor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(tag,msg,code);
                    }
                });
            }
        }));
    }

    /**
     * 执行结果返回实体对象
     * @param tag
     * @param url
     * @param params
     * @param callback
     */
    void doBeanNetPost(int tag, String url, Map<String,String> params, final BeanNetCallback<T> callback){
        Executor.THREAD_POOL_EXECUTOR.execute(new NetRunnable(NetRunnable.RequestType.POST, tag, url,params, new SimpleNetCallback() {
            @Override
            public void onSuccess(final int tag, final String entity) {
                Executor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        //解析
                        Response parsed = JSON.parseObject(entity,Response.class);
                        JSONObject dataObject = JSON.parseObject(entity);
                        parsed.setData(dataObject.getString(Response.DATA));
                        parsed.setStatus(dataObject.getInteger(Response.STATUS));
                        parsed.setMsg(dataObject.getString(Response.MSG));
                        if (callback.getType() instanceof Class) {
                            parsed.parseVo((Class<T>) callback.getType());
                        } else if (callback.getType() instanceof ParameterizedType) {
                            Type[] types = ((ParameterizedType) callback.getType()).getActualTypeArguments();
                            if (types != null && types.length > 0 && types[0] instanceof Class) {
                                parsed.parseVo((Class<T>) types[0]);
                            }
                        }
                        callback.onSuccess(tag, parsed);
                    }
                });
            }

            @Override
            public void onFailure(final int tag, final String msg, final int code) {
                Executor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(tag,msg,code);
                    }
                });
            }
        }));
    }

    /**
     * 执行结果返回列表对象
     * @param tag
     * @param url
     * @param params
     * @param callback
     */
    void doListNetPost(int tag, String url, Map<String,String> params, final ListNetCallback<T> callback){
        Executor.THREAD_POOL_EXECUTOR.execute(new NetRunnable(NetRunnable.RequestType.POST, tag, url,params, new SimpleNetCallback() {
            @Override
            public void onSuccess(final int tag, final String entity) {
                Executor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        ListResponse parsedList = JSON.parseObject(entity,ListResponse.class);
                        JSONObject dataObject = JSON.parseObject(entity);
                        parsedList.setData(dataObject.getString(Response.DATA));
                        parsedList.setStatus(dataObject.getInteger(Response.STATUS));
                        parsedList.setMsg(dataObject.getString(Response.MSG));
                        if (callback.getType() instanceof Class) {
                            parsedList.parseList((Class<T>) callback.getType());
                        } else if (callback.getType() instanceof ParameterizedType) {
                            Type[] types = ((ParameterizedType) callback.getType()).getActualTypeArguments();
                            if (types != null && types.length > 0 && types[0] instanceof Class) {
                                parsedList.parseList((Class<T>) types[0]);
                            }
                        }
                        callback.onSuccess(tag, parsedList);
                    }
                });
            }

            @Override
            public void onFailure(final int tag, final String msg, final int code) {
                Executor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(tag,msg,code);
                    }
                });
            }
        }));
    }


    /**
     * 执行结果返回sting对象
     * @param tag
     * @param url
     * @param params
     * @param callback
     */
    void doSimpleNetPost(int tag, String url, Map<String,String> params, final SimpleNetCallback callback){
        Executor.THREAD_POOL_EXECUTOR.execute(new NetRunnable(NetRunnable.RequestType.POST, tag, url,params, new SimpleNetCallback() {
            @Override
            public void onSuccess(final int tag, final String entity) {
                Executor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(tag,entity);
                    }
                });
            }

            @Override
            public void onFailure(final int tag, final String msg, final int code) {
                Executor.getNetHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(tag,msg,code);
                    }
                });
            }
        }));
    }

    static void cancelHttp(int tag){
//        LLNetRunnable.cancelHttp(tag);
    }
}
