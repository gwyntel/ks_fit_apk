package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class C implements SIGMeshBizRequestGenerator.a<Object> {
    @Override // com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator.a
    public byte[] a(Object obj) {
        if (obj instanceof String) {
            try {
                return new byte[]{(byte) (Integer.parseInt((String) obj) & 255)};
            } catch (NumberFormatException e2) {
                a.a.a.a.b.m.a.b("SIGMeshBizRequestGenerator", e2.toString());
            }
        }
        return obj instanceof Integer ? new byte[]{(byte) (((Integer) obj).intValue() & 255)} : new byte[0];
    }
}
