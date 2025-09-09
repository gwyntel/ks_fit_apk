package xyz.bczl.flutter_scankit;

import java.util.HashMap;

/* loaded from: classes5.dex */
public class ScanResult {
    private String originalValue;
    private int scanType;

    public ScanResult(String str, int i2) {
        this.originalValue = str;
        this.scanType = i2;
    }

    HashMap a() {
        HashMap map = new HashMap();
        map.put("originalValue", this.originalValue);
        map.put("scanType", Integer.valueOf(this.scanType));
        return map;
    }

    public String getOriginalValue() {
        return this.originalValue;
    }

    public int getScanType() {
        return this.scanType;
    }
}
