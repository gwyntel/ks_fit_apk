package b.f;

import androidx.annotation.VisibleForTesting;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes2.dex */
public abstract class g extends e {
    public static final int MESH_BEACON_PDU = 1;
    public static final int PROXY_CONFIGURATION_PDU = 2;
    public static final String TAG = "g";
    public int key;
    public byte[] mEncryptionKey;
    public byte[] mPrivacyKey;
    public byte[] mSrc;
    public HashMap<Integer, byte[]> segmentedAccessMessagesMessages;
    public HashMap<Integer, byte[]> segmentedControlMessagesMessages;

    private byte[] createNetworkNonce(byte b2, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(13);
        byteBufferAllocate.put((byte) 0);
        byteBufferAllocate.put(b2);
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(bArr2);
        byteBufferAllocate.put(new byte[]{0, 0});
        byteBufferAllocate.put(bArr3);
        return byteBufferAllocate.array();
    }

    private byte[] createPECB(byte[] bArr, byte[] bArr2) {
        byte[] ivIndex = this.mMeshNode.getIvIndex();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length + 5 + ivIndex.length);
        byteBufferAllocate.order(ByteOrder.BIG_ENDIAN);
        byteBufferAllocate.put(new byte[]{0, 0, 0, 0, 0});
        byteBufferAllocate.put(ivIndex);
        byteBufferAllocate.put(bArr);
        byte[] bArrArray = byteBufferAllocate.array();
        a.a.a.a.b.m.a.a(TAG, "Privacy Random: " + MeshParserUtils.bytesToHex(bArrArray, false));
        return SecureUtils.encryptWithAES(bArrArray, bArr2);
    }

    private byte[] createPrivacyRandom(byte[] bArr) {
        byte[] bArr2 = new byte[7];
        System.arraycopy(bArr, 0, bArr2, 0, 7);
        return bArr2;
    }

    private byte[] createProxyNonce(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(13);
        byteBufferAllocate.put((byte) 3);
        byteBufferAllocate.put((byte) 0);
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(bArr2);
        byteBufferAllocate.put(new byte[]{0, 0});
        byteBufferAllocate.put(bArr3);
        return byteBufferAllocate.array();
    }

    private byte[] deobfuscateNetworkHeader(byte[] bArr) {
        byte[] privacyKey = this.mMeshNode.getK2Output().getPrivacyKey();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(6);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byteBufferAllocate.order(byteOrder);
        byteBufferAllocate.put(bArr, 2, 6);
        byte[] bArrArray = byteBufferAllocate.array();
        ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(7);
        byteBufferAllocate2.order(byteOrder);
        byteBufferAllocate2.put(bArr, 8, 7);
        byte[] bArrCreatePECB = createPECB(createPrivacyRandom(byteBufferAllocate2.array()), privacyKey);
        byte[] bArr2 = new byte[6];
        for (int i2 = 0; i2 < 6; i2++) {
            bArr2[i2] = (byte) (bArrArray[i2] ^ bArrCreatePECB[i2]);
        }
        return bArr2;
    }

    private byte[] encryptNetworkPduPayload(b.d.c cVar, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArrCreateNetworkNonce = createNetworkNonce((byte) ((cVar.e() << 7) | cVar.s()), bArr, cVar.r(), cVar.g());
        a.a.a.a.b.m.a.a(TAG, "Network nonce: " + MeshParserUtils.bytesToHex(bArrCreateNetworkNonce, false));
        byte[] bArrF = cVar.f();
        return SecureUtils.encryptCCM(ByteBuffer.allocate(bArrF.length + bArr2.length).order(ByteOrder.BIG_ENDIAN).put(bArrF).put(bArr2).array(), bArr3, bArrCreateNetworkNonce, SecureUtils.getNetMicLength(cVar.e()));
    }

    private byte[] encryptProxyConfigurationPduPayload(b.d.c cVar, byte[] bArr, byte[] bArr2) {
        byte[] bArrCreateProxyNonce = createProxyNonce(cVar.q(), cVar.r(), cVar.g());
        a.a.a.a.b.m.a.a(TAG, "Proxy nonce: " + MeshParserUtils.bytesToHex(bArrCreateProxyNonce, false));
        byte[] bArrF = cVar.f();
        return SecureUtils.encryptCCM(ByteBuffer.allocate(bArrF.length + bArr.length).order(ByteOrder.BIG_ENDIAN).put(bArrF).put(bArr).array(), bArr2, bArrCreateProxyNonce, SecureUtils.getNetMicLength(cVar.e()));
    }

    private byte[] obfuscateNetworkHeader(byte b2, byte[] bArr, byte[] bArr2, byte[] bArr3) {
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

    private b.d.a parseAccessMessage(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, byte[] bArr6, int i2) {
        SecureUtils.K2Output k2Output = this.mMeshNode.getK2Output();
        byte[] encryptionKey = k2Output.getEncryptionKey();
        this.mEncryptionKey = encryptionKey;
        this.mPrivacyKey = k2Output.getPrivacyKey();
        int i3 = bArr3[0] & Byte.MAX_VALUE;
        int length = bArr2.length - (bArr3.length + 2);
        byte[] bArr7 = new byte[length];
        System.arraycopy(bArr2, 8, bArr7, 0, length);
        byte[] bArrDecryptCCM = SecureUtils.decryptCCM(bArr7, encryptionKey, bArr4, i2);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(2);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byte[] bArrArray = byteBufferAllocate.order(byteOrder).put(bArrDecryptCCM, 0, 2).array();
        if (!Arrays.equals(bArr, bArrArray)) {
            a.a.a.a.b.m.a.a(TAG, "Received an access message that was not directed to us, let's drop it");
            return null;
        }
        if (!isSegmentedMessage(bArrDecryptCCM[2])) {
            b.d.a aVar = new b.d.a();
            aVar.b(this.mMeshNode.getIvIndex());
            HashMap<Integer, byte[]> map = new HashMap<>();
            map.put(0, bArr2);
            aVar.c(map);
            aVar.h(i3);
            aVar.f(bArr5);
            aVar.a(bArrArray);
            aVar.e(bArr6);
            parseUnsegmentedAccessLowerTransportPDU(aVar, ByteBuffer.allocate(bArr3.length + 2 + bArrDecryptCCM.length).order(byteOrder).put(bArr2, 0, 2).put(bArr3).put(bArrDecryptCCM).array());
            parseUpperTransportPDU(aVar);
            parseAccessLayerPDU(aVar);
            return aVar;
        }
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Received a segmented access message from: " + MeshParserUtils.bytesToHex(bArr5, false));
        if (this.mSrc == null) {
            this.mSrc = bArr5;
        }
        if (!Arrays.equals(bArr5, this.mSrc)) {
            a.a.a.a.b.m.a.a(str, "Segment received is from a different src than the one we are processing, let's drop it");
            return null;
        }
        HashMap<Integer, byte[]> map2 = this.segmentedAccessMessagesMessages;
        if (map2 == null) {
            HashMap<Integer, byte[]> map3 = new HashMap<>();
            this.segmentedAccessMessagesMessages = map3;
            map3.put(0, bArr2);
        } else {
            this.segmentedAccessMessagesMessages.put(Integer.valueOf(map2.size()), bArr2);
        }
        b.d.a segmentedAccessLowerTransportPDU = parseSegmentedAccessLowerTransportPDU(ByteBuffer.allocate(bArr3.length + 2 + bArrDecryptCCM.length).order(byteOrder).put(bArr2, 0, 2).put(bArr3).put(bArrDecryptCCM).array());
        if (segmentedAccessLowerTransportPDU != null) {
            this.mSrc = null;
            HashMap<Integer, byte[]> map4 = this.segmentedAccessMessagesMessages;
            this.segmentedAccessMessagesMessages = null;
            this.key = 0;
            segmentedAccessLowerTransportPDU.b(this.mMeshNode.getIvIndex());
            segmentedAccessLowerTransportPDU.c(map4);
            segmentedAccessLowerTransportPDU.e(0);
            segmentedAccessLowerTransportPDU.h(i3);
            segmentedAccessLowerTransportPDU.f(bArr5);
            segmentedAccessLowerTransportPDU.a(bArrArray);
            parseUpperTransportPDU(segmentedAccessLowerTransportPDU);
            parseAccessLayerPDU(segmentedAccessLowerTransportPDU);
        }
        return segmentedAccessLowerTransportPDU;
    }

    private b.d.b parseControlMessage(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, byte[] bArr6, int i2) {
        SecureUtils.K2Output k2Output = this.mMeshNode.getK2Output();
        byte[] encryptionKey = k2Output.getEncryptionKey();
        this.mEncryptionKey = encryptionKey;
        this.mPrivacyKey = k2Output.getPrivacyKey();
        int i3 = bArr3[0] & Byte.MAX_VALUE;
        int length = bArr2.length - (bArr3.length + 2);
        byte[] bArr7 = new byte[length];
        System.arraycopy(bArr2, 8, bArr7, 0, length);
        byte[] bArrDecryptCCM = SecureUtils.decryptCCM(bArr7, encryptionKey, bArr4, i2);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(2);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byte[] bArrArray = byteBufferAllocate.order(byteOrder).put(bArrDecryptCCM, 0, 2).array();
        if (!Arrays.equals(bArr, bArrArray)) {
            a.a.a.a.b.m.a.a(TAG, "Received a control message that was not directed to us, so we drop it");
            return null;
        }
        if (!isSegmentedMessage(bArrDecryptCCM[2])) {
            b.d.b bVar = new b.d.b();
            bVar.b(this.mMeshNode.getIvIndex());
            HashMap<Integer, byte[]> map = new HashMap<>();
            map.put(0, bArr2);
            bVar.c(map);
            bVar.h(i3);
            bVar.f(bArr5);
            bVar.a(bArrArray);
            bVar.e(bArr6);
            parseUnsegmentedControlLowerTransportPDU(bVar, ByteBuffer.allocate(bArr3.length + 2 + bArrDecryptCCM.length).order(byteOrder).put(bArr2, 0, 2).put(bArr3).put(bArrDecryptCCM).array());
            return bVar;
        }
        HashMap<Integer, byte[]> map2 = this.segmentedControlMessagesMessages;
        if (map2 == null) {
            HashMap<Integer, byte[]> map3 = new HashMap<>();
            this.segmentedControlMessagesMessages = map3;
            map3.put(0, bArr2);
        } else {
            this.segmentedAccessMessagesMessages.put(Integer.valueOf(map2.size()), bArr2);
        }
        b.d.b segmentedControlLowerTransportPDU = parseSegmentedControlLowerTransportPDU(ByteBuffer.allocate(bArr3.length + 2 + bArrDecryptCCM.length).order(byteOrder).put(bArr2, 0, 2).put(bArr3).put(bArrDecryptCCM).array());
        if (segmentedControlLowerTransportPDU != null) {
            HashMap<Integer, byte[]> map4 = this.segmentedControlMessagesMessages;
            this.segmentedControlMessagesMessages = null;
            this.key = 0;
            segmentedControlLowerTransportPDU.b(this.mMeshNode.getIvIndex());
            segmentedControlLowerTransportPDU.c(map4);
            segmentedControlLowerTransportPDU.e(1);
            segmentedControlLowerTransportPDU.h(i3);
            segmentedControlLowerTransportPDU.f(bArr5);
            segmentedControlLowerTransportPDU.a(bArrArray);
        }
        return segmentedControlLowerTransportPDU;
    }

    @Override // b.f.e, b.f.h, b.f.a
    public final void createMeshMessage(b.d.c cVar) {
        if (cVar instanceof b.d.a) {
            super.createMeshMessage(cVar);
        } else {
            super.createMeshMessage(cVar);
        }
        createNetworkLayerPDU(cVar);
    }

    @Override // b.f.e
    @VisibleForTesting(otherwise = 4)
    public final b.d.c createNetworkLayerPDU(b.d.c cVar) {
        byte b2;
        int i2;
        SecureUtils.K2Output k2Output = this.mMeshNode.getK2Output();
        byte nid = k2Output.getNid();
        byte[] encryptionKey = k2Output.getEncryptionKey();
        this.mEncryptionKey = encryptionKey;
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Encryption key: ");
        int i3 = 0;
        sb.append(MeshParserUtils.bytesToHex(encryptionKey, false));
        a.a.a.a.b.m.a.a(str, sb.toString());
        byte[] privacyKey = k2Output.getPrivacyKey();
        this.mPrivacyKey = privacyKey;
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
                    cVar.e(MeshParserUtils.getSequenceNumberBytes(incrementSequenceNumber()));
                    arrayList.add(cVar.q());
                    byte[] bArrEncryptProxyConfigurationPduPayload = encryptProxyConfigurationPduPayload(cVar, bArr, encryptionKey);
                    map.put(Integer.valueOf(i4), bArrEncryptProxyConfigurationPduPayload);
                    a.a.a.a.b.m.a.a(TAG, "Encrypted Network payload: " + MeshParserUtils.bytesToHex(bArrEncryptProxyConfigurationPduPayload, false));
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
                    cVar.e(MeshParserUtils.getSequenceNumberBytes(incrementSequenceNumber(cVar.q())));
                }
                arrayList.add(cVar.q());
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Sequence Number: ");
                HashMap<Integer, byte[]> map2 = mapJ;
                sb2.append(MeshParserUtils.bytesToHex((byte[]) arrayList.get(i5), false));
                a.a.a.a.b.m.a.a(str2, sb2.toString());
                byte[] bArrEncryptNetworkPduPayload = encryptNetworkPduPayload(cVar, (byte[]) arrayList.get(i5), bArr2, encryptionKey);
                map.put(Integer.valueOf(i5), bArrEncryptNetworkPduPayload);
                a.a.a.a.b.m.a.a(str2, "Encrypted Network payload: " + MeshParserUtils.bytesToHex(bArrEncryptNetworkPduPayload, false));
                i5++;
                mapJ = map2;
            }
            i2 = 0;
        }
        HashMap<Integer, byte[]> map3 = new HashMap<>();
        for (int i6 = i2; i6 < map.size(); i6++) {
            byte[] bArr3 = (byte[]) map.get(Integer.valueOf(i6));
            byte[] bArrObfuscateNetworkHeader = obfuscateNetworkHeader(b4, (byte[]) arrayList.get(i6), bArrR, createPECB(cVar.g(), createPrivacyRandom(bArr3), privacyKey));
            map3.put(Integer.valueOf(i6), ByteBuffer.allocate(bArrObfuscateNetworkHeader.length + 2 + bArr3.length).order(ByteOrder.BIG_ENDIAN).put((byte) iP).put(b2).put(bArrObfuscateNetworkHeader).put(bArr3).array());
            cVar.c(map3);
        }
        return cVar;
    }

    @VisibleForTesting(otherwise = 4)
    public final b.d.c createRetransmitNetworkLayerPDU(b.d.c cVar, int i2) {
        byte[] bArrEncryptNetworkPduPayload;
        SecureUtils.K2Output k2Output = this.mMeshNode.getK2Output();
        byte nid = k2Output.getNid();
        byte[] encryptionKey = k2Output.getEncryptionKey();
        this.mEncryptionKey = encryptionKey;
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Encryption key: " + MeshParserUtils.bytesToHex(encryptionKey, false));
        byte[] privacyKey = k2Output.getPrivacyKey();
        this.mPrivacyKey = privacyKey;
        a.a.a.a.b.m.a.a(str, "Privacy key: " + MeshParserUtils.bytesToHex(privacyKey, false));
        int iE = cVar.e();
        int iS = cVar.s();
        byte b2 = (byte) (nid | ((cVar.g()[3] & 1) << 7));
        byte b3 = (byte) (iS | (iE << 7));
        byte[] bArrR = cVar.r();
        HashMap<Integer, byte[]> mapJ = iE == 0 ? cVar.j() : cVar.k();
        int iP = cVar.p();
        if (cVar.p() != 0) {
            bArrEncryptNetworkPduPayload = null;
        } else {
            byte[] bArr = mapJ.get(Integer.valueOf(i2));
            byte[] sequenceNumberBytes = MeshParserUtils.getSequenceNumberBytes(incrementSequenceNumber(cVar.q()));
            cVar.e(sequenceNumberBytes);
            a.a.a.a.b.m.a.a(str, "Sequence Number: " + MeshParserUtils.bytesToHex(sequenceNumberBytes, false));
            bArrEncryptNetworkPduPayload = encryptNetworkPduPayload(cVar, sequenceNumberBytes, bArr, encryptionKey);
            a.a.a.a.b.m.a.a(str, "Encrypted Network payload: " + MeshParserUtils.bytesToHex(bArrEncryptNetworkPduPayload, false));
        }
        HashMap<Integer, byte[]> map = new HashMap<>();
        byte[] bArrObfuscateNetworkHeader = obfuscateNetworkHeader(b3, cVar.q(), bArrR, createPECB(cVar.g(), createPrivacyRandom(bArrEncryptNetworkPduPayload), privacyKey));
        map.put(Integer.valueOf(i2), ByteBuffer.allocate(bArrObfuscateNetworkHeader.length + 2 + bArrEncryptNetworkPduPayload.length).order(ByteOrder.BIG_ENDIAN).put((byte) iP).put(b2).put(bArrObfuscateNetworkHeader).put(bArrEncryptNetworkPduPayload).array());
        cVar.c(map);
        return cVar;
    }

    @Override // b.f.e, b.f.h, b.f.a
    public final void createVendorMeshMessage(b.d.c cVar) {
        if (cVar instanceof b.d.a) {
            super.createVendorMeshMessage(cVar);
        } else {
            super.createVendorMeshMessage(cVar);
        }
        createNetworkLayerPDU(cVar);
    }

    public final b.d.c parseMeshMessage(byte[] bArr, byte[] bArr2) {
        SecureUtils.K2Output k2Output = this.mMeshNode.getK2Output();
        this.mEncryptionKey = k2Output.getEncryptionKey();
        this.mPrivacyKey = k2Output.getPrivacyKey();
        byte[] bArrDeobfuscateNetworkHeader = deobfuscateNetworkHeader(bArr2);
        byte b2 = bArrDeobfuscateNetworkHeader[0];
        int i2 = (b2 >> 7) & 1;
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "TTL for received message: " + (b2 & Byte.MAX_VALUE));
        int netMicLength = SecureUtils.getNetMicLength(i2);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(3);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byte[] bArrArray = byteBufferAllocate.order(byteOrder).put(bArrDeobfuscateNetworkHeader, 1, 3).array();
        byte[] bArrArray2 = ByteBuffer.allocate(2).order(byteOrder).put(bArrDeobfuscateNetworkHeader, 4, 2).array();
        byte[] bArrCreateNetworkNonce = createNetworkNonce(b2, bArrArray, bArrArray2);
        int sequenceNumber = MeshParserUtils.getSequenceNumber(bArrArray);
        a.a.a.a.b.m.a.a(str, "Sequence number of received access message: " + MeshParserUtils.getSequenceNumber(bArrArray));
        if (sequenceNumber > this.mMeshNode.getSequenceNumber()) {
            if (!MeshParserUtils.isValidSequenceNumber(Integer.valueOf(sequenceNumber))) {
                return null;
            }
            this.mMeshNode.setSequenceNumber(sequenceNumber);
        }
        return i2 == 1 ? parseControlMessage(bArr, bArr2, bArrDeobfuscateNetworkHeader, bArrCreateNetworkNonce, bArrArray2, bArrArray, netMicLength) : parseAccessMessage(bArr, bArr2, bArrDeobfuscateNetworkHeader, bArrCreateNetworkNonce, bArrArray2, bArrArray, netMicLength);
    }

    private byte[] createNetworkNonce(byte b2, byte[] bArr, byte[] bArr2) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(13);
        byteBufferAllocate.put((byte) 0);
        byteBufferAllocate.put(b2);
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(bArr2);
        byteBufferAllocate.put(new byte[]{0, 0});
        byteBufferAllocate.put(this.mMeshNode.getIvIndex());
        return byteBufferAllocate.array();
    }

    private byte[] createPECB(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr2.length + 5 + bArr.length);
        byteBufferAllocate.order(ByteOrder.BIG_ENDIAN);
        byteBufferAllocate.put(new byte[]{0, 0, 0, 0, 0});
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(bArr2);
        return SecureUtils.encryptWithAES(byteBufferAllocate.array(), bArr3);
    }

    @VisibleForTesting
    public final b.d.c parseMeshMessage(byte[] bArr) {
        SecureUtils.K2Output k2Output = this.mMeshNode.getK2Output();
        this.mEncryptionKey = k2Output.getEncryptionKey();
        this.mPrivacyKey = k2Output.getPrivacyKey();
        byte[] bArrDeobfuscateNetworkHeader = deobfuscateNetworkHeader(bArr);
        byte b2 = bArrDeobfuscateNetworkHeader[0];
        int i2 = (b2 >> 7) & 1;
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Received message, TTL: " + (b2 & Byte.MAX_VALUE) + ", CTL: " + i2);
        int netMicLength = SecureUtils.getNetMicLength(i2);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(3);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byte[] bArrArray = byteBufferAllocate.order(byteOrder).put(bArrDeobfuscateNetworkHeader, 1, 3).array();
        byte[] bArrArray2 = ByteBuffer.allocate(2).order(byteOrder).put(bArrDeobfuscateNetworkHeader, 4, 2).array();
        byte[] bArrCreateNetworkNonce = createNetworkNonce(b2, bArrArray, bArrArray2);
        if (i2 == 1) {
            return parseControlMessage(bArr, bArrDeobfuscateNetworkHeader, bArrCreateNetworkNonce, bArrArray2, bArrArray, netMicLength);
        }
        a.a.a.a.b.m.a.a(str, "Sequence number of received access message: " + MeshParserUtils.getSequenceNumber(bArrArray));
        return parseAccessMessage(bArr, bArrDeobfuscateNetworkHeader, bArrCreateNetworkNonce, bArrArray2, bArrArray, netMicLength);
    }

    @VisibleForTesting
    private b.d.b parseControlMessage(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, int i2) {
        SecureUtils.K2Output k2Output = this.mMeshNode.getK2Output();
        byte[] encryptionKey = k2Output.getEncryptionKey();
        this.mEncryptionKey = encryptionKey;
        this.mPrivacyKey = k2Output.getPrivacyKey();
        int i3 = bArr2[0] & Byte.MAX_VALUE;
        int length = bArr.length - (bArr2.length + 2);
        byte[] bArr6 = new byte[length];
        System.arraycopy(bArr, 8, bArr6, 0, length);
        byte[] bArrDecryptCCM = SecureUtils.decryptCCM(bArr6, encryptionKey, bArr3, i2);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(2);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byte[] bArrArray = byteBufferAllocate.order(byteOrder).put(bArrDecryptCCM, 0, 2).array();
        if (isSegmentedMessage(bArrDecryptCCM[2])) {
            HashMap<Integer, byte[]> map = this.segmentedControlMessagesMessages;
            if (map == null) {
                HashMap<Integer, byte[]> map2 = new HashMap<>();
                this.segmentedControlMessagesMessages = map2;
                map2.put(0, bArr);
            } else {
                this.segmentedAccessMessagesMessages.put(Integer.valueOf(map.size()), bArr);
            }
            b.d.b segmentedControlLowerTransportPDU = parseSegmentedControlLowerTransportPDU(ByteBuffer.allocate(bArr2.length + 2 + bArrDecryptCCM.length).order(byteOrder).put(bArr, 0, 2).put(bArr2).put(bArrDecryptCCM).array());
            if (segmentedControlLowerTransportPDU != null) {
                HashMap<Integer, byte[]> map3 = this.segmentedControlMessagesMessages;
                this.segmentedControlMessagesMessages = null;
                this.key = 0;
                segmentedControlLowerTransportPDU.b(this.mMeshNode.getIvIndex());
                segmentedControlLowerTransportPDU.c(map3);
                segmentedControlLowerTransportPDU.e(1);
                segmentedControlLowerTransportPDU.h(i3);
                segmentedControlLowerTransportPDU.f(bArr4);
                segmentedControlLowerTransportPDU.a(bArrArray);
            }
            return segmentedControlLowerTransportPDU;
        }
        b.d.b bVar = new b.d.b();
        bVar.b(this.mMeshNode.getIvIndex());
        HashMap<Integer, byte[]> map4 = new HashMap<>();
        map4.put(0, bArr);
        bVar.c(map4);
        bVar.h(i3);
        bVar.f(bArr4);
        bVar.a(bArrArray);
        bVar.e(bArr5);
        parseUnsegmentedControlLowerTransportPDU(bVar, ByteBuffer.allocate(bArr2.length + 2 + bArrDecryptCCM.length).order(byteOrder).put(bArr, 0, 2).put(bArr2).put(bArrDecryptCCM).array());
        return bVar;
    }

    @VisibleForTesting
    private b.d.a parseAccessMessage(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, int i2) {
        SecureUtils.K2Output k2Output = this.mMeshNode.getK2Output();
        byte[] encryptionKey = k2Output.getEncryptionKey();
        this.mEncryptionKey = encryptionKey;
        this.mPrivacyKey = k2Output.getPrivacyKey();
        int i3 = bArr2[0] & Byte.MAX_VALUE;
        int length = bArr.length - (bArr2.length + 2);
        byte[] bArr6 = new byte[length];
        System.arraycopy(bArr, 8, bArr6, 0, length);
        byte[] bArrDecryptCCM = SecureUtils.decryptCCM(bArr6, encryptionKey, bArr3, i2);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(2);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        byte[] bArrArray = byteBufferAllocate.order(byteOrder).put(bArrDecryptCCM, 0, 2).array();
        if (isSegmentedMessage(bArrDecryptCCM[2])) {
            HashMap<Integer, byte[]> map = this.segmentedAccessMessagesMessages;
            if (map == null) {
                HashMap<Integer, byte[]> map2 = new HashMap<>();
                this.segmentedAccessMessagesMessages = map2;
                map2.put(0, bArr);
            } else {
                this.segmentedAccessMessagesMessages.put(Integer.valueOf(map.size()), bArr);
            }
            b.d.a segmentedAccessLowerTransportPDU = parseSegmentedAccessLowerTransportPDU(ByteBuffer.allocate(bArr2.length + 2 + bArrDecryptCCM.length).order(byteOrder).put(bArr, 0, 2).put(bArr2).put(bArrDecryptCCM).array());
            if (segmentedAccessLowerTransportPDU != null) {
                HashMap<Integer, byte[]> map3 = this.segmentedAccessMessagesMessages;
                this.segmentedAccessMessagesMessages = null;
                this.key = 0;
                segmentedAccessLowerTransportPDU.b(this.mMeshNode.getIvIndex());
                segmentedAccessLowerTransportPDU.c(map3);
                segmentedAccessLowerTransportPDU.e(0);
                segmentedAccessLowerTransportPDU.h(i3);
                segmentedAccessLowerTransportPDU.f(bArr4);
                segmentedAccessLowerTransportPDU.a(bArrArray);
                parseUpperTransportPDU(segmentedAccessLowerTransportPDU);
                parseAccessLayerPDU(segmentedAccessLowerTransportPDU);
            }
            return segmentedAccessLowerTransportPDU;
        }
        b.d.a aVar = new b.d.a();
        aVar.b(this.mMeshNode.getIvIndex());
        HashMap<Integer, byte[]> map4 = new HashMap<>();
        map4.put(0, bArr);
        aVar.c(map4);
        aVar.h(i3);
        aVar.f(bArr4);
        aVar.a(bArrArray);
        aVar.e(bArr5);
        parseUnsegmentedAccessLowerTransportPDU(aVar, ByteBuffer.allocate(bArr2.length + 2 + bArrDecryptCCM.length).order(byteOrder).put(bArr, 0, 2).put(bArr2).put(bArrDecryptCCM).array());
        parseUpperTransportPDU(aVar);
        parseAccessLayerPDU(aVar);
        return aVar;
    }
}
