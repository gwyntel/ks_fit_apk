package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class b implements v2 {
    b() {
    }

    public int a() {
        return 0;
    }

    @Override // com.huawei.hms.scankit.p.v2
    public void a(y2 y2Var) {
        if (d4.a(y2Var.d(), y2Var.f18021f) >= 2) {
            y2Var.a(a(y2Var.d().charAt(y2Var.f18021f), y2Var.d().charAt(y2Var.f18021f + 1)));
            y2Var.f18021f += 2;
            return;
        }
        char c2 = y2Var.c();
        int iA = d4.a(y2Var.d(), y2Var.f18021f, a());
        if (iA == a()) {
            if (!d4.c(c2)) {
                y2Var.a((char) (c2 + 1));
                y2Var.f18021f++;
                return;
            } else {
                y2Var.a((char) 235);
                y2Var.a((char) (c2 - 127));
                y2Var.f18021f++;
                return;
            }
        }
        if (iA == 1) {
            y2Var.a((char) 230);
            y2Var.b(1);
            return;
        }
        if (iA == 2) {
            y2Var.a((char) 239);
            y2Var.b(2);
            return;
        }
        if (iA == 3) {
            y2Var.a((char) 238);
            y2Var.b(3);
            return;
        }
        if (iA == 4) {
            y2Var.a((char) 240);
            y2Var.b(4);
        } else if (iA == 5) {
            y2Var.a((char) 231);
            y2Var.b(5);
        } else {
            throw new IllegalStateException("Illegal mode: " + iA);
        }
    }

    private static char a(char c2, char c3) {
        if (d4.b(c2) && d4.b(c3)) {
            return (char) (((c2 - '0') * 10) + (c3 - '0') + 130);
        }
        throw new IllegalArgumentException("not digits: " + c2 + c3);
    }
}
