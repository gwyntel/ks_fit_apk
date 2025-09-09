package com.leeson.image_pickers.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import androidx.media3.common.MimeTypes;
import androidx.webkit.internal.AssetHelper;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.leeson.image_pickers.AppPath;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.utils.PictureFileUtils;
import com.umeng.analytics.pro.bg;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/* loaded from: classes4.dex */
public class Saver {
    private final String[][] MIME_MapTable = {new String[]{".3gp", MimeTypes.VIDEO_H263}, new String[]{".apk", "application/vnd.android.package-archive"}, new String[]{".asf", "video/x-ms-asf"}, new String[]{PictureMimeType.AVI, MimeTypes.VIDEO_AVI}, new String[]{".bin", OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE}, new String[]{PictureMimeType.BMP, MimeTypes.IMAGE_BMP}, new String[]{".c", AssetHelper.DEFAULT_MIME_TYPE}, new String[]{".class", OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE}, new String[]{".conf", AssetHelper.DEFAULT_MIME_TYPE}, new String[]{".cpp", AssetHelper.DEFAULT_MIME_TYPE}, new String[]{".doc", "application/msword"}, new String[]{".exe", OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE}, new String[]{PictureMimeType.GIF, "image/gif"}, new String[]{".gtar", "application/x-gtar"}, new String[]{".gz", "application/x-gzip"}, new String[]{".h", AssetHelper.DEFAULT_MIME_TYPE}, new String[]{".htm", "text/html"}, new String[]{".html", "text/html"}, new String[]{".jar", "application/java-archive"}, new String[]{".java", AssetHelper.DEFAULT_MIME_TYPE}, new String[]{PictureMimeType.JPEG, "image/jpeg"}, new String[]{PictureMimeType.JPG, "image/jpeg"}, new String[]{".js", "application/x-javascript"}, new String[]{".log", AssetHelper.DEFAULT_MIME_TYPE}, new String[]{".m3u", "audio/x-mpegurl"}, new String[]{".m4a", MimeTypes.AUDIO_AAC}, new String[]{".m4b", MimeTypes.AUDIO_AAC}, new String[]{".m4p", MimeTypes.AUDIO_AAC}, new String[]{".m4u", "video/vnd.mpegurl"}, new String[]{".m4v", "video/x-m4v"}, new String[]{".mov", "video/quicktime"}, new String[]{".mp2", "audio/x-mpeg"}, new String[]{PictureMimeType.MP3, "audio/x-mpeg"}, new String[]{PictureMimeType.MP4, "video/mp4"}, new String[]{".mpc", "application/vnd.mpohun.certificate"}, new String[]{".mpe", MimeTypes.VIDEO_MPEG}, new String[]{".mpeg", MimeTypes.VIDEO_MPEG}, new String[]{".mpg", MimeTypes.VIDEO_MPEG}, new String[]{".mpg4", "video/mp4"}, new String[]{".mpga", "audio/mpeg"}, new String[]{".msg", "application/vnd.ms-outlook"}, new String[]{".ogg", MimeTypes.AUDIO_OGG}, new String[]{".pdf", "application/pdf"}, new String[]{PictureMimeType.PNG, "image/png"}, new String[]{".pps", "application/vnd.ms-powerpoint"}, new String[]{".ppt", "application/vnd.ms-powerpoint"}, new String[]{".prop", AssetHelper.DEFAULT_MIME_TYPE}, new String[]{".rar", "application/x-rar-compressed"}, new String[]{".rc", AssetHelper.DEFAULT_MIME_TYPE}, new String[]{".rmvb", "audio/x-pn-realaudio"}, new String[]{".rtf", "application/rtf"}, new String[]{".sh", AssetHelper.DEFAULT_MIME_TYPE}, new String[]{".tar", "application/x-tar"}, new String[]{".tgz", "application/x-compressed"}, new String[]{".txt", AssetHelper.DEFAULT_MIME_TYPE}, new String[]{PictureMimeType.WAV, PictureMimeType.WAV_Q}, new String[]{".wma", "audio/x-ms-wma"}, new String[]{".wmv", "audio/x-ms-wmv"}, new String[]{".wps", "application/vnd.ms-works"}, new String[]{".xml", AssetHelper.DEFAULT_MIME_TYPE}, new String[]{".z", "application/x-compress"}, new String[]{".zip", "application/zip"}, new String[]{".xlm", "application/vnd.ms-excel"}, new String[]{".xls", "application/vnd.ms-excel"}, new String[]{".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, new String[]{".xlt", "application/vnd.ms-excel"}, new String[]{".xlw", "application/vnd.ms-excel"}, new String[]{".xm", "audio/x-mod"}, new String[]{".xml", AssetHelper.DEFAULT_MIME_TYPE}, new String[]{".xml", "application/xml"}, new String[]{".xmz", "audio/x-mod"}, new String[]{".xof", "x-world/x-vrml"}, new String[]{".xpi", "application/x-xpinstall"}, new String[]{".xpm", "image/x-xpixmap"}, new String[]{".xsit", "text/xml"}, new String[]{".xsl", "text/xml"}, new String[]{".xul", "text/xul"}, new String[]{".xwd", "image/x-xwindowdump"}, new String[]{".xyz", "chemical/x-pdb"}, new String[]{".yz1", "application/x-yz1"}, new String[]{".z", "application/x-compress"}, new String[]{".zac", "application/x-zaurus-zac"}, new String[]{".docx", "application/msword"}, new String[]{".1", "application/vnd.android.package-archive"}};
    private AppPath appPath;
    private Context context;
    private MediaScannerConnection mediaScannerConnection;

    public static class FileInfo {
        private boolean isBeforeDownload;
        private String path;
        private long size;
        private Uri uri;

        public String getPath() {
            return this.path;
        }

        public long getSize() {
            return this.size;
        }

        public Uri getUri() {
            return this.uri;
        }

        public boolean isBeforeDownload() {
            return this.isBeforeDownload;
        }

        public void setBeforeDownload(boolean z2) {
            this.isBeforeDownload = z2;
        }

        public void setPath(String str) {
            this.path = str;
        }

        public void setSize(long j2) {
            this.size = j2;
        }

        public void setUri(Uri uri) {
            this.uri = uri;
        }

        public String toString() {
            return "FileInfo{size=" + this.size + ", uri=" + this.uri + ", path='" + this.path + "', isBeforeDownload=" + this.isBeforeDownload + '}';
        }
    }

    public interface IDownload {
        void onDownloadFailed(String str);

        void onDownloadSuccess(String str, String str2);
    }

    public interface IFinishListener {
        void onFailed(String str);

        void onSuccess(FileInfo fileInfo);
    }

    public Saver(Context context) {
        this.context = context;
        this.appPath = new AppPath(context);
    }

    private void checkOrDownload(String str, String str2, String str3, final IFinishListener iFinishListener) throws IOException {
        final FileInfo fileInfo = new FileInfo();
        final File file = new File(str2, str3);
        if (!file.exists() || file.length() <= 0) {
            download(str, str2, new IDownload() { // from class: com.leeson.image_pickers.utils.Saver.6
                @Override // com.leeson.image_pickers.utils.Saver.IDownload
                public void onDownloadFailed(String str4) {
                    IFinishListener iFinishListener2 = iFinishListener;
                    if (iFinishListener2 != null) {
                        iFinishListener2.onFailed(str4);
                    }
                }

                @Override // com.leeson.image_pickers.utils.Saver.IDownload
                public void onDownloadSuccess(String str4, String str5) {
                    fileInfo.setBeforeDownload(false);
                    fileInfo.setUri(null);
                    fileInfo.setPath(file.getAbsolutePath());
                    fileInfo.setSize(file.length());
                    Saver.this.notifyGallery(fileInfo.getPath());
                    IFinishListener iFinishListener2 = iFinishListener;
                    if (iFinishListener2 != null) {
                        iFinishListener2.onSuccess(fileInfo);
                    }
                }
            });
            return;
        }
        fileInfo.setBeforeDownload(true);
        fileInfo.setUri(null);
        fileInfo.setPath(file.getAbsolutePath());
        fileInfo.setSize(file.length());
        if (iFinishListener != null) {
            iFinishListener.onSuccess(fileInfo);
        }
        notifyGallery(fileInfo.getPath());
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x007d A[Catch: IOException -> 0x0079, TRY_LEAVE, TryCatch #0 {IOException -> 0x0079, blocks: (B:34:0x0075, B:38:0x007d), top: B:42:0x0075 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0075 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.leeson.image_pickers.utils.Saver.FileInfo copy(java.lang.String r9, android.content.ContentValues r10, android.net.Uri r11) throws java.lang.Throwable {
        /*
            r8 = this;
            com.leeson.image_pickers.utils.Saver$FileInfo r0 = new com.leeson.image_pickers.utils.Saver$FileInfo
            r0.<init>()
            r1 = 0
            r0.setBeforeDownload(r1)
            android.content.Context r2 = r8.context
            android.content.ContentResolver r2 = r2.getContentResolver()
            android.net.Uri r10 = r2.insert(r11, r10)
            r11 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L62
            java.io.File r4 = new java.io.File     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L62
            r4.<init>(r9)     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L62
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L62
            if (r10 == 0) goto L39
            r0.setUri(r10)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L35
            java.io.OutputStream r11 = r2.openOutputStream(r10)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L35
            android.content.Context r9 = r8.context     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L35
            java.lang.String r9 = r8.getPath(r9, r10)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L35
            r0.setPath(r9)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L35
            goto L39
        L31:
            r9 = move-exception
            r10 = r11
            r11 = r3
            goto L73
        L35:
            r9 = move-exception
            r10 = r11
            r11 = r3
            goto L64
        L39:
            if (r11 == 0) goto L51
            r9 = 5120(0x1400, float:7.175E-42)
            byte[] r9 = new byte[r9]     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L35
            r4 = 0
        L41:
            int r10 = r3.read(r9)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L35
            r2 = -1
            if (r10 == r2) goto L4e
            long r6 = (long) r10     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L35
            long r4 = r4 + r6
            r11.write(r9, r1, r10)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L35
            goto L41
        L4e:
            r0.setSize(r4)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L35
        L51:
            r3.close()     // Catch: java.io.IOException -> L5a
            if (r11 == 0) goto L71
            r11.close()     // Catch: java.io.IOException -> L5a
            goto L71
        L5a:
            r9 = move-exception
            r9.printStackTrace()
            goto L71
        L5f:
            r9 = move-exception
            r10 = r11
            goto L73
        L62:
            r9 = move-exception
            r10 = r11
        L64:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L72
            if (r11 == 0) goto L6c
            r11.close()     // Catch: java.io.IOException -> L5a
        L6c:
            if (r10 == 0) goto L71
            r10.close()     // Catch: java.io.IOException -> L5a
        L71:
            return r0
        L72:
            r9 = move-exception
        L73:
            if (r11 == 0) goto L7b
            r11.close()     // Catch: java.io.IOException -> L79
            goto L7b
        L79:
            r10 = move-exception
            goto L81
        L7b:
            if (r10 == 0) goto L84
            r10.close()     // Catch: java.io.IOException -> L79
            goto L84
        L81:
            r10.printStackTrace()
        L84:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leeson.image_pickers.utils.Saver.copy(java.lang.String, android.content.ContentValues, android.net.Uri):com.leeson.image_pickers.utils.Saver$FileInfo");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FileInfo copyImgToPicture(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", str2);
        contentValues.put("title", str2);
        contentValues.put("description", str2);
        contentValues.put("mime_type", getMIMEType("image", str2));
        return copy(str, contentValues, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FileInfo copyToDownload(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", str2);
        contentValues.put("title", str2);
        contentValues.put("mime_type", getMIMEType("", str2));
        return copy(str, contentValues, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FileInfo copyToMovies(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", str2);
        contentValues.put("title", str2);
        contentValues.put("description", str2);
        contentValues.put("mime_type", getMIMEType("video", str2));
        return copy(str, contentValues, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FileInfo copyToMusic(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", str2);
        contentValues.put("title", str2);
        contentValues.put("mime_type", getMIMEType("", str2));
        return copy(str, contentValues, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
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
            java.lang.String r10 = "==>"
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
        throw new UnsupportedOperationException("Method not decompiled: com.leeson.image_pickers.utils.Saver.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    private FileInfo getFileInfo(Uri uri, String str, String str2, String str3, String str4, String str5) {
        Cursor cursorQuery;
        ContentResolver contentResolver = this.context.getContentResolver();
        if (contentResolver == null || (cursorQuery = contentResolver.query(uri, new String[]{str2, str3, str4}, str, null, str5)) == null || !cursorQuery.moveToFirst()) {
            return null;
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setBeforeDownload(true);
        fileInfo.setUri(Uri.withAppendedPath(uri, "" + cursorQuery.getInt(cursorQuery.getColumnIndex(str2))));
        fileInfo.setSize(cursorQuery.getLong(cursorQuery.getColumnIndex(str3)));
        fileInfo.setPath(cursorQuery.getString(cursorQuery.getColumnIndex(str4)));
        cursorQuery.close();
        return fileInfo;
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

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyGallery(final String str) {
        MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(this.context, new MediaScannerConnection.MediaScannerConnectionClient() { // from class: com.leeson.image_pickers.utils.Saver.10
            @Override // android.media.MediaScannerConnection.MediaScannerConnectionClient
            public void onMediaScannerConnected() {
                Saver.this.mediaScannerConnection.scanFile(str, null);
            }

            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
            public void onScanCompleted(String str2, Uri uri) {
                Saver.this.mediaScannerConnection.disconnect();
            }
        });
        this.mediaScannerConnection = mediaScannerConnection;
        mediaScannerConnection.connect();
    }

    private void saveToAppPrivate(String str, String str2, final IFinishListener iFinishListener) throws IOException {
        final File file = new File(str2, str.substring(str.lastIndexOf("/") + 1));
        if (!file.exists() || file.length() <= 0) {
            download(str, str2, new IDownload() { // from class: com.leeson.image_pickers.utils.Saver.1
                @Override // com.leeson.image_pickers.utils.Saver.IDownload
                public void onDownloadFailed(String str3) {
                    IFinishListener iFinishListener2 = iFinishListener;
                    if (iFinishListener2 != null) {
                        iFinishListener2.onFailed(str3);
                    }
                }

                @Override // com.leeson.image_pickers.utils.Saver.IDownload
                public void onDownloadSuccess(String str3, String str4) {
                    if (iFinishListener != null) {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setBeforeDownload(false);
                        fileInfo.setSize(file.length());
                        fileInfo.setPath(file.getAbsolutePath());
                        fileInfo.setUri(null);
                        iFinishListener.onSuccess(fileInfo);
                    }
                }
            });
            return;
        }
        if (iFinishListener != null) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setBeforeDownload(true);
            fileInfo.setSize(file.length());
            fileInfo.setPath(file.getAbsolutePath());
            fileInfo.setUri(null);
            iFinishListener.onSuccess(fileInfo);
        }
    }

    private void test(Uri uri) {
        Cursor cursorQuery = this.context.getContentResolver().query(uri, null, null, null, null);
        if (cursorQuery == null || !cursorQuery.moveToFirst()) {
            return;
        }
        while (cursorQuery.moveToNext()) {
            Log.e("cursor", "getFileInfo: cursor= > " + cursorQuery.getString(cursorQuery.getColumnIndex("_data")) + "==: " + cursorQuery.getString(cursorQuery.getColumnIndex("_display_name")));
        }
        cursorQuery.close();
    }

    public Bitmap createViewBitmap(View view) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public void download(final String str, String str2, final IDownload iDownload) throws IOException {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        final String strSubstring = str.substring(str.lastIndexOf("/") + 1);
        final File file2 = new File(str2, strSubstring);
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        new Thread(new Runnable() { // from class: com.leeson.image_pickers.utils.Saver.9

            /* renamed from: a, reason: collision with root package name */
            InputStream f18811a = null;

            /* renamed from: b, reason: collision with root package name */
            FileOutputStream f18812b = null;

            @Override // java.lang.Runnable
            public void run() throws IOException {
                try {
                    StringBuilder sb = new StringBuilder();
                    String str3 = str;
                    sb.append(str3.substring(0, str3.lastIndexOf("/") + 1));
                    sb.append(URLEncoder.encode(strSubstring, "utf-8"));
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(URLDecoder.decode(sb.toString(), "utf-8")).openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() != 200) {
                        new Handler(Saver.this.context.getMainLooper()).post(new Runnable() { // from class: com.leeson.image_pickers.utils.Saver.9.2
                            @Override // java.lang.Runnable
                            public void run() {
                                IDownload iDownload2 = iDownload;
                                if (iDownload2 != null) {
                                    iDownload2.onDownloadFailed("下载失败");
                                }
                            }
                        });
                        return;
                    }
                    this.f18811a = httpURLConnection.getInputStream();
                    this.f18812b = new FileOutputStream(file2);
                    byte[] bArr = new byte[5120];
                    while (true) {
                        int i2 = this.f18811a.read(bArr);
                        if (i2 == -1) {
                            this.f18812b.close();
                            this.f18811a.close();
                            new Handler(Saver.this.context.getMainLooper()).post(new Runnable() { // from class: com.leeson.image_pickers.utils.Saver.9.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass9 anonymousClass9 = AnonymousClass9.this;
                                    IDownload iDownload2 = iDownload;
                                    if (iDownload2 != null) {
                                        iDownload2.onDownloadSuccess(file2.getAbsolutePath(), strSubstring);
                                    }
                                }
                            });
                            return;
                        }
                        this.f18812b.write(bArr, 0, i2);
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                    new Handler(Saver.this.context.getMainLooper()).post(new Runnable() { // from class: com.leeson.image_pickers.utils.Saver.9.3
                        @Override // java.lang.Runnable
                        public void run() {
                            IDownload iDownload2 = iDownload;
                            if (iDownload2 != null) {
                                iDownload2.onDownloadFailed(e3.getMessage());
                            }
                        }
                    });
                    try {
                        FileOutputStream fileOutputStream = this.f18812b;
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        InputStream inputStream = this.f18811a;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public String getMIMEType(String str, String str2) {
        String str3 = "video".equals(str) ? "video/mp4" : "image/png";
        int iLastIndexOf = str2.lastIndexOf(".");
        if (iLastIndexOf < 0) {
            return str3;
        }
        String lowerCase = str2.substring(iLastIndexOf).toLowerCase();
        if (TextUtils.isEmpty(lowerCase)) {
            return str3;
        }
        int i2 = 0;
        while (true) {
            String[][] strArr = this.MIME_MapTable;
            if (i2 >= strArr.length) {
                return str3;
            }
            if (lowerCase.equals(strArr[i2][0])) {
                return this.MIME_MapTable[i2][1];
            }
            i2++;
        }
    }

    public String getPath(Context context, Uri uri) {
        Uri uri2 = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            }
            if (isMediaDocument(uri)) {
                String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
                String str = strArrSplit[0];
                if ("image".equals(str)) {
                    uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str)) {
                    uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(str)) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                return getDataColumn(context, uri2, "_id=?", new String[]{strArrSplit[1]});
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

    public boolean isFileExists(Uri uri) throws IOException {
        if (uri == null) {
            return false;
        }
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = this.context.getContentResolver().openFileDescriptor(uri, "r");
            if (parcelFileDescriptorOpenFileDescriptor == null) {
                return false;
            }
            try {
                parcelFileDescriptorOpenFileDescriptor.close();
                return true;
            } catch (IOException e2) {
                e2.printStackTrace();
                return true;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            return false;
        }
    }

    public void saveAudio(String str, IFinishListener iFinishListener) throws IOException {
        saveToAppPrivate(str, this.appPath.getAppAudioDirPath(), iFinishListener);
    }

    public void saveBase64ToToGallery(String str, IFinishListener iFinishListener) {
        saveByteDataToGallery(Base64.decode(str.split(",")[1], 0), iFinishListener);
    }

    public void saveBitmapToToGallery(Bitmap bitmap, final IFinishListener iFinishListener) throws IOException {
        File file = new File(this.appPath.getAppImgDirPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        final String str = System.currentTimeMillis() + (bitmap.hasAlpha() ? PictureMimeType.PNG : PictureMimeType.JPG);
        final File file2 = new File(file, str);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2, false);
            bitmap.compress(bitmap.hasAlpha() ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        new Handler(this.context.getMainLooper()).post(new Runnable() { // from class: com.leeson.image_pickers.utils.Saver.8
            @Override // java.lang.Runnable
            public void run() {
                FileInfo fileInfo = new FileInfo();
                if (Build.VERSION.SDK_INT >= 29) {
                    fileInfo = Saver.this.copyImgToPicture(file2.getAbsolutePath(), str);
                } else {
                    fileInfo.setBeforeDownload(false);
                    fileInfo.setUri(null);
                    fileInfo.setPath(file2.getAbsolutePath());
                    fileInfo.setSize(file2.length());
                }
                Saver.this.notifyGallery(fileInfo.getPath());
                IFinishListener iFinishListener2 = iFinishListener;
                if (iFinishListener2 != null) {
                    iFinishListener2.onSuccess(fileInfo);
                }
            }
        });
    }

    public void saveByteDataToGallery(final byte[] bArr, final IFinishListener iFinishListener) {
        new Thread(new Runnable() { // from class: com.leeson.image_pickers.utils.Saver.7
            @Override // java.lang.Runnable
            public void run() throws IOException {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    options.inSampleSize = 3;
                    byte[] bArr2 = bArr;
                    BitmapFactory.decodeByteArray(bArr2, 0, bArr2.length, options);
                    String str = options.outMimeType;
                    Log.e("TAG", "mimeType: == " + str);
                    File file = new File(Saver.this.appPath.getAppImgDirPath());
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String str2 = PictureMimeType.PNG;
                    if (str.contains("gif")) {
                        str2 = PictureMimeType.GIF;
                    }
                    final String str3 = System.currentTimeMillis() + str2;
                    final File file2 = new File(file, str3);
                    FileOutputStream fileOutputStream = new FileOutputStream(file2, false);
                    byte[] bArr3 = bArr;
                    fileOutputStream.write(bArr3, 0, bArr3.length);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    new Handler(Saver.this.context.getMainLooper()).post(new Runnable() { // from class: com.leeson.image_pickers.utils.Saver.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            FileInfo fileInfoCopyImgToPicture = Saver.this.copyImgToPicture(file2.getAbsolutePath(), str3);
                            Saver.this.notifyGallery(fileInfoCopyImgToPicture.getPath());
                            fileInfoCopyImgToPicture.setPath(file2.getAbsolutePath());
                            IFinishListener iFinishListener2 = iFinishListener;
                            if (iFinishListener2 != null) {
                                iFinishListener2.onSuccess(fileInfoCopyImgToPicture);
                            }
                        }
                    });
                } catch (Exception e2) {
                    e2.printStackTrace();
                    new Handler(Saver.this.context.getMainLooper()).post(new Runnable() { // from class: com.leeson.image_pickers.utils.Saver.7.2
                        @Override // java.lang.Runnable
                        public void run() {
                            IFinishListener iFinishListener2 = iFinishListener;
                            if (iFinishListener2 != null) {
                                iFinishListener2.onFailed(e2.getMessage());
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public void saveFileToDownload(String str, final IFinishListener iFinishListener) throws IOException {
        String strSubstring = str.substring(str.lastIndexOf("/") + 1);
        String appDownloadDirPath = this.appPath.getAppDownloadDirPath();
        if (Build.VERSION.SDK_INT < 29) {
            String str2 = Environment.getExternalStorageDirectory() + "/" + this.appPath.getPackageName() + "/Download";
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdirs();
            }
            checkOrDownload(str, str2, strSubstring, iFinishListener);
            return;
        }
        FileInfo fileInfo = getFileInfo(MediaStore.Downloads.EXTERNAL_CONTENT_URI, "_display_name='" + strSubstring + "'", bg.f21483d, "_size", "_data", "_size DESC");
        if (fileInfo != null && fileInfo.size > 0) {
            if (iFinishListener != null) {
                iFinishListener.onSuccess(fileInfo);
            }
        } else {
            File file2 = new File(appDownloadDirPath);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            download(str, appDownloadDirPath, new IDownload() { // from class: com.leeson.image_pickers.utils.Saver.4
                @Override // com.leeson.image_pickers.utils.Saver.IDownload
                public void onDownloadFailed(String str3) {
                    IFinishListener iFinishListener2 = iFinishListener;
                    if (iFinishListener2 != null) {
                        iFinishListener2.onFailed(str3);
                    }
                }

                @Override // com.leeson.image_pickers.utils.Saver.IDownload
                public void onDownloadSuccess(String str3, String str4) {
                    FileInfo fileInfoCopyToDownload = Saver.this.copyToDownload(str3, str4);
                    Saver.this.notifyGallery(fileInfoCopyToDownload.getPath());
                    IFinishListener iFinishListener2 = iFinishListener;
                    if (iFinishListener2 != null) {
                        iFinishListener2.onSuccess(fileInfoCopyToDownload);
                    }
                }
            });
        }
    }

    public void saveImg(String str, IFinishListener iFinishListener) throws IOException {
        saveToAppPrivate(str, this.appPath.getAppImgDirPath(), iFinishListener);
    }

    public void saveImgToGallery(String str, final IFinishListener iFinishListener) throws IOException {
        FileInfo fileInfo;
        String strSubstring = str.substring(str.lastIndexOf("/") + 1);
        String appImgDirPath = this.appPath.getAppImgDirPath();
        File file = new File(appImgDirPath, strSubstring);
        if (!file.exists() || file.length() <= 0) {
            fileInfo = null;
        } else {
            Log.e("TAG", "saveImgToGallery: has file");
            fileInfo = new FileInfo();
            fileInfo.setBeforeDownload(true);
            fileInfo.setUri(PictureFileUtils.parUri(this.context, file));
            fileInfo.setPath(file.getAbsolutePath());
            fileInfo.setSize(file.length());
        }
        if (fileInfo == null || fileInfo.size <= 0) {
            Log.e("TAG", "saveImgToGallery: start download");
            download(str, appImgDirPath, new IDownload() { // from class: com.leeson.image_pickers.utils.Saver.2
                @Override // com.leeson.image_pickers.utils.Saver.IDownload
                public void onDownloadFailed(String str2) {
                    IFinishListener iFinishListener2 = iFinishListener;
                    if (iFinishListener2 != null) {
                        iFinishListener2.onFailed(str2);
                    }
                }

                @Override // com.leeson.image_pickers.utils.Saver.IDownload
                public void onDownloadSuccess(String str2, String str3) {
                    FileInfo fileInfoCopyImgToPicture = Saver.this.copyImgToPicture(str2, System.currentTimeMillis() + str3);
                    Saver.this.notifyGallery(fileInfoCopyImgToPicture.getPath());
                    IFinishListener iFinishListener2 = iFinishListener;
                    if (iFinishListener2 != null) {
                        iFinishListener2.onSuccess(fileInfoCopyImgToPicture);
                    }
                }
            });
            return;
        }
        Log.e("TAG", "saveImgToGallery: copy file");
        FileInfo fileInfoCopyImgToPicture = copyImgToPicture(fileInfo.getPath(), System.currentTimeMillis() + strSubstring);
        notifyGallery(fileInfoCopyImgToPicture.getPath());
        if (iFinishListener != null) {
            iFinishListener.onSuccess(fileInfoCopyImgToPicture);
        }
    }

    public void saveMusicFile(String str, IFinishListener iFinishListener) throws IOException {
        saveToAppPrivate(str, this.appPath.getAppMusicDirPath(), iFinishListener);
    }

    public void saveMusicFileToMusic(String str, final IFinishListener iFinishListener) throws IOException {
        FileInfo fileInfo;
        String strSubstring = str.substring(str.lastIndexOf("/") + 1);
        String appMusicDirPath = this.appPath.getAppMusicDirPath();
        String str2 = "_display_name='" + strSubstring + "'";
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        if (Build.VERSION.SDK_INT >= 29) {
            fileInfo = getFileInfo(uri, str2, bg.f21483d, "_size", "_data", "_size DESC");
        } else {
            File file = new File(appMusicDirPath, strSubstring);
            if (!file.exists() || file.length() <= 0) {
                fileInfo = null;
            } else {
                fileInfo = new FileInfo();
                fileInfo.setBeforeDownload(true);
                fileInfo.setUri(PictureFileUtils.parUri(this.context, file));
                fileInfo.setPath(file.getAbsolutePath());
                fileInfo.setSize(file.length());
            }
        }
        if (fileInfo == null || fileInfo.size <= 0) {
            download(str, appMusicDirPath, new IDownload() { // from class: com.leeson.image_pickers.utils.Saver.5
                @Override // com.leeson.image_pickers.utils.Saver.IDownload
                public void onDownloadFailed(String str3) {
                    IFinishListener iFinishListener2 = iFinishListener;
                    if (iFinishListener2 != null) {
                        iFinishListener2.onFailed(str3);
                    }
                }

                @Override // com.leeson.image_pickers.utils.Saver.IDownload
                public void onDownloadSuccess(String str3, String str4) {
                    FileInfo fileInfoCopyToMusic = Saver.this.copyToMusic(str3, str4);
                    Saver.this.notifyGallery(fileInfoCopyToMusic.getPath());
                    IFinishListener iFinishListener2 = iFinishListener;
                    if (iFinishListener2 != null) {
                        iFinishListener2.onSuccess(fileInfoCopyToMusic);
                    }
                }
            });
        } else if (iFinishListener != null) {
            iFinishListener.onSuccess(fileInfo);
        }
    }

    public void saveVideo(String str, IFinishListener iFinishListener) throws IOException {
        saveToAppPrivate(str, this.appPath.getAppVideoDirPath(), iFinishListener);
    }

    public void saveVideoToGallery(String str, final IFinishListener iFinishListener) throws IOException {
        FileInfo fileInfo;
        String strSubstring = str.substring(str.lastIndexOf("/") + 1);
        String appVideoDirPath = this.appPath.getAppVideoDirPath();
        File file = new File(appVideoDirPath, strSubstring);
        if (!file.exists() || file.length() <= 0) {
            fileInfo = null;
        } else {
            Log.e("TAG", "saveVideoToGallery: has file");
            fileInfo = new FileInfo();
            fileInfo.setBeforeDownload(true);
            fileInfo.setUri(PictureFileUtils.parUri(this.context, file));
            fileInfo.setPath(file.getAbsolutePath());
            fileInfo.setSize(file.length());
        }
        if (fileInfo == null) {
            Log.e("TAG", "saveVideoToGallery: start download");
            download(str, appVideoDirPath, new IDownload() { // from class: com.leeson.image_pickers.utils.Saver.3
                @Override // com.leeson.image_pickers.utils.Saver.IDownload
                public void onDownloadFailed(String str2) {
                    IFinishListener iFinishListener2 = iFinishListener;
                    if (iFinishListener2 != null) {
                        iFinishListener2.onFailed(str2);
                    }
                }

                @Override // com.leeson.image_pickers.utils.Saver.IDownload
                public void onDownloadSuccess(String str2, String str3) {
                    FileInfo fileInfoCopyToMovies = Saver.this.copyToMovies(str2, System.currentTimeMillis() + str3);
                    Saver.this.notifyGallery(fileInfoCopyToMovies.getPath());
                    IFinishListener iFinishListener2 = iFinishListener;
                    if (iFinishListener2 != null) {
                        iFinishListener2.onSuccess(fileInfoCopyToMovies);
                    }
                }
            });
            return;
        }
        Log.e("TAG", "saveVideoToGallery: copy file");
        FileInfo fileInfoCopyToMovies = copyToMovies(fileInfo.getPath(), System.currentTimeMillis() + strSubstring);
        notifyGallery(fileInfoCopyToMovies.getPath());
        if (iFinishListener != null) {
            iFinishListener.onSuccess(fileInfoCopyToMovies);
        }
    }

    public void saveViewToToGallery(View view, IFinishListener iFinishListener) throws IOException {
        saveBitmapToToGallery(createViewBitmap(view), iFinishListener);
    }
}
