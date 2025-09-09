package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.aisbase.Constants;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: a.a.a.a.b.a.k, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0222k implements SIGMeshBizRequest.b {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Map f1276a;

    public C0222k(Map map) {
        this.f1276a = map;
    }

    @Override // com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest.b
    public byte[] getEncodedParameters() {
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(50).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.put(SIGMeshBizRequestGenerator.d());
        byteBufferOrder.put(new byte[]{Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, -16});
        SIGMeshBizRequestGenerator.Attribute[] attributeArr = {SIGMeshBizRequestGenerator.Attribute.powerstate, SIGMeshBizRequestGenerator.Attribute.brightness, SIGMeshBizRequestGenerator.Attribute.colorTemperature};
        int i2 = 3;
        for (int i3 = 0; i3 < 3; i3++) {
            SIGMeshBizRequestGenerator.Attribute attribute = attributeArr[i3];
            if (this.f1276a.containsKey(attribute.attributeName)) {
                i2 += attribute.attrParameterLength;
                byteBufferOrder.put(attribute.getValueEncoder().a(this.f1276a.get(attribute.attributeName)));
            }
        }
        return Arrays.copyOfRange(byteBufferOrder.array(), 0, i2);
    }
}
