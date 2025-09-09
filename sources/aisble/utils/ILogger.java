package aisble.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

/* loaded from: classes.dex */
public interface ILogger {
    void log(int i2, @StringRes int i3, @Nullable Object... objArr);

    void log(int i2, @NonNull String str);
}
