package a.a.a.a.b.i;

import b.InterfaceC0326a;
import meshprovisioner.states.UnprovisionedMeshNode;

/* loaded from: classes.dex */
public class C implements InterfaceC0326a.InterfaceC0015a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ J f1364a;

    public C(J j2) {
        this.f1364a = j2;
    }

    @Override // b.InterfaceC0326a.InterfaceC0015a
    public void a(UnprovisionedMeshNode unprovisionedMeshNode, boolean z2) {
        a.a.a.a.b.m.a.c(this.f1364a.f1373a, "CheckConfirmationValueMatchesCallback: match = " + z2 + ", mac = " + unprovisionedMeshNode.getBluetoothDeviceAddress() + ", origin mac " + this.f1364a.f1381i.getDeviceMac());
        if (z2) {
            J j2 = this.f1364a;
            j2.onReceiveVerifyResultFromCloud(j2.f1381i);
            return;
        }
        this.f1364a.onProvisionFailed(-1, "auth confirmation failed: match = " + z2 + ", callback device: " + unprovisionedMeshNode.getBluetoothDeviceAddress() + ", origin device: " + this.f1364a.f1381i.getDeviceMac());
    }
}
