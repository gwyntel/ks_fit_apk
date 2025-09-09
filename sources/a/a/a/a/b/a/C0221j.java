package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: a.a.a.a.b.a.j, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0221j implements SIGMeshBizRequest.b {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ short f1275a;

    public C0221j(short s2) {
        this.f1275a = s2;
    }

    @Override // com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest.b
    public byte[] getEncodedParameters() {
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.putShort((short) 17282);
        byteBufferOrder.putShort(this.f1275a);
        byteBufferOrder.put(SIGMeshBizRequestGenerator.d());
        return byteBufferOrder.array();
    }
}
