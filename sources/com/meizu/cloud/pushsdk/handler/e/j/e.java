package com.meizu.cloud.pushsdk.handler.e.j;

import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private int f19719a;

    /* renamed from: b, reason: collision with root package name */
    private String f19720b = String.valueOf(-1);

    /* renamed from: c, reason: collision with root package name */
    private String f19721c = "";

    /* renamed from: d, reason: collision with root package name */
    private String f19722d = "";

    /* renamed from: e, reason: collision with root package name */
    private int f19723e = -1;

    /* renamed from: f, reason: collision with root package name */
    private String f19724f = "";

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public String f19725a;

        /* renamed from: b, reason: collision with root package name */
        public String f19726b;

        /* renamed from: c, reason: collision with root package name */
        String f19727c;

        public a(String str) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (!jSONObject.isNull("code")) {
                    a(jSONObject.getString("code"));
                }
                if (!jSONObject.isNull("message")) {
                    b(jSONObject.getString("message"));
                }
                if (jSONObject.isNull("value")) {
                    return;
                }
                c(jSONObject.getString("value"));
            } catch (JSONException e2) {
                DebugLogger.e("SecurityMessage", "covert json error " + e2.getMessage());
            }
        }

        public String a() {
            return this.f19727c;
        }

        public void b(String str) {
            this.f19726b = str;
        }

        public void c(String str) {
            this.f19727c = str;
        }

        public String toString() {
            return "PublicKeyStatus{code='" + this.f19725a + "', message='" + this.f19726b + "', publicKey='" + this.f19727c + "'}";
        }

        public void a(String str) {
            this.f19725a = str;
        }
    }

    public int a() {
        return this.f19723e;
    }

    public String b() {
        return this.f19722d;
    }

    public String c() {
        return this.f19724f;
    }

    public String d() {
        return this.f19720b;
    }

    public int e() {
        return this.f19719a;
    }

    public String f() {
        return this.f19721c;
    }

    public String toString() {
        return "SecurityMessage{timestamp=" + this.f19719a + ", taskId='" + this.f19720b + "', title='" + this.f19721c + "', content='" + this.f19722d + "', clickType=" + this.f19723e + ", params='" + this.f19724f + "'}";
    }

    private static e a(String str) {
        e eVar = new e();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull("tt")) {
                eVar.b(jSONObject.getInt("tt"));
            }
            if (!jSONObject.isNull("ti")) {
                eVar.d(jSONObject.getString("ti"));
            }
            if (!jSONObject.isNull("tl")) {
                eVar.e(jSONObject.getString("tl"));
            }
            if (!jSONObject.isNull("cont")) {
                eVar.b(jSONObject.getString("cont"));
            }
            if (!jSONObject.isNull("ct")) {
                eVar.a(jSONObject.getInt("ct"));
            }
            if (!jSONObject.isNull("pm")) {
                eVar.c(jSONObject.getString("pm"));
            }
        } catch (Exception e2) {
            DebugLogger.e("SecurityMessage", "parse decryptSign error " + e2.getMessage());
        }
        return eVar;
    }

    public void b(int i2) {
        this.f19719a = i2;
    }

    public void c(String str) {
        this.f19724f = str;
    }

    public void d(String str) {
        this.f19720b = str;
    }

    public void e(String str) {
        this.f19721c = str;
    }

    public static String a(MessageV3 messageV3) throws JSONException {
        JSONObject jSONObject;
        String notificationMessage = messageV3.getNotificationMessage();
        String string = null;
        try {
            try {
                if (!TextUtils.isEmpty(notificationMessage)) {
                    try {
                        JSONObject jSONObject2 = new JSONObject(notificationMessage).getJSONObject("data");
                        if (!jSONObject2.isNull(PushConstants.EXTRA)) {
                            JSONObject jSONObject3 = jSONObject2.getJSONObject(PushConstants.EXTRA);
                            if (!jSONObject3.isNull("se")) {
                                string = jSONObject3.getString("se");
                            }
                        }
                    } catch (JSONException e2) {
                        DebugLogger.e("SecurityMessage", "parse notification message error " + e2.getMessage());
                        if (TextUtils.isEmpty(null)) {
                            jSONObject = new JSONObject(notificationMessage);
                        }
                    }
                    if (TextUtils.isEmpty(string)) {
                        jSONObject = new JSONObject(notificationMessage);
                        string = jSONObject.getString("se");
                    }
                }
            } catch (Exception unused) {
            }
            DebugLogger.i("SecurityMessage", "encrypt message " + string);
            return string;
        } catch (Throwable th) {
            if (TextUtils.isEmpty(null)) {
                try {
                    new JSONObject(notificationMessage).getString("se");
                } catch (Exception unused2) {
                }
            }
            throw th;
        }
    }

    public void b(String str) {
        this.f19722d = str;
    }

    public void a(int i2) {
        this.f19723e = i2;
    }

    public static boolean a(String str, MessageV3 messageV3) {
        String str2;
        e eVarA = a(str);
        DebugLogger.e("SecurityMessage", "securityMessage " + eVarA);
        if (System.currentTimeMillis() / 1000 > eVarA.e()) {
            str2 = "message expire";
        } else if (!messageV3.getTitle().contains(eVarA.f())) {
            str2 = "invalid title";
        } else if (!messageV3.getContent().contains(eVarA.b())) {
            str2 = "invalid content";
        } else if (!String.valueOf(-1).equals(eVarA.d()) && !eVarA.d().equals(messageV3.getTaskId())) {
            str2 = "invalid taskId";
        } else {
            if (eVarA.a() != -1) {
                int iA = eVarA.a();
                if (iA == 1) {
                    if (!messageV3.getActivity().contains(eVarA.c())) {
                        str2 = "invalid click activity";
                    }
                    return true;
                }
                if (iA == 2) {
                    if (!messageV3.getWebUrl().contains(eVarA.c())) {
                        str2 = "invalid web url";
                    }
                    return true;
                }
                if (iA == 3 && !MzPushMessage.fromMessageV3(messageV3).getSelfDefineContentString().contains(eVarA.c())) {
                    str2 = "invalid self define";
                }
                return true;
            }
            str2 = "invalid click type";
        }
        DebugLogger.e("SecurityMessage", str2);
        return false;
    }
}
