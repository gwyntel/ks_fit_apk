package com.aliyun.alink.business.devicecenter.discover.cloud;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.discovery.DiscoveryType;
import com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.cache.CacheCenter;
import com.aliyun.alink.business.devicecenter.cache.CacheType;
import com.aliyun.alink.business.devicecenter.channel.http.CloudResponse;
import com.aliyun.alink.business.devicecenter.channel.http.TransitoryClient;
import com.aliyun.alink.business.devicecenter.config.DeviceCenterBiz;
import com.aliyun.alink.business.devicecenter.discover.CloudEnrolleeDeviceHelper;
import com.aliyun.alink.business.devicecenter.discover.CloudEnrolleeDeviceWrapper;
import com.aliyun.alink.business.devicecenter.discover.annotation.DeviceDiscovery;
import com.aliyun.alink.business.devicecenter.discover.base.DiscoverChainBase;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.ThreadPool;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@DeviceDiscovery(discoveryType = {DiscoveryType.CLOUD_ENROLLEE_DEVICE})
/* loaded from: classes2.dex */
public class CloudDiscoverChain extends DiscoverChainBase {

    /* renamed from: c, reason: collision with root package name */
    public ScheduledFuture f10440c;

    /* renamed from: d, reason: collision with root package name */
    public Map<String, Object> f10441d;

    /* renamed from: e, reason: collision with root package name */
    public boolean f10442e;

    public CloudDiscoverChain(Context context, Map<String, Object> map) {
        super(context);
        this.f10440c = null;
        this.f10442e = false;
        this.f10441d = map;
        if (map == null) {
            this.f10441d = new HashMap();
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.base.AbilityReceiver
    public void onNotify(Intent intent) {
    }

    public final void resetFutureTask() {
        ScheduledFuture scheduledFuture = this.f10440c;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.f10440c = null;
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void startDiscover(final IDeviceDiscoveryListener iDeviceDiscoveryListener) {
        ALog.d("CloudDiscoverChain", "startDiscover");
        resetFutureTask();
        this.f10440c = ThreadPool.scheduleAtFixedRate(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.discover.cloud.CloudDiscoverChain.1
            @Override // java.lang.Runnable
            public void run() {
                TransitoryClient.getInstance().asynRequest(new IoTRequestBuilder().setPath(AlinkConstants.HTTP_PATH_CLOUD_ENROLLEE_DISCOVER).setScheme(Scheme.HTTPS).setApiVersion("1.0.7").setAuthType(AlinkConstants.KEY_IOT_AUTH).setParams(CloudDiscoverChain.this.f10441d).addParam(AlinkConstants.KEY_PAGE_SIZE, 100).addParam("pageNum", 1).build(), new IoTCallback() { // from class: com.aliyun.alink.business.devicecenter.discover.cloud.CloudDiscoverChain.1.1
                    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                    public void onFailure(IoTRequest ioTRequest, Exception exc) {
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                        CloudResponse cloudResponse;
                        T t2;
                        if (ioTResponse != null) {
                            try {
                                if (ioTResponse.getCode() == 200) {
                                    String str = new String(ioTResponse.getRawData());
                                    if (TextUtils.isEmpty(str) || (cloudResponse = (CloudResponse) JSON.parseObject(str, new TypeReference<CloudResponse<CloudEnrolleeDeviceWrapper>>() { // from class: com.aliyun.alink.business.devicecenter.discover.cloud.CloudDiscoverChain.1.1.1
                                    }.getType(), new Feature[0])) == null || (t2 = cloudResponse.data) == 0 || ((CloudEnrolleeDeviceWrapper) t2).items == null) {
                                        return;
                                    }
                                    if (CloudDiscoverChain.this.f10442e) {
                                        CacheCenter.getInstance().updateCache(CacheType.BATCH_CLOUD_ENROLLEE, (List) CloudEnrolleeDeviceHelper.getFilteredEnrolleeDevices(((CloudEnrolleeDeviceWrapper) cloudResponse.data).items, CloudDiscoverChain.this.f10442e));
                                    } else {
                                        CacheCenter.getInstance().updateCache(CacheType.CLOUD_ENROLLEE, (List) CloudEnrolleeDeviceHelper.getFilteredEnrolleeDevices(((CloudEnrolleeDeviceWrapper) cloudResponse.data).items, CloudDiscoverChain.this.f10442e));
                                    }
                                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                    anonymousClass1.a(CloudEnrolleeDeviceHelper.convertCloudEnrolleeDevice(((CloudEnrolleeDeviceWrapper) cloudResponse.data).items, CloudDiscoverChain.this.f10442e));
                                    return;
                                }
                            } catch (Exception e2) {
                                ALog.w("CloudDiscoverChain", "startDiscover getEnrolleeList onResponse parse exception=" + e2);
                                return;
                            }
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append("getEnrollee device list failed.request=");
                        sb.append(TransitoryClient.getInstance().requestToStr(ioTRequest));
                        sb.append(",response=");
                        sb.append(TransitoryClient.getInstance().responseToStr(ioTResponse));
                        ALog.w("CloudDiscoverChain", sb.toString());
                    }
                });
            }

            public final void a(final List<DeviceInfo> list) {
                DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.discover.cloud.CloudDiscoverChain.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        iDeviceDiscoveryListener.onDeviceFound(DiscoveryType.CLOUD_ENROLLEE_DEVICE, list);
                    }
                });
            }
        }, 0L, this.f10442e ? 3L : 5L, TimeUnit.SECONDS);
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void stopDiscover() {
        ALog.d("CloudDiscoverChain", "stopDiscover");
        resetFutureTask();
    }

    public CloudDiscoverChain(Context context, Map<String, Object> map, boolean z2) {
        this(context, map);
        this.f10442e = z2;
    }
}
