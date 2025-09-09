package b;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import b.K;
import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.bean.ConnectionParams;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import com.alibaba.ailabs.iot.mesh.utils.AliMeshUUIDParserUtil;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import datasource.bean.SigmeshKey;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNode;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes2.dex */
public class u {

    /* renamed from: a, reason: collision with root package name */
    public volatile ConcurrentHashMap<String, a> f7549a;

    /* renamed from: b, reason: collision with root package name */
    public y f7550b;

    /* renamed from: c, reason: collision with root package name */
    public Map<String, ProvisionedMeshNode> f7551c;

    /* renamed from: d, reason: collision with root package name */
    public Map<String, ProvisionedMeshNode> f7552d;

    /* renamed from: e, reason: collision with root package name */
    public a f7553e;

    /* renamed from: f, reason: collision with root package name */
    public byte[] f7554f;

    /* renamed from: g, reason: collision with root package name */
    public List<SigmeshKey> f7555g;

    /* renamed from: h, reason: collision with root package name */
    public volatile boolean f7556h = false;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public boolean f7557a;

        /* renamed from: b, reason: collision with root package name */
        public byte[] f7558b;

        /* renamed from: c, reason: collision with root package name */
        public SparseArray<byte[]> f7559c;

        /* renamed from: d, reason: collision with root package name */
        public byte[] f7560d;

        /* renamed from: e, reason: collision with root package name */
        public SparseArray<ProvisionedMeshNode> f7561e;

        /* renamed from: f, reason: collision with root package name */
        public K f7562f;

        /* renamed from: g, reason: collision with root package name */
        public byte[] f7563g;

        /* renamed from: h, reason: collision with root package name */
        public String f7564h;

        /* renamed from: i, reason: collision with root package name */
        public byte f7565i;

        /* renamed from: b.u$a$a, reason: collision with other inner class name */
        public static final class C0018a {

            /* renamed from: a, reason: collision with root package name */
            public boolean f7566a;

            /* renamed from: b, reason: collision with root package name */
            public byte[] f7567b;

            /* renamed from: c, reason: collision with root package name */
            public SparseArray<byte[]> f7568c;

            /* renamed from: d, reason: collision with root package name */
            public byte[] f7569d;

            public C0018a a(boolean z2) {
                this.f7566a = z2;
                return this;
            }

            public C0018a b(byte[] bArr) {
                if (bArr != null && bArr.length == 16) {
                    this.f7567b = bArr;
                    return this;
                }
                throw new IllegalArgumentException("unknown networkKey " + bArr);
            }

            public C0018a a(SparseArray<byte[]> sparseArray) {
                this.f7568c = sparseArray;
                return this;
            }

            public C0018a a(byte[] bArr) {
                this.f7569d = bArr;
                return this;
            }

            public a a() {
                return new a(this.f7566a, this.f7567b, this.f7568c, this.f7569d, null);
            }
        }

        public /* synthetic */ a(boolean z2, byte[] bArr, SparseArray sparseArray, byte[] bArr2, t tVar) {
            this(z2, bArr, sparseArray, bArr2);
        }

        public byte[] d() {
            return this.f7558b;
        }

        public K e() {
            return this.f7562f;
        }

        public boolean f() {
            if (this.f7561e.size() == 0) {
                return true;
            }
            if (!i()) {
                K k2 = this.f7562f;
                return k2 != null && k2.f() == 2;
            }
            K k3 = this.f7562f;
            if (k3 == null || k3.f() != 2) {
                return this.f7562f != null && MeshDeviceInfoManager.getInstance().isOnyTinyMeshExistInCurrentUser();
            }
            return true;
        }

        public boolean g() {
            if (this.f7561e.size() == 0) {
                return true;
            }
            K k2 = this.f7562f;
            return k2 != null && (k2.f() == 2 || this.f7562f.f() == 1);
        }

        public boolean h() {
            K k2 = this.f7562f;
            return k2 != null ? k2.l() : this.f7557a;
        }

        public boolean i() {
            return this.f7557a;
        }

        public boolean j() {
            if (this.f7561e.size() == 0) {
                return true;
            }
            K k2 = this.f7562f;
            return k2 != null && k2.f() == 2;
        }

        public String toString() {
            return "Subnets{mPrimaryFlag=" + this.f7557a + ", mNetworkKey=" + MeshParserUtils.bytesToHex(this.f7558b, false) + '}';
        }

