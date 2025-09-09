package com.amap.flutter.location;

import android.content.Context;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import io.flutter.plugin.common.EventChannel;
import java.util.Map;

/* loaded from: classes3.dex */
public class AMapLocationClientImpl implements AMapLocationListener {
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private Context mContext;
    private EventChannel.EventSink mEventSink;
    private String mPluginKey;

    public AMapLocationClientImpl(Context context, String str, EventChannel.EventSink eventSink) {
        this.locationClient = null;
        this.mContext = context;
        this.mPluginKey = str;
        this.mEventSink = eventSink;
        this.locationClient = new AMapLocationClient(context);
    }

    public void destroy() {
        AMapLocationClient aMapLocationClient = this.locationClient;
        if (aMapLocationClient != null) {
            aMapLocationClient.onDestroy();
            this.locationClient = null;
        }
    }

    public void onLocationChanged(AMapLocation aMapLocation) {
        if (this.mEventSink == null) {
            return;
        }
        Map<String, Object> mapBuildLocationResultMap = Utils.buildLocationResultMap(aMapLocation);
        mapBuildLocationResultMap.put("pluginKey", this.mPluginKey);
        this.mEventSink.success(mapBuildLocationResultMap);
    }

    public void setLocationOption(Map map) {
        if (this.locationOption == null) {
            this.locationOption = new AMapLocationClientOption();
        }
        if (map.containsKey("locationInterval")) {
            this.locationOption.setInterval(((Integer) map.get("locationInterval")).longValue());
        }
        if (map.containsKey("needAddress")) {
            this.locationOption.setNeedAddress(((Boolean) map.get("needAddress")).booleanValue());
        }
        if (map.containsKey("locationMode")) {
            try {
                this.locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.values()[((Integer) map.get("locationMode")).intValue()]);
            } catch (Throwable unused) {
            }
        }
        if (map.containsKey("geoLanguage")) {
            this.locationOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.values()[((Integer) map.get("geoLanguage")).intValue()]);
        }
        if (map.containsKey("onceLocation")) {
            this.locationOption.setOnceLocation(((Boolean) map.get("onceLocation")).booleanValue());
        }
        AMapLocationClient aMapLocationClient = this.locationClient;
        if (aMapLocationClient != null) {
            aMapLocationClient.setLocationOption(this.locationOption);
        }
    }

    public void startLocation() {
        if (this.locationClient == null) {
            this.locationClient = new AMapLocationClient(this.mContext);
        }
        AMapLocationClientOption aMapLocationClientOption = this.locationOption;
        if (aMapLocationClientOption != null) {
            this.locationClient.setLocationOption(aMapLocationClientOption);
        }
        this.locationClient.setLocationListener(this);
        this.locationClient.startLocation();
    }

    public void stopLocation() {
        AMapLocationClient aMapLocationClient = this.locationClient;
        if (aMapLocationClient != null) {
            aMapLocationClient.stopLocation();
            this.locationClient.onDestroy();
            this.locationClient = null;
        }
    }
}
