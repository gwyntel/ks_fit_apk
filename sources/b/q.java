package b;

import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes2.dex */
public interface q {
    ProvisionedMeshNode getMeshNode(byte[] bArr, byte[] bArr2);

    void onAppKeyAddSent(ProvisionedMeshNode provisionedMeshNode);

    void onAppKeyBindSent(ProvisionedMeshNode provisionedMeshNode);

    void onAppKeyBindStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, int i2, int i3, int i4, int i5);

    void onAppKeyStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, int i2, int i3, int i4);

    void onAppKeyUnbindSent(ProvisionedMeshNode provisionedMeshNode);

    void onBlockAcknowledgementReceived(ProvisionedMeshNode provisionedMeshNode);

    void onBlockAcknowledgementSent(ProvisionedMeshNode provisionedMeshNode);

    void onCommonMessageStatusReceived(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, String str, byte[] bArr2, a.a.a.a.b.h.a aVar);

    void onCompositionDataStatusReceived(ProvisionedMeshNode provisionedMeshNode);

    void onGenericLevelGetSent(ProvisionedMeshNode provisionedMeshNode);

    void onGenericLevelSetUnacknowledgedSent(ProvisionedMeshNode provisionedMeshNode);

    void onGenericLevelStatusReceived(ProvisionedMeshNode provisionedMeshNode, int i2, int i3, int i4, int i5);

    void onGenericOnOffGetSent(ProvisionedMeshNode provisionedMeshNode);

    void onGenericOnOffSetUnacknowledgedSent(ProvisionedMeshNode provisionedMeshNode);

    void onGenericOnOffStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, Boolean bool, int i2, int i3);

    void onGetCompositionDataSent(ProvisionedMeshNode provisionedMeshNode);

    void onMeshNodeResetSent(ProvisionedMeshNode provisionedMeshNode);

    void onMeshNodeResetStatusReceived(ProvisionedMeshNode provisionedMeshNode);

    void onPublicationSetSent(ProvisionedMeshNode provisionedMeshNode);

    void onPublicationStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, int i2, byte[] bArr, byte[] bArr2, int i3);

    void onSubscriptionAddSent(ProvisionedMeshNode provisionedMeshNode);

    void onSubscriptionDeleteSent(ProvisionedMeshNode provisionedMeshNode);

    void onSubscriptionStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, int i2, byte[] bArr, byte[] bArr2, int i3);

    void onTransactionFailed(ProvisionedMeshNode provisionedMeshNode, int i2, boolean z2);

    void onUnknownPduReceived(ProvisionedMeshNode provisionedMeshNode);

    void onVendorModelMessageStatusReceived(ProvisionedMeshNode provisionedMeshNode, byte[] bArr);
}
