package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class MoreObjects {

    public static final class ToStringHelper {
        private final String className;
        private final ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitEmptyValues;
        private boolean omitNullValues;

        private static final class UnconditionalValueHolder extends ValueHolder {
            private UnconditionalValueHolder() {
            }
        }

        static class ValueHolder {

            /* renamed from: a, reason: collision with root package name */
            String f13662a;

            /* renamed from: b, reason: collision with root package name */
            Object f13663b;

            /* renamed from: c, reason: collision with root package name */
            ValueHolder f13664c;

            ValueHolder() {
            }
        }

        private ValueHolder addHolder() {
            ValueHolder valueHolder = new ValueHolder();
            this.holderTail.f13664c = valueHolder;
            this.holderTail = valueHolder;
            return valueHolder;
        }

        private UnconditionalValueHolder addUnconditionalHolder() {
            UnconditionalValueHolder unconditionalValueHolder = new UnconditionalValueHolder();
            this.holderTail.f13664c = unconditionalValueHolder;
            this.holderTail = unconditionalValueHolder;
            return unconditionalValueHolder;
        }

        private static boolean isEmpty(Object obj) {
            return obj instanceof CharSequence ? ((CharSequence) obj).length() == 0 : obj instanceof Collection ? ((Collection) obj).isEmpty() : obj instanceof Map ? ((Map) obj).isEmpty() : obj instanceof Optional ? !((Optional) obj).isPresent() : obj.getClass().isArray() && Array.getLength(obj) == 0;
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, @CheckForNull Object obj) {
            return addHolder(str, obj);
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(@CheckForNull Object obj) {
            return addHolder(obj);
        }

        @CanIgnoreReturnValue
        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0031  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.String toString() {
            /*
                r8 = this;
                r0 = 1
                boolean r1 = r8.omitNullValues
                boolean r2 = r8.omitEmptyValues
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r4 = 32
                r3.<init>(r4)
                java.lang.String r4 = r8.className
                r3.append(r4)
                r4 = 123(0x7b, float:1.72E-43)
                r3.append(r4)
                com.google.common.base.MoreObjects$ToStringHelper$ValueHolder r4 = r8.holderHead
                com.google.common.base.MoreObjects$ToStringHelper$ValueHolder r4 = r4.f13664c
                java.lang.String r5 = ""
            L1c:
                if (r4 == 0) goto L66
                java.lang.Object r6 = r4.f13663b
                boolean r7 = r4 instanceof com.google.common.base.MoreObjects.ToStringHelper.UnconditionalValueHolder
                if (r7 != 0) goto L31
                if (r6 != 0) goto L29
                if (r1 != 0) goto L63
                goto L31
            L29:
                if (r2 == 0) goto L31
                boolean r7 = isEmpty(r6)
                if (r7 != 0) goto L63
            L31:
                r3.append(r5)
                java.lang.String r5 = r4.f13662a
                if (r5 == 0) goto L40
                r3.append(r5)
                r5 = 61
                r3.append(r5)
            L40:
                if (r6 == 0) goto L5e
                java.lang.Class r5 = r6.getClass()
                boolean r5 = r5.isArray()
                if (r5 == 0) goto L5e
                java.lang.Object[] r5 = new java.lang.Object[r0]
                r7 = 0
                r5[r7] = r6
                java.lang.String r5 = java.util.Arrays.deepToString(r5)
                int r6 = r5.length()
                int r6 = r6 - r0
                r3.append(r5, r0, r6)
                goto L61
            L5e:
                r3.append(r6)
            L61:
                java.lang.String r5 = ", "
            L63:
                com.google.common.base.MoreObjects$ToStringHelper$ValueHolder r4 = r4.f13664c
                goto L1c
            L66:
                r0 = 125(0x7d, float:1.75E-43)
                r3.append(r0)
                java.lang.String r0 = r3.toString()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.MoreObjects.ToStringHelper.toString():java.lang.String");
        }

        private ToStringHelper(String str) {
            ValueHolder valueHolder = new ValueHolder();
            this.holderHead = valueHolder;
            this.holderTail = valueHolder;
            this.omitNullValues = false;
            this.omitEmptyValues = false;
            this.className = (String) Preconditions.checkNotNull(str);
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, boolean z2) {
            return addUnconditionalHolder(str, String.valueOf(z2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(boolean z2) {
            return addUnconditionalHolder(String.valueOf(z2));
        }

        @CanIgnoreReturnValue
        private ToStringHelper addHolder(@CheckForNull Object obj) {
            addHolder().f13663b = obj;
            return this;
        }

        @CanIgnoreReturnValue
        private ToStringHelper addUnconditionalHolder(Object obj) {
            addUnconditionalHolder().f13663b = obj;
            return this;
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, char c2) {
            return addUnconditionalHolder(str, String.valueOf(c2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(char c2) {
            return addUnconditionalHolder(String.valueOf(c2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, double d2) {
            return addUnconditionalHolder(str, String.valueOf(d2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(double d2) {
            return addUnconditionalHolder(String.valueOf(d2));
        }

        @CanIgnoreReturnValue
        private ToStringHelper addHolder(String str, @CheckForNull Object obj) {
            ValueHolder valueHolderAddHolder = addHolder();
            valueHolderAddHolder.f13663b = obj;
            valueHolderAddHolder.f13662a = (String) Preconditions.checkNotNull(str);
            return this;
        }

        @CanIgnoreReturnValue
        private ToStringHelper addUnconditionalHolder(String str, Object obj) {
            UnconditionalValueHolder unconditionalValueHolderAddUnconditionalHolder = addUnconditionalHolder();
            unconditionalValueHolderAddUnconditionalHolder.f13663b = obj;
            unconditionalValueHolderAddUnconditionalHolder.f13662a = (String) Preconditions.checkNotNull(str);
            return this;
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, float f2) {
            return addUnconditionalHolder(str, String.valueOf(f2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(float f2) {
            return addUnconditionalHolder(String.valueOf(f2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, int i2) {
            return addUnconditionalHolder(str, String.valueOf(i2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(int i2) {
            return addUnconditionalHolder(String.valueOf(i2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, long j2) {
            return addUnconditionalHolder(str, String.valueOf(j2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(long j2) {
            return addUnconditionalHolder(String.valueOf(j2));
        }
    }

    private MoreObjects() {
    }

    public static <T> T firstNonNull(@CheckForNull T t2, @CheckForNull T t3) {
        if (t2 != null) {
            return t2;
        }
        if (t3 != null) {
            return t3;
        }
        throw new NullPointerException("Both parameters are null");
    }

    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj.getClass().getSimpleName());
    }

    public static ToStringHelper toStringHelper(Class<?> cls) {
        return new ToStringHelper(cls.getSimpleName());
    }

    public static ToStringHelper toStringHelper(String str) {
        return new ToStringHelper(str);
    }
}
