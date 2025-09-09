package org.apache.commons.lang.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/* loaded from: classes5.dex */
public class StrTokenizer implements ListIterator, Cloneable {
    private static final StrTokenizer CSV_TOKENIZER_PROTOTYPE;
    private static final StrTokenizer TSV_TOKENIZER_PROTOTYPE;
    private char[] chars;
    private StrMatcher delimMatcher;
    private boolean emptyAsNull;
    private boolean ignoreEmptyTokens;
    private StrMatcher ignoredMatcher;
    private StrMatcher quoteMatcher;
    private int tokenPos;
    private String[] tokens;
    private StrMatcher trimmerMatcher;

    static {
        StrTokenizer strTokenizer = new StrTokenizer();
        CSV_TOKENIZER_PROTOTYPE = strTokenizer;
        strTokenizer.setDelimiterMatcher(StrMatcher.commaMatcher());
        strTokenizer.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        strTokenizer.setIgnoredMatcher(StrMatcher.noneMatcher());
        strTokenizer.setTrimmerMatcher(StrMatcher.trimMatcher());
        strTokenizer.setEmptyTokenAsNull(false);
        strTokenizer.setIgnoreEmptyTokens(false);
        StrTokenizer strTokenizer2 = new StrTokenizer();
        TSV_TOKENIZER_PROTOTYPE = strTokenizer2;
        strTokenizer2.setDelimiterMatcher(StrMatcher.tabMatcher());
        strTokenizer2.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        strTokenizer2.setIgnoredMatcher(StrMatcher.noneMatcher());
        strTokenizer2.setTrimmerMatcher(StrMatcher.trimMatcher());
        strTokenizer2.setEmptyTokenAsNull(false);
        strTokenizer2.setIgnoreEmptyTokens(false);
    }

    public StrTokenizer() {
        this.delimMatcher = StrMatcher.splitMatcher();
        this.quoteMatcher = StrMatcher.noneMatcher();
        this.ignoredMatcher = StrMatcher.noneMatcher();
        this.trimmerMatcher = StrMatcher.noneMatcher();
        this.emptyAsNull = false;
        this.ignoreEmptyTokens = true;
        this.chars = null;
    }

    private void addToken(List list, String str) {
        if (str == null || str.length() == 0) {
            if (isIgnoreEmptyTokens()) {
                return;
            }
            if (isEmptyTokenAsNull()) {
                str = null;
            }
        }
        list.add(str);
    }

    private void checkTokenized() {
        if (this.tokens == null) {
            char[] cArr = this.chars;
            if (cArr == null) {
                List listB = b(null, 0, 0);
                this.tokens = (String[]) listB.toArray(new String[listB.size()]);
            } else {
                List listB2 = b(cArr, 0, cArr.length);
                this.tokens = (String[]) listB2.toArray(new String[listB2.size()]);
            }
        }
    }

