package com.google.protobuf.util;

import androidx.media3.common.C;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CompileTimeConstant;
import com.google.protobuf.Duration;
import com.luck.picture.lib.config.FileSizeUnit;
import com.xiaomi.mipush.sdk.Constants;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Comparator;

/* loaded from: classes2.dex */
public final class Durations {
    private static final long SECONDS_PER_DAY = 86400;
    private static final long SECONDS_PER_HOUR = 3600;
    private static final long SECONDS_PER_MINUTE = 60;
    public static final Duration MIN_VALUE = Duration.newBuilder().setSeconds(-315576000000L).setNanos(-999999999).build();
    public static final Duration MAX_VALUE = Duration.newBuilder().setSeconds(315576000000L).setNanos(999999999).build();
    public static final Duration ZERO = Duration.newBuilder().setSeconds(0).setNanos(0).build();

    private enum DurationComparator implements Comparator<Duration>, Serializable {
        INSTANCE;

        @Override // java.util.Comparator
        public int compare(Duration duration, Duration duration2) {
            Durations.checkValid(duration);
            Durations.checkValid(duration2);
            int iCompare = Long.compare(duration.getSeconds(), duration2.getSeconds());
            return iCompare != 0 ? iCompare : Integer.compare(duration.getNanos(), duration2.getNanos());
        }
    }

    private Durations() {
    }

    static Duration a(long j2, int i2) {
        if (i2 <= -1000000000 || i2 >= 1000000000) {
            j2 = LongMath.checkedAdd(j2, i2 / FileSizeUnit.ACCURATE_GB);
            i2 %= FileSizeUnit.ACCURATE_GB;
        }
        if (j2 > 0 && i2 < 0) {
            i2 += FileSizeUnit.ACCURATE_GB;
            j2--;
        }
        if (j2 < 0 && i2 > 0) {
            i2 -= FileSizeUnit.ACCURATE_GB;
            j2++;
        }
        return checkValid(Duration.newBuilder().setSeconds(j2).setNanos(i2).build());
    }

    public static Duration add(Duration duration, Duration duration2) {
        checkValid(duration);
        checkValid(duration2);
        return a(LongMath.checkedAdd(duration.getSeconds(), duration2.getSeconds()), IntMath.checkedAdd(duration.getNanos(), duration2.getNanos()));
    }

    @CanIgnoreReturnValue
    public static Duration checkNotNegative(Duration duration) {
        Preconditions.checkArgument(!isNegative(duration), "duration (%s) must not be negative", toString(duration));
        return duration;
    }

    @CanIgnoreReturnValue
    public static Duration checkPositive(Duration duration) {
        Preconditions.checkArgument(isPositive(duration), "duration (%s) must be positive", toString(duration));
        return duration;
    }

    @CanIgnoreReturnValue
    public static Duration checkValid(Duration duration) {
        long seconds = duration.getSeconds();
        int nanos = duration.getNanos();
        if (isValid(seconds, nanos)) {
            return duration;
        }
        throw new IllegalArgumentException(String.format("Duration is not valid. See proto definition for valid values. Seconds (%s) must be in range [-315,576,000,000, +315,576,000,000]. Nanos (%s) must be in range [-999,999,999, +999,999,999]. Nanos must have the same sign as seconds", Long.valueOf(seconds), Integer.valueOf(nanos)));
    }

    public static Comparator<Duration> comparator() {
        return DurationComparator.INSTANCE;
    }

    public static int compare(Duration duration, Duration duration2) {
        return DurationComparator.INSTANCE.compare(duration, duration2);
    }

    public static Duration fromDays(long j2) {
        return Duration.newBuilder().setSeconds(LongMath.checkedMultiply(j2, 86400L)).setNanos(0).build();
    }

    public static Duration fromHours(long j2) {
        return Duration.newBuilder().setSeconds(LongMath.checkedMultiply(j2, SECONDS_PER_HOUR)).setNanos(0).build();
    }

    public static Duration fromMicros(long j2) {
        return a(j2 / 1000000, (int) ((j2 % 1000000) * 1000));
    }

    public static Duration fromMillis(long j2) {
        return a(j2 / 1000, (int) ((j2 % 1000) * 1000000));
    }

    public static Duration fromMinutes(long j2) {
        return Duration.newBuilder().setSeconds(LongMath.checkedMultiply(j2, 60L)).setNanos(0).build();
    }

    public static Duration fromNanos(long j2) {
        return a(j2 / C.NANOS_PER_SECOND, (int) (j2 % C.NANOS_PER_SECOND));
    }

