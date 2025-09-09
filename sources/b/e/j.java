package b.e;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.ArrayMap;
import androidx.annotation.VisibleForTesting;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes2.dex */
public abstract class j extends e {

    /* renamed from: n, reason: collision with root package name */
    public static final String f7456n = "" + j.class.getSimpleName();

    /* renamed from: o, reason: collision with root package name */
    public ArrayMap<String, HashMap<Integer, byte[]>> f7457o = new ArrayMap<>();

    /* renamed from: p, reason: collision with root package name */
    public ArrayMap<String, HashMap<Integer, byte[]>> f7458p = new ArrayMap<>();

    /* renamed from: q, reason: collision with root package name */
    public ArrayMap<String, Integer> f7459q = new ArrayMap<>();

    @Override // b.e.e, b.e.k, b.e.a
    public final void a(b.d.c cVar) {
        if (cVar instanceof b.d.a) {
            super.a(cVar);
        } else {
            super.a(cVar);
        }
        b(cVar);
    }

    @VisibleForTesting(otherwise = 4)
    public final b.d.c b(b.d.c cVar) {
        byte b2;
        int i2;
        SecureUtils.K2Output k2OutputH = cVar.h();
        byte nid = k2OutputH.getNid();
        byte[] encryptionKey = k2OutputH.getEncryptionKey();
        String str = f7456n;
        StringBuilder sb = new StringBuilder();
        sb.append("Encryption key: ");
        int i3 = 0;
        sb.append(MeshParserUtils.bytesToHex(encryptionKey, false));
        a.a.a.a.b.m.a.a(str, sb.toString());
        byte[] privacyKey = k2OutputH.getPrivacyKey();
        a.a.a.a.b.m.a.a(str, "Privacy key: " + MeshParserUtils.bytesToHex(privacyKey, false));
        int iE = cVar.e();
        int iS = cVar.s();
        byte b3 = (byte) (nid | ((cVar.g()[3] & 1) << 7));
        byte b4 = (byte) (iS | (iE << 7));
        byte[] bArrR = cVar.r();
        HashMap<Integer, byte[]> mapJ = iE == 0 ? cVar.j() : cVar.k();
        HashMap map = new HashMap();
        ArrayList arrayList = new ArrayList();
        int iP = cVar.p();
        int iP2 = cVar.p();
        if (iP2 != 0) {
            if (iP2 == 2) {
                int i4 = 0;
                while (i4 < mapJ.size()) {
                    byte[] bArr = mapJ.get(Integer.valueOf(i4));
                    cVar.e(MeshParserUtils.getSequenceNumberBytes(a()));
                    arrayList.add(cVar.q());
                    byte[] bArrA = a(cVar, bArr, encryptionKey);
                    map.put(Integer.valueOf(i4), bArrA);
                    a.a.a.a.b.m.a.a(f7456n, "Encrypted Network payload: " + MeshParserUtils.bytesToHex(bArrA, false));
                    i4++;
                    b3 = b3;
                    i3 = 0;
                }
            }
            b2 = b3;
            i2 = i3;
        } else {
            b2 = b3;
            int i5 = 0;
            while (i5 < mapJ.size()) {
                byte[] bArr2 = mapJ.get(Integer.valueOf(i5));
                if (i5 != 0) {
                    cVar.e(MeshParserUtils.getSequenceNumberBytes(a(cVar.q())));
                }
                arrayList.add(cVar.q());
                String str2 = f7456n;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Sequence Number: ");
                HashMap<Integer, byte[]> map2 = mapJ;
                sb2.append(MeshParserUtils.bytesToHex((byte[]) arrayList.get(i5), false));
                a.a.a.a.b.m.a.a(str2, sb2.toString());
                byte[] bArrA2 = a(cVar, (byte[]) arrayList.get(i5), bArr2, encryptionKey);
                map.put(Integer.valueOf(i5), bArrA2);
                a.a.a.a.b.m.a.a(str2, "Encrypted Network payload: " + MeshParserUtils.bytesToHex(bArrA2, false));
                i5++;
                mapJ = map2;
            }
            i2 = 0;
        }
        HashMap<Integer, byte[]> map3 = new HashMap<>();
        for (int i6 = i2; i6 < map.size(); i6++) {
            byte[] bArr3 = (byte[]) map.get(Integer.valueOf(i6));
            byte[] bArrB = b(b4, (byte[]) arrayList.get(i6), bArrR, a(cVar.g(), b(bArr3), privacyKey));
            map3.put(Integer.valueOf(i6), ByteBuffer.allocate(bArrB.length + 2 + bArr3.length).order(ByteOrder.BIG_ENDIAN).put((byte) iP).put(b2).put(bArrB).put(bArr3).array());
            cVar.c(map3);
        }
        return cVar;
    }

