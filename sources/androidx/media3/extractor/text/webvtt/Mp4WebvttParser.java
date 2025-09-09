package androidx.media3.extractor.text.webvtt;

import androidx.media3.common.C;
import androidx.media3.common.text.Cue;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Consumer;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.extractor.text.CuesWithTiming;
import androidx.media3.extractor.text.Subtitle;
import androidx.media3.extractor.text.SubtitleParser;
import androidx.media3.extractor.text.e;
import java.util.ArrayList;
import java.util.Collections;

@UnstableApi
/* loaded from: classes2.dex */
public final class Mp4WebvttParser implements SubtitleParser {
    private static final int BOX_HEADER_SIZE = 8;
    public static final int CUE_REPLACEMENT_BEHAVIOR = 2;
    private static final int TYPE_payl = 1885436268;
    private static final int TYPE_sttg = 1937011815;
    private static final int TYPE_vttc = 1987343459;
    private final ParsableByteArray parsableByteArray = new ParsableByteArray();

    private static Cue parseVttCueBox(ParsableByteArray parsableByteArray, int i2) {
        CharSequence charSequenceB = null;
        Cue.Builder builderA = null;
        while (i2 > 0) {
            Assertions.checkArgument(i2 >= 8, "Incomplete vtt cue box header found.");
            int i3 = parsableByteArray.readInt();
            int i4 = parsableByteArray.readInt();
            int i5 = i3 - 8;
            String strFromUtf8Bytes = Util.fromUtf8Bytes(parsableByteArray.getData(), parsableByteArray.getPosition(), i5);
            parsableByteArray.skipBytes(i5);
            i2 = (i2 - 8) - i5;
            if (i4 == TYPE_sttg) {
                builderA = WebvttCueParser.a(strFromUtf8Bytes);
            } else if (i4 == TYPE_payl) {
                charSequenceB = WebvttCueParser.b(null, strFromUtf8Bytes.trim(), Collections.emptyList());
            }
        }
        if (charSequenceB == null) {
            charSequenceB = "";
        }
        return builderA != null ? builderA.setText(charSequenceB).build() : WebvttCueParser.newCueForText(charSequenceB);
    }

    @Override // androidx.media3.extractor.text.SubtitleParser
    public int getCueReplacementBehavior() {
        return 2;
    }

    @Override // androidx.media3.extractor.text.SubtitleParser
    public /* synthetic */ void parse(byte[] bArr, SubtitleParser.OutputOptions outputOptions, Consumer consumer) {
        e.a(this, bArr, outputOptions, consumer);
    }

    @Override // androidx.media3.extractor.text.SubtitleParser
    public /* synthetic */ Subtitle parseToLegacySubtitle(byte[] bArr, int i2, int i3) {
        return e.b(this, bArr, i2, i3);
    }

    @Override // androidx.media3.extractor.text.SubtitleParser
    public /* synthetic */ void reset() {
        e.c(this);
    }

    @Override // androidx.media3.extractor.text.SubtitleParser
    public void parse(byte[] bArr, int i2, int i3, SubtitleParser.OutputOptions outputOptions, Consumer<CuesWithTiming> consumer) {
        this.parsableByteArray.reset(bArr, i3 + i2);
        this.parsableByteArray.setPosition(i2);
        ArrayList arrayList = new ArrayList();
        while (this.parsableByteArray.bytesLeft() > 0) {
            Assertions.checkArgument(this.parsableByteArray.bytesLeft() >= 8, "Incomplete Mp4Webvtt Top Level box header found.");
            int i4 = this.parsableByteArray.readInt();
            if (this.parsableByteArray.readInt() == TYPE_vttc) {
                arrayList.add(parseVttCueBox(this.parsableByteArray, i4 - 8));
            } else {
                this.parsableByteArray.skipBytes(i4 - 8);
            }
        }
        consumer.accept(new CuesWithTiming(arrayList, C.TIME_UNSET, C.TIME_UNSET));
    }
}
