package com.yc.utesdk.ota;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.ble.close.KeyType;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.SPUtil;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/* loaded from: classes4.dex */
public class FrkBleOTAUtils {
    public static final int FRK_OTA_CM3_TYPE = 0;
    public static final int FRK_OTA_DSP_TYPE = 1;
    public static final int OTA_STATE_CAN_CONTINUE_OTA = 3;
    public static final int OTA_STATE_FINISH = 2;
    public static final int STATE_OTA_PROGRESS = 1;
    public static final int STATE_OTA_START = 0;
    private static FrkBleOTAUtils instance = null;
    public static boolean isFRKOTAING = false;

    /* renamed from: a, reason: collision with root package name */
    DeviceBusyLockUtils f24930a;
    private int mBaseAddr;
    private CommandTimeOutUtils mCommandTimeOut;
    private int mOffSet;
    private int packageSize;
    private final int FRK_CM3_CHIP_TYPE = 0;
    private final int FRK_CM3_BASE_ADDRESS_TYPE = 1;
    private final int FRK_CM3_ERASE_TYPE = 3;
    private final int FRK_CM3_WRITE_TYPE = 5;
    private final int FRK_CM3_REBOOT_TYPE = 9;
    private final int FRK_DSP_CHIP_TYPE = 11;
    private final int FRK_DSP_BASE_ADDRESS_TYPE = 12;
    private final int FRK_DSP_ERASE_TYPE = 14;
    private final int FRK_DSP_WRITE_TYPE = 16;
    private final int FRK_DSP_REBOOT_TYPE = 20;
    private final int FLAG_SUCCESS = 0;
    private int mOtaType = -1;
    private byte[] mFileData = new byte[0];
    private int mFileCRC = 0;
    private long mFileSize = 0;
    private int mEraseTotalCount = 0;
    private int mEraseCurCount = 0;
    private int mWriteTotalCount = 0;
    private int mWriteCurCount = 0;
    private int mWritePrecent = -1;
    private boolean isFRKOTAProgress = false;
    private int delayWriteTime = 2;
    private final int FRK_CHIP_MSG = 1;
    private final int FRK_BASE_ADDRESS_MSG = 2;
    private final int FRK_ERASE_MSG = 3;
    private final int FRK_WRITE_MSG = 4;
    private final int FRK_REBOOT_MSG = 5;
    private final int FRK_CONTINUE_OTA_MSG = 6;
    private FrkBleCallBack mFrkBleCallBack = null;
    private Handler mHandler = new utendo(Looper.getMainLooper());

