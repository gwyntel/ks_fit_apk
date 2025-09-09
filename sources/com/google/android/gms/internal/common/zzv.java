package com.google.android.gms.internal.common;

import java.io.IOException;
import java.util.Iterator;

/* loaded from: classes3.dex */
final class zzv implements Iterable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ CharSequence f13042a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzx f13043b;

    zzv(zzx zzxVar, CharSequence charSequence) {
        this.f13043b = zzxVar;
        this.f13042a = charSequence;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return this.f13043b.zzh(this.f13042a);
    }

    public final String toString() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Iterator it = iterator();
        try {
            if (it.hasNext()) {
                sb.append(zzq.a(it.next(), ", "));
                while (it.hasNext()) {
                    sb.append((CharSequence) ", ");
                    sb.append(zzq.a(it.next(), ", "));
                }
            }
            sb.append(']');
            return sb.toString();
        } catch (IOException e2) {
            throw new AssertionError(e2);
        }
    }
}
