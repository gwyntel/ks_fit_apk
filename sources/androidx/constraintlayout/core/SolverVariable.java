package androidx.constraintlayout.core;

import androidx.exifinterface.media.ExifInterface;
import java.util.Arrays;
import java.util.HashSet;

/* loaded from: classes.dex */
public class SolverVariable implements Comparable<SolverVariable> {
    private static final boolean INTERNAL_DEBUG = false;
    public static final int STRENGTH_BARRIER = 6;
    public static final int STRENGTH_CENTERING = 7;
    public static final int STRENGTH_EQUALITY = 5;
    public static final int STRENGTH_FIXED = 8;
    public static final int STRENGTH_HIGH = 3;
    public static final int STRENGTH_HIGHEST = 4;
    public static final int STRENGTH_LOW = 1;
    public static final int STRENGTH_MEDIUM = 2;
    public static final int STRENGTH_NONE = 0;
    private static final boolean VAR_USE_HASH = false;
    private static int uniqueConstantId = 1;
    private static int uniqueErrorId = 1;
    private static int uniqueId = 1;
    private static int uniqueSlackId = 1;
    private static int uniqueUnrestrictedId = 1;

    /* renamed from: a, reason: collision with root package name */
    int f2570a;

    /* renamed from: b, reason: collision with root package name */
    float[] f2571b;

    /* renamed from: c, reason: collision with root package name */
    float[] f2572c;
    public float computedValue;

    /* renamed from: d, reason: collision with root package name */
    Type f2573d;

    /* renamed from: e, reason: collision with root package name */
    ArrayRow[] f2574e;

    /* renamed from: f, reason: collision with root package name */
    int f2575f;

    /* renamed from: g, reason: collision with root package name */
    boolean f2576g;

    /* renamed from: h, reason: collision with root package name */
    int f2577h;

    /* renamed from: i, reason: collision with root package name */
    float f2578i;
    public int id;
    public boolean inGoal;
    public boolean isFinalValue;

    /* renamed from: j, reason: collision with root package name */
    HashSet f2579j;
    private String mName;
    public int strength;
    public int usageInRowCount;

