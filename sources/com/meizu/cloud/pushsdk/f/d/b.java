package com.meizu.cloud.pushsdk.f.d;

import android.text.TextUtils;
import com.meizu.cloud.pushsdk.f.d.a;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.umeng.analytics.pro.f;

/* loaded from: classes4.dex */
public class b extends com.meizu.cloud.pushsdk.f.d.a {

    /* renamed from: d, reason: collision with root package name */
    private final String f19590d;

    /* renamed from: e, reason: collision with root package name */
    private final String f19591e;

    /* renamed from: f, reason: collision with root package name */
    private final String f19592f;

    /* renamed from: g, reason: collision with root package name */
    private final String f19593g;

    /* renamed from: h, reason: collision with root package name */
    private final String f19594h;

    /* renamed from: i, reason: collision with root package name */
    private final String f19595i;

    /* renamed from: j, reason: collision with root package name */
    private final String f19596j;

    /* renamed from: k, reason: collision with root package name */
    private final String f19597k;

    /* renamed from: l, reason: collision with root package name */
    private final String f19598l;

    /* renamed from: m, reason: collision with root package name */
    private final int f19599m;

    /* renamed from: com.meizu.cloud.pushsdk.f.d.b$b, reason: collision with other inner class name */
    private static class C0158b extends c<C0158b> {
        private C0158b() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.meizu.cloud.pushsdk.f.d.a.AbstractC0157a
        /* renamed from: c, reason: merged with bridge method [inline-methods] */
        public C0158b a() {
            return this;
        }
    }

    public static abstract class c<T extends c<T>> extends a.AbstractC0157a<T> {

        /* renamed from: d, reason: collision with root package name */
        private String f19600d;

        /* renamed from: e, reason: collision with root package name */
        private String f19601e;

        /* renamed from: f, reason: collision with root package name */
        private String f19602f;

        /* renamed from: g, reason: collision with root package name */
        private String f19603g;

        /* renamed from: h, reason: collision with root package name */
        private String f19604h;

        /* renamed from: i, reason: collision with root package name */
        private String f19605i;

        /* renamed from: j, reason: collision with root package name */
        private String f19606j;

        /* renamed from: k, reason: collision with root package name */
        private String f19607k;

        /* renamed from: l, reason: collision with root package name */
        private String f19608l;

        /* renamed from: m, reason: collision with root package name */
        private int f19609m = 0;

        public T a(int i2) {
            this.f19609m = i2;
            return a();
        }

        public T c(String str) {
            this.f19600d = str;
            return a();
        }

        public T d(String str) {
            this.f19603g = str;
            return a();
        }

        public T e(String str) {
            this.f19607k = str;
            return a();
        }

        public T f(String str) {
            this.f19605i = str;
            return a();
        }

        public T g(String str) {
            this.f19604h = str;
            return a();
        }

        public T h(String str) {
            this.f19606j = str;
            return a();
        }

        public T i(String str) {
            this.f19601e = str;
            return a();
        }

        public T a(String str) {
            this.f19602f = str;
            return a();
        }

        public T b(String str) {
            this.f19608l = str;
            return a();
        }

        public b b() {
            return new b(this);
        }
    }

    protected b(c<?> cVar) {
        super(cVar);
        this.f19591e = ((c) cVar).f19601e;
        this.f19592f = ((c) cVar).f19602f;
        this.f19593g = ((c) cVar).f19603g;
        this.f19590d = ((c) cVar).f19600d;
        this.f19594h = ((c) cVar).f19604h;
        this.f19595i = ((c) cVar).f19605i;
        this.f19596j = ((c) cVar).f19606j;
        this.f19597k = ((c) cVar).f19607k;
        this.f19598l = ((c) cVar).f19608l;
        this.f19599m = ((c) cVar).f19609m;
    }

    public static c<?> d() {
        return new C0158b();
    }

    public com.meizu.cloud.pushsdk.f.b.c e() {
        String str;
        String str2;
        com.meizu.cloud.pushsdk.f.b.c cVar = new com.meizu.cloud.pushsdk.f.b.c();
        cVar.a("en", this.f19590d);
        cVar.a("ti", this.f19591e);
        if (TextUtils.isEmpty(this.f19593g)) {
            str = this.f19592f;
            str2 = AppIconSetting.DEFAULT_LARGE_ICON;
        } else {
            str = this.f19593g;
            str2 = "fdId";
        }
        cVar.a(str2, str);
        cVar.a(f.T, this.f19594h);
        cVar.a("pn", this.f19595i);
        cVar.a("si", this.f19596j);
        cVar.a("ms", this.f19597k);
        cVar.a("ect", this.f19598l);
        cVar.a("br", Integer.valueOf(this.f19599m));
        return a(cVar);
    }
}
