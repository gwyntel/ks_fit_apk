package com.alibaba.sdk.android.openaccount.util;

import android.util.Base64;
import com.alibaba.sdk.android.openaccount.model.Result;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class JSONUtils {
    public static Boolean optBoolean(JSONObject jSONObject, String str) {
        return Boolean.valueOf(jSONObject.has(str) ? jSONObject.optBoolean(str) : false);
    }

    public static Integer optInteger(JSONObject jSONObject, String str) {
        if (jSONObject.has(str)) {
            return Integer.valueOf(jSONObject.optInt(str));
        }
        return null;
    }

    public static Long optLong(JSONObject jSONObject, String str) {
        if (jSONObject.has(str)) {
            return Long.valueOf(jSONObject.optLong(str));
        }
        return null;
    }

    public static String optString(JSONObject jSONObject, String str) {
        if (jSONObject.has(str)) {
            return jSONObject.optString(str);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.lang.Byte[]] */
    /* JADX WARN: Type inference failed for: r0v11, types: [java.lang.Character[]] */
    /* JADX WARN: Type inference failed for: r0v5, types: [T] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.Byte[]] */
    /* JADX WARN: Type inference failed for: r0v7, types: [T, java.lang.Character[]] */
    /* JADX WARN: Type inference failed for: r9v0, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v35, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r9v36, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r9v37, types: [char[]] */
    /* JADX WARN: Type inference failed for: r9v38, types: [java.lang.String[]] */
    public static <T> T parseStringValue(String str, Class<T> cls) {
        ?? r02 = (T) null;
        if (str == 0 || cls == null) {
            return null;
        }
        if (String.class.equals(cls)) {
            return str;
        }
        if (Short.TYPE.equals(cls) || Short.class.equals(cls)) {
            return (T) Short.valueOf((String) str);
        }
        if (Integer.TYPE.equals(cls) || Integer.class.equals(cls)) {
            return (T) Integer.valueOf((String) str);
        }
        if (Long.TYPE.equals(cls) || Long.class.equals(cls)) {
            return (T) Long.valueOf((String) str);
        }
        if (Boolean.TYPE.equals(cls) || Boolean.class.equals(cls)) {
            return (T) Boolean.valueOf((String) str);
        }
        if (Float.TYPE.equals(cls) || Float.class.equals(cls)) {
            return (T) Float.valueOf((String) str);
        }
        if (Double.TYPE.equals(cls) || Double.class.equals(cls)) {
            return (T) Double.valueOf((String) str);
        }
        Class cls2 = Byte.TYPE;
        if (cls2.equals(cls) || Byte.class.equals(cls)) {
            return (T) Byte.valueOf((String) str);
        }
        Class cls3 = Character.TYPE;
        int i2 = 0;
        if (cls3.equals(cls) || Character.class.equals(cls)) {
            return (T) Character.valueOf(str.charAt(0));
        }
        if (Date.class.isAssignableFrom(cls)) {
            try {
                return (T) new SimpleDateFormat("yyyyMMddHHmmssSSSZ", Locale.US).parse(str);
            } catch (ParseException e2) {
                throw new RuntimeException("Parse Date error", e2);
            }
        }
        char cCharAt = str.charAt(0);
        if (!cls.isArray()) {
            if (cCharAt != '{') {
                if (cls.isAssignableFrom(String.class)) {
                    return str;
                }
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject((String) str);
                return Map.class.isAssignableFrom(cls) ? (T) toMap(jSONObject) : (T) toPOJO(jSONObject, cls);
            } catch (Exception e3) {
                throw new RuntimeException(e3);
            }
        }
        Class<?> componentType = cls.getComponentType();
        if (cCharAt == '[') {
            try {
                return (T) toPOJOArray(new JSONArray((String) str), componentType);
            } catch (Exception e4) {
                throw new RuntimeException(e4);
            }
        }
        if (String.class.equals(componentType)) {
            return (T) str.split(",");
        }
        if (cls3.equals(componentType)) {
            return (T) str.toCharArray();
        }
        if (Character.class.equals(componentType)) {
            char[] charArray = str.toCharArray();
            int length = charArray.length;
            ?? r03 = (T) new Character[length];
            while (i2 < length) {
                r03[i2] = Character.valueOf(charArray[i2]);
                i2++;
            }
            return r03;
        }
        if (cls2.equals(componentType)) {
            return (T) Base64.decode((String) str, 0);
        }
        if (Byte.class.equals(componentType)) {
            byte[] bArrDecode = Base64.decode((String) str, 0);
            int length2 = bArrDecode.length;
            r02 = (T) new Byte[length2];
            while (i2 < length2) {
                r02[i2] = Byte.valueOf(bArrDecode[i2]);
                i2++;
            }
        }
        return (T) r02;
    }

    public static String toJSONString(Result<String> result) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", result.code);
            jSONObject.put("message", result.message);
            jSONObject.put("data", result.data);
            return jSONObject.toString();
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String toJson(Map<String, Object> map) {
        return toJsonObject(map).toString();
    }

    public static JSONArray toJsonArray(Object[] objArr) {
        JSONArray jSONArray = new JSONArray();
        for (Object obj : objArr) {
            if (obj instanceof Map) {
                jSONArray.put(toJsonObject((Map) obj));
            } else {
                jSONArray.put(obj);
            }
        }
        return jSONArray;
    }

    public static JSONObject toJsonObject(Map<String, ? extends Object> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            for (Map.Entry<String, ? extends Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    if (value instanceof Map) {
                        jSONObject.put(entry.getKey(), toJsonObject((Map) value));
                    } else if (value instanceof List) {
                        jSONObject.put(entry.getKey(), toJsonArray((List<Object>) value));
                    } else if (value.getClass().isArray()) {
                        jSONObject.put(entry.getKey(), toJsonArray((Object[]) value));
                    } else {
                        jSONObject.put(entry.getKey(), value);
                    }
                }
            }
            return jSONObject;
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static List<Object> toList(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(jSONArray.length());
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            Object obj = jSONArray.get(i2);
            if (obj instanceof JSONObject) {
                arrayList.add(toMap((JSONObject) obj));
            } else if (obj instanceof JSONArray) {
                arrayList.add(toList((JSONArray) obj));
            } else {
                arrayList.add(jSONArray.get(i2));
            }
        }
        return arrayList;
    }

    public static Map<String, Object> toMap(JSONObject jSONObject) throws JSONException {
        HashMap map = new HashMap();
        if (jSONObject == null) {
            return map;
        }
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Object objOpt = jSONObject.opt(next);
            if (objOpt instanceof JSONObject) {
                map.put(next, toMap((JSONObject) objOpt));
            } else if (objOpt instanceof JSONArray) {
                map.put(next, toList((JSONArray) objOpt));
            } else {
                map.put(next, objOpt);
            }
        }
        return map;
    }

    public static <T> T toPOJO(JSONObject jSONObject, Class<T> cls) throws IllegalAccessException, JSONException, InstantiationException, SecurityException, IllegalArgumentException {
        if (jSONObject == null || cls == null || cls == Void.TYPE) {
            return null;
        }
        try {
            T tNewInstance = cls.newInstance();
            for (Field field : cls.getFields()) {
                Class<?> type = field.getType();
                String name = field.getName();
                if (jSONObject.has(name)) {
                    if (!type.isPrimitive()) {
                        field.set(tNewInstance, type == String.class ? jSONObject.getString(name) : (type == Boolean.class || type == Integer.class || type == Short.class || type == Long.class || type == Double.class) ? jSONObject.get(name) : type.isArray() ? toPOJOArray(jSONObject.getJSONArray(name), type.getComponentType()) : Map.class.isAssignableFrom(type) ? toMap(jSONObject.getJSONObject(name)) : toPOJO(jSONObject.getJSONObject(name), type));
                    } else if (type == Boolean.TYPE) {
                        field.setBoolean(tNewInstance, jSONObject.getBoolean(name));
                    } else if (type == Byte.TYPE) {
                        field.setByte(tNewInstance, (byte) jSONObject.getInt(name));
                    } else if (type == Character.TYPE) {
                        String string = jSONObject.getString(name);
                        field.setChar(tNewInstance, (string == null || string.length() == 0) ? (char) 0 : string.charAt(0));
                    } else if (type == Short.TYPE) {
                        field.setShort(tNewInstance, (short) jSONObject.getInt(name));
                    } else if (type == Integer.TYPE) {
                        field.setInt(tNewInstance, jSONObject.getInt(name));
                    } else if (type == Long.TYPE) {
                        field.setLong(tNewInstance, jSONObject.getLong(name));
                    } else if (type == Float.TYPE) {
                        field.setFloat(tNewInstance, (float) jSONObject.getDouble(name));
                    } else if (type == Double.TYPE) {
                        field.setDouble(tNewInstance, jSONObject.getDouble(name));
                    }
                }
            }
            return tNewInstance;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public static <T> T[] toPOJOArray(JSONArray jSONArray, Class<T> cls) throws JSONException, ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        if (jSONArray == null || cls == null || cls == Void.TYPE) {
            return null;
        }
        Object objNewInstance = Array.newInstance((Class<?>) cls, jSONArray.length());
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                if (!cls.isPrimitive()) {
                    Array.set(objNewInstance, i2, cls == String.class ? jSONArray.getString(i2) : (cls == Boolean.class || cls == Integer.class || cls == Short.class || cls == Long.class || cls == Double.class) ? jSONArray.get(i2) : cls.isArray() ? toPOJOArray(jSONArray.getJSONArray(i2), cls.getComponentType()) : Map.class.isAssignableFrom(cls) ? toMap(jSONArray.getJSONObject(i2)) : toPOJO(jSONArray.getJSONObject(i2), cls));
                } else if (cls == Boolean.TYPE) {
                    Array.setBoolean(objNewInstance, i2, jSONArray.getBoolean(i2));
                } else if (cls == Byte.TYPE) {
                    Array.setByte(objNewInstance, i2, (byte) jSONArray.getInt(i2));
                } else if (cls == Character.TYPE) {
                    String string = jSONArray.getString(i2);
                    Array.setChar(objNewInstance, i2, (string == null || string.length() == 0) ? (char) 0 : string.charAt(0));
                } else if (cls == Short.TYPE) {
                    Array.setShort(objNewInstance, i2, (short) jSONArray.getInt(i2));
                } else if (cls == Integer.TYPE) {
                    Array.setInt(objNewInstance, i2, jSONArray.getInt(i2));
                } else if (cls == Long.TYPE) {
                    Array.setLong(objNewInstance, i2, jSONArray.getLong(i2));
                } else if (cls == Float.TYPE) {
                    Array.setFloat(objNewInstance, i2, (float) jSONArray.getDouble(i2));
                } else if (cls == Double.TYPE) {
                    Array.setDouble(objNewInstance, i2, jSONArray.getDouble(i2));
                }
            } catch (JSONException e2) {
                throw new RuntimeException(e2);
            }
        }
        return (T[]) ((Object[]) objNewInstance);
    }

    public static String[] toStringArray(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return new String[0];
        }
        String[] strArr = new String[jSONArray.length()];
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            strArr[i2] = jSONArray.optString(i2);
        }
        return strArr;
    }

    /* JADX WARN: Type inference failed for: r2v5, types: [T, java.lang.String] */
    public static Result<String> toStringResult(String str) {
        Result<String> result = new Result<>();
        try {
            JSONObject jSONObject = new JSONObject(str);
            result.code = jSONObject.optInt("code");
            result.data = optString(jSONObject, "data");
            result.message = optString(jSONObject, "message");
            return result;
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static JSONArray toJsonArray(List<Object> list) {
        JSONArray jSONArray = new JSONArray();
        for (Object obj : list) {
            if (obj instanceof Map) {
                jSONArray.put(toJsonObject((Map) obj));
            } else {
                jSONArray.put(obj);
            }
        }
        return jSONArray;
    }
}
