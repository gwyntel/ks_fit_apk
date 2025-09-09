package b.e;

import android.util.ArrayMap;
import androidx.annotation.VisibleForTesting;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;
import meshprovisioner.utils.SparseIntArrayParcelable;

/* loaded from: classes2.dex */
public abstract class e extends k {

    /* renamed from: h, reason: collision with root package name */
    public static final String f7438h = "e";

    /* renamed from: i, reason: collision with root package name */
    public ArrayMap<String, HashMap<Integer, byte[]>> f7439i = new ArrayMap<>();

    /* renamed from: j, reason: collision with root package name */
    public ArrayMap<String, HashMap<Integer, byte[]>> f7440j = new ArrayMap<>();

    /* renamed from: k, reason: collision with root package name */
    public ArrayMap<String, a> f7441k = new ArrayMap<>();

    /* renamed from: l, reason: collision with root package name */
    public SparseIntArrayParcelable f7442l = new SparseIntArrayParcelable();

    /* renamed from: m, reason: collision with root package name */
    public f f7443m;

    /* JADX INFO: Access modifiers changed from: private */
    static final class a {

        /* renamed from: a, reason: collision with root package name */
        public String f7444a;

        /* renamed from: g, reason: collision with root package name */
        public Runnable f7450g = new d(this);

        /* renamed from: e, reason: collision with root package name */
        public boolean f7448e = false;

        /* renamed from: d, reason: collision with root package name */
        public boolean f7447d = false;

        /* renamed from: b, reason: collision with root package name */
        public boolean f7445b = false;

        /* renamed from: c, reason: collision with root package name */
        public Integer f7446c = null;

        /* renamed from: f, reason: collision with root package name */
        public long f7449f = 0;

        public a(String str) {
            this.f7444a = str;
        }
    }

    public abstract int a();

    public abstract int a(byte[] bArr);

    public final boolean a(byte b2) {
        return ((b2 >> 7) & 1) == 1;
    }

    public final void b(b.d.a aVar, byte[] bArr) {
        byte b2 = bArr[10];
        int i2 = (b2 >> 7) & 1;
        int i3 = (b2 >> 6) & 1;
        int i4 = b2 & 63;
        if (i2 == 0) {
            if (i3 == 0) {
                int length = bArr.length - 10;
                ByteBuffer byteBufferOrder = ByteBuffer.allocate(length).order(ByteOrder.BIG_ENDIAN);
                byteBufferOrder.put(bArr, 10, length);
                byte[] bArrArray = byteBufferOrder.array();
                HashMap<Integer, byte[]> map = new HashMap<>();
                map.put(0, bArrArray);
                aVar.a(false);
                aVar.c(0);
                aVar.b(i3);
                aVar.a(i4);
                aVar.a(map);
                return;
            }
            int length2 = bArr.length - 10;
            ByteBuffer byteBufferOrder2 = ByteBuffer.allocate(length2).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder2.put(bArr, 10, length2);
            byte[] bArrArray2 = byteBufferOrder2.array();
            HashMap<Integer, byte[]> map2 = new HashMap<>();
            map2.put(0, bArrArray2);
            aVar.a(false);
            aVar.c(0);
            aVar.b(i3);
            aVar.a(i4);
            aVar.a(map2);
        }
    }

    @VisibleForTesting(otherwise = 4)
    public final void c(b.d.b bVar) {
        if (bVar.v().length <= 11) {
            a.a.a.a.b.m.a.a(f7438h, "Creating unsegmented transport control");
            e(bVar);
        } else {
            a.a.a.a.b.m.a.a(f7438h, "Creating segmented transport control");
            d(bVar);
        }
    }

