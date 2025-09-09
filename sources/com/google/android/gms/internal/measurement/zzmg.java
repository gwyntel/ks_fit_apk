package com.google.android.gms.internal.measurement;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzb' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes3.dex */
public final class zzmg {
    public static final zzmg zza;
    public static final zzmg zzb;
    public static final zzmg zzc;
    public static final zzmg zzd;
    public static final zzmg zze;
    public static final zzmg zzf;
    public static final zzmg zzg;
    public static final zzmg zzh;
    public static final zzmg zzi;
    public static final zzmg zzj;
    private static final /* synthetic */ zzmg[] zzk;
    private final Class<?> zzl;
    private final Class<?> zzm;
    private final Object zzn;

    static {
        zzmg zzmgVar = new zzmg("VOID", 0, Void.class, Void.class, null);
        zza = zzmgVar;
        Class cls = Integer.TYPE;
        zzmg zzmgVar2 = new zzmg("INT", 1, cls, Integer.class, 0);
        zzb = zzmgVar2;
        zzmg zzmgVar3 = new zzmg("LONG", 2, Long.TYPE, Long.class, 0L);
        zzc = zzmgVar3;
        zzmg zzmgVar4 = new zzmg("FLOAT", 3, Float.TYPE, Float.class, Float.valueOf(0.0f));
        zzd = zzmgVar4;
        zzmg zzmgVar5 = new zzmg("DOUBLE", 4, Double.TYPE, Double.class, Double.valueOf(0.0d));
        zze = zzmgVar5;
        zzmg zzmgVar6 = new zzmg("BOOLEAN", 5, Boolean.TYPE, Boolean.class, Boolean.FALSE);
        zzf = zzmgVar6;
        zzmg zzmgVar7 = new zzmg("STRING", 6, String.class, String.class, "");
        zzg = zzmgVar7;
        zzmg zzmgVar8 = new zzmg("BYTE_STRING", 7, zzkm.class, zzkm.class, zzkm.zza);
        zzh = zzmgVar8;
        zzmg zzmgVar9 = new zzmg("ENUM", 8, cls, Integer.class, null);
        zzi = zzmgVar9;
        zzmg zzmgVar10 = new zzmg("MESSAGE", 9, Object.class, Object.class, null);
        zzj = zzmgVar10;
        zzk = new zzmg[]{zzmgVar, zzmgVar2, zzmgVar3, zzmgVar4, zzmgVar5, zzmgVar6, zzmgVar7, zzmgVar8, zzmgVar9, zzmgVar10};
    }

    private zzmg(String str, int i2, Class cls, Class cls2, Object obj) {
        this.zzl = cls;
        this.zzm = cls2;
        this.zzn = obj;
    }

    public static zzmg[] values() {
        return (zzmg[]) zzk.clone();
    }

    public final Class<?> zza() {
        return this.zzm;
    }
}
