package com.taobao.accs.data;

import com.taobao.accs.common.ThreadPoolExecutorFactory;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class a {
    public static final int SPLITTED_DATA_INDEX = 17;
    public static final int SPLITTED_DATA_MD5 = 18;
    public static final int SPLITTED_DATA_NUMS = 16;
    public static final int SPLITTED_TIME_OUT = 15;

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f20127a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* renamed from: b, reason: collision with root package name */
    private String f20128b;

    /* renamed from: c, reason: collision with root package name */
    private int f20129c;

    /* renamed from: d, reason: collision with root package name */
    private String f20130d;

    /* renamed from: e, reason: collision with root package name */
    private long f20131e;

    /* renamed from: g, reason: collision with root package name */
    private ScheduledFuture<?> f20133g;

    /* renamed from: f, reason: collision with root package name */
    private volatile int f20132f = 0;

    /* renamed from: h, reason: collision with root package name */
    private Map<Integer, byte[]> f20134h = new TreeMap(new b(this));

    public a(String str, int i2, String str2) {
        this.f20128b = str;
        this.f20129c = i2;
        this.f20130d = str2;
    }

    public void a(long j2) {
        if (j2 <= 0) {
            j2 = 30000;
        }
        this.f20133g = ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new c(this), j2, TimeUnit.MILLISECONDS);
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00f1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] a(int r20, int r21, byte[] r22) {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.data.a.a(int, int, byte[]):byte[]");
    }

    private static char[] a(byte[] bArr) {
        char[] cArr = new char[bArr.length << 1];
        int i2 = 0;
        for (byte b2 : bArr) {
            int i3 = i2 + 1;
            char[] cArr2 = f20127a;
            cArr[i2] = cArr2[(b2 & 240) >>> 4];
            i2 += 2;
            cArr[i3] = cArr2[b2 & 15];
        }
        return cArr;
    }
}
