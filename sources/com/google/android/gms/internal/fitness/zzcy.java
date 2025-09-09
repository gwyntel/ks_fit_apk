package com.google.android.gms.internal.fitness;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.fitness.BleApi;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.result.BleDevicesResult;

/* loaded from: classes3.dex */
public final class zzcy implements BleApi {
    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<Status> claimBleDevice(GoogleApiClient googleApiClient, BleDevice bleDevice) {
        return googleApiClient.execute(new zzct(this, googleApiClient, bleDevice));
    }

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<BleDevicesResult> listClaimedBleDevices(GoogleApiClient googleApiClient) {
        return googleApiClient.enqueue(new zzcv(this, googleApiClient));
    }

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<Status> startBleScan(GoogleApiClient googleApiClient, StartBleScanRequest startBleScanRequest) {
        return googleApiClient.enqueue(new zzcq(this, googleApiClient, startBleScanRequest, com.google.android.gms.fitness.request.zzc.zza().zzc((BleScanCallback) Preconditions.checkNotNull(startBleScanRequest.zza()), googleApiClient.getLooper())));
    }

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<Status> stopBleScan(GoogleApiClient googleApiClient, BleScanCallback bleScanCallback) {
        com.google.android.gms.fitness.request.zze zzeVarZze = com.google.android.gms.fitness.request.zzc.zza().zze(bleScanCallback, googleApiClient.getLooper());
        return zzeVarZze == null ? PendingResults.immediatePendingResult(Status.RESULT_SUCCESS, googleApiClient) : googleApiClient.enqueue(new zzcr(this, googleApiClient, zzeVarZze));
    }

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<Status> unclaimBleDevice(GoogleApiClient googleApiClient, BleDevice bleDevice) {
        return unclaimBleDevice(googleApiClient, bleDevice.getAddress());
    }

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<Status> claimBleDevice(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.execute(new zzcs(this, googleApiClient, str));
    }

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<Status> unclaimBleDevice(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.execute(new zzcu(this, googleApiClient, str));
    }
}
