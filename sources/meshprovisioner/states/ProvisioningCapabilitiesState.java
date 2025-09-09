package meshprovisioner.states;

import a.a.a.a.b.m.a;
import b.p;
import meshprovisioner.states.ProvisioningState;
import meshprovisioner.utils.AlgorithmInformationParser;
import meshprovisioner.utils.ParseInputOOBActions;
import meshprovisioner.utils.ParseOutputOOBActions;
import meshprovisioner.utils.ParsePublicKeyInformation;
import meshprovisioner.utils.ParseStaticOutputOOBInformation;

/* loaded from: classes5.dex */
public class ProvisioningCapabilitiesState extends ProvisioningState {
    public static final String TAG = ProvisioningInviteState.class.getSimpleName();
    public ProvisioningCapabilities capabilities;
    public final p mCallbacks;
    public final UnprovisionedMeshNode mUnprovisionedMeshNode;

    public ProvisioningCapabilitiesState(UnprovisionedMeshNode unprovisionedMeshNode, p pVar) {
        this.mCallbacks = pVar;
        this.mUnprovisionedMeshNode = unprovisionedMeshNode;
    }

    private boolean parseProvisioningCapabilities(byte[] bArr) {
        ProvisioningCapabilities provisioningCapabilities = new ProvisioningCapabilities();
        provisioningCapabilities.setRawCapabilities(bArr);
        byte b2 = bArr[2];
        if (b2 == 0) {
            throw new IllegalArgumentException("Number of elements cannot be zero");
        }
        String str = TAG;
        a.a(str, "Number of elements: " + ((int) b2));
        provisioningCapabilities.setNumberOfElements(b2);
        short s2 = (short) (((bArr[3] & 255) << 8) | (bArr[4] & 255));
        a.a(str, "Algorithm: " + AlgorithmInformationParser.parseAlgorithm(s2));
        provisioningCapabilities.setSupportedAlgorithm(s2);
        byte b3 = bArr[5];
        a.a(str, "Public key type: " + ParsePublicKeyInformation.parsePublicKeyInformation(b3));
        provisioningCapabilities.setPublicKeyType(b3);
        byte b4 = bArr[6];
        a.a(str, "Static OOB type: " + ParseStaticOutputOOBInformation.parseStaticOOBActionInformation(b4));
        provisioningCapabilities.setStaticOOBType(b4);
        byte b5 = bArr[7];
        a.a(str, "Output OOB size: " + ((int) b5));
        provisioningCapabilities.setOutputOOBSize(b5);
        short s3 = (short) (((bArr[8] & 255) << 8) | (bArr[9] & 255));
        ParseOutputOOBActions.parseOutputActionsFromBitMask(s3);
        provisioningCapabilities.setOutputOOBAction(s3);
        byte b6 = bArr[10];
        a.a(str, "Input OOB size: " + ((int) b6));
        provisioningCapabilities.setInputOOBSize(b6);
        short s4 = (short) ((bArr[12] & 255) | ((bArr[11] & 255) << 8));
        ParseInputOOBActions.parseInputActionsFromBitMask(s4);
        provisioningCapabilities.setInputOOBAction(s4);
        this.capabilities = provisioningCapabilities;
        return true;
    }

    @Override // meshprovisioner.states.ProvisioningState
    public void executeSend() {
    }

    public ProvisioningCapabilities getCapabilities() {
        return this.capabilities;
    }

    @Override // meshprovisioner.states.ProvisioningState
    public ProvisioningState.State getState() {
        return ProvisioningState.State.PROVISIONING_CAPABILITIES;
    }

    @Override // meshprovisioner.states.ProvisioningState
    public boolean parseData(byte[] bArr) {
        boolean provisioningCapabilities = parseProvisioningCapabilities(bArr);
        this.mUnprovisionedMeshNode.setProvisioningCapabilities(this.capabilities);
        this.mCallbacks.onProvisioningCapabilitiesReceived(this.mUnprovisionedMeshNode);
        return provisioningCapabilities;
    }
}
