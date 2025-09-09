package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

/* loaded from: classes.dex */
public class TypedBundle {
    private static final int INITIAL_BOOLEAN = 4;
    private static final int INITIAL_FLOAT = 10;
    private static final int INITIAL_INT = 10;
    private static final int INITIAL_STRING = 5;

    /* renamed from: a, reason: collision with root package name */
    int[] f2762a = new int[10];

    /* renamed from: b, reason: collision with root package name */
    int[] f2763b = new int[10];

    /* renamed from: c, reason: collision with root package name */
    int f2764c = 0;

    /* renamed from: d, reason: collision with root package name */
    int[] f2765d = new int[10];

    /* renamed from: e, reason: collision with root package name */
    float[] f2766e = new float[10];

    /* renamed from: f, reason: collision with root package name */
    int f2767f = 0;

    /* renamed from: g, reason: collision with root package name */
    int[] f2768g = new int[5];

    /* renamed from: h, reason: collision with root package name */
    String[] f2769h = new String[5];

    /* renamed from: i, reason: collision with root package name */
    int f2770i = 0;

    /* renamed from: j, reason: collision with root package name */
    int[] f2771j = new int[4];

    /* renamed from: k, reason: collision with root package name */
    boolean[] f2772k = new boolean[4];

    /* renamed from: l, reason: collision with root package name */
    int f2773l = 0;

    public void add(int i2, int i3) {
        int i4 = this.f2764c;
        int[] iArr = this.f2762a;
        if (i4 >= iArr.length) {
            this.f2762a = Arrays.copyOf(iArr, iArr.length * 2);
            int[] iArr2 = this.f2763b;
            this.f2763b = Arrays.copyOf(iArr2, iArr2.length * 2);
        }
        int[] iArr3 = this.f2762a;
        int i5 = this.f2764c;
        iArr3[i5] = i2;
        int[] iArr4 = this.f2763b;
        this.f2764c = i5 + 1;
        iArr4[i5] = i3;
    }

    public void addIfNotNull(int i2, String str) {
        if (str != null) {
            add(i2, str);
        }
    }

    public void applyDelta(TypedValues typedValues) {
        for (int i2 = 0; i2 < this.f2764c; i2++) {
            typedValues.setValue(this.f2762a[i2], this.f2763b[i2]);
        }
        for (int i3 = 0; i3 < this.f2767f; i3++) {
            typedValues.setValue(this.f2765d[i3], this.f2766e[i3]);
        }
        for (int i4 = 0; i4 < this.f2770i; i4++) {
            typedValues.setValue(this.f2768g[i4], this.f2769h[i4]);
        }
        for (int i5 = 0; i5 < this.f2773l; i5++) {
            typedValues.setValue(this.f2771j[i5], this.f2772k[i5]);
        }
    }

    public void clear() {
        this.f2773l = 0;
        this.f2770i = 0;
        this.f2767f = 0;
        this.f2764c = 0;
    }

    public int getInteger(int i2) {
        for (int i3 = 0; i3 < this.f2764c; i3++) {
            if (this.f2762a[i3] == i2) {
                return this.f2763b[i3];
            }
        }
        return -1;
    }

    public void add(int i2, float f2) {
        int i3 = this.f2767f;
        int[] iArr = this.f2765d;
        if (i3 >= iArr.length) {
            this.f2765d = Arrays.copyOf(iArr, iArr.length * 2);
            float[] fArr = this.f2766e;
            this.f2766e = Arrays.copyOf(fArr, fArr.length * 2);
        }
        int[] iArr2 = this.f2765d;
        int i4 = this.f2767f;
        iArr2[i4] = i2;
        float[] fArr2 = this.f2766e;
        this.f2767f = i4 + 1;
        fArr2[i4] = f2;
    }

    public void applyDelta(TypedBundle typedBundle) {
        for (int i2 = 0; i2 < this.f2764c; i2++) {
            typedBundle.add(this.f2762a[i2], this.f2763b[i2]);
        }
        for (int i3 = 0; i3 < this.f2767f; i3++) {
            typedBundle.add(this.f2765d[i3], this.f2766e[i3]);
        }
        for (int i4 = 0; i4 < this.f2770i; i4++) {
            typedBundle.add(this.f2768g[i4], this.f2769h[i4]);
        }
        for (int i5 = 0; i5 < this.f2773l; i5++) {
            typedBundle.add(this.f2771j[i5], this.f2772k[i5]);
        }
    }

    public void add(int i2, String str) {
        int i3 = this.f2770i;
        int[] iArr = this.f2768g;
        if (i3 >= iArr.length) {
            this.f2768g = Arrays.copyOf(iArr, iArr.length * 2);
            String[] strArr = this.f2769h;
            this.f2769h = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
        }
        int[] iArr2 = this.f2768g;
        int i4 = this.f2770i;
        iArr2[i4] = i2;
        String[] strArr2 = this.f2769h;
        this.f2770i = i4 + 1;
        strArr2[i4] = str;
    }

    public void add(int i2, boolean z2) {
        int i3 = this.f2773l;
        int[] iArr = this.f2771j;
        if (i3 >= iArr.length) {
            this.f2771j = Arrays.copyOf(iArr, iArr.length * 2);
            boolean[] zArr = this.f2772k;
            this.f2772k = Arrays.copyOf(zArr, zArr.length * 2);
        }
        int[] iArr2 = this.f2771j;
        int i4 = this.f2773l;
        iArr2[i4] = i2;
        boolean[] zArr2 = this.f2772k;
        this.f2773l = i4 + 1;
        zArr2[i4] = z2;
    }
}
