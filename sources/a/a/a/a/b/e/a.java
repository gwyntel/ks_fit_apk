package a.a.a.a.b.e;

import android.text.TextUtils;
import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.alibaba.android.multiendinonebridge.IoTMultiendInOneBridge;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.facebook.share.internal.MessengerShareContentUtility;
import datasource.bean.SigmeshIotDev;
import datasource.bean.local.DeviceBindItem;
import datasource.bean.local.DeviceBindModel;
import java.util.HashMap;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class a {
    public static void a(String str, String str2, String str3, int i2, String str4) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || i2 == 0) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        SigmeshIotDev sigmeshIotDev = new SigmeshIotDev();
        sigmeshIotDev.setDevId(str2.toLowerCase());
        sigmeshIotDev.setProductKey(str);
        sigmeshIotDev.setUnicastaddress(i2);
        sigmeshIotDev.setDeviceKey(str4.toLowerCase());
        sigmeshIotDev.setHbConfiged(false);
        sigmeshIotDev.setSupport8201(false);
        linkedList.add(sigmeshIotDev);
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("deviceList", (Object) linkedList);
        jSONObject.put(MessengerShareContentUtility.ATTACHMENT_PAYLOAD, (Object) jSONObject2);
        jSONObject.put("commandName", (Object) "SigmeshDevices");
        IoTMultiendInOneBridge.getInstance().onRecvTextMsg(jSONObject.toJSONString());
        IoTMultiendInOneBridge.getInstance().manualSetDevOnlineStatus(str2.toLowerCase(), TgMeshManager.DevOnlineStatus.DEV_ST_ONLINE.getStatus());
    }

    public static void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        DeviceBindModel deviceBindModel = new DeviceBindModel();
        deviceBindModel.setMessageType("deviceBind");
        deviceBindModel.setChangeType(RequestParameters.SUBRESOURCE_DELETE);
        deviceBindModel.setSize(1);
        deviceBindModel.setPageIndex(0);
        deviceBindModel.setPageSize(100);
        LinkedList linkedList = new LinkedList();
        DeviceBindItem deviceBindItem = new DeviceBindItem();
        deviceBindItem.setPlatform("sigmesh");
        deviceBindItem.setSkillId(3404);
        deviceBindItem.setDevType("unknown");
        deviceBindItem.setDevTypeEn("unknown");
        deviceBindItem.setDevId(str.toLowerCase());
        deviceBindItem.setAppKeyIndex(0);
        deviceBindItem.setNetKeyIndex(0);
        HashMap map = new HashMap();
        map.put("powerstate", 0);
        deviceBindItem.setStatus(map);
        linkedList.add(deviceBindItem);
        deviceBindModel.setData(linkedList);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("commandName", (Object) "IotDeviceInfoSync");
        jSONObject.put("commandDomain", (Object) "device");
        jSONObject.put("commandType", (Object) "push");
        jSONObject.put(MessengerShareContentUtility.ATTACHMENT_PAYLOAD, (Object) deviceBindModel);
        IoTMultiendInOneBridge.getInstance().onRecvTextMsg(jSONObject.toJSONString());
    }

    public static void a() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("commandDomain", (Object) "AliGenie.SmartHome");
        jSONObject.put("commandName", (Object) "ControlDevice");
        jSONObject.put(MessengerShareContentUtility.ATTACHMENT_PAYLOAD, (Object) new JSONObject());
        IoTMultiendInOneBridge.getInstance().onRecvTextMsg(jSONObject.toJSONString());
    }
}
