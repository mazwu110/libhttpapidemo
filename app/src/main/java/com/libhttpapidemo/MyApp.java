package com.libhttpapidemo;

import android.app.Activity;
import android.app.Application;

import com.httpapi.HttpHelper;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化 QHttpApi 使用的 HttpHelper，包括地址，拦截器等
        new HttpHelper.Builder(this)
                .initOkHttp()
                .createRetrofit(Constants.IP)
                .build();
    }
}
