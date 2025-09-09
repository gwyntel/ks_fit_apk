package a.a.a.a.b.c;

import a.a.a.a.b.na;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.alibaba.ailabs.iot.mesh.callback.DeviceOnlineStatusListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.android.multiendinonebridge.IUpstreamProxy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import datasource.bean.ConfigurationData;
import datasource.bean.IoTGatewayEvent;
import datasource.bean.Sigmesh;
import datasource.bean.SubscribeGroupAddr;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes.dex */
public class c implements IUpstreamProxy {

    /* renamed from: a, reason: collision with root package name */
    public static final String f1324a = "" + c.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    public String f1325b;

    /* renamed from: c, reason: collision with root package name */
    public na f1326c;

    /* renamed from: e, reason: collision with root package name */
    public Map<String, b> f1328e = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    public List<DeviceOnlineStatusListener> f1327d = new CopyOnWriteArrayList();

    private final class a implements b {
        public a() {
        }

        @Override // a.a.a.a.b.c.c.b
        public void handle(String str, String str2) {
            JSONObject object = JSON.parseObject(str2);
            if (a.a.a.a.b.d.a.f1334a) {
                object.put("type", (Object) "TmallApp");
                object.put("connectType", (Object) "app-accs");
            } else {
                object.put("type", (Object) "IotxApp");
                object.put("connectType", (Object) "iotx-mqtt");
                object.put(DeviceCommonConstants.KEY_DEVICE_ID, (Object) c.this.f1325b);
            }
            IoTGatewayEvent ioTGatewayEvent = new IoTGatewayEvent();
            IoTGatewayEvent.EventBean eventBean = new IoTGatewayEvent.EventBean();
            eventBean.setName(str);
            eventBean.setNamespace("AliGenie.SmartHome");
            eventBean.setPayload(object);
            ioTGatewayEvent.setEvent(eventBean);
            if (c.this.f1326c != null) {
                c.this.f1326c.a(str, ioTGatewayEvent);
            }
        }

        public /* synthetic */ a(c cVar, a.a.a.a.b.c.a aVar) {
            this();
        }
    }

    private interface b {
        void handle(String str, String str2);
    }

    /* renamed from: a.a.a.a.b.c.c$c, reason: collision with other inner class name */
    private class C0000c implements b {
        public C0000c() {
        }

        @Override // a.a.a.a.b.c.c.b
        public void handle(String str, String str2) {
            TgMeshManager.DevOnlineStatus devOnlineStatus;
            JSONObject object = JSON.parseObject(str2);
            String string = object.getString(AlinkConstants.KEY_SUB_DEVICE_ID);
            boolean zBooleanValue = object.getBoolean("isOnline").booleanValue();
            for (DeviceOnlineStatusListener deviceOnlineStatusListener : c.this.f1327d) {
                if (zBooleanValue) {
                    try {
                        devOnlineStatus = TgMeshManager.DevOnlineStatus.DEV_ST_ONLINE;
                    } catch (Exception e2) {
                        a.a.a.a.b.m.a.b(c.f1324a, e2.toString());
                    }
                } else {
                    devOnlineStatus = TgMeshManager.DevOnlineStatus.DEV_ST_OFFLINE;
                }
                deviceOnlineStatusListener.onOnlineStatusChange(string, devOnlineStatus);
            }
        }

        public /* synthetic */ C0000c(c cVar, a.a.a.a.b.c.a aVar) {
            this();
        }
    }

    public c(String str, na naVar) {
        this.f1325b = str;
        this.f1326c = naVar;
        a();
    }

    @Override // com.alibaba.android.multiendinonebridge.IUpstreamProxy
    public void invokeEventMethod(String str, String str2) {
        b bVar = this.f1328e.get(str);
        if (bVar == null) {
            a.a.a.a.b.m.a.d(f1324a, String.format("Ignore unsupported event: %s, payload: %s", str, str2));
        } else {
            a.a.a.a.b.m.a.c(f1324a, String.format(Locale.getDefault(), "Handle event: %s, payload: %s", str, str2));
            bVar.handle(str, str2);
        }
    }

