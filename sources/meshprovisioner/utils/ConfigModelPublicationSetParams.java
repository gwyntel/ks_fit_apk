package meshprovisioner.utils;

import androidx.annotation.NonNull;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes5.dex */
public class ConfigModelPublicationSetParams {
    public final int appKeyIndex;
    public int aszmic;
    public boolean credentialFlag;
    public final byte[] elementAddress;
    public final ProvisionedMeshNode meshNode;
    public int modelIdentifier;
    public int publicationResolution;
    public int publicationSteps;
    public final byte[] publishAddress;
    public int publishRetransmitCount;
    public int publishRetransmitIntervalSteps;
    public int publishTtl;
    public final byte[] src;

    public ConfigModelPublicationSetParams(@NonNull ProvisionedMeshNode provisionedMeshNode, @NonNull byte[] bArr, @NonNull int i2, @NonNull byte[] bArr2, @NonNull int i3) {
        this.meshNode = provisionedMeshNode;
        this.src = provisionedMeshNode.getConfigurationSrc();
        this.elementAddress = bArr;
        this.modelIdentifier = i2;
        this.publishAddress = bArr2;
        this.appKeyIndex = i3;
    }

    public int getAppKeyIndex() {
        return this.appKeyIndex;
    }

    public int getAszmic() {
        return this.aszmic;
    }

    public boolean getCredentialFlag() {
        return this.credentialFlag;
    }

    public byte[] getElementAddress() {
        return this.elementAddress;
    }

    public ProvisionedMeshNode getMeshNode() {
        return this.meshNode;
    }

    public int getModelIdentifier() {
        return this.modelIdentifier;
    }

    public int getPublicationResolution() {
        return this.publicationResolution;
    }

    public int getPublicationSteps() {
        return this.publicationSteps;
    }

    public byte[] getPublishAddress() {
        return this.publishAddress;
    }

    public int getPublishRetransmitCount() {
        return this.publishRetransmitCount;
    }

    public int getPublishRetransmitIntervalSteps() {
        return this.publishRetransmitIntervalSteps;
    }

    public int getPublishTtl() {
        return this.publishTtl;
    }

    public void setCredentialFlag(boolean z2) {
        this.credentialFlag = z2;
    }

    public void setPublicationResolution(int i2) {
        this.publicationResolution = i2;
    }

    public void setPublicationSteps(int i2) {
        this.publicationSteps = i2;
    }

    public void setPublishRetransmitCount(int i2) {
        this.publishRetransmitCount = i2;
    }

    public void setPublishRetransmitIntervalSteps(int i2) {
        this.publishRetransmitIntervalSteps = i2;
    }

    public void setPublishTtl(int i2) {
        this.publishTtl = i2;
    }
}