    /* renamed from: b, reason: collision with root package name */
    String f24931b = "";
    private final int[] crc_table = {0, 1996959894, -301047508, -1727442502, 124634137, 1886057615, -379345611, -1637575261, 249268274, 2044508324, -522852066, -1747789432, 162941995, 2125561021, -407360249, -1866523247, 498536548, 1789927666, -205950648, -2067906082, 450548861, 1843258603, -187386543, -2083289657, 325883990, 1684777152, -43845254, -1973040660, 335633487, 1661365465, -99664541, -1928851979, 997073096, 1281953886, -715111964, -1570279054, 1006888145, 1258607687, -770865667, -1526024853, 901097722, 1119000684, -608450090, -1396901568, 853044451, 1172266101, -589951537, -1412350631, 651767980, 1373503546, -925412992, -1076862698, 565507253, 1454621731, -809855591, -1195530993, 671266974, 1594198024, -972236366, -1324619484, 795835527, 1483230225, -1050600021, -1234817731, 1994146192, 31158534, -1731059524, -271249366, 1907459465, 112637215, -1614814043, -390540237, 2013776290, 251722036, -1777751922, -519137256, 2137656763, 141376813, -1855689577, -429695999, 1802195444, 476864866, -2056965928, -228458418, 1812370925, 453092731, -2113342271, -183516073, 1706088902, 314042704, -1950435094, -54949764, 1658658271, 366619977, -1932296973, -69972891, 1303535960, 984961486, -1547960204, -725929758, 1256170817, 1037604311, -1529756563, -740887301, 1131014506, 879679996, -1385723834, -631195440, 1141124467, 855842277, -1442165665, -586318647, 1342533948, 654459306, -1106571248, -921952122, 1466479909, 544179635, -1184443383, -832445281, 1591671054, 702138776, -1328506846, -942167884, 1504918807, 783551873, -1212326853, -1061524307, -306674912, -1698712650, 62317068, 1957810842, -355121351, -1647151185, 81470997, 1943803523, -480048366, -1805370492, 225274430, 2053790376, -468791541, -1828061283, 167816743, 2097651377, -267414716, -2029476910, 503444072, 1762050814, -144550051, -2140837941, 426522225, 1852507879, -19653770, -1982649376, 282753626, 1742555852, -105259153, -1900089351, 397917763, 1622183637, -690576408, -1580100738, 953729732, 1340076626, -776247311, -1497606297, 1068828381, 1219638859, -670225446, -1358292148, 906185462, 1090812512, -547295293, -1469587627, 829329135, 1181335161, -882789492, -1134132454, 628085408, 1382605366, -871598187, -1156888829, 570562233, 1426400815, -977650754, -1296233688, 733239954, 1555261956, -1026031705, -1244606671, 752459403, 1541320221, -1687895376, -328994266, 1969922972, 40735498, -1677130071, -351390145, 1913087877, 83908371, -1782625662, -491226604, 2075208622, 213261112, -1831694693, -438977011, 2094854071, 198958881, -2032938284, -237706686, 1759359992, 534414190, -2118248755, -155638181, 1873836001, 414664567, -2012718362, -15766928, 1711684554, 285281116, -1889165569, -127750551, 1634467795, 376229701, -1609899400, -686959890, 1308918612, 956543938, -1486412191, -799009033, 1231636301, 1047427035, -1362007478, -640263460, 1088359270, 936918000, -1447252397, -558129467, 1202900863, 817233897, -1111625188, -893730166, 1404277552, 615818150, -1160759803, -841546093, 1423857449, 601450431, -1285129682, -1000256840, 1567103746, 711928724, -1274298825, -1022587231, 1510334235, 755167117};

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Message message2;
            int i2;
            super.handleMessage(message);
            int i3 = message.what;
            if (i3 == 1) {
                if (FrkBleOTAUtils.this.f24930a.getDeviceBusy()) {
                    LogUtils.i("设备忙，等2ms");
                    message2 = new Message();
                    message2.what = 1;
                    message2.arg1 = message.arg1;
                }
                FrkBleOTAUtils.this.queryBaseStorageAddress();
                return;
            }
            if (i3 != 2) {
                if (i3 != 3) {
                    if (i3 != 4) {
                        if (i3 != 6) {
                            return;
                        }
                        FrkBleOTAUtils.this.mFrkBleCallBack.onBleOtaStateChange(3, 0);
                        return;
                    }
                    if (!FrkBleOTAUtils.this.f24930a.getDeviceBusy()) {
                        if (message.arg1 != 0) {
                            FrkBleOTAUtils.this.sendOtaDataSegments();
                            return;
                        }
                        if (FrkBleOTAUtils.this.mWriteCurCount >= FrkBleOTAUtils.this.mWriteTotalCount - 1) {
                            FrkBleOTAUtils.this.utendo();
                            FrkBleOTAUtils.this.mFrkBleCallBack.onBleOtaStateChange(2, 100);
                            FrkBleOTAUtils.isFRKOTAING = false;
                            return;
                        }
                        FrkBleOTAUtils.utenlong(FrkBleOTAUtils.this);
                        FrkBleOTAUtils.this.sendOtaDataSegments();
                        i2 = (((FrkBleOTAUtils.this.mEraseTotalCount + FrkBleOTAUtils.this.mWriteCurCount) + 1) * 100) / (FrkBleOTAUtils.this.mEraseTotalCount + FrkBleOTAUtils.this.mWriteTotalCount);
                        if (FrkBleOTAUtils.this.mWritePrecent == i2) {
                            return;
                        }
                        FrkBleOTAUtils.this.mWritePrecent = i2;
                        FrkBleOTAUtils.this.mFrkBleCallBack.onBleOtaStateChange(1, FrkBleOTAUtils.this.mWritePrecent);
                        return;
                    }
                    LogUtils.i("设备忙，等2ms");
                    message2 = new Message();
                    message2.what = 4;
                    message2.arg1 = message.arg1;
                } else if (FrkBleOTAUtils.this.f24930a.getDeviceBusy()) {
                    LogUtils.i("设备忙，等2ms");
                    message2 = new Message();
                    message2.what = 3;
                    message2.arg1 = message.arg1;
                } else if (message.arg1 == 0) {
                    if (FrkBleOTAUtils.this.mEraseCurCount >= FrkBleOTAUtils.this.mEraseTotalCount - 1) {
                        FrkBleOTAUtils.this.utenif();
                        return;
                    }
                    FrkBleOTAUtils.utennew(FrkBleOTAUtils.this);
                    FrkBleOTAUtils.this.eraseStorageAddress();
                    i2 = ((FrkBleOTAUtils.this.mEraseCurCount + 1) * 100) / (FrkBleOTAUtils.this.mEraseTotalCount + FrkBleOTAUtils.this.mWriteTotalCount);
                    if (FrkBleOTAUtils.this.mWritePrecent == i2) {
                        return;
                    }
                    FrkBleOTAUtils.this.mWritePrecent = i2;
                    FrkBleOTAUtils.this.mFrkBleCallBack.onBleOtaStateChange(1, FrkBleOTAUtils.this.mWritePrecent);
                    return;
                }
            } else {
                if (!FrkBleOTAUtils.this.f24930a.getDeviceBusy()) {
                    if (message.arg1 == 0) {
                        FrkBleOTAUtils.this.mEraseCurCount = 0;
                        FrkBleOTAUtils.this.mBaseAddr = message.arg2;
                    }
                    FrkBleOTAUtils.this.queryBaseStorageAddress();
                    return;
                }
                LogUtils.i("设备忙，等2ms");
                message2 = new Message();
                message2.what = 2;
                message2.arg1 = message.arg1;
                message2.arg2 = message.arg2;
            }
            FrkBleOTAUtils.this.eraseStorageAddress();
            return;
            FrkBleOTAUtils.this.mHandler.sendMessageDelayed(message2, FrkBleOTAUtils.this.delayWriteTime);
        }
    }

    public FrkBleOTAUtils() {
        isFRKOTAING = false;
        this.mCommandTimeOut = CommandTimeOutUtils.getInstance();
    }

    public static FrkBleOTAUtils getInstance() {
        if (instance == null) {
            instance = new FrkBleOTAUtils();
        }
        return instance;
    }

    public static /* synthetic */ int utenlong(FrkBleOTAUtils frkBleOTAUtils) {
        int i2 = frkBleOTAUtils.mWriteCurCount;
        frkBleOTAUtils.mWriteCurCount = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int utennew(FrkBleOTAUtils frkBleOTAUtils) {
        int i2 = frkBleOTAUtils.mEraseCurCount;
        frkBleOTAUtils.mEraseCurCount = i2 + 1;
        return i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dealWithFrkData(java.lang.String r7, byte[] r8) {
        /*
            r6 = this;
            java.lang.String r0 = r6.f24931b
            boolean r0 = r0.equals(r7)
            if (r0 == 0) goto L9
            return
        L9:
            r6.f24931b = r7
            int r7 = r8.length
            r0 = 2
            if (r7 < r0) goto L76
            r7 = 0
            r7 = r8[r7]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r1 = 1
            r2 = r8[r1]
            r2 = r2 & 255(0xff, float:3.57E-43)
            android.os.Message r3 = new android.os.Message
            r3.<init>()
            r3.arg1 = r7
            if (r2 == 0) goto L6e
            if (r2 == r1) goto L60
            r7 = 3
            if (r2 == r7) goto L5a
            r4 = 5
            if (r2 == r4) goto L53
            r5 = 9
            if (r2 == r5) goto L50
            r5 = 14
            if (r2 == r5) goto L5a
            r7 = 16
            if (r2 == r7) goto L53
            r7 = 20
            if (r2 == r7) goto L50
            r7 = 28
            if (r2 == r7) goto L47
            r7 = 11
            if (r2 == r7) goto L6e
            r7 = 12
            if (r2 == r7) goto L60
            goto L76
        L47:
            r7 = 6
        L48:
            r3.what = r7
        L4a:
            android.os.Handler r7 = r6.mHandler
            r7.sendMessage(r3)
            goto L76
        L50:
            r3.what = r4
            goto L4a
        L53:
            com.yc.utesdk.command.CommandTimeOutUtils r7 = r6.mCommandTimeOut
            r7.cancelCommandTimeOut()
            r7 = 4
            goto L48
        L5a:
            com.yc.utesdk.command.CommandTimeOutUtils r8 = r6.mCommandTimeOut
            r8.cancelCommandTimeOut()
            goto L48
        L60:
            com.yc.utesdk.command.CommandTimeOutUtils r7 = r6.mCommandTimeOut
            r7.cancelCommandTimeOut()
            int r7 = r6.utendo(r8)
            r3.what = r0
            r3.arg2 = r7
            goto L4a
        L6e:
            com.yc.utesdk.command.CommandTimeOutUtils r7 = r6.mCommandTimeOut
            r7.cancelCommandTimeOut()
            r3.what = r1
            goto L4a
        L76:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.ota.FrkBleOTAUtils.dealWithFrkData(java.lang.String, byte[]):void");
    }

    public void eraseStorageAddress() {
        this.mCommandTimeOut.setCommandTimeOut(KeyType.QUERY_FRK_DEVICE_ERASE_COMMAND);
        int i2 = this.mBaseAddr + (this.mEraseCurCount * 4096);
        writeCharaBleFrk(new byte[]{(byte) (this.mOffSet + 3), 7, 0, (byte) (i2 & 255), (byte) ((65280 & i2) >> 8), (byte) ((16711680 & i2) >> 16), (byte) ((i2 & ViewCompat.MEASURED_STATE_MASK) >> 24), 0, 0});
    }

    public boolean isFRKOTAProgress() {
        return this.isFRKOTAProgress;
    }

    public void queryBaseStorageAddress() {
        this.mCommandTimeOut.setCommandTimeOut(KeyType.QUERY_FRK_DEVICE_BASE_ADDRESS_COMMAND);
        byte b2 = (byte) (this.mOffSet + 1);
        long j2 = this.mFileSize;
        int i2 = this.mFileCRC;
        writeCharaBleFrk(new byte[]{b2, 3, 0, (byte) (255 & j2), (byte) ((65280 & j2) >> 8), (byte) ((16711680 & j2) >> 16), (byte) ((j2 & (-16777216)) >> 24), (byte) (i2 & 255), (byte) ((65280 & i2) >> 8), (byte) ((16711680 & i2) >> 16), (byte) ((i2 & ViewCompat.MEASURED_STATE_MASK) >> 24)});
    }

    public void queryFrkChipType() {
        this.mCommandTimeOut.setCommandTimeOut(KeyType.QUERY_FRK_DEVICE_CHIP_TYPE_COMMAND);
        byte[] bArr = new byte[9];
        bArr[0] = (byte) this.mOffSet;
        bArr[1] = 3;
        writeCharaBleFrk(bArr);
    }

    public void sendOtaDataSegments() {
        this.mCommandTimeOut.setCommandTimeOut(KeyType.QUERY_FRK_DEVICE_WRITE_COMMAND);
        int i2 = this.mWriteCurCount;
        byte[] bArr = this.mFileData;
        int i3 = this.packageSize;
        int length = bArr.length;
        int i4 = length / i3;
        int i5 = length % i3;
        if (i2 < i4) {
            byte[] bArr2 = new byte[i3 + 9];
            int i6 = i2 * i3;
            int i7 = this.mBaseAddr + i6;
            bArr2[0] = (byte) (this.mOffSet + 5);
            bArr2[1] = 9;
            bArr2[2] = 0;
            bArr2[3] = (byte) (i7 & 255);
            bArr2[4] = (byte) ((i7 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            bArr2[5] = (byte) ((i7 & 16711680) >> 16);
            bArr2[6] = (byte) ((i7 & ViewCompat.MEASURED_STATE_MASK) >> 24);
            bArr2[7] = (byte) (i3 & 255);
            bArr2[8] = (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            System.arraycopy(bArr, i6, bArr2, 9, i3);
            writeCharaBleFrk(bArr2);
            return;
        }
        if (i5 != 0) {
            byte[] bArr3 = new byte[i5 + 9];
            int i8 = i3 * i2;
            int i9 = this.mBaseAddr + i8;
            bArr3[0] = (byte) (this.mOffSet + 5);
            bArr3[1] = 9;
            bArr3[2] = 0;
            bArr3[3] = (byte) (i9 & 255);
            bArr3[4] = (byte) ((i9 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            bArr3[5] = (byte) ((i9 & 16711680) >> 16);
            bArr3[6] = (byte) ((i9 & ViewCompat.MEASURED_STATE_MASK) >> 24);
            bArr3[7] = (byte) (i5 & 255);
            bArr3[8] = (byte) ((i5 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            System.arraycopy(bArr, i8, bArr3, 9, i5);
            writeCharaBleFrk(bArr3);
        }
    }

    public void setFRBleCallBack(FrkBleCallBack frkBleCallBack) {
        this.mFrkBleCallBack = frkBleCallBack;
    }

    public void setFRKOTAProgress(boolean z2) {
        this.isFRKOTAProgress = z2;
    }

    public boolean startFrkOTA(int i2, String str) {
        if (isFRKOTAING && this.mOtaType == i2) {
            LogUtils.i("正在OTA中，不可重复OTA");
            return false;
        }
        this.mOtaType = i2;
        isFRKOTAING = true;
        utendo(i2, str);
        this.f24930a = DeviceBusyLockUtils.getInstance();
        this.mFrkBleCallBack.onBleOtaStateChange(0, 0);
        queryFrkChipType();
        return true;
    }

    public void writeCharaBleFrk(byte[] bArr) {
        WriteCommandToBLE.getInstance().writeCharaBleFrk(bArr);
    }

    public final int utendo(byte[] bArr) {
        int i2;
        if (bArr == null || bArr.length < 8) {
            i2 = 0;
        } else {
            i2 = ((bArr[7] & 255) << 24) | (bArr[4] & 255) | ((bArr[5] & 255) << 8) | ((bArr[6] & 255) << 16);
        }
        LogUtils.i("bytetoint addr =" + i2);
        return i2;
    }

    public final byte[] utenif(String str) throws Throwable {
        Throwable th;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(new File(str));
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = fileInputStream.read(bArr);
                    if (i2 == -1) {
                        byteArrayOutputStream.flush();
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        fileInputStream.close();
                        return byteArray;
                    }
                    byteArrayOutputStream.write(bArr, 0, i2);
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileInputStream == null) {
                    throw th;
                }
                fileInputStream.close();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
        }
    }

    public final void utendo() {
        byte b2 = (byte) (this.mOffSet + 9);
        long j2 = this.mFileSize;
        int i2 = this.mFileCRC;
        writeCharaBleFrk(new byte[]{b2, 10, 0, (byte) (255 & j2), (byte) ((65280 & j2) >> 8), (byte) ((16711680 & j2) >> 16), (byte) ((j2 & (-16777216)) >> 24), (byte) (i2 & 255), (byte) ((65280 & i2) >> 8), (byte) ((16711680 & i2) >> 16), (byte) ((i2 & ViewCompat.MEASURED_STATE_MASK) >> 24)});
    }

    public final void utenif() {
        if (this.mFileData == null) {
            return;
        }
        this.mWriteCurCount = 0;
        sendOtaDataSegments();
    }

    public final int utendo(String str) throws IOException {
        File file = new File(str);
        this.mFileSize = file.length();
        int i2 = 0;
        try {
            byte[] bArr = new byte[4096];
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            int i3 = 0;
            while (true) {
                try {
                    int i4 = bufferedInputStream.read(bArr, 0, 4096);
                    if (i4 == -1) {
                        return ~i3;
                    }
                    int i5 = 0;
                    while (i4 > 0) {
                        int i6 = i5 + 1;
                        i3 = ((i3 >> 8) & ViewCompat.MEASURED_SIZE_MASK) ^ this.crc_table[(bArr[i5] ^ i3) & 255];
                        i4--;
                        i5 = i6;
                    }
                } catch (FileNotFoundException e2) {
                    e = e2;
                    i2 = i3;
                    e.printStackTrace();
                    return i2;
                }
            }
        } catch (FileNotFoundException e3) {
            e = e3;
        }
    }

    public final void utendo(int i2, String str) {
        this.mOffSet = i2 != 0 ? 11 : 0;
        try {
            this.mFileCRC = utendo(str);
        } catch (IOException unused) {
        }
        long j2 = this.mFileSize;
        int i3 = (int) (j2 / PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM);
        this.mEraseTotalCount = i3;
        if (j2 % PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM != 0) {
            this.mEraseTotalCount = i3 + 1;
        }
        try {
            this.mFileData = utenif(str);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength() - 9;
        this.packageSize = maxCommunicationLength;
        long j3 = this.mFileSize;
        long j4 = maxCommunicationLength;
        int i4 = (int) (j3 / j4);
        this.mWriteTotalCount = i4;
        if (j3 % j4 != 0) {
            this.mWriteTotalCount = i4 + 1;
        }
    }
}
