package com.xiaomi.push;

/* loaded from: classes4.dex */
public class bd implements bf {

    /* renamed from: a, reason: collision with root package name */
    private final String f23486a;

    /* renamed from: b, reason: collision with root package name */
    private final String f23487b;

    public bd(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        this.f23486a = str;
        this.f23487b = str2;
    }

    @Override // com.xiaomi.push.bf
    public String a() {
        return this.f23486a;
    }

    @Override // com.xiaomi.push.bf
    public String b() {
        return this.f23487b;
    }
}
