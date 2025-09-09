package androidx.media3.common.util;

@UnstableApi
/* loaded from: classes.dex */
public interface TimestampIterator {
    TimestampIterator copyOf();

    long getLastTimestampUs();

    boolean hasNext();

    long next();
}
