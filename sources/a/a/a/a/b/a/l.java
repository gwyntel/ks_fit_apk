package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class l implements SIGMeshBizRequest.b {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1277a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ byte f1278b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Map f1279c;

    public l(int i2, byte b2, Map map) {
        this.f1277a = i2;
        this.f1278b = b2;
        this.f1279c = map;
    }

    @Override // com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest.b
    public byte[] getEncodedParameters() {
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(this.f1277a).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.put(this.f1278b);
        for (Map.Entry entry : this.f1279c.entrySet()) {
            byteBufferOrder.put(SIGMeshBizRequestGenerator.Attribute.valueOf((String) entry.getKey()).attrType);
            byteBufferOrder.put(SIGMeshBizRequestGenerator.Attribute.valueOf((String) entry.getKey()).getValueEncoder().a(entry.getValue()));
        }
        return byteBufferOrder.array();
    }
}