    /* renamed from: androidx.constraintlayout.core.SolverVariable$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f2580a;

        static {
            int[] iArr = new int[Type.values().length];
            f2580a = iArr;
            try {
                iArr[Type.UNRESTRICTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2580a[Type.CONSTANT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2580a[Type.SLACK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f2580a[Type.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f2580a[Type.UNKNOWN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public enum Type {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN
    }

    public SolverVariable(String str, Type type) {
        this.id = -1;
        this.f2570a = -1;
        this.strength = 0;
        this.isFinalValue = false;
        this.f2571b = new float[9];
        this.f2572c = new float[9];
        this.f2574e = new ArrayRow[16];
        this.f2575f = 0;
        this.usageInRowCount = 0;
        this.f2576g = false;
        this.f2577h = -1;
        this.f2578i = 0.0f;
        this.f2579j = null;
        this.mName = str;
        this.f2573d = type;
    }

    static void a() {
        uniqueErrorId++;
    }

    private static String getUniqueName(Type type, String str) {
        if (str != null) {
            return str + uniqueErrorId;
        }
        int i2 = AnonymousClass1.f2580a[type.ordinal()];
        if (i2 == 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("U");
            int i3 = uniqueUnrestrictedId + 1;
            uniqueUnrestrictedId = i3;
            sb.append(i3);
            return sb.toString();
        }
        if (i2 == 2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("C");
            int i4 = uniqueConstantId + 1;
            uniqueConstantId = i4;
            sb2.append(i4);
            return sb2.toString();
        }
        if (i2 == 3) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(ExifInterface.LATITUDE_SOUTH);
            int i5 = uniqueSlackId + 1;
            uniqueSlackId = i5;
            sb3.append(i5);
            return sb3.toString();
        }
        if (i2 == 4) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("e");
            int i6 = uniqueErrorId + 1;
            uniqueErrorId = i6;
            sb4.append(i6);
            return sb4.toString();
        }
        if (i2 != 5) {
            throw new AssertionError(type.name());
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        int i7 = uniqueId + 1;
        uniqueId = i7;
        sb5.append(i7);
        return sb5.toString();
    }

    public final void addToRow(ArrayRow arrayRow) {
        int i2 = 0;
        while (true) {
            int i3 = this.f2575f;
            if (i2 >= i3) {
                ArrayRow[] arrayRowArr = this.f2574e;
                if (i3 >= arrayRowArr.length) {
                    this.f2574e = (ArrayRow[]) Arrays.copyOf(arrayRowArr, arrayRowArr.length * 2);
                }
                ArrayRow[] arrayRowArr2 = this.f2574e;
                int i4 = this.f2575f;
                arrayRowArr2[i4] = arrayRow;
                this.f2575f = i4 + 1;
                return;
            }
            if (this.f2574e[i2] == arrayRow) {
                return;
            } else {
                i2++;
            }
        }
    }

    public String getName() {
        return this.mName;
    }

    public final void removeFromRow(ArrayRow arrayRow) {
        int i2 = this.f2575f;
        int i3 = 0;
        while (i3 < i2) {
            if (this.f2574e[i3] == arrayRow) {
                while (i3 < i2 - 1) {
                    ArrayRow[] arrayRowArr = this.f2574e;
                    int i4 = i3 + 1;
                    arrayRowArr[i3] = arrayRowArr[i4];
                    i3 = i4;
                }
                this.f2575f--;
                return;
            }
            i3++;
        }
    }

    public void reset() {
        this.mName = null;
        this.f2573d = Type.UNKNOWN;
        this.strength = 0;
        this.id = -1;
        this.f2570a = -1;
        this.computedValue = 0.0f;
        this.isFinalValue = false;
        this.f2576g = false;
        this.f2577h = -1;
        this.f2578i = 0.0f;
        int i2 = this.f2575f;
        for (int i3 = 0; i3 < i2; i3++) {
            this.f2574e[i3] = null;
        }
        this.f2575f = 0;
        this.usageInRowCount = 0;
        this.inGoal = false;
        Arrays.fill(this.f2572c, 0.0f);
    }

    public void setFinalValue(LinearSystem linearSystem, float f2) {
        this.computedValue = f2;
        this.isFinalValue = true;
        this.f2576g = false;
        this.f2577h = -1;
        this.f2578i = 0.0f;
        int i2 = this.f2575f;
        this.f2570a = -1;
        for (int i3 = 0; i3 < i2; i3++) {
            this.f2574e[i3].updateFromFinalVariable(linearSystem, this, false);
        }
        this.f2575f = 0;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public void setSynonym(LinearSystem linearSystem, SolverVariable solverVariable, float f2) {
        this.f2576g = true;
        this.f2577h = solverVariable.id;
        this.f2578i = f2;
        int i2 = this.f2575f;
        this.f2570a = -1;
        for (int i3 = 0; i3 < i2; i3++) {
            this.f2574e[i3].updateFromSynonymVariable(linearSystem, this, false);
        }
        this.f2575f = 0;
        linearSystem.displayReadableRows();
    }

    public void setType(Type type, String str) {
        this.f2573d = type;
    }

    public String toString() {
        if (this.mName != null) {
            return "" + this.mName;
        }
        return "" + this.id;
    }

    public final void updateReferencesWithNewDefinition(LinearSystem linearSystem, ArrayRow arrayRow) {
        int i2 = this.f2575f;
        for (int i3 = 0; i3 < i2; i3++) {
            this.f2574e[i3].updateFromRow(linearSystem, arrayRow, false);
        }
        this.f2575f = 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(SolverVariable solverVariable) {
        return this.id - solverVariable.id;
    }

    public SolverVariable(Type type, String str) {
        this.id = -1;
        this.f2570a = -1;
        this.strength = 0;
        this.isFinalValue = false;
        this.f2571b = new float[9];
        this.f2572c = new float[9];
        this.f2574e = new ArrayRow[16];
        this.f2575f = 0;
        this.usageInRowCount = 0;
        this.f2576g = false;
        this.f2577h = -1;
        this.f2578i = 0.0f;
        this.f2579j = null;
        this.f2573d = type;
    }
}
