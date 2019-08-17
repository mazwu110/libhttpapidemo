package com.httpapi;

import android.databinding.BaseObservable;

import java.io.Serializable;
/**
 * 数据解析基类,可以根据自己的后台自行修改,正常情况下 把这个注释掉，按实际的修改就行， code，message，data 如果不一致，则要增加他们相应的get方法，否则RxSubscriber，QHttp
 * Api解析会报错找不到方法，当然您也可以自行修改QHTTPAPI 和RxSubscriber那解析部分就不用到了
 */
//public class BaseResultEntity<T> extends BaseObservable implements Serializable {
//    private int code; // 后台约定的code 这里后台返回200是成功
//    private String message; // 后台返回的提示信息
//    private boolean success; // 可有可无
//    private String state; // 可有可无
//    private T data; // 后台返回的数据data
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//}

// 这里只是测试用，具体可以按上面注释的备注自己改
public class BaseResultEntity<T> extends BaseObservable implements Serializable {
    private int status; // 后台约定的code 这里后台返回1000是成功
    private String desc; // 后台返回的提示信息
    private T data; // 后台返回的数据data

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 新增2方法，方便 QHttpApi 解析，不用修改
    public int getCode() {
        if (status == 1000)
            return 200;

        return status;
    }

    public String getMessage() {
        return desc;
    }

}
