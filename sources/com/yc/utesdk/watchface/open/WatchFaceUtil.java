package com.yc.utesdk.watchface.open;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.webkit.ProxyConfig;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.google.common.base.Ascii;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.SPUtil;
import com.yc.utesdk.watchface.bean.AppLocalCustomWatchFaceInfo;
import com.yc.utesdk.watchface.bean.CustomViewPositionInfo;
import com.yc.utesdk.watchface.bean.WatchFaceCustomInfo;
import com.yc.utesdk.watchface.bean.WatchFaceOnlineClass;
import com.yc.utesdk.watchface.bean.WatchFaceOnlineInfo;
import com.yc.utesdk.watchface.bean.WatchFaceOnlineOneInfo;
import com.yc.utesdk.watchface.bean.acts.WatchFaceParams;
import com.yc.utesdk.watchface.close.PicUtils;
import com.yc.utesdk.watchface.close.PostUtil;
import com.yc.utesdk.watchface.close.Rgb;
import com.yc.utesdk.watchface.close.ZipUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.crypto.NoSuchPaddingException;
import org.android.agoo.common.AgooConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class WatchFaceUtil {
    public static final int ABORT_DIAL_BIN = 30;
    public static final int APPLY_WATCH_FACE_FAIL = 17;
    public static final int APPLY_WATCH_FACE_OK = 16;
    public static int DELAY_SYNC_WATCH_FACE_DATA_TIME = 20;
    public static final int DELETE_WATCH_FACE_FAIL = 7;
    public static final int DELETE_WATCH_FACE_SUCCESS = 8;
    public static final int FILE_UPLOAD_REQUIRED = 22;
    public static final int GET_IMAGE_WATCH_FACE_INFO_FAIL = 19;
    public static final int GET_IMAGE_WATCH_FACE_INFO_OK = 18;
    public static final int GET_WATCH_FACE_DEVICE_REPORT = 24;
    public static final int GET_WATCH_FACE_INFO_FAIL = 15;
    public static final int GET_WATCH_FACE_INFO_OK = 14;
    public static final int GET_WATCH_FACE_PARAMS_FAIL = 13;
    public static final int GET_WATCH_FACE_PARAMS_OK = 12;
    public static final String IMAGE_WATCH_FACE_BLE_ID = "-2";
    public static final int NO_WATCH_FACE_CAN_DELETE = 9;
    public static final int PATH_STATUS_ASSETS = 0;
    public static final int PATH_STATUS_DATA = 1;
    public static final int PREPARE_SYNC_WATCH_FACE_DATA_FAIL = 3;
    public static final int PREPARE_SYNC_WATCH_FACE_DATA_OK = 2;
    public static final int QUERY_ALL_SERIAL_NUMBER_WATCH_FACE_INFO_FAIL = 27;
    public static final int QUERY_ALL_SERIAL_NUMBER_WATCH_FACE_INFO_OK = 26;
    public static final int QUERY_IMAGE_WATCH_FACE_PARAMS_FAIL = 35;
    public static final int QUERY_IMAGE_WATCH_FACE_PARAMS_OK = 34;
    public static final int QUERY_SERIAL_NUMBER_WATCH_FACE_INFO = 25;
    public static final int READ_DEVICE_WATCH_FACE_CONFIGURATION_FAIL = 1;
    public static final int READ_DEVICE_WATCH_FACE_CONFIGURATION_OK = 0;
    public static int REDUCE_SPEED_SEND_ONLINE_DIAL_TIME = 100;
    public static final int SEND_ONLINE_DIAL_CANCEL = 10;
    public static final int SET_IMAGE_WATCH_FACE_CONFIG_RK_FAIL = 37;
    public static final int SET_IMAGE_WATCH_FACE_CONFIG_RK_FAIL_TOO_BIG = 38;
    public static final int SET_IMAGE_WATCH_FACE_CONFIG_RK_OK = 36;
    public static final int SET_IMAGE_WATCH_FACE_INFO_FAIL = 21;
    public static final int SET_IMAGE_WATCH_FACE_INFO_OK = 20;
    public static final int SET_SERIAL_NUMBER_WATCH_FACE_FAIL = 29;
    public static final int SET_SERIAL_NUMBER_WATCH_FACE_OK = 28;
    public static final int SWITCH_WATCH_FACE_RK_FAIL = 33;
    public static final int SWITCH_WATCH_FACE_RK_OK = 32;
    public static final int SYNC_WATCH_FACE_CRC_FAIL = 6;
    public static final int SYNC_WATCH_FACE_DATA_TOO_LARGE = 7;
    public static final int SYNC_WATCH_FACE_FAIL = 23;
    public static final int SYNC_WATCH_FACE_SECTION = 4;
    public static final int SYNC_WATCH_FACE_SUCCESS = 5;
    public static final String WATCH_FACE_DPI_240x240 = "240*240";
    public static final String WATCH_FACE_DPI_320x360 = "320*360";
    public static final int WATCH_FACE_MODE_CUSTOM = 1;
    public static final int WATCH_FACE_MODE_ONLINE = 0;
    public static final int WATCH_FACE_SHAPE_TYPE_CIRCLE = 2;
    public static final int WATCH_FACE_SHAPE_TYPE_RECTANGLE = 3;
    public static final int WATCH_FACE_SHAPE_TYPE_SQUARE = 1;
    public static final int WATCH_REACH_UPPER_LIMIT = 11;
    private static WatchFaceUtil instance;

    /* renamed from: a, reason: collision with root package name */
    WatchFaceCustomInfo f24962a;
    private int watchFaceMode = 1;
    private int RGB888_RED = 16711680;
    private int RGB888_GREEN = MotionEventCompat.ACTION_POINTER_INDEX_MASK;
    private int RGB888_BLUE = 255;
    private int[] crc32_tab = {0, 1996959894, -301047508, -1727442502, 124634137, 1886057615, -379345611, -1637575261, 249268274, 2044508324, -522852066, -1747789432, 162941995, 2125561021, -407360249, -1866523247, 498536548, 1789927666, -205950648, -2067906082, 450548861, 1843258603, -187386543, -2083289657, 325883990, 1684777152, -43845254, -1973040660, 335633487, 1661365465, -99664541, -1928851979, 997073096, 1281953886, -715111964, -1570279054, 1006888145, 1258607687, -770865667, -1526024853, 901097722, 1119000684, -608450090, -1396901568, 853044451, 1172266101, -589951537, -1412350631, 651767980, 1373503546, -925412992, -1076862698, 565507253, 1454621731, -809855591, -1195530993, 671266974, 1594198024, -972236366, -1324619484, 795835527, 1483230225, -1050600021, -1234817731, 1994146192, 31158534, -1731059524, -271249366, 1907459465, 112637215, -1614814043, -390540237, 2013776290, 251722036, -1777751922, -519137256, 2137656763, 141376813, -1855689577, -429695999, 1802195444, 476864866, -2056965928, -228458418, 1812370925, 453092731, -2113342271, -183516073, 1706088902, 314042704, -1950435094, -54949764, 1658658271, 366619977, -1932296973, -69972891, 1303535960, 984961486, -1547960204, -725929758, 1256170817, 1037604311, -1529756563, -740887301, 1131014506, 879679996, -1385723834, -631195440, 1141124467, 855842277, -1442165665, -586318647, 1342533948, 654459306, -1106571248, -921952122, 1466479909, 544179635, -1184443383, -832445281, 1591671054, 702138776, -1328506846, -942167884, 1504918807, 783551873, -1212326853, -1061524307, -306674912, -1698712650, 62317068, 1957810842, -355121351, -1647151185, 81470997, 1943803523, -480048366, -1805370492, 225274430, 2053790376, -468791541, -1828061283, 167816743, 2097651377, -267414716, -2029476910, 503444072, 1762050814, -144550051, -2140837941, 426522225, 1852507879, -19653770, -1982649376, 282753626, 1742555852, -105259153, -1900089351, 397917763, 1622183637, -690576408, -1580100738, 953729732, 1340076626, -776247311, -1497606297, 1068828381, 1219638859, -670225446, -1358292148, 906185462, 1090812512, -547295293, -1469587627, 829329135, 1181335161, -882789492, -1134132454, 628085408, 1382605366, -871598187, -1156888829, 570562233, 1426400815, -977650754, -1296233688, 733239954, 1555261956, -1026031705, -1244606671, 752459403, 1541320221, -1687895376, -328994266, 1969922972, 40735498, -1677130071, -351390145, 1913087877, 83908371, -1782625662, -491226604, 2075208622, 213261112, -1831694693, -438977011, 2094854071, 198958881, -2032938284, -237706686, 1759359992, 534414190, -2118248755, -155638181, 1873836001, 414664567, -2012718362, -15766928, 1711684554, 285281116, -1889165569, -127750551, 1634467795, 376229701, -1609899400, -686959890, 1308918612, 956543938, -1486412191, -799009033, 1231636301, 1047427035, -1362007478, -640263460, 1088359270, 936918000, -1447252397, -558129467, 1202900863, 817233897, -1111625188, -893730166, 1404277552, 615818150, -1160759803, -841546093, 1423857449, 601450431, -1285129682, -1000256840, 1567103746, 711928724, -1274298825, -1022587231, 1510334235, 755167117};

    public static WatchFaceUtil getInstance() {
        if (instance == null) {
            instance = new WatchFaceUtil();
        }
        return instance;
    }

    public static boolean watchFaceMode(int i2, int i3) {
        return (i3 & i2) == i2;
    }

    public byte[] bmpToRgb565Byte(Bitmap bitmap) {
        bitmap.copyPixelsToBuffer(ByteBuffer.allocate(bitmap.getByteCount()));
        int width = bitmap.getWidth() * bitmap.getHeight();
        int[] iArr = new int[width];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        byte[] bArr = new byte[width * 2];
        for (int i2 = 0; i2 < width; i2++) {
            int iUtendo = utendo(iArr[i2]);
            int i3 = i2 * 2;
            bArr[i3] = (byte) (iUtendo & 255);
            bArr[i3 + 1] = (byte) ((iUtendo >> 8) & 255);
        }
        return bArr;
    }

    public Bitmap changeWatchFaceBackgroundAndColor(Bitmap bitmap, int i2) {
        return PicUtils.getInstance().changeWatchFaceBackgroundAndColor(bitmap, i2);
    }

    public byte[] convertPictureToCompressedData(Bitmap bitmap) {
        return Rgb.getInstance().convertPictureToCompressedData(bitmap);
    }

    public Bitmap data565ToBitmap8888(byte[] bArr, int i2, int i3) {
        int i4 = i2 * i3;
        int[] iArr = new int[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i5 * 2;
            int i7 = ((bArr[i6 + 1] & 255) << 8) | (bArr[i6] & 255);
            iArr[i5] = ((i7 & 31) << 3) | (((63488 & i7) >> 11) << 19) | ViewCompat.MEASURED_STATE_MASK | (((i7 & 2016) >> 5) << 10);
        }
        return Bitmap.createBitmap(iArr, i2, i3, Bitmap.Config.ARGB_8888);
    }

    public boolean dealWithWatchFaceCustomData(Bitmap bitmap) {
        return PicUtils.getInstance().dealWithWatchFaceCustomData(bitmap);
    }

    public int downloadFile(String str, String str2, String str3) {
        return HttpDownloader.downloadFile(str, str2, str3);
    }

    public List<WatchFaceCustomInfo> getAppLocalWatchFace(List<AppLocalCustomWatchFaceInfo> list) throws Throwable {
        String str;
        ArrayList arrayList = new ArrayList();
        if (list == null) {
            str = "appLocalCustomWatchFaceInfos == null";
        } else {
            String resolutionWidthHeight = SPUtil.getInstance().getResolutionWidthHeight();
            int watchFaceShapeType = SPUtil.getInstance().getWatchFaceShapeType();
            LogUtils.i("getAppLocalWatchFace watchFaceShapeType =" + watchFaceShapeType + ",dpi =" + resolutionWidthHeight);
            for (int i2 = 0; i2 < list.size(); i2++) {
                AppLocalCustomWatchFaceInfo appLocalCustomWatchFaceInfo = list.get(i2);
                int watchFaceShapeType2 = appLocalCustomWatchFaceInfo.getWatchFaceShapeType();
                String dpi = appLocalCustomWatchFaceInfo.getDpi();
                String assetPath = appLocalCustomWatchFaceInfo.getAssetPath();
                if (watchFaceShapeType == watchFaceShapeType2 && resolutionWidthHeight.equals(dpi)) {
                    PicUtils.getInstance().setFolderDial(assetPath);
                    WatchFaceCustomInfo watchFaceCustomInfo = new WatchFaceCustomInfo();
                    watchFaceCustomInfo.setId("0");
                    watchFaceCustomInfo.setPathStatus(0);
                    watchFaceCustomInfo.setFolderDial(assetPath);
                    watchFaceCustomInfo.setType(watchFaceShapeType);
                    watchFaceCustomInfo.setDpi(resolutionWidthHeight);
                    try {
                        Bitmap defaultPreviewAssetPath = PicUtils.getInstance().getDefaultPreviewAssetPath();
                        if (defaultPreviewAssetPath != null) {
                            watchFaceCustomInfo.setBitmap(defaultPreviewAssetPath);
                            arrayList.add(watchFaceCustomInfo);
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            str = "AppLocalWatchFace.size() =" + arrayList.size();
        }
        LogUtils.i(str);
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getBinStringData(java.lang.String r9) throws java.io.IOException {
        /*
            r8 = this;
            r0 = 0
            r1 = 1
            java.lang.String r2 = ""
            int r3 = r9.length()     // Catch: java.io.IOException -> L4f
            int r3 = r3 + (-4)
            int r4 = r9.length()     // Catch: java.io.IOException -> L4f
            java.lang.String r3 = r9.substring(r3, r4)     // Catch: java.io.IOException -> L4f
            java.lang.String r4 = ".bin"
            boolean r3 = r3.equals(r4)     // Catch: java.io.IOException -> L4f
            if (r3 != 0) goto L1b
            return r2
        L1b:
            java.io.File r3 = new java.io.File     // Catch: java.io.IOException -> L4f
            r3.<init>(r9)     // Catch: java.io.IOException -> L4f
            java.io.FileInputStream r9 = new java.io.FileInputStream     // Catch: java.io.IOException -> L4f
            r9.<init>(r3)     // Catch: java.io.IOException -> L4f
            byte[] r3 = new byte[r1]     // Catch: java.io.IOException -> L4f
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.io.IOException -> L4f
            r4.<init>()     // Catch: java.io.IOException -> L4f
        L2c:
            int r5 = r9.read(r3)     // Catch: java.io.IOException -> L49
            r6 = -1
            if (r5 == r6) goto L4b
            java.lang.String r5 = "%02X"
            r6 = r3[r0]     // Catch: java.io.IOException -> L49
            r6 = r6 & 255(0xff, float:3.57E-43)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.io.IOException -> L49
            java.lang.Object[] r7 = new java.lang.Object[r1]     // Catch: java.io.IOException -> L49
            r7[r0] = r6     // Catch: java.io.IOException -> L49
            java.lang.String r5 = java.lang.String.format(r5, r7)     // Catch: java.io.IOException -> L49
            r4.append(r5)     // Catch: java.io.IOException -> L49
            goto L2c
        L49:
            r9 = move-exception
            goto L51
        L4b:
            r9.close()     // Catch: java.io.IOException -> L49
            goto L68
        L4f:
            r9 = move-exception
            r4 = 0
        L51:
            r9.printStackTrace()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "IOException2 ="
            r0.append(r1)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            com.yc.utesdk.log.LogUtils.i(r9)
        L68:
            if (r4 == 0) goto L6e
            java.lang.String r2 = r4.toString()
        L6e:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r0 = "a.length() ="
            r9.append(r0)
            int r0 = r2.length()
            r9.append(r0)
            java.lang.String r9 = r9.toString()
            com.yc.utesdk.log.LogUtils.i(r9)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.watchface.open.WatchFaceUtil.getBinStringData(java.lang.String):java.lang.String");
    }

    public WatchFaceCustomInfo getCurrentWatchFaceCustomInfo() {
        return this.f24962a;
    }

    public List<CustomViewPositionInfo> getDefaultViewPosition() {
        return PicUtils.getInstance().getDefaultViewPosition();
    }

    public List<WatchFaceCustomInfo> getLocalWatchFace() {
        return getAppLocalWatchFace(utendo());
    }

    public List<WatchFaceCustomInfo> getServerWatchFace() {
        return getServerWatchFace(true);
    }

    public Bitmap getWatchFaceDefaultPreview() {
        return PicUtils.getInstance().getWatchFaceDefaultPreview();
    }

    public int getWatchFaceMode() {
        return this.watchFaceMode;
    }

    public List<WatchFaceOnlineClass> getWatchFaceOnlineClass(String str) throws JSONException, NoSuchPaddingException, NoSuchAlgorithmException, PackageManager.NameNotFoundException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        JSONObject jSONObject = new JSONObject(HttpRequestor.getInstance().doPost(PostUtil.GET_WATCH_CLASS, PostUtil.getInstance().getWatchClassHashMap(str)));
        if (jSONObject.getInt(AgooConstants.MESSAGE_FLAG) < 0) {
            return null;
        }
        return (List) new Gson().fromJson(jSONObject.getJSONArray(AlinkConstants.KEY_LIST).toString(), new TypeToken<List<WatchFaceOnlineClass>>() { // from class: com.yc.utesdk.watchface.open.WatchFaceUtil.2
        }.getType());
    }

    public WatchFaceOnlineInfo getWatchFaceOnlineInformation(int i2, int i3, String str) {
        return getWatchFaceOnlineInformation(i2, i3, str, "");
    }

    public JSONObject getWatchFaceOnlineJSONObject(int i2, int i3, String str) {
        return getWatchFaceOnlineJSONObject(i2, i3, str, "");
    }

    public void parsingDdialInformation(String str, byte[] bArr) {
        int iUtendo = utendo(str, bArr);
        int iUtennew = utennew(bArr);
        int iUtenint = utenint(bArr);
        int iUtentry = utentry(bArr);
        int iUtenfor = utenfor(bArr);
        int iUtendo2 = utendo(bArr);
        int iUtenbyte = utenbyte(bArr);
        int iUtenif = utenif(bArr);
        boolean zUtencase = utencase(bArr);
        SPUtil.getInstance().setDialNumber(iUtendo);
        SPUtil.getInstance().setResolutionWidthHeight(iUtennew + ProxyConfig.MATCH_ALL_SCHEMES + iUtenint);
        SPUtil.getInstance().setWatchFaceShapeType(iUtentry);
        SPUtil.getInstance().setDialMaxDataSize(iUtenfor + "");
        SPUtil.getInstance().setDialScreenCompatibleLevel(iUtendo2);
        SPUtil.getInstance().setDialScreenCornerAngle(iUtenif);
        SPUtil.getInstance().setMaxOnlineWatchFaceCount(iUtenbyte);
        SPUtil.getInstance().setWatchFaceConfigurationSuccess(true);
        WatchFaceParams watchFaceParams = new WatchFaceParams();
        watchFaceParams.setWidth(iUtennew);
        watchFaceParams.setHeight(iUtenint);
        watchFaceParams.setScreenType(iUtentry);
        watchFaceParams.setMaxFileSize(iUtenfor);
        watchFaceParams.setScreenCorner(iUtenif);
        watchFaceParams.setWatchFaceCount(iUtenbyte);
        watchFaceParams.setCompatibleLevel(iUtendo2);
        watchFaceParams.setSupportImageWatchFace(zUtencase);
        SPUtil.getInstance().setWatchFaceParams(watchFaceParams);
        UteListenerManager.getInstance().onWatchFaceOnlineMaxCount(iUtenbyte);
        LogUtils.i("dialNumber =" + iUtendo + ",resolutionWidth =" + iUtennew + ",resolutionHeight =" + iUtenint + ",dialScreenType =" + iUtentry + ",dialMaxDataSize =" + iUtenfor + ",compatible =" + iUtendo2 + ",maxOnlineWatchFace =" + iUtenbyte + ",screenCorner =" + iUtenif + ",supportImageWatchFace =" + zUtencase);
    }

    public Bitmap picToBmpSDPath(String str) {
        return BitmapFactory.decodeFile(str);
    }

    public byte[] readBinToByte(File file) throws Throwable {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
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
            } catch (Throwable th) {
                th = th;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
        }
    }

    public void resetCustomViewPosition() {
        PicUtils.getInstance().resetCustomViewPosition();
    }

    public void resetWatchFaceBackgroundAndColor() {
        PicUtils.getInstance().resetWatchFaceBackgroundAndColor();
    }

    public void setCurrentWatchFaceCustomInfo(WatchFaceCustomInfo watchFaceCustomInfo) {
        this.f24962a = watchFaceCustomInfo;
        LogUtils.d("点击了onItemClick currentWatchFaceCustomInfo =" + new Gson().toJson(this.f24962a));
        PicUtils.getInstance().setWatchFaceShapeType(this.f24962a.getType());
        PicUtils.getInstance().setFolderDial(this.f24962a.getFolderDial());
        PicUtils.getInstance().setPathStatus(this.f24962a.getPathStatus());
    }

    public void setCustomViewPosition(List<CustomViewPositionInfo> list) {
        PicUtils.getInstance().setCustomViewPosition(list);
    }

    public void setSendDialDataTimeInterval(int i2) {
        DELAY_SYNC_WATCH_FACE_DATA_TIME = i2;
    }

    public void setWatchFaceMode(int i2) {
        this.watchFaceMode = i2;
    }

    public boolean syncCustomDialData() {
        return PicUtils.getInstance().syncCustomDialData();
    }

    public int uteDataCrc32(byte[] bArr) {
        int i2 = -1;
        for (byte b2 : bArr) {
            i2 = (i2 >>> 8) ^ this.crc32_tab[(b2 ^ i2) & 255];
        }
        return ~i2;
    }

    public final int utenbyte(byte[] bArr) {
        return Math.max(bArr.length > 16 ? bArr[16] & 255 : 1, 1);
    }

    public final boolean utencase(byte[] bArr) {
        return (bArr.length > 18 ? bArr[18] & 255 : 0) > 0;
    }

    public final int utendo(int i2) {
        return (((this.RGB888_RED & i2) >> 19) << 11) + (((this.RGB888_GREEN & i2) >> 10) << 5) + ((i2 & this.RGB888_BLUE) >> 3);
    }

    public final int utenfor(byte[] bArr) {
        int i2 = bArr[14] & 255;
        int i3 = (bArr[13] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
        return ((bArr[11] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) | i2 | i3 | ((bArr[12] << 16) & 16711680);
    }

    public final int utenif(byte[] bArr) {
        if (bArr.length > 17) {
            return bArr[17] & 255;
        }
        return 0;
    }

    public final int utenint(byte[] bArr) {
        return ((bArr[8] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[9] & 255);
    }

    public final int utennew(byte[] bArr) {
        return ((bArr[6] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[7] & 255);
    }

    public final int utentry(byte[] bArr) {
        return bArr[10] & 255;
    }

    public Bitmap zoomBitmap(Bitmap bitmap, float f2, float f3) {
        return Rgb.getInstance().zoomBitmap(bitmap, f2, f3);
    }

    public List<WatchFaceCustomInfo> getServerWatchFace(boolean z2) throws JSONException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, IOException, InvalidAlgorithmParameterException {
        ArrayList arrayList = new ArrayList();
        String json = new Gson().toJson(PostUtil.getInstance().getWatchZipsHashMap(SPUtil.getInstance().getDeviceFirmware(), SPUtil.getInstance().getResolutionWidthHeight(), SPUtil.getInstance().getWatchFaceShapeType(), z2));
        HashMap map = new HashMap();
        map.put("content", json);
        JSONObject jSONObject = new JSONObject(HttpRequestor.getInstance().doPost(PostUtil.GET_WATCH_ZIPS, map));
        int i2 = jSONObject.getInt(AgooConstants.MESSAGE_FLAG);
        LogUtils.i("getServerWatchFace flag =" + i2);
        if (i2 < 0) {
            return arrayList;
        }
        JSONArray jSONArray = jSONObject.getJSONArray(AlinkConstants.KEY_LIST);
        LogUtils.i("getServerWatchFace jsonArray =" + jSONArray);
        List list = (List) new Gson().fromJson(jSONArray.toString(), new TypeToken<List<WatchFaceCustomInfo>>() { // from class: com.yc.utesdk.watchface.open.WatchFaceUtil.3
        }.getType());
        if (list != null) {
            LogUtils.d("dialZipInfoList: " + list.size());
            for (int i3 = 0; i3 < list.size(); i3++) {
                List listUtendo = utendo((WatchFaceCustomInfo) list.get(i3));
                if (listUtendo.size() > 0) {
                    arrayList.addAll(listUtendo);
                }
            }
        }
        return arrayList;
    }

    public WatchFaceOnlineInfo getWatchFaceOnlineInformation(int i2, int i3, String str, String str2) throws Throwable {
        String strDoPost = HttpRequestor.getInstance().doPost(PostUtil.GET_WATCH, PostUtil.getInstance().getWatchHashMap(i2, i3, str, str2));
        JSONObject jSONObject = new JSONObject(strDoPost);
        LogUtils.i("getWatchFaceOnlineInformation json =" + strDoPost);
        WatchFaceOnlineInfo watchFaceOnlineInfo = new WatchFaceOnlineInfo();
        int i4 = jSONObject.getInt(AgooConstants.MESSAGE_FLAG);
        LogUtils.i("getWatchFaceOnlineInformation flag =" + i4);
        watchFaceOnlineInfo.setFlag(i4);
        if (i4 < 0) {
            return watchFaceOnlineInfo;
        }
        watchFaceOnlineInfo.setTotalCount(jSONObject.getInt("count"));
        watchFaceOnlineInfo.setWatchFaceOnlineOneInfoList((List) new Gson().fromJson(jSONObject.getJSONArray(AlinkConstants.KEY_LIST).toString(), new TypeToken<List<WatchFaceOnlineOneInfo>>() { // from class: com.yc.utesdk.watchface.open.WatchFaceUtil.1
        }.getType()));
        return watchFaceOnlineInfo;
    }

    public JSONObject getWatchFaceOnlineJSONObject(int i2, int i3, String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, PackageManager.NameNotFoundException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        return new JSONObject(HttpRequestor.getInstance().doPost(PostUtil.GET_WATCH, PostUtil.getInstance().getWatchHashMap(i2, i3, str, str2)));
    }

    public final List utendo(WatchFaceCustomInfo watchFaceCustomInfo) throws IOException {
        int iDownloadFile;
        ArrayList arrayList = new ArrayList();
        String file = watchFaceCustomInfo.getFile();
        String str = UteBleClient.getContext().getExternalFilesDir(null).getAbsolutePath() + File.separator + "DialCustom/";
        String strReplace = watchFaceCustomInfo.getDpi().replace(ProxyConfig.MATCH_ALL_SCHEMES, "x");
        String str2 = "DialCustom_" + strReplace + OpenAccountUIConstants.UNDER_LINE + watchFaceCustomInfo.getId() + ".zip";
        String str3 = "DialCustom_" + strReplace + OpenAccountUIConstants.UNDER_LINE + watchFaceCustomInfo.getId();
        LogUtils.d("downLoadZip 开始下载 path=" + str);
        if (ZipUtils.fileIsExists(str + str3)) {
            LogUtils.d("downLoadZip 已存在，不需要下载");
            iDownloadFile = 0;
        } else {
            LogUtils.d("downLoadZip 开始下载 path=" + str);
            iDownloadFile = HttpDownloader.downloadFile(file, str, str2);
            if (iDownloadFile == 0) {
                LogUtils.d("downLoadZip 开始解压");
                ZipUtils.UnZipFolder(str + str2, str + str3);
            }
        }
        if (iDownloadFile == 0) {
            watchFaceCustomInfo.setBitmap(PicUtils.getInstance().getDefaultPreviewSDPath(str + "/" + str3));
            watchFaceCustomInfo.setFolderDial(str + "/" + str3);
            watchFaceCustomInfo.setPathStatus(1);
            arrayList.add(watchFaceCustomInfo);
        }
        return arrayList;
    }

    public final int utendo(byte[] bArr) {
        if (bArr.length > 15) {
            return bArr[15] & 255;
        }
        return 0;
    }

    public final int utendo(String str, byte[] bArr) {
        if (str.startsWith("FFFFFFFF", 4)) {
            LogUtils.i("表示当前没有显示的在线表盘");
            return -1;
        }
        int i2 = bArr[5] & 255;
        int i3 = (bArr[4] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
        return i2 | i3 | ((bArr[3] << 16) & 16711680) | ((bArr[2] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK);
    }

    public final List utendo() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new AppLocalCustomWatchFaceInfo(2, WATCH_FACE_DPI_240x240, "DialCustom/dial_1_circle_240x240"));
        arrayList.add(new AppLocalCustomWatchFaceInfo(1, WATCH_FACE_DPI_240x240, "DialCustom/dial_2_square_240x240"));
        arrayList.add(new AppLocalCustomWatchFaceInfo(1, WATCH_FACE_DPI_320x360, "DialCustom/dial_10001_square_320x360"));
        return arrayList;
    }
}
