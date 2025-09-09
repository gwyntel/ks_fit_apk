package b.b;

import android.content.Context;
import android.util.LongSparseArray;
import android.util.Pair;
import b.InterfaceC0327b;
import b.InterfaceC0328c;
import b.InterfaceC0329d;
import b.e.i;
import b.q;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;
import meshprovisioner.ProxyProtocolMessageType;
import meshprovisioner.configuration.CommonMessageV2;
import meshprovisioner.configuration.ConfigAppKeyAdd;
import meshprovisioner.configuration.ConfigModelAppBind;
import meshprovisioner.configuration.ConfigModelPublicationSet;
import meshprovisioner.configuration.ConfigModelSubscriptionAdd;
import meshprovisioner.configuration.ConfigModelSubscriptionDelete;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.ConfigModelPublicationSetParams;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes2.dex */
public class a implements InterfaceC0328c {

    /* renamed from: a, reason: collision with root package name */
    public static final String f7364a = "" + a.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    public static LongSparseArray<IActionListener<Object>> f7365b = new LongSparseArray<>();

    /* renamed from: c, reason: collision with root package name */
    public static Map<Integer, Pair<Integer, byte[]>> f7366c;

    /* renamed from: d, reason: collision with root package name */
    public static Map<Integer, Integer> f7367d;

    /* renamed from: e, reason: collision with root package name */
    public final Context f7368e;

    /* renamed from: f, reason: collision with root package name */
    public final InterfaceC0329d f7369f;

    /* renamed from: g, reason: collision with root package name */
    public final InterfaceC0327b f7370g;

    /* renamed from: h, reason: collision with root package name */
    public q f7371h;

    /* renamed from: i, reason: collision with root package name */
    public long f7372i = 0;

    public a(Context context, InterfaceC0329d interfaceC0329d, InterfaceC0327b interfaceC0327b) {
        this.f7368e = context;
        this.f7369f = interfaceC0329d;
        this.f7370g = interfaceC0327b;
        i.c().a(interfaceC0329d);
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, byte[] bArr) {
    }

    public void b(ProvisionedMeshNode provisionedMeshNode, int i2, byte[] bArr, byte[] bArr2, int i3) {
        ConfigModelSubscriptionDelete configModelSubscriptionDelete = new ConfigModelSubscriptionDelete(this.f7368e, provisionedMeshNode, this, i2, bArr, bArr2, i3);
        configModelSubscriptionDelete.setTransportCallbacks(this.f7369f);
        configModelSubscriptionDelete.setStatusCallbacks(this.f7371h);
        configModelSubscriptionDelete.executeSend();
    }

    @Override // b.InterfaceC0328c
    public void a(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, boolean z2) {
    }

    public void a(q qVar) {
        this.f7371h = qVar;
        i.c().a(this.f7371h);
    }

    public final int a(byte[] bArr) {
        if (bArr.length == 2) {
            return ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getShort();
        }
        return ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getInt();
    }

    public void b(byte[] bArr, int i2, byte[] bArr2, IActionListener<Object> iActionListener) {
        long jA = a(bArr, i2, bArr2);
        a.a.a.a.b.m.a.a(f7364a, "Unregister message listener with key: " + jA + ", address: " + MeshParserUtils.bytesToHex(bArr, false) + ", opcode: " + i2);
        f7365b.remove(jA);
    }

