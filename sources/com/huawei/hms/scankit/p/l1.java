package com.huawei.hms.scankit.p;

import java.util.List;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'd' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes4.dex */
public final class l1 {

    /* renamed from: b, reason: collision with root package name */
    public static final l1 f17488b;

    /* renamed from: c, reason: collision with root package name */
    public static final l1 f17489c;

    /* renamed from: d, reason: collision with root package name */
    public static final l1 f17490d;

    /* renamed from: e, reason: collision with root package name */
    public static final l1 f17491e;

    /* renamed from: f, reason: collision with root package name */
    public static final l1 f17492f;

    /* renamed from: g, reason: collision with root package name */
    public static final l1 f17493g;

    /* renamed from: h, reason: collision with root package name */
    public static final l1 f17494h;

    /* renamed from: i, reason: collision with root package name */
    public static final l1 f17495i;

    /* renamed from: j, reason: collision with root package name */
    public static final l1 f17496j;

    /* renamed from: k, reason: collision with root package name */
    public static final l1 f17497k;

    /* renamed from: l, reason: collision with root package name */
    private static final /* synthetic */ l1[] f17498l;

    /* renamed from: a, reason: collision with root package name */
    private final Class<?> f17499a;

    static {
        l1 l1Var = new l1("OTHER", 0, Object.class);
        f17488b = l1Var;
        l1 l1Var2 = new l1("POSSIBLE_FORMATS", 1, List.class);
        f17489c = l1Var2;
        Class cls = Void.TYPE;
        l1 l1Var3 = new l1("PHOTO_MODE", 2, cls);
        f17490d = l1Var3;
        l1 l1Var4 = new l1("PHOTO_MODE_NUM", 3, Integer.TYPE);
        f17491e = l1Var4;
        l1 l1Var5 = new l1("NEED_JNI", 4, cls);
        f17492f = l1Var5;
        l1 l1Var6 = new l1("TRY_HARDER", 5, Void.class);
        f17493g = l1Var6;
        l1 l1Var7 = new l1("CHARACTER_SET", 6, String.class);
        f17494h = l1Var7;
        l1 l1Var8 = new l1("RETURN_CODABAR_START_END", 7, Void.class);
        f17495i = l1Var8;
        l1 l1Var9 = new l1("NEED_RESULT_POINT_CALLBACK", 8, v6.class);
        f17496j = l1Var9;
        l1 l1Var10 = new l1("ALLOWED_EAN_EXTENSIONS", 9, int[].class);
        f17497k = l1Var10;
        f17498l = new l1[]{l1Var, l1Var2, l1Var3, l1Var4, l1Var5, l1Var6, l1Var7, l1Var8, l1Var9, l1Var10};
    }

    private l1(String str, int i2, Class cls) {
        this.f17499a = cls;
    }

    public static l1 valueOf(String str) {
        return (l1) Enum.valueOf(l1.class, str);
    }

    public static l1[] values() {
        return (l1[]) f17498l.clone();
    }
}
