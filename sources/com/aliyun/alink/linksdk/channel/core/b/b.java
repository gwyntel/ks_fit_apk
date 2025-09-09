package com.aliyun.alink.linksdk.channel.core.b;

/* loaded from: classes2.dex */
public class b {
    public static boolean a(String str) throws ClassNotFoundException {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException e2) {
            a.d("ReflectUtils", "hasClss=" + e2);
            return false;
        } catch (Exception e3) {
            a.d("ReflectUtils", "hasClssEx=" + e3);
            return false;
        }
    }
}
