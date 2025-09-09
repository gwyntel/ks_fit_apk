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
public final class GenericOnOffStatus extends GenericMessageState {
    public static final int GENERIC_ON_OFF_STATE_ON = 1;
    public static final String TAG = "GenericOnOffStatus";
    public boolean mPresentOn;
    public int mRemainingTime;
    public Boolean mTargetOn;

    public GenericOnOffStatus(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c, MeshModel meshModel, int i2) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.mMeshModel = meshModel;
        this.mAppKeyIndex = i2;
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return MeshMessageState.MessageState.GENERIC_ON_OFF_STATUS_STATE;
    }

    public final void parseGenericOnOffStatusMessage(a aVar) {
        int i2;
        int i3;
        if (aVar == null) {
            throw new IllegalArgumentException("Access message cannot be null!");
        }
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Received generic on off status");
        ByteBuffer byteBufferOrder = ByteBuffer.wrap(aVar.o()).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.position(0);
        this.mPresentOn = byteBufferOrder.get() == 1;
        a.a.a.a.b.m.a.a(str, "Present on: " + this.mPresentOn);
        if (byteBufferOrder.limit() > 1) {
            this.mTargetOn = Boolean.valueOf(byteBufferOrder.get() == 1);
            this.mRemainingTime = byteBufferOrder.get() & 255;
            a.a.a.a.b.m.a.a(str, "Target on: " + this.mTargetOn);
            int i4 = this.mRemainingTime & 63;
            a.a.a.a.b.m.a.a(str, "Remaining time, transition number of steps: " + i4);
            int i5 = this.mRemainingTime >> 6;
            a.a.a.a.b.m.a.a(str, "Remaining time, transition number of step resolution: " + i5);
            a.a.a.a.b.m.a.a(str, "Remaining time: " + MeshParserUtils.getRemainingTime(this.mRemainingTime));
            i3 = i5;
            i2 = i4;
        } else {
            i2 = 0;
            i3 = 0;
        }
        this.mInternalTransportCallbacks.a(this.mProvisionedMeshNode);
        this.mMeshStatusCallbacks.onGenericOnOffStatusReceived(this.mProvisionedMeshNode, this.mPresentOn, this.mTargetOn, i2, i3);
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public final boolean parseMeshPdu(byte[] bArr) {
        c pdu = this.mMeshTransport.parsePdu(this.mSrc, bArr);
        if (pdu == null) {
            a.a.a.a.b.m.a.a(TAG, "Message reassembly may not be complete yet");
        } else if (pdu instanceof a) {
            a aVar = (a) pdu;
            if (((short) (((aVar.u()[0] >> 7) & 1) + 1 == 2 ? aVar.n() : aVar.n())) == -32252) {
                parseGenericOnOffStatusMessage(aVar);
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

    public GenericOnOffStatus(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c) {
        super(context, provisionedMeshNode, interfaceC0328c);
    }
}
