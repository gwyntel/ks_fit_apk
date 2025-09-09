package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import java.io.PrintStream;
import java.util.Arrays;

/* loaded from: classes.dex */
public class KeyFrameArray {

    public static class CustomArray {
        private static final int EMPTY = 999;

        /* renamed from: a, reason: collision with root package name */
        int[] f2707a = new int[101];

        /* renamed from: b, reason: collision with root package name */
        CustomAttribute[] f2708b = new CustomAttribute[101];

        /* renamed from: c, reason: collision with root package name */
        int f2709c;

        public CustomArray() {
            clear();
        }

        public void append(int i2, CustomAttribute customAttribute) {
            if (this.f2708b[i2] != null) {
                remove(i2);
            }
            this.f2708b[i2] = customAttribute;
            int[] iArr = this.f2707a;
            int i3 = this.f2709c;
            this.f2709c = i3 + 1;
            iArr[i3] = i2;
            Arrays.sort(iArr);
        }

        public void clear() {
            Arrays.fill(this.f2707a, 999);
            Arrays.fill(this.f2708b, (Object) null);
            this.f2709c = 0;
        }

        public void dump() {
            PrintStream printStream = System.out;
            printStream.println("V: " + Arrays.toString(Arrays.copyOf(this.f2707a, this.f2709c)));
            printStream.print("K: [");
            int i2 = 0;
            while (i2 < this.f2709c) {
                PrintStream printStream2 = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append(i2 == 0 ? "" : ", ");
                sb.append(valueAt(i2));
                printStream2.print(sb.toString());
                i2++;
            }
            System.out.println("]");
        }

        public int keyAt(int i2) {
            return this.f2707a[i2];
        }

        public void remove(int i2) {
            this.f2708b[i2] = null;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                int i5 = this.f2709c;
                if (i3 >= i5) {
                    this.f2709c = i5 - 1;
                    return;
                }
                int[] iArr = this.f2707a;
                if (i2 == iArr[i3]) {
                    iArr[i3] = 999;
                    i4++;
                }
                if (i3 != i4) {
                    iArr[i3] = iArr[i4];
                }
                i4++;
                i3++;
            }
        }

        public int size() {
            return this.f2709c;
        }

        public CustomAttribute valueAt(int i2) {
            return this.f2708b[this.f2707a[i2]];
        }
    }

    public static class CustomVar {
        private static final int EMPTY = 999;

        /* renamed from: a, reason: collision with root package name */
        int[] f2710a = new int[101];

        /* renamed from: b, reason: collision with root package name */
        CustomVariable[] f2711b = new CustomVariable[101];

        /* renamed from: c, reason: collision with root package name */
        int f2712c;

        public CustomVar() {
            clear();
        }

        public void append(int i2, CustomVariable customVariable) {
            if (this.f2711b[i2] != null) {
                remove(i2);
            }
            this.f2711b[i2] = customVariable;
            int[] iArr = this.f2710a;
            int i3 = this.f2712c;
            this.f2712c = i3 + 1;
            iArr[i3] = i2;
            Arrays.sort(iArr);
        }

        public void clear() {
            Arrays.fill(this.f2710a, 999);
            Arrays.fill(this.f2711b, (Object) null);
            this.f2712c = 0;
        }

        public void dump() {
            PrintStream printStream = System.out;
            printStream.println("V: " + Arrays.toString(Arrays.copyOf(this.f2710a, this.f2712c)));
            printStream.print("K: [");
            int i2 = 0;
            while (i2 < this.f2712c) {
                PrintStream printStream2 = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append(i2 == 0 ? "" : ", ");
                sb.append(valueAt(i2));
                printStream2.print(sb.toString());
                i2++;
            }
            System.out.println("]");
        }

        public int keyAt(int i2) {
            return this.f2710a[i2];
        }

        public void remove(int i2) {
            this.f2711b[i2] = null;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                int i5 = this.f2712c;
                if (i3 >= i5) {
                    this.f2712c = i5 - 1;
                    return;
                }
                int[] iArr = this.f2710a;
                if (i2 == iArr[i3]) {
                    iArr[i3] = 999;
                    i4++;
                }
                if (i3 != i4) {
                    iArr[i3] = iArr[i4];
                }
                i4++;
                i3++;
            }
        }

        public int size() {
            return this.f2712c;
        }

        public CustomVariable valueAt(int i2) {
            return this.f2711b[this.f2710a[i2]];
        }
    }

    static class FloatArray {
        private static final int EMPTY = 999;

        /* renamed from: a, reason: collision with root package name */
        int[] f2713a = new int[101];

        /* renamed from: b, reason: collision with root package name */
        float[][] f2714b = new float[101][];

        /* renamed from: c, reason: collision with root package name */
        int f2715c;

        public FloatArray() {
            clear();
        }

        public void append(int i2, float[] fArr) {
            if (this.f2714b[i2] != null) {
                remove(i2);
            }
            this.f2714b[i2] = fArr;
            int[] iArr = this.f2713a;
            int i3 = this.f2715c;
            this.f2715c = i3 + 1;
            iArr[i3] = i2;
            Arrays.sort(iArr);
        }

        public void clear() {
            Arrays.fill(this.f2713a, 999);
            Arrays.fill(this.f2714b, (Object) null);
            this.f2715c = 0;
        }

        public void dump() {
            PrintStream printStream = System.out;
            printStream.println("V: " + Arrays.toString(Arrays.copyOf(this.f2713a, this.f2715c)));
            printStream.print("K: [");
            int i2 = 0;
            while (i2 < this.f2715c) {
                PrintStream printStream2 = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append(i2 == 0 ? "" : ", ");
                sb.append(Arrays.toString(valueAt(i2)));
                printStream2.print(sb.toString());
                i2++;
            }
            System.out.println("]");
        }

        public int keyAt(int i2) {
            return this.f2713a[i2];
        }

        public void remove(int i2) {
            this.f2714b[i2] = null;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                int i5 = this.f2715c;
                if (i3 >= i5) {
                    this.f2715c = i5 - 1;
                    return;
                }
                int[] iArr = this.f2713a;
                if (i2 == iArr[i3]) {
                    iArr[i3] = 999;
                    i4++;
                }
                if (i3 != i4) {
                    iArr[i3] = iArr[i4];
                }
                i4++;
                i3++;
            }
        }

        public int size() {
            return this.f2715c;
        }

        public float[] valueAt(int i2) {
            return this.f2714b[this.f2713a[i2]];
        }
    }
}
