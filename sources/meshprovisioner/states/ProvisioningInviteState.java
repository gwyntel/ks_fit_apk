package meshprovisioner.states;

import b.InterfaceC0329d;
import b.p;
import meshprovisioner.states.ProvisioningState;

/* loaded from: classes5.dex */
public class ProvisioningInviteState extends ProvisioningState {
    public final String TAG = ProvisioningInviteState.class.getSimpleName();
    public final int attentionTimer;
    public final InterfaceC0329d mInternalTransportCallbacks;
    public final p mMeshProvisioningStatusCallbacks;
    public final UnprovisionedMeshNode mUnprovisionedMeshNode;

    public ProvisioningInviteState(UnprovisionedMeshNode unprovisionedMeshNode, int i2, InterfaceC0329d interfaceC0329d, p pVar) {
        this.mUnprovisionedMeshNode = unprovisionedMeshNode;
        this.attentionTimer = i2;
        this.mMeshProvisioningStatusCallbacks = pVar;
        this.mInternalTransportCallbacks = interfaceC0329d;
    }

    private byte[] createInvitePDU() {
        return new byte[]{3, 0, (byte) this.attentionTimer};
    }

    @Override // meshprovisioner.states.ProvisioningState
    public void executeSend() {
        byte[] bArrCreateInvitePDU = createInvitePDU();
        this.mMeshProvisioningStatusCallbacks.onProvisioningInviteSent(this.mUnprovisionedMeshNode);
        this.mInternalTransportCallbacks.sendPdu(this.mUnprovisionedMeshNode, bArrCreateInvitePDU);
    }

    @Override // meshprovisioner.states.ProvisioningState
    public ProvisioningState.State getState() {
        return ProvisioningState.State.PROVISIONING_INVITE;
    }

    @Override // meshprovisioner.states.ProvisioningState
    public boolean parseData(byte[] bArr) {
        return true;
    }
}
