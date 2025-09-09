package meshprovisioner.configuration;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.VisibleForTesting;
import b.d.a;
import b.d.b;
import b.d.c;
import b.f.f;
import b.f.g;
import meshprovisioner.models.VendorModel;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes5.dex */
public final class MeshTransport extends g {
    public static final String TAG = "MeshTransport";

    public MeshTransport(Context context, ProvisionedMeshNode provisionedMeshNode) {
        this.mContext = context;
        this.mMeshNode = provisionedMeshNode;
        initHandler();
    }

    public a createMeshMessage(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, byte[] bArr2, int i2, int i3, int i4, int i5, byte[] bArr3) {
        int iIncrementSequenceNumber = incrementSequenceNumber();
        byte[] sequenceNumberBytes = MeshParserUtils.getSequenceNumberBytes(iIncrementSequenceNumber);
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Src address: " + MeshParserUtils.bytesToHex(bArr, false));
        a.a.a.a.b.m.a.a(str, "Dst address: " + MeshParserUtils.bytesToHex(provisionedMeshNode.getUnicastAddress(), false));
        a.a.a.a.b.m.a.a(str, "Key: " + MeshParserUtils.bytesToHex(bArr2, false));
        a.a.a.a.b.m.a.a(str, "akf: " + i2);
        a.a.a.a.b.m.a.a(str, "aid: " + i3);
        a.a.a.a.b.m.a.a(str, "aszmic: " + i4);
        a.a.a.a.b.m.a.a(str, "Sequence number: " + iIncrementSequenceNumber);
        a.a.a.a.b.m.a.a(str, "Access message opcode: " + i5);
        a.a.a.a.b.m.a.a(str, "Access message parameters: " + MeshParserUtils.bytesToHex(bArr3, false));
        a aVar = new a();
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
        aVar.h(2);
        super.createMeshMessage(aVar);
        return aVar;
    }

    public b createProxyConfigMessage(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, byte[] bArr2, byte[] bArr3, int i2, int i3, int i4, int i5, byte[] bArr4) {
        int iIncrementSequenceNumber = incrementSequenceNumber();
        byte[] sequenceNumberBytes = MeshParserUtils.getSequenceNumberBytes(iIncrementSequenceNumber);
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Src address: " + MeshParserUtils.bytesToHex(bArr, false));
        a.a.a.a.b.m.a.a(str, "Dst address: " + MeshParserUtils.bytesToHex(bArr2, false));
        a.a.a.a.b.m.a.a(str, "Key: " + MeshParserUtils.bytesToHex(bArr3, false));
        a.a.a.a.b.m.a.a(str, "akf: " + i2);
        a.a.a.a.b.m.a.a(str, "aid: " + i3);
        a.a.a.a.b.m.a.a(str, "aszmic: " + i4);
        a.a.a.a.b.m.a.a(str, "Sequence number: " + iIncrementSequenceNumber);
        a.a.a.a.b.m.a.a(str, "Control message opcode: " + Integer.toHexString(i5));
        a.a.a.a.b.m.a.a(str, "Control message parameters: " + MeshParserUtils.bytesToHex(bArr4, false));
        b bVar = new b();
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
        bVar.g(new byte[0]);
        super.createMeshMessage(bVar);
        return bVar;
    }

    public c createRetransmitMeshMessage(c cVar, int i2) {
        createRetransmitNetworkLayerPDU(cVar, i2);
        return cVar;
    }

    public b createSegmentBlockAcknowledgementMessage(b bVar) {
        createLowerTransportControlPDU(bVar);
        createNetworkLayerPDU(bVar);
        return bVar;
    }

    public a createVendorMeshMessage(ProvisionedMeshNode provisionedMeshNode, VendorModel vendorModel, byte[] bArr, byte[] bArr2, byte[] bArr3, int i2, int i3, int i4, int i5, byte[] bArr4) {
        int iIncrementSequenceNumber = incrementSequenceNumber();
        byte[] sequenceNumberBytes = MeshParserUtils.getSequenceNumberBytes(iIncrementSequenceNumber);
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Src address: " + MeshParserUtils.bytesToHex(bArr, false));
        a.a.a.a.b.m.a.a(str, "Dst address: " + MeshParserUtils.bytesToHex(bArr2, false));
        a.a.a.a.b.m.a.a(str, "Key: " + MeshParserUtils.bytesToHex(bArr3, false));
        a.a.a.a.b.m.a.a(str, "akf: " + i2);
        a.a.a.a.b.m.a.a(str, "aid: " + i3);
        a.a.a.a.b.m.a.a(str, "aszmic: " + i4);
        a.a.a.a.b.m.a.a(str, "Sequence number: " + iIncrementSequenceNumber);
        a.a.a.a.b.m.a.a(str, "Access message opcode: " + Integer.toHexString(i5));
        a.a.a.a.b.m.a.a(str, "Access message parameters: " + MeshParserUtils.bytesToHex(bArr4, false));
        a aVar = new a();
        aVar.d(vendorModel.getCompanyIdentifier());
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
        super.createVendorMeshMessage(aVar);
        return aVar;
    }

    @Override // b.f.e
    public int incrementSequenceNumber() {
        return SequenceNumber.getInstance().incrementAndStore();
    }

    @Override // b.f.a
    public void initHandler() {
        this.mHandler = new Handler(this.mContext.getMainLooper());
    }

    public c parsePdu(byte[] bArr, byte[] bArr2) {
        return parseMeshMessage(bArr, bArr2);
    }

    @Override // b.f.e
    public final void setLowerTransportLayerCallbacks(f fVar) {
        super.setLowerTransportLayerCallbacks(fVar);
    }

    @Override // b.f.e
    public int incrementSequenceNumber(byte[] bArr) {
        return SequenceNumber.getInstance().incrementAndStore(bArr);
    }

    @VisibleForTesting
    public c parsePdu(byte[] bArr) {
        return parseMeshMessage(bArr);
    }

    public a createMeshMessage(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, byte[] bArr2, byte[] bArr3, int i2, int i3, int i4, int i5, byte[] bArr4) {
        int iIncrementSequenceNumber = incrementSequenceNumber();
        byte[] sequenceNumberBytes = MeshParserUtils.getSequenceNumberBytes(iIncrementSequenceNumber);
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Src address: " + MeshParserUtils.bytesToHex(bArr, false));
        a.a.a.a.b.m.a.a(str, "Dst address: " + MeshParserUtils.bytesToHex(bArr2, false));
        a.a.a.a.b.m.a.a(str, "Key: " + MeshParserUtils.bytesToHex(bArr3, false));
        a.a.a.a.b.m.a.a(str, "akf: " + i2);
        a.a.a.a.b.m.a.a(str, "aid: " + i3);
        a.a.a.a.b.m.a.a(str, "aszmic: " + i4);
        a.a.a.a.b.m.a.a(str, "Sequence number: " + iIncrementSequenceNumber);
        a.a.a.a.b.m.a.a(str, "Access message opcode: " + Integer.toHexString(i5));
        a.a.a.a.b.m.a.a(str, "Access message parameters: " + MeshParserUtils.bytesToHex(bArr4, false));
        a aVar = new a();
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
        super.createMeshMessage(aVar);
        return aVar;
    }
}
