package com.meizu.cloud.pushsdk.handler.e.j;

import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private String f19711a;

    /* renamed from: b, reason: collision with root package name */
    private String f19712b;

    /* renamed from: c, reason: collision with root package name */
    private String f19713c;

    /* renamed from: d, reason: collision with root package name */
    private String f19714d;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private String f19715a;

        /* renamed from: b, reason: collision with root package name */
        private String f19716b;

        /* renamed from: c, reason: collision with root package name */
        private String f19717c;

        /* renamed from: d, reason: collision with root package name */
        private String f19718d;

        public a a(String str) {
            this.f19718d = str;
            return this;
        }

        public a b(String str) {
            this.f19717c = str;
            return this;
        }

        public a c(String str) {
            this.f19716b = str;
            return this;
        }

        public a d(String str) {
            this.f19715a = str;
            return this;
        }

        public d a() {
            return new d(this);
        }
    }

    public d() {
    }

    public static a a() {
        return new a();
    }

    public String b() {
        return this.f19714d;
    }

    public String c() {
        return this.f19713c;
    }

    public String d() {
        return this.f19712b;
    }

    public String e() {
        return this.f19711a;
    }

    public String f() {
        com.meizu.cloud.pushsdk.f.b.c cVar = new com.meizu.cloud.pushsdk.f.b.c();
        cVar.a("task_id", this.f19711a);
        cVar.a(PushConstants.SEQ_ID, this.f19712b);
        cVar.a(PushConstants.PUSH_TIMESTAMP, this.f19713c);
        cVar.a("device_id", this.f19714d);
        return cVar.toString();
    }

    public d(a aVar) {
        this.f19711a = !TextUtils.isEmpty(aVar.f19715a) ? aVar.f19715a : "";
        this.f19712b = !TextUtils.isEmpty(aVar.f19716b) ? aVar.f19716b : "";
        this.f19713c = !TextUtils.isEmpty(aVar.f19717c) ? aVar.f19717c : "";
        this.f19714d = TextUtils.isEmpty(aVar.f19718d) ? "" : aVar.f19718d;
    }
}
