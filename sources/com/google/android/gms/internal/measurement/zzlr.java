package com.google.android.gms.internal.measurement;

import java.lang.reflect.Type;

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
public final class zzlr {
    public static final zzlr zza;
    private static final zzlr zzaa;
    private static final zzlr zzab;
    private static final zzlr zzac;
    private static final zzlr zzad;
    private static final zzlr zzae;
    private static final zzlr zzaf;
    private static final zzlr zzag;
    private static final zzlr zzah;
    private static final zzlr zzai;
    private static final zzlr zzaj;
    private static final zzlr zzak;
    private static final zzlr zzal;
    private static final zzlr zzam;
    private static final zzlr zzan;
    private static final zzlr zzao;
    private static final zzlr zzap;
    private static final zzlr zzaq;
    private static final zzlr zzar;
    private static final zzlr zzas;
    private static final zzlr zzat;
    private static final zzlr zzau;
    private static final zzlr zzav;
    private static final zzlr zzaw;
    private static final zzlr zzax;
    private static final zzlr zzay;
    private static final zzlr[] zzaz;
    public static final zzlr zzb;
    private static final Type[] zzba;
    private static final /* synthetic */ zzlr[] zzbb;
    private static final zzlr zzc;
    private static final zzlr zzd;
    private static final zzlr zze;
    private static final zzlr zzf;
    private static final zzlr zzg;
    private static final zzlr zzh;
    private static final zzlr zzi;
    private static final zzlr zzj;
    private static final zzlr zzk;
    private static final zzlr zzl;
    private static final zzlr zzm;
    private static final zzlr zzn;
    private static final zzlr zzo;
    private static final zzlr zzp;
    private static final zzlr zzq;
    private static final zzlr zzr;
    private static final zzlr zzs;
    private static final zzlr zzt;
    private static final zzlr zzu;
    private static final zzlr zzv;
    private static final zzlr zzw;
    private static final zzlr zzx;
    private static final zzlr zzy;
    private static final zzlr zzz;
    private final zzmg zzbc;
    private final int zzbd;
    private final zzlt zzbe;
    private final Class<?> zzbf;
    private final boolean zzbg;

