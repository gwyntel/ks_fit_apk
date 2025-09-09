package com.huawei.hms.opendevice;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.android.HwBuildEx;
import com.huawei.hms.android.SystemUtils;
import com.huawei.hms.mlsdk.common.AgConnectInfo;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.PackageManagerHelper;
import com.huawei.hms.utils.Util;
import com.umeng.analytics.pro.bc;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.message.MessageService;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class m {

    class a extends Thread {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f16598a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f16599b;

        a(Context context, String str) {
            this.f16598a = context;
            this.f16599b = str;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws Throwable {
            if (!d.b()) {
                HMSLog.d("ReportAaidToken", "Not HW Phone.");
                return;
            }
            if (m.b(this.f16598a)) {
                return;
            }
            String strA = com.huawei.hms.opendevice.b.a(this.f16598a);
            if (TextUtils.isEmpty(strA)) {
                HMSLog.w("ReportAaidToken", "AAID is empty.");
                return;
            }
            if (!m.d(this.f16598a, strA, this.f16599b)) {
                HMSLog.d("ReportAaidToken", "This time need not report.");
                return;
            }
            String string = AGConnectServicesConfig.fromContext(this.f16598a).getString("region");
            if (TextUtils.isEmpty(string)) {
                HMSLog.i("ReportAaidToken", "The data storage region is empty.");
                return;
            }
            String strA2 = k.a(this.f16598a, "com.huawei.hms.opendevicesdk", "ROOT", null, string);
            if (TextUtils.isEmpty(strA2)) {
                return;
            }
            String strC = m.c(this.f16598a, strA, this.f16599b);
            m.b(this.f16598a, g.a(this.f16598a, strA2 + "/rest/appdata/v1/aaid/report", strC, (Map<String, String>) null), strA, this.f16599b);
        }
    }

    private enum b {
        MOBILE("1"),
        PC("2"),
        TABLET("3"),
        TV("4"),
        SOUNDBOX(AlcsPalConst.MODEL_TYPE_TGMESH),
        GLASS("6"),
        WATCH("7"),
        VEHICLE(MessageService.MSG_ACCS_NOTIFY_CLICK),
        OFFICE_DEVICE(MessageService.MSG_ACCS_NOTIFY_DISMISS),
        IOT_DEVICES(AgooConstants.ACK_REMOVE_PACKAGE),
        HEALTHY(AgooConstants.ACK_BODY_NULL),
        ENTERTAINMENT(AgooConstants.ACK_PACK_NULL),
        TRANSPORT_DEVICES(AgooConstants.ACK_FLAG_NULL);


        /* renamed from: a, reason: collision with root package name */
        private String f16614a;

        b(String str) {
            this.f16614a = str;
        }

        public String a() {
            return this.f16614a;
        }
    }

    private enum c {
        IOS("ios"),
        ANDROID("android"),
        HARMONY(Utils.HARMONY_OS),
        WINDOWS("windows"),
        EMBED("embed"),
        OTHERS("others");


        /* renamed from: a, reason: collision with root package name */
        private String f16622a;

        c(String str) {
            this.f16622a = str;
        }

        public String a() {
            return this.f16622a;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(Context context, String str, String str2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(bc.M, TimeZone.getDefault().getID());
            jSONObject2.put("country", SystemUtils.getLocalCountry());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("agent_version", new PackageManagerHelper(context).getPackageVersionName("com.huawei.android.pushagent"));
            jSONObject3.put("hms_version", String.valueOf(Util.getHmsVersion(context)));
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("dev_type", b.MOBILE.a());
            jSONObject4.put("dev_sub_type", "phone");
            jSONObject4.put("os_type", c.ANDROID.a());
            jSONObject4.put(bc.f21426y, String.valueOf(HwBuildEx.VERSION.EMUI_SDK_INT));
            jSONObject.put("id", UUID.randomUUID().toString());
            jSONObject.put("global", jSONObject2);
            jSONObject.put("push_agent", jSONObject3);
            jSONObject.put("hardware", jSONObject4);
            jSONObject.put("aaid", str);
            jSONObject.put("token", str2);
            jSONObject.put("app_id", AGConnectServicesConfig.fromContext(context).getString(AgConnectInfo.AgConnectKey.APPLICATION_ID));
            jSONObject.put("region", AGConnectServicesConfig.fromContext(context).getString("region"));
            return jSONObject.toString();
        } catch (JSONException unused) {
            HMSLog.e("ReportAaidToken", "Catch JSONException.");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean d(Context context, String str, String str2) {
        i iVarA = i.a(context);
        if (!iVarA.containsKey("reportAaidAndToken")) {
            HMSLog.d("ReportAaidToken", "It hasn't been reported, this time needs report.");
            return true;
        }
        if (TextUtils.isEmpty(iVarA.getString("reportAaidAndToken"))) {
            HMSLog.w("ReportAaidToken", "It has been reported, but report value is empty, this time needs report.");
            return true;
        }
        return !r4.equals(n.a(str2 + str, "SHA-256"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(Context context) throws PackageManager.NameNotFoundException {
        int packageVersionCode = new PackageManagerHelper(context).getPackageVersionCode("com.huawei.android.pushagent");
        HMSLog.d("ReportAaidToken", "NC version code: " + packageVersionCode);
        return (90101400 <= packageVersionCode && packageVersionCode < 100000000) || packageVersionCode >= 100001301;
    }

    public static void a(Context context, String str) {
        new a(context, str).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            HMSLog.e("ReportAaidToken", "Https response is empty.");
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            int iOptInt = jSONObject.optInt("ret", 256);
            if (iOptInt == 0) {
                boolean zSaveString = i.a(context).saveString("reportAaidAndToken", n.a(str3 + str2, "SHA-256"));
                StringBuilder sb = new StringBuilder();
                sb.append("Report success ");
                sb.append(zSaveString ? "and save success." : "but save failure.");
                HMSLog.d("ReportAaidToken", sb.toString());
                return;
            }
            HMSLog.e("ReportAaidToken", "Https response body's ret code: " + iOptInt + ", error message: " + jSONObject.optString("msg"));
        } catch (JSONException unused) {
            HMSLog.e("ReportAaidToken", "Has JSONException.");
        } catch (Exception unused2) {
            HMSLog.e("ReportAaidToken", "Exception occur.");
        }
    }
}
