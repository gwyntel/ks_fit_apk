package androidx.media3.extractor.text.cea;

import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import androidx.annotation.Nullable;
import androidx.media3.common.text.Cue;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.CodecSpecificDataUtil;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.ParsableBitArray;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.extractor.text.Subtitle;
import androidx.media3.extractor.text.SubtitleDecoderException;
import androidx.media3.extractor.text.SubtitleInputBuffer;
import androidx.media3.extractor.text.SubtitleOutputBuffer;
import androidx.media3.extractor.text.cea.Cea708Decoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import kotlin.text.Typography;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

@UnstableApi
/* loaded from: classes2.dex */
public final class Cea708Decoder extends CeaDecoder {
    private static final int CC_VALID_FLAG = 4;
    private static final int CHARACTER_BIG_CARONS = 42;
    private static final int CHARACTER_BIG_OE = 44;
    private static final int CHARACTER_BOLD_BULLET = 53;
    private static final int CHARACTER_CLOSE_DOUBLE_QUOTE = 52;
    private static final int CHARACTER_CLOSE_SINGLE_QUOTE = 50;
    private static final int CHARACTER_DIAERESIS_Y = 63;
    private static final int CHARACTER_ELLIPSIS = 37;
    private static final int CHARACTER_FIVE_EIGHTHS = 120;
    private static final int CHARACTER_HORIZONTAL_BORDER = 125;
    private static final int CHARACTER_LOWER_LEFT_BORDER = 124;
    private static final int CHARACTER_LOWER_RIGHT_BORDER = 126;
    private static final int CHARACTER_MN = 127;
    private static final int CHARACTER_NBTSP = 33;
    private static final int CHARACTER_ONE_EIGHTH = 118;
    private static final int CHARACTER_OPEN_DOUBLE_QUOTE = 51;
    private static final int CHARACTER_OPEN_SINGLE_QUOTE = 49;
    private static final int CHARACTER_SEVEN_EIGHTHS = 121;
    private static final int CHARACTER_SM = 61;
    private static final int CHARACTER_SMALL_CARONS = 58;
    private static final int CHARACTER_SMALL_OE = 60;
    private static final int CHARACTER_SOLID_BLOCK = 48;
    private static final int CHARACTER_THREE_EIGHTHS = 119;
    private static final int CHARACTER_TM = 57;
    private static final int CHARACTER_TSP = 32;
    private static final int CHARACTER_UPPER_LEFT_BORDER = 127;
    private static final int CHARACTER_UPPER_RIGHT_BORDER = 123;
    private static final int CHARACTER_VERTICAL_BORDER = 122;
    private static final int COMMAND_BS = 8;
    private static final int COMMAND_CLW = 136;
    private static final int COMMAND_CR = 13;
    private static final int COMMAND_CW0 = 128;
    private static final int COMMAND_CW1 = 129;
    private static final int COMMAND_CW2 = 130;
    private static final int COMMAND_CW3 = 131;
    private static final int COMMAND_CW4 = 132;
    private static final int COMMAND_CW5 = 133;
    private static final int COMMAND_CW6 = 134;
    private static final int COMMAND_CW7 = 135;
    private static final int COMMAND_DF0 = 152;
    private static final int COMMAND_DF1 = 153;
    private static final int COMMAND_DF2 = 154;
    private static final int COMMAND_DF3 = 155;
    private static final int COMMAND_DF4 = 156;
    private static final int COMMAND_DF5 = 157;
    private static final int COMMAND_DF6 = 158;
    private static final int COMMAND_DF7 = 159;
    private static final int COMMAND_DLC = 142;
    private static final int COMMAND_DLW = 140;
    private static final int COMMAND_DLY = 141;
    private static final int COMMAND_DSW = 137;
    private static final int COMMAND_ETX = 3;
    private static final int COMMAND_EXT1 = 16;
    private static final int COMMAND_EXT1_END = 23;
    private static final int COMMAND_EXT1_START = 17;
    private static final int COMMAND_FF = 12;
    private static final int COMMAND_HCR = 14;
    private static final int COMMAND_HDW = 138;
    private static final int COMMAND_NUL = 0;
    private static final int COMMAND_P16_END = 31;
    private static final int COMMAND_P16_START = 24;
    private static final int COMMAND_RST = 143;
    private static final int COMMAND_SPA = 144;
    private static final int COMMAND_SPC = 145;
    private static final int COMMAND_SPL = 146;
    private static final int COMMAND_SWA = 151;
    private static final int COMMAND_TGW = 139;
    private static final int DTVCC_PACKET_DATA = 2;
    private static final int DTVCC_PACKET_START = 3;
    private static final int GROUP_C0_END = 31;
    private static final int GROUP_C1_END = 159;
    private static final int GROUP_C2_END = 31;
    private static final int GROUP_C3_END = 159;
    private static final int GROUP_G0_END = 127;
    private static final int GROUP_G1_END = 255;
    private static final int GROUP_G2_END = 127;
    private static final int GROUP_G3_END = 255;
    private static final int NUM_WINDOWS = 8;
    private static final String TAG = "Cea708Decoder";
    private final CueInfoBuilder[] cueInfoBuilders;

    @Nullable
    private List<Cue> cues;
    private CueInfoBuilder currentCueInfoBuilder;

    @Nullable
    private DtvCcPacket currentDtvCcPacket;
    private int currentWindow;
    private final boolean isWideAspectRatio;

