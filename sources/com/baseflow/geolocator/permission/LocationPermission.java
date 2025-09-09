package com.baseflow.geolocator.permission;

/* loaded from: classes3.dex */
public enum LocationPermission {
    denied,
    deniedForever,
    whileInUse,
    always;

    /* renamed from: com.baseflow.geolocator.permission.LocationPermission$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f12234a;

        static {
            int[] iArr = new int[LocationPermission.values().length];
            f12234a = iArr;
            try {
                iArr[LocationPermission.denied.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f12234a[LocationPermission.deniedForever.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f12234a[LocationPermission.whileInUse.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f12234a[LocationPermission.always.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toInt() {
        int i2 = AnonymousClass1.f12234a[ordinal()];
        if (i2 == 1) {
            return 0;
        }
        if (i2 == 2) {
            return 1;
        }
        if (i2 == 3) {
            return 2;
        }
        if (i2 == 4) {
            return 3;
        }
        throw new IndexOutOfBoundsException();
    }
}
