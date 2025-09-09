package com.alibaba.ailabs.tg.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.tg.storage.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class ConvertUtils {
    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private ConvertUtils() {
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat compressFormat) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Drawable bitmap2Drawable(@NonNull Context context, Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static byte[] bits2Bytes(String str) {
        int length = str.length() % 8;
        int length2 = str.length() / 8;
        if (length != 0) {
            while (length < 8) {
                str = "0" + str;
                length++;
            }
            length2++;
        }
        byte[] bArr = new byte[length2];
        for (int i2 = 0; i2 < length2; i2++) {
            for (int i3 = 0; i3 < 8; i3++) {
                byte b2 = (byte) (bArr[i2] << 1);
                bArr[i2] = b2;
                bArr[i2] = (byte) (b2 | (str.charAt((i2 * 8) + i3) - '0'));
            }
        }
        return bArr;
    }

    @SuppressLint({"DefaultLocale"})
    public static String byte2FitMemorySize(long j2) {
        return j2 < 0 ? "shouldn't be less than zero!" : j2 < 1024 ? String.format("%.3fB", Double.valueOf(j2)) : j2 < 1048576 ? String.format("%.3fKB", Double.valueOf(j2 / 1024.0d)) : j2 < 1073741824 ? String.format("%.3fMB", Double.valueOf(j2 / 1048576.0d)) : String.format("%.3fGB", Double.valueOf(j2 / 1.073741824E9d));
    }

    public static double byte2MemorySize(long j2, int i2) {
        if (j2 < 0) {
            return -1.0d;
        }
        return j2 / i2;
    }

    public static Bitmap bytes2Bitmap(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
    }

    public static String bytes2Bits(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArr) {
            for (int i2 = 7; i2 >= 0; i2--) {
                sb.append(((b2 >> i2) & 1) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }

    public static char[] bytes2Chars(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length) <= 0) {
            return null;
        }
        char[] cArr = new char[length];
        for (int i2 = 0; i2 < length; i2++) {
            cArr[i2] = (char) (bArr[i2] & 255);
        }
        return cArr;
    }

    public static Drawable bytes2Drawable(@NonNull Context context, byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return bitmap2Drawable(context, bytes2Bitmap(bArr));
    }

    public static String bytes2HexString(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length) <= 0) {
            return null;
        }
        char[] cArr = new char[length << 1];
        int i2 = 0;
        for (byte b2 : bArr) {
            int i3 = i2 + 1;
            char[] cArr2 = hexDigits;
            cArr[i2] = cArr2[(b2 >>> 4) & 15];
            i2 += 2;
            cArr[i3] = cArr2[b2 & 15];
        }
        return new String(cArr);
    }

    public static InputStream bytes2InputStream(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        return new ByteArrayInputStream(bArr);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [int] */
    public static OutputStream bytes2OutputStream(byte[] bArr) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        Closeable closeable = null;
        if (bArr != null) {
            ?? length = bArr.length;
            try {
                if (length > 0) {
                    try {
                        byteArrayOutputStream = new ByteArrayOutputStream();
                    } catch (IOException e2) {
                        e = e2;
                        byteArrayOutputStream = null;
                    } catch (Throwable th) {
                        th = th;
                        IOUtils.closeQuietly(closeable);
                        throw th;
                    }
                    try {
                        byteArrayOutputStream.write(bArr);
                        IOUtils.closeQuietly(byteArrayOutputStream);
                        return byteArrayOutputStream;
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                        IOUtils.closeQuietly(byteArrayOutputStream);
                        return null;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                closeable = length;
            }
        }
        return null;
    }

    public static byte[] chars2Bytes(char[] cArr) {
        if (cArr == null || cArr.length <= 0) {
            return null;
        }
        int length = cArr.length;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i2] = (byte) cArr[i2];
        }
        return bArr;
    }

    public static int dip2px(Context context, float f2) {
        float f3;
        if (context == null) {
            LogUtils.w("dimen util's context is null.");
            f3 = 2.0f;
        } else {
            f3 = context.getResources().getDisplayMetrics().density;
        }
        return (int) ((f2 * f3) + 0.5f);
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmapCreateBitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmapCreateBitmap = Bitmap.createBitmap(1, 1, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } else {
            bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static byte[] drawable2Bytes(Drawable drawable, Bitmap.CompressFormat compressFormat) {
        if (drawable == null) {
            return null;
        }
        return bitmap2Bytes(drawable2Bitmap(drawable), compressFormat);
    }

    private static int hex2Int(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - '0';
        }
        if (c2 < 'A' || c2 > 'F') {
            throw new IllegalArgumentException();
        }
        return c2 - '7';
    }

    public static byte[] hexString2Bytes(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int length = str.length();
        if (length % 2 != 0) {
            str = "0" + str;
            length++;
        }
        char[] charArray = str.toUpperCase(Locale.getDefault()).toCharArray();
        byte[] bArr = new byte[length >> 1];
        for (int i2 = 0; i2 < length; i2 += 2) {
            bArr[i2 >> 1] = (byte) ((hex2Int(charArray[i2]) << 4) | hex2Int(charArray[i2 + 1]));
        }
        return bArr;
    }

    public static ByteArrayOutputStream input2OutputStream(InputStream inputStream) {
        try {
            if (inputStream == null) {
                return null;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = inputStream.read(bArr, 0, 1024);
                if (i2 == -1) {
                    return byteArrayOutputStream;
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static byte[] inputStream2Bytes(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        return input2OutputStream(inputStream).toByteArray();
    }

    public static String inputStream2String(InputStream inputStream, String str) {
        if (inputStream != null && !TextUtils.isEmpty(str)) {
            try {
                return new String(inputStream2Bytes(inputStream), str);
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static long memorySize2Byte(long j2, int i2) {
        if (j2 < 0) {
            return -1L;
        }
        return j2 * i2;
    }

    @SuppressLint({"DefaultLocale"})
    public static String millis2FitTimeSpan(long j2, int i2) {
        if (j2 <= 0 || i2 <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String[] strArr = {"天", "小时", "分钟", "秒", "毫秒"};
        int[] iArr = {86400000, 3600000, 60000, 1000, 1};
        int iMin = Math.min(i2, 5);
        for (int i3 = 0; i3 < iMin; i3++) {
            int i4 = iArr[i3];
            if (j2 >= i4) {
                long j3 = j2 / i4;
                j2 -= i4 * j3;
                sb.append(j3);
                sb.append(strArr[i3]);
            }
        }
        return sb.toString();
    }

    public static long millis2TimeSpan(long j2, int i2) {
        return j2 / i2;
    }

    public static byte[] outputStream2Bytes(OutputStream outputStream) {
        if (outputStream == null) {
            return null;
        }
        return ((ByteArrayOutputStream) outputStream).toByteArray();
    }

    public static String outputStream2String(OutputStream outputStream, String str) {
        if (outputStream != null && !TextUtils.isEmpty(str)) {
            try {
                return new String(outputStream2Bytes(outputStream), str);
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static int px2dip(Context context, float f2) {
        float f3;
        if (context == null) {
            LogUtils.w("dimen util's context is null.");
            f3 = 2.0f;
        } else {
            f3 = context.getResources().getDisplayMetrics().density;
        }
        return (int) ((f2 / f3) + 0.5f);
    }

    public static int strToInt(String str, int i2) {
        if (TextUtils.isEmpty(str)) {
            return i2;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return i2;
        }
    }

    public static long strToLong(String str, long j2) {
        if (TextUtils.isEmpty(str)) {
            return j2;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return j2;
        }
    }

    public static InputStream string2InputStream(String str, String str2) {
        if (str != null && !TextUtils.isEmpty(str2)) {
            try {
                return new ByteArrayInputStream(str.getBytes(str2));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static OutputStream string2OutputStream(String str, String str2) {
        if (str != null && !TextUtils.isEmpty(str2)) {
            try {
                return bytes2OutputStream(str.getBytes(str2));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static long timeSpan2Millis(long j2, int i2) {
        return j2 * i2;
    }

    public static Bitmap view2Bitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Drawable background = view.getBackground();
        if (background != null) {
            background.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }
        view.draw(canvas);
        return bitmapCreateBitmap;
    }

    public ByteArrayInputStream output2InputStream(OutputStream outputStream) {
        if (outputStream == null) {
            return null;
        }
        return new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
    }

    public static int strToInt(String str) {
        return strToInt(str, 0);
    }

    public static long strToLong(String str) {
        return strToLong(str, 0L);
    }
}