    @Nullable
    private List<Cue> lastCues;
    private final int selectedServiceNumber;
    private final ParsableByteArray ccData = new ParsableByteArray();
    private final ParsableBitArray captionChannelPacketData = new ParsableBitArray();
    private int previousSequenceNumber = -1;

    /* JADX INFO: Access modifiers changed from: private */
    static final class Cea708CueInfo {
        private static final Comparator<Cea708CueInfo> LEAST_IMPORTANT_FIRST = new Comparator() { // from class: androidx.media3.extractor.text.cea.a
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Cea708Decoder.Cea708CueInfo.lambda$static$0((Cea708Decoder.Cea708CueInfo) obj, (Cea708Decoder.Cea708CueInfo) obj2);
            }
        };
        public final Cue cue;
        public final int priority;

        public Cea708CueInfo(CharSequence charSequence, Layout.Alignment alignment, float f2, int i2, int i3, float f3, int i4, float f4, boolean z2, int i5, int i6) {
            Cue.Builder size = new Cue.Builder().setText(charSequence).setTextAlignment(alignment).setLine(f2, i2).setLineAnchor(i3).setPosition(f3).setPositionAnchor(i4).setSize(f4);
            if (z2) {
                size.setWindowColor(i5);
            }
            this.cue = size.build();
            this.priority = i6;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$static$0(Cea708CueInfo cea708CueInfo, Cea708CueInfo cea708CueInfo2) {
            return Integer.compare(cea708CueInfo2.priority, cea708CueInfo.priority);
        }
    }

    private static final class CueInfoBuilder {
        private static final int BORDER_AND_EDGE_TYPE_NONE = 0;
        private static final int BORDER_AND_EDGE_TYPE_UNIFORM = 3;
        public static final int COLOR_SOLID_BLACK;
        public static final int COLOR_SOLID_WHITE = getArgbColorFromCeaColor(2, 2, 2, 0);
        public static final int COLOR_TRANSPARENT;
        private static final int DEFAULT_PRIORITY = 4;
        private static final int DIRECTION_BOTTOM_TO_TOP = 3;
        private static final int DIRECTION_LEFT_TO_RIGHT = 0;
        private static final int DIRECTION_RIGHT_TO_LEFT = 1;
        private static final int DIRECTION_TOP_TO_BOTTOM = 2;
        private static final int HORIZONTAL_SIZE = 209;
        private static final int JUSTIFICATION_CENTER = 2;
        private static final int JUSTIFICATION_FULL = 3;
        private static final int JUSTIFICATION_LEFT = 0;
        private static final int JUSTIFICATION_RIGHT = 1;
        private static final int MAXIMUM_ROW_COUNT = 15;
        private static final int PEN_FONT_STYLE_DEFAULT = 0;
        private static final int PEN_FONT_STYLE_MONOSPACED_WITHOUT_SERIFS = 3;
        private static final int PEN_FONT_STYLE_MONOSPACED_WITH_SERIFS = 1;
        private static final int PEN_FONT_STYLE_PROPORTIONALLY_SPACED_WITHOUT_SERIFS = 4;
        private static final int PEN_FONT_STYLE_PROPORTIONALLY_SPACED_WITH_SERIFS = 2;
        private static final int PEN_OFFSET_NORMAL = 1;
        private static final int PEN_SIZE_STANDARD = 1;
        private static final int[] PEN_STYLE_BACKGROUND;
        private static final int[] PEN_STYLE_EDGE_TYPE;
        private static final int[] PEN_STYLE_FONT_STYLE;
        private static final int RELATIVE_CUE_SIZE = 99;
        private static final int VERTICAL_SIZE = 74;
        private static final int[] WINDOW_STYLE_FILL;
        private static final int[] WINDOW_STYLE_JUSTIFICATION;
        private static final int[] WINDOW_STYLE_PRINT_DIRECTION;
        private static final int[] WINDOW_STYLE_SCROLL_DIRECTION;
        private static final boolean[] WINDOW_STYLE_WORD_WRAP;
        private int anchorId;
        private int backgroundColor;
        private int backgroundColorStartPosition;
        private boolean defined;
        private int foregroundColor;
        private int foregroundColorStartPosition;
        private int horizontalAnchor;
        private int italicsStartPosition;
        private int justification;
        private int penStyleId;
        private int priority;
        private boolean relativePositioning;
        private int row;
        private int rowCount;
        private int underlineStartPosition;
        private int verticalAnchor;
        private boolean visible;
        private int windowFillColor;
        private int windowStyleId;
        private final List<SpannableString> rolledUpCaptions = new ArrayList();
        private final SpannableStringBuilder captionStringBuilder = new SpannableStringBuilder();

