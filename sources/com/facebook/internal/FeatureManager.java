package com.facebook.internal;

import androidx.annotation.RestrictTo;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DiskLruHelper;
import com.facebook.FacebookSdk;
import com.facebook.internal.FetchedAppGateKeepersManager;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public final class FeatureManager {

    /* renamed from: com.facebook.internal.FeatureManager$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$internal$FeatureManager$Feature;

        static {
            int[] iArr = new int[Feature.values().length];
            $SwitchMap$com$facebook$internal$FeatureManager$Feature = iArr;
            try {
                iArr[Feature.RestrictiveDataFiltering.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.Instrument.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.CrashReport.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.ErrorReport.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.AAM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.PrivacyProtection.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.SuggestedEvents.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.PIIFiltering.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.EventDeactivation.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.Core.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.AppEvents.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.CodelessEvents.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.Login.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.Share.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$facebook$internal$FeatureManager$Feature[Feature.Places.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    public interface Callback {
        void onCompleted(boolean z2);
    }

    public enum Feature {
        Unknown(-1),
        Core(0),
        AppEvents(65536),
        CodelessEvents(65792),
        RestrictiveDataFiltering(66048),
        AAM(66304),
        PrivacyProtection(66560),
        SuggestedEvents(66561),
        PIIFiltering(66562),
        EventDeactivation(66816),
        Instrument(131072),
        CrashReport(131328),
        ErrorReport(131584),
        Login(16777216),
        Share(DiskLruHelper.DEFAULT_MAXSIZE),
        Places(50331648);

        private final int code;

        Feature(int i2) {
            this.code = i2;
        }

        static Feature fromInt(int i2) {
            for (Feature feature : values()) {
                if (feature.code == i2) {
                    return feature;
                }
            }
            return Unknown;
        }

        public Feature getParent() {
            int i2 = this.code;
            return (i2 & 255) > 0 ? fromInt(i2 & InputDeviceCompat.SOURCE_ANY) : (65280 & i2) > 0 ? fromInt(i2 & SupportMenu.CATEGORY_MASK) : (16711680 & i2) > 0 ? fromInt(i2 & ViewCompat.MEASURED_STATE_MASK) : fromInt(0);
        }

        @Override // java.lang.Enum
        public String toString() {
            switch (AnonymousClass2.$SwitchMap$com$facebook$internal$FeatureManager$Feature[ordinal()]) {
                case 1:
                    return "RestrictiveDataFiltering";
                case 2:
                    return "Instrument";
                case 3:
                    return "CrashReport";
                case 4:
                    return "ErrorReport";
                case 5:
                    return "AAM";
                case 6:
                    return "PrivacyProtection";
                case 7:
                    return "SuggestedEvents";
                case 8:
                    return "PIIFiltering";
                case 9:
                    return "EventDeactivation";
                case 10:
                    return "CoreKit";
                case 11:
                    return "AppEvents";
                case 12:
                    return "CodelessEvents";
                case 13:
                    return "LoginKit";
                case 14:
                    return "ShareKit";
                case 15:
                    return "PlacesKit";
                default:
                    return "unknown";
            }
        }
    }

    public static void checkFeature(final Feature feature, final Callback callback) {
        FetchedAppGateKeepersManager.loadAppGateKeepersAsync(new FetchedAppGateKeepersManager.Callback() { // from class: com.facebook.internal.FeatureManager.1
            @Override // com.facebook.internal.FetchedAppGateKeepersManager.Callback
            public void onCompleted() {
                callback.onCompleted(FeatureManager.isEnabled(feature));
            }
        });
    }

    private static boolean defaultStatus(Feature feature) {
        switch (AnonymousClass2.$SwitchMap$com$facebook$internal$FeatureManager$Feature[feature.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return false;
            default:
                return true;
        }
    }

    private static boolean getGKStatus(Feature feature) {
        return FetchedAppGateKeepersManager.getGateKeeperForKey("FBSDKFeature" + feature.toString(), FacebookSdk.getApplicationId(), defaultStatus(feature));
    }

    public static boolean isEnabled(Feature feature) {
        if (Feature.Unknown == feature) {
            return false;
        }
        if (Feature.Core == feature) {
            return true;
        }
        Feature parent = feature.getParent();
        return parent == feature ? getGKStatus(feature) : isEnabled(parent) && getGKStatus(feature);
    }
}
