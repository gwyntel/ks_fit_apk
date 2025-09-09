package com.xiaomi.accountsdk.diagnosis.e;

import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final ThreadLocal<SimpleDateFormat> f23314a = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.accountsdk.diagnosis.e.c.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat(CalendarUtils.yyyyMMddHHmmss, Locale.US);
        }
    };

    public static File a() throws InterruptedException {
        File fileB = b();
        File parentFile = fileB.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            return null;
        }
        try {
            Runtime.getRuntime().exec("logcat -d -f " + fileB.getAbsolutePath()).waitFor();
        } catch (IOException | InterruptedException e2) {
            e2.printStackTrace();
        }
        return fileB;
    }

    private static File b() {
        String str = f23314a.get().format(new Date());
        return new File(com.xiaomi.accountsdk.diagnosis.b.b(), str + ".logcat");
    }
}
