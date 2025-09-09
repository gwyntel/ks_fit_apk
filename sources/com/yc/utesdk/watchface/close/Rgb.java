package com.yc.utesdk.watchface.close;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.google.common.base.Ascii;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.GBUtils;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class Rgb {
    private static final boolean DEBUG = true;
    private static final String TAG = "RGB";
    private static Rgb instance;
    private int RGB888_RED = 16711680;
    private int RGB888_GREEN = MotionEventCompat.ACTION_POINTER_INDEX_MASK;
    private int RGB888_BLUE = 255;
    private int RGB565_RED = 63488;
    private int RGB565_GREEN = 2016;
    private int RGB565_BLUE = 31;
    private int[] crc32_tab = {0, 1996959894, -301047508, -1727442502, 124634137, 1886057615, -379345611, -1637575261, 249268274, 2044508324, -522852066, -1747789432, 162941995, 2125561021, -407360249, -1866523247, 498536548, 1789927666, -205950648, -2067906082, 450548861, 1843258603, -187386543, -2083289657, 325883990, 1684777152, -43845254, -1973040660, 335633487, 1661365465, -99664541, -1928851979, 997073096, 1281953886, -715111964, -1570279054, 1006888145, 1258607687, -770865667, -1526024853, 901097722, 1119000684, -608450090, -1396901568, 853044451, 1172266101, -589951537, -1412350631, 651767980, 1373503546, -925412992, -1076862698, 565507253, 1454621731, -809855591, -1195530993, 671266974, 1594198024, -972236366, -1324619484, 795835527, 1483230225, -1050600021, -1234817731, 1994146192, 31158534, -1731059524, -271249366, 1907459465, 112637215, -1614814043, -390540237, 2013776290, 251722036, -1777751922, -519137256, 2137656763, 141376813, -1855689577, -429695999, 1802195444, 476864866, -2056965928, -228458418, 1812370925, 453092731, -2113342271, -183516073, 1706088902, 314042704, -1950435094, -54949764, 1658658271, 366619977, -1932296973, -69972891, 1303535960, 984961486, -1547960204, -725929758, 1256170817, 1037604311, -1529756563, -740887301, 1131014506, 879679996, -1385723834, -631195440, 1141124467, 855842277, -1442165665, -586318647, 1342533948, 654459306, -1106571248, -921952122, 1466479909, 544179635, -1184443383, -832445281, 1591671054, 702138776, -1328506846, -942167884, 1504918807, 783551873, -1212326853, -1061524307, -306674912, -1698712650, 62317068, 1957810842, -355121351, -1647151185, 81470997, 1943803523, -480048366, -1805370492, 225274430, 2053790376, -468791541, -1828061283, 167816743, 2097651377, -267414716, -2029476910, 503444072, 1762050814, -144550051, -2140837941, 426522225, 1852507879, -19653770, -1982649376, 282753626, 1742555852, -105259153, -1900089351, 397917763, 1622183637, -690576408, -1580100738, 953729732, 1340076626, -776247311, -1497606297, 1068828381, 1219638859, -670225446, -1358292148, 906185462, 1090812512, -547295293, -1469587627, 829329135, 1181335161, -882789492, -1134132454, 628085408, 1382605366, -871598187, -1156888829, 570562233, 1426400815, -977650754, -1296233688, 733239954, 1555261956, -1026031705, -1244606671, 752459403, 1541320221, -1687895376, -328994266, 1969922972, 40735498, -1677130071, -351390145, 1913087877, 83908371, -1782625662, -491226604, 2075208622, 213261112, -1831694693, -438977011, 2094854071, 198958881, -2032938284, -237706686, 1759359992, 534414190, -2118248755, -155638181, 1873836001, 414664567, -2012718362, -15766928, 1711684554, 285281116, -1889165569, -127750551, 1634467795, 376229701, -1609899400, -686959890, 1308918612, 956543938, -1486412191, -799009033, 1231636301, 1047427035, -1362007478, -640263460, 1088359270, 936918000, -1447252397, -558129467, 1202900863, 817233897, -1111625188, -893730166, 1404277552, 615818150, -1160759803, -841546093, 1423857449, 601450431, -1285129682, -1000256840, 1567103746, 711928724, -1274298825, -1022587231, 1510334235, 755167117};

    public static void LogD(String str) {
        LogUtils.d(TAG, str);
    }

    public static Rgb getInstance() {
        if (instance == null) {
            instance = new Rgb();
        }
        return instance;
    }

    public byte[] SnNo() {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            if (i2 == 0) {
                bArr[i2] = -2;
            } else {
                bArr[i2] = -1;
            }
        }
        return bArr;
    }

    public byte[] addHeadBeForeCompress(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, 16, (byte) ((i2 >> 8) & 255), (byte) (i2 & 255), (byte) ((i3 >> 8) & 255), (byte) (i3 & 255), 1, Ascii.ESC};
        byte[] bArr3 = new byte[bArr.length + 8];
        System.arraycopy(bArr2, 0, bArr3, 0, 8);
        System.arraycopy(bArr, 0, bArr3, 8, bArr.length);
        return bArr3;
    }

    public byte[] antiAliasingBg(Bitmap bitmap, int i2, int i3) {
        int i4;
        byte[] bArr = new byte[bitmap.getWidth() * bitmap.getHeight() * 2];
        int width = bitmap.getWidth() * bitmap.getHeight();
        int[] iArr = new int[width];
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        RectF rectF = new RectF(0.0f, 0.0f, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
        paint.setAntiAlias(true);
        int i5 = 0;
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        canvas.drawRoundRect(rectF, 0.0f, 0.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        int i6 = 0;
        for (int i7 = 0; i7 < bitmap.getHeight(); i7++) {
            int i8 = i5;
            while (i8 < bitmap.getWidth()) {
                int pixel = bitmap.getPixel(i8, i7);
                int iRed = Color.red(pixel);
                int iGreen = Color.green(pixel);
                int iBlue = Color.blue(pixel);
                int iAlpha = Color.alpha(pixel);
                if (iAlpha == 0 || iAlpha == 255) {
                    i4 = i5;
                } else {
                    float f2 = iAlpha / 255.0f;
                    float f3 = 1.0f - f2;
                    int iRed2 = (int) ((Color.red(bitmapCreateBitmap.getPixel(i2 + i8, i3 + i7)) * f3) + (iRed * f2));
                    int iGreen2 = (int) ((Color.green(r8) * f3) + (iGreen * f2));
                    i4 = 0;
                    iRed = Math.max(0, Math.min(255, iRed2));
                    iGreen = Math.max(0, Math.min(255, iGreen2));
                    iBlue = Math.max(0, Math.min(255, (int) ((f3 * Color.blue(r8)) + (f2 * iBlue))));
                    if (iRed <= 16 && iGreen <= 8 && iBlue <= 16) {
                        iRed = 17;
                        iGreen = 9;
                        iBlue = 17;
                    }
                }
                iArr[i6] = Color.rgb(iRed, iGreen, iBlue);
                i6++;
                i8++;
                i5 = i4;
            }
        }
        while (i5 < width) {
            int iUtendo = utendo(iArr[i5]);
            int i9 = i5 * 2;
            bArr[i9 + 1] = (byte) (iUtendo & 255);
            bArr[i9] = (byte) ((iUtendo >> 8) & 255);
            i5++;
        }
        return bArr;
    }

    public byte[] antiAliasingDefault(Bitmap bitmap, Bitmap bitmap2, int i2, int i3, boolean z2, int i4) {
        byte[] bArr = new byte[bitmap2.getWidth() * bitmap2.getHeight() * 2];
        int width = bitmap2.getWidth() * bitmap2.getHeight();
        int[] iArr = new int[width];
        int i5 = 0;
        for (int i6 = 0; i6 < bitmap2.getHeight(); i6++) {
            for (int i7 = 0; i7 < bitmap2.getWidth(); i7++) {
                int pixel = bitmap2.getPixel(i7, i6);
                int iRed = Color.red(pixel);
                int iGreen = Color.green(pixel);
                int iBlue = Color.blue(pixel);
                int iAlpha = Color.alpha(pixel);
                if (z2 && (iRed > 0 || iGreen > 0 || iBlue > 0)) {
                    iRed = Color.red(i4);
                    iGreen = Color.green(i4);
                    iBlue = Color.blue(i4);
                }
                if (iAlpha != 0 && iAlpha != 255) {
                    float f2 = iAlpha / 255.0f;
                    float f3 = 1.0f - f2;
                    int iRed2 = (int) ((Color.red(bitmap.getPixel(i2 + i7, i3 + i6)) * f3) + (iRed * f2));
                    int iGreen2 = (int) ((Color.green(r14) * f3) + (iGreen * f2));
                    iRed = Math.max(0, Math.min(255, iRed2));
                    iGreen = Math.max(0, Math.min(255, iGreen2));
                    iBlue = Math.max(0, Math.min(255, (int) ((f3 * Color.blue(r14)) + (f2 * iBlue))));
                    if (iRed <= 16 && iGreen <= 8 && iBlue <= 16) {
                        iRed = 17;
                        iGreen = 9;
                        iBlue = 17;
                    }
                }
                iArr[i5] = Color.rgb(iRed, iGreen, iBlue);
                i5++;
            }
        }
        for (int i8 = 0; i8 < width; i8++) {
            int iUtendo = utendo(iArr[i8]);
            int i9 = i8 * 2;
            bArr[i9 + 1] = (byte) (iUtendo & 255);
            bArr[i9] = (byte) ((iUtendo >> 8) & 255);
        }
        return bArr;
    }

    public byte[] bitmapToRgb565Byte(Bitmap bitmap) {
        int width = bitmap.getWidth() * bitmap.getHeight();
        int[] iArr = new int[width];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        byte[] bArr = new byte[width * 2];
        for (int i2 = 0; i2 < width; i2++) {
            int iUtendo = utendo(iArr[i2]);
            int i3 = i2 * 2;
            bArr[i3 + 1] = (byte) (iUtendo & 255);
            bArr[i3] = (byte) ((iUtendo >> 8) & 255);
        }
        return bArr;
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
            bArr[i3 + 1] = (byte) (iUtendo & 255);
            bArr[i3] = (byte) ((iUtendo >> 8) & 255);
        }
        return bArr;
    }

    public int bytes1ToInt(byte[] bArr) {
        return bArr[0] & 255;
    }

    public byte[] bytes2HLExchange(byte[] bArr) {
        return intToBytes2L(utendo(bArr));
    }

    public int bytes2HLExchangeInt(byte[] bArr) {
        return utendo(bArr);
    }

    public String bytes2HexString(byte[] bArr) {
        String str = "";
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            str = str + hexString;
        }
        return str;
    }

    public byte[] bytes4HLExchange(byte[] bArr) {
        return intToBytes4L(utenif(bArr));
    }

    public int bytes4HLExchangeInt(byte[] bArr) {
        return utenif(bArr);
    }

    public Bitmap compositePreviewBitmap(Bitmap bitmap, int i2, String str) {
        Bitmap previewBgAssetPath;
        if (i2 == 1) {
            previewBgAssetPath = PicUtils.getInstance().getPreviewBgSDPath(str);
        } else {
            try {
                previewBgAssetPath = PicUtils.getInstance().getPreviewBgAssetPath();
            } catch (IOException e2) {
                e2.printStackTrace();
                previewBgAssetPath = null;
            }
        }
        if (previewBgAssetPath == null) {
            return bitmap;
        }
        float width = previewBgAssetPath.getWidth();
        float height = previewBgAssetPath.getHeight();
        return utendo(previewBgAssetPath, bitmap, (width * (1.0f - (bitmap.getWidth() / width))) / 2.0f, (height * (1.0f - (bitmap.getHeight() / height))) / 2.0f);
    }

    public byte[] compositePreviewToByte(byte[] bArr, Bitmap bitmap, boolean z2) {
        byte[] bArrBitmapToRgb565Byte = bitmapToRgb565Byte(bitmap);
        if (z2) {
            bArrBitmapToRgb565Byte = picDataCompress(addHeadBeForeCompress(bArrBitmapToRgb565Byte, bitmap.getWidth(), bitmap.getHeight()));
        }
        byte[] bArrHexStringToBytessForOnlineDial = GBUtils.getInstance().hexStringToBytessForOnlineDial("0a00f0002e160000f0000000000000000100000000000000");
        RgbAarrayInfo rgbAarrayInfoUtendo = bArr != null ? utendo(bArr, z2) : null;
        if (rgbAarrayInfoUtendo != null) {
            bArr = generationSortArrays(rgbAarrayInfoUtendo, bArrHexStringToBytessForOnlineDial, bArrBitmapToRgb565Byte);
        }
        byte[] bArrBytes4HLExchange = bytes4HLExchange(intToBytes4L(uteDataCrc32(bArr)));
        System.arraycopy(bArrBytes4HLExchange, 0, bArr, 8, bArrBytes4HLExchange.length);
        return bArr;
    }

    public byte[] convertPictureToCompressedData(Bitmap bitmap) {
        String str;
        if (bitmap == null) {
            str = "Bitmap is null";
        } else {
            if (bitmap.getWidth() != 0 && bitmap.getHeight() != 0) {
                return picDataCompress(addHeadBeForeCompress(bmpToRgb565Byte(bitmap), bitmap.getWidth(), bitmap.getHeight()));
            }
            str = "The width or height of the Bitmap is 0";
        }
        LogD(str);
        return null;
    }

    public Bitmap data565ToBitmap8888(byte[] bArr, int i2, int i3) {
        int i4 = i2 * i3;
        int[] iArr = new int[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i5 * 2;
            int i7 = 255;
            int i8 = ((bArr[i6] & 255) << 8) | (bArr[i6 + 1] & 255);
            int i9 = (63488 & i8) >> 11;
            int i10 = (i8 & 2016) >> 5;
            int i11 = i10 << 2;
            int i12 = (i8 & 31) << 3;
            if ((i9 << 3) <= 0 && i11 <= 0 && i12 <= 0) {
                i7 = 0;
            }
            iArr[i5] = i12 | (i9 << 19) | (i7 << 24) | (i10 << 10);
        }
        if (i2 == 0 || i3 == 0) {
            return null;
        }
        return Bitmap.createBitmap(iArr, i2, i3, Bitmap.Config.ARGB_8888);
    }

    public Bitmap dataToBitmap(byte[] bArr, int i2, int i3) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.RGB_565);
        byte[] bArr2 = new byte[bArr.length];
        for (int i4 = 0; i4 < bArr.length; i4++) {
            if (i4 % 2 == 0) {
                bArr2[i4] = bArr[i4 + 1];
            } else {
                bArr2[i4] = bArr[i4 - 1];
            }
        }
        bitmapCreateBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bArr2));
        return bitmapCreateBitmap;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0381, code lost:
    
        r19 = r4;
        r18 = r5;
        r20 = r9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] generationSortArrays(com.yc.utesdk.watchface.close.RgbAarrayInfo r22, byte[] r23, byte[] r24) {
        /*
            Method dump skipped, instructions count: 1487
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.watchface.close.Rgb.generationSortArrays(com.yc.utesdk.watchface.close.RgbAarrayInfo, byte[], byte[]):byte[]");
    }

    public byte[] intToBytes1L(int i2) {
        return new byte[]{(byte) (i2 & 255)};
    }

    public byte[] intToBytes2L(int i2) {
        return new byte[]{(byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
    }

    public byte[] intToBytes4L(int i2) {
        return new byte[]{(byte) ((i2 >> 24) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
    }

    public Bitmap mergeBitmap(ArrayList<BitmapInfo> arrayList) {
        Bitmap bitmapRoundedCornerBitmap;
        Canvas canvas;
        int i2 = 0;
        while (true) {
            if (i2 >= arrayList.size()) {
                break;
            }
            if (arrayList.get(i2).getType() == 17) {
                int picWidth = arrayList.get(i2).getPicWidth();
                int picHeight = arrayList.get(i2).getPicHeight();
                arrayList.get(i2).getX();
                arrayList.get(i2).getY();
                Bitmap bitmap = arrayList.get(i2).getBitmap();
                if (bitmap != null) {
                    bitmapRoundedCornerBitmap = Bitmap.createBitmap(picWidth, picHeight, bitmap.getConfig());
                    canvas = new Canvas(bitmapRoundedCornerBitmap);
                    canvas.drawBitmap(bitmap, new Matrix(), null);
                }
            } else {
                i2++;
            }
        }
        bitmapRoundedCornerBitmap = null;
        canvas = null;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            int type = arrayList.get(i3).getType();
            if (type != 17 && type != 10) {
                int x2 = arrayList.get(i3).getX();
                int y2 = arrayList.get(i3).getY();
                if (arrayList.get(i3).getBitmap() != null) {
                    canvas.drawBitmap(arrayList.get(i3).getBitmap(), x2, y2, (Paint) null);
                }
            }
        }
        PreviewScaleInfo previewScaleInfo = PicUtils.getInstance().getPreviewScaleInfo();
        if (previewScaleInfo == null || bitmapRoundedCornerBitmap == null) {
            return bitmapRoundedCornerBitmap;
        }
        LogD("previewDial PreviewScaleInfo " + previewScaleInfo.getPreviewWidth() + "," + previewScaleInfo.getPreviewHeight() + "," + previewScaleInfo.getPreviewRound());
        if (previewScaleInfo.getPreviewRound() > 0.0f) {
            bitmapRoundedCornerBitmap = roundedCornerBitmap(bitmapRoundedCornerBitmap, previewScaleInfo.getPreviewRound(), previewScaleInfo.getPreviewRound());
        }
        return zoomBitmap(bitmapRoundedCornerBitmap, previewScaleInfo.getPreviewWidth() / bitmapRoundedCornerBitmap.getWidth(), previewScaleInfo.getPreviewHeight() / bitmapRoundedCornerBitmap.getHeight());
    }

    public byte[] picDataCompress(byte[] bArr) {
        byte[] bArr2;
        int i2 = 4;
        int i3 = 8;
        byte b2 = 255;
        int i4 = ((bArr[4] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[5] & 255);
        int i5 = 2;
        int i6 = (65280 & (bArr[2] << 8)) | (bArr[3] & 255);
        int i7 = i4 * 4;
        int i8 = 0;
        byte[] bArr3 = new byte[0];
        byte[] bArrUtenif = new byte[0];
        int i9 = 0;
        while (i9 < i4) {
            int i10 = (i9 * i6 * i5) + i3;
            byte[] bArr4 = new byte[i8];
            int i11 = i8;
            int i12 = i11;
            int i13 = i12;
            int i14 = i13;
            int length = i14;
            while (i13 < i6) {
                int i15 = i10 + (i14 * 2);
                int i16 = i10 + (i13 * 2);
                if (bArr[i15] == bArr[i16] && bArr[i15 + 1] == bArr[i16 + 1] && i11 != b2) {
                    i11++;
                    i13++;
                    if (i13 != i6) {
                        i8 = 0;
                    }
                }
                if (i12 == 0) {
                    byte[] bArr5 = new byte[i2];
                    bArr5[0] = 1;
                    bArr5[1] = (byte) i11;
                    bArr5[2] = bArr[i15];
                    bArr5[3] = bArr[i15 + 1];
                    bArr2 = bArr5;
                } else if (i12 != i11 || (bArr4[0] & b2) >= b2) {
                    bArrUtenif = utenif(bArrUtenif, bArr4);
                    length += bArr4.length;
                    bArr2 = new byte[]{1, (byte) i11, bArr[i15], bArr[i15 + 1]};
                } else {
                    int length2 = bArr4.length;
                    byte[] bArr6 = new byte[length2 + 2];
                    System.arraycopy(bArr4, 0, bArr6, 0, bArr4.length);
                    bArr6[0] = (byte) ((bArr6[0] & b2) + 1);
                    bArr6[length2] = bArr[i15];
                    bArr6[length2 + 1] = bArr[i15 + 1];
                    bArr2 = bArr6;
                }
                if (i13 == i6) {
                    byte[] bArrUtenif2 = utenif(bArrUtenif, bArr2);
                    byte[] bArrBytes4HLExchange = bytes4HLExchange(intToBytes4L(i7));
                    length += bArr2.length;
                    i7 += length;
                    byte[] bArr7 = new byte[bArr3.length + bArrBytes4HLExchange.length];
                    i8 = 0;
                    System.arraycopy(bArr3, 0, bArr7, 0, bArr3.length);
                    System.arraycopy(bArrBytes4HLExchange, 0, bArr7, bArr3.length, bArrBytes4HLExchange.length);
                    bArr3 = bArr7;
                    bArrUtenif = bArrUtenif2;
                } else {
                    i8 = 0;
                }
                i12 = i11;
                bArr4 = bArr2;
                i11 = i8;
                i14 = i13;
                i2 = 4;
                b2 = 255;
            }
            i9++;
            i2 = 4;
            i3 = 8;
            i5 = 2;
            b2 = 255;
        }
        byte[] bArrUtendo = utendo(bArrUtenif, bArr3);
        return utendo(bArrUtendo, bytes4HLExchange(intToBytes4L(bArrUtendo.length)));
    }

    public byte[] picReserved() {
        return new byte[7];
    }

    public Bitmap picToBmpAssetPath(String str) throws IOException {
        InputStream inputStreamOpen = null;
        try {
            inputStreamOpen = utendo().open(str);
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
            bitmapDecodeStream.copyPixelsToBuffer(ByteBuffer.allocate(bitmapDecodeStream.getByteCount()));
            return bitmapDecodeStream;
        } finally {
            if (inputStreamOpen != null) {
                inputStreamOpen.close();
            }
        }
    }

    public Bitmap picToBmpSDPath(String str) {
        return BitmapFactory.decodeFile(str);
    }

    public byte[] picToRgb565ByteAssetPath(String str) throws IOException {
        Bitmap bitmapPicToBmpAssetPath = picToBmpAssetPath(str);
        int width = bitmapPicToBmpAssetPath.getWidth() * bitmapPicToBmpAssetPath.getHeight();
        int[] iArr = new int[width];
        bitmapPicToBmpAssetPath.getPixels(iArr, 0, bitmapPicToBmpAssetPath.getWidth(), 0, 0, bitmapPicToBmpAssetPath.getWidth(), bitmapPicToBmpAssetPath.getHeight());
        byte[] bArr = new byte[width * 2];
        for (int i2 = 0; i2 < width; i2++) {
            int iUtendo = utendo(iArr[i2]);
            int i3 = i2 * 2;
            bArr[i3 + 1] = (byte) (iUtendo & 255);
            bArr[i3] = (byte) ((iUtendo >> 8) & 255);
        }
        return bArr;
    }

    public byte[] readBinToByte(String str) throws Throwable {
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

    public Bitmap roundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            canvas.drawRoundRect(new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight())), bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), rect, paint);
            return bitmapCreateBitmap;
        } catch (Exception unused) {
            return bitmap;
        }
    }

    public byte[] stitchingArrays(RgbAarrayInfo rgbAarrayInfo) {
        if (rgbAarrayInfo == null) {
            return null;
        }
        int length = rgbAarrayInfo.getWatchConfig().length;
        byte[] bArr = new byte[length];
        System.arraycopy(rgbAarrayInfo.getWatchConfig(), 0, bArr, 0, length);
        int i2 = 0;
        while (i2 < rgbAarrayInfo.getPicTypeConfig().size()) {
            byte[] picType = rgbAarrayInfo.getPicTypeConfig().get(i2).getPicType();
            byte[] bArr2 = new byte[i2 == rgbAarrayInfo.getPicTypeConfig().size() + (-1) ? bArr.length + picType.length + 2 : bArr.length + picType.length];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            System.arraycopy(picType, 0, bArr2, bArr.length, picType.length);
            i2++;
            bArr = bArr2;
        }
        List listUtendo = utendo(rgbAarrayInfo.getPicDataConfig());
        int i3 = 0;
        while (i3 < listUtendo.size()) {
            byte[] picData = ((PicDataInfo) listUtendo.get(i3)).getPicData();
            byte[] bArr3 = new byte[bArr.length + picData.length];
            System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
            System.arraycopy(picData, 0, bArr3, bArr.length, picData.length);
            i3++;
            bArr = bArr3;
        }
        return bArr;
    }

    public int uteDataCrc32(byte[] bArr) {
        int i2 = -1;
        for (int i3 = 24; i3 < bArr.length; i3++) {
            i2 = (i2 >>> 8) ^ this.crc32_tab[(bArr[i3] ^ i2) & 255];
        }
        return ~i2;
    }

    public final int utendo(int i2) {
        return (((this.RGB888_RED & i2) >> 19) << 11) + (((this.RGB888_GREEN & i2) >> 10) << 5) + ((i2 & this.RGB888_BLUE) >> 3);
    }

    public final int utenif(byte[] bArr) {
        return ((bArr[3] & 255) << 24) | (bArr[0] & 255) | ((bArr[1] & 255) << 8) | ((bArr[2] & 255) << 16);
    }

    public byte[] watchReserved() {
        byte[] bArr = new byte[5];
        for (int i2 = 0; i2 < 5; i2++) {
            bArr[i2] = -1;
        }
        return bArr;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x0071 -> B:62:0x0074). Please report as a decompilation issue!!! */
    public String writeByteToBin(byte[] bArr, String str, String str2) throws Throwable {
        FileOutputStream fileOutputStream;
        String str3 = "";
        String str4 = str + File.separator + str2;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                File file = new File(str);
                if (!file.exists() && !file.isDirectory()) {
                    file.mkdirs();
                }
                fileOutputStream = new FileOutputStream(new File(str4));
                try {
                    try {
                        BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream);
                        try {
                            bufferedOutputStream2.write(bArr);
                            try {
                                bufferedOutputStream2.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                str4 = "";
                            }
                            fileOutputStream.close();
                            str3 = str4;
                        } catch (Exception e3) {
                            e = e3;
                            bufferedOutputStream = bufferedOutputStream2;
                            e.printStackTrace();
                            if (bufferedOutputStream != null) {
                                try {
                                    bufferedOutputStream.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            return str3;
                        } catch (Throwable th) {
                            th = th;
                            bufferedOutputStream = bufferedOutputStream2;
                            if (bufferedOutputStream != null) {
                                try {
                                    bufferedOutputStream.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            if (fileOutputStream == null) {
                                throw th;
                            }
                            try {
                                fileOutputStream.close();
                                throw th;
                            } catch (IOException e6) {
                                e6.printStackTrace();
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception e7) {
                    e = e7;
                }
            } catch (Exception e8) {
                e = e8;
                fileOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
            }
        } catch (IOException e9) {
            e9.printStackTrace();
        }
        return str3;
    }

    public Bitmap zoomBitmap(Bitmap bitmap, float f2, float f3) {
        Matrix matrix = new Matrix();
        matrix.postScale(f2, f3);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public byte[] intToBytes4L(long j2) {
        return new byte[]{(byte) ((j2 >> 24) & 255), (byte) ((j2 >> 16) & 255), (byte) ((j2 >> 8) & 255), (byte) (j2 & 255)};
    }

    public byte[] picReserved(int i2, int i3, int i4) {
        return new byte[]{0, 0, 0, 0, (byte) i2, (byte) i3, (byte) i4};
    }

    public Bitmap roundedCornerBitmap(Bitmap bitmap, float f2, float f3) {
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            canvas.drawRoundRect(rectF, f2, f3, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), rect, paint);
            return bitmapCreateBitmap;
        } catch (Exception unused) {
            return bitmap;
        }
    }

    public final RgbAarrayInfo utendo(byte[] bArr, boolean z2) {
        int i2;
        int i3;
        char c2;
        ArrayList arrayList;
        int i4;
        boolean z3;
        RgbAarrayInfo rgbAarrayInfo;
        boolean z4;
        int i5;
        int iBytes4HLExchangeInt;
        int iBytes4HLExchangeInt2;
        RgbAarrayInfo rgbAarrayInfo2 = new RgbAarrayInfo();
        LogD(" 解析bin得出readBinStringData.length =" + bArr.length);
        int i6 = 0;
        int i7 = 0;
        while (true) {
            i2 = 24;
            i3 = 8;
            if (i7 >= bArr.length / 24) {
                i7 = 0;
                break;
            }
            int i8 = i7 * 24;
            if (((bArr[i8 + 1] & 255) | ((bArr[i8] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) == 0 && i7 > 1) {
                LogD("跳出循环 count =" + i7);
                break;
            }
            i7++;
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        int i9 = 0;
        int i10 = 0;
        while (i9 < i7) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i9 * 24, bArr2, i6, i2);
            if (i9 == 0) {
                rgbAarrayInfo2.setWatchConfig(bArr2);
                WatchConfigInfo watchConfigInfo = new WatchConfigInfo();
                System.arraycopy(bArr2, i6, watchConfigInfo.getSnNo(), i6, watchConfigInfo.getSnNo().length);
                System.arraycopy(bArr2, 4, watchConfigInfo.getFileSize(), i6, watchConfigInfo.getFileSize().length);
                System.arraycopy(bArr2, i3, watchConfigInfo.getFileCrc(), i6, watchConfigInfo.getFileCrc().length);
                System.arraycopy(bArr2, 12, watchConfigInfo.getPixelWidth(), i6, watchConfigInfo.getPixelWidth().length);
                System.arraycopy(bArr2, 14, watchConfigInfo.getPixelHeight(), i6, watchConfigInfo.getPixelHeight().length);
                System.arraycopy(bArr2, 16, watchConfigInfo.getScreenType(), i6, watchConfigInfo.getScreenType().length);
                System.arraycopy(bArr2, 17, watchConfigInfo.getHasBg(), i6, watchConfigInfo.getHasBg().length);
                System.arraycopy(bArr2, 18, watchConfigInfo.getIsWatchVaild(), i6, watchConfigInfo.getIsWatchVaild().length);
                System.arraycopy(bArr2, 19, watchConfigInfo.getReserved(), i6, watchConfigInfo.getReserved().length);
                rgbAarrayInfo2.setWatchConfigInfo(watchConfigInfo);
                rgbAarrayInfo = rgbAarrayInfo2;
                i4 = i7;
                arrayList = arrayList2;
                z3 = true;
                c2 = 65280;
                i5 = i6;
            } else {
                PicTypeConfigInfo picTypeConfigInfo = new PicTypeConfigInfo();
                System.arraycopy(bArr2, i6, picTypeConfigInfo.getType(), i6, picTypeConfigInfo.getType().length);
                System.arraycopy(bArr2, 2, picTypeConfigInfo.getPicWidth(), i6, picTypeConfigInfo.getPicWidth().length);
                System.arraycopy(bArr2, 4, picTypeConfigInfo.getPicStartAddress(), i6, picTypeConfigInfo.getPicStartAddress().length);
                System.arraycopy(bArr2, 8, picTypeConfigInfo.getPicHeight(), i6, picTypeConfigInfo.getPicHeight().length);
                System.arraycopy(bArr2, 10, picTypeConfigInfo.getX(), i6, picTypeConfigInfo.getX().length);
                System.arraycopy(bArr2, 12, picTypeConfigInfo.getY(), i6, picTypeConfigInfo.getY().length);
                System.arraycopy(bArr2, 14, picTypeConfigInfo.getAnimationTime(), i6, picTypeConfigInfo.getAnimationTime().length);
                System.arraycopy(bArr2, 16, picTypeConfigInfo.getAnimaitonCnt(), i6, picTypeConfigInfo.getAnimaitonCnt().length);
                System.arraycopy(bArr2, 17, picTypeConfigInfo.getReserved(), i6, picTypeConfigInfo.getReserved().length);
                arrayList4.add(picTypeConfigInfo);
                arrayList2.add(new PicTypeInfo(bytes2HLExchangeInt(picTypeConfigInfo.getType()), bArr2));
                c2 = 65280;
                int i11 = (bArr2[2] & 255) | ((bArr2[3] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
                int i12 = (bArr2[8] & 255) | ((bArr2[9] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
                int i13 = bArr2[16] & 255;
                int i14 = ((bArr2[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr2[0] & 255);
                boolean z5 = z2 && (i14 < 1 || i14 > 3) && ((i14 < 35 || i14 > 37) && ((i14 < 38 || i14 > 42) && ((i14 < 43 || i14 > 46) && ((i14 < 52 || i14 > 56) && (i14 < 57 || i14 > 70)))));
                int iBytes4HLExchangeInt3 = bytes4HLExchangeInt(picTypeConfigInfo.getPicStartAddress());
                arrayList = arrayList2;
                if (i9 == 1) {
                    int i15 = (i7 * 24) + 2;
                    if (z5) {
                        int i16 = 0;
                        iBytes4HLExchangeInt2 = 0;
                        while (i16 < i13) {
                            byte[] bArr3 = new byte[4];
                            System.arraycopy(bArr, i15 + iBytes4HLExchangeInt2, bArr3, 0, 4);
                            iBytes4HLExchangeInt2 = i16 == 0 ? bytes4HLExchangeInt(bArr3) + 4 : iBytes4HLExchangeInt2 + bytes4HLExchangeInt(bArr3) + 4;
                            i16++;
                        }
                    } else {
                        iBytes4HLExchangeInt2 = i11 * i12 * 2 * i13;
                    }
                    byte[] bArr4 = new byte[iBytes4HLExchangeInt2];
                    System.arraycopy(bArr, i15, bArr4, 0, iBytes4HLExchangeInt2);
                    i10 = i15 + iBytes4HLExchangeInt2;
                    arrayList3.add(new PicDataInfo(bytes2HLExchangeInt(picTypeConfigInfo.getType()), bArr4));
                    rgbAarrayInfo = rgbAarrayInfo2;
                    i4 = i7;
                    i5 = 0;
                    z3 = true;
                } else {
                    int i17 = 0;
                    while (true) {
                        i4 = i7;
                        z3 = true;
                        if (i17 >= arrayList4.size() - 1) {
                            rgbAarrayInfo = rgbAarrayInfo2;
                            z4 = false;
                            break;
                        }
                        rgbAarrayInfo = rgbAarrayInfo2;
                        if (Arrays.equals(picTypeConfigInfo.getPicStartAddress(), arrayList4.get(i17).getPicStartAddress())) {
                            arrayList3.add(arrayList3.get(i17));
                            LogD("判断地址是否相同，相同不处理 " + i10 + "," + iBytes4HLExchangeInt3 + ",compressPicType =" + i14 + ",k =" + i17);
                            z4 = true;
                            break;
                        }
                        i17++;
                        i7 = i4;
                        rgbAarrayInfo2 = rgbAarrayInfo;
                    }
                    LogD("isSameAddr = " + z4);
                    if (z4) {
                        i5 = 0;
                    } else {
                        if (z5) {
                            int i18 = 0;
                            iBytes4HLExchangeInt = 0;
                            while (i18 < i13) {
                                byte[] bArr5 = new byte[4];
                                System.arraycopy(bArr, i10 + iBytes4HLExchangeInt, bArr5, 0, 4);
                                iBytes4HLExchangeInt = i18 == 0 ? bytes4HLExchangeInt(bArr5) + 4 : iBytes4HLExchangeInt + bytes4HLExchangeInt(bArr5) + 4;
                                i18++;
                            }
                        } else {
                            iBytes4HLExchangeInt = i11 * i12 * 2 * i13;
                        }
                        byte[] bArr6 = new byte[iBytes4HLExchangeInt];
                        i5 = 0;
                        System.arraycopy(bArr, i10, bArr6, 0, iBytes4HLExchangeInt);
                        i10 += iBytes4HLExchangeInt;
                        arrayList3.add(new PicDataInfo(bytes2HLExchangeInt(picTypeConfigInfo.getType()), bArr6));
                    }
                }
            }
            i9++;
            i6 = i5;
            arrayList2 = arrayList;
            i7 = i4;
            rgbAarrayInfo2 = rgbAarrayInfo;
            i2 = 24;
            i3 = 8;
        }
        rgbAarrayInfo2.setPicTypeConfigList(arrayList4);
        rgbAarrayInfo2.setPicTypeConfig(arrayList2);
        rgbAarrayInfo2.setPicDataConfig(arrayList3);
        return rgbAarrayInfo2;
    }

    public final byte[] utenif(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public final int utendo(byte[] bArr) {
        return ((bArr[1] & 255) << 8) | (bArr[0] & 255);
    }

    public final byte[] utendo(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(bArr, 0, bArr3, bArr2.length, bArr.length);
        return bArr3;
    }

    public final AssetManager utendo() {
        return UteBleClient.getContext().getResources().getAssets();
    }

    public final Bitmap utendo(Bitmap bitmap, Bitmap bitmap2, float f2, float f3) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(bitmap, new Matrix(), null);
        canvas.drawBitmap(bitmap2, f2, f3, (Paint) null);
        return bitmapCreateBitmap;
    }

    public final List utendo(List list) {
        for (int i2 = 0; i2 < list.size() - 1; i2++) {
            for (int size = list.size() - 1; size > i2; size--) {
                if (Arrays.equals(((PicDataInfo) list.get(size)).getPicData(), ((PicDataInfo) list.get(i2)).getPicData())) {
                    list.remove(size);
                }
            }
        }
        return list;
    }
}
