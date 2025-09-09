package a.a.a.a.b.m;

import com.alibaba.ailabs.iot.mesh.ut.IUserTracker;
import com.alibaba.ailabs.iot.mesh.utils.AliMeshUUIDParserUtil;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.WifiProvisionUtConst;
import com.taobao.accs.utl.BaseMonitor;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static final String f1585a = "b";

    public static void a(String str, String str2, String str3, String str4, long j2, int i2, String str5) {
        try {
            HashMap map = new HashMap(4);
            map.put("step", "error");
            map.put("channel", str2);
            map.put("productKey", str3);
            map.put("errorCode", Integer.valueOf(i2));
            map.put(WifiProvisionUtConst.KEY_ERROR_MSG, str5);
            map.put("costTime", Long.valueOf(j2));
            HashMap map2 = new HashMap(2);
            map2.put(WifiProvisionUtConst.KEY_BUSIZ_INFO, JSON.toJSONString(map));
            a(str, BaseMonitor.ALARM_POINT_CONNECT, map2, str4);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void a(String str, String str2, String str3, boolean z2, byte[] bArr, String str4) {
        try {
            HashMap map = new HashMap(4);
            map.put("step", "start");
            map.put("channel", str2);
            map.put("productKey", str3);
            if (bArr != null) {
                map.put(DeviceCommonConstants.KEY_DEVICE_ID, Utils.bytes2HexString(bArr));
                map.put("type", AliMeshUUIDParserUtil.getAliMeshSolutionTypeFromUUID(bArr));
            }
            map.put("batch_mode", Boolean.valueOf(z2));
            map.put("type", AliMeshUUIDParserUtil.getAliMeshSolutionTypeFromUUID(bArr));
            HashMap map2 = new HashMap(2);
            map2.put(WifiProvisionUtConst.KEY_BUSIZ_INFO, JSON.toJSONString(map));
            a(str, WifiProvisionUtConst.ARG_CONNECTION, map2, str4);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void a(String str, String str2, String str3, boolean z2, byte[] bArr, String str4, long j2) {
        try {
            HashMap map = new HashMap(4);
            map.put("step", "success");
            map.put("channel", str2);
            map.put("productKey", str3);
            if (bArr != null) {
                map.put(DeviceCommonConstants.KEY_DEVICE_ID, Utils.bytes2HexString(bArr));
                map.put("type", AliMeshUUIDParserUtil.getAliMeshSolutionTypeFromUUID(bArr));
            }
            map.put("batch_mode", Boolean.valueOf(z2));
            map.put("errorCode", 0);
            map.put(WifiProvisionUtConst.KEY_ERROR_MSG, "");
            map.put("costTime", Long.valueOf(j2));
            HashMap map2 = new HashMap(2);
            map2.put(WifiProvisionUtConst.KEY_BUSIZ_INFO, JSON.toJSONString(map));
            a(str, WifiProvisionUtConst.ARG_CONNECTION, map2, str4);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void a(String str, String str2, String str3, boolean z2, byte[] bArr, String str4, long j2, int i2, String str5, String str6, String str7) {
        try {
            HashMap map = new HashMap(4);
            map.put("step", "error");
            map.put("channel", str2);
            map.put("productKey", str3);
            if (bArr != null) {
                map.put(DeviceCommonConstants.KEY_DEVICE_ID, Utils.bytes2HexString(bArr));
                map.put("type", AliMeshUUIDParserUtil.getAliMeshSolutionTypeFromUUID(bArr));
            }
            map.put("batch_mode", Boolean.valueOf(z2));
            map.put("errorCode", Integer.valueOf(i2));
            map.put(WifiProvisionUtConst.KEY_ERROR_MSG, str5);
            map.put("costTime", Long.valueOf(j2));
            map.put("subErrorCode", str6);
            map.put("subErrorMsg", str7);
            HashMap map2 = new HashMap(2);
            map2.put(WifiProvisionUtConst.KEY_BUSIZ_INFO, JSON.toJSONString(map));
            a(str, WifiProvisionUtConst.ARG_CONNECTION, map2, str4);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void a(String str, String str2, String str3, boolean z2, byte[] bArr, String str4, long j2, int i2, String str5) {
        try {
            HashMap map = new HashMap(4);
            map.put("step", "error");
            map.put("channel", str2);
            map.put("productKey", str3);
            if (bArr != null) {
                map.put(DeviceCommonConstants.KEY_DEVICE_ID, Utils.bytes2HexString(bArr));
                map.put("type", AliMeshUUIDParserUtil.getAliMeshSolutionTypeFromUUID(bArr));
            }
            map.put("batch_mode", Boolean.valueOf(z2));
            map.put("errorCode", Integer.valueOf(i2));
            map.put(WifiProvisionUtConst.KEY_ERROR_MSG, str5);
            map.put("costTime", Long.valueOf(j2));
            HashMap map2 = new HashMap(2);
            map2.put(WifiProvisionUtConst.KEY_BUSIZ_INFO, JSON.toJSONString(map));
            a(str, WifiProvisionUtConst.ARG_CONNECTION, map2, str4);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void a(String str, String str2, Map<String, String> map, String str3) {
        if (map == null) {
            map = new HashMap<>(8);
        }
        IUserTracker iUserTracker = (IUserTracker) d.b.a().a(IUserTracker.class);
        if (iUserTracker == null) {
            a.d(f1585a, "Null IUserTracker implement");
        } else {
            iUserTracker.customHit(str, str2, map, str3);
        }
    }
}
