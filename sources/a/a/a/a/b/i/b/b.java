package a.a.a.a.b.i.b;

import a.a.a.a.a.a.b.a.d;
import a.a.a.a.b.m.c;
import android.text.TextUtils;
import b.InterfaceC0329d;
import com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionV2StatusCallback;
import com.alibaba.ailabs.iot.mesh.provision.state.FastProvisioningState;
import com.alibaba.ailabs.iot.mesh.utils.AliMeshUUIDParserUtil;
import datasource.bean.ProvisionInfo;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNode;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes.dex */
public class b extends FastProvisioningState {

    /* renamed from: a, reason: collision with root package name */
    public final String f1424a = "InexpensiveMesh" + b.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    public final UnprovisionedMeshNode f1425b;

    /* renamed from: c, reason: collision with root package name */
    public final FastProvisionV2StatusCallback f1426c;

    /* renamed from: d, reason: collision with root package name */
    public final InterfaceC0329d f1427d;

    /* renamed from: e, reason: collision with root package name */
    public final ProvisionInfo f1428e;

    public b(UnprovisionedMeshNode unprovisionedMeshNode, FastProvisionV2StatusCallback fastProvisionV2StatusCallback, InterfaceC0329d interfaceC0329d, ProvisionInfo provisionInfo) {
        this.f1425b = unprovisionedMeshNode;
        this.f1426c = fastProvisionV2StatusCallback;
        this.f1427d = interfaceC0329d;
        this.f1428e = provisionInfo;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.state.FastProvisioningState
    public void a() {
        d();
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.state.FastProvisioningState
    public FastProvisioningState.State b() {
        return FastProvisioningState.State.PROVISINING_DATA;
    }

    public final byte[] c() {
        String strExtractMacAddressFromUUID = AliMeshUUIDParserUtil.extractMacAddressFromUUID(MeshParserUtils.bytesToHex(this.f1425b.getServiceData(), false));
        if (TextUtils.isEmpty(strExtractMacAddressFromUUID)) {
            a.a.a.a.b.m.a.d(this.f1424a, "Can not extract mac address from UUID");
            strExtractMacAddressFromUUID = this.f1425b.getBluetoothAddress();
        }
        d dVar = new d(c.a(strExtractMacAddressFromUUID), (byte) 0, this.f1425b.getNetworkKey(), this.f1425b.getIvIndex()[0], AddressUtils.getUnicastAddressBytes(this.f1428e.getPrimaryUnicastAddress().intValue()), this.f1428e.getServerConfirmation());
        if (dVar.b()) {
            return dVar.a();
        }
        return null;
    }

    public final void d() {
        FastProvisionV2StatusCallback fastProvisionV2StatusCallback;
        byte[] bArrC = c();
        if (bArrC != null || (fastProvisionV2StatusCallback = this.f1426c) == null) {
            this.f1427d.sendPdu(this.f1425b, bArrC);
        } else {
            fastProvisionV2StatusCallback.onProvisioningFailed(this.f1425b, -60, "failed to generate encrypted provision data");
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.state.FastProvisioningState
    public boolean a(byte[] bArr) {
        if (bArr == null || bArr.length < 1 || bArr[0] != 3) {
            return false;
        }
        ProvisionedMeshNode provisionedMeshNode = new ProvisionedMeshNode(this.f1425b);
        provisionedMeshNode.setDeviceKey(a(this.f1428e.getServerConfirmation()));
        provisionedMeshNode.setUnicastAddress(AddressUtils.getUnicastAddressBytes(this.f1428e.getPrimaryUnicastAddress().intValue()));
        FastProvisionV2StatusCallback fastProvisionV2StatusCallback = this.f1426c;
        if (fastProvisionV2StatusCallback != null) {
            fastProvisionV2StatusCallback.onProvisioningComplete(provisionedMeshNode);
        }
        return true;
    }

    public final byte[] a(String str) {
        try {
            byte[] bArrCalculateSha256 = SecureUtils.calculateSha256((str + "DeviceKey").getBytes("ASCII"));
            if (bArrCalculateSha256 == null || bArrCalculateSha256.length < 16) {
                return null;
            }
            byte[] bArr = new byte[16];
            System.arraycopy(bArrCalculateSha256, 0, bArr, 0, 16);
            return bArr;
        } catch (UnsupportedEncodingException | UnsupportedCharsetException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
