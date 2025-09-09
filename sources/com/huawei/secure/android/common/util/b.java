package com.huawei.secure.android.common.util;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private String f18540a;

    /* renamed from: b, reason: collision with root package name */
    private Character f18541b;

    /* renamed from: c, reason: collision with root package name */
    private Character f18542c;

    /* renamed from: d, reason: collision with root package name */
    private int f18543d = 0;

    /* renamed from: e, reason: collision with root package name */
    private int f18544e = 0;

    public b(String str) {
        this.f18540a = str;
    }

    public static boolean c(Character ch) {
        char cCharValue;
        return ch != null && (cCharValue = ch.charValue()) >= '0' && cCharValue <= '7';
    }

    public void a(Character ch) {
        this.f18541b = ch;
    }

    public int b() {
        return this.f18543d;
    }

    public Character d() {
        Character ch = this.f18541b;
        if (ch != null) {
            this.f18541b = null;
            return ch;
        }
        String str = this.f18540a;
        if (str == null || str.length() == 0 || this.f18543d >= this.f18540a.length()) {
            return null;
        }
        String str2 = this.f18540a;
        int i2 = this.f18543d;
        this.f18543d = i2 + 1;
        return Character.valueOf(str2.charAt(i2));
    }

    public Character e() {
        Character chD = d();
        if (chD != null && b(chD)) {
            return chD;
        }
        return null;
    }

    public Character f() {
        Character chD = d();
        if (chD != null && c(chD)) {
            return chD;
        }
        return null;
    }

    public Character g() {
        Character ch = this.f18541b;
        if (ch != null) {
            return ch;
        }
        String str = this.f18540a;
        if (str == null || str.length() == 0 || this.f18543d >= this.f18540a.length()) {
            return null;
        }
        return Character.valueOf(this.f18540a.charAt(this.f18543d));
    }

    protected String h() {
        String strSubstring = this.f18540a.substring(this.f18543d);
        if (this.f18541b == null) {
            return strSubstring;
        }
        return this.f18541b + strSubstring;
    }

    public void i() {
        this.f18541b = this.f18542c;
        this.f18543d = this.f18544e;
    }

    public static boolean b(Character ch) {
        if (ch == null) {
            return false;
        }
        char cCharValue = ch.charValue();
        return (cCharValue >= '0' && cCharValue <= '9') || (cCharValue >= 'a' && cCharValue <= 'f') || (cCharValue >= 'A' && cCharValue <= 'F');
    }

    public boolean a() {
        if (this.f18541b != null) {
            return true;
        }
        String str = this.f18540a;
        return (str == null || str.length() == 0 || this.f18543d >= this.f18540a.length()) ? false : true;
    }

    public void c() {
        this.f18542c = this.f18541b;
        this.f18544e = this.f18543d;
    }

    public boolean a(char c2) {
        Character ch = this.f18541b;
        if (ch != null && ch.charValue() == c2) {
            return true;
        }
        String str = this.f18540a;
        return str != null && str.length() != 0 && this.f18543d < this.f18540a.length() && this.f18540a.charAt(this.f18543d) == c2;
    }
}
