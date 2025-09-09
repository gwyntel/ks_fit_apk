package meshprovisioner.configuration;

import android.content.Context;
import b.InterfaceC0328c;
import b.d.a;
import b.d.b;
import b.d.c;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import meshprovisioner.configuration.MeshMessageState;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes5.dex */
public final class GenericLevelStatus extends GenericMessageState {
    public static final int GENERIC_LEVEL_STATUS_MANDATORY_LENGTH = 2;
    public static final String TAG = "GenericLevelStatus";

    public GenericLevelStatus(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c, MeshModel meshModel, int i2) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.mMeshModel = meshModel;
        this.mAppKeyIndex = i2;
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return MeshMessageState.MessageState.GENERIC_LEVEL_STATUS_STATE;
    }

    public final void parseGenericLevelStatusMessage(a aVar) {
        short s2;
        int i2;
        int i3;
        if (aVar == null) {
            throw new IllegalArgumentException("Access message cannot be null!");
        }
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Received generic level status");
        ByteBuffer byteBufferOrder = ByteBuffer.wrap(aVar.o()).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.position(0);
        short s3 = byteBufferOrder.getShort();
        a.a.a.a.b.m.a.a(str, "Present level: " + ((int) s3));
        if (byteBufferOrder.limit() > 2) {
            short s4 = byteBufferOrder.getShort();
            byte b2 = byteBufferOrder.get();
            int i4 = b2 & 255;
            a.a.a.a.b.m.a.a(str, "Target level: " + ((int) s4));
            int i5 = b2 & 63;
            a.a.a.a.b.m.a.a(str, "Remaining time, transition number of steps: " + i5);
            int i6 = i4 >> 6;
            a.a.a.a.b.m.a.a(str, "Remaining time, transition number of step resolution: " + i6);
            a.a.a.a.b.m.a.a(str, "Remaining time: " + MeshParserUtils.getRemainingTime(i4));
            i2 = i5;
            s2 = s4;
            i3 = i6;
        } else {
            s2 = 0;
            i2 = 0;
            i3 = 0;
        }
        this.mInternalTransportCallbacks.a(this.mProvisionedMeshNode);
        this.mMeshStatusCallbacks.onGenericLevelStatusReceived(this.mProvisionedMeshNode, s3, s2, i2, i3);
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public final boolean parseMeshPdu(byte[] bArr) {
        c pdu = this.mMeshTransport.parsePdu(this.mSrc, bArr);
        if (pdu == null) {
            a.a.a.a.b.m.a.a(TAG, "Message reassembly may not be complete yet");
        } else if (pdu instanceof a) {
            a aVar = (a) pdu;
            if (((short) (((aVar.u()[0] >> 7) & 1) + 1 == 2 ? aVar.n() : aVar.n())) == -32248) {
                parseGenericLevelStatusMessage(aVar);
                return true;
            }
            this.mMeshStatusCallbacks.onUnknownPduReceived(this.mProvisionedMeshNode);
        } else {
            parseControlMessage((b) pdu, this.mPayloads.size());
        }
        return false;
    }

    @Override // b.f.f
    public void sendSegmentAcknowledgementMessage(b bVar) {
        b bVarCreateSegmentBlockAcknowledgementMessage = this.mMeshTransport.createSegmentBlockAcknowledgementMessage(bVar);
        a.a.a.a.b.m.a.a(TAG, "Sending acknowledgement: " + MeshParserUtils.bytesToHex(bVarCreateSegmentBlockAcknowledgementMessage.m().get(0), false));
        this.mInternalTransportCallbacks.sendPdu(this.mProvisionedMeshNode, bVarCreateSegmentBlockAcknowledgementMessage.m().get(0));
        this.mMeshStatusCallbacks.onBlockAcknowledgementSent(this.mProvisionedMeshNode);
    }

    public GenericLevelStatus(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c) {
        super(context, provisionedMeshNode, interfaceC0328c);
    }
}
