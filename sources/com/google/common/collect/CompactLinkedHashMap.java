package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.CheckForNull;

@J2ktIncompatible
@GwtIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
class CompactLinkedHashMap<K, V> extends CompactHashMap<K, V> {
    private static final int ENDPOINT = -2;
    private final boolean accessOrder;
    private transient int firstEntry;
    private transient int lastEntry;

    @VisibleForTesting
    @CheckForNull
    transient long[] links;

    CompactLinkedHashMap() {
        this(3);
    }

    public static <K, V> CompactLinkedHashMap<K, V> create() {
        return new CompactLinkedHashMap<>();
    }

    public static <K, V> CompactLinkedHashMap<K, V> createWithExpectedSize(int i2) {
        return new CompactLinkedHashMap<>(i2);
    }

    private int getPredecessor(int i2) {
        return ((int) (link(i2) >>> 32)) - 1;
    }

    private long link(int i2) {
        return requireLinks()[i2];
    }

    private long[] requireLinks() {
        long[] jArr = this.links;
        Objects.requireNonNull(jArr);
        return jArr;
    }

    private void setLink(int i2, long j2) {
        requireLinks()[i2] = j2;
    }

    private void setPredecessor(int i2, int i3) {
        setLink(i2, (link(i2) & 4294967295L) | ((i3 + 1) << 32));
    }

    private void setSucceeds(int i2, int i3) {
        if (i2 == -2) {
            this.firstEntry = i3;
        } else {
            setSuccessor(i2, i3);
        }
        if (i3 == -2) {
            this.lastEntry = i2;
        } else {
            setPredecessor(i3, i2);
        }
    }

    private void setSuccessor(int i2, int i3) {
        setLink(i2, (link(i2) & (-4294967296L)) | ((i3 + 1) & 4294967295L));
    }

    @Override // com.google.common.collect.CompactHashMap
    void accessEntry(int i2) {
        if (this.accessOrder) {
            setSucceeds(getPredecessor(i2), getSuccessor(i2));
            setSucceeds(this.lastEntry, i2);
            setSucceeds(i2, -2);
            incrementModCount();
        }
    }

    @Override // com.google.common.collect.CompactHashMap
    int adjustAfterRemove(int i2, int i3) {
        return i2 >= size() ? i3 : i2;
    }

    @Override // com.google.common.collect.CompactHashMap
    int allocArrays() {
        int iAllocArrays = super.allocArrays();
        this.links = new long[iAllocArrays];
        return iAllocArrays;
    }

    @Override // com.google.common.collect.CompactHashMap, java.util.AbstractMap, java.util.Map
    public void clear() {
        if (needsAllocArrays()) {
            return;
        }
        this.firstEntry = -2;
        this.lastEntry = -2;
        long[] jArr = this.links;
        if (jArr != null) {
            Arrays.fill(jArr, 0, size(), 0L);
        }
        super.clear();
    }

    @Override // com.google.common.collect.CompactHashMap
    @CanIgnoreReturnValue
    Map<K, V> convertToHashFloodingResistantImplementation() {
        Map<K, V> mapConvertToHashFloodingResistantImplementation = super.convertToHashFloodingResistantImplementation();
        this.links = null;
        return mapConvertToHashFloodingResistantImplementation;
    }

    @Override // com.google.common.collect.CompactHashMap
    Map<K, V> createHashFloodingResistantDelegate(int i2) {
        return new LinkedHashMap(i2, 1.0f, this.accessOrder);
    }

    @Override // com.google.common.collect.CompactHashMap
    int firstEntryIndex() {
        return this.firstEntry;
    }

    @Override // com.google.common.collect.CompactHashMap
    int getSuccessor(int i2) {
        return ((int) link(i2)) - 1;
    }

    @Override // com.google.common.collect.CompactHashMap
    void init(int i2) {
        super.init(i2);
        this.firstEntry = -2;
        this.lastEntry = -2;
    }

    @Override // com.google.common.collect.CompactHashMap
    void insertEntry(int i2, @ParametricNullness K k2, @ParametricNullness V v2, int i3, int i4) {
        super.insertEntry(i2, k2, v2, i3, i4);
        setSucceeds(this.lastEntry, i2);
        setSucceeds(i2, -2);
    }

    @Override // com.google.common.collect.CompactHashMap
    void moveLastEntry(int i2, int i3) {
        int size = size() - 1;
        super.moveLastEntry(i2, i3);
        setSucceeds(getPredecessor(i2), getSuccessor(i2));
        if (i2 < size) {
            setSucceeds(getPredecessor(size), i2);
            setSucceeds(i2, getSuccessor(size));
        }
        setLink(size, 0L);
    }

    @Override // com.google.common.collect.CompactHashMap
    void resizeEntries(int i2) {
        super.resizeEntries(i2);
        this.links = Arrays.copyOf(requireLinks(), i2);
    }

    CompactLinkedHashMap(int i2) {
        this(i2, false);
    }

    CompactLinkedHashMap(int i2, boolean z2) {
        super(i2);
        this.accessOrder = z2;
    }
}
