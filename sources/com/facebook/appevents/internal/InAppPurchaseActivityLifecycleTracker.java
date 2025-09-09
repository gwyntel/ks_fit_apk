package com.facebook.appevents.internal;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.facebook.FacebookSdk;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class InAppPurchaseActivityLifecycleTracker {
    private static final String BILLING_ACTIVITY_NAME = "com.android.billingclient.api.ProxyBillingActivity";
    private static final String SERVICE_INTERFACE_NAME = "com.android.vending.billing.IInAppBillingService$Stub";
    private static final String TAG = "com.facebook.appevents.internal.InAppPurchaseActivityLifecycleTracker";
    private static Application.ActivityLifecycleCallbacks callbacks;
    private static Object inAppBillingObj;
    private static Intent intent;
    private static ServiceConnection serviceConnection;
    private static final AtomicBoolean isTracking = new AtomicBoolean(false);
    private static Boolean hasBillingService = null;
    private static Boolean hasBiillingActivity = null;

    private static void initializeIfNotInitialized() throws ClassNotFoundException {
        if (hasBillingService != null) {
            return;
        }
        try {
            Class.forName(SERVICE_INTERFACE_NAME);
            Boolean bool = Boolean.TRUE;
            hasBillingService = bool;
            try {
                Class.forName(BILLING_ACTIVITY_NAME);
                hasBiillingActivity = bool;
            } catch (ClassNotFoundException unused) {
                hasBiillingActivity = Boolean.FALSE;
            }
            InAppPurchaseEventManager.clearSkuDetailsCache();
            intent = new Intent("com.android.vending.billing.InAppBillingService.BIND").setPackage("com.android.vending");
            serviceConnection = new ServiceConnection() { // from class: com.facebook.appevents.internal.InAppPurchaseActivityLifecycleTracker.1
                @Override // android.content.ServiceConnection
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    Object unused2 = InAppPurchaseActivityLifecycleTracker.inAppBillingObj = InAppPurchaseEventManager.asInterface(FacebookSdk.getApplicationContext(), iBinder);
                }

                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(ComponentName componentName) {
                }
            };
            callbacks = new Application.ActivityLifecycleCallbacks() { // from class: com.facebook.appevents.internal.InAppPurchaseActivityLifecycleTracker.2
                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityCreated(Activity activity, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityDestroyed(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityPaused(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityResumed(Activity activity) {
                    try {
                        FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.internal.InAppPurchaseActivityLifecycleTracker.2.1
                            @Override // java.lang.Runnable
                            public void run() throws JSONException {
                                Context applicationContext = FacebookSdk.getApplicationContext();
                                InAppPurchaseActivityLifecycleTracker.logPurchase(applicationContext, InAppPurchaseEventManager.getPurchasesInapp(applicationContext, InAppPurchaseActivityLifecycleTracker.inAppBillingObj), false);
                                InAppPurchaseActivityLifecycleTracker.logPurchase(applicationContext, InAppPurchaseEventManager.getPurchasesSubs(applicationContext, InAppPurchaseActivityLifecycleTracker.inAppBillingObj), true);
                            }
                        });
                    } catch (Exception unused2) {
                    }
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStarted(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStopped(Activity activity) {
                    try {
                        if (InAppPurchaseActivityLifecycleTracker.hasBiillingActivity.booleanValue() && activity.getLocalClassName().equals(InAppPurchaseActivityLifecycleTracker.BILLING_ACTIVITY_NAME)) {
                            FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.internal.InAppPurchaseActivityLifecycleTracker.2.2
                                @Override // java.lang.Runnable
                                public void run() throws JSONException {
                                    Context applicationContext = FacebookSdk.getApplicationContext();
                                    ArrayList<String> purchasesInapp = InAppPurchaseEventManager.getPurchasesInapp(applicationContext, InAppPurchaseActivityLifecycleTracker.inAppBillingObj);
                                    if (purchasesInapp.isEmpty()) {
                                        purchasesInapp = InAppPurchaseEventManager.getPurchaseHistoryInapp(applicationContext, InAppPurchaseActivityLifecycleTracker.inAppBillingObj);
                                    }
                                    InAppPurchaseActivityLifecycleTracker.logPurchase(applicationContext, purchasesInapp, false);
                                }
                            });
                        }
                    } catch (Exception unused2) {
                    }
                }
            };
        } catch (ClassNotFoundException unused2) {
            hasBillingService = Boolean.FALSE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logPurchase(Context context, ArrayList<String> arrayList, boolean z2) throws JSONException {
        if (arrayList.isEmpty()) {
            return;
        }
        HashMap map = new HashMap();
        ArrayList arrayList2 = new ArrayList();
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            String next = it.next();
            try {
                String string = new JSONObject(next).getString(AlinkConstants.KEY_PRODUCT_ID);
                map.put(string, next);
                arrayList2.add(string);
            } catch (JSONException e2) {
                Log.e(TAG, "Error parsing in-app purchase data.", e2);
            }
        }
        for (Map.Entry<String, String> entry : InAppPurchaseEventManager.getSkuDetails(context, arrayList2, inAppBillingObj, z2).entrySet()) {
            AutomaticAnalyticsLogger.logPurchase((String) map.get(entry.getKey()), entry.getValue(), z2);
        }
    }

    private static void startTracking() {
        if (isTracking.compareAndSet(false, true)) {
            Context applicationContext = FacebookSdk.getApplicationContext();
            if (applicationContext instanceof Application) {
                ((Application) applicationContext).registerActivityLifecycleCallbacks(callbacks);
                applicationContext.bindService(intent, serviceConnection, 1);
            }
        }
    }

    public static void update() {
        initializeIfNotInitialized();
        if (hasBillingService.booleanValue() && AutomaticAnalyticsLogger.isImplicitPurchaseLoggingEnabled()) {
            startTracking();
        }
    }
}
