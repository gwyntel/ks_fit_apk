package com.alibaba.ailabs.iot.mesh.biz;

import a.a.a.a.b.G;
import a.a.a.a.b.a.C0212a;
import a.a.a.a.b.a.I;
import a.a.a.a.b.a.RunnableC0219h;
import a.a.a.a.b.a.RunnableC0220i;
import aisble.Operation;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import b.C0337l;
import b.u;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import java.util.concurrent.atomic.AtomicInteger;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes2.dex */
public class SIGMeshBizRequest implements Operation {

    /* renamed from: a, reason: collision with root package name */
    public final Type f8731a;

    /* renamed from: b, reason: collision with root package name */
    public Mode f8732b;

    /* renamed from: c, reason: collision with root package name */
    public IActionListener f8733c;

    /* renamed from: d, reason: collision with root package name */
    public b f8734d;

    /* renamed from: e, reason: collision with root package name */
    public boolean f8735e;

    /* renamed from: f, reason: collision with root package name */
    public ProvisionedMeshNode f8736f;

    /* renamed from: g, reason: collision with root package name */
    public byte[] f8737g;

    /* renamed from: h, reason: collision with root package name */
    public I<?> f8738h;

    /* renamed from: i, reason: collision with root package name */
    public AtomicInteger f8739i;

    /* renamed from: j, reason: collision with root package name */
    public byte[] f8740j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f8741k;

    /* renamed from: l, reason: collision with root package name */
    public Runnable f8742l;

    /* renamed from: m, reason: collision with root package name */
    public Handler f8743m;

    /* renamed from: n, reason: collision with root package name */
    public int f8744n;

    /* renamed from: o, reason: collision with root package name */
    public int f8745o;

    /* renamed from: p, reason: collision with root package name */
    public String f8746p;

    /* renamed from: q, reason: collision with root package name */
    public C0337l f8747q;

    /* renamed from: r, reason: collision with root package name */
    public Runnable f8748r;

    public enum InteractionModel {
        REQUEST_RESPONSE,
        FIRE_AND_FORGET,
        UNKNOWN
    }

    public enum Mode {
        UNICAST,
        MULTICAST
    }

    public enum NetworkParameter {
        DEVICE_NUMBER_1(1, 5, 4, 8, 10, 30, 205, 1, 20),
        DEVICE_NUMBER_21(1, 5, 6, 12, 10, 30, 120, 21, 50),
        DEVICE_NUMBER_51(1, 5, 8, 16, 10, 30, 120, 51, 100),
        DEVICE_NUMBER_101(1, 5, 10, 20, 10, 45, 120, 101, 150),
        DEVICE_NUMBER_151(1, 5, 12, 28, 20, 60, 120, 151, Integer.MAX_VALUE);

        public int adv_duration;
        public int boot_interval;
        public int cr_enable;
        public int device_count;
        public int group_delay_max;
        public int group_delay_min;
        public int max_device_count;
        public int min_device_count;
        public int per_interval;
        public int send_ttl;

