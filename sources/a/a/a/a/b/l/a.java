package a.a.a.a.b.l;

import com.alibaba.ailabs.iot.mesh.bean.MeshAccessPayload;
import com.alibaba.ailabs.iot.mesh.callback.MeshMsgListener;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import datasource.bean.SigmeshIotDev;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes.dex */
class a implements MeshMsgListener {
    @Override // com.alibaba.ailabs.iot.mesh.callback.MeshMsgListener
    public void onReceiveMeshMessage(byte[] bArr, MeshAccessPayload meshAccessPayload) {
        short s2 = ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getShort();
        a.a.a.a.b.m.a.c("ControlMsgUtUtil", "onReceiveMeshMessage openCode " + meshAccessPayload.getOpCode() + ", address " + ((int) s2));
        if (meshAccessPayload.getOpCode().equalsIgnoreCase("D3A801")) {
            SigmeshIotDev sigmeshIotDevQueryDeviceInfoByUnicastAddress = MeshDeviceInfoManager.getInstance().queryDeviceInfoByUnicastAddress(s2, meshAccessPayload.getNetKey());
            String hexString = Hex.toHexString(meshAccessPayload.getParameters());
            if (sigmeshIotDevQueryDeviceInfoByUnicastAddress == null) {
                c.a(s2, "", "", hexString);
            } else {
                c.a(s2, sigmeshIotDevQueryDeviceInfoByUnicastAddress.getDevId(), sigmeshIotDevQueryDeviceInfoByUnicastAddress.getProductKey(), hexString);
            }
        }
    }
}
