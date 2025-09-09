package com.aliyun.iot.aep.sdk.framework.bundle;

import android.app.Application;
import android.content.res.AssetManager;
import com.aliyun.iot.aep.sdk.framework.bundle.PageConfigure;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.umeng.analytics.pro.f;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class BundleManager {
    private static ArrayList<String> a(Application application) {
        AssetManager assets;
        String[] list;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            assets = application.getAssets();
        } catch (Exception e2) {
            ALog.e("BundleManager", "find-bundle-configure failed", e2);
        }
        if (assets != null && (list = assets.list("bundle_configs")) != null && list.length > 0) {
            for (String str : list) {
                if (str != null && str.length() > 0 && str.matches("bundle_config_[A-z,0-9]+\\.json")) {
                    ALog.d("BundleManager", "page-file: bundle_configs/" + str);
                    arrayList.add("bundle_configs/" + str);
                }
            }
            return arrayList;
        }
        return arrayList;
    }

    public static int init(Application application, IBundleRegister iBundleRegister) throws JSONException {
        ArrayList<PageConfigure> arrayList;
        if (application == null || iBundleRegister == null) {
            return -1;
        }
        ArrayList<String> arrayListA = a(application);
        if (arrayListA != null && !arrayListA.isEmpty()) {
            Iterator<String> it = arrayListA.iterator();
            while (it.hasNext()) {
                BundleConfigure bundleConfigureA = a(application, it.next());
                if (bundleConfigureA != null && (arrayList = bundleConfigureA.pages) != null && !arrayList.isEmpty()) {
                    Iterator<PageConfigure> it2 = bundleConfigureA.pages.iterator();
                    while (it2.hasNext()) {
                        iBundleRegister.registerPage(application, it2.next());
                    }
                }
            }
        }
        return 0;
    }

    private static BundleConfigure a(Application application, String str) throws JSONException {
        JSONObject jSONObject;
        JSONArray jSONArray;
        BundleConfigure bundleConfigure = null;
        if (application == null || str == null || str.isEmpty()) {
            return null;
        }
        try {
            String strA = a(application.getAssets().open(str));
            if (strA == null || strA.isEmpty() || (jSONArray = (jSONObject = new JSONObject(strA)).getJSONArray(f.f21698t)) == null) {
                return null;
            }
            BundleConfigure bundleConfigure2 = new BundleConfigure();
            try {
                bundleConfigure2.name = jSONObject.optString("name");
                bundleConfigure2.pages = new ArrayList<>();
                for (int length = jSONArray.length() - 1; length >= 0; length--) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(length);
                    JSONArray jSONArray2 = jSONObject2 != null ? jSONObject2.getJSONArray("navigationConfigures") : null;
                    if (jSONArray2 != null && jSONArray2.length() > 0) {
                        PageConfigure pageConfigure = new PageConfigure();
                        pageConfigure.name = jSONObject2.optString("name");
                        pageConfigure.version = jSONObject2.optString("version");
                        pageConfigure.needLogin = jSONObject2.optBoolean("needLogin");
                        pageConfigure.navigationConfigures = new ArrayList<>();
                        for (int length2 = jSONArray2.length() - 1; length2 >= 0; length2--) {
                            JSONObject jSONObject3 = jSONArray2.getJSONObject(length2);
                            if (jSONObject3 != null) {
                                PageConfigure.NavigationConfigure navigationConfigure = new PageConfigure.NavigationConfigure();
                                navigationConfigure.navigationCode = jSONObject3.optString("navigationCode");
                                navigationConfigure.navigationIntentAction = jSONObject3.optString("navigationIntentAction");
                                navigationConfigure.navigationIntentCategory = jSONObject3.optString("navigationIntentCategory");
                                navigationConfigure.navigationIntentUrl = jSONObject3.optString("navigationIntentUrl");
                                ALog.d("BundleManager", "prepare navigation-configure: " + navigationConfigure.navigationCode + ", " + navigationConfigure.navigationIntentUrl);
                                pageConfigure.navigationConfigures.add(navigationConfigure);
                            }
                        }
                        bundleConfigure2.pages.add(pageConfigure);
                    }
                }
                return bundleConfigure2;
            } catch (Exception e2) {
                e = e2;
                bundleConfigure = bundleConfigure2;
                ALog.e("BundleManager", "prepare-bundle-configure", e);
                return bundleConfigure;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x002d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String a(java.io.InputStream r5) throws java.io.IOException {
        /*
            java.lang.String r0 = "read-file"
            java.lang.String r1 = "BundleManager"
            r2 = 0
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Exception -> L25
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch: java.lang.Exception -> L25
            r4.<init>(r5)     // Catch: java.lang.Exception -> L25
            r3.<init>(r4)     // Catch: java.lang.Exception -> L25
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L23
            r5.<init>()     // Catch: java.lang.Exception -> L23
        L14:
            java.lang.String r4 = r3.readLine()     // Catch: java.lang.Exception -> L23
            if (r4 == 0) goto L2b
            r5.append(r4)     // Catch: java.lang.Exception -> L23
            java.lang.String r4 = "\n"
            r5.append(r4)     // Catch: java.lang.Exception -> L23
            goto L14
        L23:
            r5 = move-exception
            goto L27
        L25:
            r5 = move-exception
            r3 = r2
        L27:
            com.aliyun.iot.aep.sdk.log.ALog.e(r1, r0, r5)
            r5 = r2
        L2b:
            if (r3 == 0) goto L35
            r3.close()     // Catch: java.lang.Exception -> L31
            goto L35
        L31:
            r3 = move-exception
            com.aliyun.iot.aep.sdk.log.ALog.e(r1, r0, r3)
        L35:
            if (r5 == 0) goto L3b
            java.lang.String r2 = r5.toString()
        L3b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.iot.aep.sdk.framework.bundle.BundleManager.a(java.io.InputStream):java.lang.String");
    }
}
