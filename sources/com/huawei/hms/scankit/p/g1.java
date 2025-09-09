package com.huawei.hms.scankit.p;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
abstract class g1 {

    /* renamed from: a, reason: collision with root package name */
    public static final g1 f17283a;

    /* renamed from: b, reason: collision with root package name */
    public static final g1 f17284b;

    /* renamed from: c, reason: collision with root package name */
    public static final g1 f17285c;

    /* renamed from: d, reason: collision with root package name */
    public static final g1 f17286d;

    /* renamed from: e, reason: collision with root package name */
    public static final g1 f17287e;

    /* renamed from: f, reason: collision with root package name */
    public static final g1 f17288f;

    /* renamed from: g, reason: collision with root package name */
    public static final g1 f17289g;

    /* renamed from: h, reason: collision with root package name */
    public static final g1 f17290h;

    /* renamed from: i, reason: collision with root package name */
    private static final /* synthetic */ g1[] f17291i;

    enum a extends g1 {
        a(String str, int i2) {
            super(str, i2, null);
        }

        @Override // com.huawei.hms.scankit.p.g1
        boolean a(int i2, int i3) {
            return ((i2 + i3) & 1) == 0;
        }
    }

    static {
        a aVar = new a("DATA_MASK_000", 0);
        f17283a = aVar;
        g1 g1Var = new g1("DATA_MASK_001", 1) { // from class: com.huawei.hms.scankit.p.g1.b
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.g1
            boolean a(int i2, int i3) {
                return (i2 & 1) == 0;
            }
        };
        f17284b = g1Var;
        g1 g1Var2 = new g1("DATA_MASK_010", 2) { // from class: com.huawei.hms.scankit.p.g1.c
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.g1
            boolean a(int i2, int i3) {
                return i3 % 3 == 0;
            }
        };
        f17285c = g1Var2;
        g1 g1Var3 = new g1("DATA_MASK_011", 3) { // from class: com.huawei.hms.scankit.p.g1.d
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.g1
            boolean a(int i2, int i3) {
                return (i2 + i3) % 3 == 0;
            }
        };
        f17286d = g1Var3;
        g1 g1Var4 = new g1("DATA_MASK_100", 4) { // from class: com.huawei.hms.scankit.p.g1.e
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.g1
            boolean a(int i2, int i3) {
                return (((i2 / 2) + (i3 / 3)) & 1) == 0;
            }
        };
        f17287e = g1Var4;
        g1 g1Var5 = new g1("DATA_MASK_101", 5) { // from class: com.huawei.hms.scankit.p.g1.f
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.g1
            boolean a(int i2, int i3) {
                return (i2 * i3) % 6 == 0;
            }
        };
        f17288f = g1Var5;
        g1 g1Var6 = new g1("DATA_MASK_110", 6) { // from class: com.huawei.hms.scankit.p.g1.g
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.g1
            boolean a(int i2, int i3) {
                return (i2 * i3) % 6 < 3;
            }
        };
        f17289g = g1Var6;
        g1 g1Var7 = new g1("DATA_MASK_111", 7) { // from class: com.huawei.hms.scankit.p.g1.h
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.g1
            boolean a(int i2, int i3) {
                return (((i2 + i3) + ((i2 * i3) % 3)) & 1) == 0;
            }
        };
        f17290h = g1Var7;
        f17291i = new g1[]{aVar, g1Var, g1Var2, g1Var3, g1Var4, g1Var5, g1Var6, g1Var7};
    }

    private g1(String str, int i2) {
    }

    public static g1 valueOf(String str) {
        return (g1) Enum.valueOf(g1.class, str);
    }

    public static g1[] values() {
        return (g1[]) f17291i.clone();
    }

    final void a(s sVar, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = 0; i4 < i2; i4++) {
                if (a(i3, i4)) {
                    sVar.a(i4, i3);
                }
            }
        }
    }

    abstract boolean a(int i2, int i3);

    /* synthetic */ g1(String str, int i2, a aVar) {
        this(str, i2);
    }
}
