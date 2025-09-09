package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class e extends d {

    /* renamed from: h, reason: collision with root package name */
    static HashMap<String, byte[]> f21081h;

    /* renamed from: i, reason: collision with root package name */
    static HashMap<String, HashMap<String, byte[]>> f21082i;

    /* renamed from: g, reason: collision with root package name */
    protected g f21083g;

    /* renamed from: j, reason: collision with root package name */
    private int f21084j;

    public e() {
        g gVar = new g();
        this.f21083g = gVar;
        this.f21084j = 0;
        gVar.f21090a = (short) 2;
    }

    @Override // com.tencent.bugly.proguard.d, com.tencent.bugly.proguard.c
    public final <T> void a(String str, T t2) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, UnsupportedEncodingException {
        if (str.startsWith(".")) {
            throw new IllegalArgumentException("put name can not startwith . , now is ".concat(str));
        }
        super.a(str, (String) t2);
    }

    @Override // com.tencent.bugly.proguard.d
    public final void b() {
        super.b();
        this.f21083g.f21090a = (short) 3;
    }

    public final void c(String str) {
        this.f21083g.f21095f = str;
    }

    public final void c() {
        this.f21083g.f21093d = 1;
    }

    public final void b(String str) {
        this.f21083g.f21094e = str;
    }

    @Override // com.tencent.bugly.proguard.d, com.tencent.bugly.proguard.c
    public final byte[] a() throws UnsupportedEncodingException {
        g gVar = this.f21083g;
        if (gVar.f21090a == 2) {
            if (!gVar.f21094e.equals("")) {
                if (this.f21083g.f21095f.equals("")) {
                    throw new IllegalArgumentException("funcName can not is null");
                }
            } else {
                throw new IllegalArgumentException("servantName can not is null");
            }
        } else {
            if (gVar.f21094e == null) {
                gVar.f21094e = "";
            }
            if (gVar.f21095f == null) {
                gVar.f21095f = "";
            }
        }
        l lVar = new l(0);
        lVar.a(this.f21075c);
        if (this.f21083g.f21090a == 2) {
            lVar.a((Map) this.f21073a, 0);
        } else {
            lVar.a((Map) ((d) this).f21078e, 0);
        }
        this.f21083g.f21096g = n.a(lVar.f21106a);
        l lVar2 = new l(0);
        lVar2.a(this.f21075c);
        this.f21083g.a(lVar2);
        byte[] bArrA = n.a(lVar2.f21106a);
        int length = bArrA.length + 4;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length);
        byteBufferAllocate.putInt(length).put(bArrA).flip();
        return byteBufferAllocate.array();
    }

    @Override // com.tencent.bugly.proguard.d, com.tencent.bugly.proguard.c
    public final void a(byte[] bArr) {
        if (bArr.length >= 4) {
            try {
                k kVar = new k(bArr, (byte) 0);
                kVar.a(this.f21075c);
                this.f21083g.a(kVar);
                g gVar = this.f21083g;
                if (gVar.f21090a == 3) {
                    k kVar2 = new k(gVar.f21096g);
                    kVar2.a(this.f21075c);
                    if (f21081h == null) {
                        HashMap<String, byte[]> map = new HashMap<>();
                        f21081h = map;
                        map.put("", new byte[0]);
                    }
                    ((d) this).f21078e = kVar2.a((Map) f21081h, 0, false);
                    return;
                }
                k kVar3 = new k(gVar.f21096g);
                kVar3.a(this.f21075c);
                if (f21082i == null) {
                    f21082i = new HashMap<>();
                    HashMap<String, byte[]> map2 = new HashMap<>();
                    map2.put("", new byte[0]);
                    f21082i.put("", map2);
                }
                this.f21073a = kVar3.a((Map) f21082i, 0, false);
                this.f21074b = new HashMap<>();
                return;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
        throw new IllegalArgumentException("decode package must include size head");
    }
}
