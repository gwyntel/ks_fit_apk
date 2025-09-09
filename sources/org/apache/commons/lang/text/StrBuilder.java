package org.apache.commons.lang.text;

import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.SystemUtils;

/* loaded from: classes5.dex */
public class StrBuilder implements Cloneable {
    private static final long serialVersionUID = 7628716375283629643L;

    /* renamed from: a, reason: collision with root package name */
    protected char[] f26636a;

    /* renamed from: b, reason: collision with root package name */
    protected int f26637b;
    private String newLine;
    private String nullText;

    class StrBuilderTokenizer extends StrTokenizer {
        StrBuilderTokenizer() {
        }

        @Override // org.apache.commons.lang.text.StrTokenizer
        protected List b(char[] cArr, int i2, int i3) {
            if (cArr != null) {
                return super.b(cArr, i2, i3);
            }
            StrBuilder strBuilder = StrBuilder.this;
            return super.b(strBuilder.f26636a, 0, strBuilder.size());
        }

        @Override // org.apache.commons.lang.text.StrTokenizer
        public String getContent() {
            String content = super.getContent();
            return content == null ? StrBuilder.this.toString() : content;
        }
    }

    class StrBuilderWriter extends Writer {
        StrBuilderWriter() {
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
        }

        @Override // java.io.Writer
        public void write(int i2) {
            StrBuilder.this.append((char) i2);
        }

        @Override // java.io.Writer
        public void write(char[] cArr) {
            StrBuilder.this.append(cArr);
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i2, int i3) {
            StrBuilder.this.append(cArr, i2, i3);
        }

        @Override // java.io.Writer
        public void write(String str) {
            StrBuilder.this.append(str);
        }

        @Override // java.io.Writer
        public void write(String str, int i2, int i3) {
            StrBuilder.this.append(str, i2, i3);
        }
    }

    public StrBuilder() {
        this(32);
    }

    private void deleteImpl(int i2, int i3, int i4) {
        char[] cArr = this.f26636a;
        System.arraycopy(cArr, i3, cArr, i2, this.f26637b - i3);
        this.f26637b -= i4;
    }

    private void replaceImpl(int i2, int i3, int i4, String str, int i5) {
        int i6 = (this.f26637b - i4) + i5;
        if (i5 != i4) {
            ensureCapacity(i6);
            char[] cArr = this.f26636a;
            System.arraycopy(cArr, i3, cArr, i2 + i5, this.f26637b - i3);
            this.f26637b = i6;
        }
        if (i5 > 0) {
            str.getChars(0, i5, this.f26636a, i2);
        }
    }

    protected void a(int i2) {
        if (i2 < 0 || i2 > this.f26637b) {
            throw new StringIndexOutOfBoundsException(i2);
        }
    }

    public StrBuilder append(Object obj) {
        return obj == null ? appendNull() : append(obj.toString());
    }

    public StrBuilder appendAll(Object[] objArr) {
        if (objArr != null && objArr.length > 0) {
            for (Object obj : objArr) {
                append(obj);
            }
        }
        return this;
    }

    public StrBuilder appendFixedWidthPadLeft(Object obj, int i2, char c2) {
        if (i2 > 0) {
            ensureCapacity(this.f26637b + i2);
            String nullText = obj == null ? getNullText() : obj.toString();
            if (nullText == null) {
                nullText = "";
            }
            int length = nullText.length();
            if (length >= i2) {
                nullText.getChars(length - i2, length, this.f26636a, this.f26637b);
            } else {
                int i3 = i2 - length;
                for (int i4 = 0; i4 < i3; i4++) {
                    this.f26636a[this.f26637b + i4] = c2;
                }
                nullText.getChars(0, length, this.f26636a, this.f26637b + i3);
            }
            this.f26637b += i2;
        }
        return this;
    }

    public StrBuilder appendFixedWidthPadRight(Object obj, int i2, char c2) {
        if (i2 > 0) {
            ensureCapacity(this.f26637b + i2);
            String nullText = obj == null ? getNullText() : obj.toString();
            if (nullText == null) {
                nullText = "";
            }
            int length = nullText.length();
            if (length >= i2) {
                nullText.getChars(0, i2, this.f26636a, this.f26637b);
            } else {
                int i3 = i2 - length;
                nullText.getChars(0, length, this.f26636a, this.f26637b);
                for (int i4 = 0; i4 < i3; i4++) {
                    this.f26636a[this.f26637b + length + i4] = c2;
                }
            }
            this.f26637b += i2;
        }
        return this;
    }

    public StrBuilder appendNewLine() {
        String str = this.newLine;
        if (str != null) {
            return append(str);
        }
        append(SystemUtils.LINE_SEPARATOR);
        return this;
    }

    public StrBuilder appendNull() {
        String str = this.nullText;
        return str == null ? this : append(str);
    }

    public StrBuilder appendPadding(int i2, char c2) {
        if (i2 >= 0) {
            ensureCapacity(this.f26637b + i2);
            for (int i3 = 0; i3 < i2; i3++) {
                char[] cArr = this.f26636a;
                int i4 = this.f26637b;
                this.f26637b = i4 + 1;
                cArr[i4] = c2;
            }
        }
        return this;
    }

