package com.meizu.cloud.pushsdk.platform.c;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.WifiProvisionUtConst;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.e.b.c;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.io.File;
import java.util.LinkedHashMap;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private String f19778a;

    /* renamed from: b, reason: collision with root package name */
    private String f19779b;

    /* renamed from: c, reason: collision with root package name */
    private String f19780c;

    /* renamed from: d, reason: collision with root package name */
    private String f19781d;

    /* renamed from: e, reason: collision with root package name */
    private String f19782e;

    /* renamed from: f, reason: collision with root package name */
    private String f19783f;

    /* renamed from: g, reason: collision with root package name */
    private String f19784g;

    /* renamed from: h, reason: collision with root package name */
    private String f19785h;

    /* renamed from: i, reason: collision with root package name */
    private String f19786i;

    /* renamed from: j, reason: collision with root package name */
    private String f19787j;

    /* renamed from: k, reason: collision with root package name */
    private String f19788k;

    /* renamed from: l, reason: collision with root package name */
    private String f19789l;

    /* renamed from: m, reason: collision with root package name */
    private String f19790m;

    /* renamed from: n, reason: collision with root package name */
    private String f19791n;

    /* renamed from: o, reason: collision with root package name */
    private String f19792o;

    public a(Context context) {
        this.f19778a = "https://api-push.meizu.com/garcia/api/client/";
        this.f19779b = this.f19778a + "message/registerPush";
        this.f19780c = this.f19778a + "message/unRegisterPush";
        this.f19781d = this.f19778a + "advance/unRegisterPush";
        this.f19782e = this.f19778a + "message/getRegisterSwitch";
        this.f19783f = this.f19778a + "message/changeRegisterSwitch";
        this.f19784g = this.f19778a + "message/changeAllSwitch";
        this.f19785h = this.f19778a + "message/subscribeTags";
        this.f19786i = this.f19778a + "message/unSubscribeTags";
        this.f19787j = this.f19778a + "message/unSubAllTags";
        this.f19788k = this.f19778a + "message/getSubTags";
        this.f19789l = this.f19778a + "message/subscribeAlias";
        this.f19790m = this.f19778a + "message/unSubscribeAlias";
        this.f19791n = this.f19778a + "message/getSubAlias";
        this.f19792o = this.f19778a + "advance/changeRegisterSwitch";
        com.meizu.cloud.pushsdk.e.a.a();
        if (MzSystemUtils.isOverseas()) {
            this.f19778a = "https://api-push.in.meizu.com/garcia/api/client/";
            this.f19779b = this.f19778a + "message/registerPush";
            this.f19780c = this.f19778a + "message/unRegisterPush";
            this.f19781d = this.f19778a + "advance/unRegisterPush";
            this.f19782e = this.f19778a + "message/getRegisterSwitch";
            this.f19783f = this.f19778a + "message/changeRegisterSwitch";
            this.f19784g = this.f19778a + "message/changeAllSwitch";
            this.f19785h = this.f19778a + "message/subscribeTags";
            this.f19786i = this.f19778a + "message/unSubscribeTags";
            this.f19787j = this.f19778a + "message/unSubAllTags";
            this.f19788k = this.f19778a + "message/getSubTags";
            this.f19789l = this.f19778a + "message/subscribeAlias";
            this.f19790m = this.f19778a + "message/unSubscribeAlias";
            this.f19791n = this.f19778a + "message/getSubAlias";
            this.f19792o = this.f19778a + "advance/changeRegisterSwitch";
        }
    }

    public c a(String str, String str2, String str3) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "checkPush post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.e.a.a(this.f19782e).a(linkedHashMap2).a().d();
    }

    public c b(String str, String str2, String str3) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "checkPush post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.e.a.a(this.f19788k).a(linkedHashMap2).a().d();
    }

    public c c(String str, String str2, String str3) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "subScribeAllTags post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.e.a.b(this.f19787j).a(linkedHashMap2).a().d();
    }

    public c d(String str, String str2, String str3, String str4) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        if (TextUtils.isEmpty(str4)) {
            linkedHashMap.put("deviceId", str3);
        } else {
            linkedHashMap.put("fdId", str4);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "unregister post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.e.a.a(this.f19780c).a(linkedHashMap2).a().d();
    }

    public c e(String str, String str2, String str3, String str4) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        linkedHashMap.put(PushConstants.SUB_ALIAS_STATUS_NAME, str4);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "subScribeTags post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.e.a.b(this.f19790m).a(linkedHashMap2).a().d();
    }

    public c f(String str, String str2, String str3, String str4) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        linkedHashMap.put("tags", str4);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "subScribeTags post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.e.a.b(this.f19786i).a(linkedHashMap2).a().d();
    }

    public c a(String str, String str2, String str3, int i2, boolean z2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        linkedHashMap.put("msgType", String.valueOf(i2));
        linkedHashMap.put("subSwitch", z2 ? "1" : "0");
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", this.f19783f + " switchPush post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.e.a.b(this.f19783f).a(linkedHashMap2).a().d();
    }

    public c b(String str, String str2, String str3, String str4) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put("appKey", str2);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        linkedHashMap.put(PushConstants.SUB_ALIAS_STATUS_NAME, str4);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "subScribeTags post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.e.a.b(this.f19789l).a(linkedHashMap2).a().d();
    }

    public c c(String str, String str2, String str3, String str4) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        linkedHashMap.put("tags", str4);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "subScribeTags post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.e.a.b(this.f19785h).a(linkedHashMap2).a().d();
    }

    public c a(String str, String str2, String str3, String str4) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        if (TextUtils.isEmpty(str4)) {
            linkedHashMap.put("deviceId", str3);
        } else {
            linkedHashMap.put("fdId", str4);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "register post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.e.a.b(this.f19779b).a(linkedHashMap2).a().d();
    }

    public c<String> a(String str, String str2, String str3, String str4, File file) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(RemoteMessageConst.MSGID, str);
        if (TextUtils.isEmpty(str3)) {
            linkedHashMap.put("deviceId", str2);
        } else {
            linkedHashMap.put("fdId", str3);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, "4a2ca769d79f4856bb3bd982d30de790"));
        if (!TextUtils.isEmpty(str4)) {
            linkedHashMap2.put(WifiProvisionUtConst.KEY_ERROR_MSG, str4);
        }
        DebugLogger.i("PushAPI", "uploadLogFile post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.e.a.c("https://api-push.meizu.com/garcia/api/client/log/upload").a(linkedHashMap2).a("logFile", file).a().d();
    }

    public c a(String str, String str2, String str3, boolean z2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        linkedHashMap.put("subSwitch", z2 ? "1" : "0");
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", this.f19784g + " switchPush post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.e.a.b(this.f19784g).a(linkedHashMap2).a().d();
    }
}
