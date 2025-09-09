package a.a.a.a.b.i;

import b.InterfaceC0326a;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;
import meshprovisioner.states.UnprovisionedMeshNodeData;

/* renamed from: a.a.a.a.b.i.g, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0238g implements InterfaceC0326a.b {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ UnprovisionedMeshNodeData f1481a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ FastProvisionManager f1482b;

    public C0238g(FastProvisionManager fastProvisionManager, UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        this.f1482b = fastProvisionManager;
        this.f1481a = unprovisionedMeshNodeData;
    }

    @Override // b.InterfaceC0326a.b
    public void generate(String str) {
        a.a.a.a.b.m.a.c(FastProvisionManager.TAG, "receive confirmationCloud: " + str);
        this.f1482b.onReceiveConfirmationFromCloud(this.f1481a, str);
    }
}
