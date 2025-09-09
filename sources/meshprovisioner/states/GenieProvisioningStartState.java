package meshprovisioner.states;

import a.a.a.a.b.m.a;
import b.InterfaceC0329d;
import b.p;
import meshprovisioner.states.ProvisioningState;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.ParseOutputOOBActions;
import meshprovisioner.utils.ParseProvisioningAlgorithm;

/* loaded from: classes5.dex */
public class GenieProvisioningStartState extends ProvisioningState {
    public int inputOOBAction;
    public final InterfaceC0329d mInternalTransportCallbacks;
    public final p mMeshProvisioningStatusCallbacks;
    public final UnprovisionedMeshNode mUnprovisionedMeshNode;
    public final String TAG = GenieProvisioningStartState.class.getSimpleName();
    public int numberOfElements = 1;
    public int algorithm = 1;
    public int publicKeyType = 0;
    public int staticOOBType = 1;
    public int outputOOBSize = 0;
    public int outputOOBAction = 0;
    public int inputOOBSize = 0;

    public GenieProvisioningStartState(UnprovisionedMeshNode unprovisionedMeshNode, InterfaceC0329d interfaceC0329d, p pVar) {
        this.mUnprovisionedMeshNode = unprovisionedMeshNode;
        this.mInternalTransportCallbacks = interfaceC0329d;
        this.mMeshProvisioningStatusCallbacks = pVar;
    }

    private byte[] createProvisioningStartPDU() {
        byte[] bArr = new byte[7];
        bArr[0] = 3;
        bArr[1] = 2;
        bArr[2] = ParseProvisioningAlgorithm.getAlgorithmValue(this.algorithm);
        bArr[3] = 0;
        short sSelectOutputActionsFromBitMask = (byte) ParseOutputOOBActions.selectOutputActionsFromBitMask(this.outputOOBAction);
        if (this.staticOOBType != 0) {
            bArr[4] = 1;
            bArr[5] = 0;
            bArr[6] = 0;
        } else if (sSelectOutputActionsFromBitMask != 0) {
            bArr[4] = 2;
            bArr[5] = (byte) ParseOutputOOBActions.getOuputOOBActionValue(sSelectOutputActionsFromBitMask);
            bArr[6] = (byte) this.outputOOBSize;
        } else {
            bArr[4] = 0;
            bArr[5] = 0;
            bArr[6] = 0;
        }
        a.a(this.TAG, "Provisioning start PDU: " + MeshParserUtils.bytesToHex(bArr, true));
        return bArr;
    }

    @Override // meshprovisioner.states.ProvisioningState
    public void executeSend() {
        byte[] bArrCreateProvisioningStartPDU = createProvisioningStartPDU();
        this.mMeshProvisioningStatusCallbacks.onProvisioningStartSent(this.mUnprovisionedMeshNode);
        this.mInternalTransportCallbacks.sendPdu(this.mUnprovisionedMeshNode, bArrCreateProvisioningStartPDU);
    }

    @Override // meshprovisioner.states.ProvisioningState
    public ProvisioningState.State getState() {
        return ProvisioningState.State.PROVISIONING_START;
    }

    @Override // meshprovisioner.states.ProvisioningState
    public boolean parseData(byte[] bArr) {
        return true;
    }

    public void setProvisioningCapabilities(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.numberOfElements = i2;
        this.algorithm = i3;
        this.publicKeyType = i4;
        this.staticOOBType = i5;
        this.outputOOBSize = i6;
        this.outputOOBAction = i7;
        this.inputOOBSize = i8;
        this.inputOOBAction = i9;
    }
}
