package com.huawei.hms.availableupdate;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    public static final b f15866b = new b();

    /* renamed from: c, reason: collision with root package name */
    private static final Object f15867c = new Object();

    /* renamed from: a, reason: collision with root package name */
    private final List<Activity> f15868a = new ArrayList(1);

    public void a(Activity activity) {
        synchronized (f15867c) {
            try {
                for (Activity activity2 : this.f15868a) {
                    if (activity2 != null && activity2 != activity && !activity2.isFinishing()) {
                        activity2.finish();
                    }
                }
                this.f15868a.add(activity);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void b(Activity activity) {
        synchronized (f15867c) {
            this.f15868a.remove(activity);
        }
    }
}
