package io.flutter.plugins.imagepicker;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import com.luck.picture.lib.config.PictureMimeType;
import io.flutter.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/* loaded from: classes4.dex */
class FileUtils {
    FileUtils() {
    }

    private static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[4096];
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 == -1) {
                outputStream.flush();
                return;
            }
            outputStream.write(bArr, 0, i2);
        }
    }

    private static String getBaseName(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf < 0 ? str : str.substring(0, iLastIndexOf);
    }

    private static String getImageExtension(Context context, Uri uri) {
        try {
            String extensionFromMimeType = uri.getScheme().equals("content") ? MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType(uri)) : MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
            if (extensionFromMimeType != null && !extensionFromMimeType.isEmpty()) {
                return "." + extensionFromMimeType;
            }
        } catch (Exception unused) {
        }
        return null;
    }

    private static String getImageName(Context context, Uri uri) {
        Cursor cursorQueryImageName = queryImageName(context, uri);
        if (cursorQueryImageName != null) {
            try {
                if (cursorQueryImageName.moveToFirst() && cursorQueryImageName.getColumnCount() >= 1) {
                    String string = cursorQueryImageName.getString(0);
                    cursorQueryImageName.close();
                    return string;
                }
            } catch (Throwable th) {
                try {
                    cursorQueryImageName.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (cursorQueryImageName == null) {
            return null;
        }
        cursorQueryImageName.close();
        return null;
    }

    private static Cursor queryImageName(Context context, Uri uri) {
        return context.getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
    }

    String getPathFromUri(Context context, Uri uri) throws IOException {
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            try {
                File file = new File(context.getCacheDir(), UUID.randomUUID().toString());
                file.mkdir();
                file.deleteOnExit();
                String imageName = getImageName(context, uri);
                String imageExtension = getImageExtension(context, uri);
                if (imageName == null) {
                    Log.w("FileUtils", "Cannot get file name for " + uri);
                    if (imageExtension == null) {
                        imageExtension = PictureMimeType.JPG;
                    }
                    imageName = "image_picker" + imageExtension;
                } else if (imageExtension != null) {
                    imageName = getBaseName(imageName) + imageExtension;
                }
                File file2 = new File(file, imageName);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                try {
                    copy(inputStreamOpenInputStream, fileOutputStream);
                    String path = file2.getPath();
                    fileOutputStream.close();
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
                    return path;
                } finally {
                }
            } catch (Throwable th) {
                if (inputStreamOpenInputStream != null) {
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException | SecurityException unused) {
            return null;
        }
    }
}
