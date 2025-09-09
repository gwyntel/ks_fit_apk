package com.aliyun.alink.sdk.bone.plugins.app;

import android.os.Bundle;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ConvertUtils {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.os.Bundle[]] */
    /* JADX WARN: Type inference failed for: r2v2, types: [double[]] */
    /* JADX WARN: Type inference failed for: r2v4, types: [long[]] */
    /* JADX WARN: Type inference failed for: r2v5, types: [int[]] */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.lang.String[]] */
    private static Object a(JSONArray jSONArray) {
        boolean[] zArr;
        int length = jSONArray.length();
        int i2 = 0;
        if (length == 0) {
            return new Object[0];
        }
        Object objOpt = jSONArray.opt(0);
        if (objOpt instanceof String) {
            zArr = new String[length];
            while (i2 < length) {
                zArr[i2] = jSONArray.optString(i2);
                i2++;
            }
        } else if (objOpt instanceof Integer) {
            zArr = new int[length];
            while (i2 < length) {
                zArr[i2] = jSONArray.optInt(i2);
                i2++;
            }
        } else if (objOpt instanceof Long) {
            zArr = new long[length];
            while (i2 < length) {
                zArr[i2] = jSONArray.optLong(i2);
                i2++;
            }
        } else if (objOpt instanceof Boolean) {
            zArr = new boolean[length];
            while (i2 < length) {
                zArr[i2] = jSONArray.optBoolean(i2);
                i2++;
            }
        } else if (objOpt instanceof Double) {
            zArr = new double[length];
            while (i2 < length) {
                zArr[i2] = jSONArray.optDouble(i2);
                i2++;
            }
        } else {
            if (!(objOpt instanceof JSONObject)) {
                throw new IllegalArgumentException("unsupported type" + objOpt.getClass() + " in array");
            }
            zArr = new Bundle[length];
            while (i2 < length) {
                zArr[i2] = toBundle(jSONArray.optJSONObject(i2));
                i2++;
            }
        }
        return zArr;
    }

    public static JSONArray fromArray(Object obj) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        int i2 = 0;
        if (obj instanceof String[]) {
            String[] strArr = (String[]) obj;
            int length = strArr.length;
            while (i2 < length) {
                jSONArray.put(strArr[i2]);
                i2++;
            }
        } else if (obj instanceof Bundle[]) {
            Bundle[] bundleArr = (Bundle[]) obj;
            int length2 = bundleArr.length;
            while (i2 < length2) {
                jSONArray.put(fromBundle(bundleArr[i2]));
                i2++;
            }
        } else if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            int length3 = iArr.length;
            while (i2 < length3) {
                jSONArray.put(iArr[i2]);
                i2++;
            }
        } else if (obj instanceof float[]) {
            int length4 = ((float[]) obj).length;
            while (i2 < length4) {
                jSONArray.put(r5[i2]);
                i2++;
            }
        } else if (obj instanceof double[]) {
            double[] dArr = (double[]) obj;
            int length5 = dArr.length;
            while (i2 < length5) {
                jSONArray.put(dArr[i2]);
                i2++;
            }
        } else {
            if (!(obj instanceof boolean[])) {
                throw new IllegalArgumentException("Unknown array type " + obj.getClass());
            }
            boolean[] zArr = (boolean[]) obj;
            int length6 = zArr.length;
            while (i2 < length6) {
                jSONArray.put(zArr[i2]);
                i2++;
            }
        }
        return jSONArray;
    }

    public static JSONObject fromBundle(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (bundle == null) {
            return jSONObject;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj != null) {
                if (obj.getClass().isArray()) {
                    jSONObject.put(str, fromArray(obj));
                } else if (obj instanceof String) {
                    jSONObject.put(str, (String) obj);
                } else if (obj instanceof Number) {
                    if (obj instanceof Integer) {
                        jSONObject.put(str, obj);
                    } else {
                        jSONObject.put(str, ((Number) obj).doubleValue());
                    }
                } else if (obj instanceof Boolean) {
                    jSONObject.put(str, (Boolean) obj);
                } else {
                    if (!(obj instanceof Bundle)) {
                        throw new IllegalArgumentException("Could not convert " + obj.getClass());
                    }
                    jSONObject.put(str, fromBundle((Bundle) obj));
                }
            }
        }
        return jSONObject;
    }

    public static Bundle toBundle(JSONObject jSONObject) throws JSONException {
        Bundle bundle = new Bundle(jSONObject.length());
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Object obj = jSONObject.get(next);
            if (obj instanceof String) {
                bundle.putString(next, (String) obj);
            } else if (obj instanceof Boolean) {
                bundle.putBoolean(next, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Integer) {
                bundle.putInt(next, ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                bundle.putDouble(next, ((Long) obj).longValue());
            } else if (obj instanceof Double) {
                bundle.putDouble(next, ((Double) obj).doubleValue());
            } else if (obj instanceof JSONObject) {
                bundle.putBundle(next, toBundle((JSONObject) obj));
            } else if (obj instanceof JSONArray) {
                Object objA = a((JSONArray) obj);
                if (objA instanceof String[]) {
                    bundle.putStringArray(next, (String[]) objA);
                } else if (objA instanceof int[]) {
                    bundle.putIntArray(next, (int[]) objA);
                } else if (objA instanceof long[]) {
                    bundle.putLongArray(next, (long[]) objA);
                } else if (objA instanceof boolean[]) {
                    bundle.putBooleanArray(next, (boolean[]) objA);
                } else if (objA instanceof double[]) {
                    bundle.putDoubleArray(next, (double[]) objA);
                } else if (objA instanceof Bundle[]) {
                    bundle.putParcelableArray(next, (Bundle[]) objA);
                } else {
                    if (!(objA instanceof Object[])) {
                        throw new IllegalArgumentException("unsupported array type : " + objA.getClass());
                    }
                    bundle.putStringArray(next, new String[0]);
                }
            } else if (!jSONObject.isNull(next)) {
                throw new IllegalArgumentException("Could not convert object with key: " + next + ".");
            }
        }
        return bundle;
    }
}
