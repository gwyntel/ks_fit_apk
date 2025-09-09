package meshprovisioner.configuration;

import android.content.Context;
import b.InterfaceC0328c;
import b.d.a;
import b.d.b;
import b.d.c;
import b.q;
import meshprovisioner.configuration.MeshMessageState;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes5.dex */
public class ConfigNodeReset extends ConfigMessageState {
    public static final String TAG = "ConfigNodeReset";
    public final int mAszmic;

    public ConfigNodeReset(Context context, ProvisionedMeshNode provisionedMeshNode, boolean z2, InterfaceC0328c interfaceC0328c) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.mAszmic = z2 ? 1 : 0;
        createAccessMessage();
    }

    private void createAccessMessage() {
        a aVarCreateMeshMessage = this.mMeshTransport.createMeshMessage(this.mProvisionedMeshNode, this.mSrc, this.mProvisionedMeshNode.getDeviceKey(), 0, 0, this.mAszmic, 32841, null);
        this.message = aVarCreateMeshMessage;
        this.mPayloads.putAll(aVarCreateMeshMessage.m());
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public final void executeSend() {
        q qVar;
        a.a.a.a.b.m.a.a(TAG, "Sending config node reset");
        super.executeSend();
        if (this.mPayloads.isEmpty() || (qVar = this.mMeshStatusCallbacks) == null) {
            return;
        }
        qVar.onMeshNodeResetSent(this.mProvisionedMeshNode);
    }

    @Override // meshprovisioner.configuration.ConfigMessageState, meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return MeshMessageState.MessageState.CONFIG_NODE_RESET_STATE;
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
