package com.xiaomi.account.auth;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import com.xiaomi.account.http.Request;
import com.xiaomi.account.http.UrlConnHttpFactory;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
class OAuthServiceManager {

    @Deprecated
    private static final String ACTION_FOR_AUTH_SERVICE = "android.intent.action.XIAOMI_ACCOUNT_AUTHORIZE";
    private static final String DEFAULT_PACKAGE_NAME_FOR_AUTH_SERVICE = "com.xiaomi.account";
    private static final String NEW_ACTION_FOR_AUTH_SERVICE = "miui.intent.action.XIAOMI_ACCOUNT_AUTHORIZE";
    private static final String ORDER_APP_URL = XiaomiOAuthConstants.OAUTH2_API_URL_BASE + "/extra/appOrder";
    private static final String TAG = "OAuthServiceManager";
    private Context mContext;

    OAuthServiceManager(Context context) {
        this.mContext = context;
    }

    private List<OrderApp> blockGetOrderApps() {
        try {
            JSONObject jSONObject = new JSONObject(new UrlConnHttpFactory().createHttpClient().excute(new Request.Builder().url(ORDER_APP_URL).appendQuery("platform=android").followRedirects(false).build()).body);
            if (jSONObject.getInt("code") != 0) {
                return null;
            }
            JSONArray jSONArrayOptJSONArray = jSONObject.getJSONObject("data").optJSONArray("order");
            ArrayList arrayList = new ArrayList();
            int length = jSONArrayOptJSONArray != null ? jSONArrayOptJSONArray.length() : 0;
            for (int i2 = 0; i2 < length; i2++) {
                JSONObject jSONObject2 = (JSONObject) jSONArrayOptJSONArray.get(i2);
                String strOptString = jSONObject2.optString("name");
                int iOptInt = jSONObject2.optInt("version");
                OrderApp orderApp = new OrderApp();
                orderApp.setName(strOptString);
                orderApp.setVersion(iOptInt);
                arrayList.add(orderApp);
            }
            return arrayList;
        } catch (Exception e2) {
            Log.e(TAG, e2.toString());
            return null;
        }
    }

    private static Intent getMiuiSupportService(Context context) {
        Intent intent = new Intent(NEW_ACTION_FOR_AUTH_SERVICE);
        intent.setPackage(DEFAULT_PACKAGE_NAME_FOR_AUTH_SERVICE);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 0);
        if (listQueryIntentServices != null && listQueryIntentServices.size() > 0) {
            return intent;
        }
        Intent intent2 = new Intent(ACTION_FOR_AUTH_SERVICE);
        intent2.setPackage(DEFAULT_PACKAGE_NAME_FOR_AUTH_SERVICE);
        List<ResolveInfo> listQueryIntentServices2 = packageManager.queryIntentServices(intent2, 0);
        if (listQueryIntentServices2 == null || listQueryIntentServices2.size() <= 0) {
            return null;
        }
        return intent2;
    }

    private Intent getSupportOAuthService(List<OrderApp> list) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        if (list != null && list.size() != 0) {
            for (OrderApp orderApp : list) {
                String name = orderApp.getName();
                int version = orderApp.getVersion();
                PackageManager packageManager = this.mContext.getPackageManager();
                try {
                    packageInfo = packageManager.getPackageInfo(name, 0);
                } catch (PackageManager.NameNotFoundException e2) {
                    e2.printStackTrace();
                    packageInfo = null;
                }
                if (version <= (packageInfo != null ? packageInfo.versionCode : 0)) {
                    Intent intent = new Intent(NEW_ACTION_FOR_AUTH_SERVICE);
                    intent.setPackage(name);
                    List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 0);
                    if (listQueryIntentServices != null && listQueryIntentServices.size() > 0) {
                        return intent;
                    }
                }
            }
        }
        return null;
    }

    Intent blockGetDefaultIntent() throws PackageManager.NameNotFoundException {
        Intent miuiSupportService = getMiuiSupportService(this.mContext);
        if (miuiSupportService != null) {
            return miuiSupportService;
        }
        Intent supportOAuthService = getSupportOAuthService(blockGetOrderApps());
        if (supportOAuthService != null) {
            return supportOAuthService;
        }
        return null;
    }

    boolean hasOAuthService(Context context) {
        return (getMiuiSupportService(context) == null && getSupportOAuthService(blockGetOrderApps()) == null) ? false : true;
    }
}
