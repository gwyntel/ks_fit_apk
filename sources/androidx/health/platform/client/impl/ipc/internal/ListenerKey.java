package androidx.health.platform.client.impl.ipc.internal;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class ListenerKey {
    private final Object mListenerKey;

    public ListenerKey(Object obj) {
        this.mListenerKey = obj;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ListenerKey)) {
            return false;
        }
        return this.mListenerKey.equals((ListenerKey) obj);
    }

    public int hashCode() {
        return System.identityHashCode(this.mListenerKey);
    }

    public String toString() {
        return String.valueOf(this.mListenerKey);
    }
}
