package meshprovisioner.configuration;

import android.content.Context;
import b.InterfaceC0328c;
import b.d.a;
import b.d.b;
import b.d.c;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import meshprovisioner.configuration.MeshMessageState;
import meshprovisioner.models.SigModelParser;
import meshprovisioner.models.VendorModel;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.DeviceFeatureUtils;
import meshprovisioner.utils.Element;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes5.dex */
public final class ConfigCompositionDataStatus extends ConfigMessageState {
    public static final String TAG = "ConfigCompositionDataStatus";
    public int companyIdentifier;
    public int crpl;
    public int features;
    public boolean friendFeatureSupported;
    public boolean lowPowerFeatureSupported;
    public Map<Integer, Element> mElements;
    public int mUnicastAddress;
    public int productIdentifier;
    public boolean proxyFeatureSupported;
    public boolean relayFeatureSupported;
    public int versionIdentifier;

    public ConfigCompositionDataStatus(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c) {
        super(context, provisionedMeshNode, interfaceC0328c);
        this.mElements = new LinkedHashMap();
    }

    private void pareCompositionDataPages(a aVar, int i2) {
        byte[] bArrU = aVar.u();
        this.companyIdentifier = (bArrU[3] << 8) | bArrU[2];
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Company identifier: ");
        Locale locale = Locale.US;
        sb.append(String.format(locale, "%04X", Integer.valueOf(this.companyIdentifier)));
        a.a.a.a.b.m.a.a(str, sb.toString());
        this.productIdentifier = (bArrU[5] << 8) | bArrU[4];
        a.a.a.a.b.m.a.a(str, "Product identifier: " + String.format(locale, "%04X", Integer.valueOf(this.productIdentifier)));
        this.versionIdentifier = (bArrU[7] << 8) | bArrU[6];
        a.a.a.a.b.m.a.a(str, "Version identifier: " + String.format(locale, "%04X", Integer.valueOf(this.versionIdentifier)));
        this.crpl = (bArrU[9] << 8) | bArrU[8];
        a.a.a.a.b.m.a.a(str, "crpl: " + String.format(locale, "%04X", Integer.valueOf(this.crpl)));
        this.features = (bArrU[11] << 8) | bArrU[10];
        a.a.a.a.b.m.a.a(str, "Features: " + String.format(locale, "%04X", Integer.valueOf(this.features)));
        this.relayFeatureSupported = DeviceFeatureUtils.supportsRelayFeature(this.features);
        a.a.a.a.b.m.a.a(str, "Relay feature: " + this.relayFeatureSupported);
        this.proxyFeatureSupported = DeviceFeatureUtils.supportsProxyFeature(this.features);
        a.a.a.a.b.m.a.a(str, "Proxy feature: " + this.proxyFeatureSupported);
        this.friendFeatureSupported = DeviceFeatureUtils.supportsFriendFeature(this.features);
        a.a.a.a.b.m.a.a(str, "Friend feature: " + this.friendFeatureSupported);
        this.lowPowerFeatureSupported = DeviceFeatureUtils.supportsLowPowerFeature(this.features);
        a.a.a.a.b.m.a.a(str, "Low power feature: " + this.lowPowerFeatureSupported);
        parseElements(bArrU, aVar.r(), 12);
        a.a.a.a.b.m.a.a(str, "Number of elements: " + this.mElements.size());
    }

