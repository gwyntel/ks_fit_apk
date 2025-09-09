package com.yalantis.ucrop.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/* loaded from: classes4.dex */
public class FileUtils {
    private static final String TAG = "FileUtils";

    private FileUtils() {
    }

    public static void copyFile(@NonNull String str, @NonNull String str2) throws Throwable {
        FileChannel fileChannel;
        FileChannel channel;
        if (str.equalsIgnoreCase(str2)) {
            return;
        }
        FileChannel channel2 = null;
        try {
            channel = new FileInputStream(new File(str)).getChannel();
        } catch (Throwable th) {
            th = th;
            fileChannel = null;
        }
        try {
            channel2 = new FileOutputStream(new File(str2)).getChannel();
            channel.transferTo(0L, channel.size(), channel2);
            channel.close();
            if (channel2 != null) {
                channel2.close();
            }
        } catch (Throwable th2) {
            th = th2;
            FileChannel fileChannel2 = channel2;
            channel2 = channel;
            fileChannel = fileChannel2;
            if (channel2 != null) {
                channel2.close();
            }
            if (fileChannel != null) {
                fileChannel.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002e A[PHI: r8
      0x002e: PHI (r8v3 android.database.Cursor) = (r8v2 android.database.Cursor), (r8v4 android.database.Cursor) binds: [B:21:0x004f, B:14:0x002c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0055  */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r7v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getDataColumn(android.content.Context r8, android.net.Uri r9, java.lang.String r10, java.lang.String[] r11) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "_data"
            java.lang.String[] r3 = new java.lang.String[]{r0}
            r7 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L32 java.lang.IllegalArgumentException -> L34
            r6 = 0
            r2 = r9
            r4 = r10
            r5 = r11
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L32 java.lang.IllegalArgumentException -> L34
            if (r8 == 0) goto L2c
            boolean r9 = r8.moveToFirst()     // Catch: java.lang.Throwable -> L27 java.lang.IllegalArgumentException -> L2a
            if (r9 == 0) goto L2c
            int r9 = r8.getColumnIndexOrThrow(r0)     // Catch: java.lang.Throwable -> L27 java.lang.IllegalArgumentException -> L2a
            java.lang.String r9 = r8.getString(r9)     // Catch: java.lang.Throwable -> L27 java.lang.IllegalArgumentException -> L2a
            r8.close()
            return r9
        L27:
            r9 = move-exception
            r7 = r8
            goto L53
        L2a:
            r9 = move-exception
            goto L36
        L2c:
            if (r8 == 0) goto L52
        L2e:
            r8.close()
            goto L52
        L32:
            r9 = move-exception
            goto L53
        L34:
            r9 = move-exception
            r8 = r7
        L36:
            java.lang.String r10 = "FileUtils"
            java.util.Locale r11 = java.util.Locale.getDefault()     // Catch: java.lang.Throwable -> L27
            java.lang.String r0 = "getDataColumn: _data - [%s]"
            java.lang.String r9 = r9.getMessage()     // Catch: java.lang.Throwable -> L27
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L27
            r2 = 0
            r1[r2] = r9     // Catch: java.lang.Throwable -> L27
            java.lang.String r9 = java.lang.String.format(r11, r0, r1)     // Catch: java.lang.Throwable -> L27
            android.util.Log.i(r10, r9)     // Catch: java.lang.Throwable -> L27
            if (r8 == 0) goto L52
            goto L2e
        L52:
            return r7
        L53:
            if (r7 == 0) goto L58
            r7.close()
        L58:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yalantis.ucrop.util.FileUtils.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    @SuppressLint({"NewApi"})
    public static String getPath(Context context, Uri uri) {
        Uri uri2 = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(strArrSplit[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + strArrSplit[1];
                }
            } else if (isDownloadsDocument(uri)) {
                String documentId = DocumentsContract.getDocumentId(uri);
                if (!TextUtils.isEmpty(documentId)) {
                    try {
                        return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId).longValue()), null, null);
                    } catch (NumberFormatException e2) {
                        Log.i(TAG, e2.getMessage());
                        return null;
                    }
                }
            } else if (isMediaDocument(uri)) {
                String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
                String str = strArrSplit2[0];
                if ("image".equals(str)) {
                    uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str)) {
                    uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(str)) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                return getDataColumn(context, uri2, "_id=?", new String[]{strArrSplit2[1]});
            }
        } else {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                return isGooglePhotosUri(uri) ? uri.getLastPathSegment() : getDataColumn(context, uri, null, null);
            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static void copyFile(@NonNull Context context, @NonNull Uri uri, @NonNull Uri uri2) throws Throwable {
        OutputStream outputStream;
        InputStream inputStreamOpenInputStream;
        if (uri.equals(uri2)) {
            return;
        }
        InputStream inputStream = null;
        try {
            inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
        } catch (Throwable th) {
            th = th;
            outputStream = null;
        }
        try {
            OutputStream outputStreamOpenOutputStream = context.getContentResolver().openOutputStream(uri2);
            if ((inputStreamOpenInputStream instanceof FileInputStream) && (outputStreamOpenOutputStream instanceof FileOutputStream)) {
                FileChannel channel = ((FileInputStream) inputStreamOpenInputStream).getChannel();
                channel.transferTo(0L, channel.size(), ((FileOutputStream) outputStreamOpenOutputStream).getChannel());
                if (inputStreamOpenInputStream != null) {
                    inputStreamOpenInputStream.close();
                }
                if (outputStreamOpenOutputStream != null) {
                    outputStreamOpenOutputStream.close();
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("The input or output URI don't represent a file. uCrop requires then to represent files in order to work properly.");
        } catch (Throwable th2) {
            th = th2;
            inputStream = inputStreamOpenInputStream;
            outputStream = null;
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            throw th;
        }
    }
}
