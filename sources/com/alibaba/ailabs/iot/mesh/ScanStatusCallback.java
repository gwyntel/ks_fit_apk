package com.alibaba.ailabs.iot.mesh;

import com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice;
import java.util.List;

/* loaded from: classes2.dex */
public interface ScanStatusCallback extends StatusCallback {
    void onScannResult(List<ExtendedBluetoothDevice> list, boolean z2);
}
