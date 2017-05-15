package com.example.fy.blog.util;

import android.graphics.Bitmap;

import com.example.fy.blog.bean.Comment;
import com.example.fy.blog.bean.LoginEntity;
import com.example.fy.blog.interfaces.OnBlogCallback;
import com.example.fy.blog.interfaces.OnGetCommentCallback;
import com.example.fy.blog.interfaces.OnGetPortraitCallback;
import com.example.fy.blog.interfaces.OnLoginCallback;
import com.example.fy.blog.interfaces.OnRegisterCallback;
import com.example.fy.blog.interfaces.OnSendCommentCallBack;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fy on 2016/3/27.
 */
public class APIUtils {

    private static final String TAG = "APIUtils";

//    /**
//     * 登录功能
//     * @param username
//     * @param password
//     * @param callback
//     */
//    public static void login(String username, String password, final OnLoginCallback callback) {
//        OkHttpUtils.post().url(Contasts.BASE_URL+ Contasts.URL_LOGIN).addParams(Contasts.KEY_USERNAME,username)
//        .addParams(Contasts.KEY_PASSWORD,password).build().execute(new Callback<String>() {
//            @Override
//            public String parseNetworkResponse(Response response) throws Exception {
//                //在此保存获取的cookie
//                String cookies = response.header(Contasts.KEY_COOKIE);
//                ShareUtils.putCookies(cookies);
//                String content = response.body().string();
//                return content;
//            }
//            @Override
//            public void onError(Call call, Exception e) {
//                //内含测试用例
//                String content = "{'isSuccess':true,'type':0,'errorMsg':null,'content':'用户'," +
//                        "'user':{'username':'Young','portrait':'http://ww2.sinaimg.cn/crop.0.0.1080.1080.1024/d773ebfajw8eum57eobkwj20u00u075w.jpg','new_portrait':''}}";
//                onResponse(content);
//                callback.onLoginFailed(-1,e.getMessage());
//            }
//            @Override
//            public void onResponse(String response) {
//                /*处理数据*/
//                Gson gson = new Gson();
//                LoginEntity entity = gson.fromJson(response, LoginEntity.class);
//                if(entity.isSuccess()) {
//                    callback.onLoginSuccess(entity);
//                }else {
//                    callback.onLoginFailed(entity.getType(),entity.getErrorMsg());
//                }
//            }
//        });
//    }
    /**
     * 登录功能
     * @param username
     * @param password
     * @param callback
     */
    public static void login(String username, String password, final OnLoginCallback callback) {
        OkHttpUtils.post().url(Contasts.BASE_URL+ Contasts.URL_LOGIN).addParams(Contasts.KEY_USERNAME,username)
                .addParams(Contasts.KEY_PASSWORD,password).build().execute(new Callback<String>() {
            @Override
            public String parseNetworkResponse(Response response) throws Exception {
                String content = response.body().string();
                return content;
            }
            @Override
            public void onError(Call call, Exception e) {
                String content = "";
                onResponse(content);
                callback.onLoginFailed(-1,e.getMessage());
            }
            @Override
            public void onResponse(String response) {
                callback.onLoginSuccess(null);
            }
        });
    }

