package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@GwtIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class JdkPattern extends CommonPattern implements Serializable {
    private static final long serialVersionUID = 0;
    private final Pattern pattern;

    private static final class JdkMatcher extends CommonMatcher {

        /* renamed from: a, reason: collision with root package name */
        final Matcher f13655a;

        JdkMatcher(Matcher matcher) {
            this.f13655a = (Matcher) Preconditions.checkNotNull(matcher);
        }

        @Override // com.google.common.base.CommonMatcher
        public int end() {
            return this.f13655a.end();
        }

        @Override // com.google.common.base.CommonMatcher
        public boolean find() {
            return this.f13655a.find();
        }

        @Override // com.google.common.base.CommonMatcher
        public boolean matches() {
            return this.f13655a.matches();
        }

        @Override // com.google.common.base.CommonMatcher
        public String replaceAll(String str) {
            return this.f13655a.replaceAll(str);
        }

        @Override // com.google.common.base.CommonMatcher
        public int start() {
            return this.f13655a.start();
        }

        @Override // com.google.common.base.CommonMatcher
        public boolean find(int i2) {
            return this.f13655a.find(i2);
        }
    }

    JdkPattern(Pattern pattern) {
        this.pattern = (Pattern) Preconditions.checkNotNull(pattern);
    }

    @Override // com.google.common.base.CommonPattern
    public int flags() {
        return this.pattern.flags();
    }

    @Override // com.google.common.base.CommonPattern
    public CommonMatcher matcher(CharSequence charSequence) {
        return new JdkMatcher(this.pattern.matcher(charSequence));
    }

    @Override // com.google.common.base.CommonPattern
    public String pattern() {
        return this.pattern.pattern();
    }

    @Override // com.google.common.base.CommonPattern
    public String toString() {
        return this.pattern.toString();
    }
}
