package com.meizu.cloud.pushsdk.e.d;

import androidx.browser.trusted.sharing.ShareTarget;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class b extends j {

    /* renamed from: a, reason: collision with root package name */
    private static final g f19379a = g.a(ShareTarget.ENCODING_TYPE_URL_ENCODED);

    /* renamed from: b, reason: collision with root package name */
    private final List<String> f19380b;

    /* renamed from: c, reason: collision with root package name */
    private final List<String> f19381c;

    /* renamed from: com.meizu.cloud.pushsdk.e.d.b$b, reason: collision with other inner class name */
    public static final class C0153b {

        /* renamed from: a, reason: collision with root package name */
        private final List<String> f19382a = new ArrayList();

        /* renamed from: b, reason: collision with root package name */
        private final List<String> f19383b = new ArrayList();

        public C0153b a(String str, String str2) {
            this.f19382a.add(f.a(str, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true));
            this.f19383b.add(f.a(str2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true));
            return this;
        }

        public C0153b b(String str, String str2) {
            this.f19382a.add(f.a(str, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true));
            this.f19383b.add(f.a(str2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true));
            return this;
        }

        public b a() {
            return new b(this.f19382a, this.f19383b);
        }
    }

    private b(List<String> list, List<String> list2) {
        this.f19380b = m.a(list);
        this.f19381c = m.a(list2);
    }

    @Override // com.meizu.cloud.pushsdk.e.d.j
    public long a() {
        return a((com.meizu.cloud.pushsdk.e.h.c) null, true);
    }

    @Override // com.meizu.cloud.pushsdk.e.d.j
    public g b() {
        return f19379a;
    }

    private long a(com.meizu.cloud.pushsdk.e.h.c cVar, boolean z2) {
        com.meizu.cloud.pushsdk.e.h.b bVar = z2 ? new com.meizu.cloud.pushsdk.e.h.b() : cVar.c();
        int size = this.f19380b.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (i2 > 0) {
                bVar.b(38);
            }
            bVar.a(this.f19380b.get(i2));
            bVar.b(61);
            bVar.a(this.f19381c.get(i2));
        }
        if (!z2) {
            return 0L;
        }
        long jK = bVar.k();
        bVar.e();
        return jK;
    }

    @Override // com.meizu.cloud.pushsdk.e.d.j
    public void a(com.meizu.cloud.pushsdk.e.h.c cVar) {
        a(cVar, false);
    }
}
