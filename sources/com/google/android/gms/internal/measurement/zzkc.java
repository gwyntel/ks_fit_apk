package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzkb;
import com.google.android.gms.internal.measurement.zzkc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class zzkc<MessageType extends zzkc<MessageType, BuilderType>, BuilderType extends zzkb<MessageType, BuilderType>> implements zznj {
    protected int zza = 0;

    protected static void b(Iterable iterable, List list) {
        zzlz.b(iterable);
        if (iterable instanceof zzmp) {
            List<?> listZze = ((zzmp) iterable).zze();
            zzmp zzmpVar = (zzmp) list;
            int size = list.size();
            for (Object obj : listZze) {
                if (obj == null) {
                    String str = "Element at index " + (zzmpVar.size() - size) + " is null.";
                    for (int size2 = zzmpVar.size() - 1; size2 >= size; size2--) {
                        zzmpVar.remove(size2);
                    }
                    throw new NullPointerException(str);
                }
                if (obj instanceof zzkm) {
                    zzmpVar.zza((zzkm) obj);
                } else {
                    zzmpVar.add((String) obj);
                }
            }
            return;
        }
        if (iterable instanceof zznv) {
            list.addAll((Collection) iterable);
            return;
        }
        if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
            ((ArrayList) list).ensureCapacity(list.size() + ((Collection) iterable).size());
        }
        int size3 = list.size();
        for (Object obj2 : iterable) {
            if (obj2 == null) {
                String str2 = "Element at index " + (list.size() - size3) + " is null.";
                for (int size4 = list.size() - 1; size4 >= size3; size4--) {
                    list.remove(size4);
                }
                throw new NullPointerException(str2);
            }
            list.add(obj2);
        }
    }

    int a(zzob zzobVar) {
        int iC = c();
        if (iC != -1) {
            return iC;
        }
        int iZza = zzobVar.zza(this);
        d(iZza);
        return iZza;
    }

    int c() {
        throw new UnsupportedOperationException();
    }

    void d(int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.measurement.zznj
    public final zzkm zzbu() {
        try {
            zzkr zzkrVarZzc = zzkm.zzc(zzbw());
            zza(zzkrVarZzc.zzb());
            return zzkrVarZzc.zza();
        } catch (IOException e2) {
            throw new RuntimeException("Serializing " + getClass().getName() + " to a ByteString threw an IOException (should never happen).", e2);
        }
    }

    public final byte[] zzbv() {
        try {
            byte[] bArr = new byte[zzbw()];
            zzld zzldVarZzb = zzld.zzb(bArr);
            zza(zzldVarZzb);
            zzldVarZzb.zzb();
            return bArr;
        } catch (IOException e2) {
            throw new RuntimeException("Serializing " + getClass().getName() + " to a byte array threw an IOException (should never happen).", e2);
        }
    }
}
