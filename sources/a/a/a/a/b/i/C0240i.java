package a.a.a.a.b.i;

import b.InterfaceC0326a;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;
import meshprovisioner.states.UnprovisionedMeshNode;

/* renamed from: a.a.a.a.b.i.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0240i implements InterfaceC0326a.InterfaceC0015a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ FastProvisionManager f1485a;

    public C0240i(FastProvisionManager fastProvisionManager) {
        this.f1485a = fastProvisionManager;
    }

    @Override // b.InterfaceC0326a.InterfaceC0015a
    public void a(UnprovisionedMeshNode unprovisionedMeshNode, boolean z2) {
        a.a.a.a.b.m.a.c(FastProvisionManager.TAG, "CheckConfirmationValueMatchesCallback: match = " + z2 + ", mac = " + unprovisionedMeshNode.getBluetoothDeviceAddress() + ", origin mac " + this.f1485a.unprovisionedMeshNodeData.getDeviceMac());
        if (z2) {
            FastProvisionManager fastProvisionManager = this.f1485a;
            fastProvisionManager.onReceiveVerifyResultFromCloud(fastProvisionManager.unprovisionedMeshNodeData);
            return;
        }
        this.f1485a.onProvisionFailed(-1, "auth confirmation failed: match = " + z2 + ", callback device: " + unprovisionedMeshNode.getBluetoothDeviceAddress() + ", origin device: " + this.f1485a.unprovisionedMeshNodeData.getDeviceMac());
    }
}
