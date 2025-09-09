package com.amap.flutter.location;

import com.amap.api.location.AMapLocation;
import com.kingsmith.miot.KsProperty;
import java.util.LinkedHashMap;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes3.dex */
public class Utils {
    public static Map<String, Object> buildLocationResultMap(AMapLocation aMapLocation) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("callbackTime", formatUTC(System.currentTimeMillis(), null));
        if (aMapLocation == null) {
            linkedHashMap.put("errorCode", -1);
            linkedHashMap.put("errorInfo", "location is null");
        } else if (aMapLocation.getErrorCode() == 0) {
            linkedHashMap.put("locationTime", formatUTC(aMapLocation.getTime(), null));
            linkedHashMap.put("locationType", Integer.valueOf(aMapLocation.getLocationType()));
            linkedHashMap.put("latitude", Double.valueOf(aMapLocation.getLatitude()));
            linkedHashMap.put("longitude", Double.valueOf(aMapLocation.getLongitude()));
            linkedHashMap.put("accuracy", Float.valueOf(aMapLocation.getAccuracy()));
            linkedHashMap.put("altitude", Double.valueOf(aMapLocation.getAltitude()));
            linkedHashMap.put("bearing", Float.valueOf(aMapLocation.getBearing()));
            linkedHashMap.put(KsProperty.Speed, Float.valueOf(aMapLocation.getSpeed()));
            linkedHashMap.put("country", aMapLocation.getCountry());
            linkedHashMap.put("province", aMapLocation.getProvince());
            linkedHashMap.put("city", aMapLocation.getCity());
            linkedHashMap.put("district", aMapLocation.getDistrict());
            linkedHashMap.put("street", aMapLocation.getStreet());
            linkedHashMap.put("streetNumber", aMapLocation.getStreetNum());
            linkedHashMap.put("cityCode", aMapLocation.getCityCode());
            linkedHashMap.put("adCode", aMapLocation.getAdCode());
            linkedHashMap.put("address", aMapLocation.getAddress());
            linkedHashMap.put("description", aMapLocation.getDescription());
        } else {
            linkedHashMap.put("errorCode", Integer.valueOf(aMapLocation.getErrorCode()));
            linkedHashMap.put("errorInfo", aMapLocation.getErrorInfo() + MqttTopic.MULTI_LEVEL_WILDCARD + aMapLocation.getLocationDetail());
        }
        return linkedHashMap;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0018 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String formatUTC(long r3, java.lang.String r5) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            if (r0 == 0) goto L8
            java.lang.String r5 = "yyyy-MM-dd HH:mm:ss"
        L8:
            r0 = 0
            java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat     // Catch: java.lang.Throwable -> L15
            java.util.Locale r2 = java.util.Locale.CHINA     // Catch: java.lang.Throwable -> L15
            r1.<init>(r5, r2)     // Catch: java.lang.Throwable -> L15
            r1.applyPattern(r5)     // Catch: java.lang.Throwable -> L14
            goto L16
        L14:
            r0 = r1
        L15:
            r1 = r0
        L16:
            if (r1 != 0) goto L1b
            java.lang.String r3 = "NULL"
            goto L23
        L1b:
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            java.lang.String r3 = r1.format(r3)
        L23:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.flutter.location.Utils.formatUTC(long, java.lang.String):java.lang.String");
    }
}
