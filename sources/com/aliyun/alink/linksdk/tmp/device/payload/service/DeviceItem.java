package com.aliyun.alink.linksdk.tmp.device.payload.service;

import androidx.annotation.Nullable;

/* loaded from: classes2.dex */
public class DeviceItem {
    public String iotId;

    public DeviceItem(String str) {
        this.iotId = str;
    }

    public boolean equals(@Nullable Object obj) {
        return obj instanceof String ? obj.equals(this.iotId) : super.equals(obj);
    }
}
