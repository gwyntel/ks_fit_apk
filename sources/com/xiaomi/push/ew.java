package com.xiaomi.push;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public final class ew {

    public static final class a extends e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f342a;

        /* renamed from: b, reason: collision with other field name */
        private boolean f343b;

        /* renamed from: d, reason: collision with root package name */
        private boolean f23674d;

        /* renamed from: e, reason: collision with root package name */
        private boolean f23675e;

        /* renamed from: a, reason: collision with root package name */
        private int f23671a = 0;

        /* renamed from: c, reason: collision with other field name */
        private boolean f344c = false;

        /* renamed from: b, reason: collision with root package name */
        private int f23672b = 0;

        /* renamed from: f, reason: collision with root package name */
        private boolean f23676f = false;

        /* renamed from: a, reason: collision with other field name */
        private List<String> f341a = Collections.emptyList();

        /* renamed from: c, reason: collision with root package name */
        private int f23673c = -1;

        /* renamed from: b, reason: collision with other method in class */
        public boolean m320b() {
            return this.f344c;
        }

        public int c() {
            return this.f23671a;
        }

        public int d() {
            return this.f23672b;
        }

        /* renamed from: e, reason: collision with other method in class */
        public boolean m323e() {
            return this.f23676f;
        }

        public boolean f() {
            return this.f23675e;
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m319a() {
            return this.f342a;
        }

        public a b(int i2) {
            this.f23674d = true;
            this.f23672b = i2;
            return this;
        }

        /* renamed from: c, reason: collision with other method in class */
        public boolean m321c() {
            return this.f343b;
        }

        /* renamed from: d, reason: collision with other method in class */
        public boolean m322d() {
            return this.f23674d;
        }

        public int e() {
            return this.f341a.size();
        }

        public a a(int i2) {
            this.f342a = true;
            this.f23671a = i2;
            return this;
        }

        public a b(boolean z2) {
            this.f23675e = true;
            this.f23676f = z2;
            return this;
        }

        public a a(boolean z2) {
            this.f343b = true;
            this.f344c = z2;
            return this;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iA = 0;
            int iB = m319a() ? c.b(1, c()) : 0;
            if (m321c()) {
                iB += c.a(2, m320b());
            }
            if (m322d()) {
                iB += c.a(3, d());
            }
            if (f()) {
                iB += c.a(4, m323e());
            }
            Iterator<String> it = m318a().iterator();
            while (it.hasNext()) {
                iA += c.a(it.next());
            }
            int size = iB + iA + m318a().size();
            this.f23673c = size;
            return size;
        }

        /* renamed from: a, reason: collision with other method in class */
        public List<String> m318a() {
            return this.f341a;
        }

        public a a(String str) {
            str.getClass();
            if (this.f341a.isEmpty()) {
                this.f341a = new ArrayList();
            }
            this.f341a.add(str);
            return this;
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (m319a()) {
                cVar.m240b(1, c());
            }
            if (m321c()) {
                cVar.m232a(2, m320b());
            }
            if (m322d()) {
                cVar.m227a(3, d());
            }
            if (f()) {
                cVar.m232a(4, m323e());
            }
            Iterator<String> it = m318a().iterator();
            while (it.hasNext()) {
                cVar.m231a(5, it.next());
            }
        }

        public static a b(b bVar) {
            return new a().a(bVar);
        }

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f23673c < 0) {
                b();
            }
            return this.f23673c;
        }

        @Override // com.xiaomi.push.e
        public a a(b bVar) throws d {
            while (true) {
                int iM187a = bVar.m187a();
                if (iM187a == 0) {
                    return this;
                }
                if (iM187a == 8) {
                    a(bVar.c());
                } else if (iM187a == 16) {
                    a(bVar.m193a());
                } else if (iM187a == 24) {
                    b(bVar.m196b());
                } else if (iM187a == 32) {
                    b(bVar.m193a());
                } else if (iM187a != 42) {
                    if (!a(bVar, iM187a)) {
                        return this;
                    }
                } else {
                    a(bVar.m190a());
                }
            }
        }

        public static a a(byte[] bArr) {
            return (a) new a().a(bArr);
        }
    }
}