    public void f(String str, byte[] bArr) {
        if (TextUtils.isEmpty(str) || bArr == null || bArr.length < 1) {
            return;
        }
        String meshNodeCacheKey = MeshParserUtils.getMeshNodeCacheKey(str, bArr);
        if (TextUtils.isEmpty(meshNodeCacheKey)) {
            return;
        }
        this.f7459q.put(meshNodeCacheKey, -1);
        a.a.a.a.b.m.a.c(f7456n, "clearReplaySeqNumber success, " + MeshParserUtils.bytesToHex(bArr, false));
    }

    @SuppressLint({"DefaultLocale"})
    public final b.d.c g(String str, byte[] bArr) {
        byte[] bArrA = a(SecureUtils.calculateK2(MeshParserUtils.toByteArray(str), SecureUtils.K2_MASTER_INPUT), bArr);
        byte b2 = bArrA[0];
        int i2 = (b2 >> 7) & 1;
        int i3 = b2 & Byte.MAX_VALUE;
        int netMicLength = SecureUtils.getNetMicLength(i2);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(3);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byte[] bArrArray = byteBufferAllocate.order(byteOrder).put(bArrA, 1, 3).array();
        byte[] bArrArray2 = ByteBuffer.allocate(2).order(byteOrder).put(bArrA, 4, 2).array();
        byte[] bArrA2 = a(b2, bArrArray, bArrArray2, a(str));
        String str2 = f7456n;
        a.a.a.a.b.m.a.a(str2, "Received message, TTL: " + i3 + ", CTL: " + i2 + ", SRC: " + MeshParserUtils.bytesToHex(bArrArray2, false));
        if (b(str, bArrArray2, MeshParserUtils.getSequenceNumber(bArrArray))) {
            return null;
        }
        if (i2 == 1) {
            return b(str, bArr, bArrA, bArrA2, bArrArray2, bArrArray, netMicLength);
        }
        a.a.a.a.b.m.a.a(str2, "Sequence number of received access message: " + MeshParserUtils.getSequenceNumber(bArrArray));
        return a(str, bArr, bArrA, bArrA2, bArrArray2, bArrArray, netMicLength);
    }

    public final byte[] a(b.d.c cVar, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArrA = a((byte) ((cVar.e() << 7) | cVar.s()), bArr, cVar.r(), cVar.g());
        a.a.a.a.b.m.a.a(f7456n, "Network nonce: " + MeshParserUtils.bytesToHex(bArrA, false));
        byte[] bArrF = cVar.f();
        return SecureUtils.encryptCCM(ByteBuffer.allocate(bArrF.length + bArr2.length).order(ByteOrder.BIG_ENDIAN).put(bArrF).put(bArr2).array(), bArr3, bArrA, SecureUtils.getNetMicLength(cVar.e()));
    }

    public final byte[] a(b.d.c cVar, byte[] bArr, byte[] bArr2) {
        byte[] bArrB = b(cVar.q(), cVar.r(), cVar.g());
        a.a.a.a.b.m.a.a(f7456n, "Proxy nonce: " + MeshParserUtils.bytesToHex(bArrB, false));
        byte[] bArrF = cVar.f();
        return SecureUtils.encryptCCM(ByteBuffer.allocate(bArrF.length + bArr.length).order(ByteOrder.BIG_ENDIAN).put(bArrF).put(bArr).array(), bArr2, bArrB, SecureUtils.getNetMicLength(cVar.e()));
    }

    public final byte[] a(SecureUtils.K2Output k2Output, byte[] bArr) {
        byte[] privacyKey = k2Output.getPrivacyKey();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(6);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byteBufferAllocate.order(byteOrder);
        byteBufferAllocate.put(bArr, 2, 6);
        byte[] bArrArray = byteBufferAllocate.array();
        ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(7);
        byteBufferAllocate2.order(byteOrder);
        byteBufferAllocate2.put(bArr, 8, 7);
        byte[] bArrA = a(new byte[]{0, 0, 0, 0}, b(byteBufferAllocate2.array()), privacyKey);
        byte[] bArr2 = new byte[6];
        for (int i2 = 0; i2 < 6; i2++) {
            bArr2[i2] = (byte) (bArrArray[i2] ^ bArrA[i2]);
        }
        return bArr2;
    }

