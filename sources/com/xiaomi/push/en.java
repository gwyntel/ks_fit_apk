package com.xiaomi.push;

/* loaded from: classes4.dex */
public class en {

    /* renamed from: a, reason: collision with root package name */
    private static volatile en f23664a;

    /* renamed from: a, reason: collision with other field name */
    private em f334a;

    public static en a() {
        if (f23664a == null) {
            synchronized (en.class) {
                try {
                    if (f23664a == null) {
                        f23664a = new en();
                    }
                } finally {
                }
            }
        }
        return f23664a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public em m312a() {
        return this.f334a;
    }

    public void a(em emVar) {
        this.f334a = emVar;
    }
}
