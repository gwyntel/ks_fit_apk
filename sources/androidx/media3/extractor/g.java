package androidx.media3.extractor;

import androidx.media3.common.DataReader;
import androidx.media3.common.util.ParsableByteArray;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class g {
    public static int a(TrackOutput trackOutput, DataReader dataReader, int i2, boolean z2) {
        return trackOutput.sampleData(dataReader, i2, z2, 0);
    }

    public static void b(TrackOutput trackOutput, ParsableByteArray parsableByteArray, int i2) {
        trackOutput.sampleData(parsableByteArray, i2, 0);
    }
}
