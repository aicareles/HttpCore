package com.example.admin.myhttplib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.admin.myhttplib.callback.BeanNetCallback;
import com.example.admin.myhttplib.callback.ListNetCallback;
import com.example.admin.myhttplib.callback.SimpleNetCallback;
import com.example.admin.myhttplib.model.Device;
import com.example.admin.myhttplib.model.User;
import com.example.admin.myhttplib.response.ListResponse;
import com.example.admin.myhttplib.response.Response;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String token;//这里因为token是实时获取的   所以注释了  可以自行测试自己的接口哦
    private int currentTag = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTag = 1;
                HttpManager.doNetGet(currentTag, "https://www.baidu.com", new SimpleNetCallback() {
                    @Override
                    public void onSuccess(int tag, String entity) {
                        Toast.makeText(MainActivity.this,entity,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int tag, String msg, int code) {
                        Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        findViewById(R.id.postSimple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTag = 2;
                String url = "http://media.e-toys.cn/api/user/loginByMobile";
                Map<String,String> params = new HashMap<String, String>();
                params.put("mobile", "18682176281");
                params.put("password","e10adc3949ba59abbe56e057f20f883e");
                HttpManager.getInstance().doSimpleNetPost(currentTag, url, params, new SimpleNetCallback() {

                    @Override
                    public void onFailure(int tag, String msg, int code) {
                        Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(int tag, String data) {
                        Log.e(TAG, "onSuccess: "+data);
                        Toast.makeText(MainActivity.this,"请求成功",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        findViewById(R.id.postBean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTag = 3;
                String url = "http://media.e-toys.cn/api/user/loginByMobile";
                Map<String,String> params = new HashMap<String, String>();
                params.put("mobile", "18682176281");
                params.put("password","e10adc3949ba59abbe56e057f20f883e");
                HttpManager.getInstance().doBeanNetPost(currentTag, url, params, new BeanNetCallback<User>() {

                    @Override
                    public void onFailure(int tag, String msg, int code) {
                        Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(int tag, Response<User> data) {
                        Log.e(TAG, "onSuccess: "+data.getVo().getUser_name());
                        token = data.getVo().getUser_token();
                        Toast.makeText(MainActivity.this,"请求成功",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        findViewById(R.id.postList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTag = 4;
                String url = "http://media.e-toys.cn/api/user/devices";
                HashMap<String,String> params = new HashMap<>();
                if(token!=null){
                    params.put("token",token);
                }else {
                    Toast.makeText(MainActivity.this,"token为空，请先点击登录按钮并获取token",Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpManager.getInstance().doListNetPost(currentTag, url, params, new ListNetCallback<Device>() {

                    @Override
                    public void onFailure(int tag, String msg, int code) {
                        Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(int tag, ListResponse<Device> data) {
                        Log.e(TAG, "onSuccess: "+ data.getList().size());
                        Toast.makeText(MainActivity.this,"请求成功",Toast.LENGTH_LONG).show();
                    }

                });
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTag > 0){
                    HttpManager.cancelHttp(currentTag);
                }
            }
        });


    }
}