    @Override // com.alibaba.android.multiendinonebridge.IUpstreamProxy
    public void sendIoTCommand(int i2, String str) throws InterruptedException {
        JSONObject object = JSON.parseObject(str);
        if (i2 == 0) {
            JSONArray jSONArray = object.getJSONArray("sigmesh");
            if (jSONArray == null || jSONArray.size() == 0) {
                a.a.a.a.b.m.a.d(f1324a, "Empty SIGMesh data");
                return;
            }
            Sigmesh sigmesh = (Sigmesh) jSONArray.getJSONObject(0).toJavaObject(Sigmesh.class);
            if (sigmesh == null || sigmesh.getAction() == null || sigmesh.getDevice() == null) {
                a.a.a.a.b.m.a.d(f1324a, "Illegal SIGMesh data");
                return;
            }
            int destAddr = sigmesh.getDevice().getDestAddr();
            int appKeyIndex = sigmesh.getDevice().getAppKeyIndex();
            int netKeyIndex = sigmesh.getDevice().getNetKeyIndex();
            if (sigmesh.getAction().getOpcode() != null) {
                TgMeshManager.getInstance().sendMessge(destAddr, appKeyIndex, netKeyIndex, Utils.byteArray2Int(Utils.getOpCodeBytes(Integer.parseInt(sigmesh.getAction().getOpcode(), 16))), !TextUtils.isEmpty(sigmesh.getAction().getParameters()) ? MeshParserUtils.toByteArray(sigmesh.getAction().getParameters()) : new byte[0], new a.a.a.a.b.c.a(this));
                return;
            }
            return;
        }
        if (i2 != 12) {
            return;
        }
        JSONObject jSONObject = object.getJSONObject("configuration");
        if (jSONObject == null) {
            a.a.a.a.b.m.a.d(f1324a, "Empty Configuration data");
            return;
        }
        ConfigurationData configurationData = (ConfigurationData) jSONObject.toJavaObject(ConfigurationData.class);
        if (configurationData == null || configurationData.getPrimaryUnicastAddress() == null || configurationData.getConfigResultMap() == null) {
            a.a.a.a.b.m.a.d(f1324a, "Illegal Configuration data");
            return;
        }
        List<SubscribeGroupAddr> configModelSubscription = configurationData.getConfigResultMap().getConfigModelSubscription();
        if (configModelSubscription == null || configModelSubscription.size() <= 0) {
            return;
        }
        for (SubscribeGroupAddr subscribeGroupAddr : configModelSubscription) {
            if (subscribeGroupAddr != null) {
                String str2 = f1324a;
                a.a.a.a.b.m.a.a(str2, "Config Subscription Add");
                if (TextUtils.isEmpty(configurationData.getDeviceKey())) {
                    a.a.a.a.b.m.a.d(str2, String.format(Locale.getDefault(), "Device(%d) key can not be empty, maybe server error", (Integer) configurationData.getPrimaryUnicastAddress()));
                } else {
                    TgMeshManager.getInstance().configModelSubscriptionAdd(configurationData.getDeviceKey(), ((Integer) configurationData.getPrimaryUnicastAddress()).intValue(), subscribeGroupAddr.getModelElementAddr().intValue(), subscribeGroupAddr.getGroupAddr().intValue(), subscribeGroupAddr.getModelId().intValue(), new a.a.a.a.b.c.b(this));
                }
            }
        }
    }

    public void a(String str) {
        this.f1325b = str;
    }

    public void b(DeviceOnlineStatusListener deviceOnlineStatusListener) {
        if (deviceOnlineStatusListener != null) {
            this.f1327d.remove(deviceOnlineStatusListener);
        }
    }

    public final void a() {
        a.a.a.a.b.c.a aVar = null;
        this.f1328e.put("DeviceRegister", new a(this, aVar));
        this.f1328e.put("ReportOnlineStatus", new C0000c(this, aVar));
    }

    public void a(DeviceOnlineStatusListener deviceOnlineStatusListener) {
        if (this.f1327d.contains(deviceOnlineStatusListener)) {
            return;
        }
        this.f1327d.add(deviceOnlineStatusListener);
    }

    public c(na naVar) {
        this.f1326c = naVar;
        a();
    }
}
