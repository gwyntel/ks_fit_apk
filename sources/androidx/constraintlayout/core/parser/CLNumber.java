package androidx.constraintlayout.core.parser;

/* loaded from: classes.dex */
public class CLNumber extends CLElement {

    /* renamed from: f, reason: collision with root package name */
    float f2788f;

    public CLNumber(char[] cArr) {
        super(cArr);
        this.f2788f = Float.NaN;
    }

    public static CLElement allocate(char[] cArr) {
        return new CLNumber(cArr);
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public float getFloat() {
        if (Float.isNaN(this.f2788f)) {
            this.f2788f = Float.parseFloat(content());
        }
        return this.f2788f;
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public int getInt() {
        if (Float.isNaN(this.f2788f)) {
            this.f2788f = Integer.parseInt(content());
        }
        return (int) this.f2788f;
    }

    public boolean isInt() {
        float f2 = getFloat();
        return ((float) ((int) f2)) == f2;
    }

    public void putValue(float f2) {
        this.f2788f = f2;
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    protected String toFormattedJSON(int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        a(sb, i2);
        float f2 = getFloat();
        int i4 = (int) f2;
        if (i4 == f2) {
            sb.append(i4);
        } else {
            sb.append(f2);
        }
        return sb.toString();
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    protected String toJSON() {
        float f2 = getFloat();
        int i2 = (int) f2;
        if (i2 == f2) {
            return "" + i2;
        }
        return "" + f2;
    }

    public CLNumber(float f2) {
        super(null);
        this.f2788f = f2;
    }
}