        public a(boolean z2, byte[] bArr, SparseArray<byte[]> sparseArray, byte[] bArr2) {
            this.f7561e = new SparseArray<>();
            this.f7557a = z2;
            this.f7558b = bArr;
            this.f7559c = sparseArray;
            this.f7560d = bArr2;
            this.f7564h = MeshParserUtils.bytesToHex(SecureUtils.calculateK3(bArr), false);
            this.f7565i = SecureUtils.calculateK2(this.f7558b, SecureUtils.K2_MASTER_INPUT).getNid();
            a.a.a.a.b.m.a.c("SIGMeshNetwork", String.format("%s, networkID: %s", this, this.f7564h));
        }

        public void a(K k2) {
            this.f7562f = k2;
            k2.b(toString());
        }

        public BaseMeshNode b() {
            if (this.f7561e.size() <= 0) {
                return null;
            }
            SparseArray<ProvisionedMeshNode> sparseArray = this.f7561e;
            return sparseArray.get(sparseArray.keyAt(0));
        }

        public byte c() {
            return this.f7565i;
        }

        public BaseMeshNode d(byte[] bArr) {
            if (bArr == null || bArr.length != 2) {
                return null;
            }
            return a(Integer.parseInt(MeshParserUtils.bytesToHex(bArr, false), 16));
        }

        public void e(byte[] bArr) {
            this.f7563g = bArr;
        }

        public ProvisionedMeshNode c(byte[] bArr) {
            for (int i2 = 0; i2 < this.f7561e.size(); i2++) {
                ProvisionedMeshNode provisionedMeshNode = this.f7561e.get(this.f7561e.keyAt(i2));
                if (u.a(provisionedMeshNode, bArr)) {
                    return provisionedMeshNode;
                }
            }
            return null;
        }

        public void a(boolean z2) {
            this.f7557a = z2;
        }

        public boolean b(byte[] bArr) {
            byte[] bArrF = u.f(bArr);
            if (TextUtils.isEmpty(this.f7564h)) {
                this.f7564h = MeshParserUtils.bytesToHex(SecureUtils.calculateK3(this.f7558b), false);
            }
            return bArrF != null && this.f7564h.equals(MeshParserUtils.bytesToHex(bArrF, false).toUpperCase());
        }

        public SparseArray<byte[]> a() {
            return this.f7559c;
        }

        public BaseMeshNode a(String str) {
            for (int i2 = 0; i2 < this.f7561e.size(); i2++) {
                ProvisionedMeshNode provisionedMeshNode = this.f7561e.get(this.f7561e.keyAt(i2));
                if (provisionedMeshNode == null || TextUtils.isEmpty(provisionedMeshNode.getBluetoothDeviceAddress()) || provisionedMeshNode.getBluetoothDeviceAddress().equals(str)) {
                    return provisionedMeshNode;
                }
            }
            return null;
        }

        public BaseMeshNode a(int i2) {
            ProvisionedMeshNode provisionedMeshNode = this.f7561e.get(i2);
            a.a.a.a.b.m.a.a("SIGMeshNetwork", String.format("Get mesh node via unicast address(%d) result: %s", Integer.valueOf(i2), provisionedMeshNode));
            return provisionedMeshNode == null ? a(AddressUtils.getUnicastAddressBytes(i2)) : provisionedMeshNode;
        }

