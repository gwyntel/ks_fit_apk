package com.meizu.cloud.pushsdk.handler.e.h;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.d.i;
import com.meizu.cloud.pushsdk.e.b.c;
import com.meizu.cloud.pushsdk.handler.e.h.b;
import java.io.File;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String[] f19689a = {"^MEIZU17(Pro)*$", "^MEIZU18(Pro)*$"};

    /* renamed from: b, reason: collision with root package name */
    private static final String[] f19690b = {"^.+$", "^.+$"};

    /* renamed from: c, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.handler.e.h.b f19691c;

    private static class b {

        /* renamed from: a, reason: collision with root package name */
        private static a f19692a = new a();
    }

    private a() {
    }

    public static a a() {
        return b.f19692a;
    }

    private com.meizu.cloud.pushsdk.handler.e.h.b b() {
        com.meizu.cloud.pushsdk.handler.e.h.b bVar = new com.meizu.cloud.pushsdk.handler.e.h.b();
        bVar.a(System.currentTimeMillis());
        bVar.a(2);
        bVar.a("^com\\.(meizu|flyme)(\\..+)+$");
        String[] strArr = f19689a;
        String str = strArr[0];
        String[] strArr2 = f19690b;
        bVar.a(new b.a(str, strArr2[0]));
        bVar.a(new b.a(strArr[1], strArr2[1]));
        return bVar;
    }

    private com.meizu.cloud.pushsdk.handler.e.h.b c(Context context) throws Throwable {
        c cVarC = com.meizu.cloud.pushsdk.e.a.a(PushConstants.GET_PUSH_CONFIG).a().c();
        if (cVarC == null) {
            DebugLogger.e("PushConfig", "network request config fail");
            return null;
        }
        JSONObject jSONObject = (JSONObject) cVarC.b();
        DebugLogger.i("PushConfig", "network request config result is:" + cVarC.b());
        if (jSONObject != null) {
            try {
                if (jSONObject.has("code") && "200".equals(jSONObject.getString("code")) && jSONObject.has("value")) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("value");
                    if (jSONObject2 == null) {
                        DebugLogger.e("PushConfig", "network request config fail");
                        return null;
                    }
                    jSONObject2.put("requestTime", System.currentTimeMillis());
                    a(context, jSONObject2);
                    return a(jSONObject2);
                }
            } catch (Exception e2) {
                DebugLogger.e("PushConfig", "network request config error, " + e2.getMessage());
                e2.printStackTrace();
                return null;
            }
        }
        DebugLogger.e("PushConfig", "network request config fail");
        return null;
    }

    private JSONObject d(Context context) {
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (externalFilesDir == null) {
            return null;
        }
        try {
            String strC = com.meizu.cloud.pushsdk.notification.g.a.c(externalFilesDir.getPath() + "/push_config");
            if (!TextUtils.isEmpty(strC)) {
                return new JSONObject(strC);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return null;
    }

    private synchronized com.meizu.cloud.pushsdk.handler.e.h.b a(Context context) {
        DebugLogger.i("PushConfig", "getPushConfigInfo start, mPushConfigInfo = " + this.f19691c);
        com.meizu.cloud.pushsdk.handler.e.h.b bVar = this.f19691c;
        if (bVar != null && bVar.d()) {
            DebugLogger.i("PushConfig", "getPushConfigInfo have cache and effective time, return directly");
            return this.f19691c;
        }
        if (this.f19691c == null) {
            com.meizu.cloud.pushsdk.handler.e.h.b bVarB = b(context);
            this.f19691c = bVarB;
            if (bVarB != null) {
                DebugLogger.i("PushConfig", "getPushConfigInfo to load, mPushConfigInfo = " + this.f19691c);
                return this.f19691c;
            }
        }
        com.meizu.cloud.pushsdk.handler.e.h.b bVarC = c(context);
        this.f19691c = bVarC;
        if (bVarC != null) {
            DebugLogger.i("PushConfig", "getPushConfigInfo to network, mPushConfigInfo = " + this.f19691c);
            return this.f19691c;
        }
        this.f19691c = b();
        DebugLogger.i("PushConfig", "getPushConfigInfo to default, mPushConfigInfo = " + this.f19691c);
        return this.f19691c;
    }

    private com.meizu.cloud.pushsdk.handler.e.h.b b(Context context) throws JSONException {
        com.meizu.cloud.pushsdk.handler.e.h.b bVarA = a(d(context));
        if (bVarA == null || !bVarA.d()) {
            return null;
        }
        return bVarA;
    }

    private com.meizu.cloud.pushsdk.handler.e.h.b a(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray;
        JSONArray jSONArray2;
        JSONArray jSONArray3;
        DebugLogger.i("PushConfig", "analysis config jsonObjectValue = " + jSONObject);
        if (jSONObject == null) {
            return null;
        }
        try {
            com.meizu.cloud.pushsdk.handler.e.h.b bVar = new com.meizu.cloud.pushsdk.handler.e.h.b();
            if (jSONObject.has("requestTime")) {
                bVar.a(jSONObject.getLong("requestTime"));
            }
            if (jSONObject.has("intervalHour")) {
                bVar.a(jSONObject.getInt("intervalHour"));
            }
            if (jSONObject.has("shieldPackage") && (jSONArray3 = jSONObject.getJSONArray("shieldPackage")) != null) {
                for (int i2 = 0; i2 < jSONArray3.length(); i2++) {
                    bVar.a(jSONArray3.getString(i2));
                }
            }
            if (jSONObject.has("whitePackage") && (jSONArray2 = jSONObject.getJSONArray("whitePackage")) != null) {
                for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                    bVar.b(jSONArray2.getString(i3));
                }
            }
            if (jSONObject.has("shieldConfig") && (jSONArray = jSONObject.getJSONArray("shieldConfig")) != null) {
                for (int i4 = 0; i4 < jSONArray.length(); i4++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i4);
                    if (jSONObject2 != null && jSONObject2.has("model") && jSONObject2.has("os")) {
                        bVar.a(new b.a(jSONObject2.getString("model"), jSONObject2.getString("os")));
                    }
                }
            }
            return bVar;
        } catch (Exception e2) {
            DebugLogger.e("PushConfig", "analysis config error, " + e2.getMessage());
            e2.printStackTrace();
            return null;
        }
    }

    private boolean b(com.meizu.cloud.pushsdk.handler.e.h.b bVar, String str) {
        if (!TextUtils.isEmpty(str) && bVar != null && bVar.c() != null) {
            for (int i2 = 0; i2 < bVar.c().size(); i2++) {
                String str2 = bVar.c().get(i2);
                if (!TextUtils.isEmpty(str2) && a(str2, str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void a(Context context, JSONObject jSONObject) throws Throwable {
        DebugLogger.i("PushConfig", "save local config jsonObjectValue = " + jSONObject);
        String string = jSONObject.toString();
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (externalFilesDir != null) {
            com.meizu.cloud.pushsdk.notification.g.a.c(externalFilesDir.getPath() + "/push_config", string);
        }
    }

    public boolean a(Context context, String str) {
        String str2;
        com.meizu.cloud.pushsdk.handler.e.h.b bVarA = a(context);
        if (bVarA == null) {
            str2 = "check message effective, pushConfigInfo is null";
        } else {
            if (!b(bVarA, str)) {
                if (!a(bVarA, str) || !a(bVarA)) {
                    return true;
                }
                DebugLogger.i("PushConfig", "check message effective, matching shield package success");
                return false;
            }
            str2 = "check message effective, matching white package success";
        }
        DebugLogger.i("PushConfig", str2);
        return true;
    }

    private boolean a(com.meizu.cloud.pushsdk.handler.e.h.b bVar) {
        if (bVar != null && bVar.a() != null) {
            String strA = i.a("ro.product.model");
            String strA2 = i.a("ro.build.display.id");
            if (!TextUtils.isEmpty(strA) && !TextUtils.isEmpty(strA2)) {
                for (int i2 = 0; i2 < bVar.a().size(); i2++) {
                    b.a aVar = bVar.a().get(i2);
                    if (aVar != null && !TextUtils.isEmpty(aVar.a()) && !TextUtils.isEmpty(aVar.b()) && a(aVar.a(), strA) && a(aVar.b(), strA2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean a(com.meizu.cloud.pushsdk.handler.e.h.b bVar, String str) {
        if (!TextUtils.isEmpty(str) && bVar != null && bVar.b() != null) {
            for (int i2 = 0; i2 < bVar.b().size(); i2++) {
                String str2 = bVar.b().get(i2);
                if (!TextUtils.isEmpty(str2) && a(str2, str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        String strReplace = str2.toLowerCase().replace(" ", "");
        if (lowerCase.startsWith("^") || lowerCase.endsWith("$")) {
            boolean zMatches = Pattern.compile(lowerCase).matcher(strReplace).matches();
            DebugLogger.i("PushConfig", lowerCase + " matches " + strReplace + " is " + zMatches);
            return zMatches;
        }
        if (!lowerCase.equalsIgnoreCase(strReplace)) {
            return false;
        }
        DebugLogger.i("PushConfig", lowerCase + " equalsIgnoreCase " + strReplace + " is true");
        return true;
    }
}
