package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import meshprovisioner.utils.MeshParserUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class v implements SIGMeshBizRequest.b {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f1292a;

    public v(String str) {
        this.f1292a = str;
    }

    @Override // com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest.b
    public byte[] getEncodedParameters() {
        try {
            return MeshParserUtils.toByteArray(this.f1292a);
        } catch (Exception e2) {
            e2.printStackTrace();
            a.a.a.a.b.m.a.a("SIGMeshBizRequestGenerator", "getCommonFireAndForgotRequest: eshParserUtils.toByteArray e=" + e2);
            return null;
        }
    }
}
