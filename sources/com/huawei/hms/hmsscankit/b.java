package com.huawei.hms.hmsscankit;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.hmsscankit.api.IRemoteCreator;
import com.huawei.hms.hmsscankit.api.IRemoteHmsDecoderDelegate;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.huawei.hms.ml.scan.HmsScanFrame;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.y3;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
class b {

    /* renamed from: a, reason: collision with root package name */
    private static volatile IRemoteHmsDecoderDelegate f16562a = null;

    /* renamed from: b, reason: collision with root package name */
    private static final String f16563b = "b";

    static HmsScan[] a(Context context, MLFrame mLFrame, HmsScanAnalyzerOptions hmsScanAnalyzerOptions) throws IllegalAccessException, InstantiationException {
        HmsScan[] hmsScanArrDetectWithByteBuffer;
        int i2;
        o4.d("scankit mul", "start detectForHmsDector");
        HmsScan[] hmsScanArr = new HmsScan[0];
        if (f16562a == null) {
            IRemoteCreator iRemoteCreatorC = g.c(context);
            if (iRemoteCreatorC == null) {
                return hmsScanArr;
            }
            try {
                f16562a = iRemoteCreatorC.newRemoteHmsDecoderDelegate();
            } catch (RemoteException unused) {
                o4.b("exception", "RemoteException");
            }
        }
        if (f16562a != null) {
            try {
                DetailRect detailRect = mLFrame.acquireProperty() != null ? new DetailRect(mLFrame.acquireProperty().getWidth(), mLFrame.acquireProperty().getHeight()) : new DetailRect();
                Bundle bundle = new Bundle();
                if (hmsScanAnalyzerOptions != null && (i2 = hmsScanAnalyzerOptions.mode) != 0) {
                    bundle.putInt(DetailRect.FORMAT_FLAG, i2);
                    bundle.putBoolean(DetailRect.PHOTO_MODE, hmsScanAnalyzerOptions.photoMode);
                }
                bundle.putBoolean(DetailRect.PARSE_RESULT, hmsScanAnalyzerOptions.parseResult);
                bundle.putInt(DetailRect.TYPE_TRANS, 3);
                bundle.putBoolean(DetailRect.SUPPORT_ROLLBACK, g.f16575c);
                bundle.putBoolean(DetailRect.USE_APK, g.f16573a);
                bundle.putAll(y3.a(context));
                if (mLFrame.readBitmap() != null) {
                    o4.d("scankit mul", "end detectForHmsDector");
                    hmsScanArrDetectWithByteBuffer = f16562a.decodeInBitmap(detailRect, ObjectWrapper.wrap(mLFrame.readBitmap()), ObjectWrapper.wrap(bundle));
                } else {
                    hmsScanArrDetectWithByteBuffer = f16562a.detectWithByteBuffer(detailRect, ObjectWrapper.wrap(mLFrame.acquireGrayByteBuffer()), ObjectWrapper.wrap(bundle));
                }
                f.a(hmsScanArrDetectWithByteBuffer);
                if (g.a()) {
                    IRemoteCreator iRemoteCreatorC2 = g.c(context);
                    if (iRemoteCreatorC2 == null) {
                        return hmsScanArr;
                    }
                    try {
                        f16562a = iRemoteCreatorC2.newRemoteHmsDecoderDelegate();
                    } catch (RemoteException unused2) {
                        o4.b(f16563b, "RemoteException");
                    }
                    o4.d("scankit mul", "iRemoteDecoderDelegate rollback");
                    hmsScanArrDetectWithByteBuffer = mLFrame.readBitmap() != null ? f16562a.decodeInBitmap(detailRect, ObjectWrapper.wrap(mLFrame.readBitmap()), ObjectWrapper.wrap(bundle)) : f16562a.detectWithByteBuffer(detailRect, ObjectWrapper.wrap(mLFrame.acquireGrayByteBuffer()), ObjectWrapper.wrap(bundle));
                }
                if (hmsScanArrDetectWithByteBuffer != null) {
                    return hmsScanArrDetectWithByteBuffer;
                }
            } catch (RemoteException unused3) {
                o4.b("exception", "RemoteException");
            }
        }
        return hmsScanArr;
    }

