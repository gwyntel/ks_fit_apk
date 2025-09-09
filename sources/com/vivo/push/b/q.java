package com.vivo.push.b;

import android.text.TextUtils;
import com.vivo.push.model.InsideNotificationItem;

/* loaded from: classes4.dex */
public final class q extends v {

    /* renamed from: a, reason: collision with root package name */
    protected InsideNotificationItem f23060a;

    /* renamed from: b, reason: collision with root package name */
    private String f23061b;

    public q() {
        super(4);
    }

    @Override // com.vivo.push.b.v, com.vivo.push.b.s, com.vivo.push.o
    protected final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        String strB = com.vivo.push.util.q.b(this.f23060a);
        this.f23061b = strB;
        aVar.a("notification_v1", strB);
    }

    public final InsideNotificationItem d() {
        return this.f23060a;
    }

    public final String e() {
        if (!TextUtils.isEmpty(this.f23061b)) {
            return this.f23061b;
        }
        InsideNotificationItem insideNotificationItem = this.f23060a;
        if (insideNotificationItem == null) {
            return null;
        }
        return com.vivo.push.util.q.b(insideNotificationItem);
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final String toString() {
        return "OnNotifyArrivedCommand";
    }

    @Override // com.vivo.push.b.v, com.vivo.push.b.s, com.vivo.push.o
    protected final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        String strA = aVar.a("notification_v1");
        this.f23061b = strA;
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        InsideNotificationItem insideNotificationItemA = com.vivo.push.util.q.a(this.f23061b);
        this.f23060a = insideNotificationItemA;
        if (insideNotificationItemA != null) {
            insideNotificationItemA.setMsgId(f());
        }
    }
}
