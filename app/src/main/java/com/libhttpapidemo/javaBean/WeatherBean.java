package com.libhttpapidemo.javaBean;

import com.httpapi.BaseResultEntity;

import java.util.ArrayList;

// 所有的字段必须要和后台返回的一致，包括后台返回的字段是对象的，名字也必须要统一，要不GSON解析不了
// 你也可以自己改字段，但是要用GSON的相关注解注释字段，为了不增加工作了还是老老实实按后台返回的写吧
public class WeatherBean extends BaseResultEntity<WeatherBean> {
    String city;// 字段必须要和后台返回的一致，要不GSON解析不了
    String ganmao;
    String wendu;
    Yesterday yesterday; // 字段必须要和后台返回的一致，要不GSON解析不了
    ArrayList<Forecast> forecast;// 字段必须要和后台返回的一致，要不GSON解析不了

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public Yesterday getYesterday() {
        return yesterday;
    }

    public void setYesterday(Yesterday yesterday) {
        this.yesterday = yesterday;
    }

    public ArrayList<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(ArrayList<Forecast> forecast) {
        this.forecast = forecast;
    }
}