    /**
     * 注册功能
     * @param username
     * @param password
     * @param callback
     */
    public static void register(String username, String password, final OnRegisterCallback callback){
        OkHttpUtils.post().url(Contasts.BASE_URL+ Contasts.URL_REGISTER).addParams(Contasts.KEY_USERNAME,username)
                .addParams(Contasts.KEY_PASSWORD,password).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                callback.OnRegisterFailed();
                //测试功能
                callback.OnRegisterSuccess();
            }
            @Override
            public void onResponse(String response) {
                callback.OnRegisterSuccess();
                if(response.equals("yes")){
                    callback.OnRegisterSuccess();
                }
            }
        });
    }


    /**
     * ExploreListView中的getBlog
     * 获取发现界面的blog信息
     * @param page
     * @param callback
     */
    public static void getBlog(int page,final OnBlogCallback callback){
        OkHttpUtils.post().addParams("page",new Integer(page).toString())
                .url(Contasts.BASE_URL+Contasts.URL_GETBLOG).build().execute(new Callback<String>() {
            @Override
            public String parseNetworkResponse(Response response) throws Exception {
                String content = response.body().toString();
                return content;
            }

            @Override
            public void onError(Call call, Exception e) {
                String content = "[{'blogId':'1','title':'第1篇博客','author':{'username':'fy','portrait':'http://img4.imgtn.bdimg.com/it/u=1222389009,3347821991&fm=23&gp=0.jpg','new_portrait':''},'time':'今天10:00','description':'第1篇博客','content':'1.内容介绍\n" +
                        "\n" +
                        "通过本篇博客你将学到\n" +
                        "①ListView异步加载图片的方式\n" +
                        "②给ImageView设置Tag，解决图片覆盖问题\n" +
                        "③采用LruCache缓存已经加载过的图片 \n" +
                        "④当ListView滚动时不加载图片，滚动停止时才加载图片，从而达到ListView滑动很流畅的效果 \n" +
                        "⑤当ListView加载图片时只加载当前屏幕内可见的条目\n" +
                        "上述5种功能是我们经常在项目中遇到的，这也是用户体验最好的方式，那么今天就和大家一步一步探讨上述5种方式的实现方式。光说没用首先我们来上张图，" +
                        "看看我们究竟实现了什么效果认真看上图，发现实现了图片的异步加载，但是有个问题，请仔细看图片是不是有图片被重复替换即:先显示一张图片然后又变成了另一张图片，" +
                        "看鼠标晃动的位置你会发现有的图片显示好了之后又被替换掉了，" +
                        "有的还不止被替换一次，这是为什么呢？这个问题就要追究到ListView的复用的问题上了，我们想一下当ListView中有100个Item时，它怎么加载？肯定不会傻到一次把100条全部加载完吧。" +
                        "其实它只是将当前可见的条目加载出来。这个思想跟手游有点像，有的手机游戏:控制一个小人不停的往前跑，画面是不断的变化的，其实它也是只加载当前可见的画面，并不是一次全部加载出来的。但是ListView又跟上面这个例子有很大的区别，因为它还有复用这一说，" +
                        "我们看看getView在上面的例子中每个条目的结构都是一样的，无非是显示的图片和两个TextView的文字内容不一样，这样就给ListView的复用提供了前提。在ListView中提供了一个Recycler机制，用来缓存Item的缓冲池，在创建ListView第一屏数据的时候每一个条目都是新创建的，当我们滑动时使最上面的条目滑出屏幕，这时它并不会白白的消失掉，它会到缓冲池中，这样缓冲池的item就不为空了，接着我们再次滑动，它首先会到缓冲池中look\n" +
                        " look有没有现成的item可以直接拿过来用，如果有，将它的数据清除，填充上我们设置的数据。可能大家还不是特别清楚，接下来看看下面这张图注：上图是我参考网上的一张图片画的，为什么不用原图，因为我觉得原图是有问题的，首先我们一共有7个Item当滑动时第8个Item刚漏出时，此时Item的缓存池中是没有Item的，因此这一个Item也是重新创建的，所以一共会创建8个item，当第9个Item过来的时候它会去Recyler的缓存池中去获取发现有就会复用。所以复用是在产生第9个Item开始的。\n" +
                        "\n" +
                        "了解了Adapter的convertView复用之后我们来分析一下为什么会产生图片替换的现象。\n" +
                        "由于网络请求是一个比较耗时的操作，当我们快速滑动ListView的时候有可能出现这样一种情况，某一个位置上的元素进入屏幕后开始开始请求网络图片，但是还没等图片下载完成，它就被移出了屏幕，这样会导致什么结果呢？因为被移出屏幕的Item将会很快被进入屏幕的元素重新利用，" +
                        "而如果这个时候被复用的这个Item的图片请求还没有完成(这个请求是在子线程的)，也就是说被复用的Item中的图片的网络请求还没有完成就被复用了，当被复用的Item中的网络图片请求完成时就会显示在Item的ImageView上，而新进入的Item也同样会发出一条图片的网络请求但它比被复用的Item晚那么一点点，当它请求完成时同样也会显示在Item的ImageView上，由于这个Item是复用的上面的Item所以它将把复用的Item的数据清除然后显示自己的网络请求图片，所以我们会看到上图中ImageView先显示一张图片接着就变为另一张图片的这种现象。\n" +
                        "\n" +
                        "知道了这种情况原因那么我们怎么解决这个问题呢？\n" +
                        "其实解决方案有多种，这里我们采用给ImageView设置Tag的方式，将ImageView的url设置为它的Tag，这样在每次显示图片的时候判断ImageView的tag是不是我们设置的如果是就显示图片。这样Adapter中的getView方法的代码如下','zan':16,'cai':14}," +
                        "{'blogId':'2','title':'第2篇博客','author':{'username':'fy','portrait':'http://img3.imgtn.bdimg.com/it/u=4196147459,3422251328&fm=23&gp=0.jpg','new_portrait':''},'time':'今天11:00','description':'第2篇博客,这篇博客讲解了android中的json的用法，举出了详细的示例进行讲解','content':'本文用于测试第二篇博客','zan':'16','cai':'14'}," +
                        "{'blogId':'3','title':'第3篇博客','author':{'username':'fy','portrait':'http://img1.imgtn.bdimg.com/it/u=2051191299,480014842&fm=23&gp=0.jpg','new_portrait':''},'time':'今天12:00','description':'第3篇博客','content':'本文用于测试第一篇博客','zan':13,'cai':4}," +
                        "{'blogId':'4','title':'第4篇博客','author':{'username':'fy','portrait':'http://img2.imgtn.bdimg.com/it/u=414010100,2869746564&fm=23&gp=0.jpg','new_portrait':''},'time':'今天13:00','description':'第4篇博客','content':'本文用于测试第一篇博客','zan':0,'cai':1}," +
                        "{'blogId':'5','title':'第5篇博客','author':{'username':'fy','portrait':'http://img0.imgtn.bdimg.com/it/u=2747772731,1278874057&fm=23&gp=0.jpg','new_portrait':''},'time':'今天14:00','description':'第5篇博客','content':'本文用于测试第一篇博客','zan':80,'cai':1}," +
                        "{'blogId':'6','title':'第6篇博客','author':{'username':'fy','portrait':'http://img5.imgtn.bdimg.com/it/u=1844930210,326487273&fm=23&gp=0.jpg','new_portrait':''},'time':'今天15:00','description':'第6篇博客','content':'本文用于测试第一篇博客','zan':2,'cai':0}," +
                        "{'blogId':'7','title':'第7篇博客','author':{'username':'fy','portrait':'http://img1.imgtn.bdimg.com/it/u=1710029461,1617053718&fm=23&gp=0.jpg','new_portrait':''},'time':'今天16:00','description':'第7篇博客','content':'本文用于测试第一篇博客','zan':90,'cai':14}," +
                        "{'blogId':'8','title':'第8篇博客','author':{'username':'fy','portrait':'http://img4.imgtn.bdimg.com/it/u=3514639008,2915741372&fm=23&gp=0.jpg','new_portrait':''},'time':'今天17:00','description':'第8篇博客','content':'本文用于测试第一篇博客','zan':22,'cai':14}," +
                        "{'blogId':'9','title':'第9篇博客','author':{'username':'fy','portrait':'http://img2.imgtn.bdimg.com/it/u=1287117437,543031995&fm=23&gp=0.jpg','new_portrait':''},'time':'今天18:00','description':'第9篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'10','title':'第10篇博客','author':{'username':'fy','portrait':'http://img4.imgtn.bdimg.com/it/u=26705486,1141980470&fm=23&gp=0.jpg','new_portrait':''},'time':'今天19:00','description':'第10篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'11','title':'第11篇博客','author':{'username':'fy','portrait':'http://img2.imgtn.bdimg.com/it/u=3436717755,2307538275&fm=23&gp=0.jpg','new_portrait':''},'time':'今天20:00','description':'第11篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'12','title':'第12篇博客','author':{'username':'fy','portrait':'http://img3.imgtn.bdimg.com/it/u=3251606091,3288250948&fm=23&gp=0.jpg','new_portrait':''},'time':'今天21:00','description':'第12篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'13','title':'第13篇博客','author':{'username':'fy','portrait':'http://img1.imgtn.bdimg.com/it/u=2292714531,1526184238&fm=23&gp=0.jpg','new_portrait':''},'time':'今天22:00','description':'第13篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'14','title':'第14篇博客','author':{'username':'fy','portrait':'http://img3.imgtn.bdimg.com/it/u=4196147459,3422251328&fm=23&gp=0.jpg','new_portrait':''},'time':'今天11:00','description':'第2篇博客,这篇博客讲解了android中的json的用法，举出了详细的示例进行讲解','content':'本文用于测试第二篇博客','zan':'16','cai':'14'}," +
                        "{'blogId':'15','title':'第15篇博客','author':{'username':'fy','portrait':'http://img1.imgtn.bdimg.com/it/u=2051191299,480014842&fm=23&gp=0.jpg','new_portrait':''},'time':'今天12:00','description':'第3篇博客','content':'本文用于测试第一篇博客','zan':13,'cai':4}," +
                        "{'blogId':'16','title':'第16篇博客','author':{'username':'fy','portrait':'http://img0.imgtn.bdimg.com/it/u=2747772731,1278874057&fm=23&gp=0.jpg','new_portrait':''},'time':'今天14:00','description':'第5篇博客','content':'本文用于测试第一篇博客','zan':80,'cai':1}," +
                        "{'blogId':'17','title':'第17篇博客','author':{'username':'fy','portrait':'http://img5.imgtn.bdimg.com/it/u=1844930210,326487273&fm=23&gp=0.jpg','new_portrait':''},'time':'今天15:00','description':'第6篇博客','content':'本文用于测试第一篇博客','zan':2,'cai':0}," +
                        "{'blogId':'18','title':'第18篇博客','author':{'username':'fy','portrait':'http://img1.imgtn.bdimg.com/it/u=1710029461,1617053718&fm=23&gp=0.jpg','new_portrait':''},'time':'今天16:00','description':'第7篇博客','content':'本文用于测试第一篇博客','zan':90,'cai':14}," +
                        "{'blogId':'19','title':'第19篇博客','author':{'username':'fy','portrait':'http://img4.imgtn.bdimg.com/it/u=3514639008,2915741372&fm=23&gp=0.jpg','new_portrait':''},'time':'今天17:00','description':'第8篇博客','content':'本文用于测试第一篇博客','zan':22,'cai':14}," +
                        "{'blogId':'20','title':'第20篇博客','author':{'username':'fy','portrait':'http://img2.imgtn.bdimg.com/it/u=1287117437,543031995&fm=23&gp=0.jpg','new_portrait':''},'time':'今天18:00','description':'第9篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'21','title':'第21篇博客','author':{'username':'fy','portrait':'http://img4.imgtn.bdimg.com/it/u=26705486,1141980470&fm=23&gp=0.jpg','new_portrait':''},'time':'今天19:00','description':'第10篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'22','title':'第22篇博客','author':{'username':'fy','portrait':'http://img2.imgtn.bdimg.com/it/u=414010100,2869746564&fm=23&gp=0.jpg','new_portrait':''},'time':'今天13:00','description':'第4篇博客','content':'本文用于测试第一篇博客','zan':0,'cai':1}]";
                onResponse(content);
                callback.OnGetBlogFailed();
            }

            @Override
            public void onResponse(String response) {
                callback.OnGetBlogSuccess(response);
            }
        });
    }
    /**
     * 我的博客
     * 根据名获得用户的blog信息（json数据格式，返回一个对象）
     * @param page
     * @param callback
     */
    public static void getBlog(int page,String username,final OnBlogCallback callback){
        OkHttpUtils.get().addParams("page",new Integer(page).toString()).addParams("username",username)
                .url(Contasts.BASE_URL+Contasts.URL_GETMYSELFBLOG).build().execute(new Callback<String>() {
            @Override
            public String parseNetworkResponse(Response response) throws Exception {
                //获得存储着blog数组的json字符串
                String content = response.body().toString();
                return content;
            }

            @Override
            public void onError(Call call, Exception e) {
                //测试数据
                String content = "[{'blogId':'1','title':'第1篇博客','author':{'username':'fy','portrait':'http://img2.imgtn.bdimg.com/it/u=2054136498,844254002&fm=23&gp=0.jpg','new_portrait':''},'time':'今天10:00','description':'这是我的第1篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14},"+
                        "{'blogId':'2','title':'第2篇博客','author':{'username':'fy2','portrait':'http://img2.imgtn.bdimg.com/it/u=484876316,4068673300&fm=23&gp=0.jpg','new_portrait':''},'time':'今天11:00','description':'这是我的第2篇博客','content':'本文用于测试第二篇博客','zan':10,'cai':14}," +
                        "{'blogId':'3','title':'第3篇博客','author':{'username':'fy3','portrait':'http://img1.imgtn.bdimg.com/it/u=3650873113,1200777046&fm=23&gp=0.jpg','new_portrait':''},'time':'今天12:00','description':'这是我的第3篇博客','content':'本文用于测试第3篇博客','zan':20,'cai':1},"+
                        "{'blogId':'4','title':'第4篇博客','author':{'username':'fy4','portrait':'http://img3.imgtn.bdimg.com/it/u=4185163666,3907515963&fm=23&gp=0.jpg','new_portrait':''},'time':'今天13:00','description':'这是我的第4篇博客','content':'本文用于测试第4篇博客','zan':56,'cai':4}," +
                        "{'blogId':'5','title':'第5篇博客','author':{'username':'fy5','portrait':'http://img3.imgtn.bdimg.com/it/u=79291897,4289360910&fm=23&gp=0.jpg','new_portrait':''},'time':'今天14:00','description':'这是我的第5篇博客','content':'本文用于测试第5篇博客','zan':1,'cai':1}," +
                        "{'blogId':'6','title':'第6篇博客','author':{'username':'fy6','portrait':'http://img0.imgtn.bdimg.com/it/u=2712938075,2666047643&fm=23&gp=0.jpg','new_portrait':''},'time':'今天15:00','description':'这是我的第6篇博客','content':'本文用于测试第6篇博客','zan':19,'cai':5}," +
                        "{'blogId':'7','title':'第7篇博客','author':{'username':'fy7','portrait':'http://img5.imgtn.bdimg.com/it/u=3275152842,1368283791&fm=23&gp=0.jpg','new_portrait':''},'time':'今天16:00','description':'这是我的第7篇博客','content':'本文用于测试第7篇博客','zan':11,'cai':14}," +
                        "{'blogId':'8','title':'第8篇博客','author':{'username':'fy8','portrait':'http://img0.imgtn.bdimg.com/it/u=3020912025,2598223855&fm=23&gp=0.jpg','new_portrait':''},'time':'今天17:00','description':'这是我的第8篇博客','content':'本文用于测试第8篇博客','zan':30,'cai':14}," +
                        "{'blogId':'9','title':'第9篇博客','author':{'username':'fy9','portrait':'http://img0.imgtn.bdimg.com/it/u=2507509565,2126221091&fm=23&gp=0.jpg','new_portrait':''},'time':'今天18:00','description':'这是我的第9篇博客','content':'本文用于测试第9篇博客','zan':126,'cai':10}," +
                        "{'blogId':'10','title':'第10篇博客','author':{'username':'fy10','portrait':'http://img4.imgtn.bdimg.com/it/u=3537230565,980188577&fm=23&gp=0.jpg','new_portrait':''},'time':'今天19:00','description':'这是我的第10篇博客','content':'本文用于测试第10篇博客','zan':19,'cai':1}," +
                        "{'blogId':'11','title':'第11篇博客','author':{'username':'fy11','portrait':'http://img2.imgtn.bdimg.com/it/u=542654367,3657357167&fm=23&gp=0.jpg','new_portrait':''},'time':'今天20:00','description':'这是我的第11篇博客','content':'本文用于测试第11篇博客','zan':6,'cai':1}," +
                        "{'blogId':'12','title':'第12篇博客','author':{'username':'fy12','portrait':'http://img1.imgtn.bdimg.com/it/u=255936494,712841021&fm=23&gp=0.jpg','new_portrait':''},'time':'今天21:00','description':'这是我的第12篇博客','content':'本文用于测试第12篇博客','zan':47,'cai':34}," +
                        "{'blogId':'13','title':'第13篇博客','author':{'username':'fy13','portrait':'http://img4.imgtn.bdimg.com/it/u=1706155849,661338261&fm=23&gp=0.jpg','new_portrait':''},'time':'今天22:00','description':'这是我的第13篇博客','content':'本文用于测试第13篇博客','zan':73,'cai':4}," +
                        "{'blogId':'14','title':'第14篇博客','author':{'username':'fy13','portrait':'http://img3.imgtn.bdimg.com/it/u=1275601152,1920300519&fm=23&gp=0.jpg','new_portrait':''},'time':'今天22:00','description':'这是我的第13篇博客','content':'本文用于测试第13篇博客','zan':62,'cai':5}," +
                        "{'blogId':'15','title':'第15篇博客','author':{'username':'fy13','portrait':'http://img4.imgtn.bdimg.com/it/u=3567910109,2983656753&fm=23&gp=0.jpg','new_portrait':''},'time':'今天22:00','description':'这是我的第13篇博客','content':'本文用于测试第13篇博客','zan':91,'cai':3}," +
                        "{'blogId':'16','title':'第16篇博客','author':{'username':'fy13','portrait':'http://img0.imgtn.bdimg.com/it/u=3218679654,540639324&fm=23&gp=0.jpg','new_portrait':''},'time':'今天22:00','description':'这是我的第13篇博客','content':'本文用于测试第13篇博客','zan':80,'cai':5}," +
                        "{'blogId':'17','title':'第17篇博客','author':{'username':'fy13','portrait':'http://img4.imgtn.bdimg.com/it/u=3528355709,967916823&fm=23&gp=0.jpg','new_portrait':''},'time':'今天22:00','description':'这是我的第13篇博客','content':'本文用于测试第13篇博客','zan':76,'cai':7}," +
                        "{'blogId':'18','title':'第18篇博客','author':{'username':'fy13','portrait':'http://img2.imgtn.bdimg.com/it/u=4267676506,326902154&fm=23&gp=0.jpg','new_portrait':''},'time':'今天22:00','description':'这是我的第13篇博客','content':'本文用于测试第13篇博客','zan':32,'cai':10}," +
                        "{'blogId':'19','title':'第19篇博客','author':{'username':'fy13','portrait':'http://img3.imgtn.bdimg.com/it/u=1260826892,182818765&fm=23&gp=0.jpg','new_portrait':''},'time':'今天22:00','description':'这是我的第13篇博客','content':'本文用于测试第13篇博客','zan':433,'cai':2}," +
                        "{'blogId':'20','title':'第20篇博客','author':{'username':'fy13','portrait':'http://img5.imgtn.bdimg.com/it/u=850063971,3343019444&fm=23&gp=0.jpg','new_portrait':''},'time':'今天22:00','description':'这是我的第13篇博客','content':'本文用于测试第13篇博客','zan':98,'cai':32}," +
                        "{'blogId':'21','title':'第21篇博客','author':{'username':'fy13','portrait':'http://img0.imgtn.bdimg.com/it/u=4113012401,2436004973&fm=23&gp=0.jpg','new_portrait':''},'time':'今天22:00','description':'这是我的第13篇博客','content':'本文用于测试第13篇博客','zan':16,'cai':34}," +
                        "{'blogId':'22','title':'第22篇博客','author':{'username':'fy13','portrait':'http://img1.imgtn.bdimg.com/it/u=376342463,972160886&fm=23&gp=0.jpg','new_portrait':''},'time':'今天22:00','description':'这是我的第13篇博客','content':'本文用于测试第13篇博客','zan':16,'cai':11}]";
                onResponse(content);
                callback.OnGetBlogFailed();
            }

            @Override
            public void onResponse(String response) {
                callback.OnGetBlogSuccess(response);
            }
        });
    }

    /**
     * 获得我的收藏
     * @param page
     * @param username
     * @param callback
     */
    public static void getMyselfCollect(int page, String username,final OnBlogCallback callback){
        OkHttpUtils.post().addParams("page",new Integer(page).toString())
                .url(Contasts.BASE_URL+Contasts.URL_GETMYSELFCOLLECT).build().execute(new Callback<String>() {
            @Override
            public String parseNetworkResponse(Response response) throws Exception {
                String content = response.body().toString();
                return content;
            }

            @Override
            public void onError(Call call, Exception e) {
                String content = "[{'blogId':'1','title':'收藏的第1篇博客','author':{'username':'fy','portrait':'http://img5.imgtn.bdimg.com/it/u=1969967860,549604189&fm=23&gp=0.jpg','new_portrait':''},'time':'今天10:00','description':'第1篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'2','title':'收藏的第2篇博客','author':{'username':'fy','portrait':'http://img4.imgtn.bdimg.com/it/u=4276225775,3815319357&fm=23&gp=0.jpg','new_portrait':''},'time':'今天11:00','description':'第2篇博客,这篇博客讲解了android中的json的用法，举出了详细的示例进行讲解','content':'本文用于测试第二篇博客','zan':16,'cai':14}," +
                        "{'blogId':'3','title':'收藏的第3篇博客','author':{'username':'fy','portrait':'http://img0.imgtn.bdimg.com/it/u=1061513775,4157466451&fm=23&gp=0.jpg','new_portrait':''},'time':'今天12:00','description':'第3篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'4','title':'收藏的第4篇博客','author':{'username':'fy','portrait':'http://img1.imgtn.bdimg.com/it/u=1760888802,2984528081&fm=23&gp=0.jpg','new_portrait':''},'time':'今天13:00','description':'第4篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'5','title':'收藏的第5篇博客','author':{'username':'fy','portrait':'http://img2.imgtn.bdimg.com/it/u=2401282626,1824627164&fm=23&gp=0.jpg','new_portrait':''},'time':'今天14:00','description':'第5篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'6','title':'收藏的第6篇博客','author':{'username':'fy','portrait':'http://img0.imgtn.bdimg.com/it/u=3513199696,959500134&fm=23&gp=0.jpg','new_portrait':''},'time':'今天15:00','description':'第6篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'7','title':'收藏的第7篇博客','author':{'username':'fy','portrait':'http://img4.imgtn.bdimg.com/it/u=155937678,2019936604&fm=23&gp=0.jpg','new_portrait':''},'time':'今天16:00','description':'第7篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'8','title':'收藏的第8篇博客','author':{'username':'fy','portrait':'http://img1.imgtn.bdimg.com/it/u=376342463,972160886&fm=23&gp=0.jpg','new_portrait':''},'time':'今天17:00','description':'第8篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'9','title':'收藏的第9篇博客','author':{'username':'fy','portrait':'http://img0.imgtn.bdimg.com/it/u=4113012401,2436004973&fm=23&gp=0.jpg','new_portrait':''},'time':'今天18:00','description':'第9篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'10','title':'收藏的第10篇博客','author':{'username':'fy','portrait':'http://img5.imgtn.bdimg.com/it/u=850063971,3343019444&fm=23&gp=0.jpg','new_portrait':''},'time':'今天19:00','description':'第10篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'11','title':'收藏的第11篇博客','author':{'username':'fy','portrait':'http://img3.imgtn.bdimg.com/it/u=1260826892,182818765&fm=23&gp=0.jpg','new_portrait':''},'time':'今天20:00','description':'第11篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'12','title':'收藏的第12篇博客','author':{'username':'fy','portrait':'http://img4.imgtn.bdimg.com/it/u=3528355709,967916823&fm=23&gp=0.jpg','new_portrait':''},'time':'今天21:00','description':'第12篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}," +
                        "{'blogId':'13','title':'收藏的第13篇博客','author':{'username':'fy','portrait':'http://img4.imgtn.bdimg.com/it/u=3567910109,2983656753&fm=23&gp=0.jpg','new_portrait':''},'time':'今天22:00','description':'第13篇博客','content':'本文用于测试第一篇博客','zan':16,'cai':14}]";
                onResponse(content);
                callback.OnGetBlogFailed();
            }

            @Override
            public void onResponse(String response) {
                callback.OnGetBlogSuccess(response);
            }
        });

    }
    public static void getMyselfComment(){

    }

    /**
     * 根据blogId获得博客对应的评论
     * @param blogId
     */
    public static void getComment(String blogId, final OnGetCommentCallback callback){
        OkHttpUtils.get().url(Contasts.BASE_URL+Contasts.URL_GETBLOGCOMMENT)
                .addParams("blogId",blogId).build().execute(new Callback<String>() {
            @Override
            public String parseNetworkResponse(Response response) throws Exception {
                String content = response.body().toString();
                return content;
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtil.d("APIUtils","评论连接失败");
                String content = "[{'id':'1','pid':'2','blogId':'1','userId':'2','username':'Young2','avatarUrl':'http://img4.imgtn.bdimg.com/it/u=3567910109,2983656753&fm=23&gp=0.jpg','time':'今天19:00','content':'多谢博主的博客，学到了！'}," +
                        "{'id':'2','pid':'2','blogId':'1','userId':'1','username':'Young1','avatarUrl':'http://img4.imgtn.bdimg.com/it/u=3528355709,967916823&fm=23&gp=0.jpg','time':'今天9:00','content':'写的太好了'}," +
                        "{'id':'3','pid':'1','blogId':'1','userId':'3','username':'Young3','avatarUrl':'http://img3.imgtn.bdimg.com/it/u=1260826892,182818765&fm=23&gp=0.jpg','time':'昨天21:00','content':'第一次见这么详细的博客'}," +
                        "{'id':'4','pid':'2','blogId':'1','userId':'4','username':'Young4','avatarUrl':'http://img5.imgtn.bdimg.com/it/u=850063971,3343019444&fm=23&gp=0.jpg','time':'昨天20:00','content':'多谢博主的博客，学到了！,写的太好了,感谢博主的分享,感觉周围大神太多了。。。我好方'}," +
                        "{'id':'5','pid':'2','blogId':'1','userId':'5','username':'Young5','avatarUrl':'http://img0.imgtn.bdimg.com/it/u=4113012401,2436004973&fm=23&gp=0.jpg','time':'昨天19:00','content':'感觉周围大神太多了。。。我好方'}," +
                        "{'id':'6','pid':'1','blogId':'1','userId':'5','username':'Young5','avatarUrl':'http://img1.imgtn.bdimg.com/it/u=376342463,972160886&fm=23&gp=0.jpg','time':'昨天19:00','content':'感觉周围大神太多了。。。我好方'}," +
                        "{'id':'7','pid':'3','blogId':'1','userId':'5','username':'Young5','avatarUrl':'http://img4.imgtn.bdimg.com/it/u=155937678,2019936604&fm=23&gp=0.jpg','time':'昨天19:00','content':'感觉周围大神太多了。。。我好方'}," +
                        "{'id':'8','pid':'2','blogId':'1','userId':'5','username':'Young5','avatarUrl':'http://img1.imgtn.bdimg.com/it/u=1760888802,2984528081&fm=23&gp=0.jpg','time':'昨天19:00','content':'感觉周围大神太多了。。。我好方'}," +
                        "{'id':'9','pid':'4','blogId':'1','userId':'5','username':'Young5','avatarUrl':'http://img4.imgtn.bdimg.com/it/u=4276225775,3815319357&fm=23&gp=0.jpg','time':'昨天19:00','content':'感觉周围大神太多了。。。我好方'}," +
                        "{'id':'10','pid':'3','blogId':'1','userId':'5','username':'Young5','avatarUrl':'http://img5.imgtn.bdimg.com/it/u=1969967860,549604189&fm=23&gp=0.jpg','time':'昨天19:00','content':'感觉周围大神太多了。。。我好方'}," +
                        "{'id':'11','pid':'2','blogId':'1','userId':'5','username':'Young5','avatarUrl':'http://img0.imgtn.bdimg.com/it/u=1061513775,4157466451&fm=23&gp=0.jpg','time':'昨天19:00','content':'感觉周围大神太多了。。。我好方'}," +
                        "{'id':'12','pid':'5','blogId':'1','userId':'5','username':'Young5','avatarUrl':'http://img0.imgtn.bdimg.com/it/u=1061513775,4157466451&fm=23&gp=0.jpg','time':'昨天19:00','content':'感觉周围大神太多了。。。我好方'}," +
                        "{'id':'13','pid':'5','blogId':'1','userId':'5','username':'Young5','avatarUrl':'http://img0.imgtn.bdimg.com/it/u=2712938075,2666047643&fm=23&gp=0.jpg','time':'昨天19:00','content':'感觉周围大神太多了。。。我好方'}," +
                        "{'id':'14','pid':'1','blogId':'1','userId':'5','username':'Young5','avatarUrl':'http://img2.imgtn.bdimg.com/it/u=2054136498,844254002&fm=23&gp=0.jpg','time':'昨天19:00','content':'感觉周围大神太多了。。。我好方'}]";
                onResponse(content);
                callback.OnGetCommentFailed();
            }

            @Override
            public void onResponse(String response) {
                callback.OnGetCommentSuccess(response);
            }
        });
    }

    /**
     *发送评论信息
     * @param blogId
     * @param callBack
     */
    public static void sendComment(String blogId, final OnSendCommentCallBack callBack){
        OkHttpUtils.post().url(Contasts.BASE_URL+Contasts.URL_SENDCOMMENT).addParams("blogId",blogId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                callBack.OnSendCommentSuccess();
            }

            @Override
            public void onResponse(String response) {
                callBack.OnSendCommentFailed();
            }
        });
    }
}
