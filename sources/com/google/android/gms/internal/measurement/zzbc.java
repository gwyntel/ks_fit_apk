package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class zzbc extends zzay {
    protected zzbc() {
        this.f13178a.add(zzbv.APPLY);
        this.f13178a.add(zzbv.BLOCK);
        this.f13178a.add(zzbv.BREAK);
        this.f13178a.add(zzbv.CASE);
        this.f13178a.add(zzbv.DEFAULT);
        this.f13178a.add(zzbv.CONTINUE);
        this.f13178a.add(zzbv.DEFINE_FUNCTION);
        this.f13178a.add(zzbv.FN);
        this.f13178a.add(zzbv.IF);
        this.f13178a.add(zzbv.QUOTE);
        this.f13178a.add(zzbv.RETURN);
        this.f13178a.add(zzbv.SWITCH);
        this.f13178a.add(zzbv.TERNARY);
    }

    @Override // com.google.android.gms.internal.measurement.zzay
    public final zzaq zza(String str, zzh zzhVar, List<zzaq> list) {
        int i2 = 0;
        switch (zzbf.f13182a[zzg.zza(str).ordinal()]) {
            case 1:
                zzg.zza(zzbv.APPLY, 3, list);
                zzaq zzaqVarZza = zzhVar.zza(list.get(0));
                String strZzf = zzhVar.zza(list.get(1)).zzf();
                zzaq zzaqVarZza2 = zzhVar.zza(list.get(2));
                if (!(zzaqVarZza2 instanceof zzaf)) {
                    throw new IllegalArgumentException(String.format("Function arguments for Apply are not a list found %s", zzaqVarZza2.getClass().getCanonicalName()));
                }
                if (strZzf.isEmpty()) {
                    throw new IllegalArgumentException("Function name for apply is undefined");
                }
                return zzaqVarZza.zza(strZzf, zzhVar, ((zzaf) zzaqVarZza2).zzi());
            case 2:
                return zzhVar.zza().zza(new zzaf(list));
            case 3:
                zzg.zza(zzbv.BREAK, 0, list);
                return zzaq.zzf;
            case 4:
            case 5:
                if (!list.isEmpty()) {
                    zzaq zzaqVarZza3 = zzhVar.zza(list.get(0));
                    if (zzaqVarZza3 instanceof zzaf) {
                        return zzhVar.zza((zzaf) zzaqVarZza3);
                    }
                }
                return zzaq.zzc;
            case 6:
                zzg.zza(zzbv.BREAK, 0, list);
                return zzaq.zze;
            case 7:
                zzg.zzb(zzbv.DEFINE_FUNCTION, 2, list);
                zzar zzarVar = (zzar) zza(zzhVar, list);
                if (zzarVar.zza() == null) {
                    zzhVar.zzc("", zzarVar);
                } else {
                    zzhVar.zzc(zzarVar.zza(), zzarVar);
                }
                return zzarVar;
            case 8:
                return zza(zzhVar, list);
            case 9:
                zzg.zzb(zzbv.IF, 2, list);
                zzaq zzaqVarZza4 = zzhVar.zza(list.get(0));
                zzaq zzaqVarZza5 = zzhVar.zza(list.get(1));
                zzaq zzaqVarZza6 = list.size() > 2 ? zzhVar.zza(list.get(2)) : null;
                zzaq zzaqVar = zzaq.zzc;
                zzaq zzaqVarZza7 = zzaqVarZza4.zzd().booleanValue() ? zzhVar.zza((zzaf) zzaqVarZza5) : zzaqVarZza6 != null ? zzhVar.zza((zzaf) zzaqVarZza6) : zzaqVar;
                return zzaqVarZza7 instanceof zzaj ? zzaqVarZza7 : zzaqVar;
            case 10:
                return new zzaf(list);
            case 11:
                if (list.isEmpty()) {
                    return zzaq.zzg;
                }
                zzg.zza(zzbv.RETURN, 1, list);
                return new zzaj("return", zzhVar.zza(list.get(0)));
            case 12:
                zzg.zza(zzbv.SWITCH, 3, list);
                zzaq zzaqVarZza8 = zzhVar.zza(list.get(0));
                zzaq zzaqVarZza9 = zzhVar.zza(list.get(1));
                zzaq zzaqVarZza10 = zzhVar.zza(list.get(2));
                if (!(zzaqVarZza9 instanceof zzaf)) {
                    throw new IllegalArgumentException("Malformed SWITCH statement, cases are not a list");
                }
                if (!(zzaqVarZza10 instanceof zzaf)) {
                    throw new IllegalArgumentException("Malformed SWITCH statement, case statements are not a list");
                }
                zzaf zzafVar = (zzaf) zzaqVarZza9;
                zzaf zzafVar2 = (zzaf) zzaqVarZza10;
                boolean z2 = false;
                while (true) {
                    if (i2 < zzafVar.zzb()) {
                        if (z2 || zzaqVarZza8.equals(zzhVar.zza(zzafVar.zza(i2)))) {
                            zzaq zzaqVarZza11 = zzhVar.zza(zzafVar2.zza(i2));
                            if (!(zzaqVarZza11 instanceof zzaj)) {
                                z2 = true;
                            } else if (!((zzaj) zzaqVarZza11).zzb().equals("break")) {
                                return zzaqVarZza11;
                            }
                        }
                        i2++;
                    } else if (zzafVar.zzb() + 1 == zzafVar2.zzb()) {
                        zzaq zzaqVarZza12 = zzhVar.zza(zzafVar2.zza(zzafVar.zzb()));
                        if (zzaqVarZza12 instanceof zzaj) {
                            String strZzb = ((zzaj) zzaqVarZza12).zzb();
                            if (strZzb.equals("return") || strZzb.equals("continue")) {
                                return zzaqVarZza12;
                            }
                        }
                    }
                }
                return zzaq.zzc;
            case 13:
                zzg.zza(zzbv.TERNARY, 3, list);
                return zzhVar.zza(list.get(0)).zzd().booleanValue() ? zzhVar.zza(list.get(1)) : zzhVar.zza(list.get(2));
            default:
                return super.a(str);
        }
    }

    private static zzaq zza(zzh zzhVar, List<zzaq> list) {
        zzg.zzb(zzbv.FN, 2, list);
        zzaq zzaqVarZza = zzhVar.zza(list.get(0));
        zzaq zzaqVarZza2 = zzhVar.zza(list.get(1));
        if (zzaqVarZza2 instanceof zzaf) {
            List<zzaq> listZzi = ((zzaf) zzaqVarZza2).zzi();
            List<zzaq> arrayList = new ArrayList<>();
            if (list.size() > 2) {
                arrayList = list.subList(2, list.size());
            }
            return new zzar(zzaqVarZza.zzf(), listZzi, arrayList, zzhVar);
        }
        throw new IllegalArgumentException(String.format("FN requires an ArrayValue of parameter names found %s", zzaqVarZza2.getClass().getCanonicalName()));
    }
}
