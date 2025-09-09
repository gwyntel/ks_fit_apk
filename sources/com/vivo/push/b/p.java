package com.vivo.push.b;

import android.text.TextUtils;
import com.vivo.push.model.InsideNotificationItem;

/* loaded from: classes4.dex */
public final class p extends com.vivo.push.o {

    /* renamed from: a, reason: collision with root package name */
    private String f23055a;

    /* renamed from: b, reason: collision with root package name */
    private String f23056b;

    /* renamed from: c, reason: collision with root package name */
    private byte[] f23057c;

    /* renamed from: d, reason: collision with root package name */
    private long f23058d;

    /* renamed from: e, reason: collision with root package name */
    private InsideNotificationItem f23059e;

    public p(String str, long j2, InsideNotificationItem insideNotificationItem) {
        super(5);
        this.f23055a = str;
        this.f23058d = j2;
        this.f23059e = insideNotificationItem;
    }

    @Override // com.vivo.push.o
    protected final void c(com.vivo.push.a aVar) {
        aVar.a("package_name", this.f23055a);
        aVar.a("notify_id", this.f23058d);
        aVar.a("notification_v1", com.vivo.push.util.q.b(this.f23059e));
        aVar.a("open_pkg_name", this.f23056b);
        aVar.a("open_pkg_name_encode", this.f23057c);
    }

    public final String d() {
        return this.f23055a;
    }

    public final long e() {
        return this.f23058d;
    }

    public final InsideNotificationItem f() {
        return this.f23059e;
    }

    @Override // com.vivo.push.o
    public final String toString() {
        return "OnNotificationClickCommand";
    }

    @Override // com.vivo.push.o
    protected final void d(com.vivo.push.a aVar) {
        this.f23055a = aVar.a("package_name");
        this.f23058d = aVar.b("notify_id", -1L);
        this.f23056b = aVar.a("open_pkg_name");
        this.f23057c = aVar.b("open_pkg_name_encode");
        String strA = aVar.a("notification_v1");
        if (!TextUtils.isEmpty(strA)) {
            this.f23059e = com.vivo.push.util.q.a(strA);
        }
        InsideNotificationItem insideNotificationItem = this.f23059e;
        if (insideNotificationItem != null) {
            insideNotificationItem.setMsgId(this.f23058d);
        }
    }

    public p() {
        super(5);
    }
}
