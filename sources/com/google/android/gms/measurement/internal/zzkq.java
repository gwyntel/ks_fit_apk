package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.RemoteException;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzkq extends zze {
    private final zzlj zza;
    private zzfh zzb;
    private volatile Boolean zzc;
    private final zzaw zzd;
    private final zzmf zze;
    private final List<Runnable> zzf;
    private final zzaw zzg;

    protected zzkq(zzhc zzhcVar) {
        super(zzhcVar);
        this.zzf = new ArrayList();
        this.zze = new zzmf(zzhcVar.zzb());
        this.zza = new zzlj(this);
        this.zzd = new zzkp(this, zzhcVar);
        this.zzg = new zzlc(this, zzhcVar);
    }

    static /* synthetic */ void j(zzkq zzkqVar, ComponentName componentName) {
        zzkqVar.zzt();
        if (zzkqVar.zzb != null) {
            zzkqVar.zzb = null;
            zzkqVar.zzj().zzp().zza("Disconnected from device MeasurementService", componentName);
            zzkqVar.zzt();
            zzkqVar.zzad();
        }
    }

    static /* synthetic */ void z(zzkq zzkqVar) {
        zzkqVar.zzt();
        if (zzkqVar.zzah()) {
            zzkqVar.zzj().zzp().zza("Inactivity, disconnecting from the service");
            zzkqVar.zzae();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzak() {
        zzt();
        zzj().zzp().zza("Processing queued up service tasks", Integer.valueOf(this.zzf.size()));
        Iterator<Runnable> it = this.zzf.iterator();
        while (it.hasNext()) {
            try {
                it.next().run();
            } catch (RuntimeException e2) {
                zzj().zzg().zza("Task exception while flushing queue", e2);
            }
        }
        this.zzf.clear();
        this.zzg.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzal() {
        zzt();
        this.zze.zzb();
        this.zzd.zza(zzbi.zzaj.zza(null).longValue());
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00f6  */
    @androidx.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean zzam() {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkq.zzam():boolean");
    }

    protected final void c(com.google.android.gms.internal.measurement.zzcv zzcvVar, String str, String str2) throws IllegalStateException {
        zzt();
        zzu();
        zza(new zzlh(this, str, str2, zzb(false), zzcvVar));
    }

    protected final void d(com.google.android.gms.internal.measurement.zzcv zzcvVar, String str, String str2, boolean z2) throws IllegalStateException {
        zzt();
        zzu();
        zza(new zzks(this, str, str2, zzb(false), z2, zzcvVar));
    }

    protected final void e(zzad zzadVar) throws IllegalStateException {
        Preconditions.checkNotNull(zzadVar);
        zzt();
        zzu();
        zza(new zzlf(this, true, zzb(true), zzh().zza(zzadVar), new zzad(zzadVar), zzadVar));
    }

    protected final void f(zzbg zzbgVar, String str) {
        Preconditions.checkNotNull(zzbgVar);
        zzt();
        zzu();
        zza(new zzlg(this, true, zzb(true), zzh().zza(zzbgVar), zzbgVar, str));
    }

    protected final void g(zzfh zzfhVar) {
        zzt();
        Preconditions.checkNotNull(zzfhVar);
        this.zzb = zzfhVar;
        zzal();
        zzak();
    }

    final void h(zzfh zzfhVar, AbstractSafeParcelable abstractSafeParcelable, zzo zzoVar) throws Throwable {
        int size;
        zzt();
        zzu();
        int i2 = 100;
        int i3 = 0;
        while (i3 < 1001 && i2 == 100) {
            ArrayList arrayList = new ArrayList();
            List<AbstractSafeParcelable> listZza = zzh().zza(100);
            if (listZza != null) {
                arrayList.addAll(listZza);
                size = listZza.size();
            } else {
                size = 0;
            }
            if (abstractSafeParcelable != null && size < 100) {
                arrayList.add(abstractSafeParcelable);
            }
            int size2 = arrayList.size();
            int i4 = 0;
            while (i4 < size2) {
                Object obj = arrayList.get(i4);
                i4++;
                AbstractSafeParcelable abstractSafeParcelable2 = (AbstractSafeParcelable) obj;
                if (abstractSafeParcelable2 instanceof zzbg) {
                    try {
                        zzfhVar.zza((zzbg) abstractSafeParcelable2, zzoVar);
                    } catch (RemoteException e2) {
                        zzj().zzg().zza("Failed to send event to the service", e2);
                    }
                } else if (abstractSafeParcelable2 instanceof zzmz) {
                    try {
                        zzfhVar.zza((zzmz) abstractSafeParcelable2, zzoVar);
                    } catch (RemoteException e3) {
                        zzj().zzg().zza("Failed to send user property to the service", e3);
                    }
                } else if (abstractSafeParcelable2 instanceof zzad) {
                    try {
                        zzfhVar.zza((zzad) abstractSafeParcelable2, zzoVar);
                    } catch (RemoteException e4) {
                        zzj().zzg().zza("Failed to send conditional user property to the service", e4);
                    }
                } else {
                    zzj().zzg().zza("Discarding data. Unrecognized parcel type.");
                }
            }
            i3++;
            i2 = size;
        }
    }

    protected final void i(zzkf zzkfVar) {
        zzt();
        zzu();
        zza(new zzla(this, zzkfVar));
    }

    protected final void l(zzmz zzmzVar) {
        zzt();
        zzu();
        zza(new zzkt(this, zzb(true), zzh().zza(zzmzVar), zzmzVar));
    }

    protected final void m(AtomicReference atomicReference, Bundle bundle) throws IllegalStateException {
        zzt();
        zzu();
        zza(new zzku(this, atomicReference, zzb(false), bundle));
    }

    protected final void n(AtomicReference atomicReference, String str, String str2, String str3) throws IllegalStateException {
        zzt();
        zzu();
        zza(new zzli(this, atomicReference, str, str2, str3, zzb(false)));
    }

    protected final void o(AtomicReference atomicReference, String str, String str2, String str3, boolean z2) throws IllegalStateException {
        zzt();
        zzu();
        zza(new zzlk(this, atomicReference, str, str2, str3, zzb(false), z2));
    }

    protected final void p(AtomicReference atomicReference, boolean z2) throws IllegalStateException {
        zzt();
        zzu();
        zza(new zzkr(this, atomicReference, zzb(false), z2));
    }

    protected final void q(boolean z2) {
        zzt();
        zzu();
        if (z2) {
            zzh().zzaa();
        }
        if (w()) {
            zza(new zzld(this, zzb(false)));
        }
    }

    protected final zzam r() {
        zzt();
        zzu();
        zzfh zzfhVar = this.zzb;
        if (zzfhVar == null) {
            zzad();
            zzj().zzc().zza("Failed to get consents; not connected to service yet.");
            return null;
        }
        zzo zzoVarZzb = zzb(false);
        Preconditions.checkNotNull(zzoVarZzb);
        try {
            zzam zzamVarZza = zzfhVar.zza(zzoVarZzb);
            zzal();
            return zzamVarZza;
        } catch (RemoteException e2) {
            zzj().zzg().zza("Failed to get consents; remote exception", e2);
            return null;
        }
    }

    final Boolean s() {
        return this.zzc;
    }

    protected final void t() {
        zzt();
        zzu();
        zzo zzoVarZzb = zzb(false);
        zzh().zzaa();
        zza(new zzkw(this, zzoVarZzb));
    }

    protected final void u() {
        zzt();
        zzu();
        zza(new zzle(this, zzb(true)));
    }

    final boolean v() {
        zzt();
        zzu();
        return !zzam() || zzq().zzg() >= 200900;
    }

    final boolean w() {
        zzt();
        zzu();
        return !zzam() || zzq().zzg() >= zzbi.zzbo.zza(null).intValue();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    protected final void zzac() {
        zzt();
        zzu();
        zzo zzoVarZzb = zzb(true);
        zzh().zzab();
        zza(new zzkx(this, zzoVarZzb));
    }

    final void zzad() {
        zzt();
        zzu();
        if (zzah()) {
            return;
        }
        if (zzam()) {
            this.zza.zza();
            return;
        }
        if (zze().i()) {
            return;
        }
        List<ResolveInfo> listQueryIntentServices = zza().getPackageManager().queryIntentServices(new Intent().setClassName(zza(), "com.google.android.gms.measurement.AppMeasurementService"), 65536);
        if (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) {
            zzj().zzg().zza("Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest");
            return;
        }
        Intent intent = new Intent("com.google.android.gms.measurement.START");
        intent.setComponent(new ComponentName(zza(), "com.google.android.gms.measurement.AppMeasurementService"));
        this.zza.zza(intent);
    }

    @WorkerThread
    public final void zzae() {
        zzt();
        zzu();
        this.zza.zzb();
        try {
            ConnectionTracker.getInstance().unbindService(zza(), this.zza);
        } catch (IllegalArgumentException | IllegalStateException unused) {
        }
        this.zzb = null;
    }

    @WorkerThread
    public final boolean zzah() {
        zzt();
        zzu();
        return this.zzb != null;
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzb zzc() {
        return super.zzc();
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

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzfm zzg() {
        return super.zzg();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzfl zzh() {
        return super.zzh();
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

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzin zzm() {
        return super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzki zzn() {
        return super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzkq zzo() {
        return super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzly zzp() {
        return super.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzne zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }

    @Override // com.google.android.gms.measurement.internal.zze
    protected final boolean zzz() {
        return false;
    }

    @WorkerThread
    private final zzo zzb(boolean z2) {
        return zzg().b(z2 ? zzj().zzx() : null);
    }

    @WorkerThread
    public final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws IllegalStateException {
        zzt();
        zzu();
        zza(new zzky(this, zzb(false), zzcvVar));
    }

    @WorkerThread
    public final void zza(AtomicReference<String> atomicReference) {
        zzt();
        zzu();
        zza(new zzkv(this, atomicReference, zzb(false)));
    }

    @WorkerThread
    public final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, zzbg zzbgVar, String str) throws IllegalStateException {
        zzt();
        zzu();
        if (zzq().zza(12451000) != 0) {
            zzj().zzu().zza("Not bundling data. Service unavailable or out of date");
            zzq().zza(zzcvVar, new byte[0]);
        } else {
            zza(new zzlb(this, zzbgVar, str, zzcvVar));
        }
    }

    @WorkerThread
    private final void zza(Runnable runnable) throws IllegalStateException {
        zzt();
        if (zzah()) {
            runnable.run();
        } else {
            if (this.zzf.size() >= 1000) {
                zzj().zzg().zza("Discarding data. Max runnable queue size reached");
                return;
            }
            this.zzf.add(runnable);
            this.zzg.zza(60000L);
            zzad();
        }
    }

    @WorkerThread
    public final void zza(Bundle bundle) {
        zzt();
        zzu();
        zza(new zzkz(this, zzb(false), bundle));
    }
}
