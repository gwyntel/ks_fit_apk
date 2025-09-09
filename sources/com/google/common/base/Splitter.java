package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Splitter {
    private final int limit;
    private final boolean omitEmptyStrings;
    private final Strategy strategy;
    private final CharMatcher trimmer;

    public static final class MapSplitter {
        private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
        private final Splitter entrySplitter;
        private final Splitter outerSplitter;

        public Map<String, String> split(CharSequence charSequence) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (String str : this.outerSplitter.split(charSequence)) {
                Iterator itSplittingIterator = this.entrySplitter.splittingIterator(str);
                Preconditions.checkArgument(itSplittingIterator.hasNext(), INVALID_ENTRY_MESSAGE, str);
                String str2 = (String) itSplittingIterator.next();
                Preconditions.checkArgument(!linkedHashMap.containsKey(str2), "Duplicate key [%s] found.", str2);
                Preconditions.checkArgument(itSplittingIterator.hasNext(), INVALID_ENTRY_MESSAGE, str);
                linkedHashMap.put(str2, (String) itSplittingIterator.next());
                Preconditions.checkArgument(!itSplittingIterator.hasNext(), INVALID_ENTRY_MESSAGE, str);
            }
            return Collections.unmodifiableMap(linkedHashMap);
        }

        private MapSplitter(Splitter splitter, Splitter splitter2) {
            this.outerSplitter = splitter;
            this.entrySplitter = (Splitter) Preconditions.checkNotNull(splitter2);
        }
    }

    private static abstract class SplittingIterator extends AbstractIterator<String> {

        /* renamed from: a, reason: collision with root package name */
        final CharSequence f13680a;

        /* renamed from: b, reason: collision with root package name */
        final CharMatcher f13681b;

        /* renamed from: c, reason: collision with root package name */
        final boolean f13682c;

        /* renamed from: d, reason: collision with root package name */
        int f13683d = 0;

        /* renamed from: e, reason: collision with root package name */
        int f13684e;

        protected SplittingIterator(Splitter splitter, CharSequence charSequence) {
            this.f13681b = splitter.trimmer;
            this.f13682c = splitter.omitEmptyStrings;
            this.f13684e = splitter.limit;
            this.f13680a = charSequence;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.base.AbstractIterator
        /* renamed from: c, reason: merged with bridge method [inline-methods] */
        public String a() {
            int iSeparatorStart;
            int i2 = this.f13683d;
            while (true) {
                int i3 = this.f13683d;
                if (i3 == -1) {
                    return (String) b();
                }
                iSeparatorStart = separatorStart(i3);
                if (iSeparatorStart == -1) {
                    iSeparatorStart = this.f13680a.length();
                    this.f13683d = -1;
                } else {
                    this.f13683d = separatorEnd(iSeparatorStart);
                }
                int i4 = this.f13683d;
                if (i4 == i2) {
                    int i5 = i4 + 1;
                    this.f13683d = i5;
                    if (i5 > this.f13680a.length()) {
                        this.f13683d = -1;
                    }
                } else {
                    while (i2 < iSeparatorStart && this.f13681b.matches(this.f13680a.charAt(i2))) {
                        i2++;
                    }
                    while (iSeparatorStart > i2 && this.f13681b.matches(this.f13680a.charAt(iSeparatorStart - 1))) {
                        iSeparatorStart--;
                    }
                    if (!this.f13682c || i2 != iSeparatorStart) {
                        break;
                    }
                    i2 = this.f13683d;
                }
            }
            int i6 = this.f13684e;
            if (i6 == 1) {
                iSeparatorStart = this.f13680a.length();
                this.f13683d = -1;
                while (iSeparatorStart > i2 && this.f13681b.matches(this.f13680a.charAt(iSeparatorStart - 1))) {
                    iSeparatorStart--;
                }
            } else {
                this.f13684e = i6 - 1;
            }
            return this.f13680a.subSequence(i2, iSeparatorStart).toString();
        }

        abstract int separatorEnd(int i2);

        abstract int separatorStart(int i2);
    }

    private interface Strategy {
        Iterator<String> iterator(Splitter splitter, CharSequence charSequence);
    }

    private Splitter(Strategy strategy) {
        this(strategy, false, CharMatcher.none(), Integer.MAX_VALUE);
    }

    static Splitter e(final CommonPattern commonPattern) {
        Preconditions.checkArgument(!commonPattern.matcher("").matches(), "The pattern may not match the empty string: %s", commonPattern);
        return new Splitter(new Strategy() { // from class: com.google.common.base.Splitter.3
            @Override // com.google.common.base.Splitter.Strategy
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                final CommonMatcher commonMatcherMatcher = commonPattern.matcher(charSequence);
                return new SplittingIterator(this, splitter, charSequence) { // from class: com.google.common.base.Splitter.3.1

                    /* renamed from: g, reason: collision with root package name */
                    final /* synthetic */ AnonymousClass3 f13675g;

                    {
                        this.f13675g = this;
                    }

                    @Override // com.google.common.base.Splitter.SplittingIterator
                    public int separatorEnd(int i2) {
                        return commonMatcherMatcher.end();
                    }

                    @Override // com.google.common.base.Splitter.SplittingIterator
                    public int separatorStart(int i2) {
                        if (commonMatcherMatcher.find(i2)) {
                            return commonMatcherMatcher.start();
                        }
                        return -1;
                    }
                };
            }
        });
    }

    public static Splitter fixedLength(final int i2) {
        Preconditions.checkArgument(i2 > 0, "The length may not be less than 1");
        return new Splitter(new Strategy() { // from class: com.google.common.base.Splitter.4
            @Override // com.google.common.base.Splitter.Strategy
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) { // from class: com.google.common.base.Splitter.4.1
                    @Override // com.google.common.base.Splitter.SplittingIterator
                    public int separatorEnd(int i3) {
                        return i3;
                    }

                    @Override // com.google.common.base.Splitter.SplittingIterator
                    public int separatorStart(int i3) {
                        int i4 = i3 + i2;
                        if (i4 < this.f13680a.length()) {
                            return i4;
                        }
                        return -1;
                    }
                };
            }
        });
    }

    public static Splitter on(char c2) {
        return on(CharMatcher.is(c2));
    }

    @GwtIncompatible
    public static Splitter onPattern(String str) {
        return e(Platform.a(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Iterator<String> splittingIterator(CharSequence charSequence) {
        return this.strategy.iterator(this, charSequence);
    }

    public Splitter limit(int i2) {
        Preconditions.checkArgument(i2 > 0, "must be greater than zero: %s", i2);
        return new Splitter(this.strategy, this.omitEmptyStrings, this.trimmer, i2);
    }

    public Splitter omitEmptyStrings() {
        return new Splitter(this.strategy, true, this.trimmer, this.limit);
    }

    public Iterable<String> split(final CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        return new Iterable<String>(this) { // from class: com.google.common.base.Splitter.5

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ Splitter f13679b;

            {
                this.f13679b = this;
            }

            @Override // java.lang.Iterable
            public Iterator<String> iterator() {
                return this.f13679b.splittingIterator(charSequence);
            }

            public String toString() {
                Joiner joinerOn = Joiner.on(", ");
                StringBuilder sb = new StringBuilder();
                sb.append('[');
                StringBuilder sbAppendTo = joinerOn.appendTo(sb, (Iterable<? extends Object>) this);
                sbAppendTo.append(']');
                return sbAppendTo.toString();
            }
        };
    }

    public List<String> splitToList(CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        Iterator<String> itSplittingIterator = splittingIterator(charSequence);
        ArrayList arrayList = new ArrayList();
        while (itSplittingIterator.hasNext()) {
            arrayList.add(itSplittingIterator.next());
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Splitter trimResults() {
        return trimResults(CharMatcher.whitespace());
    }

    public MapSplitter withKeyValueSeparator(String str) {
        return withKeyValueSeparator(on(str));
    }

    private Splitter(Strategy strategy, boolean z2, CharMatcher charMatcher, int i2) {
        this.strategy = strategy;
        this.omitEmptyStrings = z2;
        this.trimmer = charMatcher;
        this.limit = i2;
    }

    public static Splitter on(final CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(new Strategy() { // from class: com.google.common.base.Splitter.1
            @Override // com.google.common.base.Splitter.Strategy
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) { // from class: com.google.common.base.Splitter.1.1
                    @Override // com.google.common.base.Splitter.SplittingIterator
                    int separatorEnd(int i2) {
                        return i2 + 1;
                    }

                    @Override // com.google.common.base.Splitter.SplittingIterator
                    int separatorStart(int i2) {
                        return charMatcher.indexIn(this.f13680a, i2);
                    }
                };
            }
        });
    }

    public Splitter trimResults(CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(this.strategy, this.omitEmptyStrings, charMatcher, this.limit);
    }

    public MapSplitter withKeyValueSeparator(char c2) {
        return withKeyValueSeparator(on(c2));
    }

    public MapSplitter withKeyValueSeparator(Splitter splitter) {
        return new MapSplitter(splitter);
    }

    public static Splitter on(final String str) {
        Preconditions.checkArgument(str.length() != 0, "The separator may not be the empty string.");
        if (str.length() == 1) {
            return on(str.charAt(0));
        }
        return new Splitter(new Strategy() { // from class: com.google.common.base.Splitter.2
            @Override // com.google.common.base.Splitter.Strategy
            public SplittingIterator iterator(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) { // from class: com.google.common.base.Splitter.2.1
                    @Override // com.google.common.base.Splitter.SplittingIterator
                    public int separatorEnd(int i2) {
                        return i2 + str.length();
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:8:0x0026, code lost:
                    
                        r6 = r6 + 1;
                     */
                    @Override // com.google.common.base.Splitter.SplittingIterator
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public int separatorStart(int r6) {
                        /*
                            r5 = this;
                            com.google.common.base.Splitter$2 r0 = com.google.common.base.Splitter.AnonymousClass2.this
                            java.lang.String r0 = r1
                            int r0 = r0.length()
                            java.lang.CharSequence r1 = r5.f13680a
                            int r1 = r1.length()
                            int r1 = r1 - r0
                        Lf:
                            if (r6 > r1) goto L2d
                            r2 = 0
                        L12:
                            if (r2 >= r0) goto L2c
                            java.lang.CharSequence r3 = r5.f13680a
                            int r4 = r2 + r6
                            char r3 = r3.charAt(r4)
                            com.google.common.base.Splitter$2 r4 = com.google.common.base.Splitter.AnonymousClass2.this
                            java.lang.String r4 = r1
                            char r4 = r4.charAt(r2)
                            if (r3 == r4) goto L29
                            int r6 = r6 + 1
                            goto Lf
                        L29:
                            int r2 = r2 + 1
                            goto L12
                        L2c:
                            return r6
                        L2d:
                            r6 = -1
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Splitter.AnonymousClass2.AnonymousClass1.separatorStart(int):int");
                    }
                };
            }
        });
    }

    @GwtIncompatible
    public static Splitter on(Pattern pattern) {
        return e(new JdkPattern(pattern));
    }
}
