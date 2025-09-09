package meshprovisioner.configuration;

import a.a.a.a.b.m.a;
import android.content.Context;
import b.InterfaceC0328c;
import b.d.b;
import b.d.c;
import b.q;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import meshprovisioner.configuration.MeshMessageState;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes5.dex */
public class GenericLevelSetUnacknowledged extends GenericMessageState {
    public static final int GENERIC_LEVEL_SET_PARAMS_LENGTH = 3;
    public static final int GENERIC_LEVEL_SET_TRANSITION_PARAMS_LENGTH = 5;
    public static final String TAG = "GenericLevelSetUnacknowledged";
    public final byte[] dstAddress;
    public final int mAszmic;
    public final Integer mDelay;
    public final int mLevel;
    public final MeshModel mMeshModel;
    public final Integer mTransitionResolution;
    public final Integer mTransitionSteps;

    public GenericLevelSetUnacknowledged(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c, MeshModel meshModel, boolean z2, byte[] bArr, int i2, Integer num, Integer num2, Integer num3, int i3) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.mAszmic = z2 ? 1 : 0;
        this.dstAddress = bArr;
        this.mMeshModel = meshModel;
        this.mAppKeyIndex = i2;
        this.mTransitionSteps = num;
        this.mTransitionResolution = num2;
        this.mDelay = num3;
        this.mLevel = i3;
        createAccessMessage();
    }

    private void createAccessMessage() {
        ByteBuffer byteBufferOrder;
        String str = TAG;
        a.a(str, "State: " + this.mLevel);
        if (this.mTransitionSteps == null || this.mTransitionResolution == null || this.mDelay == null) {
            byteBufferOrder = ByteBuffer.allocate(3).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder.putShort((short) this.mLevel);
            byteBufferOrder.put((byte) this.mProvisionedMeshNode.getSequenceNumber());
        } else {
            a.a(str, "Transition steps: " + this.mTransitionSteps);
            a.a(str, "Transition step resolution: " + this.mTransitionResolution);
            byteBufferOrder = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder.putShort((short) this.mLevel);
            byteBufferOrder.put((byte) this.mProvisionedMeshNode.getSequenceNumber());
            byteBufferOrder.put((byte) ((this.mTransitionResolution.intValue() << 6) | this.mTransitionSteps.intValue()));
            byteBufferOrder.put((byte) this.mDelay.intValue());
        }
        byte[] bArrArray = byteBufferOrder.array();
        byte[] byteArray = MeshParserUtils.toByteArray(this.mMeshModel.getBoundAppkeys().get(Integer.valueOf(this.mAppKeyIndex)));
        b.d.a aVarCreateMeshMessage = this.mMeshTransport.createMeshMessage(this.mProvisionedMeshNode, this.mSrc, this.dstAddress, byteArray, 1, SecureUtils.calculateK4(byteArray), this.mAszmic, 33287, bArrArray);
        this.message = aVarCreateMeshMessage;
        this.mPayloads.putAll(aVarCreateMeshMessage.m());
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public void executeSend() {
        q qVar;
        a.a(TAG, "Sending Generic Level set unacknowledged: " + this.mLevel);
        super.executeSend();
        if (this.mPayloads.isEmpty() || (qVar = this.mMeshStatusCallbacks) == null) {
            return;
        }
        qVar.onGenericLevelSetUnacknowledgedSent(this.mProvisionedMeshNode);
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return MeshMessageState.MessageState.GENERIC_LEVEL_SET_UNACKNOWLEDGED_STATE;
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public boolean parseMeshPdu(byte[] bArr) {
        c pdu = this.mMeshTransport.parsePdu(this.mSrc, bArr);
        if (pdu == null) {
            a.a(TAG, "Message reassembly may not be complete yet");
        } else {
            if (!(pdu instanceof b.d.a)) {
                parseControlMessage((b) pdu, this.mPayloads.size());
                return true;
            }
            byte[] bArrU = ((b.d.a) pdu).u();
            a.a(TAG, "Unexpected access message received: " + MeshParserUtils.bytesToHex(bArrU, false));
        }
        return false;
    }

    @Override // b.f.f
    public void sendSegmentAcknowledgementMessage(b bVar) {
        b bVarCreateSegmentBlockAcknowledgementMessage = this.mMeshTransport.createSegmentBlockAcknowledgementMessage(bVar);
        a.a(TAG, "Sending acknowledgement: " + MeshParserUtils.bytesToHex(bVarCreateSegmentBlockAcknowledgementMessage.m().get(0), false));
        this.mInternalTransportCallbacks.sendPdu(this.mProvisionedMeshNode, bVarCreateSegmentBlockAcknowledgementMessage.m().get(0));
        this.mMeshStatusCallbacks.onBlockAcknowledgementSent(this.mProvisionedMeshNode);
    }
}
