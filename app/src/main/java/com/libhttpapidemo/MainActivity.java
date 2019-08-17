package com.libhttpapidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.httpapi.QHttpApi;
import com.httpapi.apiservice.OnHttpApiListener;
import com.httpapi.codeconfig.HttpWhatConfig;
import com.libhttpapidemo.javaBean.Forecast;
import com.libhttpapidemo.javaBean.WeatherBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnHttpApiListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        doGet();
    }

    // key-value get 获取后台数据
    public void doGet() {
        Map<String, Object> params = new HashMap<>();
        params.put("city", "北京");
        //params.put("key", "0132423b3e085efed24b7b8f00d83a91");
        // 第三个参数，需要用到哪个类解析数据结果就传哪个类进去就行，这里采用了泛型解析
        QHttpApi.doGet(Constants.getWeather, params, WeatherBean.class, HttpWhatConfig.CODE_10, this);
        // QHttpApi.doStrGet(Constants.getWeather, params, HttpWhatConfig.CODE_10, MainActivityVM.this);
    }

    // key-value post获取后台数据
    public void doPost() {
        // 另外有QHttpApi.doStrGet方法可用，此返回是后台返回什么，解析出就直接返回给您，需要您自己接收了解析，包括CODE等都返回来了
        Map<String, Object> params = new HashMap<>();
        params.put("city", "上海");
       // params.put("key", "0132423b3e085efed24b7b8f00d83a91");
        // 第三个参数WeatherBean.class，需要用到哪个类来解析数据结果就传哪个类进去就行，这里采用了泛型解析
        QHttpApi.doPost(Constants.getWeather, params, WeatherBean.class, HttpWhatConfig.CODE_11, this);
    }

    @Override
    public void onSuccess(int what, Object data) {
        switch (what) {
            case HttpWhatConfig.CODE_10: {
                // 使用请求数据的时候的class反解析就行
                WeatherBean bean = (WeatherBean) data;
                // 泛型数据解析出来了，想怎么用，自己写逻辑
                ArrayList<Forecast> list = bean.getForecast();
                break;
            }
            // 数据格式一样 就拷贝上面的解析了
            case HttpWhatConfig.CODE_11:
                // 使用请求数据的时候的class反解析就行
                WeatherBean bean = (WeatherBean) data;
                ArrayList<Forecast> list = bean.getForecast();
                break;
        }
    }

    @Override
    public void onFailure(int what, String msg, int code) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
