package b;

import com.alibaba.ailabs.iot.aisbase.spec.TLV;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import java.io.UnsupportedEncodingException;
import java.util.List;

/* renamed from: b.i, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0334i implements IActionListener<Object> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f7489a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0337l f7490b;

    public C0334i(C0337l c0337l, IActionListener iActionListener) {
        this.f7490b = c0337l;
        this.f7489a = iActionListener;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f7490b.f7507n.removeCallbacks(this.f7490b.f7508o);
        a.a.a.a.b.m.a.c(C0337l.f7494a, "sendVendorCommonMessage onFailure errorCode = " + i2 + "; desc = " + str);
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onSuccess(Object obj) {
        List<TLV> multiFromBytes;
        byte b2;
        this.f7490b.f7507n.removeCallbacks(this.f7490b.f7508o);
        a.a.a.a.b.m.a.c(C0337l.f7494a, "sendVendorCommonMessage onSuccess result = " + obj);
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            String str = null;
            byte b3 = 0;
            if (bArr == null || bArr.length <= 3) {
                multiFromBytes = null;
            } else {
                int length = bArr.length - 3;
                byte[] bArr2 = new byte[length];
                System.arraycopy(bArr, 3, bArr2, 0, length);
                multiFromBytes = TLV.parseMultiFromBytes(bArr2);
            }
            if (multiFromBytes == null || multiFromBytes.size() <= 0) {
                b2 = 0;
            } else {
                byte b4 = 0;
                b2 = 0;
                for (TLV tlv : multiFromBytes) {
                    if (tlv.getType() == 1) {
                        b4 = tlv.getValue()[0];
                    } else if (tlv.getType() == 2) {
                        try {
                            str = new String(tlv.getValue(), "UTF-8");
                        } catch (UnsupportedEncodingException e2) {
                            e2.printStackTrace();
                        }
                    } else if (tlv.getType() == 3) {
                        b2 = tlv.getValue()[0];
                    }
                }
                b3 = b4;
            }
            if (b3 == 1) {
                this.f7489a.onSuccess(Byte.valueOf(b3));
            } else {
                this.f7489a.onFailure(b3, "wifi config failure");
            }
            a.a.a.a.b.m.a.c(C0337l.f7494a, "sendVendorCommonMessage onSuccess code = " + ((int) b3) + "; type = " + ((int) b2) + "; message = " + str);
        }
    }
}
