package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class n8 extends d0 {
    n8() {
    }

    @Override // com.huawei.hms.scankit.p.d0
    public int a() {
        return 3;
    }

    @Override // com.huawei.hms.scankit.p.d0, com.huawei.hms.scankit.p.v2
    public void a(y2 y2Var) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!y2Var.i()) {
                break;
            }
            char c2 = y2Var.c();
            y2Var.f18021f++;
            a(c2, sb);
            if (sb.length() % 3 == 0) {
                d0.b(y2Var, sb);
                if (d4.a(y2Var.d(), y2Var.f18021f, a()) != a()) {
                    y2Var.b(0);
                    break;
                }
            }
        }
        a(y2Var, sb);
    }

    @Override // com.huawei.hms.scankit.p.d0
    int a(char c2, StringBuilder sb) {
        if (c2 == '\r') {
            sb.append((char) 0);
        } else if (c2 == ' ') {
            sb.append((char) 3);
        } else if (c2 == '*') {
            sb.append((char) 1);
        } else if (c2 == '>') {
            sb.append((char) 2);
        } else if (c2 >= '0' && c2 <= '9') {
            sb.append((char) (c2 - ','));
        } else if (c2 >= 'A' && c2 <= 'Z') {
            sb.append((char) (c2 - '3'));
        } else {
            d4.a(c2);
        }
        return 1;
    }

    @Override // com.huawei.hms.scankit.p.d0
    void a(y2 y2Var, StringBuilder sb) {
        y2Var.l();
        int iA = y2Var.g().a() - y2Var.a();
        y2Var.f18021f -= sb.length();
        if (y2Var.f() > 1 || iA > 1 || y2Var.f() != iA) {
            y2Var.a((char) 254);
        }
        if (y2Var.e() < 0) {
            y2Var.b(0);
        }
    }
}
