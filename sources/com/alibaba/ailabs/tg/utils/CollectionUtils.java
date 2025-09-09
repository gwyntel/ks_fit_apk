package com.alibaba.ailabs.tg.utils;

import android.text.TextUtils;
import java.util.Collection;

/* loaded from: classes2.dex */
public class CollectionUtils {
    public static final CharSequence DEFAULT_JOIN_SEPARATOR = ",";

    private CollectionUtils() {
    }

    public static <V> boolean isEmpty(Collection<V> collection) {
        return collection == null || collection.size() == 0;
    }

    public static String join(Iterable iterable) {
        return iterable == null ? "" : TextUtils.join(DEFAULT_JOIN_SEPARATOR, iterable);
    }
}