    public final byte[] a(byte b2, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(13);
        byteBufferAllocate.put((byte) 0);
        byteBufferAllocate.put(b2);
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(bArr2);
        byteBufferAllocate.put(new byte[]{0, 0});
        byteBufferAllocate.put(bArr3);
        return byteBufferAllocate.array();
    }

    public final byte[] a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr2.length + 5 + bArr.length);
        byteBufferAllocate.order(ByteOrder.BIG_ENDIAN);
        byteBufferAllocate.put(new byte[]{0, 0, 0, 0, 0});
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(bArr2);
        return SecureUtils.encryptWithAES(byteBufferAllocate.array(), bArr3);
    }

    @VisibleForTesting
    public final b.d.a a(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, int i2) {
        byte[] encryptionKey = SecureUtils.calculateK2(MeshParserUtils.toByteArray(str), SecureUtils.K2_MASTER_INPUT).getEncryptionKey();
        int i3 = bArr2[0] & Byte.MAX_VALUE;
        int length = bArr.length - (bArr2.length + 2);
        byte[] bArr6 = new byte[length];
        System.arraycopy(bArr, 8, bArr6, 0, length);
        byte[] bArrDecryptCCM = SecureUtils.decryptCCM(bArr6, encryptionKey, bArr3, i2);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(2);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byte[] bArrArray = byteBufferAllocate.order(byteOrder).put(bArrDecryptCCM, 0, 2).array();
        if (a(bArrDecryptCCM[2])) {
            a(str, bArr4, false, bArr);
            b.d.a aVarD = d(str, ByteBuffer.allocate(bArr2.length + 2 + bArrDecryptCCM.length).order(byteOrder).put(bArr, 0, 2).put(bArr2).put(bArrDecryptCCM).array());
            if (aVarD != null) {
                HashMap<Integer, byte[]> mapA = a(str, bArr4, false);
                aVarD.b(a(str));
                aVarD.c(mapA);
                aVarD.e(0);
                aVarD.h(i3);
                aVarD.f(bArr4);
                aVarD.a(bArrArray);
                aVarD.a(str);
                d(aVarD);
                b(aVarD);
            }
            return aVarD;
        }
        b.d.a aVar = new b.d.a();
        aVar.b(a(str));
        HashMap<Integer, byte[]> map = new HashMap<>();
        map.put(0, bArr);
        aVar.c(map);
        aVar.h(i3);
        aVar.f(bArr4);
        aVar.a(bArrArray);
        aVar.e(bArr5);
        aVar.a(str);
        b(aVar, ByteBuffer.allocate(bArr2.length + 2 + bArrDecryptCCM.length).order(byteOrder).put(bArr, 0, 2).put(bArr2).put(bArrDecryptCCM).array());
        d(aVar);
        b(aVar);
        return aVar;
    }

    public final byte[] b(byte b2, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(bArr.length + 1 + bArr2.length).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(b2);
        byteBufferOrder.put(bArr);
        byteBufferOrder.put(bArr2);
        byte[] bArrArray = byteBufferOrder.array();
        ByteBuffer.allocate(6).put(bArr3, 0, 6);
        byte[] bArr4 = new byte[6];
        for (int i2 = 0; i2 < 6; i2++) {
            bArr4[i2] = (byte) (bArrArray[i2] ^ bArr3[i2]);
        }
        return bArr4;
    }

    public final byte[] b(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(13);
        byteBufferAllocate.put((byte) 3);
        byteBufferAllocate.put((byte) 0);
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(bArr2);
        byteBufferAllocate.put(new byte[]{0, 0});
        byteBufferAllocate.put(bArr3);
        return byteBufferAllocate.array();
    }

    public final byte[] b(byte[] bArr) {
        byte[] bArr2 = new byte[7];
        System.arraycopy(bArr, 0, bArr2, 0, 7);
        return bArr2;
    }

    @VisibleForTesting
    public final b.d.b b(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, int i2) {
        byte[] encryptionKey = SecureUtils.calculateK2(MeshParserUtils.toByteArray(str), SecureUtils.K2_MASTER_INPUT).getEncryptionKey();
        int i3 = bArr2[0] & Byte.MAX_VALUE;
        int length = bArr.length - (bArr2.length + 2);
        byte[] bArr6 = new byte[length];
        System.arraycopy(bArr, 8, bArr6, 0, length);
        byte[] bArrDecryptCCM = SecureUtils.decryptCCM(bArr6, encryptionKey, bArr3, i2);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(2);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byte[] bArrArray = byteBufferAllocate.order(byteOrder).put(bArrDecryptCCM, 0, 2).array();
        if (a(bArrDecryptCCM[2])) {
            a(str, bArr4, true, bArr);
            b.d.b bVarE = e(str, ByteBuffer.allocate(bArr2.length + 2 + bArrDecryptCCM.length).order(byteOrder).put(bArr, 0, 2).put(bArr2).put(bArrDecryptCCM).array());
            if (bVarE != null) {
                HashMap<Integer, byte[]> mapA = a(str, bArr4, true);
                bVarE.b(a(str));
                bVarE.c(mapA);
                bVarE.e(1);
                bVarE.h(i3);
                bVarE.f(bArr4);
                bVarE.a(bArrArray);
            }
            return bVarE;
        }
        b.d.b bVar = new b.d.b();
        bVar.b(a(str));
        HashMap<Integer, byte[]> map = new HashMap<>();
        map.put(0, bArr);
        bVar.c(map);
        bVar.h(i3);
        bVar.f(bArr4);
        bVar.a(bArrArray);
        bVar.e(bArr5);
        a(bVar, ByteBuffer.allocate(bArr2.length + 2 + bArrDecryptCCM.length).order(byteOrder).put(bArr, 0, 2).put(bArr2).put(bArrDecryptCCM).array());
        return bVar;
    }

    public final HashMap<Integer, byte[]> a(String str, byte[] bArr, boolean z2, byte[] bArr2) {
        HashMap<Integer, byte[]> map;
        String meshNodeCacheKey = MeshParserUtils.getMeshNodeCacheKey(str, bArr);
        if (z2) {
            map = this.f7458p.get(meshNodeCacheKey);
            if (map == null) {
                map = new HashMap<>();
                map.put(0, bArr2);
            } else {
                map.put(Integer.valueOf(map.size()), bArr2);
            }
            this.f7458p.put(meshNodeCacheKey, map);
        } else {
            map = this.f7457o.get(meshNodeCacheKey);
            if (map == null) {
                map = new HashMap<>();
                map.put(0, bArr2);
            } else {
                map.put(Integer.valueOf(map.size()), bArr2);
            }
            this.f7457o.put(meshNodeCacheKey, map);
        }
        return map;
    }

    public final HashMap<Integer, byte[]> a(String str, byte[] bArr, boolean z2) {
        String meshNodeCacheKey = MeshParserUtils.getMeshNodeCacheKey(str, bArr);
        if (z2) {
            HashMap<Integer, byte[]> map = this.f7458p.get(meshNodeCacheKey);
            this.f7458p.remove(meshNodeCacheKey);
            return map;
        }
        HashMap<Integer, byte[]> map2 = this.f7457o.get(meshNodeCacheKey);
        this.f7457o.remove(meshNodeCacheKey);
        return map2;
    }

    public final boolean b(String str, byte[] bArr, int i2) {
        if (a.a.a.a.b.d.a.f1335b) {
            return false;
        }
        String meshNodeCacheKey = MeshParserUtils.getMeshNodeCacheKey(str, bArr);
        Integer num = this.f7459q.get(meshNodeCacheKey);
        if (num != null) {
            if (num.intValue() > i2 && num.intValue() - i2 >= 10) {
                this.f7459q.put(meshNodeCacheKey, Integer.valueOf(i2));
                return false;
            }
            if (num.intValue() >= i2) {
                a.a.a.a.b.m.a.d(f7456n, String.format(Locale.getDefault(), "detected replay attacks, device(%s) last seq: %d, received: %d", MeshParserUtils.bytesToHex(bArr, false), num, Integer.valueOf(i2)));
                return true;
            }
        }
        this.f7459q.put(meshNodeCacheKey, Integer.valueOf(i2));
        return false;
    }
}
