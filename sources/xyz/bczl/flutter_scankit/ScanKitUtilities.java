package xyz.bczl.flutter_scankit;

import com.huawei.hms.ml.scan.HmsScanBase;
import java.util.ArrayList;
import java.util.function.ToIntFunction;

/* loaded from: classes5.dex */
public class ScanKitUtilities {
    public static final int[] SCAN_TYPES = {HmsScanBase.AZTEC_SCAN_TYPE, HmsScanBase.CODABAR_SCAN_TYPE, HmsScanBase.CODE39_SCAN_TYPE, HmsScanBase.CODE93_SCAN_TYPE, HmsScanBase.CODE128_SCAN_TYPE, HmsScanBase.DATAMATRIX_SCAN_TYPE, HmsScanBase.EAN8_SCAN_TYPE, HmsScanBase.EAN13_SCAN_TYPE, HmsScanBase.ITF14_SCAN_TYPE, HmsScanBase.PDF417_SCAN_TYPE, HmsScanBase.QRCODE_SCAN_TYPE, HmsScanBase.UPCCODE_A_SCAN_TYPE, HmsScanBase.UPCCODE_E_SCAN_TYPE};

    public static int[] getArrayFromFlags(int i2) {
        if (i2 == 8191) {
            return new int[]{8191};
        }
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < 32; i3++) {
            if (((1 << i3) & i2) != 0) {
                arrayList.add(Integer.valueOf(SCAN_TYPES[i3]));
            }
        }
        return arrayList.stream().mapToInt(new ToIntFunction() { // from class: xyz.bczl.flutter_scankit.v
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Integer) obj).intValue();
            }
        }).toArray();
    }

    public static int getTypeFromFlags(int i2) {
        if (i2 == 8191) {
            return 8191;
        }
        for (int i3 = 0; i3 < 32; i3++) {
            if (((1 << i3) & i2) != 0) {
                return SCAN_TYPES[i3];
            }
        }
        return 8191;
    }

    public static int scanTypeToFlags(int i2) {
        if (i2 == 8191) {
            return 8191;
        }
        int i3 = 0;
        while (true) {
            int[] iArr = SCAN_TYPES;
            if (i3 >= iArr.length) {
                return 8191;
            }
            if (iArr[i3] == i2) {
                return 1 << i3;
            }
            i3++;
        }
    }
}
