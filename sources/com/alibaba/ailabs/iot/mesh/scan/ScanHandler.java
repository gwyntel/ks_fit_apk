package com.alibaba.ailabs.iot.mesh.scan;

import aisscanner.ScanResult;
import com.alibaba.ailabs.iot.mesh.ScanStatusCallback;

/* loaded from: classes2.dex */
public interface ScanHandler {
    void exit();

    void onScanFailed(int i2, String str);

    void onScanResult(ScanResult scanResult, Scanner scanner);

    void onScanStop();

    void setScanStatusCallback(ScanStatusCallback scanStatusCallback);
}
