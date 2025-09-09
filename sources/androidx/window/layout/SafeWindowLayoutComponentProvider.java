package androidx.window.layout;

import android.app.Activity;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.window.extensions.WindowExtensionsProvider;
import androidx.window.extensions.layout.WindowLayoutComponent;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J \u0010\u0010\u001a\u0012\u0012\u0002\b\u0003 \u0012*\b\u0012\u0002\b\u0003\u0018\u00010\u00110\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0013\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0014\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0003J\u0010\u0010\u0016\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0016\u0010\u0017\u001a\u00020\n2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u0019H\u0002J \u0010\u001a\u001a\u0012\u0012\u0002\b\u0003 \u0012*\b\u0012\u0002\b\u0003\u0018\u00010\u00110\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J \u0010\u001b\u001a\u0012\u0012\u0002\b\u0003 \u0012*\b\u0012\u0002\b\u0003\u0018\u00010\u00110\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J \u0010\u001c\u001a\u0012\u0012\u0002\b\u0003 \u0012*\b\u0012\u0002\b\u0003\u0018\u00010\u00110\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0018\u0010\u001d\u001a\u00020\n*\u00020\u000b2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u0011H\u0002J\u0018\u0010\u001d\u001a\u00020\n*\u00020\u000b2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001fH\u0002R\u001d\u0010\u0003\u001a\u0004\u0018\u00010\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\t\u001a\u00020\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\f¨\u0006 "}, d2 = {"Landroidx/window/layout/SafeWindowLayoutComponentProvider;", "", "()V", "windowLayoutComponent", "Landroidx/window/extensions/layout/WindowLayoutComponent;", "getWindowLayoutComponent", "()Landroidx/window/extensions/layout/WindowLayoutComponent;", "windowLayoutComponent$delegate", "Lkotlin/Lazy;", "isPublic", "", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)Z", "canUseWindowLayoutComponent", "classLoader", "Ljava/lang/ClassLoader;", "foldingFeatureClass", "Ljava/lang/Class;", "kotlin.jvm.PlatformType", "isFoldingFeatureValid", "isWindowExtensionsValid", "isWindowLayoutComponentValid", "isWindowLayoutProviderValid", com.alipay.sdk.m.l.c.f9460j, "block", "Lkotlin/Function0;", "windowExtensionsClass", "windowExtensionsProviderClass", "windowLayoutComponentClass", "doesReturn", "clazz", "Lkotlin/reflect/KClass;", "window_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class SafeWindowLayoutComponentProvider {

    @NotNull
    public static final SafeWindowLayoutComponentProvider INSTANCE = new SafeWindowLayoutComponentProvider();

    /* renamed from: windowLayoutComponent$delegate, reason: from kotlin metadata */
    @NotNull
    private static final Lazy windowLayoutComponent = LazyKt.lazy(new Function0<WindowLayoutComponent>() { // from class: androidx.window.layout.SafeWindowLayoutComponentProvider$windowLayoutComponent$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @Nullable
        public final WindowLayoutComponent invoke() {
            ClassLoader classLoader = SafeWindowLayoutComponentProvider.class.getClassLoader();
            if (classLoader == null || !SafeWindowLayoutComponentProvider.INSTANCE.canUseWindowLayoutComponent(classLoader)) {
                return null;
            }
            try {
                return WindowExtensionsProvider.getWindowExtensions().getWindowLayoutComponent();
            } catch (UnsupportedOperationException unused) {
                return null;
            }
        }
    });

    private SafeWindowLayoutComponentProvider() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean canUseWindowLayoutComponent(ClassLoader classLoader) {
        return Build.VERSION.SDK_INT >= 24 && isWindowLayoutProviderValid(classLoader) && isWindowExtensionsValid(classLoader) && isWindowLayoutComponentValid(classLoader) && isFoldingFeatureValid(classLoader);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean doesReturn(Method method, KClass<?> kClass) {
        return doesReturn(method, JvmClassMappingKt.getJavaClass((KClass) kClass));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Class<?> foldingFeatureClass(ClassLoader classLoader) {
        return classLoader.loadClass("androidx.window.extensions.layout.FoldingFeature");
    }

    private final boolean isFoldingFeatureValid(final ClassLoader classLoader) {
        return validate(new Function0<Boolean>() { // from class: androidx.window.layout.SafeWindowLayoutComponentProvider.isFoldingFeatureValid.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Removed duplicated region for block: B:15:0x0060  */
            @Override // kotlin.jvm.functions.Function0
            @org.jetbrains.annotations.NotNull
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Boolean invoke() throws java.lang.NoSuchMethodException, java.lang.SecurityException {
                /*
                    r6 = this;
                    androidx.window.layout.SafeWindowLayoutComponentProvider r0 = androidx.window.layout.SafeWindowLayoutComponentProvider.INSTANCE
                    java.lang.ClassLoader r1 = r1
                    java.lang.Class r1 = androidx.window.layout.SafeWindowLayoutComponentProvider.access$foldingFeatureClass(r0, r1)
                    java.lang.String r2 = "getBounds"
                    r3 = 0
                    java.lang.reflect.Method r2 = r1.getMethod(r2, r3)
                    java.lang.String r4 = "getType"
                    java.lang.reflect.Method r4 = r1.getMethod(r4, r3)
                    java.lang.String r5 = "getState"
                    java.lang.reflect.Method r1 = r1.getMethod(r5, r3)
                    java.lang.String r3 = "getBoundsMethod"
                    kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r3)
                    java.lang.Class<android.graphics.Rect> r3 = android.graphics.Rect.class
                    kotlin.reflect.KClass r3 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r3)
                    boolean r3 = androidx.window.layout.SafeWindowLayoutComponentProvider.access$doesReturn(r0, r2, r3)
                    if (r3 == 0) goto L60
                    boolean r2 = androidx.window.layout.SafeWindowLayoutComponentProvider.access$isPublic(r0, r2)
                    if (r2 == 0) goto L60
                    java.lang.String r2 = "getTypeMethod"
                    kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r2)
                    java.lang.Class r2 = java.lang.Integer.TYPE
                    kotlin.reflect.KClass r3 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r2)
                    boolean r3 = androidx.window.layout.SafeWindowLayoutComponentProvider.access$doesReturn(r0, r4, r3)
                    if (r3 == 0) goto L60
                    boolean r3 = androidx.window.layout.SafeWindowLayoutComponentProvider.access$isPublic(r0, r4)
                    if (r3 == 0) goto L60
                    java.lang.String r3 = "getStateMethod"
                    kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r3)
                    kotlin.reflect.KClass r2 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r2)
                    boolean r2 = androidx.window.layout.SafeWindowLayoutComponentProvider.access$doesReturn(r0, r1, r2)
                    if (r2 == 0) goto L60
                    boolean r0 = androidx.window.layout.SafeWindowLayoutComponentProvider.access$isPublic(r0, r1)
                    if (r0 == 0) goto L60
                    r0 = 1
                    goto L61
                L60:
                    r0 = 0
                L61:
                    java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.layout.SafeWindowLayoutComponentProvider.AnonymousClass1.invoke():java.lang.Boolean");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isPublic(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    private final boolean isWindowExtensionsValid(final ClassLoader classLoader) {
        return validate(new Function0<Boolean>() { // from class: androidx.window.layout.SafeWindowLayoutComponentProvider.isWindowExtensionsValid.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Removed duplicated region for block: B:7:0x002d  */
            @Override // kotlin.jvm.functions.Function0
            @org.jetbrains.annotations.NotNull
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Boolean invoke() throws java.lang.NoSuchMethodException, java.lang.SecurityException {
                /*
                    r4 = this;
                    androidx.window.layout.SafeWindowLayoutComponentProvider r0 = androidx.window.layout.SafeWindowLayoutComponentProvider.INSTANCE
                    java.lang.ClassLoader r1 = r1
                    java.lang.Class r1 = androidx.window.layout.SafeWindowLayoutComponentProvider.access$windowExtensionsClass(r0, r1)
                    java.lang.String r2 = "getWindowLayoutComponent"
                    r3 = 0
                    java.lang.reflect.Method r1 = r1.getMethod(r2, r3)
                    java.lang.ClassLoader r2 = r1
                    java.lang.Class r2 = androidx.window.layout.SafeWindowLayoutComponentProvider.access$windowLayoutComponentClass(r0, r2)
                    java.lang.String r3 = "getWindowLayoutComponentMethod"
                    kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r3)
                    boolean r3 = androidx.window.layout.SafeWindowLayoutComponentProvider.access$isPublic(r0, r1)
                    if (r3 == 0) goto L2d
                    java.lang.String r3 = "windowLayoutComponentClass"
                    kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r3)
                    boolean r0 = androidx.window.layout.SafeWindowLayoutComponentProvider.access$doesReturn(r0, r1, r2)
                    if (r0 == 0) goto L2d
                    r0 = 1
                    goto L2e
                L2d:
                    r0 = 0
                L2e:
                    java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.layout.SafeWindowLayoutComponentProvider.C03231.invoke():java.lang.Boolean");
            }
        });
    }

    @RequiresApi(24)
    private final boolean isWindowLayoutComponentValid(final ClassLoader classLoader) {
        return validate(new Function0<Boolean>() { // from class: androidx.window.layout.SafeWindowLayoutComponentProvider.isWindowLayoutComponentValid.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Boolean invoke() throws NoSuchMethodException, SecurityException {
                SafeWindowLayoutComponentProvider safeWindowLayoutComponentProvider = SafeWindowLayoutComponentProvider.INSTANCE;
                Class clsWindowLayoutComponentClass = safeWindowLayoutComponentProvider.windowLayoutComponentClass(classLoader);
                boolean z2 = false;
                Method addListenerMethod = clsWindowLayoutComponentClass.getMethod("addWindowLayoutInfoListener", Activity.class, i.a());
                Method removeListenerMethod = clsWindowLayoutComponentClass.getMethod("removeWindowLayoutInfoListener", i.a());
                Intrinsics.checkNotNullExpressionValue(addListenerMethod, "addListenerMethod");
                if (safeWindowLayoutComponentProvider.isPublic(addListenerMethod)) {
                    Intrinsics.checkNotNullExpressionValue(removeListenerMethod, "removeListenerMethod");
                    if (safeWindowLayoutComponentProvider.isPublic(removeListenerMethod)) {
                        z2 = true;
                    }
                }
                return Boolean.valueOf(z2);
            }
        });
    }

    private final boolean isWindowLayoutProviderValid(final ClassLoader classLoader) {
        return validate(new Function0<Boolean>() { // from class: androidx.window.layout.SafeWindowLayoutComponentProvider.isWindowLayoutProviderValid.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Boolean invoke() throws NoSuchMethodException, SecurityException {
                SafeWindowLayoutComponentProvider safeWindowLayoutComponentProvider = SafeWindowLayoutComponentProvider.INSTANCE;
                Method getWindowExtensionsMethod = safeWindowLayoutComponentProvider.windowExtensionsProviderClass(classLoader).getDeclaredMethod("getWindowExtensions", null);
                Class windowExtensionsClass = safeWindowLayoutComponentProvider.windowExtensionsClass(classLoader);
                Intrinsics.checkNotNullExpressionValue(getWindowExtensionsMethod, "getWindowExtensionsMethod");
                Intrinsics.checkNotNullExpressionValue(windowExtensionsClass, "windowExtensionsClass");
                return Boolean.valueOf(safeWindowLayoutComponentProvider.doesReturn(getWindowExtensionsMethod, (Class<?>) windowExtensionsClass) && safeWindowLayoutComponentProvider.isPublic(getWindowExtensionsMethod));
            }
        });
    }

    private final boolean validate(Function0<Boolean> block) {
        try {
            return block.invoke().booleanValue();
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Class<?> windowExtensionsClass(ClassLoader classLoader) {
        return classLoader.loadClass("androidx.window.extensions.WindowExtensions");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Class<?> windowExtensionsProviderClass(ClassLoader classLoader) {
        return classLoader.loadClass("androidx.window.extensions.WindowExtensionsProvider");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Class<?> windowLayoutComponentClass(ClassLoader classLoader) {
        return classLoader.loadClass("androidx.window.extensions.layout.WindowLayoutComponent");
    }

    @Nullable
    public final WindowLayoutComponent getWindowLayoutComponent() {
        return (WindowLayoutComponent) windowLayoutComponent.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean doesReturn(Method method, Class<?> cls) {
        return method.getReturnType().equals(cls);
    }
}
