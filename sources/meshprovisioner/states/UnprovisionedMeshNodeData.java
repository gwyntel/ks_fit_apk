package meshprovisioner.states;

import c.a.d.a;
import datasource.bean.ConfigurationData;

/* loaded from: classes5.dex */
public class UnprovisionedMeshNodeData {
    public static final int COMPANY_ID = 424;
    public static final int PROTOCOL_ID_MAX = 1;
    public static final int PROTOCOL_ID_MIN = 0;
    public static final String TAG = "UnprovisionedMeshNodeData";
    public int bleVersion;
    public int companyId;
    public ConfigurationData configurationInfo;
    public String deviceMac;
    public byte[] deviceUuid;
    public byte mFeatureFlag;
    public byte mFeatureFlag1;
    public byte mFeatureFlag2;
    public byte[] mac;
    public int productId;
    public int protocolId;
    public boolean supportEncrypt;
    public boolean supportOTA;

    public UnprovisionedMeshNodeData(byte[] bArr) {
        parseFromUnprovisionedAdData(bArr);
    }

    private void parseFromUnprovisionedAdData(byte[] bArr) {
        if (bArr == null || bArr.length < 16) {
            return;
        }
        this.deviceUuid = a.a(bArr, 0, 16);
        int i2 = this.companyId + (bArr[0] & 255);
        this.companyId = i2;
        this.companyId = i2 + ((bArr[1] & 255) << 8);
        this.protocolId = bArr[2] & 15;
        if (!isValid()) {
            a.a.a.a.b.m.a.d(TAG, "Invalid unprovision node, not ali node");
            return;
        }
        byte b2 = bArr[2];
        this.supportEncrypt = ((b2 & 16) >> 4) == 1;
        this.supportOTA = ((b2 & 32) >> 5) == 1;
        this.bleVersion = (b2 & 192) >> 6;
        int i3 = this.productId + (bArr[3] & 255);
        this.productId = i3;
        int i4 = i3 + ((bArr[4] & 255) << 8);
        this.productId = i4;
        int i5 = i4 + ((bArr[5] & 255) << 16);
        this.productId = i5;
        this.productId = i5 + ((bArr[6] & 255) << 24);
        this.deviceMac = String.format("%1$02x%2$02x%3$02x%4$02x%5$02x%6$02x", Byte.valueOf(bArr[12]), Byte.valueOf(bArr[11]), Byte.valueOf(bArr[10]), Byte.valueOf(bArr[9]), Byte.valueOf(bArr[8]), Byte.valueOf(bArr[7]));
        this.mFeatureFlag = bArr[13];
        this.mFeatureFlag1 = bArr[14];
        this.mFeatureFlag2 = bArr[15];
        this.mac = new byte[6];
        for (int i6 = 0; i6 < 6; i6++) {
            this.mac[i6] = bArr[12 - i6];
        }
    }

    public int getBleVersion() {
        return this.bleVersion;
    }

    public int getCompanyId() {
        return this.companyId;
    }

    public ConfigurationData getConfigurationInfo() {
        return this.configurationInfo;
    }

    public String getDeviceMac() {
        return this.deviceMac;
    }

    public byte[] getDeviceUuid() {
        return this.deviceUuid;
    }

    public byte getFeatureFlag1() {
        return this.mFeatureFlag1;
    }

    public byte[] getMac() {
        return this.mac;
    }

    public int getProductId() {
        return this.productId;
    }

    public int getProtocolId() {
        return this.protocolId;
    }

    public boolean isComboMeshDevice() {
        return (this.mFeatureFlag1 & 8) == 8;
    }

    public boolean isFastProvisionMesh() {
        return this.mFeatureFlag1 == 7 || isFastSupportGatt();
    }

    public boolean isFastSupportGatt() {
        if (a.a.a.a.b.d.a.f1335b) {
            byte b2 = this.mFeatureFlag1;
            return (b2 & 1) == 1 && (b2 & 4) == 0;
        }
        byte b3 = this.mFeatureFlag1;
        return b3 == -125 || b3 == -109;
    }

    public boolean isQuietModel() {
        boolean z2 = (this.mFeatureFlag & 1) == 1;
        a.a.a.a.b.m.a.a(TAG, this.deviceMac + ": quite model: " + z2);
        return z2;
    }

    public boolean isSupportAutomaticallyGenerateShareAppKey() {
        if ((this.mFeatureFlag >> 1) <= 1) {
            return false;
        }
        byte b2 = this.mFeatureFlag2;
        return (b2 >> 5) == 0 && (b2 & 8) != 0;
    }

    public boolean isSupportEncrypt() {
        return this.supportEncrypt;
    }

    public boolean isSupportFastProvisioningV2() {
        return (this.mFeatureFlag >> 1) > 1 && (this.mFeatureFlag2 & 16) == 16;
    }

    public boolean isSupportLargeScaleMeshNetwork() {
        return (this.mFeatureFlag >> 1) > 1 && (this.mFeatureFlag2 & 1) == 1;
    }

    public boolean isSupportOTA() {
        return this.supportOTA;
    }

    public boolean isValid() {
        int i2;
        return this.companyId == 424 && (i2 = this.protocolId) >= 0 && i2 <= 1;
    }

    public void setConfigurationInfo(ConfigurationData configurationData) {
        this.configurationInfo = configurationData;
    }
}
