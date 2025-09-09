package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SimpleArrayMap;
import androidx.core.util.Preconditions;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class FragmentController {
    private final FragmentHostCallback<?> mHost;

    private FragmentController(FragmentHostCallback<?> fragmentHostCallback) {
        this.mHost = fragmentHostCallback;
    }

    @NonNull
    public static FragmentController createController(@NonNull FragmentHostCallback<?> fragmentHostCallback) {
        return new FragmentController((FragmentHostCallback) Preconditions.checkNotNull(fragmentHostCallback, "callbacks == null"));
    }

    public void attachHost(@Nullable Fragment fragment) {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        fragmentHostCallback.f4168a.m(fragmentHostCallback, fragmentHostCallback, fragment);
    }

    public void dispatchActivityCreated() {
        this.mHost.f4168a.s();
    }

    @Deprecated
    public void dispatchConfigurationChanged(@NonNull Configuration configuration) {
        this.mHost.f4168a.u(configuration, true);
    }

    public boolean dispatchContextItemSelected(@NonNull MenuItem menuItem) {
        return this.mHost.f4168a.v(menuItem);
    }

    public void dispatchCreate() {
        this.mHost.f4168a.w();
    }

    @Deprecated
    public boolean dispatchCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        return this.mHost.f4168a.x(menu, menuInflater);
    }

    public void dispatchDestroy() {
        this.mHost.f4168a.y();
    }

    public void dispatchDestroyView() {
        this.mHost.f4168a.z();
    }

    @Deprecated
    public void dispatchLowMemory() {
        this.mHost.f4168a.A(true);
    }

    @Deprecated
    public void dispatchMultiWindowModeChanged(boolean z2) {
        this.mHost.f4168a.B(z2, true);
    }

    @Deprecated
    public boolean dispatchOptionsItemSelected(@NonNull MenuItem menuItem) {
        return this.mHost.f4168a.E(menuItem);
    }

    @Deprecated
    public void dispatchOptionsMenuClosed(@NonNull Menu menu) {
        this.mHost.f4168a.F(menu);
    }

    public void dispatchPause() {
        this.mHost.f4168a.G();
    }

    @Deprecated
    public void dispatchPictureInPictureModeChanged(boolean z2) {
        this.mHost.f4168a.H(z2, true);
    }

    @Deprecated
    public boolean dispatchPrepareOptionsMenu(@NonNull Menu menu) {
        return this.mHost.f4168a.I(menu);
    }

    @Deprecated
    public void dispatchReallyStop() {
    }

    public void dispatchResume() {
        this.mHost.f4168a.K();
    }

    public void dispatchStart() {
        this.mHost.f4168a.L();
    }

    public void dispatchStop() {
        this.mHost.f4168a.M();
    }

    @Deprecated
    public void doLoaderDestroy() {
    }

    @Deprecated
    public void doLoaderRetain() {
    }

    @Deprecated
    public void doLoaderStart() {
    }

    @Deprecated
    public void doLoaderStop(boolean z2) {
    }

    @Deprecated
    public void dumpLoaders(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
    }

    public boolean execPendingActions() {
        return this.mHost.f4168a.P(true);
    }

    @Nullable
    public Fragment findFragmentByWho(@NonNull String str) {
        return this.mHost.f4168a.S(str);
    }

    @NonNull
    public List<Fragment> getActiveFragments(@SuppressLint({"UnknownNullness"}) List<Fragment> list) {
        return this.mHost.f4168a.W();
    }

    public int getActiveFragmentsCount() {
        return this.mHost.f4168a.V();
    }

    @NonNull
    public FragmentManager getSupportFragmentManager() {
        return this.mHost.f4168a;
    }

    @SuppressLint({"UnknownNullness"})
    @Deprecated
    public LoaderManager getSupportLoaderManager() {
        throw new UnsupportedOperationException("Loaders are managed separately from FragmentController, use LoaderManager.getInstance() to obtain a LoaderManager.");
    }

    public void noteStateNotSaved() {
        this.mHost.f4168a.q0();
    }

    @Nullable
    public View onCreateView(@Nullable View view, @NonNull String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        return this.mHost.f4168a.Z().onCreateView(view, str, context, attributeSet);
    }

    @Deprecated
    public void reportLoaderStart() {
    }

    @Deprecated
    public void restoreAllState(@Nullable Parcelable parcelable, @Nullable List<Fragment> list) {
        this.mHost.f4168a.x0(parcelable, new FragmentManagerNonConfig(list, null, null));
    }

    @Deprecated
    public void restoreLoaderNonConfig(@SuppressLint({"UnknownNullness"}) SimpleArrayMap<String, LoaderManager> simpleArrayMap) {
    }

    @Deprecated
    public void restoreSaveState(@Nullable Parcelable parcelable) {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (!(fragmentHostCallback instanceof ViewModelStoreOwner)) {
            throw new IllegalStateException("Your FragmentHostCallback must implement ViewModelStoreOwner to call restoreSaveState(). Call restoreAllState()  if you're still using retainNestedNonConfig().");
        }
        fragmentHostCallback.f4168a.z0(parcelable);
    }

    @Nullable
    @Deprecated
    public SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        return null;
    }

    @Nullable
    @Deprecated
    public FragmentManagerNonConfig retainNestedNonConfig() {
        return this.mHost.f4168a.B0();
    }

    @Nullable
    @Deprecated
    public List<Fragment> retainNonConfig() {
        FragmentManagerNonConfig fragmentManagerNonConfigB0 = this.mHost.f4168a.B0();
        if (fragmentManagerNonConfigB0 == null || fragmentManagerNonConfigB0.b() == null) {
            return null;
        }
        return new ArrayList(fragmentManagerNonConfigB0.b());
    }

    @Nullable
    @Deprecated
    public Parcelable saveAllState() {
        return this.mHost.f4168a.D0();
    }

    @Deprecated
    public void restoreAllState(@Nullable Parcelable parcelable, @Nullable FragmentManagerNonConfig fragmentManagerNonConfig) {
        this.mHost.f4168a.x0(parcelable, fragmentManagerNonConfig);
    }
}
