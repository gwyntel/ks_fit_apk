package b;

import meshprovisioner.states.ProvisioningState;

/* loaded from: classes2.dex */
/* synthetic */ class n {

    /* renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ int[] f7518a;

    static {
        int[] iArr = new int[ProvisioningState.State.values().length];
        f7518a = iArr;
        try {
            iArr[ProvisioningState.State.PROVISIONING_INVITE.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f7518a[ProvisioningState.State.PROVISIONING_CAPABILITIES.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f7518a[ProvisioningState.State.PROVISIONING_START.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f7518a[ProvisioningState.State.PROVISIONING_PUBLIC_KEY.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f7518a[ProvisioningState.State.PROVISINING_INPUT_COMPLETE.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f7518a[ProvisioningState.State.PROVISIONING_CONFIRMATION.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f7518a[ProvisioningState.State.PROVISINING_RANDOM.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            f7518a[ProvisioningState.State.PROVISINING_DATA.ordinal()] = 8;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            f7518a[ProvisioningState.State.PROVISINING_COMPLETE.ordinal()] = 9;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            f7518a[ProvisioningState.State.PROVISINING_FAILED.ordinal()] = 10;
        } catch (NoSuchFieldError unused10) {
        }
    }
}
