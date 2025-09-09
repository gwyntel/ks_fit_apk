package a.a.a.a.b.i.b;

import a.a.a.a.b.G;
import a.a.a.a.b.m.c;
import android.annotation.SuppressLint;
import android.text.TextUtils;
import b.InterfaceC0329d;
import com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionV2StatusCallback;
import com.alibaba.ailabs.iot.mesh.provision.state.FastProvisioningState;
import com.alibaba.ailabs.iot.mesh.utils.AliMeshUUIDParserUtil;
import com.alibaba.fastjson.JSONObject;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import datasource.bean.ProvisionInfo;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes.dex */
public class a extends FastProvisioningState {

    /* renamed from: b, reason: collision with root package name */
    public final ProvisionedMeshNode f1419b;

    /* renamed from: c, reason: collision with root package name */
    public final FastProvisionV2StatusCallback f1420c;

    /* renamed from: d, reason: collision with root package name */
    public final InterfaceC0329d f1421d;

    /* renamed from: e, reason: collision with root package name */
    public final ProvisionInfo f1422e;

    /* renamed from: a, reason: collision with root package name */
    public final String f1418a = "InexpensiveMesh" + a.class.getSimpleName();

    /* renamed from: f, reason: collision with root package name */
    public String f1423f = null;

    public a(ProvisionedMeshNode provisionedMeshNode, FastProvisionV2StatusCallback fastProvisionV2StatusCallback, InterfaceC0329d interfaceC0329d, ProvisionInfo provisionInfo) {
        List<String> appKeys;
        this.f1419b = provisionedMeshNode;
        this.f1420c = fastProvisionV2StatusCallback;
        this.f1421d = interfaceC0329d;
        this.f1422e = provisionInfo;
        if (provisionInfo.getAppKeyIndexes() == null || (appKeys = provisionInfo.getAppKeys()) == null) {
            return;
        }
        Iterator<Integer> it = provisionInfo.getAppKeyIndexes().iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            if (iIntValue < appKeys.size()) {
                provisionedMeshNode.setAddedAppKey(iIntValue, appKeys.get(iIntValue));
            }
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.state.FastProvisioningState
    public void a() {
        this.f1421d.sendPdu(this.f1419b, d());
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.state.FastProvisioningState
    public FastProvisioningState.State b() {
        return FastProvisioningState.State.PROVISIONING_ADDAPPKEY;
    }

    public String c() {
        return this.f1423f;
    }

    public final byte[] d() {
        Integer num = this.f1422e.getAppKeyIndexes() != null ? this.f1422e.getAppKeyIndexes().get(0) : null;
        if (num == null) {
            num = 0;
        }
        String str = this.f1422e.getAppKeys() != null ? this.f1422e.getAppKeys().get(0) : ((ProvisionedMeshNode) G.a().b()).getAddedAppKeys().get(0);
        a.a.a.a.b.m.a.a(this.f1418a, "appKey = " + str);
        byte[] byteArray = MeshParserUtils.toByteArray(str);
        String strExtractMacAddressFromUUID = AliMeshUUIDParserUtil.extractMacAddressFromUUID(this.f1419b.getDevId());
        if (TextUtils.isEmpty(strExtractMacAddressFromUUID)) {
            a.a.a.a.b.m.a.d(this.f1418a, "Can not extract mac address from UUID");
            strExtractMacAddressFromUUID = this.f1419b.getBluetoothAddress();
        }
        return new a.a.a.a.a.a.b.a.a(c.a(strExtractMacAddressFromUUID), (byte) num.intValue(), byteArray, this.f1422e.getServerConfirmation()).a();
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.state.FastProvisioningState
    @SuppressLint({"DefaultLocale"})
    public boolean a(byte[] bArr) {
        if (bArr != null && bArr.length >= 1 && bArr[0] == 10) {
            byte b2 = bArr[1];
            if (b2 == 0) {
                if (bArr.length > 7) {
                    byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 7, bArr.length);
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("opcode", (Object) "D301A8");
                    jSONObject.put(PushConstants.PARAMS, (Object) MeshParserUtils.bytesToHex(bArrCopyOfRange, false));
                    this.f1423f = jSONObject.toJSONString();
                }
                return true;
            }
            FastProvisionV2StatusCallback fastProvisionV2StatusCallback = this.f1420c;
            ProvisionedMeshNode provisionedMeshNode = this.f1419b;
            fastProvisionV2StatusCallback.onProvisioningFailed(provisionedMeshNode, -62, String.format("device(%s) responded that the appKey addition failed, status: %d", provisionedMeshNode.getBluetoothAddress(), Byte.valueOf(b2)));
        }
        return false;
    }
}
