package com.xiaomi.push.service;

import android.app.NotificationChannel;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import com.huawei.hms.framework.common.ContainerUtils;
import com.xiaomi.push.C0472r;
import com.xiaomi.push.service.ax;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class f {

    /* renamed from: a, reason: collision with other field name */
    private static final int[] f1085a = {1, 2, 4, 8, 16, 32, 64};

    /* renamed from: a, reason: collision with root package name */
    private static final SparseArray<ax.a<String, String, String>> f24595a = new g(5);

    /* renamed from: b, reason: collision with root package name */
    private static final SparseArray<Integer> f24596b = new h(5);

    private static boolean a(int i2, int i3) {
        return i2 >= 4 || (i3 & 2) > 0 || (i3 & 1) > 0 || (i3 & 8) > 0 || (i3 & 16) > 0;
    }

    static int a(String str, String str2) {
        int i2 = m778a(str, str2, 8) ? 8 : 0;
        if (m778a(str, str2, 16)) {
            i2 |= 16;
        }
        if (m778a(str, str2, 1)) {
            i2 |= 1;
        }
        if (m778a(str, str2, 2)) {
            i2 |= 2;
        }
        return m778a(str, str2, 4) ? i2 | 4 : i2;
    }

    static void a(Context context, String str, String str2, int i2, String str3, boolean z2, int i3) {
        if (com.xiaomi.push.j.m550a(context) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            int iA = com.xiaomi.push.s.a(str3, 0);
            boolean zA = a(i2, iA);
            if (z2) {
                a(str, str2, iA, i3);
                if (zA) {
                    synchronized (f.class) {
                        a(a(context), iA, str2);
                    }
                    return;
                }
                return;
            }
            synchronized (f.class) {
                try {
                    SharedPreferences sharedPreferencesA = a(context);
                    if (zA || sharedPreferencesA.contains(str2)) {
                        a(sharedPreferencesA, iA, str, str2, i3);
                        if (zA) {
                            a(sharedPreferencesA, iA, str2);
                        } else {
                            a(sharedPreferencesA, str2);
                        }
                    }
                } finally {
                }
            }
            return;
        }
        if (com.xiaomi.push.j.m550a(context)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("ChannelPC: can`t setup permission with permissionCode:" + String.valueOf(str3) + " channelId:" + String.valueOf(str2) + " targetPkg:" + str);
        }
    }

    static void a(Context context, String str) {
        List<NotificationChannel> listM747a;
        if (!com.xiaomi.push.j.m550a(context) || TextUtils.isEmpty(str) || (listM747a = aw.a(context, str).m747a()) == null) {
            return;
        }
        synchronized (f.class) {
            try {
                SharedPreferences sharedPreferencesA = a(context);
                ArrayList arrayList = new ArrayList();
                Iterator<NotificationChannel> it = listM747a.iterator();
                while (it.hasNext()) {
                    String str2 = (String) com.xiaomi.push.bk.a(androidx.core.app.i.a(it.next()), "mId");
                    if (!TextUtils.isEmpty(str2) && sharedPreferencesA.contains(str2)) {
                        arrayList.add(str2);
                    }
                }
                if (arrayList.size() > 0) {
                    a(sharedPreferencesA, arrayList);
                }
            } finally {
            }
        }
    }

    static void a(String str, String str2, int i2, int i3) {
        for (int i4 : f1085a) {
            if ((f24596b.get(i4).intValue() & i3) == 0) {
                a(str, str2, i4, (i2 & i4) > 0);
            } else {
                com.xiaomi.channel.commonutils.logger.b.m91a("ChannelPermissions.grantPermission:" + str + ":" + str2 + ": <" + i4 + "> :stoped by userLock");
            }
        }
    }

    private static void a(String str, String str2, int i2, boolean z2) {
        com.xiaomi.channel.commonutils.logger.b.m91a("ChannelPermissions.grantPermission:" + str + ":" + str2 + ": <" + i2 + ContainerUtils.KEY_VALUE_DELIMITER + z2 + "> :" + ax.a(C0472r.m684a(), str, str2, f24595a.get(i2), z2));
    }

    public static int a(String str, String str2, int i2) {
        return ax.a(C0472r.m684a(), str, str2, f24595a.get(i2));
    }

    /* renamed from: a, reason: collision with other method in class */
    public static Bundle m777a(String str, String str2) {
        return ax.a(C0472r.m684a(), str, str2);
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m778a(String str, String str2, int i2) {
        boolean z2 = ax.a(C0472r.m684a(), str, str2, f24595a.get(i2)) == 1;
        com.xiaomi.channel.commonutils.logger.b.m91a("ChannelPermissions.checkPermission:" + str + ":" + str2 + ": <" + i2 + ContainerUtils.KEY_VALUE_DELIMITER + z2 + ">");
        return z2;
    }

    private static void a(SharedPreferences sharedPreferences, int i2, String str, String str2, int i3) {
        if (sharedPreferences.getInt(str2, 0) != i2) {
            a(str, str2, i2, i3);
        }
    }

    private static void a(SharedPreferences sharedPreferences, int i2, String str) {
        sharedPreferences.edit().putInt(str, i2).commit();
    }

    private static void a(SharedPreferences sharedPreferences, String str) {
        a(sharedPreferences, new i(str));
    }

    private static void a(SharedPreferences sharedPreferences, List<String> list) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            editorEdit.remove(it.next());
        }
        editorEdit.commit();
    }

    private static SharedPreferences a(Context context) {
        return context.getSharedPreferences("ch_permission_cache_file", 0);
    }
}
