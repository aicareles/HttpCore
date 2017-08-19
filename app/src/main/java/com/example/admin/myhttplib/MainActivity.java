package com.example.admin.myhttplib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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

        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTag = 2;
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

                    //                    @Override
//                    public void onSuccess(int tag, User user) {
//                        Log.e(TAG, "onSuccess: "+entity);
//                        Toast.makeText(MainActivity.this,"请求成功",Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onFailure(int tag, String msg, int code) {
//                        Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_LONG).show();
//                    }
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
