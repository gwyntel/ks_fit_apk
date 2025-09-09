package b;

import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNode;

/* loaded from: classes2.dex */
public interface p {
    void onProvisioningAuthenticationInputRequested(UnprovisionedMeshNode unprovisionedMeshNode);

    void onProvisioningCapabilitiesReceived(UnprovisionedMeshNode unprovisionedMeshNode);

    void onProvisioningComplete(ProvisionedMeshNode provisionedMeshNode);

    void onProvisioningConfirmationReceived(UnprovisionedMeshNode unprovisionedMeshNode);

    void onProvisioningConfirmationSent(UnprovisionedMeshNode unprovisionedMeshNode);

    void onProvisioningDataSent(UnprovisionedMeshNode unprovisionedMeshNode);

    void onProvisioningFailed(UnprovisionedMeshNode unprovisionedMeshNode, int i2);

    void onProvisioningInputCompleteSent(UnprovisionedMeshNode unprovisionedMeshNode);

    void onProvisioningInviteSent(UnprovisionedMeshNode unprovisionedMeshNode);

    void onProvisioningPublicKeyReceived(UnprovisionedMeshNode unprovisionedMeshNode);

    void onProvisioningPublicKeySent(UnprovisionedMeshNode unprovisionedMeshNode);

    void onProvisioningRandomReceived(UnprovisionedMeshNode unprovisionedMeshNode);

    void onProvisioningRandomSent(UnprovisionedMeshNode unprovisionedMeshNode);

    void onProvisioningStartSent(UnprovisionedMeshNode unprovisionedMeshNode);
}
