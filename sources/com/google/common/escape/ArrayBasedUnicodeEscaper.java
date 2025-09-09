package com.google.common.escape;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class ArrayBasedUnicodeEscaper extends UnicodeEscaper {
    private final char[][] replacements;
    private final int replacementsLength;
    private final int safeMax;
    private final char safeMaxChar;
    private final int safeMin;
    private final char safeMinChar;

    @Override // com.google.common.escape.UnicodeEscaper
    protected final char[] b(int i2) {
        char[] cArr;
        if (i2 < this.replacementsLength && (cArr = this.replacements[i2]) != null) {
            return cArr;
        }
        if (i2 < this.safeMin || i2 > this.safeMax) {
            return e(i2);
        }
        return null;
    }

    @Override // com.google.common.escape.UnicodeEscaper
    protected final int d(CharSequence charSequence, int i2, int i3) {
        while (i2 < i3) {
            char cCharAt = charSequence.charAt(i2);
            if ((cCharAt < this.replacementsLength && this.replacements[cCharAt] != null) || cCharAt > this.safeMaxChar || cCharAt < this.safeMinChar) {
                break;
            }
            i2++;
        }
        return i2;
    }

    protected abstract char[] e(int i2);

    @Override // com.google.common.escape.UnicodeEscaper, com.google.common.escape.Escaper
    public final String escape(String str) {
        Preconditions.checkNotNull(str);
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if ((cCharAt < this.replacementsLength && this.replacements[cCharAt] != null) || cCharAt > this.safeMaxChar || cCharAt < this.safeMinChar) {
                return c(str, i2);
            }
        }
        return str;
    }
}
