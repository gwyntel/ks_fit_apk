package androidx.media3.common.util;

import aisble.error.GattError;
import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.security.NetworkSecurityPolicy;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.SparseArray;
import android.util.SparseLongArray;
import android.view.Display;
import android.view.WindowManager;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.DoNotInline;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaLibraryInfo;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.ParserException;
import androidx.media3.common.Player;
import androidx.media3.common.audio.AudioProcessor;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.facebook.share.internal.ShareConstants;
import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;
import com.google.common.math.DoubleMath;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedBytes;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.luck.picture.lib.permissions.PermissionConfig;
import com.taobao.accs.AccsClientConfig;
import com.xiaomi.mipush.sdk.Constants;
import com.yc.utesdk.ble.close.KeyType;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* loaded from: classes.dex */
public final class Util {
    private static final int[] CRC16_BYTES_MSBF;
    private static final int[] CRC32_BYTES_MSBF;
    private static final int[] CRC8_BYTES_MSBF;

    @UnstableApi
    public static final String DEVICE;

    @UnstableApi
    public static final String DEVICE_DEBUG_INFO;

    @UnstableApi
    public static final byte[] EMPTY_BYTE_ARRAY;

    @UnstableApi
    public static final long[] EMPTY_LONG_ARRAY;
    private static final Pattern ESCAPED_CHARACTER_PATTERN;
    private static final String ISM_DASH_FORMAT_EXTENSION = "format=mpd-time-csf";
    private static final String ISM_HLS_FORMAT_EXTENSION = "format=m3u8-aapl";
    private static final Pattern ISM_PATH_PATTERN;

    @UnstableApi
    public static final String MANUFACTURER;

    @UnstableApi
    public static final String MODEL;

    @UnstableApi
    public static final int SDK_INT;
    private static final String TAG = "Util";
    private static final Pattern XS_DATE_TIME_PATTERN;
    private static final Pattern XS_DURATION_PATTERN;
    private static final String[] additionalIsoLanguageReplacements;
    private static final String[] isoLegacyTagReplacements;

    @Nullable
    private static HashMap<String, String> languageTagReplacementMap;

    @RequiresApi(21)
    private static final class Api21 {
        private Api21() {
        }

        @DoNotInline
        public static Drawable getDrawable(Context context, Resources resources, @DrawableRes int i2) {
            return resources.getDrawable(i2, context.getTheme());
        }
    }

    @RequiresApi(29)
    private static class Api29 {
        private Api29() {
        }

        @DoNotInline
        public static void startForeground(Service service, int i2, Notification notification, int i3, String str) {
            try {
                service.startForeground(i2, notification, i3);
            } catch (RuntimeException e2) {
                Log.e(Util.TAG, "The service must be declared with a foregroundServiceType that includes " + str);
                throw e2;
            }
        }
    }

    static {
        int i2 = Build.VERSION.SDK_INT;
        SDK_INT = i2;
        String str = Build.DEVICE;
        DEVICE = str;
        String str2 = Build.MANUFACTURER;
        MANUFACTURER = str2;
        String str3 = Build.MODEL;
        MODEL = str3;
        DEVICE_DEBUG_INFO = str + ", " + str3 + ", " + str2 + ", " + i2;
        EMPTY_BYTE_ARRAY = new byte[0];
        EMPTY_LONG_ARRAY = new long[0];
        XS_DATE_TIME_PATTERN = Pattern.compile("(\\d\\d\\d\\d)\\-(\\d\\d)\\-(\\d\\d)[Tt](\\d\\d):(\\d\\d):(\\d\\d)([\\.,](\\d+))?([Zz]|((\\+|\\-)(\\d?\\d):?(\\d\\d)))?");
        XS_DURATION_PATTERN = Pattern.compile("^(-)?P(([0-9]*)Y)?(([0-9]*)M)?(([0-9]*)D)?(T(([0-9]*)H)?(([0-9]*)M)?(([0-9.]*)S)?)?$");
        ESCAPED_CHARACTER_PATTERN = Pattern.compile("%([A-Fa-f0-9]{2})");
        ISM_PATH_PATTERN = Pattern.compile("(?:.*\\.)?isml?(?:/(manifest(.*))?)?", 2);
        additionalIsoLanguageReplacements = new String[]{"alb", "sq", "arm", "hy", "baq", "eu", "bur", "my", "tib", "bo", "chi", "zh", "cze", "cs", "dut", "nl", "ger", "de", "gre", "el", "fre", "fr", "geo", "ka", "ice", "is", AlinkConstants.KEY_MAC, "mk", "mao", "mi", "may", "ms", "per", "fa", "rum", "ro", "scc", "hbs-srp", "slo", "sk", "wel", "cy", "id", "ms-ind", "iw", "he", "heb", "he", "ji", "yi", "arb", "ar-arb", "in", "ms-ind", "ind", "ms-ind", "nb", "no-nob", "nob", "no-nob", "nn", "no-nno", "nno", "no-nno", "tw", "ak-twi", "twi", "ak-twi", "bs", "hbs-bos", "bos", "hbs-bos", "hr", "hbs-hrv", "hrv", "hbs-hrv", "sr", "hbs-srp", "srp", "hbs-srp", "cmn", "zh-cmn", "hak", "zh-hak", "nan", "zh-nan", "hsn", "zh-hsn"};
        isoLegacyTagReplacements = new String[]{"i-lux", "lb", "i-hak", "zh-hak", "i-navajo", "nv", "no-bok", "no-nob", "no-nyn", "no-nno", "zh-guoyu", "zh-cmn", "zh-hakka", "zh-hak", "zh-min-nan", "zh-nan", "zh-xiang", "zh-hsn"};
        CRC32_BYTES_MSBF = new int[]{0, 79764919, 159529838, 222504665, 319059676, 398814059, 445009330, 507990021, 638119352, 583659535, 797628118, 726387553, 890018660, 835552979, 1015980042, 944750013, 1276238704, 1221641927, 1167319070, 1095957929, 1595256236, 1540665371, 1452775106, 1381403509, 1780037320, 1859660671, 1671105958, 1733955601, 2031960084, 2111593891, 1889500026, 1952343757, -1742489888, -1662866601, -1851683442, -1788833735, -1960329156, -1880695413, -2103051438, -2040207643, -1104454824, -1159051537, -1213636554, -1284997759, -1389417084, -1444007885, -1532160278, -1603531939, -734892656, -789352409, -575645954, -646886583, -952755380, -1007220997, -827056094, -898286187, -231047128, -151282273, -71779514, -8804623, -515967244, -436212925, -390279782, -327299027, 881225847, 809987520, 1023691545, 969234094, 662832811, 591600412, 771767749, 717299826, 311336399, 374308984, 453813921, 533576470, 25881363, 88864420, 134795389, 214552010, 2023205639, 2086057648, 1897238633, 1976864222, 1804852699, 1867694188, 1645340341, 1724971778, 1587496639, 1516133128, 1461550545, 1406951526, 1302016099, 1230646740, 1142491917, 1087903418, -1398421865, -1469785312, -1524105735, -1578704818, -1079922613, -1151291908, -1239184603, -1293773166, -1968362705, -1905510760, -2094067647, -2014441994, -1716953613, -1654112188, -1876203875, -1796572374, -525066777, -462094256, -382327159, -302564546, -206542021, -143559028, -97365931, -17609246, -960696225, -1031934488, -817968335, -872425850, -709327229, -780559564, -600130067, -654598054, 1762451694, 1842216281, 1619975040, 1682949687, 2047383090, 2127137669, 1938468188, 2001449195, 1325665622, 1271206113, 1183200824, 1111960463, 1543535498, 1489069629, 1434599652, 1363369299, 622672798, 568075817, 748617968, 677256519, 907627842, 853037301, 1067152940, 995781531, 51762726, 131386257, 177728840, 240578815, 269590778, 349224269, 429104020, 491947555, -248556018, -168932423, -122852000, -60002089, -500490030, -420856475, -341238852, -278395381, -685261898, -739858943, -559578920, -630940305, -1004286614, -1058877219, -845023740, -916395085, -1119974018, -1174433591, -1262701040, -1333941337, -1371866206, -1426332139, -1481064244, -1552294533, -1690935098, -1611170447, -1833673816, -1770699233, -2009983462, -1930228819, -2119160460, -2056179517, 1569362073, 1498123566, 1409854455, 1355396672, 1317987909, 1246755826, 1192025387, 1137557660, 2072149281, 2135122070, 1912620623, 1992383480, 1753615357, 1816598090, 1627664531, 1707420964, 295390185, 358241886, 404320391, 483945776, 43990325, 106832002, 186451547, 266083308, 932423249, 861060070, 1041341759, 986742920, 613929101, 542559546, 756411363, 701822548, -978770311, -1050133554, -869589737, -924188512, -693284699, -764654318, -550540341, -605129092, -475935807, -413084042, -366743377, -287118056, -257573603, -194731862, -114850189, -35218492, -1984365303, -1921392450, -2143631769, -2063868976, -1698919467, -1635936670, -1824608069, -1744851700, -1347415887, -1418654458, -1506661409, -1561119128, -1129027987, -1200260134, -1254728445, -1309196108};
        CRC16_BYTES_MSBF = new int[]{0, 4129, 8258, 12387, 16516, 20645, 24774, 28903, 33032, 37161, 41290, 45419, 49548, 53677, 57806, 61935};
        CRC8_BYTES_MSBF = new int[]{0, 7, 14, 9, 28, 27, 18, 21, 56, 63, 54, 49, 36, 35, 42, 45, 112, 119, 126, 121, 108, 107, 98, 101, 72, 79, 70, 65, 84, 83, 90, 93, 224, 231, 238, 233, 252, 251, 242, 245, 216, 223, 214, 209, 196, 195, 202, 205, 144, 151, 158, 153, 140, 139, 130, 133, 168, 175, 166, 161, 180, 179, 186, 189, 199, 192, 201, 206, 219, 220, 213, 210, 255, KeyType.SET_COMMON_RATE_TEST_INTERVAL, 241, 246, 227, 228, 237, 234, 183, 176, 185, 190, 171, 172, 165, 162, 143, 136, 129, 134, 147, 148, 157, 154, 39, 32, 41, 46, 59, 60, 53, 50, 31, 24, 17, 22, 3, 4, 13, 10, 87, 80, 89, 94, 75, 76, 69, 66, 111, 104, 97, 102, 115, 116, 125, 122, 137, 142, 135, 128, 149, 146, 155, 156, 177, 182, 191, 184, 173, 170, 163, 164, 249, 254, 247, 240, 229, 226, 235, 236, 193, 198, 207, 200, 221, 218, 211, 212, 105, 110, 103, 96, 117, 114, 123, 124, 81, 86, 95, 88, 77, 74, 67, 68, 25, 30, 23, 16, 5, 2, 11, 12, 33, 38, 47, 40, 61, 58, 51, 52, 78, 73, 64, 71, 82, 85, 92, 91, 118, 113, 120, 127, 106, 109, 100, 99, 62, 57, 48, 55, 34, 37, 44, 43, 6, 1, 8, 15, 26, 29, 20, 19, 174, 169, 160, 167, 178, 181, 188, 187, 150, 145, 152, 159, 138, 141, 132, 131, 222, 217, 208, 215, 194, 197, 204, 203, 230, 225, 232, 239, 250, GattError.GATT_CCCD_CFG_ERROR, 244, 243};
    }

