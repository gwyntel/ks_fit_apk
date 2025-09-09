package com.vivo.push.model;

/* loaded from: classes4.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private String f23184a;

    /* renamed from: b, reason: collision with root package name */
    private String f23185b;

    public a(String str, String str2) {
        this.f23184a = str;
        this.f23185b = str2;
    }

    public final String a() {
        return this.f23184a;
    }

    public final String b() {
        return this.f23185b;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || a.class != obj.getClass()) {
            return false;
        }
        a aVar = (a) obj;
        String str = this.f23184a;
        if (str == null) {
            if (aVar.f23184a != null) {
                return false;
            }
        } else if (!str.equals(aVar.f23184a)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        String str = this.f23184a;
        return (str == null ? 0 : str.hashCode()) + 31;
    }

    public final String toString() {
        return "ConfigItem{mKey='" + this.f23184a + "', mValue='" + this.f23185b + "'}";
    }
}
