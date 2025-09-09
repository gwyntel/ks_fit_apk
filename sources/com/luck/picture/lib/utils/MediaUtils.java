package com.luck.picture.lib.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import androidx.media3.common.MimeTypes;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.basic.PictureContentResolver;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnCallbackListener;
import com.luck.picture.lib.thread.PictureThreadUtils;
import com.umeng.analytics.pro.bg;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/* loaded from: classes4.dex */
public class MediaUtils {
    public static final String QUERY_ARG_SQL_LIMIT = "android:query-arg-sql-limit";

    public static Bundle createQueryArgsBundle(String str, String[] strArr, int i2, int i3, String str2) {
        Bundle bundle = new Bundle();
        if (Build.VERSION.SDK_INT >= 26) {
            bundle.putString("android:query-arg-sql-selection", str);
            bundle.putStringArray("android:query-arg-sql-selection-args", strArr);
            bundle.putString("android:query-arg-sql-sort-order", str2);
            if (SdkVersionUtils.isR()) {
                bundle.putString(QUERY_ARG_SQL_LIMIT, i2 + " offset " + i3);
            }
        }
        return bundle;
    }

    public static void deleteUri(Context context, String str) {
        try {
            if (TextUtils.isEmpty(str) || !PictureMimeType.isContent(str)) {
                return;
            }
            context.getContentResolver().delete(Uri.parse(str), null, null);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static String generateCameraFolderName(String str) {
        File file = new File(str);
        return file.getParentFile() != null ? file.getParentFile().getName() : PictureMimeType.CAMERA;
    }

    public static void getAsyncVideoThumbnail(final Context context, final String str, final OnCallbackListener<MediaExtraInfo> onCallbackListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<MediaExtraInfo>() { // from class: com.luck.picture.lib.utils.MediaUtils.3
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public MediaExtraInfo doInBackground() {
                return MediaUtils.getVideoThumbnail(context, str);
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(MediaExtraInfo mediaExtraInfo) {
                PictureThreadUtils.cancel(this);
                OnCallbackListener onCallbackListener2 = onCallbackListener;
                if (onCallbackListener2 != null) {
                    onCallbackListener2.onCall(mediaExtraInfo);
                }
            }
        });
    }

    public static MediaExtraInfo getAudioSize(Context context, String str) throws IOException {
        MediaExtraInfo mediaExtraInfo = new MediaExtraInfo();
        if (PictureMimeType.isHasHttp(str)) {
            return mediaExtraInfo;
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                try {
                    if (PictureMimeType.isContent(str)) {
                        mediaMetadataRetriever.setDataSource(context, Uri.parse(str));
                    } else {
                        mediaMetadataRetriever.setDataSource(str);
                    }
                    mediaExtraInfo.setDuration(ValueOf.toLong(mediaMetadataRetriever.extractMetadata(9)));
                    mediaMetadataRetriever.release();
                } catch (Throwable th) {
                    try {
                        mediaMetadataRetriever.release();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                mediaMetadataRetriever.release();
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        return mediaExtraInfo;
    }

    public static int getDCIMLastImageId(Context context, String str) {
        Cursor cursorQuery = null;
        try {
            try {
                String[] strArr = {"%" + str + "%"};
                cursorQuery = SdkVersionUtils.isR() ? context.getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, createQueryArgsBundle("_data like ?", strArr, 1, 0, "_id DESC"), null) : context.getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, "_data like ?", strArr, "_id DESC limit 1 offset 0");
                if (cursorQuery == null || cursorQuery.getCount() <= 0 || !cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return -1;
                }
                int i2 = DateUtils.dateDiffer(cursorQuery.getLong(cursorQuery.getColumnIndex("date_added"))) <= 1 ? cursorQuery.getInt(cursorQuery.getColumnIndex(bg.f21483d)) : -1;
                cursorQuery.close();
                return i2;
            } catch (Exception e2) {
                e2.printStackTrace();
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return -1;
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    @Deprecated
    public static MediaExtraInfo getImageSize(String str) throws Throwable {
        MediaExtraInfo mediaExtraInfo = new MediaExtraInfo();
        InputStream inputStream = null;
        try {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                InputStream inputStreamOpenInputStream = PictureMimeType.isContent(str) ? PictureContentResolver.openInputStream(PictureAppMaster.getInstance().getAppContext(), Uri.parse(str)) : new FileInputStream(str);
                try {
                    BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                    mediaExtraInfo.setWidth(options.outWidth);
                    mediaExtraInfo.setHeight(options.outHeight);
                    PictureFileUtils.close(inputStreamOpenInputStream);
                } catch (Exception e2) {
                    inputStream = inputStreamOpenInputStream;
                    e = e2;
                    e.printStackTrace();
                    PictureFileUtils.close(inputStream);
                    return mediaExtraInfo;
                } catch (Throwable th) {
                    inputStream = inputStreamOpenInputStream;
                    th = th;
                    PictureFileUtils.close(inputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
            }
            return mediaExtraInfo;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static String getMimeType(File file) {
        return URLConnection.getFileNameMap().getContentTypeFor(file.getName());
    }

    public static String getMimeTypeFromMediaHttpUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.toLowerCase().endsWith(PictureMimeType.JPG) || str.toLowerCase().endsWith(PictureMimeType.JPEG)) {
            return "image/jpeg";
        }
        if (str.toLowerCase().endsWith(PictureMimeType.PNG)) {
            return "image/png";
        }
        if (str.toLowerCase().endsWith(PictureMimeType.GIF)) {
            return "image/gif";
        }
        if (str.toLowerCase().endsWith(PictureMimeType.WEBP)) {
            return MimeTypes.IMAGE_WEBP;
        }
        if (str.toLowerCase().endsWith(PictureMimeType.BMP)) {
            return MimeTypes.IMAGE_BMP;
        }
        if (str.toLowerCase().endsWith(PictureMimeType.MP4)) {
            return "video/mp4";
        }
        if (str.toLowerCase().endsWith(PictureMimeType.AVI)) {
            return PictureMimeType.AVI_Q;
        }
        if (str.toLowerCase().endsWith(PictureMimeType.MP3)) {
            return "audio/mpeg";
        }
        if (str.toLowerCase().endsWith(PictureMimeType.AMR)) {
            return "audio/amr";
        }
        if (str.toLowerCase().endsWith(".m4a")) {
            return "audio/mpeg";
        }
        return null;
    }

    public static String getMimeTypeFromMediaUrl(String str) {
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(str).toLowerCase());
        if (TextUtils.isEmpty(mimeTypeFromExtension)) {
            mimeTypeFromExtension = getMimeType(new File(str));
        }
        return TextUtils.isEmpty(mimeTypeFromExtension) ? "image/jpeg" : mimeTypeFromExtension;
    }

    public static Long[] getPathMediaBucketId(Context context, String str) {
        Long[] lArr = {0L, 0L};
        Cursor cursorQuery = null;
        try {
            try {
                String[] strArr = {"%" + str + "%"};
                cursorQuery = SdkVersionUtils.isR() ? context.getContentResolver().query(MediaStore.Files.getContentUri("external"), null, createQueryArgsBundle("_data like ?", strArr, 1, 0, "_id DESC"), null) : context.getContentResolver().query(MediaStore.Files.getContentUri("external"), null, "_data like ?", strArr, "_id DESC limit 1 offset 0");
                if (cursorQuery != null && cursorQuery.getCount() > 0 && cursorQuery.moveToFirst()) {
                    lArr[0] = Long.valueOf(cursorQuery.getLong(cursorQuery.getColumnIndex(bg.f21483d)));
                    lArr[1] = Long.valueOf(cursorQuery.getLong(cursorQuery.getColumnIndex("bucket_id")));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (cursorQuery != null) {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return lArr;
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    public static String getRealPathUri(long j2, String str) {
        return ContentUris.withAppendedId(PictureMimeType.isHasImage(str) ? MediaStore.Images.Media.EXTERNAL_CONTENT_URI : PictureMimeType.isHasVideo(str) ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI : PictureMimeType.isHasAudio(str) ? MediaStore.Audio.Media.EXTERNAL_CONTENT_URI : MediaStore.Files.getContentUri("external"), j2).toString();
    }

    public static void getVideoSize(final Context context, final String str, final OnCallbackListener<MediaExtraInfo> onCallbackListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<MediaExtraInfo>() { // from class: com.luck.picture.lib.utils.MediaUtils.2
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public MediaExtraInfo doInBackground() {
                return MediaUtils.getVideoSize(context, str);
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(MediaExtraInfo mediaExtraInfo) {
                PictureThreadUtils.cancel(this);
                OnCallbackListener onCallbackListener2 = onCallbackListener;
                if (onCallbackListener2 != null) {
                    onCallbackListener2.onCall(mediaExtraInfo);
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.luck.picture.lib.entity.MediaExtraInfo getVideoThumbnail(android.content.Context r7, java.lang.String r8) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.utils.MediaUtils.getVideoThumbnail(android.content.Context, java.lang.String):com.luck.picture.lib.entity.MediaExtraInfo");
    }

    public static boolean isLongImage(int i2, int i3) {
        return i2 > 0 && i3 > 0 && i3 > i2 * 3;
    }

    public static void removeMedia(Context context, int i2) {
        try {
            context.getApplicationContext().getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_id=?", new String[]{Long.toString(i2)});
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static MediaExtraInfo getVideoSize(Context context, String str) {
        int i2;
        int i3;
        MediaExtraInfo mediaExtraInfo = new MediaExtraInfo();
        if (PictureMimeType.isHasHttp(str)) {
            return mediaExtraInfo;
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                try {
                    if (PictureMimeType.isContent(str)) {
                        mediaMetadataRetriever.setDataSource(context, Uri.parse(str));
                    } else {
                        mediaMetadataRetriever.setDataSource(str);
                    }
                    String strExtractMetadata = mediaMetadataRetriever.extractMetadata(24);
                    if (TextUtils.equals("90", strExtractMetadata) || TextUtils.equals("270", strExtractMetadata)) {
                        int i4 = ValueOf.toInt(mediaMetadataRetriever.extractMetadata(18));
                        i2 = i4;
                        i3 = ValueOf.toInt(mediaMetadataRetriever.extractMetadata(19));
                    } else {
                        i3 = ValueOf.toInt(mediaMetadataRetriever.extractMetadata(18));
                        i2 = ValueOf.toInt(mediaMetadataRetriever.extractMetadata(19));
                    }
                    mediaExtraInfo.setWidth(i3);
                    mediaExtraInfo.setHeight(i2);
                    mediaExtraInfo.setOrientation(strExtractMetadata);
                    mediaExtraInfo.setDuration(ValueOf.toLong(mediaMetadataRetriever.extractMetadata(9)));
                    mediaMetadataRetriever.release();
                } catch (Throwable th) {
                    try {
                        mediaMetadataRetriever.release();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                mediaMetadataRetriever.release();
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        return mediaExtraInfo;
    }

    public static MediaExtraInfo getImageSize(Context context, String str) {
        InputStream fileInputStream;
        MediaExtraInfo mediaExtraInfo = new MediaExtraInfo();
        if (PictureMimeType.isHasHttp(str)) {
            return mediaExtraInfo;
        }
        InputStream inputStream = null;
        try {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                if (PictureMimeType.isContent(str)) {
                    fileInputStream = PictureContentResolver.openInputStream(context, Uri.parse(str));
                } else {
                    fileInputStream = new FileInputStream(str);
                }
                try {
                    BitmapFactory.decodeStream(fileInputStream, null, options);
                    mediaExtraInfo.setWidth(options.outWidth);
                    mediaExtraInfo.setHeight(options.outHeight);
                    PictureFileUtils.close(fileInputStream);
                } catch (Exception e2) {
                    inputStream = fileInputStream;
                    e = e2;
                    e.printStackTrace();
                    PictureFileUtils.close(inputStream);
                    return mediaExtraInfo;
                } catch (Throwable th) {
                    inputStream = fileInputStream;
                    th = th;
                    PictureFileUtils.close(inputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
            }
            return mediaExtraInfo;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void getImageSize(final Context context, final String str, final OnCallbackListener<MediaExtraInfo> onCallbackListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<MediaExtraInfo>() { // from class: com.luck.picture.lib.utils.MediaUtils.1
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public MediaExtraInfo doInBackground() {
                return MediaUtils.getImageSize(context, str);
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(MediaExtraInfo mediaExtraInfo) {
                PictureThreadUtils.cancel(this);
                OnCallbackListener onCallbackListener2 = onCallbackListener;
                if (onCallbackListener2 != null) {
                    onCallbackListener2.onCall(mediaExtraInfo);
                }
            }
        });
    }
}
