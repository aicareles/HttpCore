# 丑陋但简洁使用的HTTP网络请求。</h1><br>

#### 通过优化的异步任务来一步一步实现自己的http网络请求<br>
#### 使用线程池来优化多线程操作<br>

现实开发中的http网络请求一般使用第三方框架，相信很多初学者并不了解网络请求的整个过程<br>
那么该DEMO带你大概了解下整个请求过程是如何进行的。<br>


#### 用法：<br>
###### 1、GET请求<br>
                HttpManager.doNetGet(currentTag, "https://www.baidu.com", new NetCallback() {
                    @Override
                    public void onSuccess(int tag, String entity) {
                        Toast.makeText(MainActivity.this,entity,Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(int tag, String msg, int code) {
                        Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_LONG).show();
                    }
                });

###### 2、简单的POST请求<br>


         findViewById(R.id.postSimple).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentTag = 2;
                    String url = "http://media.e-toys.cn/api/user/loginByMobile";
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("mobile", "18682176281");
                    params.put("password","e10adc3949ba59abbe56e057f20f883e");
                    HttpManager.getInstance().doSimpleNetPost(currentTag, url, params, new NetCallback() {

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

###### 3、返回实体对象的的POST请求


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
                            Log.e(TAG, "onSuccess: "+data.getVo().user_name);
                            Toast.makeText(MainActivity.this,"请求成功",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });


###### 4、返回列表对象的的POST请求


        findViewById(R.id.postList).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentTag = 4;
                    String url = "http://media.e-toys.cn/api/user/devices";
                    HashMap<String,String> params = new HashMap<>();
                    params.put("token",token);
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

###### 5、取消对应的http请求


        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentTag > 0){
                        HttpManager.cancelHttp(currentTag);
                    }
                }
            });
