package com.huawei.hms.scankit;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.hmsscankit.DetailRect;
import com.huawei.hms.hmsscankit.api.IRemoteHmsDecoderDelegate;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.r3;
import com.huawei.hms.scankit.p.r6;
import com.huawei.hms.scankit.p.w3;
import com.huawei.hms.scankit.p.w7;
import com.huawei.hms.scankit.p.y3;
import com.huawei.hms.scankit.util.OpencvJNI;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class g extends IRemoteHmsDecoderDelegate.Stub {

    /* renamed from: b, reason: collision with root package name */
    private static volatile g f16931b = new g();

    /* renamed from: a, reason: collision with root package name */
    private volatile w3 f16932a = null;

    static g a() {
        return f16931b;
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteHmsDecoderDelegate
    public HmsScan[] decodeInBitmap(DetailRect detailRect, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2) throws RemoteException {
        boolean z2;
        boolean z3;
        if (!r3.A) {
            OpencvJNI.init();
        }
        o4.d("Scankit", "start decodeInBitmap");
        Bundle bundleA = a(iObjectWrapper2);
        String string = "";
        if (iObjectWrapper2 == null || !(ObjectWrapper.unwrap(iObjectWrapper2) instanceof Bundle)) {
            z2 = false;
            z3 = false;
        } else {
            string = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getString(DetailRect.CP_PACKAGE, "");
            z2 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.USE_APK, false);
            z3 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.SUPPORT_ROLLBACK, false);
        }
        if (z3 && !r3.f17714a && z2) {
            return new HmsScan[]{r6.b()};
        }
        if (this.f16932a == null && y3.a(string, y3.a())) {
            try {
                this.f16932a = new w3(bundleA, "MultiProcessor");
            } catch (RuntimeException unused) {
                o4.b("IRemoteDecoderDelegateImpl", "Ha error");
            } catch (Exception unused2) {
                o4.b("IRemoteDecoderDelegateImpl", "Ha error");
            }
        }
        o4.d("scankit mul", "end decodeInBitmap");
        return a(iObjectWrapper, iObjectWrapper2);
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteHmsDecoderDelegate
    public HmsScan[] detectWithByteBuffer(DetailRect detailRect, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2) throws RemoteException {
        boolean z2;
        boolean z3;
        if (!r3.A) {
            OpencvJNI.init();
        }
        Bundle bundleA = a(iObjectWrapper2);
        String string = "";
        if (iObjectWrapper2 == null || !(ObjectWrapper.unwrap(iObjectWrapper2) instanceof Bundle)) {
            z2 = false;
            z3 = false;
        } else {
            string = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getString(DetailRect.CP_PACKAGE, "");
            z2 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.USE_APK, false);
            z3 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.SUPPORT_ROLLBACK, false);
        }
        if (z3 && !r3.f17714a && z2) {
            return new HmsScan[]{r6.b()};
        }
        if (this.f16932a == null && y3.a(string, y3.a())) {
            try {
                this.f16932a = new w3(bundleA, "MultiProcessor");
            } catch (RuntimeException unused) {
                o4.b("IRemoteDecoderDelegateImpl", "Ha error");
            } catch (Exception unused2) {
                o4.b("IRemoteDecoderDelegateImpl", "Ha error");
            }
        }
        return a(detailRect, iObjectWrapper, iObjectWrapper2);
    }

    private Bundle a(IObjectWrapper iObjectWrapper) {
        return (iObjectWrapper == null || !(ObjectWrapper.unwrap(iObjectWrapper) instanceof Bundle)) ? new Bundle() : (Bundle) ObjectWrapper.unwrap(iObjectWrapper);
    }

    private HmsScan[] a(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2) throws SecurityException {
        boolean z2;
        boolean z3;
        int iB;
        boolean z4;
        o4.d("Scankit", "start getHmsMLVisionScanResultByBitmap");
        if (iObjectWrapper == null) {
            o4.b("ScankitRemote", "bitmap is null");
            return new HmsScan[0];
        }
        Object objUnwrap = ObjectWrapper.unwrap(iObjectWrapper);
        if (iObjectWrapper2 == null || !(ObjectWrapper.unwrap(iObjectWrapper2) instanceof Bundle)) {
            z2 = false;
            z3 = false;
            iB = 0;
            z4 = false;
        } else {
            z3 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.PHOTO_MODE, false);
            r3.f17716c = z3;
            iB = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getInt(DetailRect.FORMAT_FLAG);
            boolean z5 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.PARSE_RESULT, true);
            z4 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.PARSE_RESULT, false);
            int i2 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getInt(DetailRect.TYPE_TRANS, 0);
            DetailRect.HMSSCAN_SDK_VALUE = i2;
            z = i2 >= 2;
            if (z) {
                iB = w7.b(iB);
            }
            z2 = z;
            z = z5;
        }
        r3.f17719f = z;
        r3.f17720g = z4;
        if (objUnwrap instanceof Bitmap) {
            o4.d("Scankit", "end getHmsMLVisionScanResultByBitmap");
            HmsScan[] hmsScanArrA = r6.a().a((Bitmap) objUnwrap, iB, z3, this.f16932a);
            return !z2 ? w7.a(hmsScanArrA) : hmsScanArrA;
        }
        return new HmsScan[0];
    }

    private HmsScan[] a(DetailRect detailRect, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2) throws SecurityException {
        boolean z2;
        boolean z3;
        int i2;
        boolean z4;
        if (iObjectWrapper == null) {
            o4.b("ScankitRemoteS", "bytebuffer is null");
            return new HmsScan[0];
        }
        Object objUnwrap = ObjectWrapper.unwrap(iObjectWrapper);
        if (iObjectWrapper2 == null || !(ObjectWrapper.unwrap(iObjectWrapper2) instanceof Bundle)) {
            z2 = false;
            z3 = false;
            i2 = 0;
            z4 = false;
        } else {
            int iB = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getInt(DetailRect.FORMAT_FLAG);
            boolean z5 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.PHOTO_MODE, false);
            int i3 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getInt(DetailRect.TYPE_TRANS, 0);
            DetailRect.HMSSCAN_SDK_VALUE = i3;
            boolean z6 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.PARSE_RESULT, true);
            z2 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.NEW_VERSION, false);
            z = i3 >= 2;
            if (z) {
                iB = w7.b(iB);
            }
            i2 = iB;
            z4 = z5;
            z3 = z;
            z = z6;
        }
        r3.f17719f = z;
        r3.f17720g = z2;
        if (objUnwrap instanceof ByteBuffer) {
            HmsScan[] hmsScanArrA = r6.a().a((ByteBuffer) objUnwrap, detailRect == null ? 1000 : detailRect.width, detailRect == null ? 1000 : detailRect.height, i2, z4, this.f16932a);
            return !z3 ? w7.a(hmsScanArrA) : hmsScanArrA;
        }
        return new HmsScan[0];
    }
}
