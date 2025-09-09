package meshprovisioner.configuration;

import a.a.a.a.b.G;
import android.content.Context;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import b.InterfaceC0328c;
import b.d.a;
import b.d.b;
import b.d.c;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import java.util.Arrays;
import java.util.Set;
import meshprovisioner.configuration.MeshMessageState;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes5.dex */
public class CommonMessage extends CommonMessageState {
    public static final String TAG = "CommonMessage";
    public final byte[] dstAddress;
    public String mAppKey;
    public final int mAszmic;
    public final int opCode;
    public final byte[] parameters;
    public MeshMessageState.MessageState state;

    public CommonMessage(Context context, ProvisionedMeshNode provisionedMeshNode, boolean z2, InterfaceC0328c interfaceC0328c, String str, boolean z3, byte[] bArr, int i2, int i3, byte[] bArr2) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.mAppKey = str;
        this.mAszmic = z3 ? 1 : 0;
        this.dstAddress = bArr;
        this.mAppKeyIndex = i2;
        this.opCode = i3;
        this.parameters = bArr2;
        MeshMessageState.MessageState[] messageStateArrValues = MeshMessageState.MessageState.values();
        int length = messageStateArrValues.length;
        int i4 = 0;
        while (true) {
            if (i4 >= length) {
                break;
            }
            MeshMessageState.MessageState messageState = messageStateArrValues[i4];
            if (messageState.getState() == this.opCode) {
                this.state = messageState;
                break;
            }
            i4++;
        }
        if (z2) {
            createAccessMessage();
        } else {
            createProxyConfigMessage();
        }
    }

    private void createAccessMessage() {
        byte[] byteArray = MeshParserUtils.toByteArray(this.mAppKey);
        a aVarCreateMeshMessage = this.mMeshTransport.createMeshMessage(this.mProvisionedMeshNode, this.mSrc, this.dstAddress, byteArray, 1, SecureUtils.calculateK4(byteArray), this.mAszmic, this.opCode, this.parameters);
        this.message = aVarCreateMeshMessage;
        this.mPayloads.putAll(aVarCreateMeshMessage.m());
    }

    private void createProxyConfigMessage() {
        byte[] byteArray = MeshParserUtils.toByteArray(this.mAppKey);
        b bVarCreateProxyConfigMessage = this.mMeshTransport.createProxyConfigMessage(this.mProvisionedMeshNode, this.mSrc, this.dstAddress, byteArray, 1, SecureUtils.calculateK4(byteArray), this.mAszmic, this.opCode, this.parameters);
        this.message = bVarCreateProxyConfigMessage;
        this.mPayloads.putAll(bVarCreateProxyConfigMessage.m());
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public void executeSend() {
        a.a.a.a.b.m.a.a(TAG, "Sending common message");
        super.executeSend();
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return this.state;
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public boolean parseMeshPdu(byte[] bArr) {
        int iN;
        int i2;
        int i3;
        try {
            c pdu = this.mMeshTransport.parsePdu(bArr);
            if (pdu != null) {
                byte[] bArrR = pdu.r();
                byte[] bArrF = pdu.f();
                a.a.a.a.b.m.a.d(TAG, "Received an message, src(" + Utils.bytes2HexString(bArrR) + "), dst(" + Utils.bytes2HexString(bArrF) + ")");
                ProvisionedMeshNode provisionedMeshNode = (ProvisionedMeshNode) G.a().b();
                Set<Integer> flatSubscribeGroupAddress = provisionedMeshNode.getFlatSubscribeGroupAddress();
                String str = "[";
                for (Integer num : flatSubscribeGroupAddress) {
                    str = (str + Utils.bytes2HexString(new byte[]{(byte) ((num.intValue() >> 8) & 255), (byte) (num.intValue() & 255)})) + ",";
                }
                String str2 = str + "]";
                String str3 = TAG;
                a.a.a.a.b.m.a.a(str3, "Self subscribe address: " + str2);
                a.a.a.a.b.m.a.a(str3, "Self unicast address: " + Utils.bytes2HexString(provisionedMeshNode.getUnicastAddress()));
                int i4 = ((bArrF[0] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArrF[1] & 255);
                if (!Arrays.equals(provisionedMeshNode.getUnicastAddress(), bArrF) && !flatSubscribeGroupAddress.contains(Integer.valueOf(i4))) {
                    a.a.a.a.b.m.a.d(str3, String.format("Received an access message that was not directed to us(%s), let's drop it", Utils.bytes2HexString(bArrF)));
                    return false;
                }
                if (!(pdu instanceof a)) {
                    parseControlMessage((b) pdu, this.mPayloads.size());
                    return true;
                }
                a aVar = (a) pdu;
                int i5 = (aVar.u()[0] & 240) >> 6;
                if (i5 == 0) {
                    i5 = 1;
                }
                byte[] bArrO = aVar.o();
                int iN2 = aVar.n();
                if (i5 == 1) {
                    i3 = iN2 & 255;
                } else {
                    if (i5 == 2) {
                        iN = aVar.n();
                        i2 = 65535;
                    } else {
                        iN = aVar.n();
                        i2 = ViewCompat.MEASURED_SIZE_MASK;
                    }
                    i3 = iN & i2;
                }
                this.mMeshStatusCallbacks.onCommonMessageStatusReceived(this.mProvisionedMeshNode, pdu.r(), Integer.toHexString(i3), bArrO, null);
            } else {
                a.a.a.a.b.m.a.a(TAG, "Message reassembly may not be complete yet");
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // b.f.f
    public void sendSegmentAcknowledgementMessage(b bVar) {
        b bVarCreateSegmentBlockAcknowledgementMessage = this.mMeshTransport.createSegmentBlockAcknowledgementMessage(bVar);
        a.a.a.a.b.m.a.a(TAG, "Sending acknowledgement: " + MeshParserUtils.bytesToHex(bVarCreateSegmentBlockAcknowledgementMessage.m().get(0), false));
        this.mInternalTransportCallbacks.sendPdu(this.mProvisionedMeshNode, bVarCreateSegmentBlockAcknowledgementMessage.m().get(0));
        this.mMeshStatusCallbacks.onBlockAcknowledgementSent(this.mProvisionedMeshNode);
    }
}
