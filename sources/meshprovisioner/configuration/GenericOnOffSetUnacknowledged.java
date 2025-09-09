package meshprovisioner.configuration;

import android.content.Context;
import b.InterfaceC0328c;
import b.d.a;
import b.d.b;
import b.d.c;
import b.q;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import meshprovisioner.configuration.MeshMessageState;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes5.dex */
public class GenericOnOffSetUnacknowledged extends GenericMessageState {
    public static final int GENERIC_ON_OFF_SET_PARAMS_LENGTH = 2;
    public static final int GENERIC_ON_OFF_SET_TRANSITION_PARAMS_LENGTH = 4;
    public static final int GENERIC_ON_OFF_TRANSITION_STEP_0 = 0;
    public static final int GENERIC_ON_OFF_TRANSITION_STEP_1 = 1;
    public static final int GENERIC_ON_OFF_TRANSITION_STEP_2 = 2;
    public static final int GENERIC_ON_OFF_TRANSITION_STEP_3 = 3;
    public static final String TAG = "GenericOnOffSetUnacknowledged";
    public final byte[] dstAddress;
    public final int mAszmic;
    public final Integer mDelay;
    public final MeshModel mMeshModel;
    public final boolean mState;
    public final Integer mTransitionResolution;
    public final Integer mTransitionSteps;

    public GenericOnOffSetUnacknowledged(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c, MeshModel meshModel, boolean z2, byte[] bArr, int i2, Integer num, Integer num2, Integer num3, boolean z3) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.mAszmic = z2 ? 1 : 0;
        this.dstAddress = bArr;
        this.mMeshModel = meshModel;
        this.mAppKeyIndex = i2;
        this.mTransitionSteps = num;
        this.mTransitionResolution = num2;
        this.mDelay = num3;
        this.mState = z3;
        createAccessMessage();
    }

    private void createAccessMessage() {
        ByteBuffer byteBufferOrder;
        if (this.mTransitionSteps == null || this.mTransitionResolution == null || this.mDelay == null) {
            byteBufferOrder = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder.put(this.mState ? (byte) 1 : (byte) 0);
            byteBufferOrder.put((byte) this.mProvisionedMeshNode.getSequenceNumber());
        } else {
            byteBufferOrder = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder.put(this.mState ? (byte) 1 : (byte) 0);
            byteBufferOrder.put((byte) this.mProvisionedMeshNode.getSequenceNumber());
            byteBufferOrder.put((byte) ((this.mTransitionResolution.intValue() << 6) | this.mTransitionSteps.intValue()));
            byteBufferOrder.put((byte) this.mDelay.intValue());
        }
        byte[] bArrArray = byteBufferOrder.array();
        byte[] byteArray = MeshParserUtils.toByteArray(this.mMeshModel.getBoundAppkeys().get(Integer.valueOf(this.mAppKeyIndex)));
        a aVarCreateMeshMessage = this.mMeshTransport.createMeshMessage(this.mProvisionedMeshNode, this.mSrc, this.dstAddress, byteArray, 1, SecureUtils.calculateK4(byteArray), this.mAszmic, 33283, bArrArray);
        this.message = aVarCreateMeshMessage;
        this.mPayloads.putAll(aVarCreateMeshMessage.m());
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public void executeSend() {
        q qVar;
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Sending Generic OnOff set unacknowledged: ");
        sb.append(this.mState ? "ON" : "OFF");
        a.a.a.a.b.m.a.a(str, sb.toString());
        super.executeSend();
        if (this.mPayloads.isEmpty() || (qVar = this.mMeshStatusCallbacks) == null) {
            return;
        }
        qVar.onGenericOnOffSetUnacknowledgedSent(this.mProvisionedMeshNode);
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return MeshMessageState.MessageState.GENERIC_ON_OFF_SET_UNACKNOWLEDGED_STATE;
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public boolean parseMeshPdu(byte[] bArr) {
        c pdu = this.mMeshTransport.parsePdu(this.mSrc, bArr);
        if (pdu == null) {
            a.a.a.a.b.m.a.a(TAG, "Message reassembly may not be complete yet");
        } else {
            if (!(pdu instanceof a)) {
                parseControlMessage((b) pdu, this.mPayloads.size());
                return true;
            }
            byte[] bArrU = ((a) pdu).u();
            a.a.a.a.b.m.a.a(TAG, "Unexpected access message received: " + MeshParserUtils.bytesToHex(bArrU, false));
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
}
