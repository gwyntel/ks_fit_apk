package meshprovisioner.configuration;

import android.content.Context;
import b.InterfaceC0328c;
import meshprovisioner.configuration.MeshMessageState;

/* loaded from: classes5.dex */
public abstract class ConfigMessageState extends MeshMessageState {
    public static final String TAG = "ConfigMessageState";

    public ConfigMessageState(Context context, ProvisionedMeshNode provisionedMeshNode, InterfaceC0328c interfaceC0328c) {
        super(context, provisionedMeshNode, interfaceC0328c);
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public void executeResend() {
        super.executeResend();
    }

    @Override // meshprovisioner.configuration.MeshMessageState
    public abstract MeshMessageState.MessageState getState();
}
