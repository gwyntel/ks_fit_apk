package meshprovisioner.configuration;

import a.a.a.a.b.G;
import android.content.Context;
import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import b.InterfaceC0328c;
import b.d.a;
import b.d.b;
import b.d.c;
import b.e.i;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.ailabs.tg.utils.StringUtils;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import meshprovisioner.ProxyProtocolMessageType;
import meshprovisioner.configuration.MeshMessageState;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes5.dex */
public class CommonMessageV2 extends CommonMessageState {
    public static final String TAG = "CommonMessageV2";
    public final byte[] dstAddress;
    public boolean mAkf;
    public String mAppKey;
    public final int mAszmic;
    public final int opCode;
    public final byte[] parameters;
    public MeshMessageState.MessageState state;

    /* renamed from: meshprovisioner.configuration.CommonMessageV2$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$meshprovisioner$ProxyProtocolMessageType;

        static {
            int[] iArr = new int[ProxyProtocolMessageType.values().length];
            $SwitchMap$meshprovisioner$ProxyProtocolMessageType = iArr;
            try {
                iArr[ProxyProtocolMessageType.NetworkPDU.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$meshprovisioner$ProxyProtocolMessageType[ProxyProtocolMessageType.ProxyConfiguration.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CommonMessageV2(Context context, ProvisionedMeshNode provisionedMeshNode, ProxyProtocolMessageType proxyProtocolMessageType, boolean z2, InterfaceC0328c interfaceC0328c, String str, boolean z3, byte[] bArr, int i2, int i3, byte[] bArr2) {
        byte b2;
        super(context, provisionedMeshNode, interfaceC0328c);
        this.mAkf = true;
        this.mAppKey = str;
        this.mAszmic = z3 ? 1 : 0;
        this.dstAddress = bArr;
        this.mAppKeyIndex = i2;
        this.opCode = i3;
        this.parameters = bArr2;
        Map<Integer, String> addedAppKeys = provisionedMeshNode.getAddedAppKeys();
        int i4 = 0;
        if (addedAppKeys != null) {
            b2 = false;
            for (String str2 : addedAppKeys.values()) {
                if (!TextUtils.isEmpty(str) && StringUtils.equalsIgnoreCase(str, str2)) {
                    b2 = true;
                }
            }
        } else {
            b2 = true;
        }
        if (b2 == false) {
            this.mAkf = false;
        }
        if (AddressUtils.isValidGroupAddress(bArr)) {
            this.mAkf = true;
        }
        MeshMessageState.MessageState[] messageStateArrValues = MeshMessageState.MessageState.values();
        int length = messageStateArrValues.length;
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
        int i5 = AnonymousClass1.$SwitchMap$meshprovisioner$ProxyProtocolMessageType[proxyProtocolMessageType.ordinal()];
        if (i5 != 1) {
            if (i5 != 2) {
                return;
            }
            createProxyConfigMessage();
        } else if (z2) {
            createAccessMessage();
        } else {
            createControlMessage();
        }
    }

    private void createAccessMessage() {
        if (TextUtils.isEmpty(this.mAppKey)) {
            return;
        }
        byte[] byteArray = MeshParserUtils.toByteArray(this.mAppKey);
        boolean z2 = this.mAkf;
        byte bCalculateK4 = SecureUtils.calculateK4(byteArray);
        a aVarA = i.c().a(this.mProvisionedMeshNode, this.mSrc, this.dstAddress, byteArray, z2 ? 1 : 0, bCalculateK4, this.mAszmic, this.opCode, this.parameters);
        this.message = aVarA;
        this.mPayloads.putAll(aVarA.m());
    }

    private void createControlMessage() {
        if (TextUtils.isEmpty(this.mAppKey)) {
            return;
        }
        byte[] byteArray = MeshParserUtils.toByteArray(this.mAppKey);
        b bVarA = i.c().a(this.mProvisionedMeshNode, 0, this.mSrc, this.dstAddress, byteArray, 1, SecureUtils.calculateK4(byteArray), this.mAszmic, this.opCode, this.parameters);
        this.message = bVarA;
        this.mPayloads.putAll(bVarA.m());
    }

    private void createProxyConfigMessage() {
        if (TextUtils.isEmpty(this.mAppKey)) {
            return;
        }
        byte[] byteArray = MeshParserUtils.toByteArray(this.mAppKey);
        b bVarB = i.c().b(this.mProvisionedMeshNode, this.mSrc, this.dstAddress, byteArray, 1, SecureUtils.calculateK4(byteArray), this.mAszmic, this.opCode, this.parameters);
        this.message = bVarB;
        this.mPayloads.putAll(bVarB.m());
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