    public static Duration fromSeconds(long j2) {
        return a(j2, 0);
    }

    public static boolean isNegative(Duration duration) {
        checkValid(duration);
        if (duration.getSeconds() == 0) {
            if (duration.getNanos() >= 0) {
                return false;
            }
        } else if (duration.getSeconds() >= 0) {
            return false;
        }
        return true;
    }

    public static boolean isPositive(Duration duration) {
        checkValid(duration);
        return (isNegative(duration) || duration.equals(ZERO)) ? false : true;
    }

    public static boolean isValid(long j2, int i2) {
        if (j2 >= -315576000000L && j2 <= 315576000000L && i2 >= -999999999 && i2 < 1000000000) {
            if (j2 >= 0 && i2 >= 0) {
                return true;
            }
            if (j2 <= 0 && i2 <= 0) {
                return true;
            }
        }
        return false;
    }

    public static Duration parse(String str) throws NumberFormatException, ParseException {
        boolean z2;
        String strSubstring;
        if (str.isEmpty() || str.charAt(str.length() - 1) != 's') {
            throw new ParseException("Invalid duration string: " + str, 0);
        }
        if (str.charAt(0) == '-') {
            str = str.substring(1);
            z2 = true;
        } else {
            z2 = false;
        }
        String strSubstring2 = str.substring(0, str.length() - 1);
        int iIndexOf = strSubstring2.indexOf(46);
        if (iIndexOf != -1) {
            strSubstring = strSubstring2.substring(iIndexOf + 1);
            strSubstring2 = strSubstring2.substring(0, iIndexOf);
        } else {
            strSubstring = "";
        }
        long j2 = Long.parseLong(strSubstring2);
        int iD = strSubstring.isEmpty() ? 0 : Timestamps.d(strSubstring);
        if (j2 < 0) {
            throw new ParseException("Invalid duration string: " + str, 0);
        }
        if (z2) {
            j2 = -j2;
            iD = -iD;
        }
        try {
            return a(j2, iD);
        } catch (IllegalArgumentException e2) {
            ParseException parseException = new ParseException("Duration value is out of range.", 0);
            parseException.initCause(e2);
            throw parseException;
        }
    }

    public static Duration parseUnchecked(@CompileTimeConstant String str) {
        try {
            return parse(str);
        } catch (ParseException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public static Duration subtract(Duration duration, Duration duration2) {
        checkValid(duration);
        checkValid(duration2);
        return a(LongMath.checkedSubtract(duration.getSeconds(), duration2.getSeconds()), IntMath.checkedSubtract(duration.getNanos(), duration2.getNanos()));
    }

    public static long toDays(Duration duration) {
        return checkValid(duration).getSeconds() / 86400;
    }

    public static long toHours(Duration duration) {
        return checkValid(duration).getSeconds() / SECONDS_PER_HOUR;
    }

    public static long toMicros(Duration duration) {
        checkValid(duration);
        return LongMath.checkedAdd(LongMath.checkedMultiply(duration.getSeconds(), 1000000L), duration.getNanos() / 1000);
    }

    public static long toMillis(Duration duration) {
        checkValid(duration);
        return LongMath.checkedAdd(LongMath.checkedMultiply(duration.getSeconds(), 1000L), duration.getNanos() / 1000000);
    }

    public static long toMinutes(Duration duration) {
        return checkValid(duration).getSeconds() / 60;
    }

    public static long toNanos(Duration duration) {
        checkValid(duration);
        return LongMath.checkedAdd(LongMath.checkedMultiply(duration.getSeconds(), C.NANOS_PER_SECOND), duration.getNanos());
    }

    public static long toSeconds(Duration duration) {
        return checkValid(duration).getSeconds();
    }

    public static double toSecondsAsDouble(Duration duration) {
        checkValid(duration);
        return duration.getSeconds() + (duration.getNanos() / 1.0E9d);
    }

    public static String toString(Duration duration) {
        checkValid(duration);
        long seconds = duration.getSeconds();
        int nanos = duration.getNanos();
        StringBuilder sb = new StringBuilder();
        if (seconds < 0 || nanos < 0) {
            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            seconds = -seconds;
            nanos = -nanos;
        }
        sb.append(seconds);
        if (nanos != 0) {
            sb.append(".");
            sb.append(Timestamps.b(nanos));
        }
        sb.append("s");
        return sb.toString();
    }

    public static boolean isValid(Duration duration) {
        return isValid(duration.getSeconds(), duration.getNanos());
    }

    public static Duration checkValid(Duration.Builder builder) {
        return checkValid(builder.build());
    }
}
