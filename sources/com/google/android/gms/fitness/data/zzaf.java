package com.google.android.gms.fitness.data;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;

@ShowFirstParty
/* loaded from: classes3.dex */
public final class zzaf {
    private DataSource zza;
    private DataType zzb;

    public final zzaf zza(DataSource dataSource) {
        this.zza = dataSource;
        return this;
    }

    public final zzaf zzb(DataType dataType) {
        this.zzb = dataType;
        return this;
    }

    public final Subscription zzc() {
        DataSource dataSource;
        Preconditions.checkState((this.zza == null && this.zzb == null) ? false : true, "Must call setDataSource() or setDataType()");
        DataType dataType = this.zzb;
        Preconditions.checkState(dataType == null || (dataSource = this.zza) == null || dataType.equals(dataSource.getDataType()), "Specified data type is incompatible with specified data source");
        return new Subscription(this.zza, this.zzb, -1L, 2, 0);
    }
}
