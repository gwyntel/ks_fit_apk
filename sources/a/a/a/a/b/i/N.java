package a.a.a.a.b.i;

import android.util.Pair;
import com.alibaba.ailabs.iot.aisbase.spec.TLV;
import java.io.UnsupportedEncodingException;
import java.util.List;

/* loaded from: classes.dex */
public class N implements a.a.a.a.b.a.I<byte[]> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ P f1402a;

    public N(P p2) {
        this.f1402a = p2;
    }

    @Override // a.a.a.a.b.a.I
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public Pair<Integer, Object> parseResponse(byte[] bArr) {
        List<TLV> multiFromBytes;
        if (bArr == null || bArr.length <= 3) {
            multiFromBytes = null;
        } else {
            int length = bArr.length - 3;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 3, bArr2, 0, length);
            multiFromBytes = TLV.parseMultiFromBytes(bArr2);
        }
        if (multiFromBytes == null || multiFromBytes.size() <= 0) {
            return new Pair<>(-32, "");
        }
        byte b2 = 0;
        for (TLV tlv : multiFromBytes) {
            if (tlv.getType() == 1) {
                b2 = tlv.getValue()[0];
            } else if (tlv.getType() == 2) {
                try {
                    new String(tlv.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                }
            } else if (tlv.getType() == 3) {
                byte b3 = tlv.getValue()[0];
            }
        }
        a.a.a.a.b.m.a.c(this.f1402a.f1405b, String.format("D3 ack recevied, code: %02X", Byte.valueOf(b2)));
        return b2 == 1 ? new Pair<>(0, Byte.valueOf(b2)) : new Pair<>(-70, String.valueOf((int) b2));
    }
}
