package b;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.media3.exoplayer.ExoPlayer;
import com.alibaba.ailabs.iot.mesh.R;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.ProxyProtocolMessageType;
import meshprovisioner.configuration.MeshModel;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNodeData;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.ConfigModelPublicationSetParams;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* renamed from: b.l, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0337l implements InterfaceC0329d, InterfaceC0327b {

    /* renamed from: a, reason: collision with root package name */
    public static final String f7494a = "l";

    /* renamed from: b, reason: collision with root package name */
    public final Map<Integer, ProvisionedMeshNode> f7495b;

    /* renamed from: c, reason: collision with root package name */
    public final s f7496c;

    /* renamed from: d, reason: collision with root package name */
    public Context f7497d;

    /* renamed from: e, reason: collision with root package name */
    public byte[] f7498e;

    /* renamed from: f, reason: collision with root package name */
    public InterfaceC0338m f7499f;

    /* renamed from: g, reason: collision with root package name */
    public o f7500g;

    /* renamed from: h, reason: collision with root package name */
    public b.b.a f7501h;

    /* renamed from: i, reason: collision with root package name */
    public byte[] f7502i;

    /* renamed from: j, reason: collision with root package name */
    public int f7503j;

    /* renamed from: k, reason: collision with root package name */
    public byte[] f7504k;

    /* renamed from: l, reason: collision with root package name */
    public int f7505l;

    /* renamed from: m, reason: collision with root package name */
    public boolean f7506m;

    /* renamed from: n, reason: collision with root package name */
    public Handler f7507n;

    /* renamed from: o, reason: collision with root package name */
    public Runnable f7508o;

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b.l$a */
    class a {

        /* renamed from: a, reason: collision with root package name */
        public IActionListener<Object> f7509a;

        /* renamed from: b, reason: collision with root package name */
        public Runnable f7510b;

        /* renamed from: c, reason: collision with root package name */
        public IActionListener f7511c;

        /* renamed from: d, reason: collision with root package name */
        public a.a.a.a.b.a.I f7512d;

        /* renamed from: e, reason: collision with root package name */
        public byte[] f7513e;

        /* renamed from: f, reason: collision with root package name */
        public int f7514f;

        /* renamed from: g, reason: collision with root package name */
        public int f7515g;

        /* renamed from: h, reason: collision with root package name */
        public byte[] f7516h;

        public a(byte[] bArr, int i2, byte[] bArr2, int i3, a.a.a.a.b.a.I i4, IActionListener iActionListener) {
            this.f7516h = null;
            this.f7513e = bArr;
            this.f7514f = i2;
            this.f7512d = i4;
            this.f7511c = iActionListener;
            this.f7515g = i3;
            if ((i2 == 13871105 || i2 == 13740033) && bArr2 != null && bArr2.length >= 3) {
                this.f7516h = new byte[]{bArr2[1], bArr2[2]};
            }
            if (i3 > 0) {
                this.f7510b = new RunnableC0335j(this, C0337l.this);
            }
        }

        public void a() {
            if (this.f7510b != null) {
                C0337l.this.f7507n.postDelayed(this.f7510b, this.f7515g);
            }
            b.b.a aVar = C0337l.this.f7501h;
            byte[] bArr = this.f7513e;
            int i2 = this.f7514f;
            byte[] bArr2 = this.f7516h;
            C0336k c0336k = new C0336k(this);
            this.f7509a = c0336k;
            aVar.a(bArr, i2, bArr2, c0336k);
        }
    }

    public C0337l(Context context) {
        this(context, null, null);
    }

    public final void e() throws NumberFormatException {
        if (!this.f7506m) {
            this.f7495b.clear();
            return;
        }
        SharedPreferences sharedPreferences = this.f7497d.getSharedPreferences(Utils.PROVISIONED_NODES_FILE, 0);
        Map<String, ?> all = sharedPreferences.getAll();
        if (all.isEmpty()) {
            return;
        }
        List<Integer> listA = a(all);
        this.f7495b.clear();
        for (Integer num : listA) {
            num.intValue();
            String string = sharedPreferences.getString(String.format(Locale.US, "0x%04X", num), null);
            if (string != null) {
                ProvisionedMeshNode provisionedMeshNode = (ProvisionedMeshNode) JSON.parseObject(string, ProvisionedMeshNode.class);
                this.f7495b.put(Integer.valueOf(AddressUtils.getUnicastAddressInt(provisionedMeshNode.getUnicastAddress())), provisionedMeshNode);
            }
        }
    }

    public final void f() {
        int i2;
        if (this.f7506m && (i2 = this.f7497d.getSharedPreferences("CONFIGURATION_SRC", 0).getInt("SRC", 0)) != 0) {
            this.f7498e = new byte[]{(byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
        }
    }

    public boolean g(byte[] bArr) {
        return bArr != null && bArr[0] == 0;
    }

    public boolean h(byte[] bArr) {
        int i2 = ((bArr[0] & 255) << 8) | (bArr[1] & 255);
        if (!MeshParserUtils.validateUnicastAddressInput(this.f7497d, Integer.valueOf(i2))) {
            return false;
        }
        if (this.f7495b.containsKey(Integer.valueOf(i2))) {
            throw new IllegalArgumentException("Address already occupied by a node");
        }
        this.f7498e = bArr;
        i();
        Iterator<Map.Entry<Integer, ProvisionedMeshNode>> it = this.f7495b.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().setConfigurationSrc(this.f7498e);
        }
        h();
        return true;
    }

    public final void i() {
        if (this.f7506m) {
            SharedPreferences.Editor editorEdit = this.f7497d.getSharedPreferences("CONFIGURATION_SRC", 0).edit();
            byte[] bArr = this.f7498e;
            editorEdit.putInt("SRC", (bArr[1] & 255) | ((bArr[0] & 255) << 8));
            editorEdit.apply();
        }
    }

    @Override // b.InterfaceC0329d
    public void sendPdu(BaseMeshNode baseMeshNode, byte[] bArr) {
        if (baseMeshNode == null) {
            a.a.a.a.b.m.a.b(f7494a, "sendPdu, empty mesh node");
            return;
        }
        int mtu = this.f7499f.getMtu();
        if (baseMeshNode.supportFastProvision && !baseMeshNode.supportFastGattProvision) {
            this.f7499f.sendPdu(baseMeshNode, bArr);
        } else if (a.a.a.a.b.d.a.f1336c) {
            new Thread(new RunnableC0331f(this, c(mtu, bArr), baseMeshNode)).start();
        } else {
            this.f7499f.sendPdu(baseMeshNode, b(mtu, bArr));
        }
    }

    public C0337l(Context context, s sVar, byte[] bArr) {
        this(context, sVar, bArr, null);
    }

    public byte[] b() {
        return this.f7498e;
    }

    public Map<Integer, ProvisionedMeshNode> c() {
        return this.f7495b;
    }

    public s d() {
        return this.f7496c;
    }

    public void g() {
        o oVar = this.f7500g;
        if (oVar != null) {
            oVar.c();
        }
    }

    public C0337l(Context context, s sVar, byte[] bArr, InterfaceC0326a interfaceC0326a) throws NumberFormatException {
        this.f7495b = new LinkedHashMap();
        this.f7498e = new byte[]{7, -1};
        this.f7506m = false;
        this.f7507n = new Handler(Looper.getMainLooper());
        this.f7497d = context;
        this.f7496c = sVar == null ? new s(context) : sVar;
        this.f7500g = new o(context, this, this, interfaceC0326a);
        this.f7501h = new b.b.a(context, this, this);
        e();
        if (bArr != null) {
            h(bArr);
        } else {
            f();
        }
    }

    @Override // b.InterfaceC0329d
    public void b(ProvisionedMeshNode provisionedMeshNode) {
        if (provisionedMeshNode != null) {
            int unicastAddressInt = AddressUtils.getUnicastAddressInt(provisionedMeshNode.getUnicastAddress());
            d(provisionedMeshNode);
            this.f7495b.remove(Integer.valueOf(unicastAddressInt));
        }
    }

    @Override // b.InterfaceC0327b
    public void c(ProvisionedMeshNode provisionedMeshNode) {
        this.f7495b.put(Integer.valueOf(AddressUtils.getUnicastAddressInt(provisionedMeshNode.getUnicastAddress())), provisionedMeshNode);
        e(provisionedMeshNode);
        f(provisionedMeshNode);
    }

    public final void d(ProvisionedMeshNode provisionedMeshNode) {
        if (this.f7506m) {
            SharedPreferences.Editor editorEdit = this.f7497d.getSharedPreferences(Utils.PROVISIONED_NODES_FILE, 0).edit();
            editorEdit.remove(MeshParserUtils.bytesToHex(provisionedMeshNode.getUnicastAddress(), true));
            editorEdit.apply();
        }
    }

    public void a(InterfaceC0338m interfaceC0338m) {
        this.f7499f = interfaceC0338m;
    }

    public void a(p pVar) {
        this.f7500g.a(pVar);
    }

    public final void f(ProvisionedMeshNode provisionedMeshNode) {
        if (this.f7506m) {
            SharedPreferences.Editor editorEdit = this.f7497d.getSharedPreferences(Utils.PROVISIONED_NODES_FILE, 0).edit();
            editorEdit.putString(MeshParserUtils.bytesToHex(provisionedMeshNode.getUnicastAddress(), true), JSON.toJSONString(provisionedMeshNode, SerializerFeature.WriteClassName, SerializerFeature.WriteNullListAsEmpty));
            editorEdit.apply();
        }
    }

    public void a(q qVar) {
        this.f7501h.a(qVar);
    }

    public final byte[] b(int i2, byte[] bArr) {
        int iMin;
        int i3 = i2 - 1;
        int length = (bArr.length + i3) / i2;
        byte b2 = bArr[0];
        if (length <= 1) {
            return bArr;
        }
        byte[] bArr2 = new byte[(bArr.length + length) - 1];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < length; i6++) {
            if (i6 == 0) {
                iMin = Math.min(bArr.length - i4, i2);
                System.arraycopy(bArr, i4, bArr2, i5, iMin);
                bArr2[0] = (byte) (b2 | 64);
            } else if (i6 == length - 1) {
                iMin = Math.min(bArr.length - i4, i2);
                bArr2[i5] = (byte) (b2 | 192);
                System.arraycopy(bArr, i4, bArr2, i5 + 1, iMin);
            } else {
                iMin = Math.min(bArr.length - i4, i3);
                bArr2[i5] = (byte) (b2 | 128);
                System.arraycopy(bArr, i4, bArr2, i5 + 1, iMin);
            }
            i4 += iMin;
            i5 += i2;
        }
        return bArr2;
    }

    public final boolean i(byte[] bArr) {
        int i2 = (bArr[0] & 192) >> 6;
        return i2 == 1 || i2 == 2 || i2 == 3;
    }

    public void a(InterfaceC0326a interfaceC0326a) {
        this.f7500g.a(interfaceC0326a);
    }

    public final List<byte[]> c(int i2, byte[] bArr) {
        int iMin;
        ArrayList arrayList = new ArrayList();
        int i3 = i2 - 1;
        int length = (bArr.length + i3) / i2;
        byte b2 = bArr[0];
        if (length > 1) {
            byte[] bArr2 = new byte[(bArr.length + length) - 1];
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < length; i6++) {
                if (i6 == 0) {
                    iMin = Math.min(bArr.length - i4, i2);
                    System.arraycopy(bArr, i4, bArr2, i5, iMin);
                    bArr2[0] = (byte) (b2 | 64);
                    arrayList.add(Arrays.copyOfRange(bArr2, 0, iMin));
                } else if (i6 == length - 1) {
                    iMin = Math.min(bArr.length - i4, i2);
                    bArr2[i5] = (byte) (b2 | 192);
                    System.arraycopy(bArr, i4, bArr2, i5 + 1, iMin);
                    arrayList.add(Arrays.copyOfRange(bArr2, i5, i5 + iMin + 1));
                } else {
                    iMin = Math.min(bArr.length - i4, i3);
                    bArr2[i5] = (byte) (b2 | 128);
                    System.arraycopy(bArr, i4, bArr2, i5 + 1, iMin);
                    arrayList.add(Arrays.copyOfRange(bArr2, i5, i5 + iMin + 1));
                }
                i4 += iMin;
                i5 += i2;
            }
            return arrayList;
        }
        arrayList.add(bArr);
        return arrayList;
    }

    public final List<Integer> a(Map<String, ?> map) throws NumberFormatException {
        Set<String> setKeySet = map.keySet();
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = setKeySet.iterator();
        while (it.hasNext()) {
            Integer numDecode = Integer.decode(it.next());
            numDecode.intValue();
            arrayList.add(numDecode);
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public final byte[] d(int i2, byte[] bArr) {
        int iMin;
        int length = (bArr.length + (i2 - 1)) / i2;
        if (length <= 1) {
            return bArr;
        }
        int i3 = length - 1;
        int length2 = bArr.length - i3;
        byte[] bArr2 = new byte[length2];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < length; i6++) {
            if (i6 == 0) {
                iMin = Math.min(length2 - i5, i2);
                System.arraycopy(bArr, i4, bArr2, i5, iMin);
                bArr2[0] = (byte) (bArr2[0] & 63);
            } else if (i6 == i3) {
                iMin = Math.min(length2 - i5, i2);
                System.arraycopy(bArr, i4 + 1, bArr2, i5, iMin);
            } else {
                iMin = Math.min(length2 - i5, i2) - 1;
                System.arraycopy(bArr, i4 + 1, bArr2, i5, iMin);
            }
            i4 += i2;
            i5 += iMin;
        }
        return bArr2;
    }

    public final void h() {
        if (this.f7506m) {
            SharedPreferences.Editor editorEdit = this.f7497d.getSharedPreferences(Utils.PROVISIONED_NODES_FILE, 0).edit();
            Iterator<Map.Entry<Integer, ProvisionedMeshNode>> it = this.f7495b.entrySet().iterator();
            while (it.hasNext()) {
                ProvisionedMeshNode value = it.next().getValue();
                editorEdit.putString(MeshParserUtils.bytesToHex(value.getUnicastAddress(), true), JSON.toJSONString(value, SerializerFeature.WriteClassName));
            }
            editorEdit.apply();
        }
    }

    public boolean f(byte[] bArr) {
        return bArr != null && bArr[0] == 1;
    }

    public final void a(BaseMeshNode baseMeshNode, int i2, byte[] bArr, a.a.a.a.b.h.a aVar) {
        if (bArr == null) {
            return;
        }
        if (i(bArr) && (bArr = a(bArr)) == null) {
            return;
        }
        a(baseMeshNode, bArr, aVar);
    }

    public final void e(ProvisionedMeshNode provisionedMeshNode) {
        int unicastAddressInt = provisionedMeshNode.getUnicastAddressInt() + provisionedMeshNode.getNumberOfElements();
        byte[] bArr = this.f7498e;
        if (unicastAddressInt == ((bArr[1] & 255) | ((bArr[0] & 255) << 8))) {
            unicastAddressInt++;
        }
        this.f7496c.e(unicastAddressInt);
    }

    public final void a(BaseMeshNode baseMeshNode, byte[] bArr, a.a.a.a.b.h.a aVar) {
        byte b2 = bArr[0];
        if (b2 == 0) {
            if (baseMeshNode instanceof ProvisionedMeshNode) {
                a.a.a.a.b.m.a.a(f7494a, "Received network pdu: " + MeshParserUtils.bytesToHex(bArr, true));
                this.f7501h.a((ProvisionedMeshNode) baseMeshNode, bArr, aVar);
                return;
            }
            return;
        }
        if (b2 == 1) {
            a.a.a.a.b.m.a.a(f7494a, "Received mesh beacon: " + MeshParserUtils.bytesToHex(bArr, true));
            return;
        }
        if (b2 == 2) {
            a.a.a.a.b.m.a.a(f7494a, "Received proxy configuration message: " + MeshParserUtils.bytesToHex(bArr, true));
            return;
        }
        if (b2 != 3) {
            a.a.a.a.b.m.a.d(f7494a, "Unknown pdu received: " + MeshParserUtils.bytesToHex(bArr, true));
            if (baseMeshNode instanceof UnprovisionedMeshNode) {
                this.f7500g.d((UnprovisionedMeshNode) baseMeshNode, bArr);
                return;
            }
            return;
        }
        a.a.a.a.b.m.a.a(f7494a, "Received provisioning message: " + MeshParserUtils.bytesToHex(bArr, true));
        this.f7500g.b((UnprovisionedMeshNode) baseMeshNode, bArr);
    }

    public final byte[] e(byte[] bArr) {
        if (bArr == null || bArr.length <= 8) {
            return null;
        }
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(bArr, 9, 8);
        return byteBufferOrder.array();
    }

    public String b(byte[] bArr) {
        return MeshParserUtils.bytesToHex(SecureUtils.calculateK3(bArr), false);
    }

    public final byte[] d(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(bArr, 1, 8);
        return byteBufferOrder.array();
    }

    public boolean b(ProvisionedMeshNode provisionedMeshNode, byte[] bArr) {
        byte[] bArrE;
        byte[] bArrC = c(bArr);
        if (bArrC == null || (bArrE = e(bArr)) == null) {
            return false;
        }
        boolean zEquals = Arrays.equals(bArrC, SecureUtils.calculateHash(provisionedMeshNode.getIdentityKey(), bArrE, provisionedMeshNode.getUnicastAddress()));
        if (zEquals) {
            provisionedMeshNode.setNodeIdentifier(MeshParserUtils.bytesToHex(bArrC, false));
        }
        return zEquals;
    }

    public void b(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, byte[] bArr2, int i2) {
        this.f7501h.b(provisionedMeshNode, 0, bArr, bArr2, i2);
    }

    public final byte[] c(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(bArr, 1, 8);
        return byteBufferOrder.array();
    }

    public final void b(ProvisionedMeshNode provisionedMeshNode, boolean z2, String str, byte[] bArr, boolean z3, int i2, int i3, byte[] bArr2, IActionListener iActionListener) {
        this.f7501h.a(provisionedMeshNode, ProxyProtocolMessageType.NetworkPDU, z2, str, bArr, z3, i2, i3, bArr2);
        a(provisionedMeshNode.getUnicastAddress(), 13871105, new byte[]{bArr2[1], bArr2[2]}, new C0334i(this, iActionListener));
    }

    public void b(byte[] bArr, int i2, byte[] bArr2, IActionListener<Object> iActionListener) {
        this.f7501h.a(bArr, i2, bArr2, iActionListener);
    }

    public final void a(BaseMeshNode baseMeshNode, int i2, byte[] bArr) {
        if (i(bArr)) {
            byte[] bArrA = a(i2, bArr);
            if (bArrA == null) {
                return;
            } else {
                bArr = d(i2, bArrA);
            }
        }
        a(baseMeshNode, bArr);
    }

    public final void a(BaseMeshNode baseMeshNode, byte[] bArr) {
        byte b2 = bArr[0];
        if (b2 == 0) {
            if (baseMeshNode instanceof ProvisionedMeshNode) {
                a.a.a.a.b.m.a.a(f7494a, "Network pdu sent: " + MeshParserUtils.bytesToHex(bArr, true));
                this.f7501h.a((ProvisionedMeshNode) baseMeshNode, bArr);
                return;
            }
            return;
        }
        if (b2 == 1) {
            a.a.a.a.b.m.a.a(f7494a, "Mesh beacon pdu sent: " + MeshParserUtils.bytesToHex(bArr, true));
            return;
        }
        if (b2 == 2) {
            a.a.a.a.b.m.a.a(f7494a, "Proxy configuration pdu sent: " + MeshParserUtils.bytesToHex(bArr, true));
            return;
        }
        if (b2 != 3) {
            a.a.a.a.b.m.a.d(f7494a, "Unknown pdu sent: " + MeshParserUtils.bytesToHex(bArr, true));
            return;
        }
        a.a.a.a.b.m.a.a(f7494a, "Provisioning pdu sent: " + MeshParserUtils.bytesToHex(bArr, true));
        this.f7500g.a((UnprovisionedMeshNode) baseMeshNode);
    }

    @Override // b.InterfaceC0329d
    public void a(ProvisionedMeshNode provisionedMeshNode) {
        if (provisionedMeshNode != null) {
            int unicastAddressInt = AddressUtils.getUnicastAddressInt(provisionedMeshNode.getUnicastAddress());
            a.a.a.a.b.m.a.c(f7494a, "updateMeshNode: unicast " + unicastAddressInt);
            this.f7495b.put(Integer.valueOf(unicastAddressInt), provisionedMeshNode);
            f(provisionedMeshNode);
        }
    }

    public final byte[] a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        byte b2 = bArr[0];
        int i2 = (b2 & 192) >> 6;
        if (i2 == 1) {
            this.f7503j = bArr.length;
            this.f7502i = bArr;
            bArr[0] = (byte) (b2 & 63);
        } else {
            if ((i2 != 2 && i2 != 3) || this.f7502i == null) {
                return null;
            }
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 1, bArr.length);
            int length = bArrCopyOfRange.length;
            byte[] bArr2 = this.f7502i;
            byte[] bArr3 = new byte[bArr2.length + length];
            System.arraycopy(bArr2, 0, bArr3, 0, this.f7503j);
            System.arraycopy(bArrCopyOfRange, 0, bArr3, this.f7503j, length);
            this.f7503j += length;
            this.f7502i = bArr3;
            if (i2 == 3) {
                this.f7502i = null;
                return bArr3;
            }
        }
        return null;
    }

    public final byte[] a(int i2, byte[] bArr) {
        if (this.f7504k == null) {
            this.f7505l = Math.min(bArr.length, i2);
            this.f7504k = bArr;
        } else {
            int iMin = Math.min(bArr.length, i2);
            byte[] bArr2 = this.f7504k;
            byte[] bArr3 = new byte[bArr2.length + iMin];
            System.arraycopy(bArr2, 0, bArr3, 0, this.f7505l);
            System.arraycopy(bArr, 0, bArr3, this.f7505l, iMin);
            this.f7505l += iMin;
            this.f7504k = bArr3;
            if (iMin < i2) {
                this.f7504k = null;
                return bArr3;
            }
        }
        return null;
    }

    public void a(@NonNull String str, String str2, byte[] bArr, UnprovisionedMeshNodeData unprovisionedMeshNodeData, a.a.a.a.b.i.J j2) {
        this.f7500g.a(str, str2, this.f7496c.h(), this.f7496c.g(), this.f7496c.d(), this.f7496c.f(), this.f7496c.i(), this.f7496c.e(), this.f7498e, bArr, unprovisionedMeshNodeData, j2);
    }

    public void a(@NonNull UnprovisionedMeshNode unprovisionedMeshNode) {
        this.f7500g.g(unprovisionedMeshNode);
    }

    public boolean a(String str, byte[] bArr) {
        byte[] bArrD = d(bArr);
        return bArrD != null && str.equals(MeshParserUtils.bytesToHex(bArrD, false).toUpperCase());
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, int i2, String str) {
        if (str != null && !str.isEmpty()) {
            this.f7501h.a(provisionedMeshNode, i2, str, 0);
            return;
        }
        throw new IllegalArgumentException(this.f7497d.getString(R.string.error_null_key));
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, MeshModel meshModel, int i2) {
        this.f7501h.a(provisionedMeshNode, 0, bArr, meshModel.getModelId(), i2);
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, int i2, int i3) {
        this.f7501h.a(provisionedMeshNode, 0, bArr, i2, i3);
    }

    public void a(ConfigModelPublicationSetParams configModelPublicationSetParams) {
        this.f7501h.a(configModelPublicationSetParams);
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, byte[] bArr2, int i2) {
        this.f7501h.a(provisionedMeshNode, 0, bArr, bArr2, i2);
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, int i2, byte[] bArr) {
        a.a.a.a.b.m.a.a(f7494a, "setProxyFilterType, paramter: " + ConvertUtils.bytes2HexString(bArr));
        if (provisionedMeshNode != null) {
            byte[] bArr2 = {0, 0};
            Map<Integer, String> addedAppKeys = provisionedMeshNode.getAddedAppKeys();
            if (addedAppKeys == null || addedAppKeys.size() <= 0) {
                return;
            }
            Integer next = addedAppKeys.keySet().iterator().next();
            this.f7501h.a(provisionedMeshNode, ProxyProtocolMessageType.ProxyConfiguration, false, provisionedMeshNode.getAddedAppKeys().get(next), bArr2, false, next.intValue(), i2, bArr);
        }
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, byte[] bArr) {
        a.a.a.a.b.m.a.a(f7494a, "addAddressToFilter, addAddressParameter: " + ConvertUtils.bytes2HexString(bArr));
        if (provisionedMeshNode != null) {
            byte[] bArr2 = {0, 0};
            Map<Integer, String> addedAppKeys = provisionedMeshNode.getAddedAppKeys();
            if (addedAppKeys == null || addedAppKeys.size() <= 0) {
                return;
            }
            Integer next = addedAppKeys.keySet().iterator().next();
            this.f7501h.a(provisionedMeshNode, ProxyProtocolMessageType.ProxyConfiguration, false, provisionedMeshNode.getAddedAppKeys().get(next), bArr2, false, next.intValue(), 1, bArr);
        }
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, boolean z2, String str, byte[] bArr, boolean z3, int i2, int i3, byte[] bArr2) {
        this.f7501h.a(provisionedMeshNode, ProxyProtocolMessageType.NetworkPDU, z2, str, bArr, z3, i2, i3, bArr2);
    }

    public boolean a(ProvisionedMeshNode provisionedMeshNode, boolean z2, byte[] bArr, int i2, byte[] bArr2) {
        if (provisionedMeshNode == null || provisionedMeshNode.getAddedAppKeys() == null) {
            return false;
        }
        this.f7501h.a(provisionedMeshNode, ProxyProtocolMessageType.NetworkPDU, z2, provisionedMeshNode.getAddedAppKeys().get(0), bArr, false, 0, i2, bArr2);
        return true;
    }

    public <T> void a(ProvisionedMeshNode provisionedMeshNode, String str, byte[] bArr, int i2, int i3, byte[] bArr2, IActionListener<T> iActionListener) {
        if (provisionedMeshNode != null && provisionedMeshNode.getAddedAppKeys() != null) {
            String str2 = provisionedMeshNode.getAddedAppKeys().get(0);
            new a(bArr, i3, bArr2, b.c.a.b().c(provisionedMeshNode.getNetworkKey(), bArr), new C0332g(this), iActionListener).a();
            this.f7501h.a(provisionedMeshNode, ProxyProtocolMessageType.NetworkPDU, true, TextUtils.isEmpty(str) ? str2 : str, bArr, false, 0, i2, bArr2);
            return;
        }
        Utils.notifyFailed(iActionListener, -7, "Can not found target device or empty appKeys");
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, boolean z2, String str, byte[] bArr, boolean z3, int i2, int i3, byte[] bArr2, IActionListener iActionListener) {
        b(provisionedMeshNode, z2, str, bArr, z3, i2, i3, bArr2, iActionListener);
        RunnableC0333h runnableC0333h = new RunnableC0333h(this, provisionedMeshNode, bArr2, iActionListener, z2, str, bArr, z3, i2, i3);
        this.f7508o = runnableC0333h;
        this.f7507n.postDelayed(runnableC0333h, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    public void a(byte[] bArr, int i2, IActionListener<Object> iActionListener) {
        this.f7501h.a(bArr, i2, (byte[]) null, iActionListener);
    }

    public void a(byte[] bArr, int i2, byte[] bArr2, IActionListener<Object> iActionListener) {
        this.f7501h.a(bArr, i2, bArr2, iActionListener);
    }
}
