package org.mozilla.javascript.regexp;

import java.io.Serializable;

/* loaded from: classes5.dex */
final class RECharSet implements Serializable {
    static final long serialVersionUID = 7931787979395898394L;
    volatile transient byte[] bits;
    volatile transient boolean converted;
    final int length;
    final boolean sense;
    final int startIndex;
    final int strlength;

    RECharSet(int i2, int i3, int i4, boolean z2) {
        this.length = i2;
        this.startIndex = i3;
        this.strlength = i4;
        this.sense = z2;
    }
}
