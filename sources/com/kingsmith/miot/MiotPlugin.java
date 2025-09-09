package com.kingsmith.miot;

import android.accounts.OperationCanceledException;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.media3.common.PlaybackException;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.chgocn.miot.BuildConfig;
import com.chgocn.miot.KsmbKingSmith;
import com.chgocn.miot.KsmbWalkingPad;
import com.facebook.appevents.AppEventsConstants;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kingsmith.miot.XiaomiAccountGetPeopleInfoTask;
import com.kingsmith.plugins.Protos;
import com.miot.api.CompletionHandler;
import com.miot.api.DeviceManager;
import com.miot.api.IConfigHandler;
import com.miot.api.MiotManager;
import com.miot.common.abstractdevice.AbstractDevice;
import com.miot.common.config.AppConfiguration;
import com.miot.common.device.DiscoveryType;
import com.miot.common.device.firmware.MiotFirmware;
import com.miot.common.exception.MiotException;
import com.miot.common.model.DeviceModelException;
import com.miot.common.model.DeviceModelFactory;
import com.miot.common.people.People;
import com.miot.common.utils.Logger;
import com.xiaomi.account.openauth.XMAuthericationException;
import com.xiaomi.account.openauth.XiaomiOAuthFuture;
import com.xiaomi.account.openauth.XiaomiOAuthResults;
import com.xiaomi.account.openauth.XiaomiOAuthorize;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes4.dex */
public class MiotPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, PluginRegistry.RequestPermissionsResultListener, PluginRegistry.ActivityResultListener, ActivityAware {
    private static final int OPEN_GPS = 1;
    private static final String PUSH_COMMAND = "com.xiaomi.push.command";
    private static final String PUSH_MESSAGE = "com.xiaomi.push.message";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    public static final String TAG = "MiotPlugin";
    private Activity activity;
    private long appId;
    private MethodChannel channel;
    private MethodChannel deviceChannel;
    private Context mContext;
    private PanelDevice panelDevice;
    private ICallback scanCallback;
    private EventChannel scanResultChannel;
    private EventChannel.EventSink scanResultsSink;
    private HashMap<String, AbstractDevice> found = new HashMap<>();
    private String oauthRedirectUrl = "http://xj.kingsmith.com.cn/";
    private HashMap<String, AbstractDevice> listByAccount = new HashMap<>();
    private final EventChannel.StreamHandler scanResultsHandler = new EventChannel.StreamHandler() { // from class: com.kingsmith.miot.MiotPlugin.1
        @Override // io.flutter.plugin.common.EventChannel.StreamHandler
        public void onCancel(Object obj) {
            MiotPlugin.this.scanResultsSink = null;
        }

        @Override // io.flutter.plugin.common.EventChannel.StreamHandler
        public void onListen(Object obj, EventChannel.EventSink eventSink) {
            MiotPlugin.this.scanResultsSink = eventSink;
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.kingsmith.miot.MiotPlugin.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
        }
    };

    public static class GetOauthCodeTask<V> extends AsyncTask<Void, Void, V> {
        private ICallback callback;

        /* renamed from: e, reason: collision with root package name */
        private Exception f18694e;
        private XiaomiOAuthFuture<V> future;

        public GetOauthCodeTask(ICallback iCallback, XiaomiOAuthFuture<V> xiaomiOAuthFuture) {
            this.callback = iCallback;
            this.future = xiaomiOAuthFuture;
        }

        private void processAuthResult(XiaomiOAuthResults xiaomiOAuthResults) {
            Log.d(MiotPlugin.TAG, "processAuthResult  accessCode = " + xiaomiOAuthResults.getCode());
            Log.d(MiotPlugin.TAG, "processAuthResult  results = " + xiaomiOAuthResults);
            this.callback.onSuccess(xiaomiOAuthResults.getCode());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Object doInBackground(Void... voidArr) {
            try {
                return this.future.getResult();
            } catch (OperationCanceledException e2) {
                e2.printStackTrace();
                return null;
            } catch (android.os.OperationCanceledException e3) {
                this.f18694e = e3;
                return null;
            } catch (XMAuthericationException e4) {
                this.f18694e = e4;
                return null;
            } catch (IOException e5) {
                this.f18694e = e5;
                return null;
            }
        }

        @Override // android.os.AsyncTask
        protected void onPostExecute(Object obj) {
            if (obj != null) {
                if (obj instanceof XiaomiOAuthResults) {
                    processAuthResult((XiaomiOAuthResults) obj);
                    return;
                }
                return;
            }
            Exception exc = this.f18694e;
            if (exc != null) {
                exc.printStackTrace();
                this.callback.onError(this.f18694e);
            } else {
                Log.e(MiotPlugin.TAG, "login failed");
                this.callback.onError(null);
            }
        }
    }

