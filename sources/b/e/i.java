package b.e;

import android.content.Context;
import android.os.Handler;
import b.InterfaceC0329d;
import b.q;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.configuration.SequenceNumber;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes2.dex */
public class i extends j {

    /* renamed from: r, reason: collision with root package name */
    public static final String f7452r = "" + i.class.getSimpleName();

    /* renamed from: s, reason: collision with root package name */
    public static volatile i f7453s;

    /* renamed from: t, reason: collision with root package name */
    public InterfaceC0329d f7454t;

    /* renamed from: u, reason: collision with root package name */
    public q f7455u;

    public static i c() {
        if (f7453s == null) {
            synchronized (i.class) {
                try {
                    if (f7453s == null) {
                        f7453s = new i();
                    }
                } finally {
                }
            }
        }
        return f7453s;
    }

    public void a(Context context) {
        this.f7419b = context;
        d();
        a(new h(this));
    }

    public void d() {
        this.f7420c = new Handler(this.f7419b.getMainLooper());
    }

    public b.d.c h(String str, byte[] bArr) {
        return g(str, bArr);
    }

    public b.d.b i(b.d.b bVar) {
        c(bVar);
        b((b.d.c) bVar);
        return bVar;
    }

    public b.d.b b(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, byte[] bArr2, byte[] bArr3, int i2, int i3, int i4, int i5, byte[] bArr4) {
        int iA = a();
        byte[] sequenceNumberBytes = MeshParserUtils.getSequenceNumberBytes(iA);
        String str = f7452r;
        a.a.a.a.b.m.a.a(str, "Src address: " + MeshParserUtils.bytesToHex(bArr, false));
        a.a.a.a.b.m.a.a(str, "Dst address: " + MeshParserUtils.bytesToHex(bArr2, false));
        a.a.a.a.b.m.a.a(str, "Key: " + MeshParserUtils.bytesToHex(bArr3, false));
        a.a.a.a.b.m.a.a(str, "akf: " + i2);
        a.a.a.a.b.m.a.a(str, "aid: " + i3);
        a.a.a.a.b.m.a.a(str, "aszmic: " + i4);
        a.a.a.a.b.m.a.a(str, "Sequence number: " + iA);
        a.a.a.a.b.m.a.a(str, "Control message opcode: " + Integer.toHexString(i5));
        a.a.a.a.b.m.a.a(str, "Control message parameters: " + MeshParserUtils.bytesToHex(bArr4, false));
        b.d.b bVar = new b.d.b();
        bVar.f(bArr);
        bVar.a(bArr2);
        bVar.h(0);
        bVar.b(provisionedMeshNode.getIvIndex());
        bVar.e(sequenceNumberBytes);
        bVar.c(bArr3);
        bVar.b(i2);
        bVar.a(i3);
        bVar.c(i4);
        bVar.f(i5);
        bVar.d(bArr4);
        bVar.g(2);
        bVar.a(provisionedMeshNode.getK2Output());
        bVar.g(new byte[0]);
        super.a((b.d.c) bVar);
        return bVar;
    }

    public void a(InterfaceC0329d interfaceC0329d) {
        this.f7454t = interfaceC0329d;
    }

    public void a(q qVar) {
        this.f7455u = qVar;
    }

    @Override // b.e.e
    public final void a(f fVar) {
        super.a(fVar);
    }

    @Override // b.e.e
    public int a() {
        return SequenceNumber.getInstance().incrementAndStore();
    }

    @Override // b.e.e
    public int a(byte[] bArr) {
        return SequenceNumber.getInstance().incrementAndStore(bArr);
    }

    public b.d.a a(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, byte[] bArr2, int i2, int i3, int i4, int i5, byte[] bArr3) {
        String str = f7452r;
        a.a.a.a.b.m.a.c(str, "Create mesh message");
        int iA = a();
        byte[] sequenceNumberBytes = MeshParserUtils.getSequenceNumberBytes(iA);
        a.a.a.a.b.m.a.a(str, "Src address: " + MeshParserUtils.bytesToHex(bArr, false));
        a.a.a.a.b.m.a.a(str, "Dst address: " + MeshParserUtils.bytesToHex(provisionedMeshNode.getUnicastAddress(), false));
        a.a.a.a.b.m.a.a(str, "Key: " + MeshParserUtils.bytesToHex(bArr2, false));
        a.a.a.a.b.m.a.a(str, "akf: " + i2);
        a.a.a.a.b.m.a.a(str, "aid: " + i3);
        a.a.a.a.b.m.a.a(str, "aszmic: " + i4);
        a.a.a.a.b.m.a.a(str, "Sequence number: " + iA);
        a.a.a.a.b.m.a.a(str, "Access message opcode: " + i5);
        a.a.a.a.b.m.a.a(str, "Access message parameters: " + MeshParserUtils.bytesToHex(bArr3, false));
        b.d.a aVar = new b.d.a();
        aVar.f(bArr);
        aVar.a(provisionedMeshNode.getUnicastAddress());
        aVar.b(provisionedMeshNode.getIvIndex());
        aVar.e(sequenceNumberBytes);
        aVar.c(bArr2);
        aVar.b(i2);
        aVar.a(i3);
        aVar.c(i4);
        aVar.f(i5);
        aVar.d(bArr3);
        aVar.g(0);
        aVar.a(provisionedMeshNode.getK2Output());
        super.a((b.d.c) aVar);
        return aVar;
    }

