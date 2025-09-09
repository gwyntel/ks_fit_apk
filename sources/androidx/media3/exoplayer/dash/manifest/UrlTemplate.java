package androidx.media3.exoplayer.dash.manifest;

import androidx.media3.common.util.UnstableApi;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@UnstableApi
/* loaded from: classes.dex */
public final class UrlTemplate {
    private static final String BANDWIDTH = "Bandwidth";
    private static final int BANDWIDTH_ID = 3;
    private static final String DEFAULT_FORMAT_TAG = "%01d";
    private static final String ESCAPED_DOLLAR = "$$";
    private static final String NUMBER = "Number";
    private static final int NUMBER_ID = 2;
    private static final String REPRESENTATION = "RepresentationID";
    private static final int REPRESENTATION_ID = 1;
    private static final String TIME = "Time";
    private static final int TIME_ID = 4;
    private final List<String> identifierFormatTags;
    private final List<Integer> identifiers;
    private final List<String> urlPieces;

    private UrlTemplate(List<String> list, List<Integer> list2, List<String> list3) {
        this.urlPieces = list;
        this.identifiers = list2;
        this.identifierFormatTags = list3;
    }

    public static UrlTemplate compile(String str) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        parseTemplate(str, arrayList, arrayList2, arrayList3);
        return new UrlTemplate(arrayList, arrayList2, arrayList3);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void parseTemplate(java.lang.String r11, java.util.List<java.lang.String> r12, java.util.List<java.lang.Integer> r13, java.util.List<java.lang.String> r14) {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.dash.manifest.UrlTemplate.parseTemplate(java.lang.String, java.util.List, java.util.List, java.util.List):void");
    }

    public String buildUri(String str, long j2, int i2, long j3) {
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < this.identifiers.size(); i3++) {
            sb.append(this.urlPieces.get(i3));
            if (this.identifiers.get(i3).intValue() == 1) {
                sb.append(str);
            } else if (this.identifiers.get(i3).intValue() == 2) {
                sb.append(String.format(Locale.US, this.identifierFormatTags.get(i3), Long.valueOf(j2)));
            } else if (this.identifiers.get(i3).intValue() == 3) {
                sb.append(String.format(Locale.US, this.identifierFormatTags.get(i3), Integer.valueOf(i2)));
            } else if (this.identifiers.get(i3).intValue() == 4) {
                sb.append(String.format(Locale.US, this.identifierFormatTags.get(i3), Long.valueOf(j3)));
            }
        }
        sb.append(this.urlPieces.get(this.identifiers.size()));
        return sb.toString();
    }
}
