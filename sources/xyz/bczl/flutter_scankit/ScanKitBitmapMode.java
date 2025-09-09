package xyz.bczl.flutter_scankit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.YuvImage;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.ml.scan.HmsBuildBitmapOption;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanBase;
import com.huawei.hms.ml.scan.HmsScanFrame;
import com.huawei.hms.ml.scan.HmsScanFrameOptions;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import xyz.bczl.flutter_scankit.ScanKitAPI;

/* loaded from: classes5.dex */
public class ScanKitBitmapMode {
    static boolean a(HmsScan[] hmsScanArr) {
        return hmsScanArr != null && hmsScanArr.length > 0;
    }

    static Map b(HmsScan[] hmsScanArr) {
        if (a(hmsScanArr)) {
            HmsScan hmsScan = hmsScanArr[0];
            if (!TextUtils.isEmpty(hmsScan.getOriginalValue())) {
                return new ScanResult(hmsScan.getOriginalValue(), hmsScan.getScanType()).a();
            }
            if (new BigDecimal(Double.toString(hmsScan.getZoomValue())).compareTo(new BigDecimal("1.0")) > 0) {
                return new HashMap(Collections.singletonMap("zoomValue", Double.valueOf(hmsScan.getZoomValue())));
            }
        }
        return new HashMap();
    }

    static byte[] c(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Map<String, Object> decode(Activity activity, @NonNull byte[] bArr, @NonNull Long l2, @NonNull Long l3, Map<String, Object> map) {
        HmsScanFrame hmsScanFrame = new HmsScanFrame(new YuvImage(bArr, 17, l2.intValue(), l3.intValue(), null));
        HmsScanFrameOptions.Creator creator = new HmsScanFrameOptions.Creator();
        Object obj = map.get("scanTypes");
        Object obj2 = map.get("photoMode");
        Object obj3 = map.get("parseResult");
        if (obj != null) {
            int[] arrayFromFlags = ScanKitUtilities.getArrayFromFlags(((Integer) obj).intValue());
            creator.setHmsScanTypes(arrayFromFlags[0], Arrays.copyOfRange(arrayFromFlags, 1, arrayFromFlags.length));
        }
        if (obj2 != null) {
            creator.setPhotoMode(((Boolean) obj2).booleanValue());
        }
        if (obj3 != null) {
            creator.setParseResult(((Boolean) obj3).booleanValue());
        }
        return b(ScanUtil.decode(activity, hmsScanFrame, creator.create()).getHmsScans());
    }

    public static Map<String, Object> decodeBitmap(Context context, @NonNull byte[] bArr, @NonNull Map<String, Object> map) {
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        HmsScanFrameOptions.Creator creator = new HmsScanFrameOptions.Creator();
        Object obj = map.get("scanTypes");
        Object obj2 = map.get("photoMode");
        Object obj3 = map.get("parseResult");
        if (obj != null) {
            int[] arrayFromFlags = ScanKitUtilities.getArrayFromFlags(((Integer) obj).intValue());
            creator.setHmsScanTypes(arrayFromFlags[0], Arrays.copyOfRange(arrayFromFlags, 1, arrayFromFlags.length));
        }
        if (obj2 != null) {
            creator.setPhotoMode(((Boolean) obj2).booleanValue());
        }
        if (obj3 != null) {
            creator.setParseResult(((Boolean) obj3).booleanValue());
        }
        return b(ScanUtil.decode(context, new HmsScanFrame(bitmapDecodeByteArray), creator.create()).getHmsScans());
    }

    public static byte[] encode(@NonNull String str, @NonNull Long l2, @NonNull Long l3, @NonNull Map<String, Object> map) {
        HmsBuildBitmapOption.Creator creator = new HmsBuildBitmapOption.Creator();
        Object obj = map.get("scanTypes");
        Object obj2 = map.get("bgColor");
        Object obj3 = map.get("color");
        Object obj4 = map.get("margin");
        int typeFromFlags = HmsScanBase.QRCODE_SCAN_TYPE;
        if (obj != null) {
            typeFromFlags = ScanKitUtilities.getTypeFromFlags(((Integer) obj).intValue());
        }
        if (obj2 != null) {
            creator.setBitmapBackgroundColor(((Long) obj2).intValue());
        }
        if (obj3 != null) {
            creator.setBitmapColor(((Long) obj3).intValue());
        }
        if (obj4 != null) {
            creator.setBitmapMargin(((Integer) obj4).intValue());
        }
        try {
            return c(ScanUtil.buildBitmap(str, typeFromFlags, l2.intValue(), l3.intValue(), creator.create()));
        } catch (WriterException e2) {
            throw new ScanKitAPI.FlutterError("104", e2.getMessage(), "");
        }
    }
}
