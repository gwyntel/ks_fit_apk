package com.yalantis.ucrop.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.task.BitmapLoadTask;
import com.yc.utesdk.ble.close.KeyType;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class BitmapLoadUtils {
    private static final String CONTENT_SCHEME = "content";
    private static final String TAG = "BitmapLoadUtils";

    public static int calculateInSampleSize(@NonNull BitmapFactory.Options options, int i2, int i3) {
        int i4 = options.outHeight;
        int i5 = options.outWidth;
        int i6 = 1;
        if (i4 > i3 || i5 > i2) {
            while (true) {
                if (i4 / i6 <= i3 && i5 / i6 <= i2) {
                    break;
                }
                i6 *= 2;
            }
        }
        return i6;
    }

    public static int calculateMaxBitmapSize(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getSize(point);
        }
        int iSqrt = (int) Math.sqrt(Math.pow(point.x, 2.0d) + Math.pow(point.y, 2.0d));
        Canvas canvas = new Canvas();
        int iMin = Math.min(canvas.getMaximumBitmapWidth(), canvas.getMaximumBitmapHeight());
        if (iMin > 0) {
            iSqrt = Math.min(iSqrt, iMin);
        }
        int maxTextureSize = EglUtils.getMaxTextureSize();
        if (maxTextureSize > 0) {
            iSqrt = Math.min(iSqrt, maxTextureSize);
        }
        Log.d(TAG, "maxBitmapSize: " + iSqrt);
        return iSqrt;
    }

    public static void close(@Nullable Closeable closeable) throws IOException {
        if (closeable == null || !(closeable instanceof Closeable)) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException unused) {
        }
    }

    public static void decodeBitmapInBackground(@NonNull Context context, @NonNull Uri uri, @Nullable Uri uri2, int i2, int i3, BitmapLoadCallback bitmapLoadCallback) {
        new BitmapLoadTask(context, uri, uri2, i2, i3, bitmapLoadCallback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public static int exifToDegrees(int i2) {
        switch (i2) {
            case 3:
            case 4:
                return 180;
            case 5:
            case 6:
                return 90;
            case 7:
            case 8:
                return KeyType.QUERY_BRIGHT_SCREEN_PARAM;
            default:
                return 0;
        }
    }

    public static int exifToTranslation(int i2) {
        return (i2 == 2 || i2 == 7 || i2 == 4 || i2 == 5) ? -1 : 1;
    }

    public static int getExifOrientation(@NonNull Context context, @NonNull Uri uri) throws FileNotFoundException {
        int orientation = 0;
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream == null) {
                return 0;
            }
            orientation = new ImageHeaderParser(inputStreamOpenInputStream).getOrientation();
            close(inputStreamOpenInputStream);
            return orientation;
        } catch (IOException e2) {
            Log.e(TAG, "getExifOrientation: " + uri.toString(), e2);
            return orientation;
        }
    }

    public static boolean hasContentScheme(Uri uri) {
        return uri != null && "content".equals(uri.getScheme());
    }

    public static Bitmap transformBitmap(@NonNull Bitmap bitmap, @NonNull Matrix matrix) {
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return !bitmap.sameAs(bitmapCreateBitmap) ? bitmapCreateBitmap : bitmap;
        } catch (OutOfMemoryError e2) {
            Log.e(TAG, "transformBitmap: ", e2);
            return bitmap;
        }
    }
}
