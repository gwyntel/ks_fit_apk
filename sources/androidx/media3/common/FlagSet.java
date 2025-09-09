package androidx.media3.common;

import android.util.SparseBooleanArray;
import androidx.annotation.Nullable;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@UnstableApi
/* loaded from: classes.dex */
public final class FlagSet {
    private final SparseBooleanArray flags;

    public boolean contains(int i2) {
        return this.flags.get(i2);
    }

    public boolean containsAny(int... iArr) {
        for (int i2 : iArr) {
            if (contains(i2)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FlagSet)) {
            return false;
        }
        FlagSet flagSet = (FlagSet) obj;
        if (Util.SDK_INT >= 24) {
            return this.flags.equals(flagSet.flags);
        }
        if (size() != flagSet.size()) {
            return false;
        }
        for (int i2 = 0; i2 < size(); i2++) {
            if (get(i2) != flagSet.get(i2)) {
                return false;
            }
        }
        return true;
    }

    public int get(int i2) {
        Assertions.checkIndex(i2, 0, size());
        return this.flags.keyAt(i2);
    }

    public int hashCode() {
        if (Util.SDK_INT >= 24) {
            return this.flags.hashCode();
        }
        int size = size();
        for (int i2 = 0; i2 < size(); i2++) {
            size = (size * 31) + get(i2);
        }
        return size;
    }

    public int size() {
        return this.flags.size();
    }

    public static final class Builder {
        private boolean buildCalled;
        private final SparseBooleanArray flags = new SparseBooleanArray();

        @CanIgnoreReturnValue
        public Builder add(int i2) {
            Assertions.checkState(!this.buildCalled);
            this.flags.append(i2, true);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder addAll(int... iArr) {
            for (int i2 : iArr) {
                add(i2);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder addIf(int i2, boolean z2) {
            return z2 ? add(i2) : this;
        }

        public FlagSet build() {
            Assertions.checkState(!this.buildCalled);
            this.buildCalled = true;
            return new FlagSet(this.flags);
        }

        @CanIgnoreReturnValue
        public Builder remove(int i2) {
            Assertions.checkState(!this.buildCalled);
            this.flags.delete(i2);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder removeAll(int... iArr) {
            for (int i2 : iArr) {
                remove(i2);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder removeIf(int i2, boolean z2) {
            return z2 ? remove(i2) : this;
        }

        @CanIgnoreReturnValue
        public Builder addAll(FlagSet flagSet) {
            for (int i2 = 0; i2 < flagSet.size(); i2++) {
                add(flagSet.get(i2));
            }
            return this;
        }
    }

    private FlagSet(SparseBooleanArray sparseBooleanArray) {
        this.flags = sparseBooleanArray;
    }
}