    private Util() {
    }

    @UnstableApi
    public static long addWithOverflowDefault(long j2, long j3, long j4) {
        long j5 = j2 + j3;
        return ((j2 ^ j5) & (j3 ^ j5)) < 0 ? j4 : j5;
    }

    @UnstableApi
    public static boolean areEqual(@Nullable Object obj, @Nullable Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    @UnstableApi
    public static int binarySearchCeil(int[] iArr, int i2, boolean z2, boolean z3) {
        int i3;
        int i4;
        int iBinarySearch = Arrays.binarySearch(iArr, i2);
        if (iBinarySearch < 0) {
            i4 = ~iBinarySearch;
        } else {
            while (true) {
                i3 = iBinarySearch + 1;
                if (i3 >= iArr.length || iArr[i3] != i2) {
                    break;
                }
                iBinarySearch = i3;
            }
            i4 = z2 ? iBinarySearch : i3;
        }
        return z3 ? Math.min(iArr.length - 1, i4) : i4;
    }

    @UnstableApi
    public static int binarySearchFloor(int[] iArr, int i2, boolean z2, boolean z3) {
        int i3;
        int i4;
        int iBinarySearch = Arrays.binarySearch(iArr, i2);
        if (iBinarySearch < 0) {
            i4 = -(iBinarySearch + 2);
        } else {
            while (true) {
                i3 = iBinarySearch - 1;
                if (i3 < 0 || iArr[i3] != i2) {
                    break;
                }
                iBinarySearch = i3;
            }
            i4 = z2 ? iBinarySearch : i3;
        }
        return z3 ? Math.max(0, i4) : i4;
    }

    @EnsuresNonNull({"#1"})
    @UnstableApi
    public static <T> T castNonNull(@Nullable T t2) {
        return t2;
    }

    @EnsuresNonNull({"#1"})
    @UnstableApi
    public static <T> T[] castNonNullTypeArray(T[] tArr) {
        return tArr;
    }

    @UnstableApi
    public static int ceilDivide(int i2, int i3) {
        return ((i2 + i3) - 1) / i3;
    }

    public static boolean checkCleartextTrafficPermitted(MediaItem... mediaItemArr) {
        if (SDK_INT < 24) {
            return true;
        }
        for (MediaItem mediaItem : mediaItemArr) {
            MediaItem.LocalConfiguration localConfiguration = mediaItem.localConfiguration;
            if (localConfiguration != null) {
                if (isTrafficRestricted(localConfiguration.uri)) {
                    return false;
                }
                for (int i2 = 0; i2 < mediaItem.localConfiguration.subtitleConfigurations.size(); i2++) {
                    if (isTrafficRestricted(mediaItem.localConfiguration.subtitleConfigurations.get(i2).uri)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @UnstableApi
    public static void closeQuietly(@Nullable Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    @UnstableApi
    public static int compareLong(long j2, long j3) {
        if (j2 < j3) {
            return -1;
        }
        return j2 == j3 ? 0 : 1;
    }

    @UnstableApi
    public static int constrainValue(int i2, int i3, int i4) {
        return Math.max(i3, Math.min(i2, i4));
    }

    @UnstableApi
    public static boolean contains(Object[] objArr, @Nullable Object obj) {
        for (Object obj2 : objArr) {
            if (areEqual(obj2, obj)) {
                return true;
            }
        }
        return false;
    }

    @UnstableApi
    public static <T> boolean contentEquals(@Nullable SparseArray<T> sparseArray, @Nullable SparseArray<T> sparseArray2) {
        if (sparseArray == null) {
            return sparseArray2 == null;
        }
        if (sparseArray2 == null) {
            return false;
        }
        if (SDK_INT >= 31) {
            return sparseArray.contentEquals(sparseArray2);
        }
        int size = sparseArray.size();
        if (size != sparseArray2.size()) {
            return false;
        }
        for (int i2 = 0; i2 < size; i2++) {
            if (!Objects.equals(sparseArray.valueAt(i2), sparseArray2.get(sparseArray.keyAt(i2)))) {
                return false;
            }
        }
        return true;
    }

    @UnstableApi
    public static <T> int contentHashCode(SparseArray<T> sparseArray) {
        if (SDK_INT >= 31) {
            return sparseArray.contentHashCode();
        }
        int iKeyAt = 17;
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            iKeyAt = (((iKeyAt * 31) + sparseArray.keyAt(i2)) * 31) + Objects.hashCode(sparseArray.valueAt(i2));
        }
        return iKeyAt;
    }

    @UnstableApi
    public static int crc16(byte[] bArr, int i2, int i3, int i4) {
        while (i2 < i3) {
            int i5 = UnsignedBytes.toInt(bArr[i2]);
            i4 = crc16UpdateFourBits(i5 & 15, crc16UpdateFourBits(i5 >> 4, i4));
            i2++;
        }
        return i4;
    }

    private static int crc16UpdateFourBits(int i2, int i3) {
        return (CRC16_BYTES_MSBF[(i2 ^ ((i3 >> 12) & 255)) & 255] ^ ((i3 << 4) & 65535)) & 65535;
    }

    @UnstableApi
    public static int crc32(byte[] bArr, int i2, int i3, int i4) {
        while (i2 < i3) {
            i4 = CRC32_BYTES_MSBF[((i4 >>> 24) ^ (bArr[i2] & 255)) & 255] ^ (i4 << 8);
            i2++;
        }
        return i4;
    }

    @UnstableApi
    public static int crc8(byte[] bArr, int i2, int i3, int i4) {
        while (i2 < i3) {
            i4 = CRC8_BYTES_MSBF[i4 ^ (bArr[i2] & 255)];
            i2++;
        }
        return i4;
    }

    @UnstableApi
    public static Handler createHandler(Looper looper, @Nullable Handler.Callback callback) {
        return new Handler(looper, callback);
    }

    @UnstableApi
    public static Handler createHandlerForCurrentLooper() {
        return createHandlerForCurrentLooper(null);
    }

    @UnstableApi
    public static Handler createHandlerForCurrentOrMainLooper() {
        return createHandlerForCurrentOrMainLooper(null);
    }

    private static HashMap<String, String> createIsoLanguageReplacementMap() throws MissingResourceException {
        String[] iSOLanguages = Locale.getISOLanguages();
        HashMap<String, String> map = new HashMap<>(iSOLanguages.length + additionalIsoLanguageReplacements.length);
        int i2 = 0;
        for (String str : iSOLanguages) {
            try {
                String iSO3Language = new Locale(str).getISO3Language();
                if (!TextUtils.isEmpty(iSO3Language)) {
                    map.put(iSO3Language, str);
                }
            } catch (MissingResourceException unused) {
            }
        }
        while (true) {
            String[] strArr = additionalIsoLanguageReplacements;
            if (i2 >= strArr.length) {
                return map;
            }
            map.put(strArr[i2], strArr[i2 + 1]);
            i2 += 2;
        }
    }

    @UnstableApi
    public static ByteBuffer createReadOnlyByteBuffer(ByteBuffer byteBuffer) {
        return byteBuffer.asReadOnlyBuffer().order(byteBuffer.order());
    }

    @UnstableApi
    public static File createTempDirectory(Context context, String str) throws IOException {
        File fileCreateTempFile = createTempFile(context, str);
        fileCreateTempFile.delete();
        fileCreateTempFile.mkdir();
        return fileCreateTempFile;
    }

    @UnstableApi
    public static File createTempFile(Context context, String str) throws IOException {
        return File.createTempFile(str, null, (File) Assertions.checkNotNull(context.getCacheDir()));
    }

    @UnstableApi
    public static long durationUsToSampleCount(long j2, int i2) {
        return scaleLargeValue(j2, i2, 1000000L, RoundingMode.CEILING);
    }

    @UnstableApi
    public static String escapeFileName(String str) {
        int length = str.length();
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            if (shouldEscapeCharacter(str.charAt(i4))) {
                i3++;
            }
        }
        if (i3 == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder((i3 * 2) + length);
        while (i3 > 0) {
            int i5 = i2 + 1;
            char cCharAt = str.charAt(i2);
            if (shouldEscapeCharacter(cCharAt)) {
                sb.append('%');
                sb.append(Integer.toHexString(cCharAt));
                i3--;
            } else {
                sb.append(cCharAt);
            }
            i2 = i5;
        }
        if (i2 < length) {
            sb.append((CharSequence) str, i2, length);
        }
        return sb.toString();
    }

    @UnstableApi
    public static Uri fixSmoothStreamingIsmManifestUri(Uri uri) {
        String path = uri.getPath();
        if (path == null) {
            return uri;
        }
        Matcher matcher = ISM_PATH_PATTERN.matcher(path);
        return (matcher.matches() && matcher.group(1) == null) ? Uri.withAppendedPath(uri, "Manifest") : uri;
    }

    @UnstableApi
    public static String formatInvariant(String str, Object... objArr) {
        return String.format(Locale.US, str, objArr);
    }

    @UnstableApi
    public static String fromUtf8Bytes(byte[] bArr) {
        return new String(bArr, Charsets.UTF_8);
    }

    @RequiresApi(21)
    @UnstableApi
    public static int generateAudioSessionIdV21(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        if (audioManager == null) {
            return -1;
        }
        return audioManager.generateAudioSessionId();
    }

    @Nullable
    public static String getAdaptiveMimeTypeForContentType(int i2) {
        if (i2 == 0) {
            return MimeTypes.APPLICATION_MPD;
        }
        if (i2 == 1) {
            return MimeTypes.APPLICATION_SS;
        }
        if (i2 != 2) {
            return null;
        }
        return MimeTypes.APPLICATION_M3U8;
    }

    @UnstableApi
    public static int getApiLevelThatAudioFormatIntroducedAudioEncoding(int i2) {
        if (i2 == 20) {
            return 30;
        }
        if (i2 == 22) {
            return 31;
        }
        if (i2 == 30) {
            return 34;
        }
        switch (i2) {
            case 2:
            case 3:
                return 3;
            case 4:
            case 5:
            case 6:
                return 21;
            case 7:
            case 8:
                return 23;
            case 9:
            case 10:
            case 11:
            case 12:
                return 28;
            default:
                switch (i2) {
                    case 14:
                        return 25;
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                        return 28;
                    default:
                        return Integer.MAX_VALUE;
                }
        }
    }

    @UnstableApi
    @Deprecated
    public static int getAudioContentTypeForStreamType(int i2) {
        if (i2 != 0) {
            return (i2 == 1 || i2 == 2 || i2 == 4 || i2 == 5 || i2 == 8) ? 4 : 2;
        }
        return 1;
    }

    @RequiresApi(21)
    @UnstableApi
    public static AudioFormat getAudioFormat(int i2, int i3, int i4) {
        return new AudioFormat.Builder().setSampleRate(i2).setChannelMask(i3).setEncoding(i4).build();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0015 A[RETURN] */
    @android.annotation.SuppressLint({"InlinedApi"})
    @androidx.media3.common.util.UnstableApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getAudioTrackChannelConfig(int r2) {
        /*
            r0 = 6396(0x18fc, float:8.963E-42)
            switch(r2) {
                case 1: goto L28;
                case 2: goto L25;
                case 3: goto L22;
                case 4: goto L1f;
                case 5: goto L1c;
                case 6: goto L19;
                case 7: goto L16;
                case 8: goto L15;
                case 9: goto L5;
                case 10: goto Lb;
                case 11: goto L5;
                case 12: goto L7;
                default: goto L5;
            }
        L5:
            r2 = 0
            return r2
        L7:
            r2 = 743676(0xb58fc, float:1.042112E-39)
            return r2
        Lb:
            int r2 = androidx.media3.common.util.Util.SDK_INT
            r1 = 32
            if (r2 < r1) goto L15
            r2 = 737532(0xb40fc, float:1.033502E-39)
            return r2
        L15:
            return r0
        L16:
            r2 = 1276(0x4fc, float:1.788E-42)
            return r2
        L19:
            r2 = 252(0xfc, float:3.53E-43)
            return r2
        L1c:
            r2 = 220(0xdc, float:3.08E-43)
            return r2
        L1f:
            r2 = 204(0xcc, float:2.86E-43)
            return r2
        L22:
            r2 = 28
            return r2
        L25:
            r2 = 12
            return r2
        L28:
            r2 = 4
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.common.util.Util.getAudioTrackChannelConfig(int):int");
    }

    @UnstableApi
    public static int getAudioUsageForStreamType(int i2) {
        if (i2 == 0) {
            return 2;
        }
        if (i2 == 1) {
            return 13;
        }
        if (i2 == 2) {
            return 6;
        }
        int i3 = 4;
        if (i2 != 4) {
            i3 = 5;
            if (i2 != 5) {
                return i2 != 8 ? 1 : 3;
            }
        }
        return i3;
    }

    @UnstableApi
    public static Player.Commands getAvailableCommands(Player player, Player.Commands commands) {
        boolean zIsPlayingAd = player.isPlayingAd();
        boolean zIsCurrentMediaItemSeekable = player.isCurrentMediaItemSeekable();
        boolean zHasPreviousMediaItem = player.hasPreviousMediaItem();
        boolean zHasNextMediaItem = player.hasNextMediaItem();
        boolean zIsCurrentMediaItemLive = player.isCurrentMediaItemLive();
        boolean zIsCurrentMediaItemDynamic = player.isCurrentMediaItemDynamic();
        boolean zIsEmpty = player.getCurrentTimeline().isEmpty();
        boolean z2 = false;
        Player.Commands.Builder builderAddIf = new Player.Commands.Builder().addAll(commands).addIf(4, !zIsPlayingAd).addIf(5, zIsCurrentMediaItemSeekable && !zIsPlayingAd).addIf(6, zHasPreviousMediaItem && !zIsPlayingAd).addIf(7, !zIsEmpty && (zHasPreviousMediaItem || !zIsCurrentMediaItemLive || zIsCurrentMediaItemSeekable) && !zIsPlayingAd).addIf(8, zHasNextMediaItem && !zIsPlayingAd).addIf(9, !zIsEmpty && (zHasNextMediaItem || (zIsCurrentMediaItemLive && zIsCurrentMediaItemDynamic)) && !zIsPlayingAd).addIf(10, !zIsPlayingAd).addIf(11, zIsCurrentMediaItemSeekable && !zIsPlayingAd);
        if (zIsCurrentMediaItemSeekable && !zIsPlayingAd) {
            z2 = true;
        }
        return builderAddIf.addIf(12, z2).build();
    }

    @UnstableApi
    public static int getBigEndianInt(ByteBuffer byteBuffer, int i2) {
        int i3 = byteBuffer.getInt(i2);
        return byteBuffer.order() == ByteOrder.BIG_ENDIAN ? i3 : Integer.reverseBytes(i3);
    }

    @UnstableApi
    public static byte[] getBytesFromHexString(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) ((Character.digit(str.charAt(i3), 16) << 4) + Character.digit(str.charAt(i3 + 1), 16));
        }
        return bArr;
    }

    @UnstableApi
    public static int getCodecCountOfType(@Nullable String str, int i2) {
        int i3 = 0;
        for (String str2 : splitCodecs(str)) {
            if (i2 == MimeTypes.getTrackTypeOfCodec(str2)) {
                i3++;
            }
        }
        return i3;
    }

    @Nullable
    @UnstableApi
    public static String getCodecsOfType(@Nullable String str, int i2) {
        String[] strArrSplitCodecs = splitCodecs(str);
        if (strArrSplitCodecs.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String str2 : strArrSplitCodecs) {
            if (i2 == MimeTypes.getTrackTypeOfCodec(str2)) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(str2);
            }
        }
        if (sb.length() > 0) {
            return sb.toString();
        }
        return null;
    }

    @UnstableApi
    public static String getCountryCode(@Nullable Context context) {
        TelephonyManager telephonyManager;
        if (context != null && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null) {
            String networkCountryIso = telephonyManager.getNetworkCountryIso();
            if (!TextUtils.isEmpty(networkCountryIso)) {
                return Ascii.toUpperCase(networkCountryIso);
            }
        }
        return Ascii.toUpperCase(Locale.getDefault().getCountry());
    }

    @UnstableApi
    public static Point getCurrentDisplayModeSize(Context context) {
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        Display display = displayManager != null ? displayManager.getDisplay(0) : null;
        if (display == null) {
            display = ((WindowManager) Assertions.checkNotNull((WindowManager) context.getSystemService("window"))).getDefaultDisplay();
        }
        return getCurrentDisplayModeSize(context, display);
    }

    @UnstableApi
    public static Looper getCurrentOrMainLooper() {
        Looper looperMyLooper = Looper.myLooper();
        return looperMyLooper != null ? looperMyLooper : Looper.getMainLooper();
    }

    @UnstableApi
    public static Uri getDataUriForString(String str, String str2) {
        return Uri.parse("data:" + str + ";base64," + Base64.encodeToString(str2.getBytes(), 2));
    }

    @UnstableApi
    public static Locale getDefaultDisplayLocale() {
        return SDK_INT >= 24 ? Locale.getDefault(Locale.Category.DISPLAY) : Locale.getDefault();
    }

    @RequiresApi(23)
    private static void getDisplaySizeV23(Display display, Point point) {
        Display.Mode mode = display.getMode();
        point.x = mode.getPhysicalWidth();
        point.y = mode.getPhysicalHeight();
    }

    @UnstableApi
    public static Drawable getDrawable(Context context, Resources resources, @DrawableRes int i2) {
        return SDK_INT >= 21 ? Api21.getDrawable(context, resources, i2) : resources.getDrawable(i2);
    }

    @Nullable
    public static UUID getDrmUuid(String str) {
        String lowerCase = Ascii.toLowerCase(str);
        lowerCase.hashCode();
        switch (lowerCase) {
            case "playready":
                return C.PLAYREADY_UUID;
            case "widevine":
                return C.WIDEVINE_UUID;
            case "clearkey":
                return C.CLEARKEY_UUID;
            default:
                try {
                    return UUID.fromString(str);
                } catch (RuntimeException unused) {
                    return null;
                }
        }
    }

    @UnstableApi
    public static int getErrorCodeForMediaDrmErrorCode(int i2) {
        if (i2 == 2 || i2 == 4) {
            return 6005;
        }
        if (i2 == 10) {
            return 6004;
        }
        if (i2 == 7) {
            return 6005;
        }
        if (i2 == 8) {
            return 6003;
        }
        switch (i2) {
            case 15:
                return 6003;
            case 16:
            case 18:
                return 6005;
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
                return 6004;
            default:
                switch (i2) {
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                        return 6002;
                    default:
                        return 6006;
                }
        }
    }

    @UnstableApi
    public static int getErrorCodeFromPlatformDiagnosticsInfo(@Nullable String str) throws NumberFormatException {
        String[] strArrSplit;
        int length;
        if (str == null || (length = (strArrSplit = split(str, OpenAccountUIConstants.UNDER_LINE)).length) < 2) {
            return 0;
        }
        String str2 = strArrSplit[length - 1];
        boolean z2 = length >= 3 && "neg".equals(strArrSplit[length - 2]);
        try {
            int i2 = Integer.parseInt((String) Assertions.checkNotNull(str2));
            return z2 ? -i2 : i2;
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    @UnstableApi
    public static String getFormatSupportString(int i2) {
        if (i2 == 0) {
            return SdkConstant.CLOUDAPI_COMMAND_NOTIFY_RESPONSE;
        }
        if (i2 == 1) {
            return "NO_UNSUPPORTED_TYPE";
        }
        if (i2 == 2) {
            return "NO_UNSUPPORTED_DRM";
        }
        if (i2 == 3) {
            return "NO_EXCEEDS_CAPABILITIES";
        }
        if (i2 == 4) {
            return "YES";
        }
        throw new IllegalStateException();
    }

    @UnstableApi
    public static int getIntegerCodeForString(String str) {
        int length = str.length();
        Assertions.checkArgument(length <= 4);
        int iCharAt = 0;
        for (int i2 = 0; i2 < length; i2++) {
            iCharAt = (iCharAt << 8) | str.charAt(i2);
        }
        return iCharAt;
    }

    @UnstableApi
    public static String getLocaleLanguageTag(Locale locale) {
        return SDK_INT >= 21 ? getLocaleLanguageTagV21(locale) : locale.toString();
    }

    @RequiresApi(21)
    private static String getLocaleLanguageTagV21(Locale locale) {
        return locale.toLanguageTag();
    }

    @UnstableApi
    public static int getMaxPendingFramesCountForMediaCodecDecoders(Context context) {
        return isFrameDropAllowedOnSurfaceInput(context) ? 1 : 5;
    }

    @UnstableApi
    public static long getMediaDurationForPlayoutDuration(long j2, float f2) {
        return f2 == 1.0f ? j2 : Math.round(j2 * f2);
    }

    @UnstableApi
    public static long getNowUnixTimeMs(long j2) {
        return j2 == C.TIME_UNSET ? System.currentTimeMillis() : j2 + android.os.SystemClock.elapsedRealtime();
    }

    @UnstableApi
    public static int getPcmEncoding(int i2) {
        if (i2 == 8) {
            return 3;
        }
        if (i2 == 16) {
            return 2;
        }
        if (i2 != 24) {
            return i2 != 32 ? 0 : 22;
        }
        return 21;
    }

    @UnstableApi
    public static Format getPcmFormat(int i2, int i3, int i4) {
        return new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_RAW).setChannelCount(i3).setSampleRate(i4).setPcmEncoding(i2).build();
    }

    @UnstableApi
    public static int getPcmFrameSize(int i2, int i3) {
        if (i2 != 2) {
            if (i2 == 3) {
                return i3;
            }
            if (i2 != 4) {
                if (i2 != 21) {
                    if (i2 != 22) {
                        if (i2 != 268435456) {
                            if (i2 != 1342177280) {
                                if (i2 != 1610612736) {
                                    throw new IllegalArgumentException();
                                }
                            }
                        }
                    }
                }
                return i3 * 3;
            }
            return i3 * 4;
        }
        return i3 * 2;
    }

    @UnstableApi
    public static long getPlayoutDurationForMediaDuration(long j2, float f2) {
        return f2 == 1.0f ? j2 : Math.round(j2 / f2);
    }

    @UnstableApi
    public static List<String> getRoleFlagStrings(int i2) {
        ArrayList arrayList = new ArrayList();
        if ((i2 & 1) != 0) {
            arrayList.add("main");
        }
        if ((i2 & 2) != 0) {
            arrayList.add("alt");
        }
        if ((i2 & 4) != 0) {
            arrayList.add("supplementary");
        }
        if ((i2 & 8) != 0) {
            arrayList.add("commentary");
        }
        if ((i2 & 16) != 0) {
            arrayList.add("dub");
        }
        if ((i2 & 32) != 0) {
            arrayList.add("emergency");
        }
        if ((i2 & 64) != 0) {
            arrayList.add(ShareConstants.FEED_CAPTION_PARAM);
        }
        if ((i2 & 128) != 0) {
            arrayList.add(MessengerShareContentUtility.SUBTITLE);
        }
        if ((i2 & 256) != 0) {
            arrayList.add("sign");
        }
        if ((i2 & 512) != 0) {
            arrayList.add("describes-video");
        }
        if ((i2 & 1024) != 0) {
            arrayList.add("describes-music");
        }
        if ((i2 & 2048) != 0) {
            arrayList.add("enhanced-intelligibility");
        }
        if ((i2 & 4096) != 0) {
            arrayList.add("transcribes-dialog");
        }
        if ((i2 & 8192) != 0) {
            arrayList.add("easy-read");
        }
        if ((i2 & 16384) != 0) {
            arrayList.add("trick-play");
        }
        return arrayList;
    }

    @UnstableApi
    public static List<String> getSelectionFlagStrings(int i2) {
        ArrayList arrayList = new ArrayList();
        if ((i2 & 4) != 0) {
            arrayList.add("auto");
        }
        if ((i2 & 1) != 0) {
            arrayList.add(AccsClientConfig.DEFAULT_CONFIGTAG);
        }
        if ((i2 & 2) != 0) {
            arrayList.add("forced");
        }
        return arrayList;
    }

    @UnstableApi
    public static int getStreamTypeForAudioUsage(int i2) {
        if (i2 == 13) {
            return 1;
        }
        switch (i2) {
            case 2:
                return 0;
            case 3:
                return 8;
            case 4:
                return 4;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
                return 5;
            case 6:
                return 2;
            default:
                return 3;
        }
    }

    @UnstableApi
    public static String getStringForTime(StringBuilder sb, Formatter formatter, long j2) {
        long j3 = j2 == C.TIME_UNSET ? 0L : j2;
        String str = j3 < 0 ? Constants.ACCEPT_TIME_SEPARATOR_SERVER : "";
        long jAbs = (Math.abs(j3) + 500) / 1000;
        long j4 = jAbs % 60;
        long j5 = (jAbs / 60) % 60;
        long j6 = jAbs / 3600;
        sb.setLength(0);
        return j6 > 0 ? formatter.format("%s%d:%02d:%02d", str, Long.valueOf(j6), Long.valueOf(j5), Long.valueOf(j4)).toString() : formatter.format("%s%02d:%02d", str, Long.valueOf(j5), Long.valueOf(j4)).toString();
    }

    @UnstableApi
    public static String[] getSystemLanguageCodes() {
        String[] systemLocales = getSystemLocales();
        for (int i2 = 0; i2 < systemLocales.length; i2++) {
            systemLocales[i2] = normalizeLanguageCode(systemLocales[i2]);
        }
        return systemLocales;
    }

    private static String[] getSystemLocales() {
        Configuration configuration = Resources.getSystem().getConfiguration();
        return SDK_INT >= 24 ? getSystemLocalesV24(configuration) : new String[]{getLocaleLanguageTag(configuration.locale)};
    }

    @RequiresApi(24)
    private static String[] getSystemLocalesV24(Configuration configuration) {
        return split(configuration.getLocales().toLanguageTags(), ",");
    }

    @Nullable
    private static String getSystemProperty(String str) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class).invoke(cls, str);
        } catch (Exception e2) {
            Log.e(TAG, "Failed to read system property " + str, e2);
            return null;
        }
    }

    @UnstableApi
    public static String getTrackTypeString(int i2) {
        switch (i2) {
            case -2:
                return "none";
            case -1:
                return "unknown";
            case 0:
                return AccsClientConfig.DEFAULT_CONFIGTAG;
            case 1:
                return "audio";
            case 2:
                return "video";
            case 3:
                return "text";
            case 4:
                return "image";
            case 5:
                return TtmlNode.TAG_METADATA;
            case 6:
                return "camera motion";
            default:
                if (i2 < 10000) {
                    return "?";
                }
                return "custom (" + i2 + ")";
        }
    }

    @UnstableApi
    public static String getUserAgent(Context context, String str) {
        String str2;
        try {
            str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            str2 = "?";
        }
        return str + "/" + str2 + " (Linux;Android " + Build.VERSION.RELEASE + ") " + MediaLibraryInfo.VERSION_SLASHY;
    }

    @UnstableApi
    public static byte[] getUtf8Bytes(String str) {
        return str.getBytes(Charsets.UTF_8);
    }

    @UnstableApi
    public static byte[] gzip(byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            try {
                gZIPOutputStream.write(bArr);
                gZIPOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } finally {
            }
        } catch (IOException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public static boolean handlePauseButtonAction(@Nullable Player player) {
        if (player == null || !player.isCommandAvailable(1)) {
            return false;
        }
        player.pause();
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean handlePlayButtonAction(@androidx.annotation.Nullable androidx.media3.common.Player r4) {
        /*
            r0 = 0
            if (r4 != 0) goto L4
            return r0
        L4:
            int r1 = r4.getPlaybackState()
            r2 = 1
            if (r1 != r2) goto L17
            r3 = 2
            boolean r3 = r4.isCommandAvailable(r3)
            if (r3 == 0) goto L17
            r4.prepare()
        L15:
            r0 = r2
            goto L24
        L17:
            r3 = 4
            if (r1 != r3) goto L24
            boolean r1 = r4.isCommandAvailable(r3)
            if (r1 == 0) goto L24
            r4.seekToDefaultPosition()
            goto L15
        L24:
            boolean r1 = r4.isCommandAvailable(r2)
            if (r1 == 0) goto L2e
            r4.play()
            goto L2f
        L2e:
            r2 = r0
        L2f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.common.util.Util.handlePlayButtonAction(androidx.media3.common.Player):boolean");
    }

    public static boolean handlePlayPauseButtonAction(@Nullable Player player) {
        return handlePlayPauseButtonAction(player, true);
    }

    @UnstableApi
    @Deprecated
    public static int inferContentType(Uri uri, @Nullable String str) {
        return TextUtils.isEmpty(str) ? inferContentType(uri) : inferContentTypeForExtension(str);
    }

    public static int inferContentTypeForExtension(String str) {
        String lowerCase = Ascii.toLowerCase(str);
        lowerCase.hashCode();
        switch (lowerCase) {
            case "ism":
            case "isml":
                return 1;
            case "mpd":
                return 0;
            case "m3u8":
                return 2;
            default:
                return 4;
        }
    }

    public static int inferContentTypeForUriAndMimeType(Uri uri, @Nullable String str) {
        if (str == null) {
            return inferContentType(uri);
        }
        switch (str) {
            case "application/x-mpegURL":
                return 2;
            case "application/vnd.ms-sstr+xml":
                return 1;
            case "application/dash+xml":
                return 0;
            case "application/x-rtsp":
                return 3;
            default:
                return 4;
        }
    }

    @UnstableApi
    public static boolean inflate(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, @Nullable Inflater inflater) {
        if (parsableByteArray.bytesLeft() <= 0) {
            return false;
        }
        if (parsableByteArray2.capacity() < parsableByteArray.bytesLeft()) {
            parsableByteArray2.ensureCapacity(parsableByteArray.bytesLeft() * 2);
        }
        if (inflater == null) {
            inflater = new Inflater();
        }
        inflater.setInput(parsableByteArray.getData(), parsableByteArray.getPosition(), parsableByteArray.bytesLeft());
        int iInflate = 0;
        while (true) {
            try {
                iInflate += inflater.inflate(parsableByteArray2.getData(), iInflate, parsableByteArray2.capacity() - iInflate);
                if (!inflater.finished()) {
                    if (inflater.needsDictionary() || inflater.needsInput()) {
                        break;
                    }
                    if (iInflate == parsableByteArray2.capacity()) {
                        parsableByteArray2.ensureCapacity(parsableByteArray2.capacity() * 2);
                    }
                } else {
                    parsableByteArray2.setLimit(iInflate);
                    inflater.reset();
                    return true;
                }
            } catch (DataFormatException unused) {
                return false;
            } finally {
                inflater.reset();
            }
        }
        return false;
    }

    @UnstableApi
    public static String intToStringMaxRadix(int i2) {
        return Integer.toString(i2, 36);
    }

    private static boolean isAppSpecificStorageFileUri(Activity activity, Uri uri) throws IOException {
        try {
            String path = uri.getPath();
            if (path == null) {
                return false;
            }
            String canonicalPath = new File(path).getCanonicalPath();
            String canonicalPath2 = activity.getFilesDir().getCanonicalPath();
            String canonicalPath3 = null;
            File externalFilesDir = activity.getExternalFilesDir(null);
            if (externalFilesDir != null) {
                canonicalPath3 = externalFilesDir.getCanonicalPath();
            }
            if (!canonicalPath.startsWith(canonicalPath2)) {
                if (canonicalPath3 == null) {
                    return false;
                }
                if (!canonicalPath.startsWith(canonicalPath3)) {
                    return false;
                }
            }
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    @UnstableApi
    public static boolean isAutomotive(Context context) {
        return SDK_INT >= 23 && context.getPackageManager().hasSystemFeature("android.hardware.type.automotive");
    }

    @UnstableApi
    public static boolean isBitmapFactorySupportedMimeType(String str) {
        str.hashCode();
        switch (str) {
            case "image/avif":
                return SDK_INT >= 34;
            case "image/heic":
            case "image/heif":
                return SDK_INT >= 26;
            case "image/jpeg":
            case "image/webp":
            case "image/bmp":
            case "image/png":
                return true;
            default:
                return false;
        }
    }

    @UnstableApi
    public static boolean isEncodingHighResolutionPcm(int i2) {
        return i2 == 21 || i2 == 1342177280 || i2 == 22 || i2 == 1610612736 || i2 == 4;
    }

    @UnstableApi
    public static boolean isEncodingLinearPcm(int i2) {
        return i2 == 3 || i2 == 2 || i2 == 268435456 || i2 == 21 || i2 == 1342177280 || i2 == 22 || i2 == 1610612736 || i2 == 4;
    }

    @UnstableApi
    public static boolean isFrameDropAllowedOnSurfaceInput(Context context) {
        int i2 = SDK_INT;
        if (i2 >= 29 && context.getApplicationInfo().targetSdkVersion >= 29) {
            if (i2 == 30) {
                String str = MODEL;
                if (Ascii.equalsIgnoreCase(str, "moto g(20)") || Ascii.equalsIgnoreCase(str, "rmx3231")) {
                }
            }
            return false;
        }
        return true;
    }

    @UnstableApi
    public static boolean isLinebreak(int i2) {
        return i2 == 10 || i2 == 13;
    }

    @UnstableApi
    public static boolean isLocalFileUri(Uri uri) {
        String scheme = uri.getScheme();
        return TextUtils.isEmpty(scheme) || "file".equals(scheme);
    }

    private static boolean isMediaStoreExternalContentUri(Uri uri) {
        if (!"content".equals(uri.getScheme()) || !"media".equals(uri.getAuthority())) {
            return false;
        }
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.isEmpty()) {
            return false;
        }
        String str = pathSegments.get(0);
        return "external".equals(str) || "external_primary".equals(str);
    }

    @ChecksSdkIntAtLeast(api = 23)
    private static boolean isReadStoragePermissionRequestNeeded(Activity activity, Uri uri) {
        if (SDK_INT < 23) {
            return false;
        }
        return isLocalFileUri(uri) ? !isAppSpecificStorageFileUri(activity, uri) : isMediaStoreExternalContentUri(uri);
    }

    @UnstableApi
    public static boolean isRunningOnEmulator() {
        String lowerCase = Ascii.toLowerCase(DEVICE);
        return lowerCase.contains("emulator") || lowerCase.contains("emu64a") || lowerCase.contains("emu64x") || lowerCase.contains(MessengerShareContentUtility.TEMPLATE_GENERIC_TYPE);
    }

    @RequiresApi(api = 24)
    private static boolean isTrafficRestricted(Uri uri) {
        return "http".equals(uri.getScheme()) && !NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted((String) Assertions.checkNotNull(uri.getHost()));
    }

    @UnstableApi
    public static boolean isTv(Context context) {
        UiModeManager uiModeManager = (UiModeManager) context.getApplicationContext().getSystemService("uimode");
        return uiModeManager != null && uiModeManager.getCurrentModeType() == 4;
    }

    @UnstableApi
    public static boolean isWear(Context context) {
        return SDK_INT >= 20 && context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Thread lambda$newSingleThreadExecutor$3(String str, Runnable runnable) {
        return new Thread(runnable, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Thread lambda$newSingleThreadScheduledExecutor$4(String str, Runnable runnable) {
        return new Thread(runnable, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$postOrRunWithCompletion$0(SettableFuture settableFuture, Runnable runnable, Object obj) {
        try {
            if (settableFuture.isCancelled()) {
                return;
            }
            runnable.run();
            settableFuture.set(obj);
        } catch (Throwable th) {
            settableFuture.setException(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$transformFutureAsync$1(SettableFuture settableFuture, ListenableFuture listenableFuture) {
        if (settableFuture.isCancelled()) {
            listenableFuture.cancel(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$transformFutureAsync$2(ListenableFuture listenableFuture, SettableFuture settableFuture, AsyncFunction asyncFunction) {
        try {
            try {
                settableFuture.setFuture(asyncFunction.apply(Futures.getDone(listenableFuture)));
            } catch (Throwable th) {
                settableFuture.setException(th);
            }
        } catch (Error e2) {
            e = e2;
            settableFuture.setException(e);
        } catch (CancellationException unused) {
            settableFuture.cancel(false);
        } catch (RuntimeException e3) {
            e = e3;
            settableFuture.setException(e);
        } catch (ExecutionException e4) {
            e = e4;
            Throwable cause = e.getCause();
            if (cause != null) {
                e = cause;
            }
            settableFuture.setException(e);
        }
    }

    @UnstableApi
    public static int linearSearch(int[] iArr, int i2) {
        for (int i3 = 0; i3 < iArr.length; i3++) {
            if (iArr[i3] == i2) {
                return i3;
            }
        }
        return -1;
    }

    @UnstableApi
    public static String loadAsset(Context context, String str) throws IOException {
        InputStream inputStreamOpen = null;
        try {
            inputStreamOpen = context.getAssets().open(str);
            return fromUtf8Bytes(ByteStreams.toByteArray(inputStreamOpen));
        } finally {
            closeQuietly(inputStreamOpen);
        }
    }

    @UnstableApi
    public static long maxValue(SparseLongArray sparseLongArray) {
        if (sparseLongArray.size() == 0) {
            throw new NoSuchElementException();
        }
        long jMax = Long.MIN_VALUE;
        for (int i2 = 0; i2 < sparseLongArray.size(); i2++) {
            jMax = Math.max(jMax, sparseLongArray.valueAt(i2));
        }
        return jMax;
    }

    private static String maybeReplaceLegacyLanguageTags(String str) {
        int i2 = 0;
        while (true) {
            String[] strArr = isoLegacyTagReplacements;
            if (i2 >= strArr.length) {
                return str;
            }
            if (str.startsWith(strArr[i2])) {
                return strArr[i2 + 1] + str.substring(strArr[i2].length());
            }
            i2 += 2;
        }
    }

    @Deprecated
    public static boolean maybeRequestReadExternalStoragePermission(Activity activity, Uri... uriArr) {
        for (Uri uri : uriArr) {
            if (maybeRequestReadStoragePermission(activity, uri)) {
                return true;
            }
        }
        return false;
    }

    public static boolean maybeRequestReadStoragePermission(Activity activity, MediaItem... mediaItemArr) {
        if (SDK_INT < 23) {
            return false;
        }
        for (MediaItem mediaItem : mediaItemArr) {
            MediaItem.LocalConfiguration localConfiguration = mediaItem.localConfiguration;
            if (localConfiguration != null) {
                if (maybeRequestReadStoragePermission(activity, localConfiguration.uri)) {
                    return true;
                }
                ImmutableList<MediaItem.SubtitleConfiguration> immutableList = mediaItem.localConfiguration.subtitleConfigurations;
                for (int i2 = 0; i2 < immutableList.size(); i2++) {
                    if (maybeRequestReadStoragePermission(activity, immutableList.get(i2).uri)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @UnstableApi
    public static long minValue(SparseLongArray sparseLongArray) {
        if (sparseLongArray.size() == 0) {
            throw new NoSuchElementException();
        }
        long jMin = Long.MAX_VALUE;
        for (int i2 = 0; i2 < sparseLongArray.size(); i2++) {
            jMin = Math.min(jMin, sparseLongArray.valueAt(i2));
        }
        return jMin;
    }

    @UnstableApi
    public static <T> void moveItems(List<T> list, int i2, int i3, int i4) {
        ArrayDeque arrayDeque = new ArrayDeque();
        for (int i5 = (i3 - i2) - 1; i5 >= 0; i5--) {
            arrayDeque.addFirst(list.remove(i2 + i5));
        }
        list.addAll(Math.min(i4, list.size()), arrayDeque);
    }

    @UnstableApi
    public static long msToUs(long j2) {
        return (j2 == C.TIME_UNSET || j2 == Long.MIN_VALUE) ? j2 : j2 * 1000;
    }

    @UnstableApi
    public static ExecutorService newSingleThreadExecutor(final String str) {
        return Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: androidx.media3.common.util.u
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return Util.lambda$newSingleThreadExecutor$3(str, runnable);
            }
        });
    }

    @UnstableApi
    public static ScheduledExecutorService newSingleThreadScheduledExecutor(final String str) {
        return Executors.newSingleThreadScheduledExecutor(new ThreadFactory() { // from class: androidx.media3.common.util.r
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return Util.lambda$newSingleThreadScheduledExecutor$4(str, runnable);
            }
        });
    }

    @UnstableApi
    public static String normalizeLanguageCode(String str) {
        if (str == null) {
            return null;
        }
        String strReplace = str.replace('_', '-');
        if (!strReplace.isEmpty() && !strReplace.equals(C.LANGUAGE_UNDETERMINED)) {
            str = strReplace;
        }
        String lowerCase = Ascii.toLowerCase(str);
        String str2 = splitAtFirst(lowerCase, Constants.ACCEPT_TIME_SEPARATOR_SERVER)[0];
        if (languageTagReplacementMap == null) {
            languageTagReplacementMap = createIsoLanguageReplacementMap();
        }
        String str3 = languageTagReplacementMap.get(str2);
        if (str3 != null) {
            lowerCase = str3 + lowerCase.substring(str2.length());
            str2 = str3;
        }
        return ("no".equals(str2) || "i".equals(str2) || "zh".equals(str2)) ? maybeReplaceLegacyLanguageTags(lowerCase) : lowerCase;
    }

    @UnstableApi
    public static <T> T[] nullSafeArrayAppend(T[] tArr, T t2) {
        Object[] objArrCopyOf = Arrays.copyOf(tArr, tArr.length + 1);
        objArrCopyOf[tArr.length] = t2;
        return (T[]) castNonNullTypeArray(objArrCopyOf);
    }

    @UnstableApi
    public static <T> T[] nullSafeArrayConcatenation(T[] tArr, T[] tArr2) {
        T[] tArr3 = (T[]) Arrays.copyOf(tArr, tArr.length + tArr2.length);
        System.arraycopy(tArr2, 0, tArr3, tArr.length, tArr2.length);
        return tArr3;
    }

    @UnstableApi
    public static <T> T[] nullSafeArrayCopy(T[] tArr, int i2) {
        Assertions.checkArgument(i2 <= tArr.length);
        return (T[]) Arrays.copyOf(tArr, i2);
    }

    @UnstableApi
    public static <T> T[] nullSafeArrayCopyOfRange(T[] tArr, int i2, int i3) {
        Assertions.checkArgument(i2 >= 0);
        Assertions.checkArgument(i3 <= tArr.length);
        return (T[]) Arrays.copyOfRange(tArr, i2, i3);
    }

    @UnstableApi
    public static <T> void nullSafeListToArray(List<T> list, T[] tArr) {
        Assertions.checkState(list.size() == tArr.length);
        list.toArray(tArr);
    }

    @UnstableApi
    public static long parseXsDateTime(String str) throws ParserException {
        Matcher matcher = XS_DATE_TIME_PATTERN.matcher(str);
        if (!matcher.matches()) {
            throw ParserException.createForMalformedContainer("Invalid date/time format: " + str, null);
        }
        int i2 = 0;
        if (matcher.group(9) != null && !matcher.group(9).equalsIgnoreCase("Z")) {
            i2 = (Integer.parseInt(matcher.group(12)) * 60) + Integer.parseInt(matcher.group(13));
            if (Constants.ACCEPT_TIME_SEPARATOR_SERVER.equals(matcher.group(11))) {
                i2 *= -1;
            }
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        gregorianCalendar.clear();
        gregorianCalendar.set(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)) - 1, Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
        if (!TextUtils.isEmpty(matcher.group(8))) {
            gregorianCalendar.set(14, new BigDecimal("0." + matcher.group(8)).movePointRight(3).intValue());
        }
        long timeInMillis = gregorianCalendar.getTimeInMillis();
        return i2 != 0 ? timeInMillis - (i2 * 60000) : timeInMillis;
    }

    @UnstableApi
    public static long parseXsDuration(String str) {
        Matcher matcher = XS_DURATION_PATTERN.matcher(str);
        if (!matcher.matches()) {
            return (long) (Double.parseDouble(str) * 3600.0d * 1000.0d);
        }
        boolean zIsEmpty = true ^ TextUtils.isEmpty(matcher.group(1));
        String strGroup = matcher.group(3);
        double d2 = strGroup != null ? Double.parseDouble(strGroup) * 3.1556908E7d : 0.0d;
        String strGroup2 = matcher.group(5);
        double d3 = d2 + (strGroup2 != null ? Double.parseDouble(strGroup2) * 2629739.0d : 0.0d);
        String strGroup3 = matcher.group(7);
        double d4 = d3 + (strGroup3 != null ? Double.parseDouble(strGroup3) * 86400.0d : 0.0d);
        String strGroup4 = matcher.group(10);
        double d5 = d4 + (strGroup4 != null ? Double.parseDouble(strGroup4) * 3600.0d : 0.0d);
        String strGroup5 = matcher.group(12);
        double d6 = d5 + (strGroup5 != null ? Double.parseDouble(strGroup5) * 60.0d : 0.0d);
        String strGroup6 = matcher.group(14);
        long j2 = (long) ((d6 + (strGroup6 != null ? Double.parseDouble(strGroup6) : 0.0d)) * 1000.0d);
        return zIsEmpty ? -j2 : j2;
    }

    @UnstableApi
    public static boolean postOrRun(Handler handler, Runnable runnable) {
        if (!handler.getLooper().getThread().isAlive()) {
            return false;
        }
        if (handler.getLooper() != Looper.myLooper()) {
            return handler.post(runnable);
        }
        runnable.run();
        return true;
    }

    @UnstableApi
    public static <T> ListenableFuture<T> postOrRunWithCompletion(Handler handler, final Runnable runnable, final T t2) {
        final SettableFuture settableFutureCreate = SettableFuture.create();
        postOrRun(handler, new Runnable() { // from class: androidx.media3.common.util.v
            @Override // java.lang.Runnable
            public final void run() {
                Util.lambda$postOrRunWithCompletion$0(settableFutureCreate, runnable, t2);
            }
        });
        return settableFutureCreate;
    }

    @UnstableApi
    public static boolean readBoolean(Parcel parcel) {
        return parcel.readInt() != 0;
    }

    @UnstableApi
    public static void recursiveDelete(File file) {
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                recursiveDelete(file2);
            }
        }
        file.delete();
    }

    @Nullable
    @UnstableApi
    public static Intent registerReceiverNotExported(Context context, @Nullable BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        return SDK_INT < 33 ? context.registerReceiver(broadcastReceiver, intentFilter) : context.registerReceiver(broadcastReceiver, intentFilter, 4);
    }

    @UnstableApi
    public static <T> void removeRange(List<T> list, int i2, int i3) {
        if (i2 < 0 || i3 > list.size() || i2 > i3) {
            throw new IllegalArgumentException();
        }
        if (i2 != i3) {
            list.subList(i2, i3).clear();
        }
    }

    @RequiresApi(api = 23)
    private static boolean requestExternalStoragePermission(Activity activity) {
        if (activity.checkSelfPermission(PermissionConfig.READ_EXTERNAL_STORAGE) == 0) {
            return false;
        }
        activity.requestPermissions(new String[]{PermissionConfig.READ_EXTERNAL_STORAGE}, 0);
        return true;
    }

    @RequiresApi(api = 33)
    private static boolean requestReadMediaPermissions(Activity activity) {
        if (activity.checkSelfPermission(PermissionConfig.READ_MEDIA_AUDIO) == 0 && activity.checkSelfPermission(PermissionConfig.READ_MEDIA_VIDEO) == 0 && activity.checkSelfPermission(PermissionConfig.READ_MEDIA_IMAGES) == 0) {
            return false;
        }
        activity.requestPermissions(new String[]{PermissionConfig.READ_MEDIA_AUDIO, PermissionConfig.READ_MEDIA_IMAGES, PermissionConfig.READ_MEDIA_VIDEO}, 0);
        return true;
    }

    @UnstableApi
    public static long sampleCountToDurationUs(long j2, int i2) {
        return scaleLargeValue(j2, 1000000L, i2, RoundingMode.FLOOR);
    }

    @UnstableApi
    public static long scaleLargeTimestamp(long j2, long j3, long j4) {
        return scaleLargeValue(j2, j3, j4, RoundingMode.FLOOR);
    }

    @UnstableApi
    public static long[] scaleLargeTimestamps(List<Long> list, long j2, long j3) {
        return scaleLargeValues(list, j2, j3, RoundingMode.FLOOR);
    }

    @UnstableApi
    public static void scaleLargeTimestampsInPlace(long[] jArr, long j2, long j3) {
        scaleLargeValuesInPlace(jArr, j2, j3, RoundingMode.FLOOR);
    }

    @UnstableApi
    public static long scaleLargeValue(long j2, long j3, long j4, RoundingMode roundingMode) {
        if (j2 == 0 || j3 == 0) {
            return 0L;
        }
        return (j4 < j3 || j4 % j3 != 0) ? (j4 >= j3 || j3 % j4 != 0) ? (j4 < j2 || j4 % j2 != 0) ? (j4 >= j2 || j2 % j4 != 0) ? scaleLargeValueFallback(j2, j3, j4, roundingMode) : LongMath.saturatedMultiply(j3, LongMath.divide(j2, j4, RoundingMode.UNNECESSARY)) : LongMath.divide(j3, LongMath.divide(j4, j2, RoundingMode.UNNECESSARY), roundingMode) : LongMath.saturatedMultiply(j2, LongMath.divide(j3, j4, RoundingMode.UNNECESSARY)) : LongMath.divide(j2, LongMath.divide(j4, j3, RoundingMode.UNNECESSARY), roundingMode);
    }

    private static long scaleLargeValueFallback(long j2, long j3, long j4, RoundingMode roundingMode) {
        long jSaturatedMultiply = LongMath.saturatedMultiply(j2, j3);
        if (jSaturatedMultiply != Long.MAX_VALUE && jSaturatedMultiply != Long.MIN_VALUE) {
            return LongMath.divide(jSaturatedMultiply, j4, roundingMode);
        }
        long jGcd = LongMath.gcd(Math.abs(j3), Math.abs(j4));
        RoundingMode roundingMode2 = RoundingMode.UNNECESSARY;
        long jDivide = LongMath.divide(j3, jGcd, roundingMode2);
        long jDivide2 = LongMath.divide(j4, jGcd, roundingMode2);
        long jGcd2 = LongMath.gcd(Math.abs(j2), Math.abs(jDivide2));
        long jDivide3 = LongMath.divide(j2, jGcd2, roundingMode2);
        long jDivide4 = LongMath.divide(jDivide2, jGcd2, roundingMode2);
        long jSaturatedMultiply2 = LongMath.saturatedMultiply(jDivide3, jDivide);
        if (jSaturatedMultiply2 != Long.MAX_VALUE && jSaturatedMultiply2 != Long.MIN_VALUE) {
            return LongMath.divide(jSaturatedMultiply2, jDivide4, roundingMode);
        }
        double d2 = jDivide3 * (jDivide / jDivide4);
        if (d2 > 9.223372036854776E18d) {
            return Long.MAX_VALUE;
        }
        if (d2 < -9.223372036854776E18d) {
            return Long.MIN_VALUE;
        }
        return DoubleMath.roundToLong(d2, roundingMode);
    }

    @UnstableApi
    public static long[] scaleLargeValues(List<Long> list, long j2, long j3, RoundingMode roundingMode) {
        int i2;
        int size = list.size();
        long[] jArr = new long[size];
        if (j2 == 0) {
            return jArr;
        }
        int i3 = 0;
        if (j3 >= j2 && j3 % j2 == 0) {
            long jDivide = LongMath.divide(j3, j2, RoundingMode.UNNECESSARY);
            while (i3 < size) {
                jArr[i3] = LongMath.divide(list.get(i3).longValue(), jDivide, roundingMode);
                i3++;
            }
            return jArr;
        }
        if (j3 < j2 && j2 % j3 == 0) {
            long jDivide2 = LongMath.divide(j2, j3, RoundingMode.UNNECESSARY);
            while (i3 < size) {
                jArr[i3] = LongMath.saturatedMultiply(list.get(i3).longValue(), jDivide2);
                i3++;
            }
            return jArr;
        }
        int i4 = 0;
        while (i4 < size) {
            long jLongValue = list.get(i4).longValue();
            if (jLongValue != 0) {
                if (j3 >= jLongValue && j3 % jLongValue == 0) {
                    jArr[i4] = LongMath.divide(j2, LongMath.divide(j3, jLongValue, RoundingMode.UNNECESSARY), roundingMode);
                } else if (j3 >= jLongValue || jLongValue % j3 != 0) {
                    i2 = i4;
                    jArr[i2] = scaleLargeValueFallback(jLongValue, j2, j3, roundingMode);
                } else {
                    jArr[i4] = LongMath.saturatedMultiply(j2, LongMath.divide(jLongValue, j3, RoundingMode.UNNECESSARY));
                }
                i2 = i4;
            } else {
                i2 = i4;
            }
            i4 = i2 + 1;
        }
        return jArr;
    }

    @UnstableApi
    public static void scaleLargeValuesInPlace(long[] jArr, long j2, long j3, RoundingMode roundingMode) {
        if (j2 == 0) {
            Arrays.fill(jArr, 0L);
            return;
        }
        int i2 = 0;
        if (j3 >= j2 && j3 % j2 == 0) {
            long jDivide = LongMath.divide(j3, j2, RoundingMode.UNNECESSARY);
            while (i2 < jArr.length) {
                jArr[i2] = LongMath.divide(jArr[i2], jDivide, roundingMode);
                i2++;
            }
            return;
        }
        if (j3 < j2 && j2 % j3 == 0) {
            long jDivide2 = LongMath.divide(j2, j3, RoundingMode.UNNECESSARY);
            while (i2 < jArr.length) {
                jArr[i2] = LongMath.saturatedMultiply(jArr[i2], jDivide2);
                i2++;
            }
            return;
        }
        for (int i3 = 0; i3 < jArr.length; i3++) {
            long j4 = jArr[i3];
            if (j4 != 0) {
                if (j3 >= j4 && j3 % j4 == 0) {
                    jArr[i3] = LongMath.divide(j2, LongMath.divide(j3, j4, RoundingMode.UNNECESSARY), roundingMode);
                } else if (j3 >= j4 || j4 % j3 != 0) {
                    jArr[i3] = scaleLargeValueFallback(j4, j2, j3, roundingMode);
                } else {
                    jArr[i3] = LongMath.saturatedMultiply(j2, LongMath.divide(j4, j3, RoundingMode.UNNECESSARY));
                }
            }
        }
    }

    @UnstableApi
    public static void setForegroundServiceNotification(Service service, int i2, Notification notification, int i3, String str) {
        if (SDK_INT >= 29) {
            Api29.startForeground(service, i2, notification, i3, str);
        } else {
            service.startForeground(i2, notification);
        }
    }

    private static boolean shouldEscapeCharacter(char c2) {
        return c2 == '\"' || c2 == '%' || c2 == '*' || c2 == '/' || c2 == ':' || c2 == '<' || c2 == '\\' || c2 == '|' || c2 == '>' || c2 == '?';
    }

    @EnsuresNonNullIf(expression = {"#1"}, result = false)
    public static boolean shouldShowPlayButton(@Nullable Player player) {
        return shouldShowPlayButton(player, true);
    }

    @UnstableApi
    public static void sneakyThrow(Throwable th) throws Throwable {
        sneakyThrowInternal(th);
    }

    @UnstableApi
    public static String[] split(String str, String str2) {
        return str.split(str2, -1);
    }

    @UnstableApi
    public static String[] splitAtFirst(String str, String str2) {
        return str.split(str2, 2);
    }

    @UnstableApi
    public static String[] splitCodecs(@Nullable String str) {
        return TextUtils.isEmpty(str) ? new String[0] : split(str.trim(), "(\\s*,\\s*)");
    }

    @Nullable
    @UnstableApi
    public static ComponentName startForegroundService(Context context, Intent intent) {
        return SDK_INT >= 26 ? context.startForegroundService(intent) : context.startService(intent);
    }

    @UnstableApi
    public static long subtractWithOverflowDefault(long j2, long j3, long j4) {
        long j5 = j2 - j3;
        return ((j2 ^ j5) & (j3 ^ j2)) < 0 ? j4 : j5;
    }

    @UnstableApi
    public static long sum(long... jArr) {
        long j2 = 0;
        for (long j3 : jArr) {
            j2 += j3;
        }
        return j2;
    }

    @UnstableApi
    public static boolean tableExists(SQLiteDatabase sQLiteDatabase, String str) {
        return DatabaseUtils.queryNumEntries(sQLiteDatabase, "sqlite_master", "tbl_name = ?", new String[]{str}) > 0;
    }

    @UnstableApi
    @Deprecated
    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        return ByteStreams.toByteArray(inputStream);
    }

    @UnstableApi
    public static String toHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            sb.append(Character.forDigit((bArr[i2] >> 4) & 15, 16));
            sb.append(Character.forDigit(bArr[i2] & 15, 16));
        }
        return sb.toString();
    }

    @UnstableApi
    public static long toLong(int i2, int i3) {
        return toUnsignedLong(i3) | (toUnsignedLong(i2) << 32);
    }

    @UnstableApi
    public static long toUnsignedLong(int i2) {
        return i2 & 4294967295L;
    }

    @UnstableApi
    public static <T, U> ListenableFuture<T> transformFutureAsync(final ListenableFuture<U> listenableFuture, final AsyncFunction<U, T> asyncFunction) {
        final SettableFuture settableFutureCreate = SettableFuture.create();
        settableFutureCreate.addListener(new Runnable() { // from class: androidx.media3.common.util.s
            @Override // java.lang.Runnable
            public final void run() {
                Util.lambda$transformFutureAsync$1(settableFutureCreate, listenableFuture);
            }
        }, MoreExecutors.directExecutor());
        listenableFuture.addListener(new Runnable() { // from class: androidx.media3.common.util.t
            @Override // java.lang.Runnable
            public final void run() {
                Util.lambda$transformFutureAsync$2(listenableFuture, settableFutureCreate, asyncFunction);
            }
        }, MoreExecutors.directExecutor());
        return settableFutureCreate;
    }

    @Nullable
    @UnstableApi
    public static String unescapeFileName(String str) {
        int length = str.length();
        int iEnd = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (str.charAt(i3) == '%') {
                i2++;
            }
        }
        if (i2 == 0) {
            return str;
        }
        int i4 = length - (i2 * 2);
        StringBuilder sb = new StringBuilder(i4);
        Matcher matcher = ESCAPED_CHARACTER_PATTERN.matcher(str);
        while (i2 > 0 && matcher.find()) {
            char c2 = (char) Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1)), 16);
            sb.append((CharSequence) str, iEnd, matcher.start());
            sb.append(c2);
            iEnd = matcher.end();
            i2--;
        }
        if (iEnd < length) {
            sb.append((CharSequence) str, iEnd, length);
        }
        if (sb.length() != i4) {
            return null;
        }
        return sb.toString();
    }

    @UnstableApi
    public static long usToMs(long j2) {
        return (j2 == C.TIME_UNSET || j2 == Long.MIN_VALUE) ? j2 : j2 / 1000;
    }

    @UnstableApi
    public static void writeBoolean(Parcel parcel, boolean z2) {
        parcel.writeInt(z2 ? 1 : 0);
    }

    @UnstableApi
    public static long ceilDivide(long j2, long j3) {
        return ((j2 + j3) - 1) / j3;
    }

    @UnstableApi
    public static long constrainValue(long j2, long j3, long j4) {
        return Math.max(j3, Math.min(j2, j4));
    }

    @UnstableApi
    public static Handler createHandlerForCurrentLooper(@Nullable Handler.Callback callback) {
        return createHandler((Looper) Assertions.checkStateNotNull(Looper.myLooper()), callback);
    }

    @UnstableApi
    public static Handler createHandlerForCurrentOrMainLooper(@Nullable Handler.Callback callback) {
        return createHandler(getCurrentOrMainLooper(), callback);
    }

    @UnstableApi
    public static String fromUtf8Bytes(byte[] bArr, int i2, int i3) {
        return new String(bArr, i2, i3, Charsets.UTF_8);
    }

    @UnstableApi
    public static boolean handlePlayPauseButtonAction(@Nullable Player player, boolean z2) {
        return shouldShowPlayButton(player, z2) ? handlePlayButtonAction(player) : handlePauseButtonAction(player);
    }

    @EnsuresNonNullIf(expression = {"#1"}, result = false)
    @UnstableApi
    public static boolean shouldShowPlayButton(@Nullable Player player, boolean z2) {
        if (player == null || !player.getPlayWhenReady() || player.getPlaybackState() == 1 || player.getPlaybackState() == 4) {
            return true;
        }
        return z2 && player.getPlaybackSuppressionReason() != 0;
    }

    @UnstableApi
    public static byte[] toByteArray(int... iArr) {
        byte[] bArr = new byte[iArr.length * 4];
        int i2 = 0;
        for (int i3 : iArr) {
            bArr[i2] = (byte) (i3 >> 24);
            bArr[i2 + 1] = (byte) (i3 >> 16);
            int i4 = i2 + 3;
            bArr[i2 + 2] = (byte) (i3 >> 8);
            i2 += 4;
            bArr[i4] = (byte) i3;
        }
        return bArr;
    }

    @UnstableApi
    public static float constrainValue(float f2, float f3, float f4) {
        return Math.max(f3, Math.min(f2, f4));
    }

    @UnstableApi
    public static <T> boolean contains(SparseArray<T> sparseArray, int i2) {
        return sparseArray.indexOfKey(i2) >= 0;
    }

    @UnstableApi
    public static int linearSearch(long[] jArr, long j2) {
        for (int i2 = 0; i2 < jArr.length; i2++) {
            if (jArr[i2] == j2) {
                return i2;
            }
        }
        return -1;
    }

    @Deprecated
    public static boolean maybeRequestReadExternalStoragePermission(Activity activity, MediaItem... mediaItemArr) {
        return maybeRequestReadStoragePermission(activity, mediaItemArr);
    }

    @UnstableApi
    public static int binarySearchCeil(long[] jArr, long j2, boolean z2, boolean z3) {
        int i2;
        int i3;
        int iBinarySearch = Arrays.binarySearch(jArr, j2);
        if (iBinarySearch < 0) {
            i3 = ~iBinarySearch;
        } else {
            while (true) {
                i2 = iBinarySearch + 1;
                if (i2 >= jArr.length || jArr[i2] != j2) {
                    break;
                }
                iBinarySearch = i2;
            }
            i3 = z2 ? iBinarySearch : i2;
        }
        return z3 ? Math.min(jArr.length - 1, i3) : i3;
    }

    @UnstableApi
    public static int binarySearchFloor(long[] jArr, long j2, boolean z2, boolean z3) {
        int i2;
        int i3;
        int iBinarySearch = Arrays.binarySearch(jArr, j2);
        if (iBinarySearch < 0) {
            i3 = -(iBinarySearch + 2);
        } else {
            while (true) {
                i2 = iBinarySearch - 1;
                if (i2 < 0 || jArr[i2] != j2) {
                    break;
                }
                iBinarySearch = i2;
            }
            i3 = z2 ? iBinarySearch : i2;
        }
        return z3 ? Math.max(0, i3) : i3;
    }

    public static int inferContentType(Uri uri) {
        int iInferContentTypeForExtension;
        String scheme = uri.getScheme();
        if (scheme != null && Ascii.equalsIgnoreCase("rtsp", scheme)) {
            return 3;
        }
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment == null) {
            return 4;
        }
        int iLastIndexOf = lastPathSegment.lastIndexOf(46);
        if (iLastIndexOf >= 0 && (iInferContentTypeForExtension = inferContentTypeForExtension(lastPathSegment.substring(iLastIndexOf + 1))) != 4) {
            return iInferContentTypeForExtension;
        }
        Matcher matcher = ISM_PATH_PATTERN.matcher((CharSequence) Assertions.checkNotNull(uri.getPath()));
        if (!matcher.matches()) {
            return 4;
        }
        String strGroup = matcher.group(2);
        if (strGroup != null) {
            if (strGroup.contains(ISM_DASH_FORMAT_EXTENSION)) {
                return 0;
            }
            if (strGroup.contains(ISM_HLS_FORMAT_EXTENSION)) {
                return 2;
            }
        }
        return 1;
    }

    @UnstableApi
    public static <T extends Comparable<? super T>> int binarySearchCeil(List<? extends Comparable<? super T>> list, T t2, boolean z2, boolean z3) {
        int i2;
        int i3;
        int iBinarySearch = Collections.binarySearch(list, t2);
        if (iBinarySearch < 0) {
            i3 = ~iBinarySearch;
        } else {
            int size = list.size();
            while (true) {
                i2 = iBinarySearch + 1;
                if (i2 >= size || list.get(i2).compareTo(t2) != 0) {
                    break;
                }
                iBinarySearch = i2;
            }
            i3 = z2 ? iBinarySearch : i2;
        }
        return z3 ? Math.min(list.size() - 1, i3) : i3;
    }

    @UnstableApi
    public static <T extends Comparable<? super T>> int binarySearchFloor(List<? extends Comparable<? super T>> list, T t2, boolean z2, boolean z3) {
        int i2;
        int i3;
        int iBinarySearch = Collections.binarySearch(list, t2);
        if (iBinarySearch < 0) {
            i3 = -(iBinarySearch + 2);
        } else {
            while (true) {
                i2 = iBinarySearch - 1;
                if (i2 < 0 || list.get(i2).compareTo(t2) != 0) {
                    break;
                }
                iBinarySearch = i2;
            }
            i3 = z2 ? iBinarySearch : i2;
        }
        return z3 ? Math.max(0, i3) : i3;
    }

    @UnstableApi
    public static Format getPcmFormat(AudioProcessor.AudioFormat audioFormat) {
        return getPcmFormat(audioFormat.encoding, audioFormat.channelCount, audioFormat.sampleRate);
    }

    @UnstableApi
    public static Point getCurrentDisplayModeSize(Context context, Display display) throws ClassNotFoundException, NumberFormatException {
        String systemProperty;
        if (display.getDisplayId() == 0 && isTv(context)) {
            if (SDK_INT < 28) {
                systemProperty = getSystemProperty("sys.display-size");
            } else {
                systemProperty = getSystemProperty("vendor.display-size");
            }
            if (!TextUtils.isEmpty(systemProperty)) {
                try {
                    String[] strArrSplit = split(systemProperty.trim(), "x");
                    if (strArrSplit.length == 2) {
                        int i2 = Integer.parseInt(strArrSplit[0]);
                        int i3 = Integer.parseInt(strArrSplit[1]);
                        if (i2 > 0 && i3 > 0) {
                            return new Point(i2, i3);
                        }
                    }
                } catch (NumberFormatException unused) {
                }
                Log.e(TAG, "Invalid display size: " + systemProperty);
            }
            if ("Sony".equals(MANUFACTURER) && MODEL.startsWith("BRAVIA") && context.getPackageManager().hasSystemFeature("com.sony.dtv.hardware.panel.qfhd")) {
                return new Point(3840, 2160);
            }
        }
        Point point = new Point();
        if (SDK_INT >= 23) {
            getDisplaySizeV23(display, point);
        } else {
            display.getRealSize(point);
        }
        return point;
    }

    private static boolean maybeRequestReadStoragePermission(Activity activity, Uri uri) {
        if (!isReadStoragePermissionRequestNeeded(activity, uri)) {
            return false;
        }
        if (SDK_INT < 33) {
            return requestExternalStoragePermission(activity);
        }
        return requestReadMediaPermissions(activity);
    }

    @UnstableApi
    public static byte[] toByteArray(float f2) {
        return Ints.toByteArray(Float.floatToIntBits(f2));
    }

    @UnstableApi
    public static int binarySearchFloor(LongArray longArray, long j2, boolean z2, boolean z3) {
        int i2;
        int size = longArray.size() - 1;
        int i3 = 0;
        while (i3 <= size) {
            int i4 = (i3 + size) >>> 1;
            if (longArray.get(i4) < j2) {
                i3 = i4 + 1;
            } else {
                size = i4 - 1;
            }
        }
        if (z2 && (i2 = size + 1) < longArray.size() && longArray.get(i2) == j2) {
            return i2;
        }
        if (z3 && size == -1) {
            return 0;
        }
        return size;
    }

    @UnstableApi
    @Deprecated
    public static int inferContentType(String str) {
        return inferContentType(Uri.parse("file:///" + str));
    }

    private static <T extends Throwable> void sneakyThrowInternal(Throwable th) throws Throwable {
        throw th;
    }
}
