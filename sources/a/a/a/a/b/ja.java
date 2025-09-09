package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.bean.MeshAccessPayload;
import com.alibaba.ailabs.iot.mesh.callback.MeshMsgListener;

/* loaded from: classes.dex */
public class ja implements MeshMsgListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ a.a.a.a.b.i.P f1539a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MeshService.b f1540b;

    public ja(MeshService.b bVar, a.a.a.a.b.i.P p2) {
        this.f1540b = bVar;
        this.f1539a = p2;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.MeshMsgListener
    public void onReceiveMeshMessage(byte[] bArr, MeshAccessPayload meshAccessPayload) {
        this.f1539a.a(bArr, meshAccessPayload.getOpCode(), meshAccessPayload.getParameters());
    }
}
