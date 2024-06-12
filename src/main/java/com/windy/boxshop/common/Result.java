package com.windy.boxshop.common;


import lombok.Data;

//后端返回数据的封装
@Data
public class Result {
    private int code;
    private String msg;
    private long total;
    private Object data;

    public static Result fail() {
        return result(400, "fail", 0L, null);
    }

    public static Result suc() {
        return result(200, "success", 0L, null);
    }

    public static Result suc(Object data) {
        return result(200, "success", 0L, data);
    }

    public static Result suc(Object data, long total) {
        return result(200, "success", total, data);
    }

    private static Result result(int code, String msg, long total, Object data) {
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        res.setData(data);
        return res;
    }

}
