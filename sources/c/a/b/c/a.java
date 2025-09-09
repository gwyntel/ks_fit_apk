package c.a.b.c;

import c.a.b.d;
import c.a.b.e;
import c.a.b.h.k;
import c.a.d.h;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.aliyun.alink.business.devicecenter.config.ble.BreezeConstants;
import com.google.common.base.Ascii;
import java.lang.reflect.Array;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;

/* loaded from: classes2.dex */
public class a implements d {

    /* renamed from: a, reason: collision with root package name */
    public static final byte[] f8025a = {99, 124, 119, 123, -14, 107, 111, -59, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, 1, 103, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, 63, -9, -52, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, -91, -27, BreezeConstants.BREEZE_PROVISION_VERSION, 113, -40, Constants.CMD_TYPE.CMD_STATUS_REPORT, Ascii.NAK, 4, -57, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, -61, Ascii.CAN, -106, 5, -102, 7, 18, Byte.MIN_VALUE, -30, -21, 39, -78, 117, 9, -125, 44, Ascii.SUB, Ascii.ESC, 110, 90, -96, 82, 59, -42, -77, 41, -29, Constants.CMD_TYPE.CMD_OTA, -124, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, -123, 69, -7, 2, Byte.MAX_VALUE, 80, Constants.CMD_TYPE.CMD_BUSINESS_DOWNSTREAM, -97, -88, 81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, 16, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, Ascii.ETB, -60, -89, 126, 61, 100, 93, Ascii.EM, 115, 96, -127, 79, -36, Constants.CMD_TYPE.CMD_REQUEST_OTA, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, -112, -120, 70, -18, -72, Ascii.DC4, -34, 94, 11, -37, -32, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, 58, 10, 73, 6, Constants.CMD_TYPE.CMD_OTA_RES, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, 46, Ascii.FS, -90, -76, -58, -24, -35, 116, Ascii.US, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, Constants.CMD_TYPE.CMD_SIGNATURE_RES, 87, -71, -122, -63, Ascii.GS, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, Ascii.RS, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, Ascii.SYN};

    /* renamed from: b, reason: collision with root package name */
    public static final byte[] f8026b = {82, 9, 106, -43, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, -91, 56, -65, 64, -93, -98, -127, -13, -41, -5, 124, -29, 57, -126, -101, Constants.CMD_TYPE.CMD_OTA, -1, -121, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, -90, -62, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, 61, -18, 76, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, Constants.CMD_TYPE.CMD_OTA_RES, -78, 118, 91, -94, 73, 109, -117, -47, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, 114, -8, -10, 100, -122, 104, -104, Ascii.SYN, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, Ascii.NAK, 70, 87, -89, -115, -99, -124, -112, -40, -85, 0, -116, -68, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, 44, Ascii.RS, -113, -54, 63, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, Constants.CMD_TYPE.CMD_REQUEST_OTA, -25, -83, Constants.CMD_TYPE.CMD_SIGNATURE_RES, -123, -30, -7, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, -24, Ascii.FS, 117, -33, 110, 71, BreezeConstants.BREEZE_PROVISION_VERSION, Ascii.SUB, 113, Ascii.GS, 41, -59, -119, 111, -73, 98, 14, -86, Ascii.CAN, -66, Ascii.ESC, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12, Ascii.US, -35, -88, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, -120, 7, -57, Constants.CMD_TYPE.CMD_STATUS_REPORT, -79, 18, 16, 89, 39, Byte.MIN_VALUE, -20, 95, 96, 81, Byte.MAX_VALUE, -87, Ascii.EM, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, 59, 77, -82, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, -11, -80, -56, -21, -69, Constants.CMD_TYPE.CMD_BUSINESS_DOWNSTREAM, -125, 83, -103, 97, Ascii.ETB, 43, 4, 126, -70, 119, -42, Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, -31, 105, Ascii.DC4, 99, 85, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, 12, 125};

