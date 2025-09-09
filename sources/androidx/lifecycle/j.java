package androidx.lifecycle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract /* synthetic */ class j {
    static {
        ViewModelProvider.Factory.Companion companion = ViewModelProvider.Factory.INSTANCE;
    }

    public static ViewModel a(ViewModelProvider.Factory factory, Class modelClass) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        throw new UnsupportedOperationException("Factory.create(String) is unsupported.  This Factory requires `CreationExtras` to be passed into `create` method.");
    }

    public static ViewModel b(ViewModelProvider.Factory factory, Class modelClass, CreationExtras extras) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        Intrinsics.checkNotNullParameter(extras, "extras");
        return factory.create(modelClass);
    }

    public static ViewModelProvider.Factory c(ViewModelInitializer... viewModelInitializerArr) {
        return ViewModelProvider.Factory.INSTANCE.from(viewModelInitializerArr);
    }
}
