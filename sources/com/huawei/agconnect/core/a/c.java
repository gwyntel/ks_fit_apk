package com.huawei.agconnect.core.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.util.Log;
import com.huawei.agconnect.core.Service;
import com.huawei.agconnect.core.ServiceDiscovery;
import com.huawei.agconnect.core.ServiceRegistrar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private final Context f15644a;

    private static class a implements Serializable, Comparator<Map.Entry<String, Integer>> {
        private a() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(Map.Entry<String, Integer> entry, Map.Entry<String, Integer> entry2) {
            return entry.getValue().intValue() - entry2.getValue().intValue();
        }
    }

    c(Context context) {
        this.f15644a = context;
    }

    private <T extends ServiceRegistrar> T a(String str) throws ClassNotFoundException {
        StringBuilder sb;
        String string;
        try {
            Class<?> cls = Class.forName(str);
            if (ServiceRegistrar.class.isAssignableFrom(cls)) {
                return (T) Class.forName(str).newInstance();
            }
            Log.e("ServiceRegistrarParser", cls + " must extends from ServiceRegistrar.");
            return null;
        } catch (ClassNotFoundException e2) {
            string = "Can not found service class, " + e2.getMessage();
            Log.e("ServiceRegistrarParser", string);
            return null;
        } catch (IllegalAccessException e3) {
            e = e3;
            sb = new StringBuilder();
            sb.append("instantiate service class exception ");
            sb.append(e.getLocalizedMessage());
            string = sb.toString();
            Log.e("ServiceRegistrarParser", string);
            return null;
        } catch (InstantiationException e4) {
            e = e4;
            sb = new StringBuilder();
            sb.append("instantiate service class exception ");
            sb.append(e.getLocalizedMessage());
            string = sb.toString();
            Log.e("ServiceRegistrarParser", string);
            return null;
        }
    }

    private List<String> b() throws PackageManager.NameNotFoundException {
        StringBuilder sb;
        ArrayList arrayList = new ArrayList();
        Bundle bundleC = c();
        if (bundleC == null) {
            return arrayList;
        }
        HashMap map = new HashMap(10);
        for (String message : bundleC.keySet()) {
            if ("com.huawei.agconnect.core.ServiceRegistrar".equals(bundleC.getString(message))) {
                String[] strArrSplit = message.split(":");
                if (strArrSplit.length == 2) {
                    try {
                        map.put(strArrSplit[0], Integer.valueOf(strArrSplit[1]));
                    } catch (NumberFormatException e2) {
                        sb = new StringBuilder();
                        sb.append("registrar configuration format error:");
                        message = e2.getMessage();
                    }
                } else if (strArrSplit.length == 1) {
                    map.put(strArrSplit[0], 1000);
                } else {
                    sb = new StringBuilder();
                    sb.append("registrar configuration error, ");
                    sb.append(message);
                    Log.e("ServiceRegistrarParser", sb.toString());
                }
            }
        }
        ArrayList arrayList2 = new ArrayList(map.entrySet());
        Collections.sort(arrayList2, new a());
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList.add(((Map.Entry) it.next()).getKey());
        }
        return arrayList;
    }

    private Bundle c() throws PackageManager.NameNotFoundException {
        ServiceInfo serviceInfo;
        PackageManager packageManager = this.f15644a.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            serviceInfo = packageManager.getServiceInfo(new ComponentName(this.f15644a, (Class<?>) ServiceDiscovery.class), 128);
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("ServiceRegistrarParser", "get ServiceDiscovery exception." + e2.getLocalizedMessage());
        }
        if (serviceInfo != null) {
            return serviceInfo.metaData;
        }
        Log.e("ServiceRegistrarParser", "Can not found ServiceDiscovery service.");
        return null;
    }

    public List<Service> a() throws PackageManager.NameNotFoundException, ClassNotFoundException {
        Log.i("ServiceRegistrarParser", "getServices");
        List<String> listB = b();
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = listB.iterator();
        while (it.hasNext()) {
            ServiceRegistrar serviceRegistrarA = a(it.next());
            if (serviceRegistrarA != null) {
                serviceRegistrarA.initialize(this.f15644a);
                List<Service> services = serviceRegistrarA.getServices(this.f15644a);
                if (services != null) {
                    arrayList.addAll(services);
                }
            }
        }
        Log.i("ServiceRegistrarParser", "services:" + Integer.valueOf(arrayList.size()));
        return arrayList;
    }
}
