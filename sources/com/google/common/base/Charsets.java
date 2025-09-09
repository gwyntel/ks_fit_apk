package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Charsets {

    @J2ktIncompatible
    @GwtIncompatible
    public static final Charset US_ASCII = StandardCharsets.US_ASCII;
    public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    @J2ktIncompatible
    @GwtIncompatible
    public static final Charset UTF_16BE = StandardCharsets.UTF_16BE;

    @J2ktIncompatible
    @GwtIncompatible
    public static final Charset UTF_16LE = StandardCharsets.UTF_16LE;

    @J2ktIncompatible
    @GwtIncompatible
    public static final Charset UTF_16 = StandardCharsets.UTF_16;

    private Charsets() {
    }
}
