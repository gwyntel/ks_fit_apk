package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class y implements SIGMeshBizRequestGenerator.a<Short> {
    @Override // com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator.a
    public byte[] a(Short sh) {
        return new byte[]{(byte) (sh.shortValue() & 255)};
    }
}
