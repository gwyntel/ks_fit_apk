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
public class ConfigCompositionDataGet extends ConfigMessageState {
    public static final String TAG = "ConfigCompositionDataGet";
    public int aid;
    public int akf;
    public int mAszmic;

    public ConfigCompositionDataGet(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c, int i2) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.akf = 0;
        this.aid = 0;
        this.mAszmic = i2 == 1 ? 1 : 0;
        createAccessMessage();
    }

    private void createAccessMessage() {
        MeshTransport meshTransport = this.mMeshTransport;
        ProvisionedMeshNode provisionedMeshNode = this.mProvisionedMeshNode;
        a aVarCreateMeshMessage = meshTransport.createMeshMessage(provisionedMeshNode, this.mSrc, provisionedMeshNode.getDeviceKey(), this.akf, this.aid, this.mAszmic, com.umeng.commonsdk.internal.a.f22249m, new byte[]{-1});
        this.message = aVarCreateMeshMessage;
        this.mPayloads.putAll(aVarCreateMeshMessage.m());
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public final void executeSend() {
        q qVar;
        a.a.a.a.b.m.a.a(TAG, "Sending composition data get");
        super.executeSend();
        if (this.mPayloads.isEmpty() || (qVar = this.mMeshStatusCallbacks) == null) {
            return;
        }
        qVar.onGetCompositionDataSent(this.mProvisionedMeshNode);
    }

    public byte[] getSrc() {
        return this.mSrc;
    }

    @Override // meshprovisioner.configuration.ConfigMessageState, meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return MeshMessageState.MessageState.COMPOSITION_DATA_GET_STATE;
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
    }
}
