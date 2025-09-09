package meshprovisioner.configuration;

import android.content.Context;
import b.InterfaceC0328c;
import b.d.a;
import b.d.b;
import b.d.c;
import b.q;
import meshprovisioner.configuration.MeshMessageState;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes5.dex */
public class GenericOnOffGet extends GenericMessageState {
    public static final String TAG = "GenericOnOffGet";
    public final byte[] dstAddress;
    public final int mAszmic;

    public GenericOnOffGet(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c, MeshModel meshModel, boolean z2, byte[] bArr, int i2) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.mAszmic = z2 ? 1 : 0;
        this.dstAddress = bArr;
        this.mMeshModel = meshModel;
        this.mAppKeyIndex = i2;
        createAccessMessage();
    }

    private void createAccessMessage() {
        byte[] byteArray = MeshParserUtils.toByteArray(this.mMeshModel.getBoundAppkeys().get(Integer.valueOf(this.mAppKeyIndex)));
        a aVarCreateMeshMessage = this.mMeshTransport.createMeshMessage(this.mProvisionedMeshNode, this.mSrc, this.dstAddress, byteArray, 1, SecureUtils.calculateK4(byteArray), this.mAszmic, 33281, null);
        this.message = aVarCreateMeshMessage;
        this.mPayloads.putAll(aVarCreateMeshMessage.m());
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public void executeSend() {
        q qVar;
        a.a.a.a.b.m.a.a(TAG, "Sending Generic OnOff get");
        super.executeSend();
        if (this.mPayloads.isEmpty() || (qVar = this.mMeshStatusCallbacks) == null) {
            return;
        }
        qVar.onGenericOnOffGetSent(this.mProvisionedMeshNode);
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return MeshMessageState.MessageState.GENERIC_ON_OFF_GET_STATE;
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
