package b.d;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class a extends c {

    /* renamed from: u, reason: collision with root package name */
    public byte[] f7392u;

    /* renamed from: v, reason: collision with root package name */
    public byte[] f7393v;

    public a() {
        this.f7396a = 0;
    }

    @Override // b.d.c
    public void a(HashMap<Integer, byte[]> map) {
        super.a(map);
    }

    @Override // b.d.c
    public void c(HashMap<Integer, byte[]> map) {
        this.f7399d = map;
    }

    public void g(byte[] bArr) {
        this.f7392u = bArr;
    }

    public void h(byte[] bArr) {
        this.f7393v = bArr;
    }

    @Override // b.d.c
    public HashMap<Integer, byte[]> j() {
        return super.j();
    }

    @Override // b.d.c
    public Map<Integer, byte[]> m() {
        return this.f7399d;
    }

    public byte[] u() {
        return this.f7392u;
    }

    public byte[] v() {
        return this.f7393v;
    }
}