    public StrBuilder appendSeparator(String str) {
        return appendSeparator(str, (String) null);
    }

    public StrBuilder appendWithSeparators(Object[] objArr, String str) {
        if (objArr != null && objArr.length > 0) {
            if (str == null) {
                str = "";
            }
            append(objArr[0]);
            for (int i2 = 1; i2 < objArr.length; i2++) {
                append(str);
                append(objArr[i2]);
            }
        }
        return this;
    }

    public StrBuilder appendln(Object obj) {
        return append(obj).appendNewLine();
    }

    public Reader asReader() {
        return new StrBuilderReader();
    }

    public StrTokenizer asTokenizer() {
        return new StrBuilderTokenizer();
    }

    public Writer asWriter() {
        return new StrBuilderWriter();
    }

    protected int b(int i2, int i3) {
        if (i2 < 0) {
            throw new StringIndexOutOfBoundsException(i2);
        }
        int i4 = this.f26637b;
        if (i3 > i4) {
            i3 = i4;
        }
        if (i2 <= i3) {
            return i3;
        }
        throw new StringIndexOutOfBoundsException("end < start");
    }

    public int capacity() {
        return this.f26636a.length;
    }

    public char charAt(int i2) {
        if (i2 < 0 || i2 >= length()) {
            throw new StringIndexOutOfBoundsException(i2);
        }
        return this.f26636a[i2];
    }

    public StrBuilder clear() {
        this.f26637b = 0;
        return this;
    }

    public Object clone() throws CloneNotSupportedException {
        StrBuilder strBuilder = (StrBuilder) super.clone();
        char[] cArr = new char[this.f26636a.length];
        strBuilder.f26636a = cArr;
        char[] cArr2 = this.f26636a;
        System.arraycopy(cArr2, 0, cArr, 0, cArr2.length);
        return strBuilder;
    }

    public boolean contains(char c2) {
        char[] cArr = this.f26636a;
        for (int i2 = 0; i2 < this.f26637b; i2++) {
            if (cArr[i2] == c2) {
                return true;
            }
        }
        return false;
    }

    public StrBuilder delete(int i2, int i3) {
        int iB = b(i2, i3);
        int i4 = iB - i2;
        if (i4 > 0) {
            deleteImpl(i2, iB, i4);
        }
        return this;
    }

    public StrBuilder deleteAll(char c2) {
        int i2 = 0;
        while (i2 < this.f26637b) {
            if (this.f26636a[i2] == c2) {
                int i3 = i2;
                do {
                    i3++;
                    if (i3 >= this.f26637b) {
                        break;
                    }
                } while (this.f26636a[i3] == c2);
                int i4 = i3 - i2;
                deleteImpl(i2, i3, i4);
                i2 = i3 - i4;
            }
            i2++;
        }
        return this;
    }

    public StrBuilder deleteCharAt(int i2) {
        if (i2 < 0 || i2 >= this.f26637b) {
            throw new StringIndexOutOfBoundsException(i2);
        }
        deleteImpl(i2, i2 + 1, 1);
        return this;
    }

    public StrBuilder deleteFirst(char c2) {
        int i2 = 0;
        while (true) {
            if (i2 >= this.f26637b) {
                break;
            }
            if (this.f26636a[i2] == c2) {
                deleteImpl(i2, i2 + 1, 1);
                break;
            }
            i2++;
        }
        return this;
    }

