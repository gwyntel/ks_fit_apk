package com.yc.utesdk.scan;

import java.util.List;

/* loaded from: classes4.dex */
public interface UteScanCallback {
    void onScanComplete(List<UteScanDevice> list);

    void onScanning(UteScanDevice uteScanDevice);
}
