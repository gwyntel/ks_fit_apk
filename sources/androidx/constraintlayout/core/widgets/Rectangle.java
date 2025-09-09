package androidx.constraintlayout.core.widgets;

/* loaded from: classes.dex */
public class Rectangle {
    public int height;
    public int width;

    /* renamed from: x, reason: collision with root package name */
    public int f2922x;

    /* renamed from: y, reason: collision with root package name */
    public int f2923y;

    public boolean contains(int i2, int i3) {
        int i4;
        int i5 = this.f2922x;
        return i2 >= i5 && i2 < i5 + this.width && i3 >= (i4 = this.f2923y) && i3 < i4 + this.height;
    }

    public int getCenterX() {
        return (this.f2922x + this.width) / 2;
    }

    public int getCenterY() {
        return (this.f2923y + this.height) / 2;
    }

    public void setBounds(int i2, int i3, int i4, int i5) {
        this.f2922x = i2;
        this.f2923y = i3;
        this.width = i4;
        this.height = i5;
    }
}
