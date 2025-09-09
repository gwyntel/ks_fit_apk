package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
class d0 implements v2 {
    d0() {
    }

    static void b(y2 y2Var, StringBuilder sb) {
        y2Var.a(a(sb, 0));
        sb.delete(0, 3);
    }

    public int a() {
        return 1;
    }

    @Override // com.huawei.hms.scankit.p.v2
    public void a(y2 y2Var) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!y2Var.i()) {
                break;
            }
            char c2 = y2Var.c();
            y2Var.f18021f++;
            int iA = a(c2, sb);
            int iA2 = y2Var.a() + ((sb.length() / 3) * 2);
            y2Var.c(iA2);
            int iA3 = y2Var.g().a() - iA2;
            if (!y2Var.i()) {
                StringBuilder sb2 = new StringBuilder();
                if (sb.length() % 3 == 2 && (iA3 < 2 || iA3 > 2)) {
                    iA = a(y2Var, sb, sb2, iA);
                }
                while (sb.length() % 3 == 1 && ((iA <= 3 && iA3 != 1) || iA > 3)) {
                    iA = a(y2Var, sb, sb2, iA);
                }
            } else if (sb.length() % 3 == 0 && d4.a(y2Var.d(), y2Var.f18021f, a()) != a()) {
                y2Var.b(0);
                break;
            }
        }
        a(y2Var, sb);
    }

    private int a(y2 y2Var, StringBuilder sb, StringBuilder sb2, int i2) {
        int length = sb.length();
        sb.delete(length - i2, length);
        y2Var.f18021f--;
        int iA = a(y2Var.c(), sb2);
        y2Var.k();
        return iA;
    }

    void a(y2 y2Var, StringBuilder sb) {
        int length = (sb.length() / 3) * 2;
        int length2 = sb.length() % 3;
        int iA = y2Var.a() + length;
        y2Var.c(iA);
        int iA2 = y2Var.g().a() - iA;
        if (length2 == 2) {
            sb.append((char) 0);
            while (sb.length() >= 3) {
                b(y2Var, sb);
            }
            if (y2Var.i()) {
                y2Var.a((char) 254);
            }
        } else if (iA2 == 1 && length2 == 1) {
            while (sb.length() >= 3) {
                b(y2Var, sb);
            }
            if (y2Var.i()) {
                y2Var.a((char) 254);
            }
            y2Var.f18021f--;
        } else if (length2 == 0) {
            while (sb.length() >= 3) {
                b(y2Var, sb);
            }
            if (iA2 > 0 || y2Var.i()) {
                y2Var.a((char) 254);
            }
        } else {
            try {
                throw new IllegalStateException("Unexpected case. Please report!");
            } catch (Exception unused) {
                o4.b("exception", "Exception");
            }
        }
        y2Var.b(0);
    }

    int a(char c2, StringBuilder sb) {
        if (c2 == ' ') {
            sb.append((char) 3);
            return 1;
        }
        if (c2 >= '0' && c2 <= '9') {
            sb.append((char) (c2 - ','));
            return 1;
        }
        if (c2 >= 'A' && c2 <= 'Z') {
            sb.append((char) (c2 - '3'));
            return 1;
        }
        if (c2 < ' ') {
            sb.append((char) 0);
            sb.append(c2);
            return 2;
        }
        if (c2 >= '!' && c2 <= '/') {
            sb.append((char) 1);
            sb.append((char) (c2 - '!'));
            return 2;
        }
        if (c2 >= ':' && c2 <= '@') {
            sb.append((char) 1);
            sb.append((char) (c2 - '+'));
            return 2;
        }
        if (c2 >= '[' && c2 <= '_') {
            sb.append((char) 1);
            sb.append((char) (c2 - 'E'));
            return 2;
        }
        if (c2 >= '`' && c2 <= 127) {
            sb.append((char) 2);
            sb.append((char) (c2 - '`'));
            return 2;
        }
        sb.append("\u0001\u001e");
        return a((char) (c2 - 128), sb) + 2;
    }

    private static String a(CharSequence charSequence, int i2) {
        int iCharAt = (charSequence.charAt(i2) * 1600) + (charSequence.charAt(i2 + 1) * '(') + charSequence.charAt(i2 + 2) + 1;
        return new String(new char[]{(char) (iCharAt / 256), (char) (iCharAt % 256)});
    }
}
