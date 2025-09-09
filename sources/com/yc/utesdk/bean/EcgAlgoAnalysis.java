package com.yc.utesdk.bean;

import aisble.error.GattError;
import com.aliyun.alink.linksdk.tmp.utils.ErrorCode;
import com.yc.utesdk.ble.close.KeyType;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.utils.close.EcgRealTimeHrAnalysis;
import java.util.ArrayList;
import javax.jmdns.impl.constants.DNSRecordClass;

/* loaded from: classes4.dex */
public class EcgAlgoAnalysis {
    private static EcgAlgoAnalysis instance;
    private boolean isUseBleLib;
    private int sFs = 100;
    private final int fL = 5;
    private final float[] strw = {0.1f, 0.3f, 0.7f, 0.3f, 0.1f};
    private float sumStrw = 1.5f;
    private ArrayList<Short> ttSig = new ArrayList<>();
    private int RTCnt = 0;
    private int filterCnt = 0;
    private boolean isFilterEcgBeforeData = true;
    private final int[] indexTable16to4 = {-1, -1, -1, -1, 2, 4, 6, 8, -1, -1, -1, -1, 2, 4, 6, 8};
    private final int[] stepsizeTable16to4 = {7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 21, 23, 25, 28, 31, 34, 37, 41, 45, 50, 55, 60, 66, 73, 80, 88, 97, 107, 118, 130, 143, 157, 173, 190, 209, 230, GattError.GATT_CCCD_CFG_ERROR, KeyType.QUERY_AI_WATCH_ENABLE, 307, 337, 371, ErrorCode.ERROR_CODE_TIMEOUT, 449, 494, 544, 598, 658, 724, 796, 876, 963, 1060, 1166, 1282, 1411, 1552, 1707, 1878, 2066, 2272, 2499, 2749, 3024, 3327, 3660, 4026, 4428, 4871, 5358, 5894, 6484, 7132, 7845, 8630, 9493, 10442, 11487, 12635, 13899, 15289, 16818, 18500, 20350, 22385, 24623, 27086, 29794, DNSRecordClass.CLASS_MASK};
    private int state_valprev = 0;
    private int state_index = 0;
    private EcgRealTimeHrAnalysis mEcgRealTimeHrAnalysis = new EcgRealTimeHrAnalysis();

    public EcgAlgoAnalysis() {
        this.isUseBleLib = false;
        this.isUseBleLib = DeviceMode.isHasFunction_5(128);
    }

    public static EcgAlgoAnalysis getInstance() {
        if (instance == null) {
            instance = new EcgAlgoAnalysis();
        }
        return instance;
    }

    public void EcgHRAndRWaveAnalysis(short s2) {
        int iCalRealTimeHR = this.mEcgRealTimeHrAnalysis.calRealTimeHR(s2);
        int i2 = this.RTCnt + 1;
        this.RTCnt = i2;
        int i3 = this.sFs;
        if (i2 >= i3) {
            if (iCalRealTimeHR != 0) {
                this.RTCnt = 0;
            } else {
                this.RTCnt = i3;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0047 A[PHI: r2
      0x0047: PHI (r2v10 int) = (r2v6 int), (r2v7 int) binds: [B:26:0x0045, B:29:0x004b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public short adpcm_decoder16to4(byte r11, int r12) {
        /*
            Method dump skipped, instructions count: 183
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.bean.EcgAlgoAnalysis.adpcm_decoder16to4(byte, int):short");
    }

    public int getAverageHR() {
        return this.mEcgRealTimeHrAnalysis.getAverageHR();
    }

    public int getHRV() {
        return this.mEcgRealTimeHrAnalysis.getHRV();
    }

    public void resetVector() {
        this.mEcgRealTimeHrAnalysis.clearParameter();
        this.RTCnt = 0;
        this.state_valprev = 0;
        this.state_index = 0;
        this.filterCnt = 0;
        this.isFilterEcgBeforeData = true;
    }
}
