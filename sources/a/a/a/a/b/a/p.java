package a.a.a.a.b.a;

import android.util.Pair;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class p implements I<byte[]> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1284a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Map f1285b;

    public p(int i2, Map map) {
        this.f1284a = i2;
        this.f1285b = map;
    }

    @Override // a.a.a.a.b.a.I
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public Pair<Integer, Object> parseResponse(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("parseResponse() called with: result = [");
        sb.append(bArr == null ? TmpConstant.GROUP_ROLE_UNKNOWN : Integer.valueOf(bArr.length));
        sb.append("], setLen=");
        sb.append(this.f1284a);
        a.a.a.a.b.m.a.a("SIGMeshBizRequestGenerator", sb.toString());
        if (bArr == null || bArr.length < 4) {
            return new Pair<>(-14, "Invalid length");
        }
        byte b2 = bArr[0];
        byte b3 = bArr[bArr.length - 1];
        if (bArr.length != 4 || b3 != 0) {
            a.a.a.a.b.m.a.d("SIGMeshBizRequestGenerator", "scene unbind failed.");
            return new Pair<>(-53, b3 != 1 ? b3 != 2 ? b3 != 3 ? b3 != 4 ? "unknown error" : "Scene Number not exist" : "Scene Number error" : "Invalid Scene Number" : "Invalid request");
        }
        short s2 = (short) ((bArr[2] << 8) | bArr[1]);
        a.a.a.a.b.m.a.a("SIGMeshBizRequestGenerator", "Request, attributeSceneIdMap: " + this.f1285b + ", reply, sceneNumber: " + ((int) ((short) (bArr[bArr.length - 2] | (bArr[bArr.length - 1] << 8)))) + ", replyAttrType: " + ((int) s2));
        return new Pair<>(0, Boolean.TRUE);
    }
}
