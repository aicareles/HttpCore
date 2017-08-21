package com.example.admin.myhttplib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.admin.myhttplib.callback.LLBeanNetCallback;
import com.example.admin.myhttplib.callback.LLListNetCallback;
import com.example.admin.myhttplib.callback.LLNetCallback;
import com.example.admin.myhttplib.model.Device;
import com.example.admin.myhttplib.model.User;
import com.example.admin.myhttplib.response.LLListResponse;
import com.example.admin.myhttplib.response.LLResponse;

import java.util.Arrays;
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
                LLHttpManager.doNetGet(currentTag, "https://www.baidu.com", new LLNetCallback() {
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
                LLHttpManager.getInstance().doSimpleNetPost(currentTag, url, params, new LLNetCallback() {

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
                LLHttpManager.getInstance().doBeanNetPost(currentTag, url, params, new LLBeanNetCallback<User>() {

                    @Override
                    public void onFailure(int tag, String msg, int code) {
                        Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(int tag, LLResponse<User> data) {
                        Log.e(TAG, "onSuccess: "+data.getVo().user_name);
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
                params.put("token",token);
                LLHttpManager.getInstance().doListNetPost(currentTag, url, params, new LLListNetCallback<Device>() {

                    @Override
                    public void onFailure(int tag, String msg, int code) {
                        Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(int tag, LLListResponse<Device> data) {
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
                    LLHttpManager.cancelHttp(currentTag);
                }
            }
        });


    }
}