    public b.d.a a(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, byte[] bArr2, byte[] bArr3, int i2, int i3, int i4, int i5, byte[] bArr4) {
        String str = f7452r;
        a.a.a.a.b.m.a.c(str, "Create mesh message");
        int iA = a();
        byte[] sequenceNumberBytes = MeshParserUtils.getSequenceNumberBytes(iA);
        a.a.a.a.b.m.a.a(str, "Src address: " + MeshParserUtils.bytesToHex(bArr, false));
        a.a.a.a.b.m.a.a(str, "Dst address: " + MeshParserUtils.bytesToHex(bArr2, false));
        a.a.a.a.b.m.a.a(str, "Key: " + MeshParserUtils.bytesToHex(bArr3, false));
        a.a.a.a.b.m.a.a(str, "akf: " + i2);
        a.a.a.a.b.m.a.a(str, "aid: " + i3);
        a.a.a.a.b.m.a.a(str, "aszmic: " + i4);
        a.a.a.a.b.m.a.a(str, "Sequence number: " + iA);
        a.a.a.a.b.m.a.a(str, "Access message opcode: " + Integer.toHexString(i5));
        a.a.a.a.b.m.a.a(str, "Access message parameters: " + MeshParserUtils.bytesToHex(bArr4, false));
        b.d.a aVar = new b.d.a();
        aVar.f(bArr);
        aVar.a(bArr2);
        aVar.b(provisionedMeshNode.getIvIndex());
        aVar.e(sequenceNumberBytes);
        aVar.c(bArr3);
        aVar.b(i2);
        aVar.a(i3);
        aVar.c(i4);
        aVar.f(i5);
        aVar.d(bArr4);
        aVar.g(0);
        aVar.a(provisionedMeshNode.getK2Output());
        super.a((b.d.c) aVar);
        return aVar;
    }

    public b.d.b a(ProvisionedMeshNode provisionedMeshNode, int i2, byte[] bArr, byte[] bArr2, byte[] bArr3, int i3, int i4, int i5, int i6, byte[] bArr4) {
        int iA = a();
        byte[] sequenceNumberBytes = MeshParserUtils.getSequenceNumberBytes(iA);
        String str = f7452r;
        a.a.a.a.b.m.a.a(str, "Src address: " + MeshParserUtils.bytesToHex(bArr, false));
        a.a.a.a.b.m.a.a(str, "Dst address: " + MeshParserUtils.bytesToHex(bArr2, false));
        a.a.a.a.b.m.a.a(str, "Key: " + MeshParserUtils.bytesToHex(bArr3, false));
        a.a.a.a.b.m.a.a(str, "akf: " + i3);
        a.a.a.a.b.m.a.a(str, "aid: " + i4);
        a.a.a.a.b.m.a.a(str, "aszmic: " + i5);
        a.a.a.a.b.m.a.a(str, "Sequence number: " + iA);
        a.a.a.a.b.m.a.a(str, "Control message opcode: " + Integer.toHexString(i6));
        a.a.a.a.b.m.a.a(str, "Control message parameters: " + MeshParserUtils.bytesToHex(bArr4, false));
        b.d.b bVar = new b.d.b();
        bVar.f(bArr);
        bVar.a(bArr2);
        bVar.h(provisionedMeshNode.getTtl());
        bVar.b(provisionedMeshNode.getIvIndex());
        bVar.e(sequenceNumberBytes);
        bVar.c(bArr3);
        bVar.b(i3);
        bVar.a(i4);
        bVar.c(i5);
        bVar.f(i6);
        bVar.d(bArr4);
        bVar.g(i2);
        bVar.a(provisionedMeshNode.getK2Output());
        bVar.g(new byte[0]);
        super.a((b.d.c) bVar);
        return bVar;
    }
}