    public static class MiotCloseTask extends AsyncTask<Void, Void, Integer> {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Integer doInBackground(Void... voidArr) {
            return Integer.valueOf(MiotManager.getInstance().close());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(Integer num) {
            super.onPostExecute(num);
            Log.d(MiotPlugin.TAG, "MiotClose result: " + num);
        }
    }

    public static class MiotOpenTask extends AsyncTask<Void, Void, Integer> {
        private long appId;
        private String appKey;
        private ICallback callback;
        private WeakReference<Context> weakReference;

        public MiotOpenTask(Context context, ICallback iCallback, long j2, String str) {
            this.weakReference = new WeakReference<>(context);
            this.callback = iCallback;
            this.appId = j2;
            this.appKey = str;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Integer doInBackground(Void... voidArr) {
            AppConfiguration appConfiguration = new AppConfiguration();
            appConfiguration.setAppId(Long.valueOf(this.appId));
            appConfiguration.setAppKey(this.appKey);
            MiotManager.getInstance().setAppConfig(appConfiguration);
            try {
                MiotManager.getInstance().addModel(DeviceModelFactory.createDeviceModel(this.weakReference.get(), "ksmb.treadmill.v1", "ksmb.treadmill.v1.xml", KsmbKingSmith.class));
                MiotManager.getInstance().addModel(DeviceModelFactory.createDeviceModel(this.weakReference.get(), "ksmb.treadmill.v2", "ksmb.treadmill.v2.xml", KsmbKingSmith.class));
                MiotManager.getInstance().addModel(DeviceModelFactory.createDeviceModel(this.weakReference.get(), "ksmb.walkingpad.v1", "ksmb.walkingpad.v1.xml", KsmbWalkingPad.class));
            } catch (DeviceModelException e2) {
                e2.printStackTrace();
            }
            return Integer.valueOf(MiotManager.getInstance().open());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(Integer num) {
            super.onPostExecute(num);
            this.callback.onSuccess(num);
        }
    }

    public static class OauthTask<V> extends AsyncTask<Void, Void, V> {
        private ICallback callback;

        /* renamed from: e, reason: collision with root package name */
        private Exception f18695e;
        private XiaomiOAuthFuture<V> future;

        public OauthTask(ICallback iCallback, XiaomiOAuthFuture<V> xiaomiOAuthFuture) {
            this.callback = iCallback;
            this.future = xiaomiOAuthFuture;
        }

        private void processAuthResult(XiaomiOAuthResults xiaomiOAuthResults) {
            String accessToken = xiaomiOAuthResults.getAccessToken();
            String expiresIn = xiaomiOAuthResults.getExpiresIn();
            String scopes = xiaomiOAuthResults.getScopes();
            String state = xiaomiOAuthResults.getState();
            String tokenType = xiaomiOAuthResults.getTokenType();
            String macKey = xiaomiOAuthResults.getMacKey();
            String macAlgorithm = xiaomiOAuthResults.getMacAlgorithm();
            Log.d(MiotPlugin.TAG, "processAuthResult  results = " + xiaomiOAuthResults);
            Log.d(MiotPlugin.TAG, "accessToken = " + accessToken);
            Log.d(MiotPlugin.TAG, "expiresIn = " + expiresIn);
            Log.d(MiotPlugin.TAG, "scope = " + scopes);
            Log.d(MiotPlugin.TAG, "state = " + state);
            Log.d(MiotPlugin.TAG, "tokenType = " + tokenType);
            Log.d(MiotPlugin.TAG, "macKey = " + macKey);
            Log.d(MiotPlugin.TAG, "macAlgorithm = " + macAlgorithm);
            new XiaomiAccountGetPeopleInfoTask(accessToken, expiresIn, macKey, macAlgorithm, new XiaomiAccountGetPeopleInfoTask.Handler() { // from class: com.kingsmith.miot.MiotPlugin.OauthTask.1
                @Override // com.kingsmith.miot.XiaomiAccountGetPeopleInfoTask.Handler
                public void onFailed() {
                    Log.d(MiotPlugin.TAG, "XiaomiAccountGetPeopleInfoTask Failed");
                    OauthTask.this.callback.onError(null);
                }

                @Override // com.kingsmith.miot.XiaomiAccountGetPeopleInfoTask.Handler
                public void onSucceed(People people) {
                    Log.d(MiotPlugin.TAG, "XiaomiAccountGetPeopleInfoTask OK people: " + people);
                    try {
                        if (MiotManager.getPeopleManager() != null) {
                            MiotManager.getPeopleManager().savePeople(people);
                        }
                        OauthTask.this.callback.onSuccess(null);
                    } catch (MiotException e2) {
                        e2.printStackTrace();
                    }
                }
            }).execute(new Void[0]);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public Object doInBackground(Void... voidArr) {
            try {
                return this.future.getResult();
            } catch (OperationCanceledException e2) {
                e2.printStackTrace();
                return null;
            } catch (android.os.OperationCanceledException e3) {
                this.f18695e = e3;
                return null;
            } catch (XMAuthericationException e4) {
                this.f18695e = e4;
                return null;
            } catch (IOException e5) {
                this.f18695e = e5;
                return null;
            }
        }

        @Override // android.os.AsyncTask
        protected void onPostExecute(Object obj) {
            if (obj != null) {
                if (obj instanceof XiaomiOAuthResults) {
                    processAuthResult((XiaomiOAuthResults) obj);
                    return;
                }
                return;
            }
            Exception exc = this.f18695e;
            if (exc != null) {
                exc.printStackTrace();
                this.callback.onError(this.f18695e);
            } else {
                Log.e(MiotPlugin.TAG, "login failed");
                this.callback.onError(null);
            }
        }
    }

    private void checkEnvironment() {
        Log.d(TAG, "checkEnvironment");
        if (!((LocationManager) this.activity.getSystemService("location")).isProviderEnabled("gps")) {
            new MaterialDialog.Builder(this.activity).theme(Theme.LIGHT).content("GPS尚未打开，无法正常使用").positiveText("打开").onPositive(new MaterialDialog.SingleButtonCallback() { // from class: com.kingsmith.miot.MiotPlugin.26
                @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
                public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                    materialDialog.dismiss();
                    ActivityCompat.startActivityForResult(MiotPlugin.this.activity, new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 1, null);
                }
            }).negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() { // from class: com.kingsmith.miot.MiotPlugin.25
                @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
                public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                    materialDialog.dismiss();
                }
            }).show();
        } else if (hasPermission()) {
            doScan();
        } else {
            requestPermission();
        }
    }

