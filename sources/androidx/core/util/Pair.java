package androidx.core.util;

import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F f2, S s2) {
        this.first = f2;
        this.second = s2;
    }

    @NonNull
    public static <A, B> Pair<A, B> create(A a2, B b2) {
        return new Pair<>(a2, b2);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        return ObjectsCompat.equals(pair.first, this.first) && ObjectsCompat.equals(pair.second, this.second);
    }

    public int hashCode() {
        F f2 = this.first;
        int iHashCode = f2 == null ? 0 : f2.hashCode();
        S s2 = this.second;
        return iHashCode ^ (s2 != null ? s2.hashCode() : 0);
    }

    @NonNull
    public String toString() {
        return "Pair{" + this.first + " " + this.second + com.alipay.sdk.m.u.i.f9804d;
    }
}
