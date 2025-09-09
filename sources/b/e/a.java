package b.e;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.VisibleForTesting;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes2.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f7418a = "a";

    /* renamed from: b, reason: collision with root package name */
    public Context f7419b;

    /* renamed from: c, reason: collision with root package name */
    public Handler f7420c;

    /* renamed from: d, reason: collision with root package name */
    public g f7421d;

    /* renamed from: e, reason: collision with root package name */
    public Map<String, List<byte[]>> f7422e = new LinkedHashMap();

    public void a(g gVar) {
        this.f7421d = gVar;
    }

    public final void b(b.d.a aVar) {
        byte[] bArrU = aVar.u();
        byte b2 = bArrU[0];
        int i2 = (b2 & 240) >> 6;
        if (i2 == 0) {
            i2 = 1;
        }
        int i3 = b2 != -35 ? i2 : 1;
        String str = f7418a;
        a.a.a.a.b.m.a.a(str, "Opcode length: " + i3 + " Octets");
        aVar.f(MeshParserUtils.getOpCode(bArrU, i3));
        int length = bArrU.length - i3;
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(length).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(bArrU, i3, length);
        aVar.d(byteBufferOrder.array());
        a.a.a.a.b.m.a.a(str, "Received Access PDU " + MeshParserUtils.bytesToHex(bArrU, false));
    }

    public void c(String str, byte[] bArr) {
        this.f7422e.remove(MeshParserUtils.bytesToHex(bArr, false));
    }

    public void a(b.d.c cVar) {
        a((b.d.a) cVar);
    }

    @VisibleForTesting(otherwise = 4)
    public final void a(b.d.a aVar) {
        ByteBuffer byteBufferAllocate;
        byte[] opCodes = MeshParserUtils.getOpCodes(aVar.n());
        byte[] bArrO = aVar.o();
        if (bArrO != null) {
            byteBufferAllocate = ByteBuffer.allocate(opCodes.length + bArrO.length);
            byteBufferAllocate.put(opCodes).put(bArrO);
        } else {
            byteBufferAllocate = ByteBuffer.allocate(opCodes.length);
            byteBufferAllocate.put(opCodes);
        }
        byte[] bArrArray = byteBufferAllocate.array();
        a.a.a.a.b.m.a.a(f7418a, "Created Access PDU " + MeshParserUtils.bytesToHex(bArrArray, false));
        aVar.g(byteBufferAllocate.array());
    }

    public byte[] b(String str, byte[] bArr) {
        g gVar = this.f7421d;
        if (gVar != null) {
            return gVar.b(str, bArr);
        }
        return null;
    }

    public byte[] a(String str) {
        g gVar = this.f7421d;
        if (gVar != null) {
            return gVar.a(str);
        }
        return new byte[]{0};
    }

    public List<byte[]> a(String str, byte[] bArr) {
        List<byte[]> listA;
        String strBytesToHex = MeshParserUtils.bytesToHex(bArr, false);
        if (this.f7422e.containsKey(strBytesToHex)) {
            return this.f7422e.get(strBytesToHex);
        }
        g gVar = this.f7421d;
        if (gVar == null || (listA = gVar.a(str, bArr)) == null) {
            return null;
        }
        this.f7422e.put(strBytesToHex, listA);
        return listA;
    }
}
