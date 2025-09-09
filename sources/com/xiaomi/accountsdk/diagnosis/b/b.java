package com.xiaomi.accountsdk.diagnosis.b;

import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.accountsdk.diagnosis.e.d;
import com.xiaomi.mipush.sdk.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static String f23299a;

    /* renamed from: b, reason: collision with root package name */
    private static final ThreadLocal<SimpleDateFormat> f23300b = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.accountsdk.diagnosis.b.b.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        }
    };

    private static String a() {
        return f23300b.get().format(new Date());
    }

    private static String b() throws Throwable {
        if (f23299a == null) {
            String strA = d.a(Process.myPid());
            if (TextUtils.isEmpty(strA)) {
                strA = "UnknownProcess";
            }
            f23299a = strA;
        }
        return f23299a;
    }

    public static String a(c cVar, String str) throws Throwable {
        String strB = b();
        return a() + " " + Process.myPid() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + Process.myTid() + "/" + strB + " " + cVar.toString() + "/" + str + ": ";
    }
}
