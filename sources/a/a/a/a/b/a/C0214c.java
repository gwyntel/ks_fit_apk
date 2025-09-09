package a.a.a.a.b.a;

import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;

/* renamed from: a.a.a.a.b.a.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
/* synthetic */ class C0214c {

    /* renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ int[] f1262a;

    static {
        int[] iArr = new int[SIGMeshBizRequest.InteractionModel.values().length];
        f1262a = iArr;
        try {
            iArr[SIGMeshBizRequest.InteractionModel.FIRE_AND_FORGET.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f1262a[SIGMeshBizRequest.InteractionModel.REQUEST_RESPONSE.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
    }
}
