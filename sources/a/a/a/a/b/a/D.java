package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class D implements SIGMeshBizRequestGenerator.a<Object> {
    @Override // com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator.a
    public byte[] a(Object obj) {
        if (obj instanceof String) {
            try {
                short sRound = (short) Math.round(Integer.parseInt((String) obj) * 655.35d);
                return new byte[]{(byte) (sRound & 255), (byte) ((sRound >> 8) & 255)};
            } catch (NumberFormatException e2) {
                a.a.a.a.b.m.a.b("SIGMeshBizRequestGenerator", e2.toString());
            }
        }
        if (!(obj instanceof Integer)) {
            return new byte[0];
        }
        short sRound2 = (short) Math.round(((Integer) obj).intValue() * 655.35d);
        return new byte[]{(byte) (sRound2 & 255), (byte) ((sRound2 >> 8) & 255)};
    }
}
