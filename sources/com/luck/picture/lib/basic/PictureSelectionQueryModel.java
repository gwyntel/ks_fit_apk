package com.luck.picture.lib.basic;

import android.app.Activity;
import android.text.TextUtils;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.config.SelectorProviders;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.interfaces.OnQueryAllAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryDataResultListener;
import com.luck.picture.lib.interfaces.OnQueryDataSourceListener;
import com.luck.picture.lib.interfaces.OnQueryFilterListener;
import com.luck.picture.lib.loader.IBridgeMediaLoader;
import com.luck.picture.lib.loader.LocalMediaLoader;
import com.luck.picture.lib.loader.LocalMediaPageLoader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PictureSelectionQueryModel {
    private final SelectorConfig selectionConfig;
    private final PictureSelector selector;

    public PictureSelectionQueryModel(PictureSelector pictureSelector, int i2) {
        this.selector = pictureSelector;
        SelectorConfig selectorConfig = new SelectorConfig();
        this.selectionConfig = selectorConfig;
        SelectorProviders.getInstance().addSelectorConfigQueue(selectorConfig);
        selectorConfig.chooseMode = i2;
    }

    public IBridgeMediaLoader buildMediaLoader() {
        Activity activityA = this.selector.a();
        if (activityA != null) {
            return this.selectionConfig.isPageStrategy ? new LocalMediaPageLoader(activityA, this.selectionConfig) : new LocalMediaLoader(activityA, this.selectionConfig);
        }
        throw new NullPointerException("Activity cannot be null");
    }

    public PictureSelectionQueryModel isBmp(boolean z2) {
        this.selectionConfig.isBmp = z2;
        return this;
    }

    public PictureSelectionQueryModel isGif(boolean z2) {
        this.selectionConfig.isGif = z2;
        return this;
    }

    public PictureSelectionQueryModel isHeic(boolean z2) {
        this.selectionConfig.isHeic = z2;
        return this;
    }

    public PictureSelectionQueryModel isPageStrategy(boolean z2) {
        this.selectionConfig.isPageStrategy = z2;
        return this;
    }

    public PictureSelectionQueryModel isWebp(boolean z2) {
        this.selectionConfig.isWebp = z2;
        return this;
    }

    public void obtainAlbumData(final OnQueryDataSourceListener<LocalMediaFolder> onQueryDataSourceListener) {
        Activity activityA = this.selector.a();
        if (activityA == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (onQueryDataSourceListener == null) {
            throw new NullPointerException("OnQueryDataSourceListener cannot be null");
        }
        (this.selectionConfig.isPageStrategy ? new LocalMediaPageLoader(activityA, this.selectionConfig) : new LocalMediaLoader(activityA, this.selectionConfig)).loadAllAlbum(new OnQueryAllAlbumListener<LocalMediaFolder>() { // from class: com.luck.picture.lib.basic.PictureSelectionQueryModel.1
            @Override // com.luck.picture.lib.interfaces.OnQueryAllAlbumListener
            public void onComplete(List<LocalMediaFolder> list) {
                onQueryDataSourceListener.onComplete(list);
            }
        });
    }

    public void obtainMediaData(final OnQueryDataSourceListener<LocalMedia> onQueryDataSourceListener) {
        Activity activityA = this.selector.a();
        if (activityA == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (onQueryDataSourceListener == null) {
            throw new NullPointerException("OnQueryDataSourceListener cannot be null");
        }
        final IBridgeMediaLoader localMediaPageLoader = this.selectionConfig.isPageStrategy ? new LocalMediaPageLoader(activityA, this.selectionConfig) : new LocalMediaLoader(activityA, this.selectionConfig);
        localMediaPageLoader.loadAllAlbum(new OnQueryAllAlbumListener<LocalMediaFolder>() { // from class: com.luck.picture.lib.basic.PictureSelectionQueryModel.2
            @Override // com.luck.picture.lib.interfaces.OnQueryAllAlbumListener
            public void onComplete(List<LocalMediaFolder> list) {
                if (list == null || list.size() <= 0) {
                    return;
                }
                LocalMediaFolder localMediaFolder = list.get(0);
                if (PictureSelectionQueryModel.this.selectionConfig.isPageStrategy) {
                    localMediaPageLoader.loadPageMediaData(localMediaFolder.getBucketId(), 1, PictureSelectionQueryModel.this.selectionConfig.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.basic.PictureSelectionQueryModel.2.1
                        @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                        public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                            onQueryDataSourceListener.onComplete(arrayList);
                        }
                    });
                } else {
                    onQueryDataSourceListener.onComplete(localMediaFolder.getData());
                }
            }
        });
    }

    public PictureSelectionQueryModel setFilterMaxFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.filterMaxFileSize = j2;
        } else {
            this.selectionConfig.filterMaxFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionQueryModel setFilterMinFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.filterMinFileSize = j2;
        } else {
            this.selectionConfig.filterMinFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionQueryModel setFilterVideoMaxSecond(int i2) {
        this.selectionConfig.filterVideoMaxSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionQueryModel setFilterVideoMinSecond(int i2) {
        this.selectionConfig.filterVideoMinSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionQueryModel setQueryFilterListener(OnQueryFilterListener onQueryFilterListener) {
        this.selectionConfig.onQueryFilterListener = onQueryFilterListener;
        return this;
    }

    public PictureSelectionQueryModel setQuerySortOrder(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.selectionConfig.sortOrder = str;
        }
        return this;
    }

    public PictureSelectionQueryModel isPageStrategy(boolean z2, int i2) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.isPageStrategy = z2;
        if (i2 < 10) {
            i2 = 60;
        }
        selectorConfig.pageSize = i2;
        return this;
    }

    public PictureSelectionQueryModel isPageStrategy(boolean z2, int i2, boolean z3) {
        SelectorConfig selectorConfig = this.selectionConfig;
        selectorConfig.isPageStrategy = z2;
        if (i2 < 10) {
            i2 = 60;
        }
        selectorConfig.pageSize = i2;
        selectorConfig.isFilterInvalidFile = z3;
        return this;
    }
}
