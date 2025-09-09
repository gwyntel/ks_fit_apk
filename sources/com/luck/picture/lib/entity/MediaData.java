package com.luck.picture.lib.entity;

import java.util.ArrayList;

/* loaded from: classes4.dex */
public class MediaData {
    public ArrayList<LocalMedia> data;
    public boolean isHasNextMore;

    public MediaData() {
    }

    public MediaData(boolean z2, ArrayList<LocalMedia> arrayList) {
        this.isHasNextMore = z2;
        this.data = arrayList;
    }
}
