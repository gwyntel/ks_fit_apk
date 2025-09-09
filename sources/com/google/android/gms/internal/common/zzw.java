package com.google.android.gms.internal.common;

/* loaded from: classes3.dex */
abstract class zzw extends zzj {

    /* renamed from: a, reason: collision with root package name */
    final CharSequence f13044a;

    /* renamed from: b, reason: collision with root package name */
    final zzo f13045b;

    /* renamed from: c, reason: collision with root package name */
    final boolean f13046c;

    /* renamed from: d, reason: collision with root package name */
    int f13047d = 0;

    /* renamed from: e, reason: collision with root package name */
    int f13048e = Integer.MAX_VALUE;

    protected zzw(zzx zzxVar, CharSequence charSequence) {
        this.f13045b = zzxVar.zza;
        this.f13046c = zzxVar.zzb;
        this.f13044a = charSequence;
    }

    @Override // com.google.android.gms.internal.common.zzj
    protected final /* bridge */ /* synthetic */ Object a() {
        int iD;
        int iC;
        int i2 = this.f13047d;
        while (true) {
            int i3 = this.f13047d;
            if (i3 == -1) {
                b();
                return null;
            }
            iD = d(i3);
            if (iD == -1) {
                iD = this.f13044a.length();
                this.f13047d = -1;
                iC = -1;
            } else {
                iC = c(iD);
                this.f13047d = iC;
            }
            if (iC == i2) {
                int i4 = iC + 1;
                this.f13047d = i4;
                if (i4 > this.f13044a.length()) {
                    this.f13047d = -1;
                }
            } else {
                if (i2 < iD) {
                    this.f13044a.charAt(i2);
                }
                if (i2 < iD) {
                    this.f13044a.charAt(iD - 1);
                }
                if (!this.f13046c || i2 != iD) {
                    break;
                }
                i2 = this.f13047d;
            }
        }
        int i5 = this.f13048e;
        if (i5 == 1) {
            iD = this.f13044a.length();
            this.f13047d = -1;
            if (iD > i2) {
                this.f13044a.charAt(iD - 1);
            }
        } else {
            this.f13048e = i5 - 1;
        }
        return this.f13044a.subSequence(i2, iD).toString();
    }

    abstract int c(int i2);

    abstract int d(int i2);
}