    private static StrTokenizer getCSVClone() {
        return (StrTokenizer) CSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getCSVInstance() {
        return getCSVClone();
    }

    private static StrTokenizer getTSVClone() {
        return (StrTokenizer) TSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getTSVInstance() {
        return getTSVClone();
    }

    private boolean isQuote(char[] cArr, int i2, int i3, int i4, int i5) {
        for (int i6 = 0; i6 < i5; i6++) {
            int i7 = i2 + i6;
            if (i7 >= i3 || cArr[i7] != cArr[i4 + i6]) {
                return false;
            }
        }
        return true;
    }

    private int readNextToken(char[] cArr, int i2, int i3, StrBuilder strBuilder, List list) {
        while (i2 < i3) {
            int iMax = Math.max(getIgnoredMatcher().isMatch(cArr, i2, i2, i3), getTrimmerMatcher().isMatch(cArr, i2, i2, i3));
            if (iMax == 0 || getDelimiterMatcher().isMatch(cArr, i2, i2, i3) > 0 || getQuoteMatcher().isMatch(cArr, i2, i2, i3) > 0) {
                break;
            }
            i2 += iMax;
        }
        if (i2 >= i3) {
            addToken(list, "");
            return -1;
        }
        int iIsMatch = getDelimiterMatcher().isMatch(cArr, i2, i2, i3);
        if (iIsMatch > 0) {
            addToken(list, "");
            return i2 + iIsMatch;
        }
        int iIsMatch2 = getQuoteMatcher().isMatch(cArr, i2, i2, i3);
        return iIsMatch2 > 0 ? readWithQuotes(cArr, i2 + iIsMatch2, i3, strBuilder, list, i2, iIsMatch2) : readWithQuotes(cArr, i2, i3, strBuilder, list, 0, 0);
    }

    private int readWithQuotes(char[] cArr, int i2, int i3, StrBuilder strBuilder, List list, int i4, int i5) {
        strBuilder.clear();
        boolean z2 = i5 > 0;
        int i6 = i2;
        int size = 0;
        while (i6 < i3) {
            if (z2) {
                int i7 = size;
                int i8 = i6;
                if (isQuote(cArr, i6, i3, i4, i5)) {
                    int i9 = i8 + i5;
                    if (isQuote(cArr, i9, i3, i4, i5)) {
                        strBuilder.append(cArr, i8, i5);
                        i6 = i8 + (i5 * 2);
                        size = strBuilder.size();
                    } else {
                        size = i7;
                        i6 = i9;
                        z2 = false;
                    }
                } else {
                    i6 = i8 + 1;
                    strBuilder.append(cArr[i8]);
                    size = strBuilder.size();
                }
            } else {
                int i10 = size;
                int i11 = i6;
                int iIsMatch = getDelimiterMatcher().isMatch(cArr, i11, i2, i3);
                if (iIsMatch > 0) {
                    addToken(list, strBuilder.substring(0, i10));
                    return i11 + iIsMatch;
                }
                if (i5 <= 0 || !isQuote(cArr, i11, i3, i4, i5)) {
                    int iIsMatch2 = getIgnoredMatcher().isMatch(cArr, i11, i2, i3);
                    if (iIsMatch2 <= 0) {
                        iIsMatch2 = getTrimmerMatcher().isMatch(cArr, i11, i2, i3);
                        if (iIsMatch2 > 0) {
                            strBuilder.append(cArr, i11, iIsMatch2);
                        } else {
                            i6 = i11 + 1;
                            strBuilder.append(cArr[i11]);
                            size = strBuilder.size();
                        }
                    }
                    i6 = i11 + iIsMatch2;
                    size = i10;
                } else {
                    i6 = i11 + i5;
                    size = i10;
                    z2 = true;
                }
            }
        }
        addToken(list, strBuilder.substring(0, size));
        return -1;
    }

    Object a() {
        StrTokenizer strTokenizer = (StrTokenizer) super.clone();
        char[] cArr = strTokenizer.chars;
        if (cArr != null) {
            strTokenizer.chars = (char[]) cArr.clone();
        }
        strTokenizer.reset();
        return strTokenizer;
    }

    @Override // java.util.ListIterator
    public void add(Object obj) {
        throw new UnsupportedOperationException("add() is unsupported");
    }

    protected List b(char[] cArr, int i2, int i3) {
        if (cArr == null || i3 == 0) {
            return Collections.EMPTY_LIST;
        }
        StrBuilder strBuilder = new StrBuilder();
        ArrayList arrayList = new ArrayList();
        int nextToken = i2;
        while (nextToken >= 0 && nextToken < i3) {
            nextToken = readNextToken(cArr, nextToken, i3, strBuilder, arrayList);
            if (nextToken >= i3) {
                addToken(arrayList, "");
            }
        }
        return arrayList;
    }

    public Object clone() {
        try {
            return a();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public String getContent() {
        char[] cArr = this.chars;
        if (cArr == null) {
            return null;
        }
        return new String(cArr);
    }

    public StrMatcher getDelimiterMatcher() {
        return this.delimMatcher;
    }

    public StrMatcher getIgnoredMatcher() {
        return this.ignoredMatcher;
    }

    public StrMatcher getQuoteMatcher() {
        return this.quoteMatcher;
    }

    public String[] getTokenArray() {
        checkTokenized();
        return (String[]) this.tokens.clone();
    }

    public List getTokenList() {
        checkTokenized();
        ArrayList arrayList = new ArrayList(this.tokens.length);
        int i2 = 0;
        while (true) {
            String[] strArr = this.tokens;
            if (i2 >= strArr.length) {
                return arrayList;
            }
            arrayList.add(strArr[i2]);
            i2++;
        }
    }

    public StrMatcher getTrimmerMatcher() {
        return this.trimmerMatcher;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public boolean hasNext() {
        checkTokenized();
        return this.tokenPos < this.tokens.length;
    }

    @Override // java.util.ListIterator
    public boolean hasPrevious() {
        checkTokenized();
        return this.tokenPos > 0;
    }

    public boolean isEmptyTokenAsNull() {
        return this.emptyAsNull;
    }

    public boolean isIgnoreEmptyTokens() {
        return this.ignoreEmptyTokens;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        String[] strArr = this.tokens;
        int i2 = this.tokenPos;
        this.tokenPos = i2 + 1;
        return strArr[i2];
    }

    @Override // java.util.ListIterator
    public int nextIndex() {
        return this.tokenPos;
    }

    public String nextToken() {
        if (!hasNext()) {
            return null;
        }
        String[] strArr = this.tokens;
        int i2 = this.tokenPos;
        this.tokenPos = i2 + 1;
        return strArr[i2];
    }

    @Override // java.util.ListIterator
    public Object previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        String[] strArr = this.tokens;
        int i2 = this.tokenPos - 1;
        this.tokenPos = i2;
        return strArr[i2];
    }

    @Override // java.util.ListIterator
    public int previousIndex() {
        return this.tokenPos - 1;
    }

    public String previousToken() {
        if (!hasPrevious()) {
            return null;
        }
        String[] strArr = this.tokens;
        int i2 = this.tokenPos - 1;
        this.tokenPos = i2;
        return strArr[i2];
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("remove() is unsupported");
    }

    public StrTokenizer reset() {
        this.tokenPos = 0;
        this.tokens = null;
        return this;
    }

    @Override // java.util.ListIterator
    public void set(Object obj) {
        throw new UnsupportedOperationException("set() is unsupported");
    }

    public StrTokenizer setDelimiterChar(char c2) {
        return setDelimiterMatcher(StrMatcher.charMatcher(c2));
    }

    public StrTokenizer setDelimiterMatcher(StrMatcher strMatcher) {
        if (strMatcher == null) {
            this.delimMatcher = StrMatcher.noneMatcher();
        } else {
            this.delimMatcher = strMatcher;
        }
        return this;
    }

    public StrTokenizer setDelimiterString(String str) {
        return setDelimiterMatcher(StrMatcher.stringMatcher(str));
    }

    public StrTokenizer setEmptyTokenAsNull(boolean z2) {
        this.emptyAsNull = z2;
        return this;
    }

    public StrTokenizer setIgnoreEmptyTokens(boolean z2) {
        this.ignoreEmptyTokens = z2;
        return this;
    }

    public StrTokenizer setIgnoredChar(char c2) {
        return setIgnoredMatcher(StrMatcher.charMatcher(c2));
    }

    public StrTokenizer setIgnoredMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.ignoredMatcher = strMatcher;
        }
        return this;
    }

    public StrTokenizer setQuoteChar(char c2) {
        return setQuoteMatcher(StrMatcher.charMatcher(c2));
    }

    public StrTokenizer setQuoteMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.quoteMatcher = strMatcher;
        }
        return this;
    }

