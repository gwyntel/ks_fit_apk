package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Pair;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.proguard.ag;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.android.agoo.message.MessageService;

/* loaded from: classes4.dex */
public final class as {

    /* renamed from: a, reason: collision with root package name */
    public static int f20821a;

    /* renamed from: h, reason: collision with root package name */
    private static final Map<Integer, Pair<String, String>> f20822h = new HashMap<Integer, Pair<String, String>>() { // from class: com.tencent.bugly.proguard.as.1
        {
            put(3, new Pair("203", "103"));
            put(7, new Pair("208", "108"));
            put(0, new Pair("200", MessageService.MSG_DB_COMPLETE));
            put(1, new Pair("201", "101"));
            put(2, new Pair("202", "102"));
            put(4, new Pair("204", "104"));
            put(6, new Pair("206", "106"));
            put(5, new Pair("207", "107"));
        }
    };

    /* renamed from: i, reason: collision with root package name */
    private static final ArrayList<a> f20823i = new ArrayList<a>() { // from class: com.tencent.bugly.proguard.as.2
        {
            byte b2 = 0;
            add(new b(b2));
            add(new c(b2));
            add(new d(b2));
            add(new e(b2));
            add(new h(b2));
            add(new i(b2));
            add(new f(b2));
            add(new g(b2));
        }
    };

    /* renamed from: j, reason: collision with root package name */
    private static final Map<Integer, Integer> f20824j = new HashMap<Integer, Integer>() { // from class: com.tencent.bugly.proguard.as.3
        {
            put(3, 4);
            put(7, 7);
            put(2, 1);
            put(0, 0);
            put(1, 2);
            put(4, 3);
            put(5, 5);
            put(6, 6);
        }
    };

    /* renamed from: k, reason: collision with root package name */
    private static final Map<Integer, String> f20825k = new HashMap<Integer, String>() { // from class: com.tencent.bugly.proguard.as.4
        {
            put(3, "BuglyAnrCrash");
            put(0, "BuglyJavaCrash");
            put(1, "BuglyNativeCrash");
        }
    };

    /* renamed from: l, reason: collision with root package name */
    private static final Map<Integer, String> f20826l = new HashMap<Integer, String>() { // from class: com.tencent.bugly.proguard.as.5
        {
            put(3, "BuglyAnrCrashReport");
            put(0, "BuglyJavaCrashReport");
            put(1, "BuglyNativeCrashReport");
        }
    };

    /* renamed from: b, reason: collision with root package name */
    protected final Context f20827b;

    /* renamed from: c, reason: collision with root package name */
    protected final ai f20828c;

    /* renamed from: d, reason: collision with root package name */
    protected final w f20829d;

    /* renamed from: e, reason: collision with root package name */
    protected final ac f20830e;

    /* renamed from: f, reason: collision with root package name */
    protected aw f20831f;

    /* renamed from: g, reason: collision with root package name */
    protected BuglyStrategy.a f20832g;

    static abstract class a {

        /* renamed from: a, reason: collision with root package name */
        final int f20837a;

        /* synthetic */ a(int i2, byte b2) {
            this(i2);
        }

        abstract boolean a();

        private a(int i2) {
            this.f20837a = i2;
        }
    }

    static class b extends a {
        /* synthetic */ b(byte b2) {
            this();
        }

        @Override // com.tencent.bugly.proguard.as.a
        final boolean a() {
            return at.a().k();
        }

        private b() {
            super(3, (byte) 0);
        }
    }

    static class c extends a {
        /* synthetic */ c(byte b2) {
            this();
        }

        @Override // com.tencent.bugly.proguard.as.a
        final boolean a() {
            return true;
        }

        private c() {
            super(7, (byte) 0);
        }
    }

    static class d extends a {
        /* synthetic */ d(byte b2) {
            this();
        }

        @Override // com.tencent.bugly.proguard.as.a
        final boolean a() {
            return true;
        }

        private d() {
            super(2, (byte) 0);
        }
    }

    static class e extends a {
        /* synthetic */ e(byte b2) {
            this();
        }

        @Override // com.tencent.bugly.proguard.as.a
        final boolean a() {
            return at.a().j();
        }

        /* JADX WARN: Illegal instructions before constructor call */
        private e() {
            byte b2 = 0;
            super(b2, b2);
        }
    }

    static class f extends a {
        /* synthetic */ f(byte b2) {
            this();
        }

        @Override // com.tencent.bugly.proguard.as.a
        final boolean a() {
            return (at.a().B & 2) > 0;
        }

        private f() {
            super(5, (byte) 0);
        }
    }

    static class g extends a {
        /* synthetic */ g(byte b2) {
            this();
        }

        @Override // com.tencent.bugly.proguard.as.a
        final boolean a() {
            return (at.a().B & 1) > 0;
        }

        private g() {
            super(6, (byte) 0);
        }
    }

    static class h extends a {
        /* synthetic */ h(byte b2) {
            this();
        }

        @Override // com.tencent.bugly.proguard.as.a
        final boolean a() {
            return at.a().j();
        }

        private h() {
            super(1, (byte) 0);
        }
    }

    static class i extends a {
        /* synthetic */ i(byte b2) {
            this();
        }

        @Override // com.tencent.bugly.proguard.as.a
        final boolean a() {
            return (at.a().B & 4) > 0;
        }

        private i() {
            super(4, (byte) 0);
        }
    }

    public as(Context context, ai aiVar, w wVar, ac acVar, BuglyStrategy.a aVar) {
        f20821a = 1004;
        this.f20827b = context;
        this.f20828c = aiVar;
        this.f20829d = wVar;
        this.f20830e = acVar;
        this.f20832g = aVar;
        this.f20831f = null;
    }

    private static List<ar> a(List<ar> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        for (ar arVar : list) {
            if (arVar.f20818d && arVar.f20816b <= jCurrentTimeMillis - 86400000) {
                arrayList.add(arVar);
            }
        }
        return arrayList;
    }

