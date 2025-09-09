package com.aliyun.alink.linksdk.tmp.service;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.business.devicecenter.config.ble.BreezeConstants;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryDeviceInfo;
import com.aliyun.alink.linksdk.channel.gateway.api.subdevice.ISubDeviceActionListener;
import com.aliyun.alink.linksdk.channel.gateway.api.subdevice.ISubDeviceChannel;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.DeviceManager;
import com.aliyun.alink.linksdk.tmp.data.SubDevInfo;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr;
import com.aliyun.alink.linksdk.tmp.device.request.GateWayRequest;
import com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener;
import com.aliyun.alink.linksdk.tmp.device.request.auth.GetByAccountAndDevRequest;
import com.aliyun.alink.linksdk.tmp.device.request.auth.GetComboAccessInfoRequest;
import com.aliyun.alink.linksdk.tmp.device.request.other.GetDeviceNetTypesSupportedRequest;
import com.aliyun.alink.linksdk.tmp.listener.IProcessListener;
import com.aliyun.alink.linksdk.tmp.service.DevService;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.CloudUtils;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.breeze.TLV;
import com.aliyun.iot.breeze.mix.ConnectionCallback;
import com.aliyun.iot.breeze.mix.MixBleDelegate;
import com.aliyun.iot.breeze.mix.MixBleDevice;
import com.aliyun.iot.breeze.mix.MixMessage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class WifiBTComboDeviceService {
    private static final String TAG = "[Tmp]WifiBTComboDeviceService";
    protected volatile AtomicBoolean isDone = new AtomicBoolean(false);

    /* renamed from: com.aliyun.alink.linksdk.tmp.service.WifiBTComboDeviceService$4, reason: invalid class name */
    class AnonymousClass4 implements IProcessListener {
        final /* synthetic */ DevService.ServiceListener val$serviceListener;
        final /* synthetic */ SubDevInfo val$subDevInfo;

        /* renamed from: com.aliyun.alink.linksdk.tmp.service.WifiBTComboDeviceService$4$2, reason: invalid class name */
        class AnonymousClass2 implements IGateWayRequestListener<GetComboAccessInfoRequest, GetComboAccessInfoRequest.GetComboAccessInfoResponse> {
            AnonymousClass2() {
            }

            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onFail(GetComboAccessInfoRequest getComboAccessInfoRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e(WifiBTComboDeviceService.TAG, "GetComboAccessInfoRequest onFail:" + aError);
                DevService.ServiceListener serviceListener = AnonymousClass4.this.val$serviceListener;
                if (serviceListener != null) {
                    serviceListener.onComplete(true, null);
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onSuccess(GetComboAccessInfoRequest getComboAccessInfoRequest, final GetComboAccessInfoRequest.GetComboAccessInfoResponse getComboAccessInfoResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                T t2;
                final boolean z2 = true;
                if (getComboAccessInfoResponse == null || (t2 = getComboAccessInfoResponse.data) == 0) {
                    ALog.w(WifiBTComboDeviceService.TAG, "GetComboAccessInfoRequest onSuccess data empty");
                    DevService.ServiceListener serviceListener = AnonymousClass4.this.val$serviceListener;
                    if (serviceListener != null) {
                        serviceListener.onComplete(true, null);
                        return;
                    }
                    return;
                }
                if (((GetComboAccessInfoRequest.GetComboAccessInfoData) t2).syncAccessInfo == null || TextUtils.isEmpty(((GetComboAccessInfoRequest.GetComboAccessInfoData) t2).syncAccessInfo.authCode)) {
                    T t3 = getComboAccessInfoResponse.data;
                    if (((GetComboAccessInfoRequest.GetComboAccessInfoData) t3).accessInfo == null) {
                        ALog.w(WifiBTComboDeviceService.TAG, "GetComboAccessInfoRequest syncAccessInfo empty and accessInfo empty");
                        DevService.ServiceListener serviceListener2 = AnonymousClass4.this.val$serviceListener;
                        if (serviceListener2 != null) {
                            serviceListener2.onComplete(true, null);
                            return;
                        }
                        return;
                    }
                    ((GetComboAccessInfoRequest.GetComboAccessInfoData) t3).syncAccessInfo = new GetComboAccessInfoRequest.syncAccessInfo();
                    T t4 = getComboAccessInfoResponse.data;
                    ((GetComboAccessInfoRequest.GetComboAccessInfoData) t4).syncAccessInfo.accessKey = ((GetComboAccessInfoRequest.GetComboAccessInfoData) t4).accessInfo.accessKey;
                    ((GetComboAccessInfoRequest.GetComboAccessInfoData) t4).syncAccessInfo.accessToken = ((GetComboAccessInfoRequest.GetComboAccessInfoData) t4).accessInfo.accessToken;
                    ((GetComboAccessInfoRequest.GetComboAccessInfoData) t4).syncAccessInfo.authCode = ((GetComboAccessInfoRequest.GetComboAccessInfoData) t4).syncAccessInfo.accessKey.substring(0, 8);
                    z2 = false;
                }
                MixBleDelegate.getInstance().open(false, AnonymousClass4.this.val$subDevInfo.deviceName, new ConnectionCallback() { // from class: com.aliyun.alink.linksdk.tmp.service.WifiBTComboDeviceService.4.2.1
                    /* JADX WARN: Multi-variable type inference failed */
                    public void onConnectionStateChange(MixBleDevice mixBleDevice, int i2, int i3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        ALog.d(WifiBTComboDeviceService.TAG, "BREEZE onConnectionStateChange iBreezeDevice:" + mixBleDevice + " i:" + i2 + " i1:" + i3 + " isDone:" + WifiBTComboDeviceService.this.isDone);
                        if (i2 != 2 || mixBleDevice == null || !WifiBTComboDeviceService.this.isDone.compareAndSet(false, true)) {
                            if (i2 == 0 && WifiBTComboDeviceService.this.isDone.compareAndSet(false, true)) {
                                ALog.e(WifiBTComboDeviceService.TAG, "BREEZE onConnectionStateChange STATE_DISCONNECTED");
                                DevService.ServiceListener serviceListener3 = AnonymousClass4.this.val$serviceListener;
                                if (serviceListener3 != null) {
                                    serviceListener3.onComplete(true, null);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(new TLV.Element(BreezeConstants.BREEZE_PROVISION_VERSION, new byte[]{0}));
                        arrayList2.add(new TLV.Element((byte) 1, ((GetComboAccessInfoRequest.GetComboAccessInfoData) getComboAccessInfoResponse.data).syncAccessInfo.authCode.getBytes()));
                        arrayList.add(new TLV.Element((byte) 11, TLV.toPayload(arrayList2)));
                        byte[] payload = TLV.toPayload(arrayList);
                        MixMessage mixMessageNewMessage = mixBleDevice.newMessage(false, 13, payload);
                        ALog.d(WifiBTComboDeviceService.TAG, "authcode data:" + TextHelper.byte2hex(payload, payload.length) + " payload: authCode:" + ((GetComboAccessInfoRequest.GetComboAccessInfoData) getComboAccessInfoResponse.data).syncAccessInfo.authCode + " accessKey:" + ((GetComboAccessInfoRequest.GetComboAccessInfoData) getComboAccessInfoResponse.data).syncAccessInfo.accessKey + " accessToken:" + ((GetComboAccessInfoRequest.GetComboAccessInfoData) getComboAccessInfoResponse.data).syncAccessInfo.accessToken);
                        mixBleDevice.sendMessage(mixMessageNewMessage, new MixBleDevice.ResponseCallback() { // from class: com.aliyun.alink.linksdk.tmp.service.WifiBTComboDeviceService.4.2.1.1
                            /* JADX WARN: Code restructure failed: missing block: B:22:0x0042, code lost:
                            
                                if (r5.value.length <= 0) goto L52;
                             */
                            /* JADX WARN: Code restructure failed: missing block: B:24:0x0048, code lost:
                            
                                if (r5.value[0] != 1) goto L41;
                             */
                            /* JADX WARN: Code restructure failed: missing block: B:25:0x004a, code lost:
                            
                                r3 = true;
                             */
                            /* JADX WARN: Code restructure failed: missing block: B:51:0x000d, code lost:
                            
                                continue;
                             */
                            /* JADX WARN: Code restructure failed: missing block: B:52:0x000d, code lost:
                            
                                continue;
                             */
                            /* JADX WARN: Multi-variable type inference failed */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public void onResponse(int r8, byte[] r9) throws java.lang.IllegalAccessException, javax.crypto.NoSuchPaddingException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
                                /*
                                    Method dump skipped, instructions count: 258
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.linksdk.tmp.service.WifiBTComboDeviceService.AnonymousClass4.AnonymousClass2.AnonymousClass1.C00881.onResponse(int, byte[]):void");
                            }
                        });
                    }
                });
            }
        }

        AnonymousClass4(DevService.ServiceListener serviceListener, SubDevInfo subDevInfo) {
            this.val$serviceListener = serviceListener;
            this.val$subDevInfo = subDevInfo;
        }

        @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
        public void onFail(ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.e(WifiBTComboDeviceService.TAG, "getDeviceSupportedNetTypesByIotId onFail :" + errorInfo);
            DevService.ServiceListener serviceListener = this.val$serviceListener;
            if (serviceListener != null) {
                serviceListener.onComplete(true, null);
            }
        }

        @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
        public void onSuccess(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse getDeviceNetTypesSupportedResponse;
            T t2;
            try {
                getDeviceNetTypesSupportedResponse = (GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse) JSON.parseObject(obj.toString(), GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse.class);
            } catch (Exception unused) {
                ALog.e(WifiBTComboDeviceService.TAG, "GetDeviceNetTypesSupportedResponse parse error");
                getDeviceNetTypesSupportedResponse = null;
            }
            if (getDeviceNetTypesSupportedResponse == null || (t2 = getDeviceNetTypesSupportedResponse.data) == 0) {
                ALog.e(WifiBTComboDeviceService.TAG, "GetDeviceNetTypesSupportedResponse response or data empty");
                DevService.ServiceListener serviceListener = this.val$serviceListener;
                if (serviceListener != null) {
                    serviceListener.onComplete(true, null);
                    return;
                }
                return;
            }
            int deviceNetType = TmpEnum.DeviceNetType.formatDeviceNetType((List) t2);
            if (DevService.isDeviceWifiAndBleCombo(deviceNetType)) {
                ISubDeviceChannel iSubDeviceChannel = this.val$subDevInfo.subDeviceChannel;
                if (iSubDeviceChannel != null) {
                    iSubDeviceChannel.offline(new ISubDeviceActionListener() { // from class: com.aliyun.alink.linksdk.tmp.service.WifiBTComboDeviceService.4.1
                        @Override // com.aliyun.alink.linksdk.channel.gateway.api.subdevice.ISubDeviceActionListener
                        public void onFailed(AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                            ALog.d(WifiBTComboDeviceService.TAG, "offline onFailed aError:" + aError);
                        }

                        @Override // com.aliyun.alink.linksdk.channel.gateway.api.subdevice.ISubDeviceActionListener
                        public void onSuccess() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                            ALog.d(WifiBTComboDeviceService.TAG, "offline onSuccess");
                        }
                    });
                }
                GateWayRequest getComboAccessInfoRequest = new GetComboAccessInfoRequest(null, null, this.val$subDevInfo.iotId);
                getComboAccessInfoRequest.sendRequest(getComboAccessInfoRequest, new AnonymousClass2());
                return;
            }
            ALog.e(WifiBTComboDeviceService.TAG, "isDeviceWifiAndBleCombo not  netType:" + deviceNetType);
            DevService.ServiceListener serviceListener2 = this.val$serviceListener;
            if (serviceListener2 != null) {
                serviceListener2.onComplete(true, null);
            }
        }
    }

    public void afterBind(final SubDevInfo subDevInfo, DevService.ServiceListener serviceListener) {
        CloudUtils.setDeviceExtendProperty(subDevInfo.iotId, TmpConstant.DATA_KEY_DEVICENAME, subDevInfo.deviceName, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.service.WifiBTComboDeviceService.1
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e(WifiBTComboDeviceService.TAG, "setDeviceExtendProperty onFailure error:" + aError + " iotid:" + subDevInfo.iotId + " deviceName:" + subDevInfo.deviceName);
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(WifiBTComboDeviceService.TAG, "setDeviceExtendProperty onResponse response:" + aResponse);
            }
        });
        DevService.setWifiStatus(subDevInfo.iotId, subDevInfo.deviceWifiStatus, new DevService.ServiceListenerEx() { // from class: com.aliyun.alink.linksdk.tmp.service.WifiBTComboDeviceService.2
            @Override // com.aliyun.alink.linksdk.tmp.service.DevService.ServiceListenerEx
            public void onComplete(boolean z2, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(WifiBTComboDeviceService.TAG, "setWifiStatus onComplete isSuccess:" + z2 + " iotid:" + subDevInfo.iotId + " data:" + str);
            }
        });
        GateWayRequest getByAccountAndDevRequest = new GetByAccountAndDevRequest(subDevInfo.iotId);
        getByAccountAndDevRequest.sendRequest(getByAccountAndDevRequest, new IGateWayRequestListener<GetByAccountAndDevRequest, GetByAccountAndDevRequest.GetByAccountAndDevResponse>() { // from class: com.aliyun.alink.linksdk.tmp.service.WifiBTComboDeviceService.3
            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onFail(GetByAccountAndDevRequest getByAccountAndDevRequest2, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e(WifiBTComboDeviceService.TAG, "GetByAccountAndDevRequest onFail ");
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onSuccess(GetByAccountAndDevRequest getByAccountAndDevRequest2, GetByAccountAndDevRequest.GetByAccountAndDevResponse getByAccountAndDevResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                Map<String, Object> map;
                T t2 = getByAccountAndDevResponse.data;
                if (t2 == 0 || TextUtils.isEmpty(((GetByAccountAndDevRequest.GetByAccountAndDevData) t2).deviceName)) {
                    ALog.e(WifiBTComboDeviceService.TAG, "GetByAccountAndDevRequest empty ");
                    return;
                }
                TmpStorage.getInstance().saveDnToMac(((GetByAccountAndDevRequest.GetByAccountAndDevData) getByAccountAndDevResponse.data).deviceName, subDevInfo.deviceName);
                TmpStorage.getInstance().saveMacToDn(subDevInfo.deviceName, ((GetByAccountAndDevRequest.GetByAccountAndDevData) getByAccountAndDevResponse.data).deviceName);
                DeviceManager deviceManager = DeviceManager.getInstance();
                SubDevInfo subDevInfo2 = subDevInfo;
                String str = subDevInfo2.productKey;
                deviceManager.updateDeviceInfo(str, subDevInfo2.deviceName, str, ((GetByAccountAndDevRequest.GetByAccountAndDevData) getByAccountAndDevResponse.data).deviceName);
                DeviceBasicData deviceBasicData = DeviceManager.getInstance().getDeviceBasicData(TextHelper.combineStr(subDevInfo.productKey, ((GetByAccountAndDevRequest.GetByAccountAndDevData) getByAccountAndDevResponse.data).deviceName));
                if (deviceBasicData == null || (map = deviceBasicData.extraData) == null) {
                    return;
                }
                map.put(PalDiscoveryDeviceInfo.EXTRA_KEY_BREEZE_RESET, Boolean.FALSE);
            }
        });
        DeviceShadowMgr.getInstance().getDeviceSupportedNetTypesByIotId(subDevInfo.iotId, new AnonymousClass4(serviceListener, subDevInfo));
    }
}
