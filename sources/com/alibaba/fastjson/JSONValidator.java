package com.alibaba.fastjson;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/* loaded from: classes2.dex */
public abstract class JSONValidator implements Cloneable, Closeable {
    protected char ch;
    protected boolean eof;
    protected Type type;
    private Boolean validateResult;
    protected int pos = -1;
    protected int count = 0;
    protected boolean supportMultiValue = false;

    static class ReaderValidator extends JSONValidator {
        private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
        private char[] buf;

        /* renamed from: r, reason: collision with root package name */
        final Reader f8777r;
        private int end = -1;
        private int readCount = 0;

        ReaderValidator(Reader reader) throws IOException {
            this.f8777r = reader;
            ThreadLocal<char[]> threadLocal = bufLocal;
            char[] cArr = threadLocal.get();
            this.buf = cArr;
            if (cArr != null) {
                threadLocal.set(null);
            } else {
                this.buf = new char[8192];
            }
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            bufLocal.set(this.buf);
            this.f8777r.close();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() throws IOException {
            int i2 = this.pos;
            if (i2 < this.end) {
                char[] cArr = this.buf;
                int i3 = i2 + 1;
                this.pos = i3;
                this.ch = cArr[i3];
                return;
            }
            if (this.eof) {
                return;
            }
            try {
                Reader reader = this.f8777r;
                char[] cArr2 = this.buf;
                int i4 = reader.read(cArr2, 0, cArr2.length);
                this.readCount++;
                if (i4 > 0) {
                    this.ch = this.buf[0];
                    this.pos = 0;
                    this.end = i4 - 1;
                } else {
                    if (i4 == -1) {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = (char) 0;
                        this.eof = true;
                        return;
                    }
                    this.pos = 0;
                    this.end = 0;
                    this.buf = null;
                    this.ch = (char) 0;
                    this.eof = true;
                    throw new JSONException("read error");
                }
            } catch (IOException unused) {
                throw new JSONException("read error");
            }
        }
    }

    public enum Type {
        Object,
        Array,
        Value
    }

    static class UTF16Validator extends JSONValidator {
        private final String str;

