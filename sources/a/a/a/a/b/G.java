package a.a.a.a.b;

import android.text.TextUtils;
import android.util.SparseArray;
import b.u;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import com.alibaba.ailabs.iot.mesh.utils.AliMeshUUIDParserUtil;
import com.alibaba.ailabs.tg.utils.ListUtils;
import com.alibaba.android.multiendinonebridge.IoTMultiendInOneBridge;
import com.alibaba.fastjson.JSONObject;
import com.facebook.share.internal.MessengerShareContentUtility;
import datasource.bean.AddModelClient;
import datasource.bean.ProvisionAppKey;
import datasource.bean.ProvisionInfo4Master;
import datasource.bean.SigmeshIotDev;
import datasource.bean.SigmeshKey;
import datasource.bean.SubscribeGroupAddr_t;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.configuration.MeshModel;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.models.SigModelParser;
import meshprovisioner.models.VendorModel;
import meshprovisioner.states.UnprovisionedMeshNode;
import meshprovisioner.utils.Element;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes.dex */
public class G {

    /* renamed from: a, reason: collision with root package name */
    public static final String f1198a = "G";

    /* renamed from: b, reason: collision with root package name */
    public static G f1199b = new G();

    /* renamed from: c, reason: collision with root package name */
    public BaseMeshNode f1200c;

    /* renamed from: d, reason: collision with root package name */
    public b.u f1201d = new b.u();

    /* renamed from: e, reason: collision with root package name */
    public ProvisionInfo4Master f1202e;

    public final boolean a(int i2) {
        return i2 > 65535;
    }

    public void b(ProvisionInfo4Master provisionInfo4Master) {
        a.a.a.a.b.m.a.a(f1198a, "Init...");
        if (provisionInfo4Master != null) {
            this.f1202e = provisionInfo4Master;
            List<SigmeshIotDev> sigmeshIotDevList = provisionInfo4Master.getSigmeshIotDevList();
            if (sigmeshIotDevList != null && sigmeshIotDevList.size() > 0) {
                for (SigmeshIotDev sigmeshIotDev : sigmeshIotDevList) {
                    if ("9158416".equalsIgnoreCase(sigmeshIotDev.getProductKey())) {
                        sigmeshIotDev.setProductKey("a1QHXcgBchU");
                    }
                }
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("deviceList", (Object) sigmeshIotDevList);
                jSONObject.put(MessengerShareContentUtility.ATTACHMENT_PAYLOAD, (Object) jSONObject2);
                jSONObject.put("commandName", (Object) "SigmeshDevices");
                IoTMultiendInOneBridge.getInstance().onRecvTextMsg(jSONObject.toJSONString());
            }
            this.f1200c = new ProvisionedMeshNode();
            int provisionerAddr = provisionInfo4Master.getProvisionerAddr();
            byte[] bArr = {(byte) ((provisionerAddr >> 8) & 255), (byte) (provisionerAddr & 255)};
            this.f1201d.i(bArr);
            this.f1200c.setConfigurationSrc(bArr);
            this.f1200c.setUnicastAddress(bArr);
            List<SigmeshKey> sigmeshKeys = provisionInfo4Master.getSigmeshKeys();
            if (sigmeshKeys != null) {
                for (SigmeshKey sigmeshKey : sigmeshKeys) {
                    if (sigmeshKey != null && sigmeshKey.getProvisionAppKeys() != null) {
                        for (ProvisionAppKey provisionAppKey : sigmeshKey.getProvisionAppKeys()) {
                            ((ProvisionedMeshNode) this.f1200c).setAddedAppKey(provisionAppKey.getAppKeyIndex(), provisionAppKey.getAppKey());
                        }
                    }
                }
            }
            SigmeshKey sigmeshKey2 = (sigmeshKeys == null || sigmeshKeys.size() <= 0) ? null : sigmeshKeys.get(0);
            if (sigmeshKey2 != null && sigmeshKey2.getProvisionNetKey() != null && !TextUtils.isEmpty(sigmeshKey2.getProvisionNetKey().getNetKey())) {
                this.f1200c.setNetworkKey(MeshParserUtils.toByteArray(sigmeshKey2.getProvisionNetKey().getNetKey()));
                ((ProvisionedMeshNode) this.f1200c).setK2Ouput(SecureUtils.calculateK2(this.f1200c.getNetworkKey(), SecureUtils.K2_MASTER_INPUT));
            }
            this.f1200c.addElement(c(provisionInfo4Master));
            MeshDeviceInfoManager.getInstance().updateDeviceInfos(provisionInfo4Master.getSigmeshIotDevList());
            MeshDeviceInfoManager.getInstance().updateDeviceInfos(provisionInfo4Master.getShareDeviceList());
            List<SigmeshKey> listA = a(provisionInfo4Master);
            if (sigmeshKey2 != null) {
                listA.add(0, sigmeshKey2);
            }
            a(bArr, listA, provisionInfo4Master.getSigmeshIotDevList(), provisionInfo4Master.getShareDeviceList());
        }
    }

    public BaseMeshNode c() {
        return this.f1200c;
    }

