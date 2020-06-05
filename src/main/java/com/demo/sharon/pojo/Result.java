package com.demo.sharon.pojo;

//封装数据库返回的返回的数据

import java.util.List;

public class Result {

    private Integer code = 0;
    private String msg = "success";
    private Integer count;   // 数据总条数
    private List data;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
