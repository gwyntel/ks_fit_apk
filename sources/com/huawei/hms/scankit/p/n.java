package com.huawei.hms.scankit.p;

import androidx.core.view.InputDeviceCompat;

/* loaded from: classes4.dex */
final class n implements v2 {
    n() {
    }

    public int a() {
        return 5;
    }

    @Override // com.huawei.hms.scankit.p.v2
    public void a(y2 y2Var) {
        StringBuilder sb = new StringBuilder();
        sb.append((char) 0);
        while (true) {
            if (!y2Var.i()) {
                break;
            }
            sb.append(y2Var.c());
            y2Var.f18021f++;
            if (d4.a(y2Var.d(), y2Var.f18021f, a()) != a()) {
                y2Var.b(0);
                break;
            }
        }
        int length = sb.length() - 1;
        int iA = y2Var.a() + length + 1;
        y2Var.c(iA);
        boolean z2 = y2Var.g().a() - iA > 0;
        if (y2Var.i() || z2) {
            if (length <= 249) {
                sb.setCharAt(0, (char) length);
            } else {
                if (length > 1555) {
                    throw new IllegalStateException("Message length not in valid ranges: " + length);
                }
                sb.setCharAt(0, (char) ((length / 250) + 249));
                sb.insert(1, (char) (length % 250));
            }
        }
        int length2 = sb.length();
        for (int i2 = 0; i2 < length2; i2++) {
            y2Var.a(a(sb.charAt(i2), y2Var.a() + 1));
        }
    }

    private static char a(char c2, int i2) {
        int i3 = c2 + ((i2 * 149) % 255) + 1;
        return i3 <= 255 ? (char) i3 : (char) (i3 + InputDeviceCompat.SOURCE_ANY);
    }
}
