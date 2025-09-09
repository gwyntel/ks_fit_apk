package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
final class g7 extends d0 {
    g7() {
    }

    @Override // com.huawei.hms.scankit.p.d0
    public int a() {
        return 2;
    }

    @Override // com.huawei.hms.scankit.p.d0
    int a(char c2, StringBuilder sb) {
        if (c2 == ' ') {
            sb.append((char) 3);
            return 1;
        }
        if (c2 >= '0' && c2 <= '9') {
            sb.append((char) (c2 - ','));
            return 1;
        }
        if (c2 >= 'a' && c2 <= 'z') {
            sb.append((char) (c2 - 'S'));
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
        if (c2 == '`') {
            sb.append((char) 2);
            sb.append((char) (c2 - '`'));
            return 2;
        }
        if (c2 >= 'A' && c2 <= 'Z') {
            sb.append((char) 2);
            sb.append((char) (c2 - '@'));
            return 2;
        }
        if (c2 < '{' || c2 > 127) {
            sb.append("\u0001\u001e");
            return a((char) (c2 - 128), sb) + 2;
        }
        sb.append((char) 2);
        sb.append((char) (c2 - '`'));
        return 2;
    }
}
