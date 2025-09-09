package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;
import com.xiaomi.mipush.sdk.Constants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public final class ag {

    /* renamed from: a, reason: collision with root package name */
    private final SimpleDateFormat f20718a;

    /* renamed from: b, reason: collision with root package name */
    private final ad f20719b;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final ag f20722a = new ag(0);
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        String f20723a;

        /* renamed from: b, reason: collision with root package name */
        public long f20724b;

        /* renamed from: c, reason: collision with root package name */
        public String f20725c;

        public final String toString() {
            return "SLAData{uuid='" + this.f20723a + "', time=" + this.f20724b + ", data='" + this.f20725c + "'}";
        }
    }

    /* synthetic */ ag(byte b2) {
        this();
    }

    static void c(List<b> list) {
        if (list == null || list.isEmpty()) {
            al.c("sla batch report data is empty", new Object[0]);
            return;
        }
        al.c("sla batch report list size:%s", Integer.valueOf(list.size()));
        if (list.size() > 30) {
            list = list.subList(0, 29);
        }
        ArrayList arrayList = new ArrayList();
        Iterator<b> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().f20725c);
        }
        Pair<Integer, String> pairA = ad.a(arrayList);
        al.c("sla batch report result, rspCode:%s rspMsg:%s", pairA.first, pairA.second);
        if (((Integer) pairA.first).intValue() == 200) {
            d(list);
        }
    }

    public static void d(List<b> list) {
        if (list == null || list.isEmpty()) {
            al.c("sla batch delete list is null", new Object[0]);
            return;
        }
        al.c("sla batch delete list size:%s", Integer.valueOf(list.size()));
        try {
            String str = "_id in (" + a(",", list) + ")";
            al.c("sla batch delete where:%s", str);
            w.a().a("t_sla", str);
        } catch (Throwable th) {
            al.b(th);
        }
    }

    private static void e(List<b> list) {
        for (b bVar : list) {
            al.c("sla save id:%s time:%s msg:%s", bVar.f20723a, Long.valueOf(bVar.f20724b), bVar.f20725c);
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(com.umeng.analytics.pro.bg.f21483d, bVar.f20723a);
                contentValues.put("_tm", Long.valueOf(bVar.f20724b));
                contentValues.put("_dt", bVar.f20725c);
                w.a().a("t_sla", contentValues, (v) null);
            } catch (Throwable th) {
                al.b(th);
            }
        }
    }

    public final void a(List<c> list) {
        if (list == null || list.isEmpty()) {
            al.d("sla batch report event is null", new Object[0]);
            return;
        }
        al.c("sla batch report event size:%s", Integer.valueOf(list.size()));
        ArrayList arrayList = new ArrayList();
        Iterator<c> it = list.iterator();
        while (it.hasNext()) {
            b bVarB = b(it.next());
            if (bVarB != null) {
                arrayList.add(bVarB);
            }
        }
        e(arrayList);
        b(arrayList);
    }

    public final void b(final List<b> list) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.ag.1
                @Override // java.lang.Runnable
                public final void run() {
                    ag.c(list);
                }
            });
        } else {
            c(list);
        }
    }

    private ag() {
        this.f20718a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.US);
        this.f20719b = new ad();
    }

    private b b(c cVar) {
        if (cVar != null && !TextUtils.isEmpty(cVar.f20727b)) {
            aa aaVarB = aa.b();
            if (aaVarB == null) {
                al.d("sla convert failed because ComInfoManager is null", new Object[0]);
                return null;
            }
            StringBuilder sb = new StringBuilder("&app_version=");
            sb.append(aaVarB.f20690o);
            sb.append("&app_name=");
            sb.append(aaVarB.f20692q);
            sb.append("&app_bundle_id=");
            sb.append(aaVarB.f20678c);
            sb.append("&client_type=android&user_id=");
            sb.append(aaVarB.f());
            sb.append("&sdk_version=");
            sb.append(aaVarB.f20683h);
            sb.append("&event_code=");
            sb.append(cVar.f20727b);
            sb.append("&event_result=");
            sb.append(cVar.f20729d ? 1 : 0);
            sb.append("&event_time=");
            sb.append(this.f20718a.format(new Date(cVar.f20728c)));
            sb.append("&event_cost=");
            sb.append(cVar.f20730e);
            sb.append("&device_id=");
            sb.append(aaVarB.g());
            sb.append("&debug=");
            sb.append(aaVarB.D ? 1 : 0);
            sb.append("&param_0=");
            sb.append(cVar.f20731f);
            sb.append("&param_1=");
            sb.append(cVar.f20726a);
            sb.append("&param_2=");
            sb.append(aaVarB.M ? "rqd" : "ext");
            sb.append("&param_4=");
            sb.append(aaVarB.e());
            String string = sb.toString();
            if (!TextUtils.isEmpty(cVar.f20732g)) {
                string = string + "&param_3=" + cVar.f20732g;
            }
            al.c("sla convert eventId:%s eventType:%s, eventTime:%s success:%s cost:%s from:%s uploadMsg:", cVar.f20726a, cVar.f20727b, Long.valueOf(cVar.f20728c), Boolean.valueOf(cVar.f20729d), Long.valueOf(cVar.f20730e), cVar.f20731f, cVar.f20732g);
            String str = cVar.f20726a + Constants.ACCEPT_TIME_SEPARATOR_SERVER + cVar.f20727b;
            b bVar = new b();
            bVar.f20723a = str;
            bVar.f20724b = cVar.f20728c;
            bVar.f20725c = string;
            return bVar;
        }
        al.d("sla convert event is null", new Object[0]);
        return null;
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        String f20726a;

        /* renamed from: b, reason: collision with root package name */
        String f20727b;

        /* renamed from: c, reason: collision with root package name */
        long f20728c;

        /* renamed from: d, reason: collision with root package name */
        boolean f20729d;

        /* renamed from: e, reason: collision with root package name */
        long f20730e;

        /* renamed from: f, reason: collision with root package name */
        String f20731f;

        /* renamed from: g, reason: collision with root package name */
        String f20732g;

        public c(String str, String str2, long j2, boolean z2, long j3, String str3, String str4) {
            this.f20726a = str;
            this.f20727b = str2;
            this.f20728c = j2;
            this.f20729d = z2;
            this.f20730e = j3;
            this.f20731f = str3;
            this.f20732g = str4;
        }

        public c() {
        }
    }

    private static String a(String str, Iterable<b> iterable) {
        Iterator<b> it = iterable.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("'");
        sb.append(it.next().f20723a);
        sb.append("'");
        while (it.hasNext()) {
            sb.append(str);
            sb.append("'");
            sb.append(it.next().f20723a);
            sb.append("'");
        }
        return sb.toString();
    }

    public static List<b> a() {
        Cursor cursorA = w.a().a("t_sla", new String[]{com.umeng.analytics.pro.bg.f21483d, "_tm", "_dt"}, (String) null, "_tm", "30");
        if (cursorA == null) {
            return null;
        }
        if (cursorA.getCount() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        while (cursorA.moveToNext()) {
            try {
                b bVar = new b();
                bVar.f20723a = cursorA.getString(cursorA.getColumnIndex(com.umeng.analytics.pro.bg.f21483d));
                bVar.f20724b = cursorA.getLong(cursorA.getColumnIndex("_tm"));
                bVar.f20725c = cursorA.getString(cursorA.getColumnIndex("_dt"));
                al.c(bVar.toString(), new Object[0]);
                arrayList.add(bVar);
            } finally {
                try {
                    return arrayList;
                } finally {
                }
            }
        }
        return arrayList;
    }

    public final void a(c cVar) {
        if (TextUtils.isEmpty(cVar.f20727b)) {
            al.d("sla report event is null", new Object[0]);
        } else {
            al.c("sla report single event", new Object[0]);
            a(Collections.singletonList(cVar));
        }
    }
}
