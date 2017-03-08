package com.sherlochao.common;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class ResponseMap extends HashMap<Object, Object>{
	private ResponseMap() {
    }

    /**
     * 封装的返回体
     * @param result
     * @param msg
     * @param data
     */
    public ResponseMap(Integer result, String msg,JSONArray data) {
        super();
        put("result", result);
        put("msg", msg);
        put("data", data);
    }

    /**
     * 封装的返回体
     * @param result
     * @param msg
     * @param data
     */
    public ResponseMap(Integer result, String msg,JSONObject data) {
        super();
        put("result", result);
        put("msg", msg);
        put("data", data);
    }

    /**
     * 封装的返回体
     * @param result
     * @param msg
     * @param data
     */
    public ResponseMap(Integer result, String msg,List data) {
        super();
        put("result", result);
        put("msg", msg);
        put("data", data);
    }

    /**
     * 封装的返回体
     * @param result
     * @param msg
     */
    public ResponseMap(Integer result, String msg) {
        super();
        put("result", result);
        put("msg", msg);
    }

}
