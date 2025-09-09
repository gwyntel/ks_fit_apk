package com.leeson.image_pickers.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class CommonUtils {
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static String saveBitmap(Context context, String str, Bitmap bitmap) throws IOException {
        if (bitmap == null) {
            return "";
        }
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, System.currentTimeMillis() + PictureMimeType.PNG);
            if (file2.exists()) {
                file2.delete();
                file2.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file2, false);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            return file2.getAbsolutePath();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return "";
        } catch (IOException e3) {
            e3.printStackTrace();
            return "";
        }
    }

    public static String saveBitmapByPath(Context context, String str, String str2, Bitmap bitmap) throws IOException {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, str2);
            if (file2.exists()) {
                file2.delete();
                file2.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file2, false);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            return file2.getAbsolutePath();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return "";
        } catch (IOException e3) {
            e3.printStackTrace();
            return "";
        }
    }

    public static Bitmap saveRoundBitmap(Bitmap bitmap) {
        float f2;
        float f3;
        float f4;
        float f5;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= height) {
            f5 = width / 2;
            f4 = width;
            f2 = 0.0f;
            f3 = f4;
        } else {
            f2 = (width - height) / 2;
            f3 = height;
            f4 = width - f2;
            width = height;
            f5 = height / 2;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect((int) f2, (int) 0.0f, (int) f4, (int) f3);
        Rect rect2 = new Rect((int) 0.0f, (int) 0.0f, (int) f3, (int) f3);
        new RectF(rect2);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0);
        canvas.drawCircle(f5, f5, f5, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect2, paint);
        return bitmapCreateBitmap;
    }
}
