package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.BluetoothAdapter;
import androidx.annotation.RequiresPermission;

/* renamed from: com.alibaba.ailabs.iot.aisbase.e, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0396e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0404i f8398a;

    public RunnableC0396e(C0404i c0404i) {
        this.f8398a = c0404i;
    }

    @Override // java.lang.Runnable
    @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
    public void run() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || this.f8398a.f8413f <= 0 || this.f8398a.f8414g <= 0) {
            return;
        }
        defaultAdapter.stopLeScan(this.f8398a.f8417j);
        this.f8398a.f8412e.postDelayed(this.f8398a.f8416i, this.f8398a.f8413f);
    }
}
