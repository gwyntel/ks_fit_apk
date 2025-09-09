package meshprovisioner.configuration;

import android.content.Context;
import b.InterfaceC0328c;
import b.d.a;
import b.d.b;
import b.d.c;
import meshprovisioner.configuration.MeshMessageState;
import meshprovisioner.models.VendorModel;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes5.dex */
public class VendorModelMessage extends VendorModelMessageState {
    public static final String TAG = "VendorModelMessage";
    public static final int VENDOR_MODEL_OPCODE_LENGTH = 4;
    public final byte[] dstAddress;
    public final int mAszmic;
    public final int opCode;
    public final byte[] parameters;

    public VendorModelMessage(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c, MeshModel meshModel, boolean z2, byte[] bArr, int i2, int i3, byte[] bArr2) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.mMeshModel = meshModel;
        this.mAszmic = z2 ? 1 : 0;
        this.dstAddress = bArr;
        this.mAppKeyIndex = i2;
        this.opCode = i3;
        this.parameters = bArr2;
        createAccessMessage();
    }

    private void createAccessMessage() {
        byte[] byteArray = MeshParserUtils.toByteArray(this.mMeshModel.getBoundAppkeys().get(Integer.valueOf(this.mAppKeyIndex)));
        a aVarCreateVendorMeshMessage = this.mMeshTransport.createVendorMeshMessage(this.mProvisionedMeshNode, (VendorModel) this.mMeshModel, this.mSrc, this.dstAddress, byteArray, 1, SecureUtils.calculateK4(byteArray), this.mAszmic, this.opCode, this.parameters);
        this.message = aVarCreateVendorMeshMessage;
        this.mPayloads.putAll(aVarCreateVendorMeshMessage.m());
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public void executeSend() {
        a.a.a.a.b.m.a.a(TAG, "Sending acknowledged vendor model message");
        super.executeSend();
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return null;
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
