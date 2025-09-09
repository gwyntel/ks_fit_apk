package meshprovisioner.configuration;

import android.content.Context;
import b.InterfaceC0328c;

/* loaded from: classes5.dex */
public abstract class VendorModelMessageState extends MeshMessageState {
    public static final String TAG = "VendorModelMessageState";

    public VendorModelMessageState(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c) {
        super(context, provisionedMeshNode, interfaceC0328c);
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public void executeResend() {
        super.executeResend();
    }
}