    private void connectToCloud(String str, IConfigHandler iConfigHandler) throws RemoteException {
        try {
            MiotManager.getDeviceConnector().connectToCloud(this.found.get(str), iConfigHandler);
        } catch (MiotException e2) {
            if (BuildConfig.DEBUG) {
                e2.printStackTrace();
            }
            iConfigHandler.onFailed(-10711, e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doInUiThread(Runnable runnable) {
        this.activity.runOnUiThread(runnable);
    }

    private void doScan() {
        Log.d(TAG, "doScan");
        final ArrayList arrayList = new ArrayList();
        try {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(DiscoveryType.MIOT_WIFI);
            MiotManager.getDeviceManager().startScan(arrayList2, new DeviceManager.DeviceHandler() { // from class: com.kingsmith.miot.MiotPlugin.27
                public void onFailed(int i2, String str) {
                }

                public void onSucceed(List<AbstractDevice> list) {
                    Log.e(MiotPlugin.TAG, "startScan device" + list.size());
                    for (final AbstractDevice abstractDevice : list) {
                        Log.e(MiotPlugin.TAG, "device:" + abstractDevice.getName() + "\t" + abstractDevice.getAddress());
                        if (MiotPlugin.this.found.get(abstractDevice.getAddress()) == null) {
                            MiotPlugin.this.found.put(abstractDevice.getAddress(), abstractDevice);
                            arrayList.add(abstractDevice);
                            if (MiotPlugin.this.scanResultsSink != null) {
                                MiotPlugin.this.scanResultsSink.success(ProtoMaker.b(abstractDevice).toByteArray());
                            }
                            MiotPlugin.this.activity.runOnUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.27.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    MiotPlugin.this.channel.invokeMethod("ScanResult", ProtoMaker.b(abstractDevice).toByteArray());
                                }
                            });
                        }
                    }
                }
            });
            this.scanCallback.onSuccess(null);
        } catch (MiotException e2) {
            if (BuildConfig.DEBUG) {
                e2.printStackTrace();
            }
            this.scanCallback.onError(e2);
        }
    }

    @TargetApi(23)
    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(this.activity, "android.permission.ACCESS_FINE_LOCATION") == 0 && ContextCompat.checkSelfPermission(this.activity, "android.permission.ACCESS_COARSE_LOCATION") == 0;
    }

    private void listByAccount(DeviceManager.DeviceHandler deviceHandler) {
        try {
            Log.e(TAG, "listByAccount toCall getRemoteDeviceList");
            MiotManager.getDeviceManager().getRemoteDeviceList(deviceHandler);
        } catch (MiotException e2) {
            if (BuildConfig.DEBUG) {
                e2.printStackTrace();
            }
            deviceHandler.onFailed(-10711, e2.getMessage());
        }
    }

