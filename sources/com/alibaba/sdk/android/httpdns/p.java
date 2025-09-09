package com.alibaba.sdk.android.httpdns;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class p {

    /* renamed from: a, reason: collision with root package name */
    private static Context f8882a = null;

    /* renamed from: i, reason: collision with root package name */
    static boolean f8883i = false;

    /* renamed from: j, reason: collision with root package name */
    private static String f8884j;

    /* JADX INFO: Access modifiers changed from: private */
    public static String f() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) f8882a.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                String typeName = activeNetworkInfo.getTypeName();
                i.d("[detectCurrentNetwork] - Network name:" + typeName + " subType name: " + activeNetworkInfo.getSubtypeName());
                return typeName == null ? "None_Network" : typeName;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return "None_Network";
    }

    public static void setContext(Context context) {
        if (context == null) {
            throw new IllegalStateException("Context can't be null");
        }
        f8882a = context;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.alibaba.sdk.android.httpdns.p.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                try {
                    if (!isInitialStickyBroadcast() && ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                        com.alibaba.sdk.android.httpdns.b.b.b(context2);
                        String strF = p.f();
                        if (strF != "None_Network" && !strF.equalsIgnoreCase(p.f8884j)) {
                            i.d("[BroadcastReceiver.onReceive] - Network state changed");
                            ArrayList<String> arrayListM34a = d.a().m34a();
                            d.a().clear();
                            d.a().m35a();
                            if (p.f8883i && HttpDns.instance != null) {
                                i.d("[BroadcastReceiver.onReceive] - refresh host");
                                HttpDns.instance.setPreResolveHosts(arrayListM34a);
                            }
                        }
                        String unused = p.f8884j = strF;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION);
            f8882a.registerReceiver(broadcastReceiver, intentFilter);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
