package androidx.browser.customtabs;

import android.os.Bundle;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(33)
/* loaded from: classes.dex */
class Api33Impl {
    private Api33Impl() {
    }

    @DoNotInline
    static <T> T a(@NonNull Bundle bundle, @Nullable String str, @NonNull Class<T> cls) {
        return (T) bundle.getParcelable(str, cls);
    }
}
