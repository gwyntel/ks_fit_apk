package com.umeng.analytics.filter;

import android.util.Base64;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: b, reason: collision with root package name */
    private static final String f21261b = "Ă";

    /* renamed from: c, reason: collision with root package name */
    private MessageDigest f21263c;

    /* renamed from: e, reason: collision with root package name */
    private boolean f21265e;

    /* renamed from: a, reason: collision with root package name */
    private final String f21262a = Utils.MD5;

    /* renamed from: d, reason: collision with root package name */
    private Set<Object> f21264d = new HashSet();

    public d(boolean z2, String str) {
        this.f21265e = z2;
        try {
            this.f21263c = MessageDigest.getInstance(Utils.MD5);
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        }
        if (str != null) {
            int i2 = 0;
            if (!z2) {
                String[] strArrSplit = str.split(f21261b);
                int length = strArrSplit.length;
                while (i2 < length) {
                    this.f21264d.add(strArrSplit[i2]);
                    i2++;
                }
                return;
            }
            try {
                byte[] bArrDecode = Base64.decode(str.getBytes(), 0);
                while (i2 < bArrDecode.length / 4) {
                    int i3 = i2 * 4;
                    this.f21264d.add(Integer.valueOf(((bArrDecode[i3] & 255) << 24) + ((bArrDecode[i3 + 1] & 255) << 16) + ((bArrDecode[i3 + 2] & 255) << 8) + (bArrDecode[i3 + 3] & 255)));
                    i2++;
                }
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            }
        }
    }

    private Integer c(String str) {
        try {
            this.f21263c.update(str.getBytes());
            byte[] bArrDigest = this.f21263c.digest();
            return Integer.valueOf(((bArrDigest[0] & 255) << 24) + ((bArrDigest[1] & 255) << 16) + ((bArrDigest[2] & 255) << 8) + (bArrDigest[3] & 255));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public boolean a(String str) {
        return this.f21265e ? this.f21264d.contains(c(str)) : this.f21264d.contains(str);
    }

    public void b(String str) {
        if (this.f21265e) {
            this.f21264d.add(c(str));
        } else {
            this.f21264d.add(str);
        }
    }

    public String toString() {
        if (!this.f21265e) {
            StringBuilder sb = new StringBuilder();
            for (Object obj : this.f21264d) {
                if (sb.length() > 0) {
                    sb.append(f21261b);
                }
                sb.append(obj.toString());
            }
            return sb.toString();
        }
        byte[] bArr = new byte[this.f21264d.size() * 4];
        Iterator<Object> it = this.f21264d.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            bArr[i2] = (byte) (((-16777216) & iIntValue) >> 24);
            bArr[i2 + 1] = (byte) ((16711680 & iIntValue) >> 16);
            int i3 = i2 + 3;
            bArr[i2 + 2] = (byte) ((65280 & iIntValue) >> 8);
            i2 += 4;
            bArr[i3] = (byte) (iIntValue & 255);
        }
        return new String(Base64.encode(bArr, 0));
    }

    public void a() {
        StringBuilder sb = new StringBuilder();
        Iterator<Object> it = this.f21264d.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (sb.length() > 0) {
                sb.append(",");
            }
        }
        System.out.println(sb.toString());
    }
}
