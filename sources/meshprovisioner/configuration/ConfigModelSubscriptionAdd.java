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

/* loaded from: classes5.dex */
public final class ConfigModelSubscriptionAdd extends ConfigMessageState {
    public static final int SIG_MODEL_APP_KEY_BIND_PARAMS_LENGTH = 6;
    public static int SUBSCRIPTION_ADD = 0;
    public static final String TAG = "ConfigModelSubscriptionAdd";
    public static final int VENDOR_MODEL_APP_KEY_BIND_PARAMS_LENGTH = 8;
    public final int aid;
    public final int akf;
    public final int mAszmic;
    public final byte[] mElementAddress;
    public final int mModelIdentifier;
    public final byte[] mSubscriptionAddress;

    public ConfigModelSubscriptionAdd(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c, int i2, byte[] bArr, byte[] bArr2, int i3) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.akf = 0;
        this.aid = 0;
        this.mAszmic = i2 == 1 ? 1 : 0;
        this.mElementAddress = bArr;
        this.mModelIdentifier = i3;
        this.mSubscriptionAddress = bArr2;
        createAccessMessage();
    }

    private void createAccessMessage() {
        byte[] bArrArray;
        int i2 = this.mModelIdentifier;
        if (i2 < -32768 || i2 > 32767) {
            ByteBuffer byteBufferOrder = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder.put(this.mElementAddress[1]);
            byteBufferOrder.put(this.mElementAddress[0]);
            byteBufferOrder.put(this.mSubscriptionAddress[1]);
            byteBufferOrder.put(this.mSubscriptionAddress[0]);
            int i3 = this.mModelIdentifier;
            byte[] bArr = {(byte) ((i3 >> 24) & 255), (byte) ((i3 >> 16) & 255), (byte) ((i3 >> 8) & 255), (byte) (i3 & 255)};
            byteBufferOrder.put(bArr[1]);
            byteBufferOrder.put(bArr[0]);
            byteBufferOrder.put(bArr[3]);
            byteBufferOrder.put(bArr[2]);
            bArrArray = byteBufferOrder.array();
        } else {
            ByteBuffer byteBufferOrder2 = ByteBuffer.allocate(6).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder2.put(this.mElementAddress[1]);
            byteBufferOrder2.put(this.mElementAddress[0]);
            byteBufferOrder2.put(this.mSubscriptionAddress[1]);
            byteBufferOrder2.put(this.mSubscriptionAddress[0]);
            byteBufferOrder2.putShort((short) this.mModelIdentifier);
            bArrArray = byteBufferOrder2.array();
        }
        a aVarCreateMeshMessage = this.mMeshTransport.createMeshMessage(this.mProvisionedMeshNode, this.mSrc, this.mProvisionedMeshNode.getDeviceKey(), 0, 0, this.mAszmic, 32795, bArrArray);
        this.message = aVarCreateMeshMessage;
        this.mPayloads.putAll(aVarCreateMeshMessage.m());
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public final void executeSend() {
        q qVar;
        a.a.a.a.b.m.a.a(TAG, "Sending config model subscription add");
        super.executeSend();
        if (this.mPayloads.isEmpty() || (qVar = this.mMeshStatusCallbacks) == null) {
            return;
        }
        qVar.onSubscriptionAddSent(this.mProvisionedMeshNode);
    }

    public byte[] getSrc() {
        return this.mSrc;
    }

    @Override // meshprovisioner.configuration.ConfigMessageState, meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return MeshMessageState.MessageState.CONFIG_MODEL_SUBSCRIPTION_ADD_STATE;
    }

    public void parseData(byte[] bArr) {
        parseMeshPdu(bArr);
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