        public UTF16Validator(String str) {
            this.str = str;
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        protected final void fieldName() {
            char cCharAt;
            int i2 = this.pos;
            do {
                i2++;
                if (i2 >= this.str.length() || (cCharAt = this.str.charAt(i2)) == '\\') {
                    next();
                    while (true) {
                        char c2 = this.ch;
                        if (c2 == '\\') {
                            next();
                            if (this.ch == 'u') {
                                next();
                                next();
                                next();
                                next();
                                next();
                            } else {
                                next();
                            }
                        } else if (c2 == '\"') {
                            next();
                            return;
                        } else if (this.eof) {
                            return;
                        } else {
                            next();
                        }
                    }
                }
            } while (cCharAt != '\"');
            int i3 = i2 + 1;
            this.ch = this.str.charAt(i3);
            this.pos = i3;
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() {
            int i2 = this.pos + 1;
            this.pos = i2;
            if (i2 < this.str.length()) {
                this.ch = this.str.charAt(this.pos);
            } else {
                this.ch = (char) 0;
                this.eof = true;
            }
        }
    }

    static class UTF8InputStreamValidator extends JSONValidator {
        private static final ThreadLocal<byte[]> bufLocal = new ThreadLocal<>();
        private byte[] buf;
        private final InputStream is;
        private int end = -1;
        private int readCount = 0;

        public UTF8InputStreamValidator(InputStream inputStream) throws IOException {
            this.is = inputStream;
            ThreadLocal<byte[]> threadLocal = bufLocal;
            byte[] bArr = threadLocal.get();
            this.buf = bArr;
            if (bArr != null) {
                threadLocal.set(null);
            } else {
                this.buf = new byte[8192];
            }
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            bufLocal.set(this.buf);
            this.is.close();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() throws IOException {
            int i2 = this.pos;
            if (i2 < this.end) {
                byte[] bArr = this.buf;
                int i3 = i2 + 1;
                this.pos = i3;
                this.ch = (char) bArr[i3];
                return;
            }
            if (this.eof) {
                return;
            }
            try {
                InputStream inputStream = this.is;
                byte[] bArr2 = this.buf;
                int i4 = inputStream.read(bArr2, 0, bArr2.length);
                this.readCount++;
                if (i4 > 0) {
                    this.ch = (char) this.buf[0];
                    this.pos = 0;
                    this.end = i4 - 1;
                } else {
                    if (i4 == -1) {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = (char) 0;
                        this.eof = true;
                        return;
                    }
                    this.pos = 0;
                    this.end = 0;
                    this.buf = null;
                    this.ch = (char) 0;
                    this.eof = true;
                    throw new JSONException("read error");
                }
            } catch (IOException unused) {
                throw new JSONException("read error");
            }
        }
    }

    static class UTF8Validator extends JSONValidator {
        private final byte[] bytes;

        public UTF8Validator(byte[] bArr) {
            this.bytes = bArr;
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() {
            int i2 = this.pos + 1;
            this.pos = i2;
            byte[] bArr = this.bytes;
            if (i2 < bArr.length) {
                this.ch = (char) bArr[i2];
            } else {
                this.ch = (char) 0;
                this.eof = true;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:130:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:181:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean any() {
        /*
            Method dump skipped, instructions count: 532
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONValidator.any():boolean");
    }

    public static JSONValidator from(String str) {
        return new UTF16Validator(str);
    }

    public static JSONValidator fromUtf8(byte[] bArr) {
        return new UTF8Validator(bArr);
    }

    static final boolean isWhiteSpace(char c2) {
        return c2 == ' ' || c2 == '\t' || c2 == '\r' || c2 == '\n' || c2 == '\f' || c2 == '\b';
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    protected void fieldName() {
        next();
        while (true) {
            char c2 = this.ch;
            if (c2 == '\\') {
                next();
                if (this.ch == 'u') {
                    next();
                    next();
                    next();
                    next();
                    next();
                } else {
                    next();
                }
            } else {
                if (c2 == '\"') {
                    next();
                    return;
                }
                next();
            }
        }
    }

    public Type getType() {
        if (this.type == null) {
            validate();
        }
        return this.type;
    }

    public boolean isSupportMultiValue() {
        return this.supportMultiValue;
    }

    abstract void next();

    public JSONValidator setSupportMultiValue(boolean z2) {
        this.supportMultiValue = z2;
        return this;
    }

    void skipWhiteSpace() {
        while (isWhiteSpace(this.ch)) {
            next();
        }
    }

    protected boolean string() {
        next();
        while (!this.eof) {
            char c2 = this.ch;
            if (c2 == '\\') {
                next();
                if (this.ch == 'u') {
                    next();
                    next();
                    next();
                    next();
                    next();
                } else {
                    next();
                }
            } else {
                if (c2 == '\"') {
                    next();
                    return true;
                }
                next();
            }
        }
        return false;
    }

    public boolean validate() {
        Boolean bool = this.validateResult;
        if (bool != null) {
            return bool.booleanValue();
        }
        while (any()) {
            skipWhiteSpace();
            this.count++;
            if (this.eof) {
                this.validateResult = Boolean.TRUE;
                return true;
            }
            if (!this.supportMultiValue) {
                this.validateResult = Boolean.FALSE;
                return false;
            }
            skipWhiteSpace();
            if (this.eof) {
                this.validateResult = Boolean.TRUE;
                return true;
            }
        }
        this.validateResult = Boolean.FALSE;
        return false;
    }

    public static JSONValidator from(Reader reader) {
        return new ReaderValidator(reader);
    }

    public static JSONValidator fromUtf8(InputStream inputStream) {
        return new UTF8InputStreamValidator(inputStream);
    }
}
