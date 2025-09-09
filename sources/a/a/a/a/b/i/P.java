package a.a.a.a.b.i;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import b.u;
import com.alibaba.ailabs.iot.mesh.bean.MeshNodeStatus;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import com.alibaba.ailabs.iot.mesh.provision.WiFiConfigReplyParser;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.fastjson.JSONObject;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes.dex */
public class P {

    /* renamed from: a, reason: collision with root package name */
    public static byte[] f1404a = {-16, 6};

    /* renamed from: d, reason: collision with root package name */
    public String f1407d;

    /* renamed from: e, reason: collision with root package name */
    public Map<String, Object> f1408e;

    /* renamed from: f, reason: collision with root package name */
    public IActionListener<String> f1409f;

    /* renamed from: g, reason: collision with root package name */
    public ProvisionedMeshNode f1410g;

    /* renamed from: b, reason: collision with root package name */
    public final String f1405b = "wifi_config_" + P.class.getName();

    /* renamed from: h, reason: collision with root package name */
    public Runnable f1411h = null;

    /* renamed from: i, reason: collision with root package name */
    public final int f1412i = 40000;

    /* renamed from: j, reason: collision with root package name */
    public WiFiConfigReplyParser f1413j = null;

    /* renamed from: c, reason: collision with root package name */
    public Handler f1406c = new Handler(Looper.getMainLooper());

    public P(String str, Map<String, Object> map, IActionListener<String> iActionListener) {
        this.f1407d = MeshDeviceInfoManager.getInstance().coverIotIdToDevId(str);
        this.f1410g = a.a.a.a.b.G.a().d().b(this.f1407d);
        this.f1408e = map;
        this.f1409f = iActionListener;
    }

    public byte[] b() {
        ProvisionedMeshNode provisionedMeshNode = this.f1410g;
        if (provisionedMeshNode == null) {
            return null;
        }
        return provisionedMeshNode.getUnicastAddress();
    }

    public final void c() {
        if (this.f1413j != null) {
            return;
        }
        this.f1413j = new WiFiConfigReplyParser(new O(this));
    }

    public void d() {
        if (this.f1410g == null) {
            Utils.notifyFailed(this.f1409f, -53, "meshNode is null");
            return;
        }
        SIGMeshBizRequest sIGMeshBizRequestA = new SIGMeshBizRequest.a().a(SIGMeshBizRequest.Type.VENDOR_ATTRIBUTE_SET).a(this.f1410g).a(this.f1410g.getUnicastAddress()).a(true).a(new L(this)).a(0).a(new N(this)).a(new M(this)).a();
        LinkedList linkedList = new LinkedList();
        linkedList.add(sIGMeshBizRequestA);
        u.a aVarH = sIGMeshBizRequestA.h();
        if (aVarH == null) {
            Utils.notifyFailed(this.f1409f, -30, "Internal error");
            return;
        }
        b.K kE = aVarH.e();
        if (kE != null) {
            kE.a(linkedList);
        } else {
            Utils.notifyFailed(this.f1409f, -23, "Target mesh network unreachable");
        }
    }

    public void a(byte[] bArr, String str, byte[] bArr2) {
        if (!Arrays.equals(bArr, this.f1410g.getUnicastAddress()) || !"d4a801".equalsIgnoreCase(str) || bArr2 == null || bArr2.length < 6) {
            return;
        }
        byte b2 = bArr2[0];
        if (Arrays.equals(new byte[]{bArr2[2], bArr2[1]}, f1404a)) {
            if (this.f1413j == null) {
                c();
            }
            int length = bArr2.length - 3;
            byte[] bArr3 = new byte[length];
            System.arraycopy(bArr2, 3, bArr3, 0, length);
            this.f1413j.a(bArr3);
        }
    }

    public final void a(boolean z2, int i2, int i3, String str) {
        a();
        if (z2) {
            a.a.a.a.b.m.a.c(this.f1405b, "on successful to config Wi-Fi info");
        } else {
            a.a.a.a.b.m.a.b(this.f1405b, "on failed to config Wi-Fi info, error code: " + i2 + " , " + str);
        }
        Intent intent = new Intent(Utils.ACTION_PROVISIONING_STATE);
        intent.putExtra(Utils.EXTRA_PROVISIONING_STATE, MeshNodeStatus.COMBO_WIFI_CONFIG_STATUS.getState());
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("isSuccess", (Object) Boolean.valueOf(z2));
        jSONObject.put("subErrorCode", (Object) Integer.valueOf(i3));
        jSONObject.put("errorMessage", (Object) str);
        intent.putExtra(Utils.EXTRA_PROVISIONING_FAIL_MSG, jSONObject.toJSONString());
        if (z2) {
            Utils.notifySuccess(this.f1409f, "");
        } else {
            Utils.notifyFailed(this.f1409f, i2, jSONObject.toJSONString());
        }
    }

    public final void a() {
        Runnable runnable = this.f1411h;
        if (runnable != null) {
            this.f1406c.removeCallbacks(runnable);
            this.f1411h = null;
        }
    }
}
