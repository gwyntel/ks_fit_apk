package com.google.android.gms.internal.measurement;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzc' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes3.dex */
public class zzpj {
    public static final zzpj zza;
    public static final zzpj zzb;
    public static final zzpj zzc;
    public static final zzpj zzd;
    public static final zzpj zze;
    public static final zzpj zzf;
    public static final zzpj zzg;
    public static final zzpj zzh;
    public static final zzpj zzi;
    public static final zzpj zzj;
    public static final zzpj zzk;
    public static final zzpj zzl;
    public static final zzpj zzm;
    public static final zzpj zzn;
    public static final zzpj zzo;
    public static final zzpj zzp;
    public static final zzpj zzq;
    public static final zzpj zzr;
    private static final /* synthetic */ zzpj[] zzs;
    private final zzpt zzt;
    private final int zzu;

    static {
        zzpj zzpjVar = new zzpj("DOUBLE", 0, zzpt.DOUBLE, 1);
        zza = zzpjVar;
        zzpj zzpjVar2 = new zzpj("FLOAT", 1, zzpt.FLOAT, 5);
        zzb = zzpjVar2;
        zzpt zzptVar = zzpt.LONG;
        zzpj zzpjVar3 = new zzpj("INT64", 2, zzptVar, 0);
        zzc = zzpjVar3;
        zzpj zzpjVar4 = new zzpj("UINT64", 3, zzptVar, 0);
        zzd = zzpjVar4;
        zzpt zzptVar2 = zzpt.INT;
        zzpj zzpjVar5 = new zzpj("INT32", 4, zzptVar2, 0);
        zze = zzpjVar5;
        zzpj zzpjVar6 = new zzpj("FIXED64", 5, zzptVar, 1);
        zzf = zzpjVar6;
        zzpj zzpjVar7 = new zzpj("FIXED32", 6, zzptVar2, 5);
        zzg = zzpjVar7;
        zzpj zzpjVar8 = new zzpj("BOOL", 7, zzpt.BOOLEAN, 0);
        zzh = zzpjVar8;
        int i2 = 2;
        zzpm zzpmVar = new zzpm("STRING", zzpt.STRING);
        zzi = zzpmVar;
        zzpt zzptVar3 = zzpt.MESSAGE;
        zzpo zzpoVar = new zzpo("GROUP", zzptVar3);
        zzj = zzpoVar;
        zzpq zzpqVar = new zzpq("MESSAGE", zzptVar3);
        zzk = zzpqVar;
        zzps zzpsVar = new zzps("BYTES", zzpt.BYTE_STRING);
        zzl = zzpsVar;
        zzpj zzpjVar9 = new zzpj("UINT32", 12, zzptVar2, 0);
        zzm = zzpjVar9;
        zzpj zzpjVar10 = new zzpj("ENUM", 13, zzpt.ENUM, 0);
        zzn = zzpjVar10;
        zzpj zzpjVar11 = new zzpj("SFIXED32", 14, zzptVar2, 5);
        zzo = zzpjVar11;
        zzpj zzpjVar12 = new zzpj("SFIXED64", 15, zzptVar, 1);
        zzp = zzpjVar12;
        zzpj zzpjVar13 = new zzpj("SINT32", 16, zzptVar2, 0);
        zzq = zzpjVar13;
        zzpj zzpjVar14 = new zzpj("SINT64", 17, zzptVar, 0);
        zzr = zzpjVar14;
        zzs = new zzpj[]{zzpjVar, zzpjVar2, zzpjVar3, zzpjVar4, zzpjVar5, zzpjVar6, zzpjVar7, zzpjVar8, zzpmVar, zzpoVar, zzpqVar, zzpsVar, zzpjVar9, zzpjVar10, zzpjVar11, zzpjVar12, zzpjVar13, zzpjVar14};
    }

    public static zzpj[] values() {
        return (zzpj[]) zzs.clone();
    }

    public final int zza() {
        return this.zzu;
    }

    public final zzpt zzb() {
        return this.zzt;
    }

    private zzpj(String str, int i2, zzpt zzptVar, int i3) {
        this.zzt = zzptVar;
        this.zzu = i3;
    }
}
