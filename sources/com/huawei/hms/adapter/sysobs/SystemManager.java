package com.huawei.hms.adapter.sysobs;

import android.content.Intent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class SystemManager {

    /* renamed from: a, reason: collision with root package name */
    private static SystemManager f15791a = new SystemManager();

    /* renamed from: b, reason: collision with root package name */
    private static final Object f15792b = new Object();

    /* renamed from: c, reason: collision with root package name */
    private static SystemNotifier f15793c = new a();

    private SystemManager() {
    }

    public static SystemManager getInstance() {
        return f15791a;
    }

    public static SystemNotifier getSystemNotifier() {
        return f15793c;
    }

    public void notifyNoticeResult(int i2) {
        f15793c.notifyNoticeObservers(i2);
    }

    public void notifyResolutionResult(Intent intent, String str) {
        f15793c.notifyObservers(intent, str);
    }

    public void notifyUpdateResult(int i2) {
        f15793c.notifyObservers(i2);
    }

    class a implements SystemNotifier {

        /* renamed from: a, reason: collision with root package name */
        private final List<SystemObserver> f15794a = new ArrayList();

        a() {
        }

        @Override // com.huawei.hms.adapter.sysobs.SystemNotifier
        public void notifyNoticeObservers(int i2) {
            synchronized (SystemManager.f15792b) {
                try {
                    Iterator<SystemObserver> it = this.f15794a.iterator();
                    while (it.hasNext()) {
                        if (it.next().onNoticeResult(i2)) {
                            it.remove();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.huawei.hms.adapter.sysobs.SystemNotifier
        public void notifyObservers(Intent intent, String str) {
            synchronized (SystemManager.f15792b) {
                try {
                    Iterator<SystemObserver> it = this.f15794a.iterator();
                    while (it.hasNext()) {
                        if (it.next().onSolutionResult(intent, str)) {
                            it.remove();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.huawei.hms.adapter.sysobs.SystemNotifier
        public void registerObserver(SystemObserver systemObserver) {
            if (systemObserver == null || this.f15794a.contains(systemObserver)) {
                return;
            }
            synchronized (SystemManager.f15792b) {
                this.f15794a.add(systemObserver);
            }
        }

        @Override // com.huawei.hms.adapter.sysobs.SystemNotifier
        public void unRegisterObserver(SystemObserver systemObserver) {
            synchronized (SystemManager.f15792b) {
                this.f15794a.remove(systemObserver);
            }
        }

        @Override // com.huawei.hms.adapter.sysobs.SystemNotifier
        public void notifyObservers(int i2) {
            synchronized (SystemManager.f15792b) {
                try {
                    Iterator<SystemObserver> it = this.f15794a.iterator();
                    while (it.hasNext()) {
                        if (it.next().onUpdateResult(i2)) {
                            it.remove();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
