package c.a.a;

/* loaded from: classes2.dex */
public class Aa {

    /* renamed from: a, reason: collision with root package name */
    public String f7659a;

    /* renamed from: b, reason: collision with root package name */
    public int f7660b = 0;

    public Aa(String str) {
        this.f7659a = str;
    }

    public boolean a() {
        return this.f7660b != -1;
    }

    public String b() {
        int i2 = this.f7660b;
        if (i2 == -1) {
            return null;
        }
        int iIndexOf = this.f7659a.indexOf(46, i2);
        if (iIndexOf == -1) {
            String strSubstring = this.f7659a.substring(this.f7660b);
            this.f7660b = -1;
            return strSubstring;
        }
        String strSubstring2 = this.f7659a.substring(this.f7660b, iIndexOf);
        this.f7660b = iIndexOf + 1;
        return strSubstring2;
    }
}
