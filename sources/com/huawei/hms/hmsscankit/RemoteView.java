package com.huawei.hms.hmsscankit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.huawei.hms.feature.dynamic.DeferredLifecycleHelper;
import com.huawei.hms.feature.dynamic.LifecycleDelegate;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.feature.dynamic.OnDelegateCreatedListener;
import com.huawei.hms.hmsscankit.api.IOnErrorCallback;
import com.huawei.hms.hmsscankit.api.IOnLightCallback;
import com.huawei.hms.hmsscankit.api.IOnResultCallback;
import com.huawei.hms.hmsscankit.api.IRemoteCreator;
import com.huawei.hms.hmsscankit.api.IRemoteViewDelegate;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.huawei.hms.ml.scan.HmsScanBase;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.w7;
import com.huawei.hms.scankit.p.y3;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.permissions.PermissionConfig;

/* loaded from: classes4.dex */
public class RemoteView extends FrameLayout {
    private static final int MAX_BITMAP_SIZE = 52428800;
    public static final int REQUEST_CODE_PHOTO = 4371;
    private static final String TAG = "ScanKitRemoteView";
    private static boolean flagForGallery = false;
    private static boolean isOnStop = true;
    private Context mContext;
    private boolean mContinuouslyScan;
    private OnErrorCallback mOnErrorCallback;
    private a mRemoteHelper;
    private boolean mReturnedBitmap;

    public static class Builder {
        Activity mContext;
        HmsScanAnalyzerOptions mFormat;
        Rect mRect;
        boolean mIsCustomed = true;
        boolean mContinuouslyScan = true;
        boolean mReturnedBitmap = false;

        public RemoteView build() {
            Activity activity = this.mContext;
            boolean z2 = this.mIsCustomed;
            HmsScanAnalyzerOptions hmsScanAnalyzerOptions = this.mFormat;
            return new RemoteView(activity, z2, hmsScanAnalyzerOptions == null ? 0 : hmsScanAnalyzerOptions.mode, this.mRect).setContinuouslyScan(this.mContinuouslyScan).enableReturnBitmap(this.mReturnedBitmap);
        }

        public Builder enableReturnBitmap() {
            this.mReturnedBitmap = true;
            return this;
        }

        public Builder setBoundingBox(Rect rect) {
            this.mRect = rect;
            return this;
        }

        public Builder setContext(Activity activity) {
            this.mContext = activity;
            return this;
        }

        public Builder setContinuouslyScan(boolean z2) {
            this.mContinuouslyScan = z2;
            return this;
        }

        public Builder setFormat(int i2, int... iArr) {
            this.mFormat = new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(i2, iArr).create();
            return this;
        }
    }

    private class a extends DeferredLifecycleHelper<b> {

        /* renamed from: a, reason: collision with root package name */
        private ViewGroup f16507a;

        /* renamed from: b, reason: collision with root package name */
        public Activity f16508b;

        /* renamed from: c, reason: collision with root package name */
        private OnDelegateCreatedListener<b> f16509c;

        /* renamed from: d, reason: collision with root package name */
        private IRemoteViewDelegate f16510d;

        /* renamed from: e, reason: collision with root package name */
        private IOnResultCallback f16511e = null;

        /* renamed from: f, reason: collision with root package name */
        private boolean f16512f;

        /* renamed from: g, reason: collision with root package name */
        private boolean f16513g;

        /* renamed from: h, reason: collision with root package name */
        private int f16514h;

        /* renamed from: i, reason: collision with root package name */
        private IOnLightCallback f16515i;

        /* renamed from: j, reason: collision with root package name */
        private Rect f16516j;

        /* renamed from: k, reason: collision with root package name */
        private Bundle f16517k;

        /* renamed from: l, reason: collision with root package name */
        private boolean f16518l;

        /* renamed from: m, reason: collision with root package name */
        private boolean f16519m;

        /* renamed from: n, reason: collision with root package name */
        private int f16520n;

        /* renamed from: o, reason: collision with root package name */
        private boolean f16521o;

