package androidx.core.provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.provider.FontsContractCompat;
import androidx.core.util.Consumer;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
class FontRequestWorker {

    /* renamed from: a, reason: collision with root package name */
    static final LruCache f3665a = new LruCache(16);
    private static final ExecutorService DEFAULT_EXECUTOR_SERVICE = RequestExecutor.a("fonts-androidx", 10, 10000);

    /* renamed from: b, reason: collision with root package name */
    static final Object f3666b = new Object();

    /* renamed from: c, reason: collision with root package name */
    static final SimpleArrayMap f3667c = new SimpleArrayMap();

    private FontRequestWorker() {
    }

    static TypefaceResult a(String str, Context context, FontRequest fontRequest, int i2) {
        LruCache lruCache = f3665a;
        Typeface typeface = (Typeface) lruCache.get(str);
        if (typeface != null) {
            return new TypefaceResult(typeface);
        }
        try {
            FontsContractCompat.FontFamilyResult fontFamilyResultB = FontProvider.b(context, fontRequest, null);
            int fontFamilyResultStatus = getFontFamilyResultStatus(fontFamilyResultB);
            if (fontFamilyResultStatus != 0) {
                return new TypefaceResult(fontFamilyResultStatus);
            }
            Typeface typefaceCreateFromFontInfo = TypefaceCompat.createFromFontInfo(context, null, fontFamilyResultB.getFonts(), i2);
            if (typefaceCreateFromFontInfo == null) {
                return new TypefaceResult(-3);
            }
            lruCache.put(str, typefaceCreateFromFontInfo);
            return new TypefaceResult(typefaceCreateFromFontInfo);
        } catch (PackageManager.NameNotFoundException unused) {
            return new TypefaceResult(-1);
        }
    }

    static Typeface b(final Context context, final FontRequest fontRequest, final int i2, Executor executor, final CallbackWithHandler callbackWithHandler) {
        final String strCreateCacheId = createCacheId(fontRequest, i2);
        Typeface typeface = (Typeface) f3665a.get(strCreateCacheId);
        if (typeface != null) {
            callbackWithHandler.a(new TypefaceResult(typeface));
            return typeface;
        }
        Consumer<TypefaceResult> consumer = new Consumer<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.2
            @Override // androidx.core.util.Consumer
            public void accept(TypefaceResult typefaceResult) {
                if (typefaceResult == null) {
                    typefaceResult = new TypefaceResult(-3);
                }
                callbackWithHandler.a(typefaceResult);
            }
        };
        synchronized (f3666b) {
            try {
                SimpleArrayMap simpleArrayMap = f3667c;
                ArrayList arrayList = (ArrayList) simpleArrayMap.get(strCreateCacheId);
                if (arrayList != null) {
                    arrayList.add(consumer);
                    return null;
                }
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(consumer);
                simpleArrayMap.put(strCreateCacheId, arrayList2);
                Callable<TypefaceResult> callable = new Callable<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.3
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // java.util.concurrent.Callable
                    public TypefaceResult call() {
                        try {
                            return FontRequestWorker.a(strCreateCacheId, context, fontRequest, i2);
                        } catch (Throwable unused) {
                            return new TypefaceResult(-3);
                        }
                    }
                };
                if (executor == null) {
                    executor = DEFAULT_EXECUTOR_SERVICE;
                }
                RequestExecutor.c(executor, callable, new Consumer<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.4
                    @Override // androidx.core.util.Consumer
                    public void accept(TypefaceResult typefaceResult) {
                        synchronized (FontRequestWorker.f3666b) {
                            try {
                                SimpleArrayMap simpleArrayMap2 = FontRequestWorker.f3667c;
                                ArrayList arrayList3 = (ArrayList) simpleArrayMap2.get(strCreateCacheId);
                                if (arrayList3 == null) {
                                    return;
                                }
                                simpleArrayMap2.remove(strCreateCacheId);
                                for (int i3 = 0; i3 < arrayList3.size(); i3++) {
                                    ((Consumer) arrayList3.get(i3)).accept(typefaceResult);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static Typeface c(final Context context, final FontRequest fontRequest, CallbackWithHandler callbackWithHandler, final int i2, int i3) {
        final String strCreateCacheId = createCacheId(fontRequest, i2);
        Typeface typeface = (Typeface) f3665a.get(strCreateCacheId);
        if (typeface != null) {
            callbackWithHandler.a(new TypefaceResult(typeface));
            return typeface;
        }
        if (i3 == -1) {
            TypefaceResult typefaceResultA = a(strCreateCacheId, context, fontRequest, i2);
            callbackWithHandler.a(typefaceResultA);
            return typefaceResultA.f3678a;
        }
        try {
            TypefaceResult typefaceResult = (TypefaceResult) RequestExecutor.d(DEFAULT_EXECUTOR_SERVICE, new Callable<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public TypefaceResult call() {
                    return FontRequestWorker.a(strCreateCacheId, context, fontRequest, i2);
                }
            }, i3);
            callbackWithHandler.a(typefaceResult);
            return typefaceResult.f3678a;
        } catch (InterruptedException unused) {
            callbackWithHandler.a(new TypefaceResult(-3));
            return null;
        }
    }

    private static String createCacheId(@NonNull FontRequest fontRequest, int i2) {
        return fontRequest.a() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + i2;
    }

    static void d() {
        f3665a.evictAll();
    }

    @SuppressLint({"WrongConstant"})
    private static int getFontFamilyResultStatus(@NonNull FontsContractCompat.FontFamilyResult fontFamilyResult) {
        int i2 = 1;
        if (fontFamilyResult.getStatusCode() != 0) {
            return fontFamilyResult.getStatusCode() != 1 ? -3 : -2;
        }
        FontsContractCompat.FontInfo[] fonts = fontFamilyResult.getFonts();
        if (fonts != null && fonts.length != 0) {
            i2 = 0;
            for (FontsContractCompat.FontInfo fontInfo : fonts) {
                int resultCode = fontInfo.getResultCode();
                if (resultCode != 0) {
                    if (resultCode < 0) {
                        return -3;
                    }
                    return resultCode;
                }
            }
        }
        return i2;
    }

    static final class TypefaceResult {

        /* renamed from: a, reason: collision with root package name */
        final Typeface f3678a;

        /* renamed from: b, reason: collision with root package name */
        final int f3679b;

        TypefaceResult(int i2) {
            this.f3678a = null;
            this.f3679b = i2;
        }

        boolean a() {
            return this.f3679b == 0;
        }

        TypefaceResult(Typeface typeface) {
            this.f3678a = typeface;
            this.f3679b = 0;
        }
    }
}
