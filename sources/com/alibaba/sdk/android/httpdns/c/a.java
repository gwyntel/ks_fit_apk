package com.alibaba.sdk.android.httpdns.c;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: i, reason: collision with root package name */
    private int f8809i;

    /* renamed from: q, reason: collision with root package name */
    private String f8810q;

    /* renamed from: com.alibaba.sdk.android.httpdns.c.a$a, reason: collision with other inner class name */
    private static final class C0025a {

        /* renamed from: b, reason: collision with root package name */
        private static final a f8811b = new a();
    }

    private a() {
        this.f8809i = 0;
        this.f8810q = "UNKNOWN";
    }

    private int a(Context context) {
        ConnectivityManager connectivityManager;
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        } catch (Exception unused) {
        }
        if (connectivityManager == null) {
            return 0;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == 1) {
                return 1;
            }
            if (activeNetworkInfo.getType() != 0) {
                return 0;
            }
            switch (activeNetworkInfo.getSubtype()) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return 2;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 15:
                    return 3;
                case 12:
                case 14:
                default:
                    return 0;
                case 13:
                    return 4;
            }
        }
        return 255;
    }

    private void d(Context context) {
        int iA = a(context);
        this.f8809i = iA;
        this.f8810q = "sp_" + iA;
    }

    public void c(Context context) {
        d(context);
    }

    public int getNetworkType() {
        return this.f8809i;
    }

    public boolean h() {
        return this.f8809i == 1;
    }

    public String i() {
        return this.f8810q;
    }

    public static a a() {
        return C0025a.f8811b;
    }
}