    private static void b(CrashDetailBean crashDetailBean, List<ar> list) {
        StringBuilder sb = new StringBuilder(64);
        for (ar arVar : list) {
            if (!arVar.f20819e && !arVar.f20818d) {
                String str = crashDetailBean.f20638s;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(arVar.f20816b);
                if (!str.contains(sb2.toString())) {
                    crashDetailBean.f20639t++;
                    sb.append(arVar.f20816b);
                    sb.append("\n");
                }
            }
        }
        crashDetailBean.f20638s += sb.toString();
    }

    private static ContentValues c(CrashDetailBean crashDetailBean) {
        if (crashDetailBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            long j2 = crashDetailBean.f20620a;
            if (j2 > 0) {
                contentValues.put(com.umeng.analytics.pro.bg.f21483d, Long.valueOf(j2));
            }
            contentValues.put("_tm", Long.valueOf(crashDetailBean.f20637r));
            contentValues.put("_s1", crashDetailBean.f20640u);
            contentValues.put("_up", Integer.valueOf(crashDetailBean.f20623d ? 1 : 0));
            contentValues.put("_me", Integer.valueOf(crashDetailBean.f20629j ? 1 : 0));
            contentValues.put("_uc", Integer.valueOf(crashDetailBean.f20631l));
            contentValues.put("_dt", ap.a(crashDetailBean));
            return contentValues;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static void d(List<ar> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("_id in (");
        Iterator<ar> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next().f20815a);
            sb.append(",");
        }
        StringBuilder sb2 = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
        sb2.append(")");
        String string = sb2.toString();
        sb2.setLength(0);
        try {
            al.c("deleted %s data %d", "t_cr", Integer.valueOf(w.a().a("t_cr", string)));
        } catch (Throwable th) {
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private static void e(List<CrashDetailBean> list) {
        try {
            if (list.size() == 0) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (CrashDetailBean crashDetailBean : list) {
                sb.append(" or _id = ");
                sb.append(crashDetailBean.f20620a);
            }
            String string = sb.toString();
            if (string.length() > 0) {
                string = string.substring(4);
            }
            sb.setLength(0);
            al.c("deleted %s data %d", "t_cr", Integer.valueOf(w.a().a("t_cr", string)));
        } catch (Throwable th) {
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private static void b(List<ar> list) {
        List<CrashDetailBean> listC = c(list);
        if (listC == null || listC.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (CrashDetailBean crashDetailBean : listC) {
            String str = f20826l.get(Integer.valueOf(crashDetailBean.f20621b));
            if (!TextUtils.isEmpty(str)) {
                al.c("find expired data,crashId:%s eventType:%s", crashDetailBean.f20622c, str);
                arrayList.add(new ag.c(crashDetailBean.f20622c, str, crashDetailBean.f20637r, false, 0L, "expired", null));
            }
        }
        ag.a.f20722a.a(arrayList);
    }

    private static CrashDetailBean a(List<ar> list, CrashDetailBean crashDetailBean) {
        CrashDetailBean crashDetailBean2;
        List<CrashDetailBean> listC;
        if (list.isEmpty()) {
            return crashDetailBean;
        }
        ArrayList arrayList = new ArrayList(10);
        for (ar arVar : list) {
            if (arVar.f20819e) {
                arrayList.add(arVar);
            }
        }
        if (arrayList.isEmpty() || (listC = c(arrayList)) == null || listC.isEmpty()) {
            crashDetailBean2 = null;
        } else {
            Collections.sort(listC);
            crashDetailBean2 = listC.get(0);
            a(crashDetailBean2, listC);
        }
        if (crashDetailBean2 == null) {
            crashDetailBean.f20629j = true;
            crashDetailBean.f20639t = 0;
            crashDetailBean.f20638s = "";
            crashDetailBean2 = crashDetailBean;
        }
        b(crashDetailBean2, list);
        if (crashDetailBean2.f20637r != crashDetailBean.f20637r) {
            String str = crashDetailBean2.f20638s;
            StringBuilder sb = new StringBuilder();
            sb.append(crashDetailBean.f20637r);
            if (!str.contains(sb.toString())) {
                crashDetailBean2.f20639t++;
                crashDetailBean2.f20638s += crashDetailBean.f20637r + "\n";
            }
        }
        return crashDetailBean2;
    }

    private static List<CrashDetailBean> c(List<ar> list) {
        Cursor cursorA;
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("_id in (");
        Iterator<ar> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next().f20815a);
            sb.append(",");
        }
        if (sb.toString().contains(",")) {
            sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
        }
        sb.append(")");
        String string = sb.toString();
        sb.setLength(0);
        try {
            cursorA = w.a().a("t_cr", (String[]) null, string);
            if (cursorA == null) {
                return null;
            }
            try {
                ArrayList arrayList = new ArrayList();
                sb.append("_id in (");
                int i2 = 0;
                while (cursorA.moveToNext()) {
                    CrashDetailBean crashDetailBeanA = a(cursorA);
                    if (crashDetailBeanA != null) {
                        arrayList.add(crashDetailBeanA);
                    } else {
                        try {
                            sb.append(cursorA.getLong(cursorA.getColumnIndex(com.umeng.analytics.pro.bg.f21483d)));
                            sb.append(",");
                            i2++;
                        } catch (Throwable unused) {
                            al.d("unknown id!", new Object[0]);
                        }
                    }
                }
                if (sb.toString().contains(",")) {
                    sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
                }
                sb.append(")");
                String string2 = sb.toString();
                if (i2 > 0) {
                    al.d("deleted %s illegal data %d", "t_cr", Integer.valueOf(w.a().a("t_cr", string2)));
                }
                cursorA.close();
                return arrayList;
            } catch (Throwable th) {
                th = th;
                try {
                    if (!al.a(th)) {
                        th.printStackTrace();
                    }
                    if (cursorA != null) {
                        cursorA.close();
                    }
                    return null;
                } finally {
                    if (cursorA != null) {
                        cursorA.close();
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
            cursorA = null;
        }
    }

    private static String e(CrashDetailBean crashDetailBean) {
        try {
            Pair<String, String> pair = f20822h.get(Integer.valueOf(crashDetailBean.f20621b));
            if (pair == null) {
                al.e("crash type error! %d", Integer.valueOf(crashDetailBean.f20621b));
                return "";
            }
            if (crashDetailBean.f20629j) {
                return (String) pair.first;
            }
            return (String) pair.second;
        } catch (Exception e2) {
            al.a(e2);
            return "";
        }
    }

    private boolean d(CrashDetailBean crashDetailBean) {
        String absolutePath;
        try {
            al.c("save eup logs", new Object[0]);
            aa aaVarB = aa.b();
            String str = "#--------\npackage:" + aaVarB.e() + "\nversion:" + aaVarB.f20690o + "\nsdk:" + aaVarB.f20683h + "\nprocess:" + crashDetailBean.A + "\ndate:" + ap.a(new Date(crashDetailBean.f20637r)) + "\ntype:" + crashDetailBean.f20633n + "\nmessage:" + crashDetailBean.f20634o + "\nstack:\n" + crashDetailBean.f20636q + "\neupID:" + crashDetailBean.f20622c + "\n";
            if (at.f20849m == null) {
                if (Environment.getExternalStorageState().equals("mounted")) {
                    absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Tencent/" + this.f20827b.getPackageName();
                } else {
                    absolutePath = null;
                }
            } else {
                File file = new File(at.f20849m);
                if (file.isFile()) {
                    file = file.getParentFile();
                }
                absolutePath = file.getAbsolutePath();
            }
            am.a(absolutePath + "/euplog.txt", str, at.f20850n);
            return true;
        } catch (Throwable th) {
            al.d("rqdp{  save error} %s", th.toString());
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    public final void b(CrashDetailBean crashDetailBean, boolean z2) {
        if (at.f20851o) {
            al.a("try to upload right now", new Object[0]);
            ArrayList arrayList = new ArrayList();
            arrayList.add(crashDetailBean);
            a(arrayList, 3000L, z2, crashDetailBean.f20621b == 7, z2);
            return;
        }
        al.a("do not upload spot crash right now, crash would be uploaded when app next start", new Object[0]);
    }

    public final void b(CrashDetailBean crashDetailBean) {
        if (crashDetailBean == null) {
            return;
        }
        ContentValues contentValuesC = c(crashDetailBean);
        if (contentValuesC != null) {
            long jA = w.a().a("t_cr", contentValuesC, (v) null);
            if (jA >= 0) {
                al.c("insert %s success!", "t_cr");
                crashDetailBean.f20620a = jA;
            }
        }
        if (at.f20848l) {
            d(crashDetailBean);
        }
    }

    private static void a(CrashDetailBean crashDetailBean, List<CrashDetailBean> list) {
        String[] strArrSplit;
        StringBuilder sb = new StringBuilder(128);
        for (int i2 = 1; i2 < list.size(); i2++) {
            String str = list.get(i2).f20638s;
            if (str != null && (strArrSplit = str.split("\n")) != null) {
                for (String str2 : strArrSplit) {
                    if (!crashDetailBean.f20638s.contains(str2)) {
                        crashDetailBean.f20639t++;
                        sb.append(str2);
                        sb.append("\n");
                    }
                }
            }
        }
        crashDetailBean.f20638s += sb.toString();
    }

    private static ar b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            ar arVar = new ar();
            arVar.f20815a = cursor.getLong(cursor.getColumnIndex(com.umeng.analytics.pro.bg.f21483d));
            arVar.f20816b = cursor.getLong(cursor.getColumnIndex("_tm"));
            arVar.f20817c = cursor.getString(cursor.getColumnIndex("_s1"));
            arVar.f20818d = cursor.getInt(cursor.getColumnIndex("_up")) == 1;
            arVar.f20819e = cursor.getInt(cursor.getColumnIndex("_me")) == 1;
            arVar.f20820f = cursor.getInt(cursor.getColumnIndex("_uc"));
            return arVar;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ce A[Catch: all -> 0x006e, TryCatch #1 {all -> 0x006e, blocks: (B:21:0x0049, B:23:0x0057, B:26:0x0071, B:28:0x0086, B:30:0x009c, B:35:0x00c0, B:37:0x00ce, B:42:0x00f5, B:48:0x012e, B:50:0x0132, B:52:0x0141, B:43:0x00fd, B:45:0x0103, B:47:0x0120, B:46:0x0106, B:38:0x00dc, B:40:0x00e0, B:31:0x00a7, B:33:0x00ab), top: B:100:0x0049 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00dc A[Catch: all -> 0x006e, TryCatch #1 {all -> 0x006e, blocks: (B:21:0x0049, B:23:0x0057, B:26:0x0071, B:28:0x0086, B:30:0x009c, B:35:0x00c0, B:37:0x00ce, B:42:0x00f5, B:48:0x012e, B:50:0x0132, B:52:0x0141, B:43:0x00fd, B:45:0x0103, B:47:0x0120, B:46:0x0106, B:38:0x00dc, B:40:0x00e0, B:31:0x00a7, B:33:0x00ab), top: B:100:0x0049 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00f5 A[Catch: all -> 0x006e, TryCatch #1 {all -> 0x006e, blocks: (B:21:0x0049, B:23:0x0057, B:26:0x0071, B:28:0x0086, B:30:0x009c, B:35:0x00c0, B:37:0x00ce, B:42:0x00f5, B:48:0x012e, B:50:0x0132, B:52:0x0141, B:43:0x00fd, B:45:0x0103, B:47:0x0120, B:46:0x0106, B:38:0x00dc, B:40:0x00e0, B:31:0x00a7, B:33:0x00ab), top: B:100:0x0049 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00fd A[Catch: all -> 0x006e, TryCatch #1 {all -> 0x006e, blocks: (B:21:0x0049, B:23:0x0057, B:26:0x0071, B:28:0x0086, B:30:0x009c, B:35:0x00c0, B:37:0x00ce, B:42:0x00f5, B:48:0x012e, B:50:0x0132, B:52:0x0141, B:43:0x00fd, B:45:0x0103, B:47:0x0120, B:46:0x0106, B:38:0x00dc, B:40:0x00e0, B:31:0x00a7, B:33:0x00ab), top: B:100:0x0049 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0132 A[Catch: all -> 0x006e, TryCatch #1 {all -> 0x006e, blocks: (B:21:0x0049, B:23:0x0057, B:26:0x0071, B:28:0x0086, B:30:0x009c, B:35:0x00c0, B:37:0x00ce, B:42:0x00f5, B:48:0x012e, B:50:0x0132, B:52:0x0141, B:43:0x00fd, B:45:0x0103, B:47:0x0120, B:46:0x0106, B:38:0x00dc, B:40:0x00e0, B:31:0x00a7, B:33:0x00ab), top: B:100:0x0049 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x019b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean a(com.tencent.bugly.crashreport.crash.CrashDetailBean r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 643
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.as.a(com.tencent.bugly.crashreport.crash.CrashDetailBean, boolean):boolean");
    }

    private static List<ar> b() {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            Cursor cursorA = w.a().a("t_cr", new String[]{com.umeng.analytics.pro.bg.f21483d, "_tm", "_s1", "_up", "_me", "_uc"}, (String) null);
            if (cursorA == null) {
                if (cursorA != null) {
                    cursorA.close();
                }
                return null;
            }
            try {
                if (cursorA.getCount() <= 0) {
                    cursorA.close();
                    return arrayList;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("_id in (");
                int i2 = 0;
                while (cursorA.moveToNext()) {
                    ar arVarB = b(cursorA);
                    if (arVarB != null) {
                        arrayList.add(arVarB);
                    } else {
                        try {
                            sb.append(cursorA.getLong(cursorA.getColumnIndex(com.umeng.analytics.pro.bg.f21483d)));
                            sb.append(",");
                            i2++;
                        } catch (Throwable unused) {
                            al.d("unknown id!", new Object[0]);
                        }
                    }
                }
                if (sb.toString().contains(",")) {
                    sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
                }
                sb.append(")");
                String string = sb.toString();
                sb.setLength(0);
                if (i2 > 0) {
                    al.d("deleted %s illegal data %d", "t_cr", Integer.valueOf(w.a().a("t_cr", string)));
                }
                cursorA.close();
                return arrayList;
            } catch (Throwable th) {
                th = th;
                cursor = cursorA;
                try {
                    if (!al.a(th)) {
                        th.printStackTrace();
                    }
                    return arrayList;
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static void c(ArrayList<bn> arrayList, String str) {
        if (ap.b(str)) {
            return;
        }
        try {
            bn bnVar = new bn((byte) 1, "crashInfos.txt", str.getBytes("utf-8"));
            al.c("attach crash infos", new Object[0]);
            arrayList.add(bnVar);
        } catch (Exception e2) {
            e2.printStackTrace();
            al.a(e2);
        }
    }

    private static void b(ArrayList<bn> arrayList, String str) {
        if (str != null) {
            try {
                arrayList.add(new bn((byte) 1, "jniLog.txt", str.getBytes("utf-8")));
            } catch (Exception e2) {
                e2.printStackTrace();
                al.a(e2);
            }
        }
    }

    private static void b(ArrayList<bn> arrayList, CrashDetailBean crashDetailBean, Context context) {
        String str;
        if (crashDetailBean.f20621b == 1 && (str = crashDetailBean.f20641v) != null) {
            try {
                bn bnVarA = a("tomb.zip", context, str);
                if (bnVarA != null) {
                    al.c("attach tombs", new Object[0]);
                    arrayList.add(bnVarA);
                }
            } catch (Exception e2) {
                al.a(e2);
            }
        }
    }

    private static void b(ArrayList<bn> arrayList, byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        try {
            arrayList.add(new bn((byte) 1, "userExtraByteData", bArr));
            al.c("attach extraData", new Object[0]);
        } catch (Exception e2) {
            al.a(e2);
        }
    }

    private boolean b(CrashDetailBean crashDetailBean, List<ar> list, List<ar> list2) {
        boolean z2;
        int i2 = crashDetailBean.f20621b;
        boolean z3 = i2 == 0 || i2 == 1;
        boolean z4 = i2 == 3;
        if (p.f21112c) {
            z2 = false;
        } else {
            z2 = (z4 || z3) ? at.f20841e : true;
        }
        if (!z2) {
            return false;
        }
        ArrayList<ar> arrayList = new ArrayList(10);
        if (!a(crashDetailBean, list, arrayList)) {
            try {
                if (arrayList.size() >= at.f20840d) {
                }
            } catch (Exception e2) {
                al.a(e2);
                al.d("Failed to merge crash.", new Object[0]);
            }
            return false;
        }
        al.a("same crash occur too much do merged!", new Object[0]);
        CrashDetailBean crashDetailBeanA = a((List<ar>) arrayList, crashDetailBean);
        for (ar arVar : arrayList) {
            if (arVar.f20815a != crashDetailBeanA.f20620a) {
                list2.add(arVar);
            }
        }
        b(crashDetailBeanA);
        d(list2);
        al.b("[crash] save crash success. For this device crash many times, it will not upload crashes immediately", new Object[0]);
        return true;
    }

    private static boolean a(String str) {
        String str2 = at.f20854r;
        if (str2 != null && !str2.isEmpty()) {
            try {
                al.c("Crash regular filter for crash stack is: %s", at.f20854r);
                if (Pattern.compile(at.f20854r).matcher(str).find()) {
                    al.d("This crash matches the regular filter string set. It will not be record and upload.", new Object[0]);
                    return true;
                }
            } catch (Exception e2) {
                al.a(e2);
                al.d("Failed to compile " + at.f20854r, new Object[0]);
            }
        }
        return false;
    }

    private static boolean a(CrashDetailBean crashDetailBean, List<ar> list, List<ar> list2) {
        boolean z2 = false;
        for (ar arVar : list) {
            if (crashDetailBean.f20640u.equals(arVar.f20817c)) {
                if (arVar.f20819e) {
                    z2 = true;
                }
                list2.add(arVar);
            }
        }
        return z2;
    }

    public static List<CrashDetailBean> a() {
        StrategyBean strategyBeanC = ac.a().c();
        if (strategyBeanC == null) {
            al.d("have not synced remote!", new Object[0]);
            return null;
        }
        if (!strategyBeanC.f20602f) {
            al.d("Crashreport remote closed, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            al.b("[init] WARNING! Crashreport closed by server, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            return null;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jB = ap.b();
        List<ar> listB = b();
        al.c("Size of crash list loaded from DB: %s", Integer.valueOf(listB.size()));
        if (listB.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList.addAll(a(listB));
        listB.removeAll(arrayList);
        Iterator<ar> it = listB.iterator();
        while (it.hasNext()) {
            ar next = it.next();
            long j2 = next.f20816b;
            if (j2 < jB - at.f20846j) {
                arrayList2.add(next);
                it.remove();
                arrayList.add(next);
            } else if (next.f20818d) {
                if (j2 >= jCurrentTimeMillis - 86400000) {
                    it.remove();
                } else if (!next.f20819e) {
                    it.remove();
                    arrayList.add(next);
                }
            } else if (next.f20820f >= 3 && j2 < jCurrentTimeMillis - 86400000) {
                it.remove();
                arrayList.add(next);
            }
        }
        b(arrayList2);
        if (arrayList.size() > 0) {
            d(arrayList);
        }
        ArrayList arrayList3 = new ArrayList();
        List<CrashDetailBean> listC = c(listB);
        if (listC != null && listC.size() > 0) {
            String str = aa.b().f20690o;
            Iterator<CrashDetailBean> it2 = listC.iterator();
            while (it2.hasNext()) {
                CrashDetailBean next2 = it2.next();
                if (!str.equals(next2.f20625f)) {
                    it2.remove();
                    arrayList3.add(next2);
                }
            }
        }
        if (arrayList3.size() > 0) {
            e(arrayList3);
        }
        return listC;
    }

    public final void a(CrashDetailBean crashDetailBean) {
        int i2 = crashDetailBean.f20621b;
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 == 3 && !at.a().k()) {
                    return;
                }
            } else if (!at.a().j()) {
                return;
            }
        } else if (!at.a().j()) {
            return;
        }
        if (this.f20831f != null) {
            al.c("Calling 'onCrashHandleEnd' of RQD crash listener.", new Object[0]);
        }
    }

    public final void a(final List<CrashDetailBean> list, long j2, final boolean z2, boolean z3, boolean z4) {
        if (!aa.a(this.f20827b).f20681f) {
            al.d("warn: not upload process", new Object[0]);
            return;
        }
        ai aiVar = this.f20828c;
        if (aiVar == null) {
            al.d("warn: upload manager is null", new Object[0]);
            return;
        }
        if (!z4 && !aiVar.b(at.f20838a)) {
            al.d("warn: not crashHappen or not should upload", new Object[0]);
            return;
        }
        StrategyBean strategyBeanC = this.f20830e.c();
        if (!strategyBeanC.f20602f) {
            al.d("remote report is disable!", new Object[0]);
            al.b("[crash] server closed bugly in this app. please check your appid if is correct, and re-install it", new Object[0]);
            return;
        }
        if (list != null && list.size() != 0) {
            try {
                String str = strategyBeanC.f20614r;
                String str2 = StrategyBean.f20598b;
                bp bpVarA = a(this.f20827b, list, aa.b());
                if (bpVarA == null) {
                    al.d("create eupPkg fail!", new Object[0]);
                    return;
                }
                byte[] bArrA = ae.a((m) bpVarA);
                if (bArrA == null) {
                    al.d("send encode fail!", new Object[0]);
                    return;
                }
                bq bqVarA = ae.a(this.f20827b, 830, bArrA);
                if (bqVarA == null) {
                    al.d("request package is null.", new Object[0]);
                    return;
                }
                final long jCurrentTimeMillis = System.currentTimeMillis();
                ah ahVar = new ah() { // from class: com.tencent.bugly.proguard.as.6
                    @Override // com.tencent.bugly.proguard.ah
                    public final void a(boolean z5, String str3) {
                        as.a(list, z5, System.currentTimeMillis() - jCurrentTimeMillis, z2 ? "realtime" : "cache", str3);
                        as.a(z5, (List<CrashDetailBean>) list);
                    }
                };
                if (z2) {
                    this.f20828c.a(f20821a, bqVarA, str, str2, ahVar, j2, z3);
                    return;
                } else {
                    this.f20828c.a(f20821a, bqVarA, str, str2, ahVar, false);
                    return;
                }
            } catch (Throwable th) {
                al.e("req cr error %s", th.toString());
                if (al.b(th)) {
                    return;
                }
                th.printStackTrace();
                return;
            }
        }
        al.d("warn: crashList is null or crashList num is 0", new Object[0]);
    }

    public static void a(boolean z2, List<CrashDetailBean> list) {
        if (list != null && list.size() > 0) {
            al.c("up finish update state %b", Boolean.valueOf(z2));
            for (CrashDetailBean crashDetailBean : list) {
                al.c("pre uid:%s uc:%d re:%b me:%b", crashDetailBean.f20622c, Integer.valueOf(crashDetailBean.f20631l), Boolean.valueOf(crashDetailBean.f20623d), Boolean.valueOf(crashDetailBean.f20629j));
                int i2 = crashDetailBean.f20631l + 1;
                crashDetailBean.f20631l = i2;
                crashDetailBean.f20623d = z2;
                al.c("set uid:%s uc:%d re:%b me:%b", crashDetailBean.f20622c, Integer.valueOf(i2), Boolean.valueOf(crashDetailBean.f20623d), Boolean.valueOf(crashDetailBean.f20629j));
            }
            Iterator<CrashDetailBean> it = list.iterator();
            while (it.hasNext()) {
                at.a().a(it.next());
            }
            al.c("update state size %d", Integer.valueOf(list.size()));
        }
        if (z2) {
            return;
        }
        al.b("[crash] upload fail.", new Object[0]);
    }

    private static CrashDetailBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j2 = cursor.getLong(cursor.getColumnIndex(com.umeng.analytics.pro.bg.f21483d));
            CrashDetailBean crashDetailBean = (CrashDetailBean) ap.a(blob, CrashDetailBean.CREATOR);
            if (crashDetailBean != null) {
                crashDetailBean.f20620a = j2;
            }
            return crashDetailBean;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static bo a(Context context, CrashDetailBean crashDetailBean, aa aaVar) {
        ArrayList<bl> arrayList = null;
        if (context != null && crashDetailBean != null && aaVar != null) {
            bo boVar = new bo();
            boVar.f20981a = e(crashDetailBean);
            boVar.f20982b = crashDetailBean.f20637r;
            boVar.f20983c = crashDetailBean.f20633n;
            boVar.f20984d = crashDetailBean.f20634o;
            boVar.f20985e = crashDetailBean.f20635p;
            boVar.f20987g = crashDetailBean.f20636q;
            boVar.f20988h = crashDetailBean.f20645z;
            boVar.f20989i = crashDetailBean.f20622c;
            boVar.f20990j = null;
            boVar.f20992l = crashDetailBean.f20632m;
            boVar.f20993m = crashDetailBean.f20624e;
            boVar.f20986f = crashDetailBean.B;
            boVar.f20994n = null;
            Map<String, PlugInBean> map = crashDetailBean.f20627h;
            if (map != null && !map.isEmpty()) {
                arrayList = new ArrayList<>(crashDetailBean.f20627h.size());
                for (Map.Entry<String, PlugInBean> entry : crashDetailBean.f20627h.entrySet()) {
                    bl blVar = new bl();
                    blVar.f20964a = entry.getValue().f20594a;
                    blVar.f20966c = entry.getValue().f20596c;
                    blVar.f20968e = entry.getValue().f20595b;
                    arrayList.add(blVar);
                }
            }
            boVar.f20996p = arrayList;
            al.c("libInfo %s", boVar.f20995o);
            ArrayList<bn> arrayList2 = new ArrayList<>(20);
            a(arrayList2, crashDetailBean);
            a(arrayList2, crashDetailBean.f20642w);
            b(arrayList2, crashDetailBean.f20643x);
            c(arrayList2, crashDetailBean.Z);
            a(arrayList2, crashDetailBean.aa, context);
            a(arrayList2, crashDetailBean.f20644y);
            a(arrayList2, crashDetailBean, context);
            b(arrayList2, crashDetailBean, context);
            a(arrayList2, aaVar.L);
            b(arrayList2, crashDetailBean.Y);
            boVar.f20997q = arrayList2;
            if (crashDetailBean.f20629j) {
                boVar.f20991k = crashDetailBean.f20639t;
            }
            boVar.f20998r = a(crashDetailBean, aaVar);
            boVar.f20999s = new HashMap();
            Map<String, String> map2 = crashDetailBean.S;
            if (map2 != null && map2.size() > 0) {
                boVar.f20999s.putAll(crashDetailBean.S);
                al.a("setted message size %d", Integer.valueOf(boVar.f20999s.size()));
            }
            Map<String, String> map3 = boVar.f20999s;
            al.c("pss:" + crashDetailBean.I + " vss:" + crashDetailBean.J + " javaHeap:" + crashDetailBean.K, new Object[0]);
            StringBuilder sb = new StringBuilder();
            sb.append(crashDetailBean.I);
            map3.put("SDK_UPLOAD_U1", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(crashDetailBean.J);
            map3.put("SDK_UPLOAD_U2", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(crashDetailBean.K);
            map3.put("SDK_UPLOAD_U3", sb3.toString());
            al.c("%s rid:%s sess:%s ls:%ds isR:%b isF:%b isM:%b isN:%b mc:%d ,%s ,isUp:%b ,vm:%d", crashDetailBean.f20633n, crashDetailBean.f20622c, aaVar.d(), Long.valueOf((crashDetailBean.f20637r - crashDetailBean.Q) / 1000), Boolean.valueOf(crashDetailBean.f20630k), Boolean.valueOf(crashDetailBean.R), Boolean.valueOf(crashDetailBean.f20629j), Boolean.valueOf(crashDetailBean.f20621b == 1), Integer.valueOf(crashDetailBean.f20639t), crashDetailBean.f20638s, Boolean.valueOf(crashDetailBean.f20623d), Integer.valueOf(boVar.f20998r.size()));
            return boVar;
        }
        al.d("enExp args == null", new Object[0]);
        return null;
    }

    private static bp a(Context context, List<CrashDetailBean> list, aa aaVar) {
        if (context != null && list != null && list.size() != 0 && aaVar != null) {
            bp bpVar = new bp();
            bpVar.f21003a = new ArrayList<>();
            Iterator<CrashDetailBean> it = list.iterator();
            while (it.hasNext()) {
                bpVar.f21003a.add(a(context, it.next(), aaVar));
            }
            return bpVar;
        }
        al.d("enEXPPkg args == null!", new Object[0]);
        return null;
    }

    private static bn a(String str, Context context, String str2) throws IOException {
        FileInputStream fileInputStream;
        if (str2 != null && context != null) {
            al.c("zip %s", str2);
            File file = new File(str2);
            File file2 = new File(context.getCacheDir(), str);
            if (!ap.a(file, file2)) {
                al.d("zip fail!", new Object[0]);
                return null;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                fileInputStream = new FileInputStream(file2);
                try {
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int i2 = fileInputStream.read(bArr);
                        if (i2 <= 0) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, i2);
                        byteArrayOutputStream.flush();
                    }
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    al.c("read bytes :%d", Integer.valueOf(byteArray.length));
                    bn bnVar = new bn((byte) 2, file2.getName(), byteArray);
                    try {
                        fileInputStream.close();
                    } catch (IOException e2) {
                        if (!al.a(e2)) {
                            e2.printStackTrace();
                        }
                    }
                    if (file2.exists()) {
                        al.c("del tmp", new Object[0]);
                        file2.delete();
                    }
                    return bnVar;
                } catch (Throwable th) {
                    th = th;
                    try {
                        if (!al.a(th)) {
                            th.printStackTrace();
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e3) {
                                if (!al.a(e3)) {
                                    e3.printStackTrace();
                                }
                            }
                        }
                        if (file2.exists()) {
                            al.c("del tmp", new Object[0]);
                            file2.delete();
                        }
                        return null;
                    } catch (Throwable th2) {
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e4) {
                                if (!al.a(e4)) {
                                    e4.printStackTrace();
                                }
                            }
                        }
                        if (file2.exists()) {
                            al.c("del tmp", new Object[0]);
                            file2.delete();
                        }
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = null;
            }
        } else {
            al.d("rqdp{  createZipAttachment sourcePath == null || context == null ,pls check}", new Object[0]);
            return null;
        }
    }

    public static void a(String str, String str2, String str3, String str4, String str5, CrashDetailBean crashDetailBean) {
        String str6;
        aa aaVarB = aa.b();
        if (aaVarB == null) {
            return;
        }
        al.e("#++++++++++Record By Bugly++++++++++#", new Object[0]);
        al.e("# You can use Bugly(http:\\\\bugly.qq.com) to get more Crash Detail!", new Object[0]);
        al.e("# PKG NAME: %s", aaVarB.f20678c);
        al.e("# APP VER: %s", aaVarB.f20690o);
        al.e("# SDK VER: %s", aaVarB.f20683h);
        al.e("# LAUNCH TIME: %s", ap.a(new Date(aa.b().f20676a)));
        al.e("# CRASH TYPE: %s", str);
        al.e("# CRASH TIME: %s", str2);
        al.e("# CRASH PROCESS: %s", str3);
        al.e("# CRASH FOREGROUND: %s", Boolean.valueOf(aaVarB.a()));
        al.e("# CRASH THREAD: %s", str4);
        if (crashDetailBean != null) {
            al.e("# REPORT ID: %s", crashDetailBean.f20622c);
            al.e("# CRASH DEVICE: %s %s", aaVarB.h(), aaVarB.r().booleanValue() ? "ROOTED" : "UNROOT");
            al.e("# RUNTIME AVAIL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.C), Long.valueOf(crashDetailBean.D), Long.valueOf(crashDetailBean.E));
            al.e("# RUNTIME TOTAL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.F), Long.valueOf(crashDetailBean.G), Long.valueOf(crashDetailBean.H));
            if (!ap.b(crashDetailBean.O)) {
                al.e("# EXCEPTION FIRED BY %s %s", crashDetailBean.O, crashDetailBean.N);
            } else if (crashDetailBean.f20621b == 3) {
                if (crashDetailBean.T == null) {
                    str6 = TmpConstant.GROUP_ROLE_UNKNOWN;
                } else {
                    str6 = crashDetailBean.T.get("BUGLY_CR_01");
                }
                al.e("# EXCEPTION ANR MESSAGE:\n %s", str6);
            }
        }
        if (!ap.b(str5)) {
            al.e("# CRASH STACK: ", new Object[0]);
            al.e(str5, new Object[0]);
        }
        al.e("#++++++++++++++++++++++++++++++++++++++++++#", new Object[0]);
    }

    private static void a(CrashDetailBean crashDetailBean, Map<String, String> map) {
        String value;
        if (map != null && !map.isEmpty()) {
            crashDetailBean.S = new LinkedHashMap(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (!ap.b(entry.getKey())) {
                    String key = entry.getKey();
                    if (key.length() > 100) {
                        key = key.substring(0, 100);
                        al.d("setted key length is over limit %d substring to %s", 100, key);
                    }
                    if (!ap.b(entry.getValue()) && entry.getValue().length() > 100000) {
                        value = entry.getValue().substring(entry.getValue().length() - 100000);
                        al.d("setted %s value length is over limit %d substring", key, 100000);
                    } else {
                        value = entry.getValue();
                    }
                    crashDetailBean.S.put(key, value);
                    al.a("add setted key %s value size:%d", key, Integer.valueOf(value.length()));
                }
            }
            return;
        }
        al.d("extra map is empty. CrashBean won't have userDatas.", new Object[0]);
    }

    private static void a(ArrayList<bn> arrayList, CrashDetailBean crashDetailBean) {
        String str;
        if (crashDetailBean.f20629j && (str = crashDetailBean.f20638s) != null && str.length() > 0) {
            try {
                arrayList.add(new bn((byte) 1, "alltimes.txt", crashDetailBean.f20638s.getBytes("utf-8")));
            } catch (Exception e2) {
                e2.printStackTrace();
                al.a(e2);
            }
        }
    }

    private static void a(ArrayList<bn> arrayList, String str) {
        if (str != null) {
            try {
                arrayList.add(new bn((byte) 1, "log.txt", str.getBytes("utf-8")));
            } catch (Exception e2) {
                e2.printStackTrace();
                al.a(e2);
            }
        }
    }

    private static void a(ArrayList<bn> arrayList, String str, Context context) {
        if (str != null) {
            try {
                bn bnVarA = a("backupRecord.zip", context, str);
                if (bnVarA != null) {
                    al.c("attach backup record", new Object[0]);
                    arrayList.add(bnVarA);
                }
            } catch (Exception e2) {
                al.a(e2);
            }
        }
    }

    private static void a(ArrayList<bn> arrayList, byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        try {
            bn bnVar = new bn((byte) 2, "buglylog.zip", bArr);
            al.c("attach user log", new Object[0]);
            arrayList.add(bnVar);
        } catch (Exception e2) {
            al.a(e2);
        }
    }

    private static void a(ArrayList<bn> arrayList, CrashDetailBean crashDetailBean, Context context) {
        bn bnVarA;
        if (crashDetailBean.f20621b != 3) {
            return;
        }
        al.c("crashBean.anrMessages:%s", crashDetailBean.T);
        try {
            Map<String, String> map = crashDetailBean.T;
            if (map != null && map.containsKey("BUGLY_CR_01")) {
                if (!TextUtils.isEmpty(crashDetailBean.T.get("BUGLY_CR_01"))) {
                    arrayList.add(new bn((byte) 1, "anrMessage.txt", crashDetailBean.T.get("BUGLY_CR_01").getBytes("utf-8")));
                    al.c("attach anr message", new Object[0]);
                }
                crashDetailBean.T.remove("BUGLY_CR_01");
            }
            String str = crashDetailBean.f20641v;
            if (str == null || (bnVarA = a("trace.zip", context, str)) == null) {
                return;
            }
            al.c("attach traces", new Object[0]);
            arrayList.add(bnVarA);
        } catch (Exception e2) {
            e2.printStackTrace();
            al.a(e2);
        }
    }

    private static void a(ArrayList<bn> arrayList, List<String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        try {
            arrayList.add(new bn((byte) 1, "martianlog.txt", sb.toString().getBytes("utf-8")));
            al.c("attach pageTracingList", new Object[0]);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static Map<String, String> a(CrashDetailBean crashDetailBean, aa aaVar) {
        HashMap map = new HashMap(30);
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(crashDetailBean.C);
            map.put("A9", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(crashDetailBean.D);
            map.put("A11", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(crashDetailBean.E);
            map.put("A10", sb3.toString());
            map.put("A23", crashDetailBean.f20625f);
            StringBuilder sb4 = new StringBuilder();
            aaVar.getClass();
            map.put("A7", sb4.toString());
            map.put("A6", aa.n());
            map.put("A5", aaVar.m());
            map.put("A22", aaVar.g());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(crashDetailBean.G);
            map.put("A2", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append(crashDetailBean.F);
            map.put("A1", sb6.toString());
            map.put("A24", aaVar.f20686k);
            StringBuilder sb7 = new StringBuilder();
            sb7.append(crashDetailBean.H);
            map.put("A17", sb7.toString());
            map.put("A25", aaVar.g());
            map.put("A15", aaVar.q());
            StringBuilder sb8 = new StringBuilder();
            sb8.append(aaVar.r());
            map.put("A13", sb8.toString());
            map.put("A34", crashDetailBean.A);
            if (aaVar.G != null) {
                map.put("productIdentify", aaVar.G);
            }
            map.put("A26", URLEncoder.encode(crashDetailBean.L, "utf-8"));
            if (crashDetailBean.f20621b == 1) {
                map.put("A27", crashDetailBean.O);
                map.put("A28", crashDetailBean.N);
                StringBuilder sb9 = new StringBuilder();
                sb9.append(crashDetailBean.f20630k);
                map.put("A29", sb9.toString());
            }
            map.put("A30", crashDetailBean.P);
            StringBuilder sb10 = new StringBuilder();
            sb10.append(crashDetailBean.Q);
            map.put("A18", sb10.toString());
            StringBuilder sb11 = new StringBuilder();
            sb11.append(true ^ crashDetailBean.R);
            map.put("A36", sb11.toString());
            StringBuilder sb12 = new StringBuilder();
            sb12.append(aaVar.f20701z);
            map.put("F02", sb12.toString());
            StringBuilder sb13 = new StringBuilder();
            sb13.append(aaVar.A);
            map.put("F03", sb13.toString());
            map.put("F04", aaVar.d());
            StringBuilder sb14 = new StringBuilder();
            sb14.append(aaVar.B);
            map.put("F05", sb14.toString());
            map.put("F06", aaVar.f20700y);
            map.put("F08", aaVar.E);
            map.put("F09", aaVar.F);
            StringBuilder sb15 = new StringBuilder();
            sb15.append(aaVar.C);
            map.put("F10", sb15.toString());
            a(map, crashDetailBean);
        } catch (Exception e2) {
            e2.printStackTrace();
            al.a(e2);
        }
        return map;
    }

    private static void a(Map<String, String> map, CrashDetailBean crashDetailBean) {
        if (crashDetailBean.U >= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(crashDetailBean.U);
            map.put("C01", sb.toString());
        }
        if (crashDetailBean.V >= 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(crashDetailBean.V);
            map.put("C02", sb2.toString());
        }
        Map<String, String> map2 = crashDetailBean.W;
        if (map2 != null && map2.size() > 0) {
            for (Map.Entry<String, String> entry : crashDetailBean.W.entrySet()) {
                map.put("C03_" + entry.getKey(), entry.getValue());
            }
        }
        Map<String, String> map3 = crashDetailBean.X;
        if (map3 == null || map3.size() <= 0) {
            return;
        }
        for (Map.Entry<String, String> entry2 : crashDetailBean.X.entrySet()) {
            map.put("C04_" + entry2.getKey(), entry2.getValue());
        }
    }

    static /* synthetic */ void a(List list, boolean z2, long j2, String str, String str2) {
        if (list == null || list.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CrashDetailBean crashDetailBean = (CrashDetailBean) it.next();
            String str3 = f20826l.get(Integer.valueOf(crashDetailBean.f20621b));
            if (!TextUtils.isEmpty(str3)) {
                arrayList.add(new ag.c(crashDetailBean.f20622c, str3, crashDetailBean.f20637r, z2, j2, str, str2));
            }
        }
        ag.a.f20722a.a(arrayList);
    }
}