        /* renamed from: p, reason: collision with root package name */
        private boolean f16522p;

        /* renamed from: com.huawei.hms.hmsscankit.RemoteView$a$a, reason: collision with other inner class name */
        class ViewOnClickListenerC0134a implements View.OnClickListener {
            ViewOnClickListenerC0134a() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                a aVar = a.this;
                Activity activity = aVar.f16508b;
                if (activity != null) {
                    RemoteView.this.startPhotoCode(activity);
                }
            }
        }

        class b implements View.OnClickListener {
            b() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                a aVar = a.this;
                Activity activity = aVar.f16508b;
                if (activity != null) {
                    RemoteView.this.startPhotoCode(activity);
                }
            }
        }

        a(Activity activity, ViewGroup viewGroup, boolean z2, int i2, Rect rect) {
            this.f16507a = viewGroup;
            this.f16508b = activity;
            this.f16512f = z2;
            this.f16514h = i2;
            this.f16516j = rect;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean g() {
            IRemoteViewDelegate iRemoteViewDelegate = this.f16510d;
            if (iRemoteViewDelegate == null) {
                return false;
            }
            try {
                iRemoteViewDelegate.turnOnLight();
                return true;
            } catch (RemoteException unused) {
                o4.b("exception", "RemoteException");
                return false;
            }
        }

        @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper
        public void createDelegate(OnDelegateCreatedListener<b> onDelegateCreatedListener) throws IllegalAccessException, InstantiationException {
            Bundle bundle;
            IRemoteCreator iRemoteCreatorD;
            this.f16509c = onDelegateCreatedListener;
            if (onDelegateCreatedListener == null || getDelegate() != null) {
                return;
            }
            this.f16510d = null;
            try {
                bundle = new Bundle();
                boolean z2 = this.f16512f;
                if (!z2 && this.f16514h == 0 && this.f16516j == null) {
                    o4.d(RemoteView.TAG, "!mCustomed && mFormatValue == 0 && mRect == null");
                } else {
                    bundle.putBoolean(DetailRect.CUSTOMED_FLAG, z2);
                    bundle.putInt(DetailRect.FORMAT_FLAG, this.f16514h);
                    Rect rect = this.f16516j;
                    if (rect != null) {
                        bundle.putParcelable(DetailRect.RECT_FLAG, rect);
                    }
                }
                boolean z3 = this.f16518l;
                if (z3) {
                    bundle.putBoolean(DetailRect.SCAN_OFFSCEEN_FLAG, z3);
                }
                boolean z4 = this.f16513g;
                if (z4) {
                    bundle.putBoolean(DetailRect.DEEPLINK_JUMP_FLAG, z4);
                    bundle.putAll(this.f16517k);
                }
                bundle.putInt(DetailRect.TYPE_TRANS, 3);
                bundle.putBoolean(DetailRect.RETURN_BITMAP, this.f16519m);
                bundle.putAll(y3.a(this.f16508b));
                bundle.putBoolean(DetailRect.SCAN_NEW_UI, true);
                bundle.putInt(DetailRect.SCAN_VIEWTYPE_FLAG, this.f16520n);
                bundle.putBoolean(DetailRect.SCAN_CAMERA_PERMISSION, this.f16521o);
                bundle.putBoolean(HmsScanBase.SCAN_GUIDE_FLAG, this.f16522p);
                iRemoteCreatorD = g.d(this.f16508b);
            } catch (RemoteException unused) {
                o4.b("exception", "RemoteException");
            }
            if (iRemoteCreatorD == null) {
                return;
            }
            this.f16510d = iRemoteCreatorD.newRemoteViewDelegate(ObjectWrapper.wrap(this.f16508b), ObjectWrapper.wrap(bundle));
            IRemoteViewDelegate iRemoteViewDelegate = this.f16510d;
            if (iRemoteViewDelegate == null) {
                return;
            }
            try {
                IOnResultCallback iOnResultCallback = this.f16511e;
                if (iOnResultCallback != null) {
                    iRemoteViewDelegate.setOnResultCallback(iOnResultCallback);
                    this.f16510d.setOnClickListener(ObjectWrapper.wrap(new ViewOnClickListenerC0134a()));
                }
                this.f16510d.setOnClickListener(ObjectWrapper.wrap(new b()));
                IOnLightCallback iOnLightCallback = this.f16515i;
                if (iOnLightCallback != null) {
                    this.f16510d.setOnLightVisbleCallBack(iOnLightCallback);
                }
            } catch (RemoteException unused2) {
                o4.b("exception", "RemoteException");
            }
            this.f16509c.onDelegateCreated(new b(this.f16507a, this.f16510d));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d() {
            IRemoteViewDelegate iRemoteViewDelegate = this.f16510d;
            if (iRemoteViewDelegate != null) {
                try {
                    iRemoteViewDelegate.pauseContinuouslyScan();
                } catch (RemoteException unused) {
                    o4.b("exception", "RemoteException");
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            IRemoteViewDelegate iRemoteViewDelegate = this.f16510d;
            if (iRemoteViewDelegate != null) {
                try {
                    iRemoteViewDelegate.resumeContinuouslyScan();
                } catch (RemoteException unused) {
                    o4.b("exception", "RemoteException");
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean f() {
            IRemoteViewDelegate iRemoteViewDelegate = this.f16510d;
            if (iRemoteViewDelegate == null) {
                return false;
            }
            try {
                iRemoteViewDelegate.turnOffLight();
                return true;
            } catch (RemoteException unused) {
                o4.b("exception", "RemoteException");
                return false;
            }
        }

        public void b(boolean z2) {
            this.f16518l = z2;
        }

        public void c(boolean z2) {
            this.f16519m = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b() {
            if (this.f16508b != null) {
                this.f16508b = null;
            }
            if (this.f16507a != null) {
                this.f16507a = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean c() {
            IRemoteViewDelegate iRemoteViewDelegate = this.f16510d;
            if (iRemoteViewDelegate == null) {
                return false;
            }
            try {
                return iRemoteViewDelegate.getLightStatus();
            } catch (RemoteException unused) {
                o4.b("exception", "RemoteException");
                return false;
            }
        }

        a(Activity activity, ViewGroup viewGroup, boolean z2, int i2, Rect rect, int i3) {
            this.f16507a = viewGroup;
            this.f16508b = activity;
            this.f16512f = z2;
            this.f16514h = i2;
            this.f16516j = rect;
            this.f16520n = i3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(boolean z2) {
            this.f16513g = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Bundle bundle) {
            this.f16517k = bundle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IOnResultCallback iOnResultCallback) {
            this.f16511e = iOnResultCallback;
            IRemoteViewDelegate iRemoteViewDelegate = this.f16510d;
            if (iRemoteViewDelegate != null) {
                try {
                    iRemoteViewDelegate.setOnResultCallback(iOnResultCallback);
                } catch (RemoteException unused) {
                    o4.b("exception", "RemoteException");
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IOnErrorCallback iOnErrorCallback) {
            IRemoteViewDelegate iRemoteViewDelegate = this.f16510d;
            if (iRemoteViewDelegate != null) {
                try {
                    iRemoteViewDelegate.setOnErrorCallback(iOnErrorCallback);
                } catch (RemoteException unused) {
                    o4.b("exception", "RemoteException");
                }
            }
        }

        a(Activity activity, ViewGroup viewGroup, boolean z2, int i2, Rect rect, int i3, boolean z3, boolean z4) {
            this.f16507a = viewGroup;
            this.f16508b = activity;
            this.f16512f = z2;
            this.f16514h = i2;
            this.f16516j = rect;
            this.f16520n = i3;
            this.f16521o = z3;
            this.f16522p = z4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IOnLightCallback iOnLightCallback) {
            this.f16515i = iOnLightCallback;
            IRemoteViewDelegate iRemoteViewDelegate = this.f16510d;
            if (iRemoteViewDelegate != null) {
                try {
                    iRemoteViewDelegate.setOnLightVisbleCallBack(iOnLightCallback);
                } catch (RemoteException unused) {
                    o4.b("exception", "RemoteException");
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i2, int i3, Intent intent) {
            HmsScan[] hmsScanArrA;
            if (i3 == -1 && intent != null && i2 == 4371) {
                try {
                    Bitmap bitmapA = w7.a(RemoteView.this.mContext, intent);
                    if (bitmapA != null && bitmapA.getWidth() * bitmapA.getHeight() <= 52428800) {
                        hmsScanArrA = f.a(RemoteView.this.mContext, bitmapA, new HmsScanAnalyzerOptions.Creator().setPhotoMode(true).create(), this.f16514h);
                    } else {
                        if (bitmapA != null) {
                            o4.e("ScanUtil", "input image is too large:" + bitmapA.getWidth());
                        }
                        hmsScanArrA = new HmsScan[0];
                    }
                    IOnResultCallback iOnResultCallback = this.f16511e;
                    if (iOnResultCallback != null) {
                        iOnResultCallback.onResult(hmsScanArrA);
                    }
                } catch (RemoteException unused) {
                    o4.b(RemoteView.TAG, "RemoteException in remoteview");
                } catch (Error unused2) {
                    o4.b(RemoteView.TAG, "Exception in error");
                } catch (IllegalStateException unused3) {
                    o4.b(RemoteView.TAG, "IllegalStateException in remoteview");
                } catch (Exception unused4) {
                    o4.b(RemoteView.TAG, "Exception in remoteview");
                }
            }
        }
    }

    private static class b implements LifecycleDelegate {

        /* renamed from: a, reason: collision with root package name */
        private ViewGroup f16526a;

        /* renamed from: b, reason: collision with root package name */
        private View f16527b;

        /* renamed from: c, reason: collision with root package name */
        private IRemoteViewDelegate f16528c;

        b(ViewGroup viewGroup, IRemoteViewDelegate iRemoteViewDelegate) {
            this.f16526a = viewGroup;
            this.f16528c = iRemoteViewDelegate;
        }

        @Override // com.huawei.hms.feature.dynamic.LifecycleDelegate
        public void onCreate(Bundle bundle) {
            try {
                this.f16528c.onCreate(bundle);
                this.f16527b = (View) ObjectWrapper.unwrap(this.f16528c.getView());
                this.f16526a.removeAllViews();
                this.f16526a.addView(this.f16527b);
            } catch (RemoteException unused) {
                o4.b("exception", "RemoteException");
            }
        }

        @Override // com.huawei.hms.feature.dynamic.LifecycleDelegate
        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            return new View(null);
        }

        @Override // com.huawei.hms.feature.dynamic.LifecycleDelegate
        public void onDestroy() {
            try {
                this.f16528c.onDestroy();
            } catch (RemoteException unused) {
                o4.b("exception", "RemoteException");
            }
        }

        @Override // com.huawei.hms.feature.dynamic.LifecycleDelegate
        public void onDestroyView() {
        }

        @Override // com.huawei.hms.feature.dynamic.LifecycleDelegate
        public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
        }

        @Override // com.huawei.hms.feature.dynamic.LifecycleDelegate
        public void onLowMemory() {
        }

        @Override // com.huawei.hms.feature.dynamic.LifecycleDelegate
        public void onPause() {
            try {
                this.f16528c.onPause();
            } catch (RemoteException unused) {
                o4.b("exception", "RemoteException");
            }
        }

        @Override // com.huawei.hms.feature.dynamic.LifecycleDelegate
        public void onResume() {
            try {
                if (RemoteView.isOnStop || !RemoteView.flagForGallery) {
                    this.f16528c.onResume();
                } else {
                    this.f16528c.onStart();
                }
            } catch (RemoteException unused) {
                o4.b("exception", "RemoteException");
            }
        }

        @Override // com.huawei.hms.feature.dynamic.LifecycleDelegate
        public void onSaveInstanceState(Bundle bundle) {
        }

        @Override // com.huawei.hms.feature.dynamic.LifecycleDelegate
        public void onStart() {
            try {
                this.f16528c.onStart();
            } catch (RemoteException unused) {
                o4.b("exception", "RemoteException");
            }
        }

        @Override // com.huawei.hms.feature.dynamic.LifecycleDelegate
        public void onStop() {
            try {
                this.f16528c.onStop();
            } catch (RemoteException unused) {
                o4.b("exception", "RemoteException");
            }
        }
    }

    RemoteView(Activity activity, boolean z2, int i2, Rect rect) {
        super(activity);
        this.mContinuouslyScan = true;
        this.mReturnedBitmap = false;
        this.mOnErrorCallback = null;
        this.mContext = activity;
        this.mRemoteHelper = new a(activity, this, z2, i2, rect);
    }

    private boolean checkPhotoPermission(Activity activity) {
        return Build.VERSION.SDK_INT >= 33 ? w7.b((Context) activity) || activity.checkPermission(PermissionConfig.READ_MEDIA_IMAGES, Process.myPid(), Process.myUid()) == 0 : w7.b((Context) activity) || activity.checkPermission(PermissionConfig.READ_EXTERNAL_STORAGE, Process.myPid(), Process.myUid()) == 0;
    }

    private void requestPhotoPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 33) {
            activity.requestPermissions(new String[]{PermissionConfig.READ_MEDIA_IMAGES}, 4371);
        } else {
            activity.requestPermissions(new String[]{PermissionConfig.READ_EXTERNAL_STORAGE}, 4371);
        }
    }

    private void startActivityForGallery(Activity activity) {
        try {
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Intent intent = new Intent("android.intent.action.PICK", uri);
            if (w7.g(activity)) {
                if (w7.a(new Intent(), "com.android.gallery3d", activity) != null && w7.a("com.android.gallery3d", activity)) {
                    o4.d(TAG, "Start Gallery:com.android.gallery3d");
                    intent.setPackage("com.android.gallery3d");
                } else if (w7.a(new Intent(), "com.huawei.photos", activity) != null && w7.a("com.huawei.photos", activity)) {
                    intent.setPackage("com.huawei.photos");
                    o4.d(TAG, "Start Gallery:com.huawei.photos");
                }
            }
            intent.setDataAndType(uri, SelectMimeType.SYSTEM_IMAGE);
            flagForGallery = true;
            activity.startActivityForResult(intent, 4371);
        } catch (Exception unused) {
            o4.b(TAG, "startPhotoCode Exception");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPhotoCode(Activity activity) {
        if (checkPhotoPermission(activity)) {
            startActivityForGallery(activity);
            return;
        }
        o4.d(TAG, "no photo permission");
        if (this.mOnErrorCallback == null) {
            requestPhotoPermission(activity);
        } else {
            o4.b(TAG, "no photo permission, report error2");
            this.mOnErrorCallback.onError(2);
        }
    }

    RemoteView enableReturnBitmap(boolean z2) {
        this.mReturnedBitmap = z2;
        this.mRemoteHelper.c(z2);
        return this;
    }

    public boolean getLightStatus() {
        a aVar = this.mRemoteHelper;
        if (aVar != null) {
            return aVar.c();
        }
        return false;
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        Log.i(TAG, "onActivityResult: ");
        a aVar = this.mRemoteHelper;
        if (aVar != null) {
            aVar.a(i2, i3, intent);
        }
    }

    public void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate:");
        Context context = this.mContext;
        if ((context instanceof Activity) && ((Activity) context).getWindow() != null) {
            ((Activity) this.mContext).getWindow().setFlags(16777216, 16777216);
        }
        this.mRemoteHelper.onCreate(bundle);
    }

    public final void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        a aVar = this.mRemoteHelper;
        if (aVar != null) {
            aVar.onDestroy();
            this.mRemoteHelper.b();
            this.mRemoteHelper = null;
        }
    }

    public final void onPause() {
        Log.i(TAG, "onPause: ");
        a aVar = this.mRemoteHelper;
        if (aVar != null) {
            aVar.onPause();
        }
        a aVar2 = this.mRemoteHelper;
        if (aVar2 != null && flagForGallery) {
            aVar2.onStop();
        }
        isOnStop = false;
    }

    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr, Activity activity) {
        if (i2 == 4371 && iArr.length == 1 && iArr[0] == 0) {
            startPhotoCode(activity);
        }
    }

    public final void onResume() {
        Log.i(TAG, "onResume: ");
        a aVar = this.mRemoteHelper;
        if (aVar != null) {
            aVar.onResume();
        }
        flagForGallery = false;
    }

    public final void onStart() {
        Log.i(TAG, "onStart: ");
        a aVar = this.mRemoteHelper;
        if (aVar != null) {
            aVar.onStart();
        }
        flagForGallery = false;
    }

    public final void onStop() {
        Log.i(TAG, "onStop: ");
        a aVar = this.mRemoteHelper;
        if (aVar != null && !flagForGallery) {
            aVar.onStop();
        }
        isOnStop = true;
    }

    public void pauseContinuouslyScan() {
        a aVar = this.mRemoteHelper;
        if (aVar != null) {
            aVar.d();
        }
    }

    public void resumeContinuouslyScan() {
        a aVar = this.mRemoteHelper;
        if (aVar != null) {
            aVar.e();
        }
    }

    public void selectPictureFromLocalFile() {
        startPhotoCode((Activity) this.mContext);
    }

    RemoteView setContinuouslyScan(boolean z2) {
        this.mContinuouslyScan = z2;
        return this;
    }

    public void setOnErrorCallback(OnErrorCallback onErrorCallback) {
        this.mOnErrorCallback = onErrorCallback;
        a aVar = this.mRemoteHelper;
        if (aVar != null) {
            aVar.a(new c(this.mOnErrorCallback));
        }
    }

    public void setOnLightVisibleCallback(OnLightVisibleCallBack onLightVisibleCallBack) {
        a aVar = this.mRemoteHelper;
        if (aVar != null) {
            aVar.a(new d(onLightVisibleCallBack));
        }
    }

    public void setOnResultCallback(OnResultCallback onResultCallback) {
        a aVar = this.mRemoteHelper;
        if (aVar != null) {
            aVar.a(new e(onResultCallback, this.mContinuouslyScan));
        }
    }

    public boolean switchLight() {
        if (this.mRemoteHelper != null) {
            return !getLightStatus() ? this.mRemoteHelper.g() : this.mRemoteHelper.f();
        }
        return false;
    }

    RemoteView(Activity activity, boolean z2, int i2, Rect rect, int i3) {
        super(activity);
        this.mContinuouslyScan = true;
        this.mReturnedBitmap = false;
        this.mOnErrorCallback = null;
        this.mContext = activity;
        this.mRemoteHelper = new a(activity, this, z2, i2, rect, i3);
    }

    RemoteView(Activity activity, boolean z2, int i2, Rect rect, int i3, boolean z3, boolean z4) {
        super(activity);
        this.mContinuouslyScan = true;
        this.mReturnedBitmap = false;
        this.mOnErrorCallback = null;
        this.mContext = activity;
        this.mRemoteHelper = new a(activity, this, z2, i2, rect, i3, z3, z4);
    }

    RemoteView(Activity activity, boolean z2, int i2, Rect rect, boolean z3) {
        super(activity);
        this.mContinuouslyScan = true;
        this.mReturnedBitmap = false;
        this.mOnErrorCallback = null;
        this.mContext = activity;
        a aVar = new a(activity, this, z2, i2, rect);
        this.mRemoteHelper = aVar;
        aVar.b(z3);
    }

    RemoteView(Activity activity, boolean z2, Bundle bundle) {
        super(activity);
        this.mContinuouslyScan = true;
        this.mReturnedBitmap = false;
        this.mOnErrorCallback = null;
        this.mContext = activity;
        a aVar = new a(activity, this, false, 0, null);
        this.mRemoteHelper = aVar;
        aVar.a(z2);
        this.mRemoteHelper.a(bundle);
    }
}
