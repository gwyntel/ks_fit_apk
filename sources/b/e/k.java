package b.e;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;
import org.spongycastle.crypto.InvalidCipherTextException;

/* loaded from: classes2.dex */
public abstract class k extends a {

    /* renamed from: f, reason: collision with root package name */
    public static final String f7460f = "k";

    /* renamed from: g, reason: collision with root package name */
    public Map<byte[], Integer> f7461g = new LinkedHashMap();

    public final int a(int i2, int i3) {
        int i4 = i2 & 8191;
        return i4 < i3 ? (i2 - (i4 - i3)) - 8192 : i2 - (i4 - i3);
    }

    public final void b(b.d.b bVar) {
        if (bVar.e() == 1) {
            byte[] bArrV = bVar.v();
            if (((byte) (bArrV[0] & Byte.MAX_VALUE)) != 10) {
                return;
            }
            byte b2 = bArrV[1];
            Arrays.copyOfRange(bArrV, 1, bArrV.length);
        }
    }

    public final byte[] c(b.d.a aVar) {
        byte[] bArrA;
        byte[] bArrU = aVar.u();
        int iB = aVar.b();
        int iC = aVar.c();
        byte[] bArrQ = aVar.q();
        byte[] bArrR = aVar.r();
        byte[] bArrF = aVar.f();
        byte[] bArrG = aVar.g();
        byte[] bArrI = aVar.i();
        if (iB == 0) {
            bArrA = b(iC, bArrQ, bArrR, bArrF, bArrG);
            a.a.a.a.b.m.a.a(f7460f, "Device nonce: " + MeshParserUtils.bytesToHex(bArrA, false));
        } else {
            bArrA = a(iC, bArrQ, bArrR, bArrF, bArrG);
            a.a.a.a.b.m.a.a(f7460f, "Application nonce: " + MeshParserUtils.bytesToHex(bArrA, false));
        }
        return SecureUtils.encryptCCM(bArrU, bArrI, bArrA, bArrU.length + 4 <= 15 ? SecureUtils.getTransMicLength(aVar.e()) : SecureUtils.getTransMicLength(aVar.c()));
    }

    public final void d(b.d.a aVar) {
        if (aVar.e() == 0) {
            e(aVar);
            aVar.g(a(aVar, (byte[]) null));
        }
    }

    public abstract void e(b.d.a aVar);

    @Override // b.e.a
    public void a(b.d.c cVar) {
        super.a(cVar);
        b.d.a aVar = (b.d.a) cVar;
        byte[] bArrC = c(aVar);
        a.a.a.a.b.m.a.a(f7460f, "Encrypted upper transport pdu: " + MeshParserUtils.bytesToHex(bArrC, false));
        aVar.h(bArrC);
    }

    public final byte[] b(int i2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(13);
        byteBufferAllocate.put((byte) 2);
        byteBufferAllocate.put((byte) (i2 << 7));
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(bArr2);
        byteBufferAllocate.put(bArr3);
        byteBufferAllocate.put(bArr4);
        return byteBufferAllocate.array();
    }

    public byte[] a(b.d.b bVar) {
        byte[] bArrA;
        int iN = bVar.n();
        byte[] bArrO = bVar.o();
        if (iN != 81 && iN != 80) {
            return null;
        }
        int iB = bVar.b();
        int iC = bVar.c();
        byte[] bArrQ = bVar.q();
        byte[] bArrR = bVar.r();
        byte[] bArrF = bVar.f();
        byte[] bArrG = bVar.g();
        byte[] bArrI = bVar.i();
        if (iB == 0) {
            bArrA = b(iC, bArrQ, bArrR, bArrF, bArrG);
            a.a.a.a.b.m.a.a(f7460f, "Device nonce: " + MeshParserUtils.bytesToHex(bArrA, false));
        } else {
            bArrA = a(iC, bArrQ, bArrR, bArrF, bArrG);
            a.a.a.a.b.m.a.a(f7460f, "Application nonce: " + MeshParserUtils.bytesToHex(bArrA, false));
        }
        return SecureUtils.encryptCCM(bArrO, bArrI, bArrA, 4);
    }