    private void logout(ICallback iCallback) {
        try {
            if (MiotManager.getPeopleManager() != null) {
                MiotManager.getPeopleManager().deletePeople();
            }
            iCallback.onSuccess(null);
        } catch (MiotException e2) {
            if (BuildConfig.DEBUG) {
                e2.printStackTrace();
            }
            iCallback.onError(e2);
        }
    }

    private void registerPush() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PUSH_COMMAND);
        intentFilter.addAction(PUSH_MESSAGE);
        this.activity.registerReceiver(this.mReceiver, intentFilter, 2);
    }

    @TargetApi(23)
    private void requestPermission() {
        ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 1);
    }

    private void saveUserInfo(People people, final ICallback iCallback) {
        Log.d(TAG, "saveUserInfo people: " + people);
        new XiaomiAccountGetPeopleInfoTask(people.getAccessToken(), people.getExpiresIn().toString(), people.getMacKey(), people.getMacAlgorithm(), new XiaomiAccountGetPeopleInfoTask.Handler() { // from class: com.kingsmith.miot.MiotPlugin.24
            @Override // com.kingsmith.miot.XiaomiAccountGetPeopleInfoTask.Handler
            public void onFailed() {
                Log.d(MiotPlugin.TAG, "XiaomiAccountGetPeopleInfoTask Failed");
                iCallback.onError(null);
            }

            @Override // com.kingsmith.miot.XiaomiAccountGetPeopleInfoTask.Handler
            public void onSucceed(People people2) {
                Log.d(MiotPlugin.TAG, "XiaomiAccountGetPeopleInfoTask OK people: " + people2.getUserName());
                try {
                    if (MiotManager.getPeopleManager() != null) {
                        MiotManager.getPeopleManager().savePeople(people2);
                    }
                    JsonObject jsonObject = new JsonObject();
                    Gson gson = new Gson();
                    jsonObject.addProperty("userName", people2.getUserName());
                    jsonObject.addProperty("userId", people2.getUserId());
                    iCallback.onSuccess(gson.toJson((JsonElement) jsonObject));
                } catch (MiotException e2) {
                    e2.printStackTrace();
                }
            }
        }).execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] send(int i2, String str) {
        return Protos.PResponse.newBuilder().setCode(i2).setMsg(str).build().toByteArray();
    }

    private void startScan(ICallback iCallback) {
        Log.d(TAG, "startScan");
        this.scanCallback = iCallback;
        doScan();
    }

    private void stopScan(ICallback iCallback) {
        try {
            MiotManager.getDeviceManager().stopScan();
            iCallback.onSuccess(null);
        } catch (MiotException e2) {
            if (BuildConfig.DEBUG) {
                e2.printStackTrace();
            }
            iCallback.onError(e2);
        }
    }

    private void unbindDevice(AbstractDevice abstractDevice, CompletionHandler completionHandler) {
        try {
            MiotManager.getDeviceManager().disclaimOwnership(abstractDevice, completionHandler);
        } catch (MiotException e2) {
            if (BuildConfig.DEBUG) {
                e2.printStackTrace();
            }
            completionHandler.onFailed(-10711, e2.getMessage());
        }
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int i2, int i3, Intent intent) {
        if (i2 != 1) {
            return false;
        }
        checkEnvironment();
        return false;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        this.activity = activityPluginBinding.getActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.mContext = flutterPluginBinding.getApplicationContext();
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "miot");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        MethodChannel methodChannel2 = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "miot/device");
        this.deviceChannel = methodChannel2;
        methodChannel2.setMethodCallHandler(this);
        EventChannel eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "miot/scan");
        this.scanResultChannel = eventChannel;
        eventChannel.setStreamHandler(this.scanResultsHandler);
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, final MethodChannel.Result result) throws MiotException {
        boolean z2 = false;
        if (methodCall.method.equals("initSDK")) {
            Logger.enableLog(BuildConfig.DEBUG);
            this.appId = Long.parseLong((String) methodCall.argument("appId"));
            Log.d("miot", "initSDK appId: " + this.appId);
            String str = (String) methodCall.argument("appKey");
            MiotManager.getInstance().initialize(this.mContext);
            new MiotOpenTask(this.mContext, new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.3
                @Override // com.kingsmith.miot.ICallback
                public void onError(Throwable th) {
                }

                @Override // com.kingsmith.miot.ICallback
                public void onSuccess(Object obj) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            result.success(null);
                        }
                    });
                }
            }, this.appId, str).execute(new Void[0]);
            return;
        }
        int i2 = 1;
        if (methodCall.method.equals("isLogin")) {
            if (MiotManager.getPeopleManager() != null && MiotManager.getPeopleManager().isLogin()) {
                z2 = true;
            }
            result.success(Boolean.valueOf(z2));
            return;
        }
        if (methodCall.method.equals("getUserInfo")) {
            if (MiotManager.getPeopleManager() == null) {
                result.success(null);
                return;
            }
            People people = MiotManager.getPeopleManager().getPeople();
            Log.i(TAG, "getUserInfo: " + people.toString());
            result.success(people);
            return;
        }
        if (methodCall.method.equals("saveUserInfo")) {
            String str2 = (String) methodCall.argument("accessToken");
            String str3 = (String) methodCall.argument("uid");
            String str4 = (String) methodCall.argument("nickName");
            String str5 = (String) methodCall.argument("expiresIn");
            String str6 = (String) methodCall.argument("macKey");
            String str7 = (String) methodCall.argument("macAlgorithm");
            People people2 = new People(str2, str3);
            people2.setUserName(str4);
            people2.setExpiresIn(Long.valueOf(Long.parseLong(str5)));
            people2.setMacKey(str6);
            people2.setMacAlgorithm(str7);
            saveUserInfo(people2, new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.4
                @Override // com.kingsmith.miot.ICallback
                public void onError(final Throwable th) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.4.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Throwable th2 = th;
                            AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                            result.success(MiotPlugin.this.send(-1, th2 != null ? th2.getMessage() : "unkown error"));
                        }
                    });
                }

                @Override // com.kingsmith.miot.ICallback
                public void onSuccess(final Object obj) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                            result.success(MiotPlugin.this.send(0, (String) obj));
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("setDeviceNickName")) {
            try {
                MiotManager.getDeviceManager().renameDevice(this.listByAccount.get((String) methodCall.argument("iotId")), (String) methodCall.argument("nickName"), new CompletionHandler() { // from class: com.kingsmith.miot.MiotPlugin.5
                    public void onFailed(final int i3, final String str8) {
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.5.2
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass5 anonymousClass5 = AnonymousClass5.this;
                                result.success(MiotPlugin.this.send(i3, str8));
                            }
                        });
                    }

                    public void onSucceed() {
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.5.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass5 anonymousClass5 = AnonymousClass5.this;
                                result.success(MiotPlugin.this.send(0, ""));
                            }
                        });
                    }
                });
                return;
            } catch (MiotException e2) {
                e2.printStackTrace();
                result.success(send(-10711, e2.getMessage()));
                return;
            }
        }
        if (methodCall.method.equals("getStatus")) {
            String str8 = (String) methodCall.argument("deviceId");
            try {
                AbstractDevice device = MiotManager.getDeviceManager().getDevice(str8);
                Log.d(TAG, "getStatus deviceId " + str8 + " result: " + device);
                if (!device.isOnline()) {
                    i2 = 3;
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("status", Integer.valueOf(i2));
                result.success(send(0, new Gson().toJson((JsonElement) jsonObject)));
                return;
            } catch (MiotException e3) {
                e3.printStackTrace();
                return;
            }
        }
        if (methodCall.method.equals("getOAuthCode")) {
            new GetOauthCodeTask(new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.6
                @Override // com.kingsmith.miot.ICallback
                public void onError(final Throwable th) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.6.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Throwable th2 = th;
                            AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                            result.success(MiotPlugin.this.send(-1, th2 != null ? th2.getMessage() : "unkown error"));
                        }
                    });
                }

                @Override // com.kingsmith.miot.ICallback
                public void onSuccess(final Object obj) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Log.d("miot", "OauthTask onSuccess : " + ((String) obj));
                            AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                            result.success(MiotPlugin.this.send(0, (String) obj));
                        }
                    });
                }
            }, new XiaomiOAuthorize().setAppId(this.appId).setRedirectUrl(this.oauthRedirectUrl).setScope(new int[]{1, 3, PlaybackException.ERROR_CODE_DRM_UNSPECIFIED}).startGetOAuthCode(this.activity)).execute(new Void[0]);
            return;
        }
        if (methodCall.method.equals(FirebaseAnalytics.Event.LOGIN)) {
            new OauthTask(new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.7
                @Override // com.kingsmith.miot.ICallback
                public void onError(final Throwable th) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.7.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Throwable th2 = th;
                            AnonymousClass7 anonymousClass7 = AnonymousClass7.this;
                            result.success(MiotPlugin.this.send(-1, th2 != null ? th2.getMessage() : "unkown error"));
                        }
                    });
                }

                @Override // com.kingsmith.miot.ICallback
                public void onSuccess(Object obj) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass7 anonymousClass7 = AnonymousClass7.this;
                            result.success(MiotPlugin.this.send(0, ""));
                        }
                    });
                }
            }, new XiaomiOAuthorize().setAppId(this.appId).setRedirectUrl(this.oauthRedirectUrl).setScope(new int[]{1, 3, PlaybackException.ERROR_CODE_DRM_UNSPECIFIED}).startGetAccessToken(this.activity)).execute(new Void[0]);
            return;
        }
        if (methodCall.method.equals("logout")) {
            logout(new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.8
                @Override // com.kingsmith.miot.ICallback
                public void onError(final Throwable th) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.8.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Throwable th2 = th;
                            AnonymousClass8 anonymousClass8 = AnonymousClass8.this;
                            result.success(MiotPlugin.this.send(-1, th2 != null ? th2.getMessage() : "unkown error"));
                        }
                    });
                }

                @Override // com.kingsmith.miot.ICallback
                public void onSuccess(Object obj) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.8.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass8 anonymousClass8 = AnonymousClass8.this;
                            result.success(MiotPlugin.this.send(0, ""));
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("startScan")) {
            this.found.clear();
            startScan(new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.9
                @Override // com.kingsmith.miot.ICallback
                public void onError(final Throwable th) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.9.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Throwable th2 = th;
                            AnonymousClass9 anonymousClass9 = AnonymousClass9.this;
                            result.success(MiotPlugin.this.send(-1, th2 != null ? th2.getMessage() : "unkown error"));
                        }
                    });
                }

                @Override // com.kingsmith.miot.ICallback
                public void onSuccess(Object obj) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.9.1
                        @Override // java.lang.Runnable
                        public void run() {
                            result.success(null);
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("stopScan")) {
            stopScan(new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.10
                @Override // com.kingsmith.miot.ICallback
                public void onError(final Throwable th) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.10.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Throwable th2 = th;
                            AnonymousClass10 anonymousClass10 = AnonymousClass10.this;
                            result.success(MiotPlugin.this.send(-1, th2 != null ? th2.getMessage() : "unkown error"));
                        }
                    });
                }

                @Override // com.kingsmith.miot.ICallback
                public void onSuccess(Object obj) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.10.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass10 anonymousClass10 = AnonymousClass10.this;
                            result.success(MiotPlugin.this.send(0, ""));
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("connectConfig")) {
            final String str9 = (String) methodCall.argument(AlinkConstants.KEY_MAC);
            try {
                connectToCloud(str9, new IConfigHandler() { // from class: com.kingsmith.miot.MiotPlugin.11
                    public IBinder asBinder() {
                        return null;
                    }

                    public void onFailed(final int i3, final String str10) {
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.11.2
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass11 anonymousClass11 = AnonymousClass11.this;
                                result.success(MiotPlugin.this.send(i3, str10));
                            }
                        });
                    }

                    public void onSucceed(String str10) {
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.11.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Log.e(MiotPlugin.TAG, "connectToCloud: " + ((AbstractDevice) MiotPlugin.this.found.get(str9)).getDeviceId());
                                AnonymousClass11 anonymousClass11 = AnonymousClass11.this;
                                result.success(MiotPlugin.this.send(0, ""));
                            }
                        });
                    }
                });
                return;
            } catch (RemoteException e4) {
                throw new RuntimeException(e4);
            }
        }
        if (methodCall.method.equals("unbindDevice")) {
            unbindDevice(this.listByAccount.get((String) methodCall.argument("iotId")), new CompletionHandler() { // from class: com.kingsmith.miot.MiotPlugin.12
                public void onFailed(final int i3, final String str10) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.12.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass12 anonymousClass12 = AnonymousClass12.this;
                            result.success(MiotPlugin.this.send(i3, str10));
                        }
                    });
                }

                public void onSucceed() {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.12.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass12 anonymousClass12 = AnonymousClass12.this;
                            result.success(MiotPlugin.this.send(0, ""));
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("listByAccount")) {
            this.listByAccount.clear();
            listByAccount(new DeviceManager.DeviceHandler() { // from class: com.kingsmith.miot.MiotPlugin.13
                public void onFailed(int i3, String str10) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.13.2
                        @Override // java.lang.Runnable
                        public void run() {
                            result.success(ProtoMaker.a(null).toByteArray());
                        }
                    });
                }

                public void onSucceed(final List<AbstractDevice> list) {
                    Log.e(MiotPlugin.TAG, "listByAccount device" + list.size());
                    for (AbstractDevice abstractDevice : list) {
                        Log.d(MiotPlugin.TAG, "putDevice[" + abstractDevice.getDeviceId() + "]: " + abstractDevice);
                        MiotPlugin.this.listByAccount.put(abstractDevice.getDeviceId(), abstractDevice);
                    }
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.13.1
                        @Override // java.lang.Runnable
                        public void run() {
                            result.success(ProtoMaker.a(list).toByteArray());
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("initPanelDevice")) {
            this.panelDevice = new PanelDevice(this.listByAccount.get((String) methodCall.argument("iotId")));
            result.success(null);
            return;
        }
        if (methodCall.method.equals("setProperty")) {
            try {
                this.panelDevice.invokeMethod(((Protos.PAction.Builder) Protos.PAction.newBuilder().mergeFrom((byte[]) methodCall.arguments())).build(), new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.14
                    @Override // com.kingsmith.miot.ICallback
                    public void onError(final Throwable th) {
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.14.2
                            @Override // java.lang.Runnable
                            public void run() {
                                Throwable th2 = th;
                                AnonymousClass14 anonymousClass14 = AnonymousClass14.this;
                                result.success(MiotPlugin.this.send(-2, th2 != null ? th2.getMessage() : "unkown error"));
                            }
                        });
                    }

                    @Override // com.kingsmith.miot.ICallback
                    public void onSuccess(final Object obj) {
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.14.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass14 anonymousClass14 = AnonymousClass14.this;
                                result.success(MiotPlugin.this.send(0, "" + obj));
                            }
                        });
                    }
                });
                return;
            } catch (Exception e5) {
                if (BuildConfig.DEBUG) {
                    e5.printStackTrace();
                }
                doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.15
                    @Override // java.lang.Runnable
                    public void run() {
                        result.success(MiotPlugin.this.send(-1, e5.getMessage()));
                    }
                });
                return;
            }
        }
        if (methodCall.method.equals("invokeMethod")) {
            try {
                this.panelDevice.invokeMethod(((Protos.PAction.Builder) Protos.PAction.newBuilder().mergeFrom((byte[]) methodCall.arguments())).build(), new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.16
                    @Override // com.kingsmith.miot.ICallback
                    public void onError(final Throwable th) {
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.16.2
                            @Override // java.lang.Runnable
                            public void run() {
                                Throwable th2 = th;
                                AnonymousClass16 anonymousClass16 = AnonymousClass16.this;
                                result.success(MiotPlugin.this.send(-2, th2 != null ? th2.getMessage() : "unkown error"));
                            }
                        });
                    }

                    @Override // com.kingsmith.miot.ICallback
                    public void onSuccess(Object obj) {
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.16.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass16 anonymousClass16 = AnonymousClass16.this;
                                result.success(MiotPlugin.this.send(0, ""));
                            }
                        });
                    }
                });
                return;
            } catch (Exception e6) {
                if (BuildConfig.DEBUG) {
                    e6.printStackTrace();
                }
                doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.17
                    @Override // java.lang.Runnable
                    public void run() {
                        result.success(MiotPlugin.this.send(-1, e6.getMessage()));
                    }
                });
                return;
            }
        }
        if (methodCall.method.equals("getProperties")) {
            this.panelDevice.getProperties(new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.18
                @Override // com.kingsmith.miot.ICallback
                public void onError(Throwable th) {
                    th.printStackTrace();
                    if (MiotPlugin.this.panelDevice.c()) {
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.18.3
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.e(null).toByteArray());
                            }
                        });
                    } else {
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.18.4
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.d(null, 0).toByteArray());
                            }
                        });
                    }
                }

                @Override // com.kingsmith.miot.ICallback
                public void onSuccess(final Object obj) {
                    if (MiotPlugin.this.panelDevice.c()) {
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.18.1
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.e((JsonObject) obj).toByteArray());
                            }
                        });
                    } else {
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.18.2
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(ProtoMaker.d((JsonObject) obj, 0).toByteArray());
                            }
                        });
                    }
                }
            });
            return;
        }
        if (methodCall.method.equals("getMotionData")) {
            this.panelDevice.getMotionData(new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.19
                @Override // com.kingsmith.miot.ICallback
                public void onError(Throwable th) {
                    th.printStackTrace();
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.19.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass19 anonymousClass19 = AnonymousClass19.this;
                            result.success(ProtoMaker.c(null, MiotPlugin.this.panelDevice.c()).toByteArray());
                        }
                    });
                }

                @Override // com.kingsmith.miot.ICallback
                public void onSuccess(Object obj) {
                    final JsonObject jsonObject2 = (JsonObject) obj;
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.19.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass19 anonymousClass19 = AnonymousClass19.this;
                            result.success(ProtoMaker.c(jsonObject2, MiotPlugin.this.panelDevice.c()).toByteArray());
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("subscribe")) {
            this.panelDevice.subscribe(new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.20
                @Override // com.kingsmith.miot.ICallback
                public void onError(Throwable th) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.20.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass20 anonymousClass20 = AnonymousClass20.this;
                            result.success(ProtoMaker.c(null, MiotPlugin.this.panelDevice.c()).toByteArray());
                        }
                    });
                }

                @Override // com.kingsmith.miot.ICallback
                public void onSuccess(Object obj) {
                    final JsonObject jsonObject2 = (JsonObject) obj;
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.20.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass20 anonymousClass20 = AnonymousClass20.this;
                            result.success(ProtoMaker.c(jsonObject2, MiotPlugin.this.panelDevice.c()).toByteArray());
                        }
                    });
                }
            }, new IChange() { // from class: com.kingsmith.miot.MiotPlugin.21
                @Override // com.kingsmith.miot.IChange
                public void onChange(Object obj) {
                    final JsonObject jsonObject2 = (JsonObject) obj;
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.21.1
                        @Override // java.lang.Runnable
                        public void run() {
                            MiotPlugin.this.channel.invokeMethod(AppEventsConstants.EVENT_NAME_SUBSCRIBE, ProtoMaker.c(jsonObject2, MiotPlugin.this.panelDevice.c()).toByteArray());
                        }
                    });
                }
            });
            return;
        }
        if (methodCall.method.equals("queryOTAStatus")) {
            final boolean zBooleanValue = ((Boolean) methodCall.argument("upgrading")).booleanValue();
            this.panelDevice.queryOtaStatus(zBooleanValue, new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.22
                @Override // com.kingsmith.miot.ICallback
                public void onError(Throwable th) {
                    if (BuildConfig.DEBUG) {
                        th.printStackTrace();
                    }
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.22.3
                        @Override // java.lang.Runnable
                        public void run() {
                            result.success(null);
                        }
                    });
                }

                @Override // com.kingsmith.miot.ICallback
                public void onSuccess(Object obj) {
                    MiotFirmware miotFirmware = (MiotFirmware) obj;
                    Log.e(MiotPlugin.TAG, miotFirmware.getCurrentVersion());
                    Log.e(MiotPlugin.TAG, miotFirmware.getDescription());
                    Log.e(MiotPlugin.TAG, miotFirmware.getLatestVersion());
                    Log.e(MiotPlugin.TAG, miotFirmware.getOtaStatus());
                    Log.e(MiotPlugin.TAG, "progress:" + miotFirmware.getOtaProgress());
                    if (zBooleanValue) {
                        final Protos.POTAStatus.Builder builderNewBuilder = Protos.POTAStatus.newBuilder();
                        builderNewBuilder.setStatus(miotFirmware.getOtaStatus());
                        builderNewBuilder.setProgress(miotFirmware.getOtaProgress());
                        MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.22.1
                            @Override // java.lang.Runnable
                            public void run() {
                                result.success(builderNewBuilder.build().toByteArray());
                            }
                        });
                        return;
                    }
                    final Protos.POTAFirmware.Builder builderNewBuilder2 = Protos.POTAFirmware.newBuilder();
                    builderNewBuilder2.setCurrentVersion(miotFirmware.getCurrentVersion());
                    builderNewBuilder2.setNewVersion(miotFirmware.getLatestVersion());
                    builderNewBuilder2.setDesc(miotFirmware.getDescription());
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.22.2
                        @Override // java.lang.Runnable
                        public void run() {
                            result.success(builderNewBuilder2.build().toByteArray());
                        }
                    });
                }
            });
        } else if (methodCall.method.equals("startUpgrade")) {
            this.panelDevice.startUpgrade(new ICallback() { // from class: com.kingsmith.miot.MiotPlugin.23
                @Override // com.kingsmith.miot.ICallback
                public void onError(final Throwable th) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.23.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Throwable th2 = th;
                            if (th2 instanceof HttpError) {
                                AnonymousClass23 anonymousClass23 = AnonymousClass23.this;
                                result.success(MiotPlugin.this.send(((HttpError) th2).getCode(), th.getMessage()));
                            } else {
                                AnonymousClass23 anonymousClass232 = AnonymousClass23.this;
                                result.success(MiotPlugin.this.send(-1, th2 != null ? th2.getMessage() : "unkown error"));
                            }
                        }
                    });
                }

                @Override // com.kingsmith.miot.ICallback
                public void onSuccess(Object obj) {
                    MiotPlugin.this.doInUiThread(new Runnable() { // from class: com.kingsmith.miot.MiotPlugin.23.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass23 anonymousClass23 = AnonymousClass23.this;
                            result.success(MiotPlugin.this.send(0, ""));
                        }
                    });
                }
            });
        } else if (!methodCall.method.equals("gc")) {
            result.notImplemented();
        } else {
            new MiotCloseTask().execute(new Void[0]);
            result.success(null);
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
    }

    @Override // io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
    public boolean onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        if (i2 != 1 || iArr.length <= 0 || iArr[0] != 0) {
            return false;
        }
        ICallback iCallback = this.scanCallback;
        if (iCallback != null) {
            startScan(iCallback);
        }
        return true;
    }
}
