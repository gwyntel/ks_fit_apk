package com.google.firebase.platforminfo;

import com.google.auto.value.AutoValue;
import javax.annotation.Nonnull;

@AutoValue
/* loaded from: classes3.dex */
abstract class LibraryVersion {
    LibraryVersion() {
    }

    static LibraryVersion a(String str, String str2) {
        return new AutoValue_LibraryVersion(str, str2);
    }

    @Nonnull
    public abstract String getLibraryName();

    @Nonnull
    public abstract String getVersion();
}
