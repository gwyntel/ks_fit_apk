package vn.hunghd.flutter.plugins.imagecropper;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes5.dex */
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

    private static String getDataColumn(Context context, Uri uri, String str, String[] strArr) throws Throwable {
        Cursor cursor = null;
        try {
            Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                        cursorQuery.close();
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @SuppressLint({"NewApi"})
    private String getPathFromLocalUri(Context context, Uri uri) {
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
                    } catch (NumberFormatException unused) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r4v12, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getPathFromRemoteUri(android.content.Context r4, android.net.Uri r5) throws java.lang.Throwable {
        /*
            r0 = 0
            r1 = 0
            android.content.ContentResolver r2 = r4.getContentResolver()     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L3b
            java.io.InputStream r5 = r2.openInputStream(r5)     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L3b
            java.lang.String r2 = "image_picker"
            java.lang.String r3 = "jpg"
            java.io.File r4 = r4.getCacheDir()     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L35
            java.io.File r4 = java.io.File.createTempFile(r2, r3, r4)     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L35
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L33
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L33
            if (r5 == 0) goto L25
            copy(r5, r2)     // Catch: java.lang.Throwable -> L22 java.io.IOException -> L4a
            r3 = 1
            goto L26
        L22:
            r4 = move-exception
        L23:
            r0 = r5
            goto L3f
        L25:
            r3 = r1
        L26:
            if (r5 == 0) goto L2b
            r5.close()     // Catch: java.io.IOException -> L2b
        L2b:
            r2.close()     // Catch: java.io.IOException -> L54
            r1 = r3
            goto L54
        L30:
            r4 = move-exception
            r2 = r0
            goto L23
        L33:
            r2 = r0
            goto L4a
        L35:
            r4 = r0
            r2 = r4
            goto L4a
        L38:
            r4 = move-exception
            r2 = r0
            goto L3f
        L3b:
            r4 = r0
            r5 = r4
            r2 = r5
            goto L4a
        L3f:
            if (r0 == 0) goto L44
            r0.close()     // Catch: java.io.IOException -> L44
        L44:
            if (r2 == 0) goto L49
            r2.close()     // Catch: java.io.IOException -> L49
        L49:
            throw r4
        L4a:
            if (r5 == 0) goto L4f
            r5.close()     // Catch: java.io.IOException -> L4f
        L4f:
            if (r2 == 0) goto L54
            r2.close()     // Catch: java.io.IOException -> L54
        L54:
            if (r1 == 0) goto L5a
            java.lang.String r0 = r4.getPath()
        L5a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: vn.hunghd.flutter.plugins.imagecropper.FileUtils.getPathFromRemoteUri(android.content.Context, android.net.Uri):java.lang.String");
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    String a(Context context, Uri uri) {
        String pathFromLocalUri = getPathFromLocalUri(context, uri);
        return pathFromLocalUri == null ? getPathFromRemoteUri(context, uri) : pathFromLocalUri;
    }
}
