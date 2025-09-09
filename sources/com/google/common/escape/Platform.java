package com.google.common.escape;

import com.google.common.annotations.GwtCompatible;
import java.util.Objects;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class Platform {
    private static final ThreadLocal<char[]> DEST_TL = new ThreadLocal<char[]>() { // from class: com.google.common.escape.Platform.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public char[] initialValue() {
            return new char[1024];
        }
    };

    private Platform() {
    }

    static char[] a() {
        char[] cArr = DEST_TL.get();
        Objects.requireNonNull(cArr);
        return cArr;
    }
}
