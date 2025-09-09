package androidx.media3.extractor;

import android.net.Uri;
import androidx.media3.extractor.text.SubtitleParser;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class f {
    static {
        ExtractorsFactory extractorsFactory = ExtractorsFactory.EMPTY;
    }

    public static Extractor[] a(ExtractorsFactory extractorsFactory, Uri uri, Map map) {
        return extractorsFactory.createExtractors();
    }

    public static /* synthetic */ Extractor[] d() {
        return new Extractor[0];
    }

    public static ExtractorsFactory b(ExtractorsFactory extractorsFactory, boolean z2) {
        return extractorsFactory;
    }

    public static ExtractorsFactory c(ExtractorsFactory extractorsFactory, SubtitleParser.Factory factory) {
        return extractorsFactory;
    }
}
