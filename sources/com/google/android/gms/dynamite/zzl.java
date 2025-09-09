package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

/* loaded from: classes3.dex */
final class zzl implements DynamiteModule.VersionPolicy {
    zzl() {
    }

    @Override // com.google.android.gms.dynamite.DynamiteModule.VersionPolicy
    public final DynamiteModule.VersionPolicy.SelectionResult selectModule(Context context, String str, DynamiteModule.VersionPolicy.IVersions iVersions) throws DynamiteModule.LoadingException {
        int iZzb;
        DynamiteModule.VersionPolicy.SelectionResult selectionResult = new DynamiteModule.VersionPolicy.SelectionResult();
        int iZza = iVersions.zza(context, str);
        selectionResult.localVersion = iZza;
        int i2 = 1;
        int i3 = 0;
        if (iZza != 0) {
            iZzb = iVersions.zzb(context, str, false);
            selectionResult.remoteVersion = iZzb;
        } else {
            iZzb = iVersions.zzb(context, str, true);
            selectionResult.remoteVersion = iZzb;
        }
        int i4 = selectionResult.localVersion;
        if (i4 == 0) {
            if (iZzb == 0) {
                i2 = 0;
            }
            selectionResult.selection = i2;
            return selectionResult;
        }
        i3 = i4;
        if (iZzb < i3) {
            i2 = -1;
        }
        selectionResult.selection = i2;
        return selectionResult;
    }
}
