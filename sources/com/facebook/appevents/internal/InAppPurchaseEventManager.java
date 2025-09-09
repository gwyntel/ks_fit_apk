package com.facebook.appevents.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.alipay.sdk.m.u.i;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.facebook.FacebookSdk;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
class InAppPurchaseEventManager {
    private static final String AS_INTERFACE = "asInterface";
    private static final int CACHE_CLEAR_TIME_LIMIT_SEC = 604800;
    private static final String DETAILS_LIST = "DETAILS_LIST";
    private static final String GET_PURCHASES = "getPurchases";
    private static final String GET_PURCHASE_HISTORY = "getPurchaseHistory";
    private static final String GET_SKU_DETAILS = "getSkuDetails";
    private static final String INAPP = "inapp";
    private static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";
    private static final String INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";
    private static final String IN_APP_BILLING_SERVICE = "com.android.vending.billing.IInAppBillingService";
    private static final String IN_APP_BILLING_SERVICE_STUB = "com.android.vending.billing.IInAppBillingService$Stub";
    private static final String IS_BILLING_SUPPORTED = "isBillingSupported";
    private static final String ITEM_ID_LIST = "ITEM_ID_LIST";
    private static final String LAST_CLEARED_TIME = "LAST_CLEARED_TIME";
    private static final int MAX_QUERY_PURCHASE_NUM = 30;
    private static final int PURCHASE_EXPIRE_TIME_SEC = 86400;
    private static final int PURCHASE_STOP_QUERY_TIME_SEC = 1200;
    private static final String RESPONSE_CODE = "RESPONSE_CODE";
    private static final int SKU_DETAIL_EXPIRE_TIME_SEC = 43200;
    private static final String SUBSCRIPTION = "subs";
    private static final String TAG = "com.facebook.appevents.internal.InAppPurchaseEventManager";
    private static final HashMap<String, Method> methodMap = new HashMap<>();
    private static final HashMap<String, Class<?>> classMap = new HashMap<>();
    private static final String PACKAGE_NAME = FacebookSdk.getApplicationContext().getPackageName();
    private static final String SKU_DETAILS_STORE = "com.facebook.internal.SKU_DETAILS";
    private static final SharedPreferences skuDetailSharedPrefs = FacebookSdk.getApplicationContext().getSharedPreferences(SKU_DETAILS_STORE, 0);
    private static final String PURCHASE_INAPP_STORE = "com.facebook.internal.PURCHASE";
    private static final SharedPreferences purchaseInappSharedPrefs = FacebookSdk.getApplicationContext().getSharedPreferences(PURCHASE_INAPP_STORE, 0);

    InAppPurchaseEventManager() {
    }

    @Nullable
    static Object asInterface(Context context, IBinder iBinder) {
        return invokeMethod(context, IN_APP_BILLING_SERVICE_STUB, AS_INTERFACE, null, new Object[]{iBinder});
    }

    static void clearSkuDetailsCache() {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        SharedPreferences sharedPreferences = skuDetailSharedPrefs;
        long j2 = sharedPreferences.getLong(LAST_CLEARED_TIME, 0L);
        if (j2 == 0) {
            sharedPreferences.edit().putLong(LAST_CLEARED_TIME, jCurrentTimeMillis).apply();
        } else if (jCurrentTimeMillis - j2 > 604800) {
            sharedPreferences.edit().clear().putLong(LAST_CLEARED_TIME, jCurrentTimeMillis).apply();
        }
    }

