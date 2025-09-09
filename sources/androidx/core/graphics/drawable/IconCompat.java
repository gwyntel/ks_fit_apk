package androidx.core.graphics.drawable;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.DoNotInline;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.versionedparcelable.CustomVersionedParcelable;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class IconCompat extends CustomVersionedParcelable {
    private static final float ADAPTIVE_ICON_INSET_FACTOR = 0.25f;
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final float BLUR_FACTOR = 0.010416667f;
    private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667f;
    private static final float ICON_DIAMETER_FACTOR = 0.9166667f;
    private static final int KEY_SHADOW_ALPHA = 61;
    private static final float KEY_SHADOW_OFFSET_FACTOR = 0.020833334f;
    private static final String TAG = "IconCompat";
    public static final int TYPE_ADAPTIVE_BITMAP = 5;
    public static final int TYPE_BITMAP = 1;
    public static final int TYPE_DATA = 3;
    public static final int TYPE_RESOURCE = 2;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_URI = 4;
    public static final int TYPE_URI_ADAPTIVE_BITMAP = 6;

    /* renamed from: c, reason: collision with root package name */
    static final PorterDuff.Mode f3573c = PorterDuff.Mode.SRC_IN;

    /* renamed from: a, reason: collision with root package name */
    Object f3574a;

    /* renamed from: b, reason: collision with root package name */
    PorterDuff.Mode f3575b;

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public byte[] mData;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mInt1;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mInt2;

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Parcelable mParcelable;

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String mString1;

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public ColorStateList mTintList;

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String mTintModeStr;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int mType;

    @RequiresApi(23)
    static class Api23Impl {
        private Api23Impl() {
        }

        static IconCompat a(Context context, Icon icon) {
            int iE = e(icon);
            if (iE == 2) {
                String strD = d(icon);
                try {
                    return IconCompat.createWithResource(IconCompat.b(context, strD), strD, c(icon));
                } catch (Resources.NotFoundException unused) {
                    throw new IllegalArgumentException("Icon resource cannot be found");
                }
            }
            if (iE == 4) {
                return IconCompat.createWithContentUri(f(icon));
            }
            if (iE == 6) {
                return IconCompat.createWithAdaptiveBitmapContentUri(f(icon));
            }
            IconCompat iconCompat = new IconCompat(-1);
            iconCompat.f3574a = icon;
            return iconCompat;
        }

        static IconCompat b(Object obj) {
            Preconditions.checkNotNull(obj);
            int iE = e(obj);
            if (iE == 2) {
                return IconCompat.createWithResource(null, d(obj), c(obj));
            }
            if (iE == 4) {
                return IconCompat.createWithContentUri(f(obj));
            }
            if (iE == 6) {
                return IconCompat.createWithAdaptiveBitmapContentUri(f(obj));
            }
            IconCompat iconCompat = new IconCompat(-1);
            iconCompat.f3574a = obj;
            return iconCompat;
        }

        static int c(Object obj) {
            if (Build.VERSION.SDK_INT >= 28) {
                return Api28Impl.a(obj);
            }
            try {
                return ((Integer) obj.getClass().getMethod("getResId", null).invoke(obj, null)).intValue();
            } catch (IllegalAccessException e2) {
                Log.e(IconCompat.TAG, "Unable to get icon resource", e2);
                return 0;
            } catch (NoSuchMethodException e3) {
                Log.e(IconCompat.TAG, "Unable to get icon resource", e3);
                return 0;
            } catch (InvocationTargetException e4) {
                Log.e(IconCompat.TAG, "Unable to get icon resource", e4);
                return 0;
            }
        }

        static String d(Object obj) {
            if (Build.VERSION.SDK_INT >= 28) {
                return Api28Impl.b(obj);
            }
            try {
                return (String) obj.getClass().getMethod("getResPackage", null).invoke(obj, null);
            } catch (IllegalAccessException e2) {
                Log.e(IconCompat.TAG, "Unable to get icon package", e2);
                return null;
            } catch (NoSuchMethodException e3) {
                Log.e(IconCompat.TAG, "Unable to get icon package", e3);
                return null;
            } catch (InvocationTargetException e4) {
                Log.e(IconCompat.TAG, "Unable to get icon package", e4);
                return null;
            }
        }

        static int e(Object obj) {
            if (Build.VERSION.SDK_INT >= 28) {
                return Api28Impl.c(obj);
            }
            try {
                return ((Integer) obj.getClass().getMethod("getType", null).invoke(obj, null)).intValue();
            } catch (IllegalAccessException e2) {
                Log.e(IconCompat.TAG, "Unable to get icon type " + obj, e2);
                return -1;
            } catch (NoSuchMethodException e3) {
                Log.e(IconCompat.TAG, "Unable to get icon type " + obj, e3);
                return -1;
            } catch (InvocationTargetException e4) {
                Log.e(IconCompat.TAG, "Unable to get icon type " + obj, e4);
                return -1;
            }
        }

        @Nullable
        @DoNotInline
        static Uri f(@NonNull Object obj) {
            if (Build.VERSION.SDK_INT >= 28) {
                return Api28Impl.d(obj);
            }
            try {
                return (Uri) obj.getClass().getMethod("getUri", null).invoke(obj, null);
            } catch (IllegalAccessException e2) {
                Log.e(IconCompat.TAG, "Unable to get icon uri", e2);
                return null;
            } catch (NoSuchMethodException e3) {
                Log.e(IconCompat.TAG, "Unable to get icon uri", e3);
                return null;
            } catch (InvocationTargetException e4) {
                Log.e(IconCompat.TAG, "Unable to get icon uri", e4);
                return null;
            }
        }

        @DoNotInline
        static Drawable g(Icon icon, Context context) {
            return icon.loadDrawable(context);
        }

        @DoNotInline
        static Icon h(IconCompat iconCompat, Context context) {
            Icon iconCreateWithBitmap;
            switch (iconCompat.mType) {
                case -1:
                    return (Icon) iconCompat.f3574a;
                case 0:
                default:
                    throw new IllegalArgumentException("Unknown type");
                case 1:
                    iconCreateWithBitmap = Icon.createWithBitmap((Bitmap) iconCompat.f3574a);
                    break;
                case 2:
                    iconCreateWithBitmap = Icon.createWithResource(iconCompat.getResPackage(), iconCompat.mInt1);
                    break;
                case 3:
                    iconCreateWithBitmap = Icon.createWithData((byte[]) iconCompat.f3574a, iconCompat.mInt1, iconCompat.mInt2);
                    break;
                case 4:
                    iconCreateWithBitmap = Icon.createWithContentUri((String) iconCompat.f3574a);
                    break;
                case 5:
                    if (Build.VERSION.SDK_INT < 26) {
                        iconCreateWithBitmap = Icon.createWithBitmap(IconCompat.a((Bitmap) iconCompat.f3574a, false));
                        break;
                    } else {
                        iconCreateWithBitmap = Api26Impl.b((Bitmap) iconCompat.f3574a);
                        break;
                    }
                case 6:
                    int i2 = Build.VERSION.SDK_INT;
                    if (i2 >= 30) {
                        iconCreateWithBitmap = Api30Impl.a(iconCompat.getUri());
                        break;
                    } else {
                        if (context == null) {
                            throw new IllegalArgumentException("Context is required to resolve the file uri of the icon: " + iconCompat.getUri());
                        }
                        InputStream uriInputStream = iconCompat.getUriInputStream(context);
                        if (uriInputStream == null) {
                            throw new IllegalStateException("Cannot load adaptive icon from uri: " + iconCompat.getUri());
                        }
                        if (i2 < 26) {
                            iconCreateWithBitmap = Icon.createWithBitmap(IconCompat.a(BitmapFactory.decodeStream(uriInputStream), false));
                            break;
                        } else {
                            iconCreateWithBitmap = Api26Impl.b(BitmapFactory.decodeStream(uriInputStream));
                            break;
                        }
                    }
            }
            ColorStateList colorStateList = iconCompat.mTintList;
            if (colorStateList != null) {
                iconCreateWithBitmap.setTintList(colorStateList);
            }
            PorterDuff.Mode mode = iconCompat.f3575b;
            if (mode != IconCompat.f3573c) {
                iconCreateWithBitmap.setTintMode(mode);
            }
            return iconCreateWithBitmap;
        }
    }

    @RequiresApi(26)
    static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        static Drawable a(Drawable drawable, Drawable drawable2) {
            return new AdaptiveIconDrawable(drawable, drawable2);
        }

        @DoNotInline
        static Icon b(Bitmap bitmap) {
            return Icon.createWithAdaptiveBitmap(bitmap);
        }
    }

    @RequiresApi(28)
    static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        static int a(Object obj) {
            return ((Icon) obj).getResId();
        }

        @DoNotInline
        static String b(Object obj) {
            return ((Icon) obj).getResPackage();
        }

        @DoNotInline
        static int c(Object obj) {
            return ((Icon) obj).getType();
        }

        @DoNotInline
        static Uri d(Object obj) {
            return ((Icon) obj).getUri();
        }
    }

    @RequiresApi(30)
    static class Api30Impl {
        private Api30Impl() {
        }

        @DoNotInline
        static Icon a(Uri uri) {
            return Icon.createWithAdaptiveBitmapContentUri(uri);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface IconType {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public IconCompat() {
        this.mType = -1;
        this.mData = null;
        this.mParcelable = null;
        this.mInt1 = 0;
        this.mInt2 = 0;
        this.mTintList = null;
        this.f3575b = f3573c;
        this.mTintModeStr = null;
    }

    static Bitmap a(Bitmap bitmap, boolean z2) {
        int iMin = (int) (Math.min(bitmap.getWidth(), bitmap.getHeight()) * DEFAULT_VIEW_PORT_SCALE);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iMin, iMin, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint(3);
        float f2 = iMin;
        float f3 = 0.5f * f2;
        float f4 = ICON_DIAMETER_FACTOR * f3;
        if (z2) {
            float f5 = BLUR_FACTOR * f2;
            paint.setColor(0);
            paint.setShadowLayer(f5, 0.0f, f2 * KEY_SHADOW_OFFSET_FACTOR, 1023410176);
            canvas.drawCircle(f3, f3, f4, paint);
            paint.setShadowLayer(f5, 0.0f, 0.0f, 503316480);
            canvas.drawCircle(f3, f3, f4, paint);
            paint.clearShadowLayer();
        }
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        BitmapShader bitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
        Matrix matrix = new Matrix();
        matrix.setTranslate((-(bitmap.getWidth() - iMin)) / 2.0f, (-(bitmap.getHeight() - iMin)) / 2.0f);
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        canvas.drawCircle(f3, f3, f4, paint);
        canvas.setBitmap(null);
        return bitmapCreateBitmap;
    }

    static Resources b(Context context, String str) throws PackageManager.NameNotFoundException {
        if ("android".equals(str)) {
            return Resources.getSystem();
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 8192);
            if (applicationInfo != null) {
                return packageManager.getResourcesForApplication(applicationInfo);
            }
            return null;
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e(TAG, String.format("Unable to find pkg=%s for icon", str), e2);
            return null;
        }
    }

    @Nullable
    public static IconCompat createFromBundle(@NonNull Bundle bundle) {
        int i2 = bundle.getInt("type");
        IconCompat iconCompat = new IconCompat(i2);
        iconCompat.mInt1 = bundle.getInt("int1");
        iconCompat.mInt2 = bundle.getInt("int2");
        iconCompat.mString1 = bundle.getString("string1");
        if (bundle.containsKey("tint_list")) {
            iconCompat.mTintList = (ColorStateList) bundle.getParcelable("tint_list");
        }
        if (bundle.containsKey("tint_mode")) {
            iconCompat.f3575b = PorterDuff.Mode.valueOf(bundle.getString("tint_mode"));
        }
        switch (i2) {
            case -1:
            case 1:
            case 5:
                iconCompat.f3574a = bundle.getParcelable("obj");
                return iconCompat;
            case 0:
            default:
                Log.w(TAG, "Unknown type " + i2);
                return null;
            case 2:
            case 4:
            case 6:
                iconCompat.f3574a = bundle.getString("obj");
                return iconCompat;
            case 3:
                iconCompat.f3574a = bundle.getByteArray("obj");
                return iconCompat;
        }
    }

    @Nullable
    @RequiresApi(23)
    public static IconCompat createFromIcon(@NonNull Context context, @NonNull Icon icon) {
        Preconditions.checkNotNull(icon);
        return Api23Impl.a(context, icon);
    }

    @Nullable
    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static IconCompat createFromIconOrNullIfZeroResId(@NonNull Icon icon) {
        if (Api23Impl.e(icon) == 2 && Api23Impl.c(icon) == 0) {
            return null;
        }
        return Api23Impl.b(icon);
    }

    @NonNull
    public static IconCompat createWithAdaptiveBitmap(@NonNull Bitmap bitmap) {
        ObjectsCompat.requireNonNull(bitmap);
        IconCompat iconCompat = new IconCompat(5);
        iconCompat.f3574a = bitmap;
        return iconCompat;
    }

    @NonNull
    public static IconCompat createWithAdaptiveBitmapContentUri(@NonNull String str) {
        ObjectsCompat.requireNonNull(str);
        IconCompat iconCompat = new IconCompat(6);
        iconCompat.f3574a = str;
        return iconCompat;
    }

    @NonNull
    public static IconCompat createWithBitmap(@NonNull Bitmap bitmap) {
        ObjectsCompat.requireNonNull(bitmap);
        IconCompat iconCompat = new IconCompat(1);
        iconCompat.f3574a = bitmap;
        return iconCompat;
    }

    @NonNull
    public static IconCompat createWithContentUri(@NonNull String str) {
        ObjectsCompat.requireNonNull(str);
        IconCompat iconCompat = new IconCompat(4);
        iconCompat.f3574a = str;
        return iconCompat;
    }

    @NonNull
    public static IconCompat createWithData(@NonNull byte[] bArr, int i2, int i3) {
        ObjectsCompat.requireNonNull(bArr);
        IconCompat iconCompat = new IconCompat(3);
        iconCompat.f3574a = bArr;
        iconCompat.mInt1 = i2;
        iconCompat.mInt2 = i3;
        return iconCompat;
    }

    @NonNull
    public static IconCompat createWithResource(@NonNull Context context, @DrawableRes int i2) {
        ObjectsCompat.requireNonNull(context);
        return createWithResource(context.getResources(), context.getPackageName(), i2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private Drawable loadDrawableInner(Context context) {
        switch (this.mType) {
            case 1:
                return new BitmapDrawable(context.getResources(), (Bitmap) this.f3574a);
            case 2:
                String resPackage = getResPackage();
                if (TextUtils.isEmpty(resPackage)) {
                    resPackage = context.getPackageName();
                }
                try {
                    return ResourcesCompat.getDrawable(b(context, resPackage), this.mInt1, context.getTheme());
                } catch (RuntimeException e2) {
                    Log.e(TAG, String.format("Unable to load resource 0x%08x from pkg=%s", Integer.valueOf(this.mInt1), this.f3574a), e2);
                    break;
                }
            case 3:
                return new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray((byte[]) this.f3574a, this.mInt1, this.mInt2));
            case 4:
                InputStream uriInputStream = getUriInputStream(context);
                if (uriInputStream != null) {
                    return new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(uriInputStream));
                }
                return null;
            case 5:
                return new BitmapDrawable(context.getResources(), a((Bitmap) this.f3574a, false));
            case 6:
                InputStream uriInputStream2 = getUriInputStream(context);
                if (uriInputStream2 != null) {
                    return Build.VERSION.SDK_INT >= 26 ? Api26Impl.a(null, new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(uriInputStream2))) : new BitmapDrawable(context.getResources(), a(BitmapFactory.decodeStream(uriInputStream2), false));
                }
                return null;
            default:
                return null;
        }
    }

    private static String typeToString(int i2) {
        switch (i2) {
            case 1:
                return "BITMAP";
            case 2:
                return "RESOURCE";
            case 3:
                return "DATA";
            case 4:
                return "URI";
            case 5:
                return "BITMAP_MASKABLE";
            case 6:
                return "URI_MASKABLE";
            default:
                return "UNKNOWN";
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void addToShortcutIntent(@NonNull Intent intent, @Nullable Drawable drawable, @NonNull Context context) throws PackageManager.NameNotFoundException {
        Bitmap bitmapCopy;
        checkResource(context);
        int i2 = this.mType;
        if (i2 == 1) {
            bitmapCopy = (Bitmap) this.f3574a;
            if (drawable != null) {
                bitmapCopy = bitmapCopy.copy(bitmapCopy.getConfig(), true);
            }
        } else if (i2 == 2) {
            try {
                Context contextCreatePackageContext = context.createPackageContext(getResPackage(), 0);
                if (drawable == null) {
                    intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(contextCreatePackageContext, this.mInt1));
                    return;
                }
                Drawable drawable2 = ContextCompat.getDrawable(contextCreatePackageContext, this.mInt1);
                if (drawable2.getIntrinsicWidth() <= 0 || drawable2.getIntrinsicHeight() <= 0) {
                    int launcherLargeIconSize = ((ActivityManager) contextCreatePackageContext.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getLauncherLargeIconSize();
                    bitmapCopy = Bitmap.createBitmap(launcherLargeIconSize, launcherLargeIconSize, Bitmap.Config.ARGB_8888);
                } else {
                    bitmapCopy = Bitmap.createBitmap(drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                }
                drawable2.setBounds(0, 0, bitmapCopy.getWidth(), bitmapCopy.getHeight());
                drawable2.draw(new Canvas(bitmapCopy));
            } catch (PackageManager.NameNotFoundException e2) {
                throw new IllegalArgumentException("Can't find package " + this.f3574a, e2);
            }
        } else {
            if (i2 != 5) {
                throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
            }
            bitmapCopy = a((Bitmap) this.f3574a, true);
        }
        if (drawable != null) {
            int width = bitmapCopy.getWidth();
            int height = bitmapCopy.getHeight();
            drawable.setBounds(width / 2, height / 2, width, height);
            drawable.draw(new Canvas(bitmapCopy));
        }
        intent.putExtra("android.intent.extra.shortcut.ICON", bitmapCopy);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void checkResource(@NonNull Context context) {
        Object obj;
        if (this.mType != 2 || (obj = this.f3574a) == null) {
            return;
        }
        String str = (String) obj;
        if (str.contains(":")) {
            String str2 = str.split(":", -1)[1];
            String str3 = str2.split("/", -1)[0];
            String str4 = str2.split("/", -1)[1];
            String str5 = str.split(":", -1)[0];
            if ("0_resource_name_obfuscated".equals(str4)) {
                Log.i(TAG, "Found obfuscated resource, not trying to update resource id for it");
                return;
            }
            String resPackage = getResPackage();
            int identifier = b(context, resPackage).getIdentifier(str4, str3, str5);
            if (this.mInt1 != identifier) {
                Log.i(TAG, "Id has changed for " + resPackage + " " + str);
                this.mInt1 = identifier;
            }
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public Bitmap getBitmap() {
        int i2 = this.mType;
        if (i2 == -1) {
            Object obj = this.f3574a;
            if (obj instanceof Bitmap) {
                return (Bitmap) obj;
            }
            return null;
        }
        if (i2 == 1) {
            return (Bitmap) this.f3574a;
        }
        if (i2 == 5) {
            return a((Bitmap) this.f3574a, true);
        }
        throw new IllegalStateException("called getBitmap() on " + this);
    }

    @DrawableRes
    public int getResId() {
        int i2 = this.mType;
        if (i2 == -1) {
            return Api23Impl.c(this.f3574a);
        }
        if (i2 == 2) {
            return this.mInt1;
        }
        throw new IllegalStateException("called getResId() on " + this);
    }

    @NonNull
    public String getResPackage() {
        int i2 = this.mType;
        if (i2 == -1) {
            return Api23Impl.d(this.f3574a);
        }
        if (i2 == 2) {
            String str = this.mString1;
            return (str == null || TextUtils.isEmpty(str)) ? ((String) this.f3574a).split(":", -1)[0] : this.mString1;
        }
        throw new IllegalStateException("called getResPackage() on " + this);
    }

    public int getType() {
        int i2 = this.mType;
        return i2 == -1 ? Api23Impl.e(this.f3574a) : i2;
    }

    @NonNull
    public Uri getUri() {
        int i2 = this.mType;
        if (i2 == -1) {
            return Api23Impl.f(this.f3574a);
        }
        if (i2 == 4 || i2 == 6) {
            return Uri.parse((String) this.f3574a);
        }
        throw new IllegalStateException("called getUri() on " + this);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public InputStream getUriInputStream(@NonNull Context context) {
        Uri uri = getUri();
        String scheme = uri.getScheme();
        if ("content".equals(scheme) || "file".equals(scheme)) {
            try {
                return context.getContentResolver().openInputStream(uri);
            } catch (Exception e2) {
                Log.w(TAG, "Unable to load image from URI: " + uri, e2);
                return null;
            }
        }
        try {
            return new FileInputStream(new File((String) this.f3574a));
        } catch (FileNotFoundException e3) {
            Log.w(TAG, "Unable to load image from path: " + uri, e3);
            return null;
        }
    }

    @Nullable
    public Drawable loadDrawable(@NonNull Context context) {
        checkResource(context);
        return Api23Impl.g(toIcon(context), context);
    }

    @Override // androidx.versionedparcelable.CustomVersionedParcelable
    public void onPostParceling() {
        this.f3575b = PorterDuff.Mode.valueOf(this.mTintModeStr);
        switch (this.mType) {
            case -1:
                Parcelable parcelable = this.mParcelable;
                if (parcelable == null) {
                    throw new IllegalArgumentException("Invalid icon");
                }
                this.f3574a = parcelable;
                return;
            case 0:
            default:
                return;
            case 1:
            case 5:
                Parcelable parcelable2 = this.mParcelable;
                if (parcelable2 != null) {
                    this.f3574a = parcelable2;
                    return;
                }
                byte[] bArr = this.mData;
                this.f3574a = bArr;
                this.mType = 3;
                this.mInt1 = 0;
                this.mInt2 = bArr.length;
                return;
            case 2:
            case 4:
            case 6:
                String str = new String(this.mData, Charset.forName("UTF-16"));
                this.f3574a = str;
                if (this.mType == 2 && this.mString1 == null) {
                    this.mString1 = str.split(":", -1)[0];
                    return;
                }
                return;
            case 3:
                this.f3574a = this.mData;
                return;
        }
    }

    @Override // androidx.versionedparcelable.CustomVersionedParcelable
    public void onPreParceling(boolean z2) {
        this.mTintModeStr = this.f3575b.name();
        switch (this.mType) {
            case -1:
                if (z2) {
                    throw new IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
                }
                this.mParcelable = (Parcelable) this.f3574a;
                return;
            case 0:
            default:
                return;
            case 1:
            case 5:
                if (!z2) {
                    this.mParcelable = (Parcelable) this.f3574a;
                    return;
                }
                Bitmap bitmap = (Bitmap) this.f3574a;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
                this.mData = byteArrayOutputStream.toByteArray();
                return;
            case 2:
                this.mData = ((String) this.f3574a).getBytes(Charset.forName("UTF-16"));
                return;
            case 3:
                this.mData = (byte[]) this.f3574a;
                return;
            case 4:
            case 6:
                this.mData = this.f3574a.toString().getBytes(Charset.forName("UTF-16"));
                return;
        }
    }

    @NonNull
    public IconCompat setTint(@ColorInt int i2) {
        return setTintList(ColorStateList.valueOf(i2));
    }

    @NonNull
    public IconCompat setTintList(@Nullable ColorStateList colorStateList) {
        this.mTintList = colorStateList;
        return this;
    }

    @NonNull
    public IconCompat setTintMode(@Nullable PorterDuff.Mode mode) {
        this.f3575b = mode;
        return this;
    }

    @NonNull
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        switch (this.mType) {
            case -1:
                bundle.putParcelable("obj", (Parcelable) this.f3574a);
                break;
            case 0:
            default:
                throw new IllegalArgumentException("Invalid icon");
            case 1:
            case 5:
                bundle.putParcelable("obj", (Bitmap) this.f3574a);
                break;
            case 2:
            case 4:
            case 6:
                bundle.putString("obj", (String) this.f3574a);
                break;
            case 3:
                bundle.putByteArray("obj", (byte[]) this.f3574a);
                break;
        }
        bundle.putInt("type", this.mType);
        bundle.putInt("int1", this.mInt1);
        bundle.putInt("int2", this.mInt2);
        bundle.putString("string1", this.mString1);
        ColorStateList colorStateList = this.mTintList;
        if (colorStateList != null) {
            bundle.putParcelable("tint_list", colorStateList);
        }
        PorterDuff.Mode mode = this.f3575b;
        if (mode != f3573c) {
            bundle.putString("tint_mode", mode.name());
        }
        return bundle;
    }

    @NonNull
    @RequiresApi(23)
    @Deprecated
    public Icon toIcon() {
        return toIcon(null);
    }

    @NonNull
    public String toString() {
        if (this.mType == -1) {
            return String.valueOf(this.f3574a);
        }
        StringBuilder sb = new StringBuilder("Icon(typ=");
        sb.append(typeToString(this.mType));
        switch (this.mType) {
            case 1:
            case 5:
                sb.append(" size=");
                sb.append(((Bitmap) this.f3574a).getWidth());
                sb.append("x");
                sb.append(((Bitmap) this.f3574a).getHeight());
                break;
            case 2:
                sb.append(" pkg=");
                sb.append(this.mString1);
                sb.append(" id=");
                sb.append(String.format("0x%08x", Integer.valueOf(getResId())));
                break;
            case 3:
                sb.append(" len=");
                sb.append(this.mInt1);
                if (this.mInt2 != 0) {
                    sb.append(" off=");
                    sb.append(this.mInt2);
                    break;
                }
                break;
            case 4:
            case 6:
                sb.append(" uri=");
                sb.append(this.f3574a);
                break;
        }
        if (this.mTintList != null) {
            sb.append(" tint=");
            sb.append(this.mTintList);
        }
        if (this.f3575b != f3573c) {
            sb.append(" mode=");
            sb.append(this.f3575b);
        }
        sb.append(")");
        return sb.toString();
    }

    @NonNull
    @RequiresApi(23)
    public Icon toIcon(@Nullable Context context) {
        return Api23Impl.h(this, context);
    }

    @Nullable
    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static IconCompat createFromIcon(@NonNull Icon icon) {
        return Api23Impl.b(icon);
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static IconCompat createWithResource(@Nullable Resources resources, @NonNull String str, @DrawableRes int i2) {
        ObjectsCompat.requireNonNull(str);
        if (i2 != 0) {
            IconCompat iconCompat = new IconCompat(2);
            iconCompat.mInt1 = i2;
            if (resources != null) {
                try {
                    iconCompat.f3574a = resources.getResourceName(i2);
                } catch (Resources.NotFoundException unused) {
                    throw new IllegalArgumentException("Icon resource cannot be found");
                }
            } else {
                iconCompat.f3574a = str;
            }
            iconCompat.mString1 = str;
            return iconCompat;
        }
        throw new IllegalArgumentException("Drawable resource ID must not be 0");
    }

    @NonNull
    public static IconCompat createWithAdaptiveBitmapContentUri(@NonNull Uri uri) {
        ObjectsCompat.requireNonNull(uri);
        return createWithAdaptiveBitmapContentUri(uri.toString());
    }

    @NonNull
    public static IconCompat createWithContentUri(@NonNull Uri uri) {
        ObjectsCompat.requireNonNull(uri);
        return createWithContentUri(uri.toString());
    }

    IconCompat(int i2) {
        this.mData = null;
        this.mParcelable = null;
        this.mInt1 = 0;
        this.mInt2 = 0;
        this.mTintList = null;
        this.f3575b = f3573c;
        this.mTintModeStr = null;
        this.mType = i2;
    }
}