    static {
        zzlt zzltVar = zzlt.SCALAR;
        zzmg zzmgVar = zzmg.zze;
        zzlr zzlrVar = new zzlr("DOUBLE", 0, 0, zzltVar, zzmgVar);
        zzc = zzlrVar;
        zzmg zzmgVar2 = zzmg.zzd;
        zzlr zzlrVar2 = new zzlr("FLOAT", 1, 1, zzltVar, zzmgVar2);
        zzd = zzlrVar2;
        zzmg zzmgVar3 = zzmg.zzc;
        zzlr zzlrVar3 = new zzlr("INT64", 2, 2, zzltVar, zzmgVar3);
        zze = zzlrVar3;
        zzlr zzlrVar4 = new zzlr("UINT64", 3, 3, zzltVar, zzmgVar3);
        zzf = zzlrVar4;
        zzmg zzmgVar4 = zzmg.zzb;
        zzlr zzlrVar5 = new zzlr("INT32", 4, 4, zzltVar, zzmgVar4);
        zzg = zzlrVar5;
        zzlr zzlrVar6 = new zzlr("FIXED64", 5, 5, zzltVar, zzmgVar3);
        zzh = zzlrVar6;
        zzlr zzlrVar7 = new zzlr("FIXED32", 6, 6, zzltVar, zzmgVar4);
        zzi = zzlrVar7;
        zzmg zzmgVar5 = zzmg.zzf;
        zzlr zzlrVar8 = new zzlr("BOOL", 7, 7, zzltVar, zzmgVar5);
        zzj = zzlrVar8;
        zzmg zzmgVar6 = zzmg.zzg;
        zzlr zzlrVar9 = new zzlr("STRING", 8, 8, zzltVar, zzmgVar6);
        zzk = zzlrVar9;
        zzmg zzmgVar7 = zzmg.zzj;
        zzlr zzlrVar10 = new zzlr("MESSAGE", 9, 9, zzltVar, zzmgVar7);
        zzl = zzlrVar10;
        zzmg zzmgVar8 = zzmg.zzh;
        zzlr zzlrVar11 = new zzlr("BYTES", 10, 10, zzltVar, zzmgVar8);
        zzm = zzlrVar11;
        zzlr zzlrVar12 = new zzlr("UINT32", 11, 11, zzltVar, zzmgVar4);
        zzn = zzlrVar12;
        zzmg zzmgVar9 = zzmg.zzi;
        zzlr zzlrVar13 = new zzlr("ENUM", 12, 12, zzltVar, zzmgVar9);
        zzo = zzlrVar13;
        zzlr zzlrVar14 = new zzlr("SFIXED32", 13, 13, zzltVar, zzmgVar4);
        zzp = zzlrVar14;
        zzlr zzlrVar15 = new zzlr("SFIXED64", 14, 14, zzltVar, zzmgVar3);
        zzq = zzlrVar15;
        zzlr zzlrVar16 = new zzlr("SINT32", 15, 15, zzltVar, zzmgVar4);
        zzr = zzlrVar16;
        zzlr zzlrVar17 = new zzlr("SINT64", 16, 16, zzltVar, zzmgVar3);
        zzs = zzlrVar17;
        zzlr zzlrVar18 = new zzlr("GROUP", 17, 17, zzltVar, zzmgVar7);
        zzt = zzlrVar18;
        zzlt zzltVar2 = zzlt.VECTOR;
        zzlr zzlrVar19 = new zzlr("DOUBLE_LIST", 18, 18, zzltVar2, zzmgVar);
        zzu = zzlrVar19;
        zzlr zzlrVar20 = new zzlr("FLOAT_LIST", 19, 19, zzltVar2, zzmgVar2);
        zzv = zzlrVar20;
        zzlr zzlrVar21 = new zzlr("INT64_LIST", 20, 20, zzltVar2, zzmgVar3);
        zzw = zzlrVar21;
        zzlr zzlrVar22 = new zzlr("UINT64_LIST", 21, 21, zzltVar2, zzmgVar3);
        zzx = zzlrVar22;
        zzlr zzlrVar23 = new zzlr("INT32_LIST", 22, 22, zzltVar2, zzmgVar4);
        zzy = zzlrVar23;
        zzlr zzlrVar24 = new zzlr("FIXED64_LIST", 23, 23, zzltVar2, zzmgVar3);
        zzz = zzlrVar24;
        zzlr zzlrVar25 = new zzlr("FIXED32_LIST", 24, 24, zzltVar2, zzmgVar4);
        zzaa = zzlrVar25;
        zzlr zzlrVar26 = new zzlr("BOOL_LIST", 25, 25, zzltVar2, zzmgVar5);
        zzab = zzlrVar26;
        zzlr zzlrVar27 = new zzlr("STRING_LIST", 26, 26, zzltVar2, zzmgVar6);
        zzac = zzlrVar27;
        zzlr zzlrVar28 = new zzlr("MESSAGE_LIST", 27, 27, zzltVar2, zzmgVar7);
        zzad = zzlrVar28;
        zzlr zzlrVar29 = new zzlr("BYTES_LIST", 28, 28, zzltVar2, zzmgVar8);
        zzae = zzlrVar29;
        zzlr zzlrVar30 = new zzlr("UINT32_LIST", 29, 29, zzltVar2, zzmgVar4);
        zzaf = zzlrVar30;
        zzlr zzlrVar31 = new zzlr("ENUM_LIST", 30, 30, zzltVar2, zzmgVar9);
        zzag = zzlrVar31;
        zzlr zzlrVar32 = new zzlr("SFIXED32_LIST", 31, 31, zzltVar2, zzmgVar4);
        zzah = zzlrVar32;
        zzlr zzlrVar33 = new zzlr("SFIXED64_LIST", 32, 32, zzltVar2, zzmgVar3);
        zzai = zzlrVar33;
        zzlr zzlrVar34 = new zzlr("SINT32_LIST", 33, 33, zzltVar2, zzmgVar4);
        zzaj = zzlrVar34;
        zzlr zzlrVar35 = new zzlr("SINT64_LIST", 34, 34, zzltVar2, zzmgVar3);
        zzak = zzlrVar35;
        zzlt zzltVar3 = zzlt.PACKED_VECTOR;
        zzlr zzlrVar36 = new zzlr("DOUBLE_LIST_PACKED", 35, 35, zzltVar3, zzmgVar);
        zza = zzlrVar36;
        zzlr zzlrVar37 = new zzlr("FLOAT_LIST_PACKED", 36, 36, zzltVar3, zzmgVar2);
        zzal = zzlrVar37;
        zzlr zzlrVar38 = new zzlr("INT64_LIST_PACKED", 37, 37, zzltVar3, zzmgVar3);
        zzam = zzlrVar38;
        zzlr zzlrVar39 = new zzlr("UINT64_LIST_PACKED", 38, 38, zzltVar3, zzmgVar3);
        zzan = zzlrVar39;
        zzlr zzlrVar40 = new zzlr("INT32_LIST_PACKED", 39, 39, zzltVar3, zzmgVar4);
        zzao = zzlrVar40;
        zzlr zzlrVar41 = new zzlr("FIXED64_LIST_PACKED", 40, 40, zzltVar3, zzmgVar3);
        zzap = zzlrVar41;
        zzlr zzlrVar42 = new zzlr("FIXED32_LIST_PACKED", 41, 41, zzltVar3, zzmgVar4);
        zzaq = zzlrVar42;
        zzlr zzlrVar43 = new zzlr("BOOL_LIST_PACKED", 42, 42, zzltVar3, zzmgVar5);
        zzar = zzlrVar43;
        zzlr zzlrVar44 = new zzlr("UINT32_LIST_PACKED", 43, 43, zzltVar3, zzmgVar4);
        zzas = zzlrVar44;
        zzlr zzlrVar45 = new zzlr("ENUM_LIST_PACKED", 44, 44, zzltVar3, zzmgVar9);
        zzat = zzlrVar45;
        zzlr zzlrVar46 = new zzlr("SFIXED32_LIST_PACKED", 45, 45, zzltVar3, zzmgVar4);
        zzau = zzlrVar46;
        zzlr zzlrVar47 = new zzlr("SFIXED64_LIST_PACKED", 46, 46, zzltVar3, zzmgVar3);
        zzav = zzlrVar47;
        zzlr zzlrVar48 = new zzlr("SINT32_LIST_PACKED", 47, 47, zzltVar3, zzmgVar4);
        zzaw = zzlrVar48;
        zzlr zzlrVar49 = new zzlr("SINT64_LIST_PACKED", 48, 48, zzltVar3, zzmgVar3);
        zzb = zzlrVar49;
        zzlr zzlrVar50 = new zzlr("GROUP_LIST", 49, 49, zzltVar2, zzmgVar7);
        zzax = zzlrVar50;
        zzlr zzlrVar51 = new zzlr("MAP", 50, 50, zzlt.MAP, zzmg.zza);
        zzay = zzlrVar51;
        zzbb = new zzlr[]{zzlrVar, zzlrVar2, zzlrVar3, zzlrVar4, zzlrVar5, zzlrVar6, zzlrVar7, zzlrVar8, zzlrVar9, zzlrVar10, zzlrVar11, zzlrVar12, zzlrVar13, zzlrVar14, zzlrVar15, zzlrVar16, zzlrVar17, zzlrVar18, zzlrVar19, zzlrVar20, zzlrVar21, zzlrVar22, zzlrVar23, zzlrVar24, zzlrVar25, zzlrVar26, zzlrVar27, zzlrVar28, zzlrVar29, zzlrVar30, zzlrVar31, zzlrVar32, zzlrVar33, zzlrVar34, zzlrVar35, zzlrVar36, zzlrVar37, zzlrVar38, zzlrVar39, zzlrVar40, zzlrVar41, zzlrVar42, zzlrVar43, zzlrVar44, zzlrVar45, zzlrVar46, zzlrVar47, zzlrVar48, zzlrVar49, zzlrVar50, zzlrVar51};
        zzba = new Type[0];
        zzlr[] zzlrVarArrValues = values();
        zzaz = new zzlr[zzlrVarArrValues.length];
        for (zzlr zzlrVar52 : zzlrVarArrValues) {
            zzaz[zzlrVar52.zzbd] = zzlrVar52;
        }
    }

    private zzlr(String str, int i2, int i3, zzlt zzltVar, zzmg zzmgVar) {
        int i4;
        this.zzbd = i3;
        this.zzbe = zzltVar;
        this.zzbc = zzmgVar;
        int iOrdinal = zzltVar.ordinal();
        if (iOrdinal == 1 || iOrdinal == 3) {
            this.zzbf = zzmgVar.zza();
        } else {
            this.zzbf = null;
        }
        this.zzbg = (zzltVar != zzlt.SCALAR || (i4 = zzlq.f13229a[zzmgVar.ordinal()]) == 1 || i4 == 2 || i4 == 3) ? false : true;
    }

    public static zzlr[] values() {
        return (zzlr[]) zzbb.clone();
    }

    public final int zza() {
        return this.zzbd;
    }
}
