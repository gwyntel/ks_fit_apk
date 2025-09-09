package com.huawei.hms.scankit.p;

import android.hardware.Camera;
import com.huawei.hms.scankit.p.j0;

/* loaded from: classes4.dex */
public class f6 implements Camera.PreviewCallback {

    /* renamed from: a, reason: collision with root package name */
    private j0.e f17248a;

    public f6(j0.e eVar) {
        this.f17248a = eVar;
    }

    @Override // android.hardware.Camera.PreviewCallback
    public void onPreviewFrame(byte[] bArr, Camera camera) {
        this.f17248a.a(bArr);
    }
}
