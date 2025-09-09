package com.afollestad.materialdialogs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import androidx.core.view.GravityCompat;

/* loaded from: classes2.dex */
public enum GravityEnum {
    START,
    CENTER,
    END;

    private static final boolean HAS_RTL = true;

    /* renamed from: com.afollestad.materialdialogs.GravityEnum$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f8164a;

        static {
            int[] iArr = new int[GravityEnum.values().length];
            f8164a = iArr;
            try {
                iArr[GravityEnum.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f8164a[GravityEnum.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f8164a[GravityEnum.END.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @SuppressLint({"RtlHardcoded"})
    public int getGravityInt() {
        int i2 = AnonymousClass1.f8164a[ordinal()];
        if (i2 == 1) {
            if (HAS_RTL) {
                return GravityCompat.START;
            }
            return 3;
        }
        if (i2 == 2) {
            return 1;
        }
        if (i2 != 3) {
            throw new IllegalStateException("Invalid gravity constant");
        }
        if (HAS_RTL) {
            return GravityCompat.END;
        }
        return 5;
    }

    @TargetApi(17)
    public int getTextAlignment() {
        int i2 = AnonymousClass1.f8164a[ordinal()];
        if (i2 != 2) {
            return i2 != 3 ? 5 : 6;
        }
        return 4;
    }
}
