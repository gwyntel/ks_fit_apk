package com.xiaomi.push;

import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public final class gw extends gq {
    public gw() {
        a("PING", (String) null);
        a("0");
        a(0);
    }

    @Override // com.xiaomi.push.gq
    /* renamed from: a */
    ByteBuffer mo445a(ByteBuffer byteBuffer) {
        return m448a().length == 0 ? byteBuffer : super.mo445a(byteBuffer);
    }

    @Override // com.xiaomi.push.gq
    public int c() {
        if (m448a().length == 0) {
            return 0;
        }
        return super.c();
    }
}
