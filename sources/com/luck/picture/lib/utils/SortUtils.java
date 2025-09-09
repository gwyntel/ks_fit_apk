package com.luck.picture.lib.utils;

import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public class SortUtils {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$sortFolder$0(LocalMediaFolder localMediaFolder, LocalMediaFolder localMediaFolder2) {
        if (localMediaFolder.getData() == null || localMediaFolder2.getData() == null) {
            return 0;
        }
        return Integer.compare(localMediaFolder2.getFolderTotalNum(), localMediaFolder.getFolderTotalNum());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$sortLocalMediaAddedTime$1(LocalMedia localMedia, LocalMedia localMedia2) {
        return Long.compare(localMedia2.getDateAddedTime(), localMedia.getDateAddedTime());
    }

    public static void sortFolder(List<LocalMediaFolder> list) {
        Collections.sort(list, new Comparator() { // from class: com.luck.picture.lib.utils.b
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return SortUtils.lambda$sortFolder$0((LocalMediaFolder) obj, (LocalMediaFolder) obj2);
            }
        });
    }

    public static void sortLocalMediaAddedTime(List<LocalMedia> list) {
        Collections.sort(list, new Comparator() { // from class: com.luck.picture.lib.utils.a
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return SortUtils.lambda$sortLocalMediaAddedTime$1((LocalMedia) obj, (LocalMedia) obj2);
            }
        });
    }
}
