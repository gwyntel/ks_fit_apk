package com.tencent.bugly.proguard;

/* loaded from: classes4.dex */
public final class ar implements Comparable<ar> {

    /* renamed from: a, reason: collision with root package name */
    public long f20815a = -1;

    /* renamed from: b, reason: collision with root package name */
    public long f20816b = -1;

    /* renamed from: c, reason: collision with root package name */
    public String f20817c = null;

    /* renamed from: d, reason: collision with root package name */
    public boolean f20818d = false;

    /* renamed from: e, reason: collision with root package name */
    public boolean f20819e = false;

    /* renamed from: f, reason: collision with root package name */
    public int f20820f = 0;

    @Override // java.lang.Comparable
    public final /* bridge */ /* synthetic */ int compareTo(ar arVar) {
        ar arVar2 = arVar;
        if (arVar2 == null) {
            return 1;
        }
        long j2 = this.f20816b - arVar2.f20816b;
        if (j2 <= 0) {
            return j2 < 0 ? -1 : 0;
        }
        return 1;
    }
}
