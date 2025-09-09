package de.greenrobot.event;

import java.lang.reflect.Method;

/* loaded from: classes4.dex */
final class SubscriberMethod {

    /* renamed from: a, reason: collision with root package name */
    final Method f24978a;

    /* renamed from: b, reason: collision with root package name */
    final ThreadMode f24979b;

    /* renamed from: c, reason: collision with root package name */
    final Class f24980c;

    /* renamed from: d, reason: collision with root package name */
    String f24981d;

    SubscriberMethod(Method method, ThreadMode threadMode, Class cls) {
        this.f24978a = method;
        this.f24979b = threadMode;
        this.f24980c = cls;
    }

    private synchronized void checkMethodString() {
        if (this.f24981d == null) {
            StringBuilder sb = new StringBuilder(64);
            sb.append(this.f24978a.getDeclaringClass().getName());
            sb.append('#');
            sb.append(this.f24978a.getName());
            sb.append('(');
            sb.append(this.f24980c.getName());
            this.f24981d = sb.toString();
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SubscriberMethod)) {
            return false;
        }
        checkMethodString();
        return this.f24981d.equals(((SubscriberMethod) obj).f24981d);
    }

    public int hashCode() {
        return this.f24978a.hashCode();
    }
}
