package b.f;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.VisibleForTesting;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes2.dex */
public abstract class a {
    public static final String TAG = "a";
    public Context mContext;
    public Handler mHandler;
    public ProvisionedMeshNode mMeshNode;
    public int sequenceNumber;

    @VisibleForTesting(otherwise = 4)
    public final void createAccessMessage(b.d.a aVar) {
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
        a.a.a.a.b.m.a.a(TAG, "Created Access PDU " + MeshParserUtils.bytesToHex(bArrArray, false));
        aVar.g(byteBufferAllocate.array());
    }

    @VisibleForTesting(otherwise = 4)
    public final void createCustomAccessMessage(b.d.a aVar) {
        ByteBuffer byteBufferAllocate;
        int iN = aVar.n();
        int iD = aVar.d();
        byte[] bArrO = aVar.o();
        byte[] bArrCreateVendorOpCode = MeshParserUtils.createVendorOpCode(iN, iD);
        if (bArrO != null) {
            byteBufferAllocate = ByteBuffer.allocate(bArrCreateVendorOpCode.length + bArrO.length);
            byteBufferAllocate.put(bArrCreateVendorOpCode);
            byteBufferAllocate.put(bArrO);
        } else {
            byteBufferAllocate = ByteBuffer.allocate(bArrCreateVendorOpCode.length);
            byteBufferAllocate.put(bArrCreateVendorOpCode);
        }
        byte[] bArrArray = byteBufferAllocate.array();
        a.a.a.a.b.m.a.a(TAG, "Created Access PDU " + MeshParserUtils.bytesToHex(bArrArray, false));
        aVar.g(bArrArray);
    }

    public void createMeshMessage(b.d.c cVar) {
        createAccessMessage((b.d.a) cVar);
    }

    public void createVendorMeshMessage(b.d.c cVar) {
        createCustomAccessMessage((b.d.a) cVar);
    }

    public abstract void initHandler();

    public final void parseAccessLayerPDU(b.d.a aVar) {
        byte[] bArrU = aVar.u();
        int i2 = (bArrU[0] & 240) >> 6;
        if (i2 == 0) {
            i2 = 1;
        }
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Opcode length: " + i2 + " Octets");
        aVar.f(MeshParserUtils.getOpCode(bArrU, i2));
        int length = bArrU.length - i2;
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(length).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(bArrU, i2, length);
        aVar.d(byteBufferOrder.array());
        a.a.a.a.b.m.a.a(str, "Received Access PDU " + MeshParserUtils.bytesToHex(bArrU, false));
    }
}
