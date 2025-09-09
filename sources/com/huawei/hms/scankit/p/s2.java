package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class s2 implements v2 {
    s2() {
    }

    public int a() {
        return 4;
    }

    @Override // com.huawei.hms.scankit.p.v2
    public void a(y2 y2Var) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!y2Var.i()) {
                break;
            }
            a(y2Var.c(), sb);
            y2Var.f18021f++;
            if (sb.length() >= 4) {
                y2Var.a(a(sb, 0));
                sb.delete(0, 4);
                if (d4.a(y2Var.d(), y2Var.f18021f, a()) != a()) {
                    y2Var.b(0);
                    break;
                }
            }
        }
        sb.append((char) 31);
        a(y2Var, sb);
    }

    private static void a(y2 y2Var, CharSequence charSequence) {
        try {
            int length = charSequence.length();
            if (length == 0) {
                return;
            }
            boolean z2 = true;
            if (length == 1) {
                y2Var.l();
                int iA = y2Var.g().a() - y2Var.a();
                int iF = y2Var.f();
                if (iF > iA) {
                    y2Var.c(y2Var.a() + 1);
                    iA = y2Var.g().a() - y2Var.a();
                }
                if (iF <= iA && iA <= 2) {
                    return;
                }
            }
            if (length <= 4) {
                int i2 = length - 1;
                String strA = a(charSequence, 0);
                if (!(!y2Var.i()) || i2 > 2) {
                    z2 = false;
                }
                if (i2 <= 2) {
                    y2Var.c(y2Var.a() + i2);
                    if (y2Var.g().a() - y2Var.a() >= 3) {
                        y2Var.c(y2Var.a() + strA.length());
                        z2 = false;
                    }
                }
                if (z2) {
                    y2Var.k();
                    y2Var.f18021f -= i2;
                } else {
                    y2Var.a(strA);
                }
                return;
            }
            throw new IllegalStateException("Count must not exceed 4");
        } finally {
            y2Var.b(0);
        }
    }

    private static void a(char c2, StringBuilder sb) {
        if (c2 >= ' ' && c2 <= '?') {
            sb.append(c2);
        } else if (c2 >= '@' && c2 <= '^') {
            sb.append((char) (c2 - '@'));
        } else {
            d4.a(c2);
        }
    }

    private static String a(CharSequence charSequence, int i2) {
        int length = charSequence.length() - i2;
        if (length != 0) {
            int iCharAt = (charSequence.charAt(i2) << 18) + ((length >= 2 ? charSequence.charAt(i2 + 1) : (char) 0) << '\f') + ((length >= 3 ? charSequence.charAt(i2 + 2) : (char) 0) << 6) + (length >= 4 ? charSequence.charAt(i2 + 3) : (char) 0);
            char c2 = (char) ((iCharAt >> 16) & 255);
            char c3 = (char) ((iCharAt >> 8) & 255);
            char c4 = (char) (iCharAt & 255);
            StringBuilder sb = new StringBuilder(3);
            sb.append(c2);
            if (length >= 2) {
                sb.append(c3);
            }
            if (length >= 3) {
                sb.append(c4);
            }
            return sb.toString();
        }
        throw new IllegalStateException("StringBuilder must not be empty");
    }
}
