package com.aliyun.alink.linksdk.channel.mobile.b;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.util.HttpConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent;
import com.aliyun.alink.linksdk.channel.mobile.api.MobileConnectConfig;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientImpl;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Env;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestWrapper;
import com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f11002a = false;

    /* renamed from: b, reason: collision with root package name */
    private static String f11003b = "";

    /* renamed from: c, reason: collision with root package name */
    private static String f11004c = "1.0.0";

    /* renamed from: d, reason: collision with root package name */
    private static Context f11005d = null;

    /* renamed from: e, reason: collision with root package name */
    private static IoTAPIClient f11006e = null;

    /* renamed from: f, reason: collision with root package name */
    private static String f11007f = null;

    /* renamed from: g, reason: collision with root package name */
    private static String f11008g = null;

    /* renamed from: h, reason: collision with root package name */
    private static String f11009h = null;

    /* renamed from: i, reason: collision with root package name */
    private static String f11010i = "";

    public interface a {
        void a(l lVar);

        void a(String str);
    }

    public static String a() {
        String defaultHost = IoTAPIClientImpl.getInstance().getDefaultHost();
        if (TextUtils.isEmpty(defaultHost)) {
            defaultHost = "api.link.aliyun.com";
        }
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileAuthHttpRequest", "getDefaulHost(), " + defaultHost);
        return defaultHost;
    }

    /* renamed from: com.aliyun.alink.linksdk.channel.mobile.b.b$b, reason: collision with other inner class name */
    private static class C0073b implements Tracker {

        /* renamed from: a, reason: collision with root package name */
        final String f11011a;

        private C0073b() {
            this.f11011a = "APIGatewaySDKDelegate.Tracker";
        }

        private static String a(IoTRequest ioTRequest) {
            StringBuilder sb = new StringBuilder("Request:");
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            sb.append("url:");
            sb.append(ioTRequest.getScheme());
            sb.append(HttpConstant.SCHEME_SPLIT);
            sb.append(ioTRequest.getHost() == null ? "" : ioTRequest.getHost());
            sb.append(ioTRequest.getPath());
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            sb.append("apiVersion:");
            sb.append(ioTRequest.getAPIVersion());
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            sb.append("params:");
            sb.append(ioTRequest.getParams() != null ? JSON.toJSONString(ioTRequest.getParams()) : "");
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            return sb.toString();
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker
        public void onFailure(IoTRequest ioTRequest, Exception exc) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.b("APIGatewaySDKDelegate.Tracker", "onFailure:\r\n" + a(ioTRequest) + "ERROR-MESSAGE:" + exc.getMessage());
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker
        public void onRawFailure(IoTRequestWrapper ioTRequestWrapper, Exception exc) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("APIGatewaySDKDelegate.Tracker", "onRawFailure:\r\n" + a(ioTRequestWrapper) + "ERROR-MESSAGE:" + exc.getMessage());
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker
        public void onRawResponse(IoTRequestWrapper ioTRequestWrapper, IoTResponse ioTResponse) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("APIGatewaySDKDelegate.Tracker", "onRawResponse:\r\n" + a(ioTRequestWrapper) + a(ioTResponse));
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker
        public void onRealSend(IoTRequestWrapper ioTRequestWrapper) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("APIGatewaySDKDelegate.Tracker", "onRealSend:\r\n" + a(ioTRequestWrapper));
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker
        public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.b("APIGatewaySDKDelegate.Tracker", "onResponse:\r\n" + a(ioTRequest) + a(ioTResponse));
        }

        @Override // com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker
        public void onSend(IoTRequest ioTRequest) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.b("APIGatewaySDKDelegate.Tracker", "onSend:\r\n" + a(ioTRequest));
        }

        /* synthetic */ C0073b(c cVar) {
            this();
        }

        private static String a(IoTRequestWrapper ioTRequestWrapper) {
            IoTRequest ioTRequest = ioTRequestWrapper.request;
            StringBuilder sb = new StringBuilder("Request:");
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            sb.append("id:");
            sb.append(ioTRequestWrapper.payload.getId());
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            sb.append("url:");
            sb.append(ioTRequest.getScheme());
            sb.append(HttpConstant.SCHEME_SPLIT);
            sb.append(ioTRequestWrapper.request.getHost());
            sb.append(ioTRequest.getPath());
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            sb.append("apiVersion:");
            sb.append(ioTRequest.getAPIVersion());
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            sb.append("params:");
            sb.append(ioTRequest.getParams() == null ? "" : JSON.toJSONString(ioTRequest.getParams()));
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            return sb.toString();
        }

        private static String a(IoTResponse ioTResponse) {
            StringBuilder sb = new StringBuilder("Response:");
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            sb.append("id:");
            sb.append(ioTResponse.getId());
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            sb.append("code:");
            sb.append(ioTResponse.getCode());
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            sb.append("message:");
            sb.append(ioTResponse.getMessage());
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            sb.append("localizedMsg:");
            sb.append(ioTResponse.getLocalizedMsg());
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            sb.append("data:");
            sb.append(ioTResponse.getData() == null ? "" : ioTResponse.getData().toString());
            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
            return sb.toString();
        }
    }

    public static void a(Context context, MobileConnectConfig mobileConnectConfig, a aVar) {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileAuthHttpRequest", "request()");
        if (context == null || mobileConnectConfig == null || !mobileConnectConfig.checkValid() || aVar == null) {
            return;
        }
        f11005d = context;
        f11009h = mobileConnectConfig.appkey;
        Scheme scheme = Scheme.HTTPS;
        f11003b = a();
        if (!TextUtils.isEmpty(mobileConnectConfig.authServer)) {
            f11003b = mobileConnectConfig.authServer;
        }
        if (f11003b.equals("api-performance.aliplus.com")) {
            scheme = Scheme.HTTP;
        }
        if (!TextUtils.isEmpty(mobileConnectConfig.securityGuardAuthcode)) {
            f11010i = mobileConnectConfig.securityGuardAuthcode;
        }
        if (f11007f == null) {
            f11007f = com.aliyun.alink.linksdk.channel.mobile.c.a.a(32);
        }
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileAuthHttpRequest", "request(), deviceSn = " + f11007f);
        if (f11008g == null) {
            f11008g = com.aliyun.alink.linksdk.channel.mobile.c.a.a(8);
        }
        HashMap map = new HashMap();
        String str = System.currentTimeMillis() + "";
        map.put("appKey", f11009h);
        map.put(com.alipay.sdk.m.t.a.f9743k, str);
        map.put(TmpConstant.KEY_CLIENT_ID, f11008g);
        map.put("deviceSn", f11007f);
        String strA = a(map);
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileAuthHttpRequest", "signed str = " + strA);
        if (!TextUtils.isEmpty(strA)) {
            map.put("sign", strA);
        }
        map.remove("appKey");
        try {
            IoTRequest ioTRequestBuild = new IoTRequestBuilder().setScheme(scheme).setHost(f11003b).setPath("/app/aepauth/handle").setApiVersion(f11004c).addParam("authInfo", (Map) map).build();
            if (f11006e == null) {
                try {
                    IoTAPIClientImpl.InitializeConfig initializeConfig = new IoTAPIClientImpl.InitializeConfig();
                    initializeConfig.appKey = f11009h;
                    initializeConfig.host = f11003b;
                    initializeConfig.apiEnv = Env.RELEASE;
                    if (f11002a) {
                        IoTAPIClientImpl.getInstance().setPerformanceTracker(new C0073b(null));
                    }
                    IoTAPIClientImpl.getInstance().init(context, initializeConfig);
                } catch (Exception e2) {
                    com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileAuthHttpRequest", "init api gateway error," + e2.toString());
                }
                f11006e = new IoTAPIClientFactory().getClient();
            }
            f11006e.send(ioTRequestBuild, new c(aVar));
        } catch (Exception e3) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileAuthHttpRequest", "request error, e = " + e3.toString());
            e3.printStackTrace();
            aVar.a(e3.toString());
        }
    }

    private static String a(Map<String, String> map) {
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("appKey");
        arrayList.add(TmpConstant.KEY_CLIENT_ID);
        arrayList.add("deviceSn");
        arrayList.add(com.alipay.sdk.m.t.a.f9743k);
        String strA = "";
        for (String str : arrayList) {
            strA = a(strA, str, map.get(str));
        }
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileAuthHttpRequest", "sign(), toSignStr = " + strA);
        try {
            ISecureSignatureComponent secureSignatureComp = SecurityGuardManager.getInstance(f11005d).getSecureSignatureComp();
            HashMap map2 = new HashMap();
            map2.put("INPUT", strA);
            SecurityGuardParamContext securityGuardParamContext = new SecurityGuardParamContext();
            securityGuardParamContext.appKey = f11009h;
            securityGuardParamContext.paramMap = map2;
            securityGuardParamContext.requestType = 3;
            try {
                return secureSignatureComp.signRequest(securityGuardParamContext, f11010i);
            } catch (SecException e2) {
                com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileAuthHttpRequest", "sign(),signe req error,e" + e2.toString());
                e2.printStackTrace();
                return null;
            }
        } catch (SecException e3) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileAuthHttpRequest", "sign(), create sg manager error, e" + e3.toString());
            e3.printStackTrace();
            return null;
        }
    }

    private static String a(Object... objArr) {
        String string;
        if (objArr == null || objArr.length <= 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Object obj : objArr) {
            if (obj == null) {
                string = "";
            } else {
                string = obj.toString();
            }
            stringBuffer.append(string);
        }
        return stringBuffer.toString();
    }
}
