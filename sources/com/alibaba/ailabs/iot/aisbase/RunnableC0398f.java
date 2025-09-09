package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.BluetoothAdapter;
import androidx.annotation.RequiresPermission;

/* renamed from: com.alibaba.ailabs.iot.aisbase.f, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0398f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0404i f8402a;

    public RunnableC0398f(C0404i c0404i) {
        this.f8402a = c0404i;
    }

    @Override // java.lang.Runnable
    @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
    public void run() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || this.f8402a.f8413f <= 0 || this.f8402a.f8414g <= 0) {
            return;
        }
        defaultAdapter.startLeScan(this.f8402a.f8417j);
        this.f8402a.f8412e.postDelayed(this.f8402a.f8415h, this.f8402a.f8414g);
    }
}