    private int parseCompanyIdentifier(short s2) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(s2).getShort(0);
    }

    private int parseCrpl(short s2) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(s2).getShort(0);
    }

    private void parseElements(byte[] bArr, byte[] bArr2, int i2) {
        ConfigCompositionDataStatus configCompositionDataStatus = this;
        int i3 = 0;
        int i4 = 0;
        byte[] bArrIncrementUnicastAddress = null;
        int i5 = i2;
        while (i5 < bArr.length) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int i6 = ((bArr[i5 + 1] & 255) << 8) | bArr[i5];
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Location identifier: ");
            Locale locale = Locale.US;
            Object[] objArr = new Object[1];
            objArr[i3] = Integer.valueOf(i6);
            sb.append(String.format(locale, "%04X", objArr));
            a.a.a.a.b.m.a.a(str, sb.toString());
            byte b2 = bArr[i5 + 2];
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Number of sig models: ");
            Object[] objArr2 = new Object[1];
            objArr2[i3] = Integer.valueOf(b2);
            sb2.append(String.format(locale, "%04X", objArr2));
            a.a.a.a.b.m.a.a(str, sb2.toString());
            byte b3 = bArr[i5 + 3];
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Number of vendor models: ");
            Object[] objArr3 = new Object[1];
            objArr3[i3] = Integer.valueOf(b3);
            sb3.append(String.format(locale, "%04X", objArr3));
            a.a.a.a.b.m.a.a(str, sb3.toString());
            i5 += 4;
            if (b2 > 0) {
                int i7 = i3;
                while (i7 < b2) {
                    int i8 = ((bArr[i5 + 1] & 255) << 8) | (bArr[i5] & 255);
                    linkedHashMap.put(Integer.valueOf(i8), SigModelParser.getSigModel(i8));
                    a.a.a.a.b.m.a.a(TAG, "Sig model ID " + i7 + " : " + String.format(Locale.US, "%04X", Integer.valueOf(i8)));
                    i5 += 2;
                    i7++;
                    b2 = b2;
                }
            }
            byte b4 = b2;
            if (b3 > 0) {
                for (int i9 = 0; i9 < b3; i9++) {
                    int i10 = ((bArr[i5 + 1] & 255) << 24) | ((bArr[i5] & 255) << 16) | ((bArr[i5 + 3] & 255) << 8) | (bArr[i5 + 2] & 255);
                    linkedHashMap.put(Integer.valueOf(i10), new VendorModel(i10));
                    a.a.a.a.b.m.a.a(TAG, "Vendor - model ID " + i9 + " : " + String.format(Locale.US, "%08X", Integer.valueOf(i10)));
                    i5 += 4;
                }
            }
            bArrIncrementUnicastAddress = i4 == 0 ? bArr2 : AddressUtils.incrementUnicastAddress(bArrIncrementUnicastAddress);
            i4++;
            Element element = new Element(bArrIncrementUnicastAddress, i6, b4, b3, linkedHashMap);
            int unicastAddressInt = AddressUtils.getUnicastAddressInt(bArrIncrementUnicastAddress);
            this.mElements.put(Integer.valueOf(unicastAddressInt), element);
            this.mUnicastAddress = unicastAddressInt;
            configCompositionDataStatus = this;
            i3 = 0;
        }
    }

    private int parseFeatures(short s2) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(s2).getShort(0);
    }

    private int parseProductIdentifier(short s2) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(s2).getShort(0);
    }

    private int parseVersionIdentifier(short s2) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(s2).getShort(0);
    }

    public int getCompanyIdentifier() {
        return this.companyIdentifier;
    }

    public int getCrpl() {
        return this.crpl;
    }

    public Map<Integer, Element> getElements() {
        return this.mElements;
    }

    public int getFeatures() {
        return this.features;
    }

    public int getProductIdentifier() {
        return this.productIdentifier;
    }

    @Override // meshprovisioner.configuration.ConfigMessageState, meshprovisioner.configuration.MeshMessageState
    public MeshMessageState.MessageState getState() {
        return MeshMessageState.MessageState.COMPOSITION_DATA_STATUS_STATE;
    }

    public int getUnicastAddress() {
        return this.mUnicastAddress;
    }

    public int getVersionIdentifier() {
        return this.versionIdentifier;
    }

    public boolean isFriendFeatureSupported() {
        return this.friendFeatureSupported;
    }

    public boolean isLowPowerFeatureSupported() {
        return this.lowPowerFeatureSupported;
    }

    public boolean isProxyFeatureSupported() {
        return this.proxyFeatureSupported;
    }

    public boolean isRelayFeatureSupported() {
        return this.relayFeatureSupported;
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public final boolean parseMeshPdu(byte[] bArr) {
        c pdu = this.mMeshTransport.parsePdu(this.mSrc, bArr);
        if (pdu == null) {
            a.a.a.a.b.m.a.a(TAG, "Message reassembly may not be complete yet");
            return false;
        }
        if (!(pdu instanceof a)) {
            parseControlMessage((b) pdu, this.mPayloads.size());
            return false;
        }
        a aVar = (a) pdu;
        if (aVar.n() != 2) {
            this.mMeshStatusCallbacks.onUnknownPduReceived(this.mProvisionedMeshNode);
            return false;
        }
        a.a.a.a.b.m.a.a(TAG, "Received composition data status");
        pareCompositionDataPages(aVar, 2);
        this.mProvisionedMeshNode.setCompositionData(this);
        this.mMeshStatusCallbacks.onCompositionDataStatusReceived(this.mProvisionedMeshNode);
        this.mInternalTransportCallbacks.a(this.mProvisionedMeshNode);
        return true;
    }

    @Override // b.f.f
    public final void sendSegmentAcknowledgementMessage(b bVar) {
        b bVarCreateSegmentBlockAcknowledgementMessage = this.mMeshTransport.createSegmentBlockAcknowledgementMessage(bVar);
        a.a.a.a.b.m.a.a(TAG, "Sending acknowledgement: " + MeshParserUtils.bytesToHex(bVarCreateSegmentBlockAcknowledgementMessage.m().get(0), false));
        this.mInternalTransportCallbacks.sendPdu(this.mProvisionedMeshNode, bVarCreateSegmentBlockAcknowledgementMessage.m().get(0));
        this.mMeshStatusCallbacks.onBlockAcknowledgementSent(this.mProvisionedMeshNode);
    }
}
