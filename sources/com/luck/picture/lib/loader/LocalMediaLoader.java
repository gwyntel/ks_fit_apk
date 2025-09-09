package com.luck.picture.lib.loader;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.interfaces.OnQueryAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryAllAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryDataResultListener;
import com.luck.picture.lib.interfaces.OnQueryFilterListener;
import com.luck.picture.lib.thread.PictureThreadUtils;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.SdkVersionUtils;
import com.luck.picture.lib.utils.SortUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class LocalMediaLoader extends IBridgeMediaLoader {
    public LocalMediaLoader(Context context, SelectorConfig selectorConfig) {
        super(context, selectorConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LocalMediaFolder getImageFolder(String str, String str2, String str3, List<LocalMediaFolder> list) {
        for (LocalMediaFolder localMediaFolder : list) {
            String folderName = localMediaFolder.getFolderName();
            if (!TextUtils.isEmpty(folderName) && TextUtils.equals(folderName, str3)) {
                return localMediaFolder;
            }
        }
        LocalMediaFolder localMediaFolder2 = new LocalMediaFolder();
        localMediaFolder2.setFolderName(str3);
        localMediaFolder2.setFirstImagePath(str);
        localMediaFolder2.setFirstMimeType(str2);
        list.add(localMediaFolder2);
        return localMediaFolder2;
    }

    private static String getSelectionArgsForAllMediaCondition(String str, String str2, String str3, String str4) {
        return "(media_type=?" + str3 + " OR " + MessengerShareContentUtility.MEDIA_TYPE + "=?" + str4 + " AND " + str + ") AND " + str2;
    }

    private static String getSelectionArgsForAudioMediaCondition(String str, String str2) {
        return "media_type=?" + str2 + " AND " + str;
    }

    private static String getSelectionArgsForImageMediaCondition(String str, String str2) {
        return "media_type=?" + str2 + " AND " + str;
    }

    private static String getSelectionArgsForVideoMediaCondition(String str, String str2) {
        return "media_type=?" + str2 + " AND " + str;
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public String getAlbumFirstCover(long j2) {
        return null;
    }

    protected String i() {
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

    protected String[] j() {
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

    protected String k() {
        return TextUtils.isEmpty(b().sortOrder) ? "date_modified DESC" : b().sortOrder;
    }

    protected LocalMedia l(Cursor cursor, boolean z2) throws IllegalArgumentException {
        long j2;
        long j3;
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
        long j4 = cursor.getLong(columnIndexOrThrow);
        long j5 = cursor.getLong(columnIndexOrThrow11);
        String string = cursor.getString(columnIndexOrThrow3);
        String string2 = cursor.getString(columnIndexOrThrow2);
        String realPathUri = SdkVersionUtils.isQ() ? MediaUtils.getRealPathUri(j4, string) : string2;
        if (TextUtils.isEmpty(string)) {
            string = PictureMimeType.ofJPEG();
        }
        if (string.endsWith(SelectMimeType.SYSTEM_IMAGE)) {
            string = MediaUtils.getMimeTypeFromMediaUrl(string2);
            j2 = j5;
            if (!b().isGif && PictureMimeType.isHasGif(string)) {
                return null;
            }
        } else {
            j2 = j5;
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
        int i2 = cursor.getInt(columnIndexOrThrow4);
        int i3 = cursor.getInt(columnIndexOrThrow5);
        int i4 = cursor.getInt(columnIndexOrThrow12);
        if (i4 == 90 || i4 == 270) {
            i2 = cursor.getInt(columnIndexOrThrow5);
            i3 = cursor.getInt(columnIndexOrThrow4);
        }
        long j6 = cursor.getLong(columnIndexOrThrow6);
        long j7 = cursor.getLong(columnIndexOrThrow7);
        String string3 = cursor.getString(columnIndexOrThrow8);
        String string4 = cursor.getString(columnIndexOrThrow9);
        int i5 = i2;
        long j8 = cursor.getLong(columnIndexOrThrow10);
        if (TextUtils.isEmpty(string4)) {
            string4 = PictureMimeType.getUrlToFileName(string2);
        }
        if (b().isFilterSizeDuration && j7 > 0 && j7 < 1024) {
            return null;
        }
        if (PictureMimeType.isHasVideo(string) || PictureMimeType.isHasAudio(string)) {
            if (b().filterVideoMinSecond > 0) {
                j3 = j7;
                if (j6 < b().filterVideoMinSecond) {
                    return null;
                }
            } else {
                j3 = j7;
            }
            if (b().filterVideoMaxSecond > 0 && j6 > b().filterVideoMaxSecond) {
                return null;
            }
            if (b().isFilterSizeDuration && j6 <= 0) {
                return null;
            }
        } else {
            j3 = j7;
        }
        LocalMedia localMediaCreate = LocalMedia.create();
        localMediaCreate.setId(j4);
        localMediaCreate.setBucketId(j8);
        localMediaCreate.setPath(realPathUri);
        localMediaCreate.setRealPath(string2);
        localMediaCreate.setFileName(string4);
        localMediaCreate.setParentFolderName(string3);
        localMediaCreate.setDuration(j6);
        localMediaCreate.setChooseModel(b().chooseMode);
        localMediaCreate.setMimeType(string);
        localMediaCreate.setWidth(i5);
        localMediaCreate.setHeight(i3);
        localMediaCreate.setSize(j3);
        localMediaCreate.setDateAddedTime(j2);
        OnQueryFilterListener onQueryFilterListener = this.f19074a.onQueryFilterListener;
        if (onQueryFilterListener == null || !onQueryFilterListener.onFilter(localMediaCreate)) {
            return localMediaCreate;
        }
        return null;
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public void loadAllAlbum(final OnQueryAllAlbumListener<LocalMediaFolder> onQueryAllAlbumListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<List<LocalMediaFolder>>() { // from class: com.luck.picture.lib.loader.LocalMediaLoader.1
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public List<LocalMediaFolder> doInBackground() {
                String string;
                Context contextC;
                int i2;
                ArrayList arrayList = new ArrayList();
                Cursor cursorQuery = LocalMediaLoader.this.c().getContentResolver().query(IBridgeMediaLoader.f19071c, IBridgeMediaLoader.f19072d, LocalMediaLoader.this.i(), LocalMediaLoader.this.j(), LocalMediaLoader.this.k());
                try {
                    if (cursorQuery != null) {
                        try {
                            LocalMediaFolder localMediaFolder = new LocalMediaFolder();
                            ArrayList<LocalMedia> arrayList2 = new ArrayList<>();
                            if (cursorQuery.getCount() > 0) {
                                cursorQuery.moveToFirst();
                                do {
                                    LocalMedia localMediaL = LocalMediaLoader.this.l(cursorQuery, false);
                                    if (localMediaL != null) {
                                        LocalMediaFolder imageFolder = LocalMediaLoader.this.getImageFolder(localMediaL.getPath(), localMediaL.getMimeType(), localMediaL.getParentFolderName(), arrayList);
                                        imageFolder.setBucketId(localMediaL.getBucketId());
                                        imageFolder.getData().add(localMediaL);
                                        imageFolder.setFolderTotalNum(imageFolder.getFolderTotalNum() + 1);
                                        imageFolder.setBucketId(localMediaL.getBucketId());
                                        arrayList2.add(localMediaL);
                                        localMediaFolder.setFolderTotalNum(localMediaFolder.getFolderTotalNum() + 1);
                                    }
                                } while (cursorQuery.moveToNext());
                                LocalMediaFolder localMediaFolderLoadInAppSandboxFolderFile = SandboxFileLoader.loadInAppSandboxFolderFile(LocalMediaLoader.this.c(), LocalMediaLoader.this.b().sandboxDir);
                                if (localMediaFolderLoadInAppSandboxFolderFile != null) {
                                    arrayList.add(localMediaFolderLoadInAppSandboxFolderFile);
                                    localMediaFolder.setFolderTotalNum(localMediaFolder.getFolderTotalNum() + localMediaFolderLoadInAppSandboxFolderFile.getFolderTotalNum());
                                    localMediaFolder.setData(localMediaFolderLoadInAppSandboxFolderFile.getData());
                                    arrayList2.addAll(0, localMediaFolderLoadInAppSandboxFolderFile.getData());
                                    if (60 > localMediaFolderLoadInAppSandboxFolderFile.getFolderTotalNum()) {
                                        if (arrayList2.size() > 60) {
                                            SortUtils.sortLocalMediaAddedTime(arrayList2.subList(0, 60));
                                        } else {
                                            SortUtils.sortLocalMediaAddedTime(arrayList2);
                                        }
                                    }
                                }
                                if (arrayList2.size() > 0) {
                                    SortUtils.sortFolder(arrayList);
                                    arrayList.add(0, localMediaFolder);
                                    localMediaFolder.setFirstImagePath(arrayList2.get(0).getPath());
                                    localMediaFolder.setFirstMimeType(arrayList2.get(0).getMimeType());
                                    if (TextUtils.isEmpty(LocalMediaLoader.this.b().defaultAlbumName)) {
                                        if (LocalMediaLoader.this.b().chooseMode == SelectMimeType.ofAudio()) {
                                            contextC = LocalMediaLoader.this.c();
                                            i2 = R.string.ps_all_audio;
                                        } else {
                                            contextC = LocalMediaLoader.this.c();
                                            i2 = R.string.ps_camera_roll;
                                        }
                                        string = contextC.getString(i2);
                                    } else {
                                        string = LocalMediaLoader.this.b().defaultAlbumName;
                                    }
                                    localMediaFolder.setFolderName(string);
                                    localMediaFolder.setBucketId(-1L);
                                    localMediaFolder.setData(arrayList2);
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            if (!cursorQuery.isClosed()) {
                            }
                        }
                        if (cursorQuery != null && !cursorQuery.isClosed()) {
                            cursorQuery.close();
                        }
                    } else if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayList;
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
                OnQueryAllAlbumListener onQueryAllAlbumListener2 = onQueryAllAlbumListener;
                if (onQueryAllAlbumListener2 != null) {
                    onQueryAllAlbumListener2.onComplete(list);
                }
            }
        });
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public void loadOnlyInAppDirAllMedia(final OnQueryAlbumListener<LocalMediaFolder> onQueryAlbumListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<LocalMediaFolder>() { // from class: com.luck.picture.lib.loader.LocalMediaLoader.2
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public LocalMediaFolder doInBackground() {
                return SandboxFileLoader.loadInAppSandboxFolderFile(LocalMediaLoader.this.c(), LocalMediaLoader.this.b().sandboxDir);
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
    public void loadPageMediaData(long j2, int i2, int i3, OnQueryDataResultListener<LocalMedia> onQueryDataResultListener) {
    }
}
