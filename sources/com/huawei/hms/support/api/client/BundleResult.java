package com.huawei.hms.support.api.client;

import android.os.Bundle;

/* loaded from: classes4.dex */
public class BundleResult {

    /* renamed from: a, reason: collision with root package name */
    private int f18101a;

    /* renamed from: b, reason: collision with root package name */
    private Bundle f18102b;

    public BundleResult(int i2, Bundle bundle) {
        this.f18101a = i2;
        this.f18102b = bundle;
    }

    public int getResultCode() {
        return this.f18101a;
    }

    public Bundle getRspBody() {
        return this.f18102b;
    }

    public void setResultCode(int i2) {
        this.f18101a = i2;
    }

    public void setRspBody(Bundle bundle) {
        this.f18102b = bundle;
    }
}
