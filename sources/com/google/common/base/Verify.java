package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Verify {
    private Verify() {
    }

    public static void verify(boolean z2) {
        if (!z2) {
            throw new VerifyException();
        }
    }

    @CanIgnoreReturnValue
    public static <T> T verifyNotNull(@CheckForNull T t2) {
        return (T) verifyNotNull(t2, "expected a non-null reference", new Object[0]);
    }

    public static void verify(boolean z2, String str, @CheckForNull Object... objArr) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, objArr));
        }
    }

    @CanIgnoreReturnValue
    public static <T> T verifyNotNull(@CheckForNull T t2, String str, @CheckForNull Object... objArr) {
        if (t2 != null) {
            return t2;
        }
        throw new VerifyException(Strings.lenientFormat(str, objArr));
    }

    public static void verify(boolean z2, String str, char c2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Character.valueOf(c2)));
        }
    }

    public static void verify(boolean z2, String str, int i2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Integer.valueOf(i2)));
        }
    }

    public static void verify(boolean z2, String str, long j2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Long.valueOf(j2)));
        }
    }

    public static void verify(boolean z2, String str, @CheckForNull Object obj) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj));
        }
    }

    public static void verify(boolean z2, String str, char c2, char c3) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Character.valueOf(c2), Character.valueOf(c3)));
        }
    }

    public static void verify(boolean z2, String str, int i2, char c2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Integer.valueOf(i2), Character.valueOf(c2)));
        }
    }

    public static void verify(boolean z2, String str, long j2, char c2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Long.valueOf(j2), Character.valueOf(c2)));
        }
    }

    public static void verify(boolean z2, String str, @CheckForNull Object obj, char c2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj, Character.valueOf(c2)));
        }
    }

    public static void verify(boolean z2, String str, char c2, int i2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Character.valueOf(c2), Integer.valueOf(i2)));
        }
    }

    public static void verify(boolean z2, String str, int i2, int i3) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Integer.valueOf(i2), Integer.valueOf(i3)));
        }
    }

    public static void verify(boolean z2, String str, long j2, int i2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Long.valueOf(j2), Integer.valueOf(i2)));
        }
    }

    public static void verify(boolean z2, String str, @CheckForNull Object obj, int i2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj, Integer.valueOf(i2)));
        }
    }

    public static void verify(boolean z2, String str, char c2, long j2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Character.valueOf(c2), Long.valueOf(j2)));
        }
    }

    public static void verify(boolean z2, String str, int i2, long j2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Integer.valueOf(i2), Long.valueOf(j2)));
        }
    }

    public static void verify(boolean z2, String str, long j2, long j3) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Long.valueOf(j2), Long.valueOf(j3)));
        }
    }

    public static void verify(boolean z2, String str, @CheckForNull Object obj, long j2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj, Long.valueOf(j2)));
        }
    }

    public static void verify(boolean z2, String str, char c2, @CheckForNull Object obj) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Character.valueOf(c2), obj));
        }
    }

    public static void verify(boolean z2, String str, int i2, @CheckForNull Object obj) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Integer.valueOf(i2), obj));
        }
    }

    public static void verify(boolean z2, String str, long j2, @CheckForNull Object obj) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Long.valueOf(j2), obj));
        }
    }

    public static void verify(boolean z2, String str, @CheckForNull Object obj, @CheckForNull Object obj2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj, obj2));
        }
    }

    public static void verify(boolean z2, String str, @CheckForNull Object obj, @CheckForNull Object obj2, @CheckForNull Object obj3) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj, obj2, obj3));
        }
    }

    public static void verify(boolean z2, String str, @CheckForNull Object obj, @CheckForNull Object obj2, @CheckForNull Object obj3, @CheckForNull Object obj4) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj, obj2, obj3, obj4));
        }
    }
}
