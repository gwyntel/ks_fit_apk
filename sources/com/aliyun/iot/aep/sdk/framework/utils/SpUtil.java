package com.aliyun.iot.aep.sdk.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class SpUtil {

    /* renamed from: a, reason: collision with root package name */
    private static SharedPreferences f11835a;

    private static SharedPreferences a(Context context) {
        if (f11835a == null) {
            if (context == null) {
                context = AApplication.getInstance();
            }
            f11835a = context.getSharedPreferences("GlobalConfigFW", 0);
        }
        return f11835a;
    }

    public static void clean(Context context) {
        a(context).edit().clear();
    }

    public static boolean getBoolean(Context context, String str, boolean z2) {
        return a(context).getBoolean(str, z2);
    }

    public static int getInt(Context context, String str, int i2) {
        return a(context).getInt(str, i2);
    }

    public static <E extends Serializable> List<E> getList(Context context, String str) {
        try {
            return (List) a(context, str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static long getLong(Context context, String str, long j2) {
        return a(context).getLong(str, j2);
    }

    public static <K extends String, V extends String> Map<K, V> getMap(Context context, String str) {
        try {
            return (Map) new Gson().fromJson(getString(context, str), Map.class);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public static <T extends Serializable> T getObject(Context context, String str) {
        try {
            return (T) a(context, str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getString(Context context, String str) {
        return a(context).getString(str, "");
    }

    public static void putBoolean(Context context, String str, boolean z2) {
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putBoolean(str, z2);
        editorEdit.commit();
    }

    public static void putInt(Context context, String str, int i2) {
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putInt(str, i2);
        editorEdit.commit();
    }

    public static void putList(Context context, String str, List<? extends Serializable> list) {
        try {
            a(context, str, list);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void putLong(Context context, String str, long j2) {
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putLong(str, j2);
        editorEdit.commit();
    }

    public static <K extends String, V extends String> void putMap(Context context, String str, Map<K, V> map) {
        try {
            a(context, str, map);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static <T extends Serializable> void putObject(Context context, String str, T t2) {
        try {
            a(context, str, t2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void putString(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putString(str, str2);
        editorEdit.commit();
    }

    public static void remove(Context context, String str, Object obj) {
        try {
            if (obj instanceof Boolean) {
                putBoolean(context, str, false);
            } else if ((obj instanceof Integer) || (obj instanceof Float)) {
                putInt(context, str, -1);
            } else {
                a(context, str, null);
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static <T extends Serializable> T getObject(Context context, String str, Class<T> cls) {
        try {
            return (T) new Gson().fromJson(getString(context, str), (Class) cls);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static void a(Context context, String str, Object obj) {
        putString(context, str, new Gson().toJson(obj));
    }

    public static void remove(Context context, String str) {
        SharedPreferences sharedPreferencesA = a(context);
        SharedPreferences.Editor editorEdit = sharedPreferencesA.edit();
        if (sharedPreferencesA.contains(str)) {
            editorEdit.remove(str);
        }
    }

    private static Object a(Context context, String str) throws ClassNotFoundException, IOException {
        String string = getString(context, str);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(string.getBytes(), 0));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return object;
    }
}
