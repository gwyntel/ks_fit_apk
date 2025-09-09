package c.a.b.h;

import com.aliyun.alink.business.devicecenter.config.ble.BreezeConstants;
import com.google.common.base.Ascii;

/* loaded from: classes2.dex */
public class c extends k {

    /* renamed from: b, reason: collision with root package name */
    public static byte[] f8093b = {1, 1, 1, 1, 1, 1, 1, 1, Ascii.US, Ascii.US, Ascii.US, Ascii.US, 14, 14, 14, 14, -32, -32, -32, -32, BreezeConstants.BREEZE_PROVISION_VERSION, BreezeConstants.BREEZE_PROVISION_VERSION, BreezeConstants.BREEZE_PROVISION_VERSION, BreezeConstants.BREEZE_PROVISION_VERSION, -2, -2, -2, -2, -2, -2, -2, -2, 1, -2, 1, -2, 1, -2, 1, -2, Ascii.US, -32, Ascii.US, -32, 14, BreezeConstants.BREEZE_PROVISION_VERSION, 14, BreezeConstants.BREEZE_PROVISION_VERSION, 1, -32, 1, -32, 1, BreezeConstants.BREEZE_PROVISION_VERSION, 1, BreezeConstants.BREEZE_PROVISION_VERSION, Ascii.US, -2, Ascii.US, -2, 14, -2, 14, -2, 1, Ascii.US, 1, Ascii.US, 1, 14, 1, 14, -32, -2, -32, -2, BreezeConstants.BREEZE_PROVISION_VERSION, -2, BreezeConstants.BREEZE_PROVISION_VERSION, -2, -2, 1, -2, 1, -2, 1, -2, 1, -32, Ascii.US, -32, Ascii.US, BreezeConstants.BREEZE_PROVISION_VERSION, 14, BreezeConstants.BREEZE_PROVISION_VERSION, 14, -32, 1, -32, 1, BreezeConstants.BREEZE_PROVISION_VERSION, 1, BreezeConstants.BREEZE_PROVISION_VERSION, 1, -2, Ascii.US, -2, Ascii.US, -2, 14, -2, 14, Ascii.US, 1, Ascii.US, 1, 14, 1, 14, 1, -2, -32, -2, -32, -2, BreezeConstants.BREEZE_PROVISION_VERSION, -2, BreezeConstants.BREEZE_PROVISION_VERSION};

    public static void a(byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b2 = bArr[i2];
            bArr[i2] = (byte) (((((b2 >> 7) ^ ((((((b2 >> 1) ^ (b2 >> 2)) ^ (b2 >> 3)) ^ (b2 >> 4)) ^ (b2 >> 5)) ^ (b2 >> 6))) ^ 1) & 1) | (b2 & 254));
        }
    }
}
