package b.d;

import java.util.HashMap;
import java.util.Map;
import meshprovisioner.control.TransportControlMessage;

/* loaded from: classes2.dex */
public class b extends c {

    /* renamed from: u, reason: collision with root package name */
    public byte[] f7394u;

    /* renamed from: v, reason: collision with root package name */
    public TransportControlMessage f7395v;

    public b() {
        this.f7396a = 1;
    }

    public void a(TransportControlMessage transportControlMessage) {
        this.f7395v = transportControlMessage;
    }

    @Override // b.d.c
    public void b(HashMap<Integer, byte[]> map) {
        super.b(map);
    }

    @Override // b.d.c
    public void c(HashMap<Integer, byte[]> map) {
        this.f7399d = map;
    }

    public void g(byte[] bArr) {
        this.f7394u = bArr;
    }

    @Override // b.d.c
    public HashMap<Integer, byte[]> k() {
        return super.k();
    }

    @Override // b.d.c
    public Map<Integer, byte[]> m() {
        return this.f7399d;
    }

    public TransportControlMessage u() {
        return this.f7395v;
    }

    public byte[] v() {
        return this.f7394u;
    }
}
