package com.xiaomi.accountsdk.diagnosis.b;

import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final ThreadLocal<SimpleDateFormat> f23298a = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.accountsdk.diagnosis.b.a.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat(CalendarUtils.yyyyMMdd, Locale.US);
        }
    };

    private static File a() {
        String str = f23298a.get().format(new Date());
        return new File(com.xiaomi.accountsdk.diagnosis.b.a(), str + ".log");
    }

    private static void a(Closeable closeable) throws IOException {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException unused) {
        }
    }

    public static void a(String str) throws Throwable {
        File fileA = a();
        File parentFile = fileA.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            return;
        }
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(fileA, true));
                try {
                    bufferedWriter2.append((CharSequence) str);
                    bufferedWriter2.newLine();
                    a(bufferedWriter2);
                } catch (IOException e2) {
                    e = e2;
                    bufferedWriter = bufferedWriter2;
                    e.printStackTrace();
                    a(bufferedWriter);
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    a(bufferedWriter);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
