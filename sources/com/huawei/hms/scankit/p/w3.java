package com.huawei.hms.scankit.p;

import android.os.Bundle;
import android.util.SparseArray;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.huawei.hms.feature.DynamicModuleInitializer;
import com.huawei.hms.hmsscankit.DetailRect;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.huawei.hms.utils.FileUtil;
import com.kingsmith.miot.KsProperty;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class w3 extends u3 {

    /* renamed from: h, reason: collision with root package name */
    private volatile String f17917h;

    /* renamed from: i, reason: collision with root package name */
    private volatile String f17918i;

    /* renamed from: j, reason: collision with root package name */
    private boolean f17919j;

    /* renamed from: k, reason: collision with root package name */
    private volatile long f17920k;

    /* renamed from: l, reason: collision with root package name */
    public d f17921l;

    class a extends SimpleDateFormat {
        a(String str) {
            super(str);
            setTimeZone(TimeZone.getTimeZone("UTC"));
        }
    }

    class b extends SimpleDateFormat {
        b(String str) {
            super(str);
            setTimeZone(TimeZone.getTimeZone("UTC"));
        }
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        private int f17924a;

        /* renamed from: b, reason: collision with root package name */
        private String f17925b;

        /* renamed from: c, reason: collision with root package name */
        private String f17926c;

        /* renamed from: d, reason: collision with root package name */
        private long f17927d;

        /* renamed from: e, reason: collision with root package name */
        private long f17928e;

        /* renamed from: f, reason: collision with root package name */
        private String f17929f;

        /* renamed from: g, reason: collision with root package name */
        private String f17930g;

        /* renamed from: h, reason: collision with root package name */
        private boolean f17931h;

        /* renamed from: i, reason: collision with root package name */
        private int f17932i;

        /* renamed from: j, reason: collision with root package name */
        private boolean f17933j;

        /* synthetic */ c(long j2, String str, String str2, boolean z2, int i2, int i3, a aVar) {
            this(j2, str, str2, z2, i2, i3);
        }

        private c(long j2, String str, String str2, boolean z2, int i2, int i3) {
            this.f17927d = j2;
            this.f17925b = str;
            this.f17926c = str2;
            this.f17931h = z2;
            this.f17932i = i2;
            this.f17924a = i3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public c b(String str) {
            this.f17930g = str;
            return this;
        }

        public c a(int i2) {
            this.f17924a = i2;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public c a(long j2) {
            this.f17928e = j2;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public c a(boolean z2) {
            this.f17933j = z2;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public c a(String str) {
            this.f17929f = str;
            return this;
        }
    }

    public class d {

        /* renamed from: a, reason: collision with root package name */
        private String f17934a = d.class.getSimpleName();

        /* renamed from: b, reason: collision with root package name */
        public Timer f17935b = new Timer();

        /* renamed from: c, reason: collision with root package name */
        private volatile boolean f17936c = true;

        /* renamed from: d, reason: collision with root package name */
        private List<c> f17937d = new ArrayList(10);

        /* renamed from: e, reason: collision with root package name */
        private List<c> f17938e = new ArrayList(10);

        class a extends TimerTask {
            a() {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                try {
                    d.this.f17936c = true;
                    d.this.a();
                } catch (Exception unused) {
                    o4.b(d.this.f17934a, "onLog Exception");
                }
            }
        }

        private class b {

            /* renamed from: a, reason: collision with root package name */
            private StringBuilder f17941a;

            /* renamed from: b, reason: collision with root package name */
            private AtomicInteger[] f17942b;

            /* renamed from: c, reason: collision with root package name */
            private String[] f17943c;

            /* renamed from: d, reason: collision with root package name */
            private long[] f17944d;

            private b() {
                this.f17941a = new StringBuilder(100);
                this.f17942b = new AtomicInteger[]{new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), new AtomicInteger()};
                this.f17943c = new String[]{"lt10K:", "lt100K:", "lt1M:", "lt3M:", "lt10M:", "lt40M:", "gt40M:"};
                this.f17944d = new long[]{FileUtil.LOCAL_REPORT_FILE_MAX_SIZE, OSSConstants.MIN_PART_SIZE_LIMIT, 1048576, 3145728, 10485760, 41943040, Long.MAX_VALUE};
            }

            /* JADX INFO: Access modifiers changed from: private */
            public String a() {
                StringBuilder sb = this.f17941a;
                sb.delete(0, sb.length());
                this.f17941a.append("{");
                for (int i2 = 0; i2 < this.f17942b.length; i2++) {
                    this.f17941a.append(this.f17943c[i2]);
                    this.f17941a.append(this.f17942b[i2]);
                    this.f17941a.append(",");
                }
                this.f17941a.replace(r0.length() - 1, this.f17941a.length(), com.alipay.sdk.m.u.i.f9804d);
                return this.f17941a.toString();
            }

            /* synthetic */ b(d dVar, a aVar) {
                this();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void a(int i2) {
                int i3 = 0;
                while (true) {
                    AtomicInteger[] atomicIntegerArr = this.f17942b;
                    if (i3 >= atomicIntegerArr.length) {
                        return;
                    }
                    if (i2 <= this.f17944d[i3]) {
                        atomicIntegerArr[i3].addAndGet(1);
                        return;
                    }
                    i3++;
                }
            }
        }

        private class c {

            /* renamed from: a, reason: collision with root package name */
            private StringBuilder f17946a;

            /* renamed from: b, reason: collision with root package name */
            private SparseArray<AtomicInteger> f17947b;

            class a extends SparseArray<AtomicInteger> {
                a() {
                    put(0, new AtomicInteger());
                }
            }

            class b extends AtomicInteger {
                b() {
                    addAndGet(1);
                }
            }

            private c() {
                this.f17946a = new StringBuilder(60);
                this.f17947b = new a();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void a(int i2) {
                if (this.f17947b.get(i2) == null) {
                    this.f17947b.put(i2, new b());
                } else {
                    this.f17947b.get(i2).addAndGet(1);
                }
            }

            /* synthetic */ c(d dVar, a aVar) {
                this();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public String a() {
                StringBuilder sb = this.f17946a;
                sb.delete(0, sb.length());
                this.f17946a.append("{");
                for (int i2 = 0; i2 < this.f17947b.size(); i2++) {
                    this.f17946a.append(this.f17947b.keyAt(i2));
                    this.f17946a.append(":");
                    this.f17946a.append(this.f17947b.valueAt(i2));
                    this.f17946a.append(",");
                }
                this.f17946a.replace(r0.length() - 1, this.f17946a.length(), com.alipay.sdk.m.u.i.f9804d);
                return this.f17946a.toString();
            }
        }

        public d() {
        }

        public void b() {
            Timer timer = this.f17935b;
            if (timer != null) {
                timer.cancel();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(c cVar) {
            if (this.f17937d.size() > 100) {
                return;
            }
            synchronized (this) {
                try {
                    this.f17937d.add(cVar);
                    if (this.f17936c) {
                        this.f17936c = false;
                        this.f17935b.schedule(new a(), 1000L);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a() {
            if (this.f17937d.size() > 0) {
                synchronized (this) {
                    List<c> list = this.f17937d;
                    List<c> list2 = this.f17938e;
                    this.f17937d = list2;
                    this.f17938e = list;
                    list2.clear();
                }
                a(this.f17938e);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r24v2, types: [java.lang.Boolean] */
        private void a(List<c> list) {
            HashSet<String> hashSet = new HashSet();
            Iterator<c> it = list.iterator();
            while (it.hasNext()) {
                hashSet.add(it.next().f17926c);
            }
            for (String str : hashSet) {
                a aVar = null;
                c cVar = new c(this, aVar);
                b bVar = new b(this, aVar);
                String str2 = "";
                long j2 = Long.MIN_VALUE;
                long j3 = 0;
                long j4 = 0;
                long j5 = 0;
                long j6 = 0;
                long j7 = Long.MAX_VALUE;
                String str3 = "";
                String str4 = str3;
                for (c cVar2 : list) {
                    str2 = cVar2.f17925b;
                    str3 = cVar2.f17929f;
                    str4 = cVar2.f17930g;
                    ?? ValueOf = Boolean.valueOf(cVar2.f17931h);
                    j4 += cVar2.f17928e - cVar2.f17927d;
                    cVar.a(cVar2.f17924a);
                    bVar.a(cVar2.f17932i);
                    j3++;
                    if (cVar2.f17933j) {
                        j6++;
                    }
                    if (cVar2.f17924a != 0) {
                        j5++;
                    }
                    if (cVar2.f17928e - cVar2.f17927d < j7) {
                        j7 = cVar2.f17928e - cVar2.f17927d;
                    }
                    if (cVar2.f17928e - cVar2.f17927d > j2) {
                        j2 = cVar2.f17928e - cVar2.f17927d;
                    }
                    aVar = ValueOf;
                }
                LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
                w3.this.g();
                linkedHashMap.putAll(w3.this.f17845b);
                linkedHashMap.put("result", cVar.a());
                linkedHashMap.put("imgSizeHistogram", bVar.a());
                linkedHashMap.put("callTime", str2);
                linkedHashMap.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, str);
                if (j3 != 0) {
                    j4 /= j3;
                }
                linkedHashMap.put("costTime", String.valueOf(j4));
                linkedHashMap.put("allCnt", String.valueOf(j3));
                linkedHashMap.put("failCnt", String.valueOf(j5));
                linkedHashMap.put("codeCnt", String.valueOf(j6));
                linkedHashMap.put("scanType", str3);
                linkedHashMap.put("sceneType", str4);
                linkedHashMap.put("min", String.valueOf(j7));
                linkedHashMap.put(KsProperty.Max, String.valueOf(j2));
                linkedHashMap.put("algPhotoMode", String.valueOf(aVar));
                a4.b().b("60001", linkedHashMap);
            }
        }
    }

    public w3(Bundle bundle, String str) {
        super(bundle, DynamicModuleInitializer.getContext().getApplicationContext());
        this.f17919j = false;
        this.f17921l = new d();
        this.f17845b.put("apiName", str);
        if (DetailRect.PHOTO_MODE.equals(str)) {
            this.f17919j = true;
        }
    }

    public void a(String str) {
        this.f17845b.put("algapi", str);
    }

    public c a(boolean z2, int i2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            if (this.f17919j) {
                return new c(jCurrentTimeMillis, new a("yyyyMMddHHmmss.SSS").format(Long.valueOf(jCurrentTimeMillis)), UUID.randomUUID().toString(), z2, i2, 0, null);
            }
            if (jCurrentTimeMillis - this.f17920k > 1500) {
                String str = new b("yyyyMMddHHmmss.SSS").format(Long.valueOf(jCurrentTimeMillis));
                String string = UUID.randomUUID().toString();
                if (jCurrentTimeMillis - this.f17920k > 1500) {
                    this.f17917h = str;
                    this.f17918i = string;
                    this.f17920k = jCurrentTimeMillis;
                }
            }
            return new c(jCurrentTimeMillis, this.f17917h, this.f17918i, z2, i2, 0, null);
        } catch (Exception unused) {
            o4.b("HaLog6001", "exception happens");
            return new c(jCurrentTimeMillis, this.f17917h, this.f17918i, z2, i2, 0, null);
        }
    }

    public void a(HmsScan[] hmsScanArr, c cVar) {
        try {
            String str = u3.f17840d;
            String strB = u3.f17841e;
            if (a()) {
                boolean z2 = false;
                int i2 = 0;
                z2 = false;
                if (hmsScanArr != null && hmsScanArr.length > 0) {
                    int length = hmsScanArr.length;
                    while (i2 < length) {
                        HmsScan hmsScan = hmsScanArr[i2];
                        String strA = u3.a(hmsScan.scanType);
                        i2++;
                        strB = u3.b(hmsScan.scanTypeForm);
                        str = strA;
                    }
                    z2 = true;
                }
                this.f17921l.a(cVar.a(System.currentTimeMillis()).a(z2).a(str).b(strB));
                this.f17920k = cVar.f17928e;
            }
        } catch (NullPointerException unused) {
            o4.b("HaLog60001", "nullPoint");
        } catch (Exception unused2) {
            o4.b("HaLog60001", "logEnd Exception");
        }
    }
}
