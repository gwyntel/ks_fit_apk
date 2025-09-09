package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.regex.Pattern;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class Platform {
    private static final PatternCompiler patternCompiler = loadPatternCompiler();

    private static final class JdkPatternCompiler implements PatternCompiler {
        private JdkPatternCompiler() {
        }

        @Override // com.google.common.base.PatternCompiler
        public CommonPattern compile(String str) {
            return new JdkPattern(Pattern.compile(str));
        }

        @Override // com.google.common.base.PatternCompiler
        public boolean isPcreLike() {
            return true;
        }
    }

    private Platform() {
    }

    static CommonPattern a(String str) {
        Preconditions.checkNotNull(str);
        return patternCompiler.compile(str);
    }

    static String b(String str) {
        if (h(str)) {
            return null;
        }
        return str;
    }

    static String c(double d2) {
        return String.format(Locale.ROOT, "%.4g", Double.valueOf(d2));
    }

    static Optional d(Class cls, String str) {
        WeakReference weakReference = (WeakReference) Enums.a(cls).get(str);
        return weakReference == null ? Optional.absent() : Optional.fromNullable((Enum) cls.cast(weakReference.get()));
    }

    static String e(String str) {
        return str == null ? "" : str;
    }

    static boolean f() {
        return patternCompiler.isPcreLike();
    }

    static CharMatcher g(CharMatcher charMatcher) {
        return charMatcher.b();
    }

    static boolean h(String str) {
        return str == null || str.isEmpty();
    }

    private static PatternCompiler loadPatternCompiler() {
        return new JdkPatternCompiler();
    }
}
