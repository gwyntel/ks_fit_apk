package a.a.a.a.b;

import androidx.annotation.NonNull;
import anetwork.channel.util.RequestConstant;
import com.alibaba.ailabs.iot.mesh.AuthInfoListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import datasource.MeshConfig;
import datasource.MeshConfigCallback;
import datasource.bean.ConfigurationData;
import datasource.bean.DeviceStatus;
import datasource.bean.IoTGatewayEvent;
import datasource.bean.ProvisionInfo;
import datasource.bean.ProvisionInfo4Master;
import datasource.bean.ServerConfirmation;
import datasource.bean.Sigmesh;
import datasource.bean.WakeUpServiceContext;
import java.util.List;

/* loaded from: classes.dex */
public class na {

    /* renamed from: a, reason: collision with root package name */
    public static final String f1610a = "tg_mesh_sdk_" + na.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    public AuthInfoListener f1611b;

    /* renamed from: c, reason: collision with root package name */
    public MeshConfig f1612c;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final na f1613a = new na();
    }

    public static na a() {
        return a.f1613a;
    }

    public void b(@NonNull String str, @NonNull String str2, @NonNull String str3, MeshConfigCallback<String> meshConfigCallback) {
        String str4 = f1610a;
        a.a.a.a.b.m.a.a(str4, "getInfoByAuthInfo called...");
        if (this.f1612c == null) {
            a.a.a.a.b.m.a.b(str4, "mMeshConfig is null");
        } else {
            AuthInfoListener authInfoListener = this.f1611b;
            this.f1612c.getInfoByAuthInfo(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, str2, str3, meshConfigCallback);
        }
    }

    public void c(@NonNull String str, @NonNull String str2, @NonNull String str3, MeshConfigCallback<ProvisionInfo> meshConfigCallback) {
        String str4 = f1610a;
        a.a.a.a.b.m.a.a(str4, "getProvisionInfo called...");
        if (this.f1612c == null) {
            a.a.a.a.b.m.a.b(str4, "mMeshConfig is null");
        } else {
            AuthInfoListener authInfoListener = this.f1611b;
            this.f1612c.getProvisionInfo(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, str2, str3, meshConfigCallback);
        }
    }

    public void d(@NonNull String str, @NonNull String str2, @NonNull String str3, MeshConfigCallback<List<Sigmesh>> meshConfigCallback) {
        String str4 = f1610a;
        a.a.a.a.b.m.a.a(str4, "groupControl called...");
        if (this.f1612c == null) {
            a.a.a.a.b.m.a.b(str4, "meshConfig is null");
            return;
        }
        AuthInfoListener authInfoListener = this.f1611b;
        if (authInfoListener != null) {
            authInfoListener.getAuthInfo();
        }
        this.f1612c.groupControl(str, str2, str3, meshConfigCallback);
    }

    public void a(AuthInfoListener authInfoListener, MeshConfig meshConfig) {
        a.a.a.a.b.m.a.a(f1610a, "init...");
        this.f1611b = authInfoListener;
        this.f1612c = meshConfig;
    }

    public void a(String str, MeshConfigCallback<ProvisionInfo4Master> meshConfigCallback) {
        String str2 = f1610a;
        a.a.a.a.b.m.a.a(str2, "getProvisionInfo4Master called...");
        if (this.f1612c == null) {
            a.a.a.a.b.m.a.b(str2, "mMeshConfig is null");
        } else {
            AuthInfoListener authInfoListener = this.f1611b;
            this.f1612c.getProvisionInfo4Master(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, meshConfigCallback);
        }
    }

    public String b() {
        AuthInfoListener authInfoListener = this.f1611b;
        if (authInfoListener == null) {
            a.a.a.a.b.m.a.b(f1610a, "mAuthInfoListener is null");
            return "";
        }
        try {
            JSONObject object = JSON.parseObject(authInfoListener.getAuthInfo());
            if (object != null) {
                return object.getString("userId");
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public String c() {
        AuthInfoListener authInfoListener = this.f1611b;
        if (authInfoListener == null) {
            a.a.a.a.b.m.a.b(f1610a, "mAuthInfoListener is null");
            return "";
        }
        try {
            JSONObject object = JSON.parseObject(authInfoListener.getAuthInfo());
            if (object != null) {
                return object.getString("utdId");
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public void a(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull String str4, @NonNull String str5, MeshConfigCallback<ServerConfirmation> meshConfigCallback) {
        String str6 = f1610a;
        a.a.a.a.b.m.a.a(str6, "provisionConfirm called...");
        if (this.f1612c == null) {
            a.a.a.a.b.m.a.b(str6, "mMeshConfig is null");
        } else {
            AuthInfoListener authInfoListener = this.f1611b;
            this.f1612c.provisionConfirm(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str2, str, str3, str4, str5, meshConfigCallback);
        }
    }

    public void a(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull String str4, @NonNull String str5, @NonNull String str6, MeshConfigCallback<Boolean> meshConfigCallback) {
        String str7 = f1610a;
        a.a.a.a.b.m.a.a(str7, "provisionAuth called...");
        if (this.f1612c == null) {
            a.a.a.a.b.m.a.b(str7, "mMeshConfig is null");
        } else {
            AuthInfoListener authInfoListener = this.f1611b;
            this.f1612c.provisionAuth(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, str2, str3, str4, str5, str6, meshConfigCallback);
        }
    }

    public void a(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull String str4, MeshConfigCallback<ConfigurationData> meshConfigCallback) {
        String str5 = f1610a;
        a.a.a.a.b.m.a.a(str5, "provisionComplete called...");
        if (this.f1612c == null) {
            a.a.a.a.b.m.a.b(str5, "mMeshConfig is null");
        } else {
            AuthInfoListener authInfoListener = this.f1611b;
            this.f1612c.provisionComplete(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, str2, str3, str4, meshConfigCallback);
        }
    }

    public void a(@NonNull String str, @NonNull String str2, @NonNull String str3, MeshConfigCallback<List<Sigmesh>> meshConfigCallback) {
        String str4 = f1610a;
        a.a.a.a.b.m.a.a(str4, "deviceControl called...");
        if (this.f1612c == null) {
            a.a.a.a.b.m.a.b(str4, "mMeshConfig is null");
        } else {
            AuthInfoListener authInfoListener = this.f1611b;
            this.f1612c.deviceControl(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, str2, str3, meshConfigCallback);
        }
    }

    public void a(@NonNull String str, @NonNull List<DeviceStatus> list, MeshConfigCallback<String> meshConfigCallback) {
        String str2 = f1610a;
        a.a.a.a.b.m.a.a(str2, "reportDevicesStatus called...");
        if (this.f1612c == null) {
            a.a.a.a.b.m.a.b(str2, "mMeshConfig is null");
        } else {
            AuthInfoListener authInfoListener = this.f1611b;
            this.f1612c.reportDevicesStatus(authInfoListener != null ? authInfoListener.getAuthInfo() : "", str, list, meshConfigCallback);
        }
    }

    public void a(String str, IoTGatewayEvent ioTGatewayEvent) {
        MeshConfig meshConfig = this.f1612c;
        if (meshConfig == null) {
            a.a.a.a.b.m.a.b(f1610a, "mMeshConfig is null");
        } else {
            meshConfig.triggerGatewayEventAccs(str, ioTGatewayEvent);
        }
    }

    public void a(String str, boolean z2, MeshConfigCallback<List<Sigmesh>> meshConfigCallback) {
        String str2 = f1610a;
        a.a.a.a.b.m.a.a(str2, "wakeUpDevice called...");
        if (this.f1612c == null) {
            a.a.a.a.b.m.a.b(str2, "wakeUpDevice is null");
            return;
        }
        WakeUpServiceContext wakeUpServiceContext = new WakeUpServiceContext();
        if (z2) {
            wakeUpServiceContext.setPushGenie("true");
        } else {
            wakeUpServiceContext.setPushGenie(RequestConstant.FALSE);
        }
        this.f1612c.wakeUpDevice(str, wakeUpServiceContext, meshConfigCallback);
    }
}
