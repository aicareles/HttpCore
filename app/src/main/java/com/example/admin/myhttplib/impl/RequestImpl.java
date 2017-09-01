package com.example.admin.myhttplib.impl;

import android.util.Log;
import android.util.SparseArray;

import com.example.admin.myhttplib.Executor;
import com.example.admin.myhttplib.callback.SimpleNetCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 * Created by LiuLei on 2017/9/1.
 */

public class RequestImpl implements RequestTypeLisenter {

    private final static int TimeOutMillis = 5 * 1000;
    private static SparseArray<HttpURLConnection> connections = new SparseArray<>();

    private static RequestImpl instance = new RequestImpl();


    public static RequestImpl getInstance(){
        return instance;
    }

    @Override
    public void doGet(int tag, String url, SimpleNetCallback meNetCallback) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL mUrl = new URL(url);
            URLConnection urlConnection = mUrl.openConnection();
            httpURLConnection = (HttpURLConnection) urlConnection;
            connections.put(tag,httpURLConnection);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            httpURLConnection.setReadTimeout(TimeOutMillis);
            httpURLConnection.setConnectTimeout(TimeOutMillis);
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                //创建字节输出流对象
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                //定义读取的长度
                int len = 0;
                //定义缓冲区
                byte[] buff = new byte[1024];
                //按照缓冲区的大小，循环读取
                while ((len = is.read(buff)) != -1) {
                    //根据读取的长度写入到os对象中
                    os.write(buff, 0, len);
                }
                //释放资源
                is.close();
                os.close();
                //返回字符串
                String result = new String(os.toByteArray());
                meNetCallback.onSuccess(tag, result);
            } else {
                meNetCallback.onFailure(tag, "无法获取信息", -1);
            }
        } catch (MalformedURLException e) {
            meNetCallback.onFailure(tag, e.getMessage(), -1);
            e.printStackTrace();
        } catch (IOException e) {
            meNetCallback.onFailure(tag, e.getMessage(), -1);
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
                connections.remove(tag);
            }
        }
    }

    @Override
    public void doPost(int tag, String url, Map<String, String> params, SimpleNetCallback callback) {
        HttpURLConnection connection = null;
        byte[] data = getRequestData(params, "utf-8").toString().getBytes();
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            connections.put(tag,connection);
            connection.setConnectTimeout(TimeOutMillis);
            connection.setDoInput(true);//打开输入流，以便从服务器获取数据
            connection.setDoOutput(true);//打开输出流，以便向服务器提交数据
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);//使用post方式不能使用缓存
            //设置请求体的类型是文本类型
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            //设置请求体的长度
            connection.setRequestProperty("Content-Length",String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data);

            int response = connection.getResponseCode();//获得服务器的响应吗
            if(response == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                callback.onSuccess(tag, dealResponseResult(inputStream));
            }else {
                callback.onFailure(tag, "无法获取信息", -1);
            }
        } catch (IOException e) {
            callback.onFailure(tag, e.getMessage(), -1);
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
                connections.remove(tag);
            }
        }

    }

    @Override
    public void cancelRequest(final int tag) {
        final HttpURLConnection con = connections.get(tag);
        if(con!=null){
            Executor.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    con.disconnect();
                    connections.remove(tag);
                    Log.e("LLNetRunnable","取消请求成功");
                }
            });
        }
    }

    /**
     * Function:封装请求体信息
     *
     * @param params 请求体内容
     * @param encode 编码格式
     * @return
     */
    private StringBuffer getRequestData(Map<String, String> params, String encode) {
        if(params == null){
            throw new IllegalArgumentException("Params is not null,please put params");
        }else {
            StringBuffer buffer = new StringBuffer();//存储封装好的请求体信息
            try {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    buffer.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), encode))
                            .append("&");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return buffer;
        }
    }

    /*
    * Function  :   处理服务器的响应结果（将输入流转化成字符串）
    * Param     :   inputStream服务器的响应输入流
    */
    private String dealResponseResult(InputStream inputStream) {
        String result = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[]data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data))!=-1){
                byteArrayOutputStream.write(data,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = new String(byteArrayOutputStream.toByteArray());
        return result;
    }

}
