package io.flutter.plugins.imagepicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.SizeFCompat;
import androidx.exifinterface.media.ExifInterface;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
class ImageResizer {
    private final Context context;
    private final ExifDataCopier exifDataCopier;

    ImageResizer(@NonNull Context context, @NonNull ExifDataCopier exifDataCopier) {
        this.context = context;
        this.exifDataCopier = exifDataCopier;
    }

    private int calculateSampleSize(BitmapFactory.Options options, int i2, int i3) {
        int i4 = options.outHeight;
        int i5 = options.outWidth;
        int i6 = 1;
        if (i4 > i3 || i5 > i2) {
            int i7 = i4 / 2;
            int i8 = i5 / 2;
            while (i7 / i6 >= i3 && i8 / i6 >= i2) {
                i6 *= 2;
            }
        }
        return i6;
    }

    private SizeFCompat calculateTargetSize(double d2, double d3, @Nullable Double d4, @Nullable Double d5) {
        double d6 = d2 / d3;
        boolean z2 = false;
        boolean z3 = d4 != null;
        boolean z4 = d5 != null;
        double dMin = z3 ? Math.min(d2, Math.round(d4.doubleValue())) : d2;
        double dMin2 = z4 ? Math.min(d3, Math.round(d5.doubleValue())) : d3;
        boolean z5 = z3 && d4.doubleValue() < d2;
        if (z4 && d5.doubleValue() < d3) {
            z2 = true;
        }
        if (z5 || z2) {
            double d7 = dMin2 * d6;
            double d8 = dMin / d6;
            if (d8 > dMin2) {
                dMin = Math.round(d7);
            } else {
                dMin2 = Math.round(d8);
            }
        }
        return new SizeFCompat((float) dMin, (float) dMin2);
    }

    private void copyExif(String str, String str2) throws Throwable {
        try {
            this.exifDataCopier.copyExif(new ExifInterface(str), new ExifInterface(str2));
        } catch (Exception e2) {
            Log.e("ImageResizer", "Error preserving Exif data on selected image: " + e2);
        }
    }

    private File createFile(File file, String str) {
        File file2 = new File(file, str);
        if (!file2.getParentFile().exists()) {
            file2.getParentFile().mkdirs();
        }
        return file2;
    }

    private File createImageOnExternalDirectory(String str, Bitmap bitmap, int i2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        boolean zHasAlpha = bitmap.hasAlpha();
        if (zHasAlpha) {
            Log.d("ImageResizer", "image_picker: compressing is not supported for type PNG. Returning the image with original quality");
        }
        bitmap.compress(zHasAlpha ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
        File fileCreateFile = createFile(this.context.getCacheDir(), str);
        FileOutputStream fileOutputStreamCreateOutputStream = createOutputStream(fileCreateFile);
        fileOutputStreamCreateOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStreamCreateOutputStream.close();
        return fileCreateFile;
    }

    private FileOutputStream createOutputStream(File file) throws IOException {
        return new FileOutputStream(file);
    }

    private Bitmap createScaledBitmap(Bitmap bitmap, int i2, int i3, boolean z2) {
        return Bitmap.createScaledBitmap(bitmap, i2, i3, z2);
    }

    private Bitmap decodeFile(String str, @Nullable BitmapFactory.Options options) {
        return BitmapFactory.decodeFile(str, options);
    }

    private File resizedImage(Bitmap bitmap, Double d2, Double d3, int i2, String str) throws IOException {
        return createImageOnExternalDirectory("/scaled_" + str, createScaledBitmap(bitmap, d2.intValue(), d3.intValue(), false), i2);
    }

    @VisibleForTesting
    SizeFCompat readFileDimensions(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeFile(str, options);
        return new SizeFCompat(options.outWidth, options.outHeight);
    }

    String resizeImageIfNeeded(String str, @Nullable Double d2, @Nullable Double d3, int i2) throws Throwable {
        SizeFCompat fileDimensions = readFileDimensions(str);
        if (fileDimensions.getWidth() == -1.0f || fileDimensions.getHeight() == -1.0f) {
            return str;
        }
        if (d2 == null && d3 == null && i2 >= 100) {
            return str;
        }
        try {
            String str2 = str.split("/")[r2.length - 1];
            SizeFCompat sizeFCompatCalculateTargetSize = calculateTargetSize(fileDimensions.getWidth(), fileDimensions.getHeight(), d2, d3);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = calculateSampleSize(options, (int) sizeFCompatCalculateTargetSize.getWidth(), (int) sizeFCompatCalculateTargetSize.getHeight());
            Bitmap bitmapDecodeFile = decodeFile(str, options);
            if (bitmapDecodeFile == null) {
                return str;
            }
            File fileResizedImage = resizedImage(bitmapDecodeFile, Double.valueOf(sizeFCompatCalculateTargetSize.getWidth()), Double.valueOf(sizeFCompatCalculateTargetSize.getHeight()), i2, str2);
            copyExif(str, fileResizedImage.getPath());
            return fileResizedImage.getPath();
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }
}
