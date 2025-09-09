package com.aliyun.iot.aep.sdk.apiclient;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.aliyun.iot.aep.sdk.apiclient.adapter.APIGatewayHttpAdapterImpl;
import com.aliyun.iot.aep.sdk.apiclient.adapter.IoTHttpClientAdapter;
import com.aliyun.iot.aep.sdk.apiclient.adapter.IoTHttpClientAdapterConfig;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponseImpl;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Env;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Method;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.hook.IoTAPIHook;
import com.aliyun.iot.aep.sdk.apiclient.hook.IoTAuthProvider;
import com.aliyun.iot.aep.sdk.apiclient.hook.IoTGlobalAPIHook;
import com.aliyun.iot.aep.sdk.apiclient.hook.IoTMockProvider;
import com.aliyun.iot.aep.sdk.apiclient.hook.IoTRequestPayloadCallback;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestPayload;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestWrapper;
import com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.net.SocketFactory;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class IoTAPIClientImpl implements IoTAPIClient {

    /* renamed from: i, reason: collision with root package name */
    public static IoTAPIClientImpl f11551i;

    /* renamed from: a, reason: collision with root package name */
    public boolean f11552a = false;

    /* renamed from: b, reason: collision with root package name */
    public IoTHttpClientAdapter f11553b;

    /* renamed from: c, reason: collision with root package name */
    public InitializeConfig f11554c;

    /* renamed from: d, reason: collision with root package name */
    public h f11555d;

    /* renamed from: e, reason: collision with root package name */
    public f f11556e;

    /* renamed from: f, reason: collision with root package name */
    public e f11557f;

    /* renamed from: g, reason: collision with root package name */
    public i f11558g;

    /* renamed from: h, reason: collision with root package name */
    public WeakReference<Context> f11559h;

    public static class InitializeConfig {
        public IoTHttpClientAdapter adapter;
        public Env apiEnv;

        @Deprecated
        public String appKey;
        public String authCode;
        public long connectTimeout;
        public EventListener eventListener;
        public String host;
        public long readTimeout;
        public SocketFactory socketFactory;
        public long writeTimeout;
        public boolean isHttpConnectionRetry = true;
        public boolean isDebug = false;
    }

    public class a implements IoTRequestPayloadCallback {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ IoTRequest f11560a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ IoTCallback f11561b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ IoTRequestPayload f11562c;

        public a(IoTRequest ioTRequest, IoTCallback ioTCallback, IoTRequestPayload ioTRequestPayload) {
            this.f11560a = ioTRequest;
            this.f11561b = ioTCallback;
            this.f11562c = ioTRequestPayload;
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
        public void onFailure(IoTRequest ioTRequest, Exception exc) {
            IoTAPIClientImpl.this.f11557f.a(exc);
            IoTAPIClientImpl.this.f11558g.a(this.f11561b, this.f11560a, exc);
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.hook.IoTRequestPayloadCallback
        public void onIoTRequestPayloadReady() {
            IoTRequestWrapper ioTRequestWrapper = new IoTRequestWrapper();
            ioTRequestWrapper.request = this.f11560a;
            ioTRequestWrapper.callback = this.f11561b;
            ioTRequestWrapper.payload = this.f11562c;
            IoTAPIClientImpl.this.a(ioTRequestWrapper);
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
        public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
            IoTAPIClientImpl.this.f11557f.a(ioTResponse);
            IoTAPIClientImpl.this.f11558g.a(this.f11561b, ioTRequest, ioTResponse);
        }
    }

    public class b implements IoTCallback {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ IoTRequestWrapper f11564a;

        public b(IoTRequestWrapper ioTRequestWrapper) {
            this.f11564a = ioTRequestWrapper;
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
        public void onFailure(IoTRequest ioTRequest, Exception exc) {
            IoTAPIClientImpl.this.f11558g.a(this.f11564a, exc);
            IoTAPIClientImpl.this.a(this.f11564a, exc);
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
        public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
            IoTAPIClientImpl.this.f11558g.a(this.f11564a, ioTResponse);
            IoTResponseImpl ioTResponseImpl = new IoTResponseImpl();
            if (200 != ioTResponse.getCode()) {
                ioTResponseImpl.setId(ioTResponse.getId());
                ioTResponseImpl.setCode(ioTResponse.getCode());
                ioTResponseImpl.setMessage(ioTResponse.getMessage());
                ioTResponseImpl.setLocalizedMsg(ioTResponse.getLocalizedMsg());
            } else if (ioTResponse.getData() instanceof JSONObject) {
                JSONObject jSONObject = (JSONObject) ioTResponse.getData();
                ioTResponseImpl.setId(jSONObject.optString("id"));
                ioTResponseImpl.setCode(jSONObject.optInt("code"));
                ioTResponseImpl.setMessage(jSONObject.optString("message"));
                ioTResponseImpl.setLocalizedMsg(jSONObject.optString(AlinkConstants.KEY_LOCALIZED_MSG));
                ioTResponseImpl.setData(jSONObject.opt("data"));
                ioTResponseImpl.setRawData(ioTResponse.getRawData());
            }
            IoTAPIClientImpl.this.a(this.f11564a, ioTResponseImpl);
        }
    }

    public class c implements IoTCallback {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ IoTRequestWrapper f11566a;

        public c(IoTRequestWrapper ioTRequestWrapper) {
            this.f11566a = ioTRequestWrapper;
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
        public void onFailure(IoTRequest ioTRequest, Exception exc) {
            IoTAPIClientImpl.this.f11557f.a(exc);
            i iVar = IoTAPIClientImpl.this.f11558g;
            IoTRequestWrapper ioTRequestWrapper = this.f11566a;
            iVar.a(ioTRequestWrapper.callback, ioTRequestWrapper.request, exc);
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
        public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
            IoTAPIClientImpl.this.f11557f.a(ioTResponse);
            i iVar = IoTAPIClientImpl.this.f11558g;
            IoTRequestWrapper ioTRequestWrapper = this.f11566a;
            iVar.a(ioTRequestWrapper.callback, ioTRequestWrapper.request, ioTResponse);
        }
    }

    public class d implements IoTCallback {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ IoTRequestWrapper f11568a;

        public d(IoTRequestWrapper ioTRequestWrapper) {
            this.f11568a = ioTRequestWrapper;
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
        public void onFailure(IoTRequest ioTRequest, Exception exc) {
            IoTAPIClientImpl.this.f11557f.a(exc);
            i iVar = IoTAPIClientImpl.this.f11558g;
            IoTRequestWrapper ioTRequestWrapper = this.f11568a;
            iVar.a(ioTRequestWrapper.callback, ioTRequestWrapper.request, exc);
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
        public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
            IoTAPIClientImpl.this.f11557f.a(ioTResponse);
            i iVar = IoTAPIClientImpl.this.f11558g;
            IoTRequestWrapper ioTRequestWrapper = this.f11568a;
            iVar.a(ioTRequestWrapper.callback, ioTRequestWrapper.request, ioTResponse);
        }
    }

    public class e {
        public e() {
        }

        public /* synthetic */ e(IoTAPIClientImpl ioTAPIClientImpl, a aVar) {
            this();
        }

        public final void b(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("feature can not be empty");
            }
        }

        public final void a() {
            if (!IoTAPIClientImpl.this.f11552a) {
                throw new RuntimeException("initialize first");
            }
        }

        public final void a(IoTRequest ioTRequest, IoTCallback ioTCallback) {
            if (ioTRequest != null) {
                if (Method.POST == ioTRequest.getMethod()) {
                    if (!TextUtils.isEmpty(ioTRequest.getPath())) {
                        if (TextUtils.isEmpty(ioTRequest.getAPIVersion())) {
                            throw new IllegalArgumentException("apiVersion can not be empty");
                        }
                        if (ioTCallback == null) {
                            throw new IllegalArgumentException("callback can not be null");
                        }
                        return;
                    }
                    throw new IllegalArgumentException("path can not be empty !");
                }
                throw new IllegalArgumentException("only support POST at present");
            }
            throw new IllegalArgumentException("request can not be null");
        }

        public final void a(IoTResponse ioTResponse) {
            if (ioTResponse == null) {
                throw new IllegalArgumentException("response can not be null");
            }
        }

        public final void a(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("authType can not be empty");
            }
        }

        public final void a(IoTAuthProvider ioTAuthProvider) {
            if (ioTAuthProvider == null) {
                throw new IllegalArgumentException("ioTAuthProvider can not be null");
            }
        }

        public final void a(IoTMockProvider ioTMockProvider) {
            if (ioTMockProvider == null) {
                throw new IllegalArgumentException("ioTMockProvider can not be null");
            }
        }

        public final void a(String str, IoTAPIHook ioTAPIHook) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("feature can not be empty");
            }
            if (ioTAPIHook != null) {
                if (IoTAPIClientImpl.this.f11556e.a(str)) {
                    throw new IllegalArgumentException(String.format(Locale.ENGLISH, "hook with %s has been registered", str));
                }
                return;
            }
            throw new IllegalArgumentException("hook can not be empty");
        }

        public final void a(Exception exc) {
            if (exc == null) {
                throw new IllegalArgumentException("e can not be null");
            }
        }

        public final void a(Tracker tracker) {
            if (tracker == null) {
                throw new IllegalArgumentException("trackerManager can not be null");
            }
        }
    }

    public class f {

        /* renamed from: a, reason: collision with root package name */
        public IoTGlobalAPIHook f11571a;

        /* renamed from: b, reason: collision with root package name */
        public Map<String, IoTAPIHook> f11572b;

        public class a implements IoTRequestPayloadCallback {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ IoTRequest f11573a;

            /* renamed from: b, reason: collision with root package name */
            public final /* synthetic */ IoTRequestPayload f11574b;

            /* renamed from: c, reason: collision with root package name */
            public final /* synthetic */ IoTRequestPayloadCallback f11575c;

            public a(IoTRequest ioTRequest, IoTRequestPayload ioTRequestPayload, IoTRequestPayloadCallback ioTRequestPayloadCallback) {
                this.f11573a = ioTRequest;
                this.f11574b = ioTRequestPayload;
                this.f11575c = ioTRequestPayloadCallback;
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onFailure(IoTRequest ioTRequest, Exception exc) {
                f.this.f11571a.onInterceptFailure(ioTRequest, this.f11574b, exc, this.f11575c);
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.hook.IoTRequestPayloadCallback
            public void onIoTRequestPayloadReady() {
                f.this.f11571a.onInterceptSend(this.f11573a, this.f11574b, this.f11575c);
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                f.this.f11571a.onInterceptResponse(ioTRequest, this.f11574b, ioTResponse, this.f11575c);
            }
        }

        public class b implements IoTCallback {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ IoTRequestPayload f11577a;

            /* renamed from: b, reason: collision with root package name */
            public final /* synthetic */ IoTCallback f11578b;

            public b(IoTRequestPayload ioTRequestPayload, IoTCallback ioTCallback) {
                this.f11577a = ioTRequestPayload;
                this.f11578b = ioTCallback;
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onFailure(IoTRequest ioTRequest, Exception exc) {
                f.this.f11571a.onInterceptFailure(ioTRequest, this.f11577a, exc, this.f11578b);
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                f.this.f11571a.onInterceptResponse(ioTRequest, this.f11577a, ioTResponse, this.f11578b);
            }
        }

        public f(IoTAPIClientImpl ioTAPIClientImpl) {
            this.f11571a = new IoTGlobalAPIHook();
            this.f11572b = new HashMap();
        }

        public final void b(String str) {
            this.f11572b.remove(str);
        }

        public /* synthetic */ f(IoTAPIClientImpl ioTAPIClientImpl, a aVar) {
            this(ioTAPIClientImpl);
        }

        public final void a(String str, IoTAPIHook ioTAPIHook) {
            this.f11572b.put(str, ioTAPIHook);
        }

        public final boolean a(String str) {
            return this.f11572b.containsKey(str);
        }

        public final IoTRequestPayloadCallback a(IoTRequest ioTRequest, IoTRequestPayload ioTRequestPayload, IoTRequestPayloadCallback ioTRequestPayloadCallback) {
            return new a(ioTRequest, ioTRequestPayload, ioTRequestPayloadCallback);
        }

        public final void a(String str, IoTRequest ioTRequest, IoTRequestPayload ioTRequestPayload, IoTRequestPayloadCallback ioTRequestPayloadCallback) {
            IoTRequestPayloadCallback ioTRequestPayloadCallbackA = a(ioTRequest, ioTRequestPayload, ioTRequestPayloadCallback);
            IoTAPIHook ioTAPIHook = this.f11572b.get(str);
            if (ioTAPIHook == null) {
                ioTRequestPayloadCallbackA.onIoTRequestPayloadReady();
                return;
            }
            try {
                ioTAPIHook.onInterceptSend(ioTRequest, ioTRequestPayload, ioTRequestPayloadCallbackA);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        public final IoTCallback a(IoTRequestPayload ioTRequestPayload, IoTCallback ioTCallback) {
            return new b(ioTRequestPayload, ioTCallback);
        }

        public final void a(String str, IoTRequestWrapper ioTRequestWrapper, Exception exc, IoTCallback ioTCallback) {
            IoTCallback ioTCallbackA = a(ioTRequestWrapper.payload, ioTCallback);
            IoTAPIHook ioTAPIHook = this.f11572b.get(str);
            IoTRequest ioTRequest = ioTRequestWrapper.request;
            IoTRequestPayload ioTRequestPayload = ioTRequestWrapper.payload;
            if (ioTAPIHook == null) {
                ioTCallbackA.onFailure(ioTRequest, exc);
                return;
            }
            try {
                ioTAPIHook.onInterceptFailure(ioTRequest, ioTRequestPayload, exc, ioTCallbackA);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        public final void a(String str, IoTRequestWrapper ioTRequestWrapper, IoTResponse ioTResponse, IoTCallback ioTCallback) {
            IoTCallback ioTCallbackA = a(ioTRequestWrapper.payload, ioTCallback);
            IoTAPIHook ioTAPIHook = this.f11572b.get(str);
            IoTRequest ioTRequest = ioTRequestWrapper.request;
            IoTRequestPayload ioTRequestPayload = ioTRequestWrapper.payload;
            if (ioTAPIHook == null) {
                ioTCallbackA.onResponse(ioTRequest, ioTResponse);
                return;
            }
            try {
                ioTAPIHook.onInterceptResponse(ioTRequest, ioTRequestPayload, ioTResponse, ioTCallbackA);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static class g {
        public g() {
        }

        public /* synthetic */ g(a aVar) {
            this();
        }

        public final void a(String str) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            if ("api-performance.aliplus.com".equalsIgnoreCase(str)) {
                try {
                    Field declaredField = IoTRequestBuilder.class.getDeclaredField("defaultScheme");
                    if (declaredField != null) {
                        declaredField.setAccessible(true);
                        declaredField.set(null, Scheme.HTTP);
                    }
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                } catch (NoSuchFieldException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public class h {

        /* renamed from: a, reason: collision with root package name */
        public List<Tracker> f11580a;

        public h(IoTAPIClientImpl ioTAPIClientImpl) {
            this.f11580a = new LinkedList();
        }

        public final void b(Tracker tracker) {
            synchronized (this.f11580a) {
                try {
                    if (this.f11580a.contains(tracker)) {
                        this.f11580a.remove(tracker);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public /* synthetic */ h(IoTAPIClientImpl ioTAPIClientImpl, a aVar) {
            this(ioTAPIClientImpl);
        }

        public final void a(Tracker tracker) {
            synchronized (this.f11580a) {
                try {
                    if (this.f11580a.contains(tracker)) {
                        return;
                    }
                    this.f11580a.add(tracker);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void a() {
            synchronized (this.f11580a) {
                this.f11580a.clear();
            }
        }

        public final void a(IoTRequest ioTRequest) {
            ArrayList arrayList = new ArrayList();
            synchronized (this.f11580a) {
                arrayList.addAll(this.f11580a);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                try {
                    ((Tracker) it.next()).onSend(ioTRequest);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        public final void a(IoTRequest ioTRequest, Exception exc) {
            ArrayList arrayList = new ArrayList();
            synchronized (this.f11580a) {
                arrayList.addAll(this.f11580a);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                try {
                    ((Tracker) it.next()).onFailure(ioTRequest, exc);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        public final void a(IoTRequest ioTRequest, IoTResponse ioTResponse) {
            ArrayList arrayList = new ArrayList();
            synchronized (this.f11580a) {
                arrayList.addAll(this.f11580a);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                try {
                    ((Tracker) it.next()).onResponse(ioTRequest, ioTResponse);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        public final void a(IoTRequestWrapper ioTRequestWrapper) {
            ArrayList arrayList = new ArrayList();
            synchronized (this.f11580a) {
                arrayList.addAll(this.f11580a);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                try {
                    ((Tracker) it.next()).onRealSend(ioTRequestWrapper);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        public final void a(IoTRequestWrapper ioTRequestWrapper, Exception exc) {
            ArrayList arrayList = new ArrayList();
            synchronized (this.f11580a) {
                arrayList.addAll(this.f11580a);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                try {
                    ((Tracker) it.next()).onRawFailure(ioTRequestWrapper, exc);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        public final void a(IoTRequestWrapper ioTRequestWrapper, IoTResponse ioTResponse) {
            ArrayList arrayList = new ArrayList();
            synchronized (this.f11580a) {
                arrayList.addAll(this.f11580a);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                try {
                    ((Tracker) it.next()).onRawResponse(ioTRequestWrapper, ioTResponse);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class i {
        public i() {
        }

        public /* synthetic */ i(IoTAPIClientImpl ioTAPIClientImpl, a aVar) {
            this();
        }

        public final void a(IoTRequest ioTRequest) {
            IoTAPIClientImpl.this.f11555d.a(ioTRequest);
        }

        public final void a(IoTCallback ioTCallback, IoTRequest ioTRequest, Exception exc) {
            IoTAPIClientImpl.this.f11555d.a(ioTRequest, exc);
            ioTCallback.onFailure(ioTRequest, exc);
        }

        public final void a(IoTCallback ioTCallback, IoTRequest ioTRequest, IoTResponse ioTResponse) {
            IoTAPIClientImpl.this.f11555d.a(ioTRequest, ioTResponse);
            ioTCallback.onResponse(ioTRequest, ioTResponse);
        }

        public final void a(IoTRequestWrapper ioTRequestWrapper) {
            IoTAPIClientImpl.this.f11555d.a(ioTRequestWrapper);
        }

        public final void a(IoTRequestWrapper ioTRequestWrapper, Exception exc) {
            IoTAPIClientImpl.this.f11555d.a(ioTRequestWrapper, exc);
        }

        public final void a(IoTRequestWrapper ioTRequestWrapper, IoTResponse ioTResponse) {
            IoTAPIClientImpl.this.f11555d.a(ioTRequestWrapper, ioTResponse);
        }
    }

    public static IoTAPIClientImpl getInstance() {
        if (f11551i == null) {
            f11551i = new IoTAPIClientImpl();
        }
        return f11551i;
    }

    public void clearTracker() {
        this.f11555d.a();
    }

    public Context getAppContext() {
        WeakReference<Context> weakReference = this.f11559h;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public String getAuthCode() {
        if (this.f11552a) {
            return this.f11554c.authCode;
        }
        throw new RuntimeException("sdk not initialize !");
    }

    public String getDefaultHost() {
        if (this.f11552a) {
            return this.f11554c.host;
        }
        throw new RuntimeException("sdk not initialize !");
    }

    public OkHttpClient getOkHttpClient() {
        if (!this.f11552a) {
            throw new IllegalStateException("apiclient not initialized.");
        }
        IoTHttpClientAdapter ioTHttpClientAdapter = this.f11553b;
        if (ioTHttpClientAdapter instanceof APIGatewayHttpAdapterImpl) {
            return ((APIGatewayHttpAdapterImpl) ioTHttpClientAdapter).getOkHttpClient();
        }
        return null;
    }

    public boolean hasInited() {
        return this.f11552a;
    }

    public boolean hasIoTAUthProvider(String str) {
        this.f11557f.a();
        this.f11557f.a(str);
        return a("auth/" + str);
    }

    public boolean hasIoTMockProvider(String str) {
        this.f11557f.a();
        this.f11557f.a(str);
        return a("mock/" + str);
    }

    public void init(Context context, InitializeConfig initializeConfig) {
        if (this.f11552a) {
            throw new RuntimeException("can not duplicated initialize !");
        }
        a aVar = null;
        this.f11557f = new e(this, aVar);
        this.f11558g = new i(this, aVar);
        IoTHttpClientAdapter aPIGatewayHttpAdapterImpl = initializeConfig.adapter;
        if (aPIGatewayHttpAdapterImpl == null) {
            IoTHttpClientAdapterConfig.Builder debug = new IoTHttpClientAdapterConfig.Builder().setAppKey(initializeConfig.appKey).setDefaultHost(initializeConfig.host).setApiEnv(initializeConfig.apiEnv).setAuthCode(initializeConfig.authCode).setConnectTimeout(initializeConfig.connectTimeout).setReadTimeout(initializeConfig.readTimeout).setWriteTimeout(initializeConfig.writeTimeout).setEventListener(initializeConfig.eventListener).setSocketFactory(initializeConfig.socketFactory).setHttpConnectionRetry(initializeConfig.isHttpConnectionRetry).setDebug(initializeConfig.isDebug);
            if (TextUtils.isEmpty(initializeConfig.authCode)) {
                initializeConfig.authCode = "114d";
                debug.setAuthCode("114d");
            }
            if (Utils.hasSecurityGuardDep()) {
                APIGatewayHttpAdapterImpl.checkSecurityPicture(context, initializeConfig.authCode);
            }
            if (initializeConfig.appKey == null) {
                String appKey = APIGatewayHttpAdapterImpl.getAppKey(context, initializeConfig.authCode);
                initializeConfig.appKey = appKey;
                debug.setAppKey(appKey);
            }
            aPIGatewayHttpAdapterImpl = new APIGatewayHttpAdapterImpl((Application) context.getApplicationContext(), debug.build());
        }
        if (TextUtils.isEmpty(initializeConfig.appKey)) {
            throw new IllegalArgumentException("appKey can not be empty !");
        }
        if (TextUtils.isEmpty(initializeConfig.host)) {
            throw new IllegalArgumentException("host can not be empty !");
        }
        new g(aVar).a(initializeConfig.host);
        this.f11553b = aPIGatewayHttpAdapterImpl;
        this.f11556e = new f(this, aVar);
        this.f11555d = new h(this, aVar);
        this.f11559h = new WeakReference<>(context.getApplicationContext());
        this.f11552a = true;
        this.f11554c = initializeConfig;
    }

    public void registerIoTAuthProvider(String str, IoTAuthProvider ioTAuthProvider) {
        this.f11557f.a();
        this.f11557f.a(str);
        this.f11557f.a(ioTAuthProvider);
        a("auth/" + str, ioTAuthProvider);
    }

    public void registerMockProvider(String str, IoTMockProvider ioTMockProvider) {
        this.f11557f.a();
        this.f11557f.a(ioTMockProvider);
        a("mock/" + str, ioTMockProvider);
    }

    public void registerTracker(Tracker tracker) {
        this.f11557f.a(tracker);
        this.f11555d.a(tracker);
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient
    public void send(IoTRequest ioTRequest, IoTCallback ioTCallback) {
        this.f11557f.a();
        this.f11557f.a(ioTRequest, ioTCallback);
        this.f11558g.a(ioTRequest);
        a(ioTRequest, ioTCallback);
    }

    public void setDefaultHost(String str) {
        if (!this.f11552a) {
            throw new RuntimeException("sdk not initialize !");
        }
        this.f11554c.host = str;
        IoTHttpClientAdapter ioTHttpClientAdapter = this.f11553b;
        if (ioTHttpClientAdapter != null) {
            ioTHttpClientAdapter.setDefaultHost(str);
        }
    }

    public void setLanguage(String str) {
        this.f11556e.f11571a.setLanguage(str);
    }

    @Deprecated
    public void setPerformanceTracker(Tracker tracker) {
        if (tracker == null) {
            return;
        }
        registerTracker(tracker);
    }

    public void unregisterIoTAuthProvider(String str) {
        this.f11557f.a();
        this.f11557f.a(str);
        b("auth/" + str);
    }

    public void unregisterIoTMockProvider(String str) {
        this.f11557f.a();
        b("mock/" + str);
    }

    public void unregisterTracker(Tracker tracker) {
        this.f11557f.a(tracker);
        this.f11555d.b(tracker);
    }

    public final void b(String str) {
        this.f11557f.b(str);
        this.f11556e.b(str);
    }

    public final void a(String str, IoTAPIHook ioTAPIHook) {
        this.f11557f.a(str, ioTAPIHook);
        this.f11556e.a(str, ioTAPIHook);
    }

    public final boolean a(String str) {
        this.f11557f.b(str);
        return this.f11556e.a(str);
    }

    public final IoTRequestPayload a(IoTRequest ioTRequest) {
        IoTRequestPayload ioTRequestPayload = new IoTRequestPayload(ioTRequest.getId(), "1.0");
        ioTRequestPayload.getRequest().put("apiVer", ioTRequest.getAPIVersion());
        ioTRequestPayload.getParams().putAll(ioTRequest.getParams());
        return ioTRequestPayload;
    }

    public final void a(IoTRequest ioTRequest, IoTCallback ioTCallback) {
        String str;
        IoTRequestPayload ioTRequestPayloadA = a(ioTRequest);
        String mockType = ioTRequest.getMockType();
        String authType = ioTRequest.getAuthType();
        if (!TextUtils.isEmpty(mockType)) {
            str = "mock/" + mockType;
        } else if (!TextUtils.isEmpty(authType)) {
            str = "auth/" + authType;
            if (!a(str)) {
                this.f11558g.a(ioTCallback, ioTRequest, new IllegalArgumentException(String.format(Locale.ENGLISH, "unsupported auth type %s, maybe you forgot to register IoTAuthProvider", authType)));
                return;
            }
        } else {
            str = "";
        }
        this.f11556e.a(str, ioTRequest, ioTRequestPayloadA, new a(ioTRequest, ioTCallback, ioTRequestPayloadA));
    }

    public final void a(IoTRequestWrapper ioTRequestWrapper) {
        this.f11553b.send(ioTRequestWrapper, new b(ioTRequestWrapper));
        this.f11558g.a(ioTRequestWrapper);
    }

    public final void a(IoTRequestWrapper ioTRequestWrapper, Exception exc) {
        String str;
        String authType = ioTRequestWrapper.request.getAuthType();
        if (!TextUtils.isEmpty(authType)) {
            str = "auth/" + authType;
        } else {
            str = "";
        }
        this.f11556e.a(str, ioTRequestWrapper, exc, new c(ioTRequestWrapper));
    }

    public final void a(IoTRequestWrapper ioTRequestWrapper, IoTResponse ioTResponse) {
        String authType = ioTRequestWrapper.request.getAuthType();
        if (TextUtils.isEmpty(authType)) {
            this.f11558g.a(ioTRequestWrapper.callback, ioTRequestWrapper.request, ioTResponse);
            return;
        }
        String str = "auth/" + authType;
        if (!a(str)) {
            this.f11558g.a(ioTRequestWrapper.callback, ioTRequestWrapper.request, new IllegalArgumentException(String.format(Locale.ENGLISH, "unsupported auth type %s, maybe you forgot to register IoTAuthProvider", authType)));
        } else {
            this.f11556e.a(str, ioTRequestWrapper, ioTResponse, new d(ioTRequestWrapper));
        }
    }
}
