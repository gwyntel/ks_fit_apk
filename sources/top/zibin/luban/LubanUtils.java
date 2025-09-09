package top.zibin.luban;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import java.util.Locale;

/* loaded from: classes5.dex */
public class LubanUtils {
    private static final String TAG = "Luban";

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            } catch (IllegalArgumentException e2) {
                Log.i(TAG, String.format(Locale.getDefault(), "getDataColumn: _data - [%s]", e2.getMessage()));
                if (cursorQuery == null) {
                    return "";
                }
            }
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                cursorQuery.close();
                return string;
            }
            if (cursorQuery == null) {
                return "";
            }
            cursorQuery.close();
            return "";
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    @SuppressLint({"NewApi"})
    public static String getPath(Context context, Uri uri) {
        Context applicationContext = context.getApplicationContext();
        int i2 = Build.VERSION.SDK_INT;
        Uri uri2 = null;
        if (!DocumentsContract.isDocumentUri(applicationContext, uri)) {
            return "content".equalsIgnoreCase(uri.getScheme()) ? isGooglePhotosUri(uri) ? uri.getLastPathSegment() : getDataColumn(applicationContext, uri, null, null) : "file".equalsIgnoreCase(uri.getScheme()) ? uri.getPath() : "";
        }
        if (!isExternalStorageDocument(uri)) {
            if (isDownloadsDocument(uri)) {
                return getDataColumn(applicationContext, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(DocumentsContract.getDocumentId(uri))), null, null);
            }
            if (!isMediaDocument(uri)) {
                return "";
            }
            String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
            String str = strArrSplit[0];
            if ("image".equals(str)) {
                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(str)) {
                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(str)) {
                uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            return getDataColumn(applicationContext, uri2, "_id=?", new String[]{strArrSplit[1]});
        }
        String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
        if (!"primary".equalsIgnoreCase(strArrSplit2[0])) {
            return "";
        }
        if (i2 >= 29) {
            return applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + strArrSplit2[1];
        }
        return Environment.getExternalStorageDirectory() + "/" + strArrSplit2[1];
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
}