        static {
            int argbColorFromCeaColor = getArgbColorFromCeaColor(0, 0, 0, 0);
            COLOR_SOLID_BLACK = argbColorFromCeaColor;
            int argbColorFromCeaColor2 = getArgbColorFromCeaColor(0, 0, 0, 3);
            COLOR_TRANSPARENT = argbColorFromCeaColor2;
            WINDOW_STYLE_JUSTIFICATION = new int[]{0, 0, 0, 0, 0, 2, 0};
            WINDOW_STYLE_PRINT_DIRECTION = new int[]{0, 0, 0, 0, 0, 0, 2};
            WINDOW_STYLE_SCROLL_DIRECTION = new int[]{3, 3, 3, 3, 3, 3, 1};
            WINDOW_STYLE_WORD_WRAP = new boolean[]{false, false, false, true, true, true, false};
            WINDOW_STYLE_FILL = new int[]{argbColorFromCeaColor, argbColorFromCeaColor2, argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor2, argbColorFromCeaColor, argbColorFromCeaColor};
            PEN_STYLE_FONT_STYLE = new int[]{0, 1, 2, 3, 4, 3, 4};
            PEN_STYLE_EDGE_TYPE = new int[]{0, 0, 0, 0, 0, 3, 3};
            PEN_STYLE_BACKGROUND = new int[]{argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor2, argbColorFromCeaColor2};
        }

        public CueInfoBuilder() {
            reset();
        }

        public static int getArgbColorFromCeaColor(int i2, int i3, int i4) {
            return getArgbColorFromCeaColor(i2, i3, i4, 0);
        }

        public void append(char c2) {
            if (c2 != '\n') {
                this.captionStringBuilder.append(c2);
                return;
            }
            this.rolledUpCaptions.add(buildSpannableString());
            this.captionStringBuilder.clear();
            if (this.italicsStartPosition != -1) {
                this.italicsStartPosition = 0;
            }
            if (this.underlineStartPosition != -1) {
                this.underlineStartPosition = 0;
            }
            if (this.foregroundColorStartPosition != -1) {
                this.foregroundColorStartPosition = 0;
            }
            if (this.backgroundColorStartPosition != -1) {
                this.backgroundColorStartPosition = 0;
            }
            while (true) {
                if (this.rolledUpCaptions.size() < this.rowCount && this.rolledUpCaptions.size() < 15) {
                    this.row = this.rolledUpCaptions.size();
                    return;
                }
                this.rolledUpCaptions.remove(0);
            }
        }

        public void backspace() {
            int length = this.captionStringBuilder.length();
            if (length > 0) {
                this.captionStringBuilder.delete(length - 1, length);
            }
        }

