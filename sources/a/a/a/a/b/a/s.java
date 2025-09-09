package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import meshprovisioner.utils.AddressUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class s implements SIGMeshBizRequest.b {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1287a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f1288b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f1289c;

    public s(int i2, int i3, int i4) {
        this.f1287a = i2;
        this.f1288b = i3;
        this.f1289c = i4;
    }

    @Override // com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest.b
    public byte[] getEncodedParameters() {
        byte[] unicastAddressBytes = AddressUtils.getUnicastAddressBytes(this.f1287a);
        byte[] unicastAddressBytes2 = AddressUtils.getUnicastAddressBytes(this.f1288b);
        int i2 = this.f1289c;
        if (i2 >= -32768 && i2 <= 32767) {
            ByteBuffer byteBufferOrder = ByteBuffer.allocate(6).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder.put(unicastAddressBytes[1]);
            byteBufferOrder.put(unicastAddressBytes[0]);
            byteBufferOrder.put(unicastAddressBytes2[1]);
            byteBufferOrder.put(unicastAddressBytes2[0]);
            byteBufferOrder.putShort((short) this.f1289c);
            return byteBufferOrder.array();
        }
        ByteBuffer byteBufferOrder2 = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder2.put(unicastAddressBytes[1]);
        byteBufferOrder2.put(unicastAddressBytes[0]);
        byteBufferOrder2.put(unicastAddressBytes2[1]);
        byteBufferOrder2.put(unicastAddressBytes2[0]);
        int i3 = this.f1289c;
        byte[] bArr = {(byte) ((i3 >> 24) & 255), (byte) ((i3 >> 16) & 255), (byte) ((i3 >> 8) & 255), (byte) (i3 & 255)};
        byteBufferOrder2.put(bArr[1]);
        byteBufferOrder2.put(bArr[0]);
        byteBufferOrder2.put(bArr[3]);
        byteBufferOrder2.put(bArr[2]);
        return byteBufferOrder2.array();
    }
}
