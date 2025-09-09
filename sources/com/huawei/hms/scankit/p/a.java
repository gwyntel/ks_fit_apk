package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public class a extends Exception {

    /* renamed from: a, reason: collision with root package name */
    protected static final boolean f16942a;

    /* renamed from: b, reason: collision with root package name */
    protected static final StackTraceElement[] f16943b;

    /* renamed from: c, reason: collision with root package name */
    private static final a f16944c;

    static {
        f16942a = System.getProperty("surefire.test.class.path") != null;
        StackTraceElement[] stackTraceElementArr = new StackTraceElement[0];
        f16943b = stackTraceElementArr;
        a aVar = new a();
        f16944c = aVar;
        aVar.setStackTrace(stackTraceElementArr);
    }

    private a() {
    }

    public static a a() {
        return f16942a ? new a() : f16944c;
    }

    private a(String str) {
        super(str);
    }

    public static a a(String str) {
        return new a(str);
    }
}
