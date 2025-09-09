package androidx.preference;

import androidx.annotation.Nullable;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class PreferenceDataStore {
    public boolean getBoolean(String str, boolean z2) {
        return z2;
    }

    public float getFloat(String str, float f2) {
        return f2;
    }

    public int getInt(String str, int i2) {
        return i2;
    }

    public long getLong(String str, long j2) {
        return j2;
    }

    @Nullable
    public String getString(String str, @Nullable String str2) {
        return str2;
    }

    @Nullable
    public Set<String> getStringSet(String str, @Nullable Set<String> set) {
        return set;
    }

    public void putBoolean(String str, boolean z2) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    public void putFloat(String str, float f2) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    public void putInt(String str, int i2) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    public void putLong(String str, long j2) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    public void putString(String str, @Nullable String str2) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    public void putStringSet(String str, @Nullable Set<String> set) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }
}
