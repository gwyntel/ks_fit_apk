package com.yc.utesdk.watchface.close;

import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.watchface.bean.acts.DevicePacketDataInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class DevicePacketDataUtils {
    private static DevicePacketDataUtils instance;

    public static DevicePacketDataUtils getInstance() {
        if (instance == null) {
            instance = new DevicePacketDataUtils();
        }
        return instance;
    }

    public List<DevicePacketDataInfo> doDevicePacketData(int i2, int i3, int i4, byte[] bArr) {
        int i5;
        int i6;
        byte b2;
        int i7 = i3 + i4;
        ArrayList arrayList = new ArrayList();
        int i8 = 0;
        while (i2 < bArr.length) {
            int i9 = i3 == 1 ? bArr[i2] & 255 : i3 == 2 ? ((bArr[i2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[i2 + 1] & 255) : 0;
            if (i4 == 1) {
                i8 = bArr[i2 + i3] & 255;
            } else if (i4 == 2) {
                i8 = ((bArr[i2 + i3] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[i2 + 1 + i3] & 255);
            }
            byte[] bArr2 = new byte[i8];
            System.arraycopy(bArr, i7 + i2, bArr2, 0, i8);
            if (i8 == 1) {
                i5 = bArr2[0] & 255;
            } else {
                if (i8 == 2) {
                    i6 = (bArr2[0] & 255) << 8;
                    b2 = bArr2[1];
                } else if (i8 == 3) {
                    i5 = (bArr2[2] & 255) | ((bArr2[1] & 255) << 8) | ((bArr2[0] & 255) << 16);
                } else if (i8 == 4) {
                    i6 = ((bArr2[2] & 255) << 8) | ((bArr2[1] & 255) << 16) | ((bArr2[0] & 255) << 24);
                    b2 = bArr2[3];
                } else {
                    i5 = 0;
                }
                i5 = i6 | (b2 & 255);
            }
            arrayList.add(new DevicePacketDataInfo(i9, i8, bArr2, i5));
            i2 += i7 + i8;
        }
        return arrayList;
    }
}
