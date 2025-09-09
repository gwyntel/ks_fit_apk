package com.fluttercandies.image_editor.option;

import android.graphics.Paint;
import androidx.media3.extractor.text.ttml.TtmlNode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u000eHÆ\u0003J\t\u0010\u001f\u001a\u00020\u0005HÆ\u0003J\t\u0010 \u001a\u00020\u0005HÆ\u0003J\t\u0010!\u001a\u00020\u0005HÆ\u0003J\t\u0010\"\u001a\u00020\u0005HÆ\u0003J\t\u0010#\u001a\u00020\u0005HÆ\u0003J\t\u0010$\u001a\u00020\u0005HÆ\u0003J\t\u0010%\u001a\u00020\u0005HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003Jm\u0010'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u000eHÆ\u0001J\u0013\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010+\u001a\u00020\u0005HÖ\u0001J\t\u0010,\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011¨\u0006-"}, d2 = {"Lcom/fluttercandies/image_editor/option/Text;", "", "text", "", "x", "", "y", "fontSizePx", "r", "g", "b", "a", "fontName", TtmlNode.ATTR_TTS_TEXT_ALIGN, "Landroid/graphics/Paint$Align;", "(Ljava/lang/String;IIIIIIILjava/lang/String;Landroid/graphics/Paint$Align;)V", "getA", "()I", "getB", "getFontName", "()Ljava/lang/String;", "getFontSizePx", "getG", "getR", "getText", "getTextAlign", "()Landroid/graphics/Paint$Align;", "getX", "getY", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class Text {
    private final int a;
    private final int b;

    @NotNull
    private final String fontName;
    private final int fontSizePx;
    private final int g;
    private final int r;

    @NotNull
    private final String text;

    @NotNull
    private final Paint.Align textAlign;
    private final int x;
    private final int y;

    public Text(@NotNull String text, int i2, int i3, int i4, int i5, int i6, int i7, int i8, @NotNull String fontName, @NotNull Paint.Align textAlign) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(fontName, "fontName");
        Intrinsics.checkNotNullParameter(textAlign, "textAlign");
        this.text = text;
        this.x = i2;
        this.y = i3;
        this.fontSizePx = i4;
        this.r = i5;
        this.g = i6;
        this.b = i7;
        this.a = i8;
        this.fontName = fontName;
        this.textAlign = textAlign;
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getText() {
        return this.text;
    }

    @NotNull
    /* renamed from: component10, reason: from getter */
    public final Paint.Align getTextAlign() {
        return this.textAlign;
    }

    /* renamed from: component2, reason: from getter */
    public final int getX() {
        return this.x;
    }

    /* renamed from: component3, reason: from getter */
    public final int getY() {
        return this.y;
    }

    /* renamed from: component4, reason: from getter */
    public final int getFontSizePx() {
        return this.fontSizePx;
    }

    /* renamed from: component5, reason: from getter */
    public final int getR() {
        return this.r;
    }

    /* renamed from: component6, reason: from getter */
    public final int getG() {
        return this.g;
    }

    /* renamed from: component7, reason: from getter */
    public final int getB() {
        return this.b;
    }

    /* renamed from: component8, reason: from getter */
    public final int getA() {
        return this.a;
    }

    @NotNull
    /* renamed from: component9, reason: from getter */
    public final String getFontName() {
        return this.fontName;
    }

    @NotNull
    public final Text copy(@NotNull String text, int x2, int y2, int fontSizePx, int r2, int g2, int b2, int a2, @NotNull String fontName, @NotNull Paint.Align textAlign) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(fontName, "fontName");
        Intrinsics.checkNotNullParameter(textAlign, "textAlign");
        return new Text(text, x2, y2, fontSizePx, r2, g2, b2, a2, fontName, textAlign);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Text)) {
            return false;
        }
        Text text = (Text) other;
        return Intrinsics.areEqual(this.text, text.text) && this.x == text.x && this.y == text.y && this.fontSizePx == text.fontSizePx && this.r == text.r && this.g == text.g && this.b == text.b && this.a == text.a && Intrinsics.areEqual(this.fontName, text.fontName) && this.textAlign == text.textAlign;
    }

    public final int getA() {
        return this.a;
    }

    public final int getB() {
        return this.b;
    }

    @NotNull
    public final String getFontName() {
        return this.fontName;
    }

    public final int getFontSizePx() {
        return this.fontSizePx;
    }

    public final int getG() {
        return this.g;
    }

    public final int getR() {
        return this.r;
    }

    @NotNull
    public final String getText() {
        return this.text;
    }

    @NotNull
    public final Paint.Align getTextAlign() {
        return this.textAlign;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public int hashCode() {
        return (((((((((((((((((this.text.hashCode() * 31) + this.x) * 31) + this.y) * 31) + this.fontSizePx) * 31) + this.r) * 31) + this.g) * 31) + this.b) * 31) + this.a) * 31) + this.fontName.hashCode()) * 31) + this.textAlign.hashCode();
    }

    @NotNull
    public String toString() {
        return "Text(text=" + this.text + ", x=" + this.x + ", y=" + this.y + ", fontSizePx=" + this.fontSizePx + ", r=" + this.r + ", g=" + this.g + ", b=" + this.b + ", a=" + this.a + ", fontName=" + this.fontName + ", textAlign=" + this.textAlign + ")";
    }
}
