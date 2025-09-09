package androidx.webkit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import java.util.List;

@UiThread
/* loaded from: classes2.dex */
public interface ProfileStore {
    boolean deleteProfile(@NonNull String str);

    @NonNull
    List<String> getAllProfileNames();

    @NonNull
    Profile getOrCreateProfile(@NonNull String str);

    @Nullable
    Profile getProfile(@NonNull String str);
}