    public final byte[] a(b.d.a aVar, byte[] bArr) {
        byte bCalculateK4;
        byte[] bArrA;
        byte[] bArrDecryptCCM;
        List<byte[]> listA;
        if (aVar.b() == 0) {
            bArr = b(aVar.l(), aVar.r());
            if (bArr != null) {
                bArrA = b(aVar.c(), aVar.q(), aVar.r(), aVar.f(), aVar.g());
                bCalculateK4 = -1;
            } else {
                throw new IllegalArgumentException("Unable to find the device key to decrypt the message");
            }
        } else {
            if (bArr == null) {
                bArr = a(aVar.l(), aVar.r(), aVar.a());
            }
            if (bArr != null) {
                bCalculateK4 = SecureUtils.calculateK4(bArr);
                if (bCalculateK4 == aVar.a()) {
                    bArrA = a(aVar.c(), aVar.q(), aVar.r(), aVar.f(), aVar.g());
                } else {
                    throw new IllegalArgumentException("Unable to decrypt the message, invalid application key identifier");
                }
            } else {
                throw new IllegalArgumentException("Unable to find the app key to decrypt the message");
            }
        }
        int i2 = aVar.c() == 1 ? 8 : 4;
        try {
            bArrDecryptCCM = SecureUtils.decryptCCMWithThrowsException(aVar.v(), bArr, bArrA, i2);
        } catch (InvalidCipherTextException e2) {
            a.a.a.a.b.m.a.b(f7460f, e2.toString());
            if (aVar.b() != 0 && (listA = a(aVar.l(), aVar.r())) != null && listA.size() > 0) {
                for (byte[] bArr2 : listA) {
                    byte bCalculateK42 = SecureUtils.calculateK4(bArr2);
                    if (!Arrays.equals(bArr2, bArr) && bCalculateK42 == bCalculateK4) {
                        return a(aVar, bArr2);
                    }
                }
            }
            bArrDecryptCCM = SecureUtils.decryptCCM(aVar.v(), bArr, bArrA, i2);
        }
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[bArrDecryptCCM.length]);
        byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferWrap.put(bArrDecryptCCM);
        return byteBufferWrap.array();
    }

    public final byte[] a(String str, byte[] bArr, int i2) {
        int iCalculateK4;
        List<byte[]> listA = a(str, bArr);
        if (listA != null && listA.size() != 0) {
            for (byte[] bArr2 : listA) {
                if (this.f7461g.get(bArr2) != null) {
                    iCalculateK4 = this.f7461g.get(bArr2).intValue();
                } else {
                    iCalculateK4 = SecureUtils.calculateK4(bArr2);
                    this.f7461g.put(bArr2, Integer.valueOf(iCalculateK4));
                }
                if (i2 == iCalculateK4) {
                    return bArr2;
                }
            }
            c(str, bArr);
            a.a.a.a.b.m.a.b(f7460f, String.format("Unable find matched application key from local , packet from %s, received aid: %d, local appKey record size: %d", MeshParserUtils.bytesToHex(bArr, false), Integer.valueOf(i2), Integer.valueOf(listA.size())));
            return null;
        }
        a.a.a.a.b.m.a.b(f7460f, String.format("Unable find any application key from local , packet from %s, received aid: %d", MeshParserUtils.bytesToHex(bArr, false), Integer.valueOf(i2)));
        return null;
    }

    public final byte[] a(int i2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(13);
        byteBufferAllocate.put((byte) 1);
        byteBufferAllocate.put((byte) (i2 << 7));
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(bArr2);
        byteBufferAllocate.put(bArr3);
        byteBufferAllocate.put(bArr4);
        return byteBufferAllocate.array();
    }
}
