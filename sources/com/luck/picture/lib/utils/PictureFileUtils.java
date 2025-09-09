package com.luck.picture.lib.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.media3.common.C;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes4.dex */
public class PictureFileUtils {
    private static final int BYTE_SIZE = 1024;
    private static final String POSTFIX_AMR = ".amr";
    private static final String POSTFIX_JPG = ".jpg";
    private static final String POSTFIX_MP4 = ".mp4";

    private PictureFileUtils() {
    }

    public static void close(@Nullable Closeable closeable) {
        if (closeable instanceof Closeable) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static void copyFile(@NonNull String str, @NonNull String str2) throws Throwable {
        FileChannel fileChannel;
        if (str.equalsIgnoreCase(str2)) {
            return;
        }
        FileChannel channel = null;
        try {
            FileChannel channel2 = new FileInputStream(str).getChannel();
            try {
                channel = new FileOutputStream(str2).getChannel();
                channel2.transferTo(0L, channel2.size(), channel);
                close(channel2);
                close(channel);
            } catch (Exception e2) {
                e = e2;
                FileChannel fileChannel2 = channel;
                channel = channel2;
                fileChannel = fileChannel2;
                try {
                    e.printStackTrace();
                    close(channel);
                    close(fileChannel);
                } catch (Throwable th) {
                    th = th;
                    close(channel);
                    close(fileChannel);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                FileChannel fileChannel3 = channel;
                channel = channel2;
                fileChannel = fileChannel3;
                close(channel);
                close(fileChannel);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            fileChannel = null;
        } catch (Throwable th3) {
            th = th3;
            fileChannel = null;
        }
    }

    public static File createCameraFile(Context context, int i2, String str, String str2, String str3) {
        return createMediaFile(context, i2, str, str2, str3);
    }

    public static String createFilePath(Context context, String str, String str2) {
        File rootDirFile;
        String str3;
        String lastSourceSuffix = PictureMimeType.getLastSourceSuffix(str);
        if (PictureMimeType.isHasVideo(str)) {
            rootDirFile = getRootDirFile(context, 2);
            str3 = "VID_";
        } else if (PictureMimeType.isHasAudio(str)) {
            rootDirFile = getRootDirFile(context, 3);
            str3 = "AUD_";
        } else {
            rootDirFile = getRootDirFile(context, 1);
            str3 = "IMG_";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(rootDirFile.getPath());
        sb.append(File.separator);
        if (TextUtils.isEmpty(str2)) {
            str2 = DateUtils.getCreateFileName(str3) + lastSourceSuffix;
        }
        sb.append(str2);
        return sb.toString();
    }

    private static File createMediaFile(Context context, int i2, String str, String str2, String str3) {
        return createOutFile(context, i2, str, str2, str3);
    }

    private static File createOutFile(Context context, int i2, String str, String str2, String str3) {
        File file;
        File rootDirFile;
        Context applicationContext = context.getApplicationContext();
        if (TextUtils.isEmpty(str3)) {
            if (TextUtils.equals("mounted", Environment.getExternalStorageState())) {
                rootDirFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                StringBuilder sb = new StringBuilder();
                sb.append(rootDirFile.getAbsolutePath());
                String str4 = File.separator;
                sb.append(str4);
                sb.append(PictureMimeType.CAMERA);
                sb.append(str4);
                file = new File(sb.toString());
            } else {
                rootDirFile = getRootDirFile(applicationContext, i2);
                file = new File(rootDirFile.getAbsolutePath() + File.separator);
            }
            if (!rootDirFile.exists()) {
                rootDirFile.mkdirs();
            }
        } else {
            File file2 = new File(str3);
            File parentFile = file2.getParentFile();
            Objects.requireNonNull(parentFile);
            if (!parentFile.exists()) {
                file2.getParentFile().mkdirs();
            }
            file = file2;
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        boolean zIsEmpty = TextUtils.isEmpty(str);
        if (i2 == 2) {
            if (zIsEmpty) {
                str = DateUtils.getCreateFileName("VID_") + ".mp4";
            }
            return new File(file, str);
        }
        if (i2 == 3) {
            if (zIsEmpty) {
                str = DateUtils.getCreateFileName("AUD_") + ".amr";
            }
            return new File(file, str);
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = ".jpg";
        }
        if (zIsEmpty) {
            str = DateUtils.getCreateFileName("IMG_") + str2;
        }
        return new File(file, str);
    }

    @Deprecated
    public static void deleteAllCacheDirFile(Context context) {
        File[] fileArrListFiles;
        File[] fileArrListFiles2;
        File[] fileArrListFiles3;
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir != null && (fileArrListFiles3 = externalFilesDir.listFiles()) != null) {
            for (File file : fileArrListFiles3) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
        File externalFilesDir2 = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        if (externalFilesDir2 != null && (fileArrListFiles2 = externalFilesDir2.listFiles()) != null) {
            for (File file2 : fileArrListFiles2) {
                if (file2.isFile()) {
                    file2.delete();
                }
            }
        }
        File externalFilesDir3 = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        if (externalFilesDir3 == null || (fileArrListFiles = externalFilesDir3.listFiles()) == null) {
            return;
        }
        for (File file3 : fileArrListFiles) {
            if (file3.isFile()) {
                file3.delete();
            }
        }
    }

    @Deprecated
    public static void deleteCacheDirFile(Context context, int i2) {
        File[] fileArrListFiles;
        File externalFilesDir = context.getExternalFilesDir(i2 == SelectMimeType.ofImage() ? Environment.DIRECTORY_PICTURES : Environment.DIRECTORY_MOVIES);
        if (externalFilesDir == null || (fileArrListFiles = externalFilesDir.listFiles()) == null) {
            return;
        }
        for (File file : fileArrListFiles) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }

    @SuppressLint({"DefaultLocale"})
    public static String formatAccurateUnitFileSize(long j2) {
        double d2;
        String str;
        if (j2 < 0) {
            throw new IllegalArgumentException("byteSize shouldn't be less than zero!");
        }
        if (j2 < 1000) {
            d2 = j2;
            str = "";
        } else if (j2 < 1000000) {
            d2 = j2 / 1000.0d;
            str = "KB";
        } else if (j2 < C.NANOS_PER_SECOND) {
            d2 = j2 / 1000000.0d;
            str = "MB";
        } else {
            d2 = j2 / 1.0E9d;
            str = "GB";
        }
        Object objValueOf = String.format(new Locale("zh"), "%.2f", Double.valueOf(d2));
        StringBuilder sb = new StringBuilder();
        if (Math.round(ValueOf.toDouble(objValueOf)) - ValueOf.toDouble(objValueOf) == 0.0d) {
            objValueOf = Long.valueOf(Math.round(ValueOf.toDouble(objValueOf)));
        }
        sb.append(objValueOf);
        sb.append(str);
        return sb.toString();
    }

    @SuppressLint({"DefaultLocale"})
    public static String formatFileSize(long j2) {
        if (j2 < 0) {
            throw new IllegalArgumentException("byteSize shouldn't be less than zero!");
        }
        if (j2 < 1024) {
            Object objValueOf = String.format("%.2f", Double.valueOf(j2));
            double d2 = ValueOf.toDouble(objValueOf);
            long jRound = Math.round(d2);
            StringBuilder sb = new StringBuilder();
            if (jRound - d2 == 0.0d) {
                objValueOf = Long.valueOf(jRound);
            }
            sb.append(objValueOf);
            sb.append("B");
            return sb.toString();
        }
        if (j2 < 1048576) {
            Object objValueOf2 = String.format("%.2f", Double.valueOf(j2 / 1024.0d));
            double d3 = ValueOf.toDouble(objValueOf2);
            long jRound2 = Math.round(d3);
            StringBuilder sb2 = new StringBuilder();
            if (jRound2 - d3 == 0.0d) {
                objValueOf2 = Long.valueOf(jRound2);
            }
            sb2.append(objValueOf2);
            sb2.append("KB");
            return sb2.toString();
        }
        if (j2 < 1073741824) {
            Object objValueOf3 = String.format("%.2f", Double.valueOf(j2 / 1048576.0d));
            double d4 = ValueOf.toDouble(objValueOf3);
            long jRound3 = Math.round(d4);
            StringBuilder sb3 = new StringBuilder();
            if (jRound3 - d4 == 0.0d) {
                objValueOf3 = Long.valueOf(jRound3);
            }
            sb3.append(objValueOf3);
            sb3.append("MB");
            return sb3.toString();
        }
        Object objValueOf4 = String.format("%.2f", Double.valueOf(j2 / 1.073741824E9d));
        double d5 = ValueOf.toDouble(objValueOf4);
        long jRound4 = Math.round(d5);
        StringBuilder sb4 = new StringBuilder();
        if (jRound4 - d5 == 0.0d) {
            objValueOf4 = Long.valueOf(jRound4);
        }
        sb4.append(objValueOf4);
        sb4.append("GB");
        return sb4.toString();
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            } catch (IllegalArgumentException e2) {
                Log.i("PictureFileUtils", String.format(Locale.getDefault(), "getDataColumn: _data - [%s]", e2.getMessage()));
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
        Uri uri2 = null;
        if (!DocumentsContract.isDocumentUri(applicationContext, uri)) {
            return "content".equalsIgnoreCase(uri.getScheme()) ? isGooglePhotosUri(uri) ? uri.getLastPathSegment() : getDataColumn(applicationContext, uri, null, null) : "file".equalsIgnoreCase(uri.getScheme()) ? uri.getPath() : "";
        }
        if (!isExternalStorageDocument(uri)) {
            if (isDownloadsDocument(uri)) {
                return getDataColumn(applicationContext, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), ValueOf.toLong(DocumentsContract.getDocumentId(uri))), null, null);
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
        if (SdkVersionUtils.isQ()) {
            return applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + strArrSplit2[1];
        }
        return Environment.getExternalStorageDirectory() + "/" + strArrSplit2[1];
    }

    private static File getRootDirFile(Context context, int i2) {
        return new File(FileDirMap.getFileDirPath(context, i2));
    }

    public static String getVideoThumbnailDir(Context context) {
        File file = new File(context.getExternalFilesDir("").getAbsolutePath(), "VideoThumbnail");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath() + File.separator;
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isFileExists(String str) {
        return !TextUtils.isEmpty(str) && new File(str).exists();
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isImageFileExists(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 2;
        BitmapFactory.decodeFile(str, options);
        return options.outWidth > 0 && options.outHeight > 0;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static Uri parUri(Context context, File file) {
        return Build.VERSION.SDK_INT > 23 ? FileProvider.getUriForFile(context, context.getPackageName() + ".luckProvider", file) : Uri.fromFile(file);
    }

    public static boolean writeFileFromIS(InputStream inputStream, OutputStream outputStream) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        BufferedInputStream bufferedInputStream = null;
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(inputStream);
            try {
                bufferedOutputStream = new BufferedOutputStream(outputStream);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i2 = bufferedInputStream2.read(bArr);
                        if (i2 == -1) {
                            outputStream.flush();
                            close(bufferedInputStream2);
                            close(bufferedOutputStream);
                            return true;
                        }
                        outputStream.write(bArr, 0, i2);
                    }
                } catch (Exception e2) {
                    e = e2;
                    bufferedInputStream = bufferedInputStream2;
                    try {
                        e.printStackTrace();
                        close(bufferedInputStream);
                        close(bufferedOutputStream);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        close(bufferedInputStream);
                        close(bufferedOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedInputStream = bufferedInputStream2;
                    close(bufferedInputStream);
                    close(bufferedOutputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                bufferedOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedOutputStream = null;
            }
        } catch (Exception e4) {
            e = e4;
            bufferedOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            bufferedOutputStream = null;
        }
    }
}
