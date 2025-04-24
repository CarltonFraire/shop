package com.example.shop;

import lombok.Data;

@Data
public class R{
    private Integer code;
    private String msg;
    private Object data;

    public static R ok(){
        R r = new R();
        r.code = 200;
        return r;
    }
    public static R ok(String msg){
        R r = new R();
        r.code = 200;
        r.msg = msg;
        return r;
    }
    public static R ok(Object data){
        R r = new R();
        r.code = 200;
        r.data = data;
        return r;
    }
    public static R ok(String msg, Object data){
        R ok = R.ok(msg);
        ok.data = data;
        return ok;
    }
    public static R error(String msg){
        R r = new R();
        r.code = 500;
        r.msg = msg;
        return r;
    }

}
