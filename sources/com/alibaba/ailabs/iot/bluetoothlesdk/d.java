package com.alibaba.ailabs.iot.bluetoothlesdk;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.iot.aisbase.utils.ut.UTUtil;

/* loaded from: classes2.dex */
public class d<T> implements IActionListener<T> {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f8645a = true;

    /* renamed from: b, reason: collision with root package name */
    private IActionListener<T> f8646b;

    /* renamed from: c, reason: collision with root package name */
    private BluetoothDeviceWrapper f8647c;

    /* renamed from: d, reason: collision with root package name */
    private String f8648d;

    public d(IActionListener<T> iActionListener, BluetoothDeviceWrapper bluetoothDeviceWrapper, String str) {
        this.f8646b = iActionListener;
        this.f8647c = bluetoothDeviceWrapper;
        this.f8648d = str;
        a(str, bluetoothDeviceWrapper, "start", 0, "");
    }

    private void a(String str, BluetoothDeviceWrapper bluetoothDeviceWrapper, String str2, int i2, String str3) {
        if (f8645a) {
            UTUtil.updateBusInfo(str, bluetoothDeviceWrapper, str2, i2, str3);
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        IActionListener<T> iActionListener = this.f8646b;
        if (iActionListener != null) {
            iActionListener.onFailure(i2, str);
        }
        a(this.f8648d, this.f8647c, "error", i2, str);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onSuccess(T t2) {
        IActionListener<T> iActionListener = this.f8646b;
        if (iActionListener != null) {
            iActionListener.onSuccess(t2);
        }
        a(this.f8648d, this.f8647c, "success", 0, "");
    }
}
