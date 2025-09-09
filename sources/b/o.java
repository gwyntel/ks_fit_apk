package b;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import b.InterfaceC0326a;
import java.nio.ByteBuffer;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.states.GenieProvisioningStartState;
import meshprovisioner.states.ProvisioningCapabilities;
import meshprovisioner.states.ProvisioningCapabilitiesState;
import meshprovisioner.states.ProvisioningCompleteState;
import meshprovisioner.states.ProvisioningConfirmationState;
import meshprovisioner.states.ProvisioningDataState;
import meshprovisioner.states.ProvisioningFailedState;
import meshprovisioner.states.ProvisioningInviteState;
import meshprovisioner.states.ProvisioningPublicKeyState;
import meshprovisioner.states.ProvisioningRandomConfirmationState;
import meshprovisioner.states.ProvisioningState;
import meshprovisioner.states.UnprovisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNodeData;
import meshprovisioner.utils.ParseOutputOOBActions;
import meshprovisioner.utils.ParseProvisioningAlgorithm;
import meshprovisioner.utils.UnprovisionedMeshNodeUtil;

/* loaded from: classes2.dex */
public class o implements InterfaceC0326a.InterfaceC0015a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f7519a = "o";

    /* renamed from: b, reason: collision with root package name */
    public final InterfaceC0329d f7520b;

    /* renamed from: c, reason: collision with root package name */
    public final Context f7521c;

    /* renamed from: d, reason: collision with root package name */
    public p f7522d;

    /* renamed from: e, reason: collision with root package name */
    public int f7523e;

    /* renamed from: f, reason: collision with root package name */
    public int f7524f;

    /* renamed from: g, reason: collision with root package name */
    public int f7525g;

    /* renamed from: h, reason: collision with root package name */
    public int f7526h;

    /* renamed from: i, reason: collision with root package name */
    public int f7527i;

    /* renamed from: j, reason: collision with root package name */
    public int f7528j;

    /* renamed from: k, reason: collision with root package name */
    public int f7529k;

    /* renamed from: l, reason: collision with root package name */
    public int f7530l;

    /* renamed from: m, reason: collision with root package name */
    public int f7531m;

    /* renamed from: n, reason: collision with root package name */
    public ProvisioningState f7532n;

    /* renamed from: o, reason: collision with root package name */
    public boolean f7533o;

    /* renamed from: p, reason: collision with root package name */
    public boolean f7534p;

    /* renamed from: q, reason: collision with root package name */
    public InterfaceC0327b f7535q;

    /* renamed from: r, reason: collision with root package name */
    public InterfaceC0326a f7536r;

    /* renamed from: s, reason: collision with root package name */
    public Handler f7537s = new Handler(Looper.getMainLooper());

    /* renamed from: t, reason: collision with root package name */
    public boolean f7538t = false;

    /* renamed from: u, reason: collision with root package name */
    public SparseIntArray f7539u = new SparseIntArray();

    public o(Context context, InterfaceC0329d interfaceC0329d, InterfaceC0327b interfaceC0327b, InterfaceC0326a interfaceC0326a) {
        this.f7521c = context;
        this.f7520b = interfaceC0329d;
        this.f7535q = interfaceC0327b;
        this.f7536r = interfaceC0326a;
    }

    public void a(InterfaceC0326a interfaceC0326a) {
        this.f7536r = interfaceC0326a;
    }

    public void b(UnprovisionedMeshNode unprovisionedMeshNode, byte[] bArr) {
        if (this.f7538t) {
        }
        int i2 = n.f7518a[this.f7532n.getState().ordinal()];
        if (i2 == 2) {
            if (c(bArr)) {
                e(unprovisionedMeshNode, bArr);
                return;
            } else {
                c(unprovisionedMeshNode, bArr);
                return;
            }
        }
        if (i2 == 4) {
            if (c(bArr)) {
                a(unprovisionedMeshNode, bArr);
                return;
            } else {
                c(unprovisionedMeshNode, bArr);
                return;
            }
        }
        switch (i2) {
            case 6:
                if (!c(bArr)) {
                    c(unprovisionedMeshNode, bArr);
                    break;
                } else if (a(bArr)) {
                    f(unprovisionedMeshNode);
                    break;
                }
                break;
            case 7:
                if (!c(bArr)) {
                    c(unprovisionedMeshNode, bArr);
                    break;
                } else {
                    b(bArr);
                    break;
                }
            case 8:
            case 9:
            case 10:
                c(unprovisionedMeshNode, bArr);
                break;
        }
    }

    public final void c(UnprovisionedMeshNode unprovisionedMeshNode, byte[] bArr) {
        if (bArr.length >= 2 && bArr[0] == 3 && bArr[1] == ProvisioningState.State.PROVISINING_COMPLETE.getState()) {
            this.f7532n = new ProvisioningCompleteState(unprovisionedMeshNode);
            this.f7533o = false;
            this.f7534p = false;
            ProvisionedMeshNode provisionedMeshNode = new ProvisionedMeshNode(unprovisionedMeshNode);
            this.f7535q.c(provisionedMeshNode);
            this.f7522d.onProvisioningComplete(provisionedMeshNode);
            return;
        }
        if (bArr.length >= 2 && bArr[0] == 3 && bArr[1] < this.f7532n.getState().ordinal()) {
            a.a.a.a.b.m.a.d(f7519a, "Received data that did not meet expectations: " + Integer.toHexString(bArr[1] & 255));
            return;
        }
        this.f7533o = false;
        this.f7534p = false;
        ProvisioningFailedState provisioningFailedState = new ProvisioningFailedState(this.f7521c, unprovisionedMeshNode);
        this.f7532n = provisioningFailedState;
        if (bArr.length > 2 && provisioningFailedState.parseData(bArr)) {
            unprovisionedMeshNode.setIsProvisioned(false);
            this.f7522d.onProvisioningFailed(unprovisionedMeshNode, provisioningFailedState.getErrorCode());
        } else {
            unprovisionedMeshNode.setIsProvisioned(false);
            provisioningFailedState.setErrorCode(ProvisioningFailedState.ProvisioningFailureCode.UNEXPECTED_ERROR);
            this.f7522d.onProvisioningFailed(unprovisionedMeshNode, provisioningFailedState.getErrorCode());
        }
    }

    public void d(UnprovisionedMeshNode unprovisionedMeshNode, byte[] bArr) {
        if (this.f7532n == null) {
            return;
        }
        c(unprovisionedMeshNode, bArr);
    }

    public final boolean e(UnprovisionedMeshNode unprovisionedMeshNode, byte[] bArr) {
        ProvisioningCapabilitiesState provisioningCapabilitiesState = new ProvisioningCapabilitiesState(unprovisionedMeshNode, this.f7522d);
        this.f7532n = provisioningCapabilitiesState;
        provisioningCapabilitiesState.parseData(bArr);
        return true;
    }

    public final void f(UnprovisionedMeshNode unprovisionedMeshNode) {
        ProvisioningRandomConfirmationState provisioningRandomConfirmationState = new ProvisioningRandomConfirmationState(this, unprovisionedMeshNode, this.f7520b, this.f7522d);
        this.f7532n = provisioningRandomConfirmationState;
        provisioningRandomConfirmationState.executeSend();
    }

    public void g(@NonNull UnprovisionedMeshNode unprovisionedMeshNode) {
        this.f7538t = false;
        this.f7523e = 5;
        e(unprovisionedMeshNode);
    }

    @Override // b.InterfaceC0326a.InterfaceC0015a
    public void a(UnprovisionedMeshNode unprovisionedMeshNode, boolean z2) {
        if (unprovisionedMeshNode == null || !z2) {
            return;
        }
        c(unprovisionedMeshNode);
    }

    public void a(UnprovisionedMeshNode unprovisionedMeshNode) {
        if (this.f7538t) {
            return;
        }
        int i2 = n.f7518a[this.f7532n.getState().ordinal()];
        if (i2 == 1) {
            this.f7532n = new ProvisioningCapabilitiesState(unprovisionedMeshNode, this.f7522d);
        } else if (i2 == 3 || i2 == 4) {
            b(unprovisionedMeshNode);
        }
    }

    public final void d(UnprovisionedMeshNode unprovisionedMeshNode) {
        this.f7533o = false;
        this.f7534p = false;
        this.f7523e = 5;
        ProvisioningInviteState provisioningInviteState = new ProvisioningInviteState(unprovisionedMeshNode, 5, this.f7520b, this.f7522d);
        this.f7532n = provisioningInviteState;
        provisioningInviteState.executeSend();
    }

    public final void e(UnprovisionedMeshNode unprovisionedMeshNode) {
        ProvisioningCapabilities capabilities = ((ProvisioningCapabilitiesState) this.f7532n).getCapabilities();
        this.f7524f = capabilities.getNumberOfElements();
        this.f7525g = capabilities.getSupportedAlgorithm();
        this.f7526h = capabilities.getPublicKeyType();
        this.f7527i = capabilities.getStaticOOBType();
        this.f7528j = capabilities.getOutputOOBSize();
        this.f7529k = capabilities.getOutputOOBAction();
        this.f7530l = capabilities.getInputOOBSize();
        this.f7531m = capabilities.getInputOOBAction();
        new GenieProvisioningStartState(unprovisionedMeshNode, this.f7520b, this.f7522d).executeSend();
        this.f7532n = new ProvisioningPublicKeyState(unprovisionedMeshNode, this.f7520b, this.f7522d);
    }

    public void a(@NonNull String str, String str2, @NonNull String str3, int i2, int i3, int i4, int i5, int i6, byte[] bArr, byte[] bArr2, UnprovisionedMeshNodeData unprovisionedMeshNodeData, a.a.a.a.b.i.J j2) {
        this.f7538t = false;
        this.f7539u.clear();
        UnprovisionedMeshNode unprovisionedMeshNodeBuildUnprovisionedMeshNode = UnprovisionedMeshNodeUtil.buildUnprovisionedMeshNode(this.f7521c, str, str2, str3, i2, i3, i4, i5, i6, bArr, bArr2, this.f7536r);
        unprovisionedMeshNodeBuildUnprovisionedMeshNode.setSupportFastProvision(unprovisionedMeshNodeData.isFastProvisionMesh());
        unprovisionedMeshNodeBuildUnprovisionedMeshNode.setSupportFastGattProvision(unprovisionedMeshNodeData.isFastSupportGatt());
        unprovisionedMeshNodeBuildUnprovisionedMeshNode.setSupportAutomaticallyGenerateShareAppKey(unprovisionedMeshNodeData.isSupportAutomaticallyGenerateShareAppKey());
        if (unprovisionedMeshNodeBuildUnprovisionedMeshNode.supportFastProvision) {
            a.a.a.a.b.m.a.c("InexpensiveMesh", "identify: try fast provision");
            if (j2 != null) {
                j2.a(unprovisionedMeshNodeBuildUnprovisionedMeshNode);
                j2.a(unprovisionedMeshNodeData);
                return;
            }
            return;
        }
        d(unprovisionedMeshNodeBuildUnprovisionedMeshNode);
    }

    public final void b(UnprovisionedMeshNode unprovisionedMeshNode) {
        if (this.f7533o) {
            return;
        }
        ProvisioningState provisioningState = this.f7532n;
        if (provisioningState instanceof ProvisioningPublicKeyState) {
            this.f7533o = true;
            provisioningState.executeSend();
        } else {
            ProvisioningPublicKeyState provisioningPublicKeyState = new ProvisioningPublicKeyState(unprovisionedMeshNode, this.f7520b, this.f7522d);
            this.f7532n = provisioningPublicKeyState;
            this.f7533o = true;
            provisioningPublicKeyState.executeSend();
        }
    }

    public final void a(UnprovisionedMeshNode unprovisionedMeshNode, byte[] bArr) {
        ProvisioningState provisioningState = this.f7532n;
        if (provisioningState instanceof ProvisioningPublicKeyState) {
            boolean data = ((ProvisioningPublicKeyState) provisioningState).parseData(bArr);
            this.f7534p = data;
            if (!data) {
                ProvisioningFailedState provisioningFailedState = new ProvisioningFailedState(this.f7521c, unprovisionedMeshNode);
                this.f7532n = provisioningFailedState;
                unprovisionedMeshNode.setIsProvisioned(false);
                provisioningFailedState.setErrorCode(ProvisioningFailedState.ProvisioningFailureCode.INVALID_PDU);
                this.f7522d.onProvisioningFailed(unprovisionedMeshNode, provisioningFailedState.getErrorCode());
            }
            if (this.f7533o && this.f7534p) {
                this.f7532n = new ProvisioningConfirmationState(this, unprovisionedMeshNode, this.f7520b, this.f7522d);
                if (this.f7529k == 0 && this.f7531m == 0) {
                    a("");
                } else {
                    this.f7522d.onProvisioningAuthenticationInputRequested(unprovisionedMeshNode);
                }
            }
        }
    }

    public final void c(UnprovisionedMeshNode unprovisionedMeshNode) {
        ProvisioningDataState provisioningDataState = new ProvisioningDataState(this, unprovisionedMeshNode, this.f7520b, this.f7522d);
        this.f7532n = provisioningDataState;
        provisioningDataState.executeSend();
    }

    public final boolean c(byte[] bArr) {
        return bArr[0] == 3 && bArr[1] == this.f7532n.getState().ordinal();
    }

    public final boolean b(byte[] bArr) {
        String str = f7519a;
        StringBuilder sb = new StringBuilder();
        sb.append("isMainThread: ");
        sb.append(Looper.getMainLooper() == Looper.myLooper());
        a.a.a.a.b.m.a.a(str, sb.toString());
        return ((ProvisioningRandomConfirmationState) this.f7532n).parseData(bArr);
    }

    public void c() {
        this.f7538t = true;
    }

    public final byte[] b() {
        byte[] bArr = new byte[5];
        bArr[0] = ParseProvisioningAlgorithm.getAlgorithmValue(this.f7525g);
        bArr[1] = 0;
        short sSelectOutputActionsFromBitMask = (byte) ParseOutputOOBActions.selectOutputActionsFromBitMask(this.f7529k);
        if (this.f7527i != 0) {
            bArr[2] = 1;
            bArr[3] = 0;
            bArr[4] = 0;
        } else if (sSelectOutputActionsFromBitMask != 0) {
            bArr[2] = 2;
            bArr[3] = (byte) ParseOutputOOBActions.getOuputOOBActionValue(sSelectOutputActionsFromBitMask);
            bArr[4] = (byte) this.f7528j;
        } else {
            bArr[2] = 0;
            bArr[3] = 0;
            bArr[4] = 0;
        }
        return bArr;
    }

    public void a(String str) {
        if (str != null) {
            ProvisioningConfirmationState provisioningConfirmationState = (ProvisioningConfirmationState) this.f7532n;
            provisioningConfirmationState.setPin(str);
            provisioningConfirmationState.executeSend();
        }
    }

    public final boolean a(byte[] bArr) {
        return ((ProvisioningConfirmationState) this.f7532n).parseData(bArr);
    }

    public final byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = {(byte) this.f7523e};
        byte[] bArrA = a();
        byte[] bArrB = b();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(1 + bArrA.length + bArrB.length + bArr.length + bArr2.length);
        byteBufferAllocate.put(bArr3);
        byteBufferAllocate.put(bArrA);
        byteBufferAllocate.put(bArrB);
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(bArr2);
        return byteBufferAllocate.array();
    }

    public final byte[] a() {
        byte b2 = (byte) this.f7524f;
        int i2 = this.f7525g;
        byte b3 = (byte) this.f7526h;
        byte b4 = (byte) this.f7527i;
        byte b5 = (byte) this.f7528j;
        int i3 = this.f7529k;
        byte b6 = (byte) this.f7530l;
        int i4 = this.f7531m;
        return new byte[]{b2, (byte) ((i2 >> 8) & 255), (byte) (i2 & 255), b3, b4, b5, (byte) ((i3 >> 8) & 255), (byte) (i3 & 255), b6, (byte) ((i4 >> 8) & 255), (byte) (i4 & 255)};
    }

    public void a(p pVar) {
        this.f7522d = pVar;
    }
}