    public final HashMap<Integer, byte[]> d(b.d.b bVar) {
        bVar.a(false);
        byte[] bArrV = bVar.v();
        int iN = bVar.n();
        int iCalculateSeqZero = MeshParserUtils.calculateSeqZero(bVar.q());
        int length = (bArrV.length + 7) / 8;
        int i2 = length - 1;
        HashMap<Integer, byte[]> map = new HashMap<>();
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            int iMin = Math.min(bArrV.length - i3, 8);
            ByteBuffer byteBufferOrder = ByteBuffer.allocate(iMin + 4).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.put((byte) (iN | 128));
            byteBufferOrder.put((byte) ((iCalculateSeqZero >> 6) & 127));
            byteBufferOrder.put((byte) (((iCalculateSeqZero << 2) & 252) | ((i4 >> 3) & 3)));
            byteBufferOrder.put((byte) (((i4 << 5) & 224) | (i2 & 31)));
            byteBufferOrder.put(bArrV, i3, iMin);
            i3 += iMin;
            byte[] bArrArray = byteBufferOrder.array();
            a.a.a.a.b.m.a.a(f7438h, "Segmented Lower transport access PDU: " + MeshParserUtils.bytesToHex(bArrArray, false) + " " + i4 + " of " + length);
            map.put(Integer.valueOf(i4), bArrArray);
        }
        bVar.b(map);
        return map;
    }

    @Override // b.e.k
    public final void e(b.d.a aVar) {
        aVar.h(MeshParserUtils.concatenateSegmentedMessages(i(aVar)));
    }

    @VisibleForTesting(otherwise = 4)
    public final void f(b.d.a aVar) {
        HashMap<Integer, byte[]> mapG;
        if (aVar.v().length <= 15) {
            aVar.a(false);
            byte[] bArrH = h(aVar);
            mapG = new HashMap<>();
            mapG.put(0, bArrH);
        } else {
            aVar.a(true);
            mapG = g(aVar);
        }
        aVar.a(mapG);
    }

    public final void g(b.d.b bVar) {
        bVar.g(MeshParserUtils.concatenateSegmentedMessages(h(bVar)));
    }

    public final HashMap<Integer, byte[]> h(b.d.b bVar) {
        HashMap<Integer, byte[]> mapK = bVar.k();
        if (mapK.size() > 1) {
            for (int i2 = 0; i2 < mapK.size(); i2++) {
                byte[] bArr = mapK.get(Integer.valueOf(i2));
                mapK.put(Integer.valueOf(i2), a(bArr, 4, bArr.length - 4));
            }
        } else if (bVar.n() != 0) {
            byte[] bArr2 = mapK.get(0);
            mapK.put(0, a(bArr2, 1, bArr2.length - 1));
        } else {
            byte[] bArr3 = mapK.get(0);
            mapK.put(0, a(bArr3, 3, bArr3.length - 3));
        }
        return mapK;
    }

    public final HashMap<Integer, byte[]> i(b.d.a aVar) {
        HashMap<Integer, byte[]> mapJ = aVar.j();
        if (aVar.t()) {
            for (int i2 = 0; i2 < mapJ.size(); i2++) {
                byte[] bArr = mapJ.get(Integer.valueOf(i2));
                mapJ.put(Integer.valueOf(i2), a(bArr, 4, bArr.length - 4));
            }
        } else {
            byte[] bArr2 = mapJ.get(0);
            mapJ.put(0, a(bArr2, 1, bArr2.length - 1));
        }
        return mapJ;
    }

    public void a(f fVar) {
        this.f7443m = fVar;
    }

    @Override // b.e.k, b.e.a
    public void a(b.d.c cVar) {
        if (cVar instanceof b.d.a) {
            super.a(cVar);
            f((b.d.a) cVar);
        } else {
            c((b.d.b) cVar);
        }
    }

    @VisibleForTesting(otherwise = 4)
    public final byte[] e(b.d.b bVar) {
        ByteBuffer byteBufferOrder;
        bVar.a(false);
        int iN = bVar.n();
        byte[] bArrO = bVar.o();
        if (iN == 81 || iN == 80) {
            bArrO = a(bVar);
        }
        byte[] bArrV = bVar.v();
        byte b2 = (byte) iN;
        if (bArrO != null) {
            byteBufferOrder = ByteBuffer.allocate(bArrO.length + 1 + bArrV.length).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.put(b2);
            byteBufferOrder.put(bArrO);
        } else {
            byteBufferOrder = ByteBuffer.allocate(bArrV.length + 1).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.put(b2);
        }
        byteBufferOrder.put(bArrV);
        byte[] bArrArray = byteBufferOrder.array();
        a.a.a.a.b.m.a.a(f7438h, "Unsegmented Lower transport control PDU " + MeshParserUtils.bytesToHex(bArrArray, false));
        HashMap<Integer, byte[]> map = new HashMap<>();
        map.put(0, bArrArray);
        bVar.b(map);
        return bArrArray;
    }

    public final HashMap<Integer, byte[]> g(b.d.a aVar) {
        byte[] bArrV = aVar.v();
        int iB = (aVar.b() << 6) | aVar.a();
        int iC = aVar.c();
        int iCalculateSeqZero = MeshParserUtils.calculateSeqZero(aVar.q());
        int length = (bArrV.length + 11) / 12;
        int i2 = length - 1;
        HashMap<Integer, byte[]> map = new HashMap<>();
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            int iMin = Math.min(bArrV.length - i3, 12);
            ByteBuffer byteBufferOrder = ByteBuffer.allocate(iMin + 4).order(ByteOrder.BIG_ENDIAN);
            byteBufferOrder.put((byte) (iB | 128));
            byteBufferOrder.put((byte) ((iC << 7) | ((iCalculateSeqZero >> 6) & 127)));
            byteBufferOrder.put((byte) (((iCalculateSeqZero << 2) & 252) | ((i4 >> 3) & 3)));
            byteBufferOrder.put((byte) (((i4 << 5) & 224) | (i2 & 31)));
            byteBufferOrder.put(bArrV, i3, iMin);
            i3 += iMin;
            byte[] bArrArray = byteBufferOrder.array();
            a.a.a.a.b.m.a.a(f7438h, "Segmented Lower transport access PDU: " + MeshParserUtils.bytesToHex(bArrArray, false) + " " + i4 + " of " + length);
            map.put(Integer.valueOf(i4), bArrArray);
        }
        return map;
    }

    public final void c(a aVar, int i2, int i3, byte[] bArr, byte[] bArr2, int i4) {
        Integer num = aVar.f7446c;
        if (num == null) {
            return;
        }
        int iIntValue = num.intValue();
        if (b.a.a.b(num, i4)) {
            a.a.a.a.b.m.a.a(f7438h, "All segments received cancelling incomplete timer");
            this.f7420c.removeCallbacks(aVar.f7450g);
        }
        byte[] bArrB = b(i2, iIntValue);
        String str = f7438h;
        a.a.a.a.b.m.a.a(str, "Block acknowledgement payload: " + MeshParserUtils.bytesToHex(bArrB, false));
        b.d.b bVar = new b.d.b();
        bVar.f(0);
        bVar.g(bArrB);
        bVar.h(i3);
        bVar.g(0);
        bVar.f(bArr);
        bVar.a(bArr2);
        bVar.b(a(aVar.f7444a));
        bVar.a(SecureUtils.calculateK2(MeshParserUtils.toByteArray(aVar.f7444a), SecureUtils.K2_MASTER_INPUT));
        bVar.a(aVar.f7444a);
        bVar.e(MeshParserUtils.getSequenceNumberBytes(a()));
        aVar.f7448e = true;
        this.f7443m.sendSegmentAcknowledgementMessage(bVar);
        aVar.f7445b = false;
        a.a.a.a.b.m.a.a(str, "Block ack value: " + iIntValue);
        aVar.f7446c = null;
    }

    public final byte[] a(byte[] bArr, int i2, int i3) {
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(i3).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(bArr, i2, i3);
        return byteBufferOrder.array();
    }

    public final void f(b.d.b bVar) {
        g(bVar);
        byte[] bArrV = bVar.v();
        if (bVar.n() == 0) {
            bVar.a(new b.a.a(bArrV));
        }
        b(bVar);
    }

    public final void a(b.d.b bVar, byte[] bArr) {
        int i2 = bArr[10] & Byte.MAX_VALUE;
        int length = bArr.length - 10;
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(length).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(bArr, 10, length);
        byte[] bArrArray = byteBufferOrder.array();
        HashMap<Integer, byte[]> map = new HashMap<>();
        map.put(0, bArrArray);
        bVar.a(false);
        bVar.c(0);
        bVar.f(i2);
        bVar.b(map);
        f(bVar);
    }

    public final byte[] h(b.d.a aVar) {
        byte[] bArrV = aVar.v();
        byte bA = (byte) (aVar.a() | (aVar.b() << 6) | ((aVar.t() ? 1 : 0) << 7));
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(bArrV.length + 1).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(bA);
        byteBufferOrder.put(bArrV);
        byte[] bArrArray = byteBufferOrder.array();
        a.a.a.a.b.m.a.a(f7438h, "Unsegmented Lower transport access PDU " + MeshParserUtils.bytesToHex(bArrArray, false));
        return bArrArray;
    }

    public final b.d.a d(String str, byte[] bArr) {
        a aVar;
        byte[] bArr2;
        int i2;
        String str2;
        int i3;
        String str3;
        byte[] bArr3;
        byte b2 = bArr[10];
        int i4 = (b2 >> 6) & 1;
        int i5 = b2 & 63;
        byte b3 = bArr[11];
        int i6 = (b3 >> 7) & 1;
        byte b4 = bArr[12];
        int i7 = ((b3 & Byte.MAX_VALUE) << 6) | ((b4 & 252) >> 2);
        int i8 = (b4 & 3) << 3;
        byte b5 = bArr[13];
        int i9 = i8 | ((b5 & 224) >> 5);
        int i10 = b5 & Ascii.US;
        int i11 = bArr[2] & Byte.MAX_VALUE;
        byte[] srcAddress = MeshParserUtils.getSrcAddress(bArr);
        byte[] dstAddress = MeshParserUtils.getDstAddress(bArr);
        String str4 = f7438h;
        a.a.a.a.b.m.a.a(str4, "SEG O: " + i9);
        a.a.a.a.b.m.a.a(str4, "SEG N: " + i10);
        String meshNodeCacheKey = MeshParserUtils.getMeshNodeCacheKey(str, srcAddress);
        a aVar2 = this.f7441k.get(meshNodeCacheKey);
        if (aVar2 == null) {
            aVar2 = new a(str);
            this.f7441k.put(meshNodeCacheKey, aVar2);
        }
        a aVar3 = aVar2;
        int i12 = ByteBuffer.wrap(a(str)).order(ByteOrder.BIG_ENDIAN).getInt() | a(MeshParserUtils.getSequenceNumberFromPDU(bArr), i7);
        int i13 = this.f7442l.get(AddressUtils.getUnicastAddressInt(srcAddress));
        a.a.a.a.b.m.a.a(str4, "Last SeqAuth value " + Integer.valueOf(i13));
        a.a.a.a.b.m.a.a(str4, "Current SeqAuth value " + i12);
        if (i13 < i12) {
            a.a.a.a.b.m.a.a(str4, "Starting incomplete timer for src: " + MeshParserUtils.bytesToHex(srcAddress, false));
            a(aVar3);
            this.f7442l.put(AddressUtils.getUnicastAddressInt(srcAddress), i12);
            if (MeshParserUtils.isValidUnicastAddress(dstAddress)) {
                aVar = aVar3;
                str2 = meshNodeCacheKey;
                i3 = i5;
                str3 = str4;
                bArr2 = srcAddress;
                i2 = i10;
                a(aVar3, i7, i11, dstAddress, srcAddress, i10);
                bArr3 = bArr2;
            } else {
                aVar = aVar3;
                i2 = i10;
                str2 = meshNodeCacheKey;
                i3 = i5;
                str3 = str4;
                bArr3 = srcAddress;
            }
        } else {
            aVar = aVar3;
            bArr2 = srcAddress;
            i2 = i10;
            str2 = meshNodeCacheKey;
            i3 = i5;
            str3 = str4;
            if (i13 == i12 && aVar.f7447d) {
                a.a.a.a.b.m.a.a(str3, "Restarting incomplete timer for src: " + MeshParserUtils.bytesToHex(bArr2, false));
                b(aVar);
                if (!MeshParserUtils.isValidUnicastAddress(dstAddress) || aVar.f7445b) {
                    bArr3 = bArr2;
                } else {
                    a.a.a.a.b.m.a.a(str3, "Restarting block acknowledgement timer for src: " + MeshParserUtils.bytesToHex(bArr2, false));
                    bArr3 = bArr2;
                    a(aVar, i7, i11, dstAddress, bArr2, i2);
                }
            } else {
                bArr3 = bArr2;
            }
        }
        aVar.f7446c = b.a.a.a(aVar.f7446c, i9);
        int length = bArr.length - 10;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length);
        byteBufferAllocate.put(bArr, 10, length);
        HashMap<Integer, byte[]> map = this.f7439i.get(str2 + i7);
        if (map == null) {
            map = new HashMap<>();
            this.f7439i.put(str2 + i7, map);
        }
        HashMap<Integer, byte[]> map2 = map;
        map2.put(Integer.valueOf(i9), byteBufferAllocate.array());
        int i14 = i2;
        if (i14 != map2.size() - 1) {
            return null;
        }
        a.a.a.a.b.m.a.a(str3, "All segments received");
        this.f7420c.removeCallbacks(aVar.f7450g);
        a.a.a.a.b.m.a.a(str3, "Block ack sent? " + aVar.f7448e);
        if (aVar.f7449f > System.currentTimeMillis() && !aVar.f7448e && MeshParserUtils.isValidUnicastAddress(dstAddress)) {
            this.f7420c.removeCallbacksAndMessages(null);
            a.a.a.a.b.m.a.a(str3, "Cancelling Scheduled block ack and incomplete timer, sending an immediate block ack");
            c(aVar, i7, i11, dstAddress, bArr3, i14);
        }
        byte[] sequenceNumberBytes = MeshParserUtils.getSequenceNumberBytes(a(MeshParserUtils.getSequenceNumberFromPDU(bArr), i7));
        b.d.a aVar4 = new b.d.a();
        aVar4.c(i6);
        aVar4.e(sequenceNumberBytes);
        aVar4.b(i4);
        aVar4.a(i3);
        aVar4.a(true);
        HashMap<Integer, byte[]> map3 = new HashMap<>(map2);
        map2.clear();
        aVar4.a(map3);
        return aVar4;
    }

    public final b.d.b e(String str, byte[] bArr) {
        HashMap<Integer, byte[]> map;
        int i2 = bArr[10];
        int i3 = (i2 >> 6) & 1;
        int i4 = i2 & 63;
        int i5 = bArr[11];
        int i6 = (i5 >> 7) & 1;
        int i7 = bArr[12];
        int i8 = ((i5 & 127) << 6) | ((i7 & 252) >> 2);
        int i9 = (i7 & 3) << 3;
        int i10 = bArr[13];
        int i11 = i9 | ((i10 & 224) >> 5);
        int i12 = i10 & 31;
        int i13 = bArr[2] & 127;
        byte[] srcAddress = MeshParserUtils.getSrcAddress(bArr);
        byte[] dstAddress = MeshParserUtils.getDstAddress(bArr);
        String str2 = f7438h;
        a.a.a.a.b.m.a.a(str2, "SEG O: " + i11);
        a.a.a.a.b.m.a.a(str2, "SEG N: " + i12);
        String meshNodeCacheKey = MeshParserUtils.getMeshNodeCacheKey(str, srcAddress);
        a aVar = this.f7441k.get(meshNodeCacheKey);
        if (aVar == null) {
            aVar = new a(str);
            this.f7441k.put(meshNodeCacheKey, aVar);
        }
        a aVar2 = aVar;
        b(aVar2, i8, i13, srcAddress, dstAddress, i12);
        aVar2.f7446c = b.a.a.a(aVar2.f7446c, i11);
        a.a.a.a.b.m.a.a(str2, "Block acknowledgement value for " + aVar2.f7446c + " Seg O " + i11);
        int length = bArr.length - 10;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length);
        byteBufferAllocate.put(bArr, 10, length);
        HashMap<Integer, byte[]> map2 = this.f7440j.get(meshNodeCacheKey);
        if (map2 == null) {
            map2 = new HashMap<>();
            this.f7440j.put(meshNodeCacheKey, map2);
        }
        HashMap<Integer, byte[]> map3 = map2;
        map3.put(Integer.valueOf(i11), byteBufferAllocate.array());
        if (i12 != map3.size() - 1) {
            return null;
        }
        a.a.a.a.b.m.a.a(str2, "All segments received");
        this.f7420c.removeCallbacks(aVar2.f7450g);
        a.a.a.a.b.m.a.a(str2, "Block ack sent? " + aVar2.f7448e);
        if (aVar2.f7449f <= System.currentTimeMillis() || aVar2.f7448e || !MeshParserUtils.isValidUnicastAddress(dstAddress)) {
            map = map3;
        } else {
            this.f7420c.removeCallbacksAndMessages(null);
            a.a.a.a.b.m.a.a(str2, "Cancelling Scheduled block ack and incomplete timer, sending an immediate block ack");
            map = map3;
            c(aVar2, i8, i13, dstAddress, srcAddress, i12);
        }
        byte[] sequenceNumberBytes = MeshParserUtils.getSequenceNumberBytes(a(MeshParserUtils.getSequenceNumberFromPDU(bArr), i8));
        b.d.b bVar = new b.d.b();
        bVar.c(i6);
        bVar.e(sequenceNumberBytes);
        bVar.b(i3);
        bVar.a(i4);
        bVar.a(true);
        HashMap<Integer, byte[]> map4 = new HashMap<>();
        map4.putAll(map);
        map.clear();
        bVar.b(map4);
        g(bVar);
        b(bVar);
        return bVar;
    }

    public final void a(a aVar) {
        this.f7420c.postDelayed(aVar.f7450g, 10000L);
        aVar.f7447d = true;
    }

    public final void b(a aVar) {
        if (aVar == null) {
            return;
        }
        if (aVar.f7447d) {
            this.f7420c.removeCallbacks(aVar.f7450g);
        }
        a(aVar);
    }

    public final void a(a aVar, int i2, int i3, byte[] bArr, byte[] bArr2, int i4) {
        if (aVar.f7445b) {
            return;
        }
        long j2 = (i3 * 50) + 150;
        aVar.f7449f = System.currentTimeMillis() + j2;
        this.f7420c.postDelayed(new b(this, aVar, i2, i3, bArr, bArr2, i4), j2);
        aVar.f7445b = true;
        aVar.f7448e = false;
    }

    public final void b(a aVar, int i2, int i3, byte[] bArr, byte[] bArr2, int i4) {
        if (aVar.f7445b) {
            return;
        }
        aVar.f7445b = true;
        long j2 = (i3 * 50) + 150;
        aVar.f7449f = System.currentTimeMillis() + j2;
        this.f7420c.postDelayed(new c(this, aVar, i2, i3, bArr, bArr2, i4), j2);
    }

    public final byte[] b(int i2, int i3) {
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(6).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put((byte) ((i2 >> 6) & 127));
        byteBufferOrder.put((byte) ((i2 << 2) & 252));
        byteBufferOrder.putInt(i3);
        return byteBufferOrder.array();
    }
}