    private static ArrayList<String> filterPurchases(ArrayList<String> arrayList) throws JSONException {
        ArrayList<String> arrayList2 = new ArrayList<>();
        SharedPreferences.Editor editorEdit = purchaseInappSharedPrefs.edit();
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            String next = it.next();
            try {
                JSONObject jSONObject = new JSONObject(next);
                String string = jSONObject.getString(AlinkConstants.KEY_PRODUCT_ID);
                long j2 = jSONObject.getLong("purchaseTime");
                String string2 = jSONObject.getString("purchaseToken");
                if (jCurrentTimeMillis - (j2 / 1000) <= 86400 && !purchaseInappSharedPrefs.getString(string, "").equals(string2)) {
                    editorEdit.putString(string, string2);
                    arrayList2.add(next);
                }
            } catch (JSONException unused) {
            }
        }
        editorEdit.apply();
        return arrayList2;
    }

    @Nullable
    private static Class<?> getClass(Context context, String str) throws ClassNotFoundException {
        HashMap<String, Class<?>> map = classMap;
        Class<?> clsLoadClass = map.get(str);
        if (clsLoadClass != null) {
            return clsLoadClass;
        }
        try {
            clsLoadClass = context.getClassLoader().loadClass(str);
            map.put(str, clsLoadClass);
            return clsLoadClass;
        } catch (ClassNotFoundException unused) {
            return clsLoadClass;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004a  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.reflect.Method getMethod(java.lang.Class<?> r11, java.lang.String r12) throws java.lang.NoSuchMethodException, java.lang.SecurityException {
        /*
            r0 = 4
            r1 = 2
            r2 = 3
            r3 = 0
            r4 = 1
            java.util.HashMap<java.lang.String, java.lang.reflect.Method> r5 = com.facebook.appevents.internal.InAppPurchaseEventManager.methodMap
            java.lang.Object r6 = r5.get(r12)
            java.lang.reflect.Method r6 = (java.lang.reflect.Method) r6
            if (r6 == 0) goto L10
            return r6
        L10:
            int r7 = r12.hashCode()     // Catch: java.lang.NoSuchMethodException -> L9e
            switch(r7) {
                case -1801122596: goto L40;
                case -1450694211: goto L36;
                case -1123215065: goto L2c;
                case -594356707: goto L22;
                case -573310373: goto L18;
                default: goto L17;
            }     // Catch: java.lang.NoSuchMethodException -> L9e
        L17:
            goto L4a
        L18:
            java.lang.String r7 = "getSkuDetails"
            boolean r7 = r12.equals(r7)     // Catch: java.lang.NoSuchMethodException -> L9e
            if (r7 == 0) goto L4a
            r7 = r4
            goto L4b
        L22:
            java.lang.String r7 = "getPurchaseHistory"
            boolean r7 = r12.equals(r7)     // Catch: java.lang.NoSuchMethodException -> L9e
            if (r7 == 0) goto L4a
            r7 = r0
            goto L4b
        L2c:
            java.lang.String r7 = "asInterface"
            boolean r7 = r12.equals(r7)     // Catch: java.lang.NoSuchMethodException -> L9e
            if (r7 == 0) goto L4a
            r7 = r3
            goto L4b
        L36:
            java.lang.String r7 = "isBillingSupported"
            boolean r7 = r12.equals(r7)     // Catch: java.lang.NoSuchMethodException -> L9e
            if (r7 == 0) goto L4a
            r7 = r1
            goto L4b
        L40:
            java.lang.String r7 = "getPurchases"
            boolean r7 = r12.equals(r7)     // Catch: java.lang.NoSuchMethodException -> L9e
            if (r7 == 0) goto L4a
            r7 = r2
            goto L4b
        L4a:
            r7 = -1
        L4b:
            if (r7 == 0) goto L91
            java.lang.Class<android.os.Bundle> r8 = android.os.Bundle.class
            java.lang.Class<java.lang.String> r9 = java.lang.String.class
            if (r7 == r4) goto L84
            if (r7 == r1) goto L79
            if (r7 == r2) goto L6c
            if (r7 == r0) goto L5b
            r0 = 0
            goto L97
        L5b:
            r7 = 5
            java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch: java.lang.NoSuchMethodException -> L9e
            java.lang.Class r10 = java.lang.Integer.TYPE     // Catch: java.lang.NoSuchMethodException -> L9e
            r7[r3] = r10     // Catch: java.lang.NoSuchMethodException -> L9e
            r7[r4] = r9     // Catch: java.lang.NoSuchMethodException -> L9e
            r7[r1] = r9     // Catch: java.lang.NoSuchMethodException -> L9e
            r7[r2] = r9     // Catch: java.lang.NoSuchMethodException -> L9e
            r7[r0] = r8     // Catch: java.lang.NoSuchMethodException -> L9e
            r0 = r7
            goto L97
        L6c:
            java.lang.Class[] r0 = new java.lang.Class[r0]     // Catch: java.lang.NoSuchMethodException -> L9e
            java.lang.Class r7 = java.lang.Integer.TYPE     // Catch: java.lang.NoSuchMethodException -> L9e
            r0[r3] = r7     // Catch: java.lang.NoSuchMethodException -> L9e
            r0[r4] = r9     // Catch: java.lang.NoSuchMethodException -> L9e
            r0[r1] = r9     // Catch: java.lang.NoSuchMethodException -> L9e
            r0[r2] = r9     // Catch: java.lang.NoSuchMethodException -> L9e
            goto L97
        L79:
            java.lang.Class[] r0 = new java.lang.Class[r2]     // Catch: java.lang.NoSuchMethodException -> L9e
            java.lang.Class r2 = java.lang.Integer.TYPE     // Catch: java.lang.NoSuchMethodException -> L9e
            r0[r3] = r2     // Catch: java.lang.NoSuchMethodException -> L9e
            r0[r4] = r9     // Catch: java.lang.NoSuchMethodException -> L9e
            r0[r1] = r9     // Catch: java.lang.NoSuchMethodException -> L9e
            goto L97
        L84:
            java.lang.Class[] r0 = new java.lang.Class[r0]     // Catch: java.lang.NoSuchMethodException -> L9e
            java.lang.Class r7 = java.lang.Integer.TYPE     // Catch: java.lang.NoSuchMethodException -> L9e
            r0[r3] = r7     // Catch: java.lang.NoSuchMethodException -> L9e
            r0[r4] = r9     // Catch: java.lang.NoSuchMethodException -> L9e
            r0[r1] = r9     // Catch: java.lang.NoSuchMethodException -> L9e
            r0[r2] = r8     // Catch: java.lang.NoSuchMethodException -> L9e
            goto L97
        L91:
            java.lang.Class[] r0 = new java.lang.Class[r4]     // Catch: java.lang.NoSuchMethodException -> L9e
            java.lang.Class<android.os.IBinder> r1 = android.os.IBinder.class
            r0[r3] = r1     // Catch: java.lang.NoSuchMethodException -> L9e
        L97:
            java.lang.reflect.Method r6 = r11.getDeclaredMethod(r12, r0)     // Catch: java.lang.NoSuchMethodException -> L9e
            r5.put(r12, r6)     // Catch: java.lang.NoSuchMethodException -> L9e
        L9e:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.internal.InAppPurchaseEventManager.getMethod(java.lang.Class, java.lang.String):java.lang.reflect.Method");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x008e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.ArrayList<java.lang.String> getPurchaseHistory(android.content.Context r20, java.lang.Object r21, java.lang.String r22) throws java.lang.ClassNotFoundException {
        /*
            r0 = 0
            r1 = 1
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.lang.Boolean r3 = isBillingSupported(r20, r21, r22)
            boolean r3 = r3.booleanValue()
            if (r3 == 0) goto L9b
            r3 = 0
            r5 = r0
            r6 = r5
            r4 = r3
        L15:
            r7 = 6
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            android.os.Bundle r8 = new android.os.Bundle
            r8.<init>()
            r9 = 5
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r9[r0] = r7
            java.lang.String r7 = com.facebook.appevents.internal.InAppPurchaseEventManager.PACKAGE_NAME
            r9[r1] = r7
            r7 = 2
            r9[r7] = r22
            r7 = 3
            r9[r7] = r4
            r4 = 4
            r9[r4] = r8
            java.lang.String r4 = "com.android.vending.billing.IInAppBillingService"
            java.lang.String r7 = "getPurchaseHistory"
            r8 = r20
            r10 = r21
            java.lang.Object r4 = invokeMethod(r8, r4, r7, r10, r9)
            if (r4 == 0) goto L8e
            long r11 = java.lang.System.currentTimeMillis()
            r13 = 1000(0x3e8, double:4.94E-321)
            long r11 = r11 / r13
            android.os.Bundle r4 = (android.os.Bundle) r4
            java.lang.String r7 = "RESPONSE_CODE"
            int r7 = r4.getInt(r7)
            if (r7 != 0) goto L8e
            java.lang.String r7 = "INAPP_PURCHASE_DATA_LIST"
            java.util.ArrayList r7 = r4.getStringArrayList(r7)
            if (r7 != 0) goto L59
            goto L8e
        L59:
            java.util.Iterator r7 = r7.iterator()
        L5d:
            boolean r9 = r7.hasNext()
            if (r9 == 0) goto L86
            java.lang.Object r9 = r7.next()
            java.lang.String r9 = (java.lang.String) r9
            org.json.JSONObject r15 = new org.json.JSONObject     // Catch: org.json.JSONException -> L84
            r15.<init>(r9)     // Catch: org.json.JSONException -> L84
            java.lang.String r0 = "purchaseTime"
            long r16 = r15.getLong(r0)     // Catch: org.json.JSONException -> L84
            long r16 = r16 / r13
            long r16 = r11 - r16
            r18 = 1200(0x4b0, double:5.93E-321)
            int r0 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r0 <= 0) goto L80
            r6 = r1
            goto L86
        L80:
            r2.add(r9)     // Catch: org.json.JSONException -> L84
            int r5 = r5 + r1
        L84:
            r0 = 0
            goto L5d
        L86:
            java.lang.String r0 = "INAPP_CONTINUATION_TOKEN"
            java.lang.String r0 = r4.getString(r0)
            r4 = r0
            goto L8f
        L8e:
            r4 = r3
        L8f:
            r0 = 30
            if (r5 >= r0) goto L9b
            if (r4 == 0) goto L9b
            if (r6 == 0) goto L98
            goto L9b
        L98:
            r0 = 0
            goto L15
        L9b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.internal.InAppPurchaseEventManager.getPurchaseHistory(android.content.Context, java.lang.Object, java.lang.String):java.util.ArrayList");
    }

    static ArrayList<String> getPurchaseHistoryInapp(Context context, Object obj) {
        Class<?> cls;
        ArrayList<String> arrayList = new ArrayList<>();
        return (obj == null || (cls = getClass(context, IN_APP_BILLING_SERVICE)) == null || getMethod(cls, GET_PURCHASE_HISTORY) == null) ? arrayList : filterPurchases(getPurchaseHistory(context, obj, INAPP));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.ArrayList<java.lang.String> getPurchases(android.content.Context r9, java.lang.Object r10, java.lang.String r11) throws java.lang.ClassNotFoundException {
        /*
            r0 = 3
            r1 = 0
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            if (r10 != 0) goto La
            return r2
        La:
            java.lang.Boolean r3 = isBillingSupported(r9, r10, r11)
            boolean r3 = r3.booleanValue()
            if (r3 == 0) goto L5c
            r3 = 0
            r5 = r1
            r4 = r3
        L17:
            java.lang.Integer r6 = java.lang.Integer.valueOf(r0)
            r7 = 4
            java.lang.Object[] r7 = new java.lang.Object[r7]
            r7[r1] = r6
            java.lang.String r6 = com.facebook.appevents.internal.InAppPurchaseEventManager.PACKAGE_NAME
            r8 = 1
            r7[r8] = r6
            r6 = 2
            r7[r6] = r11
            r7[r0] = r4
            java.lang.String r4 = "com.android.vending.billing.IInAppBillingService"
            java.lang.String r6 = "getPurchases"
            java.lang.Object r4 = invokeMethod(r9, r4, r6, r10, r7)
            if (r4 == 0) goto L55
            android.os.Bundle r4 = (android.os.Bundle) r4
            java.lang.String r6 = "RESPONSE_CODE"
            int r6 = r4.getInt(r6)
            if (r6 != 0) goto L55
            java.lang.String r6 = "INAPP_PURCHASE_DATA_LIST"
            java.util.ArrayList r6 = r4.getStringArrayList(r6)
            if (r6 == 0) goto L5c
            int r7 = r6.size()
            int r5 = r5 + r7
            r2.addAll(r6)
            java.lang.String r6 = "INAPP_CONTINUATION_TOKEN"
            java.lang.String r4 = r4.getString(r6)
            goto L56
        L55:
            r4 = r3
        L56:
            r6 = 30
            if (r5 >= r6) goto L5c
            if (r4 != 0) goto L17
        L5c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.internal.InAppPurchaseEventManager.getPurchases(android.content.Context, java.lang.Object, java.lang.String):java.util.ArrayList");
    }

    static ArrayList<String> getPurchasesInapp(Context context, Object obj) {
        return filterPurchases(getPurchases(context, obj, INAPP));
    }

    static ArrayList<String> getPurchasesSubs(Context context, Object obj) {
        return filterPurchases(getPurchases(context, obj, SUBSCRIPTION));
    }

    static Map<String, String> getSkuDetails(Context context, ArrayList<String> arrayList, Object obj, boolean z2) {
        Map<String, String> skuDetailsFromCache = readSkuDetailsFromCache(arrayList);
        ArrayList arrayList2 = new ArrayList();
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (!skuDetailsFromCache.containsKey(next)) {
                arrayList2.add(next);
            }
        }
        skuDetailsFromCache.putAll(getSkuDetailsFromGoogle(context, arrayList2, obj, z2));
        return skuDetailsFromCache;
    }

    private static Map<String, String> getSkuDetailsFromGoogle(Context context, ArrayList<String> arrayList, Object obj, boolean z2) throws ClassNotFoundException {
        HashMap map = new HashMap();
        if (obj != null && !arrayList.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(ITEM_ID_LIST, arrayList);
            Object objInvokeMethod = invokeMethod(context, IN_APP_BILLING_SERVICE, GET_SKU_DETAILS, obj, new Object[]{3, PACKAGE_NAME, z2 ? SUBSCRIPTION : INAPP, bundle});
            if (objInvokeMethod != null) {
                Bundle bundle2 = (Bundle) objInvokeMethod;
                if (bundle2.getInt(RESPONSE_CODE) == 0) {
                    ArrayList<String> stringArrayList = bundle2.getStringArrayList(DETAILS_LIST);
                    if (stringArrayList != null && arrayList.size() == stringArrayList.size()) {
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            map.put(arrayList.get(i2), stringArrayList.get(i2));
                        }
                    }
                    writeSkuDetailsToCache(map);
                }
            }
        }
        return map;
    }

    static boolean hasFreeTrialPeirod(String str) {
        try {
            String strOptString = new JSONObject(str).optString("freeTrialPeriod");
            if (strOptString != null) {
                return !strOptString.isEmpty();
            }
            return false;
        } catch (JSONException unused) {
            return false;
        }
    }

    @Nullable
    private static Object invokeMethod(Context context, String str, String str2, Object obj, Object[] objArr) throws ClassNotFoundException {
        Method method;
        Class<?> cls = getClass(context, str);
        if (cls == null || (method = getMethod(cls, str2)) == null) {
            return null;
        }
        if (obj != null) {
            obj = cls.cast(obj);
        }
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return null;
        }
    }

    private static Boolean isBillingSupported(Context context, Object obj, String str) throws ClassNotFoundException {
        if (obj == null) {
            return Boolean.FALSE;
        }
        Object objInvokeMethod = invokeMethod(context, IN_APP_BILLING_SERVICE, IS_BILLING_SUPPORTED, obj, new Object[]{3, PACKAGE_NAME, str});
        return Boolean.valueOf(objInvokeMethod != null && ((Integer) objInvokeMethod).intValue() == 0);
    }

    private static Map<String, String> readSkuDetailsFromCache(ArrayList<String> arrayList) {
        HashMap map = new HashMap();
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            String next = it.next();
            String string = skuDetailSharedPrefs.getString(next, null);
            if (string != null) {
                String[] strArrSplit = string.split(i.f9802b, 2);
                if (jCurrentTimeMillis - Long.parseLong(strArrSplit[0]) < 43200) {
                    map.put(next, strArrSplit[1]);
                }
            }
        }
        return map;
    }

    private static void writeSkuDetailsToCache(Map<String, String> map) {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        SharedPreferences.Editor editorEdit = skuDetailSharedPrefs.edit();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            editorEdit.putString(entry.getKey(), jCurrentTimeMillis + i.f9802b + entry.getValue());
        }
        editorEdit.apply();
    }
}
