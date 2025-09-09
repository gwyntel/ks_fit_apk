package meshprovisioner.configuration;

import android.content.Context;
import b.InterfaceC0328c;
import b.d.a;
import b.d.b;
import b.e.i;
import meshprovisioner.configuration.MeshMessageState;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes5.dex */
public class CommonConfigMessage extends CommonMessageState {
    public static final String TAG = "CommonConfigMessage";
    public final byte[] dstAddress;
    public final int mAszmic;
    public byte[] mDeviceKey;
    public final int opCode;
    public final byte[] parameters;
    public MeshMessageState.MessageState state;

    public CommonConfigMessage(Context context, ProvisionedMeshNode provisionedMeshNode, boolean z2, InterfaceC0328c interfaceC0328c, boolean z3, byte[] bArr, int i2, byte[] bArr2) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.mDeviceKey = provisionedMeshNode.getDeviceKey();
        this.mAszmic = z3 ? 1 : 0;
        this.dstAddress = bArr;
        this.opCode = i2;
        this.parameters = bArr2;
        MeshMessageState.MessageState[] messageStateArrValues = MeshMessageState.MessageState.values();
        int length = messageStateArrValues.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            MeshMessageState.MessageState messageState = messageStateArrValues[i3];
            if (messageState.getState() == this.opCode) {
                this.state = messageState;
                break;
            }
            i3++;
        }
        if (z2) {
            createAccessMessage();
        }
    }

    private void createAccessMessage() {
        a aVarA = i.c().a(this.mProvisionedMeshNode, this.mSrc, this.dstAddress, this.mDeviceKey, 0, SecureUtils.calculateK4(this.mDeviceKey), this.mAszmic, this.opCode, this.parameters);
        this.message = aVarA;
        this.mPayloads.putAll(aVarA.m());
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return this.state;
    }

    @Override // b.f.f
    public void sendSegmentAcknowledgementMessage(b bVar) {
        b bVarCreateSegmentBlockAcknowledgementMessage = this.mMeshTransport.createSegmentBlockAcknowledgementMessage(bVar);
        a.a.a.a.b.m.a.a(TAG, "Sending acknowledgement: " + MeshParserUtils.bytesToHex(bVarCreateSegmentBlockAcknowledgementMessage.m().get(0), false));
        this.mInternalTransportCallbacks.sendPdu(this.mProvisionedMeshNode, bVarCreateSegmentBlockAcknowledgementMessage.m().get(0));
        this.mMeshStatusCallbacks.onBlockAcknowledgementSent(this.mProvisionedMeshNode);
    }
}