    public StrTokenizer setTrimmerMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.trimmerMatcher = strMatcher;
        }
        return this;
    }

    public int size() {
        checkTokenized();
        return this.tokens.length;
    }

    public String toString() {
        if (this.tokens == null) {
            return "StrTokenizer[not tokenized yet]";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("StrTokenizer");
        stringBuffer.append(getTokenList());
        return stringBuffer.toString();
    }

    public static StrTokenizer getCSVInstance(String str) {
        StrTokenizer cSVClone = getCSVClone();
        cSVClone.reset(str);
        return cSVClone;
    }

    public static StrTokenizer getTSVInstance(String str) {
        StrTokenizer tSVClone = getTSVClone();
        tSVClone.reset(str);
        return tSVClone;
    }

    public StrTokenizer reset(String str) {
        reset();
        if (str != null) {
            this.chars = str.toCharArray();
        } else {
            this.chars = null;
        }
        return this;
    }

    public static StrTokenizer getCSVInstance(char[] cArr) {
        StrTokenizer cSVClone = getCSVClone();
        cSVClone.reset(cArr);
        return cSVClone;
    }

    public static StrTokenizer getTSVInstance(char[] cArr) {
        StrTokenizer tSVClone = getTSVClone();
        tSVClone.reset(cArr);
        return tSVClone;
    }

    public StrTokenizer reset(char[] cArr) {
        reset();
        this.chars = cArr;
        return this;
    }

    public StrTokenizer(String str) {
        this.delimMatcher = StrMatcher.splitMatcher();
        this.quoteMatcher = StrMatcher.noneMatcher();
        this.ignoredMatcher = StrMatcher.noneMatcher();
        this.trimmerMatcher = StrMatcher.noneMatcher();
        this.emptyAsNull = false;
        this.ignoreEmptyTokens = true;
        if (str != null) {
            this.chars = str.toCharArray();
        } else {
            this.chars = null;
        }
    }

    public StrTokenizer(String str, char c2) {
        this(str);
        setDelimiterChar(c2);
    }

    public StrTokenizer(String str, String str2) {
        this(str);
        setDelimiterString(str2);
    }

    public StrTokenizer(String str, StrMatcher strMatcher) {
        this(str);
        setDelimiterMatcher(strMatcher);
    }

    public StrTokenizer(String str, char c2, char c3) {
        this(str, c2);
        setQuoteChar(c3);
    }

    public StrTokenizer(String str, StrMatcher strMatcher, StrMatcher strMatcher2) {
        this(str, strMatcher);
        setQuoteMatcher(strMatcher2);
    }

    public StrTokenizer(char[] cArr) {
        this.delimMatcher = StrMatcher.splitMatcher();
        this.quoteMatcher = StrMatcher.noneMatcher();
        this.ignoredMatcher = StrMatcher.noneMatcher();
        this.trimmerMatcher = StrMatcher.noneMatcher();
        this.emptyAsNull = false;
        this.ignoreEmptyTokens = true;
        this.chars = cArr;
    }

    public StrTokenizer(char[] cArr, char c2) {
        this(cArr);
        setDelimiterChar(c2);
    }

    public StrTokenizer(char[] cArr, String str) {
        this(cArr);
        setDelimiterString(str);
    }

    public StrTokenizer(char[] cArr, StrMatcher strMatcher) {
        this(cArr);
        setDelimiterMatcher(strMatcher);
    }

    public StrTokenizer(char[] cArr, char c2, char c3) {
        this(cArr, c2);
        setQuoteChar(c3);
    }

    public StrTokenizer(char[] cArr, StrMatcher strMatcher, StrMatcher strMatcher2) {
        this(cArr, strMatcher);
        setQuoteMatcher(strMatcher2);
    }
}
