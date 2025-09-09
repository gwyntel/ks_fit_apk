package meshprovisioner.configuration;

import a.a.a.a.b.m.a;
import android.content.Context;
import b.InterfaceC0328c;
import b.d.b;
import b.d.c;
import meshprovisioner.configuration.MeshMessageState;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes5.dex */
public final class VendorModelMessageStatus extends VendorModelMessageState {
    public static final String TAG = "VendorModelMessageStatus";

    public VendorModelMessageStatus(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c, MeshModel meshModel, int i2) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.mMeshModel = meshModel;
        this.mAppKeyIndex = i2;
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return null;
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public final boolean parseMeshPdu(byte[] bArr) {
        c pdu = this.mMeshTransport.parsePdu(this.mSrc, bArr);
        if (pdu == null) {
            a.a(TAG, "Message reassembly may not be complete yet");
        } else {
            if (pdu instanceof b.d.a) {
                byte[] bArrU = ((b.d.a) pdu).u();
                a.a(TAG, "Received vendor model access message status: " + MeshParserUtils.bytesToHex(bArrU, false));
                this.mMeshStatusCallbacks.onVendorModelMessageStatusReceived(this.mProvisionedMeshNode, bArrU);
                this.mInternalTransportCallbacks.a(this.mProvisionedMeshNode);
                return true;
            }
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Received vendor model control message: ");
            b bVar = (b) pdu;
            sb.append(MeshParserUtils.bytesToHex(bVar.v(), false));
            a.a(str, sb.toString());
            parseControlMessage(bVar, this.mPayloads.size());
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
