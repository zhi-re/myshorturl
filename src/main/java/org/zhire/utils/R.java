package org.zhire.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chenqi
 * @Date: 2019.4.16 17:27
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("data", "success");
    }

    public static R error() {
        return error("9999", "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error("9999", msg);
    }

    public static R error(String code, String message) {
        R r = new R();
        r.put("code", code);
        r.put("data", message);
        return r;
    }

    public static R ok(String message) {
        R r = new R();
        r.put("data", message);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}