package a.a.a.a.b.f;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.aliyun.alink.linksdk.connectsdk.ApiCallBack;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class a extends ApiCallBack {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1343a;

    public a(IActionListener iActionListener) {
        this.f1343a = iActionListener;
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    public void onFail(int i2, String str) {
        Utils.notifyFailed(this.f1343a, i2, str);
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    public void onSuccess(Object obj) {
        Utils.notifySuccess((IActionListener<Boolean>) this.f1343a, Boolean.TRUE);
    }
}
