package com.umeng.message;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.bc;
import com.umeng.message.common.UPLog;
import com.umeng.message.proguard.aq;
import com.umeng.message.proguard.f;
import com.umeng.message.proguard.h;
import com.umeng.message.proguard.x;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes4.dex */
public class MessageSharedPrefs {

    /* renamed from: d, reason: collision with root package name */
    private static volatile MessageSharedPrefs f22599d;

    /* renamed from: a, reason: collision with root package name */
    public final Context f22600a;

    /* renamed from: b, reason: collision with root package name */
    public final aq f22601b = new aq("push");

    /* renamed from: c, reason: collision with root package name */
    public Boolean f22602c = null;

    private MessageSharedPrefs(Context context) {
        this.f22600a = context.getApplicationContext();
    }

    public static MessageSharedPrefs getInstance(Context context) {
        if (f22599d == null) {
            synchronized (MessageSharedPrefs.class) {
                try {
                    if (f22599d == null) {
                        f22599d = new MessageSharedPrefs(context);
                    }
                } finally {
                }
            }
        }
        return f22599d;
    }

    private void setMessageAppKey(String str) {
        if (f.b(this.f22600a)) {
            if (TextUtils.isEmpty(str)) {
                UPLog.e("Prefs", "appkey is empty!");
            } else {
                this.f22601b.a("appkey", str);
            }
        }
    }

    private void setMessageAppSecret(String str) {
        if (f.b(this.f22600a)) {
            if (TextUtils.isEmpty(str)) {
                UPLog.e("Prefs", "message secret is empty!");
            } else {
                this.f22601b.a("message_secret", str);
            }
        }
    }

    private void setMessageChannel(String str) {
        if (f.b(this.f22600a)) {
            this.f22601b.a("channel", str);
        }
    }