        public void a(ProvisionedMeshNode provisionedMeshNode, boolean z2) {
            Map<Integer, String> addedAppKeys = provisionedMeshNode.getAddedAppKeys();
            int size = addedAppKeys == null ? 0 : addedAppKeys.size();
            ProvisionedMeshNode provisionedMeshNode2 = this.f7561e.get(Integer.parseInt(MeshParserUtils.bytesToHex(provisionedMeshNode.getUnicastAddress(), false), 16));
            if (!z2 && provisionedMeshNode2 != null && provisionedMeshNode2.getAddedAppKeys() != null && provisionedMeshNode2.getAddedAppKeys().size() >= size) {
                if (provisionedMeshNode.getDeviceKey() != null) {
                    a.a.a.a.b.m.a.a("SIGMeshNetwork", "replace deviceKey with latest node");
                    provisionedMeshNode2.setDeviceKey(provisionedMeshNode.getDeviceKey());
                    return;
                }
                return;
            }
            if (addedAppKeys != null && addedAppKeys.size() <= 0 && this.f7559c.get(0) != null) {
                a.a.a.a.b.m.a.d("SIGMeshNetwork", "set added default appkey for node");
                provisionedMeshNode.setAddedAppKey(0, MeshParserUtils.bytesToHex(this.f7559c.get(0), false));
            }
            String devId = provisionedMeshNode.getDevId();
            if (a.a.a.a.b.d.a.f1335b && addedAppKeys.size() == 1 && AliMeshUUIDParserUtil.isSupportAutomaticallyGenerateShareAppKey(devId)) {
                String str = provisionedMeshNode.getAddedAppKeys().get(0);
                if (!TextUtils.isEmpty(str)) {
                    byte[] networkKey = provisionedMeshNode.getNetworkKey();
                    String lowerCase = AliMeshUUIDParserUtil.extractMacAddressFromUUID(devId).replace(":", "").toLowerCase();
                    String str2 = String.format("%s,%s,%s", str.toLowerCase(), MeshParserUtils.bytesToHex(provisionedMeshNode.getUnicastAddress(), false).toLowerCase(), MeshParserUtils.bytesToHex(networkKey, false).toLowerCase());
                    String strBytesToHex = MeshParserUtils.bytesToHex(Arrays.copyOf(SecureUtils.calculateSha256(str2.getBytes()), 16), false);
                    provisionedMeshNode.setAddedAppKey(1, strBytesToHex);
                    a.a.a.a.b.m.a.a("SIGMeshNetwork", String.format("Automatically generate AppKey1, input[%s], result[%s] for node[%s]", str2, strBytesToHex, lowerCase));
                }
            }
            this.f7561e.put(Integer.parseInt(MeshParserUtils.bytesToHex(provisionedMeshNode.getUnicastAddress(), false), 16), provisionedMeshNode);
        }

