package com.yc.utesdk.utils.open;

import android.os.SystemClock;
import com.yc.utesdk.ble.close.UteDeviceDataAnalysis;
import com.yc.utesdk.log.LogUtils;
import java.text.ParseException;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class BleReturnDataTest {
    private static BleReturnDataTest instance;

    public static synchronized BleReturnDataTest getInstance() {
        try {
            if (instance == null) {
                instance = new BleReturnDataTest();
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    public void stepTest() throws ParseException {
        ArrayList arrayList = new ArrayList();
        arrayList.add("B107E70C1B11012200000000000002020122");
        arrayList.add("B207E70C1B0D08260000000000273B120826");
        arrayList.add("B207E70C1B0E079700000000003B3B0B0797");
        arrayList.add("B207E70C1B0F0CE600000000003B13110CE6");
        arrayList.add("B107E70C1B11012300000000000002020123");
        arrayList.add("B207E70C1B1009DE00000000001A3B1009DE");
        arrayList.add("B207E70C1B11012200000000003B02020122");
        arrayList.add("B2FDCC");
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            testBleReturnData((String) arrayList.get(i2));
            SystemClock.sleep(20L);
        }
    }

    public void testBleReturnData(String str) throws ParseException {
        byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(str);
        LogUtils.i("BLE--->APK4 =" + str);
        UteDeviceDataAnalysis.getInstance().datePacketOperate(str, bArrHexStringToBytes);
    }

    public void testBleReturnData(byte[] bArr) throws ParseException {
        UteDeviceDataAnalysis.getInstance().datePacketOperate(GBUtils.getInstance().bytes2HexString(bArr), bArr);
    }
}