        @Nullable
        public Cea708CueInfo build() {
            Layout.Alignment alignment;
            float f2;
            float f3;
            if (isEmpty()) {
                return null;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            for (int i2 = 0; i2 < this.rolledUpCaptions.size(); i2++) {
                spannableStringBuilder.append((CharSequence) this.rolledUpCaptions.get(i2));
                spannableStringBuilder.append('\n');
            }
            spannableStringBuilder.append((CharSequence) buildSpannableString());
            int i3 = this.justification;
            if (i3 == 0) {
                alignment = Layout.Alignment.ALIGN_NORMAL;
            } else if (i3 == 1) {
                alignment = Layout.Alignment.ALIGN_OPPOSITE;
            } else if (i3 != 2) {
                if (i3 != 3) {
                    throw new IllegalArgumentException("Unexpected justification value: " + this.justification);
                }
                alignment = Layout.Alignment.ALIGN_NORMAL;
            } else {
                alignment = Layout.Alignment.ALIGN_CENTER;
            }
            Layout.Alignment alignment2 = alignment;
            if (this.relativePositioning) {
                f2 = this.horizontalAnchor / 99.0f;
                f3 = this.verticalAnchor / 99.0f;
            } else {
                f2 = this.horizontalAnchor / 209.0f;
                f3 = this.verticalAnchor / 74.0f;
            }
            float f4 = (f2 * 0.9f) + 0.05f;
            float f5 = (f3 * 0.9f) + 0.05f;
            int i4 = this.anchorId;
            return new Cea708CueInfo(spannableStringBuilder, alignment2, f5, 0, i4 / 3 == 0 ? 0 : i4 / 3 == 1 ? 1 : 2, f4, i4 % 3 == 0 ? 0 : i4 % 3 == 1 ? 1 : 2, -3.4028235E38f, this.windowFillColor != COLOR_SOLID_BLACK, this.windowFillColor, this.priority);
        }

        public SpannableString buildSpannableString() {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.captionStringBuilder);
            int length = spannableStringBuilder.length();
            if (length > 0) {
                if (this.italicsStartPosition != -1) {
                    spannableStringBuilder.setSpan(new StyleSpan(2), this.italicsStartPosition, length, 33);
                }
                if (this.underlineStartPosition != -1) {
                    spannableStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, length, 33);
                }
                if (this.foregroundColorStartPosition != -1) {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(this.foregroundColor), this.foregroundColorStartPosition, length, 33);
                }
                if (this.backgroundColorStartPosition != -1) {
                    spannableStringBuilder.setSpan(new BackgroundColorSpan(this.backgroundColor), this.backgroundColorStartPosition, length, 33);
                }
            }
            return new SpannableString(spannableStringBuilder);
        }

        public void clear() {
            this.rolledUpCaptions.clear();
            this.captionStringBuilder.clear();
            this.italicsStartPosition = -1;
            this.underlineStartPosition = -1;
            this.foregroundColorStartPosition = -1;
            this.backgroundColorStartPosition = -1;
            this.row = 0;
        }

        public void defineWindow(boolean z2, int i2, boolean z3, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.defined = true;
            this.visible = z2;
            this.priority = i2;
            this.relativePositioning = z3;
            this.verticalAnchor = i3;
            this.horizontalAnchor = i4;
            this.anchorId = i6;
            int i9 = i5 + 1;
            if (this.rowCount != i9) {
                this.rowCount = i9;
                while (true) {
                    if (this.rolledUpCaptions.size() < this.rowCount && this.rolledUpCaptions.size() < 15) {
                        break;
                    } else {
                        this.rolledUpCaptions.remove(0);
                    }
                }
            }
            if (i7 != 0 && this.windowStyleId != i7) {
                this.windowStyleId = i7;
                int i10 = i7 - 1;
                setWindowAttributes(WINDOW_STYLE_FILL[i10], COLOR_TRANSPARENT, WINDOW_STYLE_WORD_WRAP[i10], 0, WINDOW_STYLE_PRINT_DIRECTION[i10], WINDOW_STYLE_SCROLL_DIRECTION[i10], WINDOW_STYLE_JUSTIFICATION[i10]);
            }
            if (i8 == 0 || this.penStyleId == i8) {
                return;
            }
            this.penStyleId = i8;
            int i11 = i8 - 1;
            setPenAttributes(0, 1, 1, false, false, PEN_STYLE_EDGE_TYPE[i11], PEN_STYLE_FONT_STYLE[i11]);
            setPenColor(COLOR_SOLID_WHITE, PEN_STYLE_BACKGROUND[i11], COLOR_SOLID_BLACK);
        }

        public boolean isDefined() {
            return this.defined;
        }

        public boolean isEmpty() {
            return !isDefined() || (this.rolledUpCaptions.isEmpty() && this.captionStringBuilder.length() == 0);
        }

        public boolean isVisible() {
            return this.visible;
        }

        public void reset() {
            clear();
            this.defined = false;
            this.visible = false;
            this.priority = 4;
            this.relativePositioning = false;
            this.verticalAnchor = 0;
            this.horizontalAnchor = 0;
            this.anchorId = 0;
            this.rowCount = 15;
            this.justification = 0;
            this.windowStyleId = 0;
            this.penStyleId = 0;
            int i2 = COLOR_SOLID_BLACK;
            this.windowFillColor = i2;
            this.foregroundColor = COLOR_SOLID_WHITE;
            this.backgroundColor = i2;
        }

        public void setPenAttributes(int i2, int i3, int i4, boolean z2, boolean z3, int i5, int i6) {
            if (this.italicsStartPosition != -1) {
                if (!z2) {
                    this.captionStringBuilder.setSpan(new StyleSpan(2), this.italicsStartPosition, this.captionStringBuilder.length(), 33);
                    this.italicsStartPosition = -1;
                }
            } else if (z2) {
                this.italicsStartPosition = this.captionStringBuilder.length();
            }
            if (this.underlineStartPosition == -1) {
                if (z3) {
                    this.underlineStartPosition = this.captionStringBuilder.length();
                }
            } else {
                if (z3) {
                    return;
                }
                this.captionStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, this.captionStringBuilder.length(), 33);
                this.underlineStartPosition = -1;
            }
        }

        public void setPenColor(int i2, int i3, int i4) {
            if (this.foregroundColorStartPosition != -1 && this.foregroundColor != i2) {
                this.captionStringBuilder.setSpan(new ForegroundColorSpan(this.foregroundColor), this.foregroundColorStartPosition, this.captionStringBuilder.length(), 33);
            }
            if (i2 != COLOR_SOLID_WHITE) {
                this.foregroundColorStartPosition = this.captionStringBuilder.length();
                this.foregroundColor = i2;
            }
            if (this.backgroundColorStartPosition != -1 && this.backgroundColor != i3) {
                this.captionStringBuilder.setSpan(new BackgroundColorSpan(this.backgroundColor), this.backgroundColorStartPosition, this.captionStringBuilder.length(), 33);
            }
            if (i3 != COLOR_SOLID_BLACK) {
                this.backgroundColorStartPosition = this.captionStringBuilder.length();
                this.backgroundColor = i3;
            }
        }

        public void setPenLocation(int i2, int i3) {
            if (this.row != i2) {
                append('\n');
            }
            this.row = i2;
        }

        public void setVisibility(boolean z2) {
            this.visible = z2;
        }

        public void setWindowAttributes(int i2, int i3, boolean z2, int i4, int i5, int i6, int i7) {
            this.windowFillColor = i2;
            this.justification = i7;
        }

        /* JADX WARN: Removed duplicated region for block: B:9:0x001b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static int getArgbColorFromCeaColor(int r4, int r5, int r6, int r7) {
            /*
                r0 = 0
                r1 = 4
                androidx.media3.common.util.Assertions.checkIndex(r4, r0, r1)
                androidx.media3.common.util.Assertions.checkIndex(r5, r0, r1)
                androidx.media3.common.util.Assertions.checkIndex(r6, r0, r1)
                androidx.media3.common.util.Assertions.checkIndex(r7, r0, r1)
                r1 = 1
                r2 = 255(0xff, float:3.57E-43)
                if (r7 == 0) goto L1b
                if (r7 == r1) goto L1b
                r3 = 2
                if (r7 == r3) goto L1f
                r3 = 3
                if (r7 == r3) goto L1d
            L1b:
                r7 = r2
                goto L21
            L1d:
                r7 = r0
                goto L21
            L1f:
                r7 = 127(0x7f, float:1.78E-43)
            L21:
                if (r4 <= r1) goto L25
                r4 = r2
                goto L26
            L25:
                r4 = r0
            L26:
                if (r5 <= r1) goto L2a
                r5 = r2
                goto L2b
            L2a:
                r5 = r0
            L2b:
                if (r6 <= r1) goto L2e
                r0 = r2
            L2e:
                int r4 = android.graphics.Color.argb(r7, r4, r5, r0)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.media3.extractor.text.cea.Cea708Decoder.CueInfoBuilder.getArgbColorFromCeaColor(int, int, int, int):int");
        }
    }

    private static final class DtvCcPacket {

        /* renamed from: a, reason: collision with root package name */
        int f5622a = 0;
        public final byte[] packetData;
        public final int packetSize;
        public final int sequenceNumber;

        public DtvCcPacket(int i2, int i3) {
            this.sequenceNumber = i2;
            this.packetSize = i3;
            this.packetData = new byte[(i3 * 2) - 1];
        }
    }

    public Cea708Decoder(int i2, @Nullable List<byte[]> list) {
        this.selectedServiceNumber = i2 == -1 ? 1 : i2;
        this.isWideAspectRatio = list != null && CodecSpecificDataUtil.parseCea708InitializationData(list);
        this.cueInfoBuilders = new CueInfoBuilder[8];
        for (int i3 = 0; i3 < 8; i3++) {
            this.cueInfoBuilders[i3] = new CueInfoBuilder();
        }
        this.currentCueInfoBuilder = this.cueInfoBuilders[0];
    }

    private void finalizeCurrentPacket() {
        if (this.currentDtvCcPacket == null) {
            return;
        }
        processCurrentPacket();
        this.currentDtvCcPacket = null;
    }

    private List<Cue> getDisplayCues() {
        Cea708CueInfo cea708CueInfoBuild;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 8; i2++) {
            if (!this.cueInfoBuilders[i2].isEmpty() && this.cueInfoBuilders[i2].isVisible() && (cea708CueInfoBuild = this.cueInfoBuilders[i2].build()) != null) {
                arrayList.add(cea708CueInfoBuild);
            }
        }
        Collections.sort(arrayList, Cea708CueInfo.LEAST_IMPORTANT_FIRST);
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            arrayList2.add(((Cea708CueInfo) arrayList.get(i3)).cue);
        }
        return Collections.unmodifiableList(arrayList2);
    }

    private void handleC0Command(int i2) {
        if (i2 != 0) {
            if (i2 == 3) {
                this.cues = getDisplayCues();
            }
            if (i2 == 8) {
                this.currentCueInfoBuilder.backspace();
                return;
            }
            switch (i2) {
                case 12:
                    resetCueBuilders();
                    break;
                case 13:
                    this.currentCueInfoBuilder.append('\n');
                    break;
                case 14:
                    break;
                default:
                    if (i2 >= 17 && i2 <= 23) {
                        Log.w(TAG, "Currently unsupported COMMAND_EXT1 Command: " + i2);
                        this.captionChannelPacketData.skipBits(8);
                        break;
                    } else if (i2 >= 24 && i2 <= 31) {
                        Log.w(TAG, "Currently unsupported COMMAND_P16 Command: " + i2);
                        this.captionChannelPacketData.skipBits(16);
                        break;
                    } else {
                        Log.w(TAG, "Invalid C0 command: " + i2);
                        break;
                    }
                    break;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void handleC1Command(int i2) {
        int i3 = 1;
        switch (i2) {
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
                int i4 = i2 - 128;
                if (this.currentWindow != i4) {
                    this.currentWindow = i4;
                    this.currentCueInfoBuilder = this.cueInfoBuilders[i4];
                    break;
                }
                break;
            case 136:
                while (i3 <= 8) {
                    if (this.captionChannelPacketData.readBit()) {
                        this.cueInfoBuilders[8 - i3].clear();
                    }
                    i3++;
                }
                break;
            case 137:
                for (int i5 = 1; i5 <= 8; i5++) {
                    if (this.captionChannelPacketData.readBit()) {
                        this.cueInfoBuilders[8 - i5].setVisibility(true);
                    }
                }
                break;
            case 138:
                while (i3 <= 8) {
                    if (this.captionChannelPacketData.readBit()) {
                        this.cueInfoBuilders[8 - i3].setVisibility(false);
                    }
                    i3++;
                }
                break;
            case 139:
                for (int i6 = 1; i6 <= 8; i6++) {
                    if (this.captionChannelPacketData.readBit()) {
                        this.cueInfoBuilders[8 - i6].setVisibility(!r0.isVisible());
                    }
                }
                break;
            case 140:
                while (i3 <= 8) {
                    if (this.captionChannelPacketData.readBit()) {
                        this.cueInfoBuilders[8 - i3].reset();
                    }
                    i3++;
                }
                break;
            case 141:
                this.captionChannelPacketData.skipBits(8);
                break;
            case 142:
                break;
            case 143:
                resetCueBuilders();
                break;
            case 144:
                if (this.currentCueInfoBuilder.isDefined()) {
                    handleSetPenAttributes();
                    break;
                } else {
                    this.captionChannelPacketData.skipBits(16);
                    break;
                }
            case 145:
                if (this.currentCueInfoBuilder.isDefined()) {
                    handleSetPenColor();
                    break;
                } else {
                    this.captionChannelPacketData.skipBits(24);
                    break;
                }
            case 146:
                if (this.currentCueInfoBuilder.isDefined()) {
                    handleSetPenLocation();
                    break;
                } else {
                    this.captionChannelPacketData.skipBits(16);
                    break;
                }
            case 147:
            case 148:
            case 149:
            case 150:
            default:
                Log.w(TAG, "Invalid C1 command: " + i2);
                break;
            case 151:
                if (this.currentCueInfoBuilder.isDefined()) {
                    handleSetWindowAttributes();
                    break;
                } else {
                    this.captionChannelPacketData.skipBits(32);
                    break;
                }
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
                int i7 = i2 - 152;
                handleDefineWindow(i7);
                if (this.currentWindow != i7) {
                    this.currentWindow = i7;
                    this.currentCueInfoBuilder = this.cueInfoBuilders[i7];
                    break;
                }
                break;
        }
    }

    private void handleC2Command(int i2) {
        if (i2 <= 7) {
            return;
        }
        if (i2 <= 15) {
            this.captionChannelPacketData.skipBits(8);
        } else if (i2 <= 23) {
            this.captionChannelPacketData.skipBits(16);
        } else if (i2 <= 31) {
            this.captionChannelPacketData.skipBits(24);
        }
    }

    private void handleC3Command(int i2) {
        if (i2 <= 135) {
            this.captionChannelPacketData.skipBits(32);
            return;
        }
        if (i2 <= 143) {
            this.captionChannelPacketData.skipBits(40);
        } else if (i2 <= 159) {
            this.captionChannelPacketData.skipBits(2);
            this.captionChannelPacketData.skipBits(this.captionChannelPacketData.readBits(6) * 8);
        }
    }

    private void handleDefineWindow(int i2) {
        CueInfoBuilder cueInfoBuilder = this.cueInfoBuilders[i2];
        this.captionChannelPacketData.skipBits(2);
        boolean bit = this.captionChannelPacketData.readBit();
        this.captionChannelPacketData.skipBits(2);
        int bits = this.captionChannelPacketData.readBits(3);
        boolean bit2 = this.captionChannelPacketData.readBit();
        int bits2 = this.captionChannelPacketData.readBits(7);
        int bits3 = this.captionChannelPacketData.readBits(8);
        int bits4 = this.captionChannelPacketData.readBits(4);
        int bits5 = this.captionChannelPacketData.readBits(4);
        this.captionChannelPacketData.skipBits(2);
        this.captionChannelPacketData.skipBits(6);
        this.captionChannelPacketData.skipBits(2);
        cueInfoBuilder.defineWindow(bit, bits, bit2, bits2, bits3, bits5, bits4, this.captionChannelPacketData.readBits(3), this.captionChannelPacketData.readBits(3));
    }

    private void handleG0Character(int i2) {
        if (i2 == 127) {
            this.currentCueInfoBuilder.append((char) 9835);
        } else {
            this.currentCueInfoBuilder.append((char) (i2 & 255));
        }
    }

    private void handleG1Character(int i2) {
        this.currentCueInfoBuilder.append((char) (i2 & 255));
    }

    private void handleG2Character(int i2) {
        if (i2 == 32) {
            this.currentCueInfoBuilder.append(' ');
        }
        if (i2 == 33) {
            this.currentCueInfoBuilder.append(Typography.nbsp);
            return;
        }
        if (i2 == 37) {
            this.currentCueInfoBuilder.append(Typography.ellipsis);
            return;
        }
        if (i2 == 42) {
            this.currentCueInfoBuilder.append((char) 352);
            return;
        }
        if (i2 == 44) {
            this.currentCueInfoBuilder.append((char) 338);
            return;
        }
        if (i2 == 63) {
            this.currentCueInfoBuilder.append((char) 376);
            return;
        }
        if (i2 == 57) {
            this.currentCueInfoBuilder.append(Typography.tm);
            return;
        }
        if (i2 == 58) {
            this.currentCueInfoBuilder.append((char) 353);
            return;
        }
        if (i2 == 60) {
            this.currentCueInfoBuilder.append((char) 339);
            return;
        }
        if (i2 == 61) {
            this.currentCueInfoBuilder.append((char) 8480);
            return;
        }
        switch (i2) {
            case 48:
                this.currentCueInfoBuilder.append((char) 9608);
                break;
            case 49:
                this.currentCueInfoBuilder.append(Typography.leftSingleQuote);
                break;
            case 50:
                this.currentCueInfoBuilder.append(Typography.rightSingleQuote);
                break;
            case 51:
                this.currentCueInfoBuilder.append(Typography.leftDoubleQuote);
                break;
            case 52:
                this.currentCueInfoBuilder.append(Typography.rightDoubleQuote);
                break;
            case 53:
                this.currentCueInfoBuilder.append(Typography.bullet);
                break;
            default:
                switch (i2) {
                    case 118:
                        this.currentCueInfoBuilder.append((char) 8539);
                        break;
                    case 119:
                        this.currentCueInfoBuilder.append((char) 8540);
                        break;
                    case 120:
                        this.currentCueInfoBuilder.append((char) 8541);
                        break;
                    case 121:
                        this.currentCueInfoBuilder.append((char) 8542);
                        break;
                    case 122:
                        this.currentCueInfoBuilder.append((char) 9474);
                        break;
                    case 123:
                        this.currentCueInfoBuilder.append((char) 9488);
                        break;
                    case 124:
                        this.currentCueInfoBuilder.append((char) 9492);
                        break;
                    case 125:
                        this.currentCueInfoBuilder.append((char) 9472);
                        break;
                    case 126:
                        this.currentCueInfoBuilder.append((char) 9496);
                        break;
                    case 127:
                        this.currentCueInfoBuilder.append((char) 9484);
                        break;
                    default:
                        Log.w(TAG, "Invalid G2 character: " + i2);
                        break;
                }
        }
    }

    private void handleG3Character(int i2) {
        if (i2 == 160) {
            this.currentCueInfoBuilder.append((char) 13252);
            return;
        }
        Log.w(TAG, "Invalid G3 character: " + i2);
        this.currentCueInfoBuilder.append('_');
    }

    private void handleSetPenAttributes() {
        this.currentCueInfoBuilder.setPenAttributes(this.captionChannelPacketData.readBits(4), this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBit(), this.captionChannelPacketData.readBit(), this.captionChannelPacketData.readBits(3), this.captionChannelPacketData.readBits(3));
    }

    private void handleSetPenColor() {
        int argbColorFromCeaColor = CueInfoBuilder.getArgbColorFromCeaColor(this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2));
        int argbColorFromCeaColor2 = CueInfoBuilder.getArgbColorFromCeaColor(this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2));
        this.captionChannelPacketData.skipBits(2);
        this.currentCueInfoBuilder.setPenColor(argbColorFromCeaColor, argbColorFromCeaColor2, CueInfoBuilder.getArgbColorFromCeaColor(this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2)));
    }

    private void handleSetPenLocation() {
        this.captionChannelPacketData.skipBits(4);
        int bits = this.captionChannelPacketData.readBits(4);
        this.captionChannelPacketData.skipBits(2);
        this.currentCueInfoBuilder.setPenLocation(bits, this.captionChannelPacketData.readBits(6));
    }

    private void handleSetWindowAttributes() {
        int argbColorFromCeaColor = CueInfoBuilder.getArgbColorFromCeaColor(this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2));
        int bits = this.captionChannelPacketData.readBits(2);
        int argbColorFromCeaColor2 = CueInfoBuilder.getArgbColorFromCeaColor(this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2), this.captionChannelPacketData.readBits(2));
        if (this.captionChannelPacketData.readBit()) {
            bits |= 4;
        }
        boolean bit = this.captionChannelPacketData.readBit();
        int bits2 = this.captionChannelPacketData.readBits(2);
        int bits3 = this.captionChannelPacketData.readBits(2);
        int bits4 = this.captionChannelPacketData.readBits(2);
        this.captionChannelPacketData.skipBits(8);
        this.currentCueInfoBuilder.setWindowAttributes(argbColorFromCeaColor, argbColorFromCeaColor2, bit, bits, bits2, bits3, bits4);
    }

    @RequiresNonNull({"currentDtvCcPacket"})
    private void processCurrentPacket() {
        DtvCcPacket dtvCcPacket = this.currentDtvCcPacket;
        if (dtvCcPacket.f5622a != (dtvCcPacket.packetSize * 2) - 1) {
            Log.d(TAG, "DtvCcPacket ended prematurely; size is " + ((this.currentDtvCcPacket.packetSize * 2) - 1) + ", but current index is " + this.currentDtvCcPacket.f5622a + " (sequence number " + this.currentDtvCcPacket.sequenceNumber + ");");
        }
        ParsableBitArray parsableBitArray = this.captionChannelPacketData;
        DtvCcPacket dtvCcPacket2 = this.currentDtvCcPacket;
        parsableBitArray.reset(dtvCcPacket2.packetData, dtvCcPacket2.f5622a);
        boolean z2 = false;
        while (true) {
            if (this.captionChannelPacketData.bitsLeft() <= 0) {
                break;
            }
            int bits = this.captionChannelPacketData.readBits(3);
            int bits2 = this.captionChannelPacketData.readBits(5);
            if (bits == 7) {
                this.captionChannelPacketData.skipBits(2);
                bits = this.captionChannelPacketData.readBits(6);
                if (bits < 7) {
                    Log.w(TAG, "Invalid extended service number: " + bits);
                }
            }
            if (bits2 == 0) {
                if (bits != 0) {
                    Log.w(TAG, "serviceNumber is non-zero (" + bits + ") when blockSize is 0");
                }
            } else if (bits != this.selectedServiceNumber) {
                this.captionChannelPacketData.skipBytes(bits2);
            } else {
                int position = this.captionChannelPacketData.getPosition() + (bits2 * 8);
                while (this.captionChannelPacketData.getPosition() < position) {
                    int bits3 = this.captionChannelPacketData.readBits(8);
                    if (bits3 == 16) {
                        int bits4 = this.captionChannelPacketData.readBits(8);
                        if (bits4 <= 31) {
                            handleC2Command(bits4);
                        } else {
                            if (bits4 <= 127) {
                                handleG2Character(bits4);
                            } else if (bits4 <= 159) {
                                handleC3Command(bits4);
                            } else if (bits4 <= 255) {
                                handleG3Character(bits4);
                            } else {
                                Log.w(TAG, "Invalid extended command: " + bits4);
                            }
                            z2 = true;
                        }
                    } else if (bits3 <= 31) {
                        handleC0Command(bits3);
                    } else {
                        if (bits3 <= 127) {
                            handleG0Character(bits3);
                        } else if (bits3 <= 159) {
                            handleC1Command(bits3);
                        } else if (bits3 <= 255) {
                            handleG1Character(bits3);
                        } else {
                            Log.w(TAG, "Invalid base command: " + bits3);
                        }
                        z2 = true;
                    }
                }
            }
        }
        if (z2) {
            this.cues = getDisplayCues();
        }
    }

    private void resetCueBuilders() {
        for (int i2 = 0; i2 < 8; i2++) {
            this.cueInfoBuilders[i2].reset();
        }
    }

    @Override // androidx.media3.extractor.text.cea.CeaDecoder
    protected Subtitle a() {
        List<Cue> list = this.cues;
        this.lastCues = list;
        return new CeaSubtitle((List) Assertions.checkNotNull(list));
    }

    @Override // androidx.media3.extractor.text.cea.CeaDecoder
    protected void b(SubtitleInputBuffer subtitleInputBuffer) {
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(subtitleInputBuffer.data);
        this.ccData.reset(byteBuffer.array(), byteBuffer.limit());
        while (this.ccData.bytesLeft() >= 3) {
            int unsignedByte = this.ccData.readUnsignedByte();
            int i2 = unsignedByte & 3;
            boolean z2 = (unsignedByte & 4) == 4;
            byte unsignedByte2 = (byte) this.ccData.readUnsignedByte();
            byte unsignedByte3 = (byte) this.ccData.readUnsignedByte();
            if (i2 == 2 || i2 == 3) {
                if (z2) {
                    if (i2 == 3) {
                        finalizeCurrentPacket();
                        int i3 = (unsignedByte2 & 192) >> 6;
                        int i4 = this.previousSequenceNumber;
                        if (i4 != -1 && i3 != (i4 + 1) % 4) {
                            resetCueBuilders();
                            Log.w(TAG, "Sequence number discontinuity. previous=" + this.previousSequenceNumber + " current=" + i3);
                        }
                        this.previousSequenceNumber = i3;
                        int i5 = unsignedByte2 & 63;
                        if (i5 == 0) {
                            i5 = 64;
                        }
                        DtvCcPacket dtvCcPacket = new DtvCcPacket(i3, i5);
                        this.currentDtvCcPacket = dtvCcPacket;
                        byte[] bArr = dtvCcPacket.packetData;
                        int i6 = dtvCcPacket.f5622a;
                        dtvCcPacket.f5622a = i6 + 1;
                        bArr[i6] = unsignedByte3;
                    } else {
                        Assertions.checkArgument(i2 == 2);
                        DtvCcPacket dtvCcPacket2 = this.currentDtvCcPacket;
                        if (dtvCcPacket2 == null) {
                            Log.e(TAG, "Encountered DTVCC_PACKET_DATA before DTVCC_PACKET_START");
                        } else {
                            byte[] bArr2 = dtvCcPacket2.packetData;
                            int i7 = dtvCcPacket2.f5622a;
                            int i8 = i7 + 1;
                            dtvCcPacket2.f5622a = i8;
                            bArr2[i7] = unsignedByte2;
                            dtvCcPacket2.f5622a = i7 + 2;
                            bArr2[i8] = unsignedByte3;
                        }
                    }
                    DtvCcPacket dtvCcPacket3 = this.currentDtvCcPacket;
                    if (dtvCcPacket3.f5622a == (dtvCcPacket3.packetSize * 2) - 1) {
                        finalizeCurrentPacket();
                    }
                }
            }
        }
    }

    @Override // androidx.media3.extractor.text.cea.CeaDecoder, androidx.media3.decoder.Decoder
    @Nullable
    public /* bridge */ /* synthetic */ SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        return super.dequeueInputBuffer();
    }

    @Override // androidx.media3.extractor.text.cea.CeaDecoder, androidx.media3.decoder.Decoder
    @Nullable
    public /* bridge */ /* synthetic */ SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        return super.dequeueOutputBuffer();
    }

    @Override // androidx.media3.extractor.text.cea.CeaDecoder
    protected boolean e() {
        return this.cues != this.lastCues;
    }

    @Override // androidx.media3.extractor.text.cea.CeaDecoder, androidx.media3.decoder.Decoder
    public void flush() {
        super.flush();
        this.cues = null;
        this.lastCues = null;
        this.currentWindow = 0;
        this.currentCueInfoBuilder = this.cueInfoBuilders[0];
        resetCueBuilders();
        this.currentDtvCcPacket = null;
    }

    @Override // androidx.media3.extractor.text.cea.CeaDecoder, androidx.media3.decoder.Decoder
    public String getName() {
        return TAG;
    }

    @Override // androidx.media3.extractor.text.cea.CeaDecoder
    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) throws SubtitleDecoderException {
        super.queueInputBuffer(subtitleInputBuffer);
    }

    @Override // androidx.media3.extractor.text.cea.CeaDecoder, androidx.media3.decoder.Decoder
    public /* bridge */ /* synthetic */ void release() {
        super.release();
    }

    @Override // androidx.media3.extractor.text.cea.CeaDecoder, androidx.media3.extractor.text.SubtitleDecoder
    public /* bridge */ /* synthetic */ void setPositionUs(long j2) {
        super.setPositionUs(j2);
    }
}
