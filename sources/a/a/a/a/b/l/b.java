package a.a.a.a.b.l;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.fastjson.JSON;

/* loaded from: classes.dex */
class b implements IActionListener {
    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        a.a.a.a.b.m.a.c("ControlMsgUtUtil", "onFailure: " + i2 + ", " + str);
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onSuccess(Object obj) {
        a.a.a.a.b.m.a.c("ControlMsgUtUtil", "onSuccess: " + JSON.toJSONString(obj));
        c.c();
    }
}
