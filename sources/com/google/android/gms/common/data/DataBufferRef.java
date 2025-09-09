package com.google.android.gms.common.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class DataBufferRef {

    /* renamed from: a, reason: collision with root package name */
    protected final DataHolder f12794a;

    /* renamed from: b, reason: collision with root package name */
    protected int f12795b;
    private int zaa;

    @KeepForSdk
    public DataBufferRef(@NonNull DataHolder dataHolder, int i2) {
        this.f12794a = (DataHolder) Preconditions.checkNotNull(dataHolder);
        a(i2);
    }

    protected final void a(int i2) {
        boolean z2 = false;
        if (i2 >= 0 && i2 < this.f12794a.getCount()) {
            z2 = true;
        }
        Preconditions.checkState(z2);
        this.f12795b = i2;
        this.zaa = this.f12794a.getWindowIndex(i2);
    }

    @KeepForSdk
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof DataBufferRef) {
            DataBufferRef dataBufferRef = (DataBufferRef) obj;
            if (Objects.equal(Integer.valueOf(dataBufferRef.f12795b), Integer.valueOf(this.f12795b)) && Objects.equal(Integer.valueOf(dataBufferRef.zaa), Integer.valueOf(this.zaa)) && dataBufferRef.f12794a == this.f12794a) {
                return true;
            }
        }
        return false;
    }

    @KeepForSdk
    public boolean hasColumn(@NonNull String str) {
        return this.f12794a.hasColumn(str);
    }

    @KeepForSdk
    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.f12795b), Integer.valueOf(this.zaa), this.f12794a);
    }

    @KeepForSdk
    public boolean isDataValid() {
        return !this.f12794a.isClosed();
    }
}
