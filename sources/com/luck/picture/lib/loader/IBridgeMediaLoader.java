package com.luck.picture.lib.loader;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.huawei.hms.framework.common.ContainerUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.interfaces.OnQueryAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryAllAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryDataResultListener;
import com.umeng.analytics.pro.bg;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public abstract class IBridgeMediaLoader {

    /* renamed from: b, reason: collision with root package name */
    protected static final String f19070b = "IBridgeMediaLoader";

    /* renamed from: c, reason: collision with root package name */
    protected static final Uri f19071c = MediaStore.Files.getContentUri("external");

    /* renamed from: d, reason: collision with root package name */
    protected static final String[] f19072d = {bg.f21483d, "_data", "mime_type", ViewHierarchyConstants.DIMENSION_WIDTH_KEY, ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, "duration", "_size", "bucket_display_name", "_display_name", "bucket_id", "date_added", "orientation"};

    /* renamed from: e, reason: collision with root package name */
    protected static final String[] f19073e = {bg.f21483d, "_data", "mime_type", ViewHierarchyConstants.DIMENSION_WIDTH_KEY, ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, "duration", "_size", "bucket_display_name", "_display_name", "bucket_id", "date_added", "orientation", "COUNT(*) AS count"};

    /* renamed from: a, reason: collision with root package name */
    protected final SelectorConfig f19074a;
    private final Context mContext;

    public IBridgeMediaLoader(Context context, SelectorConfig selectorConfig) {
        this.mContext = context;
        this.f19074a = selectorConfig;
    }

    protected String a() {
        List<String> list = b().queryOnlyAudioList;
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (i2 < list.size()) {
            String str = list.get(i2);
            sb.append(i2 == 0 ? " AND " : " OR ");
            sb.append("mime_type");
            sb.append("='");
            sb.append(str);
            sb.append("'");
            i2++;
        }
        return sb.toString();
    }

    protected SelectorConfig b() {
        return this.f19074a;
    }

    protected Context c() {
        return this.mContext;
    }

    protected String d() {
        return String.format(Locale.CHINA, "%d <%s duration and duration <= %d", Long.valueOf(Math.max(0L, b().filterVideoMinSecond)), ContainerUtils.KEY_VALUE_DELIMITER, Long.valueOf(b().filterVideoMaxSecond == 0 ? Long.MAX_VALUE : b().filterVideoMaxSecond));
    }

    protected String e() {
        return String.format(Locale.CHINA, "%d <%s _size and _size <= %d", Long.valueOf(Math.max(0L, b().filterMinFileSize)), ContainerUtils.KEY_VALUE_DELIMITER, Long.valueOf(b().filterMaxFileSize == 0 ? Long.MAX_VALUE : b().filterMaxFileSize));
    }

    protected String f() {
        List<String> list = b().queryOnlyImageList;
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (i2 < list.size()) {
            String str = list.get(i2);
            sb.append(i2 == 0 ? " AND " : " OR ");
            sb.append("mime_type");
            sb.append("='");
            sb.append(str);
            sb.append("'");
            i2++;
        }
        if (!b().isGif && !b().queryOnlyImageList.contains(PictureMimeType.ofGIF())) {
            sb.append(" AND (mime_type!='image/gif')");
        }
        if (!b().isWebp && !b().queryOnlyImageList.contains(PictureMimeType.ofWEBP())) {
            sb.append(" AND (mime_type!='image/webp')");
        }
        if (!b().isBmp && !b().queryOnlyImageList.contains(PictureMimeType.ofBMP()) && !b().queryOnlyImageList.contains(PictureMimeType.ofXmsBMP()) && !b().queryOnlyImageList.contains(PictureMimeType.ofWapBMP())) {
            sb.append(" AND (mime_type!='image/bmp')");
            sb.append(" AND (mime_type!='image/x-ms-bmp')");
            sb.append(" AND (mime_type!='image/vnd.wap.wbmp')");
        }
        if (!b().isHeic && !b().queryOnlyImageList.contains(PictureMimeType.ofHeic())) {
            sb.append(" AND (mime_type!='image/heic')");
        }
        return sb.toString();
    }

    protected String g() {
        List<String> list = b().queryOnlyVideoList;
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (i2 < list.size()) {
            String str = list.get(i2);
            sb.append(i2 == 0 ? " AND " : " OR ");
            sb.append("mime_type");
            sb.append("='");
            sb.append(str);
            sb.append("'");
            i2++;
        }
        return sb.toString();
    }

    public abstract String getAlbumFirstCover(long j2);

    public abstract void loadAllAlbum(OnQueryAllAlbumListener<LocalMediaFolder> onQueryAllAlbumListener);

    public abstract void loadOnlyInAppDirAllMedia(OnQueryAlbumListener<LocalMediaFolder> onQueryAlbumListener);

    public abstract void loadPageMediaData(long j2, int i2, int i3, OnQueryDataResultListener<LocalMedia> onQueryDataResultListener);
}
