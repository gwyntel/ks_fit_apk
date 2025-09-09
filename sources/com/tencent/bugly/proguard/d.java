package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public class d extends c {

    /* renamed from: e, reason: collision with root package name */
    protected HashMap<String, byte[]> f21078e = null;

    /* renamed from: g, reason: collision with root package name */
    private HashMap<String, Object> f21080g = new HashMap<>();

    /* renamed from: f, reason: collision with root package name */
    k f21079f = new k();

    private void c(String str, Object obj) {
        this.f21080g.put(str, obj);
    }

    @Override // com.tencent.bugly.proguard.c
    public final /* bridge */ /* synthetic */ void a(String str) {
        super.a(str);
    }

    public void b() {
        this.f21078e = new HashMap<>();
    }

    @Override // com.tencent.bugly.proguard.c
    public <T> void a(String str, T t2) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, UnsupportedEncodingException {
        if (this.f21078e == null) {
            super.a(str, (String) t2);
            return;
        }
        if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        }
        if (t2 == null) {
            throw new IllegalArgumentException("put value can not is null");
        }
        if (t2 instanceof Set) {
            throw new IllegalArgumentException("can not support Set");
        }
        l lVar = new l();
        lVar.a(this.f21075c);
        lVar.a(t2, 0);
        this.f21078e.put(str, n.a(lVar.f21106a));
    }

    public final <T> T b(String str, T t2) throws b {
        HashMap<String, byte[]> map = this.f21078e;
        if (map != null) {
            if (!map.containsKey(str)) {
                return null;
            }
            if (this.f21080g.containsKey(str)) {
                return (T) this.f21080g.get(str);
            }
            try {
                this.f21079f.a(this.f21078e.get(str));
                this.f21079f.a(this.f21075c);
                T t3 = (T) this.f21079f.a((k) t2, 0, true);
                if (t3 != null) {
                    c(str, t3);
                }
                return t3;
            } catch (Exception e2) {
                throw new b(e2);
            }
        }
        if (!this.f21073a.containsKey(str)) {
            return null;
        }
        if (this.f21080g.containsKey(str)) {
            return (T) this.f21080g.get(str);
        }
        byte[] value = new byte[0];
        Iterator<Map.Entry<String, byte[]>> it = this.f21073a.get(str).entrySet().iterator();
        if (it.hasNext()) {
            Map.Entry<String, byte[]> next = it.next();
            next.getKey();
            value = next.getValue();
        }
        try {
            this.f21079f.a(value);
            this.f21079f.a(this.f21075c);
            T t4 = (T) this.f21079f.a((k) t2, 0, true);
            c(str, t4);
            return t4;
        } catch (Exception e3) {
            throw new b(e3);
        }
    }

    @Override // com.tencent.bugly.proguard.c
    public byte[] a() throws UnsupportedEncodingException {
        if (this.f21078e != null) {
            l lVar = new l(0);
            lVar.a(this.f21075c);
            lVar.a((Map) this.f21078e, 0);
            return n.a(lVar.f21106a);
        }
        return super.a();
    }

    @Override // com.tencent.bugly.proguard.c
    public void a(byte[] bArr) {
        try {
            super.a(bArr);
        } catch (Exception unused) {
            this.f21079f.a(bArr);
            this.f21079f.a(this.f21075c);
            HashMap map = new HashMap(1);
            map.put("", new byte[0]);
            this.f21078e = this.f21079f.a((Map) map, 0, false);
        }
    }
}