        NetworkParameter(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
            this.cr_enable = i2;
            this.send_ttl = i3;
            this.group_delay_min = i4;
            this.group_delay_max = i5;
            this.boot_interval = i6;
            this.per_interval = i7;
            this.adv_duration = i8;
            this.min_device_count = i9;
            this.max_device_count = i10;
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x0028  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static synchronized boolean changeNetworkParameter(com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest.NetworkParameter r4, boolean r5) {
            /*
                java.lang.Class<com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest$NetworkParameter> r0 = com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest.NetworkParameter.class
                monitor-enter(r0)
                r1 = 0
                if (r4 != 0) goto L8
                monitor-exit(r0)
                return r1
            L8:
                r2 = 1
                if (r5 == 0) goto L14
                int r5 = r4.device_count     // Catch: java.lang.Throwable -> L12
                int r5 = r5 + r2
                r4.setDevice_count(r5)     // Catch: java.lang.Throwable -> L12
                goto L1e
            L12:
                r4 = move-exception
                goto L2b
            L14:
                int r5 = r4.min_device_count     // Catch: java.lang.Throwable -> L12
                if (r5 <= r2) goto L1e
                int r5 = r4.device_count     // Catch: java.lang.Throwable -> L12
                int r5 = r5 - r2
                r4.setDevice_count(r5)     // Catch: java.lang.Throwable -> L12
            L1e:
                int r5 = r4.device_count     // Catch: java.lang.Throwable -> L12
                int r3 = r4.min_device_count     // Catch: java.lang.Throwable -> L12
                if (r5 < r3) goto L28
                int r4 = r4.max_device_count     // Catch: java.lang.Throwable -> L12
                if (r5 <= r4) goto L29
            L28:
                r1 = r2
            L29:
                monitor-exit(r0)
                return r1
            L2b:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L12
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest.NetworkParameter.changeNetworkParameter(com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest$NetworkParameter, boolean):boolean");
        }

        public static NetworkParameter getNetworkParameter(int i2) {
            if (i2 == 0) {
                return null;
            }
            NetworkParameter networkParameter = i2 < 21 ? DEVICE_NUMBER_1 : i2 < 52 ? DEVICE_NUMBER_21 : i2 < 102 ? DEVICE_NUMBER_51 : i2 < 152 ? DEVICE_NUMBER_101 : DEVICE_NUMBER_151;
            networkParameter.setDevice_count(i2);
            return networkParameter;
        }

        public byte[] getParameter() {
            return new byte[]{(byte) this.cr_enable, (byte) this.send_ttl, (byte) this.group_delay_min, (byte) this.group_delay_max, (byte) this.boot_interval, (byte) this.per_interval, (byte) this.adv_duration};
        }

        public void setDevice_count(int i2) {
            this.device_count = i2;
        }

        public void setSend_ttl(int i2) {
            this.send_ttl = i2;
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'CONFIG_MODEL_SUBSCRIPTION' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class Type {
        public static final /* synthetic */ Type[] $VALUES;
        public static final Type COMMON_DEVICE_REST_NODE;
        public static final Type COMMON_FIRE_AND_FORGET;
        public static final Type COMMON_REQUEST_RESPONSE;
        public static final Type CONFIG_MODEL_SUBSCRIPTION;
        public static final Type SCENE_RECALL;
        public static final Type SCENE_RECALL_UNACKNOWLEDGED;
        public static final Type SCENE_SETUP_DELETE;
        public static final Type SCENE_SETUP_STORE;
        public static final Type UPDATE_MESH_PARAMETER;
        public static final Type VENDOR_ATTRIBUTE_SET;
        public static final Type VENDOR_ATTRIBUTE_SET_UNACKNOWLEDGED;
        public static final Type VENDOR_DELEGATE_PROTOCOL;
        public boolean access;
        public int expectedOpcode;
        public InteractionModel interactionModel;
        public int opcode;

        static {
            InteractionModel interactionModel = InteractionModel.REQUEST_RESPONSE;
            Type type = new Type("CONFIG_MODEL_SUBSCRIPTION", 0, -32741, interactionModel, -1, true);
            CONFIG_MODEL_SUBSCRIPTION = type;
            Type type2 = new Type("SCENE_SETUP_STORE", 1, 33350, interactionModel, -32187, true);
            SCENE_SETUP_STORE = type2;
            Type type3 = new Type("SCENE_SETUP_DELETE", 2, 33438, interactionModel, -32187, true);
            SCENE_SETUP_DELETE = type3;
            InteractionModel interactionModel2 = InteractionModel.FIRE_AND_FORGET;
            Type type4 = new Type("SCENE_RECALL_UNACKNOWLEDGED", 3, 33347, interactionModel2, -1, true);
            SCENE_RECALL_UNACKNOWLEDGED = type4;
            Type type5 = new Type("SCENE_RECALL", 4, 33346, interactionModel, 94, true);
            SCENE_RECALL = type5;
            Type type6 = new Type("VENDOR_ATTRIBUTE_SET_UNACKNOWLEDGED", 5, 13805569, interactionModel2, -1, true);
            VENDOR_ATTRIBUTE_SET_UNACKNOWLEDGED = type6;
            Type type7 = new Type("VENDOR_ATTRIBUTE_SET", 6, 13740033, interactionModel, 13871105, true);
            VENDOR_ATTRIBUTE_SET = type7;
            Type type8 = new Type("VENDOR_DELEGATE_PROTOCOL", 7, 81, interactionModel2, -1, false);
            VENDOR_DELEGATE_PROTOCOL = type8;
            Type type9 = new Type("COMMON_FIRE_AND_FORGET", 8, 0, interactionModel2, -1, true);
            COMMON_FIRE_AND_FORGET = type9;
            Type type10 = new Type("COMMON_REQUEST_RESPONSE", 9, 0, interactionModel, -1, true);
            COMMON_REQUEST_RESPONSE = type10;
            Type type11 = new Type("COMMON_DEVICE_REST_NODE", 10, 32841, interactionModel2, -1, true);
            COMMON_DEVICE_REST_NODE = type11;
            Type type12 = new Type("UPDATE_MESH_PARAMETER", 11, 80, interactionModel2, -1, false);
            UPDATE_MESH_PARAMETER = type12;
            $VALUES = new Type[]{type, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12};
        }

        public Type(String str, int i2, int i3, InteractionModel interactionModel, int i4, boolean z2) {
            this.opcode = i3;
            this.interactionModel = interactionModel;
            this.expectedOpcode = i4;
            this.access = z2;
        }

        public static Type valueOf(String str) {
            return (Type) Enum.valueOf(Type.class, str);
        }

        public static Type[] values() {
            return (Type[]) $VALUES.clone();
        }

        public int getExpectedOpcode() {
            return this.expectedOpcode;
        }

        public InteractionModel getInteractionModel() {
            return this.interactionModel;
        }

        public int getOpcode() {
            return this.opcode;
        }

        public boolean isAccess() {
            return this.access;
        }

        public void setExpectedOpcode(int i2) {
            this.expectedOpcode = i2;
        }

        public void setOpcode(int i2) {
            this.opcode = i2;
        }
    }

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        public Type f8749a;

        /* renamed from: b, reason: collision with root package name */
        public ProvisionedMeshNode f8750b;

        /* renamed from: f, reason: collision with root package name */
        public IActionListener f8754f;

        /* renamed from: i, reason: collision with root package name */
        public String f8757i;

        /* renamed from: c, reason: collision with root package name */
        public boolean f8751c = true;

        /* renamed from: d, reason: collision with root package name */
        public I<?> f8752d = null;

        /* renamed from: e, reason: collision with root package name */
        public b f8753e = null;

        /* renamed from: g, reason: collision with root package name */
        public byte[] f8755g = null;

        /* renamed from: h, reason: collision with root package name */
        public Mode f8756h = Mode.UNICAST;

        /* renamed from: j, reason: collision with root package name */
        public int f8758j = -1;

        public a a(Type type) {
            this.f8749a = type;
            return this;
        }

        public a a(byte[] bArr) {
            if (bArr == null || bArr.length != 2) {
                throw new IllegalArgumentException("Illegal address");
            }
            this.f8755g = bArr;
            if (AddressUtils.isValidGroupAddress(bArr)) {
                this.f8756h = Mode.MULTICAST;
            } else if (AddressUtils.isValidUnicastAddress(bArr)) {
                this.f8756h = Mode.UNICAST;
            }
            return this;
        }

        public a a(ProvisionedMeshNode provisionedMeshNode) {
            this.f8750b = provisionedMeshNode;
            return this;
        }

        public a a(boolean z2) {
            this.f8751c = z2;
            return this;
        }

        public a a(I<?> i2) {
            this.f8752d = i2;
            return this;
        }

        public a a(b bVar) {
            this.f8753e = bVar;
            return this;
        }

        public a a(IActionListener<?> iActionListener) {
            this.f8754f = iActionListener;
            return this;
        }

        public a a(String str) {
            this.f8757i = str;
            return this;
        }

        public a a(int i2) {
            this.f8758j = i2;
            return this;
        }

        public SIGMeshBizRequest a() {
            SIGMeshBizRequest sIGMeshBizRequest = new SIGMeshBizRequest(this.f8749a, this.f8756h, this.f8750b, null);
            sIGMeshBizRequest.f8735e = this.f8751c;
            I<?> i2 = this.f8752d;
            if (i2 != null) {
                sIGMeshBizRequest.f8738h = i2;
            }
            b bVar = this.f8753e;
            if (bVar != null) {
                sIGMeshBizRequest.f8734d = bVar;
            }
            IActionListener iActionListener = this.f8754f;
            if (iActionListener != null) {
                sIGMeshBizRequest.f8733c = iActionListener;
            }
            byte[] bArr = this.f8755g;
            if (bArr != null) {
                sIGMeshBizRequest.f8737g = bArr;
            }
            if (!TextUtils.isEmpty(this.f8757i)) {
                sIGMeshBizRequest.f8746p = this.f8757i;
            }
            int i3 = this.f8758j;
            if (i3 != -1) {
                sIGMeshBizRequest.f8739i = new AtomicInteger(i3);
            }
            return sIGMeshBizRequest;
        }
    }

