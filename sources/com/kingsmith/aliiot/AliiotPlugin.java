package com.kingsmith.aliiot;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.business.devicecenter.api.add.AddDeviceBiz;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener;
import com.aliyun.alink.business.devicecenter.api.add.ProvisionStatus;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.iot.aep.sdk.IoTSmart;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialListener;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageError;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import com.aliyun.iot.aep.sdk.credential.data.IoTCredentialData;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.aliyun.iot.aep.sdk.login.ILoginCallback;
import com.aliyun.iot.aep.sdk.login.ILogoutCallback;
import com.aliyun.iot.aep.sdk.login.IRefreshSessionCallback;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingsmith.aliiot.bean.OTADeviceDetailInfo;
import com.kingsmith.aliiot.bean.OTAStatusInfo;
import com.kingsmith.miot.KsProperty;
import com.kingsmith.plugin.Protos;
import com.tekartik.sqflite.Constant;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class AliiotPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, PluginRegistry.ActivityResultListener, ActivityAware {
    private static final int REQUEST_CODE_PRODUCT_ADD = 1;
    private static final int REQUEST_CODE_PRODUCT_DETAIL = 2;
    private static final String TAG = "AliiotPlugin";
    private Activity activity;
    private MethodChannel channel;
    private MethodChannel channelDevice;
    private HashMap<String, DeviceInfoBean> listByAccount = new HashMap<>();
    private PanelDevice panelDevice;
    private String productKey;

    private void authLogin(String str, ILoginCallback iLoginCallback) {
        LoginBusiness.getLoginAdapter().authCodeLogin(str, iLoginCallback);
    }

    private void bindDevice(String str, OnBindDeviceCompletedListener onBindDeviceCompletedListener) {
        ALog.d("TAG", "productKey:" + this.productKey + ",deviceName:" + str + ",token: " + ((String) null));
        Device device = new Device();
        device.pk = this.productKey;
        device.dn = str;
        device.token = null;
        device.iotId = null;
        DeviceBindBusiness deviceBindBusiness = new DeviceBindBusiness(this.activity);
        deviceBindBusiness.setGroupId(null);
        deviceBindBusiness.queryProductInfo(device);
        deviceBindBusiness.bindDevice(device, onBindDeviceCompletedListener);
    }

    private void invokeMethodUIThread(final String str, final byte[] bArr) {
        this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.20
            @Override // java.lang.Runnable
            public void run() {
                AliiotPlugin.this.channel.invokeMethod(str, bArr);
            }
        });
    }

    private void listByAccount(IoTCallback ioTCallback) {
        if (LoginBusiness.isLogin()) {
            LoginBusiness.refreshSession(true, new IRefreshSessionCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.18
                @Override // com.aliyun.iot.aep.sdk.login.IRefreshSessionCallback
                public void onRefreshFailed() {
                    ALog.i(AliiotPlugin.TAG, "刷新Session失败");
                }

                @Override // com.aliyun.iot.aep.sdk.login.IRefreshSessionCallback
                public void onRefreshSuccess() {
                    ALog.i(AliiotPlugin.TAG, "刷新Session成功");
                }
            });
        }
        IoTCredentialManageImpl ioTCredentialManageImpl = IoTCredentialManageImpl.getInstance(this.activity.getApplication());
        if (ioTCredentialManageImpl != null) {
            ALog.d(TAG, ioTCredentialManageImpl.getIoTCredential().toString());
            ioTCredentialManageImpl.asyncRefreshIoTCredential(new IoTCredentialListener() { // from class: com.kingsmith.aliiot.AliiotPlugin.19
                @Override // com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialListener
                public void onRefreshIoTCredentialFailed(IoTCredentialManageError ioTCredentialManageError) {
                    ALog.i(AliiotPlugin.TAG, "refresh IoTCredentailData failed ");
                    if (ioTCredentialManageError != null) {
                        ALog.i(AliiotPlugin.TAG, "error code is:" + ioTCredentialManageError.errorCode);
                    }
                }

                @Override // com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialListener
                public void onRefreshIoTCredentialSuccess(IoTCredentialData ioTCredentialData) {
                    ALog.i(AliiotPlugin.TAG, "refresh IoTCredentailData success :" + ioTCredentialData.toString());
                }
            });
        }
        new IoTAPIClientFactory().getClient().send(new IoTRequestBuilder().setPath("/uc/listBindingByAccount").setScheme(Scheme.HTTPS).setApiVersion("1.0.2").setAuthType(AlinkConstants.KEY_IOT_AUTH).setParams(new HashMap()).build(), ioTCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void printLogger(String str) {
        invokeMethodUIThread("ConnectConfigResponse", Protos.ConnectConfigResponse.newBuilder().setProductKey(str).build().toByteArray());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] send(int i2, String str) {
        return Protos.PResponse.newBuilder().setCode(i2).setMsg(str).build().toByteArray();
    }

    private void setDeviceName(String str, String str2, IoTCallback ioTCallback) {
        HashMap map = new HashMap();
        map.put("iotId", str);
        map.put("nickName", str2);
        new IoTAPIClientFactory().getClient().send(new IoTRequestBuilder().setPath("/uc/setDeviceNickName").setScheme(Scheme.HTTPS).setApiVersion("1.0.2").setAuthType(AlinkConstants.KEY_IOT_AUTH).setParams(map).build(), ioTCallback);
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int i2, int i3, Intent intent) {
        if (1 != i2) {
            if (2 != i3 || -1 != i3) {
                return false;
            }
            ALog.d("TAG", intent != null ? intent.toString() : TmpConstant.GROUP_ROLE_UNKNOWN);
            return false;
        }
        if (-1 != i3) {
            return false;
        }
        ALog.d(TAG, "配网成功");
        String stringExtra = intent.getStringExtra("productKey");
        String stringExtra2 = intent.getStringExtra("deviceName");
        if (stringExtra != null && stringExtra2 != null) {
            this.channel.invokeMethod("ConnectConfigResponse", Protos.ConnectConfigResponse.newBuilder().setProductKey(stringExtra).setDeviceName(stringExtra2).build().toByteArray());
        }
        return true;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        this.activity = activityPluginBinding.getActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "aliiot");
        this.channelDevice = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "aliiot/device");
        this.channel.setMethodCallHandler(this);
        this.channelDevice.setMethodCallHandler(this);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.channel.setMethodCallHandler(null);
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, final MethodChannel.Result result) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (methodCall.method.equals(Constant.METHOD_GET_PLATFORM_VERSION)) {
            result.success("Android " + Build.VERSION.RELEASE);
            return;
        }
        if (methodCall.method.equals("setLogLevel")) {
            byte bIntValue = (byte) ((Integer) methodCall.argument(FirebaseAnalytics.Param.LEVEL)).intValue();
            ALog.setLevel(bIntValue);
            com.aliyun.alink.linksdk.tools.ALog.setLevel(bIntValue);
            return;
        }
        if (methodCall.method.equals("initAliiot")) {
            ALog.setLevel((byte) 2);
            IoTSmart.InitConfig debug = new IoTSmart.InitConfig().setRegionType(0).setProductEnv(IoTSmart.PRODUCT_ENV_PROD).setDebug(true);
            String str = (String) methodCall.argument("appKey");
            IoTSmart.AppKeyConfig appKeyConfig = new IoTSmart.AppKeyConfig();
            appKeyConfig.appKey = str;
            appKeyConfig.appSecret = "ddd24a8e79d9e4b19e52daeda7d764e1";
            debug.setAppKeyConfig(appKeyConfig);
            Log.e("UMLog", "initAliiot keyConfig.appKey: " + appKeyConfig.appKey);
            IoTSmart.setProductScope(IoTSmart.PRODUCT_SCOPE_PUBLISHED);
            IoTSmart.init(AApplication.getInstance(), debug);
            result.success(null);
            return;
        }
        if (methodCall.method.equals(FirebaseAnalytics.Event.LOGIN)) {
            IoTSmart.setCountry("CN", new IoTSmart.ICountrySetCallBack() { // from class: com.kingsmith.aliiot.AliiotPlugin.1
                @Override // com.aliyun.iot.aep.sdk.IoTSmart.ICountrySetCallBack
                public void onCountrySet(boolean z2) {
                    AliiotPlugin.this.printLogger("初始化 设置国家结果: " + z2);
                }
            });
            authLogin((String) methodCall.argument("code"), new ILoginCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.2
                @Override // com.aliyun.iot.aep.sdk.login.ILoginCallback
                public void onLoginFailed(final int i2, final String str2) {
                    Log.e(AliiotPlugin.TAG, "登陆失败");
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.2.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                            result.success(AliiotPlugin.this.send(i2, str2));
                        }
                    });
                }

                @Override // com.aliyun.iot.aep.sdk.login.ILoginCallback
                public void onLoginSuccess() {
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                            result.success(AliiotPlugin.this.send(0, "登陆成功"));
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("logout")) {
            LoginBusiness.logout(new ILogoutCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.3
                @Override // com.aliyun.iot.aep.sdk.login.ILogoutCallback
                public void onLogoutFailed(final int i2, final String str2) {
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.3.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                            result.success(AliiotPlugin.this.send(i2, str2));
                        }
                    });
                }

                @Override // com.aliyun.iot.aep.sdk.login.ILogoutCallback
                public void onLogoutSuccess() {
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                            result.success(AliiotPlugin.this.send(0, "退出成功"));
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("connectConfig")) {
            final String str2 = (String) methodCall.argument("wifiName");
            final String str3 = (String) methodCall.argument("wifiPassword");
            this.productKey = "a2zo9R7QnM3";
            DeviceInfo deviceInfo = new DeviceInfo();
            String str4 = this.productKey;
            deviceInfo.productKey = str4 != null ? str4 : "a2zo9R7QnM3";
            deviceInfo.linkType = "ForceAliLinkTypeNone";
            AddDeviceBiz.getInstance().setDevice(deviceInfo);
            AddDeviceBiz.getInstance().startAddDevice(this.activity, new IAddDeviceListener() { // from class: com.kingsmith.aliiot.AliiotPlugin.4
                @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
                public void onPreCheck(boolean z2, DCErrorCode dCErrorCode) {
                }

                @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
                public void onProvisionPrepare(int i2) {
                    Log.e(AliiotPlugin.TAG, "onProvisionPrepare: " + i2);
                    AddDeviceBiz.getInstance().toggleProvision(str2, str3, 60);
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            result.success(null);
                        }
                    });
                }

                @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
                public void onProvisionStatus(ProvisionStatus provisionStatus) {
                }

                @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
                public void onProvisionedResult(boolean z2, DeviceInfo deviceInfo2, DCErrorCode dCErrorCode) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("success: ");
                    sb.append(z2);
                    sb.append("\ndeviceInfo:");
                    String string = TmpConstant.GROUP_ROLE_UNKNOWN;
                    sb.append(deviceInfo2 == null ? TmpConstant.GROUP_ROLE_UNKNOWN : deviceInfo2.toString());
                    sb.append("\nerrorCode:");
                    if (dCErrorCode != null) {
                        string = dCErrorCode.toString();
                    }
                    sb.append(string);
                    String string2 = sb.toString();
                    Log.e(AliiotPlugin.TAG, string2);
                    final byte[] byteArray = z2 ? Protos.ConnectConfigResponse.newBuilder().setProductKey(deviceInfo2.productKey).setDeviceName(deviceInfo2.deviceName).build().toByteArray() : Protos.ConnectConfigResponse.newBuilder().setProductKey(string2).setDeviceName("NetPairErrorTemp").build().toByteArray();
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.4.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Log.e(AliiotPlugin.TAG, "send connectConfigResponse");
                            AliiotPlugin.this.channel.invokeMethod("ConnectConfigResponse", new String(byteArray));
                        }
                    });
                }

                @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
                public void onProvisioning() {
                }
            });
            return;
        }
        if (methodCall.method.equals("bindDevice")) {
            bindDevice((String) methodCall.argument("deviceName"), new OnBindDeviceCompletedListener() { // from class: com.kingsmith.aliiot.AliiotPlugin.5
                @Override // com.kingsmith.aliiot.OnBindDeviceCompletedListener
                public void onFailed(final Exception exc) {
                    ALog.d(AliiotPlugin.TAG, "bindDevice onFail s = " + exc);
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.5.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass5 anonymousClass5 = AnonymousClass5.this;
                            result.success(AliiotPlugin.this.send(-1, exc.getMessage()));
                        }
                    });
                }

                @Override // com.kingsmith.aliiot.OnBindDeviceCompletedListener
                public void onSuccess(final String str5) {
                    Log.e(AliiotPlugin.TAG, "绑定成功:" + str5);
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.5.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass5 anonymousClass5 = AnonymousClass5.this;
                            result.success(AliiotPlugin.this.send(0, str5));
                        }
                    });
                }

                @Override // com.kingsmith.aliiot.OnBindDeviceCompletedListener
                public void printLog(String str5) {
                    AliiotPlugin.this.printLogger(str5);
                }

                @Override // com.kingsmith.aliiot.OnBindDeviceCompletedListener
                public void onFailed(final int i2, final String str5, String str6) {
                    ALog.d(AliiotPlugin.TAG, "onFailure" + i2 + "msg: " + str5);
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.5.3
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass5 anonymousClass5 = AnonymousClass5.this;
                            result.success(AliiotPlugin.this.send(i2, str5));
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("unbindDevice")) {
            new DeviceUnbindBusiness().unbind((String) methodCall.argument("iotId"), new IoTCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.6
                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onFailure(IoTRequest ioTRequest, final Exception exc) {
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                            result.success(AliiotPlugin.this.send(-2, exc.getMessage()));
                        }
                    });
                }

                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onResponse(IoTRequest ioTRequest, final IoTResponse ioTResponse) {
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.6.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (ioTResponse.getCode() == 200) {
                                AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                                result.success(AliiotPlugin.this.send(0, "解绑成功"));
                            } else {
                                AnonymousClass6 anonymousClass62 = AnonymousClass6.this;
                                result.success(AliiotPlugin.this.send(ioTResponse.getCode(), ioTResponse.getMessage()));
                            }
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("listByAccount")) {
            listByAccount(new IoTCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.7
                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onFailure(IoTRequest ioTRequest, final Exception exc) {
                    exc.printStackTrace();
                    ALog.d("TAG", "onFailure");
                    AliiotPlugin.this.printLogger("listByAccount onFailure :" + exc.getMessage());
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            result.error(exc.getMessage(), "", "");
                        }
                    });
                }

                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onResponse(IoTRequest ioTRequest, final IoTResponse ioTResponse) {
                    int code = ioTResponse.getCode();
                    String localizedMsg = ioTResponse.getLocalizedMsg();
                    Log.e("TAG", "code:" + code + ",msg:" + localizedMsg);
                    Log.e("TAG", "msg:" + ioTResponse.getMessage() + ",data:" + ioTResponse.getData());
                    AliiotPlugin.this.printLogger("listByAccount code:" + code + ",msg:" + localizedMsg);
                    AliiotPlugin.this.printLogger("listByAccount msg:" + ioTResponse.getMessage() + ",data:" + ioTResponse.getData());
                    if (code != 200) {
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.7.2
                            @Override // java.lang.Runnable
                            public void run() {
                                result.error(ioTResponse.getMessage(), "", "");
                            }
                        });
                        return;
                    }
                    Object data = ioTResponse.getData();
                    if (data == null) {
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.7.3
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.from((List<DeviceInfoBean>) null).toByteArray());
                            }
                        });
                        return;
                    }
                    if (!(data instanceof JSONObject)) {
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.7.4
                            @Override // java.lang.Runnable
                            public void run() {
                                result.error(ioTResponse.getMessage(), "", "");
                            }
                        });
                        return;
                    }
                    try {
                        final List<DeviceInfoBean> array = JSON.parseArray(((JSONObject) data).getJSONArray("data").toString(), DeviceInfoBean.class);
                        for (DeviceInfoBean deviceInfoBean : array) {
                            AliiotPlugin.this.listByAccount.put(deviceInfoBean.getIotId(), deviceInfoBean);
                        }
                        Log.e("TAG", array.size() + "---");
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.7.5
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.from((List<DeviceInfoBean>) array).toByteArray());
                            }
                        });
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.7.6
                            @Override // java.lang.Runnable
                            public void run() {
                                result.error(e2.getMessage(), "", "");
                            }
                        });
                    }
                }
            });
            return;
        }
        if (methodCall.method.equals("getUserInfo")) {
            result.success(ProtoMaker.from(LoginBusiness.getUserInfo()).toByteArray());
            return;
        }
        if (methodCall.method.equals("detail")) {
            new Bundle().putString("iotId", (String) methodCall.argument("iotId"));
            return;
        }
        if (methodCall.method.equals("isLogin")) {
            result.success(Boolean.valueOf(LoginBusiness.isLogin()));
            return;
        }
        if (methodCall.method.equals("initPanelDevice")) {
            String str5 = (String) methodCall.argument("iotId");
            this.panelDevice = new PanelDevice(str5, this.listByAccount.get(str5), ((Integer) methodCall.argument(KsProperty.Sensitivity)).intValue());
            result.success(null);
            return;
        }
        if (methodCall.method.equals("setProperties")) {
            this.panelDevice.setProperties((String) methodCall.argument("paramsStr"), new IPanelCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.8
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, Object obj) {
                    Gson gson = new Gson();
                    if (obj instanceof String) {
                        final AliResponse aliResponse = (AliResponse) gson.fromJson((String) obj, AliResponse.class);
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.8.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (aliResponse.getCode() == 200) {
                                    AnonymousClass8 anonymousClass8 = AnonymousClass8.this;
                                    result.success(AliiotPlugin.this.send(0, "设置成功"));
                                } else {
                                    AnonymousClass8 anonymousClass82 = AnonymousClass8.this;
                                    result.success(AliiotPlugin.this.send(aliResponse.getCode(), "3333"));
                                }
                            }
                        });
                    } else if (obj instanceof AError) {
                        ALog.d(AliiotPlugin.TAG, "getStatus(), AError o:" + ((AError) obj).getMsg());
                    }
                }
            });
            return;
        }
        if (methodCall.method.equals("getStatus")) {
            this.panelDevice.getStatus(new IPanelCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.9
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, Object obj) {
                    final Gson gson = new Gson();
                    ALog.d(AliiotPlugin.TAG, "getStatus(), b: " + z2 + " o:" + obj);
                    if (obj instanceof String) {
                        final AliResponse aliResponse = (AliResponse) gson.fromJson((String) obj, AliResponse.class);
                        ALog.d(AliiotPlugin.TAG, "getStatus(), AliResponse," + aliResponse.getData());
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.9.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (aliResponse.getCode() == 200) {
                                    AnonymousClass9 anonymousClass9 = AnonymousClass9.this;
                                    result.success(AliiotPlugin.this.send(0, gson.toJson(aliResponse.getData())));
                                } else {
                                    AnonymousClass9 anonymousClass92 = AnonymousClass9.this;
                                    result.success(AliiotPlugin.this.send(aliResponse.getCode(), "3333"));
                                }
                            }
                        });
                        return;
                    }
                    if (obj instanceof AError) {
                        ALog.d(AliiotPlugin.TAG, "getStatus(), AError o:" + ((AError) obj).getMsg());
                    }
                }
            });
            return;
        }
        if (methodCall.method.equals("getProperties")) {
            PanelDevice panelDevice = this.panelDevice;
            if (panelDevice == null) {
                throw new RuntimeException("You must init PanelDevice first");
            }
            panelDevice.getProperties(new IPanelCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.10
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, Object obj) {
                    ALog.d(AliiotPlugin.TAG, "getProps(), request complete," + z2);
                    if (!(obj instanceof String)) {
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.10.3
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.from(null, -1).toByteArray());
                            }
                        });
                        return;
                    }
                    String str6 = (String) obj;
                    ALog.d(AliiotPlugin.TAG, str6);
                    final AliResponse aliResponse = (AliResponse) new Gson().fromJson(str6, new TypeToken<AliResponse<Properties>>() { // from class: com.kingsmith.aliiot.AliiotPlugin.10.1
                    }.getType());
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.10.2
                        @Override // java.lang.Runnable
                        public void run() {
                            result.success(ProtoMaker.from((Properties) aliResponse.getData(), aliResponse.getCode()).toByteArray());
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("getMotionData")) {
            PanelDevice panelDevice2 = this.panelDevice;
            if (panelDevice2 == null) {
                throw new RuntimeException("You must init PanelDevice first");
            }
            panelDevice2.getProperties(new IPanelCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.11
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, Object obj) {
                    ALog.d(AliiotPlugin.TAG, "getProps(), request complete," + z2);
                    if (!(obj instanceof String)) {
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.11.3
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.from(null, -1).toByteArray());
                            }
                        });
                        return;
                    }
                    String str6 = (String) obj;
                    ALog.d(AliiotPlugin.TAG, str6);
                    final AliResponse aliResponse = (AliResponse) new Gson().fromJson(str6, new TypeToken<AliResponse<Properties>>() { // from class: com.kingsmith.aliiot.AliiotPlugin.11.1
                    }.getType());
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.11.2
                        @Override // java.lang.Runnable
                        public void run() {
                            result.success(ProtoMaker.from((Properties) aliResponse.getData(), aliResponse.getCode()).toByteArray());
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("invokeService")) {
            if (this.panelDevice == null) {
                throw new RuntimeException("You must init PanelDevice first");
            }
            this.panelDevice.invokeService((String) methodCall.argument("paramsStr"), new IPanelCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.12
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, Object obj) {
                    ALog.d(AliiotPlugin.TAG, "invokeService(), request complete," + z2 + obj);
                    if (!(obj instanceof String)) {
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.12.2
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass12 anonymousClass12 = AnonymousClass12.this;
                                result.success(AliiotPlugin.this.send(-2, "3333"));
                            }
                        });
                        return;
                    }
                    String str6 = (String) obj;
                    ALog.d(AliiotPlugin.TAG, str6);
                    final AliResponse aliResponse = (AliResponse) new Gson().fromJson(str6, AliResponse.class);
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.12.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (aliResponse.getCode() == 200) {
                                AnonymousClass12 anonymousClass12 = AnonymousClass12.this;
                                result.success(AliiotPlugin.this.send(0, "设置成功"));
                            } else {
                                AnonymousClass12 anonymousClass122 = AnonymousClass12.this;
                                result.success(AliiotPlugin.this.send(aliResponse.getCode(), "3333"));
                            }
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("setDeviceBiz")) {
            return;
        }
        if (methodCall.method.equals("setConnectDevice")) {
            this.productKey = (String) methodCall.argument("model");
            DeviceInfo deviceInfo2 = new DeviceInfo();
            String str6 = this.productKey;
            if (str6 == null) {
                str6 = "a1XJ81n3tV1";
            }
            deviceInfo2.productKey = str6;
            deviceInfo2.linkType = "ForceAliLinkTypeNone";
            AddDeviceBiz.getInstance().setDevice(deviceInfo2);
            result.success(null);
            return;
        }
        if (methodCall.method.equals("startConnect")) {
            AddDeviceBiz.getInstance().startAddDevice(this.activity, new IAddDeviceListener() { // from class: com.kingsmith.aliiot.AliiotPlugin.13
                @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
                public void onPreCheck(boolean z2, DCErrorCode dCErrorCode) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("b\t");
                    sb.append(!z2 ? dCErrorCode.toString() : "");
                    ALog.d(AliiotPlugin.TAG, sb.toString());
                    if (z2) {
                        return;
                    }
                    final byte[] byteArray = Protos.ConnectConfigResponse.newBuilder().setProductKey("").setDeviceName("").build().toByteArray();
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.13.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ALog.d(AliiotPlugin.TAG, "send connectConfigResponse");
                            AliiotPlugin.this.channel.invokeMethod("ConnectConfigResponse", byteArray);
                        }
                    });
                }

                @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
                public void onProvisionPrepare(int i2) {
                    ALog.d(AliiotPlugin.TAG, "onProvisionPrepare: " + i2);
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.13.2
                        @Override // java.lang.Runnable
                        public void run() {
                            result.success(null);
                        }
                    });
                }

                @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
                public void onProvisionStatus(ProvisionStatus provisionStatus) {
                }

                /* JADX WARN: Removed duplicated region for block: B:18:0x005d  */
                @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void onProvisionedResult(boolean r4, com.aliyun.alink.business.devicecenter.api.add.DeviceInfo r5, com.aliyun.alink.business.devicecenter.base.DCErrorCode r6) {
                    /*
                        r3 = this;
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder
                        r0.<init>()
                        java.lang.String r1 = "success: "
                        r0.append(r1)
                        r0.append(r4)
                        java.lang.String r1 = "\ndeviceInfo:"
                        r0.append(r1)
                        java.lang.String r1 = "null"
                        if (r5 != 0) goto L18
                        r2 = r1
                        goto L1c
                    L18:
                        java.lang.String r2 = r5.toString()
                    L1c:
                        r0.append(r2)
                        java.lang.String r2 = "\nerrorCode:"
                        r0.append(r2)
                        if (r6 != 0) goto L27
                        goto L2b
                    L27:
                        java.lang.String r1 = r6.toString()
                    L2b:
                        r0.append(r1)
                        java.lang.String r0 = r0.toString()
                        java.lang.String r1 = "AliiotPlugin"
                        com.aliyun.iot.aep.sdk.log.ALog.d(r1, r0)
                        com.kingsmith.plugin.Protos$PResponse$Builder r0 = com.kingsmith.plugin.Protos.PResponse.newBuilder()
                        if (r4 == 0) goto L48
                        r4 = 0
                        com.kingsmith.plugin.Protos$PResponse$Builder r4 = r0.setCode(r4)
                        java.lang.String r5 = r5.deviceName
                        r4.setMsg(r5)
                        goto L6c
                    L48:
                        java.lang.String r4 = r6.code
                        java.lang.String r5 = "101601"
                        boolean r4 = r5.equals(r4)
                        if (r4 == 0) goto L5d
                        java.lang.String r4 = r6.subcode
                        java.lang.String r5 = "60101"
                        boolean r4 = r5.equals(r4)
                        if (r4 == 0) goto L5d
                        goto L5f
                    L5d:
                        java.lang.String r5 = r6.code
                    L5f:
                        int r4 = java.lang.Integer.parseInt(r5)
                        com.kingsmith.plugin.Protos$PResponse$Builder r4 = r0.setCode(r4)
                        java.lang.String r5 = r6.msg
                        r4.setMsg(r5)
                    L6c:
                        com.kingsmith.aliiot.AliiotPlugin r4 = com.kingsmith.aliiot.AliiotPlugin.this
                        android.app.Activity r4 = com.kingsmith.aliiot.AliiotPlugin.a(r4)
                        com.kingsmith.aliiot.AliiotPlugin$13$3 r5 = new com.kingsmith.aliiot.AliiotPlugin$13$3
                        r5.<init>()
                        r4.runOnUiThread(r5)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.aliiot.AliiotPlugin.AnonymousClass13.onProvisionedResult(boolean, com.aliyun.alink.business.devicecenter.api.add.DeviceInfo, com.aliyun.alink.business.devicecenter.base.DCErrorCode):void");
                }

                @Override // com.aliyun.alink.business.devicecenter.api.add.IAddDeviceListener
                public void onProvisioning() {
                }
            });
            return;
        }
        if (methodCall.method.equals("stopConnect")) {
            AddDeviceBiz.getInstance().stopAddDevice();
            result.success(send(-10711, "stopConnect success"));
            return;
        }
        if (methodCall.method.equals("setSSidAndPwd")) {
            String str7 = (String) methodCall.argument("ssid");
            String str8 = (String) methodCall.argument("pwd");
            AddDeviceBiz.getInstance().getWifiType(this.activity);
            AddDeviceBiz.getInstance().toggleProvision(str7, str8, 60);
            result.success(null);
            return;
        }
        if (methodCall.method.equals("queryFirmware")) {
            HashMap map = new HashMap();
            map.put("iotId", this.panelDevice.getDevice().getIotId());
            new IoTAPIClientFactory().getClient().send(new IoTRequestBuilder().setAuthType(AlinkConstants.KEY_IOT_AUTH).setApiVersion("1.0.2").setPath("/thing/ota/info/queryByUser").setParams(map).build(), new IoTCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.14
                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onFailure(IoTRequest ioTRequest, Exception exc) {
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.14.1
                        @Override // java.lang.Runnable
                        public void run() {
                            result.success(ProtoMaker.from((OTADeviceDetailInfo) null).toByteArray());
                        }
                    });
                }

                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                    int code = ioTResponse.getCode();
                    ALog.d("TAG", "code:" + code + ",msg:" + ioTResponse.getLocalizedMsg());
                    ALog.d("TAG", "msg:" + ioTResponse.getMessage() + ",data:" + ioTResponse.getData());
                    if (code != 200) {
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.14.2
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.from((OTADeviceDetailInfo) null).toByteArray());
                            }
                        });
                        return;
                    }
                    Object data = ioTResponse.getData();
                    if (data == null) {
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.14.3
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.from((OTADeviceDetailInfo) null).toByteArray());
                            }
                        });
                        return;
                    }
                    if (!(data instanceof JSONObject)) {
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.14.4
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.from((OTADeviceDetailInfo) null).toByteArray());
                            }
                        });
                        return;
                    }
                    try {
                        final OTADeviceDetailInfo oTADeviceDetailInfo = (OTADeviceDetailInfo) JSON.parse(((JSONObject) ((JSONObject) data).get("data")).toString());
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.14.5
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.from(oTADeviceDetailInfo).toByteArray());
                            }
                        });
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.14.6
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.from((OTADeviceDetailInfo) null).toByteArray());
                            }
                        });
                    }
                }
            });
        } else if (methodCall.method.equals("queryOTAStatus")) {
            HashMap map2 = new HashMap();
            map2.put("iotId", this.panelDevice.getDevice().getIotId());
            new IoTAPIClientFactory().getClient().send(new IoTRequestBuilder().setAuthType(AlinkConstants.KEY_IOT_AUTH).setApiVersion("1.0.2").setPath("/thing/ota/info/progress/getByUser").setParams(map2).build(), new IoTCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.15
                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onFailure(IoTRequest ioTRequest, Exception exc) {
                }

                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                    Object data;
                    int code = ioTResponse.getCode();
                    ALog.d("TAG", "code:" + code + ",msg:" + ioTResponse.getLocalizedMsg());
                    ALog.d("TAG", "msg:" + ioTResponse.getMessage() + ",data:" + ioTResponse.getData());
                    if (code == 200 && (data = ioTResponse.getData()) != null && (data instanceof JSONObject)) {
                        try {
                            final OTAStatusInfo oTAStatusInfo = (OTAStatusInfo) JSON.parse(((JSONObject) ((JSONObject) data).get("data")).toString());
                            AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.15.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    result.success(ProtoMaker.from(oTAStatusInfo).toByteArray());
                                }
                            });
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
        } else if (methodCall.method.equals("startUpgrade")) {
            HashMap map3 = new HashMap();
            map3.put("iotIds", new String[]{this.panelDevice.getDevice().getIotId()});
            new IoTAPIClientFactory().getClient().send(new IoTRequestBuilder().setAuthType(AlinkConstants.KEY_IOT_AUTH).setApiVersion("1.0.2").setPath("/thing/ota/batchUpgradeByUser").setParams(map3).build(), new IoTCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.16
                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onFailure(IoTRequest ioTRequest, Exception exc) {
                }

                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                    final int code = ioTResponse.getCode();
                    final String localizedMsg = ioTResponse.getLocalizedMsg();
                    ALog.d("TAG", "code:" + code + ",msg:" + localizedMsg);
                    ALog.d("TAG", "msg:" + ioTResponse.getMessage() + ",data:" + ioTResponse.getData());
                    if (code != 200) {
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.16.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass16 anonymousClass16 = AnonymousClass16.this;
                                result.success(AliiotPlugin.this.send(code, localizedMsg));
                            }
                        });
                    } else {
                        AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.16.2
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass16 anonymousClass16 = AnonymousClass16.this;
                                result.success(AliiotPlugin.this.send(0, ""));
                            }
                        });
                    }
                }
            });
        } else if (methodCall.method.equals("setDeviceNickName")) {
            setDeviceName((String) methodCall.argument("iotId"), (String) methodCall.argument("nickName"), new IoTCallback() { // from class: com.kingsmith.aliiot.AliiotPlugin.17
                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onFailure(IoTRequest ioTRequest, Exception exc) {
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.17.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass17 anonymousClass17 = AnonymousClass17.this;
                            result.success(AliiotPlugin.this.send(0, ""));
                        }
                    });
                }

                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                    AliiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.aliiot.AliiotPlugin.17.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass17 anonymousClass17 = AnonymousClass17.this;
                            result.success(AliiotPlugin.this.send(0, ""));
                        }
                    });
                }
            });
        } else {
            result.notImplemented();
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
    }
}