    public b.u d() {
        return this.f1201d;
    }

    public static G a() {
        return f1199b;
    }

    public final LinkedHashMap<Integer, Element> c(ProvisionInfo4Master provisionInfo4Master) {
        int i2;
        int i3;
        MeshModel sigModel;
        List arrayList;
        LinkedHashMap<Integer, Element> linkedHashMap = new LinkedHashMap<>();
        List<AddModelClient> addModelClient = provisionInfo4Master.getAddModelClient();
        List<SubscribeGroupAddr_t> subscribeGroupAddr = provisionInfo4Master.getSubscribeGroupAddr();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        SparseArray sparseArray = new SparseArray();
        if (subscribeGroupAddr != null && subscribeGroupAddr.size() > 0) {
            for (SubscribeGroupAddr_t subscribeGroupAddr_t : subscribeGroupAddr) {
                byte[] bArr = {(byte) ((subscribeGroupAddr_t.getGroupAddr() >> 8) & 255), (byte) (subscribeGroupAddr_t.getGroupAddr() & 255)};
                int modelId = subscribeGroupAddr_t.getModelId();
                if (sparseArray.get(modelId) != null) {
                    arrayList = (List) sparseArray.get(modelId);
                    arrayList.add(bArr);
                } else {
                    arrayList = new ArrayList();
                    arrayList.add(bArr);
                }
                sparseArray.put(subscribeGroupAddr_t.getModelId(), arrayList);
            }
        }
        if (addModelClient == null || addModelClient.size() <= 0) {
            i2 = 0;
            i3 = 0;
        } else {
            Iterator<AddModelClient> it = addModelClient.iterator();
            int i4 = 0;
            int i5 = 0;
            while (it.hasNext()) {
                int modelId2 = it.next().getModelId();
                if (a(modelId2)) {
                    i5++;
                    Integer numValueOf = Integer.valueOf(modelId2);
                    sigModel = new VendorModel(modelId2);
                    linkedHashMap2.put(numValueOf, sigModel);
                } else {
                    i4++;
                    Integer numValueOf2 = Integer.valueOf(modelId2);
                    sigModel = SigModelParser.getSigModel(modelId2);
                    linkedHashMap2.put(numValueOf2, sigModel);
                }
                if (sparseArray.get(modelId2) != null) {
                    sigModel.setSubscriptionAddress((List) sparseArray.get(modelId2));
                }
            }
            i2 = i4;
            i3 = i5;
        }
        int provisionerAddr = provisionInfo4Master.getProvisionerAddr();
        linkedHashMap.put(Integer.valueOf(provisionerAddr), new Element(new byte[]{(byte) ((provisionerAddr >> 8) & 255), (byte) (provisionerAddr & 255)}, 0, i2, i3, linkedHashMap2));
        return linkedHashMap;
    }

