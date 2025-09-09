package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class z implements SIGMeshBizRequest.b {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ boolean f1295a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ short f1296b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Map f1297c;

    public z(boolean z2, short s2, Map map) {
        this.f1295a = z2;
        this.f1296b = s2;
        this.f1297c = map;
    }

    @Override // com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest.b
    public byte[] getEncodedParameters() {
        int length = 3;
        if (this.f1295a) {
            ByteBuffer byteBufferOrder = ByteBuffer.allocate(20).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder.putShort(this.f1296b);
            byteBufferOrder.put((byte) -88);
            Map map = this.f1297c;
            if (map != null) {
                for (Map.Entry entry : map.entrySet()) {
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append("attr: ");
                        sb.append((String) entry.getKey());
                        sb.append(", value: ");
                        sb.append(entry.getValue().toString());
                        a.a.a.a.b.m.a.a("SIGMeshBizRequestGenerator", sb.toString());
                        SIGMeshBizRequestGenerator.Attribute attributeValueOf = SIGMeshBizRequestGenerator.Attribute.valueOf((String) entry.getKey());
                        byteBufferOrder.put(attributeValueOf.attrType);
                        byteBufferOrder.put(attributeValueOf.getValueEncoder().a(entry.getValue()));
                        length += attributeValueOf.attrType.length + attributeValueOf.attrParameterLength;
                    } catch (Exception e2) {
                        a.a.a.a.b.m.a.b("SIGMeshBizRequestGenerator", e2.toString());
                    }
                }
            }
            return Arrays.copyOfRange(byteBufferOrder.array(), 0, length);
        }
        ByteBuffer byteBufferOrder2 = ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder2.putShort(this.f1296b);
        Map map2 = this.f1297c;
        int i2 = 2;
        if (map2 != null) {
            SIGMeshBizRequestGenerator.Attribute attribute = SIGMeshBizRequestGenerator.Attribute.powerstate;
            if (map2.containsKey(attribute.attributeName)) {
                byteBufferOrder2.put(attribute.getValueEncoder().a(this.f1297c.get(attribute.attributeName)));
            } else {
                length = 2;
            }
            Map map3 = this.f1297c;
            SIGMeshBizRequestGenerator.Attribute attribute2 = SIGMeshBizRequestGenerator.Attribute.brightness;
            if (map3.containsKey(attribute2.attributeName)) {
                length += 2;
                byteBufferOrder2.put(attribute2.getValueEncoder().a(this.f1297c.get(attribute2.attributeName)));
            }
            i2 = length;
            Map map4 = this.f1297c;
            SIGMeshBizRequestGenerator.Attribute attribute3 = SIGMeshBizRequestGenerator.Attribute.colorTemperature;
            if (map4.containsKey(attribute3.attributeName)) {
                i2 += 2;
                byteBufferOrder2.put(attribute3.getValueEncoder().a(this.f1297c.get(attribute3.attributeName)));
            }
        }
        return Arrays.copyOfRange(byteBufferOrder2.array(), 0, i2);
    }
}
