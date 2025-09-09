package anetwork.channel.entity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final ExecutorService[] f7210a = new ExecutorService[2];

    /* renamed from: b, reason: collision with root package name */
    private static AtomicInteger f7211b = new AtomicInteger(0);

    static {
        for (int i2 = 0; i2 < 2; i2++) {
            f7210a[i2] = Executors.newSingleThreadExecutor(new b());
        }
    }

    public static void a(int i2, Runnable runnable) {
        f7210a[Math.abs(i2 % 2)].submit(runnable);
    }
}
