package com.huawei.hms.opendevice;

import com.huawei.secure.android.common.encrypt.utils.HexUtil;

/* loaded from: classes4.dex */
public abstract class c {
    public static String a(byte[] bArr) {
        return HexUtil.byteArray2HexStr(bArr);
    }

    public static byte[] a(String str) {
        return HexUtil.hexStr2ByteArray(str);
    }
}
