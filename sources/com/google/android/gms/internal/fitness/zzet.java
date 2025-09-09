package com.google.android.gms.internal.fitness;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.BleApi;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.result.BleDevicesResult;

/* loaded from: classes3.dex */
public final class zzet implements BleApi {
    public static final Status zza = new Status(FitnessStatusCodes.UNSUPPORTED_PLATFORM);

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<Status> claimBleDevice(GoogleApiClient googleApiClient, BleDevice bleDevice) {
        return PendingResults.immediatePendingResult(zza, googleApiClient);
    }

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<BleDevicesResult> listClaimedBleDevices(GoogleApiClient googleApiClient) {
        return PendingResults.immediateFailedResult(BleDevicesResult.zza(zza), googleApiClient);
    }

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<Status> startBleScan(GoogleApiClient googleApiClient, StartBleScanRequest startBleScanRequest) {
        return PendingResults.immediatePendingResult(zza, googleApiClient);
    }

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<Status> stopBleScan(GoogleApiClient googleApiClient, BleScanCallback bleScanCallback) {
        return PendingResults.immediatePendingResult(zza, googleApiClient);
    }

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<Status> unclaimBleDevice(GoogleApiClient googleApiClient, BleDevice bleDevice) {
        return PendingResults.immediatePendingResult(zza, googleApiClient);
    }

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<Status> claimBleDevice(GoogleApiClient googleApiClient, String str) {
        return PendingResults.immediatePendingResult(zza, googleApiClient);
    }

    @Override // com.google.android.gms.fitness.BleApi
    public final PendingResult<Status> unclaimBleDevice(GoogleApiClient googleApiClient, String str) {
        return PendingResults.immediatePendingResult(zza, googleApiClient);
    }
}