    public interface b {
        byte[] getEncodedParameters();
    }

    public /* synthetic */ SIGMeshBizRequest(Type type, Mode mode, ProvisionedMeshNode provisionedMeshNode, RunnableC0219h runnableC0219h) {
        this(type, mode, provisionedMeshNode);
    }

    public void b() {
        Runnable runnable = this.f8742l;
        if (runnable != null) {
            this.f8743m.removeCallbacks(runnable);
        }
    }

    public byte[] c() {
        b bVar = this.f8734d;
        if (bVar != null) {
            return bVar.getEncodedParameters();
        }
        return null;
    }

    public byte[] d() {
        return this.f8740j;
    }

    public int e() {
        return this.f8745o;
    }

    public int f() {
        return this.f8744n;
    }

    public I g() {
        return this.f8738h;
    }

    public u.a h() {
        u uVarD = G.a().d();
        ProvisionedMeshNode provisionedMeshNode = this.f8736f;
        if (provisionedMeshNode == null) {
            return uVarD.d();
        }
        u.a aVarH = uVarD.h(provisionedMeshNode.getNetworkKey());
        if (aVarH != null) {
            return aVarH;
        }
        a.a.a.a.b.m.a.d("SIGMeshBizRequest", String.format("Can't find Subnet via netKey(%s)", MeshParserUtils.bytesToHex(this.f8736f.getNetworkKey(), false)));
        return uVarD.d();
    }