        public final ProvisionedMeshNode a(byte[] bArr) {
            UnprovisionedMeshNode unprovisionedMeshNode = new UnprovisionedMeshNode();
            unprovisionedMeshNode.setConfigurationSrc(this.f7563g);
            unprovisionedMeshNode.setUnicastAddress(bArr);
            unprovisionedMeshNode.setIvIndex(this.f7560d);
            unprovisionedMeshNode.setNetworkKey(this.f7558b);
            ProvisionedMeshNode provisionedMeshNode = new ProvisionedMeshNode(unprovisionedMeshNode);
            provisionedMeshNode.setIsProvisioned(true);
            provisionedMeshNode.setConfigured(true);
            if (this.f7559c.get(0) != null) {
                String strBytesToHex = MeshParserUtils.bytesToHex(this.f7559c.get(0), false);
                a.a.a.a.b.m.a.d("SIGMeshNetwork", "set added default appKey:" + strBytesToHex + " for node");
                provisionedMeshNode.setAddedAppKey(0, strBytesToHex);
            }
            a(provisionedMeshNode, false);
            return provisionedMeshNode;
        }
    }

    public void b(K.c cVar) {
        y yVar = this.f7550b;
        if (yVar == null) {
            a.a.a.a.b.m.a.b("SIGMeshNetwork", "Must initTransportManager first");
        } else {
            yVar.b(cVar);
        }
    }

    public void c(byte[] bArr) {
        if (this.f7549a == null) {
            a.a.a.a.b.m.a.b("SIGMeshNetwork", "netKey to subnets mapping data is not initialized");
            return;
        }
        String strBytesToHex = MeshParserUtils.bytesToHex(bArr, false);
        a aVar = this.f7549a.get(strBytesToHex);
        if (aVar == null) {
            a.a.a.a.b.m.a.d("SIGMeshNetwork", "Can't find the corresponding subnet");
            return;
        }
        y yVar = this.f7550b;
        if (yVar != null) {
            yVar.b(aVar);
        }
        if (aVar.e() != null) {
            aVar.e().n();
        }
        this.f7549a.remove(strBytesToHex);
    }

    public a d() {
        return this.f7553e;
    }

    public void e() {
        this.f7556h = true;
        y yVar = this.f7550b;
        if (yVar != null) {
            yVar.b();
        }
    }

    public void f() {
        a.a.a.a.b.m.a.c("SIGMeshNetwork", "Try connect all subnets");
        if (this.f7550b == null || this.f7549a == null) {
            a.a.a.a.b.m.a.b("SIGMeshNetwork", "Must initTransportManager first");
            return;
        }
        for (Map.Entry<String, a> entry : this.f7549a.entrySet()) {
            if (entry.getValue().e() == null || entry.getValue().h()) {
                this.f7550b.f();
                return;
            }
        }
        a.a.a.a.b.m.a.a("SIGMeshNetwork", "All SIGMesh subnets in connected state, no need to perform connect");
    }

    public void g() {
        y yVar = this.f7550b;
        if (yVar == null) {
            a.a.a.a.b.m.a.b("SIGMeshNetwork", "Must initTransportManager first");
        } else {
            yVar.g();
        }
    }

    public void h() {
        this.f7556h = false;
        y yVar = this.f7550b;
        if (yVar != null) {
            yVar.h();
        }
    }

    public void i(byte[] bArr) {
        this.f7554f = bArr;
    }

    public void a(Context context, q qVar) {
        if (this.f7550b == null) {
            this.f7550b = new y(context, this, qVar);
        }
        if (this.f7556h) {
            this.f7550b.b();
        } else {
            this.f7550b.h();
        }
    }

    public a d(byte[] bArr) {
        byte[] bArrF = f(bArr);
        for (Map.Entry<String, a> entry : this.f7549a.entrySet()) {
            String str = entry.getValue().f7564h;
            if (bArrF != null && str.equals(MeshParserUtils.bytesToHex(bArrF, false).toUpperCase())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static byte[] e(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(bArr, 1, 8);
        return byteBufferOrder.array();
    }

    public static byte[] g(byte[] bArr) {
        if (bArr == null || bArr.length <= 8) {
            return null;
        }
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(bArr, 9, 8);
        return byteBufferOrder.array();
    }

    public void b(List<SigmeshKey> list) {
        this.f7555g = list;
    }

    public a h(byte[] bArr) {
        if (this.f7549a == null) {
            a.a.a.a.b.m.a.b("SIGMeshNetwork", "netKey to subnets mapping data is not initialized");
            return null;
        }
        return this.f7549a.get(MeshParserUtils.bytesToHex(bArr, false));
    }

    public ProvisionedMeshNode b(String str) {
        Map<String, ProvisionedMeshNode> map = this.f7551c;
        if (map == null || !map.containsKey(str)) {
            return null;
        }
        return this.f7551c.get(str);
    }

    public void a(a aVar, boolean z2) {
        a.a.a.a.b.m.a.c("SIGMeshNetwork", "Try connect specified " + aVar);
        if (this.f7550b != null && this.f7549a != null) {
            if (aVar.e() != null && !aVar.h()) {
                a.a.a.a.b.m.a.d("SIGMeshNetwork", "not need connect, return");
                return;
            } else {
                this.f7550b.a(aVar, z2);
                return;
            }
        }
        a.a.a.a.b.m.a.b("SIGMeshNetwork", "Must initTransportManager first");
    }

    public Pair<a, ProvisionedMeshNode> b(byte[] bArr) {
        for (Map.Entry<String, a> entry : this.f7549a.entrySet()) {
            for (int i2 = 0; i2 < entry.getValue().f7561e.size(); i2++) {
                ProvisionedMeshNode provisionedMeshNode = (ProvisionedMeshNode) entry.getValue().f7561e.get(entry.getValue().f7561e.keyAt(i2));
                if (a(provisionedMeshNode, bArr)) {
                    return new Pair<>(entry.getValue(), provisionedMeshNode);
                }
            }
        }
        return null;
    }

    public static byte[] f(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(bArr, 1, 8);
        return byteBufferOrder.array();
    }

    public a c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Map<String, ProvisionedMeshNode> map = this.f7551c;
        if (map == null) {
            a.a.a.a.b.m.a.b("SIGMeshNetwork", "devId to mesh node mapping data is not initialized");
            return null;
        }
        ProvisionedMeshNode provisionedMeshNode = map.get(str);
        if (provisionedMeshNode == null || provisionedMeshNode.getNetworkKey() == null) {
            return null;
        }
        return h(provisionedMeshNode.getNetworkKey());
    }

    public byte[] b() {
        return this.f7554f;
    }

    public void a(ConnectionParams connectionParams) {
        if (connectionParams == null) {
            f();
            return;
        }
        if (!TextUtils.isEmpty(connectionParams.getDeviceId())) {
            a.a.a.a.b.m.a.c("SIGMeshNetwork", "Try connect target subnets, first find it...");
            a aVarC = c(connectionParams.getDeviceId());
            if (aVarC == null) {
                a.a.a.a.b.m.a.b("SIGMeshNetwork", String.format("The target subnet was not found by deviceId(%s)", connectionParams.getDeviceId()));
                return;
            }
            if (aVarC.f()) {
                a.a.a.a.b.m.a.c("SIGMeshNetwork", String.format("The %s is available, do nothing", aVarC));
                return;
            }
            y yVar = this.f7550b;
            if (yVar == null) {
                a.a.a.a.b.m.a.b("SIGMeshNetwork", "Must initTransportManager first");
                return;
            } else {
                yVar.a(aVarC, false);
                return;
            }
        }
        if (TextUtils.isEmpty(connectionParams.getDirectConnectionProxyNodeMacAddress())) {
            return;
        }
        String directConnectionProxyNodeMacAddress = connectionParams.getDirectConnectionProxyNodeMacAddress();
        if (!BluetoothAdapter.checkBluetoothAddress(directConnectionProxyNodeMacAddress)) {
            a.a.a.a.b.m.a.b("SIGMeshNetwork", directConnectionProxyNodeMacAddress + " is not a valid Bluetooth address");
            return;
        }
        a aVar = this.f7553e;
        if (aVar == null || aVar.e() == null) {
            return;
        }
        if (this.f7553e.a(directConnectionProxyNodeMacAddress) == null) {
            this.f7553e.b();
        }
        this.f7553e.e().b(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(directConnectionProxyNodeMacAddress));
    }

    public int c() {
        if (this.f7549a == null) {
            return 0;
        }
        return this.f7549a.size();
    }

    public void a(K.c cVar) {
        y yVar = this.f7550b;
        if (yVar == null) {
            a.a.a.a.b.m.a.b("SIGMeshNetwork", "Must initTransportManager first");
        } else {
            yVar.a(cVar);
        }
    }

    public boolean a() {
        a.a.a.a.b.m.a.c("SIGMeshNetwork", "Check any subnet accept new connections ...");
        for (Map.Entry<String, a> entry : this.f7549a.entrySet()) {
            a value = entry.getValue();
            if (value == null || (value.g() && !value.h())) {
                a.a.a.a.b.m.a.a("SIGMeshNetwork", String.format("%s is connected", entry.getValue()));
            } else {
                a.a.a.a.b.m.a.a("SIGMeshNetwork", String.format("%s is disconnected or multiProxy acceptable", entry.getValue()));
                return true;
            }
        }
        return false;
    }

    public void a(boolean z2, MeshService.OnDisconnectListener onDisconnectListener) {
        if (z2) {
            a aVar = this.f7553e;
            if (aVar != null && aVar.f7562f != null) {
                this.f7553e.f7562f.a(onDisconnectListener);
                return;
            } else {
                onDisconnectListener.onDisconnected();
                return;
            }
        }
        if (this.f7549a == null) {
            return;
        }
        Iterator<Map.Entry<String, a>> it = this.f7549a.entrySet().iterator();
        while (it.hasNext()) {
            a value = it.next().getValue();
            if (value.f7562f != null) {
                value.f7562f.a(new t(this));
            }
        }
    }

    public void a(byte[] bArr, a aVar, boolean z2) {
        if (bArr == null || aVar == null) {
            return;
        }
        aVar.e(this.f7554f);
        if (this.f7549a == null) {
            synchronized (this) {
                this.f7549a = new ConcurrentHashMap<>();
            }
        }
        String strBytesToHex = MeshParserUtils.bytesToHex(bArr, false);
        if (this.f7549a.containsKey(strBytesToHex) && z2) {
            a.a.a.a.b.m.a.c("SIGMeshNetwork", String.format("Update %s", aVar));
            this.f7549a.put(strBytesToHex, aVar);
            if (aVar.i()) {
                this.f7553e = aVar;
                return;
            }
            return;
        }
        if (this.f7549a.containsKey(strBytesToHex)) {
            return;
        }
        a.a.a.a.b.m.a.c("SIGMeshNetwork", String.format("add %s", aVar));
        this.f7549a.put(strBytesToHex, aVar);
        if (aVar.i()) {
            this.f7553e = aVar;
        }
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, boolean z2, boolean z3) {
        if (provisionedMeshNode == null || provisionedMeshNode.getNetworkKey() == null) {
            return;
        }
        provisionedMeshNode.setIsProvisioned(true);
        provisionedMeshNode.setConfigurationSrc(this.f7554f);
        if (this.f7549a == null) {
            synchronized (this) {
                this.f7549a = new ConcurrentHashMap<>();
            }
        }
        String devId = provisionedMeshNode.getDevId();
        if (!TextUtils.isEmpty(devId)) {
            if (this.f7551c == null) {
                this.f7551c = new HashMap();
            }
            if (this.f7552d == null) {
                this.f7552d = new HashMap();
            }
            this.f7551c.put(devId, provisionedMeshNode);
            this.f7552d.put(Utils.deviceId2Mac(devId).toLowerCase(), provisionedMeshNode);
        }
        a.a.a.a.b.m.a.c("SIGMeshNetwork", String.format(Locale.getDefault(), "Add or update provisioned mesh node(%s) to network (address:%d), appKeys count: %d", provisionedMeshNode.getDevId(), Integer.valueOf(Integer.parseInt(MeshParserUtils.bytesToHex(provisionedMeshNode.getUnicastAddress(), false), 16)), Integer.valueOf(provisionedMeshNode.getAddedAppKeys() == null ? 0 : provisionedMeshNode.getAddedAppKeys().size())));
        a aVarA = this.f7549a.get(MeshParserUtils.bytesToHex(provisionedMeshNode.getNetworkKey(), false));
        if (aVarA == null) {
            Map<Integer, String> addedAppKeys = provisionedMeshNode.getAddedAppKeys();
            SparseArray<byte[]> sparseArray = new SparseArray<>();
            for (Integer num : addedAppKeys.keySet()) {
                sparseArray.put(num.intValue(), MeshParserUtils.toByteArray(addedAppKeys.get(num)));
            }
            aVarA = new a.C0018a().b(provisionedMeshNode.getNetworkKey()).a(new byte[]{0, 0, 0, 0}).a(z2).a(sparseArray).a();
            a(provisionedMeshNode.getNetworkKey(), aVarA, true);
        }
        aVarA.a(provisionedMeshNode, z3);
    }

    public BaseMeshNode a(byte[] bArr, byte[] bArr2) {
        if (bArr != null && bArr2 != null) {
            a aVar = this.f7549a.get(MeshParserUtils.bytesToHex(bArr, false));
            if (aVar != null) {
                return aVar.d(bArr2);
            }
        }
        return null;
    }

    public BaseMeshNode a(String str, byte[] bArr) {
        a aVar;
        if (TextUtils.isEmpty(str) || bArr == null || (aVar = this.f7549a.get(str)) == null) {
            return null;
        }
        return aVar.d(bArr);
    }

    public ProvisionedMeshNode a(String str) {
        Map<String, ProvisionedMeshNode> map = this.f7552d;
        if (map == null || !map.containsKey(str.toLowerCase())) {
            return null;
        }
        return this.f7552d.get(str.toLowerCase());
    }

    public void a(List<SigmeshKey> list) {
        if (this.f7549a == null) {
            return;
        }
        a.a.a.a.b.m.a.a("SIGMeshNetwork", "Full refresh network");
        HashSet hashSet = new HashSet();
        for (SigmeshKey sigmeshKey : list) {
            if (sigmeshKey.getProvisionNetKey() != null) {
                String netKey = sigmeshKey.getProvisionNetKey().getNetKey();
                if (!TextUtils.isEmpty(netKey)) {
                    hashSet.add(netKey.toLowerCase());
                }
            }
        }
        for (Map.Entry<String, a> entry : this.f7549a.entrySet()) {
            if (!hashSet.contains(entry.getKey().toLowerCase())) {
                c(entry.getValue().d());
            }
        }
    }

    public static boolean a(ProvisionedMeshNode provisionedMeshNode, byte[] bArr) {
        byte[] bArrG;
        byte[] bArrE = e(bArr);
        if (bArrE == null || (bArrG = g(bArr)) == null || provisionedMeshNode.getIdentityKey() == null) {
            return false;
        }
        boolean zEquals = Arrays.equals(bArrE, SecureUtils.calculateHash(provisionedMeshNode.getIdentityKey(), bArrG, provisionedMeshNode.getUnicastAddress()));
        if (zEquals) {
            provisionedMeshNode.setNodeIdentifier(MeshParserUtils.bytesToHex(bArrE, false));
        }
        return zEquals;
    }
}
