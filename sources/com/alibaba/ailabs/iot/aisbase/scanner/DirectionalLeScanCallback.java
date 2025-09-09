package com.alibaba.ailabs.iot.aisbase.scanner;

import android.content.Context;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceSubtype;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.ailabs.tg.utils.LogUtils;
import java.lang.reflect.Array;

/* loaded from: classes2.dex */
public class DirectionalLeScanCallback<T extends BluetoothDeviceWrapper> implements ILeScanCallback {

    /* renamed from: a, reason: collision with root package name */
    public static final String f8545a = "DirectionalLeScanCallback";

    /* renamed from: b, reason: collision with root package name */
    public Context f8546b;

    /* renamed from: c, reason: collision with root package name */
    public String[] f8547c;

    /* renamed from: d, reason: collision with root package name */
    public IActionListener<T[]> f8548d;

    /* renamed from: e, reason: collision with root package name */
    public T[] f8549e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f8550f = false;

    /* renamed from: g, reason: collision with root package name */
    public Class<T> f8551g;

    public DirectionalLeScanCallback(Context context, String[] strArr, IActionListener<T[]> iActionListener, Class<T> cls) {
        this.f8551g = cls;
        this.f8546b = context;
        this.f8547c = strArr;
        this.f8548d = iActionListener;
        this.f8549e = (T[]) ((BluetoothDeviceWrapper[]) Array.newInstance((Class<?>) cls, strArr.length));
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
    public void onAliBLEDeviceFound(BluetoothDeviceWrapper bluetoothDeviceWrapper, BluetoothDeviceSubtype bluetoothDeviceSubtype) {
        T[] tArr;
        if (this.f8551g.isInstance(bluetoothDeviceWrapper)) {
            String address = bluetoothDeviceWrapper.getAddress();
            Object[] objArr = this.f8547c;
            int length = objArr.length;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                if (address.equals(objArr[i3])) {
                    ((T[]) this.f8549e)[i4] = bluetoothDeviceWrapper;
                    break;
                } else {
                    i4++;
                    i3++;
                }
            }
            while (true) {
                tArr = this.f8549e;
                if (i2 >= tArr.length || tArr[i2] == null) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 >= tArr.length) {
                this.f8550f = true;
                BLEScannerProxy.getInstance().stopDirectionalScan();
                if (this.f8548d != null) {
                    LogUtils.d(f8545a, "Directional scanning completed");
                    this.f8548d.onSuccess(this.f8549e);
                }
            }
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
    public void onStartScan() {
        LogUtils.d(f8545a, "Start directional scanning");
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback
    public void onStopScan() {
        if (this.f8550f || this.f8548d == null) {
            return;
        }
        this.f8550f = true;
        LogUtils.d(f8545a, "Directional scanning completed");
        this.f8548d.onSuccess(this.f8549e);
    }
}
