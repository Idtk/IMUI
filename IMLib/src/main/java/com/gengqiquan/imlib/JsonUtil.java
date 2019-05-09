/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gengqiquan.imlib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 描述：json处理类.
 *
 * @author gengqiquan
 * @version v1.0
 * @date：2016-11-1 09:41:49
 */
public class JsonUtil {

    /**
     * 描述：将对象转化为json.
     *
     * @return
     */
    public static String toJson(Object src) {
        String json = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            json = gson.toJson(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 描述：将对象转化为json.
     *
     * @return
     */
    public static String toJson(Map src) {
        String json = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            json = gson.toJson(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 描述：将列表转化为json.
     *
     * @param list
     * @return
     */
    public static String toJson(List<?> list) {
        String json = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            json = gson.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static boolean getBoolean(String json, String key) {
        boolean value = false;
        try {
            JSONObject obj = new JSONObject(json);
            value = obj.optBoolean(key);
        } catch (JSONException e) {
        }
        return value;
    }

    public static boolean getBooleanDefaultTrue(String json, String key) {
        boolean value = true;
        try {
            JSONObject obj = new JSONObject(json);
            value = obj.optBoolean(key,true);
        } catch (JSONException e) {
        }
        return value;
    }

    /**
     * 描述：将json转化为列表.
     *
     * @param json
     * @param typeToken new TypeToken<ArrayList<T>>() {}.getType();
     * @return
     */
    public static <T> List<T> fromJson(String json, TypeToken<List<T>> typeToken) {
        List<T> list = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            Type type = typeToken.getType();
            list = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 描述：将json转化为对象.
     *
     * @param json
     * @param clazz
     * @return
     */
    public static Object fromJson(String json, Class clazz) {
        Object obj = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            obj = gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 描述：将String转化为JSONObject.
     *
     * @param str
     * @return
     */
    public static JSONObject toJSONObject(String str) {
        JSONObject json = null;
        try {
            json = new JSONObject(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 描述：将String转化为JSONArray.
     *
     * @param str
     * @return
     */
    public static JSONArray toJSONArray(String str) {
        JSONArray json = null;
        try {
            json = new JSONArray(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 描述：根据key取值
     *
     * @param json
     * @return
     */
    public static Object getObject(String json, String key) {
        Object str = null;
        try {
            JSONObject obj = new JSONObject(json);
            str = obj.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (str == null) {
            str = "";
        }
        return str;
    }

    /**
     * 描述：根据key值获取String.
     *
     * @return
     */
    public static String getString(String json, String key) {
        String value = null;
        try {
            JSONObject obj = new JSONObject(json);
            value = obj.getString(key);
        } catch (JSONException e) {
            value = "";
        }
        return value;
    }

    /**
     * 描述：根据key值获取Integer.
     *
     * @return
     */
    public static Integer getInt(String json, String key) {
        Integer value = -1;
        try {
            JSONObject obj = new JSONObject(json);
            value = obj.getInt(key);
        } catch (JSONException e) {
            value = -1;
        }
        return value;
    }

    /**
     * 描述：根据key值获取Double.
     *
     * @return
     */
    public static Double getDouble(String json, String key) {
        Double value = null;
        try {
            JSONObject obj = new JSONObject(json);
            value = obj.getDouble(key);
        } catch (JSONException e) {
            value = 0.0;
        }
        return value;
    }

    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

}
