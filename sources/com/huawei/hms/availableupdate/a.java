package com.huawei.hms.availableupdate;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class a {

    /* renamed from: c, reason: collision with root package name */
    public static final a f15862c = new a();

    /* renamed from: d, reason: collision with root package name */
    private static final Object f15863d = new Object();

    /* renamed from: a, reason: collision with root package name */
    private final AtomicBoolean f15864a = new AtomicBoolean(false);

    /* renamed from: b, reason: collision with root package name */
    private final List<Activity> f15865b = new ArrayList(1);

    public void a(Activity activity) {
        synchronized (f15863d) {
            try {
                for (Activity activity2 : this.f15865b) {
                    if (activity2 != null && activity2 != activity && !activity2.isFinishing()) {
                        activity2.finish();
                    }
                }
                this.f15865b.add(activity);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void b(Activity activity) {
        synchronized (f15863d) {
            this.f15865b.remove(activity);
        }
    }

    public void a(boolean z2) {
        this.f15864a.set(z2);
    }

    public AtomicBoolean a() {
        return this.f15864a;
    }
}
