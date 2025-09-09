package com.google.android.gms.common.moduleinstall;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import anetwork.channel.util.RequestConstant;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "ModuleInstallResponseCreator")
/* loaded from: classes3.dex */
public class ModuleInstallResponse extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<ModuleInstallResponse> CREATOR = new zad();

    @SafeParcelable.Field(getter = "getSessionId", id = 1)
    private final int zaa;

    @SafeParcelable.Field(defaultValue = RequestConstant.FALSE, getter = "getShouldUnregisterListener", id = 2)
    private final boolean zab;

    @KeepForSdk
    public ModuleInstallResponse(int i2) {
        this(i2, false);
    }

    public boolean areModulesAlreadyInstalled() {
        return this.zaa == 0;
    }

    public int getSessionId() {
        return this.zaa;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, getSessionId());
        SafeParcelWriter.writeBoolean(parcel, 2, this.zab);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final boolean zaa() {
        return this.zab;
    }

    @SafeParcelable.Constructor
    public ModuleInstallResponse(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) boolean z2) {
        this.zaa = i2;
        this.zab = z2;
    }
}
