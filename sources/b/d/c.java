package b.d;

import java.util.HashMap;
import java.util.Map;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes2.dex */
public abstract class c {

    /* renamed from: a, reason: collision with root package name */
    public int f7396a;

    /* renamed from: b, reason: collision with root package name */
    public HashMap<Integer, byte[]> f7397b;

    /* renamed from: c, reason: collision with root package name */
    public HashMap<Integer, byte[]> f7398c;

    /* renamed from: d, reason: collision with root package name */
    public HashMap<Integer, byte[]> f7399d;

    /* renamed from: e, reason: collision with root package name */
    public int f7400e;

    /* renamed from: f, reason: collision with root package name */
    public int f7401f = 5;

    /* renamed from: g, reason: collision with root package name */
    public byte[] f7402g;

    /* renamed from: h, reason: collision with root package name */
    public byte[] f7403h;

    /* renamed from: i, reason: collision with root package name */
    public byte[] f7404i;

    /* renamed from: j, reason: collision with root package name */
    public byte[] f7405j;

    /* renamed from: k, reason: collision with root package name */
    public int f7406k;

    /* renamed from: l, reason: collision with root package name */
    public int f7407l;

    /* renamed from: m, reason: collision with root package name */
    public int f7408m;

    /* renamed from: n, reason: collision with root package name */
    public int f7409n;

    /* renamed from: o, reason: collision with root package name */
    public byte[] f7410o;

    /* renamed from: p, reason: collision with root package name */
    public int f7411p;

    /* renamed from: q, reason: collision with root package name */
    public byte[] f7412q;

    /* renamed from: r, reason: collision with root package name */
    public boolean f7413r;

    /* renamed from: s, reason: collision with root package name */
    public SecureUtils.K2Output f7414s;

    /* renamed from: t, reason: collision with root package name */
    public String f7415t;

    public void a(byte[] bArr) {
        this.f7403h = bArr;
    }

    public int b() {
        return this.f7406k;
    }

    public abstract void c(HashMap<Integer, byte[]> map);

    public void c(byte[] bArr) {
        this.f7405j = bArr;
    }

    public void d(byte[] bArr) {
        this.f7410o = bArr;
    }

    public int e() {
        return this.f7396a;
    }

    public void f(byte[] bArr) {
        this.f7402g = bArr;
    }

    public void g(int i2) {
        this.f7400e = i2;
    }

    public void h(int i2) {
        this.f7401f = i2;
    }

    public byte[] i() {
        return this.f7405j;
    }

    public HashMap<Integer, byte[]> j() {
        return this.f7397b;
    }

    public HashMap<Integer, byte[]> k() {
        return this.f7398c;
    }

    public String l() {
        return this.f7415t;
    }

    public abstract Map<Integer, byte[]> m();

    public int n() {
        return this.f7409n;
    }

    public byte[] o() {
        return this.f7410o;
    }

    public int p() {
        return this.f7400e;
    }

    public byte[] q() {
        return this.f7404i;
    }

    public byte[] r() {
        return this.f7402g;
    }

    public int s() {
        return this.f7401f;
    }

    public boolean t() {
        return this.f7413r;
    }

    public int a() {
        return this.f7407l;
    }

    public void b(int i2) {
        this.f7406k = i2;
    }

    public int c() {
        return this.f7408m;
    }

    public int d() {
        return this.f7411p;
    }

    public void e(int i2) {
        this.f7396a = i2;
    }

    public byte[] f() {
        return this.f7403h;
    }

    public byte[] g() {
        return this.f7412q;
    }

    public SecureUtils.K2Output h() {
        return this.f7414s;
    }

    public void a(int i2) {
        this.f7407l = i2;
    }

    public void b(byte[] bArr) {
        this.f7412q = bArr;
    }

    public void c(int i2) {
        this.f7408m = i2;
    }

    public void d(int i2) {
        this.f7411p = i2;
    }

    public void e(byte[] bArr) {
        this.f7404i = bArr;
    }

    public void f(int i2) {
        this.f7409n = i2;
    }

    public void a(boolean z2) {
        this.f7413r = z2;
    }

    public void b(HashMap<Integer, byte[]> map) {
        this.f7398c = map;
    }

    public void a(SecureUtils.K2Output k2Output) {
        this.f7414s = k2Output;
    }

    public void a(HashMap<Integer, byte[]> map) {
        this.f7397b = map;
    }

    public void a(String str) {
        this.f7415t = str;
    }
}
