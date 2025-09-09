package com.google.zxing.oned.rss.expanded;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
final class ExpandedRow {
    private final List<ExpandedPair> pairs;
    private final int rowNumber;
    private final boolean wasReversed;

    ExpandedRow(List list, int i2, boolean z2) {
        this.pairs = new ArrayList(list);
        this.rowNumber = i2;
        this.wasReversed = z2;
    }

    List a() {
        return this.pairs;
    }

    int b() {
        return this.rowNumber;
    }

    boolean c(List list) {
        return this.pairs.equals(list);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ExpandedRow)) {
            return false;
        }
        ExpandedRow expandedRow = (ExpandedRow) obj;
        return this.pairs.equals(expandedRow.a()) && this.wasReversed == expandedRow.wasReversed;
    }

    public int hashCode() {
        return this.pairs.hashCode() ^ Boolean.valueOf(this.wasReversed).hashCode();
    }

    public String toString() {
        return "{ " + this.pairs + " }";
    }
}
