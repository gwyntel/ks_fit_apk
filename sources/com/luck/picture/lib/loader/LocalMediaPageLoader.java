package com.luck.picture.lib.loader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.entity.MediaData;
import com.luck.picture.lib.interfaces.OnQueryAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryAllAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryDataResultListener;
import com.luck.picture.lib.interfaces.OnQueryFilterListener;
import com.luck.picture.lib.thread.PictureThreadUtils;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.PictureFileUtils;
import com.luck.picture.lib.utils.SdkVersionUtils;
import com.luck.picture.lib.utils.SortUtils;
import com.luck.picture.lib.utils.ValueOf;
import com.umeng.analytics.pro.bg;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes4.dex */
public final class LocalMediaPageLoader extends IBridgeMediaLoader {
    public LocalMediaPageLoader(Context context, SelectorConfig selectorConfig) {
        super(context, selectorConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getFirstCoverMimeType(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow("mime_type"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getFirstUri(Cursor cursor) {
        return MediaUtils.getRealPathUri(cursor.getLong(cursor.getColumnIndexOrThrow(bg.f21483d)), cursor.getString(cursor.getColumnIndexOrThrow("mime_type")));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getFirstUrl(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow("_data"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getPageSelection(long j2) {
        String strD = d();
        String strE = e();
        int i2 = b().chooseMode;
        if (i2 == 0) {
            return getPageSelectionArgsForAllMediaCondition(j2, f(), g(), strD, strE);
        }
        if (i2 == 1) {
            return getPageSelectionArgsForImageMediaCondition(j2, f(), strE);
        }
        if (i2 == 2) {
            return getPageSelectionArgsForVideoMediaCondition(j2, g(), strD, strE);
        }
        if (i2 != 3) {
            return null;
        }
        return getPageSelectionArgsForAudioMediaCondition(j2, a(), strD, strE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String[] getPageSelectionArgs(long j2) {
        int i2 = b().chooseMode;
        if (i2 == 0) {
            return j2 == -1 ? new String[]{String.valueOf(1), String.valueOf(3)} : new String[]{String.valueOf(1), String.valueOf(3), ValueOf.toString(Long.valueOf(j2))};
        }
        if (i2 == 1) {
            return getSelectionArgsForPageSingleMediaType(1, j2);
        }
        if (i2 == 2) {
            return getSelectionArgsForPageSingleMediaType(3, j2);
        }
        if (i2 != 3) {
            return null;
        }
        return getSelectionArgsForPageSingleMediaType(2, j2);
    }

    private static String getPageSelectionArgsForAllMediaCondition(long j2, String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(MessengerShareContentUtility.MEDIA_TYPE);
        sb.append("=?");
        sb.append(str);
        sb.append(" OR ");
        sb.append(MessengerShareContentUtility.MEDIA_TYPE);
        sb.append("=?");
        sb.append(str2);
        sb.append(" AND ");
        sb.append(str3);
        sb.append(")");
        sb.append(" AND ");
        if (j2 == -1) {
            sb.append(str4);
            return sb.toString();
        }
        sb.append("bucket_id");
        sb.append("=? AND ");
        sb.append(str4);
        return sb.toString();
    }

    private static String getPageSelectionArgsForAudioMediaCondition(long j2, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(MessengerShareContentUtility.MEDIA_TYPE);
        sb.append("=?");
        sb.append(str);
        sb.append(" AND ");
        sb.append(str2);
        sb.append(") AND ");
        if (j2 == -1) {
            sb.append(str3);
            return sb.toString();
        }
        sb.append("bucket_id");
        sb.append("=? AND ");
        sb.append(str3);
        return sb.toString();
    }

    private static String getPageSelectionArgsForImageMediaCondition(long j2, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(MessengerShareContentUtility.MEDIA_TYPE);
        sb.append("=?");
        if (j2 == -1) {
            sb.append(str);
            sb.append(") AND ");
            sb.append(str2);
            return sb.toString();
        }
        sb.append(str);
        sb.append(") AND ");
        sb.append("bucket_id");
        sb.append("=? AND ");
        sb.append(str2);
        return sb.toString();
    }

    private static String getPageSelectionArgsForVideoMediaCondition(long j2, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(MessengerShareContentUtility.MEDIA_TYPE);
        sb.append("=?");
        sb.append(str);
        sb.append(" AND ");
        sb.append(str2);
        sb.append(") AND ");
        if (j2 == -1) {
            sb.append(str3);
            return sb.toString();
        }
        sb.append("bucket_id");
        sb.append("=? AND ");
        sb.append(str3);
        return sb.toString();
    }

    private String getSelectionArgsForAllMediaCondition(String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(MessengerShareContentUtility.MEDIA_TYPE);
        sb.append("=?");
        sb.append(str3);
        sb.append(" OR ");
        sb.append(MessengerShareContentUtility.MEDIA_TYPE);
        sb.append("=?");
        sb.append(str4);
        sb.append(" AND ");
        sb.append(str);
        sb.append(")");
        sb.append(" AND ");
        sb.append(str2);
        if (isWithAllQuery()) {
            return sb.toString();
        }
        sb.append(")");
        sb.append(" GROUP BY (bucket_id");
        return sb.toString();
    }

    private String getSelectionArgsForAudioMediaCondition(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (isWithAllQuery()) {
            sb.append(MessengerShareContentUtility.MEDIA_TYPE);
            sb.append("=?");
            sb.append(str2);
            sb.append(" AND ");
            sb.append(str);
            return sb.toString();
        }
        sb.append("(");
        sb.append(MessengerShareContentUtility.MEDIA_TYPE);
        sb.append("=?");
        sb.append(str2);
        sb.append(") AND ");
        sb.append(str);
        sb.append(")");
        sb.append(" GROUP BY (bucket_id");
        return sb.toString();
    }

    private String getSelectionArgsForImageMediaCondition(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (isWithAllQuery()) {
            sb.append(MessengerShareContentUtility.MEDIA_TYPE);
            sb.append("=?");
            sb.append(str2);
            sb.append(" AND ");
            sb.append(str);
            return sb.toString();
        }
        sb.append("(");
        sb.append(MessengerShareContentUtility.MEDIA_TYPE);
        sb.append("=?");
        sb.append(str2);
        sb.append(") AND ");
        sb.append(str);
        sb.append(")");
        sb.append(" GROUP BY (bucket_id");
        return sb.toString();
    }

    private static String[] getSelectionArgsForPageSingleMediaType(int i2, long j2) {
        return j2 == -1 ? new String[]{String.valueOf(i2)} : new String[]{String.valueOf(i2), ValueOf.toString(Long.valueOf(j2))};
    }

    private String getSelectionArgsForVideoMediaCondition(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (isWithAllQuery()) {
            sb.append(MessengerShareContentUtility.MEDIA_TYPE);
            sb.append("=?");
            sb.append(str2);
            sb.append(" AND ");
            sb.append(str);
            return sb.toString();
        }
        sb.append("(");
        sb.append(MessengerShareContentUtility.MEDIA_TYPE);
        sb.append("=?");
        sb.append(str2);
        sb.append(") AND ");
        sb.append(str);
        sb.append(")");
        sb.append(" GROUP BY (bucket_id");
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isWithAllQuery() {
        if (SdkVersionUtils.isQ()) {
            return true;
        }
        return b().isPageSyncAsCount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void synchronousFirstCover(List<LocalMediaFolder> list) throws Throwable {
        for (int i2 = 0; i2 < list.size(); i2++) {
            LocalMediaFolder localMediaFolder = list.get(i2);
            if (localMediaFolder != null) {
                String albumFirstCover = getAlbumFirstCover(localMediaFolder.getBucketId());
                if (!TextUtils.isEmpty(albumFirstCover)) {
                    localMediaFolder.setFirstImagePath(albumFirstCover);
                }
            }
        }
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public String getAlbumFirstCover(long j2) throws Throwable {
        Cursor cursor;
        Cursor cursorQuery;
        Cursor cursor2 = null;
        try {
            if (SdkVersionUtils.isR()) {
                cursorQuery = c().getContentResolver().query(IBridgeMediaLoader.f19071c, new String[]{bg.f21483d, "mime_type", "_data"}, MediaUtils.createQueryArgsBundle(getPageSelection(j2), getPageSelectionArgs(j2), 1, 0, q()), null);
            } else {
                cursorQuery = c().getContentResolver().query(IBridgeMediaLoader.f19071c, new String[]{bg.f21483d, "mime_type", "_data"}, getPageSelection(j2), getPageSelectionArgs(j2), q() + " limit 1 offset 0");
            }
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() > 0) {
                        if (!cursorQuery.moveToFirst()) {
                            if (!cursorQuery.isClosed()) {
                                cursorQuery.close();
                            }
                            return null;
                        }
                        String realPathUri = SdkVersionUtils.isQ() ? MediaUtils.getRealPathUri(cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow(bg.f21483d)), cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("mime_type"))) : cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                        if (!cursorQuery.isClosed()) {
                            cursorQuery.close();
                        }
                        return realPathUri;
                    }
                } catch (Exception e2) {
                    cursor = cursorQuery;
                    e = e2;
                    try {
                        e.printStackTrace();
                        if (cursor != null && !cursor.isClosed()) {
                            cursor.close();
                        }
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        cursor2 = cursor;
                        if (cursor2 != null && !cursor2.isClosed()) {
                            cursor2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    cursor2 = cursorQuery;
                    th = th2;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            }
            if (cursorQuery != null && !cursorQuery.isClosed()) {
                cursorQuery.close();
            }
        } catch (Exception e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
        }
        return null;
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public void loadAllAlbum(final OnQueryAllAlbumListener<LocalMediaFolder> onQueryAllAlbumListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<List<LocalMediaFolder>>() { // from class: com.luck.picture.lib.loader.LocalMediaPageLoader.3
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public List<LocalMediaFolder> doInBackground() {
                int folderTotalNum;
                String string;
                Context contextC;
                int i2;
                Cursor cursorQuery = LocalMediaPageLoader.this.c().getContentResolver().query(IBridgeMediaLoader.f19071c, LocalMediaPageLoader.this.isWithAllQuery() ? IBridgeMediaLoader.f19072d : IBridgeMediaLoader.f19073e, LocalMediaPageLoader.this.o(), LocalMediaPageLoader.this.p(), LocalMediaPageLoader.this.q());
                try {
                    if (cursorQuery != null) {
                        try {
                            int count = cursorQuery.getCount();
                            ArrayList<LocalMediaFolder> arrayList = new ArrayList();
                            if (count > 0) {
                                if (LocalMediaPageLoader.this.isWithAllQuery()) {
                                    HashMap map = new HashMap();
                                    HashSet hashSet = new HashSet();
                                    while (cursorQuery.moveToNext()) {
                                        if (LocalMediaPageLoader.this.b().isPageSyncAsCount) {
                                            LocalMedia localMediaR = LocalMediaPageLoader.this.r(cursorQuery, true);
                                            if (localMediaR != null) {
                                                localMediaR.recycle();
                                            }
                                        }
                                        long j2 = cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("bucket_id"));
                                        Long l2 = (Long) map.get(Long.valueOf(j2));
                                        map.put(Long.valueOf(j2), l2 == null ? 1L : Long.valueOf(l2.longValue() + 1));
                                        if (!hashSet.contains(Long.valueOf(j2))) {
                                            LocalMediaFolder localMediaFolder = new LocalMediaFolder();
                                            localMediaFolder.setBucketId(j2);
                                            String string2 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("bucket_display_name"));
                                            String string3 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("mime_type"));
                                            if (map.containsKey(Long.valueOf(j2))) {
                                                Long l3 = (Long) map.get(Long.valueOf(j2));
                                                l3.longValue();
                                                long j3 = cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow(bg.f21483d));
                                                localMediaFolder.setFolderName(string2);
                                                localMediaFolder.setFolderTotalNum(ValueOf.toInt(l3));
                                                localMediaFolder.setFirstImagePath(MediaUtils.getRealPathUri(j3, string3));
                                                localMediaFolder.setFirstMimeType(string3);
                                                arrayList.add(localMediaFolder);
                                                hashSet = hashSet;
                                                hashSet.add(Long.valueOf(j2));
                                            }
                                        }
                                    }
                                    folderTotalNum = 0;
                                    for (LocalMediaFolder localMediaFolder2 : arrayList) {
                                        int i3 = ValueOf.toInt(map.get(Long.valueOf(localMediaFolder2.getBucketId())));
                                        localMediaFolder2.setFolderTotalNum(i3);
                                        folderTotalNum += i3;
                                    }
                                } else {
                                    cursorQuery.moveToFirst();
                                    int i4 = 0;
                                    do {
                                        String string4 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                                        String string5 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("bucket_display_name"));
                                        String string6 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("mime_type"));
                                        long j4 = cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("bucket_id"));
                                        int i5 = cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow("count"));
                                        LocalMediaFolder localMediaFolder3 = new LocalMediaFolder();
                                        localMediaFolder3.setBucketId(j4);
                                        localMediaFolder3.setFirstImagePath(string4);
                                        localMediaFolder3.setFolderName(string5);
                                        localMediaFolder3.setFirstMimeType(string6);
                                        localMediaFolder3.setFolderTotalNum(i5);
                                        arrayList.add(localMediaFolder3);
                                        i4 += i5;
                                    } while (cursorQuery.moveToNext());
                                    folderTotalNum = i4;
                                }
                                LocalMediaFolder localMediaFolder4 = new LocalMediaFolder();
                                LocalMediaFolder localMediaFolderLoadInAppSandboxFolderFile = SandboxFileLoader.loadInAppSandboxFolderFile(LocalMediaPageLoader.this.c(), LocalMediaPageLoader.this.b().sandboxDir);
                                if (localMediaFolderLoadInAppSandboxFolderFile != null) {
                                    arrayList.add(localMediaFolderLoadInAppSandboxFolderFile);
                                    long jLastModified = new File(localMediaFolderLoadInAppSandboxFolderFile.getFirstImagePath()).lastModified();
                                    folderTotalNum += localMediaFolderLoadInAppSandboxFolderFile.getFolderTotalNum();
                                    localMediaFolder4.setData(new ArrayList<>());
                                    if (cursorQuery.moveToFirst()) {
                                        localMediaFolder4.setFirstImagePath(SdkVersionUtils.isQ() ? LocalMediaPageLoader.getFirstUri(cursorQuery) : LocalMediaPageLoader.getFirstUrl(cursorQuery));
                                        localMediaFolder4.setFirstMimeType(LocalMediaPageLoader.getFirstCoverMimeType(cursorQuery));
                                        if (jLastModified > (PictureMimeType.isContent(localMediaFolder4.getFirstImagePath()) ? new File(PictureFileUtils.getPath(LocalMediaPageLoader.this.c(), Uri.parse(localMediaFolder4.getFirstImagePath()))).lastModified() : new File(localMediaFolder4.getFirstImagePath()).lastModified())) {
                                            localMediaFolder4.setFirstImagePath(localMediaFolderLoadInAppSandboxFolderFile.getFirstImagePath());
                                            localMediaFolder4.setFirstMimeType(localMediaFolderLoadInAppSandboxFolderFile.getFirstMimeType());
                                        }
                                    }
                                } else if (cursorQuery.moveToFirst()) {
                                    localMediaFolder4.setFirstImagePath(SdkVersionUtils.isQ() ? LocalMediaPageLoader.getFirstUri(cursorQuery) : LocalMediaPageLoader.getFirstUrl(cursorQuery));
                                    localMediaFolder4.setFirstMimeType(LocalMediaPageLoader.getFirstCoverMimeType(cursorQuery));
                                }
                                if (folderTotalNum == 0) {
                                    if (!cursorQuery.isClosed()) {
                                        cursorQuery.close();
                                    }
                                    return arrayList;
                                }
                                SortUtils.sortFolder(arrayList);
                                localMediaFolder4.setFolderTotalNum(folderTotalNum);
                                localMediaFolder4.setBucketId(-1L);
                                if (TextUtils.isEmpty(LocalMediaPageLoader.this.b().defaultAlbumName)) {
                                    if (LocalMediaPageLoader.this.b().chooseMode == SelectMimeType.ofAudio()) {
                                        contextC = LocalMediaPageLoader.this.c();
                                        i2 = R.string.ps_all_audio;
                                    } else {
                                        contextC = LocalMediaPageLoader.this.c();
                                        i2 = R.string.ps_camera_roll;
                                    }
                                    string = contextC.getString(i2);
                                } else {
                                    string = LocalMediaPageLoader.this.b().defaultAlbumName;
                                }
                                localMediaFolder4.setFolderName(string);
                                arrayList.add(0, localMediaFolder4);
                                if (LocalMediaPageLoader.this.b().isSyncCover && LocalMediaPageLoader.this.b().chooseMode == SelectMimeType.ofAll()) {
                                    LocalMediaPageLoader.this.synchronousFirstCover(arrayList);
                                }
                                if (!cursorQuery.isClosed()) {
                                    cursorQuery.close();
                                }
                                return arrayList;
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            Log.i(IBridgeMediaLoader.f19070b, "loadAllMedia Data Error: " + e2.getMessage());
                            if (!cursorQuery.isClosed()) {
                            }
                        }
                        if (cursorQuery != null && !cursorQuery.isClosed()) {
                            cursorQuery.close();
                        }
                    } else if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return new ArrayList();
                } catch (Throwable th) {
                    if (!cursorQuery.isClosed()) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(List<LocalMediaFolder> list) {
                PictureThreadUtils.cancel(this);
                LocalMedia.destroyPool();
                OnQueryAllAlbumListener onQueryAllAlbumListener2 = onQueryAllAlbumListener;
                if (onQueryAllAlbumListener2 != null) {
                    onQueryAllAlbumListener2.onComplete(list);
                }
            }
        });
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public void loadOnlyInAppDirAllMedia(final OnQueryAlbumListener<LocalMediaFolder> onQueryAlbumListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<LocalMediaFolder>() { // from class: com.luck.picture.lib.loader.LocalMediaPageLoader.2
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public LocalMediaFolder doInBackground() {
                return SandboxFileLoader.loadInAppSandboxFolderFile(LocalMediaPageLoader.this.c(), LocalMediaPageLoader.this.b().sandboxDir);
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(LocalMediaFolder localMediaFolder) {
                PictureThreadUtils.cancel(this);
                OnQueryAlbumListener onQueryAlbumListener2 = onQueryAlbumListener;
                if (onQueryAlbumListener2 != null) {
                    onQueryAlbumListener2.onComplete(localMediaFolder);
                }
            }
        });
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public void loadPageMediaData(final long j2, final int i2, final int i3, final OnQueryDataResultListener<LocalMedia> onQueryDataResultListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<MediaData>() { // from class: com.luck.picture.lib.loader.LocalMediaPageLoader.1
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public MediaData doInBackground() {
                String strQ;
                ArrayList<LocalMedia> arrayListLoadInAppSandboxFile;
                Cursor cursorQuery = null;
                try {
                    try {
                        boolean z2 = true;
                        if (SdkVersionUtils.isR()) {
                            String pageSelection = LocalMediaPageLoader.this.getPageSelection(j2);
                            String[] pageSelectionArgs = LocalMediaPageLoader.this.getPageSelectionArgs(j2);
                            int i4 = i3;
                            cursorQuery = LocalMediaPageLoader.this.c().getContentResolver().query(IBridgeMediaLoader.f19071c, IBridgeMediaLoader.f19072d, MediaUtils.createQueryArgsBundle(pageSelection, pageSelectionArgs, i4, (i2 - 1) * i4, LocalMediaPageLoader.this.q()), null);
                        } else {
                            if (i2 == -1) {
                                strQ = LocalMediaPageLoader.this.q();
                            } else {
                                strQ = LocalMediaPageLoader.this.q() + " limit " + i3 + " offset " + ((i2 - 1) * i3);
                            }
                            cursorQuery = LocalMediaPageLoader.this.c().getContentResolver().query(IBridgeMediaLoader.f19071c, IBridgeMediaLoader.f19072d, LocalMediaPageLoader.this.getPageSelection(j2), LocalMediaPageLoader.this.getPageSelectionArgs(j2), strQ);
                        }
                        if (cursorQuery == null) {
                            if (cursorQuery != null && !cursorQuery.isClosed()) {
                                cursorQuery.close();
                            }
                            return new MediaData();
                        }
                        ArrayList arrayList = new ArrayList();
                        if (cursorQuery.getCount() > 0) {
                            cursorQuery.moveToFirst();
                            do {
                                LocalMedia localMediaR = LocalMediaPageLoader.this.r(cursorQuery, false);
                                if (localMediaR != null) {
                                    arrayList.add(localMediaR);
                                }
                            } while (cursorQuery.moveToNext());
                        }
                        if (j2 == -1 && i2 == 1 && (arrayListLoadInAppSandboxFile = SandboxFileLoader.loadInAppSandboxFile(LocalMediaPageLoader.this.c(), LocalMediaPageLoader.this.b().sandboxDir)) != null) {
                            arrayList.addAll(arrayListLoadInAppSandboxFile);
                            SortUtils.sortLocalMediaAddedTime(arrayList);
                        }
                        if (cursorQuery.getCount() <= 0) {
                            z2 = false;
                        }
                        MediaData mediaData = new MediaData(z2, arrayList);
                        if (!cursorQuery.isClosed()) {
                            cursorQuery.close();
                        }
                        return mediaData;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Log.i(IBridgeMediaLoader.f19070b, "loadMedia Page Data Error: " + e2.getMessage());
                        MediaData mediaData2 = new MediaData();
                        if (cursorQuery != null && !cursorQuery.isClosed()) {
                            cursorQuery.close();
                        }
                        return mediaData2;
                    }
                } catch (Throwable th) {
                    if (cursorQuery != null && !cursorQuery.isClosed()) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(MediaData mediaData) {
                PictureThreadUtils.cancel(this);
                OnQueryDataResultListener onQueryDataResultListener2 = onQueryDataResultListener;
                if (onQueryDataResultListener2 != null) {
                    ArrayList<LocalMedia> arrayList = mediaData.data;
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    onQueryDataResultListener2.onComplete(arrayList, mediaData.isHasNextMore);
                }
            }
        });
    }

    protected String o() {
        String strD = d();
        String strE = e();
        int i2 = b().chooseMode;
        if (i2 == 0) {
            return getSelectionArgsForAllMediaCondition(strD, strE, f(), g());
        }
        if (i2 == 1) {
            return getSelectionArgsForImageMediaCondition(strE, f());
        }
        if (i2 == 2) {
            return getSelectionArgsForVideoMediaCondition(strD, g());
        }
        if (i2 != 3) {
            return null;
        }
        return getSelectionArgsForAudioMediaCondition(strD, a());
    }

    protected String[] p() {
        int i2 = b().chooseMode;
        if (i2 == 0) {
            return new String[]{String.valueOf(1), String.valueOf(3)};
        }
        if (i2 == 1) {
            return new String[]{String.valueOf(1)};
        }
        if (i2 == 2) {
            return new String[]{String.valueOf(3)};
        }
        if (i2 != 3) {
            return null;
        }
        return new String[]{String.valueOf(2)};
    }

    protected String q() {
        return TextUtils.isEmpty(b().sortOrder) ? "date_modified DESC" : b().sortOrder;
    }

    protected LocalMedia r(Cursor cursor, boolean z2) throws IllegalArgumentException {
        String str;
        int i2;
        long j2;
        String[] strArr = IBridgeMediaLoader.f19072d;
        int columnIndexOrThrow = cursor.getColumnIndexOrThrow(strArr[0]);
        int columnIndexOrThrow2 = cursor.getColumnIndexOrThrow(strArr[1]);
        int columnIndexOrThrow3 = cursor.getColumnIndexOrThrow(strArr[2]);
        int columnIndexOrThrow4 = cursor.getColumnIndexOrThrow(strArr[3]);
        int columnIndexOrThrow5 = cursor.getColumnIndexOrThrow(strArr[4]);
        int columnIndexOrThrow6 = cursor.getColumnIndexOrThrow(strArr[5]);
        int columnIndexOrThrow7 = cursor.getColumnIndexOrThrow(strArr[6]);
        int columnIndexOrThrow8 = cursor.getColumnIndexOrThrow(strArr[7]);
        int columnIndexOrThrow9 = cursor.getColumnIndexOrThrow(strArr[8]);
        int columnIndexOrThrow10 = cursor.getColumnIndexOrThrow(strArr[9]);
        int columnIndexOrThrow11 = cursor.getColumnIndexOrThrow(strArr[10]);
        int columnIndexOrThrow12 = cursor.getColumnIndexOrThrow(strArr[11]);
        long j3 = cursor.getLong(columnIndexOrThrow);
        String string = cursor.getString(columnIndexOrThrow3);
        String string2 = cursor.getString(columnIndexOrThrow2);
        String realPathUri = SdkVersionUtils.isQ() ? MediaUtils.getRealPathUri(j3, string) : string2;
        if (TextUtils.isEmpty(string)) {
            string = PictureMimeType.ofJPEG();
        }
        if (b().isFilterInvalidFile) {
            if (PictureMimeType.isHasImage(string)) {
                if (!TextUtils.isEmpty(string2) && !PictureFileUtils.isImageFileExists(string2)) {
                    return null;
                }
            } else if (!PictureFileUtils.isFileExists(string2)) {
                return null;
            }
        }
        if (string.endsWith(SelectMimeType.SYSTEM_IMAGE)) {
            string = MediaUtils.getMimeTypeFromMediaUrl(string2);
            str = realPathUri;
            if (!b().isGif && PictureMimeType.isHasGif(string)) {
                return null;
            }
        } else {
            str = realPathUri;
        }
        if (string.endsWith(SelectMimeType.SYSTEM_IMAGE)) {
            return null;
        }
        if (!b().isWebp && string.startsWith(PictureMimeType.ofWEBP())) {
            return null;
        }
        if (!b().isBmp && PictureMimeType.isHasBmp(string)) {
            return null;
        }
        if (!b().isHeic && PictureMimeType.isHasHeic(string)) {
            return null;
        }
        int i3 = cursor.getInt(columnIndexOrThrow4);
        int i4 = cursor.getInt(columnIndexOrThrow5);
        int i5 = cursor.getInt(columnIndexOrThrow12);
        if (i5 == 90 || i5 == 270) {
            i2 = cursor.getInt(columnIndexOrThrow5);
            i4 = cursor.getInt(columnIndexOrThrow4);
        } else {
            i2 = i3;
        }
        long j4 = cursor.getLong(columnIndexOrThrow6);
        long j5 = cursor.getLong(columnIndexOrThrow7);
        String string3 = cursor.getString(columnIndexOrThrow8);
        String string4 = cursor.getString(columnIndexOrThrow9);
        long j6 = cursor.getLong(columnIndexOrThrow10);
        long j7 = cursor.getLong(columnIndexOrThrow11);
        if (TextUtils.isEmpty(string4)) {
            string4 = PictureMimeType.getUrlToFileName(string2);
        }
        if (b().isFilterSizeDuration && j5 > 0 && j5 < 1024) {
            return null;
        }
        if (PictureMimeType.isHasVideo(string) || PictureMimeType.isHasAudio(string)) {
            if (b().filterVideoMinSecond > 0) {
                j2 = j7;
                if (j4 < b().filterVideoMinSecond) {
                    return null;
                }
            } else {
                j2 = j7;
            }
            if (b().filterVideoMaxSecond > 0 && j4 > b().filterVideoMaxSecond) {
                return null;
            }
            if (b().isFilterSizeDuration && j4 <= 0) {
                return null;
            }
        } else {
            j2 = j7;
        }
        LocalMedia localMediaObtain = z2 ? LocalMedia.obtain() : LocalMedia.create();
        localMediaObtain.setId(j3);
        localMediaObtain.setBucketId(j6);
        localMediaObtain.setPath(str);
        localMediaObtain.setRealPath(string2);
        localMediaObtain.setFileName(string4);
        localMediaObtain.setParentFolderName(string3);
        localMediaObtain.setDuration(j4);
        localMediaObtain.setChooseModel(b().chooseMode);
        localMediaObtain.setMimeType(string);
        localMediaObtain.setWidth(i2);
        localMediaObtain.setHeight(i4);
        localMediaObtain.setSize(j5);
        localMediaObtain.setDateAddedTime(j2);
        OnQueryFilterListener onQueryFilterListener = this.f19074a.onQueryFilterListener;
        if (onQueryFilterListener == null || !onQueryFilterListener.onFilter(localMediaObtain)) {
            return localMediaObtain;
        }
        return null;
    }
}
