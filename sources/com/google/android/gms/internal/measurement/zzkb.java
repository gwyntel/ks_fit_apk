package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzkb;
import com.google.android.gms.internal.measurement.zzkc;
import java.io.IOException;

/* loaded from: classes3.dex */
public abstract class zzkb<MessageType extends zzkc<MessageType, BuilderType>, BuilderType extends zzkb<MessageType, BuilderType>> implements zzni {
    @Override // 
    /* renamed from: zza, reason: merged with bridge method [inline-methods] */
    public abstract BuilderType zzb(zzkx zzkxVar, zzlj zzljVar) throws IOException;

    public BuilderType zza(byte[] bArr, int i2, int i3) throws zzme {
        try {
            zzkx zzkxVarA = zzkx.a(bArr, 0, i3, false);
            zzb(zzkxVarA, zzlj.f13225a);
            zzkxVarA.zzc(0);
            return this;
        } catch (zzme e2) {
            throw e2;
        } catch (IOException e3) {
            throw new RuntimeException(zza("byte array"), e3);
        }
    }

    @Override // 
    /* renamed from: zzy, reason: merged with bridge method [inline-methods] */
    public abstract BuilderType clone();

    public BuilderType zza(byte[] bArr, int i2, int i3, zzlj zzljVar) throws zzme {
        try {
            zzkx zzkxVarA = zzkx.a(bArr, 0, i3, false);
            zzb(zzkxVarA, zzljVar);
            zzkxVarA.zzc(0);
            return this;
        } catch (zzme e2) {
            throw e2;
        } catch (IOException e3) {
            throw new RuntimeException(zza("byte array"), e3);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzni
    public final /* synthetic */ zzni zza(byte[] bArr) throws zzme {
        return zza(bArr, 0, bArr.length);
    }

    @Override // com.google.android.gms.internal.measurement.zzni
    public final /* synthetic */ zzni zza(byte[] bArr, zzlj zzljVar) throws zzme {
        return zza(bArr, 0, bArr.length, zzljVar);
    }

    private final String zza(String str) {
        return "Reading " + getClass().getName() + " from a " + str + " threw an IOException (should never happen).";
    }
}
