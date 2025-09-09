package com.huawei.hms.base.log;

import android.os.Process;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.apache.commons.io.IOUtils;

/* loaded from: classes.dex */
public class e {

    /* renamed from: b, reason: collision with root package name */
    private String f15878b;

    /* renamed from: c, reason: collision with root package name */
    private String f15879c;

    /* renamed from: d, reason: collision with root package name */
    private int f15880d;

    /* renamed from: g, reason: collision with root package name */
    private String f15883g;

    /* renamed from: h, reason: collision with root package name */
    private int f15884h;

    /* renamed from: i, reason: collision with root package name */
    private int f15885i;

    /* renamed from: j, reason: collision with root package name */
    private int f15886j;

    /* renamed from: a, reason: collision with root package name */
    private final StringBuilder f15877a = new StringBuilder();

    /* renamed from: e, reason: collision with root package name */
    private long f15881e = 0;

    /* renamed from: f, reason: collision with root package name */
    private long f15882f = 0;

    e(int i2, String str, int i3, String str2) {
        this.f15879c = "HMS";
        this.f15886j = i2;
        this.f15878b = str;
        this.f15880d = i3;
        if (str2 != null) {
            this.f15879c = str2;
        }
        b();
    }

    public static String a(int i2) {
        return i2 != 3 ? i2 != 4 ? i2 != 5 ? i2 != 6 ? String.valueOf(i2) : ExifInterface.LONGITUDE_EAST : ExifInterface.LONGITUDE_WEST : "I" : "D";
    }

    private e b() {
        this.f15881e = System.currentTimeMillis();
        Thread threadCurrentThread = Thread.currentThread();
        this.f15882f = threadCurrentThread.getId();
        this.f15884h = Process.myPid();
        StackTraceElement[] stackTrace = threadCurrentThread.getStackTrace();
        int length = stackTrace.length;
        int i2 = this.f15886j;
        if (length > i2) {
            StackTraceElement stackTraceElement = stackTrace[i2];
            this.f15883g = stackTraceElement.getFileName();
            this.f15885i = stackTraceElement.getLineNumber();
        }
        return this;
    }

    public String c() {
        StringBuilder sb = new StringBuilder();
        b(sb);
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        b(sb);
        a(sb);
        return sb.toString();
    }

    public <T> e a(T t2) {
        this.f15877a.append(t2);
        return this;
    }

    public e a(Throwable th) {
        a((e) '\n').a((e) Log.getStackTraceString(th));
        return this;
    }

    public String a() {
        StringBuilder sb = new StringBuilder();
        a(sb);
        return sb.toString();
    }

    private StringBuilder b(StringBuilder sb) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        sb.append('[');
        sb.append(simpleDateFormat.format(Long.valueOf(this.f15881e)));
        String strA = a(this.f15880d);
        sb.append(' ');
        sb.append(strA);
        sb.append(IOUtils.DIR_SEPARATOR_UNIX);
        sb.append(this.f15879c);
        sb.append(IOUtils.DIR_SEPARATOR_UNIX);
        sb.append(this.f15878b);
        sb.append(' ');
        sb.append(this.f15884h);
        sb.append(':');
        sb.append(this.f15882f);
        sb.append(' ');
        sb.append(this.f15883g);
        sb.append(':');
        sb.append(this.f15885i);
        sb.append(']');
        return sb;
    }

    private StringBuilder a(StringBuilder sb) {
        sb.append(' ');
        sb.append(this.f15877a.toString());
        return sb;
    }
}
