通过优化的异步任务来一步一步实现自己的http网络请求<br>
使用线程池来优化多线程操作<br>

现实开发中的http网络请求一般使用第三方框架，相信很多初学者并不了解网络请求的整个过程<br>
那么该DEMO带你大概了解下整个请求过程是如何进行的。<br>


用法：<br>
1、GET请求<br>
                LLHttpManager.doNetGet(currentTag, "https://www.baidu.com", new LLNetCallback() {<br>
                    @Override<br>
                    public void onSuccess(int tag, String entity) {<br>
                        Toast.makeText(MainActivity.this,entity,Toast.LENGTH_LONG).show();<br>
                    }<br>

                    @Override<br>
                    public void onFailure(int tag, String msg, int code) {<br>
                        Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_LONG).show();<br>
                    }<br>
                });<br>

2、POST请求<br>
                String url = "http://media.e-toys.cn/api/user/loginByMobile";<br>
                Map<String,String> params = new HashMap<String, String>();<br>
                params.put("mobile", "18682176281");<br>
                params.put("password","e10adc3949ba59abbe56e057f20f883e");<br>
                LLHttpManager.doNetPost(currentTag, url, params, new LLNetCallback() {<br>
                    @Override<br>
                    public void onSuccess(int tag, String entity) {<br>
                        Toast.makeText(MainActivity.this,"请求成功",Toast.LENGTH_LONG).show();<br>
                    }<br>

                    @Override<br>
                    public void onFailure(int tag, String msg, int code) {<br>
                        Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_LONG).show();<br>
                    }<br>
                });<br>



