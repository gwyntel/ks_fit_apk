package com.google.android.gms.common.data;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.ArrayList;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class EntityBuffer<T> extends AbstractDataBuffer<T> {
    private boolean zaa;
    private ArrayList zab;

    private final void zab() {
        synchronized (this) {
            try {
                if (!this.zaa) {
                    int count = ((DataHolder) Preconditions.checkNotNull(this.f12788a)).getCount();
                    ArrayList arrayList = new ArrayList();
                    this.zab = arrayList;
                    if (count > 0) {
                        arrayList.add(0);
                        String strG = g();
                        String string = this.f12788a.getString(strG, 0, this.f12788a.getWindowIndex(0));
                        for (int i2 = 1; i2 < count; i2++) {
                            int windowIndex = this.f12788a.getWindowIndex(i2);
                            String string2 = this.f12788a.getString(strG, i2, windowIndex);
                            if (string2 == null) {
                                throw new NullPointerException("Missing value for markerColumn: " + strG + ", at row: " + i2 + ", for window: " + windowIndex);
                            }
                            if (!string2.equals(string)) {
                                this.zab.add(Integer.valueOf(i2));
                                string = string2;
                            }
                        }
                    }
                    this.zaa = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    protected String e() {
        return null;
    }

    protected abstract Object f(int i2, int i3);

    protected abstract String g();

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer
    @NonNull
    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public final T get(int i2) {
        int iIntValue;
        int iIntValue2;
        zab();
        int iH = h(i2);
        int i3 = 0;
        if (i2 >= 0 && i2 != this.zab.size()) {
            if (i2 == this.zab.size() - 1) {
                iIntValue = ((DataHolder) Preconditions.checkNotNull(this.f12788a)).getCount();
                iIntValue2 = ((Integer) this.zab.get(i2)).intValue();
            } else {
                iIntValue = ((Integer) this.zab.get(i2 + 1)).intValue();
                iIntValue2 = ((Integer) this.zab.get(i2)).intValue();
            }
            int i4 = iIntValue - iIntValue2;
            if (i4 == 1) {
                int iH2 = h(i2);
                int windowIndex = ((DataHolder) Preconditions.checkNotNull(this.f12788a)).getWindowIndex(iH2);
                String strE = e();
                if (strE == null || this.f12788a.getString(strE, iH2, windowIndex) != null) {
                    i3 = 1;
                }
            } else {
                i3 = i4;
            }
        }
        return (T) f(iH, i3);
    }

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer
    @KeepForSdk
    public int getCount() {
        zab();
        return this.zab.size();
    }

    final int h(int i2) {
        if (i2 >= 0 && i2 < this.zab.size()) {
            return ((Integer) this.zab.get(i2)).intValue();
        }
        throw new IllegalArgumentException("Position " + i2 + " is out of bounds for this buffer");
    }
}
