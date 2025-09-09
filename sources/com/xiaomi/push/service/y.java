package com.xiaomi.push.service;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import com.xiaomi.push.hm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class y {

    /* renamed from: a, reason: collision with other field name */
    private static final Map<String, byte[]> f1117a = new HashMap();

    /* renamed from: a, reason: collision with root package name */
    private static ArrayList<Pair<String, byte[]>> f24636a = new ArrayList<>();

    public static void a(String str, byte[] bArr) {
        Map<String, byte[]> map = f1117a;
        synchronized (map) {
            com.xiaomi.channel.commonutils.logger.b.m91a("pending registration request. " + str);
            map.put(str, bArr);
        }
    }

    public static void b(String str, byte[] bArr) {
        synchronized (f24636a) {
            try {
                f24636a.add(new Pair<>(str, bArr));
                if (f24636a.size() > 50) {
                    f24636a.remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void a(XMPushService xMPushService, boolean z2) {
        try {
            Map<String, byte[]> map = f1117a;
            synchronized (map) {
                for (String str : map.keySet()) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("processing pending registration request. " + str);
                    ai.a(xMPushService, str, f1117a.get(str));
                    if (z2 && !com.xiaomi.push.s.a()) {
                        try {
                            Thread.sleep(200L);
                        } catch (Exception unused) {
                        }
                    }
                }
                f1117a.clear();
            }
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.d("fail to deal with pending register request. " + e2);
            xMPushService.a(10, e2);
        }
    }

    public static void a(Context context, int i2, String str) {
        Map<String, byte[]> map = f1117a;
        synchronized (map) {
            try {
                for (String str2 : map.keySet()) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("notify registration error. " + str2);
                    a(context, str2, f1117a.get(str2), i2, str);
                }
                f1117a.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void a(XMPushService xMPushService) throws InterruptedException {
        ArrayList<Pair<String, byte[]>> arrayList;
        try {
            synchronized (f24636a) {
                arrayList = f24636a;
                f24636a = new ArrayList<>();
            }
            boolean zA = com.xiaomi.push.s.a();
            Iterator<Pair<String, byte[]>> it = arrayList.iterator();
            while (it.hasNext()) {
                Pair<String, byte[]> next = it.next();
                ai.a(xMPushService, (String) next.first, (byte[]) next.second);
                if (!zA) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException unused) {
                    }
                }
            }
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.d("meet error when process pending message. " + e2);
            xMPushService.a(10, e2);
        }
    }

    public static void a(Context context, String str, byte[] bArr, int i2, String str2) {
        Intent intent = new Intent("com.xiaomi.mipush.ERROR");
        intent.setPackage(str);
        intent.putExtra("mipush_payload", bArr);
        intent.putExtra("mipush_error_code", i2);
        intent.putExtra("mipush_error_msg", str2);
        context.sendBroadcast(intent, ai.a(str));
    }
}
