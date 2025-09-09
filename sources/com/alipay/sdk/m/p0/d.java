package com.alipay.sdk.m.p0;

import android.database.ContentObserver;
import android.util.Log;

/* loaded from: classes2.dex */
public class d extends ContentObserver {

    /* renamed from: d, reason: collision with root package name */
    public static final String f9671d = "VMS_IDLG_SDK_Observer";

    /* renamed from: a, reason: collision with root package name */
    public String f9672a;

    /* renamed from: b, reason: collision with root package name */
    public int f9673b;

    /* renamed from: c, reason: collision with root package name */
    public c f9674c;

    public d(c cVar, int i2, String str) {
        super(null);
        this.f9674c = cVar;
        this.f9673b = i2;
        this.f9672a = str;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z2) {
        c cVar = this.f9674c;
        if (cVar != null) {
            cVar.a(this.f9673b, this.f9672a);
        } else {
            Log.e(f9671d, "mIdentifierIdClient is null");
        }
    }
}
