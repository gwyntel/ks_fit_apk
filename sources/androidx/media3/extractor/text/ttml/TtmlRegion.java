package androidx.media3.extractor.text.ttml;

/* loaded from: classes2.dex */
final class TtmlRegion {
    public final float height;
    public final String id;
    public final float line;
    public final int lineAnchor;
    public final int lineType;
    public final float position;
    public final float textSize;
    public final int textSizeType;
    public final int verticalType;
    public final float width;

    public TtmlRegion(String str) {
        this(str, -3.4028235E38f, -3.4028235E38f, Integer.MIN_VALUE, Integer.MIN_VALUE, -3.4028235E38f, -3.4028235E38f, Integer.MIN_VALUE, -3.4028235E38f, Integer.MIN_VALUE);
    }

    public TtmlRegion(String str, float f2, float f3, int i2, int i3, float f4, float f5, int i4, float f6, int i5) {
        this.id = str;
        this.position = f2;
        this.line = f3;
        this.lineType = i2;
        this.lineAnchor = i3;
        this.width = f4;
        this.height = f5;
        this.textSizeType = i4;
        this.textSize = f6;
        this.verticalType = i5;
    }
}