    public final boolean a() {
        Throwable th;
        long jB;
        try {
            jB = this.f22601b.b(com.umeng.analytics.pro.f.f21694p, 0L);
            if (jB > 0) {
                try {
                    UPLog.d("Prefs", "today first start:", new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss, Locale.getDefault()).format(new Date(jB)));
                } catch (Throwable th2) {
                    th = th2;
                    UPLog.e("Prefs", th);
                    return f.a(jB);
                }
            }
        } catch (Throwable th3) {
            th = th3;
            jB = 0;
        }
        return f.a(jB);
    }

    public final int b() {
        return this.f22601b.b("notification_number", 1);
    }

    public final String c() {
        return this.f22601b.b("appkey", "");
    }

    public final int d() {
        return this.f22601b.b("tag_remain", 64);
    }

    public final String e() {
        String strB = this.f22601b.b("service_class", "");
        if (!TextUtils.isEmpty(strB)) {
            try {
                Class.forName(strB);
                return strB;
            } catch (Throwable unused) {
                UPLog.e("Prefs", "custom service not exist:", strB, "if has removed. pls invoke PushAgent.setPushIntentServiceClass(null)");
            }
        }
        return "";
    }

    public final String f() {
        return this.f22601b.b("last_click_msg_id", "");
    }

    public final int g() {
        return this.f22601b.b("mute_duration", 60);
    }

    public final int h() {
        return this.f22601b.b("notification_vibrate", 0);
    }

    public final int i() {
        return this.f22601b.b("notification_light", 0);
    }

    public final int j() {
        return this.f22601b.b("notification_sound", 0);
    }

    public final String k() {
        return this.f22601b.b(RemoteMessageConst.DEVICE_TOKEN, "");
    }

    public final boolean l() {
        return this.f22601b.b("l_u_e", false);
    }

    public final long m() {
        return this.f22601b.b("smart_lc", 0L);
    }

    public final int n() {
        return this.f22601b.b("re_pop_cfg", 0);
    }

    public final int o() {
        Calendar calendar = Calendar.getInstance();
        String str = String.format(Locale.getDefault(), "%d.%d.", Integer.valueOf(calendar.get(1)), Integer.valueOf(calendar.get(6)));
        String strB = this.f22601b.b("re_pop_times", "");
        if (strB.startsWith(str)) {
            try {
                return Integer.parseInt(strB.replace(str, ""));
            } catch (Throwable unused) {
            }
        }
        return 0;
    }

    public final void b(String str) {
        this.f22601b.a(str + bc.ba);
        this.f22601b.a(str + "ts");
    }

    public final void c(String str) {
        this.f22601b.a("last_click_msg_id", str);
    }

    public final void a(String str, String str2, int i2, long j2) {
        Cursor cursorQuery = null;
        try {
            Application applicationA = x.a();
            try {
                this.f22600a.getContentResolver().delete(h.a(this.f22600a), "type=?", new String[]{str2});
            } catch (Exception e2) {
                UPLog.e("Prefs", e2);
            }
            String[] strArr = {str, str2, String.valueOf(i2)};
            cursorQuery = applicationA.getContentResolver().query(h.a(applicationA), null, "alias=? and type=? and exclusive=?", strArr, "time desc");
            ContentValues contentValues = new ContentValues();
            contentValues.put("time", Long.valueOf(System.currentTimeMillis()));
            contentValues.put(RemoteMessageConst.TTL, Long.valueOf(j2));
            contentValues.put("type", str2);
            contentValues.put(PushConstants.SUB_ALIAS_STATUS_NAME, str);
            contentValues.put("exclusive", Integer.valueOf(i2));
            if (cursorQuery != null && cursorQuery.getCount() > 0) {
                this.f22600a.getContentResolver().update(h.a(applicationA), contentValues, "alias=? and type=? and exclusive=?", strArr);
            } else {
                this.f22600a.getContentResolver().insert(h.a(applicationA), contentValues);
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        if (cursorQuery != null) {
            try {
                cursorQuery.close();
            } catch (Throwable th) {
                UPLog.e("Prefs", th);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0066 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String a(int r10, java.lang.String r11) throws java.lang.Throwable {
        /*
            r9 = this;
            java.lang.String r0 = "alias"
            java.lang.String r1 = "Prefs"
            r2 = 0
            java.lang.String r6 = "type=? and exclusive=?"
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            java.lang.String[] r7 = new java.lang.String[]{r11, r10}     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            java.lang.String r8 = "time desc"
            android.content.Context r10 = r9.f22600a     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            android.content.ContentResolver r3 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            android.content.Context r10 = r9.f22600a     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            android.net.Uri r4 = com.umeng.message.proguard.h.a(r10)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            java.lang.String[] r5 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            android.database.Cursor r10 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            if (r10 == 0) goto L47
            int r11 = r10.getCount()     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            if (r11 > 0) goto L2e
            goto L47
        L2e:
            r10.moveToFirst()     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            int r11 = r10.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            java.lang.String r11 = r10.getString(r11)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            r10.close()     // Catch: java.lang.Throwable -> L3d
            goto L41
        L3d:
            r10 = move-exception
            com.umeng.message.common.UPLog.e(r1, r10)
        L41:
            return r11
        L42:
            r11 = move-exception
            r2 = r10
            goto L64
        L45:
            r11 = move-exception
            goto L56
        L47:
            if (r10 == 0) goto L51
            r10.close()     // Catch: java.lang.Throwable -> L4d
            goto L51
        L4d:
            r10 = move-exception
            com.umeng.message.common.UPLog.e(r1, r10)
        L51:
            return r2
        L52:
            r11 = move-exception
            goto L64
        L54:
            r11 = move-exception
            r10 = r2
        L56:
            com.umeng.message.common.UPLog.e(r1, r11)     // Catch: java.lang.Throwable -> L42
            if (r10 == 0) goto L63
            r10.close()     // Catch: java.lang.Throwable -> L5f
            goto L63
        L5f:
            r10 = move-exception
            com.umeng.message.common.UPLog.e(r1, r10)
        L63:
            return r2
        L64:
            if (r2 == 0) goto L6e
            r2.close()     // Catch: java.lang.Throwable -> L6a
            goto L6e
        L6a:
            r10 = move-exception
            com.umeng.message.common.UPLog.e(r1, r10)
        L6e:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.MessageSharedPrefs.a(int, java.lang.String):java.lang.String");
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:40:0x00f8 -> B:61:0x010b). Please report as a decompilation issue!!! */
    public final boolean a(int i2, String str, String str2) throws Throwable {
        Throwable th;
        Cursor cursorQuery;
        int count;
        try {
            try {
                try {
                    String[] strArr = {str2, str, String.valueOf(i2)};
                    UPLog.i("Prefs", "type", str2, PushConstants.SUB_ALIAS_STATUS_NAME, str, "exclusive", Integer.valueOf(i2));
                    cursorQuery = this.f22600a.getContentResolver().query(h.a(this.f22600a), null, "type=? and alias=? and exclusive=?", strArr, null);
                } catch (Exception e2) {
                    e = e2;
                    cursorQuery = null;
                } catch (Throwable th2) {
                    th = th2;
                    Cursor cursor = null;
                    if (0 != 0) {
                        try {
                            cursor.close();
                            throw th;
                        } catch (Throwable th3) {
                            UPLog.e("Prefs", th3);
                            throw th;
                        }
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                UPLog.e("Prefs", th4);
            }
            if (cursorQuery == null) {
                if (cursorQuery != null) {
                    try {
                        cursorQuery.close();
                    } catch (Throwable th5) {
                        UPLog.e("Prefs", th5);
                    }
                }
                return false;
            }
            try {
                count = cursorQuery.getCount();
                UPLog.i("Prefs", "count", Integer.valueOf(count));
            } catch (Exception e3) {
                e = e3;
                UPLog.e("Prefs", e);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return false;
            }
            if (count <= 0) {
                try {
                    cursorQuery.close();
                } catch (Throwable th6) {
                    UPLog.e("Prefs", th6);
                }
                return false;
            }
            cursorQuery.moveToFirst();
            String string = cursorQuery.getString(cursorQuery.getColumnIndex("type"));
            String string2 = cursorQuery.getString(cursorQuery.getColumnIndex(PushConstants.SUB_ALIAS_STATUS_NAME));
            long j2 = cursorQuery.getLong(cursorQuery.getColumnIndex(RemoteMessageConst.TTL));
            boolean z2 = Math.abs(System.currentTimeMillis() - cursorQuery.getLong(cursorQuery.getColumnIndex("time"))) < 1000 * j2;
            UPLog.i("Prefs", "type", string, PushConstants.SUB_ALIAS_STATUS_NAME, string2, "alive", Boolean.valueOf(z2), RemoteMessageConst.TTL, Long.valueOf(j2));
            if (z2 && TextUtils.equals(string, str2)) {
                if (TextUtils.equals(str, string2)) {
                    try {
                        cursorQuery.close();
                    } catch (Throwable th7) {
                        UPLog.e("Prefs", th7);
                    }
                    return true;
                }
            }
            cursorQuery.close();
            return false;
        } catch (Throwable th8) {
            th = th8;
        }
    }

    public final void a(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return;
        }
        Set<String> setB = this.f22601b.b("tags", new HashSet());
        setB.addAll(Arrays.asList(strArr));
        this.f22601b.a("tags", setB);
    }

    public final void a(String str, long j2) {
        this.f22601b.a(str + bc.ba, j2);
        this.f22601b.a(str + "ts", System.currentTimeMillis());
    }

    public final boolean a(String str) {
        long jB = this.f22601b.b(str + bc.ba, 0L);
        if (jB <= 0) {
            return true;
        }
        aq aqVar = this.f22601b;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("ts");
        return Math.abs(System.currentTimeMillis() - aqVar.b(sb.toString(), 0L)) / 1000 >= jB;
    }

    public final void a(int i2) {
        this.f22601b.a("tag_remain", i2);
    }
}
