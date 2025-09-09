package com.google.android.gms.measurement.internal;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.util.Clock;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzba extends zzid {
    private long zza;
    private String zzb;
    private AccountManager zzc;
    private Boolean zzd;
    private long zze;

    zzba(zzhc zzhcVar) {
        super(zzhcVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    protected final boolean a() {
        Calendar calendar = Calendar.getInstance();
        this.zza = TimeUnit.MINUTES.convert(calendar.get(15) + calendar.get(16), TimeUnit.MILLISECONDS);
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        Locale locale2 = Locale.ENGLISH;
        this.zzb = language.toLowerCase(locale2) + Constants.ACCEPT_TIME_SEPARATOR_SERVER + locale.getCountry().toLowerCase(locale2);
        return false;
    }

    final long c() {
        zzt();
        return this.zze;
    }

    final void d() {
        zzt();
        this.zzd = null;
        this.zze = 0L;
    }

    final boolean e() throws OperationCanceledException, IOException, AuthenticatorException {
        Account[] result;
        zzt();
        long jCurrentTimeMillis = zzb().currentTimeMillis();
        if (jCurrentTimeMillis - this.zze > 86400000) {
            this.zzd = null;
        }
        Boolean bool = this.zzd;
        if (bool != null) {
            return bool.booleanValue();
        }
        if (ContextCompat.checkSelfPermission(zza(), "android.permission.GET_ACCOUNTS") != 0) {
            zzj().zzw().zza("Permission error checking for dasher/unicorn accounts");
            this.zze = jCurrentTimeMillis;
            this.zzd = Boolean.FALSE;
            return false;
        }
        if (this.zzc == null) {
            this.zzc = AccountManager.get(zza());
        }
        try {
            result = this.zzc.getAccountsByTypeAndFeatures("com.google", new String[]{"service_HOSTED"}, null, null).getResult();
        } catch (AuthenticatorException e2) {
            e = e2;
            zzj().zzm().zza("Exception checking account types", e);
            this.zze = jCurrentTimeMillis;
            this.zzd = Boolean.FALSE;
            return false;
        } catch (OperationCanceledException e3) {
            e = e3;
            zzj().zzm().zza("Exception checking account types", e);
            this.zze = jCurrentTimeMillis;
            this.zzd = Boolean.FALSE;
            return false;
        } catch (IOException e4) {
            e = e4;
            zzj().zzm().zza("Exception checking account types", e);
            this.zze = jCurrentTimeMillis;
            this.zzd = Boolean.FALSE;
            return false;
        }
        if (result != null && result.length > 0) {
            this.zzd = Boolean.TRUE;
            this.zze = jCurrentTimeMillis;
            return true;
        }
        Account[] result2 = this.zzc.getAccountsByTypeAndFeatures("com.google", new String[]{"service_uca"}, null, null).getResult();
        if (result2 != null && result2.length > 0) {
            this.zzd = Boolean.TRUE;
            this.zze = jCurrentTimeMillis;
            return true;
        }
        this.zze = jCurrentTimeMillis;
        this.zzd = Boolean.FALSE;
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzae zzd() {
        return super.zzd();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzaf zze() {
        return super.zze();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzba zzf() {
        return super.zzf();
    }

    public final long zzg() {
        zzab();
        return this.zza;
    }

    public final String zzh() {
        zzab();
        return this.zzb;
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzfn zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzfs zzj() {
        return super.zzj();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzge zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzgz zzl() {
        return super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzne zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }
}
