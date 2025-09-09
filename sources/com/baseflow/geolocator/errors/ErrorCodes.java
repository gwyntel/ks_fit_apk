package com.baseflow.geolocator.errors;

/* loaded from: classes3.dex */
public enum ErrorCodes {
    activityMissing,
    errorWhileAcquiringPosition,
    locationServicesDisabled,
    permissionDefinitionsNotFound,
    permissionDenied,
    permissionRequestInProgress;

    /* renamed from: com.baseflow.geolocator.errors.ErrorCodes$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f12212a;

        static {
            int[] iArr = new int[ErrorCodes.values().length];
            f12212a = iArr;
            try {
                iArr[ErrorCodes.activityMissing.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f12212a[ErrorCodes.errorWhileAcquiringPosition.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f12212a[ErrorCodes.locationServicesDisabled.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f12212a[ErrorCodes.permissionDefinitionsNotFound.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f12212a[ErrorCodes.permissionDenied.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f12212a[ErrorCodes.permissionRequestInProgress.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public String toDescription() {
        switch (AnonymousClass1.f12212a[ordinal()]) {
            case 1:
                return "Activity is missing. This might happen when running a certain function from the background that requires a UI element (e.g. requesting permissions or enabling the location services).";
            case 2:
                return "An unexpected error occurred while trying to acquire the device's position.";
            case 3:
                return "Location services are disabled. To receive location updates the location services should be enabled.";
            case 4:
                return "No location permissions are defined in the manifest. Make sure at least ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION are defined in the manifest.";
            case 5:
                return "User denied permissions to access the device's location.";
            case 6:
                return "Already listening for location updates. If you want to restart listening please cancel other subscriptions first";
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    @Override // java.lang.Enum
    public String toString() {
        switch (AnonymousClass1.f12212a[ordinal()]) {
            case 1:
                return "ACTIVITY_MISSING";
            case 2:
                return "ERROR_WHILE_ACQUIRING_POSITION";
            case 3:
                return "LOCATION_SERVICES_DISABLED";
            case 4:
                return "PERMISSION_DEFINITIONS_NOT_FOUND";
            case 5:
                return "PERMISSION_DENIED";
            case 6:
                return "PERMISSION_REQUEST_IN_PROGRESS";
            default:
                throw new IndexOutOfBoundsException();
        }
    }
}
