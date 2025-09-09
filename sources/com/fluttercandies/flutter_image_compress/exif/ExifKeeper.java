package com.fluttercandies.flutter_image_compress.exif;

import android.content.Context;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.IOUtils;

/* loaded from: classes3.dex */
public class ExifKeeper {
    private static final List<String> attributes = Arrays.asList(ExifInterface.TAG_F_NUMBER, ExifInterface.TAG_EXPOSURE_TIME, ExifInterface.TAG_ISO_SPEED_RATINGS, ExifInterface.TAG_GPS_ALTITUDE, ExifInterface.TAG_GPS_ALTITUDE_REF, ExifInterface.TAG_FOCAL_LENGTH, ExifInterface.TAG_GPS_DATESTAMP, ExifInterface.TAG_WHITE_BALANCE, ExifInterface.TAG_GPS_PROCESSING_METHOD, ExifInterface.TAG_GPS_TIMESTAMP, ExifInterface.TAG_DATETIME, ExifInterface.TAG_FLASH, ExifInterface.TAG_GPS_LATITUDE, ExifInterface.TAG_GPS_LATITUDE_REF, ExifInterface.TAG_GPS_LONGITUDE, ExifInterface.TAG_GPS_LONGITUDE_REF, ExifInterface.TAG_MAKE, ExifInterface.TAG_MODEL);
    private final ExifInterface oldExif;

    public ExifKeeper(String str) throws IOException {
        this.oldExif = new ExifInterface(str);
    }

    private static void copyExif(ExifInterface exifInterface, ExifInterface exifInterface2) throws Throwable {
        Iterator<String> it = attributes.iterator();
        while (it.hasNext()) {
            setIfNotNull(exifInterface, exifInterface2, it.next());
        }
        try {
            exifInterface2.saveAttributes();
        } catch (IOException unused) {
        }
    }

    private static void setIfNotNull(ExifInterface exifInterface, ExifInterface exifInterface2, String str) throws NumberFormatException {
        if (exifInterface.getAttribute(str) != null) {
            exifInterface2.setAttribute(str, exifInterface.getAttribute(str));
        }
    }

    public void copyExifToFile(File file) throws Throwable {
        try {
            ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
            copyExif(this.oldExif, exifInterface);
            exifInterface.saveAttributes();
        } catch (IOException unused) {
        }
    }

    public ByteArrayOutputStream writeToOutputStream(Context context, ByteArrayOutputStream byteArrayOutputStream) throws Throwable {
        FileInputStream fileInputStream;
        Exception e2;
        FileOutputStream fileOutputStream;
        try {
            String string = UUID.randomUUID().toString();
            File file = new File(context.getCacheDir(), string + PictureMimeType.JPG);
            fileOutputStream = new FileOutputStream(file);
            try {
                IOUtils.write(byteArrayOutputStream.toByteArray(), fileOutputStream);
                fileOutputStream.close();
                ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
                copyExif(this.oldExif, exifInterface);
                exifInterface.saveAttributes();
                fileOutputStream.close();
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                fileInputStream = new FileInputStream(file);
                try {
                    IOUtils.copy(fileInputStream, byteArrayOutputStream2);
                    fileInputStream.close();
                    return byteArrayOutputStream2;
                } catch (Exception e3) {
                    e2 = e3;
                    Log.e("ExifDataCopier", "Error preserving Exif data on selected image: " + e2);
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    return byteArrayOutputStream;
                }
            } catch (Exception e6) {
                fileInputStream = null;
                e2 = e6;
            }
        } catch (Exception e7) {
            fileInputStream = null;
            e2 = e7;
            fileOutputStream = null;
        }
    }

    public ExifKeeper(byte[] bArr) throws IOException {
        this.oldExif = new ExifInterface(new ByteArrayInputStream(bArr));
    }
}
