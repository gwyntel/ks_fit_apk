package com.aliyun.alink.linksdk.channel.core.persistent.mqtt;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.channel.core.a.e;
import com.aliyun.alink.linksdk.channel.core.base.AError;
import com.aliyun.alink.linksdk.channel.core.base.ARequest;
import com.aliyun.alink.linksdk.channel.core.base.AResponse;
import com.aliyun.alink.linksdk.channel.core.base.ASend;
import com.aliyun.alink.linksdk.channel.core.base.IOnCallListener;
import com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener;
import com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener;
import com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentInitParams;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentNet;
import com.aliyun.alink.linksdk.channel.core.persistent.event.PersistentEventDispatcher;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.c;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.d;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttSubscribeRequest;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttSubscribeRequestParams;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.PersisitentNetParams;
import com.aliyun.alink.linksdk.id2.Id2ItlsSdk;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.alink.linksdk.tools.NetTools;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.eclipse.paho.client.mqttv3.AlarmMqttPingSender;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/* loaded from: classes2.dex */
public class b implements IPersisitentNet {

    /* renamed from: o, reason: collision with root package name */
    private static final Object f10888o = new Object();

    /* renamed from: p, reason: collision with root package name */
    private static String[] f10889p = {"register", "regnwl"};

    /* renamed from: a, reason: collision with root package name */
    private Context f10890a;

    /* renamed from: b, reason: collision with root package name */
    private MemoryPersistence f10891b;

    /* renamed from: c, reason: collision with root package name */
    private IMqttAsyncClient f10892c;

    /* renamed from: d, reason: collision with root package name */
    private SSLSocketFactory f10893d;

    /* renamed from: e, reason: collision with root package name */
    private MqttConnectOptions f10894e;

    /* renamed from: f, reason: collision with root package name */
    private InputStream f10895f;

    /* renamed from: g, reason: collision with root package name */
    private AtomicBoolean f10896g;

    /* renamed from: h, reason: collision with root package name */
    private AtomicBoolean f10897h;

    /* renamed from: i, reason: collision with root package name */
    private AtomicBoolean f10898i;

    /* renamed from: j, reason: collision with root package name */
    private PersistentConnectState f10899j;

    /* renamed from: k, reason: collision with root package name */
    private com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a f10900k;

    /* renamed from: l, reason: collision with root package name */
    private IOnCallListener f10901l;

    /* renamed from: m, reason: collision with root package name */
    private MqttInitParams f10902m;

