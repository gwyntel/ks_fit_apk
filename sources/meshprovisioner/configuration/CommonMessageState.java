package meshprovisioner.configuration;

import android.content.Context;
import b.InterfaceC0328c;

/* loaded from: classes5.dex */
public abstract class CommonMessageState extends MeshMessageState {
    public static final String TAG = "CommonMessageState";

    public CommonMessageState(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c) {
        super(context, provisionedMeshNode, interfaceC0328c);
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public void executeResend() {
        super.executeResend();
    }
}