    public final List<SigmeshKey> a(ProvisionInfo4Master provisionInfo4Master) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        List<SigmeshIotDev> sigmeshIotDevList = provisionInfo4Master.getSigmeshIotDevList();
        if (!ListUtils.isEmpty(sigmeshIotDevList)) {
            for (SigmeshIotDev sigmeshIotDev : sigmeshIotDevList) {
                if (sigmeshIotDev.getSigmeshKeys() != null) {
                    SigmeshKey sigmeshKey = sigmeshIotDev.getSigmeshKeys().get(0);
                    if (ListUtils.isEmpty(copyOnWriteArrayList)) {
                        copyOnWriteArrayList.add(sigmeshKey);
                    } else {
                        Iterator it = copyOnWriteArrayList.iterator();
                        while (it.hasNext()) {
                            if (!((SigmeshKey) it.next()).getProvisionNetKey().getNetKey().equals(sigmeshKey.getProvisionNetKey().getNetKey())) {
                                copyOnWriteArrayList.add(sigmeshKey);
                            }
                        }
                    }
                }
            }
        }
        List<SigmeshIotDev> shareDeviceList = provisionInfo4Master.getShareDeviceList();
        if (!ListUtils.isEmpty(shareDeviceList)) {
            for (SigmeshIotDev sigmeshIotDev2 : shareDeviceList) {
                if (sigmeshIotDev2.getSigmeshKeys() != null) {
                    SigmeshKey sigmeshKey2 = sigmeshIotDev2.getSigmeshKeys().get(0);
                    if (ListUtils.isEmpty(copyOnWriteArrayList)) {
                        copyOnWriteArrayList.add(sigmeshKey2);
                    } else {
                        Iterator it2 = copyOnWriteArrayList.iterator();
                        while (it2.hasNext()) {
                            if (!((SigmeshKey) it2.next()).getProvisionNetKey().getNetKey().equals(sigmeshKey2.getProvisionNetKey().getNetKey())) {
                                copyOnWriteArrayList.add(sigmeshKey2);
                            }
                        }
                    }
                }
            }
        }
        return copyOnWriteArrayList;
    }

    public final void a(byte[] bArr, List<SigmeshKey> list, List<SigmeshIotDev> list2, List<SigmeshIotDev> list3) {
        this.f1201d.a(list);
        if (ListUtils.isEmpty(list)) {
            return;
        }
        if (ListUtils.isEmpty(list2) && ListUtils.isEmpty(list3)) {
            return;
        }
        String str = f1198a;
        StringBuilder sb = new StringBuilder();
        sb.append("Own devices count: ");
        sb.append(list2 == null ? 0 : list2.size());
        sb.append(", share devices count: ");
        sb.append(list3 == null ? 0 : list3.size());
        a.a.a.a.b.m.a.c(str, sb.toString());
        this.f1201d.b(list);
        SigmeshKey sigmeshKey = list.get(0);
        byte[] byteArray = MeshParserUtils.toByteArray(sigmeshKey.getProvisionNetKey().getNetKey());
        SparseArray<byte[]> sparseArray = new SparseArray<>();
        for (ProvisionAppKey provisionAppKey : sigmeshKey.getProvisionAppKeys()) {
            sparseArray.put(provisionAppKey.getAppKeyIndex(), MeshParserUtils.toByteArray(provisionAppKey.getAppKey()));
        }
        u.a aVarA = new u.a.C0018a().a(true).b(byteArray).a(new byte[]{0, 0, 0, 0}).a(sparseArray).a();
        aVarA.a(true);
        this.f1201d.a(byteArray, aVarA, false);
        a.a.a.a.b.m.a.a(f1198a, "group add subnet:" + MeshParserUtils.bytesToHex(byteArray, false));
        if (!ListUtils.isEmpty(list3)) {
            for (SigmeshIotDev sigmeshIotDev : list3) {
                int appKeyIndex = sigmeshIotDev.getAppKeyIndex();
                SigmeshKey sigmeshKey2 = sigmeshIotDev.getSigmeshKeys().get(0);
                byte[] byteArray2 = MeshParserUtils.toByteArray(sigmeshKey2.getProvisionNetKey().getNetKey());
                SparseArray sparseArray2 = new SparseArray();
                Iterator<ProvisionAppKey> it = sigmeshKey2.getProvisionAppKeys().iterator();
                while (true) {
                    if (it.hasNext()) {
                        ProvisionAppKey next = it.next();
                        if (appKeyIndex == next.getAppKeyIndex()) {
                            sparseArray2.put(next.getAppKeyIndex(), MeshParserUtils.toByteArray(next.getAppKey()));
                            break;
                        }
                    }
                }
                a(bArr, sigmeshIotDev, sigmeshKey2, byteArray2);
            }
        }
        for (SigmeshIotDev sigmeshIotDev2 : list2) {
            a(bArr, sigmeshIotDev2, (sigmeshIotDev2.getSigmeshKeys() == null || sigmeshIotDev2.getSigmeshKeys().size() <= 0) ? sigmeshKey : sigmeshIotDev2.getSigmeshKeys().get(0), byteArray);
        }
    }

    public BaseMeshNode b() {
        if (this.f1201d.d() != null) {
            return this.f1201d.d().b();
        }
        return this.f1200c;
    }

    public final void a(byte[] bArr, SigmeshIotDev sigmeshIotDev, SigmeshKey sigmeshKey, byte[] bArr2) {
        UnprovisionedMeshNode unprovisionedMeshNode = new UnprovisionedMeshNode();
        int unicastaddress = sigmeshIotDev.getUnicastaddress();
        unprovisionedMeshNode.setBluetoothAddress(sigmeshIotDev.getMac());
        unprovisionedMeshNode.setUnicastAddress(new byte[]{(byte) ((unicastaddress >> 8) & 255), (byte) (unicastaddress & 255)});
        unprovisionedMeshNode.setSupportFastProvision(false);
        unprovisionedMeshNode.setIvIndex(ByteBuffer.allocate(4).putInt(0).array());
        unprovisionedMeshNode.setNetworkKey(bArr2);
        unprovisionedMeshNode.setConfigurationSrc(bArr);
        ProvisionedMeshNode provisionedMeshNode = new ProvisionedMeshNode(unprovisionedMeshNode);
        for (ProvisionAppKey provisionAppKey : sigmeshKey.getProvisionAppKeys()) {
            provisionedMeshNode.setAddedAppKey(provisionAppKey.getAppKeyIndex(), provisionAppKey.getAppKey());
        }
        provisionedMeshNode.setIsProvisioned(true);
        provisionedMeshNode.setConfigured(true);
        String devId = sigmeshIotDev.getDevId();
        provisionedMeshNode.setSupportFastProvision(AliMeshUUIDParserUtil.isSupportFastProvision(devId));
        provisionedMeshNode.setSupportFastGattProvision(AliMeshUUIDParserUtil.isSupportFastProvisionViaGatt(devId));
        provisionedMeshNode.setBluetoothDeviceAddress(sigmeshIotDev.getMac());
        provisionedMeshNode.setDevId(devId);
        if (!TextUtils.isEmpty(sigmeshIotDev.getDeviceKey())) {
            provisionedMeshNode.setDeviceKey(MeshParserUtils.toByteArray(sigmeshIotDev.getDeviceKey()));
        }
        this.f1201d.a(provisionedMeshNode, false, false);
    }
}