    public boolean endsWith(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return true;
        }
        int i2 = this.f26637b;
        if (length > i2) {
            return false;
        }
        int i3 = i2 - length;
        int i4 = 0;
        while (i4 < length) {
            if (this.f26636a[i3] != str.charAt(i4)) {
                return false;
            }
            i4++;
            i3++;
        }
        return true;
    }

    public StrBuilder ensureCapacity(int i2) {
        char[] cArr = this.f26636a;
        if (i2 > cArr.length) {
            char[] cArr2 = new char[i2 * 2];
            this.f26636a = cArr2;
            System.arraycopy(cArr, 0, cArr2, 0, this.f26637b);
        }
        return this;
    }

    public boolean equals(StrBuilder strBuilder) {
        if (this == strBuilder) {
            return true;
        }
        int i2 = this.f26637b;
        if (i2 != strBuilder.f26637b) {
            return false;
        }
        char[] cArr = this.f26636a;
        char[] cArr2 = strBuilder.f26636a;
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            if (cArr[i3] != cArr2[i3]) {
                return false;
            }
        }
        return true;
    }

    public boolean equalsIgnoreCase(StrBuilder strBuilder) {
        if (this == strBuilder) {
            return true;
        }
        int i2 = this.f26637b;
        if (i2 != strBuilder.f26637b) {
            return false;
        }
        char[] cArr = this.f26636a;
        char[] cArr2 = strBuilder.f26636a;
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            char c2 = cArr[i3];
            char c3 = cArr2[i3];
            if (c2 != c3 && Character.toUpperCase(c2) != Character.toUpperCase(c3)) {
                return false;
            }
        }
        return true;
    }

    public char[] getChars(char[] cArr) {
        int length = length();
        if (cArr == null || cArr.length < length) {
            cArr = new char[length];
        }
        System.arraycopy(this.f26636a, 0, cArr, 0, length);
        return cArr;
    }

    public String getNewLineText() {
        return this.newLine;
    }

    public String getNullText() {
        return this.nullText;
    }

    public int hashCode() {
        char[] cArr = this.f26636a;
        int i2 = 0;
        for (int i3 = this.f26637b - 1; i3 >= 0; i3--) {
            i2 = (i2 * 31) + cArr[i3];
        }
        return i2;
    }

    public int indexOf(char c2) {
        return indexOf(c2, 0);
    }

    public StrBuilder insert(int i2, Object obj) {
        return obj == null ? insert(i2, this.nullText) : insert(i2, obj.toString());
    }

    public boolean isEmpty() {
        return this.f26637b == 0;
    }

    public int lastIndexOf(char c2) {
        return lastIndexOf(c2, this.f26637b - 1);
    }

    public String leftString(int i2) {
        if (i2 <= 0) {
            return "";
        }
        int i3 = this.f26637b;
        return i2 >= i3 ? new String(this.f26636a, 0, i3) : new String(this.f26636a, 0, i2);
    }

    public int length() {
        return this.f26637b;
    }

    public String midString(int i2, int i3) {
        int i4;
        if (i2 < 0) {
            i2 = 0;
        }
        return (i3 <= 0 || i2 >= (i4 = this.f26637b)) ? "" : i4 <= i2 + i3 ? new String(this.f26636a, i2, i4 - i2) : new String(this.f26636a, i2, i3);
    }

    public StrBuilder minimizeCapacity() {
        if (this.f26636a.length > length()) {
            char[] cArr = this.f26636a;
            char[] cArr2 = new char[length()];
            this.f26636a = cArr2;
            System.arraycopy(cArr, 0, cArr2, 0, this.f26637b);
        }
        return this;
    }

    public StrBuilder replace(int i2, int i3, String str) {
        int iB = b(i2, i3);
        replaceImpl(i2, iB, iB - i2, str, str == null ? 0 : str.length());
        return this;
    }

    public StrBuilder replaceAll(char c2, char c3) {
        if (c2 != c3) {
            for (int i2 = 0; i2 < this.f26637b; i2++) {
                char[] cArr = this.f26636a;
                if (cArr[i2] == c2) {
                    cArr[i2] = c3;
                }
            }
        }
        return this;
    }

    public StrBuilder replaceFirst(char c2, char c3) {
        if (c2 != c3) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.f26637b) {
                    break;
                }
                char[] cArr = this.f26636a;
                if (cArr[i2] == c2) {
                    cArr[i2] = c3;
                    break;
                }
                i2++;
            }
        }
        return this;
    }

    public StrBuilder reverse() {
        int i2 = this.f26637b;
        if (i2 == 0) {
            return this;
        }
        int i3 = i2 / 2;
        char[] cArr = this.f26636a;
        int i4 = i2 - 1;
        int i5 = 0;
        while (i5 < i3) {
            char c2 = cArr[i5];
            cArr[i5] = cArr[i4];
            cArr[i4] = c2;
            i5++;
            i4--;
        }
        return this;
    }

    public String rightString(int i2) {
        if (i2 <= 0) {
            return "";
        }
        int i3 = this.f26637b;
        return i2 >= i3 ? new String(this.f26636a, 0, i3) : new String(this.f26636a, i3 - i2, i2);
    }

    public StrBuilder setCharAt(int i2, char c2) {
        if (i2 < 0 || i2 >= length()) {
            throw new StringIndexOutOfBoundsException(i2);
        }
        this.f26636a[i2] = c2;
        return this;
    }

    public StrBuilder setLength(int i2) {
        if (i2 < 0) {
            throw new StringIndexOutOfBoundsException(i2);
        }
        int i3 = this.f26637b;
        if (i2 < i3) {
            this.f26637b = i2;
        } else if (i2 > i3) {
            ensureCapacity(i2);
            this.f26637b = i2;
            for (int i4 = this.f26637b; i4 < i2; i4++) {
                this.f26636a[i4] = 0;
            }
        }
        return this;
    }

    public StrBuilder setNewLineText(String str) {
        this.newLine = str;
        return this;
    }

    public StrBuilder setNullText(String str) {
        if (str != null && str.length() == 0) {
            str = null;
        }
        this.nullText = str;
        return this;
    }

    public int size() {
        return this.f26637b;
    }

    public boolean startsWith(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return true;
        }
        if (length > this.f26637b) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (this.f26636a[i2] != str.charAt(i2)) {
                return false;
            }
        }
        return true;
    }

    public String substring(int i2) {
        return substring(i2, this.f26637b);
    }

    public char[] toCharArray() {
        int i2 = this.f26637b;
        if (i2 == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        char[] cArr = new char[i2];
        System.arraycopy(this.f26636a, 0, cArr, 0, i2);
        return cArr;
    }

    public String toString() {
        return new String(this.f26636a, 0, this.f26637b);
    }

    public StringBuffer toStringBuffer() {
        StringBuffer stringBuffer = new StringBuffer(this.f26637b);
        stringBuffer.append(this.f26636a, 0, this.f26637b);
        return stringBuffer;
    }

    public StrBuilder trim() {
        int i2 = this.f26637b;
        if (i2 == 0) {
            return this;
        }
        char[] cArr = this.f26636a;
        int i3 = 0;
        while (i3 < i2 && cArr[i3] <= ' ') {
            i3++;
        }
        while (i3 < i2 && cArr[i2 - 1] <= ' ') {
            i2--;
        }
        int i4 = this.f26637b;
        if (i2 < i4) {
            delete(i2, i4);
        }
        if (i3 > 0) {
            delete(0, i3);
        }
        return this;
    }

    class StrBuilderReader extends Reader {
        private int mark;
        private int pos;

        StrBuilderReader() {
        }

        @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.Reader
        public void mark(int i2) {
            this.mark = this.pos;
        }

        @Override // java.io.Reader
        public boolean markSupported() {
            return true;
        }

        @Override // java.io.Reader
        public int read() {
            if (!ready()) {
                return -1;
            }
            StrBuilder strBuilder = StrBuilder.this;
            int i2 = this.pos;
            this.pos = i2 + 1;
            return strBuilder.charAt(i2);
        }

        @Override // java.io.Reader
        public boolean ready() {
            return this.pos < StrBuilder.this.size();
        }

        @Override // java.io.Reader
        public void reset() {
            this.pos = this.mark;
        }

        @Override // java.io.Reader
        public long skip(long j2) {
            if (this.pos + j2 > StrBuilder.this.size()) {
                j2 = StrBuilder.this.size() - this.pos;
            }
            if (j2 < 0) {
                return 0L;
            }
            this.pos = (int) (this.pos + j2);
            return j2;
        }

        @Override // java.io.Reader
        public int read(char[] cArr, int i2, int i3) {
            int i4;
            if (i2 < 0 || i3 < 0 || i2 > cArr.length || (i4 = i2 + i3) > cArr.length || i4 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (i3 == 0) {
                return 0;
            }
            if (this.pos >= StrBuilder.this.size()) {
                return -1;
            }
            if (this.pos + i3 > StrBuilder.this.size()) {
                i3 = StrBuilder.this.size() - this.pos;
            }
            StrBuilder strBuilder = StrBuilder.this;
            int i5 = this.pos;
            strBuilder.getChars(i5, i5 + i3, cArr, i2);
            this.pos += i3;
            return i3;
        }
    }

    public StrBuilder(int i2) {
        this.f26636a = new char[i2 <= 0 ? 32 : i2];
    }

    public StrBuilder appendSeparator(String str, String str2) {
        if (isEmpty()) {
            str = str2;
        }
        if (str != null) {
            append(str);
        }
        return this;
    }

    public StrBuilder appendln(String str) {
        return append(str).appendNewLine();
    }

    public int indexOf(char c2, int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 >= this.f26637b) {
            return -1;
        }
        char[] cArr = this.f26636a;
        while (i2 < this.f26637b) {
            if (cArr[i2] == c2) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public int lastIndexOf(char c2, int i2) {
        int i3 = this.f26637b;
        if (i2 >= i3) {
            i2 = i3 - 1;
        }
        if (i2 < 0) {
            return -1;
        }
        while (i2 >= 0) {
            if (this.f26636a[i2] == c2) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    public String substring(int i2, int i3) {
        return new String(this.f26636a, i2, b(i2, i3) - i2);
    }

    public StrBuilder append(String str) {
        if (str == null) {
            return appendNull();
        }
        int length = str.length();
        if (length > 0) {
            int length2 = length();
            ensureCapacity(length2 + length);
            str.getChars(0, length, this.f26636a, length2);
            this.f26637b += length;
        }
        return this;
    }

    public StrBuilder appendln(String str, int i2, int i3) {
        return append(str, i2, i3).appendNewLine();
    }

    public StrBuilder insert(int i2, String str) {
        a(i2);
        if (str == null) {
            str = this.nullText;
        }
        int length = str == null ? 0 : str.length();
        if (length > 0) {
            int i3 = this.f26637b + length;
            ensureCapacity(i3);
            char[] cArr = this.f26636a;
            System.arraycopy(cArr, i2, cArr, i2 + length, this.f26637b - i2);
            this.f26637b = i3;
            str.getChars(0, length, this.f26636a, i2);
        }
        return this;
    }

    public StrBuilder(String str) {
        if (str == null) {
            this.f26636a = new char[32];
        } else {
            this.f26636a = new char[str.length() + 32];
            append(str);
        }
    }

    public StrBuilder appendAll(Collection collection) {
        if (collection != null && collection.size() > 0) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                append(it.next());
            }
        }
        return this;
    }

    public StrBuilder appendSeparator(char c2) {
        if (size() > 0) {
            append(c2);
        }
        return this;
    }

    public StrBuilder appendln(StringBuffer stringBuffer) {
        return append(stringBuffer).appendNewLine();
    }

    public boolean contains(String str) {
        return indexOf(str, 0) >= 0;
    }

    public StrBuilder deleteFirst(String str) {
        int iIndexOf;
        int length = str == null ? 0 : str.length();
        if (length > 0 && (iIndexOf = indexOf(str, 0)) >= 0) {
            deleteImpl(iIndexOf, iIndexOf + length, length);
        }
        return this;
    }

    public int lastIndexOf(String str) {
        return lastIndexOf(str, this.f26637b - 1);
    }

    public StrBuilder replace(StrMatcher strMatcher, String str, int i2, int i3, int i4) {
        return replaceImpl(strMatcher, str, i2, b(i2, i3), i4);
    }

    public StrBuilder replaceAll(String str, String str2) {
        int length = str == null ? 0 : str.length();
        if (length > 0) {
            int length2 = str2 == null ? 0 : str2.length();
            int iIndexOf = indexOf(str, 0);
            while (iIndexOf >= 0) {
                replaceImpl(iIndexOf, iIndexOf + length, length, str2, length2);
                iIndexOf = indexOf(str, iIndexOf + length2);
            }
        }
        return this;
    }

    public StrBuilder replaceFirst(String str, String str2) {
        int iIndexOf;
        int length = str == null ? 0 : str.length();
        if (length > 0 && (iIndexOf = indexOf(str, 0)) >= 0) {
            replaceImpl(iIndexOf, iIndexOf + length, length, str2, str2 != null ? str2.length() : 0);
        }
        return this;
    }

    public StrBuilder appendln(StringBuffer stringBuffer, int i2, int i3) {
        return append(stringBuffer, i2, i3).appendNewLine();
    }

    public boolean contains(StrMatcher strMatcher) {
        return indexOf(strMatcher, 0) >= 0;
    }

    public boolean equals(Object obj) {
        if (obj instanceof StrBuilder) {
            return equals((StrBuilder) obj);
        }
        return false;
    }

    public void getChars(int i2, int i3, char[] cArr, int i4) {
        if (i2 >= 0) {
            if (i3 < 0 || i3 > length()) {
                throw new StringIndexOutOfBoundsException(i3);
            }
            if (i2 <= i3) {
                System.arraycopy(this.f26636a, i2, cArr, i4, i3 - i2);
                return;
            }
            throw new StringIndexOutOfBoundsException("end < start");
        }
        throw new StringIndexOutOfBoundsException(i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0036, code lost:
    
        r9 = r9 - 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int lastIndexOf(java.lang.String r8, int r9) {
        /*
            r7 = this;
            int r0 = r7.f26637b
            r1 = 1
            if (r9 < r0) goto L7
            int r9 = r0 + (-1)
        L7:
            r0 = -1
            if (r8 == 0) goto L40
            if (r9 >= 0) goto Ld
            goto L40
        Ld:
            int r2 = r8.length()
            if (r2 <= 0) goto L3d
            int r3 = r7.f26637b
            if (r2 > r3) goto L3d
            r3 = 0
            if (r2 != r1) goto L23
            char r8 = r8.charAt(r3)
            int r8 = r7.lastIndexOf(r8, r9)
            return r8
        L23:
            int r9 = r9 - r2
            int r9 = r9 + r1
        L25:
            if (r9 < 0) goto L40
            r1 = r3
        L28:
            if (r1 >= r2) goto L3c
            char r4 = r8.charAt(r1)
            char[] r5 = r7.f26636a
            int r6 = r9 + r1
            char r5 = r5[r6]
            if (r4 == r5) goto L39
            int r9 = r9 + (-1)
            goto L25
        L39:
            int r1 = r1 + 1
            goto L28
        L3c:
            return r9
        L3d:
            if (r2 != 0) goto L40
            return r9
        L40:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.text.StrBuilder.lastIndexOf(java.lang.String, int):int");
    }

    public char[] toCharArray(int i2, int i3) {
        int iB = b(i2, i3) - i2;
        if (iB == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        char[] cArr = new char[iB];
        System.arraycopy(this.f26636a, i2, cArr, 0, iB);
        return cArr;
    }

    private StrBuilder replaceImpl(StrMatcher strMatcher, String str, int i2, int i3, int i4) {
        if (strMatcher != null && this.f26637b != 0) {
            int length = str == null ? 0 : str.length();
            char[] cArr = this.f26636a;
            int i5 = i2;
            while (i5 < i3 && i4 != 0) {
                int iIsMatch = strMatcher.isMatch(cArr, i5, i2, i3);
                if (iIsMatch > 0) {
                    replaceImpl(i5, i5 + iIsMatch, iIsMatch, str, length);
                    i3 = (i3 - iIsMatch) + length;
                    i5 = (i5 + length) - 1;
                    if (i4 > 0) {
                        i4--;
                    }
                }
                i5++;
            }
        }
        return this;
    }

    public StrBuilder appendSeparator(char c2, char c3) {
        if (size() > 0) {
            append(c2);
        } else {
            append(c3);
        }
        return this;
    }

    public StrBuilder appendln(StrBuilder strBuilder) {
        return append(strBuilder).appendNewLine();
    }

    public StrBuilder deleteAll(String str) {
        int length = str == null ? 0 : str.length();
        if (length > 0) {
            int iIndexOf = indexOf(str, 0);
            while (iIndexOf >= 0) {
                deleteImpl(iIndexOf, iIndexOf + length, length);
                iIndexOf = indexOf(str, iIndexOf);
            }
        }
        return this;
    }

    public int indexOf(String str) {
        return indexOf(str, 0);
    }

    public StrBuilder appendWithSeparators(Collection collection, String str) {
        if (collection != null && collection.size() > 0) {
            if (str == null) {
                str = "";
            }
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                append(it.next());
                if (it.hasNext()) {
                    append(str);
                }
            }
        }
        return this;
    }

    public StrBuilder appendln(StrBuilder strBuilder, int i2, int i3) {
        return append(strBuilder, i2, i3).appendNewLine();
    }

    public StrBuilder deleteFirst(StrMatcher strMatcher) {
        return replace(strMatcher, null, 0, this.f26637b, 1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0037, code lost:
    
        r10 = r10 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int indexOf(java.lang.String r9, int r10) {
        /*
            r8 = this;
            r0 = 0
            if (r10 >= 0) goto L4
            r10 = r0
        L4:
            r1 = -1
            if (r9 == 0) goto L3e
            int r2 = r8.f26637b
            if (r10 < r2) goto Lc
            goto L3e
        Lc:
            int r2 = r9.length()
            r3 = 1
            if (r2 != r3) goto L1c
            char r9 = r9.charAt(r0)
            int r9 = r8.indexOf(r9, r10)
            return r9
        L1c:
            if (r2 != 0) goto L1f
            return r10
        L1f:
            int r4 = r8.f26637b
            if (r2 <= r4) goto L24
            return r1
        L24:
            char[] r5 = r8.f26636a
            int r4 = r4 - r2
            int r4 = r4 + r3
        L28:
            if (r10 >= r4) goto L3e
            r3 = r0
        L2b:
            if (r3 >= r2) goto L3d
            char r6 = r9.charAt(r3)
            int r7 = r10 + r3
            char r7 = r5[r7]
            if (r6 == r7) goto L3a
            int r10 = r10 + 1
            goto L28
        L3a:
            int r3 = r3 + 1
            goto L2b
        L3d:
            return r10
        L3e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.text.StrBuilder.indexOf(java.lang.String, int):int");
    }

    public StrBuilder appendAll(Iterator it) {
        if (it != null) {
            while (it.hasNext()) {
                append(it.next());
            }
        }
        return this;
    }

    public StrBuilder appendln(char[] cArr) {
        return append(cArr).appendNewLine();
    }

    public StrBuilder replaceFirst(StrMatcher strMatcher, String str) {
        return replace(strMatcher, str, 0, this.f26637b, 1);
    }

    public StrBuilder append(String str, int i2, int i3) {
        int i4;
        if (str == null) {
            return appendNull();
        }
        if (i2 >= 0 && i2 <= str.length()) {
            if (i3 < 0 || (i4 = i2 + i3) > str.length()) {
                throw new StringIndexOutOfBoundsException("length must be valid");
            }
            if (i3 > 0) {
                int length = length();
                ensureCapacity(length + i3);
                str.getChars(i2, i4, this.f26636a, length);
                this.f26637b += i3;
            }
            return this;
        }
        throw new StringIndexOutOfBoundsException("startIndex must be valid");
    }

    public StrBuilder appendFixedWidthPadLeft(int i2, int i3, char c2) {
        return appendFixedWidthPadLeft(String.valueOf(i2), i3, c2);
    }

    public StrBuilder appendFixedWidthPadRight(int i2, int i3, char c2) {
        return appendFixedWidthPadRight(String.valueOf(i2), i3, c2);
    }

    public StrBuilder appendSeparator(String str, int i2) {
        if (str != null && i2 > 0) {
            append(str);
        }
        return this;
    }

    public StrBuilder appendln(char[] cArr, int i2, int i3) {
        return append(cArr, i2, i3).appendNewLine();
    }

    public StrBuilder replaceAll(StrMatcher strMatcher, String str) {
        return replace(strMatcher, str, 0, this.f26637b, -1);
    }

    public StrBuilder appendSeparator(char c2, int i2) {
        if (i2 > 0) {
            append(c2);
        }
        return this;
    }

    public StrBuilder appendln(boolean z2) {
        return append(z2).appendNewLine();
    }

    public StrBuilder deleteAll(StrMatcher strMatcher) {
        return replace(strMatcher, null, 0, this.f26637b, -1);
    }

    public int lastIndexOf(StrMatcher strMatcher) {
        return lastIndexOf(strMatcher, this.f26637b);
    }

    public StrBuilder appendln(char c2) {
        return append(c2).appendNewLine();
    }

    public StrBuilder insert(int i2, char[] cArr) {
        a(i2);
        if (cArr == null) {
            return insert(i2, this.nullText);
        }
        int length = cArr.length;
        if (length > 0) {
            ensureCapacity(this.f26637b + length);
            char[] cArr2 = this.f26636a;
            System.arraycopy(cArr2, i2, cArr2, i2 + length, this.f26637b - i2);
            System.arraycopy(cArr, 0, this.f26636a, i2, length);
            this.f26637b += length;
        }
        return this;
    }

    public int lastIndexOf(StrMatcher strMatcher, int i2) {
        int i3 = this.f26637b;
        if (i2 >= i3) {
            i2 = i3 - 1;
        }
        if (strMatcher != null && i2 >= 0) {
            char[] cArr = this.f26636a;
            int i4 = i2 + 1;
            while (i2 >= 0) {
                if (strMatcher.isMatch(cArr, i2, 0, i4) > 0) {
                    return i2;
                }
                i2--;
            }
        }
        return -1;
    }

    public StrBuilder appendln(int i2) {
        return append(i2).appendNewLine();
    }

    public StrBuilder appendln(long j2) {
        return append(j2).appendNewLine();
    }

    public int indexOf(StrMatcher strMatcher) {
        return indexOf(strMatcher, 0);
    }

    public StrBuilder appendWithSeparators(Iterator it, String str) {
        if (it != null) {
            if (str == null) {
                str = "";
            }
            while (it.hasNext()) {
                append(it.next());
                if (it.hasNext()) {
                    append(str);
                }
            }
        }
        return this;
    }

    public StrBuilder appendln(float f2) {
        return append(f2).appendNewLine();
    }

    public int indexOf(StrMatcher strMatcher, int i2) {
        int i3;
        if (i2 < 0) {
            i2 = 0;
        }
        if (strMatcher != null && i2 < (i3 = this.f26637b)) {
            char[] cArr = this.f26636a;
            for (int i4 = i2; i4 < i3; i4++) {
                if (strMatcher.isMatch(cArr, i4, i2, i3) > 0) {
                    return i4;
                }
            }
        }
        return -1;
    }

    public StrBuilder appendln(double d2) {
        return append(d2).appendNewLine();
    }

    public StrBuilder append(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return appendNull();
        }
        int length = stringBuffer.length();
        if (length > 0) {
            int length2 = length();
            ensureCapacity(length2 + length);
            stringBuffer.getChars(0, length, this.f26636a, length2);
            this.f26637b += length;
        }
        return this;
    }

    public StrBuilder insert(int i2, char[] cArr, int i3, int i4) {
        a(i2);
        if (cArr == null) {
            return insert(i2, this.nullText);
        }
        if (i3 >= 0 && i3 <= cArr.length) {
            if (i4 < 0 || i3 + i4 > cArr.length) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid length: ");
                stringBuffer.append(i4);
                throw new StringIndexOutOfBoundsException(stringBuffer.toString());
            }
            if (i4 > 0) {
                ensureCapacity(this.f26637b + i4);
                char[] cArr2 = this.f26636a;
                System.arraycopy(cArr2, i2, cArr2, i2 + i4, this.f26637b - i2);
                System.arraycopy(cArr, i3, this.f26636a, i2, i4);
                this.f26637b += i4;
            }
            return this;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Invalid offset: ");
        stringBuffer2.append(i3);
        throw new StringIndexOutOfBoundsException(stringBuffer2.toString());
    }

    public StrBuilder append(StringBuffer stringBuffer, int i2, int i3) {
        int i4;
        if (stringBuffer == null) {
            return appendNull();
        }
        if (i2 >= 0 && i2 <= stringBuffer.length()) {
            if (i3 < 0 || (i4 = i2 + i3) > stringBuffer.length()) {
                throw new StringIndexOutOfBoundsException("length must be valid");
            }
            if (i3 > 0) {
                int length = length();
                ensureCapacity(length + i3);
                stringBuffer.getChars(i2, i4, this.f26636a, length);
                this.f26637b += i3;
            }
            return this;
        }
        throw new StringIndexOutOfBoundsException("startIndex must be valid");
    }

    public StrBuilder insert(int i2, boolean z2) {
        a(i2);
        if (z2) {
            ensureCapacity(this.f26637b + 4);
            char[] cArr = this.f26636a;
            System.arraycopy(cArr, i2, cArr, i2 + 4, this.f26637b - i2);
            char[] cArr2 = this.f26636a;
            cArr2[i2] = 't';
            cArr2[i2 + 1] = 'r';
            cArr2[i2 + 2] = 'u';
            cArr2[i2 + 3] = 'e';
            this.f26637b += 4;
        } else {
            ensureCapacity(this.f26637b + 5);
            char[] cArr3 = this.f26636a;
            System.arraycopy(cArr3, i2, cArr3, i2 + 5, this.f26637b - i2);
            char[] cArr4 = this.f26636a;
            cArr4[i2] = 'f';
            cArr4[i2 + 1] = 'a';
            cArr4[i2 + 2] = 'l';
            cArr4[i2 + 3] = 's';
            cArr4[i2 + 4] = 'e';
            this.f26637b += 5;
        }
        return this;
    }

    public StrBuilder append(StrBuilder strBuilder) {
        if (strBuilder == null) {
            return appendNull();
        }
        int length = strBuilder.length();
        if (length > 0) {
            int length2 = length();
            ensureCapacity(length2 + length);
            System.arraycopy(strBuilder.f26636a, 0, this.f26636a, length2, length);
            this.f26637b += length;
        }
        return this;
    }

    public StrBuilder append(StrBuilder strBuilder, int i2, int i3) {
        int i4;
        if (strBuilder == null) {
            return appendNull();
        }
        if (i2 >= 0 && i2 <= strBuilder.length()) {
            if (i3 < 0 || (i4 = i2 + i3) > strBuilder.length()) {
                throw new StringIndexOutOfBoundsException("length must be valid");
            }
            if (i3 > 0) {
                int length = length();
                ensureCapacity(length + i3);
                strBuilder.getChars(i2, i4, this.f26636a, length);
                this.f26637b += i3;
            }
            return this;
        }
        throw new StringIndexOutOfBoundsException("startIndex must be valid");
    }

    public StrBuilder insert(int i2, char c2) {
        a(i2);
        ensureCapacity(this.f26637b + 1);
        char[] cArr = this.f26636a;
        System.arraycopy(cArr, i2, cArr, i2 + 1, this.f26637b - i2);
        this.f26636a[i2] = c2;
        this.f26637b++;
        return this;
    }

    public StrBuilder append(char[] cArr) {
        if (cArr == null) {
            return appendNull();
        }
        int length = cArr.length;
        if (length > 0) {
            int length2 = length();
            ensureCapacity(length2 + length);
            System.arraycopy(cArr, 0, this.f26636a, length2, length);
            this.f26637b += length;
        }
        return this;
    }

    public StrBuilder insert(int i2, int i3) {
        return insert(i2, String.valueOf(i3));
    }

    public StrBuilder insert(int i2, long j2) {
        return insert(i2, String.valueOf(j2));
    }

    public StrBuilder insert(int i2, float f2) {
        return insert(i2, String.valueOf(f2));
    }

    public StrBuilder insert(int i2, double d2) {
        return insert(i2, String.valueOf(d2));
    }

    public StrBuilder append(char[] cArr, int i2, int i3) {
        if (cArr == null) {
            return appendNull();
        }
        if (i2 >= 0 && i2 <= cArr.length) {
            if (i3 < 0 || i2 + i3 > cArr.length) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid length: ");
                stringBuffer.append(i3);
                throw new StringIndexOutOfBoundsException(stringBuffer.toString());
            }
            if (i3 > 0) {
                int length = length();
                ensureCapacity(length + i3);
                System.arraycopy(cArr, i2, this.f26636a, length, i3);
                this.f26637b += i3;
            }
            return this;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Invalid startIndex: ");
        stringBuffer2.append(i3);
        throw new StringIndexOutOfBoundsException(stringBuffer2.toString());
    }

    public StrBuilder append(boolean z2) {
        if (z2) {
            ensureCapacity(this.f26637b + 4);
            char[] cArr = this.f26636a;
            int i2 = this.f26637b;
            int i3 = i2 + 1;
            this.f26637b = i3;
            cArr[i2] = 't';
            int i4 = i2 + 2;
            this.f26637b = i4;
            cArr[i3] = 'r';
            int i5 = i2 + 3;
            this.f26637b = i5;
            cArr[i4] = 'u';
            this.f26637b = i2 + 4;
            cArr[i5] = 'e';
        } else {
            ensureCapacity(this.f26637b + 5);
            char[] cArr2 = this.f26636a;
            int i6 = this.f26637b;
            int i7 = i6 + 1;
            this.f26637b = i7;
            cArr2[i6] = 'f';
            int i8 = i6 + 2;
            this.f26637b = i8;
            cArr2[i7] = 'a';
            int i9 = i6 + 3;
            this.f26637b = i9;
            cArr2[i8] = 'l';
            int i10 = i6 + 4;
            this.f26637b = i10;
            cArr2[i9] = 's';
            this.f26637b = i6 + 5;
            cArr2[i10] = 'e';
        }
        return this;
    }

    public StrBuilder append(char c2) {
        ensureCapacity(length() + 1);
        char[] cArr = this.f26636a;
        int i2 = this.f26637b;
        this.f26637b = i2 + 1;
        cArr[i2] = c2;
        return this;
    }

    public StrBuilder append(int i2) {
        return append(String.valueOf(i2));
    }

    public StrBuilder append(long j2) {
        return append(String.valueOf(j2));
    }

    public StrBuilder append(float f2) {
        return append(String.valueOf(f2));
    }

    public StrBuilder append(double d2) {
        return append(String.valueOf(d2));
    }
}