    static HmsScan[] a(Context context, HmsScanFrame hmsScanFrame, HmsScanAnalyzerOptions hmsScanAnalyzerOptions) {
        HmsScan[] hmsScanArrDetectWithByteBuffer;
        HmsScan[] hmsScanArrDetectWithByteBuffer2;
        o4.d("scankit mul", "start detectForHmsDector");
        HmsScan[] hmsScanArr = new HmsScan[0];
        if (f16562a == null) {
            IRemoteCreator iRemoteCreatorC = g.c(context);
            if (iRemoteCreatorC == null) {
                return hmsScanArr;
            }
            try {
                f16562a = iRemoteCreatorC.newRemoteHmsDecoderDelegate();
            } catch (RemoteException unused) {
                o4.b("exception", "RemoteException");
            }
        }
        if (f16562a != null) {
            try {
                DetailRect detailRect = new DetailRect(hmsScanFrame.getWidth(), hmsScanFrame.getHeight());
                Bundle bundle = new Bundle();
                if (hmsScanAnalyzerOptions != null) {
                    bundle.putInt(DetailRect.FORMAT_FLAG, hmsScanAnalyzerOptions.mode);
                    bundle.putBoolean(DetailRect.PARSE_RESULT, hmsScanAnalyzerOptions.parseResult);
                    bundle.putBoolean(DetailRect.PHOTO_MODE, hmsScanAnalyzerOptions.photoMode);
                }
                bundle.putBoolean(DetailRect.SUPPORT_ROLLBACK, g.f16575c);
                bundle.putBoolean(DetailRect.USE_APK, g.f16573a);
                bundle.putInt(DetailRect.TYPE_TRANS, 3);
                bundle.putBoolean(DetailRect.NEW_VERSION, true);
                bundle.putString(DetailRect.CP_PACKAGE, y3.b(context));
                bundle.putAll(y3.a(context));
                if (hmsScanFrame.getBitmap() != null) {
                    o4.d("scankit mul", "end detectForHmsDector");
                    hmsScanArrDetectWithByteBuffer = f16562a.decodeInBitmap(detailRect, ObjectWrapper.wrap(hmsScanFrame.getBitmap()), ObjectWrapper.wrap(bundle));
                } else {
                    hmsScanArrDetectWithByteBuffer = f16562a.detectWithByteBuffer(detailRect, ObjectWrapper.wrap(ByteBuffer.wrap(hmsScanFrame.getYuvImage().getYuvData())), ObjectWrapper.wrap(bundle));
                }
                f.a(hmsScanArrDetectWithByteBuffer);
                if (g.a()) {
                    IRemoteCreator iRemoteCreatorC2 = g.c(context);
                    if (iRemoteCreatorC2 == null) {
                        return hmsScanArr;
                    }
                    try {
                        f16562a = iRemoteCreatorC2.newRemoteHmsDecoderDelegate();
                    } catch (RemoteException unused2) {
                        o4.b(f16563b, "RemoteException");
                    }
                    o4.d("scankit mul", "iRemoteDecoderDelegate rollback");
                    if (hmsScanFrame.getBitmap() != null) {
                        hmsScanArrDetectWithByteBuffer2 = f16562a.decodeInBitmap(detailRect, ObjectWrapper.wrap(hmsScanFrame.getBitmap()), ObjectWrapper.wrap(bundle));
                    } else {
                        hmsScanArrDetectWithByteBuffer2 = f16562a.detectWithByteBuffer(detailRect, ObjectWrapper.wrap(ByteBuffer.wrap(hmsScanFrame.getYuvImage().getYuvData())), ObjectWrapper.wrap(bundle));
                    }
                    hmsScanArrDetectWithByteBuffer = hmsScanArrDetectWithByteBuffer2;
                }
                if (hmsScanArrDetectWithByteBuffer != null) {
                    return hmsScanArrDetectWithByteBuffer;
                }
            } catch (RemoteException unused3) {
                o4.b("exception", "RemoteException");
            }
        }
        return hmsScanArr;
    }
}
