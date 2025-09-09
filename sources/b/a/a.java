package b.a;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import meshprovisioner.control.TransportControlMessage;

/* loaded from: classes2.dex */
public class a extends TransportControlMessage {

    /* renamed from: a, reason: collision with root package name */
    public static final String f7363a = "a";

    public a(byte[] bArr) {
    }

    public static Integer a(Integer num, int i2) {
        return num == null ? Integer.valueOf(1 << i2) : Integer.valueOf(num.intValue() | (1 << i2));
    }

    public static boolean b(Integer num, int i2) {
        int iIntValue = num.intValue();
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            if (((iIntValue >> i4) & 1) == 1) {
                i3++;
            }
        }
        return i3 == i2;
    }

    @Override // meshprovisioner.control.TransportControlMessage
    public TransportControlMessage.TransportControlMessageState a() {
        return TransportControlMessage.TransportControlMessageState.LOWER_TRANSPORT_BLOCK_ACKNOWLEDGEMENT;
    }

    public static ArrayList<Integer> a(byte[] bArr, int i2) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        int i3 = ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getInt();
        for (int i4 = 0; i4 < i2; i4++) {
            if (((i3 >> i4) & 1) == 1) {
                String str = f7363a;
                StringBuilder sb = new StringBuilder();
                sb.append("Segment ");
                sb.append(i4);
                sb.append(" of ");
                sb.append(i2 - 1);
                sb.append(" received by peer");
                a.a.a.a.b.m.a.a(str, sb.toString());
            } else {
                arrayList.add(Integer.valueOf(i4));
                String str2 = f7363a;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Segment ");
                sb2.append(i4);
                sb2.append(" of ");
                sb2.append(i2 - 1);
                sb2.append(" not received by peer");
                a.a.a.a.b.m.a.a(str2, sb2.toString());
            }
        }
        return arrayList;
    }
}
