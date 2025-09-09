package com.huawei.hms.scankit.p;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
abstract class f1 {

    /* renamed from: a, reason: collision with root package name */
    public static final f1 f17232a;

    /* renamed from: b, reason: collision with root package name */
    public static final f1 f17233b;

    /* renamed from: c, reason: collision with root package name */
    public static final f1 f17234c;

    /* renamed from: d, reason: collision with root package name */
    public static final f1 f17235d;

    /* renamed from: e, reason: collision with root package name */
    public static final f1 f17236e;

    /* renamed from: f, reason: collision with root package name */
    public static final f1 f17237f;

    /* renamed from: g, reason: collision with root package name */
    public static final f1 f17238g;

    /* renamed from: h, reason: collision with root package name */
    public static final f1 f17239h;

    /* renamed from: i, reason: collision with root package name */
    private static final /* synthetic */ f1[] f17240i;

    enum a extends f1 {
        a(String str, int i2) {
            super(str, i2, null);
        }

        @Override // com.huawei.hms.scankit.p.f1
        boolean a(int i2, int i3) {
            return ((i2 + i3) & 1) == 0;
        }
    }

    static {
        a aVar = new a("DATA_MASK_000", 0);
        f17232a = aVar;
        f1 f1Var = new f1("DATA_MASK_001", 1) { // from class: com.huawei.hms.scankit.p.f1.b
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.f1
            boolean a(int i2, int i3) {
                return (i2 & 1) == 0;
            }
        };
        f17233b = f1Var;
        f1 f1Var2 = new f1("DATA_MASK_010", 2) { // from class: com.huawei.hms.scankit.p.f1.c
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.f1
            boolean a(int i2, int i3) {
                return i3 % 3 == 0;
            }
        };
        f17234c = f1Var2;
        f1 f1Var3 = new f1("DATA_MASK_011", 3) { // from class: com.huawei.hms.scankit.p.f1.d
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.f1
            boolean a(int i2, int i3) {
                return (i2 + i3) % 3 == 0;
            }
        };
        f17235d = f1Var3;
        f1 f1Var4 = new f1("DATA_MASK_100", 4) { // from class: com.huawei.hms.scankit.p.f1.e
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.f1
            boolean a(int i2, int i3) {
                return (((i2 / 2) + (i3 / 3)) & 1) == 0;
            }
        };
        f17236e = f1Var4;
        f1 f1Var5 = new f1("DATA_MASK_101", 5) { // from class: com.huawei.hms.scankit.p.f1.f
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.f1
            boolean a(int i2, int i3) {
                return (i2 * i3) % 6 == 0;
            }
        };
        f17237f = f1Var5;
        f1 f1Var6 = new f1("DATA_MASK_110", 6) { // from class: com.huawei.hms.scankit.p.f1.g
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.f1
            boolean a(int i2, int i3) {
                return (i2 * i3) % 6 < 3;
            }
        };
        f17238g = f1Var6;
        f1 f1Var7 = new f1("DATA_MASK_111", 7) { // from class: com.huawei.hms.scankit.p.f1.h
            {
                a aVar2 = null;
            }

            @Override // com.huawei.hms.scankit.p.f1
            boolean a(int i2, int i3) {
                return (((i2 + i3) + ((i2 * i3) % 3)) & 1) == 0;
            }
        };
        f17239h = f1Var7;
        f17240i = new f1[]{aVar, f1Var, f1Var2, f1Var3, f1Var4, f1Var5, f1Var6, f1Var7};
    }

    private f1(String str, int i2) {
    }

    public static f1 valueOf(String str) {
        return (f1) Enum.valueOf(f1.class, str);
    }

    public static f1[] values() {
        return (f1[]) f17240i.clone();
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

    /* synthetic */ f1(String str, int i2, a aVar) {
        this(str, i2);
    }
}