    public C0337l i() {
        return this.f8747q;
    }

    public byte[] j() {
        return this.f8737g;
    }

    public String k() {
        ProvisionedMeshNode provisionedMeshNode = this.f8736f;
        return provisionedMeshNode != null ? Utils.deviceId2Mac(provisionedMeshNode.getDevId()) : "";
    }

    public Type l() {
        return this.f8731a;
    }

    public IActionListener m() {
        return this.f8733c;
    }

    public boolean n() {
        return this.f8741k;
    }

    public void o() {
        this.f8739i.decrementAndGet();
    }

    public void p() {
        this.f8739i.getAndDecrement();
    }

    public void q() {
        Runnable runnable = this.f8748r;
        if (runnable != null) {
            runnable.run();
        }
    }

    public SIGMeshBizRequest(@NonNull Type type, @NonNull Mode mode) {
        this.f8735e = true;
        this.f8739i = new AtomicInteger(3);
        this.f8740j = null;
        this.f8741k = false;
        this.f8742l = null;
        this.f8743m = new Handler(Looper.getMainLooper());
        this.f8748r = null;
        this.f8731a = type;
        this.f8732b = mode;
        this.f8744n = type.opcode;
        this.f8745o = type.expectedOpcode;
    }

    public SIGMeshBizRequest a(IActionListener iActionListener) {
        this.f8733c = iActionListener;
        return this;
    }

    public boolean a() {
        return this.f8739i.get() > 0;
    }

    public void a(byte[] bArr) {
        this.f8740j = bArr;
    }

    public static C0212a a(String str, String str2, int i2, int i3, int i4, int i5) {
        return new C0212a(Type.CONFIG_MODEL_SUBSCRIPTION, str, str2, i2, i3, i4, i5);
    }

    public void a(int i2, Runnable runnable) {
        RunnableC0220i runnableC0220i = new RunnableC0220i(this, runnable);
        this.f8742l = runnableC0220i;
        this.f8743m.postDelayed(runnableC0220i, i2);
    }

    public SIGMeshBizRequest(@NonNull Type type, @NonNull Mode mode, @NonNull ProvisionedMeshNode provisionedMeshNode) {
        this(type, mode);
        this.f8736f = provisionedMeshNode;
    }

    public void a(C0337l c0337l) {
        this.f8747q = c0337l;
    }

    public void a(Runnable runnable) {
        this.f8748r = runnable;
    }
}
