package com.meizu.cloud.pushsdk.f.d;

import com.meizu.cloud.pushsdk.f.b.c;
import com.meizu.cloud.pushsdk.f.g.d;
import com.meizu.cloud.pushsdk.f.g.e;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private final List<com.meizu.cloud.pushsdk.f.b.b> f19584a;

    /* renamed from: b, reason: collision with root package name */
    private final long f19585b;

    /* renamed from: c, reason: collision with root package name */
    private final String f19586c;

    /* renamed from: com.meizu.cloud.pushsdk.f.d.a$a, reason: collision with other inner class name */
    public static abstract class AbstractC0157a<T extends AbstractC0157a<T>> {

        /* renamed from: a, reason: collision with root package name */
        private List<com.meizu.cloud.pushsdk.f.b.b> f19587a = new LinkedList();

        /* renamed from: b, reason: collision with root package name */
        private long f19588b = System.currentTimeMillis();

        /* renamed from: c, reason: collision with root package name */
        private String f19589c = e.a();

        protected abstract T a();

        public T a(long j2) {
            this.f19588b = j2;
            return (T) a();
        }
    }

    protected a(AbstractC0157a<?> abstractC0157a) {
        d.a(((AbstractC0157a) abstractC0157a).f19587a);
        d.a(((AbstractC0157a) abstractC0157a).f19589c);
        d.a(!((AbstractC0157a) abstractC0157a).f19589c.isEmpty(), "eventId cannot be empty");
        this.f19584a = ((AbstractC0157a) abstractC0157a).f19587a;
        this.f19585b = ((AbstractC0157a) abstractC0157a).f19588b;
        this.f19586c = ((AbstractC0157a) abstractC0157a).f19589c;
    }

    protected c a(c cVar) {
        cVar.a(NotificationStyle.EXPANDABLE_IMAGE_URL, a());
        cVar.a("ts", Long.toString(c()));
        return cVar;
    }

    public List<com.meizu.cloud.pushsdk.f.b.b> b() {
        return new ArrayList(this.f19584a);
    }

    public long c() {
        return this.f19585b;
    }

    public String a() {
        return this.f19586c;
    }
}
