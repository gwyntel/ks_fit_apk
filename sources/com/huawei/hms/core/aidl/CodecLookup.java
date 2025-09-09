package com.huawei.hms.core.aidl;

/* loaded from: classes4.dex */
public final class CodecLookup {
    private CodecLookup() {
    }

    public static MessageCodec find(int i2) {
        return i2 == 2 ? new MessageCodecV2() : new MessageCodec();
    }
}
