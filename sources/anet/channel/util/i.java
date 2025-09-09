package anet.channel.util;

import android.text.TextUtils;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static AtomicInteger f7094a = new AtomicInteger();

    public static String a(String str) {
        if (f7094a.get() == Integer.MAX_VALUE) {
            f7094a.set(0);
        }
        return !TextUtils.isEmpty(str) ? StringUtils.concatString(str, ".AWCN", String.valueOf(f7094a.incrementAndGet())) : StringUtils.concatString("AWCN", String.valueOf(f7094a.incrementAndGet()));
    }
}