    /* renamed from: n, reason: collision with root package name */
    private AtomicBoolean f10903n;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final b f10916a = new b();
    }

    private void f() {
        if (MqttConfigure.mqttRootCrtFile != null) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "init(),custom cert file");
            this.f10895f = MqttConfigure.mqttRootCrtFile;
            return;
        }
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "init(),default cert file");
        try {
            this.f10895f = this.f10890a.getAssets().open(MqttConfigure.DEFAULT_ROOTCRT);
        } catch (Exception e2) {
            com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", "setCertFile : cannot config cert file：" + e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        try {
            this.f10899j = PersistentConnectState.DISCONNECTED;
            this.f10893d = null;
            this.f10902m = null;
            com.aliyun.alink.linksdk.channel.core.b.a.c("MqttNet", "connection lost disconnect by user.");
            PersistentEventDispatcher.getInstance().broadcastMessage(2, null, null, 0, "disconnect success");
        } catch (Exception e2) {
            com.aliyun.alink.linksdk.channel.core.b.a.c("MqttNet", "destroyP(), internal error, e = " + e2.toString());
            e2.printStackTrace();
        }
    }

    private void h() throws NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException {
        this.f10891b = new MemoryPersistence();
        System.currentTimeMillis();
        String strReplace = MqttConfigure.mqttHost;
        if (TextUtils.isEmpty(strReplace)) {
            strReplace = MqttConfigure.SECURE_MODE == 8 ? MqttConfigure.DEFAULT_ITLS_HOST : MqttConfigure.DEFAULT_HOST;
        }
        if (strReplace.contains("${productKey}")) {
            strReplace = strReplace.replace("${productKey}", MqttConfigure.productKey);
        }
        if (MqttConfigure.SECURE_MODE == 3 && !strReplace.startsWith("tcp://")) {
            strReplace = "tcp://" + strReplace;
        } else if (MqttConfigure.SECURE_MODE != 3 && !strReplace.startsWith("ssl://")) {
            strReplace = "ssl://" + strReplace;
        }
        String str = MqttConfigure.clientId;
        if (TextUtils.isEmpty(str)) {
            str = MqttConfigure.deviceName + "&" + MqttConfigure.productKey;
        }
        HashMap map = new HashMap();
        map.put("productKey", MqttConfigure.productKey);
        map.put("deviceName", MqttConfigure.deviceName);
        map.put(TmpConstant.KEY_CLIENT_ID, str);
        String strA = a((String) null, true);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("|securemode=");
        sb.append(strA.contains("connwl") ? -2 : MqttConfigure.SECURE_MODE);
        sb.append(",_v=");
        sb.append(PersistentNet.getInstance().getSDKVersion());
        sb.append(",lan=Android");
        sb.append(",os=");
        sb.append(Build.VERSION.RELEASE);
        sb.append(",signmethod=");
        sb.append(MqttConfigure.SIGN_METHOD);
        sb.append(strA);
        sb.append(TextUtils.isEmpty(MqttConfigure.extraMqttClientIdItems) ? "" : MqttConfigure.extraMqttClientIdItems);
        sb.append(",ext=1|");
        String string = sb.toString();
        String str2 = "";
        String strA2 = "";
        if (!TextUtils.isEmpty(MqttConfigure.deviceSecret)) {
            str2 = MqttConfigure.deviceName + "&" + MqttConfigure.productKey;
            strA2 = a(map, MqttConfigure.deviceSecret);
        } else if (!TextUtils.isEmpty(MqttConfigure.mqttUserName) && !TextUtils.isEmpty(MqttConfigure.mqttPassWord)) {
            str2 = MqttConfigure.mqttUserName;
            strA2 = MqttConfigure.mqttPassWord;
            string = MqttConfigure.mqttClientId;
        }
        if (!TextUtils.isEmpty(MqttConfigure.clientId) && !TextUtils.isEmpty(MqttConfigure.deviceToken)) {
            str2 = MqttConfigure.deviceName + "&" + MqttConfigure.productKey;
            strA2 = MqttConfigure.deviceToken;
        }
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "mqttClientConnect mqttUsername:" + str2 + " mqttPassword:" + strA2 + " mqttClientId:" + string);
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(strA2)) {
            this.f10896g.set(false);
            this.f10897h.set(false);
            this.f10899j = PersistentConnectState.CONNECTFAIL;
            PersistentEventDispatcher.getInstance().broadcastMessage(7, null, null, 4201, "create mqtt client error empty username or password");
            return;
        }
        if (this.f10892c != null) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "clear mqttAndroidAsyncClient force.");
            try {
                IMqttAsyncClient iMqttAsyncClient = this.f10892c;
                if (iMqttAsyncClient != null) {
                    iMqttAsyncClient.disconnectForcibly();
                }
            } catch (Exception unused) {
            }
            try {
                IMqttAsyncClient iMqttAsyncClient2 = this.f10892c;
                if (iMqttAsyncClient2 != null) {
                    iMqttAsyncClient2.close();
                }
            } catch (Exception unused2) {
            }
            this.f10892c = null;
        }
        try {
            if (MqttConfigure.pingSender != null) {
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "use user define timer ping sender.");
                this.f10892c = new e(strReplace, string, this.f10891b, MqttConfigure.pingSender);
            } else if ("android".equals(MqttConfigure.pingSenderType)) {
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "use android timer ping sender.");
                this.f10892c = new e(strReplace, string, this.f10891b, new AlarmMqttPingSender(this.f10890a));
            } else {
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "use java timer ping sender.");
                this.f10892c = new e(strReplace, string, this.f10891b);
            }
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            this.f10894e = mqttConnectOptions;
            mqttConnectOptions.setMqttVersion(4);
            this.f10894e.setConnectionTimeout(10);
            if (MqttConfigure.SECURE_MODE == 2 && MqttConfigure.isCheckRootCrt) {
                synchronized (f10888o) {
                    f();
                    try {
                        SSLSocketFactory sSLSocketFactoryI = i();
                        this.f10893d = sSLSocketFactoryI;
                        this.f10894e.setSocketFactory(sSLSocketFactoryI);
                    } catch (Exception e2) {
                        com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", "create SSL Socket error" + e2.toString());
                        e2.printStackTrace();
                    }
                }
            }
            this.f10894e.setAutomaticReconnect(MqttConfigure.automaticReconnect);
            this.f10894e.setCleanSession(MqttConfigure.cleanSession);
            this.f10894e.setUserName(str2);
            this.f10894e.setPassword(strA2.toCharArray());
            this.f10894e.setKeepAliveInterval(MqttConfigure.getKeepAliveInterval());
            this.f10894e.setMaxInflight(MqttConfigure.maxInflight);
            this.f10892c.setCallback(this.f10900k);
            final HashMap map2 = new HashMap();
            map2.put("startTime-connect", String.valueOf(System.currentTimeMillis()));
            try {
                this.f10899j = PersistentConnectState.CONNECTING;
                this.f10892c.connect(this.f10894e, null, new IMqttActionListener() { // from class: com.aliyun.alink.linksdk.channel.core.persistent.mqtt.b.5
                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onFailure(IMqttToken iMqttToken, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", "mqtt connect onFailure, exce = " + th.toString());
                        b.this.f10896g.set(false);
                        b.this.f10897h.set(false);
                        b.this.f10899j = PersistentConnectState.CONNECTFAIL;
                        map2.put("endTime-connect", String.valueOf(System.currentTimeMillis()));
                        map2.put("result", "0");
                        if (th instanceof MqttException) {
                            MqttException mqttException = (MqttException) th;
                            map2.put("errorCode", String.valueOf(mqttException.getReasonCode()));
                            PersistentEventDispatcher.getInstance().broadcastMessage(7, null, null, mqttException.getReasonCode(), mqttException.toString());
                        } else {
                            PersistentEventDispatcher.getInstance().broadcastMessage(7, null, null, 4201, th.toString());
                            map2.put("errorCode", String.valueOf(4201));
                        }
                        if (b.this.f10903n.compareAndSet(false, true)) {
                            d.a("mqtt-connect", map2);
                        }
                    }

                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onSuccess(IMqttToken iMqttToken) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        com.aliyun.alink.linksdk.channel.core.b.a.b("MqttNet", "mqtt connect onSuccess");
                        if (b.this.f10896g.compareAndSet(true, false)) {
                            b.this.f10897h.set(true);
                            b.this.f10899j = PersistentConnectState.CONNECTED;
                            map2.put("endTime-connect", String.valueOf(System.currentTimeMillis()));
                            map2.put("result", "1");
                            if (b.this.f10903n.compareAndSet(false, true)) {
                                d.a("mqtt-connect", map2);
                            }
                        }
                    }
                });
                com.aliyun.alink.linksdk.channel.core.b.a.b("MqttNet", "mqtt client connect..," + strReplace);
            } catch (MqttException e3) {
                com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", " mqtt client connect error,e" + e3.toString());
                e3.printStackTrace();
                this.f10896g.set(false);
                this.f10897h.set(false);
                this.f10899j = PersistentConnectState.CONNECTFAIL;
                PersistentEventDispatcher.getInstance().broadcastMessage(7, null, null, e3.getReasonCode(), e3.toString());
            } catch (Exception e4) {
                com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", " mqtt client connect error,e" + e4.toString());
                e4.printStackTrace();
                this.f10896g.set(false);
                this.f10897h.set(false);
                this.f10899j = PersistentConnectState.CONNECTFAIL;
                PersistentEventDispatcher.getInstance().broadcastMessage(7, null, null, 4201, e4.toString());
            }
        } catch (Exception e5) {
            com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", "create mqtt client error,e" + e5.toString());
            e5.printStackTrace();
            this.f10897h.set(false);
            this.f10896g.set(false);
            this.f10899j = PersistentConnectState.CONNECTFAIL;
            PersistentEventDispatcher.getInstance().broadcastMessage(7, null, null, 4201, "create mqtt client error,e" + e5.toString());
        }
    }

    private SSLSocketFactory i() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sSLContext = SSLContext.getInstance("TLSV1.2");
        sSLContext.init(null, new TrustManager[]{new c(this.f10895f)}, null);
        return sSLContext.getSocketFactory();
    }

    @Override // com.aliyun.alink.linksdk.channel.core.base.INet
    public ASend asyncSend(ARequest aRequest, IOnCallListener iOnCallListener) {
        com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send.b bVar = new com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send.b(aRequest, iOnCallListener);
        new com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send.c().asyncSend(bVar);
        return bVar;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void destroy() throws InterruptedException {
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "destroy()");
        this.f10903n.set(false);
        try {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "before destroy with no params." + System.currentTimeMillis());
            destroy(10000L, null, new IMqttActionListener() { // from class: com.aliyun.alink.linksdk.channel.core.persistent.mqtt.b.1
                @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                }

                @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                public void onSuccess(IMqttToken iMqttToken) {
                }
            });
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "after destroy with no params." + System.currentTimeMillis());
        } catch (MqttException e2) {
            com.aliyun.alink.linksdk.channel.core.b.a.c("MqttNet", "destroy exception=" + e2);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void dynamicRegister(final Context context, PersistentInitParams persistentInitParams, final IOnCallListener iOnCallListener) throws NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException {
        String str;
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "mqttDynamicRegister() called with: persistentInitParams = [" + persistentInitParams + "], listener = [" + iOnCallListener + "]");
        this.f10891b = new MemoryPersistence();
        this.f10890a = context;
        if (!(persistentInitParams instanceof MqttInitParams)) {
            if (iOnCallListener != null) {
                AError aError = new AError();
                aError.setMsg("init params should be instance of MqttInitParams.");
                iOnCallListener.onFailed(null, aError);
                return;
            }
            return;
        }
        MqttInitParams mqttInitParams = (MqttInitParams) persistentInitParams;
        if (!mqttInitParams.checkValid() || TextUtils.isEmpty(mqttInitParams.productSecret) || TextUtils.isEmpty(a(mqttInitParams.registerType, false))) {
            if (iOnCallListener != null) {
                AError aError2 = new AError();
                aError2.setMsg("init params invalid.");
                iOnCallListener.onFailed(null, aError2);
                return;
            }
            return;
        }
        MqttConfigure.productKey = mqttInitParams.productKey;
        MqttConfigure.deviceName = mqttInitParams.deviceName;
        MqttConfigure.productSecret = mqttInitParams.productSecret;
        String strReplace = MqttConfigure.mqttHost;
        if (TextUtils.isEmpty(strReplace)) {
            if (MqttConfigure.SECURE_MODE == 8) {
                if (iOnCallListener != null) {
                    AError aError3 = new AError();
                    aError3.setMsg("init params invalid. itls do not support dynamic register.");
                    iOnCallListener.onFailed(null, aError3);
                    return;
                }
                return;
            }
            strReplace = MqttConfigure.DEFAULT_REGISTER_TLS_HOST;
        }
        if (strReplace.contains("${productKey}")) {
            strReplace = strReplace.replace("${productKey}", MqttConfigure.productKey);
        }
        if (MqttConfigure.SECURE_MODE == 3 && !strReplace.startsWith("tcp://")) {
            strReplace = "tcp://" + strReplace;
        } else if (MqttConfigure.SECURE_MODE != 3 && !strReplace.startsWith("ssl://")) {
            strReplace = "ssl://" + strReplace;
        }
        String str2 = MqttConfigure.clientId;
        if (TextUtils.isEmpty(str2)) {
            str2 = MqttConfigure.deviceName + "&" + MqttConfigure.productKey;
        }
        String strB = com.aliyun.alink.linksdk.channel.core.b.c.b();
        HashMap map = new HashMap();
        map.put("productKey", MqttConfigure.productKey);
        map.put("deviceName", MqttConfigure.deviceName);
        map.put(AlinkConstants.KEY_RANDOM, strB);
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("|securemode=");
        sb.append(MqttConfigure.SECURE_MODE);
        sb.append(a(mqttInitParams.registerType, false));
        sb.append(",random=");
        sb.append(strB);
        sb.append(",signmethod=");
        sb.append(MqttConfigure.SIGN_METHOD);
        if (TextUtils.isEmpty(MqttConfigure.registerInstanceId)) {
            str = "";
        } else {
            str = ",instanceId=" + MqttConfigure.registerInstanceId;
        }
        sb.append(str);
        sb.append("|");
        String string = sb.toString();
        String str3 = MqttConfigure.deviceName + "&" + MqttConfigure.productKey;
        String strA = a(map, MqttConfigure.productSecret);
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "mqttClientConnect mqttUsername:" + str3 + " mqttPassword:" + strA + " mqttClientId:" + string);
        if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(strA)) {
            this.f10896g.set(false);
            this.f10897h.set(false);
            if (iOnCallListener != null) {
                AError aError4 = new AError();
                aError4.setMsg("create mqtt client error empty username or password");
                iOnCallListener.onFailed(null, aError4);
                return;
            }
            return;
        }
        if (this.f10892c != null) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "clear mqttAndroidAsyncClient force.");
            try {
                IMqttAsyncClient iMqttAsyncClient = this.f10892c;
                if (iMqttAsyncClient != null) {
                    iMqttAsyncClient.disconnectForcibly();
                }
            } catch (Exception unused) {
            }
            try {
                IMqttAsyncClient iMqttAsyncClient2 = this.f10892c;
                if (iMqttAsyncClient2 != null) {
                    iMqttAsyncClient2.close();
                }
            } catch (Exception unused2) {
            }
            this.f10892c = null;
        }
        try {
            this.f10892c = new e(strReplace, string, this.f10891b);
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            this.f10894e = mqttConnectOptions;
            mqttConnectOptions.setMqttVersion(4);
            if (MqttConfigure.SECURE_MODE == 2 && MqttConfigure.isCheckRootCrt) {
                synchronized (f10888o) {
                    f();
                    try {
                        SSLSocketFactory sSLSocketFactoryI = i();
                        this.f10893d = sSLSocketFactoryI;
                        this.f10894e.setSocketFactory(sSLSocketFactoryI);
                    } catch (Exception e2) {
                        com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", "create SSL Socket error" + e2.toString());
                        e2.printStackTrace();
                    }
                }
            }
            this.f10894e.setAutomaticReconnect(false);
            this.f10894e.setCleanSession(MqttConfigure.cleanSession);
            this.f10894e.setUserName(str3);
            this.f10894e.setPassword(strA.toCharArray());
            this.f10894e.setKeepAliveInterval(MqttConfigure.getKeepAliveInterval());
            this.f10894e.setMaxInflight(MqttConfigure.maxInflight);
            this.f10892c.setCallback(this.f10900k);
            this.f10901l = iOnCallListener;
            try {
                com.aliyun.alink.linksdk.channel.core.b.a.b("MqttNet", "dynamicRegister mqtt client connect..." + strReplace);
                this.f10892c.connect(this.f10894e, null, new IMqttActionListener() { // from class: com.aliyun.alink.linksdk.channel.core.persistent.mqtt.b.4
                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onFailure(IMqttToken iMqttToken, Throwable th) {
                        com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", "dynamicRegister mqtt connect onFailure, exce = " + th.toString());
                        if (th instanceof MqttException) {
                            MqttException mqttException = (MqttException) th;
                            if (iOnCallListener != null) {
                                AError aError5 = new AError();
                                aError5.setCode(mqttException.getReasonCode());
                                aError5.setMsg(mqttException.toString());
                                iOnCallListener.onFailed(null, aError5);
                                return;
                            }
                            return;
                        }
                        if (iOnCallListener != null) {
                            AError aError6 = new AError();
                            if (NetTools.isAvailable(context)) {
                                aError6.setCode(4201);
                                aError6.setMsg("dynamicRegister mqtt connect failed. " + th.toString());
                            } else {
                                aError6.setCode(4101);
                                aError6.setMsg("dynamicRegister mqtt connect failed, invalid network. " + th.toString());
                            }
                            iOnCallListener.onFailed(null, aError6);
                        }
                    }

                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onSuccess(IMqttToken iMqttToken) {
                        com.aliyun.alink.linksdk.channel.core.b.a.b("MqttNet", "dynamicRegister mqtt connect onSuccess");
                    }
                });
            } catch (MqttException e3) {
                com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", " dynamicRegister mqtt connect error,e" + e3.toString());
                e3.printStackTrace();
                if (iOnCallListener != null) {
                    AError aError5 = new AError();
                    aError5.setCode(e3.getReasonCode());
                    aError5.setMsg("dynamicRegister mqtt connect exception, " + e3.toString());
                    iOnCallListener.onFailed(null, aError5);
                }
            } catch (Exception e4) {
                com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", "dynamicRegister mqtt connect error,e" + e4.toString());
                e4.printStackTrace();
                if (iOnCallListener != null) {
                    AError aError6 = new AError();
                    if (NetTools.isAvailable(context)) {
                        aError6.setCode(4201);
                        aError6.setMsg("dynamicRegister mqtt connect exception. " + e4.toString());
                    } else {
                        aError6.setCode(4101);
                        aError6.setMsg("dynamicRegister mqtt connect exception, invalid network. " + e4.toString());
                    }
                    iOnCallListener.onFailed(null, aError6);
                }
            }
        } catch (Exception e5) {
            com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", "create mqtt client error,e" + e5.toString());
            e5.printStackTrace();
            if (iOnCallListener != null) {
                AError aError7 = new AError();
                aError7.setMsg("create mqtt client error " + e5.toString());
                iOnCallListener.onFailed(null, aError7);
            }
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public PersistentConnectState getConnectState() {
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "getConnectState()");
        if (c() == null) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "getConnectState() client is empty");
            this.f10899j = PersistentConnectState.DISCONNECTED;
        } else {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "getConnectState() paho state = " + a().d());
            this.f10899j = a().d() ? PersistentConnectState.CONNECTED : PersistentConnectState.DISCONNECTED;
        }
        return this.f10899j;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void init(Context context, PersistentInitParams persistentInitParams) throws NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException {
        PersistentConnectState persistentConnectState;
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "init()");
        if (this.f10896g.get() || this.f10897h.get() || (persistentConnectState = this.f10899j) == PersistentConnectState.CONNECTING || persistentConnectState == PersistentConnectState.CONNECTED) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "init(), already init, ignore init call!");
            this.f10899j = PersistentConnectState.CONNECTFAIL;
            PersistentEventDispatcher.getInstance().broadcastMessage(7, null, null, 4300, "is initing or inited.");
            return;
        }
        if (context != null && persistentInitParams != null && (persistentInitParams instanceof MqttInitParams)) {
            MqttInitParams mqttInitParams = (MqttInitParams) persistentInitParams;
            if (mqttInitParams.checkValid()) {
                if (this.f10898i.get()) {
                    com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", "is deiniting, return");
                    this.f10899j = PersistentConnectState.CONNECTFAIL;
                    PersistentEventDispatcher.getInstance().broadcastMessage(7, null, null, 4302, "mqtt is deiniting");
                    return;
                }
                this.f10896g.set(true);
                this.f10897h.set(false);
                this.f10890a = context;
                this.f10902m = mqttInitParams;
                LoggerFactory.setLogger(com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.b.class.getName());
                MqttInitParams mqttInitParams2 = this.f10902m;
                MqttConfigure.productKey = mqttInitParams2.productKey;
                MqttConfigure.productSecret = mqttInitParams2.productSecret;
                MqttConfigure.deviceName = mqttInitParams2.deviceName;
                MqttConfigure.deviceSecret = mqttInitParams2.deviceSecret;
                MqttConfigure.cleanSession = !mqttInitParams2.receiveOfflineMsg;
                int i2 = mqttInitParams2.secureMode;
                MqttConfigure.SECURE_MODE = i2;
                if (i2 == 8) {
                    Id2ItlsSdk.init(context);
                }
                h();
                return;
            }
        }
        com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", "init error ,params error");
        this.f10899j = PersistentConnectState.CONNECTFAIL;
        PersistentEventDispatcher.getInstance().broadcastMessage(7, null, null, 4301, "init error ,params error");
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public boolean isDeiniting() {
        return this.f10898i.get();
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void openLog(boolean z2) {
        if (z2) {
            ALog.setLevel((byte) 1);
        } else {
            ALog.setLevel((byte) 4);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void reconnect() throws MqttException {
        IMqttAsyncClient iMqttAsyncClient = this.f10892c;
        if (iMqttAsyncClient == null || !(iMqttAsyncClient instanceof MqttAsyncClient)) {
            return;
        }
        ((MqttAsyncClient) iMqttAsyncClient).reconnect();
    }

    @Override // com.aliyun.alink.linksdk.channel.core.base.INet
    public void retry(ASend aSend) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        new com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send.c().asyncSend(aSend);
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void subscribe(String str, IOnSubscribeListener iOnSubscribeListener) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        if (TextUtils.isEmpty(str)) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "subscribe, topic is empty");
            return;
        }
        MqttSubscribeRequest mqttSubscribeRequest = new MqttSubscribeRequest();
        mqttSubscribeRequest.topic = str;
        mqttSubscribeRequest.isSubscribe = true;
        new com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send.c().asyncSend(new com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send.b(mqttSubscribeRequest, iOnSubscribeListener));
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void subscribeRrpc(String str, final IOnSubscribeRrpcListener iOnSubscribeRrpcListener) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "subscribeRrpc(),topic = " + str);
        if (TextUtils.isEmpty(str) || iOnSubscribeRrpcListener == null) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "subscribeRrpc(), params error");
            return;
        }
        subscribe(str, new IOnSubscribeListener() { // from class: com.aliyun.alink.linksdk.channel.core.persistent.mqtt.b.3
            @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener
            public boolean needUISafety() {
                return iOnSubscribeRrpcListener.needUISafety();
            }

            @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener
            public void onFailed(String str2, AError aError) {
                iOnSubscribeRrpcListener.onSubscribeFailed(str2, aError);
            }

            @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener
            public void onSuccess(String str2) {
                iOnSubscribeRrpcListener.onSubscribeSuccess(str2);
            }
        });
        if (this.f10900k != null) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "subscribeRrpc(), registerRrpcListener");
            this.f10900k.a(str, iOnSubscribeRrpcListener);
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void unSubscribe(String str, IOnSubscribeListener iOnSubscribeListener) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        if (TextUtils.isEmpty(str)) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "unSubscribe, topic is empty");
            return;
        }
        MqttSubscribeRequest mqttSubscribeRequest = new MqttSubscribeRequest();
        mqttSubscribeRequest.topic = str;
        mqttSubscribeRequest.isSubscribe = false;
        new com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send.c().asyncSend(new com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send.b(mqttSubscribeRequest, iOnSubscribeListener));
    }

    private b() {
        this.f10896g = new AtomicBoolean(false);
        this.f10897h = new AtomicBoolean(false);
        this.f10898i = new AtomicBoolean(false);
        this.f10899j = PersistentConnectState.DISCONNECTED;
        this.f10900k = new com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a();
        this.f10901l = null;
        this.f10903n = new AtomicBoolean(false);
        LoggerFactory.setLogger(com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.b.class.getName());
    }

    public PersistentInitParams b() {
        return this.f10902m;
    }

    public IMqttAsyncClient c() {
        return this.f10892c;
    }

    public boolean d() {
        try {
            IMqttAsyncClient iMqttAsyncClient = this.f10892c;
            if (iMqttAsyncClient != null) {
                return iMqttAsyncClient.isConnected();
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public Context e() {
        return this.f10890a;
    }

    public static b a() {
        return a.f10916a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        try {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "closeConnect " + str);
            IMqttAsyncClient iMqttAsyncClient = this.f10892c;
            if (iMqttAsyncClient != null) {
                iMqttAsyncClient.close();
            }
        } catch (Exception e2) {
            com.aliyun.alink.linksdk.channel.core.b.a.c("MqttNet", "closeConnect e = " + e2.toString());
            e2.printStackTrace();
        }
        this.f10892c = null;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void destroy(long j2, Object obj, Object obj2) throws InterruptedException {
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "destroyP() called with: quiesceTimeout = [" + j2 + "], userContext = [" + obj + "], callback = [" + obj2 + "] " + hashCode());
        if (obj2 instanceof IMqttActionListener) {
            final IMqttActionListener iMqttActionListener = (IMqttActionListener) obj2;
            if (this.f10896g.get()) {
                if (iMqttActionListener != null) {
                    iMqttActionListener.onFailure(null, new IllegalStateException("Please wait for init done."));
                    return;
                }
                return;
            }
            this.f10896g.set(false);
            this.f10897h.set(false);
            if (!this.f10898i.compareAndSet(false, true)) {
                if (iMqttActionListener != null) {
                    iMqttActionListener.onFailure(null, new IllegalStateException("Please wait for last deiniting to finish."));
                    return;
                }
                return;
            }
            if (c() == null) {
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "destroyP(), client is null");
                a("onClientNull -> closeConnect callback=" + iMqttActionListener);
                this.f10898i.set(false);
                com.aliyun.alink.linksdk.channel.core.b.a.b("MqttNet", "mqtt disconnect finished and callback=" + iMqttActionListener + " onFailure. " + hashCode());
                if (iMqttActionListener != null) {
                    iMqttActionListener.onSuccess(null);
                    return;
                }
                return;
            }
            try {
                MemoryPersistence memoryPersistence = this.f10891b;
                if (memoryPersistence != null) {
                    memoryPersistence.close();
                }
            } catch (Exception unused) {
            }
            this.f10891b = null;
            synchronized (f10888o) {
                try {
                    InputStream inputStream = this.f10895f;
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception unused2) {
                }
                this.f10895f = null;
            }
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            try {
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "destroyP->disconnect");
                this.f10892c.disconnect(j2, obj, new IMqttActionListener() { // from class: com.aliyun.alink.linksdk.channel.core.persistent.mqtt.b.2
                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onFailure(IMqttToken iMqttToken, Throwable th) {
                        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "onFailure() called with: iMqttToken = [" + iMqttToken + "], throwable = [" + th + "], callback = [" + iMqttActionListener + "]");
                        b bVar = b.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("onFailure -> closeConnect callback = ");
                        sb.append(iMqttActionListener);
                        sb.append(", hasCallback = ");
                        sb.append(atomicBoolean);
                        bVar.a(sb.toString());
                        if (atomicBoolean.compareAndSet(false, true)) {
                            b.this.g();
                            b.this.f10898i.set(false);
                            com.aliyun.alink.linksdk.channel.core.b.a.b("MqttNet", "mqtt disconnect finished and callback onFailure. " + hashCode());
                            IMqttActionListener iMqttActionListener2 = iMqttActionListener;
                            if (iMqttActionListener2 != null) {
                                iMqttActionListener2.onFailure(iMqttToken, th);
                            }
                        }
                        CountDownLatch countDownLatch2 = countDownLatch;
                        if (countDownLatch2 != null) {
                            countDownLatch2.countDown();
                        }
                    }

                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onSuccess(IMqttToken iMqttToken) {
                        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "onSuccess() called with: iMqttToken = [" + iMqttToken + "], callback = [" + iMqttActionListener + "]");
                        b.this.a("onSuccess -> closeConnect callback = " + iMqttActionListener + ", hasCallback = " + atomicBoolean);
                        if (atomicBoolean.compareAndSet(false, true)) {
                            b.this.g();
                            b.this.f10898i.set(false);
                            IMqttActionListener iMqttActionListener2 = iMqttActionListener;
                            if (iMqttActionListener2 != null) {
                                iMqttActionListener2.onSuccess(iMqttToken);
                            }
                        }
                        CountDownLatch countDownLatch2 = countDownLatch;
                        if (countDownLatch2 != null) {
                            countDownLatch2.countDown();
                        }
                    }
                });
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "destroyP->disconnected");
                try {
                    countDownLatch.await(j2, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                if (atomicBoolean.compareAndSet(false, true)) {
                    a("onSuccess -> closeConnect callback = " + iMqttActionListener + ", hasCallback = " + atomicBoolean);
                    g();
                    this.f10898i.set(false);
                    com.aliyun.alink.linksdk.channel.core.b.a.b("MqttNet", "mqtt disconnect finished and callback success. " + hashCode());
                    if (iMqttActionListener != null) {
                        iMqttActionListener.onSuccess(null);
                        return;
                    }
                    return;
                }
                this.f10898i.set(false);
            } catch (Exception e3) {
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "destroyP(), error, e = " + e3.toString());
                e3.printStackTrace();
                a("onFailure exception -> closeConnect callback=" + iMqttActionListener + ", hasCallback = " + atomicBoolean);
                if (atomicBoolean.compareAndSet(false, true)) {
                    this.f10898i.set(false);
                    g();
                    com.aliyun.alink.linksdk.channel.core.b.a.b("MqttNet", "mqtt disconnect finished and callback failure. " + hashCode());
                    if (iMqttActionListener != null) {
                        iMqttActionListener.onFailure(null, e3);
                    }
                }
                countDownLatch.countDown();
            }
        }
    }

    @Override // com.aliyun.alink.linksdk.channel.core.persistent.IPersisitentNet
    public void subscribe(String str, PersisitentNetParams persisitentNetParams, IOnSubscribeListener iOnSubscribeListener) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        if (TextUtils.isEmpty(str)) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttNet", "subscribe, topic is empty");
            return;
        }
        MqttSubscribeRequest mqttSubscribeRequest = new MqttSubscribeRequest();
        mqttSubscribeRequest.topic = str;
        mqttSubscribeRequest.isSubscribe = true;
        if (persisitentNetParams != null && (persisitentNetParams instanceof MqttSubscribeRequestParams)) {
            mqttSubscribeRequest.subscribeRequestParams = (MqttSubscribeRequestParams) persisitentNetParams;
        }
        new com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send.c().asyncSend(new com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send.b(mqttSubscribeRequest, iOnSubscribeListener));
    }

    public void a(PersistentConnectState persistentConnectState) {
        this.f10899j = persistentConnectState;
    }

    public void a(String str, MqttMessage mqttMessage) {
        if ((!"/ext/regnwl".equals(str) && !"/ext/register".equals(str)) || mqttMessage == null || mqttMessage.getPayload() == null || this.f10901l == null) {
            return;
        }
        AResponse aResponse = new AResponse();
        aResponse.data = mqttMessage.getPayload();
        this.f10901l.onSuccess(null, aResponse);
        this.f10901l = null;
    }

    private String a(String str, boolean z2) {
        if (z2 && MqttConfigure.SECURE_MODE == 8) {
            return ",authtype=id2";
        }
        if (z2) {
            if (TextUtils.isEmpty(MqttConfigure.deviceToken) || TextUtils.isEmpty(MqttConfigure.clientId)) {
                return "";
            }
            return ",authType=connwl";
        }
        if (!TextUtils.isEmpty(str) && !f10889p[0].equals(str)) {
            if (!f10889p[1].equals(str)) {
                return "";
            }
            return ",authType=regnwl";
        }
        return ",authType=register";
    }

    private String a(Map<String, String> map, String str) throws NoSuchAlgorithmException, InvalidKeyException {
        if (map != null && !TextUtils.isEmpty(str)) {
            String[] strArr = (String[]) map.keySet().toArray(new String[0]);
            Arrays.sort(strArr);
            StringBuilder sb = new StringBuilder();
            for (String str2 : strArr) {
                if (!"sign".equalsIgnoreCase(str2)) {
                    sb.append(str2);
                    sb.append(map.get(str2));
                }
            }
            try {
                SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes("utf-8"), MqttConfigure.SIGN_METHOD);
                Mac mac = Mac.getInstance(secretKeySpec.getAlgorithm());
                mac.init(secretKeySpec);
                return a(mac.doFinal(sb.toString().getBytes("utf-8")));
            } catch (Exception e2) {
                com.aliyun.alink.linksdk.channel.core.b.a.d("MqttNet", "hmacSign error, e" + e2.toString());
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static final String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length);
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() < 2) {
                stringBuffer.append(0);
            }
            stringBuffer.append(hexString.toUpperCase());
        }
        return stringBuffer.toString();
    }
}
