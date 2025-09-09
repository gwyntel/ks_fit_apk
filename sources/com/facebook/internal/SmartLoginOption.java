package com.facebook.internal;

import java.util.EnumSet;
import java.util.Iterator;

/* loaded from: classes3.dex */
public enum SmartLoginOption {
    None(0),
    Enabled(1),
    RequireConfirm(2);

    public static final EnumSet<SmartLoginOption> ALL = EnumSet.allOf(SmartLoginOption.class);
    private final long mValue;

    SmartLoginOption(long j2) {
        this.mValue = j2;
    }

    public static EnumSet<SmartLoginOption> parseOptions(long j2) {
        EnumSet<SmartLoginOption> enumSetNoneOf = EnumSet.noneOf(SmartLoginOption.class);
        Iterator<SmartLoginOption> it = ALL.iterator();
        while (it.hasNext()) {
            SmartLoginOption next = it.next();
            if ((next.getValue() & j2) != 0) {
                enumSetNoneOf.add(next);
            }
        }
        return enumSetNoneOf;
    }

    public long getValue() {
        return this.mValue;
    }
}
