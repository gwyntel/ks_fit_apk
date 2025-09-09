package com.aliyun.alink.business.devicecenter.cache;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.channel.coap.request.CoapRequestPayload;
import com.aliyun.alink.business.devicecenter.channel.coap.response.DevicePayload;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.DeviceInfoUtils;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPRequest;

/* loaded from: classes2.dex */
public class ProvisionDeviceInfoCache {

    /* renamed from: a, reason: collision with root package name */
    public final Object f10219a;

    /* renamed from: b, reason: collision with root package name */
    public DevicePayload f10220b;

    /* renamed from: c, reason: collision with root package name */
    public long f10221c;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final ProvisionDeviceInfoCache f10223a = new ProvisionDeviceInfoCache();
    }

    public static ProvisionDeviceInfoCache getInstance() {
        return SingletonHolder.f10223a;
    }

    public void clearCache() {
        ALog.d("ProvisionDeviceInfoCache", "clearCache");
        synchronized (this.f10219a) {
            this.f10220b = null;
            this.f10221c = -1L;
        }
    }

    public DevicePayload getCache() {
        ALog.d("ProvisionDeviceInfoCache", "getCache");
        synchronized (this.f10219a) {
            try {
                DevicePayload devicePayload = this.f10220b;
                if (devicePayload == null) {
                    ALog.w("ProvisionDeviceInfoCache", "cache model empty.");
                    return null;
                }
                if (!TextUtils.isEmpty(devicePayload.productKey) && !TextUtils.isEmpty(this.f10220b.deviceName)) {
                    if (TextUtils.isEmpty(this.f10220b.token)) {
                        ALog.w("ProvisionDeviceInfoCache", "cache model token empty.");
                        return this.f10220b;
                    }
                    if (System.currentTimeMillis() - this.f10221c > this.f10220b.getRemainTime()) {
                        ALog.w("ProvisionDeviceInfoCache", "cache model expired.");
                        this.f10220b.remainTime = null;
                        this.f10221c = -1L;
                        this.f10220b = null;
                    }
                    return this.f10220b;
                }
                ALog.w("ProvisionDeviceInfoCache", "cache model pk or dn empty.");
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean handleNotifyCache(AlcsCoAPRequest alcsCoAPRequest) {
        DevicePayload devicePayload;
        ALog.d("ProvisionDeviceInfoCache", "handleNotifyCache");
        try {
            devicePayload = this.f10220b;
        } catch (Exception e2) {
            ALog.w("ProvisionDeviceInfoCache", "handleNotifyCache exception e=" + e2);
        }
        if (devicePayload != null && !TextUtils.isEmpty(devicePayload.productKey) && !TextUtils.isEmpty(this.f10220b.deviceName)) {
            CoapRequestPayload coapRequestPayload = (CoapRequestPayload) JSON.parseObject(alcsCoAPRequest.getPayloadString(), new TypeReference<CoapRequestPayload<DevicePayload>>() { // from class: com.aliyun.alink.business.devicecenter.cache.ProvisionDeviceInfoCache.1
            }.getType(), new Feature[0]);
            DevicePayload devicePayload2 = (DevicePayload) coapRequestPayload.params;
            if (AlinkConstants.COAP_METHOD_DEVICE_INFO_NOTIFY.equals(coapRequestPayload.method)) {
                if (this.f10220b.productKey.equals(devicePayload2.productKey) && this.f10220b.deviceName.equals(devicePayload2.deviceName)) {
                    ALog.d("ProvisionDeviceInfoCache", "ack device info notify.");
                    updateCache(devicePayload2);
                    return true;
                }
            } else if (AlinkConstants.COAP_METHOD_NOTIFY_PROVISION_SUCCESS.equals(coapRequestPayload.method) && this.f10220b.productKey.equals(devicePayload2.productKey) && this.f10220b.deviceName.equals(devicePayload2.deviceName)) {
                ALog.d("ProvisionDeviceInfoCache", "ack connect ap.");
                return true;
            }
            return false;
        }
        this.f10220b = null;
        ALog.d("ProvisionDeviceInfoCache", "handleNotifyCache cache model is empty, return.");
        return false;
    }

    public void updateCache(DevicePayload devicePayload) {
        ALog.d("ProvisionDeviceInfoCache", "updateCache expiringModel=" + devicePayload);
        if (devicePayload == null) {
            ALog.d("ProvisionDeviceInfoCache", "updateCache expiringModel invalid PK|DN|OJ empty.");
            return;
        }
        if (!DeviceInfoUtils.isDevicePayloadValid(devicePayload)) {
            ALog.d("ProvisionDeviceInfoCache", "updateCache expiringModel invalid expiringModel = " + devicePayload);
            return;
        }
        synchronized (this.f10219a) {
            try {
                DevicePayload devicePayload2 = this.f10220b;
                if (devicePayload2 == null) {
                    this.f10220b = new DevicePayload(devicePayload);
                    if (!TextUtils.isEmpty(devicePayload.token)) {
                        this.f10221c = System.currentTimeMillis();
                    }
                } else if (devicePayload.productKey.equals(devicePayload2.productKey) && devicePayload.deviceName.equals(this.f10220b.deviceName)) {
                    if (TextUtils.isEmpty(devicePayload.token)) {
                        this.f10220b.remainTime = null;
                        this.f10221c = -1L;
                    } else {
                        DevicePayload devicePayload3 = this.f10220b;
                        devicePayload3.token = devicePayload.token;
                        devicePayload3.remainTime = devicePayload.remainTime;
                        this.f10221c = System.currentTimeMillis();
                    }
                }
                StringBuilder sb = new StringBuilder();
                sb.append("updateCache success. cache model=");
                sb.append(this.f10220b);
                ALog.d("ProvisionDeviceInfoCache", sb.toString());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ProvisionDeviceInfoCache() {
        this.f10219a = new Object();
        this.f10220b = null;
        this.f10221c = -1L;
    }
}