    /* JADX WARN: Removed duplicated region for block: B:160:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03fc A[Catch: Exception -> 0x002c, TryCatch #0 {Exception -> 0x002c, blocks: (B:3:0x0009, B:5:0x001f, B:7:0x0024, B:10:0x002f, B:12:0x0053, B:14:0x005c, B:16:0x0072, B:18:0x008a, B:20:0x0090, B:22:0x0093, B:23:0x00a5, B:25:0x00b3, B:45:0x010c, B:48:0x0114, B:50:0x011b, B:57:0x0133, B:59:0x014c, B:236:0x0703, B:238:0x0714, B:239:0x071a, B:241:0x0720, B:242:0x072f, B:54:0x0122, B:55:0x0129, B:56:0x012b, B:64:0x0169, B:66:0x0173, B:68:0x0177, B:70:0x0192, B:72:0x019a, B:75:0x01a9, B:79:0x020e, B:81:0x0227, B:84:0x0239, B:87:0x0241, B:89:0x024e, B:90:0x0253, B:92:0x025b, B:94:0x0279, B:98:0x028f, B:101:0x02a6, B:102:0x02b0, B:104:0x02ba, B:107:0x02bf, B:109:0x02c6, B:111:0x02c9, B:114:0x02d5, B:116:0x02d9, B:118:0x02dd, B:120:0x02ec, B:122:0x02f0, B:124:0x02f4, B:126:0x0308, B:128:0x030b, B:130:0x030f, B:132:0x0320, B:134:0x0328, B:137:0x0340, B:139:0x035c, B:141:0x0378, B:142:0x038e, B:144:0x039a, B:146:0x03a0, B:148:0x03ac, B:150:0x03b8, B:152:0x03c0, B:154:0x03d6, B:156:0x03e8, B:161:0x03fc, B:163:0x040e, B:165:0x0422, B:171:0x045f, B:175:0x0479, B:177:0x049c, B:179:0x04c9, B:181:0x04cc, B:184:0x04ec, B:183:0x04dd, B:178:0x04ad, B:187:0x0514, B:191:0x051f, B:193:0x053c, B:195:0x056a, B:198:0x0583, B:200:0x0595, B:202:0x05ab, B:203:0x05ce, B:205:0x05d6, B:194:0x054c, B:208:0x05ea, B:213:0x05f7, B:215:0x062b, B:217:0x0652, B:219:0x0655, B:222:0x0675, B:221:0x0666, B:216:0x0639, B:225:0x068e, B:230:0x069b, B:233:0x06ef), top: B:246:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:203:0x05ce A[Catch: Exception -> 0x002c, TRY_LEAVE, TryCatch #0 {Exception -> 0x002c, blocks: (B:3:0x0009, B:5:0x001f, B:7:0x0024, B:10:0x002f, B:12:0x0053, B:14:0x005c, B:16:0x0072, B:18:0x008a, B:20:0x0090, B:22:0x0093, B:23:0x00a5, B:25:0x00b3, B:45:0x010c, B:48:0x0114, B:50:0x011b, B:57:0x0133, B:59:0x014c, B:236:0x0703, B:238:0x0714, B:239:0x071a, B:241:0x0720, B:242:0x072f, B:54:0x0122, B:55:0x0129, B:56:0x012b, B:64:0x0169, B:66:0x0173, B:68:0x0177, B:70:0x0192, B:72:0x019a, B:75:0x01a9, B:79:0x020e, B:81:0x0227, B:84:0x0239, B:87:0x0241, B:89:0x024e, B:90:0x0253, B:92:0x025b, B:94:0x0279, B:98:0x028f, B:101:0x02a6, B:102:0x02b0, B:104:0x02ba, B:107:0x02bf, B:109:0x02c6, B:111:0x02c9, B:114:0x02d5, B:116:0x02d9, B:118:0x02dd, B:120:0x02ec, B:122:0x02f0, B:124:0x02f4, B:126:0x0308, B:128:0x030b, B:130:0x030f, B:132:0x0320, B:134:0x0328, B:137:0x0340, B:139:0x035c, B:141:0x0378, B:142:0x038e, B:144:0x039a, B:146:0x03a0, B:148:0x03ac, B:150:0x03b8, B:152:0x03c0, B:154:0x03d6, B:156:0x03e8, B:161:0x03fc, B:163:0x040e, B:165:0x0422, B:171:0x045f, B:175:0x0479, B:177:0x049c, B:179:0x04c9, B:181:0x04cc, B:184:0x04ec, B:183:0x04dd, B:178:0x04ad, B:187:0x0514, B:191:0x051f, B:193:0x053c, B:195:0x056a, B:198:0x0583, B:200:0x0595, B:202:0x05ab, B:203:0x05ce, B:205:0x05d6, B:194:0x054c, B:208:0x05ea, B:213:0x05f7, B:215:0x062b, B:217:0x0652, B:219:0x0655, B:222:0x0675, B:221:0x0666, B:216:0x0639, B:225:0x068e, B:230:0x069b, B:233:0x06ef), top: B:246:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(meshprovisioner.configuration.ProvisionedMeshNode r28, byte[] r29, a.a.a.a.b.h.a r30) {
        /*
            Method dump skipped, instructions count: 1891
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: b.b.a.a(meshprovisioner.configuration.ProvisionedMeshNode, byte[], a.a.a.a.b.h.a):void");
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, int i2, String str, int i3) {
        provisionedMeshNode.setAddedAppKey(i2, str);
        ConfigAppKeyAdd configAppKeyAdd = new ConfigAppKeyAdd(this.f7368e, provisionedMeshNode, this, i3, str, i2);
        configAppKeyAdd.setTransportCallbacks(this.f7369f);
        configAppKeyAdd.setStatusCallbacks(this.f7371h);
        configAppKeyAdd.executeSend();
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, int i2, byte[] bArr, int i3, int i4) {
        ConfigModelAppBind configModelAppBind = new ConfigModelAppBind(this.f7368e, provisionedMeshNode, this, i2, bArr, i3, i4);
        configModelAppBind.setTransportCallbacks(this.f7369f);
        configModelAppBind.setStatusCallbacks(this.f7371h);
        configModelAppBind.executeSend();
    }

    public void a(ConfigModelPublicationSetParams configModelPublicationSetParams) {
        ConfigModelPublicationSet configModelPublicationSet = new ConfigModelPublicationSet(this.f7368e, configModelPublicationSetParams, this);
        configModelPublicationSet.setTransportCallbacks(this.f7369f);
        configModelPublicationSet.setStatusCallbacks(this.f7371h);
        configModelPublicationSet.executeSend();
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, int i2, byte[] bArr, byte[] bArr2, int i3) {
        ConfigModelSubscriptionAdd configModelSubscriptionAdd = new ConfigModelSubscriptionAdd(this.f7368e, provisionedMeshNode, this, i2, bArr, bArr2, i3);
        configModelSubscriptionAdd.setTransportCallbacks(this.f7369f);
        configModelSubscriptionAdd.setStatusCallbacks(this.f7371h);
        configModelSubscriptionAdd.executeSend();
    }

    public void a(ProvisionedMeshNode provisionedMeshNode, ProxyProtocolMessageType proxyProtocolMessageType, boolean z2, String str, byte[] bArr, boolean z3, int i2, int i3, byte[] bArr2) {
        if (i3 == 33350 || i3 == 33438) {
            if (f7366c == null) {
                f7366c = new LinkedHashMap();
            }
            f7366c.put(Integer.valueOf(AddressUtils.getUnicastAddressInt(bArr)), new Pair<>(Integer.valueOf(i3), bArr2));
        }
        String str2 = f7364a;
        a.a.a.a.b.m.a.c(str2, "sendCommonMessage called, opcode: " + i3 + ", address: " + MeshParserUtils.bytesToHex(bArr, true));
        if (i3 == 32795 || i3 == 32796) {
            if (f7367d == null) {
                f7367d = new LinkedHashMap();
            }
            a.a.a.a.b.m.a.c(str2, "sendCommonMessage called, put record: " + i3);
            f7367d.put(Integer.valueOf(AddressUtils.getUnicastAddressInt(bArr)), Integer.valueOf(i3));
        }
        CommonMessageV2 commonMessageV2 = new CommonMessageV2(this.f7368e, provisionedMeshNode, proxyProtocolMessageType, z2, this, str, z3, bArr, i2, i3, bArr2);
        commonMessageV2.setTransportCallbacks(this.f7369f);
        commonMessageV2.setStatusCallbacks(this.f7371h);
        commonMessageV2.executeSend();
        b.c.a.b().c().a(provisionedMeshNode.getNetworkKey(), bArr, i3, bArr2);
    }

    public void a(byte[] bArr, int i2, byte[] bArr2, IActionListener<Object> iActionListener) {
        long jA = a(bArr, i2, bArr2);
        String str = f7364a;
        a.a.a.a.b.m.a.a(str, "Register message listener with key: " + jA + ", address: " + MeshParserUtils.bytesToHex(bArr, false) + ", opcode: " + i2);
        if (f7365b.get(jA) != null) {
            a.a.a.a.b.m.a.a(str, String.format("Update desired message listener for(%s:%d)", MeshParserUtils.bytesToHex(bArr, false), Integer.valueOf(i2)));
        }
        f7365b.put(jA, iActionListener);
    }

    public final long a(byte[] bArr, int i2) {
        long j2 = ((bArr[0] & 255) << 8) | (bArr[1] & 255);
        for (int i3 = 0; i3 < MeshParserUtils.getOpCodes(i2).length; i3++) {
            j2 = (j2 << 8) | (r7[i3] & 255);
        }
        return j2;
    }

    public final long a(byte[] bArr, int i2, byte[] bArr2) {
        long j2 = ((bArr[0] & 255) << 8) | (bArr[1] & 255);
        byte[] opCodes = MeshParserUtils.getOpCodes(i2);
        for (byte b2 : opCodes) {
            j2 = (j2 << 8) | (b2 & 255);
        }
        return (bArr2 == null || bArr2.length != 2) ? j2 : (((j2 << 8) | (bArr2[0] & 255)) << 8) | (bArr2[1] & 255);
    }
}
