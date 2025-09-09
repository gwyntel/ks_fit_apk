package com.kingsmith.aliiot;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.api.discovery.IOnDeviceTokenGetListener;
import com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.aliyun.iot.aep.sdk.threadpool.ThreadPool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class DeviceBindBusiness {
    private static final int BIND_STATUS_DOING = 11;
    private static final int BIND_STATUS_FAILED = 13;
    private static final int BIND_STATUS_NONE = 10;
    private static final int BIND_STATUS_SUCCESS = 12;
    private static final int QUREY_STATUS_DOING = 1;
    private static final int QUREY_STATUS_FAILED = 3;
    private static final int QUREY_STATUS_NONE = 0;
    private static final int QUREY_STATUS_SUCCESS = 2;
    private static final String TAG = "DeviceBindBusiness";
    private Context context;
    private String groupId;
    private Device mDevice;
    private OnBindDeviceCompletedListener onBindDeviceCompletedListener;
    private int qureyStatus = 0;
    private int bindStatus = 10;

    public DeviceBindBusiness(Context context) {
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindDeviceInternal(final OnBindDeviceCompletedListener onBindDeviceCompletedListener) {
        String pathByDevice = getPathByDevice(this.mDevice);
        if (TextUtils.isEmpty(pathByDevice)) {
            onBindDeviceCompletedListener.onFailed(new UnsupportedOperationException("ble bind is not support at present@" + this.mDevice.toString()));
        }
        HashMap map = new HashMap();
        map.put("productKey", this.mDevice.pk);
        map.put("deviceName", this.mDevice.dn);
        if (!TextUtils.isEmpty(this.mDevice.token)) {
            map.put("token", this.mDevice.token);
        }
        if (!TextUtils.isEmpty(this.groupId)) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(this.groupId);
            map.put("groupIds", arrayList);
        }
        if (onBindDeviceCompletedListener != null) {
            onBindDeviceCompletedListener.printLog("bindDeviceInternal request path:" + pathByDevice + "setAuthType iotAuth params: " + map);
        }
        new IoTAPIClientFactory().getClient().send(new IoTRequestBuilder().setPath(pathByDevice).setApiVersion("1.0.2").setAuthType(AlinkConstants.KEY_IOT_AUTH).setParams(map).build(), new IoTCallback() { // from class: com.kingsmith.aliiot.DeviceBindBusiness.2
            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onFailure(IoTRequest ioTRequest, final Exception exc) {
                ALog.d(DeviceBindBusiness.TAG, "onFailure");
                DeviceBindBusiness.this.bindStatus = 13;
                ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.kingsmith.aliiot.DeviceBindBusiness.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            onBindDeviceCompletedListener.onFailed(exc);
                        } catch (Exception e2) {
                            ALog.e(DeviceBindBusiness.TAG, "exception happen when call listener.onFailed", e2);
                            e2.printStackTrace();
                        }
                    }
                });
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onResponse(IoTRequest ioTRequest, final IoTResponse ioTResponse) {
                ALog.d(DeviceBindBusiness.TAG, "onResponse bindWithWiFi ok");
                if (200 != ioTResponse.getCode() || !(ioTResponse.getData() instanceof String)) {
                    DeviceBindBusiness.this.bindStatus = 13;
                    ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.kingsmith.aliiot.DeviceBindBusiness.2.2
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                onBindDeviceCompletedListener.onFailed(ioTResponse.getCode(), ioTResponse.getMessage(), ioTResponse.getLocalizedMsg());
                            } catch (Exception e2) {
                                ALog.e(DeviceBindBusiness.TAG, "exception happen when call listener.onFailed", e2);
                                e2.printStackTrace();
                            }
                        }
                    });
                } else {
                    final String str = (String) ioTResponse.getData();
                    DeviceBindBusiness.this.bindStatus = 12;
                    ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.kingsmith.aliiot.DeviceBindBusiness.2.3
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                onBindDeviceCompletedListener.onSuccess(str);
                            } catch (Exception e2) {
                                ALog.e(DeviceBindBusiness.TAG, "exception happen when call listener.onSuccess", e2);
                                e2.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindWithWiFi(final Device device) {
        ALog.d(TAG, "bindWithWiFi");
        this.qureyStatus = 1;
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        LocalDeviceMgr.getInstance().getDeviceToken(this.context, device.pk, device.dn, 60000, 2000, new IOnDeviceTokenGetListener() { // from class: com.kingsmith.aliiot.DeviceBindBusiness.3
            @Override // com.aliyun.alink.business.devicecenter.api.discovery.IOnDeviceTokenGetListener
            public void onFail(final String str) {
                ALog.e(DeviceBindBusiness.TAG, "getDeviceToken onFail s = " + str);
                DeviceBindBusiness.this.qureyStatus = 3;
                DeviceBindBusiness.this.bindStatus = 13;
                if (atomicBoolean.get()) {
                    return;
                }
                atomicBoolean.set(true);
                ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.kingsmith.aliiot.DeviceBindBusiness.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            if (DeviceBindBusiness.this.onBindDeviceCompletedListener != null) {
                                DeviceBindBusiness.this.onBindDeviceCompletedListener.onFailed(new RuntimeException(str));
                            }
                        } catch (Exception e2) {
                            ALog.e(DeviceBindBusiness.TAG, "exception happen when call listener.onFailed", e2);
                            e2.printStackTrace();
                        }
                        DeviceBindBusiness.this.onBindDeviceCompletedListener = null;
                    }
                });
            }

            @Override // com.aliyun.alink.business.devicecenter.api.discovery.IOnDeviceTokenGetListener
            public void onSuccess(String str) {
                ALog.d(DeviceBindBusiness.TAG, "getDeviceToken onSuccess token = " + str);
                if (atomicBoolean.get()) {
                    return;
                }
                atomicBoolean.set(true);
                DeviceBindBusiness.this.qureyStatus = 2;
                Device device2 = new Device();
                Device device3 = device;
                device2.pk = device3.pk;
                device2.dn = device3.dn;
                device2.netType = device3.netType;
                device2.token = str;
                DeviceBindBusiness.this.mDevice = device2;
                if (DeviceBindBusiness.this.bindStatus == 11) {
                    DeviceBindBusiness deviceBindBusiness = DeviceBindBusiness.this;
                    deviceBindBusiness.bindDeviceInternal(deviceBindBusiness.onBindDeviceCompletedListener);
                }
            }
        });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:23:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getPathByDevice(com.kingsmith.aliiot.Device r6) {
        /*
            r5 = this;
            java.lang.String r6 = r6.netType
            java.lang.String r6 = r6.toUpperCase()
            int r0 = r6.hashCode()
            r1 = 1
            r2 = 2
            r3 = 3
            r4 = 4
            switch(r0) {
                case -2125520807: goto L44;
                case -1995574700: goto L3a;
                case -1622758932: goto L30;
                case -1176552596: goto L26;
                case 783500718: goto L1c;
                case 2103711895: goto L12;
                default: goto L11;
            }
        L11:
            goto L4e
        L12:
            java.lang.String r0 = "NET_WIFI"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L4e
            r6 = 0
            goto L4f
        L1c:
            java.lang.String r0 = "NET_OTHER"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L4e
            r6 = r4
            goto L4f
        L26:
            java.lang.String r0 = "NET_ZIGBEE"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L4e
            r6 = r3
            goto L4f
        L30:
            java.lang.String r0 = "NET_CELLULAR"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L4e
            r6 = r2
            goto L4f
        L3a:
            java.lang.String r0 = "NET_BT"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L4e
            r6 = 5
            goto L4f
        L44:
            java.lang.String r0 = "NET_ETHERNET"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L4e
            r6 = r1
            goto L4f
        L4e:
            r6 = -1
        L4f:
            if (r6 == 0) goto L61
            if (r6 == r1) goto L61
            if (r6 == r2) goto L5e
            if (r6 == r3) goto L5b
            if (r6 == r4) goto L5b
            r6 = 0
            return r6
        L5b:
            java.lang.String r6 = "/awss/subdevice/bind"
            return r6
        L5e:
            java.lang.String r6 = "/awss/gprs/user/bind"
            return r6
        L61:
            java.lang.String r6 = "/awss/enrollee/user/bind"
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.aliiot.DeviceBindBusiness.getPathByDevice(com.kingsmith.aliiot.Device):java.lang.String");
    }

    public void bindDevice(Device device, OnBindDeviceCompletedListener onBindDeviceCompletedListener) {
        if (this.bindStatus == 11) {
            onBindDeviceCompletedListener.onFailed(new IllegalStateException("bindStatus = BIND_STATUS_DOING"));
            return;
        }
        if (onBindDeviceCompletedListener != null) {
            onBindDeviceCompletedListener.printLog("bindDevice qureyStatus:" + this.qureyStatus);
        }
        this.bindStatus = 11;
        int i2 = this.qureyStatus;
        if (i2 == 2) {
            bindDeviceInternal(onBindDeviceCompletedListener);
        } else if (i2 == 1) {
            this.onBindDeviceCompletedListener = onBindDeviceCompletedListener;
        } else {
            this.onBindDeviceCompletedListener = onBindDeviceCompletedListener;
            queryProductInfo(device);
        }
    }

    public void queryProductInfo(final Device device) {
        if (device == null) {
            throw new IllegalArgumentException("device can not be null");
        }
        this.qureyStatus = 1;
        IoTRequest ioTRequestBuild = new IoTRequestBuilder().setPath("/thing/detailInfo/queryProductInfoByProductKey").setApiVersion("1.1.1").addParam("productKey", device.pk).setAuthType(AlinkConstants.KEY_IOT_AUTH).build();
        OnBindDeviceCompletedListener onBindDeviceCompletedListener = this.onBindDeviceCompletedListener;
        if (onBindDeviceCompletedListener != null) {
            onBindDeviceCompletedListener.printLog("queryProductInfo request path:/thing/detailInfo/queryProductInfoByProductKeyproductKey: " + device.pk);
        }
        new IoTAPIClientFactory().getClient().send(ioTRequestBuild, new IoTCallback() { // from class: com.kingsmith.aliiot.DeviceBindBusiness.1
            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onFailure(IoTRequest ioTRequest, final Exception exc) {
                DeviceBindBusiness.this.qureyStatus = 3;
                DeviceBindBusiness.this.bindStatus = 13;
                ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.kingsmith.aliiot.DeviceBindBusiness.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            if (DeviceBindBusiness.this.onBindDeviceCompletedListener != null) {
                                DeviceBindBusiness.this.onBindDeviceCompletedListener.onFailed(exc);
                            }
                        } catch (Exception e2) {
                            ALog.e(DeviceBindBusiness.TAG, "exception happen when call listener.onFailed", e2);
                            e2.printStackTrace();
                        }
                        DeviceBindBusiness.this.onBindDeviceCompletedListener = null;
                    }
                });
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onResponse(IoTRequest ioTRequest, final IoTResponse ioTResponse) {
                if (200 != ioTResponse.getCode() || !(ioTResponse.getData() instanceof JSONObject)) {
                    DeviceBindBusiness.this.qureyStatus = 3;
                    DeviceBindBusiness.this.bindStatus = 13;
                    ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.kingsmith.aliiot.DeviceBindBusiness.1.2
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (DeviceBindBusiness.this.onBindDeviceCompletedListener != null) {
                                    DeviceBindBusiness.this.onBindDeviceCompletedListener.onFailed(ioTResponse.getCode(), ioTResponse.getMessage(), ioTResponse.getLocalizedMsg());
                                }
                            } catch (Exception e2) {
                                ALog.e(DeviceBindBusiness.TAG, "exception happen when call listener.onFailed", e2);
                                e2.printStackTrace();
                            }
                            DeviceBindBusiness.this.onBindDeviceCompletedListener = null;
                        }
                    });
                    return;
                }
                String strOptString = ((JSONObject) ioTResponse.getData()).optString("netType");
                device.netType = strOptString;
                if (DeviceBindBusiness.this.onBindDeviceCompletedListener != null) {
                    DeviceBindBusiness.this.onBindDeviceCompletedListener.printLog("queryProductInfo 查询成功 netType:" + device.netType);
                }
                if ("NET_WIFI".equalsIgnoreCase(strOptString) || "NET_ETHERNET".equalsIgnoreCase(strOptString)) {
                    DeviceBindBusiness.this.bindWithWiFi(device);
                    return;
                }
                if (!"NET_CELLULAR".equalsIgnoreCase(strOptString) && !"NET_ZIGBEE".equalsIgnoreCase(strOptString) && !"NET_OTHER".equalsIgnoreCase(strOptString) && !"NET_BT".equalsIgnoreCase(strOptString)) {
                    DeviceBindBusiness.this.bindStatus = 13;
                    ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.kingsmith.aliiot.DeviceBindBusiness.1.3
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (DeviceBindBusiness.this.onBindDeviceCompletedListener != null) {
                                    DeviceBindBusiness.this.onBindDeviceCompletedListener.onFailed(new IllegalArgumentException("unsupported net type"));
                                }
                            } catch (Exception e2) {
                                ALog.e(DeviceBindBusiness.TAG, "exception happen when call listener.onFailed", e2);
                                e2.printStackTrace();
                            }
                            DeviceBindBusiness.this.onBindDeviceCompletedListener = null;
                        }
                    });
                    return;
                }
                DeviceBindBusiness.this.qureyStatus = 2;
                Device device2 = new Device();
                Device device3 = device;
                device2.pk = device3.pk;
                device2.dn = device3.dn;
                device2.netType = device3.netType;
                DeviceBindBusiness.this.mDevice = device2;
                if (DeviceBindBusiness.this.bindStatus == 11) {
                    DeviceBindBusiness deviceBindBusiness = DeviceBindBusiness.this;
                    deviceBindBusiness.bindDeviceInternal(deviceBindBusiness.onBindDeviceCompletedListener);
                }
            }
        });
    }

    public DeviceBindBusiness setGroupId(String str) {
        this.groupId = str;
        return this;
    }
}