    /* renamed from: c, reason: collision with root package name */
    public static final int[] f8027c = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, 239, 197, 145};

    /* renamed from: d, reason: collision with root package name */
    public static final int[] f8028d = {-1520213050, -2072216328, -1720223762, -1921287178, 234025727, -1117033514, -1318096930, 1422247313, 1345335392, 50397442, -1452841010, 2099981142, 436141799, 1658312629, -424957107, -1703512340, 1170918031, -1652391393, 1086966153, -2021818886, 368769775, -346465870, -918075506, 200339707, -324162239, 1742001331, -39673249, -357585083, -1080255453, -140204973, -1770884380, 1539358875, -1028147339, 486407649, -1366060227, 1780885068, 1513502316, 1094664062, 49805301, 1338821763, 1546925160, -190470831, 887481809, 150073849, -1821281822, 1943591083, 1395732834, 1058346282, 201589768, 1388824469, 1696801606, 1589887901, 672667696, -1583966665, 251987210, -1248159185, 151455502, 907153956, -1686077413, 1038279391, 652995533, 1764173646, -843926913, -1619692054, 453576978, -1635548387, 1949051992, 773462580, 756751158, -1301385508, -296068428, -73359269, -162377052, 1295727478, 1641469623, -827083907, 2066295122, 1055122397, 1898917726, -1752923117, -179088474, 1758581177, 0, 753790401, 1612718144, 536673507, -927878791, -312779850, -1100322092, 1187761037, -641810841, 1262041458, -565556588, -733197160, -396863312, 1255133061, 1808847035, 720367557, -441800113, 385612781, -985447546, -682799718, 1429418854, -1803188975, -817543798, 284817897, 100794884, -2122350594, -263171936, 1144798328, -1163944155, -475486133, -212774494, -22830243, -1069531008, -1970303227, -1382903233, -1130521311, 1211644016, 83228145, -541279133, -1044990345, 1977277103, 1663115586, 806359072, 452984805, 250868733, 1842533055, 1288555905, 336333848, 890442534, 804056259, -513843266, -1567123659, -867941240, 957814574, 1472513171, -223893675, -2105639172, 1195195770, -1402706744, -413311558, 723065138, -1787595802, -1604296512, -1736343271, -783331426, 2145180835, 1713513028, 2116692564, -1416589253, -2088204277, -901364084, 703524551, -742868885, 1007948840, 2044649127, -497131844, 487262998, 1994120109, 1004593371, 1446130276, 1312438900, 503974420, -615954030, 168166924, 1814307912, -463709000, 1573044895, 1859376061, -273896381, -1503501628, -1466855111, -1533700815, 937747667, -1954973198, 854058965, 1137232011, 1496790894, -1217565222, -1936880383, 1691735473, -766620004, -525751991, -1267962664, -95005012, 133494003, 636152527, -1352309302, -1904575756, -374428089, 403179536, -709182865, -2005370640, 1864705354, 1915629148, 605822008, -240736681, -944458637, 1371981463, 602466507, 2094914977, -1670089496, 555687742, -582268010, -591544991, -2037675251, -2054518257, -1871679264, 1111375484, -994724495, -1436129588, -666351472, 84083462, 32962295, 302911004, -1553899070, 1597322602, -111716434, -793134743, -1853454825, 1489093017, 656219450, -1180787161, 954327513, 335083755, -1281845205, 856756514, -1150719534, 1893325225, -1987146233, -1483434957, -1231316179, 572399164, -1836611819, 552200649, 1238290055, -11184726, 2015897680, 2061492133, -1886614525, -123625127, -2138470135, 386731290, -624967835, 837215959, -968736124, -1201116976, -1019133566, -1332111063, 1999449434, 286199582, -877612933, -61582168, -692339859, 974525996};

    /* renamed from: e, reason: collision with root package name */
    public static final int[] f8029e = {1353184337, 1399144830, -1012656358, -1772214470, -882136261, -247096033, -1420232020, -1828461749, 1442459680, -160598355, -1854485368, 625738485, -52959921, -674551099, -2143013594, -1885117771, 1230680542, 1729870373, -1743852987, -507445667, 41234371, 317738113, -1550367091, -956705941, -413167869, -1784901099, -344298049, -631680363, 763608788, -752782248, 694804553, 1154009486, 1787413109, 2021232372, 1799248025, -579749593, -1236278850, 397248752, 1722556617, -1271214467, 407560035, -2110711067, 1613975959, 1165972322, -529046351, -2068943941, 480281086, -1809118983, 1483229296, 436028815, -2022908268, -1208452270, 601060267, -503166094, 1468997603, 715871590, 120122290, 63092015, -1703164538, -1526188077, -226023376, -1297760477, -1167457534, 1552029421, 723308426, -1833666137, -252573709, -1578997426, -839591323, -708967162, 526529745, -1963022652, -1655493068, -1604979806, 853641733, 1978398372, 971801355, -1427152832, 111112542, 1360031421, -108388034, 1023860118, -1375387939, 1186850381, -1249028975, 90031217, 1876166148, -15380384, 620468249, -1746289194, -868007799, 2006899047, -1119688528, -2004121337, 945494503, -605108103, 1191869601, -384875908, -920746760, 0, -2088337399, 1223502642, -1401941730, 1316117100, -67170563, 1446544655, 517320253, 658058550, 1691946762, 564550760, -783000677, 976107044, -1318647284, 266819475, -761860428, -1634624741, 1338359936, -1574904735, 1766553434, 370807324, 179999714, -450191168, 1138762300, 488053522, 185403662, -1379431438, -1180125651, -928440812, -2061897385, 1275557295, -1143105042, -44007517, -1624899081, -1124765092, -985962940, 880737115, 1982415755, -590994485, 1761406390, 1676797112, -891538985, 277177154, 1076008723, 538035844, 2099530373, -130171950, 288553390, 1839278535, 1261411869, -214912292, -330136051, -790380169, 1813426987, -1715900247, -95906799, 577038663, -997393240, 440397984, -668172970, -275762398, -951170681, -1043253031, -22885748, 906744984, -813566554, 685669029, 646887386, -1530942145, -459458004, 227702864, -1681105046, 1648787028, -1038905866, -390539120, 1593260334, -173030526, -1098883681, 2090061929, -1456614033, -1290656305, 999926984, -1484974064, 1852021992, 2075868123, 158869197, -199730834, 28809964, -1466282109, 1701746150, 2129067946, 147831841, -420997649, -644094022, -835293366, -737566742, -696471511, -1347247055, 824393514, 815048134, -1067015627, 935087732, -1496677636, -1328508704, 366520115, 1251476721, -136647615, 240176511, 804688151, -1915335306, 1303441219, 1414376140, -553347356, -474623586, 461924940, -1205916479, 2136040774, 82468509, 1563790337, 1937016826, 776014843, 1511876531, 1389550482, 861278441, 323475053, -1939744870, 2047648055, -1911228327, -1992551445, -299390514, 902390199, -303751967, 1018251130, 1507840668, 1064563285, 2043548696, -1086863501, -355600557, 1537932639, 342834655, -2032450440, -2114736182, 1053059257, 741614648, 1598071746, 1925389590, 203809468, -1958134744, 1100287487, 1895934009, -558691320, -1662733096, -1866377628, 1636092795, 1890988757, 1952214088, 1113045200};

    /* renamed from: f, reason: collision with root package name */
    public int f8030f;

    /* renamed from: g, reason: collision with root package name */
    public int[][] f8031g = null;

    /* renamed from: h, reason: collision with root package name */
    public int f8032h;

    /* renamed from: i, reason: collision with root package name */
    public int f8033i;

    /* renamed from: j, reason: collision with root package name */
    public int f8034j;

    /* renamed from: k, reason: collision with root package name */
    public int f8035k;

    /* renamed from: l, reason: collision with root package name */
    public boolean f8036l;

    /* renamed from: m, reason: collision with root package name */
    public byte[] f8037m;

    public static int b(int i2) {
        int i3 = (1061109567 & i2) << 2;
        int i4 = i2 & (-1061109568);
        int i5 = i4 ^ (i4 >>> 1);
        return (i5 >>> 5) ^ (i3 ^ (i5 >>> 2));
    }

    public static int c(int i2) {
        int iA = a(i2, 8) ^ i2;
        int iA2 = i2 ^ a(iA);
        int iB = iA ^ b(iA2);
        return iA2 ^ (iB ^ a(iB, 16));
    }

    public static int d(int i2) {
        byte[] bArr = f8025a;
        return (bArr[(i2 >> 24) & 255] << Ascii.CAN) | (bArr[i2 & 255] & 255) | ((bArr[(i2 >> 8) & 255] & 255) << 8) | ((bArr[(i2 >> 16) & 255] & 255) << 16);
    }

    @Override // c.a.b.d
    public int a() {
        return 16;
    }

    @Override // c.a.b.d
    public void reset() {
    }

    public static int a(int i2) {
        return (((i2 & (-2139062144)) >>> 7) * 27) ^ ((2139062143 & i2) << 1);
    }

    public final void b(byte[] bArr, int i2) {
        int i3 = bArr[i2] & 255;
        this.f8032h = i3;
        int i4 = ((bArr[i2 + 1] & 255) << 8) | i3;
        this.f8032h = i4;
        int i5 = i4 | ((bArr[i2 + 2] & 255) << 16);
        this.f8032h = i5;
        this.f8032h = i5 | (bArr[i2 + 3] << Ascii.CAN);
        int i6 = bArr[i2 + 4] & 255;
        this.f8033i = i6;
        int i7 = ((bArr[i2 + 5] & 255) << 8) | i6;
        this.f8033i = i7;
        int i8 = i7 | ((bArr[i2 + 6] & 255) << 16);
        this.f8033i = i8;
        this.f8033i = i8 | (bArr[i2 + 7] << Ascii.CAN);
        int i9 = bArr[i2 + 8] & 255;
        this.f8034j = i9;
        int i10 = ((bArr[i2 + 9] & 255) << 8) | i9;
        this.f8034j = i10;
        int i11 = i10 | ((bArr[i2 + 10] & 255) << 16);
        this.f8034j = i11;
        this.f8034j = i11 | (bArr[i2 + 11] << Ascii.CAN);
        int i12 = bArr[i2 + 12] & 255;
        this.f8035k = i12;
        int i13 = ((bArr[i2 + 13] & 255) << 8) | i12;
        this.f8035k = i13;
        int i14 = i13 | ((bArr[i2 + 14] & 255) << 16);
        this.f8035k = i14;
        this.f8035k = (bArr[i2 + 15] << Ascii.CAN) | i14;
    }

    public static int a(int i2, int i3) {
        return (i2 << (-i3)) | (i2 >>> i3);
    }

    public final int[][] a(byte[] bArr, boolean z2) {
        int length = bArr.length;
        if (length >= 16 && length <= 32 && (length & 7) == 0) {
            int i2 = length >>> 2;
            this.f8030f = i2 + 6;
            int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i2 + 7, 4);
            if (i2 == 4) {
                int iC = h.c(bArr, 0);
                iArr[0][0] = iC;
                int iC2 = h.c(bArr, 4);
                iArr[0][1] = iC2;
                int iC3 = h.c(bArr, 8);
                iArr[0][2] = iC3;
                int iC4 = h.c(bArr, 12);
                iArr[0][3] = iC4;
                for (int i3 = 1; i3 <= 10; i3++) {
                    iC ^= d(a(iC4, 8)) ^ f8027c[i3 - 1];
                    int[] iArr2 = iArr[i3];
                    iArr2[0] = iC;
                    iC2 ^= iC;
                    iArr2[1] = iC2;
                    iC3 ^= iC2;
                    iArr2[2] = iC3;
                    iC4 ^= iC3;
                    iArr2[3] = iC4;
                }
            } else if (i2 == 6) {
                int iC5 = h.c(bArr, 0);
                iArr[0][0] = iC5;
                int iC6 = h.c(bArr, 4);
                iArr[0][1] = iC6;
                int iC7 = h.c(bArr, 8);
                iArr[0][2] = iC7;
                int iC8 = h.c(bArr, 12);
                iArr[0][3] = iC8;
                int iC9 = h.c(bArr, 16);
                iArr[1][0] = iC9;
                int iC10 = h.c(bArr, 20);
                iArr[1][1] = iC10;
                int iD = iC5 ^ (d(a(iC10, 8)) ^ 1);
                int[] iArr3 = iArr[1];
                iArr3[2] = iD;
                int i4 = iC6 ^ iD;
                iArr3[3] = i4;
                int i5 = iC7 ^ i4;
                int[] iArr4 = iArr[2];
                iArr4[0] = i5;
                int i6 = iC8 ^ i5;
                iArr4[1] = i6;
                int i7 = iC9 ^ i6;
                iArr4[2] = i7;
                int i8 = iC10 ^ i7;
                iArr4[3] = i8;
                int i9 = 2;
                for (int i10 = 3; i10 < 12; i10 += 3) {
                    int iD2 = iD ^ (d(a(i8, 8)) ^ i9);
                    int[] iArr5 = iArr[i10];
                    iArr5[0] = iD2;
                    int i11 = i4 ^ iD2;
                    iArr5[1] = i11;
                    int i12 = i5 ^ i11;
                    iArr5[2] = i12;
                    int i13 = i6 ^ i12;
                    iArr5[3] = i13;
                    int i14 = i7 ^ i13;
                    int i15 = i10 + 1;
                    int[] iArr6 = iArr[i15];
                    iArr6[0] = i14;
                    int i16 = i8 ^ i14;
                    iArr6[1] = i16;
                    int iD3 = d(a(i16, 8)) ^ (i9 << 1);
                    i9 <<= 2;
                    iD = iD2 ^ iD3;
                    int[] iArr7 = iArr[i15];
                    iArr7[2] = iD;
                    i4 = i11 ^ iD;
                    iArr7[3] = i4;
                    i5 = i12 ^ i4;
                    int[] iArr8 = iArr[i10 + 2];
                    iArr8[0] = i5;
                    i6 = i13 ^ i5;
                    iArr8[1] = i6;
                    i7 = i14 ^ i6;
                    iArr8[2] = i7;
                    i8 = i16 ^ i7;
                    iArr8[3] = i8;
                }
                int iD4 = (d(a(i8, 8)) ^ i9) ^ iD;
                int[] iArr9 = iArr[12];
                iArr9[0] = iD4;
                int i17 = iD4 ^ i4;
                iArr9[1] = i17;
                int i18 = i17 ^ i5;
                iArr9[2] = i18;
                iArr9[3] = i18 ^ i6;
            } else if (i2 == 8) {
                int iC11 = h.c(bArr, 0);
                iArr[0][0] = iC11;
                int iC12 = h.c(bArr, 4);
                iArr[0][1] = iC12;
                int iC13 = h.c(bArr, 8);
                iArr[0][2] = iC13;
                int iC14 = h.c(bArr, 12);
                iArr[0][3] = iC14;
                int iC15 = h.c(bArr, 16);
                iArr[1][0] = iC15;
                int iC16 = h.c(bArr, 20);
                iArr[1][1] = iC16;
                int iC17 = h.c(bArr, 24);
                iArr[1][2] = iC17;
                int iC18 = h.c(bArr, 28);
                iArr[1][3] = iC18;
                int i19 = 1;
                for (int i20 = 2; i20 < 14; i20 += 2) {
                    int iD5 = d(a(iC18, 8)) ^ i19;
                    i19 <<= 1;
                    iC11 ^= iD5;
                    int[] iArr10 = iArr[i20];
                    iArr10[0] = iC11;
                    iC12 ^= iC11;
                    iArr10[1] = iC12;
                    iC13 ^= iC12;
                    iArr10[2] = iC13;
                    iC14 ^= iC13;
                    iArr10[3] = iC14;
                    iC15 ^= d(iC14);
                    int[] iArr11 = iArr[i20 + 1];
                    iArr11[0] = iC15;
                    iC16 ^= iC15;
                    iArr11[1] = iC16;
                    iC17 ^= iC16;
                    iArr11[2] = iC17;
                    iC18 ^= iC17;
                    iArr11[3] = iC18;
                }
                int iD6 = (d(a(iC18, 8)) ^ i19) ^ iC11;
                int[] iArr12 = iArr[14];
                iArr12[0] = iD6;
                int i21 = iD6 ^ iC12;
                iArr12[1] = i21;
                int i22 = i21 ^ iC13;
                iArr12[2] = i22;
                iArr12[3] = i22 ^ iC14;
            } else {
                throw new IllegalStateException("Should never get here");
            }
            if (!z2) {
                for (int i23 = 1; i23 < this.f8030f; i23++) {
                    for (int i24 = 0; i24 < 4; i24++) {
                        int[] iArr13 = iArr[i23];
                        iArr13[i24] = c(iArr13[i24]);
                    }
                }
            }
            return iArr;
        }
        throw new IllegalArgumentException("Key length not 128/192/256 bits.");
    }

    public final void b(int[][] iArr) {
        int i2 = this.f8032h;
        char c2 = 0;
        int[] iArr2 = iArr[0];
        int i3 = i2 ^ iArr2[0];
        int i4 = 1;
        int i5 = this.f8033i ^ iArr2[1];
        int i6 = this.f8034j ^ iArr2[2];
        char c3 = 3;
        int iA = iArr2[3] ^ this.f8035k;
        int i7 = 1;
        while (i7 < this.f8030f - i4) {
            int[] iArr3 = f8028d;
            int iA2 = (((iArr3[i3 & 255] ^ a(iArr3[(i5 >> 8) & 255], 24)) ^ a(iArr3[(i6 >> 16) & 255], 16)) ^ a(iArr3[(iA >> 24) & 255], 8)) ^ iArr[i7][c2];
            int iA3 = (((a(iArr3[(i6 >> 8) & 255], 24) ^ iArr3[i5 & 255]) ^ a(iArr3[(iA >> 16) & 255], 16)) ^ a(iArr3[(i3 >> 24) & 255], 8)) ^ iArr[i7][i4];
            int iA4 = (((a(iArr3[(iA >> 8) & 255], 24) ^ iArr3[i6 & 255]) ^ a(iArr3[(i3 >> 16) & 255], 16)) ^ a(iArr3[(i5 >> 24) & 255], 8)) ^ iArr[i7][2];
            int iA5 = ((a(iArr3[(i3 >> 8) & 255], 24) ^ iArr3[iA & 255]) ^ a(iArr3[(i5 >> 16) & 255], 16)) ^ a(iArr3[(i6 >> 24) & 255], 8);
            int i8 = i7 + 1;
            int i9 = iA5 ^ iArr[i7][c3];
            int iA6 = (((iArr3[iA2 & 255] ^ a(iArr3[(iA3 >> 8) & 255], 24)) ^ a(iArr3[(iA4 >> 16) & 255], 16)) ^ a(iArr3[(i9 >> 24) & 255], 8)) ^ iArr[i8][0];
            int iA7 = (((iArr3[iA3 & 255] ^ a(iArr3[(iA4 >> 8) & 255], 24)) ^ a(iArr3[(i9 >> 16) & 255], 16)) ^ a(iArr3[(iA2 >> 24) & 255], 8)) ^ iArr[i8][1];
            int iA8 = (((a(iArr3[(i9 >> 8) & 255], 24) ^ iArr3[iA4 & 255]) ^ a(iArr3[(iA2 >> 16) & 255], 16)) ^ a(iArr3[(iA3 >> 24) & 255], 8)) ^ iArr[i8][2];
            i7 += 2;
            iA = (((iArr3[i9 & 255] ^ a(iArr3[(iA2 >> 8) & 255], 24)) ^ a(iArr3[(iA3 >> 16) & 255], 16)) ^ a(iArr3[(iA4 >> 24) & 255], 8)) ^ iArr[i8][3];
            i3 = iA6;
            i5 = iA7;
            i6 = iA8;
            c2 = 0;
            i4 = 1;
            c3 = 3;
        }
        int[] iArr4 = f8028d;
        int iA9 = (((iArr4[i3 & 255] ^ a(iArr4[(i5 >> 8) & 255], 24)) ^ a(iArr4[(i6 >> 16) & 255], 16)) ^ a(iArr4[(iA >> 24) & 255], 8)) ^ iArr[i7][0];
        int iA10 = (((iArr4[i5 & 255] ^ a(iArr4[(i6 >> 8) & 255], 24)) ^ a(iArr4[(iA >> 16) & 255], 16)) ^ a(iArr4[(i3 >> 24) & 255], 8)) ^ iArr[i7][1];
        int iA11 = (((iArr4[i6 & 255] ^ a(iArr4[(iA >> 8) & 255], 24)) ^ a(iArr4[(i3 >> 16) & 255], 16)) ^ a(iArr4[(i5 >> 24) & 255], 8)) ^ iArr[i7][2];
        int iA12 = (((a(iArr4[(i3 >> 8) & 255], 24) ^ iArr4[iA & 255]) ^ a(iArr4[(i5 >> 16) & 255], 16)) ^ a(iArr4[(i6 >> 24) & 255], 8)) ^ iArr[i7][3];
        byte[] bArr = f8025a;
        int i10 = (bArr[iA9 & 255] & 255) ^ ((bArr[(iA10 >> 8) & 255] & 255) << 8);
        byte[] bArr2 = this.f8037m;
        int i11 = (i10 ^ ((bArr2[(iA11 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iA12 >> 24) & 255] << Ascii.CAN);
        int[] iArr5 = iArr[i7 + 1];
        this.f8032h = i11 ^ iArr5[0];
        this.f8033i = ((((bArr2[iA10 & 255] & 255) ^ ((bArr[(iA11 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iA12 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iA9 >> 24) & 255] << Ascii.CAN)) ^ iArr5[1];
        this.f8034j = ((((bArr2[iA11 & 255] & 255) ^ ((bArr[(iA12 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iA9 >> 16) & 255] & 255) << 16)) ^ (bArr[(iA10 >> 24) & 255] << Ascii.CAN)) ^ iArr5[2];
        this.f8035k = ((((bArr2[iA12 & 255] & 255) ^ ((bArr2[(iA9 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iA10 >> 16) & 255] & 255) << 16)) ^ (bArr[(iA11 >> 24) & 255] << Ascii.CAN)) ^ iArr5[3];
    }

    @Override // c.a.b.d
    public void a(boolean z2, e eVar) {
        if (eVar instanceof k) {
            this.f8031g = a(((k) eVar).a(), z2);
            this.f8036l = z2;
            if (z2) {
                this.f8037m = c.a.d.a.a(f8025a);
                return;
            } else {
                this.f8037m = c.a.d.a.a(f8026b);
                return;
            }
        }
        throw new IllegalArgumentException("invalid parameter passed to AES init - " + eVar.getClass().getName());
    }

    @Override // c.a.b.d
    public int a(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.f8031g != null) {
            if (i2 + 16 <= bArr.length) {
                if (i3 + 16 <= bArr2.length) {
                    if (this.f8036l) {
                        b(bArr, i2);
                        b(this.f8031g);
                        a(bArr2, i3);
                        return 16;
                    }
                    b(bArr, i2);
                    a(this.f8031g);
                    a(bArr2, i3);
                    return 16;
                }
                throw new OutputLengthException("output buffer too short");
            }
            throw new DataLengthException("input buffer too short");
        }
        throw new IllegalStateException("AES engine not initialised");
    }

    public final void a(byte[] bArr, int i2) {
        int i3 = this.f8032h;
        bArr[i2] = (byte) i3;
        bArr[i2 + 1] = (byte) (i3 >> 8);
        bArr[i2 + 2] = (byte) (i3 >> 16);
        bArr[i2 + 3] = (byte) (i3 >> 24);
        int i4 = this.f8033i;
        bArr[i2 + 4] = (byte) i4;
        bArr[i2 + 5] = (byte) (i4 >> 8);
        bArr[i2 + 6] = (byte) (i4 >> 16);
        bArr[i2 + 7] = (byte) (i4 >> 24);
        int i5 = this.f8034j;
        bArr[i2 + 8] = (byte) i5;
        bArr[i2 + 9] = (byte) (i5 >> 8);
        bArr[i2 + 10] = (byte) (i5 >> 16);
        bArr[i2 + 11] = (byte) (i5 >> 24);
        int i6 = this.f8035k;
        bArr[i2 + 12] = (byte) i6;
        bArr[i2 + 13] = (byte) (i6 >> 8);
        bArr[i2 + 14] = (byte) (i6 >> 16);
        bArr[i2 + 15] = (byte) (i6 >> 24);
    }

    public final void a(int[][] iArr) {
        int i2 = this.f8032h;
        int i3 = this.f8030f;
        int[] iArr2 = iArr[i3];
        char c2 = 0;
        int i4 = i2 ^ iArr2[0];
        int i5 = 1;
        int i6 = this.f8033i ^ iArr2[1];
        int i7 = this.f8034j ^ iArr2[2];
        int i8 = i3 - 1;
        char c3 = 3;
        int iA = iArr2[3] ^ this.f8035k;
        while (i8 > i5) {
            int[] iArr3 = f8029e;
            int iA2 = (((iArr3[i4 & 255] ^ a(iArr3[(iA >> 8) & 255], 24)) ^ a(iArr3[(i7 >> 16) & 255], 16)) ^ a(iArr3[(i6 >> 24) & 255], 8)) ^ iArr[i8][c2];
            int iA3 = (((a(iArr3[(i4 >> 8) & 255], 24) ^ iArr3[i6 & 255]) ^ a(iArr3[(iA >> 16) & 255], 16)) ^ a(iArr3[(i7 >> 24) & 255], 8)) ^ iArr[i8][i5];
            int iA4 = (((a(iArr3[(i6 >> 8) & 255], 24) ^ iArr3[i7 & 255]) ^ a(iArr3[(i4 >> 16) & 255], 16)) ^ a(iArr3[(iA >> 24) & 255], 8)) ^ iArr[i8][2];
            int iA5 = a(iArr3[(i4 >> 24) & 255], 8) ^ ((iArr3[iA & 255] ^ a(iArr3[(i7 >> 8) & 255], 24)) ^ a(iArr3[(i6 >> 16) & 255], 16));
            int i9 = i8 - 1;
            int i10 = iA5 ^ iArr[i8][c3];
            int iA6 = (((iArr3[iA2 & 255] ^ a(iArr3[(i10 >> 8) & 255], 24)) ^ a(iArr3[(iA4 >> 16) & 255], 16)) ^ a(iArr3[(iA3 >> 24) & 255], 8)) ^ iArr[i9][0];
            int iA7 = (((iArr3[iA3 & 255] ^ a(iArr3[(iA2 >> 8) & 255], 24)) ^ a(iArr3[(i10 >> 16) & 255], 16)) ^ a(iArr3[(iA4 >> 24) & 255], 8)) ^ iArr[i9][1];
            int iA8 = (((a(iArr3[(iA3 >> 8) & 255], 24) ^ iArr3[iA4 & 255]) ^ a(iArr3[(iA2 >> 16) & 255], 16)) ^ a(iArr3[(i10 >> 24) & 255], 8)) ^ iArr[i9][2];
            i8 -= 2;
            iA = iArr[i9][3] ^ (((iArr3[i10 & 255] ^ a(iArr3[(iA4 >> 8) & 255], 24)) ^ a(iArr3[(iA3 >> 16) & 255], 16)) ^ a(iArr3[(iA2 >> 24) & 255], 8));
            i4 = iA6;
            i6 = iA7;
            i7 = iA8;
            c2 = 0;
            i5 = 1;
            c3 = 3;
        }
        int[] iArr4 = f8029e;
        int iA9 = (((iArr4[i4 & 255] ^ a(iArr4[(iA >> 8) & 255], 24)) ^ a(iArr4[(i7 >> 16) & 255], 16)) ^ a(iArr4[(i6 >> 24) & 255], 8)) ^ iArr[i8][0];
        int iA10 = (((iArr4[i6 & 255] ^ a(iArr4[(i4 >> 8) & 255], 24)) ^ a(iArr4[(iA >> 16) & 255], 16)) ^ a(iArr4[(i7 >> 24) & 255], 8)) ^ iArr[i8][1];
        int iA11 = (((iArr4[i7 & 255] ^ a(iArr4[(i6 >> 8) & 255], 24)) ^ a(iArr4[(i4 >> 16) & 255], 16)) ^ a(iArr4[(iA >> 24) & 255], 8)) ^ iArr[i8][2];
        int iA12 = (a(iArr4[(i4 >> 24) & 255], 8) ^ ((iArr4[iA & 255] ^ a(iArr4[(i7 >> 8) & 255], 24)) ^ a(iArr4[(i6 >> 16) & 255], 16))) ^ iArr[i8][3];
        byte[] bArr = f8026b;
        int i11 = bArr[iA9 & 255] & 255;
        byte[] bArr2 = this.f8037m;
        int i12 = ((i11 ^ ((bArr2[(iA12 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iA11 >> 16) & 255] & 255) << 16)) ^ (bArr[(iA10 >> 24) & 255] << Ascii.CAN);
        int[] iArr5 = iArr[0];
        this.f8032h = i12 ^ iArr5[0];
        this.f8033i = ((((bArr2[iA10 & 255] & 255) ^ ((bArr2[(iA9 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iA12 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iA11 >> 24) & 255] << Ascii.CAN)) ^ iArr5[1];
        this.f8034j = ((((bArr2[iA11 & 255] & 255) ^ ((bArr[(iA10 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iA9 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iA12 >> 24) & 255] << Ascii.CAN)) ^ iArr5[2];
        this.f8035k = ((((bArr[iA12 & 255] & 255) ^ ((bArr2[(iA11 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iA10 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iA9 >> 24) & 255] << Ascii.CAN)) ^ iArr5[3];
    }
}
